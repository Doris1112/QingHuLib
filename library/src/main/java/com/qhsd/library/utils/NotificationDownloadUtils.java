package com.qhsd.library.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.qhsd.library.helper.thread.CustomThreadExecutor;
import com.qhsd.library.requster.IOkHttpManager;
import com.qhsd.library.requster.OkHttpDownloadBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public class NotificationDownloadUtils {

    private Context mContext;
    private int mIcon;
    private NotificationManager mNotificationManager;

    private static List<String> mDownloads = new ArrayList<>();
    private static int notificationId = 0;

    public NotificationDownloadUtils(Context context, int icon) {
        this.mContext = context;
        this.mIcon = icon;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void create(final String downloadUrl, final String fileName, final IOkHttpManager okHttpManager) {
        if (isDownloading(downloadUrl)) {
            return;
        }
        notificationId++;
        mDownloads.add(downloadUrl);
        ToastUtils.showToastCenter(mContext, "开始下载...");
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setContentText(fileName)
                .setSmallIcon(mIcon)
                .setTicker("下载通知")
                .setProgress(100, 0, false);
        mNotificationManager.notify(notificationId, builder.build());
        new CustomThreadExecutor(true).execute(new Runnable() {
            @Override
            public void run() {
                okHttpManager.downloadFile(downloadUrl, fileName, new OkHttpDownloadBack() {
                    @Override
                    public void onDownloading(int progress) {
                        if (progress % 10 == 0){
                            builder.setProgress(100, progress, false);
                            mNotificationManager.notify(notificationId, builder.build());
                        }
                    }

                    @Override
                    public void onDownloadSuccess(File file, String fileUrl, String fileName) {
                        mNotificationManager.cancel(notificationId);
                        mDownloads.remove(fileUrl);
                        ApkUtils.installAPK(file, mContext);
                    }

                    @Override
                    public void onDownloadFailed(String fileUrl, String fileName) {
                        mNotificationManager.cancel(notificationId);
                        mDownloads.remove(fileUrl);
                        ToastUtils.showToastCenter(mContext, fileName + "下载失败");
                    }
                });
            }
        });
    }

    /**
     * 是否正在下载
     *
     * @param url 下载地址
     * @return 是：true， 否：false
     */
    private boolean isDownloading(String url) {
        if (mDownloads.contains(url)) {
            ToastUtils.showToastCenter(mContext, "后台正在下载..");
            return true;
        }
        return false;
    }

}
