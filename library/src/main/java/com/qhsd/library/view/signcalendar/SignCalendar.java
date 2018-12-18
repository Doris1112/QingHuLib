package com.qhsd.library.view.signcalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.qhsd.library.utils.ScreenUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;

/**
 * @author Doris.
 * @date 2018/8/30.
 */

public class SignCalendar extends View {

    private String[] weekTitles = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private Calendar calendar = Calendar.getInstance();
    /**
     * 今天所在的年月日信息
     */
    private int currentYear, currentMonth, currentDay;
    private GregorianCalendar date = new GregorianCalendar();
    private SignCalendarView.Config config;
    private HashSet<String> signRecords;
    private Paint paint = new Paint();
    private LunarHelper lunarHelper;
    private int itemWidth, itemHeight;
    private float solarTextHeight;
    private int currentPosition;

    public SignCalendar(Context context) {
        super(context);
    }

    public SignCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SignCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void init(SignCalendarView.Config config) {
        this.config = config;
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(ScreenUtils.sp2px(getContext(), 0.6f));
        currentPosition = (currentYear - 1970) * 12 + currentMonth + 1;
        setClickable(true);
        if (config.isShowLunar) {
            lunarHelper = new LunarHelper();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        itemWidth = getWidth() / 7;
        itemHeight = (getHeight() - (int) config.weekHeight) / 6;
        paint.setTextSize(config.calendarTextSize);
        solarTextHeight = getTextHeight();
        if (config.signSize == 0) {
            config.signSize = Math.min(itemHeight, itemWidth);
        }
    }

    final void selectDate(int position) {
        currentPosition = position - 1;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.LTGRAY);
        canvas.drawLine(0, config.weekHeight, 0, getHeight() - config.weekHeight, paint);
        // 画日历顶部周的标题
        paint.setColor(config.weekBackgroundColor);
        canvas.drawRect(0, 0, getWidth(), config.weekHeight, paint);
        paint.setTextSize(config.weekTextSize);
        paint.setColor(config.weekTextColor);
        float delay = getTextHeight() / 4;
        for (int i = 0; i < 7; i++) {
            canvas.drawText(weekTitles[i], itemWidth * (i + 0.5f), config.weekHeight / 2 + delay, paint);
        }
        // 画日历
        int year = 1970 + currentPosition / 12;
        int month = currentPosition % 12;
        calendar.set(year, month, 1);
        int firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int selectMonthMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        delay = solarTextHeight / 4;
        if (config.isShowLunar) {
            delay = 0;
        }
        for (int i = 1; i <= selectMonthMaxDay; i++) {
            int position = i - 1 + firstDay;
            int x = (position % 7) * itemWidth + itemWidth / 2;
            int y = (position / 7) * itemHeight + itemHeight / 2 + (int) config.weekHeight + (int) delay;
            //签到/今天背景
            boolean isSign = false, isToday = false;
            if (signRecords != null) {
                date.set(year, month, i);
                String dateStr = format.format(date.getTime());
                if (signRecords.contains(dateStr)) {
                    paint.setColor(config.signColor);
                    canvas.drawCircle(x, y, config.signSize / 2, paint);
                    isSign = true;
                }
            }
            if (year == currentYear && month == currentMonth && i == currentDay) {
                //今天
                isToday = true;
                if (isSign) {
                    paint.setColor(config.signColor);
                    canvas.drawCircle(x, y, config.signSize / 2, paint);
                } else {
                    paint.setColor(config.todayColor);
                    canvas.drawCircle(x, y, config.signSize / 2, paint);
                }
            }
            //农历
            if (config.isShowLunar) {
                if (isSign) {
                    paint.setColor(config.signTextColor);
                } else if (isToday) {
                    paint.setColor(config.todayTextColor);
                } else {
                    paint.setColor(config.lunarTextColor);
                }
                String lunar = lunarHelper.SolarToLunarString(year, month + 1, i);
                paint.setTextSize(config.lunarTextSize);
                canvas.drawText(lunar, x, y + solarTextHeight * 2 / 3, paint);
            }
            //阳历
            if (isSign) {
                paint.setColor(config.signTextColor);
            } else if (year == currentYear && month == currentMonth && i == currentDay) {
                //今天
                paint.setColor(config.todayTextColor);
            } else {
                //其他天
                paint.setColor(config.calendarTextColor);
            }
            paint.setTextSize(config.calendarTextSize);
            canvas.drawText(String.valueOf(i), x, y, paint);
        }
    }

    void setSignRecords(HashSet<String> signRecords) {
        this.signRecords = signRecords;
    }

    private float getTextHeight() {
        return paint.descent() - paint.ascent();
    }
}
