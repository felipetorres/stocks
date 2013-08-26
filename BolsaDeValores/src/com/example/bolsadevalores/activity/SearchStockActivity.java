package com.example.bolsadevalores.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.json.JSONSymbolSuggestObject.Suggestion;
import com.example.bolsadevalores.web.YahooSuggestionsConnector;

public class SearchStockActivity extends ActionBarActivity {

	private Suggestion selected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_stock);
		ListView resultList = (ListView) findViewById(R.id.search_result);
		
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);

		String stock = getIntent().getStringExtra("stock");
		new YahooSuggestionsConnector(this, resultList).execute(stock);
		
		resultList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				
				Suggestion suggestion = (Suggestion) adapter.getItemAtPosition(position);
				
				Intent details = new Intent(SearchStockActivity.this, DetailsActivity.class);
				details.putExtra("searched_stock", suggestion);
				startActivity(details);
			}
		});
		
		resultList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				
				selected = (Suggestion) adapter.getItemAtPosition(position);
				return false;
			}
		});
		
		registerForContextMenu(resultList);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_stock, menu);
		
		new OptionsMenuDelegator(this).withSearchView(menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return new OptionsMenuDelegator(this).select(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		MenuItem bookmark = menu.add("Adicionar aos favoritos");
		
		bookmark.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				SharedPreferencesAccessor shPref = new SharedPreferencesAccessor(SearchStockActivity.this);
				
				List<String> stocks = shPref.retrieveBookmarkedStocks();
				shPref.tryToBookmark(selected, stocks);
				
				return false;
			}
		});
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
}
