package com.example.bolsadevalores.web;

import java.util.List;

public class YahooWebConnector {

	private static String YAHOO_FINANCE;
	private List<String> symbols;
	
	public YahooWebConnector(List<String> symbols) {
		this.symbols = symbols;
	}
	
	public String connectToStockUrl() throws Exception {
		buildStockUrl();
		return new HttpConnector().getTo(YAHOO_FINANCE);
	}
	
	public String connectToCurrencyUrl() throws Exception {
		buildCurrencyUrl();
		return new HttpConnector().getTo(YAHOO_FINANCE);
	}
	
	private YahooWebConnector buildStockUrl() {
		
		YAHOO_FINANCE = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
		
		for (String symbol : symbols) {
			YAHOO_FINANCE += "%22"+symbol+"%22%2C";
		}
		
		YAHOO_FINANCE += "%22%22)%0A%09%09&format=json&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=";
		return this;
	}
	
	private YahooWebConnector buildCurrencyUrl() {
		
		YAHOO_FINANCE = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
		
		for (String symbol : symbols) {
			YAHOO_FINANCE += "%22"+symbol+"%3DX%22%2C";
		}
		
		YAHOO_FINANCE += "%22%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
		return this;
	}
}
