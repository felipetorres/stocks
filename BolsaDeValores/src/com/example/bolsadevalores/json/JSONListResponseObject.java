package com.example.bolsadevalores.json;

import java.util.ArrayList;
import java.util.List;

import com.example.bolsadevalores.model.Stock;

@SuppressWarnings("unused")
public class JSONListResponseObject implements JSONResponseObject{

	private Query query;

	public List<Stock> getStocks() {
		try {
			return this.query.results.quote;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Stock>();
		}
	}
	
	private class Query {
		
		private int count;
		private String created;
		private String lang;
		private Results results;
	}
	
	private class Results {
		
		private List<Stock> quote;
	}
}
