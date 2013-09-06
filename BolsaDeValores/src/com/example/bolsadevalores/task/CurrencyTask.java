package com.example.bolsadevalores.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.example.bolsadevalores.adapter.CurrencyListAdapter;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.ProgressManager;
import com.example.bolsadevalores.model.Currency;
import com.example.bolsadevalores.web.YahooWebConnector;


public class CurrencyTask extends AsyncTask<String, Object, List<Currency>>{
	
	private Activity activity;
	private ListView listView;
	private ErrorHandler errorHandler;
	private ProgressManager progressManager;

	public CurrencyTask(Activity activity, View view, ListView listView, ErrorHandler errorHandler) {
		this.activity = activity;
		this.listView = listView;
		this.errorHandler = errorHandler;
		this.progressManager = new ProgressManager(view);
	}
	
	@Override
	protected void onPreExecute() {
		progressManager.show();
	}
	
	@Override
	protected List<Currency> doInBackground(String... params) {
		
		List<Currency> currencies = new ArrayList<Currency>();
		
		List<String> currencySymbols = Arrays.asList(params);
		
		try {
			for (String symbol : currencySymbols) {
				Elements responseElements = new YahooWebConnector().connectToCurrencyUrl(symbol);
				currencies.add(new Currency(responseElements));
			}
			return currencies;
		
		} catch (Exception e) {
			return null;
		} 
	}
	
	@Override
	protected void onPostExecute(List<Currency> currencies) {
		progressManager.hide();
		
		try {
			CurrencyListAdapter adapter = new CurrencyListAdapter(activity, currencies);
			listView.setAdapter(adapter);
		}catch (Exception e) {
			errorHandler.onError(e);
		}
		
	}
}
