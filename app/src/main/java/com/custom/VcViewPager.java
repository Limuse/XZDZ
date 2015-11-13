package com.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;

/**
 * Created by huisou on 2015/11/11.
 */
public class VcViewPager extends ViewPager {
//    private EdgeEffectCompat leftEdge;
//
//    private EdgeEffectCompat rightEdge;
//
//    public VcViewPager(Context context) {
//        super(context);
//
//    }
//
//    public VcViewPager(Context context, ViewPager viewPager) {
//        super(context, (AttributeSet) viewPager);
//        try {
//            Field leftEdgeField = viewPager.getClass().getDeclaredField("mLeftEdge");
//            Field rightEdgeField = viewPager.getClass().getDeclaredField("mRightEdge");
//            if (leftEdgeField != null && rightEdgeField != null) {
//                leftEdgeField.setAccessible(true);
//                rightEdgeField.setAccessible(true);
//                leftEdge = (EdgeEffectCompat) leftEdgeField.get(viewPager);
//                rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewPager);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public VcViewPager(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public void setOnPageChangeListener(OnPageChangeListener listener) {
//        super.setOnPageChangeListener(listener);
//    }
//
//    @Override
//    protected void onPageScrolled(int position, float offset, int offsetPixels) {
//        super.onPageScrolled(position, offset, offsetPixels);
//        if (leftEdge != null && rightEdge != null) {
//            leftEdge.finish();
//            rightEdge.finish();
//            leftEdge.setSize(0, 0);
//            rightEdge.setSize(0, 0);
//        }
//    }
private boolean scrollble=true;

    public VcViewPager (Context context){
        super(context);
    }

    public VcViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollble) {
            return true;
        }
        return super.onTouchEvent(ev);
    }


    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }
}
