package com.demo.zbj.mycode.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 工具类
 * Created by BiJim on 2018/10/29.
 */

public class Utils {
    public static Bitmap getBitmap(Context context,float halfWidth, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        options.inDensity = options.outWidth;
        options.inTargetDensity = (int) (2 * halfWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    public static Bitmap getBitmap(Context context,int width,int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        options.inDensity = options.outWidth;
        options.inTargetDensity =  width;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    public static float getZForCamera(int num) {
        return num * Resources.getSystem().getDisplayMetrics().density;
    }
}
