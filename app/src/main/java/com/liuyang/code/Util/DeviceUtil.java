package com.liuyang.code.util;

import android.os.Build;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Liuyang 2016/2/11.
 */
public class DeviceUtil {

    public static final int DEVICE_INFO_UNKNOWN = -1;
    private static final FileFilter CPU_FILTER = pathname -> {
        String path = pathname.getName();
        //regex is slow, so checking char by char.
        if (path.startsWith("cpu")) {
            for (int i = 3; i < path.length(); i++) {
                if (!Character.isDigit(path.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    };

    public static int cpuCores() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            // Gingerbread doesn't support giving a single application access to both cores, but a
            // handful of devices (Atrix 4G and Droid X2 for example) were released with a dual-core
            // chipset and Gingerbread; that can let an app in the background run without impacting
            // the foreground application. But for our purposes, it makes them single core.
            return 1;
        }
        int cores;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (SecurityException e) {
            cores = DEVICE_INFO_UNKNOWN;
        } catch (NullPointerException e) {
            cores = DEVICE_INFO_UNKNOWN;
        }
        return cores;
    }

    public static String maxCpuFreqInKHZ() {
        return FileUtils.retrivePattern("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "(\\d+)");
    }

    public static String minCpuFreqInKHZ() {
        return FileUtils.retrivePattern("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq", "(\\d+)");
    }
}
