package com.demo.zbj.mycode.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.demo.zbj.mycode.R;
import com.demo.zbj.mycode.util.DisplayUtils;
import com.demo.zbj.mycode.util.Utils;

/**
 * 遇图片自动缩进，自动换行显示长文本
 * Created by BiJim on 2018/10/29.
 */

public class ImageTextView extends View {
    private static final int BITMAP_SIZE = (int) DisplayUtils.dp2px(120);
    private static final int BITMAP_OFFSET_X = (int) DisplayUtils.dp2px(0);
    private static final int BITMAP_OFFSET_Y = (int) DisplayUtils.dp2px(80);
    private static final int TEXT_PRE_OFFSET = (int) DisplayUtils.dp2px(2);
    private static final int TEXT_SIZE = (int) DisplayUtils.sp2px(14);

    private Paint paint;
    private Paint.FontMetrics fontMetrics;
    private int width, height;
    private Bitmap bitmap;
    private String content;
    private float[] useWidth;

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(TEXT_SIZE);
        fontMetrics = new Paint.FontMetrics();
        paint.getFontMetrics(fontMetrics);
        bitmap = Utils.getBitmap(getContext(), BITMAP_SIZE, R.drawable.westlake);
        content = getResources().getString(R.string.west_lake_description);
        useWidth = new float[1];

    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, BITMAP_OFFSET_X, BITMAP_OFFSET_Y, paint);

        //文本长度
        int length = content.length();
        //文字行高
        int rawHeight = (int) (-fontMetrics.top + fontMetrics.bottom);
        //绘制字符起始索引
        int start = 0;
        int totalHeight=0;
//        System.out.println("text top=" + fontMetrics.top + "   text bottom=" + fontMetrics.bottom);

        int charCount;
        while (start < length) {
            totalHeight += rawHeight;
            if (totalHeight < BITMAP_OFFSET_Y || totalHeight > BITMAP_OFFSET_Y + BITMAP_SIZE) {
                //用于给长文本进行分段
                charCount = paint.breakText(content, start, length, true, width-TEXT_PRE_OFFSET, useWidth);
                canvas.drawText(content.substring(start, start + charCount), TEXT_PRE_OFFSET, totalHeight, paint);
            } else {
                charCount = paint.breakText(content, start, length, true, width - bitmap.getWidth()-TEXT_PRE_OFFSET, useWidth);
                canvas.drawText(content.substring(start, start + charCount), width-useWidth[0]+TEXT_PRE_OFFSET, totalHeight, paint);
            }
            start += charCount;
        }
    }
}
