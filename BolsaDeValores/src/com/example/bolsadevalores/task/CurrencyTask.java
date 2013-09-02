package com.example.bolsadevalores.task;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.bolsadevalores.adapter.CurrencyListAdapter;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.ProgressManager;
import com.example.bolsadevalores.json.JSONListResponseObject;
import com.example.bolsadevalores.json.JSONResponseObject;
import com.example.bolsadevalores.json.JSONSingleResponseObject;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.web.YahooWebConnector;
import com.google.gson.Gson;


public class CurrencyTask extends AsyncTask<String, Object, JSONResponseObject>{
	
	private Activity activity;
	private ListView listView;
	private ErrorHandler errorHandler;
	private ProgressManager progressManager;

	public CurrencyTask(Activity activity, ListView listView, ErrorHandler errorHandler) {
		this.activity = activity;
		this.listView = listView;
		this.errorHandler = errorHandler;
		this.progressManager = new ProgressManager(activity);
	}
	
	@Override
	protected void onPreExecute() {
		progressManager.show();
	}
	
	@Override
	protected JSONResponseObject doInBackground(String... params) {
		
		Class<? extends JSONResponseObject> clazz;
		
		List<String> currencySymbols = Arrays.asList(params);
		if(currencySymbols.size() <= 1) clazz = JSONSingleResponseObject.class;
		else clazz = JSONListResponseObject.class;
		
		try {
			String response = new YahooWebConnector(currencySymbols).connectToCurrencyUrl();
			return new Gson().fromJson(response, clazz);
		
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(JSONResponseObject result) {
		progressManager.hide();
		
		try {
			List<Stock> currencies = result.getStocks();
			CurrencyListAdapter adapter = new CurrencyListAdapter(activity, currencies);
			listView.setAdapter(adapter);
		}catch (Exception e) {
			errorHandler.onError(e);
		}
		
	}
}
