package com.liuyang.code.controllers;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.ImageView;

import com.liuyang.code.R;

/**
 * @author Liuyang 2016/3/28.
 */
public class Drawables extends BaseFragment implements View.OnClickListener {

    int level = 0;
    private ImageView vLevelListDrawable;
    private ImageView vTransitionDrawable;
    private ImageView vClipDrawable;
    private ImageView vScaleDrawable;

    @Override
    protected int layoutId() {
        return R.layout.drawables;
    }

    @Override
    protected void init() {
        vLevelListDrawable = find(R.id.level_list_drawable);
        vLevelListDrawable.setOnClickListener(this);
        vTransitionDrawable = find(R.id.transition_drawable);
        vTransitionDrawable.setOnClickListener(this);
        vScaleDrawable = find(R.id.scale_drawable);
        vScaleDrawable.setOnClickListener(this);
        vScaleDrawable.getBackground().setLevel(10000);
        vClipDrawable = find(R.id.clip_drawable);
        vClipDrawable.setOnClickListener(this);
        vClipDrawable.getBackground().setLevel(10000);
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.level_list_drawable:
                vLevelListDrawable.getDrawable().setLevel(level++ % 3);
                break;
            case R.id.transition_drawable:
                TransitionDrawable drawable = (TransitionDrawable) vTransitionDrawable.getBackground();
                if (level++ % 2 == 0) {
                    drawable.startTransition(1000);
                } else {
                    drawable.reverseTransition(1000);
                }
                break;
            case R.id.scale_drawable:
                vScaleDrawable.getBackground().setLevel(1);
                break;
            case R.id.clip_drawable:
                ClipDrawable scaleDrawable = (ClipDrawable) vClipDrawable.getBackground();
                scaleDrawable.setLevel(9000);
                break;
            default:
                break;
        }
    }
}
