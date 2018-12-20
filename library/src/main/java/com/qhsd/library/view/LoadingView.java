package com.qhsd.library.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.qhsd.library.R;

/**
 * @author Doris.
 * @date 2018/12/19.
 */

public class LoadingView extends View {

    private Paint mPaint;
    private float mWidth = 0f, mPadding = 0f, startAngle = 0f;
    private ValueAnimator mValueAnimator;
    private boolean isStartAnim;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 设置视图的大小即View本身的大小
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 得到宽
        if (getMeasuredWidth() > getHeight()) {
            mWidth = getMeasuredHeight();
        } else {
            mWidth = getMeasuredWidth();
        }
        mPadding = 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.default_color_alpha60));
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mPadding, mPaint);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.default_color));
        RectF rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        // 第四个参数是否显示半径
        canvas.drawArc(rectF, startAngle, 100, false, mPaint);

        startAnim();
    }

    /**
     * 开始转动动画
     */
    public void startAnim() {
        if (isStartAnim){
            return;
        }
        stopAnim();
        startViewAnim();
        isStartAnim = true;
    }

    /**
     * 停止转动动画
     */
    public void stopAnim() {
        if (mValueAnimator != null) {
            clearAnimation();
            mValueAnimator.setRepeatCount(1);
            mValueAnimator.cancel();
            mValueAnimator.end();
        }
        isStartAnim = false;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(8);
    }

    /**
     * 初始化动画
     */
    private void startViewAnim() {
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        // 无限循环
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (Float) valueAnimator.getAnimatedValue();
                startAngle = 360 * value;

                invalidate();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        if (!mValueAnimator.isRunning()) {
            mValueAnimator.start();
        }
    }
}
