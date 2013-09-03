package com.example.bolsadevalores.tab;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;

public class TabListener implements ActionBar.TabListener{
	
	private ViewPager pager;

	public TabListener(ViewPager pager) {
		this.pager = pager;
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction tx) {
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction tx) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
