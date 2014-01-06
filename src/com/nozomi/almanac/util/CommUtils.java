package com.nozomi.almanac.util;

import com.nozomi.almanac.R;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class CommUtils {

	private static Toast toast = null;

	public static void makeToast(Context context, String text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			View backgroundView  =toast.getView();
			backgroundView.setBackgroundResource(R.drawable.toast_background);
			toast.setView(backgroundView);
		} else {
			toast.setText(text);
		}
		toast.show();
	}


}
