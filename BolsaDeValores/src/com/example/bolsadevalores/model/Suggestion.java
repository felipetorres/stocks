package com.example.bolsadevalores.model;

import java.io.Serializable;

import com.example.bolsadevalores.model.interfaces.Bookmarkable;

public class Suggestion implements Serializable, Bookmarkable{
	
	private static final long serialVersionUID = 1L;
	
	String symbol;
	String name;
	String exch;
	String type;
	String exchDisp;
	String typeDisp;
	
	transient boolean checked;
	
	@Override
	public String toString() {
		return symbol;
	}
	
	@Override
	public String getSymbol() {
		return symbol;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void check(boolean checked) {
		this.checked = checked;
	}
}
