package com.example.bolsadevalores.web;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpConnector {
	
	private HttpClient client;

	public HttpConnector() {
		client = new DefaultHttpClient();
	}

	public String getTo(String url) {
		
		HttpGet get = new HttpGet(url);
		get.setHeader("accepts", "application/json");
		
		String responseString = null;
		try {
			HttpResponse response = client.execute(get);
			responseString = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseString;
	}
}
