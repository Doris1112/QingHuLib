package com.qhsd.library.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qhsd.library.R;


/**
 * @author Doris
 * @date 2018/12/13
 **/
public class ToastUtils {

    private static Handler uiHandler = new Handler(Looper.getMainLooper());
    private static final int TOAST_DEFAULT_TIME = 1500;

    /**
     * toast显示在下部
     *
     * @param context Context
     * @param text    提示字符串
     */
    public static void showToastBottom(final Context context, final String text) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, text, TOAST_DEFAULT_TIME);
            }
        });
    }

    /**
     * toast显示在下部
     *
     * @param context Context
     * @param resId   指定String资源Id
     */
    public static void showToastBottom(final Context context, final int resId) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, context.getResources().getString(resId), TOAST_DEFAULT_TIME);
            }
        });
    }


    /**
     * toast显示在中间
     *
     * @param context Context
     * @param text    提示字符串
     */
    public static void showToastCenter(final Context context, final String text) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, Gravity.CENTER, text, TOAST_DEFAULT_TIME);
            }
        });
    }

    /**
     * toast显示在中间
     *
     * @param context  Context
     * @param text     提示字符串
     * @param showTime 显示时间，单位毫秒
     */
    public static void showToastCenter(final Context context, final String text, final int showTime) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, Gravity.CENTER, text, showTime);
            }
        });
    }

    /**
     * 自定义Toast显示位置
     *
     * @param context Context
     * @param gravity 放的位置 eg: Gravity.BOTTOM
     * @param resId   指定String资源Id
     */
    public static void showToast(final Context context, final int gravity, final int resId) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, gravity, context.getResources().getString(resId), TOAST_DEFAULT_TIME);
            }
        });
    }

    /**
     * 自定义Toast显示位置
     *
     * @param context Context
     * @param gravity 放的位置 eg: Gravity.BOTTOM
     * @param text    提示字符串
     */
    public static void showToast(final Context context, final int gravity, final String text) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(context, gravity, text, TOAST_DEFAULT_TIME);
            }
        });
    }

    /**
     * 大Toast显示在中间
     *
     * @param context Context
     * @param text    提示字符串
     * @param flag    显示正确或错误图片 true正确  false错误
     */
    public static void showBigToastCenter(final Context context, final String text, final boolean flag) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                showBigToast(context, text, flag, TOAST_DEFAULT_TIME);
            }
        });
    }


    /**
     * 创建自定义toast并显示
     *
     * @param context Context
     * @param text    提示字符串
     * @param time    显示时间，单位毫秒
     */
    private static void showToast(final Context context, final String text, final int time) {
        if (context == null) {
            return;
        }
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.lib_toast, null);
        TextView textView = view.findViewById(R.id.lib_toast_content);
        textView.setText(text);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setDuration(time);
        toast.show();
    }

    /**
     * Toast显示
     *
     * @param context Context
     * @param gravity 放的位置 eg: Gravity.BOTTOM
     * @param text    提示字符串
     * @param time    显示时间，单位毫秒
     */
    public static void showToast(final Context context, final int gravity, final String text, final int time) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.lib_toast, null);
        TextView textView = view.findViewById(R.id.lib_toast_content);
        textView.setText(text);
        toast.setView(view);
        toast.setGravity(gravity, 0, 0);
        toast.setDuration(time);
        toast.show();
    }

    /**
     * 中间大提示
     *
     * @param context Context
     * @param text  提示字符串
     * @param flag    显示正确或错误图片 true正确  false错误
     * @param time 显示时间，单位毫秒
     */
    private static void showBigToast(final Context context, final String text, boolean flag, final int time) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.lib_toast_big, null);
        TextView textView = view.findViewById(R.id.lib_toast_content);
        ImageView imageView = view.findViewById(R.id.lib_toast_img);
        if (flag) {
            imageView.setImageResource(R.drawable.lib_ic_toast_success);
        } else {
            imageView.setImageResource(R.drawable.lib_ic_toast_fail);
        }
        textView.setText(text);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(time);
        toast.show();
    }

}
