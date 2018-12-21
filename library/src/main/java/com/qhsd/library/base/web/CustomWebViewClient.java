package com.qhsd.library.base.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.qhsd.library.config.BaseString;
import com.qhsd.library.utils.ApkUtils;
import com.qhsd.library.utils.ToastUtils;

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public class CustomWebViewClient extends WebViewClient {

    private Context mContext;
    private ProgressBar mProgressBar;

    public CustomWebViewClient(Context context){
        mContext = context;
    }

    public CustomWebViewClient(Context context, ProgressBar progressBar) {
        mContext = context;
        mProgressBar = progressBar;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith(BaseString.START_HTTP) || url.startsWith(BaseString.START_HTTPS)) {
            view.loadUrl(url);
        }
        // 支付宝支付
        if (url.contains(BaseString.CONTENT_ALI_PAY)){
            if (ApkUtils.checkAliPayInstalled(mContext)){
                ApkUtils.startAliPayActivity(mContext, url);
            } else {
                ToastUtils.showToastCenter(mContext, "支付宝未安装，请先安装支付宝！");
            }
        }
        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        handler.proceed();
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mProgressBar != null){
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mProgressBar != null){
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
