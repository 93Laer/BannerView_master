package com.example.laitianbing.bannerview_master;

import android.os.Build;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ZoomButtonsController;



import java.lang.reflect.Field;


public class WebViewUtils {
    public static final String URL = "url";
    public static final String TITLE = "title";


    /**
     * 初始化网页浏览器
     *
     * @param webView
     */
    public static void initWebView(final WebView webView, final ProgressBar pbWeb) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); // 开启JavaScript支持
        settings.setDefaultTextEncodingName("GBK"); // 中文支持  webView.getSettings().setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {//用于判断是否为Android 3.0系统, 然后隐藏缩放控件
            settings.setDisplayZoomControls(false);
        } else {
            setZoomControlGone(webView);  // Android 3.0(11) 以下使用以下方法
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (pbWeb != null)
                    pbWeb.setProgress(newProgress);
                if (newProgress >= 100) {
                    webView.setTag(R.id.tag_web_loaded, true);
                    if (pbWeb != null)
                        pbWeb.setVisibility(View.INVISIBLE);
                } else {
                    webView.setTag(R.id.tag_web_loaded, false);
                    if (pbWeb != null)
                        pbWeb.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

        });
    }


    /**
     * 隐藏网页上放大缩小按钮
     *
     * @param view WebView
     */
    private static void setZoomControlGone(View view) {
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
                    view);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


}
