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
import com.example.bolsadevalores.tab.EstadaoFeedFragment;
import com.example.bolsadevalores.tab.ExameFeedFragment;
import com.example.bolsadevalores.tab.InfoMoneyFeedFragment;
import com.example.bolsadevalores.tab.ReutersFeedFragment;
import com.example.bolsadevalores.tab.TabListener;
import com.example.bolsadevalores.tab.ValorFeedFragment;

public class TabHelper {
	
	private ActionBarActivity activity;
	private ArrayList<Class<? extends Fragment>> clazzes = new ArrayList<Class<? extends Fragment>>();
	private ViewPager pager;

	public TabHelper(ActionBarActivity activity) {
		this.activity = activity;
		pager = (ViewPager) activity.findViewById(R.id.feed_pager);
	}

	public TabHelper addTabsTo(ActionBar actionBar) {

		clazzes.add(InfoMoneyFeedFragment.class);
		clazzes.add(ExameFeedFragment.class);
		clazzes.add(ValorFeedFragment.class);
		clazzes.add(EstadaoFeedFragment.class);
		clazzes.add(ReutersFeedFragment.class);

		try {
			for (Class<?> clazz : clazzes) {
				Tab infoMoney = actionBar.newTab()
						.setText(clazz.newInstance().toString())
						.setTabListener(new TabListener(pager));
				actionBar.addTab(infoMoney);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	
	public void withSwipe(FragmentManager manager) {
		
		List<Fragment> fragments = new ArrayList<Fragment>();
		try {
		for (Class<? extends Fragment> clazz : clazzes) {
			fragments.add(clazz.newInstance());
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		pager.setAdapter(new FragmentPagerCustomAdapter(manager, fragments));
		pager.setOffscreenPageLimit(5);
		
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				activity.getSupportActionBar().setSelectedNavigationItem(position);
			}
		});
	}
}
