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
import com.umeng.scrshot.adapter.UMAppAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.sensor.UMSensor.OnSensorListener;
import com.umeng.socialize.sensor.UMSensor.WhitchButton;
import com.umeng.socialize.sensor.controller.UMShakeService;
import com.umeng.socialize.sensor.controller.impl.UMShakeServiceFactory;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.update.UmengUpdateAgent;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AlmanacActivity extends Activity {

	private TextView itemDateCalendarView = null;
	private TextView itemSubdateCalendarView = null;
	private TextView itemSignCalendarView = null;

	private UMShakeService mShakeController = null;
	private UMSocialService mController = null;
	private UMAppAdapter appAdapter = null;
	private ArrayList<SHARE_MEDIA> platforms = new ArrayList<SHARE_MEDIA>();
	private Pair<ArrayList<TableItem>, ArrayList<TableItem>> tableItemArrayPair = new Pair<ArrayList<TableItem>, ArrayList<TableItem>>(
			new ArrayList<TableItem>(), new ArrayList<TableItem>());
	private TableItemAdapter goodTableItemAdapter = null;
	private TableItemAdapter badTableItemAdapter = null;
	private Calendar calendar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.almanac_activity);

		calendar = Calendar.getInstance(Locale.CHINA);
		initView();
		updateView();

		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

		mController = UMServiceFactory.getUMSocialService("com.umeng.share",
				RequestType.SOCIAL);
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		String appID = "wx4f7f32d56bb3ddb6";
		String contentUrl = "http://www.wandoujia.com/apps/com.nozomi.almanac";
		mController.getConfig().supportWXPlatform(this, appID, contentUrl);
		mController.getConfig()
				.supportWXCirclePlatform(this, appID, contentUrl);

		mShakeController = UMShakeServiceFactory
				.getShakeService("com.umeng.share");

		mShakeController.setShareContent("#Acfun黄历#");

		platforms.add(SHARE_MEDIA.SINA);
		platforms.add(SHARE_MEDIA.WEIXIN);
		platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE);

		appAdapter = new UMAppAdapter(AlmanacActivity.this);
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

		ArrayList<Integer> avatars = CommUtils.getAvatars(this);
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

		itemDateCalendarView = (TextView) findViewById(R.id.item_date_calendar);
		itemDateCalendarView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new DatePickerDialog(AlmanacActivity.this,
						new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								calendar.set(Calendar.YEAR, year);
								calendar.set(Calendar.MONTH, monthOfYear);
								calendar.set(Calendar.DATE, dayOfMonth);
								updateView();
							}
						}, calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DATE)).show();
			}
		});

		itemSignCalendarView = (TextView) findViewById(R.id.item_sign_calendar);

		itemSubdateCalendarView = (TextView) findViewById(R.id.item_subdate_calendar);

		ListView goodRightView = (ListView) findViewById(R.id.good_right);
		goodTableItemAdapter = new TableItemAdapter(this,
				tableItemArrayPair.first);
		goodRightView.setAdapter(goodTableItemAdapter);

		ListView badRightView = (ListView) findViewById(R.id.bad_right);
		badTableItemAdapter = new TableItemAdapter(this,
				tableItemArrayPair.second);
		badRightView.setAdapter(badTableItemAdapter);

		ImageView shakeView = (ImageView) findViewById(R.id.shake);
		Animation animation = new RotateAnimation(0, 45,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(400);
		animation.setRepeatMode(Animation.REVERSE);
		animation.setRepeatCount(Animation.INFINITE);
		shakeView.startAnimation(animation);

		shakeView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommUtils.makeToast(AlmanacActivity.this, "摇一摇");
			}
		});
	}

	private void updateView() {
		String[] dayOfWeek = { "日", "一", "二", "三", "四", "五", "六" };
		itemDateCalendarView.setText(calendar.get(Calendar.YEAR) + "年"
				+ (1 + calendar.get(Calendar.MONTH)) + "月"
				+ calendar.get(Calendar.DATE) + "日 星期"
				+ dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

		itemSubdateCalendarView
				.setText(LunarUtil.GetLunarDay(calendar.get(Calendar.YEAR),
						(1 + calendar.get(Calendar.MONTH)),
						calendar.get(Calendar.DATE)));

		final Pair<Long, String> fortunePair = CommUtils.getFortune(this,
				calendar);
		itemSignCalendarView.setText(fortunePair.second);
		itemSignCalendarView.setTextColor(Color.rgb(
				(int) (255 * (10 + fortunePair.first * 0.8) / 100), 51, 51));
		itemSignCalendarView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommUtils.makeToast(AlmanacActivity.this, "运势指数："
						+ fortunePair.first + "%");
			}
		});

		CommUtils.getTableItemArray(this, calendar, tableItemArrayPair);
		goodTableItemAdapter.notifyDataSetChanged();
		badTableItemAdapter.notifyDataSetChanged();
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

		mShakeController.registerShakeListender(AlmanacActivity.this,
				appAdapter, false, platforms, mSensorListener);

	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		mShakeController.unregisterShakeListener(AlmanacActivity.this);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
