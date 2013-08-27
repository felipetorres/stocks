package com.example.bolsadevalores.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.bolsadevalores.task.CurrencyTask;

public class CurrencyActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new CurrencyTask().execute();
	}
	
}
