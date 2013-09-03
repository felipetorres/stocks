package com.example.bolsadevalores.menu;

import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.model.Bookmark;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.task.StockTask;

public class StockContextActionBar {
	
	private Activity activity;
	private Stock selected;
	private GridView grid;
	private ErrorHandler errorHandler;
	private View view;

	public StockContextActionBar(Activity activity, GridView grid, ErrorHandler handler) {
		this.activity = activity;
		this.grid = grid;
		this.errorHandler = handler;
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
				view.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.back_border));

			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				view.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.back_border_selected));
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

		if(bookmarkedStocks.size() >= 1) {
			final String[] stocks = (String[]) bookmarkedStocks.toArray();
			new StockTask(activity, grid, errorHandler, true).execute(stocks);
			
			final Handler handler = new Handler();
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					new StockTask(activity, grid, errorHandler, false).execute(stocks);
					handler.postDelayed(this, 30000);
				}
			});
		}
	}

}
