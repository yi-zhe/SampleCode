package com.liuyang.code.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.liuyang.code.R;
import com.liuyang.code.SimpleTextAdapter;
import com.liuyang.code.model.Book;
import com.liuyang.code.service.MessengerService;

/**
 * @author Liuyang 2016/3/20.
 */
public class IPC extends BaseFragment implements View.OnClickListener {
    private String[] items;
    private Messenger mService;
    private ServiceConnection connection;
    public static final int MSG_FROM_SERVICE = 2;
    private Messenger mReplyMessenger;

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

        mReplyMessenger = new Messenger(new MessengerHandler());
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        switch (tag) {
            case 0:
                Intent intent = new Intent(host, ParcelableIPCActivity.class);
                Book book = new Book("name", 208976388449L);
                Bundle b = new Bundle();
                b.putParcelable(Book.class.getName(), book);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case 1:
                connection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        mService = new Messenger(service);
                        Message msg = Message.obtain(null, MessengerService.MSG_FROM_CLIENT);
                        Bundle data = new Bundle();
                        data.putString("msg", "hello, this is client.");
                        msg.setData(data);
                        msg.replyTo = mReplyMessenger;
                        try {
                            mService.send(msg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                };

                Intent serviceIntent = new Intent(host, MessengerService.class);
                host.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
                break;
            default:
                open(PATH + items[tag]);
        }
    }

    @Override
    public void onDestroy() {
        if (connection != null) {
            host.unbindService(connection);
        }
        super.onDestroy();
    }

    private static class MessengerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_SERVICE:
                    Log.v("MessengerService", "receive msg from service:" + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

}
