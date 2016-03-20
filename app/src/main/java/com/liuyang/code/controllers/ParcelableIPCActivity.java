package com.liuyang.code.controllers;

import android.app.Activity;
import android.os.Bundle;

import com.liuyang.code.model.Book;
import com.liuyang.code.util.Toast;

/**
 * @author Liuyang 2016/3/20.
 */
public class ParcelableIPCActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.show(this, getIntent().getExtras().getParcelable(Book.class.getName()).toString());
    }
}
