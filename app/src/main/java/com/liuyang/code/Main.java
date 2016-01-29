package com.liuyang.code;

import android.os.Bundle;
import android.view.View;

import com.liuyang.code.controllers.BaseActivity;
import com.liuyang.code.controllers.Entrance;

public class Main extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        open(new Entrance());
    }
}
