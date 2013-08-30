package com.example.bolsadevalores.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ErrorDialog;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.task.CurrencyTask;

public class CurrencyActivity extends ActionBarActivity implements ErrorHandler{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		ListView listView = (ListView) findViewById(R.id.currency_list);
		
		new CurrencyTask(this, listView, this).execute("BRL","EUR");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.currency, menu);
		
		new OptionsMenuDelegator(this).withSearchView(menu);
		
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
