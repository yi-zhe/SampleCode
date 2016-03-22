package com.liuyang.code.controllers;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liuyang.code.R;
import com.liuyang.code.SimpleTextAdapter;

/**
 * @author Liuyang 2016/3/22.
 */
public class ViewRelated extends BaseFragment implements View.OnClickListener {
    private String[] items;

    @Override
    protected int layoutId() {
        return R.layout.view_related;
    }

    @Override
    protected void init() {
        RecyclerView vLanguages = find(R.id.view_related);
        LinearLayoutManager manager = new LinearLayoutManager(host);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        vLanguages.setLayoutManager(manager);
        items = getResources().getStringArray(R.array.view_related);
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
