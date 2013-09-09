package com.example.bolsadevalores.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.adapter.CurrencySearchAdapter;
import com.example.bolsadevalores.helper.ErrorDialog;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.model.Suggestion;
import com.example.bolsadevalores.task.SearchTask;

public class CurrencySearchActivity extends ActionBarActivity implements ErrorHandler{

	private ListView resultList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		resultList = (ListView) findViewById(R.id.search_result);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    
		String stock = getIntent().getStringExtra("query");
		new SearchTask(this, resultList, this, CurrencySearchAdapter.class).execute(stock);
		
		resultList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				
				Suggestion suggestion = (Suggestion) adapter.getItemAtPosition(position);
				
				Intent details = new Intent(CurrencySearchActivity.this, DetailsActivity.class);
				details.putExtra("searched_stock", suggestion);
				startActivity(details);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.currency_search, menu);
		
		new OptionsMenuDelegator(this).withSearchView(menu, CurrencySearchActivity.class);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return new OptionsMenuDelegator(this).select(item);
	}
	
	@Override
	public void onError(Exception ex) {
		new ErrorDialog(this).withText("Search eror").show();
	}
}
