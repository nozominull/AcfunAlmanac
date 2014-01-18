package com.nozomi.almanac.activity;

import com.nozomi.almanac.R;
import com.nozomi.almanac.util.CommDef;
import com.nozomi.almanac.util.CommUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class SplashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		try {
			String versionName = getPackageManager().getPackageInfo(
					getPackageName(), 0).versionName;

			SharedPreferences sp = getSharedPreferences("acfun_almanac",
					Context.MODE_PRIVATE);
			boolean isFirstRun = sp.getBoolean(CommDef.SP_IS_FIRST_RUN
					+ versionName, true);
			if (isFirstRun) {
				Editor editor = sp.edit();
				editor.putBoolean(CommDef.SP_IS_FIRST_RUN + versionName, false);
				int uid = sp.getInt(CommDef.SP_UID, 0);
				if (uid == 0) {
					uid = (int) (System.currentTimeMillis() % 1000000);
					editor.putInt(CommDef.SP_UID, uid);
				}
				editor.commit();
				CommUtils.setAlarm(this);

			}

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		Animation animation = new AlphaAnimation(0, 1);
		animation.setDuration(500);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				Intent intent = new Intent(SplashActivity.this,
						AlmanacActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				finish();
			}
		});

		ImageView logoView = (ImageView) findViewById(R.id.logo);
		logoView.startAnimation(animation);

	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}