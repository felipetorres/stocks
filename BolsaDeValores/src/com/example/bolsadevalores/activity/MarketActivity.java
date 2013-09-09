package com.example.bolsadevalores.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ErrorDialog;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.TabHelper;

public class MarketActivity extends ActionBarActivity implements ErrorHandler {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_market);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    new TabHelper(this).addMarketTabsTo(actionBar).withSwipe(getSupportFragmentManager());
	}
	
	@Override
	public void onError(Exception ex) {
		ex.printStackTrace();
		new ErrorDialog(this).withText("Deu pau").show();
	}
}