package com.doris.qinghulib;

import android.content.Context;
import android.os.Environment;

import com.qhsd.library.requster.IOkHttpManager;

import java.io.File;

/**
 * @author Doris.
 * @date 2018/9/5.
 */

public class OkHttpManager extends IOkHttpManager {

    private static final String TAG = OkHttpManager.class.getSimpleName();

    /**
     * 单利引用
     */
    private static volatile OkHttpManager mInstance;

    /**
     * 获取单例引用
     *
     * @return 实例
     */
    public static OkHttpManager getInstance(Context context) {
        OkHttpManager inst = mInstance;
        if (inst == null) {
            synchronized (OkHttpManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new OkHttpManager(context);
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 初始化OkHttpManager
     */
    private OkHttpManager(Context context) {
        super(context);
    }

    @Override
    public String getBaseUrl() {
        return "";
    }

    @Override
    public String getDownloadSavePath() {
        String apkSavePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Loan/download/";
        File file = new File(apkSavePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return apkSavePath;
    }

}
