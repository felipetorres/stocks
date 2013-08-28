package com.example.bolsadevalores.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JSONSymbolSuggestObject {

	ResultSet ResultSet;
	
	public List<Suggestion> getSuggestions() {
		if(this.ResultSet != null) {
			return this.ResultSet.Result;
		}
		return new ArrayList<Suggestion>();
	}
	
	private class ResultSet {
		
		String Query;
		List<Suggestion> Result;
	}
	
	public class Suggestion implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		String symbol;
		String name;
		String exch;
		String type;
		String exchDisp;
		String typeDisp;
		
		@Override
		public String toString() {
			return symbol;
		}
		
		public String getSymbol() {
			return symbol;
		}
	}
}
