package com.example.bolsadevalores.task;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ColorHelper;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.ProgressManager;
import com.example.bolsadevalores.json.JSONSingleResponseObject;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.web.WebConnector;
import com.google.gson.Gson;

public class DetailsTask extends
		AsyncTask<String, Object, JSONSingleResponseObject> {

	private Activity activity;
	private ProgressManager progressManager;
	private ErrorHandler errorHandler;
	private WebConnector webConnector;

	public DetailsTask(Activity activity, ErrorHandler errorHandler) {
		this.activity = activity;
		this.errorHandler = errorHandler;
		this.progressManager = new ProgressManager(activity);
	}

	@Override
	protected void onPreExecute() {
		progressManager.show();
	}

	@Override
	protected JSONSingleResponseObject doInBackground(String... params) {

		List<String> stockSymbols = Arrays.asList(params);

		String resposta;
		try {
			webConnector = new WebConnector();
			resposta = webConnector.connectToStockUrl(stockSymbols);
			
			return new Gson().fromJson(resposta, JSONSingleResponseObject.class);
		} catch (Exception e) {
			return new JSONSingleResponseObject();
		}
	}

	@Override
	protected void onPostExecute(JSONSingleResponseObject result) {

		progressManager.hide();
		
		try {
			List<Stock> stocks = result.getStocks();
			Stock stock = stocks.get(0);
		
			TextView name = (TextView) activity.findViewById(R.id.details_name);
			TextView updatedAt = (TextView) activity.findViewById(R.id.details_updated_at);
			TextView dayValue = (TextView) activity.findViewById(R.id.details_day);
			TextView lastValue = (TextView) activity.findViewById(R.id.details_recent);
			TextView previousCloseValue = (TextView) activity.findViewById(R.id.details_previous_close);
			TextView openValue = (TextView) activity.findViewById(R.id.details_open);
			WebView chart = (WebView) activity.findViewById(R.id.details_chart);
		
			name.setText(stock.Name);
			updatedAt.setText(stock.getPrettyLastTradeDateAndTime());
			chart.loadUrl(webConnector.getChartUrlFor(stock.getSymbol()));
			
			String realtimePercentage = stock.getRealtimePercentage();
			
			dayValue.setText(realtimePercentage);
			new ColorHelper(activity).setTextColorTo(dayValue, realtimePercentage);
			
			lastValue.setText(stock.getLastTradePrice());
			previousCloseValue.setText(stock.PreviousClose);
			openValue.setText(stock.Open);
		} catch(Exception e) {
			errorHandler.onError(e);
		}
	}

}
