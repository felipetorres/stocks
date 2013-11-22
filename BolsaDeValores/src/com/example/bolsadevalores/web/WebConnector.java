package com.example.bolsadevalores.web;

import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class WebConnector {

	private static String BITCOIN_URL = "https://www.mercadobitcoin.com.br/api/trades/";
	private static String LITECOIN_URL = "https://www.mercadobitcoin.com.br/api/trades_litecoin/";
	private static String YAHOO_FINANCE;
	private String TIME; //lololol
	
	public String connectToStockUrl(List<String> symbols) throws Exception {
		buildStockUrl(symbols);
		return new HttpConnector().getTo(YAHOO_FINANCE);
	}
	
	public Elements connectToCurrencyUrl(String symbol) throws Exception {
		buildCurrencyUrl(symbol);
		Document document = Jsoup.connect(YAHOO_FINANCE).get();
		//Elements elements = document.select("[id~=(l10|c10|p20|t10|market_time)], .time_rtq_content, .title h2");
		Elements elements = document.select(".symbol-name, .stock-price, .change-amount, .change-percentage, .meta");
		findTimeIn(document);
		elements.add(new Element(Tag.valueOf("span"),"").attr("class", "time").text(TIME));
		
		return elements;
	}
	
	public String connectToBitcoinUrl() throws Exception {
		String millis = getMillis();
		return new HttpConnector().getTo(BITCOIN_URL + millis + "/");
	}

	public String connectToLitecoinUrl() throws Exception {
		String millis = getMillis();
		return new HttpConnector().getTo(LITECOIN_URL + millis + "/");
	}
	
	private String getMillis() {
		DateMidnight dateTime = new DateTime().toDateMidnight();
		String millis = String.valueOf(dateTime.getMillis()).substring(0, 10);
		return millis;
	}
	
	private void findTimeIn(Node node) {
		List<Node> childNodes = node.childNodes();
		for (Node child : childNodes) {
			if (child.nodeName().equals("#comment")) {
				if (child.toString().contains("Served by Touchdown")) TIME = child.toString();
			} else findTimeIn(child);
		}
	}
		
	private WebConnector buildStockUrl(List<String> symbols) {
		
		YAHOO_FINANCE = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(";
		
		for (String symbol : symbols) {
			symbol = symbol.replace("^", "%5E");
			YAHOO_FINANCE += "%22"+symbol+"%22%2C";
		}
		
		YAHOO_FINANCE += "%22%22)%0A%09%09&format=json&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=";
		return this;
	}
	
	private WebConnector buildCurrencyUrl(String symbol) {
		
		YAHOO_FINANCE = "http://finance.yahoo.com/q?s=" + symbol; //EURUSD=X
		
		return this;
	}

	public String getChartUrlFor(String symbol) {
		return "http://chart.finance.yahoo.com/z?s="+symbol+"&t=1d&q=l&l=on&z=s&a=v&p=s&lang=en-US&region=BR";
	}
}
