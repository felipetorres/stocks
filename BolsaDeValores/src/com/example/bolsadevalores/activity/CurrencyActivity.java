package com.example.bolsadevalores.activity;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ErrorDialog;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.model.Bookmark;
import com.example.bolsadevalores.task.CurrencyTask;
import com.example.bolsadevalores.task.StockTask;

public class CurrencyActivity extends ActionBarActivity implements ErrorHandler{

	private ListView currencyList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		currencyList = (ListView) findViewById(R.id.currency_list);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		SharedPreferencesAccessor accessor = new SharedPreferencesAccessor(this, Bookmark.CURRENCY);
		List<String> bookmarkedCurrencies = accessor.retrieveBookmarked();
		
		if(bookmarkedCurrencies.size() >= 1) {
			String[] currencies = (String[]) bookmarkedCurrencies.toArray();
			
			new CurrencyTask(this, currencyList, this).execute(currencies);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.currency, menu);
		
		new OptionsMenuDelegator(this).withSearchView(menu, CurrencySearchActivity.class);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		new OptionsMenuDelegator(this).select(item);
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onError(Exception ex) {
		ex.printStackTrace();
		new ErrorDialog(this).withText("Currency error").show();
	}
	
}
