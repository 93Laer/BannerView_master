package com.example.laitianbing.bannerview_master;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class WebActivity extends AppCompatActivity {
    private static final String EXTRA_URL = "url";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String url = getIntent().getStringExtra(EXTRA_URL);
        mWebView = findViewById(R.id.webview);
        ProgressBar progressBar = findViewById(R.id.pb_progress);
        WebViewUtils.initWebView(mWebView, progressBar);
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void start(Activity activity, @NonNull String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(activity, WebActivity.class);
            intent.putExtra(EXTRA_URL, url);
            ContextCompat.startActivity(activity, intent, null);
        }
    }
}
