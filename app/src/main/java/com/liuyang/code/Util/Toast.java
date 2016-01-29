package com.liuyang.code.util;

import android.content.Context;

/**
 * @author Liuyang 2016/1/28.
 */
public class Toast {
    public static void show(Context cxt, String text) {
        android.widget.Toast.makeText(cxt, text, android.widget.Toast.LENGTH_SHORT).show();
    }
}
