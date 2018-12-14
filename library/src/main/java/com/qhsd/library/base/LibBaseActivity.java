package com.qhsd.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.qhsd.library.helper.AppManager;
import com.qhsd.library.utils.HideInputUtils;
import com.qhsd.library.utils.ProgressDialogUtil;

/**
 * @author Doris.
 * @date 2018/12/14.
 */
public abstract class LibBaseActivity extends AppCompatActivity {

    protected static final String TAG = "qhsd";

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
        initView();
        initData();
    }

    public void initSetContentViewBefore() {

    }

    public void initViewBefore() {

    }

    public void initView() {

    }

    public void initData() {

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
        ProgressDialogUtil.dismissDialog();
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
