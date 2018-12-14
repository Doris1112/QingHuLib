package com.qhsd.library.requster;

import java.io.File;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public interface OkHttpDownloadBack {

    /**
     * 下载中
     *
     * @param progress 下载进度
     */
    void onDownloading(int progress);

    /**
     * 这是一个下载成功之后，返回文件路径的方法
     *
     * @param file     下载文件
     * @param fileUrl  下载路径
     * @param fileName 文件名
     */
    void onDownloadSuccess(File file, String fileUrl, String fileName);

    /**
     * 下载失败的回调方法
     *
     * @param fileUrl  下载路径
     * @param fileName 文件名
     */
    void onDownloadFailed(String fileUrl, String fileName);

}
