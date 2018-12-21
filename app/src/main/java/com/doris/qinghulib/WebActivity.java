package com.doris.qinghulib;

import com.qhsd.library.base.web.BaseLibWebActivity;
import com.qhsd.library.utils.ScreenUtils;

/**
 * @author Doris
 * @date 2018/12/19
 **/
public class WebActivity extends BaseLibWebActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initViewBefore() {
        super.initViewBefore();
        ScreenUtils.setStatusBarColor(this, R.color.gray_c);
    }

    @Override
    protected void initView() {
        super.initView();
        setWebUrl("file:///android_asset/webpage/fileChooser.html");
    }

}
