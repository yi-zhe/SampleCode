package com.liuyang.code.controllers;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.liuyang.code.R;

import java.util.Stack;

/**
 * @author Liuyang 2016/1/27.
 */
public class BaseActivity extends Activity {

    private FragmentManager manager;
    private Stack<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getFragmentManager();
        tags = new Stack<>();
    }

    public void open(BaseFragment f) {
        open(R.id.main, f);
    }

    public void open(int container, BaseFragment f) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (!tags.empty()) {
            transaction.hide(manager.findFragmentByTag(tags.peek()));
        }
        transaction.add(container, f, f.getClass().getName());
        tags.push(f.getClass().getName());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = manager.beginTransaction();
        if (!tags.empty()) {
            transaction.remove(manager.findFragmentByTag(tags.pop()));
        } else {
            super.onBackPressed();
        }
        if (!tags.empty()) {
            transaction.show(manager.findFragmentByTag(tags.peek()));
        }
        transaction.commit();
    }
}
