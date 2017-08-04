package com.wingjay.wingjay_skin_loader;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import com.wingjay.wingjay_skin_loader.listener.OnSkinLoadListener;
import com.wingjay.wingjay_skin_loader.listener.OnSkinUpdateListener;

/**
 * OkSkin: load skin file and notify outside when skin updated.
 *
 * @author wingjay
 * @date 2017/08/03
 */
public class OkSkin {
    private WeakReference<Context> contextWeakReference;
    private Resources systemResources;

    private static final Object lock = new Object();
    private static OkSkin instance;
    private List<OnSkinUpdateListener> skinUpdateListeners = new ArrayList<>();
    private OkSkin() {}
    public static OkSkin getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new OkSkin();
                }
            }
        }

        return instance;
    }

    public void init(Context context) {
        this.contextWeakReference = new WeakReference<>(context);
        this.systemResources = context.getResources();
    }

    public void listenSkinUpdate(OnSkinUpdateListener listener) {
        if (skinUpdateListeners.contains(listener)) {
            throw new IllegalArgumentException("this OnSkinUpdateListener already be added.");
        }
        skinUpdateListeners.add(listener);
    }

    public void removeSkinUpdateListener(OnSkinUpdateListener listener) {
        skinUpdateListeners.remove(listener);
    }

    private void notifySkinUpdate() {
        for (OnSkinUpdateListener listener : skinUpdateListeners) {
            listener.onSkinUpdate();
        }
    }

    public void restoreDefaultTheme() {

    }

    public void load(String skinFilePath, OnSkinLoadListener listener) {
        if (contextWeakReference.get() == null) {
            return;
        }
        // build new Resource
        try {
            PackageManager packageManager = contextWeakReference.get().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(skinFilePath, PackageManager.GET_ACTIVITIES);
            String skinPackageName = packageInfo.packageName;

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(assetManager, skinFilePath);

            // After fetch this skin resources, we can start to apply new skin.
            Resources skinResources = new Resources(assetManager, systemResources.getDisplayMetrics(),
                systemResources.getConfiguration());

            SkinResourceManager.setup(systemResources, skinResources, skinPackageName);
            notifySkinUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure();
        }
    }
}
