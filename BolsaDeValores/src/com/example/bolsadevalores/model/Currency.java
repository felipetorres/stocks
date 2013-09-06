package com.example.bolsadevalores.model;

import org.jsoup.select.Elements;

public class Currency {

	private Elements rawParams;
	private String name;
	private String value;
	private boolean isUp;
	private String valueChange;
	private String percentChange;

	public Currency(Elements rawParams) {
		this.rawParams = rawParams;
		this.name = textAtPosition(0).split(" ")[0];
		this.value = textAtPosition(1);
		this.isUp = textAtPosition(2) == "up_g" ? true : false;
		this.valueChange = textAtPosition(3);
		this.percentChange = textAtPosition(4);
	}
	
	private String textAtPosition(int pos) {
		return rawParams.get(pos).text();
	}

	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getPercentChange() {
		return percentChange;
	}
	
	public boolean isUp() {
		return isUp;
	}
}
