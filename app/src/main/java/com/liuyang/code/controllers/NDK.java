package com.liuyang.code.controllers;

import com.liuyang.code.R;

/**
 * @author Liuyang 2016/1/29.
 * $ javah -d ../../../../src/main/jni/ -classpath ".;d:/sdk/platforms/android-23/android.jar" com.liuyang.code.controllers.NDK
 */
public class NDK extends BaseFragment {

    static {
        System.loadLibrary("ndk");
    }

    @Override
    protected int layoutId() {
        return R.layout.ndk;
    }

    @Override
    protected void init() {
        find(R.id.ndk).setOnClickListener(v -> show(stringFromC()));
    }

    @Override
    protected void refresh() {

    }

    private native String stringFromC();

}
