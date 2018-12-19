package com.qhsd.library.entity.api;

/**
 * @author Doris.
 * @date 2018/12/19.
 */

public class CheckUpdate {

    private String DownloadUrl;
    private boolean NeedUpdate;
    private String Content;

    public String getDownloadUrl() {
        return DownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        DownloadUrl = downloadUrl;
    }

    public boolean isNeedUpdate() {
        return NeedUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        NeedUpdate = needUpdate;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
