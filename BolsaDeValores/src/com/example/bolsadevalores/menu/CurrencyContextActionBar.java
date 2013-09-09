package com.example.bolsadevalores.menu;

import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.model.Bookmark;
import com.example.bolsadevalores.model.Currency;
import com.example.bolsadevalores.task.CurrencyTask;

public class CurrencyContextActionBar {
	
	private Activity activity;
	private Currency selected;
	private ListView currencyList;
	private ErrorHandler errorHandler;
	private View view;
	private Runnable ticker;
	private Handler handler = new Handler();
	private View layout;

	public CurrencyContextActionBar(Activity activity, View layout, ListView currencyList, ErrorHandler handler) {
		this.activity = activity;
		this.layout = layout;
		this.currencyList = currencyList;
		this.errorHandler = handler;
	}
	
	public CurrencyContextActionBar withSelected(Currency selected) {
		this.selected = selected;
		return this;
	}
	
	public CurrencyContextActionBar withSelectedView(View view) {
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

			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.getMenuInflater().inflate(R.menu.stock_context, menu);

				return true;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				if(item.getItemId() == R.id.stock_context_remove) {
					new SharedPreferencesAccessor(activity, Bookmark.CURRENCY).removeFromBookmark(selected);
					loadCurrenciesWithTicker();
					mode.finish();
					return true;
				}
				
				return false;
			}
		};
	}
	
	public void loadCurrenciesWithTicker() {
		SharedPreferencesAccessor accessor = new SharedPreferencesAccessor(activity, Bookmark.CURRENCY);

		List<String> bookmarkedStocks = accessor.retrieveBookmarked();

		if(bookmarkedStocks.size() >= 1) {
			final String[] currencies = (String[]) bookmarkedStocks.toArray();
			new CurrencyTask(activity, layout, currencyList, errorHandler, true).execute(currencies);
			
			ticker = new Runnable() {
				@Override
				public void run() {
					new CurrencyTask(activity, layout, currencyList, errorHandler, false).execute(currencies);
					handler.postDelayed(this, 30000);
				}
			};
		}
	}

	public void stopTicker() {
		handler.removeCallbacks(ticker);
	}
	
	public void startTicker() {
		handler.post(ticker);
	}
}
