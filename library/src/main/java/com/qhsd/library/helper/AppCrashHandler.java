package com.qhsd.library.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.qhsd.library.utils.LogUtils;

/**
 * @author Doris.
 * @date 2018/8/30.
 */

public class AppCrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static AppCrashHandler instance;
    private Context mContext;

    public static AppCrashHandler getInstance() {
        if (instance == null) {
            instance = new AppCrashHandler();
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
        if (!handleException(arg1) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(arg0, arg1);
        } else {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        saveCrashInfoToFile(ex, mContext);
        return true;
    }

    private void saveCrashInfoToFile(Throwable ex, Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                LogUtils.getInstance().writeLog("程序崩溃！版本号:　" + pi.versionName, ex);
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
