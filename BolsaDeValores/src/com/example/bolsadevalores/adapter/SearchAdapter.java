package com.example.bolsadevalores.adapter;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bolsadevalores.R;
import com.example.bolsadevalores.json.JSONSymbolSuggestObject.Suggestion;
import com.example.bolsadevalores.menu.SearchContextActionBar;

public class SearchAdapter extends BaseAdapter{
	
	private ActionBarActivity activity;
	private List<Suggestion> suggestions;
	private SearchContextActionBar contextActionBar;
    private ActionMode mode;
    private int selected;

	public SearchAdapter(ActionBarActivity activity, List<Suggestion> suggestions) {
		this.activity = activity;
		this.suggestions = suggestions;
		this.contextActionBar = new SearchContextActionBar(activity, suggestions);
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

		setChecked(checkbox, suggestion);
		checkbox.setTag(suggestion);

        checkbox.setOnClickListener(new OnClickListener(){

			@Override
            public void onClick(View v) {
                CheckBox check = (CheckBox) v;
                Suggestion item = (Suggestion) check.getTag();
                item.check(check.isChecked());
                
                updateStarWith(check);
                updateContextActionBarText();
                destroyIfNothingSelected();
            }
        });
		
		return layout;
	}

	private void updateStarWith(CheckBox check) {
		if(check.isChecked()) {
        	selected++;
        	check.setButtonDrawable(android.R.drawable.star_on);
        }
        else {
        	selected--;
        	check.setButtonDrawable(android.R.drawable.star_off);
        }
	}
	
	private void setChecked(CheckBox check, Suggestion suggestion) {
		if(suggestion.isChecked()) {
        	check.setButtonDrawable(android.R.drawable.star_on);
        }
        else {
        	check.setButtonDrawable(android.R.drawable.star_off);
        }
	}

	private void updateContextActionBarText() {
		if(mode == null) mode = activity.startSupportActionMode(contextActionBar.build());
		else mode.setTitle(selected + " Selected");
	}

	private void destroyIfNothingSelected() {
		if(selected == 0) {
    		mode.finish();
    		mode = null;
    	}
	}

}
