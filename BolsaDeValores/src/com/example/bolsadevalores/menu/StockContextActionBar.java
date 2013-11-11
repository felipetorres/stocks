package com.example.bolsadevalores.menu;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.model.Bookmark;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.model.interfaces.ResultHandler;
import com.example.bolsadevalores.task.StockTask;

public class StockContextActionBar {
	
	private ResultHandler resultHandler;
	private Stock selected;
	private View view;
	private Runnable ticker;
	private Handler handler = new Handler();
	private Activity activity;
	private View withProgress;
	private boolean running;

	public StockContextActionBar(ResultHandler resultHandler, View withProgress) {
		this.resultHandler = resultHandler;
		this.withProgress = withProgress;
		this.activity = resultHandler.getParent();
	}
	
	public StockContextActionBar withSelected(Stock selected) {
		this.selected = selected;
		return this;
	}
	
	public StockContextActionBar withSelectedView(View view) {
		this.view = view;
		return this;
	}

	public ActionMode.Callback build() {
		return new ActionMode.Callback() {

			@Override
			public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode arg0) {
				view.setBackgroundResource(R.drawable.item_stock_background);

			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				view.setBackgroundResource(R.drawable.item_stock_background_selected);
				mode.getMenuInflater().inflate(R.menu.stock_context, menu);

				return true;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				if(item.getItemId() == R.id.stock_context_remove) {
					new SharedPreferencesAccessor(activity, Bookmark.STOCK).removeFromBookmark(selected);
					loadStocksWithTicker();
					mode.finish();
					return true;
				}
				
				return false;
			}
		};
	}
	
	public void loadStocksWithTicker() {
		SharedPreferencesAccessor accessor = new SharedPreferencesAccessor(activity, Bookmark.STOCK);

		List<String> bookmarkedStocks = accessor.retrieveBookmarked();
		
		if(bookmarkedStocks.isEmpty()) {
			bookmarkedStocks = Arrays.asList(new String[] {
					"CIEL3.SA", "^BVSP", "PETR3.SA", "PETR4.SA",
					"VALE3.SA", "VALE5.SA", "BBDC3.SA", "BBDC4.SA"});
		}

		if(bookmarkedStocks.size() >= 1) {
			final String[] stocks = (String[]) bookmarkedStocks.toArray();
			new StockTask(resultHandler, withProgress, true).execute(stocks);
			
			ticker = new Runnable() {
				@Override
				public void run() {
					new StockTask(resultHandler, withProgress, false).execute(stocks);
					handler.postDelayed(this, 30000);
				}
			};
		}
		this.startTicker();
	}

	public void stopTicker() {
		handler.removeCallbacks(ticker);
		running = false;
	}
	
	public void startTicker() {
		if(!running) {
			handler.post(ticker);
			running = true;
		}
	}
}
