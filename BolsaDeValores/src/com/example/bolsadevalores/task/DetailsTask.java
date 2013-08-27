package com.example.bolsadevalores.task;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.json.JSONSingleResponseObject;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.web.YahooWebConnector;
import com.google.gson.Gson;

public class DetailsTask extends
		AsyncTask<String, Object, JSONSingleResponseObject> {

	private Activity activity;
	private Dialog dialog;

	public DetailsTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(activity);
		dialog.show();
	}

	@Override
	protected JSONSingleResponseObject doInBackground(String... params) {

		List<String> stockSymbols = Arrays.asList(params);

		String resposta = new YahooWebConnector(stockSymbols).connect();
		return new Gson().fromJson(resposta, JSONSingleResponseObject.class);
	}

	@Override
	protected void onPostExecute(JSONSingleResponseObject result) {

		dialog.dismiss();
		Stock stock = result.getStocks().get(0);
		
		TextView name = (TextView) activity.findViewById(R.id.details_name);
		TextView updatedAt = (TextView) activity.findViewById(R.id.details_updated_at);
		TextView dayValue = (TextView) activity.findViewById(R.id.details_day);
		TextView lastValue = (TextView) activity.findViewById(R.id.details_recent);
		TextView previousCloseValue = (TextView) activity.findViewById(R.id.details_previous_close);
		TextView openValue = (TextView) activity.findViewById(R.id.details_open);
		
		name.setText(stock.Name);
		updatedAt.setText(stock.getPrettyLastTradeDateAndTime());
		dayValue.setText(stock.PercentChange);
		lastValue.setText(stock.Ask);
		previousCloseValue.setText(stock.PreviousClose);
		openValue.setText(stock.Open);
	}

}
