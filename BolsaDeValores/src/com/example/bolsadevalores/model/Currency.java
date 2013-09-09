package com.example.bolsadevalores.model;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.select.Elements;

public class Currency implements Bookmarkable{

	private String symbol;
	private Elements rawParams;
	private String marketTime;
	private String name;
	private String value;
	private CurrencyState state;
	private String valueChange;
	private String percentChange;
	private String lastChange;

	public Currency(String symbol, Elements rawParams) {
		this.symbol = symbol;
		this.rawParams = rawParams;
		this.marketTime = textAtPosition(0).split("-")[0];
		this.name = textAtPosition(1).split(" ")[0];
		this.value = textAtPosition(2);
		this.state = getStateFromCssClass(3);
		this.valueChange = textAtPosition(4);
		this.percentChange = textAtPosition(5);
		this.lastChange = textAtPosition(6);
	}

	private CurrencyState getStateFromCssClass(int pos) {
		String cssClass = classAtPosition(pos);
		if(cssClass.contains("up_g")) return CurrencyState.UP;
		if(cssClass.contains("down_r")) return CurrencyState.DOWN;
		return CurrencyState.NEUTRAL;
	}
	
	private String textAtPosition(int pos) {
		return rawParams.get(pos).text();
	}
	
	private String classAtPosition(int pos) {
		return rawParams.get(pos).className();
	}

	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getPercentChange() {
		String percentChange = this.percentChange.replace("(", "").replace(")", "");
		if(state.equals(CurrencyState.UP)) return "+" + percentChange;
		if(state.equals(CurrencyState.DOWN)) return "-" + percentChange;
		return percentChange;
	}
	
	
	public String getPrettyLastChange() {
		
		//Mon, Sep 9, 2013, 2:10PM EDT
		this.marketTime = this.marketTime.replaceFirst("(.*,.*,.*,).*", "$1");
		
		//2:09PM EDT
		this.lastChange = this.lastChange.replaceFirst("(AM|PM).*", "$1");
		
		DateTimeFormatter formatFrom = DateTimeFormat.forPattern("EEE, MMM dd, yyyy, hh:mmaa")
										.withLocale(Locale.US)
										.withZone(DateTimeZone.forID("America/New_York"));
		
		DateTime parsed = formatFrom.parseDateTime(this.marketTime + " " + this.lastChange)
									.withZone(DateTimeZone.forID("America/Sao_Paulo"));
		
		DateTimeFormatter formatTo = DateTimeFormat.forPattern("dd/MMM HH:mm");
		return parsed.toString(formatTo);
	}
	
	@Override
	public String getSymbol() {
		return this.symbol;
	}
}
