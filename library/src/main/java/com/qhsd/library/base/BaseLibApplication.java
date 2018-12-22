package com.qhsd.library.base;

import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.qhsd.library.helper.AppCrashHandler;
import com.qhsd.library.helper.ScreenAdaptation;
import com.qhsd.library.utils.ScreenUtils;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;

/**
 * @author Doris.
 * @date 2018/12/20.
 */

public class BaseLibApplication extends MultiDexApplication {

    protected static final String TAG = "qhsd";

    public static boolean isInitX5EnvironmentSuccess;

    protected int mScreenWidth = 720;
    protected int mScreenHeight = 1280;

    @Override
    public void onCreate() {
        super.onCreate();
        // 捕获异常帮助类
        AppCrashHandler.getInstance().init(this);
        // 适配 需要传入ui设计给的大小,初始化
        if (ScreenUtils.getScreenWidth(this) < mScreenWidth) {
            new ScreenAdaptation(this, mScreenWidth, mScreenHeight).register();
        }
        initX5WebCore();
    }

    private void initX5WebCore(){
        if (!QbSdk.isTbsCoreInited()) {
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
            // 非wifi网络条件下是否允许下载内核，默认为false(针对用户没有安装微信/手Q/QQ空间[无内核]的情况下)
            QbSdk.setDownloadWithoutWifi(true);
        }
    }

    /**
     * 获取下载保存目录
     * @return 保存目录绝对路径
     */
    public static String getDownloadSavePath(){
        String downloadSavePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/qhsd/download/";
        File file = new File(downloadSavePath);
        if (!file.exists()) {
            if (file.mkdirs()){
                Log.d(TAG, "getDownloadSavePath: 创建下载保存目录成功！");
            }
        }
        return downloadSavePath;
    }
}
