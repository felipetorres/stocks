package com.example.bolsadevalores.activity;

import com.tinymission.rss.FeedActivity;

public class MyFeedActivity extends FeedActivity {

	@Override
	public String[] getFeedUrl() {
		return new String[] {"http://www.guj.com.br/noticias/rss","http://www.infomoney.com.br/ultimas-noticias/rss"};
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
