package com.example.bolsadevalores.tab;

import com.tinymission.rss.FeedFragment;

public class EstadaoFeedFragment extends FeedFragment {

	@Override
	public String[] getFeedUrl() {
		return new String[] {"http://economia.estadao.com.br/EN/rss/ultimas.xml"};
	}
	
	@Override
	public String toString() {
		return "Estadão";
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