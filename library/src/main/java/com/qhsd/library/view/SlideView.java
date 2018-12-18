package com.qhsd.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qhsd.library.R;

/**
 * @author Doris.
 * @date 2018/12/18.
 */

public class SlideView extends FrameLayout {

    private ViewGroup mContentView;
    private ViewGroup mHolderView;
    private int mHolderWidth = 120;
    private int mContentWidth;

    private int mLastX = 0;
    private int mLastY = 0;
    private static final int TAN = 2;
    private float mParallax;

    private OnSlideListener onSlideListener;

    /**
     * SlideView的三种状态：开始滑动，打开，关闭
     */
    public interface OnSlideListener {
        int SLIDE_STATUS_OFF = 0;
        int SLIDE_STATUS_START_SCROLL = 1;
        int SLIDE_STATUS_ON = 2;

        /**
         * 滑动状态改变
         * @param view   current SlideView
         * @param status SLIDE_STATUS_ON, SLIDE_STATUS_OFF or SLIDE_STATUS_START_SCROLL
         */
        void onSlide(View view, int status);
    }


    public SlideView(Context context) {
        this(context, null);
        View.inflate(context, R.layout.lib_view_slide, this);
        mHolderView = findViewById(R.id.lib_view_holder);
        mContentView = findViewById(R.id.lib_view_content);
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mParallax = 0.0f;
    }

    public void setContentView(View view) {
        mContentView.addView(view);
    }

    public void setParallax(float parallax) {
        if (parallax < 0 || parallax > 1) {
            throw new IllegalArgumentException("parallax is between 0 and 1");
        }
        mParallax = parallax;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View contentView = findViewById(R.id.lib_view_content);
        if (contentView == null || !(contentView instanceof ViewGroup)) {
            throw new IllegalArgumentException("please check id #content");
        }
        mContentView = (ViewGroup) contentView;

        View holderView = findViewById(R.id.lib_view_holder);
        if (holderView == null || !(holderView instanceof ViewGroup)) {
            throw new IllegalArgumentException("please check id #holder");
        }
        mHolderView = (ViewGroup) holderView;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mHolderView.setTranslationX(mContentView.getMeasuredWidth());
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        this.onSlideListener = onSlideListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHolderWidth = mHolderView.getMeasuredWidth();
                mContentWidth = mContentView.getMeasuredWidth();
                mLastX = x;
                mLastY = y;
                if (onSlideListener != null) {
                    onSlideListener.onSlide(this, OnSlideListener.SLIDE_STATUS_OFF);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                // 如果用户在做水平滑动，拦截事件
                if (Math.abs(deltaX) > Math.abs(deltaY) * TAN) {
                    abortAnimation();
                    if (onSlideListener != null) {
                        onSlideListener.onSlide(this, OnSlideListener.SLIDE_STATUS_START_SCROLL);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        float scrollX = (int) mContentView.getTranslationX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                float newScrollX = scrollX + deltaX;
                if (deltaX != 0) {
                    if (newScrollX > 0) {
                        newScrollX = 0;
                    } else if (newScrollX < -mHolderWidth) {
                        newScrollX = -mHolderWidth;
                    }
                    translationX(newScrollX, mParallax);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                getParent().requestDisallowInterceptTouchEvent(false);
                int newScrollX = 0;
                if (scrollX < -mHolderWidth * 0.75) {
                    newScrollX = -mHolderWidth;
                }
                this.smoothScrollTo(newScrollX);

                if (onSlideListener != null) {
                    onSlideListener.onSlide(this, newScrollX == 0 ? OnSlideListener.SLIDE_STATUS_OFF : OnSlideListener.SLIDE_STATUS_ON);
                }
                break;
            }
            default:
                break;
        }
        mLastX = x;
        mLastY = y;

        return super.onTouchEvent(event);
    }

    private void translationX(float translationX, float parallax) {
        float newTranslationX = translationX;
        if (parallax > 0) {
            newTranslationX = -mHolderWidth * parallax + translationX * (1 - parallax);
        }
        mHolderView.setTranslationX(mContentWidth + newTranslationX);
        mContentView.setTranslationX(translationX);
    }

    public void shrink() {
        if (mContentView.getTranslationX() != 0) {
            this.smoothScrollTo(0);
        }
    }

    private void smoothScrollTo(int destX) {
        // 缓慢滚动到指定位置
        int animDuration = 300;
        mHolderView.animate().translationX(mContentWidth + destX).setDuration(animDuration).start();
        mContentView.animate().translationX(destX).setDuration(animDuration).start();
    }

    private void abortAnimation() {
        mHolderView.animate().cancel();
        mContentView.animate().cancel();
    }
}
