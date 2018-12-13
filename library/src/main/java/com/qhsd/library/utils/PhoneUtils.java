package com.qhsd.library.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class PhoneUtils {

    /**
     * 复制文字
     *
     * @param context Context
     * @param textCopy 需要复制的文字
     */
    public static void copyText(Context context, CharSequence textCopy) {
        android.content.ClipboardManager c = (android.content.ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        c.setText(textCopy);
    }

    /**
     * 拨号
     * @param context Context
     * @param phone 电话号码
     */
    public static void callPhone(Context context, String phone) {
        Uri uri = Uri.parse("tel:" + phone);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 获取设备IMEI
     *
     * @return 手机唯一标识
     */
    public static String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                return telephonyManager.getDeviceId();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
