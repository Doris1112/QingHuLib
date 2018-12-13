package com.qhsd.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class ApkUtils {

    /**
     * 下载到本地后执行安装
     *
     * @param apkFile 需要安装的apk文件
     * @param context Context
     */
    public static void installAPK(File apkFile, Context context) {
        try {
            Intent intent = new Intent();
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context.getApplicationContext(), context.getPackageName() + ".provider", apkFile);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(apkFile);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查是否有安装支付宝
     *
     * @param context Context
     * @return 安装：true，没安装：false
     */
    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    /**
     * 调用支付宝支付
     *
     * @param context Context
     * @param url     支付链接
     * @return 调用成功：true，调用失败：false
     */
    public static boolean startAliPayActivity(Context context, String url) {
        try {
            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
