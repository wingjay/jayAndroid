package com.wingjay.jayandroid.drag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.OnClick;

/**
 * Created by wingjay on 10/25/15.
 */
public class DragChooseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drag_choose);

        findViewById(R.id.buttonDragH).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DragChooseActivity.this, DragActivity.class);
                intent.putExtra("horizontal", true);
                startActivity(intent);
            }
        });
        findViewById(R.id.buttonDragV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DragChooseActivity.this, DragActivity.class);
                intent.putExtra("vertical", true);
                startActivity(intent);
            }
        });
        findViewById(R.id.buttonDragEdge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DragChooseActivity.this, DragActivity.class);
                intent.putExtra("edge", true);
                startActivity(intent);
            }
        });
        findViewById(R.id.buttonDragCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DragChooseActivity.this, DragActivity.class);
                intent.putExtra("capture", true);
                startActivity(intent);
            }
        });
        findViewById(R.id.buttonYoutube).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, YoutubeActivity.class);
                //startActivity(intent);
            }
        });

    }

    @OnClick(R.id.buttonDragHorizentalItem)
    void drag() {
        Intent intent = new Intent(DragChooseActivity.this, DragActivity.class);
        intent.putExtra("horizental", true);
        startActivity(intent);
    }

}
