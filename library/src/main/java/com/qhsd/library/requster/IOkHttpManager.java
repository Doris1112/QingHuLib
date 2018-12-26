package com.qhsd.library.requster;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebSettings;

import com.google.gson.Gson;
import com.qhsd.library.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public abstract class IOkHttpManager {

    private static final String TAG = "QingHuShiDai";

    /**
     * api
     *
     * @return 基础地址
     */
    public abstract String getBaseUrl();

    /**
     * 下载保存目录
     *
     * @return 下载保存目录
     */
    public abstract String getDownloadSavePath();

    /**
     * mediaType 这个需要和服务端保持一致
     */
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    /**
     * 图片上传使用
     */
    private static final MediaType MEDIA_TYPE_FILE = MediaType.parse("image/jpg");
    /**
     * okHttpClient 实例
     */
    private OkHttpClient mOkHttpClient;
    /**
     * 全局处理子线程和M主线程通信
     */
    private Handler okHttpHandler;
    private Context mContext;

    /**
     * 初始化OkHttpManager
     */
    public IOkHttpManager(Context context) {
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                //设置超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                //设置读取超时时间
                .readTimeout(10, TimeUnit.SECONDS)
                //设置写入超时时间
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        //初始化Handler
        okHttpHandler = new Handler(context.getMainLooper());
        mContext = context;
    }

    public Context getContext(){
        return mContext;
    }

    /**
     * get异步请求
     *
     * @param actionUrl    接口地址
     * @param paramsMap    请求参数
     * @param callBack     请求返回数据回调
     * @param pastCallback 登录失效回调
     */
    public void get(final String actionUrl, Map<String, Object> paramsMap, final OkHttpCallBack callBack, final TokenPastCallback pastCallback) {
        try {
            final String requestUrl = String.format("%s%s?%s", getBaseUrl(), actionUrl, getParam(paramsMap));
            final Request request = addHeaders().url(requestUrl).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onFailedCallBack(e, callBack, actionUrl);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseStr = response.body().string();
                        ResponseMessage responseMessage = new Gson().fromJson(responseStr, ResponseMessage.class);
                        if (responseMessage.getStatus() == -99) {
                            onTokenPast(pastCallback);
                            return;
                        }
                        onSuccessCallBack(actionUrl, responseStr, callBack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     */
    public void get(final String actionUrl, Map<String, Object> paramsMap, final OkHttpCallBack callBack) {
        try {
            final String requestUrl = String.format("%s%s?%s", getBaseUrl(), actionUrl, getParam(paramsMap));
            final Request request = addHeaders().url(requestUrl).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onFailedCallBack(e, callBack, actionUrl);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseStr = response.body().string();
                        onSuccessCallBack(actionUrl, responseStr, callBack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get异步请求
     *
     * @param baseUrl   Api地址
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     */
    public void get(String baseUrl, final String actionUrl, Map<String, Object> paramsMap, final OkHttpCallBack callBack) {
        try {
            final String requestUrl = String.format("%s%s?%s", baseUrl, actionUrl, getParam(paramsMap));
            final Request request = addHeaders().url(requestUrl).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onFailedCallBack(e, callBack, actionUrl);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseStr = response.body().string();
                        onSuccessCallBack(actionUrl, responseStr, callBack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post异步请求
     *
     * @param actionUrl    接口地址
     * @param paramsMap    请求参数
     * @param callBack     请求返回数据回调
     * @param pastCallback 登录失效回调
     */
    public void post(final String actionUrl, Map<String, Object> paramsMap, final OkHttpCallBack callBack, final TokenPastCallback pastCallback) {
        try {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, getParam(paramsMap));
            final String requestUrl = String.format("%s%s", getBaseUrl(), actionUrl);
            final Request request = addHeaders().url(requestUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onFailedCallBack(e, callBack, actionUrl);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseStr = response.body().string();
                        ResponseMessage responseMessage = new Gson().fromJson(responseStr, ResponseMessage.class);
                        if (responseMessage.getStatus() == -99) {
                            onTokenPast(pastCallback);
                            return;
                        }
                        onSuccessCallBack(actionUrl, responseStr, callBack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     */
    public void post(final String actionUrl, Map<String, Object> paramsMap, final OkHttpCallBack callBack) {
        try {
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, getParam(paramsMap));
            final String requestUrl = String.format("%s%s", getBaseUrl(), actionUrl);
            final Request request = addHeaders().url(requestUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onFailedCallBack(e, callBack, actionUrl);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseStr = response.body().string();
                        onSuccessCallBack(actionUrl, responseStr, callBack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件 带参数
     *
     * @param actionUrl    请求地址
     * @param paramsMap    参数
     * @param fileMap      图片地址
     * @param callBack     回调
     * @param pastCallback 登录失效回调
     */
    public void postFiles(final String actionUrl, Map<String, Object> paramsMap, Map<String, File> fileMap, final OkHttpCallBack callBack, final TokenPastCallback pastCallback) {
        try {
            MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //入参-字符串
            for (Map.Entry entry : paramsMap.entrySet()) {
                requestBody.addFormDataPart(entry.getKey().toString(), entry.getValue().toString());
            }
            //入参-文件
            for (Map.Entry entry : fileMap.entrySet()) {
                File file = (File) entry.getValue();
                RequestBody fileBody = RequestBody.create(MEDIA_TYPE_FILE, file);
                String fileName = file.getName();
                requestBody.addFormDataPart(entry.getKey().toString(), fileName, fileBody);
            }
            Request request = addHeaders().url(actionUrl).post(requestBody.build()).build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onFailedCallBack(e, callBack, actionUrl);
                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseStr = response.body().string();
                        ResponseMessage responseMessage = new Gson().fromJson(responseStr, ResponseMessage.class);
                        if (responseMessage.getStatus() == -99) {
                            onTokenPast(pastCallback);
                            return;
                        }
                        onSuccessCallBack(actionUrl, responseStr, callBack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件 带参数
     *
     * @param actionUrl 请求地址
     * @param paramsMap 参数
     * @param fileMap   图片地址
     * @param callBack  回调
     */
    public void postFiles(final String actionUrl, Map<String, Object> paramsMap, Map<String, File> fileMap, final OkHttpCallBack callBack) {
        try {
            MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //入参-字符串
            for (Map.Entry entry : paramsMap.entrySet()) {
                requestBody.addFormDataPart(entry.getKey().toString(), entry.getValue().toString());
            }
            //入参-文件
            for (Map.Entry entry : fileMap.entrySet()) {
                File file = (File) entry.getValue();
                RequestBody fileBody = RequestBody.create(MEDIA_TYPE_FILE, file);
                String fileName = file.getName();
                requestBody.addFormDataPart(entry.getKey().toString(), fileName, fileBody);
            }
            Request request = addHeaders().url(actionUrl).post(requestBody.build()).build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onFailedCallBack(e, callBack, actionUrl);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseStr = response.body().string();
                        onSuccessCallBack(actionUrl, responseStr, callBack);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param fileUrl      下载地址
     * @param fileName     下载后保存的文件名字
     * @param downloadBack 回调
     */
    public void downloadFile(final String fileUrl, final String fileName, final OkHttpDownloadBack downloadBack) {
        Request request = new Request.Builder().url(fileUrl).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                onFailedDownloadBack(fileUrl, fileName, downloadBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() != 200) {
                    onFailedDownloadBack(fileUrl, fileName, downloadBack);
                    return;
                }
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = getDownloadSavePath();
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, fileName);
                    FileUtils.deleteFile(file);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        onSuccessDownloadBack(progress, downloadBack);
                    }
                    fos.flush();
                    // 下载完成
                    onSuccessDownloadBack(file, fileUrl, fileName, downloadBack);
                } catch (Exception e) {
                    onFailedDownloadBack(fileUrl, fileName, downloadBack);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 处理参数
     */
    private String getParam(Map<String, Object> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key).toString(), "utf-8")));
                pos++;
            }
        } catch (Exception e) {
            Log.d(TAG, "getRequestUrl: 对参数进行URLEncoder异常");
            e.printStackTrace();
        }
        return tempParams.toString();
    }

    /**
     * 添加消息头
     *
     * @return 返回一个builder
     */
    protected Request.Builder addHeaders() {
        return new Request.Builder().addHeader("Connection", "keep-alive")
                .addHeader("User-Agent", getUserAgent());
    }

    private String getUserAgent() {
        try {
            String userAgent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    userAgent = WebSettings.getDefaultUserAgent(mContext);
                } catch (Exception e) {
                    userAgent = System.getProperty("http.agent");
                }
            } else {
                userAgent = System.getProperty("http.agent");
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0, length = userAgent.length(); i < length; i++) {
                char c = userAgent.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    sb.append(String.format("\\u%04x", (int) c));
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "android";
    }

    /**
     * 成功的回调
     *
     * @param requestUrl 请求地址
     * @param response   返回消息
     * @param callBack   回调
     */
    private void onSuccessCallBack(final String requestUrl, final String response, final OkHttpCallBack callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (null != callBack) {
                    callBack.onSuccess(requestUrl, response);
                }
            }
        });
    }

    /**
     * 失败的回调
     *
     * @param e        错误
     * @param callBack 回调
     */
    private void onFailedCallBack(final IOException e, final OkHttpCallBack callBack, final String requestUrl) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (null != callBack) {
                    callBack.onFailed(requestUrl, e);
                }
            }
        });
    }

    /**
     * 下载进度的回调
     *
     * @param progress     下载进度
     * @param downloadBack 回调
     */
    private void onSuccessDownloadBack(final int progress, final OkHttpDownloadBack downloadBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (null != downloadBack) {
                    downloadBack.onDownloading(progress);
                }
            }
        });
    }

    /**
     * 下载进度的回调
     *
     * @param file         下载进度
     * @param downloadBack 回调
     */
    private void onSuccessDownloadBack(final File file, final String fileUrl, final String fileName, final OkHttpDownloadBack downloadBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (null != downloadBack) {
                    downloadBack.onDownloadSuccess(file, fileUrl, fileName);
                }
            }
        });
    }

    /**
     * 下载失败回调
     *
     * @param downloadBack 回调
     */
    private void onFailedDownloadBack(final String fileUrl, final String fileName, final OkHttpDownloadBack downloadBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (null != downloadBack) {
                    downloadBack.onDownloadFailed(fileUrl, fileName);
                }
            }
        });
    }

    private void onTokenPast(final TokenPastCallback pastCallback) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (null != pastCallback) {
                    pastCallback.tokenPastDue();
                }
            }
        });
    }
}
