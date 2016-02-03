package com.liuyang.code.controllers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.liuyang.code.R;

/**
 * @author Liuyang 2016/2/3.
 */
public class Phone extends BaseFragment {
    @Override
    protected int layoutId() {
        return R.layout.phone;
    }

    @Override
    protected void init() {
        find(R.id.call_phone_dial_directly).setOnClickListener(l -> {
            call(host, "88888888888");
        });

        find(R.id.call_phone_dial_pad).setOnClickListener(l -> {
            dial(host, "88888888888");
        });
    }

    @Override
    protected void refresh() {

    }

    private static void call(Activity act, String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        act.startActivity(intent);
    }

    private static void dial(Activity act, String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        act.startActivity(intent);
    }
}
