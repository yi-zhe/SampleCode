package com.liuyang.code.controllers;

import android.content.Intent;
import android.net.Uri;

import com.liuyang.code.R;

import java.io.File;

/**
 * @author Liuyang 2016/2/3.
 */
public class SMS extends BaseFragment {

    @Override
    protected int layoutId() {
        return R.layout.sms;
    }

    @Override
    protected void init() {
        find(R.id.send_sms_via_other_app).setOnClickListener(l -> {
            // send sms
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:18888888888"));
            intent.putExtra("sms_body", "Press send to send me");
            startActivity(intent);
        });

        find(R.id.send_mms_via_other_app).setOnClickListener(l -> {
            // send mms
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/sdcard/1.jpg")));
            intent.putExtra("address", "15948003106");
            intent.putExtra("sms_body", "Press see the attached image");
            startActivity(intent);
        });
    }

    @Override
    protected void refresh() {

    }
}
