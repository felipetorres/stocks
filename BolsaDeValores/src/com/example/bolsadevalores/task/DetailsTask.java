package com.example.bolsadevalores.task;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ErrorHandler;
import com.example.bolsadevalores.helper.ProgressManager;
import com.example.bolsadevalores.json.JSONSingleResponseObject;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.web.YahooWebConnector;
import com.google.gson.Gson;

public class DetailsTask extends
		AsyncTask<String, Object, JSONSingleResponseObject> {

	private Activity activity;
	private ProgressManager progressManager;
	private ErrorHandler errorHandler;

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
			resposta = new YahooWebConnector(stockSymbols).connectToStockUrl();
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
		
			name.setText(stock.Name);
			updatedAt.setText(stock.getPrettyLastTradeDateAndTime());
			dayValue.setText(stock.getRealtimePercentage());
			lastValue.setText(stock.Ask);
			previousCloseValue.setText(stock.PreviousClose);
			openValue.setText(stock.Open);
		} catch(Exception e) {
			errorHandler.onError(e);
		}
	}

}
