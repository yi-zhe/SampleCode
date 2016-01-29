package com.liuyang.code.controllers;

import com.liuyang.code.R;
import com.liuyang.code.widgets.titanic.Titanic;
import com.liuyang.code.widgets.titanic.TitanicTextView;

/**
 * @author Liuyang 2016/1/27.
 */
public class WaveTextView extends BaseFragment {
    @Override
    protected int layoutId() {
        return R.layout.wave_text;
    }

    @Override
    protected void init() {
        TitanicTextView vWave = (TitanicTextView) host.findViewById(R.id.wave);
        Titanic titanic = new Titanic();
        titanic.start(vWave);
    }

    @Override
    protected void refresh() {

    }
}
