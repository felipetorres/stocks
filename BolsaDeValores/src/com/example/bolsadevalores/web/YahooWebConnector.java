package com.example.bolsadevalores.web;

import java.util.List;

public class YahooWebConnector implements Connector{

	private static String YAHOO_FINANCE;
	
	public YahooWebConnector(List<String> symbols) {
		buildYahooFinanceUrlWithStockSymbols(symbols);
	}
	
	private void buildYahooFinanceUrlWithStockSymbols(List<String> symbols) {
		
		YAHOO_FINANCE = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
		
		for (String symbol : symbols) {
			YAHOO_FINANCE += "%22"+symbol+"%22%2C";
		}
		
		YAHOO_FINANCE += "%22%22)%0A%09%09&format=json&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=";
	}

	@Override
	public String connect() {
		return new HttpConnector().getTo(YAHOO_FINANCE);
	}
}
