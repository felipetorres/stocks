package com.example.bolsadevalores.model;

import java.text.NumberFormat;
import java.util.Locale;

import org.joda.time.DateTime;

import com.example.bolsadevalores.model.interfaces.ResponseElement;

public class Coin implements ResponseElement {

	private long date;
	private double price;
	private double percentageChange;
	private String name;
	
	public Coin(String name, long date, double price, double percentageChange) {
		this.name = name;
		this.date = date;
		this.price = price;
		this.percentageChange = percentageChange;
	}
	
	public Coin() {}

	public String getLastChange() {
		return new DateTime(date*1000).toString("dd/MMM HH:mm");
	}
	
	public long getDate() {
		return date;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getFormattedPrice() {
		return format(price);
	}
	
	public String getName() {
		return name;
	}
	
	public String getFormattedPercentageChange() {
		String change = format(percentageChange) + "%";
		if(percentageChange > 0) return "+" + change;
		return change;
	}

	private String format(double number) {
		NumberFormat formatter = NumberFormat.getInstance(Locale.ENGLISH);
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		formatter.setGroupingUsed(false);
		return formatter.format(number);
	}
}
