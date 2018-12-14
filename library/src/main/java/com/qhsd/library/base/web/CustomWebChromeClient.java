package com.qhsd.library.base.web;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.qhsd.library.popup.TakePhotoPopupWindow;
import com.qhsd.library.utils.ToastUtils;

import java.io.File;

/**
 * @author Doris.
 * @date 2018/12/14.
 */

public class CustomWebChromeClient extends WebChromeClient {

    /**
     * 选择文件
     */
    public static final int REQUEST_CODE_TAKE_PHOTO = 1001;
    public static final int REQUEST_FILE_PICKER = 1002;

    private Activity mContext;
    private WebView mWebView;

    private ValueCallback mFilePathCallback;
    private ValueCallback mFilePathCallback4;
    private boolean mChoose = false;
    private String mPhotoPath;

    public CustomWebChromeClient(Activity context, WebView webView){
        mContext = context;
        mWebView = webView;
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        mFilePathCallback = filePathCallback;
        TakePhotoPopupWindow popup = new TakePhotoPopupWindow(mContext);
        popup.showAtLocation(webView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!mChoose) {
                    mFilePathCallback.onReceiveValue(null);
                    mFilePathCallback = null;
                    mChoose = false;
                }
            }
        });
        return true;
    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback<Uri> filePathCallback) {
        mFilePathCallback4 = filePathCallback;
        TakePhotoPopupWindow popup = new TakePhotoPopupWindow(mContext);
        popup.showAtLocation(mWebView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!mChoose) {
                    mFilePathCallback4.onReceiveValue(null);
                    mFilePathCallback4 = null;
                    mChoose = false;
                }
            }
        });
    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback filePathCallback, String acceptType) {
        mFilePathCallback4 = filePathCallback;
        TakePhotoPopupWindow popup = new TakePhotoPopupWindow(mContext);
        popup.showAtLocation(mWebView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!mChoose) {
                    mFilePathCallback4.onReceiveValue(null);
                    mFilePathCallback4 = null;
                    mChoose = false;
                }
            }
        });
    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
        mFilePathCallback4 = filePathCallback;
        TakePhotoPopupWindow popup = new TakePhotoPopupWindow(mContext);
        popup.showAtLocation(mWebView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!mChoose) {
                    mFilePathCallback4.onReceiveValue(null);
                    mFilePathCallback4 = null;
                    mChoose = false;
                }
            }
        });
    }

    public boolean isChoose(){
        return mChoose;
    }

    public void setChoose(boolean choose){
        mChoose = choose;
    }

    /**
     * 调用系统相机
     */
    private void goToTakePhoto() {
        mChoose = true;
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            photoFile = new File(photoFile, System.currentTimeMillis() + ".jpg");
            mPhotoPath = photoFile.getAbsolutePath();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                // Android 7.0以上的方式
                Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", photoFile);
                mContext.grantUriPermission(mContext.getPackageName(), contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                // Android 7.0以下的方式
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            }
            mContext.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
        } catch (Exception e) {
            int flag = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
            if (PackageManager.PERMISSION_GRANTED != flag) {
                ToastUtils.showToastCenter(mContext, "拍照权限被禁用，请在权限管理修改！");
            }
        }
    }

    /**
     * 打开文件管理器
     */
    public void openFileManager() {
        mChoose = true;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        mContext.startActivityForResult(Intent.createChooser(intent, "选择文件"), REQUEST_FILE_PICKER);
    }

    public void pickPhotoResult(int resultCode, Intent data){
        if (mFilePathCallback != null) {
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = getPath(mContext, result);
                Uri uri = Uri.fromFile(new File(path));
                mFilePathCallback.onReceiveValue(new Uri[]{uri});
                mFilePathCallback = null;
                mPhotoPath = path;
            } else {
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
                mChoose = false;
            }
        } else if (mFilePathCallback4 != null) {
            /**
             * 针对API 19之前的版本
             */
            Uri result = data == null || resultCode !=Activity. RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = getPath(mContext, result);
                Uri uri = Uri.fromFile(new File(path));
                mFilePathCallback4.onReceiveValue(uri);
                mFilePathCallback4 = null;
            } else {
                mFilePathCallback4.onReceiveValue(null);
                mFilePathCallback4 = null;
                mChoose = false;
            }
        }
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    private String getPath(Context context, Uri uri) {
        boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                String id = DocumentsContract.getDocumentId(uri);
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = "_id=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private void takePhotoResult(int resultCode) {
        if (mFilePathCallback != null) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = Uri.fromFile(new File(mPhotoPath));
                mFilePathCallback.onReceiveValue(new Uri[]{uri});
                mFilePathCallback = null;
            } else {
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
                mChoose = false;
            }
        } else if (mFilePathCallback4 != null) {
            /**
             * 针对API 19之前的版本
             */
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = Uri.fromFile(new File(mPhotoPath));
                mFilePathCallback4.onReceiveValue(uri);
                mFilePathCallback4 = null;
            } else {
                mFilePathCallback4.onReceiveValue(null);
                mFilePathCallback4 = null;
                mChoose = false;
            }
        }
    }
}
