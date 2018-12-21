package com.qhsd.library.entity.api;

/**
 * @author Doris.
 * @date 2018/12/21.
 */

public class SignInHistory {

    private String Date;
    private String Month;
    private String Day;
    private String DayOfWeek;
    private boolean IsSignIn;
    private boolean Tips;
    private String SignInDate;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        DayOfWeek = dayOfWeek;
    }

    public boolean isSignIn() {
        return IsSignIn;
    }

    public void setSignIn(boolean signIn) {
        IsSignIn = signIn;
    }

    public boolean isTips() {
        return Tips;
    }

    public void setTips(boolean tips) {
        Tips = tips;
    }

    public String getSignInDate() {
        return SignInDate;
    }

    public void setSignInDate(String signInDate) {
        SignInDate = signInDate;
    }
}
