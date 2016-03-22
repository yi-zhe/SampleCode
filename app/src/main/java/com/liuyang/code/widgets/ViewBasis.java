package com.liuyang.code.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;

import com.liuyang.code.util.Toast;

/**
 * @author Liuyang 2016/3/22.
 */
public class ViewBasis extends FrameLayout {

    private VelocityTracker velocityTracker;
    private Context context;

    public ViewBasis(Context context) {
        this(context, null);
    }

    public ViewBasis(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewBasis(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        velocityTracker = VelocityTracker.obtain();
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        velocityTracker.computeCurrentVelocity(1000);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Toast.show(context, String.valueOf(velocityTracker.getXVelocity()) + " " + velocityTracker.getYVelocity());
                velocityTracker.clear();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (velocityTracker != null) {
            velocityTracker.clear();
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }
}
