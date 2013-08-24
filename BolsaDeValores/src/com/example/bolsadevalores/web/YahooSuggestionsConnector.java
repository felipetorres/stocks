package com.example.bolsadevalores.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bolsadevalores.json.JSONSymbolSuggestObject;
import com.example.bolsadevalores.json.JSONSymbolSuggestObject.Suggestion;
import com.google.gson.Gson;

public class YahooSuggestionsConnector {
	
	private Context context;
	private ListView listView;

	public YahooSuggestionsConnector(Context context, ListView listView) {
		this.context = context;
		this.listView = listView;
	}

	public void getFor(final String stock) {

		Handler handler = new Handler();
		handler.post(new Runnable() {

			URI uri = buildURIWith(stock);

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(uri);

				try {
					HttpResponse response = client.execute(get);
					String json = EntityUtils.toString(response.getEntity())
								.replace("YAHOO.Finance.SymbolSuggest.ssCallback(", "")
								.replace(")", "");
					
					JSONSymbolSuggestObject jsonObject = new Gson().fromJson(json, JSONSymbolSuggestObject.class);
					List<Suggestion> suggestions = jsonObject.getSuggestions();
					
					ArrayAdapter<Suggestion> adapter = new ArrayAdapter<Suggestion>(context, android.R.layout.simple_list_item_1, suggestions);
					listView.setAdapter(adapter);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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

}
