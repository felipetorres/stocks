package com.example.bolsadevalores.tab;

import com.tinymission.rss.FeedFragment;

public class ReutersFeedFragment extends FeedFragment {

	@Override
	public String[] getFeedUrl() {
		return new String[] {"http://feeds.reuters.com/reuters/companyNews"};
	}
	
	@Override
	public String toString() {
		return "Reuters";
	}
	
	@Override
	public int getMaxItems() {
		return 10;
	}
	
	@Override
	public boolean isDateVisible() {
		return true;
	}
	
	@Override
	public void setDateFormat(String format) {
		super.setDateFormat("dd/MMM HH:mm'h'");
	}
}
