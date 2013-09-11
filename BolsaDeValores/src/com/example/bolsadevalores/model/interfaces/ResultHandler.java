package com.example.bolsadevalores.model.interfaces;

import java.util.List;

import android.app.Activity;

import com.example.bolsadevalores.model.Currency;

public interface ResultHandler {

	public void updateWith(List<Currency> currencies);
	
	public Activity getParent();

}
