package com.qhsd.library.base;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * @author Doris.
 * @date 2018/12/20.
 */

public class BaseLibApplication extends MultiDexApplication {

    protected static final String TAG = "qhsd";
    public static boolean isInitX5EnvironmentSuccess;

    @Override
    public void onCreate() {
        super.onCreate();
        initX5WebCore();
    }

    private void initX5WebCore(){
        if (!QbSdk.isTbsCoreInited()) {
            // 允许使用流量下载
//            QbSdk.setDownloadWithoutWifi(true);
            QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {
                    Log.d(TAG, "onCoreInitFinished: ");
                }

                @Override
                public void onViewInitFinished(boolean b) {
                    BaseLibApplication.isInitX5EnvironmentSuccess = b;
                    Log.d(TAG, "onViewInitFinished: " + b);
                }
            });
        }
    }
}
