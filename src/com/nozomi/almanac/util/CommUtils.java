package com.nozomi.almanac.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.nozomi.almanac.R;
import com.nozomi.almanac.activity.AlarmReceiver;
import com.nozomi.almanac.activity.SplashActivity;
import com.nozomi.almanac.activity.WidgetAlermReceiver;
import com.nozomi.almanac.model.ListItem;
import com.nozomi.almanac.model.TableItem;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

public class CommUtils {

	private static Toast toast = null;
	private static ArrayList<Integer> avatars = null;

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

	public static void makeNotification(Context context, String title,
			String text) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(context, SplashActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = title + ";" + text;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// 设置通知显示的参数
		notification.setLatestEventInfo(context, title, text, pendingIntent);
		notificationManager.notify(0, notification);
	}

	public static ArrayList<Integer> getAvatars(Context context) {
		if (avatars == null) {
			avatars = new ArrayList<Integer>();
			for (int i = 1; i <= 54; i++) {
				avatars.add(context.getResources().getIdentifier(
						"ac_" + String.format("%02d", i), "drawable",
						context.getPackageName()));
			}
		}
		return avatars;
	}

	public static void getTableItemArray(Context context, Calendar calendar,
			Pair<ArrayList<TableItem>, ArrayList<TableItem>> tableItemArrayPair) {
		ArrayList<Integer> avatars = new ArrayList<Integer>();
		for (int i = 1; i <= 50; i++) {
			avatars.add(context.getResources().getIdentifier(
					"ac_" + String.format("%02d", i), "drawable",
					context.getPackageName()));
		}

		long seed = calendar.get(Calendar.YEAR) * 37621
				+ (calendar.get(Calendar.MONTH) + 1) * 539
				+ calendar.get(Calendar.DATE);

		ArrayList<ListItem> list = new ArrayList<ListItem>();
		list.add(new ListItem("看AV", "释放压力，重铸自我", "会被家人撞到"));
		list.add(new ListItem("组模型", "今天的喷漆会很完美", "精神不集中板件被剪断了"));
		list.add(new ListItem("投稿情感区", "问题圆满解决", "会被当事人发现"));
		list.add(new ListItem("逛匿名版", "今天也要兵库北", "看到丧尸在晒妹"));
		list.add(new ListItem("和女神聊天", "女神好感度上升", "我去洗澡了，呵呵"));
		list.add(new ListItem("啪啪啪", "啪啪啪啪啪啪啪", "会卡在里面"));
		list.add(new ListItem("熬夜", "夜间的效率更高", "明天有很重要的事"));
		list.add(new ListItem("锻炼", "八分钟给你比利般的身材", "会拉伤肌肉"));
		list.add(new ListItem("散步", "遇到妹子主动搭讪", "走路会踩到水坑"));
		list.add(new ListItem("打排位赛", "遇到大腿上分500", "我方三人挂机"));
		list.add(new ListItem("汇报工作", "被夸奖工作认真", "上班偷玩游戏被扣工资"));
		list.add(new ListItem("抚摸猫咪", "才不是特意蹭你的呢", "死开！愚蠢的人类"));
		list.add(new ListItem("遛狗", "遇见女神遛狗搭讪", "狗狗随地大小便被罚款"));
		list.add(new ListItem("烹饪", "黑暗料理界就由我来打败", "难道这就是……仰望星空派？"));
		list.add(new ListItem("告白", "其实我也喜欢你好久了", "对不起，你是一个好人"));
		list.add(new ListItem("求站内信", "最新种子入手", "收到有码葫芦娃"));
		list.add(new ListItem("追新番", "完结之前我绝不会死", "会被剧透"));
		list.add(new ListItem("打卡日常", "怒回首页", "会被老板发现"));
		list.add(new ListItem("下副本", "配合默契一次通过", "会被灭到散团"));
		list.add(new ListItem("抢沙发", "沙发入手弹无虚发", "会被挂起来羞耻play"));
		list.add(new ListItem("网购", "商品大减价", "问题产品需要退换"));
		list.add(new ListItem("跳槽", "新工作待遇大幅提升", "再忍一忍就加薪了"));
		list.add(new ListItem("读书", "知识就是力量", "注意力完全无法集中"));
		list.add(new ListItem("早睡", "早睡早起方能养生", "会在半夜醒来，然后失眠"));
		list.add(new ListItem("逛街", "物美价廉大优惠", "会遇到奸商"));

		long sg = rnd(seed, 8) % 100;
		tableItemArrayPair.first.clear();
		for (long i = 0, l = rnd(seed, 9) % 3 + 2; i < l; i++) {
			int n = (int) (sg * 0.01 * list.size());
			ListItem a = list.get(n);
			int m = (int) (rnd(seed, (3 + i)) % 100 * 0.01 * avatars.size());
			tableItemArrayPair.first.add(new TableItem(avatars.get(m), a
					.getName(), a.getGood()));
			list.remove(n);
			avatars.remove(m);
		}

		long sb = rnd(seed, 4) % 100;
		tableItemArrayPair.second.clear();
		for (long i = 0, l = rnd(seed, 7) % 3 + 2; i < l; i++) {
			int n = (int) (sb * 0.01 * list.size());
			ListItem a = list.get(n);
			int m = (int) (rnd(seed, (2 + i)) % 100 * 0.01 * avatars.size());
			tableItemArrayPair.second.add(new TableItem(avatars.get(m), a
					.getName(), a.getBad()));
			list.remove(n);
			avatars.remove(m);
		}
	}

