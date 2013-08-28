package com.example.bolsadevalores.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.json.JSONSymbolSuggestObject.Suggestion;

public class SearchAdapter extends BaseAdapter{
	
	private Activity activity;
	private List<Suggestion> suggestions;

	public SearchAdapter(Activity activity, List<Suggestion> suggestions) {
		this.activity = activity;
		this.suggestions = suggestions;
	}

	@Override
	public int getCount() {
		return suggestions.size();
	}

	@Override
	public Object getItem(int position) {
		return suggestions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View layout = activity.getLayoutInflater().inflate(R.layout.item_search, null);
		
		TextView name = (TextView) layout.findViewById(R.id.item_search_name);
        CheckBox checkbox = (CheckBox) layout.findViewById(R.id.item_search_checkbox);
        
        Suggestion suggestion = suggestions.get(position);
        
		name.setText(suggestion.getSymbol());

		checkbox.setTag(suggestion);
        checkbox.setChecked(suggestion.isChecked());

        checkbox.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                CheckBox check = (CheckBox) v;
                Suggestion item = (Suggestion) check.getTag();
                item.check(check.isChecked());
                if(check.isChecked()) check.setButtonDrawable(android.R.drawable.star_on);
                else check.setButtonDrawable(android.R.drawable.star_off);
            }
        });
		
		return layout;
	}

}
