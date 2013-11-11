package com.example.bolsadevalores.model;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.select.Elements;

import com.example.bolsadevalores.model.interfaces.Bookmarkable;
import com.example.bolsadevalores.model.interfaces.ResponseElement;

public class Currency implements Bookmarkable, ResponseElement{

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
		this.name = textAtPosition(0);
		this.value = textAtPosition(1);
		this.valueChange = textAtPosition(2);
		this.state = getStateFromCssClass(2);
		this.percentChange = textAtPosition(3);
		this.lastChange = textAtPosition(4);
		this.marketTime = textAtPosition(5).split("\\|")[2].trim();
	}

	private CurrencyState getStateFromCssClass(int pos) {
		String cssClass = classAtPosition(pos);
		if(cssClass.contains("Positive")) return CurrencyState.UP;
		if(cssClass.contains("Negative")) return CurrencyState.DOWN;
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
		
		//Mon Sep 9 2013 14:10:33 GMT+0000 (UTC)
		this.marketTime = this.marketTime.replaceFirst("(.*?\\s.*?\\s.*?\\s.*?)\\s.*", "$1");
		//Mon Sep 9 2013
		
		//2:09PM EDT
		this.lastChange = this.lastChange.replaceFirst("(AM|PM).*", "$1");
		//2:09PM
		
		try {
			return parsedDateAndTime();
			
		} catch (IllegalArgumentException e) {
			return parsedDateAtMidnight();
		}
	}

	private String parsedDateAtMidnight() {
		DateTimeFormatter formatFrom = DateTimeFormat.forPattern("EEE MMM dd yyyy")
				.withLocale(Locale.US)
				.withZone(DateTimeZone.forID("America/New_York"));

		DateTime parsed = formatFrom.parseDateTime(this.marketTime)
				.withZone(DateTimeZone.forID("America/Sao_Paulo"));
		DateTimeFormatter formatTo = DateTimeFormat.forPattern("dd/MMM");
		return parsed.toString(formatTo) + " " + "00:00";
	}

	private String parsedDateAndTime() {
		DateTimeFormatter formatFrom = DateTimeFormat.forPattern("EEE MMM dd yyyy hh:mmaa")
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
