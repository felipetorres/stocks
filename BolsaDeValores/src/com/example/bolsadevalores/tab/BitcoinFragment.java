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
import com.example.bolsadevalores.adapter.BitcoinListAdapter;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.menu.BitcoinContextActionBar;
import com.example.bolsadevalores.model.interfaces.ResponseElement;
import com.example.bolsadevalores.model.interfaces.ResultHandler;

public class BitcoinFragment extends Fragment implements WithTabName, ResultHandler{

	private ListView currencyList;
	private ActionBarActivity activity;
	private ErrorHandler errorHandler;
	private View layout;
	private BitcoinContextActionBar bitcoinActionBar;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (ActionBarActivity) activity;
		this.errorHandler = (ErrorHandler) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		layout = inflater.inflate(R.layout.fragment_currency, null);
		currencyList = (ListView) layout.findViewById(R.id.currency_list);
		
		bitcoinActionBar = new BitcoinContextActionBar(this, layout);
		
		return layout;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		setHasOptionsMenu(true);
		
		bitcoinActionBar.loadBitcoinsWithTicker();
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if(bitcoinActionBar != null) {
			if(isVisibleToUser) bitcoinActionBar.startTicker();
			else bitcoinActionBar.stopTicker();
		}
	}
	
	@Override
	public String toString() {
		return "Bitcoin";
	}

	@Override
	public void updateWith(List<? extends ResponseElement> bitcoins) {
		try {
			if(bitcoins != null) {
				BitcoinListAdapter adapter = new BitcoinListAdapter(activity, bitcoins);
				currencyList.setAdapter(adapter);
			}
		}catch (Exception e) {
			errorHandler.onError(e);
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.bitcoin, menu);
		new OptionsMenuDelegator(activity);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return new OptionsMenuDelegator(activity).select(item);
	}

	@Override
	public Activity getParent() {
		return activity;
	}
}