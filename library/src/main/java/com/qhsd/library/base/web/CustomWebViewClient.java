package com.qhsd.library.base.web;

import android.content.Context;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qhsd.library.utils.ApkUtils;
import com.qhsd.library.utils.ToastUtils;

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public class CustomWebViewClient extends WebViewClient {

    private static final String START_HTTP = "http:";
    private static final String START_HTTPS = "https:";
    private static final String CONTENT_ALI_PAY = "alipays://platformapi";

    private Context mContext;

    public CustomWebViewClient(Context context){
        mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith(START_HTTP) || url.startsWith(START_HTTPS)) {
            view.loadUrl(url);
        }
        // 支付宝支付
        if (url.contains(CONTENT_ALI_PAY)){
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

}
