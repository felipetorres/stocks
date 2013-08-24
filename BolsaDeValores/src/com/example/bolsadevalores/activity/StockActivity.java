package com.example.bolsadevalores.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.json.JSONListResponseObject;
import com.example.bolsadevalores.json.JSONSingleResponseObject;
import com.example.bolsadevalores.task.StockTask;

public class StockActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock);
	
	}
	
	protected void onResume() {
		super.onResume();
		
		SharedPreferencesAccessor accessor = new SharedPreferencesAccessor(this);
		
		List<String> bookmarkedStocks = accessor.retrieveBookmarkedStocks();
		String[] stocks = (String[]) bookmarkedStocks.toArray();

		if(bookmarkedStocks.size() == 2) {
			new StockTask(this, JSONSingleResponseObject.class).execute(stocks);
		} else if (bookmarkedStocks.size() > 2) {
			new StockTask(this, JSONListResponseObject.class).execute(stocks);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.stock, menu);
		
		new OptionsMenuDelegator(this).withSearchView(menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return new OptionsMenuDelegator(this).select(item);
	}
}
