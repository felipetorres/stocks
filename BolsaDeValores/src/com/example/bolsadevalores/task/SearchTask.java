package com.example.bolsadevalores.task;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.json.JSONSymbolSuggestObject;
import com.example.bolsadevalores.json.JSONSymbolSuggestObject.Suggestion;
import com.google.gson.Gson;

public class SearchTask extends AsyncTask<String, Object, JSONSymbolSuggestObject> {

	private Context context;
	private ListView listView;
	private ErrorHandler errorHandler;

	public SearchTask(Context context, ListView listView, ErrorHandler errorHandler) {
		this.context = context;
		this.listView = listView;
		this.errorHandler = errorHandler;
	}

	private URI buildURIWith(String stock) {
		String uri = "http://autoc.finance.yahoo.com/autoc?query=" + stock
				+ "&callback=YAHOO.Finance.SymbolSuggest.ssCallback";

		try {
			return new URI(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected JSONSymbolSuggestObject doInBackground(String... stocks) {
		String stock = stocks[0];
		URI uri = buildURIWith(stock);

		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);
		String json = "";

		try {
			HttpResponse response = client.execute(get);
			json = EntityUtils.toString(response.getEntity())
					.replace("YAHOO.Finance.SymbolSuggest.ssCallback(", "")
					.replace(")", "");
			
			return new Gson().fromJson(json, JSONSymbolSuggestObject.class);
	
		} catch (Exception e) {
			return null; 
		}
	}
	
	@Override
	protected void onPostExecute(JSONSymbolSuggestObject jsonObject) {
		
		try {
			List<Suggestion> suggestions = jsonObject.getSuggestions();
	
			ArrayAdapter<Suggestion> adapter = new ArrayAdapter<Suggestion>(
					context, android.R.layout.simple_list_item_1, suggestions);
			listView.setAdapter(adapter);
		} catch (Exception e) {
			errorHandler.onError(e);
		}
	}
}