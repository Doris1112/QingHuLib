package com.qhsd.library.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.qhsd.library.R;
import com.qhsd.library.view.LoadingView;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class ProgressDialogUtils {

    private static Dialog mDialog;

    public static void showDialog(final Context context, String content) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.lib_dialog_progress, null);
        if (!TextUtils.isEmpty(content)){
            TextView contentView = view.findViewById(R.id.lib_dialog_content);
            contentView.setText(content);
        }
        mDialog = new Dialog(context, R.style.lib_DefaultDialogStyle);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        Window window = mDialog.getWindow();
        if (window != null) {
            mDialog.show();
        }
    }

    public static void showDialog(final Context context) {
        showDialog(context, "");
    }

    public static void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
