package com.junior.MyVolume;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.RemoteViews;

public class WidgetAPI extends AppWidgetProvider {

    public static final String ACTION_SHOW_VOLUME = "com.junior.MyVolume.SHOW_VOLUME";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();
        if (action == null) return;

        if (ACTION_SHOW_VOLUME.equals(action)) {
            AudioManager audioManager =
                    (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_SAME,
                    AudioManager.FLAG_SHOW_UI
            );
        }

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] ids = manager.getAppWidgetIds(
                new android.content.ComponentName(context, WidgetAPI.class));
        for (int id : ids) {
            updateWidget(context, manager, id);
        }
    }

    private void updateWidget(Context context,
                              AppWidgetManager appWidgetManager,
                              int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_layout);

        Intent intent = new Intent(context, WidgetAPI.class);
        intent.setAction(ACTION_SHOW_VOLUME);
        PendingIntent pending = PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.tv_volume, pending);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}