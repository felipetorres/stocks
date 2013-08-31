package com.example.bolsadevalores.model;

public enum Bookmark {

	STOCK {
		@Override
		public String getType() {
			return "stocks";
		}
	},
	CURRENCY {
		@Override
		public String getType() {
			return "currencies";
		}
	};
	
	public abstract String getType();
}
