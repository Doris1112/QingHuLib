package com.qhsd.library.base.web;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.qhsd.library.R;
import com.qhsd.library.base.BaseLibActivity;
import com.qhsd.library.base.BaseLibApplication;
import com.qhsd.library.config.BaseNumber;
import com.qhsd.library.helper.AndroidStatusBugWorkaround;
import com.qhsd.library.utils.ToastUtils;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.sdk.DownloadListener;

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public class BaseLibWebActivity extends BaseLibActivity {

    protected WebView mWebView;
    protected com.tencent.smtt.sdk.WebView mX5WebView;
    protected ProgressBar mWebProgress;

    protected CustomWebChromeClient mWebChromeClient;
    protected CustomX5WebChromeClient mX5WebChromeClient;

    protected boolean mIsX5 = true;
    protected String mTitle, mUrl, mDownloadApkUrl;
    protected static final String GET_WRITE_PERMISSION_DOWNLOAD = "download";
    protected String mGetWritePermission;

    @Override
    protected int getLayoutResId() {
        return R.layout.lib_layout_web;
    }


    @Override
    protected void initViewBefore() {
        super.initViewBefore();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        mIsX5 = getIntent().getBooleanExtra("isX5", mIsX5);
        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
    }

    @Override
    protected void initView() {
        super.initView();
        if (mUrl.isEmpty()) {
            ToastUtils.showToastCenter(this, "跳转链接错误！");
            finish();
        }
        mWebView = findViewById(R.id.lib_web);
        mX5WebView = findViewById(R.id.lib_web_x5);
        mWebProgress = findViewById(R.id.lib_web_progress);
        initBaseX5WebView(mX5WebView);
        initBaseWebView(mWebView);
        setBaseTitle(mTitle);
        setWebUrl(mUrl);
    }

    /**
     * 初始化WebView
     *
     * @param webView WebView
     */
    protected final void initBaseWebView(WebView webView) {
        if (webView == null) {
            return;
        }
        // 为防止键盘弹出挡住页面
        AndroidStatusBugWorkaround.assistActivity(this);

        if (mX5WebView != null){
            mX5WebView.setVisibility(View.GONE);
        }
        webView.setVisibility(View.VISIBLE);
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
        // 不可复制
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        // 无缓存
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 特别注意：5.1以上默认禁止了https和http混用。下面代码是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(2);
        }
        // webView支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 不支持缩放
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        // 设置支持DOM storage API
        webSettings.setDomStorageEnabled(true);
        webView.requestFocus();

        mWebChromeClient = getCustomWebChromeClient();
        webView.setWebChromeClient(mWebChromeClient);
        webView.setWebViewClient(getCustomWebViewClient());

        mWebView.setDownloadListener(new android.webkit.DownloadListener() {
            @Override
            public void onDownloadStart(final String url, String userAgent,
                                        String contentDisposition, String mimeType,
                                        long contentLength) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadApkUrl = url;
                        Log.d(TAG, "onDownloadStart: mWebView-" + mDownloadApkUrl);
                        initPermissionForWriteExternalStorage(GET_WRITE_PERMISSION_DOWNLOAD);
                    }
                });
            }
        });
    }

    protected CustomWebChromeClient getCustomWebChromeClient(){
        return new CustomWebChromeClient(this, mWebProgress);
    }

    protected CustomWebViewClient getCustomWebViewClient(){
        return new CustomWebViewClient(this, mWebProgress);
    }

    /**
     * 初始化X5 WebView
     *
     * @param webView WebView
     */
    protected final void initBaseX5WebView(com.tencent.smtt.sdk.WebView webView) {
        if (webView == null) {
            return;
        }

        if (mWebView != null){
            mWebView.setVisibility(View.GONE);
        }
        webView.setVisibility(View.VISIBLE);
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
        // 不可复制
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        IX5WebViewExtension extension = webView.getX5WebViewExtension();
        if (extension != null){
            extension.setScrollBarFadingEnabled(false);
        }

        com.tencent.smtt.sdk.WebSettings webSettings = webView.getSettings();
        // 特别注意：5.1以上默认禁止了https和http混用。下面代码是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(2);
        }
        // 不使用缓存，直接用网络加载
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webView支持javascript
        webSettings.setJavaScriptEnabled(true);
        // js可以自动打开window
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 让页面加载显示适应手机的屏幕大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 不可缩放
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        // 支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        // 设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        // 设置支持DOM storage API
        webSettings.setDomStorageEnabled(true);

        mX5WebChromeClient = getCustomX5WebChromeClient();
        webView.setWebChromeClient(mX5WebChromeClient);
        webView.setWebViewClient(getCustomX5WebViewClient());

        mX5WebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String s, String s1, String s2, String s3, long l) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadApkUrl = s;
                        Log.d(TAG, "onDownloadStart: mX5WebView-" + mDownloadApkUrl);
                        initPermissionForWriteExternalStorage(GET_WRITE_PERMISSION_DOWNLOAD);
                    }
                });
            }
        });
    }

    protected CustomX5WebChromeClient getCustomX5WebChromeClient(){
        return new CustomX5WebChromeClient(this, mWebProgress);
    }

    protected CustomX5WebViewClient getCustomX5WebViewClient(){
        return new CustomX5WebViewClient(this, mWebProgress);
    }

    protected void setWebUrl(String url){
        if (BaseLibApplication.isInitX5EnvironmentSuccess && mIsX5){
            Log.d(TAG, "initWeb: X5");
            if (mX5WebView != null){
                mX5WebView.loadUrl(url);
            }
            if (mWebView != null){
                mWebView.setVisibility(View.GONE);
            }
        } else {
            Log.d(TAG, "initWeb: Default");
            if (mWebView != null){
                mWebView.loadUrl(url);
            }
            if (mX5WebView != null){
                mX5WebView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取读写权限
     */
    protected final void initPermissionForWriteExternalStorage(String state) {
        mGetWritePermission = state;
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    BaseNumber.REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            getWriteExternalStorageAfterDoing();
        }
    }

    /**
     * 读写权限获取成功之后操作
     */
    public void getWriteExternalStorageAfterDoing() {

    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == BaseNumber.REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length == 0) {
                return;
            }
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWriteExternalStorageAfterDoing();
            } else {
                ToastUtils.showToastCenter(this, "无法获取读写入权限！");
            }
        } else {
            onBaseRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void onBaseRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

    }

    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mWebChromeClient != null && mWebChromeClient.onChooseResult(requestCode, data)){
            Log.d(TAG, "onActivityResult: mWebChromeClient.onChooseResult 已处理");
        } else if (mX5WebChromeClient != null && mX5WebChromeClient.onChooseResult(requestCode, data)){
            Log.d(TAG, "onActivityResult: mX5WebChromeClient.onChooseResult 已处理");
        } else {
            onBaseActivityResult(requestCode, resultCode, data);
        }
    }

    protected void onBaseActivityResult(int requestCode, int resultCode, Intent data){

    }

    @Override
    public void onBackPressed() {
        if (BaseLibApplication.isInitX5EnvironmentSuccess){
            if (mX5WebView != null && mX5WebView.canGoBack()) {
                mX5WebView.goBack();
            } else {
                super.onBackPressed();
            }
        } else {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
