package com.nozomi.almanac.util;

import com.nozomi.almanac.R;
import com.nozomi.almanac.activity.SplashActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class CommUtils {

	private static Toast toast = null;

	public static void makeToast(Context context, String text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			View backgroundView = toast.getView();
			backgroundView.setBackgroundResource(R.drawable.toast_background);
			toast.setView(backgroundView);
		} else {
			toast.setText(text);
		}
		toast.show();
	}

	public static void makeNotification(Context context, String text) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(context, SplashActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		Notification notification = new Notification();
		 notification.icon = R.drawable.ic_launcher;
		notification.tickerText = text;
		notification.defaults |=Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// 设置通知显示的参数
		notification
				.setLatestEventInfo(context, "Acfun黄历", text, pendingIntent);
		notificationManager.notify(0, notification);
	}
}
