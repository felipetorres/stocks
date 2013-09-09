package com.example.bolsadevalores.model;

import org.jsoup.select.Elements;

public class Currency {

	private Elements rawParams;
	private String name;
	private String value;
	private CurrencyState state;
	private String valueChange;
	private String percentChange;

	public Currency(Elements rawParams) {
		this.rawParams = rawParams;
		this.name = textAtPosition(0).split(" ")[0];
		this.value = textAtPosition(1);
		this.state = getStateFromCssClass();
		this.valueChange = textAtPosition(3);
		this.percentChange = textAtPosition(4);
	}

	private CurrencyState getStateFromCssClass() {
		String cssClass = classAtPosition(2);
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
}
