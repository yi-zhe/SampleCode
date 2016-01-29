package com.liuyang.code;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Liuyang 2016/1/26.
 */
public class SimpleTextAdapter extends RecyclerView.Adapter {
    private String[] items;
    private View.OnClickListener listener;

    public SimpleTextAdapter(String[] items, View.OnClickListener l) {
        this.items = items;
        this.listener = l;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleTextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_text__item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(listener);
        ((TextView) holder.itemView).setText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.length;
    }

    class SimpleTextViewHolder extends RecyclerView.ViewHolder {
        public SimpleTextViewHolder(View itemView) {
            super(itemView);
        }
    }
}
