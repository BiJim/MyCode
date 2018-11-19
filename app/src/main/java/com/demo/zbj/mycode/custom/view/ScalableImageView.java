package com.demo.zbj.mycode.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.demo.zbj.mycode.R;
import com.demo.zbj.mycode.util.DisplayUtils;
import com.demo.zbj.mycode.util.Utils;

/**
 * 可缩放拖拽的image view
 * Created by BiJim on 2018/11/19.
 */

public class ScalableImageView extends View implements GestureDetector.OnGestureListener {
    private static final float IMAGE_WIDTH = DisplayUtils.dp2px(150);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap bitmap;
    private float offsetX, offsetY;
    private float smallScale, bigScale;
    GestureDetectorCompat detector = new GestureDetectorCompat(getContext(),this);

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getBitmap(context, IMAGE_WIDTH, R.drawable.westlake);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getWidth();
        int height = getHeight();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        //将图片绘制到中心点
        offsetX = (width - bitmapWidth) / 2f;
        offsetY = (height - bitmapHeight) / 2f;
        //缩放需要考虑图片宽高和屏幕宽高的大小关系，和宽高比
        //此例子暂时未考虑图片原始宽或高大于屏幕的情况
        if ((float)bitmapWidth / width  > (float)bitmapHeight / height) {
            smallScale = (float) width / bitmapWidth;
            bigScale = (float) height / bitmapHeight;
        } else {
            smallScale = (float) height / bitmapHeight;
            bigScale = (float) width / bitmapWidth;
        }
        System.out.println(width + "--" + height + "-,-" + bitmapWidth + "--" + bitmapHeight);
        System.out.println(smallScale + "--" + bigScale);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //支持多点触控
        int actionMasked = event.getActionMasked();
        int actionDown = MotionEvent.ACTION_DOWN;
        int actionMove = MotionEvent.ACTION_MOVE;
        int actionUp = MotionEvent.ACTION_UP;
        int actionCancel = MotionEvent.ACTION_CANCEL;
        int actionPointerDown = MotionEvent.ACTION_POINTER_DOWN;
        int actionPointerUp = MotionEvent.ACTION_POINTER_UP;

        //多点触控获取手指索引
        int actionIndex = event.getActionIndex();
        int pointerId = event.getPointerId(actionIndex);


        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scale = bigScale;
        canvas.scale(scale, scale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //按下，返回true
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //延迟按下显示
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //单击抬起
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //长按
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
