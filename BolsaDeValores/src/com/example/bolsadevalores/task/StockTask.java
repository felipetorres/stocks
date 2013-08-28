package com.example.bolsadevalores.task;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.GridView;

import com.example.bolsadevalores.adapter.GridAdapter;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.ProgressManager;
import com.example.bolsadevalores.json.JSONResponseObject;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.web.YahooWebConnector;
import com.google.gson.Gson;

public class StockTask extends
		AsyncTask<String, Object, JSONResponseObject> {

	private Activity activity;
	private Class<? extends JSONResponseObject> clazz;
	private GridView grid;
	private ProgressManager progressManager;
	private ErrorHandler errorHandler;

	public StockTask(Activity activity, GridView grid, Class<? extends JSONResponseObject> clazz, ErrorHandler errorHandler) {
		this.activity = activity;
		this.grid = grid;
		this.clazz = clazz;
		this.progressManager = new ProgressManager(activity);
		this.errorHandler = errorHandler;
	}

	@Override
	protected void onPreExecute() {
		progressManager.show();
	}

	@Override
	protected JSONResponseObject doInBackground(String... params) {

		List<String> stockSymbols = Arrays.asList(params);

		String resposta;
		JSONResponseObject newInstance = null;
		try {
			newInstance = clazz.newInstance();
			resposta = new YahooWebConnector(stockSymbols).connect();
			return new Gson().fromJson(resposta, clazz);
		} catch (Exception e) {
			return newInstance;
		}
	}

	@Override
	protected void onPostExecute(JSONResponseObject result) {

		progressManager.hide();

		try {
			List<Stock> stocks = result.getStocks();
			GridAdapter adapter = new GridAdapter(activity, stocks);
			grid.setAdapter(adapter);
		} catch(Exception e) {
			errorHandler.onError(e);
		}
	}
}
