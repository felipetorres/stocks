package com.example.bolsadevalores.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.helper.TabHelper;

public class MyFeedActivity extends ActionBarActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_feed);
		
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    new TabHelper(this).addTabsTo(actionBar);
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.feed, menu);
		
		new OptionsMenuDelegator(this).withSearchView(menu, StockSearchActivity.class);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return new OptionsMenuDelegator(this).select(item);
	}
	
}
