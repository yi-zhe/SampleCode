package com.liuyang.code.controllers;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liuyang.code.R;
import com.liuyang.code.SimpleTextAdapter;

/**
 * @author Liuyang 2016/2/22.
 */
public class Hardware extends BaseFragment implements View.OnClickListener {
    private String[] items;

    @Override
    protected int layoutId() {
        return R.layout.hardware;
    }

    @Override
    protected void init() {
        RecyclerView vLanguages = find(R.id.hardware);
        LinearLayoutManager manager = new LinearLayoutManager(host);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        vLanguages.setLayoutManager(manager);
        items = getResources().getStringArray(R.array.hardware);
        RecyclerView.Adapter adapter = new SimpleTextAdapter(items, this);
        vLanguages.setAdapter(adapter);
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
