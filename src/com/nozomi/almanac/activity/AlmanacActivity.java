package com.nozomi.almanac.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.nozomi.almanac.R;
import com.nozomi.almanac.model.ListItem;
import com.nozomi.almanac.model.TableItem;
import com.nozomi.almanac.util.LunarUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AlmanacActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initView() {

		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		String[] dayOfWeek = { "日", "一", "二", "三", "四", "五", "六" };

		TextView itemDateCalendarView = (TextView) findViewById(R.id.item_date_calendar);
		itemDateCalendarView.setText(calendar.get(Calendar.YEAR) + "年"
				+ (1 + calendar.get(Calendar.MONTH)) + "月"
				+ calendar.get(Calendar.DATE) + "日 星期"
				+ dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

		long seed = calendar.get(Calendar.YEAR) * 37621
				+ (calendar.get(Calendar.MONTH) + 1) * 539
				+ calendar.get(Calendar.DATE);

		TextView itemSignCalendarView = (TextView) findViewById(R.id.item_sign_calendar);
		f(itemSignCalendarView, seed);

		TextView itemSubdateCalendarView = (TextView) findViewById(R.id.item_subdate_calendar);
		itemSubdateCalendarView
				.setText(LunarUtil.GetLunarDay(calendar.get(Calendar.YEAR),
						(1 + calendar.get(Calendar.MONTH)),
						calendar.get(Calendar.DATE)));

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

		ArrayList<Integer> avatars = new ArrayList<Integer>();
		for (int i = 1; i <= 50; i++) {
			avatars.add(getResources().getIdentifier(
					"ac_" + String.format("%02d", i), "drawable",
					getPackageName()));
		}

		long sg = rnd(seed, 8) % 100;
		ArrayList<TableItem> goodTableItemArray = new ArrayList<TableItem>();
		for (long i = 0, l = rnd(seed, 9) % 3 + 2; i < l; i++) {
			int n = (int) (sg * 0.01 * list.size());
			ListItem a = list.get(n);
			int m = (int) (rnd(seed, (3 + i)) % 100 * 0.01 * avatars.size());
			// System.out.println(avatars.get(m) + ".gif " + a.getName() + " "
			// + a.getGood());
			goodTableItemArray.add(new TableItem(avatars.get(m), a.getName(), a
					.getGood()));
			list.remove(n);
			avatars.remove(m);
		}
		ListView goodRightView = (ListView) findViewById(R.id.good_right);
		TableItemAdapter goodTableItemAdapter = new TableItemAdapter(this,
				goodTableItemArray);
		goodRightView.setAdapter(goodTableItemAdapter);

		long sb = rnd(seed, 4) % 100;
		ArrayList<TableItem> badTableItemArray = new ArrayList<TableItem>();
		for (long i = 0, l = rnd(seed, 7) % 3 + 2; i < l; i++) {
			int n = (int) (sb * 0.01 * list.size());
			ListItem a = list.get(n);
			int m = (int) (rnd(seed, (2 + i)) % 100 * 0.01 * avatars.size());
			badTableItemArray.add(new TableItem(avatars.get(m), a.getName(), a
					.getBad()));
			list.remove(n);
			avatars.remove(m);
		}
		ListView badRightView = (ListView) findViewById(R.id.bad_right);
		TableItemAdapter badTableItemAdapter = new TableItemAdapter(this,
				badTableItemArray);
		badRightView.setAdapter(badTableItemAdapter);

	}

	private long rnd(long a, long b) {
		long n = a % 11117;
		for (long i = 0; i < 25 + b; i++) {
			n = n * n;
			n = n % 11117;
		}
		return n;
	}

	private void f(TextView itemSignCalendarView, long seed) {
		//
		final long n = rnd(seed * 624755, 6) % 100;
		String t = "末吉";
		// if
		if (n < 5) {
			// 5%
			t = "大凶";
		} else if (n < 20) {
			// 15%
			t = "凶";
		} else if (n < 50) {
			// 30%
			t = "末吉";
		} else if (n < 60) {
			// 10%
			t = "半吉";
		} else if (n < 70) {
			// 10%
			t = "吉";
		} else if (n < 80) {
			// 10%
			t = "小吉";
		} else if (n < 90) {
			// 10%
			t = "中吉";
		} else {
			// 5%
			t = "大吉";
		}

		itemSignCalendarView.setText(t);
		itemSignCalendarView.setTextColor(Color.rgb(
				(int) (255 * (10 + n * 0.8) / 100), 51, 51));
		itemSignCalendarView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(AlmanacActivity.this, "今日运势指数：" + n + "%", Toast.LENGTH_SHORT)
						.show();

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

				holder.cView = (ImageView) convertView.findViewById(R.id.c);
				holder.aView = (TextView) convertView.findViewById(R.id.a);
				holder.bView = (TextView) convertView.findViewById(R.id.b);
				convertView.setTag(holder);
			} else {
				holder = (GViewHolder) convertView.getTag();
			}

			TableItem tableItem = tableItemArray.get(position);
			holder.cView.setImageResource(tableItem.getC());
			holder.aView.setText(tableItem.getA());
			holder.bView.setText(tableItem.getB());

			return convertView;
		}

		private class GViewHolder {
			ImageView cView;
			TextView aView;
			TextView bView;
		}
	}

}
