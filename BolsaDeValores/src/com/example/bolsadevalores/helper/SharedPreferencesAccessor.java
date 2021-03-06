package com.example.bolsadevalores.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bolsadevalores.model.Bookmark;
import com.example.bolsadevalores.model.Suggestion;
import com.example.bolsadevalores.model.interfaces.Bookmarkable;

public class SharedPreferencesAccessor {
	
	private Context context;
	private Bookmark bookmark;

	public SharedPreferencesAccessor(Context context, Bookmark bookmark) {
		this.context = context;
		this.bookmark = bookmark;
	}

	public void tryToBookmark(Suggestion selected, List<String> stocks) {
		
		String selectedSymbol = selected.getSymbol();
		
		if(!stocks.contains(selectedSymbol)) {
			String stockString = listToString(stocks);
			stockString += stocks.size() == 0 ? selectedSymbol : "," + selectedSymbol;
			
			writeToSharedPreferences(stockString);
					
		}
	}
	
	public void removeFromBookmark(Bookmarkable bookmarkable) {
		List<String> stocks = new ArrayList<String>();
		
		stocks.addAll(this.retrieveBookmarked());
		
		stocks.remove(bookmarkable.getSymbol());
		String string = listToString(stocks);
		writeToSharedPreferences(string);
	}
	
	public List<String> retrieveBookmarked() {
		SharedPreferences settings = context.getSharedPreferences("bookmark", 0);
		String stockString = settings.getString(bookmark.getType(), "");
		if(stockString != "") {
			String[] stocks = stockString.split(",");
			return Arrays.asList(stocks);
		}
		return new ArrayList<String>();
	}

	private void writeToSharedPreferences(String stockString) {
		SharedPreferences settings = context.getSharedPreferences("bookmark", 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString(bookmark.getType(), stockString);
		editor.commit();
	}

	private String listToString(List<String> stocks) {
		String stockString = "";
		
		if(stocks.size() >= 1) stockString += stocks.get(0);
		
		for(int i=1;i<stocks.size();i++) {
			stockString += "," + stocks.get(i);
		}
		
		return stockString;
	}
	
}
