package com.liuyang.code.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.liuyang.code.R;
import com.liuyang.code.service.IAddService;

/**
 * @author Liuyang 2016/2/8.
 */
public class AIDL extends BaseFragment {

    private IAddService addService;
    private AddServiceConnection connection;

    @Override
    protected int layoutId() {
        return R.layout.aidl;
    }

    @Override
    protected void init() {
        if (!initService()) {
            show("init service failed!");
        }

        find(R.id.aidl_add).setOnClickListener(l -> {
            int add = 0;
            try {
                add = addService.add(5, 7);
            } catch (Exception e) {
                e.printStackTrace();
            }
            show(add + "");
        });
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyService();
    }

    private boolean initService() {
        connection = new AddServiceConnection();
        Intent intent = new Intent();
        intent.setClassName(host.getPackageName(), com.liuyang.code.service.AddService.class.getName());
        // This call is asynchronous, you should not call the service's method until onServiceConnected is called.
        return host.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void destroyService() {
        host.unbindService(connection);
        connection = null;
    }

    private class AddServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            addService = IAddService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            addService = null;
        }
    }
}
