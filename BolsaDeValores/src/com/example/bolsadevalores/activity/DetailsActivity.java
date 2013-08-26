package com.example.bolsadevalores.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.json.JSONSymbolSuggestObject.Suggestion;
import com.example.bolsadevalores.task.DetailsTask;

public class DetailsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		Suggestion stock = (Suggestion) getIntent().getSerializableExtra("searched_stock");
		String stockName = "";
		if(stock == null) {
			stockName = getIntent().getStringExtra("stock");
		} else {
			stockName = stock.toString();
		}
		
		new DetailsTask(this).execute(stockName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.details, menu);
		
		new OptionsMenuDelegator(this).withSearchView(menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return new OptionsMenuDelegator(this).select(item);
	}
}
