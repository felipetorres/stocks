package com.example.bolsadevalores.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.model.Stock;

public class GridAdapter extends BaseAdapter{
	
	private List<Stock> stocks;
	private Activity activity;

	public GridAdapter(Activity activity, List<Stock> stocks) {
		this.activity = activity;
		this.stocks = stocks;
	}

	@Override
	public int getCount() {
		return stocks.size();
	}

	@Override
	public Object getItem(int position) {
		return stocks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View layout = activity.getLayoutInflater().inflate(R.layout.item, null);
		
		TextView symbol = (TextView) layout.findViewById(R.id.item_symbol);
		TextView percentChange = (TextView) layout.findViewById(R.id.item_percentChange);
		TextView price = (TextView) layout.findViewById(R.id.item_price);
		TextView lastTradeDate = (TextView) layout.findViewById(R.id.item_lastTradeDate);
		
		String change = stocks.get(position).PercentChange;
		
		symbol.setText(stocks.get(position).Symbol);
		percentChange.setText(change);
		price.setText(stocks.get(position).AskRealtime);
		lastTradeDate.setText(stocks.get(position).getPrettyLastTradeDateAndTime());

//		Pattern pattern = Pattern.compile("[+-](.*?)%");
//		Matcher matcher = pattern.matcher(change);
//		String value = "";
//		
//		if(matcher.matches()) {
//			value = matcher.group(1);
//		}
		
		setBackgroundColorTo(percentChange, change);
		
		return layout;
	}

	private void setBackgroundColorTo(TextView percentChange, String change) {
		if(change.startsWith("-")) {
			percentChange.setBackgroundColor(activity.getResources().getColor(R.color.negativo));
		} else {
			percentChange.setBackgroundColor(activity.getResources().getColor(R.color.positivo));
		}
	}
}