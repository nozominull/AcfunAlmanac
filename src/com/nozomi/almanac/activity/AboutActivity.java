package com.nozomi.almanac.activity;

import java.util.ArrayList;
import java.util.Random;

import com.nozomi.almanac.R;
import com.nozomi.almanac.util.CommUtils;
import com.umeng.analytics.MobclickAgent;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);

		initView();
	}

	private void initView() {

		ImageButton backView = (ImageButton) findViewById(R.id.back);
		backView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		ImageButton logoHeaderView = (ImageButton) findViewById(R.id.logo_header);
		logoHeaderView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommUtils.makeToast(AboutActivity.this, "认真你就输了");
			}
		});

		ArrayList<Integer> avatars = new ArrayList<Integer>();
		for (int i = 1; i <= 54; i++) {
			avatars.add(getResources().getIdentifier(
					"ac_" + String.format("%02d", i), "drawable",
					getPackageName()));
		}
		Random random = new Random();
		ImageView avatarHeaderView = (ImageView) findViewById(R.id.avatar_header);
		avatarHeaderView.setImageResource(avatars.get(1 + random.nextInt(50)));

		TextView versionNameView = (TextView) findViewById(R.id.version_name);
		try {
			String versionName = getPackageManager().getPackageInfo(
					getPackageName(), 0).versionName;
			versionNameView.setText(versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		TextView weiboView = (TextView) findViewById(R.id.weibo);
		weiboView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("http://weibo.com/xuyangbill");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		TextView githubView = (TextView) findViewById(R.id.github);
		githubView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri
						.parse("https://github.com/xuyangbill/AcfunAlmanac");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);

			}
		});

		TextView acfunView = (TextView) findViewById(R.id.acfun);
		acfunView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommUtils.makeToast(AboutActivity.this, "认真你就输了");
			}
		});

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

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
