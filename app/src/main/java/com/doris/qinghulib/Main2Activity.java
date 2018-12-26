package com.doris.qinghulib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qhsd.library.config.BaseNumber;
import com.qhsd.library.helper.CountDownHelper;
import com.qhsd.library.popup.LimitPopupWindow;
import com.qhsd.library.popup.TypePopupWindow;
import com.qhsd.library.utils.DialogCommonUtils;
import com.qhsd.library.utils.ToastUtils;

/**
 * @author Doris
 * @date 2018/12/14
 **/
public class Main2Activity extends AppCompatActivity {

    private Button button1, button2;
    private int mMoneyStart = 0, mMoneyEnd = 0, mType = BaseNumber.NINE;
    private TextView text1;
    private CountDownHelper countDownHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        text1 = findViewById(R.id.text1);
    }

    public void onMain2ViewClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                LimitPopupWindow limitPopupWindow = new LimitPopupWindow(this,
                        button1, mMoneyStart, mMoneyEnd);
                limitPopupWindow.showAsDropDown(button1);
                limitPopupWindow.setListener(new LimitPopupWindow.CheckListener() {
                    @Override
                    public void onCheck(int moneyStart, int moneyEnd) {
                        mMoneyStart = moneyStart;
                        mMoneyEnd = moneyEnd;
                    }
                });
                break;
            case R.id.button2:
                TypePopupWindow typePopupWindow = new TypePopupWindow(this,
                        button2, mType);
                typePopupWindow.showAsDropDown(button2);
                typePopupWindow.setListener(new TypePopupWindow.CheckListener() {
                    @Override
                    public void onCheck(int type) {
                        mType = type;
                    }
                });
                break;
            case R.id.text1:
                if (countDownHelper == null) {
                    countDownHelper = new CountDownHelper(text1, "重新倒计时", 60, 1);
                }
                countDownHelper.start();
            case R.id.button3:
                DialogCommonUtils.showDialog(this,
                        "提示", "显示是否正确？",
                        new DialogCommonUtils.Callback() {
                            @Override
                            public void onClick() {
                                ToastUtils.showToastCenter(Main2Activity.this, "正确");
                            }
                        });
                break;
            case R.id.button4:
                startActivity(new Intent(this, Main3Activity.class));
                break;
            default:
                break;
        }
    }
}
