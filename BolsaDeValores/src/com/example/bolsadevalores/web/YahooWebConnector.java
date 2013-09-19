package com.example.bolsadevalores.web;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class YahooWebConnector {

	private static String YAHOO_FINANCE;
	
	public String connectToStockUrl(List<String> symbols) throws Exception {
		buildStockUrl(symbols);
		return new HttpConnector().getTo(YAHOO_FINANCE);
	}
	
	public Elements connectToCurrencyUrl(String symbol) throws Exception {
		buildCurrencyUrl(symbol);
		Document document = Jsoup.connect(YAHOO_FINANCE).get();
		return document.select("[id~=(l10|c10|p20|t10|market_time)], .time_rtq_content, .title h2");
	}
	
	private YahooWebConnector buildStockUrl(List<String> symbols) {
		
		YAHOO_FINANCE = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
		
		for (String symbol : symbols) {
			symbol = symbol.replace("^", "%5E");
			YAHOO_FINANCE += "%22"+symbol+"%22%2C";
		}
		
		YAHOO_FINANCE += "%22%22)%0A%09%09&format=json&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=";
		return this;
	}
	
	private YahooWebConnector buildCurrencyUrl(String symbol) {
		
		YAHOO_FINANCE = "http://finance.yahoo.com/q?s=" + symbol; //EURUSD=X
		
		return this;
	}

	public String getChartUrlFor(String symbol) {
		return "http://chart.finance.yahoo.com/z?s="+symbol+"&t=1d&q=l&l=on&z=s&a=v&p=s&lang=en-US&region=BR";
	}
}
