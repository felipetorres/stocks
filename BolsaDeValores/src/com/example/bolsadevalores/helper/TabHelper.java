package com.example.bolsadevalores.helper;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.adapter.FragmentPagerCustomAdapter;
import com.example.bolsadevalores.tab.CurrencyFragment;
import com.example.bolsadevalores.tab.EstadaoFeedFragment;
import com.example.bolsadevalores.tab.ExameFeedFragment;
import com.example.bolsadevalores.tab.InfoMoneyFeedFragment;
import com.example.bolsadevalores.tab.ReutersFeedFragment;
import com.example.bolsadevalores.tab.StockFragment;
import com.example.bolsadevalores.tab.TabListener;
import com.example.bolsadevalores.tab.ValorFeedFragment;

public class TabHelper {
	
	private ActionBarActivity activity;
	private ArrayList<Class<? extends Fragment>> clazzes = new ArrayList<Class<? extends Fragment>>();
	private ViewPager pager;

	public TabHelper(ActionBarActivity activity) {
		this.activity = activity;
		pager = (ViewPager) activity.findViewById(R.id.pager);
	}

	public TabHelper addFeedTabsTo(ActionBar actionBar) {

		clazzes.add(InfoMoneyFeedFragment.class);
		clazzes.add(ExameFeedFragment.class);
		clazzes.add(ValorFeedFragment.class);
		clazzes.add(EstadaoFeedFragment.class);
		clazzes.add(ReutersFeedFragment.class);

		addTabsTo(actionBar);
		return this;
	}
	
	public TabHelper addMarketTabsTo(ActionBar actionBar) {
		
		clazzes.add(StockFragment.class);
		clazzes.add(CurrencyFragment.class);

		addTabsTo(actionBar);
		return this;
	}
	
	public void withSwipe(FragmentManager manager) {
		
		final List<Fragment> fragments = new ArrayList<Fragment>();
		try {
			for (Class<? extends Fragment> clazz : clazzes) {
				Fragment fragment = clazz.newInstance();
				fragments.add(fragment);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		pager.setAdapter(new FragmentPagerCustomAdapter(manager, fragments));
		pager.setOffscreenPageLimit(fragments.size());
		
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				activity.getSupportActionBar().setSelectedNavigationItem(position);
				for (int i=0; i<fragments.size(); i++) {
					if(i!=position) fragments.get(i).onPause();
				}
			}
		});
	}
	
	private void addTabsTo(ActionBar actionBar) {
		try {
			for (Class<?> clazz : clazzes) {
				Tab tab = actionBar.newTab()
						.setText(clazz.newInstance().toString())
						.setTabListener(new TabListener(pager));
				actionBar.addTab(tab);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
