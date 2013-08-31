package com.example.bolsadevalores.menu;

import java.util.List;

import android.app.Activity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bolsadevalores.helper.SharedPreferencesAccessor;
import com.example.bolsadevalores.json.JSONSymbolSuggestObject.Suggestion;
import com.example.bolsadevalores.model.Bookmark;

public class CurrencySearchContextActionBar {
	
	private Activity activity;
	private List<Suggestion> suggestions;

	public CurrencySearchContextActionBar(Activity activity, List<Suggestion> suggestions) {
		this.activity = activity;
		this.suggestions = suggestions;
	}
	
	public ActionMode.Callback build() {
		
		return new ActionMode.Callback() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				if(containsSelected()) {
					bookmarkAllSelected();
					activity.finish();
				}
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.setTitle("1 Selected");
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode arg0, MenuItem arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
	
	private void bookmarkAllSelected() {
		SharedPreferencesAccessor shPref = new SharedPreferencesAccessor(activity, Bookmark.CURRENCY);

		for (Suggestion suggestion : suggestions) {
			if(suggestion.isChecked()) {
				List<String> currencies = shPref.retrieveBookmarked();
				shPref.tryToBookmark(suggestion, currencies);
			}
		}
	}
	
	private boolean containsSelected() {
		for (Suggestion suggestion : suggestions) {
			if(suggestion.isChecked()) {
				return true;
			}
		}
		return false;
	}
}
