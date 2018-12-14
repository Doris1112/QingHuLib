package com.qhsd.library.base.web;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.qhsd.library.base.LibBaseActivity;
import com.qhsd.library.utils.NetworkUtils;
import com.qhsd.library.utils.ToastUtils;

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public abstract class LibBaseWebActivity extends LibBaseActivity {

    protected static final int REQUEST_CODE_PERMISSION_CAMERA = 0x101;
    protected static final int REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE = 0x102;

    /**
     * 初始化WebView
     *
     * @param webView WebView
     */
    protected void initBaseWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        // 自适应屏幕
        webView.getSettings().setUseWideViewPort(true);
        // 不支持缩放(如果要支持缩放，html页面本身也要支持缩放：不能加user-scalable=no)
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setDomStorageEnabled(true);
        if (NetworkUtils.isNetworkAvailable(this)) {
            // 有网络连接，设置默认缓存模式
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            // 无网络连接，设置本地缓存模式
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        //设置缓存路径
        webView.getSettings().setAppCachePath(getFilesDir().getAbsolutePath() + "android_web");
        //开启缓存功能
        webView.getSettings().setAppCacheEnabled(true);
        //支持通过JS打开新窗口
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webView.getSettings().setLoadsImagesAutomatically(true);
        //设置编码格式
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setGeolocationDatabasePath(getApplicationContext()
                .getDir("database", Context.MODE_PRIVATE).getPath());
        webView.getSettings().setGeolocationEnabled(true);
        webView.requestFocus();
        webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(2);
        }
        // 不可复制
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    /**
     * Android 6.0以上版本，需求添加运行时权限申请；否则，可能程序崩溃
     */
    protected void initPermissionForCamera() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_PERMISSION_CAMERA);
        }
    }

    protected void initPermissionForWriteExternalStorage() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            getWriteExternalStorageAfterDoing();
        }
    }

    public void getWriteExternalStorageAfterDoing(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length == 0) {
                return;
            }
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWriteExternalStorageAfterDoing();
            } else {
                ToastUtils.showToastCenter(this, "无法获取读写入权限！");
            }
        } else if (REQUEST_CODE_PERMISSION_CAMERA == requestCode) {
            if (grantResults.length == 0) {
                return;
            }
            switch (grantResults[0]) {
                case PackageManager.PERMISSION_DENIED:
                    boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
                    if (isSecondRequest) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_CAMERA);
                    } else {
                        ToastUtils.showToastCenter(this, "拍照权限被禁用，请在权限管理修改！");
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
