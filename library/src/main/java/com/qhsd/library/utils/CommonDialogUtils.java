package com.qhsd.library.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.qhsd.library.R;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class CommonDialogUtils {

    /**
     * 按钮点击回调
     */
    public interface CommonDialogCallback {

        void onClick();
    }

    /**
     * 显示弹窗
     *
     * @param activity   Activity
     * @param titleStr   标题
     * @param contentStr 内容
     * @param callback   回调
     */
    public static void showDialog(Activity activity, String titleStr, String contentStr, final CommonDialogCallback callback) {
        View view = getView(activity, titleStr, contentStr);
        final Dialog dialog = getDialog(activity, view);
        view.findViewById(R.id.lib_common_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.lib_common_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClick();
                dialog.dismiss();
            }
        });
    }

    private static View getView(Activity activity, String titleStr, String contentStr) {
        View view = LayoutInflater.from(activity).inflate(R.layout.lib_dialog_common, null);
        TextView title = view.findViewById(R.id.lib_common_title);
        TextView content = view.findViewById(R.id.lib_common_content);
        title.setText(titleStr);
        content.setText(contentStr);
        return view;
    }

    private static Dialog getDialog(Activity activity, View view) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        WindowManager m = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        m.getDefaultDisplay().getMetrics(outMetrics);
        //获取对话框当前的参数值
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        //宽度设置为屏幕的0.8
        p.width = (int) (outMetrics.widthPixels * 0.8);
        //设置生效
        dialog.getWindow().setAttributes(p);
        return dialog;
    }

}
