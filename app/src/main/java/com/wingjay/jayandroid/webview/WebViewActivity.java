package com.wingjay.jayandroid.webview;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

/**
 * Created by Jay on 11/3/16.
 */
public class WebViewActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_webview);
    WebView webView = (WebView) findViewById(R.id.webview);
    String htmlWithJs = "<button onclick=\"makeToast('Haha')\" >Click me</button>" +
        " <script type=\"text/javascript\">" +
        "function makeToast(content) {" +
        " Android.showToast(content);" +
        "}" +
        "</script>";
    webView.addJavascriptInterface(this, "Android");
    webView.getSettings().setJavaScriptEnabled(true);
    webView.loadData(htmlWithJs, "text/html", "UTF-8");
  }

  @JavascriptInterface
  public void showToast(String content) {
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
  }
}
