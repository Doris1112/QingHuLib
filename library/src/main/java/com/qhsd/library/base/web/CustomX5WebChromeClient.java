package com.qhsd.library.base.web;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;

import com.qhsd.library.base.BaseNumber;
import com.qhsd.library.base.BaseString;
import com.qhsd.library.utils.WebViewChooseUtils;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public class CustomX5WebChromeClient extends WebChromeClient {

    private Activity mContext;
    private ProgressBar mProgressBar;

    private ValueCallback<Uri[]> mFilePathCallback5;

    public CustomX5WebChromeClient(Activity context) {
        this(context, null);
    }

    public CustomX5WebChromeClient(Activity context, ProgressBar progressBar){
        mContext = context;
        mProgressBar = progressBar;
    }

    @Override
    public void onProgressChanged(WebView webView, int i) {
        super.onProgressChanged(webView, i);
        if (mProgressBar != null){
            mProgressBar.setProgress(i);
            if (i >= BaseNumber.ONE_HUNDRED){
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Android  >= 5.0
     */
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        mFilePathCallback5 = filePathCallback;
        if (fileChooserParams.getAcceptTypes().length > 0 &&
                BaseString.IMAGE_TYPE.equals(fileChooserParams.getAcceptTypes()[0])) {
            // 选择图片
            int maxSelectNum = fileChooserParams.getMode() == 0 ? 1 : 9;
            WebViewChooseUtils.openPictureChooseProcess(mContext, maxSelectNum,
                    fileChooserParams.isCaptureEnabled());
        } else {
            // 选择文件
            WebViewChooseUtils.openFileChooseProcess(mContext);
        }
        return true;
    }

    /**
     * 选择回调
     *
     * @return 是否以处理 true：已处理，false：未处理
     */
    public boolean onChooseResult(int requestCode, Intent data) {
        if (requestCode == BaseNumber.REQUEST_CODE_PICKER_FILE) {
            // 选择文件
            if (mFilePathCallback5 != null) {
                mFilePathCallback5.onReceiveValue(
                        WebViewChooseUtils.getPathUri5(mContext, false, data));
                mFilePathCallback5 = null;
            }
            return true;
        } else if (requestCode == BaseNumber.REQUEST_CODE_PICKER_PICTURE) {
            // 选择图片
            if (mFilePathCallback5 != null) {
                mFilePathCallback5.onReceiveValue(
                        WebViewChooseUtils.getPathUri5(mContext, true, data));
                mFilePathCallback5 = null;
            }
            return true;
        }
        return false;
    }

}
