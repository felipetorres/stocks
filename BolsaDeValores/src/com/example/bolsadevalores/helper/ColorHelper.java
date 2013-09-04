package com.example.bolsadevalores.helper;

import android.app.Activity;
import android.widget.TextView;

import com.example.bolsadevalores.R;

public class ColorHelper {
	
	private Activity activity;

	public ColorHelper(Activity activity) {
		this.activity = activity;
	}

	public void setBackgroundColorTo(TextView percentChange, String change) {
		if(change.startsWith("-")) {
			percentChange.setBackgroundColor(activity.getResources().getColor(R.color.negativo));
		} else if(change.startsWith("+")) {
			percentChange.setBackgroundColor(activity.getResources().getColor(R.color.positivo));
		} else {
			percentChange.setBackgroundColor(activity.getResources().getColor(R.color.neutro));
		}
	}
	
	public void setTextColorTo(TextView percentChange, String change) {
		if(change.startsWith("-")) {
			percentChange.setTextColor(activity.getResources().getColor(R.color.negativo));
		} else if(change.startsWith("+")) {
			percentChange.setTextColor(activity.getResources().getColor(R.color.positivo));
		} else {
			percentChange.setTextColor(activity.getResources().getColor(R.color.neutro));
		}
	}
}
