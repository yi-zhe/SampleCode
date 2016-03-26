package com.liuyang.code.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.liuyang.code.R;
import com.liuyang.code.util.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Liuyang 2016/3/25.
 */
public class WidgetProvider extends AppWidgetProvider {
    public static final String CLICK_ACTION = "com.liuyang.code.action.widget.click";

    public WidgetProvider() {
        super();
    }

    /**
     * This will be called when the widget is added to the launcher for the <strong>first</strong> time
     */
    @Override
    public void onEnabled(Context context) {
        Toast.show(context, "onEnabled");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (CLICK_ACTION.equals(intent.getAction())) {
            Toast.show(context, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(new Date()));
        }
    }

    /**
     * 每当小部件添加或者更新时都会调用一次, 更新时机由updatePeriodMillis决定
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }
    }

    /**
     * 每删除一个桌面小部件都会调用一次
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Toast.show(context, "onDeleted");
    }

    /**
     * 最后一个该类型的桌面小部件被删除时调用
     */
    @Override
    public void onDisabled(Context context) {
        Toast.show(context, "onDisabled");
    }

    private void onWidgetUpdate(Context context, AppWidgetManager appWidgeManger, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_text, pendingIntent);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        remoteViews.setTextViewText(R.id.widget_text, df.format(new Date()));
        appWidgeManger.updateAppWidget(appWidgetId, remoteViews);
    }
}
