package com.example.bolsadevalores.task;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.ProgressManager;
import com.example.bolsadevalores.json.JSONSymbolSuggestObject;
import com.example.bolsadevalores.json.JSONSymbolSuggestObject.Suggestion;
import com.google.gson.Gson;

public class SearchTask extends AsyncTask<String, Object, JSONSymbolSuggestObject> {

	private ActionBarActivity activity;
	private ListView listView;
	private ErrorHandler errorHandler;
	private ProgressManager progressManager;
	private Class<? extends BaseAdapter> searchAdapterClass;

	public SearchTask(ActionBarActivity activity, ListView listView, ErrorHandler errorHandler, Class<? extends BaseAdapter> searchAdapterClass) { 
		this.activity = activity;
		this.listView = listView;
		this.errorHandler = errorHandler;
		this.searchAdapterClass = searchAdapterClass;
		this.progressManager = new ProgressManager(activity);
	}
	
	@Override
	protected void onPreExecute() {
		progressManager.show();
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
		
		progressManager.hide();
		
		try {
			List<Suggestion> suggestions = jsonObject.getSuggestions();
	
			BaseAdapter adapter = newInstanceOf(searchAdapterClass, activity, suggestions); 
			listView.setAdapter(adapter);
			
		} catch (Exception e) {
			errorHandler.onError(e);
		}
	}
	
	private BaseAdapter newInstanceOf(Class<? extends BaseAdapter> clazz, Activity activity, List<Suggestion> suggestions) {
		
		Constructor<? extends BaseAdapter> constructor;
		BaseAdapter newInstance = null;
		
		try {
			constructor = clazz.getConstructor(ActionBarActivity.class, List.class);
			newInstance = constructor.newInstance(activity, suggestions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newInstance;
	}
	
	private URI buildURIWith(String stock) {
		stock = stock.replace("=", "%3D");
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