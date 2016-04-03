package com.liuyang.code;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.liuyang.code.controllers.BaseActivity;
import com.liuyang.code.controllers.Entrance;

public class Main extends BaseActivity {

    private LinearLayout vContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vContainer = (LinearLayout) findViewById(R.id.main);
        Log.v(Main.class.getSimpleName(), "onCreate " + vContainer.getWidth() + " " + vContainer.getHeight());
        vContainer.post(() -> Log.v(Main.class.getSimpleName(), "post Runnable " + vContainer.getWidth() + " " + vContainer.getHeight()));
        open(new Entrance());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.v(Main.class.getSimpleName(), "onWindowFocusChanged " + vContainer.getWidth() + " " + vContainer.getHeight());
        }
    }
}
