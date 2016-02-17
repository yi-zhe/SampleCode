package com.liuyang.code.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

import java.util.List;

/**
 * @author Liuyang 2016/2/8.
 */
public class AccessService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getSource() == null) return;
        findAndPerformAction(Button.class.getName(), "确定", AccessibilityNodeInfo.ACTION_CLICK);
        findAndPerformAction(Button.class.getName(), "安装", AccessibilityNodeInfo.ACTION_CLICK);
        findAndPerformAction(Button.class.getName(), "下一步", AccessibilityNodeInfo.ACTION_CLICK);
        findAndPerformAction(Button.class.getName(), "打开", AccessibilityNodeInfo.ACTION_CLICK);
    }

    @Override
    public void onInterrupt() {

    }

    private void findAndPerformAction(String nodeClassName, String text, int action) {
        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
        if (rootInActiveWindow == null) return;

        List<AccessibilityNodeInfo> nodes = rootInActiveWindow.findAccessibilityNodeInfosByText(text);
        int size = nodes.size();
        for (int i = 0; i < size; i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            if (nodeClassName.equals(node.getClassName())) {
                node.performAction(action);
            }
        }
    }
}
