package com.liuyang.code.controllers;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liuyang.code.R;
import com.liuyang.code.SimpleTextAdapter;

/**
 * @author Liuyang 2016/1/27.
 */
public class Entrance extends BaseFragment implements View.OnClickListener {
    private String[] items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.entrance;
    }

    @Override
    protected void init() {
        RecyclerView vEntrances = findRecycler(R.id.categories);
        LinearLayoutManager manager = new LinearLayoutManager(host);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        vEntrances.setLayoutManager(manager);
        items = getResources().getStringArray(R.array.categories);
        RecyclerView.Adapter adapter = new SimpleTextAdapter(items, this);
        vEntrances.setAdapter(adapter);
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
