package com.example.bolsadevalores.model.interfaces;

import java.util.List;

import android.app.Activity;

public interface ResultHandler {

	public void updateWith(List<? extends ResponseElement> currencies);
	
	public Activity getParent();

}
