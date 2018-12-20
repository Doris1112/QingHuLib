package com.doris.qinghulib;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.qhsd.library.base.BaseNumber;
import com.qhsd.library.base.web.BaseLibWebActivity;
import com.qhsd.library.base.web.CustomWebChromeClient;
import com.qhsd.library.base.web.CustomWebViewClient;
import com.qhsd.library.base.web.CustomX5WebChromeClient;
import com.qhsd.library.base.web.CustomX5WebViewClient;
import com.qhsd.library.utils.ScreenUtils;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebView;

/**
 * @author Doris
 * @date 2018/12/19
 **/
public class WebActivity extends BaseLibWebActivity {

    private CustomX5WebChromeClient mWebChromeClient;
    private CustomWebChromeClient mWebChromeClient1;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initViewBefore() {
        super.initViewBefore();
        ScreenUtils.setStatusBarColor(this, R.color.gray_c);
    }

    @Override
    protected void initView() {
        super.initView();
        ProgressBar webProgress = findViewById(R.id.webProgress);

        WebView webView = findViewById(R.id.webView);
        initBaseX5WebView(webView);
        mWebChromeClient = new CustomX5WebChromeClient(this, webProgress);
        webView.setWebChromeClient(mWebChromeClient);
        webView.setWebViewClient(new CustomX5WebViewClient(this, webProgress));
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String s, final String s1, final String s2, final String s3, final long l) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

//        webView.loadUrl("file:///android_asset/webpage/fileChooser.html");
        webView.loadUrl("http://btreg.gzqiqub.com/borrowUser/toTgRedister?code=bb2af9c3461bd0adf4ebcdf7b94296f6");

//        android.webkit.WebView webView1 = findViewById(R.id.webView1);
//        initBaseWebView(webView1);
//        mWebChromeClient1 = new CustomWebChromeClient(this){
//            @Override
//            public void onProgressChanged(android.webkit.WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                webProgress.setProgress(newProgress);
//                if (newProgress >= BaseNumber.ONE_HUNDRED){
//                    webProgress.setVisibility(View.GONE);
//                }
//            }
//        };
//        webView1.setWebChromeClient(mWebChromeClient1);
//        webView1.setWebViewClient(new CustomWebViewClient(this){
//            @Override
//            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                webProgress.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(android.webkit.WebView view, String url) {
//                super.onPageFinished(view, url);
//                webProgress.setVisibility(View.GONE);
//            }
//        });
//        webView1.setDownloadListener(new android.webkit.DownloadListener() {
//            @Override
//            public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                });
//            }
//        });
//        webView1.loadUrl("file:///android_asset/webpage/fileChooser.html");
//        webView1.loadUrl("http://btreg.gzqiqub.com/borrowUser/toTgRedister?code=bb2af9c3461bd0adf4ebcdf7b94296f6");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mWebChromeClient != null && mWebChromeClient.onChooseResult(requestCode, data)){
            Log.d(TAG, "onActivityResult: 已处理");
        }
        if (mWebChromeClient1 != null && mWebChromeClient1.onChooseResult(requestCode, data)){
            Log.d(TAG, "onActivityResult: 已处理");
        }
    }
}
