package com.wingjay.jayandroid.skin;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.feng.skin.manager.base.BaseActivity;
import cn.feng.skin.manager.listener.ILoaderListener;
import cn.feng.skin.manager.loader.SkinManager;
import com.wingjay.jayandroid.R;

/**
 * AndroidSkinLoaderDemoActivity
 *
 * @author wingjay
 * @date 2017/08/03
 */
public class AndroidSkinLoaderDemoActivity extends BaseActivity {

    private boolean applied;
    private Button changeSkinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_skin_loader_demo);
        changeSkinButton = (Button) findViewById(R.id.change_skin);
        changeSkinButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                applySkin();
            }
        });
    }

    private void applySkin() {
        if (applied) {
            SkinManager.getInstance().restoreDefaultTheme();
            applied = false;
            return;
        }
        String path = Environment.getExternalStorageDirectory() + File.separator + "BlackFantacy.skin";
        SkinManager.getInstance().load(path, new ILoaderListener() {
            @Override
            public void onStart() {
                Log.d("jaydebug", "Skin onStart");
            }

            @Override
            public void onSuccess() {
                Log.d("jaydebug", "Skin onSuccess");
                applied = true;
            }

            @Override
            public void onFailed() {
                Log.d("jaydebug", "Skin onFailed");
            }
        });
    }
}
