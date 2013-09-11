package com.example.bolsadevalores.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ColorHelper;
import com.example.bolsadevalores.model.Stock;
import com.example.bolsadevalores.model.interfaces.ResponseElement;

public class GridAdapter extends BaseAdapter{
	
	private List<? extends ResponseElement> stocks;
	private Activity activity;

	public GridAdapter(Activity activity, List<? extends ResponseElement> stocks) {
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
		
		View layout = activity.getLayoutInflater().inflate(R.layout.item_stock, null);
		
		TextView symbol = (TextView) layout.findViewById(R.id.item_symbol);
		TextView percentChange = (TextView) layout.findViewById(R.id.item_percentChange);
		TextView price = (TextView) layout.findViewById(R.id.item_price);
		TextView lastTradeDate = (TextView) layout.findViewById(R.id.item_lastTradeDate);
		
		Stock stock = (Stock) stocks.get(position);
		
		symbol.setText(stock.Symbol);
		percentChange.setText(stock.getRealtimePercentage());
		price.setText(stock.getLastTradePrice());
		lastTradeDate.setText(stock.getPrettyLastTradeDateAndTime());

//		Pattern pattern = Pattern.compile("[+-](.*?)%");
//		Matcher matcher = pattern.matcher(change);
//		String value = "";
//		
//		if(matcher.matches()) {
//			value = matcher.group(1);
//		}
		
		new ColorHelper(activity).setBackgroundColorTo(percentChange, stock.getRealtimePercentage());
		
		return layout;
	}
}
