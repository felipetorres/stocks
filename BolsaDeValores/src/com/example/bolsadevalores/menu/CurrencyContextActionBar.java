package com.example.bolsadevalores.menu;

import java.util.List;

import android.os.Handler;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.model.Bookmark;
import com.example.bolsadevalores.model.Currency;
import com.example.bolsadevalores.model.interfaces.ResultHandler;
import com.example.bolsadevalores.task.CurrencyTask;

public class CurrencyContextActionBar {
	
	private Currency selected;
	private View view;
	private Runnable ticker;
	private Handler handler = new Handler();
	private View layout;
	private ResultHandler resultHandler;

	public CurrencyContextActionBar(ResultHandler resultHandler, View layout) {
		this.resultHandler = resultHandler;
		this.layout = layout;
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
				view.setBackgroundResource(R.drawable.item_currency_background);
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				view.setBackgroundResource(R.drawable.item_currency_background_selected);
				mode.getMenuInflater().inflate(R.menu.stock_context, menu);

				return true;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				if(item.getItemId() == R.id.stock_context_remove) {
					new SharedPreferencesAccessor(resultHandler.getParent(), Bookmark.CURRENCY).removeFromBookmark(selected);
					loadCurrenciesWithTicker();
					mode.finish();
					return true;
				}
				
				return false;
			}
		};
	}
	
	public void loadCurrenciesWithTicker() {
		SharedPreferencesAccessor accessor = new SharedPreferencesAccessor(resultHandler.getParent(), Bookmark.CURRENCY);

		List<String> bookmarkedStocks = accessor.retrieveBookmarked();

		if(bookmarkedStocks.size() >= 1) {
			final String[] currencies = (String[]) bookmarkedStocks.toArray();
			new CurrencyTask(resultHandler, layout, true).execute(currencies);
			
			ticker = new Runnable() {
				@Override
				public void run() {
					new CurrencyTask(resultHandler, layout, false).execute(currencies);
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
