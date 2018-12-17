package com.doris.qinghulib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qhsd.library.base.BaseNumber;
import com.qhsd.library.popup.LimitPopupWindow;
import com.qhsd.library.popup.TypePopupWindow;

/**
 * @author Doris
 * @date 2018/12/14
 **/
public class Main2Activity extends AppCompatActivity {

    private Button button1, button2;
    private int mMoneyStart = 0, mMoneyEnd = 0, mType = BaseNumber.NINE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
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
            case R.id.button3:
                break;
            case R.id.button4:
                break;
            default:
                break;
        }
    }
}
