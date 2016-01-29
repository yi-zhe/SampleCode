package com.liuyang.code.controllers;

import android.widget.SeekBar;

import com.liuyang.code.R;
import com.liuyang.code.widgets.WaveLoadingView;

/**
 * @author Liuyang 2016/1/28.
 */
public class WaveLoading extends BaseFragment {
    @Override
    protected int layoutId() {
        return R.layout.wave_loading;
    }

    @Override
    protected void init() {
        final WaveLoadingView view = (WaveLoadingView) host.findViewById(R.id.wave_loading);
        findSeekBar(R.id.seekbar).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                view.setPercent(progress);
                view.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void refresh() {

    }
}
