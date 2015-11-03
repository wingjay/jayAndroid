package com.wingjay.jayandroid.contentprovider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.contentprovider.Users;

/**
 * Created by wingjay on 9/15/15.
 */
public class ContentResolverActivity extends BaseActivity {

    private static final String TAG = "ContentResolverActivity";
    private ContentResolver contentResolver;
    private static final Uri uri = Uri.parse("content://" + Users.AUTHORITY);

    private EditText idEditText,nameEditText, ageEditText;
    private Button insertBtn, queryBtn, deleteBtn, updateBtn;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_resolver);

        contentResolver = getContentResolver();

        nameEditText = (EditText) findViewById(R.id.name);
        ageEditText = (EditText) findViewById(R.id.age);
        idEditText = (EditText) findViewById(R.id.id);
        insertBtn = (Button) findViewById(R.id.insert);
        queryBtn = (Button) findViewById(R.id.query);
        deleteBtn = (Button) findViewById(R.id.delete);
        updateBtn = (Button) findViewById(R.id.update);
        contentTextView = (TextView) findViewById(R.id.content);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(Users.User.NAME, nameEditText.getText().toString());
                values.put(Users.User.AGE, ageEditText.getText().toString());
                insert(values);
                query();
            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(Users.User.NAME, nameEditText.getText().toString());
                values.put(Users.User.AGE, ageEditText.getText().toString());
                update(values, Long.parseLong(idEditText.getText().toString()));
                query();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(Long.parseLong(idEditText.getText().toString()));
                query();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        query();
    }

    private void insert(ContentValues values) {
        Uri insertUri = Users.User.JAY_ANDROID_USERS_CONTENT_URI;
        Log.i(TAG, "send insert with uri : " + insertUri.toString() + ", and values: " + values.toString());
        contentResolver.insert(insertUri, values);
    }
    private void query() {
        Uri queryUri = Users.User.JAY_ANDROID_USERS_CONTENT_URI;
        Log.i(TAG, "send query with uri : " + queryUri.toString());
        Cursor cursor = contentResolver.query(queryUri, null, null, null, null);
        Log.i(TAG, "query result : " + cursor.toString());
        if (cursor == null || cursor.getCount() == 0) {
            contentTextView.setText("no content");
            return;
        }
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            String[] columns = cursor.getColumnNames();
            for (int i = 0; i<columns.length; i++) {
                builder.append(columns[i] + " : " + cursor.getString(cursor.getColumnIndex(columns[i])) + ", ");
            }
            builder.append("<br/>");
        }
        contentTextView.setText(Html.fromHtml(builder.toString()));
    }

    private void update(ContentValues values, long updateId) {
        Uri updateUri = ContentUris.withAppendedId(Users.User.JAY_ANDROID_USER_CONTENT_URI, updateId);
        Log.i(TAG, "send update with uri : " + updateUri.toString() + ", and new values: " + values.toString());
        contentResolver.update(updateUri, values, null, null);
    }

    private void delete(long deleteId) {
        Uri deleteUri = ContentUris.withAppendedId(Users.User.JAY_ANDROID_USER_CONTENT_URI, deleteId);
        Log.i(TAG, "send delete with uri : " + deleteUri.toString());
        contentResolver.delete(deleteUri, null, null);
    }

}
