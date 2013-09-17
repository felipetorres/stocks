package com.example.bolsadevalores.tab;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.activity.DetailsActivity;
import com.example.bolsadevalores.activity.StockSearchActivity;
import com.example.bolsadevalores.adapter.GridAdapter;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.menu.StockContextActionBar;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.model.interfaces.ResponseElement;
import com.example.bolsadevalores.model.interfaces.ResultHandler;

public class StockFragment extends Fragment implements WithTabName, ResultHandler{
	
	private Stock selected;
	private GridView grid;
	private StockContextActionBar customActionMode;
	private ActionBarActivity activity;
	private ErrorHandler errorHandler;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (ActionBarActivity) activity;
		this.errorHandler = (ErrorHandler) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_stock, null);
		
		grid = (GridView) layout.findViewById(R.id.gridView);
		
		customActionMode = new StockContextActionBar(this, layout);
		
		return layout;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		setHasOptionsMenu(true);
		
		customActionMode.loadStocksWithTicker();
		
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {

				Stock stock = (Stock) adapter.getItemAtPosition(position);
				Intent details = new Intent(getActivity(), DetailsActivity.class);
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
				activity.startSupportActionMode(mode);

				return true;
			}
		});
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.stock, menu);
		new OptionsMenuDelegator(activity).withSearchView(menu, StockSearchActivity.class);
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if (customActionMode != null) {
			if(isVisibleToUser) customActionMode.startTicker();
			else customActionMode.stopTicker();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return new OptionsMenuDelegator(activity).select(item);
	}

	@Override
	public String toString() {
		return "Stocks";
	}

	@Override
	public void updateWith(List<? extends ResponseElement> stocks) {
		
		try {
			GridAdapter adapter = new GridAdapter(activity, stocks);
			grid.setAdapter(adapter);
		} catch(Exception e) {
			e.printStackTrace();
			errorHandler.onError(e);
		}
	}

	@Override
	public Activity getParent() {
		return activity;
	}
}
