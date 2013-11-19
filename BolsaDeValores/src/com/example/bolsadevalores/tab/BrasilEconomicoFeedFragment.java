package com.example.bolsadevalores.tab;

import com.tinymission.rss.FeedFragment;

public class BrasilEconomicoFeedFragment extends FeedFragment implements WithTabName {

	@Override
	public String[] getFeedUrl() {
		return new String[] {"http://brasileconomico.ig.com.br/rss/ultimas"};
	}
	
	@Override
	public String toString() {
		return "BR Econômico";
	}
	
	@Override
	public int getMaxItems() {
		return 20;
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
