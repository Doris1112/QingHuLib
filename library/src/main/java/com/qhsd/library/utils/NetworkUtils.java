package com.qhsd.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class NetworkUtils {

    public enum NetworkType {
        NET_TYPE_NONE,
        NET_TYPE_WIFI,
        NET_TYPE_CMWAP,
        NET_TYPE_CMNET
    }

    /**
     * 获取网络类型
     *
     * @param context Context
     * @return NetworkType
     */
    public static NetworkType getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetworkType.NET_TYPE_NONE;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!extraInfo.isEmpty()) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    return NetworkType.NET_TYPE_CMNET;
                } else {
                    return NetworkType.NET_TYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {

            return NetworkType.NET_TYPE_WIFI;
        }
        return NetworkType.NET_TYPE_NONE;
    }

    /**
     * 检测当前网络可用
     *
     * @param context Context
     * @return 可用：true，不可用：false
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

}
