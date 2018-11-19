package com.demo.zbj.mycode.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * touch事件分发demo
 * 1.activity.dispatchTouchEvent
 *  2.根view开始递归调用 view.dispatchTouchEvent,寻找消费事件流的view
 *      3.viewGroup.onInterceptTouchEvent
 *      4.child.dispatchTouchEvent
 *          5.super.dispatchTouchEvent
 *          6.view.onTouchEvent
 * 7.activity.onTouchEvent
 * Created by BiJim on 2018/11/19.
 */

public class TouchGroup extends ViewGroup {
    public TouchGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestDisallowInterceptTouchEvent(true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    //view的dispatchTouchEvent（e）主要判断是否被消费
    //viewGroup的dispatchTouchEvent（e）需要如下模拟判断：
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
       /* 模拟判断
       boolean result;
        if (onInterceptTouchEvent(ev)) {
            result = onTouchEvent(ev);
        } else {

            //核心难点在于此处，ViewGroup.dispatchTouchEvent源码逻辑大致如下：
            1.事件流开始--action down，清除touchTarget和DISALLOW_INTERCEPT FLAG
            2.拦截处理
            3.如果不拦截&&不是cancel事件，并且是down或者pointer_down，尝试把pointer（触摸点）
            通过TouchTarget分配给子view；如果分配成功，调用child.dispatchTouchEvent（）把事件流也交给子view；
            4.判断TouchTarget
                有，调用child.dispatchTouchEvent（）把事件流也交给目标子view；
                没有，调用自己的super.dispatchTouchEvent（），相当于View.dispatchTouchEvent(),
            5.如果是POINTER_UP,从TouchTarget中清除POINTER信息，如果UP或CANCEL,重置状态；

            result = child.dispatchTouchEvent(ev);
        }
        return result;
        */
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //当满足一定条件时，返回true对子view触摸事件流进行截断，交给自身的onTouchEvent处理
        //帮助onTouchEvent记录事件信息，比如按下位置，因为onTouchEvent未接收拦截之前的事件流
        int masked = ev.getActionMasked();
        if (masked == MotionEvent.ACTION_DOWN) {
            ev.getX();
            ev.getY();
        }
        float y = ev.getY();
        if (y > getWidth() / 2) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //当onInterceptTouchEvent拦截后，该viewGroup的父view会直接将事件流交到此方法
        return super.onTouchEvent(event);
    }
}
