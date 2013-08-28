package com.example.bolsadevalores.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.bolsadevalores.R;

public class ErrorDialog {

	private Activity activity;
	private AlertDialog.Builder builder;

	public ErrorDialog(Activity activity) {
		this.activity = activity;
		initialize();
	}

	private void initialize() {
		builder = new AlertDialog.Builder(activity);
		builder.setPositiveButton(R.string.error_dialog_refresh,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent activityIntent = activity.getIntent();
						Bundle extras = activityIntent.getExtras();
						activity.finish();
						
						Intent intent = new Intent(activityIntent);
						intent.putExtras(extras);
						
						activity.startActivity(intent);
					}
				})
				.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
	}
	
	public Builder withMessage(String text) {
		builder.setMessage(text);
		return builder;
	}

	public Builder withText(String text) {
		builder.setTitle(text);
		return builder;
	}

	public void show() {
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
