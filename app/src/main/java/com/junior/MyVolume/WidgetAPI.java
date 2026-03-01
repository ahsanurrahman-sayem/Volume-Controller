package com.junior.MyVolume;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.RemoteViews;

public class WidgetAPI extends AppWidgetProvider {

// Action constants for button broadcasts
public static final String ACTION_VOLUME_UP   = "com.junior.MyVolume.VOLUME_UP";
public static final String ACTION_VOLUME_DOWN = "com.junior.MyVolume.VOLUME_DOWN";

// -----------------------------------------------------------------------
// Widget lifecycle
// -----------------------------------------------------------------------

@Override
public void onEnabled(Context context) {
    super.onEnabled(context);
}

@Override
public void onDisabled(Context context) {
    super.onDisabled(context);
}

// -----------------------------------------------------------------------
// onUpdate — called on first add and on every scheduled refresh
// -----------------------------------------------------------------------

@Override
public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    for (int appWidgetId : appWidgetIds) {
        updateWidget(context, appWidgetManager, appWidgetId);
    }
}

// -----------------------------------------------------------------------
// onReceive — handles button-press broadcast actions
// -----------------------------------------------------------------------

@Override
public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent); // handles standard AppWidget broadcasts

    String action = intent.getAction();
    if (action == null) return;

    AudioManager audioManager =
            (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

    if (ACTION_VOLUME_UP.equals(action)) {
        audioManager.adjustStreamVolume(
                AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_RAISE,
                AudioManager.FLAG_SHOW_UI
        );
    } else if (ACTION_VOLUME_DOWN.equals(action)) {
        audioManager.adjustStreamVolume(
                AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_LOWER,
                AudioManager.FLAG_SHOW_UI
        );
    }

    // Refresh all widget instances to show the updated volume level
    AppWidgetManager manager = AppWidgetManager.getInstance(context);
    int[] ids = manager.getAppWidgetIds(
            new android.content.ComponentName(context, WidgetAPI.class));
    for (int id : ids) {
        updateWidget(context, manager, id);
    }
}

// -----------------------------------------------------------------------
// Helper — build and push RemoteViews for a single widget instance
// -----------------------------------------------------------------------

private void updateWidget(Context context,
                          AppWidgetManager appWidgetManager,
                          int appWidgetId) {

    AudioManager audioManager =
            (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

    int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    int max     = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

    // Build the view
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_layout);

    // Show "current / max" in the volume text view
    views.setTextViewText(R.id.tv_volume, current + " / " + max);

    // Wire up Volume Up button
    Intent upIntent = new Intent(context, WidgetAPI.class);
    upIntent.setAction(ACTION_VOLUME_UP);
    PendingIntent upPending = PendingIntent.getBroadcast(
            context, 0, upIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    views.setOnClickPendingIntent(R.id.btn_volume_up, upPending);

    // Wire up Volume Down button
    Intent downIntent = new Intent(context, WidgetAPI.class);
    downIntent.setAction(ACTION_VOLUME_DOWN);
    PendingIntent downPending = PendingIntent.getBroadcast(
            context, 1, downIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    views.setOnClickPendingIntent(R.id.btn_volume_down, downPending);

    // Push the updated views
    appWidgetManager.updateAppWidget(appWidgetId, views);
}

}