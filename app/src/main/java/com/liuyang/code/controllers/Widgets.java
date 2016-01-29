package com.liuyang.code.controllers;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liuyang.code.R;
import com.liuyang.code.SimpleTextAdapter;

/**
 * @author Liuyang 2016/1/27.
 */
public class Widgets extends BaseFragment implements View.OnClickListener {
    private String[] items;

    @Override
    protected int layoutId() {
        return R.layout.widgets;
    }

    @Override
    protected void init() {
        RecyclerView vWidgets = findRecycler(R.id.widgets);
        LinearLayoutManager manager = new LinearLayoutManager(host);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        vWidgets.setLayoutManager(manager);
        items = getResources().getStringArray(R.array.widgets);
        RecyclerView.Adapter adapter = new SimpleTextAdapter(items, this);
        vWidgets.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onClick(View v) {
        open(PATH + items[(int) v.getTag()]);
    }
}
