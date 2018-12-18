package com.qhsd.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qhsd.library.R;
import com.qhsd.library.helper.AppManager;
import com.qhsd.library.utils.HideInputUtils;
import com.qhsd.library.utils.ProgressDialogUtils;

/**
 * @author Doris.
 * @date 2018/12/14.
 */
public abstract class BaseLibActivity extends AppCompatActivity {

    protected static final String TAG = "qhsd";

    protected LinearLayout mBackLayout, mRightLayout;
    protected TextView mTitleName, mRightText;

    /**
     * 防止连续点击跳转两个页面
     */
    protected long mLastClickTime;
    protected int mLastClickInterval = 900;

    /**
     * 设置布局文件
     *
     * @return 资源Layout的Id
     */
    protected abstract int getLayoutResId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSetContentViewBefore();
        setContentView(getLayoutResId());
        AppManager.getAppManager().addActivity(this);
        initViewBefore();
        initBaseItemView();
        initView();
        initData();
    }

    protected void initSetContentViewBefore() {

    }

    protected void initViewBefore() {

    }

    private void initBaseItemView(){
        mBackLayout = findViewById(R.id.item_title_back_layout);
        mTitleName = findViewById(R.id.item_title_name);
        mRightLayout = findViewById(R.id.item_title_right_layout);
        mRightText = findViewById(R.id.item_title_right_tv);

        if (mBackLayout != null){
            mBackLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    AppManager.getAppManager().finishActivity(BaseLibActivity.this);
                    finish();
                }
            });
        }
    }

    protected void initView() {

    }

    protected void initData() {

    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    protected void setTitle(String title){
        if (mTitleName != null){
            mTitleName.setText(title);
        }
    }

    /**
     * 设置左边按钮显示
     */
    protected void setLeftBtnVisible(){
        if (mBackLayout != null){
            mBackLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置右边文字
     * 默认隐藏
     *
     * @param text 右边文字
     */
    protected void setRightText(String text) {
        if (mRightText != null) {
            mRightLayout.setVisibility(View.VISIBLE);
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setText(text);
        }
    }

    /**
     * 设置右边文字点击事件
     *
     * @param onClickListener 点击事件
     */
    protected void setRightOnclick(View.OnClickListener onClickListener) {
        if (mRightLayout != null){
            mRightLayout.setVisibility(View.VISIBLE);
            mRightLayout.setOnClickListener(onClickListener);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (HideInputUtils.isShouldHideSoftKeyBoard(view, ev)) {
                HideInputUtils.hideSoftKeyBoard(view.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        ProgressDialogUtils.dismissDialog();
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 是否点击了两次
     *
     * @return 是：true，不是：false
     */
    protected boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - mLastClickTime < mLastClickInterval) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }

    protected void startActivity(Class clz) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

    protected void startActivity(Class clz, Bundle bundle) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startActivityForResult(Class clz, int requestCode) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class clz, Bundle bundle, int requestCode) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }
}
