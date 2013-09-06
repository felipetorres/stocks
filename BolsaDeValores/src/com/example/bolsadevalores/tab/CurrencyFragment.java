package com.example.bolsadevalores.tab;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.activity.CurrencySearchActivity;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.model.Bookmark;
import com.example.bolsadevalores.task.CurrencyTask;

public class CurrencyFragment extends Fragment implements WithTabName{

	private ListView currencyList;
	private ActionBarActivity activity;
	private View layout;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (ActionBarActivity) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		layout = inflater.inflate(R.layout.fragment_currency, null);
		currencyList = (ListView) layout.findViewById(R.id.currency_list);
		
		return layout;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setHasOptionsMenu(true);
		
		SharedPreferencesAccessor accessor = new SharedPreferencesAccessor(activity, Bookmark.CURRENCY);
		List<String> bookmarkedCurrencies = accessor.retrieveBookmarked();
		
		if(bookmarkedCurrencies.size() >= 1) {
			String[] currencies = (String[]) bookmarkedCurrencies.toArray();
			
			new CurrencyTask(activity, layout, currencyList,(ErrorHandler) activity).execute(currencies);
		}
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.currency, menu);
		new OptionsMenuDelegator(activity).withSearchView(menu, CurrencySearchActivity.class);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return new OptionsMenuDelegator(activity).select(item);
	}

	@Override
	public String toString() {
		return "Currencies";
	}
}
