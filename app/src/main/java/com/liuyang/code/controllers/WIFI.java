package com.liuyang.code.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.liuyang.code.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liuyang 2016/2/22.
 */
public class WIFI extends BaseFragment implements View.OnClickListener {
    private WifiManager manager;
    private WifiStateChangedReceiver wifiStateChangedReceiver;
    private ScanResultsAvailableReceiver scanResultsAvailableReceiver;
    private List<ScanResult> scanResults;
    private RecyclerView vResults;
    private WifiScanResultsAdapter wifiScanResultsAdapter;

    @Override
    protected int layoutId() {
        return R.layout.wifi;
    }

    @Override
    protected void init() {
        Switch wifiSwitch = find(R.id.wifi_switch);
        manager = (WifiManager) host.getSystemService(Context.WIFI_SERVICE);
        wifiSwitch.setChecked(manager.isWifiEnabled());
        wifiSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> manager.setWifiEnabled(isChecked));

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        wifiStateChangedReceiver = new WifiStateChangedReceiver();
        host.registerReceiver(wifiStateChangedReceiver, filter);

        IntentFilter scanFinishedFilter = new IntentFilter();
        scanFinishedFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        scanResultsAvailableReceiver = new ScanResultsAvailableReceiver();
        host.registerReceiver(scanResultsAvailableReceiver, scanFinishedFilter);

        scanResults = new ArrayList<>();
        vResults = find(R.id.available_wifi);
        LinearLayoutManager manager = new LinearLayoutManager(host);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        vResults.setLayoutManager(manager);
        wifiScanResultsAdapter = new WifiScanResultsAdapter(scanResults, this);
        vResults.setAdapter(wifiScanResultsAdapter);
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        host.unregisterReceiver(wifiStateChangedReceiver);
        host.unregisterReceiver(scanResultsAvailableReceiver);
    }

    @Override
    public void onClick(View v) {
        ScanResult result = scanResults.get((int) v.getTag());
        manager.disconnect();
        // in Xiaomi 4 this will cause anr.
        manager.addNetwork(createWifiInfo(result.SSID, "11111111", 1));
    }

    /*
     * type == 2 is not tested.
     */
    public WifiConfiguration createWifiInfo(String SSID, String password, int type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.isExsits(SSID);
        if (tempConfig != null) {
            manager.removeNetwork(tempConfig.networkId);
        }

        if (type == 1) {//WIFICIPHER_NOPASS
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }

        if (type == 2) {//WIFICIPHER_WEP
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (type == 3) {//WIFICIPHER_WPA
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration isExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = manager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    private class WifiStateChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            int previousWifiState = intent.getIntExtra(WifiManager.EXTRA_PREVIOUS_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            show("current state:" + getWifiState(wifiState) + ", previous state:" + getWifiState(previousWifiState));

            if (WifiManager.WIFI_STATE_ENABLED == wifiState) {
                manager.startScan();
            } else if (WifiManager.WIFI_STATE_DISABLED == wifiState) {
                scanResults.clear();
                wifiScanResultsAdapter.notifyDataSetChanged();
            }
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

    private class ScanResultsAvailableReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            scanResults.clear();
            scanResults.addAll(manager.getScanResults());
            wifiScanResultsAdapter.notifyDataSetChanged();
        }
    }

    private class WifiScanResultsAdapter extends RecyclerView.Adapter {

        private List<ScanResult> results;
        private View.OnClickListener l;

        public WifiScanResultsAdapter(List<ScanResult> results, View.OnClickListener l) {
            this.results = results;
            this.l = l;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_text__item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextView) holder.itemView).setText(results.get(position).toString());
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(l);
        }

        @Override
        public int getItemCount() {
            return results == null ? 0 : results.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
