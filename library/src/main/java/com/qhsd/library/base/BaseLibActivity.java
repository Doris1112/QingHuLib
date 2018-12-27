package com.qhsd.library.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

    protected static final String TAG = "QingHuShiDai";

    protected FrameLayout mTitleLayout;
    protected LinearLayout mLeftLayout, mRightLayout;
    protected ImageView mLeftIcon;
    protected TextView mTitleView, mRightText;
    protected View mTitleLine;

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

    private void initBaseItemView() {
        mTitleLayout = findViewById(R.id.lib_layout_title);
        mLeftLayout = findViewById(R.id.lib_title_left);
        mLeftIcon = findViewById(R.id.lib_title_left_icon);
        mTitleView = findViewById(R.id.lib_title);
        mRightLayout = findViewById(R.id.lib_title_right_layout);
        mRightText = findViewById(R.id.lib_title_right_text);
        mTitleLine = findViewById(R.id.lib_title_line);
    }

    protected void initView() {

    }

    protected void initData() {

    }

    /**
     * 设置标题栏背景图片
     *
     * @param resId 图片资源Id
     */
    protected final void setTitleLayoutBackgroundImage(int resId) {
        if (mTitleLayout != null) {
            mTitleLayout.setBackgroundResource(resId);
        }
    }

    /**
     * 设置标题栏背景颜色
     *
     * @param color 颜色值
     */
    protected final void setTitleLayoutBackgroundColor(int color) {
        if (mTitleLayout != null) {
            mTitleLayout.setBackgroundColor(color);
        }
    }

    /**
     * 设置左边按钮显示
     */
    protected final void setLeftBtnVisible() {
        if (mLeftLayout != null) {
            mLeftLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置左边按钮隐藏
     */
    protected final void setLeftBtnGone() {
        if (mLeftLayout != null) {
            mLeftLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 左边图片按钮（一般是返回按钮）
     *
     * @param resId 图片资源Id
     */
    protected final void setLeftIcon(int resId) {
        if (mLeftIcon != null) {
            mLeftIcon.setImageResource(resId);
        }
        setLeftBtnOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (manager != null) {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                AppManager.getAppManager().finishActivity(BaseLibActivity.this);
                finish();
            }
        });
    }

    /**
     * 设置左边按钮点击事件
     * @param listener 点击事件
     */
    protected final void setLeftBtnOnClick(View.OnClickListener listener) {
        if (mLeftLayout != null) {
            mLeftLayout.setOnClickListener(listener);
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    protected final void setBaseTitle(String title) {
        if (mTitleView != null) {
            mTitleView.setText(title);
        }
    }

    /**
     * 设置标题颜色
     * @param color 颜色
     */
    protected final void setBaseTitleColor(int color){
        if (mTitleView != null) {
            mTitleView.setTextColor(color);
        }
    }

    /**
     * 设置右边文字
     * 默认隐藏
     *
     * @param text 右边文字
     */
    protected final void setRightText(String text) {
        if (mRightLayout != null && mRightText != null) {
            mRightLayout.setVisibility(View.VISIBLE);
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setText(text);
            mRightText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    /**
     * 设置右边图片
     * @param drawable 图片
     */
    protected final void setRightIcon(Drawable drawable){
        if (mRightLayout != null && mRightText != null) {
            mRightLayout.setVisibility(View.VISIBLE);
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setText("");
            mRightText.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
    }

    /**
     * 设置右边文字点击事件
     *
     * @param onClickListener 点击事件
     */
    protected final void setRightBtnOnClick(View.OnClickListener onClickListener) {
        if (mRightLayout != null) {
            mRightLayout.setOnClickListener(onClickListener);
        }
    }

    /**
     * 隐藏右边按钮
     */
    protected final void setRightBtnGone(){
        if (mRightLayout != null){
            mRightLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题下边线条显示
     */
    protected final void setTitleLineVisible(){
        if (mTitleLine != null){
            mTitleLine.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置标题下边线条隐藏
     */
    protected final void setTitleLineGone(){
        if (mTitleLine != null){
            mTitleLine.setVisibility(View.GONE);
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
    protected final boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - mLastClickTime < mLastClickInterval) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }

    protected final void startActivity(Class clz) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

    protected final void startActivity(Class clz, Bundle bundle) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected final void startActivityForResult(Class clz, int requestCode) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        startActivityForResult(intent, requestCode);
    }

    protected final void startActivityForResult(Class clz, Bundle bundle, int requestCode) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }
}
