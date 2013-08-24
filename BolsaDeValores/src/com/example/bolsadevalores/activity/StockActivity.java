package com.example.bolsadevalores.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.json.JSONListResponseObject;
import com.example.bolsadevalores.json.JSONSingleResponseObject;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.task.StockTask;

public class StockActivity extends ActionBarActivity {
	
	Stock selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock);
	
	}
	
	protected void onResume() {
		super.onResume();
		
		GridView grid = (GridView) findViewById(R.id.gridView);
		
		SharedPreferencesAccessor accessor = new SharedPreferencesAccessor(this);
		
		List<String> bookmarkedStocks = accessor.retrieveBookmarkedStocks();
		String[] stocks = (String[]) bookmarkedStocks.toArray();

		if(bookmarkedStocks.size() == 2) {
			new StockTask(this, grid, JSONSingleResponseObject.class).execute(stocks);
		} else if (bookmarkedStocks.size() > 2) {
			new StockTask(this, grid, JSONListResponseObject.class).execute(stocks);
		}

		
		grid.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				
				selected = (Stock) adapter.getItemAtPosition(position);
				return false;
			}
		});
		
		registerForContextMenu(grid);
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuItem remove = menu.add("Remover");
		
		remove.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				new SharedPreferencesAccessor(StockActivity.this).removeFromBookmark(selected);
				return false;
			}
		});
		
	}
}
