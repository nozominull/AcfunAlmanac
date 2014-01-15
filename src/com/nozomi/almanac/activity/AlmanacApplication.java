package com.nozomi.almanac.activity;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

public class AlmanacApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

	}
}
