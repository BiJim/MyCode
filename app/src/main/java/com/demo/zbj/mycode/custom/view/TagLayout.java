package com.demo.zbj.mycode.custom.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.demo.zbj.mycode.util.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义标签布局
 * Created by BiJim on 2018/11/7.
 */

public class TagLayout extends ViewGroup {
    private final List<Rect> sizeList = new ArrayList<>();
    private final int BASE_MARGIN_TOP = (int) DisplayUtils.dp2px(10);
    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parenWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthUsed=0;
        int heightUsed=BASE_MARGIN_TOP;
        int lineMaxHeight=0;
        int childMeasuredWidth=0;
        int childMeasuredHeight=0;
        int extraWidth;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //测量当前子view需要的宽高,widthUsed需要为0
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,heightUsed);
            childMeasuredWidth = child.getMeasuredWidth();
            childMeasuredHeight = child.getMeasuredHeight();

            //超过当前行宽度，换行
            if (parentMode != MeasureSpec.UNSPECIFIED && childMeasuredWidth + widthUsed > parenWidth) {
                //换行前记录剩余的行宽，可以用来平均行内view间隔，使得看起来美观
                extraWidth = parenWidth - widthUsed;
                //已使用高度=原高度+新的一行高度+行间距高度；
                heightUsed = heightUsed + lineMaxHeight + BASE_MARGIN_TOP;
                lineMaxHeight = 0;
                widthUsed = 0;
                measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
                childMeasuredWidth = child.getMeasuredWidth();
                childMeasuredHeight = child.getMeasuredHeight();
            }
            Rect rect;
            if (sizeList.size() <= i) {
                rect = new Rect();
                sizeList.add(rect);
            } else {
                rect = sizeList.get(i);
            }
            lineMaxHeight = Math.max(lineMaxHeight, childMeasuredHeight);
            rect.set(widthUsed,heightUsed,widthUsed + childMeasuredWidth,heightUsed + childMeasuredHeight);
            widthUsed += childMeasuredWidth;

        }
        //已使用高度+最后一行view的高度=viewGroup高度
        heightUsed += lineMaxHeight;
        System.out.println(parenWidth+" -- "+widthUsed+" -- "+heightUsed+ " -- "+getChildCount()+ " \nchild:  H"+childMeasuredHeight+",W"+childMeasuredWidth);
        setMeasuredDimension(parenWidth,heightUsed);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect childBounds = sizeList.get(i);
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

}
