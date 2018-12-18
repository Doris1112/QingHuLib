package com.qhsd.library.utils;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * @author Doris.
 * @date 2018/12/18.
 */

public class ViewUtils {

    /**
     * 计算宽高
     *
     * @param view
     * @return
     */
    public static int[] calculationWH(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    /**
     * 部分字体变色
     *
     * @param text
     * @param color
     * @return
     */
    public static SpannableString getColorText(String text, final int color) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableString("");
        }
        SpannableString colorText = new SpannableString(text);
        colorText.setSpan(new ForegroundColorSpan(color), 0, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return colorText;
    }

    /**
     * 修改TabLayout下划线的宽度
     * @param tabs
     * @param leftDip 数值越大表示宽度越小
     * @param rightDip
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

}
