package com.example.bolsadevalores.task;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.adapter.GridAdapter;
import com.example.bolsadevalores.json.JSONResponseObject;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.web.YahooWebConnector;
import com.google.gson.Gson;

public class StockTask extends
		AsyncTask<String, Object, JSONResponseObject> {

	private Activity activity;
	private Dialog dialog;
	private Class<? extends JSONResponseObject> clazz;

	public StockTask(Activity activity, Class<? extends JSONResponseObject> clazz) {
		this.activity = activity;
		this.clazz = clazz;
	}

	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(activity);
		dialog.show();
	}

	@Override
	protected JSONResponseObject doInBackground(String... params) {

		List<String> stockSymbols = Arrays.asList(params);

		String resposta = new YahooWebConnector(stockSymbols).conecta();
		return new Gson().fromJson(resposta, clazz);
	}

	@Override
	protected void onPostExecute(JSONResponseObject result) {

		dialog.dismiss();
		
		List<Stock> stocks = result.getStocks();
		
		if (stocks != null) {
			GridAdapter adapter = new GridAdapter(activity, stocks);
			GridView grid = (GridView) activity.findViewById(R.id.gridView);
			grid.setAdapter(adapter);
		} else {
			Toast.makeText(activity, "YAHOO tá ramelando", Toast.LENGTH_LONG).show();
		}
	}

}
