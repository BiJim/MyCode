package com.demo.zbj.mycode.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * dp px转换等屏幕显示相关方法工具类
 * Created by BiJim on 2018/10/22.
 */

public class DisplayUtils {
    public static float dp2px(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float sp2px(float sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }
}
