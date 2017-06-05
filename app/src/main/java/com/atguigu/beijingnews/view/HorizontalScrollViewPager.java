package com.atguigu.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/6/5.
 */

public class HorizontalScrollViewPager extends ViewPager {

    private float downX;
    private float moveX;

    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getRawX();

                //从左向右滑动
                if (moveX - downX > 0 && getCurrentItem() == 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);

                } else if (downX - moveX > 0 && getCurrentItem() == getAdapter().getCount() - 1) {

                    getParent().requestDisallowInterceptTouchEvent(false);

                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }


                break;
            case MotionEvent.ACTION_UP:


                break;
        }


        return super.dispatchTouchEvent(ev);
    }
}




