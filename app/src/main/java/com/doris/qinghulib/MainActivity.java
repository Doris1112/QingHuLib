package com.doris.qinghulib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.qhsd.library.popup.ChooseListPopupWindow;
import com.qhsd.library.utils.DialogContactUtils;
import com.qhsd.library.utils.ImageUtils;
import com.qhsd.library.utils.PhoneUtils;
import com.qhsd.library.utils.ProgressDialogUtils;
import com.qhsd.library.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_PATH = "http://i2.houputech.com/upload/bbd/adverts/82ab5dac0817473d98413893c75bd7dc.png";
    private ImageView imageView;
    private List<String> list = new ArrayList<>(8);
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.main_scroll);
        imageView = findViewById(R.id.image1);

        list.add("仅一次");
        list.add("每个月");
        list.add("每2个月");
        list.add("每3个月");
        list.add("每4个月");
        list.add("每5个月");
        list.add("每半年");
        list.add("每年");
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
            case R.id.button4:
                Intent intent1 = new Intent(this, WebActivity.class);
                startActivity(intent1);
                break;
            case R.id.button5:
                Glide.with(this).load(IMAGE_PATH).into(imageView);
                ImageUtils.getImageWidthWithHeight(this, IMAGE_PATH, new ImageUtils.GetImageWidthWithHeightCallback() {
                    @Override
                    public void onGet(int width, int height) {
                        Log.d("doris", "onGet: " + width + "_" + height);
                    }
                });
                break;
            case R.id.button6:
                DialogContactUtils.showDialog(this, R.mipmap.ic_launcher, "微信客服：",
                        "bbdaikuan", "复制",
                        new DialogContactUtils.Callback() {
                            @Override
                            public void onClick(String contentStr) {
                                PhoneUtils.copyText(MainActivity.this, contentStr);
                                ToastUtils.showToastCenter(MainActivity.this, "复制成功！");
                            }
                        });
                break;
            case R.id.button7:
                DialogContactUtils.showDialog(this, 0,
                        "微信客服：", "bbdaikuan", "复制",
                        new DialogContactUtils.Callback() {
                            @Override
                            public void onClick(String contentStr) {
                                PhoneUtils.copyText(MainActivity.this, contentStr);
                                ToastUtils.showToastCenter(MainActivity.this, "复制成功！");
                            }
                        }, 0,
                        "联系电话：", "1321906677", "呼叫",
                        new DialogContactUtils.Callback() {
                            @Override
                            public void onClick(String contentStr) {
                                PhoneUtils.callPhone(MainActivity.this, contentStr);
                            }
                        });
                break;
            case R.id.button8:
                ChooseListPopupWindow popupWindow = new ChooseListPopupWindow(this, list);
                popupWindow.setOnItemClickListener(new ChooseListPopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(String data, int position) {
                        Log.d("Doris", "onItemClick: " + data + "_" + position);
                    }
                });
                popupWindow.showAtLocation(scrollView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            default:
                break;
        }
    }
}
