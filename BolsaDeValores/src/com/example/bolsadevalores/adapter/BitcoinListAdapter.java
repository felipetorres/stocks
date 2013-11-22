package com.example.bolsadevalores.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.helper.ColorHelper;
import com.example.bolsadevalores.model.Coin;
import com.example.bolsadevalores.model.interfaces.ResponseElement;

public class BitcoinListAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<? extends ResponseElement> bitcoins;

	public BitcoinListAdapter(Activity activity, List<? extends ResponseElement> bitcoins) {
		this.activity = activity;
		this.bitcoins = bitcoins;
	}

	@Override
	public int getCount() {
		return bitcoins.size();
	}

	@Override
	public Object getItem(int position) {
		return bitcoins.get(position);
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
		TextView lastChange = (TextView) layout.findViewById(R.id.item_currency_lastChange);
		TextView percentChange = (TextView) layout.findViewById(R.id.item_currency_percentChange);
		
		
		Coin coin = (Coin) bitcoins.get(position);
		
		ColorHelper colorHelper = new ColorHelper(activity);
		String textPercentChange = coin.getFormattedPercentageChange();
		
		name.setText(coin.getName()+"/BRL");
		value.setText(coin.getFormattedPrice());
		lastChange.setText(coin.getLastChange());
		percentChange.setText(textPercentChange);
		colorHelper.setTextColorTo(percentChange, textPercentChange);
		
		return layout;
	}

}
