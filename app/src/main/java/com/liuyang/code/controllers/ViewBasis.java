package com.liuyang.code.controllers;

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
    }

    @Override
    protected void refresh() {

    }
}
