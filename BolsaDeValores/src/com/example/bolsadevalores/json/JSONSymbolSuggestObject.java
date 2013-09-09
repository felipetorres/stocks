package com.example.bolsadevalores.json;

import java.util.ArrayList;
import java.util.List;

import com.example.bolsadevalores.model.Suggestion;

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
}
