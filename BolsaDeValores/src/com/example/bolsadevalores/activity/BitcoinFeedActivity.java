package com.example.bolsadevalores.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.TabHelper;

public class BitcoinFeedActivity extends ActionBarActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_market);
		
        ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    new TabHelper(this).addBitcoinFeedTabTo(actionBar).withSwipe(getSupportFragmentManager());
	    
	}
}
