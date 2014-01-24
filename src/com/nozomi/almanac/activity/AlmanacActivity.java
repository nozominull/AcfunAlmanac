package com.nozomi.almanac.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import com.nozomi.almanac.R;
import com.nozomi.almanac.model.TableItem;
import com.nozomi.almanac.util.CommUtils;
import com.nozomi.almanac.util.LunarUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.scrshot.UMScrShotController.OnScreenshotListener;
import com.umeng.scrshot.adapter.UMAppAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sensor.UMSensor.OnSensorListener;
import com.umeng.socialize.sensor.UMSensor.WhitchButton;
import com.umeng.socialize.sensor.controller.UMShakeService;
import com.umeng.socialize.sensor.controller.impl.UMShakeServiceFactory;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.update.UmengUpdateAgent;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AlmanacActivity extends Activity {

	private UMShakeService mShakeController;
	private UMSocialService mController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.almanac_activity);

		initView();

		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

		mController = UMServiceFactory.getUMSocialService("com.umeng.share",
				RequestType.SOCIAL);
		mController.getConfig().setSsoHandler(new SinaSsoHandler());

		mShakeController = UMShakeServiceFactory
				.getShakeService("share almanac");

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
				CommUtils.makeToast(AlmanacActivity.this, "认真你就输了");

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

		ImageButton settingView = (ImageButton) findViewById(R.id.setting);
		settingView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AlmanacActivity.this,
						SettingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});

		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		String[] dayOfWeek = { "日", "一", "二", "三", "四", "五", "六" };

		TextView itemDateCalendarView = (TextView) findViewById(R.id.item_date_calendar);
		itemDateCalendarView.setText(calendar.get(Calendar.YEAR) + "年"
				+ (1 + calendar.get(Calendar.MONTH)) + "月"
				+ calendar.get(Calendar.DATE) + "日 星期"
				+ dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

		TextView itemSignCalendarView = (TextView) findViewById(R.id.item_sign_calendar);
		final Pair<Long, String> fortunePair = CommUtils.getFortune(this);

		itemSignCalendarView.setText(fortunePair.second);
		itemSignCalendarView.setTextColor(Color.rgb(
				(int) (255 * (10 + fortunePair.first * 0.8) / 100), 51, 51));
		itemSignCalendarView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommUtils.makeToast(AlmanacActivity.this, "今日运势指数："
						+ fortunePair.first + "%");
			}
		});

		TextView itemSubdateCalendarView = (TextView) findViewById(R.id.item_subdate_calendar);
		itemSubdateCalendarView
				.setText(LunarUtil.GetLunarDay(calendar.get(Calendar.YEAR),
						(1 + calendar.get(Calendar.MONTH)),
						calendar.get(Calendar.DATE)));

		Pair<ArrayList<TableItem>, ArrayList<TableItem>> tableItemArrayPair = CommUtils
				.getTableItemArray(this);

		ListView goodRightView = (ListView) findViewById(R.id.good_right);
		TableItemAdapter goodTableItemAdapter = new TableItemAdapter(this,
				tableItemArrayPair.first);
		goodRightView.setAdapter(goodTableItemAdapter);

		ListView badRightView = (ListView) findViewById(R.id.bad_right);
		TableItemAdapter badTableItemAdapter = new TableItemAdapter(this,
				tableItemArrayPair.second);
		badRightView.setAdapter(badTableItemAdapter);

		ImageView shackView = (ImageView) findViewById(R.id.shake);
		RotateAnimation animation = new RotateAnimation(0, 45,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(400);
		animation.setRepeatMode(Animation.REVERSE);
		animation.setRepeatCount(Animation.INFINITE);
		shackView.startAnimation(animation);
		shackView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommUtils.makeToast(AlmanacActivity.this, "摇一摇");
			}
		});

	}

	private class TableItemAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<TableItem> tableItemArray;

		public TableItemAdapter(Context context,
				ArrayList<TableItem> tableItemArray) {
			this.context = context;
			this.tableItemArray = tableItemArray;
		}

		@Override
		public int getCount() {
			return tableItemArray.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final GViewHolder holder;
			if (convertView == null) {
				holder = new GViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.table_item, null, false);

				holder.avatarView = (ImageView) convertView
						.findViewById(R.id.avatar);
				holder.nameView = (TextView) convertView
						.findViewById(R.id.name);
				holder.contentView = (TextView) convertView
						.findViewById(R.id.content);
				convertView.setTag(holder);
			} else {
				holder = (GViewHolder) convertView.getTag();
			}

			TableItem tableItem = tableItemArray.get(position);
			holder.avatarView.setImageResource(tableItem.getAvatar());
			holder.nameView.setText(tableItem.getName());
			holder.contentView.setText(tableItem.getContent());

			return convertView;
		}

		private class GViewHolder {
			ImageView avatarView;
			TextView nameView;
			TextView contentView;
		}
	}

	private OnSensorListener mSensorListener = new OnSensorListener() {

		@Override
		public void onStart() {
		}

		/**
		 * 分享完成后回调
		 */
		@Override
		public void onComplete(SHARE_MEDIA platform, int eCode,
				SocializeEntity entity) {

		}

		/**
		 * @Description: 摇一摇动作完成后回调
		 */
		@Override
		public void onActionComplete(SensorEvent event) {
			// Toast.makeText(YourActivity.this, "用户摇一摇，可在这暂停游戏",
			// Toast.LENGTH_SHORT).show();
		}

		/**
		 * @Description: 用户点击分享窗口的取消和分享按钮触发的回调
		 * @param button
		 *            用户在分享窗口点击的按钮，有取消和分享两个按钮
		 */
		@Override
		public void onButtonClick(WhitchButton button) {
			if (button == WhitchButton.BUTTON_CANCEL) {
				// Toast.makeText(YourActivity.this, "取消分享,游戏重新开始",
				// Toast.LENGTH_SHORT).show();
			} else {
				// 分享中, ( 用户点击了分享按钮 )
			}
		}
	};

	private OnScreenshotListener mScrShotListener = new OnScreenshotListener() {
		@Override
		public void onComplete(Bitmap bmp) {
			if (null != bmp) {
				// 得到截图
				mController.setShareContent("#Acfun黄历#");
				mController
						.setShareImage(new UMImage(AlmanacActivity.this, bmp));
				mController.postShare(AlmanacActivity.this, SHARE_MEDIA.SINA,
						new SnsPostListener() {
							@Override
							public void onStart() {

							}

							@Override
							public void onComplete(SHARE_MEDIA platform,
									int eCode, SocializeEntity entity) {
								// if (eCode == 200) {
								// CommUtils.makeToast(AlmanacActivity.this,
								// "分享成功");
								// }
							}
						});

			}
		}
	};

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

		UMAppAdapter appAdapter = new UMAppAdapter(AlmanacActivity.this);
		// 配置摇一摇截屏分享时用户可选的平台，最多支持五个平台
		ArrayList<SHARE_MEDIA> platforms = new ArrayList<SHARE_MEDIA>();
		platforms.add(SHARE_MEDIA.SINA);
		// 设置摇一摇分享的文字内容
		mShakeController.setShareContent("#Acfun黄历#");
		// 注册摇一摇截屏分享功能,mSensorListener在2.1.2中定义
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		// {
		// mShakeController.registerShakeListender(AlmanacActivity.this,
		// appAdapter, false, platforms, mSensorListener);
		// } else {
		mShakeController.registerShakeToScrShot(AlmanacActivity.this,
				appAdapter, false, mScrShotListener);
		// }
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		// {
		mShakeController.unregisterShakeListener(AlmanacActivity.this);
		// }
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
