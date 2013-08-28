package com.example.bolsadevalores.menu;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public class SearchContextActionBar {

	public ActionMode.Callback build() {
		
		return new ActionMode.Callback() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				return false;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode arg0, MenuItem arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
}
