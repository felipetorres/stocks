package com.example.bolsadevalores.helper;

import java.util.ArrayList;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;

import com.example.bolsadevalores.tab.EstadaoFeedFragment;
import com.example.bolsadevalores.tab.ExameFeedFragment;
import com.example.bolsadevalores.tab.InfoMoneyFeedFragment;
import com.example.bolsadevalores.tab.ReutersFeedFragment;
import com.example.bolsadevalores.tab.TabListener;
import com.example.bolsadevalores.tab.ValorFeedFragment;

public class TabHelper {

	public void addTabsTo(ActionBar actionBar) {

		ArrayList<Class<?>> clazzes = new ArrayList<Class<?>>();
		clazzes.add(InfoMoneyFeedFragment.class);
		clazzes.add(ExameFeedFragment.class);
		clazzes.add(ValorFeedFragment.class);
		clazzes.add(EstadaoFeedFragment.class);
		clazzes.add(ReutersFeedFragment.class);

		try {
			for (Class<?> clazz : clazzes) {
				Tab infoMoney = actionBar.newTab()
						.setText(clazz.newInstance().toString())
						.setTabListener(new TabListener(clazz));
				actionBar.addTab(infoMoney);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
