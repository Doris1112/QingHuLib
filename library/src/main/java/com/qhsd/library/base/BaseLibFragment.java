package com.qhsd.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qhsd.library.R;

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public abstract class BaseLibFragment extends Fragment {

    protected static final String TAG = "QingHuShiDai";

    protected Activity mContext;
    protected View mRoot;

    protected FrameLayout mTitleLayout;
    protected LinearLayout mLeftLayout, mRightLayout;
    protected ImageView mLeftIcon;
    protected TextView mTitleView, mRightText;
    protected View mTitleLine;

    /**
     * 标示是否第一次初始化数据
     */
    private boolean mIsFirstInitData = true;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            // 初始化当前的跟布局，但是不在创建时就添加到container里边
            View root = inflater.inflate(getLayoutResId(), container, false);
            mContext = getActivity();
            mRoot = root;
            initViewBefore();
            initBaseItemView();
            initView();
        } else {
            if (mRoot.getParent() != null) {
                // 把当前Root从其父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mContext == null){
            mContext = getActivity();
        }
        if (mIsFirstInitData){
            mIsFirstInitData = false;
            onFirstInit();
            initKotlinView();
        }
        initData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }

    protected void initArgs(Bundle bundle){

    }

    protected void initViewBefore() {

    }

    private void initBaseItemView(){
        mTitleLayout = mRoot.findViewById(R.id.lib_layout_title);
        mLeftLayout = mRoot.findViewById(R.id.lib_title_left);
        mLeftIcon = mRoot.findViewById(R.id.lib_title_left_icon);
        mTitleView = mRoot.findViewById(R.id.lib_title);
        mRightLayout = mRoot.findViewById(R.id.lib_title_right_layout);
        mRightText = mRoot.findViewById(R.id.lib_title_right_text);
        mTitleLine = mRoot.findViewById(R.id.lib_title_line);
    }

    protected void initView() {

    }

    protected void initData() {

    }

    /**
     * Kotlin 初始化控件在return view之后
     */
    protected void initKotlinView(){

    }

    /**
     * 当首次初始化数据的时候会调用的方法
     */
    protected void onFirstInit() {

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
     * 左边图片按钮
     *
     * @param resId 图片资源Id
     */
    protected final void setLeftIcon(int resId) {
        if (mLeftIcon != null) {
            mLeftIcon.setImageResource(resId);
        }
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
    protected final void setTitle(String title) {
        if (mTitleView != null) {
            mTitleView.setText(title);
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
     * 设置右边按钮点击事件
     *
     * @param onClickListener 点击事件
     */
    protected void setRightBtnOnClick(View.OnClickListener onClickListener) {
        if (mRightLayout != null){
            mRightLayout.setOnClickListener(onClickListener);
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
        Intent intent = new Intent(getContext(), clz);
        startActivity(intent);
    }

    protected final void startActivity(Class clz, Bundle bundle) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(getContext(), clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected final void startActivityForResult(Class clz, int requestCode) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(getContext(), clz);
        startActivityForResult(intent, requestCode);
    }

    protected final void startActivityForResult(Class clz, Bundle bundle, int requestCode) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(getContext(), clz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }
}
