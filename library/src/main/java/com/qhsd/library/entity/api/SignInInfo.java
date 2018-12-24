package com.qhsd.library.entity.api;

import java.util.List;

/**
 * @author Doris.
 * @date 2018/12/21.
 */

public class SignInInfo {

    private double PendingAmount;
    private String Summary;
    private String CurrentMonth;
    private List<SignInHistory> SignIns;
    private List<String> Rules;

    public double getPendingAmount() {
        return PendingAmount;
    }

    public void setPendingAmount(double pendingAmount) {
        PendingAmount = pendingAmount;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getCurrentMonth() {
        return CurrentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        CurrentMonth = currentMonth;
    }

    public List<SignInHistory> getSignIns() {
        return SignIns;
    }

    public void setSignIns(List<SignInHistory> signIns) {
        SignIns = signIns;
    }

    public List<String> getRules() {
        return Rules;
    }

    public void setRules(List<String> rules) {
        Rules = rules;
    }
}
