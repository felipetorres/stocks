package com.example.bolsadevalores.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bolsadevalores.json.JSONSymbolSuggestObject.Suggestion;
import com.example.bolsadevalores.model.Stock;

public class SharedPreferencesAccessor {
	
	private Context context;

	public SharedPreferencesAccessor(Context context) {
		this.context = context;
	}

	public void tryToBookmark(Suggestion selected, List<String> stocks) {
		
		String selectedSymbol = selected.getSymbol();
		
		if(!stocks.contains(selectedSymbol)) {
			String stockString = listToString(stocks);
			stockString += selectedSymbol;
			
			writeToSharedPreferences(stockString);
					
		}
	}
	
	public void removeFromBookmark(Stock stock) {
		List<String> stocks = new ArrayList<String>();
		
		stocks.addAll(this.retrieveBookmarkedStocks());
		
		stocks.remove(stock.Symbol);
		String string = listToString(stocks);
		writeToSharedPreferences(string);
		
	}
	
	public List<String> retrieveBookmarkedStocks() {
		SharedPreferences settings = context.getSharedPreferences("bookmark", 0);
		String stockString = settings.getString("stocks", "");
		String[] stocks = stockString.split(",");
		return Arrays.asList(stocks);
	}

	private void writeToSharedPreferences(String stockString) {
		SharedPreferences settings = context.getSharedPreferences("bookmark", 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString("stocks", stockString);
		editor.commit();
	}

	private String listToString(List<String> stocks) {
		String stockString = "";
		
		for (String string : stocks) {
			stockString += string + ",";
		}
		return stockString;
	}
	
}
