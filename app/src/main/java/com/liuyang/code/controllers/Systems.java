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
public class Systems extends BaseFragment implements View.OnClickListener {
    private String[] items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.common_list;
    }

    @Override
    protected void init() {
        RecyclerView vEntrances = find(R.id.lists);
        LinearLayoutManager manager = new LinearLayoutManager(host);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        vEntrances.setLayoutManager(manager);
        items = getResources().getStringArray(R.array.systems);
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
