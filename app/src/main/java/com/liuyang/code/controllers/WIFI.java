package com.liuyang.code.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.widget.Switch;

import com.liuyang.code.R;

/**
 * @author Liuyang 2016/2/22.
 */
public class WIFI extends BaseFragment {
    private WifiStateChangedReceiver wifiStateChangedReceiver;

    @Override
    protected int layoutId() {
        return R.layout.wifi;
    }

    @Override
    protected void init() {
        Switch wifiSwitch = find(R.id.wifi_switch);
        WifiManager manager = (WifiManager) host.getSystemService(Context.WIFI_SERVICE);
        wifiSwitch.setChecked(manager.isWifiEnabled());
        wifiSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> manager.setWifiEnabled(isChecked));

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        wifiStateChangedReceiver = new WifiStateChangedReceiver();
        host.registerReceiver(wifiStateChangedReceiver, filter);
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        host.unregisterReceiver(wifiStateChangedReceiver);
    }

    private class WifiStateChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            int previousWifiState = intent.getIntExtra(WifiManager.EXTRA_PREVIOUS_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            show("current state:" + getWifiState(wifiState) + ", previous state:" + getWifiState(previousWifiState));
        }

        private String getWifiState(int state) {
            String result;
            switch (state) {
                case WifiManager.WIFI_STATE_DISABLED:
                    result = "DISABLED";
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    result = "DISABLING";
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    result = "ENABLED";
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    result = "ENABLING";
                    break;
                default:
                    result = "UNKNOWN";
                    break;
            }
            return result;
        }
    }
}
