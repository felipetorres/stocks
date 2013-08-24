package com.example.bolsadevalores.helper;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bolsadevalores.json.JSONSymbolSuggestObject.Suggestion;

public class SharedPreferencesAccessor {
	
	private Context context;

	public SharedPreferencesAccessor(Context context) {
		this.context = context;
	}

	public void tryToBookmark(Suggestion selected, List<String> stocks) {
		
		String selectedSymbol = selected.getSymbol();
		
		if(!stocks.contains(selectedSymbol)) {
			SharedPreferences settings = context.getSharedPreferences("bookmark", 0);
			SharedPreferences.Editor editor = settings.edit();
			
			String stockString = "";
			
			for (String string : stocks) {
				stockString += string + ",";
			}
			stockString += selectedSymbol;
			
			editor.putString("stocks", stockString);
			editor.commit();		
		}
	}

	public List<String> retrieveBookmarkedStocks() {
		SharedPreferences settings = context.getSharedPreferences("bookmark", 0);
		String stockString = settings.getString("stocks", "");
		String[] stocks = stockString.split(",");
		return Arrays.asList(stocks);
	}
}
