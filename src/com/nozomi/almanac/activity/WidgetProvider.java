package com.nozomi.almanac.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.nozomi.almanac.R;
import com.nozomi.almanac.model.TableItem;
import com.nozomi.almanac.util.CommUtils;
import com.nozomi.almanac.util.LunarUtil;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Pair;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	public static void updateAppWidget(Context context,
			AppWidgetManager appWidgeManger, int appWidgetId) {
		RemoteViews rv = new RemoteViews(context.getPackageName(),
				R.layout.widget);

		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		String[] dayOfWeek = { "日", "一", "二", "三", "四", "五", "六" };
		rv.setTextViewText(
				R.id.item_date_calendar,
				calendar.get(Calendar.YEAR) + "年"
						+ (1 + calendar.get(Calendar.MONTH)) + "月"
						+ calendar.get(Calendar.DATE) + "日 星期"
						+ dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

		Pair<Long, String> fortunePair = CommUtils
				.getFortune(context, calendar);
		rv.setTextViewText(R.id.item_sign_calendar, fortunePair.second);

		rv.setTextColor(R.id.item_sign_calendar, Color.rgb(
				(int) (255 * (10 + fortunePair.first * 0.8) / 100), 51, 51));

		rv.setTextViewText(R.id.item_subdate_calendar,
				LunarUtil.GetLunarDay(calendar.get(Calendar.YEAR),
						(1 + calendar.get(Calendar.MONTH)),
						calendar.get(Calendar.DATE)));

		Pair<ArrayList<TableItem>, ArrayList<TableItem>> tableItemArrayPair = new Pair<ArrayList<TableItem>, ArrayList<TableItem>>(
				new ArrayList<TableItem>(), new ArrayList<TableItem>());

		CommUtils.getTableItemArray(context, calendar, tableItemArrayPair);
		ArrayList<TableItem> goodTableItemArray = tableItemArrayPair.first;
		rv.removeAllViews(R.id.good_right);
		for (int i = 0; i < goodTableItemArray.size(); i++) {
			TableItem tableItem = goodTableItemArray.get(i);
			RemoteViews tableItemView = new RemoteViews(
					context.getPackageName(), R.layout.widget_table_item);
			tableItemView.setImageViewResource(R.id.avatar,
					tableItem.getAvatar());
			tableItemView.setTextViewText(R.id.name, tableItem.getName());
			tableItemView.setTextViewText(R.id.content, tableItem.getContent());
			rv.addView(R.id.good_right, tableItemView);
		}

		ArrayList<TableItem> badTableItemArray = tableItemArrayPair.second;
		rv.removeAllViews(R.id.bad_right);
		for (int i = 0; i < badTableItemArray.size(); i++) {
			TableItem tableItem = badTableItemArray.get(i);
			RemoteViews tableItemView = new RemoteViews(
					context.getPackageName(), R.layout.widget_table_item);
			tableItemView.setImageViewResource(R.id.avatar,
					tableItem.getAvatar());
			tableItemView.setTextViewText(R.id.name, tableItem.getName());
			tableItemView.setTextViewText(R.id.content, tableItem.getContent());
			rv.addView(R.id.bad_right, tableItemView);
		}
		Intent intent = new Intent(context, SplashActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		rv.setOnClickPendingIntent(R.id.background, pendingIntent);
		appWidgeManger.updateAppWidget(appWidgetId, rv);
	}
}