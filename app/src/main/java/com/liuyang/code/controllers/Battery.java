package com.liuyang.code.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.liuyang.code.R;

/**
 * Created by Liuyang on 2016/2/7.
 */
public class Battery extends BaseFragment {

    private BatteryReceiver batteryReceiver;

    @Override
    protected int layoutId() {
        return R.layout.battery;
    }

    @Override
    protected void init() {
        batteryReceiver = new BatteryReceiver();
        host.registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (batteryReceiver != null) {
            host.unregisterReceiver(batteryReceiver);
        }
    }

    private class BatteryReceiver extends BroadcastReceiver {
        int health;
        int temperature;
        int level;
        int scale;
        int plugged;
        boolean isBatteryPresent;
        int status;
        String technology;
        int voltage;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                return;
            }
            health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            isBatteryPresent = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
            status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            show("电池温度=" + temperature / 10 + "摄氏度\n电池状态=" + batteryHealth(health) + "\n电池余量=" + level
                    + "\n电池总量=" + scale + "\n充电类型=" + batteryPlugged(plugged) + "\n是否有电池=" + isBatteryPresent
                    + "\n电池充放电状态=" + batteryStatus(status) + "\n电池技术=" + technology
                    + "\n电池电压=" + voltage + "mv");
        }

        private String batteryHealth(int status) {
            String result;
            switch (status) {
                case BatteryManager.BATTERY_HEALTH_COLD:
                    result = "BATTERY_HEALTH_COLD";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    result = "BATTERY_HEALTH_DEAD";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    result = "BATTERY_HEALTH_GOOD";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    result = "BATTERY_HEALTH_OVERHEAT";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    result = "BATTERY_HEALTH_OVER_VOLTAGE";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    result = "BATTERY_HEALTH_UNSPECIFIED_FAILURE";
                    break;
                default:
                    result = "BATTERY_HEALTH_UNKNOWN";
                    break;
            }
            return result;
        }

        private String batteryPlugged(int plugged) {
            String result;
            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    result = "BATTERY_PLUGGED_AC";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    result = "BATTERY_PLUGGED_USB";
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    result = "BATTERY_PLUGGED_WIRELESS";
                    break;
                default:
                    result = "UNKNOWN";
                    break;
            }
            return result;
        }

        private String batteryStatus(int status) {
            String result;
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    result = "BATTERY_STATUS_CHARGING";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    result = "BATTERY_STATUS_DISCHARGING";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    result = "BATTERY_STATUS_FULL";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    result = "BATTERY_STATUS_NOT_CHARGING";
                    break;
                default:
                    result = "BATTERY_STATUS_UNKNOWN";
                    break;
            }
            return result;
        }
    }
}
