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

public class TypePopupWindow extends PopupWindow implements View.OnClickListener {

    private View mContentView;
    private CheckListener mListener;

    public TypePopupWindow(Context context, View parent, int type) {
        mContentView = View.inflate(context, R.layout.lib_popup_type, null);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.lib_popup_fade_anim_style);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(false);
        setContentView(mContentView);
        showAsDropDown(parent);
        initView(type);
        update();
    }

    private void initView(int type) {
        TextView hot = mContentView.findViewById(R.id.lib_popup_hot);
        TextView credit = mContentView.findViewById(R.id.lib_popup_credit);
        TextView large = mContentView.findViewById(R.id.lib_popup_large);
        TextView wage = mContentView.findViewById(R.id.lib_popup_wage);
        LinearLayout typeLl = mContentView.findViewById(R.id.lib_popup_type_ll);

        typeLl.setOnClickListener(this);
        hot.setOnClickListener(this);
        credit.setOnClickListener(this);
        large.setOnClickListener(this);
        wage.setOnClickListener(this);

        switch (type) {
            case BaseNumber.SIX:
                hot.setSelected(false);
                credit.setSelected(true);
                large.setSelected(false);
                wage.setSelected(false);
                break;
            case BaseNumber.EIGHT:
                hot.setSelected(false);
                credit.setSelected(false);
                large.setSelected(true);
                wage.setSelected(false);
                break;
            case BaseNumber.NINE:
                hot.setSelected(true);
                credit.setSelected(false);
                large.setSelected(false);
                wage.setSelected(false);
                break;
            default:
                hot.setSelected(false);
                credit.setSelected(false);
                large.setSelected(false);
                wage.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            if (v.getId() == R.id.lib_popup_hot){
                mListener.onCheck(BaseNumber.NINE);
            } else if (v.getId() == R.id.lib_popup_credit){
                mListener.onCheck(BaseNumber.SIX);
            } else if (v.getId() == R.id.lib_popup_large){
                mListener.onCheck(BaseNumber.EIGHT);
            } else if (v.getId() == R.id.lib_popup_wage){
                mListener.onCheck(BaseNumber.SEVEN);
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
         * @param type 类型
         */
        void onCheck(int type);
    }
}
