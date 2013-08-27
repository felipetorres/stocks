package com.example.bolsadevalores.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bolsadevalores.R;

public class CurrencyListAdapter extends BaseAdapter{
	
	private Activity activity;
	private Map<String, Double> currencies;

	public CurrencyListAdapter(Activity activity, Map<String, Double> currencies) {
		this.activity = activity;
		this.currencies = currencies;
	}

	@Override
	public int getCount() {
		return currencies.size();
	}

	@Override
	public Object getItem(int position) {
		Set<Entry<String,Double>> set = currencies.entrySet();

		List<Entry<String,Double>> list = new ArrayList<Entry<String,Double>>(set);
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View layout = activity.getLayoutInflater().inflate(R.layout.item_currency, null);
		
		TextView name = (TextView) layout.findViewById(R.id.item_currency_name);
		TextView value = (TextView) layout.findViewById(R.id.item_currency_value);
		
		List<Entry<String,Double>> list = new ArrayList<Entry<String,Double>>(currencies.entrySet());
		
		name.setText(list.get(position).getKey());
		value.setText(list.get(position).getValue().toString());
		
		return layout;
	}

}
