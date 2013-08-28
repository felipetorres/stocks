package com.example.bolsadevalores.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ErrorDialog;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.menu.StockContextActionBar;
import com.example.bolsadevalores.model.Stock;

public class StockActivity extends ActionBarActivity implements ErrorHandler {

	private Stock selected;
	private GridView grid;
	private StockContextActionBar customActionMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock);
		
		grid = (GridView) findViewById(R.id.gridView);
		
		customActionMode = new StockContextActionBar(this, grid, this);
	}

	protected void onResume() {
		super.onResume();
		
		customActionMode.loadStocks();
		
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {

				Stock stock = (Stock) adapter.getItemAtPosition(position);
				Intent details = new Intent(StockActivity.this,
						DetailsActivity.class);
				details.putExtra("stock", stock.Symbol);
				startActivity(details);
			}

		});

		grid.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				
				selected = (Stock) adapter.getItemAtPosition(position);
				Callback mode = customActionMode.withSelected(selected).withSelectedView(view).build();
				StockActivity.this.startSupportActionMode(mode);

				return true;
			}
		});

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
	public void onError(Exception ex) {
		new ErrorDialog(this).withText("Deu pau").show();
	}
}