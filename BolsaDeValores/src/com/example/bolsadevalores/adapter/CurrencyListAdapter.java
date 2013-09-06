package com.example.bolsadevalores.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.model.Currency;

public class CurrencyListAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<Currency> currencies;

	public CurrencyListAdapter(Activity activity, List<Currency> currencies) {
		this.activity = activity;
		this.currencies = currencies;
	}

	@Override
	public int getCount() {
		return currencies.size();
	}

	@Override
	public Object getItem(int position) {
		return currencies.get(position);
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
		TextView percentChange = (TextView) layout.findViewById(R.id.item_currency_percentChange);
		
		Currency currency = currencies.get(position);
		
		name.setText(currency.getName());
		value.setText(currency.getValue());
		percentChange.setText(currency.getPercentChange());
		
		return layout;
	}

}
