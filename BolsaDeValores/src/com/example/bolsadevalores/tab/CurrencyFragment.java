package com.example.bolsadevalores.tab;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.activity.CurrencySearchActivity;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.menu.CurrencyContextActionBar;
import com.example.bolsadevalores.model.Currency;

public class CurrencyFragment extends Fragment implements WithTabName{

	private ListView currencyList;
	private ActionBarActivity activity;
	private View layout;
	private CurrencyContextActionBar contextActionBar;
	
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
		
		contextActionBar = new CurrencyContextActionBar(activity, layout, currencyList, (ErrorHandler) activity);
		
		return layout;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		setHasOptionsMenu(true);
		
		contextActionBar.loadCurrenciesWithTicker();
		
		currencyList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				
				Currency currency = (Currency) adapter.getItemAtPosition(position);
				Callback mode = contextActionBar.withSelected(currency).withSelectedView(view).build();
				activity.startSupportActionMode(mode);
				
				return true;
			}
		});
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.currency, menu);
		new OptionsMenuDelegator(activity).withSearchView(menu, CurrencySearchActivity.class);
		contextActionBar.startTicker();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		contextActionBar.stopTicker();
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
