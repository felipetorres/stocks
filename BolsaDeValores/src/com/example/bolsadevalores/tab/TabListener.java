package com.example.bolsadevalores.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;

import com.example.bolsadevalores.R;

public class TabListener<T extends Fragment> implements ActionBar.TabListener{
	
	private Class<T> clazz;
	private Fragment fragment;

	public TabListener(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction tx) {
		if(fragment == null) {
			try {
				tx.replace(R.id.feed_fragment, clazz.newInstance());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			tx.replace(R.id.feed_fragment,fragment);
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
