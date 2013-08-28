package com.example.bolsadevalores.task;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.joda.time.DateTime;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.bolsadevalores.adapter.CurrencyListAdapter;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.json.JSONCurrencyDeserializer;
import com.example.bolsadevalores.json.JSONCurrencyResponseObject;
import com.example.bolsadevalores.web.HttpConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class CurrencyTask extends AsyncTask<Object, Object, Map<String,Double>>{
	
	private Activity activity;
	private ListView listView;
	private ErrorHandler errorHandler;

	public CurrencyTask(Activity activity, ListView listView, ErrorHandler errorHandler) {
		this.activity = activity;
		this.listView = listView;
		this.errorHandler = errorHandler;
	}
	
	
	@Override
	protected Map<String, Double> doInBackground(Object... params) {
		
		DateTime dateTime = new DateTime().minusDays(1);
		String dateYesterday = dateTime.toString("yyyy-MM-dd");
		
		String url = "http://openexchangerates.org/api/latest.json?app_id=466305c65e584919bf441cb7dffcb32f";
		
		String yesterdayExch = "http://openexchangerates.org/api/historical/" 
									+ dateYesterday 
									+ ".json?app_id=466305c65e584919bf441cb7dffcb32f";
		
		HttpConnector httpConnector = new HttpConnector();
		
		try {
			String response = httpConnector.getTo(url);
			String responseYesterday = httpConnector.getTo(yesterdayExch);
			
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(
					new TypeToken<Map<String, String>>() {}.getType(),
					new JSONCurrencyDeserializer());
			Gson gson = builder.create();
			
			JSONCurrencyResponseObject today = gson.fromJson(response, JSONCurrencyResponseObject.class);
			JSONCurrencyResponseObject yesterday = gson.fromJson(responseYesterday, JSONCurrencyResponseObject.class);
			
			calculateChangeBetween(today,yesterday);
		
			return today.getRates();
			
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(Map<String, Double> result) {
		
		try {
			CurrencyListAdapter adapter = new CurrencyListAdapter(activity, result);
			listView.setAdapter(adapter);
		}catch (Exception e) {
			errorHandler.onError(e);
		}
		
	}

	private void calculateChangeBetween(JSONCurrencyResponseObject today,
			JSONCurrencyResponseObject yesterday) {

		Set<Entry<String,Double>> rates = today.getRates().entrySet();
		
		for (Entry<String, Double> entry : rates) {
			Double todayValue = entry.getValue();
			Double yesterdayValue = yesterday.getRates().get(entry.getKey());
			
			double change = todayValue.doubleValue() / yesterdayValue.doubleValue();
			today.getRates().put(entry.getKey(), (change - 1)*100);
			
		}
	}
}
