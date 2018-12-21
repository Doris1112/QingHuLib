package com.qhsd.library.entity.api;

/**
 * @author Doris.
 * @date 2018/12/21.
 */

public class Bulletin {

    private int Pid;
    private String Title;
    private String Summary;
    private String SummaryHighLineWords;
    private String SummaryHighLineWordsSplit;
    private String PLogo;
    private String CreateTime;

    public int getPid() {
        return Pid;
    }

    public void setPid(int pid) {
        Pid = pid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getSummaryHighLineWords() {
        return SummaryHighLineWords;
    }

    public void setSummaryHighLineWords(String summaryHighLineWords) {
        SummaryHighLineWords = summaryHighLineWords;
    }

    public String getSummaryHighLineWordsSplit() {
        return SummaryHighLineWordsSplit;
    }

    public void setSummaryHighLineWordsSplit(String summaryHighLineWordsSplit) {
        SummaryHighLineWordsSplit = summaryHighLineWordsSplit;
    }

    public String getPLogo() {
        return PLogo;
    }

    public void setPLogo(String PLogo) {
        this.PLogo = PLogo;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}
