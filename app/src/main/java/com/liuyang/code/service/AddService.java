package com.liuyang.code.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Liuyang on 2016/2/8.
 */
public class AddService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IAddService.Stub() {
            @Override
            public int add(int a, int b) throws RemoteException {
                return a + b;
            }
        };
    }
}
