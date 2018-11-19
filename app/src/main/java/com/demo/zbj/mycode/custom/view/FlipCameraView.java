package com.demo.zbj.mycode.custom.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.demo.zbj.mycode.R;
import com.demo.zbj.mycode.util.DisplayUtils;
import com.demo.zbj.mycode.util.Utils;

/**
 * graphic camera with animator
 * Created by BiJim on 2018/10/31.
 */

public class FlipCameraView extends View {
    private static final float RADIUS = DisplayUtils.dp2px(100);
    private static final float DEGREE = 20;
    private int width,height;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera = new Camera();

    //canvas 旋转角度
    private float canvasRotateDegree =1;

    //将图片裁剪为上下两部分，camera rotateX 旋转角度
    private float flipTop, flipBottom;

    public float getCanvasRotateDegree() {
        return canvasRotateDegree;
    }

    public void setCanvasRotateDegree(float canvasRotateDegree) {
        this.canvasRotateDegree = canvasRotateDegree;
        invalidate();
    }

    public float getFlipTop() {
        return flipTop;
    }

    public void setFlipTop(float flipTop) {
        this.flipTop = flipTop;
        invalidate();
    }

    public float getFlipBottom() {
        return flipBottom;
    }

    public void setFlipBottom(float flipBottom) {
        this.flipBottom = flipBottom;
        invalidate();
    }


    {
        //控制Z轴照射点远近， 默认-8，72个pixel为1个单位 ，值= -8 * 72
        camera.setLocation(0, 0, Utils.getZForCamera(-5));

    }

    public FlipCameraView(Context context, @Nullable AttributeSet attrs) {
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


        //上半部分裁剪，中心归零，以便计算时以View坐标系作准，不用考虑canvas坐标系，逆序思考
        //canvas每次变换坐标系参考原点都会跟着变 不利于计算
        canvas.save();
        //5 按希望的方向平移
        canvas.translate(width/2,height/2);
        //4 按希望的方向旋转
        canvas.rotate(-canvasRotateDegree);
        camera.save();
        camera.rotateX(flipTop);
        camera.applyToCanvas(canvas);
        camera.restore();
        //裁剪x轴以上部分,旋转后rect需要能完全框住需要显示的图形，
        // 画一下坐标轴其实就是求x，y轴切过的三角斜边。大于斜边就行，此处直接2倍处理
        canvas.clipRect(-2 * RADIUS, -2 * RADIUS, 2 * RADIUS, 0);
        //3 按希望的方向逆向旋转
        canvas.rotate(canvasRotateDegree);
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
        canvas.rotate(-canvasRotateDegree);
        //使用camera z轴坐标系映射转换
        camera.save();
        camera.rotateX(flipBottom);
        camera.applyToCanvas(canvas);
        camera.restore();
        //裁剪x轴以下部分
        canvas.clipRect(-2 * RADIUS, 0, 2 * RADIUS, 2 * RADIUS);
        //3 按希望的方向逆向旋转
        canvas.rotate(canvasRotateDegree);
        //2 绘制内容中心平移回view坐标系原点（0,0）
        canvas.translate(-width/2,-height/2);
        //1 画
        canvas.drawBitmap(Utils.getBitmap(getContext(), RADIUS,R.drawable.westlake)
                , width/2-RADIUS, height/2-RADIUS, paint);
        canvas.restore();



    }
}
