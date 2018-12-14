package com.qhsd.library.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qhsd.library.R;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Doris.
 * @date 2018/12/14.
 */
public class TakePhotoPopupWindow extends BasePopupWindow implements View.OnClickListener{

    public TakePhotoPopupWindow(Context context) {
        super(context);
        View mContentView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.lib_popup_chose_picture, null);
        // 设置PopupWindow的View
        this.setContentView(mContentView);
        // 设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置不允许点击其他地方关闭popupWindow
        this.setOutsideTouchable(false);
        //设置允许触摸popupWindow
        this.setTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000333333);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // 设置PopupWindow弹出窗体动画效果
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.lib_popup_bottom_anim_style);

        mContentView.findViewById(R.id.lib_popup_take_photo).setOnClickListener(this);
        mContentView.findViewById(R.id.lib_popup_choose_form_storage).setOnClickListener(this);
        mContentView.findViewById(R.id.lib_popup_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lib_popup_take_photo){
            EventBus.getDefault().post("takePhoto");
        } else if (v.getId() == R.id.lib_popup_choose_form_storage){
            EventBus.getDefault().post("storage");
        } else if (v.getId() == R.id.lib_popup_cancel){
            dismiss();
        }
    }


}
