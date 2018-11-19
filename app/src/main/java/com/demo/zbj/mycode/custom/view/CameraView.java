package com.demo.zbj.mycode.custom.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.zbj.mycode.R;
import com.demo.zbj.mycode.util.DisplayUtils;
import com.demo.zbj.mycode.util.Utils;

/**
 * graphic camera api demo
 * Created by BiJim on 2018/10/31.
 */

public class CameraView extends View {
    private static final float RADIUS = DisplayUtils.dp2px(100);
    private static final float DEGREE = 20;
    private int width,height;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera = new Camera();

    private ValueAnimator valueAnimator;
    private float animDegree=1;

    {
        camera.rotateX(-45);
//        camera.rotateY(45);
        //控制Z轴照射点远近， 默认-8，72个pixel为1个单位 ，值= -8 * 72
        camera.setLocation(0, 0, Utils.getZForCamera(-6));


        valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setStartDelay(2000);
        valueAnimator.setDuration(10000);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height = getHeight();
    }

    public void startAnimator() {
        valueAnimator.addUpdateListener(animation -> {
            animDegree = (float) animation.getAnimatedValue();
            System.out.println("animDegree"+animDegree);
            invalidate();
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //裁剪出canvas上某个范围内的内容用于绘制，如果绘制的内容在裁剪范围外就不显示
//        canvas.clipRect(rectF1);

        canvas.save();
        //5 按希望的方向平移
        canvas.translate(width/2,height/2);
        //4 按希望的方向旋转
        canvas.rotate(-animDegree);
        //使用camera z轴坐标系映射转换
        camera.applyToCanvas(canvas);
        //裁剪全部
        canvas.clipRect(-2 * RADIUS, -2 * RADIUS, 2 * RADIUS, 2 * RADIUS);
        //3 按希望的方向逆向旋转
        canvas.rotate(animDegree);
        //2 绘制内容中心平移回view坐标系原点（0,0）
        canvas.translate(-width/2,-height/2);
        //1 画
        canvas.drawBitmap(Utils.getBitmap(getContext(), RADIUS,R.drawable.westlake)
                , width/2-RADIUS, height/2-RADIUS, paint);
        canvas.restore();


       /* //上半部分裁剪，中心归零，以便计算时以View坐标系作准，不用考虑canvas坐标系，逆序思考
        //canvas每次变换坐标系参考原点都会跟着变 不利于计算
        canvas.save();
        //5 按希望的方向平移
        canvas.translate(width/2,height/2);
        //4 按希望的方向旋转
        canvas.rotate(-DEGREE);
        //裁剪x轴以上部分,旋转后rect需要能完全框住需要显示的图形，
        // 画一下坐标轴其实就是求x，y轴切过的三角斜边。大于斜边就行，此处直接2倍处理
        canvas.clipRect(-2 * RADIUS, -2 * RADIUS, 2 * RADIUS, 0);
        //3 按希望的方向逆向旋转
        canvas.rotate(DEGREE);
        //2 绘制内容中心平移回view坐标系原点（0,0）
        canvas.translate(-width/2,-height/2);
        //1 画
        canvas.drawBitmap(Utils.getBitmap(getContext(), RADIUS,R.drawable.westlake)
                , width/2-RADIUS, height/2-RADIUS, paint);
        canvas.restore();

        //下半部分裁剪
        canvas.save();
        //5 按希望的方向平移
        canvas.translate(width/2,height/2);
        //4 按希望的方向旋转
        canvas.rotate(-DEGREE);
        //使用camera z轴坐标系映射转换
        camera.applyToCanvas(canvas);
        //裁剪x轴以下部分
        canvas.clipRect(-2 * RADIUS, 0, 2 * RADIUS, 2 * RADIUS);
        //3 按希望的方向逆向旋转
        canvas.rotate(DEGREE);
        //2 绘制内容中心平移回view坐标系原点（0,0）
        canvas.translate(-width/2,-height/2);
        //1 画
        canvas.drawBitmap(Utils.getBitmap(getContext(), RADIUS,R.drawable.westlake)
                , width/2-RADIUS, height/2-RADIUS, paint);
        canvas.restore();*/



    }
}
