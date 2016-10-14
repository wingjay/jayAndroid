package com.wingjay.jayandroid.abot;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wingjay.jayandroid.App;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jay on 10/14/16.
 */

public class ABotActivity extends BaseActivity {

  @Inject
  ApiAiService apiAiService;

  @Bind(R.id.query)
  EditText query;

  @Bind(R.id.query_btn)
  Button doQueryBtn;

  @Bind(R.id.query_result)
  TextView queryResult;

  @Bind(R.id.query_status)
  TextView queryStatus;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((App) getApplication()).getAppComponent().inject(this);

    setContentView(R.layout.activity_abot);


  }

  @OnClick(R.id.query_btn)
  void doQuery() {
    String queryString = query.getText().toString();
    if (TextUtils.isEmpty(queryString)) {
      Toast.makeText(this, "query string cannot be null", Toast.LENGTH_SHORT).show();
      return;
    }
    queryStatus.setText("start query abot with " + queryString);
    apiAiService.query(queryString)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<ApiAiResponse>() {
          @Override
          public void call(ApiAiResponse apiAiResponse) {
            queryStatus.setText("success query");
            Gson gson = new Gson();
            queryResult.setText(gson.toJson(apiAiResponse));
          }
        }, new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            queryStatus.setText("success fail, throwable " + throwable);
          }
        });
  }


}
