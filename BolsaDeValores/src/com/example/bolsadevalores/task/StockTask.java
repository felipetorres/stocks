package com.example.bolsadevalores.task;

import java.util.Arrays;
import java.util.List;

import android.os.AsyncTask;
import android.view.View;

import com.example.bolsadevalores.helper.ProgressManager;
import com.example.bolsadevalores.json.JSONListResponseObject;
import com.example.bolsadevalores.json.JSONResponseObject;
import com.example.bolsadevalores.json.JSONSingleResponseObject;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.model.interfaces.ResultHandler;
import com.example.bolsadevalores.web.WebConnector;
import com.google.gson.Gson;

public class StockTask extends
		AsyncTask<String, Object, JSONResponseObject> {

	private ProgressManager progressManager;
	private boolean shouldShowProgress;
	private ResultHandler resultHandler;

	public StockTask(ResultHandler resultHandler, View progress, boolean shouldShowProgress) {
		this.resultHandler = resultHandler;
		this.progressManager = new ProgressManager(progress);
		this.shouldShowProgress = shouldShowProgress;
	}

	@Override
	protected void onPreExecute() {
		if(shouldShowProgress) progressManager.show();
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
			String resposta = new WebConnector().connectToStockUrl(stockSymbols);
			return new Gson().fromJson(resposta, clazz);
		} catch (Exception e) {
			return newInstance;
		}
	}

	@Override
	protected void onPostExecute(JSONResponseObject result) {
		if(shouldShowProgress) progressManager.hide();
		
		List<Stock> stocks = result.getStocks();
		if(!stocks.isEmpty()) resultHandler.updateWith(stocks);
	}
}
