package com.qhsd.library.helper;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.qhsd.library.utils.ApkUtils;
import com.qhsd.library.utils.ToastUtils;

import java.io.File;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class Downloader {

    /**
     * 下载器
     */
    private DownloadManager downloadManager;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 下载的ID
     */
    private long downloadId;

    public Downloader(Context context) {
        this.mContext = context;
    }

    /**
     * 下载apk
     *
     * @param url
     * @param name
     */
    public void downloadAPK(String url, String name) {
        try {
            File file =  Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            if (!file.exists()){
                file.mkdir();
            }
            file = new File(file.getAbsoluteFile(), name);
            if (file.exists()){
                ApkUtils.installAPK(file, mContext);
                return;
            }
            //创建下载任务
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            //移动网络情况下是否允许漫游
            request.setAllowedOverRoaming(false);
            //在通知栏中显示，默认就是显示的
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setTitle(name);
            request.setDescription("正在下载...");
            request.setVisibleInDownloadsUi(true);
            //设置下载的路径
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
            //获取DownloadManager
            downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
            downloadId = downloadManager.enqueue(request);
            //注册广播接收者，监听下载状态
            mContext.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 广播监听下载的各个状态
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    /**
     * 检查下载状态
     */
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    ApkUtils.installAPK(queryDownloadedApk(), mContext);
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    ToastUtils.showToastCenter(mContext, "下载失败");
                    break;
                default:
                    break;
            }
        }
    }

    private File queryDownloadedApk() {
        File targetApkFile = null;
        if (downloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloadManager.query(query);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!uriString.isEmpty()) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;
    }
}