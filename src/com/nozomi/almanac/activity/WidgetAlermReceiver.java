package com.nozomi.almanac.activity;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class WidgetAlermReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		int[] appWidgetIds = appWidgetManager
				.getAppWidgetIds(new ComponentName(context,
						WidgetProvider.class));
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			WidgetProvider.updateAppWidget(context, appWidgetManager,
					appWidgetIds[i]);
		}
	}
}