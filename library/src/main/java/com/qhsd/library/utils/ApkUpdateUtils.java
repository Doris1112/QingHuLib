package com.qhsd.library.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qhsd.library.R;
import com.qhsd.library.helper.AppManager;
import com.qhsd.library.requster.IOkHttpManager;
import com.qhsd.library.requster.OkHttpDownloadBack;

import java.io.File;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class ApkUpdateUtils implements OkHttpDownloadBack {

    private Activity mActivity;
    private Dialog mDownloadDialog;
    private TextView mProgressValue;
    private ProgressBar mProgress;

    public ApkUpdateUtils(Activity activity) {
        mActivity = activity;
    }

    /**
     * 更新提示
     *
     * @param downloadUrl  下载地址
     * @param contentValue 更新内容
     * @param NeedUpdate   是否需要强制更新
     */
    public void updateDialog(final String downloadUrl, String contentValue, boolean NeedUpdate,
                             final IOkHttpManager httpManager) {
        try {
            final Dialog dialog = new Dialog(mActivity);
            View view = LayoutInflater.from(mActivity).inflate(R.layout.lib_dialog_update_apk, null);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCanceledOnTouchOutside(!NeedUpdate);
            dialog.setCancelable(!NeedUpdate);
            WindowManager m = mActivity.getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            m.getDefaultDisplay().getMetrics(outMetrics);
            //获取对话框当前的参数值
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
            //宽度设置为屏幕的0.8
            p.width = (int) (outMetrics.widthPixels * 0.8);
            //设置生效
            dialog.getWindow().setAttributes(p);
            TextView content = view.findViewById(R.id.lib_update_apk_content);
            content.setText(contentValue);
            view.findViewById(R.id.lib_update_apk_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    httpManager.downloadFile(downloadUrl, ApkUpdateUtils.this);
                    dialog.dismiss();
                    downloadDialog();
                }
            });
            dialog.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 下载进度
     */
    private void downloadDialog() {
        mDownloadDialog = new Dialog(mActivity);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.lib_dialog_download_progress, null);
        mDownloadDialog.setContentView(view);
        mDownloadDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDownloadDialog.setCanceledOnTouchOutside(false);
        mDownloadDialog.setCancelable(false);
        WindowManager m = mActivity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(outMetrics);
        //获取对话框当前的参数值
        WindowManager.LayoutParams p = mDownloadDialog.getWindow().getAttributes();
        //宽度设置为屏幕的0.8
        p.width = (int) (outMetrics.widthPixels * 0.8);
        //设置生效
        mDownloadDialog.getWindow().setAttributes(p);
        mProgressValue = view.findViewById(R.id.lib_download_progress_value);
        mProgress = view.findViewById(R.id.lib_download_progress);
        mDownloadDialog.show();
    }

    private void updateDownloadDialog(int progress) {
        if (mProgress != null) {
            mProgress.setProgress(progress);
        }
        if (mProgressValue != null) {
            mProgressValue.setText("当前进度：" + progress + "%");
        }
    }

    private void downloadDialogDismiss() {
        if (mDownloadDialog != null && mDownloadDialog.isShowing()) {
            mDownloadDialog.dismiss();
        }
    }

    @Override
    public void onDownloading(int progress) {
        try {
            updateDownloadDialog(progress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadSuccess(File file) {
        try {
            ApkUtils.installAPK(file, mActivity);
            AppManager.getAppManager().finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadFailed() {
        try {
            downloadDialogDismiss();
            ToastUtils.showToastCenter(mActivity, "下载失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
