package com.qhsd.library.view.signcalendar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.util.ArraySet;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qhsd.library.R;
import com.qhsd.library.view.CustomViewPager;

import java.util.Calendar;
import java.util.HashSet;

/**
 * @author Doris.
 * @date 2018/8/30.
 */
public class SignCalendarView extends FrameLayout {

    private ArraySet<SignCalendar> destroyViews = new ArraySet<>();
    private ArraySet<SignCalendar> viewSet = new ArraySet<>();
    private HashSet<String> signRecords;
    private int pagerSize = 1200;
    private CustomViewPager pager;
    private Config config;
    private int currentPosition;

    public SignCalendarView(Context context) {
        super(context);
        init(null, 0);
    }

    public SignCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SignCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int def) {
        config = new Config();
        //获取日历的UI配置
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SignCalendarView, def, 0);
        config.weekHeight = array.getDimension(R.styleable.SignCalendarView_weekHeight, dip2px(30));
        config.weekBackgroundColor = array.getColor(R.styleable.SignCalendarView_weekBackgroundColor, Color.TRANSPARENT);
        config.weekTextColor = array.getColor(R.styleable.SignCalendarView_weekTextColor, Color.GRAY);
        config.weekTextSize = array.getDimension(R.styleable.SignCalendarView_weekTextSize, sp2px(14));
        config.calendarTextColor = array.getColor(R.styleable.SignCalendarView_calendarTextColor, Color.BLACK);
        config.calendarTextSize = array.getDimension(R.styleable.SignCalendarView_calendarTextSize, sp2px(14));
        config.isShowLunar = array.getBoolean(R.styleable.SignCalendarView_isShowLunar, false);
        if (config.isShowLunar) {
            config.lunarTextColor = array.getColor(R.styleable.SignCalendarView_lunarTextColor, Color.LTGRAY);
            config.lunarTextSize = array.getDimension(R.styleable.SignCalendarView_lunarTextSize, sp2px(11));
        }
        config.todayTextColor = array.getColor(R.styleable.SignCalendarView_todayTextColor, Color.BLUE);
        config.todayColor = array.getColor(R.styleable.SignCalendarView_todayColor, Color.TRANSPARENT);
        config.signSize = array.getDimension(R.styleable.SignCalendarView_signSize, 0);
        config.signTextColor = array.getColor(R.styleable.SignCalendarView_signTextColor, Color.parseColor("#BA7436"));
        config.limitFutureMonth = array.getBoolean(R.styleable.SignCalendarView_limitFutureMonth, false);
        config.signColor = array.getColor(R.styleable.SignCalendarView_signColor, Color.BLUE);
        array.recycle();
        initPager();
    }

    private float sp2px(float spValue) {
        float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    private int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        height = hMode == MeasureSpec.EXACTLY ? height : dip2px(220);
        setMeasuredDimension(widthMeasureSpec, height);
    }

    private void initPager() {
        pager = new CustomViewPager(getContext());
        addView(pager);
        pager.setOffscreenPageLimit(1);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (listener == null) {
                    return;
                }
                position -= 1;
                int year = 1970 + position / 12;
                int month = position % 12;
                listener.change(year, month + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //选择当前日期
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        currentPosition = getPosition(year, month);
        if (config.limitFutureMonth) {
            pagerSize = currentPosition + 1;
        }
        pager.setAdapter(new CVAdapter());
        pager.setCurrentItem(currentPosition, false);
        post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.change(year, month + 1);
                }
            }
        });
    }

    private int getPosition(int year, int month) {
        return (year - 1970) * 12 + month + 1;
    }

    private class CVAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pagerSize;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SignCalendar calendarView = getContent(position);
            container.addView(calendarView);
            return calendarView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            destroyViews.add((SignCalendar) object);
            container.removeView((View) object);
        }
    }

    private SignCalendar getContent(int position) {
        SignCalendar calendarView;
        if (destroyViews.size() != 0) {
            calendarView = destroyViews.valueAt(0);
            destroyViews.remove(calendarView);
        } else {
            calendarView = new SignCalendar(getContext());
            calendarView.init(config);
            viewSet.add(calendarView);
        }
        calendarView.setSignRecords(signRecords);
        calendarView.selectDate(position);
        calendarView.setTag(position);
        return calendarView;
    }

    static class Config {
        float weekHeight;
        float weekTextSize;
        int weekBackgroundColor;
        int weekTextColor;
        float calendarTextSize;
        int calendarTextColor;
        boolean isShowLunar;
        int lunarTextColor;
        float lunarTextSize;
        int todayTextColor;
        int todayColor;
        int signColor;
        float signSize;
        int signTextColor;
        boolean limitFutureMonth;
    }

    public interface DateListener {
        void change(int year, int month);
    }

    private DateListener listener;

    public void setDateListener(DateListener listener) {
        this.listener = listener;
    }

    public void selectMonth(int year, int month) {
        if (pager == null || year < 1970 || month < 1 || month > 12) {
            return;
        }
        int position = getPosition(year, month);
        pager.setCurrentItem(position, false);
    }

    /**
     * 设置签到记录
     *
     * @param signRecords 为日期格式像 "2017-08-25"
     */
    public void setSignRecords(final HashSet<String> signRecords) {
        if (signRecords == null) {
            return;
        }
        this.signRecords = signRecords;
        for (SignCalendar view : viewSet) {
            view.setSignRecords(signRecords);
            view.invalidate();
        }
    }

    public void showNextMonth() {
        if (pager != null) {
            int index = pager.getCurrentItem() + 1;
            pager.setCurrentItem(index, true);
        }
    }

    public void showPreviousMonth() {
        if (pager != null) {
            int index = pager.getCurrentItem() - 1;
            pager.setCurrentItem(index, true);
        }
    }

    public void backCurrentMonth() {
        if (pager != null) {
            pager.setCurrentItem(currentPosition, false);
        }
    }

    public void setPagerScanScroll(boolean isScroll){
        pager.setScanScroll(isScroll);
    }
}