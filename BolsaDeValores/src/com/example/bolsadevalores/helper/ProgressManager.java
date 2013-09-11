package com.example.bolsadevalores.helper;

import android.app.Activity;
import android.view.View;

import com.example.bolsadevalores.R;

public class ProgressManager {

	private View progress;
	
	public ProgressManager(Activity activity) {
		this.progress = activity.findViewById(R.id.progress);
	}

	public ProgressManager(View view) {
		this.progress = view.findViewById(R.id.progress);
	}
	
	public void show() {
		progress.setVisibility(View.VISIBLE);
	}
	
	public void hide() {
		progress.setVisibility(View.GONE);
	}
}
