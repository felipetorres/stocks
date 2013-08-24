package com.example.bolsadevalores.json;

import java.util.Arrays;
import java.util.List;

import com.example.bolsadevalores.model.Stock;

@SuppressWarnings("unused")
public class JSONSingleResponseObject implements JSONResponseObject{

	private Query query;

	@Override
	public List<Stock> getStocks() {
		if(this.query.results != null) {
			return Arrays.asList(this.query.results.quote);
		}
		return null;
	}
	
	private class Query {
		
		private int count;
		private String created;
		private String lang;
		private Results results;
	}
	
	private class Results {
		
		private Stock quote;
	}
}
