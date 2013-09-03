package com.example.bolsadevalores.tab;

import com.tinymission.rss.FeedFragment;

public class InfoMoneyFeedFragment extends FeedFragment {

	@Override
	public String[] getFeedUrl() {
		return new String[] {"http://www.infomoney.com.br/ultimas-noticias/rss"};
	}
	
	@Override
	public String toString() {
		return "InfoMoney";
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
