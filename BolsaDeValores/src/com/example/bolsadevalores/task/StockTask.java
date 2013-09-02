package com.example.bolsadevalores.task;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.GridView;

import com.example.bolsadevalores.adapter.GridAdapter;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.ProgressManager;
import com.example.bolsadevalores.json.JSONListResponseObject;
import com.example.bolsadevalores.json.JSONResponseObject;
import com.example.bolsadevalores.json.JSONSingleResponseObject;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.web.YahooWebConnector;
import com.google.gson.Gson;

public class StockTask extends
		AsyncTask<String, Object, JSONResponseObject> {

	private Activity activity;
	private GridView grid;
	private ProgressManager progressManager;
	private ErrorHandler errorHandler;

	public StockTask(Activity activity, GridView grid, ErrorHandler errorHandler) {
		this.activity = activity;
		this.grid = grid;
		this.progressManager = new ProgressManager(activity);
		this.errorHandler = errorHandler;
	}

	@Override
	protected void onPreExecute() {
		progressManager.show();
	}

	@Override
	protected JSONResponseObject doInBackground(String... params) {

		Class<? extends JSONResponseObject> clazz;
		
		List<String> stockSymbols = Arrays.asList(params);
		
		if(stockSymbols.size() == 1) clazz = JSONSingleResponseObject.class;
		else clazz = JSONListResponseObject.class;

		JSONResponseObject newInstance = null;
		try {
			newInstance = clazz.newInstance();
			String resposta = new YahooWebConnector(stockSymbols).connectToStockUrl();
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
			e.printStackTrace();
			errorHandler.onError(e);
		}
	}
}
