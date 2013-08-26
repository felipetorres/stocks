package com.example.bolsadevalores.json;

import java.util.List;

import com.example.bolsadevalores.model.Stock;

@SuppressWarnings("unused")
public class JSONListResponseObject implements JSONResponseObject{

	private Query query;

	public List<Stock> getStocks() {
		if(query.results != null) return this.query.results.quote;
		return null;
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
