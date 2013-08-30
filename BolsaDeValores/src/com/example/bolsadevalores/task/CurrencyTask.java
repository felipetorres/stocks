package com.example.bolsadevalores.task;

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
import com.example.bolsadevalores.web.HttpConnector;
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
		
		String CURRENCY = "";
		Class<? extends JSONResponseObject> clazz;
		
		for (String param : params) {
			CURRENCY += param + "%3Dx%22%2C%20%22";
		}
		
		String url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22"
				+ CURRENCY
				+ "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
		
		if(params.length <= 1) clazz = JSONSingleResponseObject.class;
		else clazz = JSONListResponseObject.class;
		
		HttpConnector httpConnector = new HttpConnector();
		
		try {
			String response = httpConnector.getTo(url);
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
