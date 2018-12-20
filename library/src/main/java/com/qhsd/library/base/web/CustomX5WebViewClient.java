package com.qhsd.library.base.web;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ProgressBar;

import com.qhsd.library.base.BaseString;
import com.qhsd.library.utils.ApkUtils;
import com.qhsd.library.utils.ToastUtils;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public class CustomX5WebViewClient extends WebViewClient {

    private Context mContext;
    private ProgressBar mProgressBar;

    public CustomX5WebViewClient(Context context) {
        this(context, null);
    }

    public CustomX5WebViewClient(Context context, ProgressBar progressBar) {
        mContext = context;
        mProgressBar = progressBar;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith(BaseString.START_HTTP) || url.startsWith(BaseString.START_HTTPS)) {
            view.loadUrl(url);
        }
        // 支付宝支付
        if (url.contains(BaseString.CONTENT_ALI_PAY)) {
            if (ApkUtils.checkAliPayInstalled(mContext)) {
                ApkUtils.startAliPayActivity(mContext, url);
            } else {
                ToastUtils.showToastCenter(mContext, "支付宝未安装，请先安装支付宝！");
            }
        }
        return true;
    }

    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
        sslErrorHandler.proceed();
    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
        if (mProgressBar != null){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        if (mProgressBar != null){
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
