package com.example.bolsadevalores.menu;

import java.util.List;

import android.app.Activity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.json.JSONListResponseObject;
import com.example.bolsadevalores.json.JSONSingleResponseObject;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.task.StockTask;

public class StockContextActionBar {
	
	private Activity activity;
	private Stock selected;
	private GridView grid;
	private ErrorHandler handler;
	private View view;

	public StockContextActionBar(Activity activity, GridView grid, ErrorHandler handler) {
		this.activity = activity;
		this.grid = grid;
		this.handler = handler;
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
					new SharedPreferencesAccessor(activity).removeFromBookmark(selected);
					loadStocks();
					mode.finish();
					return true;
				}
				
				return false;
			}
		};
	}
	
	public void loadStocks() {
		SharedPreferencesAccessor accessor = new SharedPreferencesAccessor(activity);

		List<String> bookmarkedStocks = accessor.retrieveBookmarkedStocks();
		String[] stocks = (String[]) bookmarkedStocks.toArray();

		if (bookmarkedStocks.size() == 2) {
			new StockTask(activity, grid, JSONSingleResponseObject.class, handler)
					.execute(stocks);
		} else if (bookmarkedStocks.size() > 2) {
			new StockTask(activity, grid, JSONListResponseObject.class, handler)
					.execute(stocks);
		}
	}

}
