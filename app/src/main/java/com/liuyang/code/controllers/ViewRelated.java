package com.liuyang.code.controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RemoteViews;

import com.liuyang.code.Main;
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
        int tag = (int) v.getTag();
        if (tag == 1) {
            Notification notification = new Notification();
            notification.icon = R.mipmap.ic_launcher;
            notification.tickerText = "hello world";
            notification.when = System.currentTimeMillis();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            Intent intent = new Intent(host, Main.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(host, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            RemoteViews remoteViews = new RemoteViews(host.getPackageName(), R.layout.custom_notification);
            remoteViews.setTextViewText(R.id.msg, "hello msg");
            remoteViews.setImageViewResource(R.id.icon, R.mipmap.ic_launcher);
            notification.contentView = remoteViews;
            notification.contentIntent = pendingIntent;
            NotificationManager manager = (NotificationManager) host.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(2, notification);
            return;
        }
        open(PATH + items[tag]);
    }
}
