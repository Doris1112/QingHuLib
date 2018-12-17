package com.doris.qinghulib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qhsd.library.utils.ProgressDialogUtils;
import com.qhsd.library.utils.ToastUtils;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMainViewClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                ToastUtils.showToastCenter(this, "123");
                break;
            case R.id.button2:
                ProgressDialogUtils.showDialog(this);
                break;
            case R.id.button3:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
