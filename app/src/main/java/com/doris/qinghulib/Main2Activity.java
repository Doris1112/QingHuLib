package com.doris.qinghulib;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qhsd.library.utils.ApkUpdateUtils;
import com.qhsd.library.utils.ToastUtils;

import okhttp3.OkHttpClient;

/**
 * @author Doris
 * @date 2018/12/14
 **/
public class Main2Activity extends AppCompatActivity {

    private ApkUpdateUtils mApkUpdateUtils1;
    private ApkUpdateUtils mApkUpdateUtils2;

    private String mUrl = "http://i2.houputech.com/fedapppackages/qhsd_v4.0.1_2018.12.1201_promotion.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },123);

        mApkUpdateUtils1 = new ApkUpdateUtils(this, true);
        mApkUpdateUtils2 = new ApkUpdateUtils(this, false, true);
    }

    public void onMain2ViewClick(View view){
        switch (view.getId()){
            case R.id.button1:
                mApkUpdateUtils1.showUpdateMessageDialog(mUrl, "测试", "test.apk",
                        true, OkHttpManager.getInstance(this));
                break;
            case R.id.button2:
                mApkUpdateUtils2.showDownloadProgressDialog(mUrl, "test1.apk",
                        OkHttpManager.getInstance(this));
                break;
            default:
                break;
        }
    }
}
