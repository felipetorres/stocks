package com.example.bolsadevalores.json;

import java.util.Map;

public class JSONCurrencyResponseObject {

	String disclaimer;
	String licence;
	int timestamp;
	String base;
	Map<String, Double> rates;

	public Map<String, Double> getRates() {
		return rates;
	}
	
}
