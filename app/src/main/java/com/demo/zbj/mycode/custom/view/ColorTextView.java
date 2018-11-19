package com.demo.zbj.mycode.custom.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.demo.zbj.mycode.R;
import com.demo.zbj.mycode.util.DisplayUtils;
import com.demo.zbj.mycode.util.Utils;

import java.util.Random;

/**
 * 带背景色的text view
 * Created by BiJim on 2018/11/7.
 */

public class ColorTextView extends AppCompatTextView {
    private static final int CORNER_RADIUS = (int) DisplayUtils.dp2px(4);
    private static final int X_PADDING = (int) DisplayUtils.dp2px(16);
    private static final int Y_PADDING = (int) DisplayUtils.dp2px(8);
    private static final Random random = new Random();
    private final int[] sizes = {8,10,13,16};
    private String[] colors;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        setTextColor(Color.WHITE);
        setTextSize(DisplayUtils.sp2px(sizes[random.nextInt(sizes.length)]));
        setPadding(X_PADDING,Y_PADDING,X_PADDING,Y_PADDING);
        colors = getResources().getStringArray(R.array.color_text_view_bg);
        paint.setColor(Color.parseColor(colors[random.nextInt(colors.length)]));
    }

    public ColorTextView(Context context) {
        this(context,null);
    }

    public ColorTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0,0,getWidth(),getHeight(),CORNER_RADIUS,CORNER_RADIUS,paint);
        super.onDraw(canvas);
    }
}
