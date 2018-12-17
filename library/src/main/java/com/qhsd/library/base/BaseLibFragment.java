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

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public abstract class BaseLibFragment extends Fragment {

    protected static final String TAG = "qhsd";

    protected Activity mContext;
    protected View mRoot;
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
