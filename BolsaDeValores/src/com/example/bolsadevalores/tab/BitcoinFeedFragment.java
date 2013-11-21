package com.example.bolsadevalores.tab;

import com.tinymission.rss.FeedFragment;

public class BitcoinFeedFragment extends FeedFragment implements WithTabName {

	@Override
	public String[] getFeedUrl() {
		return new String[] {"http://bitcoin.gw.gd/spip.php?page=backend"};
	}
	
	@Override
	public String toString() {
		return "Bitcoin";
	}
	
	@Override
	public int getMaxItems() {
		return 30;
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
