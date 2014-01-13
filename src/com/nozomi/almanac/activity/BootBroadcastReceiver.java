package com.nozomi.almanac.activity;

import com.nozomi.almanac.util.CommUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		CommUtils.setAlarm(context);
	}

}