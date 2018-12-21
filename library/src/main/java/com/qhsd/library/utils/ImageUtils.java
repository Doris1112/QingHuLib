package com.qhsd.library.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.FileOutputStream;

/**
 * @author Doris.
 * @date 2018/12/18.
 */

public class ImageUtils {

    /**
     * 保存图片
     */
    public static boolean saveImg(Bitmap bitmap, String path, Bitmap.CompressFormat format, int quality) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            bitmap.compress(format, quality, fileOutputStream);
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将字符串转换成Bitmap类型
     */
    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            if (string.startsWith("data:")) {
                string = string.split(",")[1];
            }
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 同步媒体库
     */
    public static void updateFileFromDatabase(Context context, String filePath) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String[] paths = new String[]{Environment.getExternalStorageDirectory().toString()};
                MediaScannerConnection.scanFile(context, paths, null, null);
                MediaScannerConnection.scanFile(context, new String[]{filePath},
                        null, new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                            }
                        });
            } else {
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片的宽高
     */
    public static void getImageWidthWithHeight(Context context, String url, final GetImageWidthWithHeightCallback callback){
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (callback != null){
                    callback.onGet(resource.getWidth(), resource.getHeight());
                }
            }
        });
    }

    public interface GetImageWidthWithHeightCallback{
        void onGet(int width, int height);
    }
}
