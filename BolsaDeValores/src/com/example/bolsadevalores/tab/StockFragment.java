package com.example.bolsadevalores.tab;

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
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.OptionsMenuDelegator;
import com.example.bolsadevalores.menu.StockContextActionBar;
import com.example.bolsadevalores.model.Stock;

public class StockFragment extends Fragment implements WithTabName{
	
	private Stock selected;
	private GridView grid;
	private StockContextActionBar customActionMode;
	private ActionBarActivity activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (ActionBarActivity) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_stock, null);
		
		grid = (GridView) layout.findViewById(R.id.gridView);
		
		customActionMode = new StockContextActionBar(activity, grid, (ErrorHandler) activity);
		
		return layout;
	}
	
	@Override
	public void onResume() {
		super.onResume();
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
	public void onPause() {
		super.onPause();
		customActionMode.stopTicker();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.stock, menu);
		new OptionsMenuDelegator(activity).withSearchView(menu, StockSearchActivity.class);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return new OptionsMenuDelegator(activity).select(item);
	}

	@Override
	public String toString() {
		return "Stocks";
	}
}
