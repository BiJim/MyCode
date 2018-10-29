package com.demo.zbj.mycode.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.zbj.mycode.util.DisplayUtils;

/**
 * 自定义饼图
 * Created by BiJim on 2018/10/23.
 */

public class PieChatView extends View {
    private static final float RADIUS = DisplayUtils.dp2px(80);
    private static final float XLENGTH = DisplayUtils.dp2px(10);
    private String[] colors = {"#6BCAFF", "#FF72BE", "#FFA21A", "#56FF63"};
    private int[] angles = {60, 100, 80, 120};
    private int choiceIndex = 0;

    private Paint paint;
    private RectF rectF;

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public PieChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS
                , getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int currentAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            paint.setColor(Color.parseColor(colors[i]));
            if (i == choiceIndex) {
                canvas.save();
                double[] translateValue = getTranslateValue();
                canvas.translate((float) translateValue[0], (float)translateValue[1]);
            }
            canvas.drawArc(rectF, currentAngle, angles[i], true, paint);
            currentAngle += angles[i];
            if (i == choiceIndex) {
                canvas.restore();
            }
        }
    }

    private double[] getTranslateValue() {
        double[] values = new double[2];
        int middleAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            if (i == choiceIndex) {
                middleAngle += angles[i] / 2;
                break;
            } else {
                middleAngle += angles[i];
            }
        }

        double radians = Math.toRadians(middleAngle);
        values[0] = Math.cos(radians) * XLENGTH;
        values[1] = Math.sin(radians) * XLENGTH;
        return values;
    }
}
