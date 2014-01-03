package com.nozomi.almanac.activity;

import com.nozomi.almanac.R;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);

		initView();
	}

	private void initView() {
		LinearLayout githubView = (LinearLayout) findViewById(R.id.github);
		githubView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri
						.parse("https://github.com/xuyangbill/AcfunAlmanac");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);

			}
		});
		
		TextView githubTextView  = (TextView) findViewById(R.id.github_text);
		githubTextView.getPaint().setUnderlineText(true);

		LinearLayout weiboView = (LinearLayout) findViewById(R.id.weibo);
		weiboView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("http://weibo.com/xuyangbill");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		
		
		TextView weiboTextView  = (TextView) findViewById(R.id.weibo_text);
		weiboTextView.getPaint().setUnderlineText(true);

	}

}