	public static Pair<Long, String> getFortune(Context context,
			Calendar calendar) {
		long seed = calendar.get(Calendar.YEAR) * 37621
				+ (calendar.get(Calendar.MONTH) + 1) * 539
				+ calendar.get(Calendar.DATE);


		SharedPreferences sp = context.getSharedPreferences("acfun_almanac",
				Context.MODE_PRIVATE);
		// A站用的是uid，这里用时间戳代替
		int uid = sp.getInt(CommDef.SP_UID, 0);
		if (uid == 0) {
			Editor editor = sp.edit();
			uid = (int) (System.currentTimeMillis() % 1000000);
			editor.putInt(CommDef.SP_UID, uid);
			editor.commit();
		}
		//uid = 624755;

		long fortune = rnd(seed * uid, 6) % 100;
		String fortuneLevel = "末吉";
		// if
		if (fortune < 5) {
			// 5%
			fortuneLevel = "大凶";
		} else if (fortune < 20) {
			// 15%
			fortuneLevel = "凶";
		} else if (fortune < 50) {
			// 30%
			fortuneLevel = "末吉";
		} else if (fortune < 60) {
			// 10%
			fortuneLevel = "半吉";
		} else if (fortune < 70) {
			// 10%
			fortuneLevel = "吉";
		} else if (fortune < 80) {
			// 10%
			fortuneLevel = "小吉";
		} else if (fortune < 90) {
			// 10%
			fortuneLevel = "中吉";
		} else {
			// 5%
			fortuneLevel = "大吉";
		}
		return new Pair<Long, String>(fortune, fortuneLevel);
	}

	private static long rnd(long a, long b) {
		long n = a % 11117;
		for (long i = 0; i < 25 + b; i++) {
			n = n * n;
			n = n % 11117;
		}
		return n;
	}

	public static void setAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1,
				intent, 0);
		am.cancel(pendingIntent);

		SharedPreferences sp = context.getSharedPreferences("acfun_almanac",
				Context.MODE_PRIVATE);
		boolean notificationState = sp.getBoolean(
				CommDef.SP_NOTIFICATION_STATE, false);
		if (notificationState) {
			String notificationTime = sp.getString(
					CommDef.SP_NOTIFICATION_TIME, "09:00");
			String[] notificationTimeSplit = notificationTime.split(":");
			int hourOfDay = 9;
			int minute = 0;
			try {
				hourOfDay = Integer.valueOf(notificationTimeSplit[0]);
				minute = Integer.valueOf(notificationTimeSplit[1]);
			} catch (Exception e) {

			}
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
				calendar.add(Calendar.DATE, 1);
			}
			am.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), (24 * 60 * 60 * 1000),
					pendingIntent);
		}
	}

	public static void setWidgetAlerm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(context, WidgetAlermReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2,
				intent, 0);
		am.cancel(pendingIntent);

		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
			calendar.add(Calendar.DATE, 1);
		}
		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				(24 * 60 * 60 * 1000), pendingIntent);

	}
}
