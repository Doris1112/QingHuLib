package com.qhsd.library.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qhsd.library.R;
import com.qhsd.library.config.BaseNumber;

/**
 * @author Doris.
 * @date 2018/12/26.
 */

public class DialogContactUtils {

    /**
     * 按钮点击回调
     */
    public interface DialogContactCallback {
        /**
         * 点击事件
         *
         * @param contentStr Content
         */
        void onClick(String contentStr);
    }

    /**
     * 显示对话框
     *
     * @param activity     Activity
     * @param iconRes      图片资源
     * @param titleStr     title
     * @param contentStr   content
     * @param operationStr 操作
     * @param callback     回调
     */
    public static void showDialog(Activity activity, int iconRes, String titleStr, final String contentStr, String operationStr,
                                  final DialogContactCallback callback) {
        View view = getView(activity, iconRes, 0, titleStr, contentStr, operationStr);
        final Dialog dialog = getDialog(activity, view);
        view.findViewById(R.id.lib_contact_operation1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onClick(contentStr);
                }
            }
        });
    }

    /**
     * 显示对话框
     *
     * @param activity      Activity
     * @param iconRes1      图片资源1
     * @param titleStr1     title1
     * @param contentStr1   content1
     * @param operationStr1 操作1
     * @param callback1     回调1
     * @param iconRes2      图片资源2
     * @param titleStr2     title2
     * @param contentStr2   content2
     * @param operationStr2 操作2
     * @param callback2     回调2
     */
    public static void showDialog(Activity activity, int iconRes1, String titleStr1, final String contentStr1, String operationStr1,
                                  final DialogContactCallback callback1,
                                  int iconRes2, String titleStr2, final String contentStr2, String operationStr2,
                                  final DialogContactCallback callback2) {
        View view = getView(activity, iconRes1, iconRes2, titleStr1, contentStr1, operationStr1,
                titleStr2, contentStr2, operationStr2);
        final Dialog dialog = getDialog(activity, view);
        view.findViewById(R.id.lib_contact_operation1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback1 != null) {
                    callback1.onClick(contentStr1);
                }
            }
        });
        view.findViewById(R.id.lib_contact_operation2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback2 != null) {
                    callback2.onClick(contentStr2);
                }
            }
        });
    }

    private static View getView(Activity activity, int iconRes1, int iconRes2, String... strings) {
        View view = LayoutInflater.from(activity).inflate(R.layout.lib_dialog_contact, null);
        ImageView icon1 = view.findViewById(R.id.lib_contact_icon1);
        TextView title1 = view.findViewById(R.id.lib_contact_title1);
        TextView content1 = view.findViewById(R.id.lib_contact_content1);
        TextView operation1 = view.findViewById(R.id.lib_contact_operation1);
        if (iconRes1 == 0) {
            icon1.setVisibility(View.GONE);
        } else {
            icon1.setVisibility(View.VISIBLE);
            icon1.setImageResource(iconRes1);
        }
        title1.setText(strings[0]);
        content1.setText(strings[1]);
        operation1.setText(strings[2]);
        View line = view.findViewById(R.id.lib_contact_line);
        LinearLayout layout2 = view.findViewById(R.id.lib_contact_layout2);
        if (strings.length == BaseNumber.THREE) {
            line.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
        } else if (strings.length == BaseNumber.SIX) {
            line.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            ImageView icon2 = view.findViewById(R.id.lib_contact_icon2);
            TextView title2 = view.findViewById(R.id.lib_contact_title2);
            TextView content2 = view.findViewById(R.id.lib_contact_content2);
            TextView operation2 = view.findViewById(R.id.lib_contact_operation2);
            if (iconRes2 == 0) {
                icon2.setVisibility(View.GONE);
            } else {
                icon2.setVisibility(View.VISIBLE);
                icon2.setImageResource(iconRes2);
            }
            title2.setText(strings[3]);
            content2.setText(strings[4]);
            operation2.setText(strings[5]);
        }
        return view;
    }

    private static Dialog getDialog(Activity activity, View view) {
        Dialog dialog = new Dialog(activity, R.style.lib_DefaultDialogStyle);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getAttributes().width = (int) (ScreenUtils.getScreenWidth(activity) * 0.9);
            dialog.show();
        }
        return dialog;
    }
}
