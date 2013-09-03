package com.example.bolsadevalores.tab;

import com.tinymission.rss.FeedFragment;

public class ValorFeedFragment extends FeedFragment {

	@Override
	public String[] getFeedUrl() {
		return new String[] {"http://www.valor.com.br/financas/rss"};
	}
	
	@Override
	public String toString() {
		return "Valor";
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
