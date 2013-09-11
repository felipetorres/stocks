package com.example.bolsadevalores.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.view.View;

import com.example.bolsadevalores.helper.ProgressManager;
import com.example.bolsadevalores.model.Currency;
import com.example.bolsadevalores.model.interfaces.ResultHandler;
import com.example.bolsadevalores.web.YahooWebConnector;


public class CurrencyTask extends AsyncTask<String, Object, List<Currency>>{
	
	private ResultHandler resultHandler;
	private ProgressManager progressManager;
	private boolean withProgress;

	public CurrencyTask(ResultHandler resultHandler, View view, boolean withProgress) {
		this.resultHandler = resultHandler;
		this.withProgress = withProgress;
		this.progressManager = new ProgressManager(view);
	}
	
	@Override
	protected void onPreExecute() {
		if(withProgress) progressManager.show();
	}
	
	@Override
	protected List<Currency> doInBackground(String... params) {
		
		List<Currency> currencies = new ArrayList<Currency>();
		
		List<String> currencySymbols = Arrays.asList(params);
		
		try {
			for (String symbol : currencySymbols) {
				Elements responseElements = new YahooWebConnector().connectToCurrencyUrl(symbol);
				currencies.add(new Currency(symbol, responseElements));
			}
			return currencies;
		
		} catch (Exception e) {
			return null;
		} 
	}
	
	@Override
	protected void onPostExecute(List<Currency> currencies) {
		if(withProgress) progressManager.hide();
		
		resultHandler.updateWith(currencies);
	}
}
