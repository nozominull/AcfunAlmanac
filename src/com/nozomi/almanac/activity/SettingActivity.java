package com.nozomi.almanac.activity;

import java.util.ArrayList;
import java.util.Random;

import com.nozomi.almanac.R;
import com.nozomi.almanac.util.CommDef;
import com.nozomi.almanac.util.CommUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;

public class SettingActivity extends Activity {

	private TextView notificationTimeView;
	private UMSocialService mController = null;
	private SharedPreferences sp;
	private Editor editor;
	private boolean notificationState;
	private String notificationTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);

		sp = getSharedPreferences("acfun_almanac", Context.MODE_PRIVATE);
		editor = sp.edit();

		mController = UMServiceFactory.getUMSocialService("com.umeng.share",
				RequestType.SOCIAL);
		mController.getConfig().setSsoHandler(new SinaSsoHandler());

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
				CommUtils.makeToast(SettingActivity.this, "认真你就输了");
			}
		});

		ArrayList<Integer> avatars = CommUtils.getAvatars(this);
		Random random = new Random();
		ImageView avatarHeaderView = (ImageView) findViewById(R.id.avatar_header);
		avatarHeaderView.setImageResource(avatars.get(1 + random.nextInt(50)));

		TextView versionNameView = (TextView) findViewById(R.id.version_name);
		try {
			String versionName = getPackageManager().getPackageInfo(
					getPackageName(), 0).versionName;
			versionNameView.setText("当前版本: " + versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		RelativeLayout notificationStateLayoutView = (RelativeLayout) findViewById(R.id.notification_state_layout);
		final CheckBox notificationStateView = (CheckBox) findViewById(R.id.notification_state);
		final ImageView notificationDividerView = (ImageView) findViewById(R.id.notification_divider);
		final RelativeLayout notificationTimeLayoutView = (RelativeLayout) findViewById(R.id.notification_time_layout);

		notificationState = sp.getBoolean(CommDef.SP_NOTIFICATION_STATE, false);
		if (notificationState) {
			notificationStateView.setChecked(true);
			notificationDividerView.setVisibility(View.VISIBLE);
			notificationTimeLayoutView.setVisibility(View.VISIBLE);
		}

		notificationStateLayoutView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (notificationState) {
					notificationState = false;
					notificationStateView.setChecked(false);
					notificationDividerView.setVisibility(View.GONE);
					notificationTimeLayoutView.setVisibility(View.GONE);
				} else {
					notificationState = true;
					notificationStateView.setChecked(true);
					notificationDividerView.setVisibility(View.VISIBLE);
					notificationTimeLayoutView.setVisibility(View.VISIBLE);
				}
				editor.putBoolean(CommDef.SP_NOTIFICATION_STATE,
						notificationState);
				editor.commit();
				CommUtils.setAlarm(SettingActivity.this);
				CommUtils.makeToast(SettingActivity.this, "设置成功");
			}
		});

		notificationTime = sp.getString(CommDef.SP_NOTIFICATION_TIME, "09:00");
		notificationTimeView = (TextView) findViewById(R.id.notification_time);
		notificationTimeView.setText(notificationTime);

		notificationTimeLayoutView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				notificationTimeDialog();
			}
		});

		RelativeLayout checkUpdateLayoutView = (RelativeLayout) findViewById(R.id.check_update_layout);
		checkUpdateLayoutView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UmengUpdateAgent.setUpdateOnlyWifi(false);
				UmengUpdateAgent.update(SettingActivity.this);
				UmengUpdateAgent.setUpdateAutoPopup(false);
				UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

					@Override
					public void onUpdateReturned(int updateStatus,
							UpdateResponse updateInfo) {
						switch (updateStatus) {
						case 0:
							UmengUpdateAgent.showUpdateDialog(
									SettingActivity.this, updateInfo);
							break;
						case 1:
							CommUtils.makeToast(SettingActivity.this, "已经是最新版");
							break;
						case 3:
							CommUtils.makeToast(SettingActivity.this, "超时");
							break;
						}

					}
				});
			}
		});

		TextView feedbackView = (TextView) findViewById(R.id.feedback);
		feedbackView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mController.setShareContent("@xuyangbill ");
				mController.postShare(SettingActivity.this, SHARE_MEDIA.SINA,
						new SnsPostListener() {
							@Override
							public void onStart() {

							}

							@Override
							public void onComplete(SHARE_MEDIA platform,
									int eCode, SocializeEntity entity) {

							}
						});
			}
		});

		TextView aboutView = (TextView) findViewById(R.id.about);
		aboutView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this,
						AboutActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});
	}

	private void notificationTimeDialog() {
		String[] notificationTimeSplit = notificationTime.split(":");
		int hourOfDay = 9;
		int minute = 0;
		try {
			hourOfDay = Integer.valueOf(notificationTimeSplit[0]);
			minute = Integer.valueOf(notificationTimeSplit[1]);
		} catch (Exception e) {

		}
		new TimePickerDialog(this, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				notificationTime = "";
				if (hourOfDay < 10) {
					notificationTime += "0" + hourOfDay;
				} else {
					notificationTime += hourOfDay;
				}
				notificationTime += ":";
				if (minute < 10) {
					notificationTime += "0" + minute;
				} else {
					notificationTime += minute;
				}
				notificationTimeView.setText(notificationTime);
				editor.putString(CommDef.SP_NOTIFICATION_TIME, notificationTime);
				editor.commit();
				CommUtils.setAlarm(SettingActivity.this);
				CommUtils.makeToast(SettingActivity.this, "设置成功");
			}

		}, hourOfDay, minute, true).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */

		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
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
