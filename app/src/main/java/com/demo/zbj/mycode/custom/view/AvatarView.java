package com.demo.zbj.mycode.custom.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.zbj.mycode.R;
import com.demo.zbj.mycode.util.DisplayUtils;

/**
 * 圆形头像裁剪合成view
 * Created by BiJim on 2018/10/23.
 */

public class AvatarView extends View {
    private static final float RADIUS = DisplayUtils.dp2px(80);
    private static final float PADDING = DisplayUtils.dp2px(5);

    private Paint paintSrc,paintDst;
    private RectF rectSrc,rectDst;
    private Xfermode xfermode;

    {
        rectSrc = new RectF();
        rectDst = new RectF();
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

        paintSrc = new Paint(Paint.ANTI_ALIAS_FLAG);

//        paintDst = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paintDst.setStyle(Paint.Style.FILL_AND_STROKE);
//        LinearGradient gradient = new LinearGradient(0.0f, 0.0f, getWidth()/2, getHeight()/2
//                , Color.parseColor("#FF7B11")
//                , Color.parseColor("#FF7B11")
//                , Shader.TileMode.CLAMP);
//        paintDst.setShader(gradient);

    }
    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectSrc.set(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS
                , getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
        rectDst.set(getWidth() / 2 - RADIUS - PADDING, getHeight() / 2 - RADIUS - PADDING
                , getWidth() / 2 + RADIUS + PADDING, getHeight() / 2 + RADIUS + PADDING);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        paintSrc.setStyle(Paint.Style.STROKE);
//        paintSrc.setStrokeWidth(DisplayUtils.dp2px(4));
//        canvas.drawOval(rectSrc, paintSrc);
//        paintSrc.setStyle(Paint.Style.FILL_AND_STROKE);
//        paintSrc.setXfermode(xfermode);
//        canvas.drawBitmap(getBitmap(), null, rectSrc, paintSrc);
//        paintSrc.setXfermode(null);
        canvas.drawOval(rectDst, paintSrc);
        int saved = canvas.saveLayer(rectDst, paintSrc);
        canvas.drawOval(rectSrc, paintSrc);
        paintSrc.setXfermode(xfermode);
        canvas.drawBitmap(getBitmap(), null, rectSrc, paintSrc);
        paintSrc.setXfermode(null);
        canvas.restoreToCount(saved);

    }

    public Bitmap getBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.westlake, options);
        options.inDensity = options.outWidth;
        options.inTargetDensity = (int) (2 * RADIUS);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(), R.drawable.westlake, options);
    }
}
