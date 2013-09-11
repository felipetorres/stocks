package com.example.bolsadevalores.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.example.bolsadevalores.model.interfaces.Bookmarkable;
import com.example.bolsadevalores.model.interfaces.ResponseElement;

public class Stock implements Bookmarkable, ResponseElement{
	
	public String symbol;
    public String Ask;
    public String AverageDailyVolume;
    public String Bid;
    public String AskRealtime;
    public String BidRealtime;
    public String BookValue;
    public String Change_PercentChange;
    public String Change;
    public String Commission;
    public String ChangeRealtime;
    public String AfterHoursChangeRealtime;
    public String DividendShare;
    public String LastTradeDate;
    public String TradeDate;
    public String EarningsShare;
    public String ErrorIndicationreturnedforsymbolchangedinvalid;     
    public String EPSEstimateCurrentYear;
    public String EPSEstimateNextYear;
    public String EPSEstimateNextQuarter;
    public String DaysLow;
    public String DaysHigh;
    public String YearLow;
    public String YearHigh;
    public String HoldingsGainPercent;
    public String AnnualizedGain;
    public String HoldingsGain;
    public String HoldingsGainPercentRealtime;
    public String HoldingsGainRealtime;
    public String MoreInfo;
    public String OrderBookRealtime;
    public String MarketCapitalization;
    public String MarketCapRealtime;
    public String EBITDA;
    public String ChangeFromYearLow;
    public String PercentChangeFromYearLow;
    public String LastTradeRealtimeWithTime;
    public String ChangePercentRealtime;
    public String ChangeFromYearHigh;
    public String PercebtChangeFromYearHigh;
    public String LastTradeWithTime;
    public String LastTradePriceOnly;
    public String HighLimit;
    public String LowLimit;
    public String DaysRange;
    public String DaysRangeRealtime;
    public String FiftydayMovingAverage;
    public String TwoHundreddayMovingAverage;
    public String ChangeFromTwoHundreddayMovingAverage;
    public String PercentChangeFromTwoHundreddayMovingAverage;
    public String ChangeFromFiftydayMovingAverage;
    public String PercentChangeFromFiftydayMovingAverage;
    public String Name;
    public String Notes;
    public String Open;
    public String PreviousClose;
    public String PricePaid;
    public String ChangeinPercent;
    public String PriceSales;
    public String PriceBook;
    public String ExDividendDate;
    public String PERatio;
    public String DividendPayDate;
    public String PERatioRealtime;
    public String PEGRatio;
    public String PriceEPSEstimateCurrentYear;
    public String PriceEPSEstimateNextYear;
    public String Symbol;
    public String SharesOwned;
    public String ShortRatio;
    public String LastTradeTime;
    public String TickerTrend;
    public String OneyrTargetPrice;
    public String Volume;
    public String HoldingsValue;
    public String HoldingsValueRealtime;
    public String YearRange;
    public String DaysValueChange;
    public String DaysValueChangeRealtime;
    public String StockExchange;
    public String DividendYield;
    public String PercentChange;
    
    public DateTime getLastTradeDateAndTime() {
    	DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy hh:mmaa");
    	formatter = formatter.withZone(DateTimeZone.forID("America/New_York"));
    	DateTime parsedDateTime = formatter.parseDateTime(this.LastTradeDate + " " + this.LastTradeTime);
    	
    	DateTime withZone = parsedDateTime.toDateTime().withZone(DateTimeZone.forID("America/Sao_Paulo"));
    	
		return withZone;
    }
    
    public String getPrettyLastTradeDateAndTime() {
    	DateTime tradeDateAndTime = this.getLastTradeDateAndTime();
    	DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
    	
    	return tradeDateAndTime.toString(formatter);
    }
    
    public String getRealtimePercentage() {

    	double change = 0;
    	
    	try {
	    	Double previousClose = Double.valueOf(PreviousClose);
	    	Double recentValue = Double.valueOf(getLastTradePrice());
			change = (recentValue - previousClose)/previousClose*100;
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
	    	BigDecimal percentage = new BigDecimal(change).setScale(2, RoundingMode.HALF_UP);
			
			if (percentage.signum() == 1) {
				return "+" + percentage.toString() + "%";
			} else {
				return percentage.toString() + "%";
			}
    	}
    }

	public String getLastTradePrice() {
		if(Ask != null) return Ask;
		return LastTradePriceOnly;
	}

	@Override
	public String getSymbol() {
		return this.Symbol;
	}
    
}
