package com.wingjay.jayandroid.skin;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.wingjay.jayandroid.R;
import com.wingjay.wingjay_skin_loader.OkSkin;
import com.wingjay.wingjay_skin_loader.OkSkin.OnSkinLoadListener;

/**
 * JaySkinLoaderDemoActivity
 *
 * @author wingjay
 * @date 2017/08/03
 */
public class JaySkinLoaderDemoActivity extends BaseSkinActivity {

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
            OkSkin.getInstance().restoreDefaultTheme();
            applied = false;
            return;
        }
        String path = Environment.getExternalStorageDirectory() + File.separator + "BlackFantacy.skin";
        OkSkin.getInstance().load(path, new OnSkinLoadListener() {
            @Override
            public void onSuccess() {
                Log.d("jaydebug", "Skin onSuccess");
                applied = true;
            }

            @Override
            public void onFailure() {
                Log.d("jaydebug", "Skin onFailed");
            }
        });
    }
}
