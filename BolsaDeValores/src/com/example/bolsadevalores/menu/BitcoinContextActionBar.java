package com.example.bolsadevalores.menu;

import android.os.Handler;
import android.view.View;

import com.example.bolsadevalores.model.interfaces.ResultHandler;
import com.example.bolsadevalores.task.BitcoinTask;

public class BitcoinContextActionBar {
	
	private Runnable ticker;
	private Handler handler = new Handler();
	private View layout;
	private ResultHandler resultHandler;
	private boolean running = false;

	public BitcoinContextActionBar(ResultHandler resultHandler, View layout) {
		this.resultHandler = resultHandler;
		this.layout = layout;
	}
	
	public void loadBitcoinsWithTicker() {
		new BitcoinTask(resultHandler, layout, true).execute();
		
		ticker = new Runnable() {
			@Override
			public void run() {
				new BitcoinTask(resultHandler, layout, false).execute();
				handler.postDelayed(this, 120000);
			}
		};
		this.startTicker();
	}

	public void stopTicker() {
		handler.removeCallbacks(ticker);
		running = false;
	}
	
	public void startTicker() {
		if(!running) {
			handler.post(ticker);
			running = true;
		}
	}
}
