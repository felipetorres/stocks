package com.example.bolsadevalores.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class FeedTask extends AsyncTask<Object, Object, String> {
	
	private Context context;
	private ProgressDialog progressDialog;

	public FeedTask(Context context) {
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(context);
		progressDialog.show();
	}

	@Override
	protected String doInBackground(Object... params) {

		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		progressDialog.dismiss();
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
	}
}
