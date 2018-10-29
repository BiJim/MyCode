package com.demo.zbj.mycode.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.zbj.mycode.util.DisplayUtils;

/**
 * 圆环+中心文字绘制 view
 * Created by BiJim on 2018/10/29.
 */

public class CircleSportView extends View {
    private static final int RADIUS = (int) DisplayUtils.dp2px(90);
    private static final int STROKE = (int) DisplayUtils.dp2px(10);
    private static final int SIZE = (int) DisplayUtils.sp2px(30);
    private String textContent = "apgibk";

    private Paint paint;
    private RectF rectF;
    private Rect rect;
    private Paint.FontMetrics fontMetrics;

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontMetrics = new Paint.FontMetrics();
        rect = new Rect();

    }
    public CircleSportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(getWidth() / 2 - RADIUS,
                getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS,
                getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE);
        paint.setColor(Color.parseColor("#A09EA2"));
        canvas.drawOval(rectF,paint);
        canvas.save();
        canvas.rotate(-90f,getWidth()/2,getHeight()/2);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.parseColor("#FFA21A"));
        canvas.drawArc(rectF,0,270,false,paint);
        canvas.restore();
        paint.reset();
        paint.setColor(Color.parseColor("#FA6DFF"));
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(SIZE);

        //测量方式1：文字矩阵大小会根据文字内容调整，变更内容位置也会发生微小变化，
//        paint.getTextBounds(textContent,0,textContent.length(),rect);
//        float textOffset=rect.height()/2;


        //测量方式2：
        // fontMetrics 中保存的ascent表示基于文字baseline以上部分绘制最大高度，负值；
        // fontMetrics 中保存的decent表示基于文字baseline以下部分绘制最大高度，正值；
        paint.getFontMetrics(fontMetrics);
        //baseline 之上都是负值，ascent=-m  ；decent=+n, 求中心点
        System.out.println("ascent"+fontMetrics.ascent+ "   descent"+fontMetrics.descent);
        float textOffset=(fontMetrics.descent+fontMetrics.ascent)/2;
        canvas.drawText(textContent,getWidth()/2,getHeight()/2-textOffset,paint);
    }
}
