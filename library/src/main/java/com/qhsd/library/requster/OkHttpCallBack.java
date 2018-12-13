package com.qhsd.library.requster;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public interface OkHttpCallBack {
    /**
     * 请求成功
     *
     * @param requestUrl 请求地址
     * @param resultStr  返回的信息
     */
    void onSuccess(String requestUrl, String resultStr);

    /**
     * 请求失败
     *
     * @param requestUrl 请求地址
     * @param e          异常
     */
    void onFailed(String requestUrl, Exception e);
}
