package com.example.bolsadevalores.tab;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;

import com.example.bolsadevalores.R;

public class TabListener<T extends Fragment> implements ActionBar.TabListener{
	
	private Activity activity;
	private Class<T> clazz;
	private Fragment fragment;

	public TabListener(Activity activity, Class<T> clazz) {
		this.activity = activity;
		this.clazz = clazz;
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction tx) {
		if(fragment == null) {
			try {
				fragment = Fragment.instantiate(activity, clazz.getName());
				tx.add(R.id.feed_fragment, fragment);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			tx.attach(fragment);
		}
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction tx) {
		if(fragment != null) {
			tx.detach(fragment);
		}
	}
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
