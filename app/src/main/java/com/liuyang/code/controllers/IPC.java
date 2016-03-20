package com.liuyang.code.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liuyang.code.R;
import com.liuyang.code.SimpleTextAdapter;
import com.liuyang.code.model.Book;

/**
 * @author Liuyang 2016/3/20.
 */
public class IPC extends BaseFragment implements View.OnClickListener {
    private String[] items;

    @Override
    protected int layoutId() {
        return R.layout.ipc;
    }

    @Override
    protected void init() {
        RecyclerView vLanguages = find(R.id.ipc);
        LinearLayoutManager manager = new LinearLayoutManager(host);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        vLanguages.setLayoutManager(manager);
        items = getResources().getStringArray(R.array.ipc);
        RecyclerView.Adapter adapter = new SimpleTextAdapter(items, this);
        vLanguages.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        if (tag == 0) {
            Intent intent = new Intent(host, ParcelableIPCActivity.class);
            Book book = new Book("name", 208976388449L);
            Bundle b = new Bundle();
            b.putParcelable(Book.class.getName(), book);
            intent.putExtras(b);
            startActivity(intent);
            return;
        }
        open(PATH + items[tag]);
    }
}
