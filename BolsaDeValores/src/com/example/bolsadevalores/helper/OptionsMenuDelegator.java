package com.example.bolsadevalores.helper;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.activity.MyFeedActivity;

public class OptionsMenuDelegator {

	private Activity activity;

	public OptionsMenuDelegator(Activity activity) {
		this.activity = activity;
	}

	public void withSearchView(Menu menu, final Class<? extends Activity> clazz) {

		MenuItem searchItem = menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				Intent intent = new Intent(activity, clazz);
				intent.putExtra("query", query);
				activity.startActivity(intent);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				return false;
			}
		});
	}

	public boolean select(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_feeds:
			Intent feed = new Intent(activity, MyFeedActivity.class);
			activity.startActivity(feed);
			return true;
		default:
			return false;
		}
	}
}
