package com.demo.zbj.mycode.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.zbj.mycode.util.DecimalUtil;
import com.demo.zbj.mycode.util.DisplayUtils;

import java.math.BigDecimal;


/**
 * 圆形带刻度表盘
 * Created by BiJim on 2018/10/22.
 */

public class BoardView extends View {
    //半径
    private static final int RADIUS = (int) DisplayUtils.dp2px(90);
    private static final int STROKE = (int) DisplayUtils.dp2px(2);
    private static final int CURSOR_LENGTH = (int) DisplayUtils.dp2px(70);
    //缺口角度
    private static final int BASE_ANGLE = 90;
    private Paint paint, paintRed;
    private RectF rect, rectDash;
    private PathMeasure pathMeasure;
    private Path path, pathDash, pathRed;
    private PathEffect pathEffect;

    {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#000000"));
        paint.setStrokeWidth(STROKE);

        paintRed = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintRed.setStyle(Paint.Style.FILL_AND_STROKE);
        paintRed.setColor(Color.parseColor("#FF0F2F"));
        paintRed.setStrokeWidth(STROKE);

        pathMeasure = new PathMeasure();


    }

    public BoardView(Context context) {
        super(context);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rect = new RectF(getWidth() / 2 - RADIUS,
                getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS,
                getHeight() / 2 + RADIUS);
        //设置圆盘path
        path = new Path();
        path.addArc(rect, 90 + BASE_ANGLE / 2, 360 - BASE_ANGLE);
        pathMeasure.setPath(path, false);

        //刻度轨迹path
        rectDash = new RectF(0, 0, DisplayUtils.dp2px(2), DisplayUtils.dp2px(4));
        pathDash = new Path();
        pathDash.addRect(rectDash, Path.Direction.CW);
        float length = pathMeasure.getLength();
        pathEffect = new PathDashPathEffect(pathDash, (length - DisplayUtils.dp2px(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);

        //中心指针path
        pathRed = new Path();
        pathRed.moveTo(getWidth() / 2, getHeight() / 2);
        //根据三角函数，指针长度为斜边，求出邻边=x轴偏移量，对边=y轴偏移量
        float x = (float) (CURSOR_LENGTH * Math.cos(transAngle2Radian(15)) + getWidth() / 2);
        float y = (float) (CURSOR_LENGTH * Math.sin(transAngle2Radian(15)) + getHeight() / 2);
        //画出指针
        pathRed.lineTo(x, y);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint);
        paint.setPathEffect(pathEffect);
        canvas.drawPath(path, paint);
        //需要清空路径效果
        paint.setPathEffect(null);
        canvas.drawPath(pathRed, paintRed);
    }

    /**
     * 角度转弧度，用于计算指针位置
     *
     * @param mark 刻度单位
     */
    private double transAngle2Radian(int mark) {
        //                     ------初始角度------  +            -------转过的刻度角度--------
        double cursorAngle = 90 + ((double)BASE_ANGLE / 2) + (360 - (double) BASE_ANGLE) / 20 * mark;
        System.out.println("cursorAngle = " + cursorAngle + "  ; int cursorAngle = " + (int) cursorAngle + "v2=" +(double)BASE_ANGLE/20);
        return Math.toRadians(cursorAngle);
    }
}
