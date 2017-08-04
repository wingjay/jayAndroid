package com.wingjay.jayandroid.skin;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.view.LayoutInflater;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.wingjay_skin_loader.OkSkin;
import com.wingjay.wingjay_skin_loader.SkinInflaterFactory;
import com.wingjay.wingjay_skin_loader.listener.OnSkinUpdateListener;

/**
 * BaseSkinActivity
 *
 * @author wingjay
 * @date 2017/08/04
 */
public class BaseSkinActivity extends BaseActivity implements OnSkinUpdateListener {

    private SkinInflaterFactory skinInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        skinInflaterFactory = new SkinInflaterFactory(this);
        LayoutInflater inflater = LayoutInflater.from(this); // shouldn't use LayoutInflater.from(applicationContext)
        try {
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(inflater, false);
            inflater.setFactory(skinInflaterFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        OkSkin.getInstance().listenSkinUpdate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkSkin.getInstance().removeSkinUpdateListener(this);
        skinInflaterFactory.clean();
    }

    @Override
    public void onSkinUpdate() {
        skinInflaterFactory.updateSkin();
    }
}
