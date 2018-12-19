package com.qhsd.library.helper;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author Doris.
 * @date 2018/12/19.
 */

public class CountDownHelper {

    private CountDownTimer mTimer;
    private TextView mCountDownView;
    private OnFinishListener mOnFinishListener;

    /**
     * @param countDownView 倒计时 TextView 控件
     * @param finishText    倒计时结束TextView控件显示文字
     * @param second        需要倒计时second秒（单位秒）
     * @param interval      倒计时时间间隔（单位秒）
     */
    public CountDownHelper(TextView countDownView, final String finishText, int second, int interval) {
        this.mCountDownView = countDownView;
        mTimer = new CountDownTimer(second * 1000, interval * 1000 - 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCountDownView.setText(String.format("重新发送(%ss)", (millisUntilFinished + 15) / 1000));
            }

            @Override
            public void onFinish() {
                mCountDownView.setEnabled(true);
                mCountDownView.setText(finishText);
                if (mOnFinishListener != null){
                    mOnFinishListener.onFinish();
                }
            }
        };
    }

    /**
     * 开始倒计时
     */
    public void start() {
        mCountDownView.setEnabled(false);
        mTimer.start();
    }

    /**
     * 停止倒计时
     */
    public void stop() {
        mCountDownView.setEnabled(true);
        mTimer.cancel();
    }

    /**
     * 设置倒计时结束事件
     * @param listener OnFinishListener
     */
    public void setOnFinishListener(OnFinishListener listener){
        mOnFinishListener = listener;
    }

    /**
     * 倒计时结束的回调接口
     */
    public interface OnFinishListener {
        /**
         * 结束事件
         */
        void onFinish();
    }
}
