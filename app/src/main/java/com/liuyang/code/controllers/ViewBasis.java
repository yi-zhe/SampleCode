package com.liuyang.code.controllers;

import android.animation.ObjectAnimator;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuyang.code.R;
import com.liuyang.code.widgets.ScrollerView;

/**
 * @author Liuyang 2016/3/22.
 */
public class ViewBasis extends BaseFragment {
    private int i = 0;

    @Override
    protected int layoutId() {
        return R.layout.view_basis;
    }

    @Override
    protected void init() {
        ScrollerView view = find(R.id.scroller_view);
        view.setOnClickListener(l -> {
            if (i++ % 2 == 0) {
                view.smoothScrollTo(-100, 0);
            } else {
                view.smoothScrollTo(0, 0);
            }
        });

        TextView animationView = find(R.id.animation_view);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) animationView.getLayoutParams();
        animationView.setOnClickListener(l -> {
            if (i++ % 2 == 0) {
                lp.topMargin = 0;
                ObjectAnimator.ofFloat(animationView, "translationX", 0, 100).setDuration(1000).start();
            } else {
                lp.topMargin = 20;
                ObjectAnimator.ofFloat(animationView, "translationX", 100, 0).setDuration(1000).start();
            }
            animationView.setLayoutParams(lp);
        });

    }

    @Override
    protected void refresh() {

    }
}
