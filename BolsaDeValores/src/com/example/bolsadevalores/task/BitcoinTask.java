package com.example.bolsadevalores.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.AsyncTask;
import android.view.View;

import com.example.bolsadevalores.helper.ProgressManager;
import com.example.bolsadevalores.model.Coin;
import com.example.bolsadevalores.model.interfaces.ResultHandler;
import com.example.bolsadevalores.web.WebConnector;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class BitcoinTask extends AsyncTask<String, Object, List<Coin>>{
	
	private ResultHandler resultHandler;
	private ProgressManager progressManager;
	private boolean withProgress;

	public BitcoinTask(ResultHandler resultHandler, View view, boolean withProgress) {
		this.resultHandler = resultHandler;
		this.withProgress = withProgress;
		this.progressManager = new ProgressManager(view);
	}
	
	@Override
	protected void onPreExecute() {
		if(withProgress) progressManager.show();
	}
	
	@Override
	protected List<Coin> doInBackground(String... params) {
		
		try {
			String bitcoinTrades = new WebConnector().connectToBitcoinUrl();
			Coin bitcoin = buildCoinFrom(bitcoinTrades, "BTC");
			
			String litecoinTrades = new WebConnector().connectToLitecoinUrl();
			Coin litecoin = buildCoinFrom(litecoinTrades, "LTC");
			
			return Arrays.asList(new Coin[]{bitcoin, litecoin});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Coin buildCoinFrom(String response, String coinName) {
		List<Coin> trades = new Gson().fromJson(response, new TypeToken<ArrayList<Coin>>(){}.getType());
		
		if(!trades.isEmpty()) {
			double open = trades.get(0).getPrice();
			Coin last = trades.get(trades.size()-1);
			double recent = last.getPrice();
			long lastDate = last.getDate();
			
			return new Coin(coinName, lastDate, recent, ((recent/open)-1)*100);
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(List<Coin> coins) {
		if(withProgress) progressManager.hide();
		
		if(!coins.isEmpty()) {	
			resultHandler.updateWith(coins);
		}
	}
}
