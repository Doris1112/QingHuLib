package com.qhsd.library.view.flake;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import java.util.HashMap;

/**
 * @author Doris
 * @date 2018/12/13
 **/
public class Flake {

    private static final int WIDTH_1080 = 1080;

    float x, y;
    float rotation;
    float speed;
    float rotationSpeed;
    int width, height;
    Bitmap bitmap;

    private static HashMap<Integer, Bitmap> bitmapMap = new HashMap<>();

    static Flake createFlake(float xRange, Bitmap originalBitmap,Context context) {
        bitmapMap.clear();
        Flake flake = new Flake();
        DisplayMetrics metrics = getDisplayMetrics(context);
        if (metrics.widthPixels >= WIDTH_1080) {
            flake.width = (int) (5 + (float) Math.random() * 80);
            float hwRatio = originalBitmap.getHeight() / originalBitmap.getWidth();
            flake.height = (int) (flake.width * hwRatio + 60);
        } else {
            flake.width = (int) (5 + (float) Math.random() * 50);
            float hwRatio = originalBitmap.getHeight() / originalBitmap.getWidth();
            flake.height = (int) (flake.width * hwRatio + 40);

        }
        flake.x = (float) Math.random() * (xRange - flake.width);
        flake.y = 0 - (flake.height + (float) Math.random() * flake.height);

        flake.speed = 50 + (float) Math.random() * 150;

        flake.rotation = (float) Math.random() * 180 - 90;
        flake.rotationSpeed = (float) Math.random() * 90 - 45;

        flake.bitmap = bitmapMap.get(flake.width);
        if (flake.bitmap == null) {
            flake.bitmap = Bitmap.createScaledBitmap(originalBitmap,
                    flake.width, flake.height, true);
            bitmapMap.put(flake.width, flake.bitmap);
        }
        return flake;
    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    private static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();
        } else {
            mResources = context.getResources();
        }
        return mResources.getDisplayMetrics();
    }

}
