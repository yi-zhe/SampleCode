package com.liuyang.code.controllers;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.liuyang.code.R;

/**
 * @author Liuyang 2016/2/11.
 */
public class DeviceInfo extends BaseFragment {
    @Override
    protected int layoutId() {
        return R.layout.device_info;
    }

    @Override
    protected void init() {
        ((TextView) find(R.id.brand)).setText("品牌:" + Build.BRAND);
        ((TextView) find(R.id.model)).setText("型号:" + Build.MODEL);
        ((TextView) find(R.id.android_version_name)).setText("Android版本:" + Build.VERSION.RELEASE);
        ((TextView) find(R.id.cpu_model)).setText("CPU型号:" + Build.HARDWARE.toUpperCase());
        ((TextView) find(R.id.gpu)).setText("GPU渲染器:" + Build.HARDWARE.toUpperCase());

        DisplayMetrics dm = new DisplayMetrics();
        host.getWindowManager().getDefaultDisplay().getMetrics(dm);
        ((TextView) find(R.id.resolution)).setText("分辨率:" + dm.widthPixels + " X " + dm.heightPixels);
        ((TextView) find(R.id.density)).setText("屏幕密度:" + dm.density);
        int cameraCount = Camera.getNumberOfCameras();
        try {
            for (int i = 0; i < cameraCount; i++) {
                Camera.Parameters parameter = Camera.open(i).getParameters();
                Camera.Size size = parameter.getSupportedPictureSizes().get(i);
                show("摄像头" + i + " 分辨率 " + size.width + " X " + size.height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((TextView) find(R.id.serial)).setText("设备序列号:" + Build.SERIAL);

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ((ActivityManager) host.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(mi);
        ((TextView) find(R.id.memory)).setText("运行内存:" + Formatter.formatFileSize(host, mi.availMem) + "/" + Formatter.formatFileSize(host, mi.totalMem));
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            ((TextView) find(R.id.storage)).setText("机身存储:" + Formatter.formatFileSize(host, stat.getFreeBytes()) + "/" + Formatter.formatFileSize(host, stat.getTotalBytes()));
        }
        
    }

    @Override
    protected void refresh() {

    }
}
