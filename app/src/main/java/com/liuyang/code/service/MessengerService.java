package com.liuyang.code.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.liuyang.code.controllers.IPC;

/**
 * @author Liuyang 2016/2/23.
 */
public class MessengerService extends Service {

    public static final int MSG_FROM_CLIENT = 1;
    private Messenger mMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        mMessenger = new Messenger(new MessengerHandler());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger == null ? null : mMessenger.getBinder();
    }

    private static class MessengerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    Log.v("MessengerService", "receive msg from client:" + msg.getData().getString("msg"));
                    Message replyMessage = Message.obtain(null, IPC.MSG_FROM_SERVICE);
                    Bundle data = new Bundle();
                    data.putString("reply", "hello, this is server.");
                    replyMessage.setData(data);
                    try {
                        msg.replyTo.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
