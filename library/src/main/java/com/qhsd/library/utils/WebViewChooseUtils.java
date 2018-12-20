package com.qhsd.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qhsd.library.base.BaseNumber;

import java.io.File;
import java.util.List;

/**
 * @author Doris.
 * @date 2018/12/20.
 */

public class WebViewChooseUtils {

    /**
     * 选择文件
     */
    public static void openFileChooseProcess(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        activity.startActivityForResult(Intent.createChooser(intent, "选择文件"),
                BaseNumber.REQUEST_CODE_PICKER_FILE);
    }

    /**
     * 选择图片
     */
    public static void openPictureChooseProcess(Activity activity, int maxSelectNum, boolean isCaptureEnabled) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(maxSelectNum)
                .isCamera(isCaptureEnabled)
                .forResult(BaseNumber.REQUEST_CODE_PICKER_PICTURE);
    }

    /**
     * 获取选择好的文件的 Uri 集合
     */
    public static Uri[] getPathUri5(Context context, boolean isPicture, Intent data){
        if (data == null){
            return null;
        }
        if (!isPicture){
            // 不是图片，是文件
            return new Uri[]{data.getData()};
        }
        // 是图片
        List<LocalMedia> mediaList = PictureSelector.obtainMultipleResult(data);
        if (mediaList != null && mediaList.size() > 0) {
            Uri[] uris = new Uri[mediaList.size()];
            for (int i = 0; i < uris.length; i++) {
                File file = new File(mediaList.get(i).getPath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uris[i] = FileProvider.getUriForFile(context.getApplicationContext(),
                            context.getPackageName() + ".provider", file);
                } else {
                    uris[i] = Uri.fromFile(file);
                }
            }
            return uris;
        }
        return null;
    }
}
