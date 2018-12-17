package com.qhsd.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qhsd.library.R;
import com.qhsd.library.helper.AppManager;

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public abstract class BaseLibFragment extends Fragment {

    protected static final String TAG = "qhsd";

    protected Activity mContext;
    protected View mRoot;

    protected LinearLayout mBackLayout, mRightLayout;
    protected TextView mTitleName, mRightText;

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
        mBackLayout = mRoot.findViewById(R.id.item_title_back_layout);
        mTitleName = mRoot.findViewById(R.id.item_title_name);
        mRightLayout = mRoot.findViewById(R.id.item_title_right_layout);
        mRightText = mRoot.findViewById(R.id.item_title_right_tv);
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
     * 设置右边文字
     * 默认隐藏
     *
     * @param text 右边文字
     */
    protected void setRightText(String text) {
        if (mRightText != null) {
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
            mRightLayout.setOnClickListener(onClickListener);
        }
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
        Intent intent = new Intent(getContext(), clz);
        startActivity(intent);
    }

    protected void startActivity(Class clz, Bundle bundle) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(getContext(), clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startActivityForResult(Class clz, int requestCode) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(getContext(), clz);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class clz, Bundle bundle, int requestCode) {
        if (isFastDoubleClick()) {
            return;
        }
        Intent intent = new Intent(getContext(), clz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }
}
