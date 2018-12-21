package com.qhsd.library.popup;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qhsd.library.R;
import com.qhsd.library.config.BaseNumber;

/**
 * @author Doris.
 * @date 2018/12/17.
 */

public class LimitPopupWindow extends PopupWindow implements View.OnClickListener {

    private View mContentView;
    private CheckListener mListener;

    public LimitPopupWindow(Context context, View parent, int moneyStart, int moneyEnd) {
        mContentView = View.inflate(context, R.layout.lib_popup_limit, null);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.lib_popup_fade_anim_style);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(false);
        setContentView(mContentView);
        showAsDropDown(parent);
        initView(moneyStart, moneyEnd);
        update();
    }

    private void initView(int moneyStart, int moneyEnd) {
        TextView all = mContentView.findViewById(R.id.lib_popup_all);
        TextView two = mContentView.findViewById(R.id.lib_popup_two_thousand);
        TextView twoToTen = mContentView.findViewById(R.id.lib_popup_two_to_ten);
        TextView ten = mContentView.findViewById(R.id.lib_popup_ten_up);
        LinearLayout limitLl = mContentView.findViewById(R.id.lib_popup_limit_ll);
        limitLl.setOnClickListener(this);
        all.setOnClickListener(this);
        two.setOnClickListener(this);
        twoToTen.setOnClickListener(this);
        ten.setOnClickListener(this);

        if (moneyStart == BaseNumber.ZERO && moneyEnd == BaseNumber.ZERO) {
            all.setSelected(true);
            two.setSelected(false);
            twoToTen.setSelected(false);
            ten.setSelected(false);
        } else if (moneyStart == BaseNumber.ZERO && moneyEnd == BaseNumber.TWO_THOUSAND) {
            all.setSelected(false);
            two.setSelected(true);
            twoToTen.setSelected(false);
            ten.setSelected(false);
        } else if (moneyStart == BaseNumber.TWO_THOUSAND && moneyEnd == BaseNumber.TEN_THOUSAND) {
            all.setSelected(false);
            two.setSelected(false);
            twoToTen.setSelected(true);
            ten.setSelected(false);
        } else {
            all.setSelected(false);
            two.setSelected(false);
            twoToTen.setSelected(false);
            ten.setSelected(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            if (v.getId() == R.id.lib_popup_all) {
                mListener.onCheck(BaseNumber.ZERO, BaseNumber.ZERO);
            } else if (v.getId() == R.id.lib_popup_two_thousand) {
                mListener.onCheck(BaseNumber.ZERO, BaseNumber.TWO_THOUSAND);
            } else if (v.getId() == R.id.lib_popup_two_to_ten) {
                mListener.onCheck(BaseNumber.TWO_THOUSAND, BaseNumber.TEN_THOUSAND);
            } else if (v.getId() == R.id.lib_popup_ten_up) {
                mListener.onCheck(BaseNumber.TEN_THOUSAND, BaseNumber.TEN_THOUSAND);
            }
        }
        dismiss();
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    public void setListener(CheckListener listener) {
        this.mListener = listener;
    }

    public interface CheckListener {

        /**
         * 选择
         *
         * @param moneyStart 最低金额
         * @param moneyEnd 最高金额
         */
        void onCheck(int moneyStart, int moneyEnd);
    }
}
