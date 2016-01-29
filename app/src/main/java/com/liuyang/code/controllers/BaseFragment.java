package com.liuyang.code.controllers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.liuyang.code.util.Toast;

/**
 * @author Liuyang 2016/1/27.
 */
public abstract class BaseFragment extends Fragment {

    protected Activity host;
    private FragmentManager manager;
    protected static final String PATH = "com.liuyang.code.controllers.";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layoutId(), container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        host = getActivity();
        manager = host.getFragmentManager();
        init();
        refresh();
    }

    protected void open(String clazz) {
        try {
            open((BaseFragment) Class.forName(clazz).newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void open(BaseFragment f) {
        ((BaseActivity) getActivity()).open(f);
    }

    protected RecyclerView findRecycler(int id) {
        return (RecyclerView) host.findViewById(id);
    }

    protected SeekBar findSeekBar(int id) {
        return (SeekBar) host.findViewById(id);
    }

    protected TextView findText(int id) {
        return (TextView) host.findViewById(id);
    }

    protected void show(String text) {
        Toast.show(host, text);
    }

    protected abstract int layoutId();

    protected abstract void init();

    protected abstract void refresh();
}
