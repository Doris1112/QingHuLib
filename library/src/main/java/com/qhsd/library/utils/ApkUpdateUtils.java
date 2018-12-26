package com.qhsd.library.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class ApkUpdateUtils implements OkHttpDownloadBack {

    private Activity mActivity;
    private Dialog mDownloadDialog;
    private TextView mProgressValue;
    private ProgressBar mProgress;
    private boolean mIsBackstageDownload = false;
    private boolean mIsDownload = false;
    private boolean mIsUpdate = true;

    private static List<String> mDownloadUrls = new ArrayList<>();

    public ApkUpdateUtils(Activity activity, boolean isUpdate) {
        this(activity, isUpdate, false);
    }

    public ApkUpdateUtils(Activity activity, boolean isUpdate, boolean isBackstageDownload) {
        mActivity = activity;
        mIsUpdate = isUpdate;
        mIsBackstageDownload = isBackstageDownload;
    }

    /**
     * 更新提示
     *
     * @param downloadUrl  下载地址
     * @param contentValue 更新内容
     * @param fileName     文件名称
     * @param needUpdate   是否需要强制更新
     * @param httpManager  请求
     */
    public void showUpdateMessageDialog(final String downloadUrl, String contentValue, final String fileName,
                                        boolean needUpdate, final IOkHttpManager httpManager) {
        try {
            final Dialog dialog = new Dialog(mActivity);
            View view = LayoutInflater.from(mActivity).inflate(R.layout.lib_dialog_update_apk, null);
            dialog.setContentView(view);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCanceledOnTouchOutside(!needUpdate);
            dialog.setCancelable(!needUpdate);
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
                    dialog.dismiss();
                    if (!isDownloading(downloadUrl)){
                        if (httpManager != null){
                            mDownloadUrls.add(downloadUrl);
                            httpManager.downloadFile(downloadUrl, fileName, ApkUpdateUtils.this);
                            downloadDialog();
                        }
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示下载进度对话框
     *
     * @param downloadUrl 下载地址
     * @param fileName    文件名称
     * @param httpManager 请求
     */
    public void showDownloadProgressDialog(String downloadUrl, String fileName, IOkHttpManager httpManager) {
        if (!isDownloading(downloadUrl)){
            mDownloadUrls.add(downloadUrl);
            httpManager.downloadFile(downloadUrl, fileName, ApkUpdateUtils.this);
            downloadDialog();
        }
    }

    /**
     * 是否正在下载
     * @param url 下载地址
     * @return 是：true， 否：false
     */
    private boolean isDownloading(String url){
        if (mDownloadUrls.contains(url)){
            ToastUtils.showToastCenter(mActivity, "后台正在下载..");
            return true;
        }
        return false;
    }

    /**
     * 下载进度
     */
    private void downloadDialog() {
        mDownloadDialog = new Dialog(mActivity);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.lib_dialog_download_progress, null);
        mDownloadDialog.setContentView(view);
        mDownloadDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDownloadDialog.setCanceledOnTouchOutside(mIsBackstageDownload);
        mDownloadDialog.setCancelable(mIsBackstageDownload);
        mDownloadDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mIsDownload && mIsBackstageDownload) {
                    ToastUtils.showToastCenter(mActivity, "进入后台下载！");
                }
            }
        });
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
            mIsDownload = true;
            updateDownloadDialog(progress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadSuccess(File file, String fileUrl, String fileName) {
        try {
            mIsDownload = false;
            downloadDialogDismiss();
            mDownloadUrls.remove(fileUrl);
            ApkUtils.installAPK(file, mActivity);
            if (mIsUpdate) {
                AppManager.getAppManager().finishAllActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadFailed(String fileUrl, String fileName) {
        try {
            downloadDialogDismiss();
            mDownloadUrls.remove(fileUrl);
            ToastUtils.showToastCenter(mActivity, "下载失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
