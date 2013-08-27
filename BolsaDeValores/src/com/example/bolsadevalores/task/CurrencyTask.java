package com.example.bolsadevalores.task;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.joda.time.DateTime;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bolsadevalores.json.JSONCurrencyDeserializer;
import com.example.bolsadevalores.json.JSONCurrencyResponseObject;
import com.example.bolsadevalores.web.HttpConnector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class CurrencyTask extends AsyncTask<Object, Object, String>{
	//http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22EURUSD%22%2C%22GBPUSD%22%2C%22USDBRL%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=
	
	@Override
	protected String doInBackground(Object... params) {
		
		DateTime dateTime = new DateTime().minusDays(1);
		String dateYesterday = dateTime.toString("yyyy-MM-dd");
		
		String url = "http://openexchangerates.org/api/latest.json?app_id=466305c65e584919bf441cb7dffcb32f";
		
		String yesterdayExch = "http://openexchangerates.org/api/historical/" 
									+ dateYesterday 
									+ ".json?app_id=466305c65e584919bf441cb7dffcb32f";
		
		HttpConnector httpConnector = new HttpConnector();
		
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
	
		return null;
	}

	private void calculateChangeBetween(JSONCurrencyResponseObject today,
			JSONCurrencyResponseObject yesterday) {

		Set<Entry<String,Double>> rates = today.getRates().entrySet();
		
		for (Entry<String, Double> entry : rates) {
			Double todayValue = entry.getValue();
			Double yesterdayValue = yesterday.getRates().get(entry.getKey());
			
			double change = todayValue.doubleValue() / yesterdayValue.doubleValue();
			today.getRates().put(entry.getKey(), (change - 1)*100);
			
			Log.i("BLA", entry.getKey() + " " + today.getRates().get(entry.getKey()));
		}
		
	}
}
