package com.liuyang.code.controllers;

import android.widget.Toast;

import com.liuyang.code.R;

/**
 * @author Liuyang 2016/1/28.
 */
public class LambdaTest extends BaseFragment {
    @Override
    protected int layoutId() {
        return R.layout.lambda_test;
    }

    @Override
    protected void init() {
        find(R.id.lambda_test).setOnClickListener(v -> Toast.makeText(host, "Lambda", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void refresh() {

    }
}
