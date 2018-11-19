package com.demo.zbj.mycode.custom.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.demo.zbj.mycode.R;
import com.demo.zbj.mycode.util.DisplayUtils;

/**
 * custom material EditText
 * Created by BiJim on 2018/11/2.
 */

public class MaterialEditText extends AppCompatEditText{
    private static final float TEXT_HINT_SIZE = DisplayUtils.sp2px(12);
    private static final float HINT_MARGIN_TOP = DisplayUtils.dp2px(4);
    private static final float HINT_MARGIN_BASE = DisplayUtils.dp2px(18);
//    private static final float HINT_MARGIN_BOTTOM = DisplayUtils.dp2px(3);
    private static final float HINT_MARGIN_LEFT = DisplayUtils.dp2px(5);
    private Paint paint;
    private Rect backgroundRect;
    private Paint.FontMetrics fontMetrics;
    private ObjectAnimator animator;
    private boolean beforeChange,afterChange;
    private boolean useFloatLabel;

    private float percent;

    public float getPercent() {
        return percent;
    }


    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }

    public void setFloatLabel(boolean floatLabel) {
        if (useFloatLabel != floatLabel) {
            useFloatLabel = floatLabel;
            judgeFloatLabel();
            invalidate();
        }
    }

    public boolean getFloatLabel() {
        return useFloatLabel;
    }

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        useFloatLabel = typedArray.getBoolean(R.styleable.MaterialEditText_useFloatLabel, true);
        judgeFloatLabel();
        init();

    }

    private void judgeFloatLabel() {
        backgroundRect = new Rect();
        getBackground().getPadding(backgroundRect);
        if (useFloatLabel) {
            setPadding(backgroundRect.left, (int) (backgroundRect.top+TEXT_HINT_SIZE+HINT_MARGIN_TOP)
                    ,backgroundRect.right,backgroundRect.bottom);
            initAnimator();
        } else {
            if (animator != null) {
                animator = null;
                percent = 0;
            }
            setPadding(backgroundRect.left, backgroundRect.top
                    ,backgroundRect.right,backgroundRect.bottom);
        }
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(TEXT_HINT_SIZE);
        paint.setColor(Color.parseColor("#5A595A"));

        fontMetrics = new Paint.FontMetrics();

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeChange = TextUtils.isEmpty(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                afterChange = TextUtils.isEmpty(s);
                if (animator != null) {
                    if (afterChange && !beforeChange) {//有-无
                        animator.reverse();
                    } else if (beforeChange && !afterChange) {//无-有
                        animator.start();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initAnimator() {
        animator = ObjectAnimator.ofFloat(this, "percent", 0, 1);
        animator.setDuration(150);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setAlpha((int) (0xff*percent));
        float extraOffset = (1 - percent) * (HINT_MARGIN_TOP + TEXT_HINT_SIZE);
        canvas.drawText(getHint().toString(), HINT_MARGIN_LEFT, HINT_MARGIN_BASE+extraOffset, paint);
    }
}
