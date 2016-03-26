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

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (CLICK_ACTION.equals(intent.getAction())) {
            Toast.show(context, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(new Date()));
        }
    }

    /**
     * 每次窗口小部件被点击更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }
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
