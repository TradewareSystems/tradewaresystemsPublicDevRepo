package com.tradeware.stockmarket.bean;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import com.tradeware.stockmarket.util.Constants;

public class OptionChainDataBean {
	
	public static NumberFormat myformatter = new DecimalFormat("########");  

	private Integer optionChainId;
	private String symbol;
	private double strikePrice1;
	private double strikePrice2;
	private double tickerSize;
	private Integer timeFrameNumber;
	// CALLS
	private double top1OpenInterestCall;
	private double top2OpenInterestCall;
	private double top3OpenInterestCall;

	private double top1OpenInterestChangeCall;
	private double top2OpenInterestChangeCall;
	private double top3OpenInterestChangeCall;

	private double top1OIVolumesCall;
	private double top2OIVolumesCall;
	private double top3OIVolumesCall;

	private double top1OINetChangeCall;
	private double top2OINetChangeCall;
	private double top3OINetChangeCall;

	private double top1StrikePriceCall;
	private double top2StrikePriceCall;
	private double top3StrikePriceCall;

	// PUTs
	private double top1OpenInterestPut;
	private double top2OpenInterestPut;
	private double top3OpenInterestPut;

	private double top1OpenInterestChangePut;
	private double top2OpenInterestChangePut;
	private double top3OpenInterestChangePut;

	private double top1OIVolumesPut;
	private double top2OIVolumesPut;
	private double top3OIVolumesPut;

	private double top1OINetChangePut;
	private double top2OINetChangePut;
	private double top3OINetChangePut;

	private double top1StrikePricePut;
	private double top2StrikePricePut;
	private double top3StrikePricePut;
	
	private double totalOpenInterestCall;
	private double totalOpenInterestPut;
	private double totalOIVolumesCall;
	private double totalOIVolumesPut;
	
	private Double putCallRatio;
	private String putCallRatioStyleClass;
	
	//OI Spruts
	private double day1OpenInterest;
	private double day2OpenInterest;
	private double openInterestChange;
	private double openInterestChangePercentage;
	private double openInterestVolumes;//contracts
	private double underlyingPrice;//current market price  value
	private Date dateTimeStamp;
	private Date dateStamp;
	
	private Boolean bestTradeInd = Boolean.FALSE;
	private String bestTradeIndStr = "";
	
	private String timeStampStr;
	public String getTimeStampStr() {
		return timeStampStr;
	}
	public void setTimeStampStr(String timeStampStr) {
		this.timeStampStr = timeStampStr;
	}

	public OptionChainDataBean(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	public OptionChainDataBean(Integer lotSize, String symbol) {
		super();
		this.lotSize=lotSize;
		this.symbol = symbol;
	}
	
	private String expiryDate;
	
	public OptionChainDataBean clone() {
		OptionChainDataBean bean = new OptionChainDataBean(this.lotSize, this.symbol);
		bean.setOptionChainId(optionChainId);
		bean.setSymbol(symbol);
		bean.setStrikePrice1(strikePrice1);
		bean.setTickerSize(tickerSize);
		bean.setTimeFrameNumber(timeFrameNumber);
		bean.setTop1OpenInterestCall(top1OpenInterestCall);
		bean.setTop2OpenInterestCall(top2OpenInterestCall);
		bean.setTop3OpenInterestCall(top3OpenInterestCall);
		bean.setTop1OpenInterestChangeCall(top1OpenInterestChangeCall);
		bean.setTop2OpenInterestChangeCall(top2OpenInterestChangeCall);
		bean.setTop3OpenInterestChangeCall(top3OpenInterestChangeCall);
		bean.setTop1OIVolumesCall(top1OIVolumesCall);
		bean.setTop2OIVolumesCall(top2OIVolumesCall);
		bean.setTop3OIVolumesCall(top3OIVolumesCall);
		bean.setTop1OINetChangeCall(top1OINetChangeCall);
		bean.setTop2OINetChangeCall(top2OINetChangeCall);
		bean.setTop3OINetChangeCall(top3OINetChangeCall);
		bean.setTop1StrikePriceCall(top1StrikePriceCall);
		bean.setTop2StrikePriceCall(top2StrikePriceCall);
		bean.setTop3StrikePriceCall(top3StrikePriceCall);
		bean.setTop1OpenInterestPut(top1OpenInterestPut);
		bean.setTop2OpenInterestPut(top2OpenInterestPut);
		bean.setTop3OpenInterestPut(top3OpenInterestPut);
		bean.setTop1OpenInterestChangePut(top1OpenInterestChangePut);
		bean.setTop2OpenInterestChangePut(top2OpenInterestChangePut);
		bean.setTop3OpenInterestChangePut(top3OpenInterestChangePut);
		bean.setTop1OIVolumesPut(top1OIVolumesPut);
		bean.setTop2OIVolumesPut(top2OIVolumesPut);
		bean.setTop3OIVolumesPut(top3OIVolumesPut);
		bean.setTop1OINetChangePut(top1OINetChangePut);
		bean.setTop2OINetChangePut(top2OINetChangePut);
		bean.setTop3OINetChangePut(top3OINetChangePut);
		bean.setTop1StrikePricePut(top1StrikePricePut);
		bean.setTop2StrikePricePut(top2StrikePricePut);
		bean.setTop3StrikePricePut(top3StrikePricePut);
		bean.setTotalOpenInterestCall(totalOpenInterestCall);
		bean.setTotalOpenInterestPut(totalOpenInterestPut);
		bean.setTotalOIVolumesCall(totalOIVolumesCall);
		bean.setTotalOIVolumesPut(totalOIVolumesPut);
		bean.setDay1OpenInterest(day1OpenInterest);
		bean.setDay2OpenInterest(day2OpenInterest);
		bean.setOpenInterestChange(openInterestChange);
		bean.setOpenInterestChangePercentage(openInterestChangePercentage);
		bean.setOpenInterestVolumes(openInterestVolumes);
		bean.setUnderlyingPrice(underlyingPrice);
		bean.setDateTimeStamp(dateTimeStamp);
		bean.setWeek52High(week52High);
		bean.setWeek52Low(week52Low);
		bean.setOITrend(oITrend);
		bean.setStyle(style);
		bean.setLotSize(lotSize);	
		bean.setIndexInd(indexInd);	
		bean.setStrongOrder(strongOrder);
		bean.setLtpOnDecision(ltpOnDecision);
		bean.setLtp(ltp);
		bean.setBestEntry(bestEntry);
		bean.setGoForTrade(goForTrade);
		bean.setChangePercentage(changePercentage); 
		bean.setCandleNumber(candleNumber);
		bean.setParentRecordInd(parentRecordInd);	
		/*bean.setStyleNetChange1 = EMPTY_STR;
		bean.setStyleNetChange2 = EMPTY_STR;
		bean.setStyleNetChange3(;*/
		bean.setDateStamp(dateStamp);
		
		bean.setPreviousTop1StrikePriceCall(previousTop1StrikePriceCall);
		bean.setPreviousTop2StrikePriceCall(previousTop2StrikePriceCall);
		bean.setPreviousTop3StrikePriceCall(previousTop3StrikePriceCall);
		bean.setPreviousTop1StrikePricePut(previousTop1StrikePricePut);
		bean.setPreviousTop2StrikePricePut(previousTop2StrikePricePut);
		bean.setPreviousTop3StrikePricePut(previousTop3StrikePricePut);
		/*bean.setTop1OpenInterestCallStyle(top1OpenInterestCallStyle);
		bean.setTop2OpenInterestCallStyle(top2OpenInterestCallStyle);
		bean.setTop3OpenInterestCallStyle(top3OpenInterestCallStyle);
		bean.setTop1OpenInterestPutStyle(top1OpenInterestPutStyle);
		bean.setTop2OpenInterestPutStyle(top2OpenInterestPutStyle);
		bean.setTop3OpenInterestPutStyle(top3OpenInterestPutStyle);*/
		
		bean.setTotalOINetChangeCallPrevious1(totalOINetChangeCallPrevious1);
		bean.setTotalOINetChangePutPrevious1(totalOINetChangePutPrevious1);
		bean.setTotalOINetChangeCallPrevious2(totalOINetChangeCallPrevious2);
		bean.setTotalOINetChangePutPrevious2(totalOINetChangePutPrevious2);;
		bean.setTotalOINetChangeCallPrevious3(totalOINetChangeCallPrevious3);
		bean.setTotalOINetChangePutPrevious3(totalOINetChangePutPrevious3);
		bean.setTotalOINetChangeCallPrevious4(totalOINetChangeCallPrevious4);
		bean.setTotalOINetChangePutPrevious4(totalOINetChangePutPrevious4);;
		bean.setTotalOINetChangeCallPrevious5(totalOINetChangeCallPrevious5);
		bean.setTotalOINetChangePutPrevious5(totalOINetChangePutPrevious5);
		
		bean.setUnderlyingPricePrevious1(underlyingPricePrevious1);
		bean.setUnderlyingPricePrevious2(underlyingPricePrevious2);
		bean.setUnderlyingPricePrevious3(underlyingPricePrevious3);
		bean.setUnderlyingPricePrevious4(underlyingPricePrevious4);
		bean.setUnderlyingPricePrevious5(underlyingPricePrevious5);
		
		bean.setTotalOpenInterestChangeCallPrevious1(totalOpenInterestChangeCallPrevious1);
		bean.setTotalOpenInterestChangePutPrevious1(totalOpenInterestChangePutPrevious1);
		bean.setTotalOpenInterestChangeCallPrevious2(totalOpenInterestChangeCallPrevious2);
		bean.setTotalOpenInterestChangePutPrevious2(totalOpenInterestChangePutPrevious2);;
		bean.setTotalOpenInterestChangeCallPrevious3(totalOpenInterestChangeCallPrevious3);
		bean.setTotalOpenInterestChangePutPrevious3(totalOpenInterestChangePutPrevious3);
		bean.setTotalOpenInterestChangeCallPrevious4(totalOpenInterestChangeCallPrevious4);
		bean.setTotalOpenInterestChangePutPrevious4(totalOpenInterestChangePutPrevious4);;
		bean.setTotalOpenInterestChangeCallPrevious5(totalOpenInterestChangeCallPrevious5);
		bean.setTotalOpenInterestChangePutPrevious5(totalOpenInterestChangePutPrevious5);
		
		bean.setTotalOINetChangeCall(totalOINetChangeCall);
		bean.setTotalOINetChangePut(totalOINetChangePut);
		bean.setTotalOpenInterestChangeCall(this.totalOpenInterestChangeCall);;
		bean.setTotalOpenInterestChangeCall(this.totalOpenInterestChangePut);;
		bean.setTotalOINetChangeCallStyle(totalOINetChangeCallStyle);
		bean.setTotalOINetChangePutStyle(totalOINetChangePutStyle);
		bean.setTotalOpenInterestChangeCallStyle(this.totalOpenInterestChangeCallStyle);
		bean.setTotalOpenInterestChangePutStyle(this.totalOpenInterestChangePutStyle);
		bean.setPriceMoveTrend(priceMoveTrend);
		bean.setPriceOpenIntrestChangeTrend(priceOpenIntrestChangeTrend);
		
		bean.setTop1OpenInterestCallStyle(EMPTY_STR);
		bean.setTop2OpenInterestCallStyle(EMPTY_STR);
		bean.setTop3OpenInterestCallStyle(EMPTY_STR);
		bean.setTop1OpenInterestPutStyle(EMPTY_STR);
		bean.setTop2OpenInterestPutStyle(EMPTY_STR);
		bean.setTop3OpenInterestPutStyle(EMPTY_STR);
		bean.setAttentionStyleBuy(EMPTY_STR);
		bean.setAttentionStyleSell(EMPTY_STR);
		
		bean.setSortOrder(sortOrder);
		bean.setTopTenGainLooseSortOrder(topTenGainLooseSortOrder);
		bean.setSortOrderStyle(sortOrderStyle);
		bean.setYearHigh(yearHigh);
		bean.setYearLow(yearLow);
		bean.setPutCallRatio(putCallRatio);
		bean.setExpiryDate(this.expiryDate);
		return bean;
	}
	
	public Integer getOptionChainId() {
		return optionChainId;
	}

	public void setOptionChainId(Integer optionChainId) {
		this.optionChainId = optionChainId;
	}

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Integer getTimeFrameNumber() {
		return timeFrameNumber;
	}

	public void setTimeFrameNumber(Integer timeFrameNumber) {
		this.timeFrameNumber = timeFrameNumber;
	}

	public double getStrikePrice1() {
		return strikePrice1;
	}
	public void setStrikePrice1(double strikePrice1) {
		this.strikePrice1 = strikePrice1;
	}
	public double getStrikePrice2() {
		return strikePrice2;
	}
	public void setStrikePrice2(double strikePrice2) {
		this.strikePrice2 = strikePrice2;
	}
	public double getTickerSize() {
		return tickerSize;
	}
	public void setTickerSize(double tickerSize) {
		this.tickerSize = tickerSize;
	}
	public double getTop1OpenInterestCall() {
		return top1OpenInterestCall;
	}
	public void setTop1OpenInterestCall(double top1OpenInterestCall) {
		this.top1OpenInterestCall = top1OpenInterestCall;
	}
	public double getTop2OpenInterestCall() {
		return top2OpenInterestCall;
	}
	public void setTop2OpenInterestCall(double top2OpenInterestCall) {
		this.top2OpenInterestCall = top2OpenInterestCall;
	}
	public double getTop3OpenInterestCall() {
		return top3OpenInterestCall;
	}
	public void setTop3OpenInterestCall(double top3OpenInterestCall) {
		this.top3OpenInterestCall = top3OpenInterestCall;
	}
	public double getTop1OpenInterestChangeCall() {
		return top1OpenInterestChangeCall;
	}
	public void setTop1OpenInterestChangeCall(double top1OpenInterestChangeCall) {
		this.top1OpenInterestChangeCall = top1OpenInterestChangeCall;
	}
	public double getTop2OpenInterestChangeCall() {
		return top2OpenInterestChangeCall;
	}
	public void setTop2OpenInterestChangeCall(double top2OpenInterestChangeCall) {
		this.top2OpenInterestChangeCall = top2OpenInterestChangeCall;
	}
	public double getTop3OpenInterestChangeCall() {
		return top3OpenInterestChangeCall;
	}
	public void setTop3OpenInterestChangeCall(double top3OpenInterestChangeCall) {
		this.top3OpenInterestChangeCall = top3OpenInterestChangeCall;
	}
	public double getTop1OIVolumesCall() {
		return top1OIVolumesCall;
	}
	public void setTop1OIVolumesCall(double top1oiVolumesCall) {
		top1OIVolumesCall = top1oiVolumesCall;
	}
	public double getTop2OIVolumesCall() {
		return top2OIVolumesCall;
	}
	public void setTop2OIVolumesCall(double top2oiVolumesCall) {
		top2OIVolumesCall = top2oiVolumesCall;
	}
	public double getTop3OIVolumesCall() {
		return top3OIVolumesCall;
	}
	public void setTop3OIVolumesCall(double top3oiVolumesCall) {
		top3OIVolumesCall = top3oiVolumesCall;
	}
	public double getTop1OINetChangeCall() {
		return top1OINetChangeCall;
	}
	public void setTop1OINetChangeCall(double top1oiNetChangeCall) {
		top1OINetChangeCall = top1oiNetChangeCall;
	}
	public double getTop2OINetChangeCall() {
		return top2OINetChangeCall;
	}
	public void setTop2OINetChangeCall(double top2oiNetChangeCall) {
		top2OINetChangeCall = top2oiNetChangeCall;
	}
	public double getTop3OINetChangeCall() {
		return top3OINetChangeCall;
	}
	public void setTop3OINetChangeCall(double top3oiNetChangeCall) {
		top3OINetChangeCall = top3oiNetChangeCall;
	}
	public double getTop1StrikePriceCall() {
		return top1StrikePriceCall;
	}
	public void setTop1StrikePriceCall(double top1StrikePriceCall) {
		this.top1StrikePriceCall = top1StrikePriceCall;
	}
	public double getTop2StrikePriceCall() {
		return top2StrikePriceCall;
	}
	public void setTop2StrikePriceCall(double top2StrikePriceCall) {
		this.top2StrikePriceCall = top2StrikePriceCall;
	}
	public double getTop3StrikePriceCall() {
		return top3StrikePriceCall;
	}
	public void setTop3StrikePriceCall(double top3StrikePriceCall) {
		this.top3StrikePriceCall = top3StrikePriceCall;
	}
	public double getTop1OpenInterestPut() {
		return top1OpenInterestPut;
	}
	public void setTop1OpenInterestPut(double top1OpenInterestPut) {
		this.top1OpenInterestPut = top1OpenInterestPut;
	}
	public double getTop2OpenInterestPut() {
		return top2OpenInterestPut;
	}
	public void setTop2OpenInterestPut(double top2OpenInterestPut) {
		this.top2OpenInterestPut = top2OpenInterestPut;
	}
	public double getTop3OpenInterestPut() {
		return top3OpenInterestPut;
	}
	public void setTop3OpenInterestPut(double top3OpenInterestPut) {
		this.top3OpenInterestPut = top3OpenInterestPut;
	}
	public double getTop1OpenInterestChangePut() {
		return top1OpenInterestChangePut;
	}
	public void setTop1OpenInterestChangePut(double top1OpenInterestChangePut) {
		this.top1OpenInterestChangePut = top1OpenInterestChangePut;
	}
	public double getTop2OpenInterestChangePut() {
		return top2OpenInterestChangePut;
	}
	public void setTop2OpenInterestChangePut(double top2OpenInterestChangePut) {
		this.top2OpenInterestChangePut = top2OpenInterestChangePut;
	}
	public double getTop3OpenInterestChangePut() {
		return top3OpenInterestChangePut;
	}
	public void setTop3OpenInterestChangePut(double top3OpenInterestChangePut) {
		this.top3OpenInterestChangePut = top3OpenInterestChangePut;
	}
	public double getTop1OIVolumesPut() {
		return top1OIVolumesPut;
	}
	public void setTop1OIVolumesPut(double top1oiVolumesPut) {
		top1OIVolumesPut = top1oiVolumesPut;
	}
	public double getTop2OIVolumesPut() {
		return top2OIVolumesPut;
	}
	public void setTop2OIVolumesPut(double top2oiVolumesPut) {
		top2OIVolumesPut = top2oiVolumesPut;
	}
	public double getTop3OIVolumesPut() {
		return top3OIVolumesPut;
	}
	public void setTop3OIVolumesPut(double top3oiVolumesPut) {
		top3OIVolumesPut = top3oiVolumesPut;
	}
	public double getTop1OINetChangePut() {
		return top1OINetChangePut;
	}
	public void setTop1OINetChangePut(double top1oiNetChangePut) {
		top1OINetChangePut = top1oiNetChangePut;
	}
	public double getTop2OINetChangePut() {
		return top2OINetChangePut;
	}
	public void setTop2OINetChangePut(double top2oiNetChangePut) {
		top2OINetChangePut = top2oiNetChangePut;
	}
	public double getTop3OINetChangePut() {
		return top3OINetChangePut;
	}
	public void setTop3OINetChangePut(double top3oiNetChangePut) {
		top3OINetChangePut = top3oiNetChangePut;
	}
	public double getTop1StrikePricePut() {
		return top1StrikePricePut;
	}
	public void setTop1StrikePricePut(double top1StrikePricePut) {
		this.top1StrikePricePut = top1StrikePricePut;
	}
	public double getTop2StrikePricePut() {
		return top2StrikePricePut;
	}
	public void setTop2StrikePricePut(double top2StrikePricePut) {
		this.top2StrikePricePut = top2StrikePricePut;
	}
	public double getTop3StrikePricePut() {
		return top3StrikePricePut;
	}
	public void setTop3StrikePricePut(double top3StrikePricePut) {
		this.top3StrikePricePut = top3StrikePricePut;
	}
	public double getTotalOpenInterestCall() {
		return totalOpenInterestCall;
	}
	public void setTotalOpenInterestCall(double totalOpenInterestCall) {
		this.totalOpenInterestCall = totalOpenInterestCall;
	}
	public double getTotalOpenInterestPut() {
		return totalOpenInterestPut;
	}
	public void setTotalOpenInterestPut(double totalOpenInterestPut) {
		this.totalOpenInterestPut = totalOpenInterestPut;
	}
	public double getTotalOIVolumesCall() {
		return totalOIVolumesCall;
	}
	public void setTotalOIVolumesCall(double totalOIVolumesCall) {
		this.totalOIVolumesCall = totalOIVolumesCall;
	}
	public double getTotalOIVolumesPut() {
		return totalOIVolumesPut;
	}
	public void setTotalOIVolumesPut(double totalOIVolumesPut) {
		this.totalOIVolumesPut = totalOIVolumesPut;
	}
	public double getDay1OpenInterest() {
		return day1OpenInterest;
	}
	public void setDay1OpenInterest(double day1OpenInterest) {
		this.day1OpenInterest = day1OpenInterest;
	}
	public double getDay2OpenInterest() {
		return day2OpenInterest;
	}
	public void setDay2OpenInterest(double day2OpenInterest) {
		this.day2OpenInterest = day2OpenInterest;
	}
	public double getOpenInterestChange() {
		return openInterestChange;
	}
	public void setOpenInterestChange(double openInterestChange) {
		this.openInterestChange = openInterestChange;
	}
	public double getOpenInterestChangePercentage() {
		return openInterestChangePercentage;
	}
	public void setOpenInterestChangePercentage(double openInterestChangePercentage) {
		this.openInterestChangePercentage = openInterestChangePercentage;
	}
	public double getOpenInterestVolumes() {
		return openInterestVolumes;
	}
	public void setOpenInterestVolumes(double openInterestVolumes) {
		this.openInterestVolumes = openInterestVolumes;
	}
	public double getUnderlyingPrice() {
		return underlyingPrice;
	}
	public void setUnderlyingPrice(double underlyingPrice) {
		this.underlyingPrice = underlyingPrice;
	}
	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}

	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}

	public Boolean getBestTradeInd() {
		return bestTradeInd;
	}
	public void setBestTradeInd(Boolean bestTradeInd) {
		this.bestTradeInd = bestTradeInd;
	}
	
	public String getBestTradeIndStr() {
		if (null != bestTradeInd && bestTradeInd) {
			bestTradeIndStr = "checked";
		}
		return bestTradeIndStr;
	}

	@Override
	public String toString() {
		return "OptionChainDataBean [optionChainId=" + optionChainId + ", symbol=" + symbol + ", oITrend=" + oITrend
				+ ", priceMoveTrend=" + priceMoveTrend + ", strikePrice1=" + strikePrice1 + ", strikePrice2="
				+ strikePrice2 + ", tickerSize=" + tickerSize + ", timeFrameNumber=" + timeFrameNumber
				+ ", top1OpenInterestCall=" + top1OpenInterestCall + ", top2OpenInterestCall=" + top2OpenInterestCall
				+ ", top3OpenInterestCall=" + top3OpenInterestCall + ", top1OpenInterestChangeCall="
				+ top1OpenInterestChangeCall + ", top2OpenInterestChangeCall=" + top2OpenInterestChangeCall
				+ ", top3OpenInterestChangeCall=" + top3OpenInterestChangeCall + ", top1OIVolumesCall="
				+ top1OIVolumesCall + ", top2OIVolumesCall=" + top2OIVolumesCall + ", top3OIVolumesCall="
				+ top3OIVolumesCall + ", top1OINetChangeCall=" + top1OINetChangeCall + ", top2OINetChangeCall="
				+ top2OINetChangeCall + ", top3OINetChangeCall=" + top3OINetChangeCall + ", top1StrikePriceCall="
				+ top1StrikePriceCall + ", top2StrikePriceCall=" + top2StrikePriceCall + ", top3StrikePriceCall="
				+ top3StrikePriceCall + ", top1OpenInterestPut=" + top1OpenInterestPut + ", top2OpenInterestPut="
				+ top2OpenInterestPut + ", top3OpenInterestPut=" + top3OpenInterestPut + ", top1OpenInterestChangePut="
				+ top1OpenInterestChangePut + ", top2OpenInterestChangePut=" + top2OpenInterestChangePut
				+ ", top3OpenInterestChangePut=" + top3OpenInterestChangePut + ", top1OIVolumesPut=" + top1OIVolumesPut
				+ ", top2OIVolumesPut=" + top2OIVolumesPut + ", top3OIVolumesPut=" + top3OIVolumesPut
				+ ", top1OINetChangePut=" + top1OINetChangePut + ", top2OINetChangePut=" + top2OINetChangePut
				+ ", top3OINetChangePut=" + top3OINetChangePut + ", top1StrikePricePut=" + top1StrikePricePut
				+ ", top2StrikePricePut=" + top2StrikePricePut + ", top3StrikePricePut=" + top3StrikePricePut
				+ ", totalOpenInterestCall=" + totalOpenInterestCall + ", totalOpenInterestPut=" + totalOpenInterestPut
				+ ", totalOIVolumesCall=" + totalOIVolumesCall + ", totalOIVolumesPut=" + totalOIVolumesPut
				+ ", day1OpenInterest=" + day1OpenInterest + ", day2OpenInterest=" + day2OpenInterest
				+ ", openInterestChange=" + openInterestChange + ", openInterestChangePercentage="
				+ openInterestChangePercentage + ", openInterestVolumes=" + openInterestVolumes + ", underlyingPrice="
				+ underlyingPrice + ", dateTimeStamp=" + dateTimeStamp + ", dateStamp=" + dateStamp + ", bestTradeInd="
				+ bestTradeInd + ", bestTradeIndStr=" + bestTradeIndStr + ", timeStampStr=" + timeStampStr
				+ ", week52High=" + week52High + ", week52Low=" + week52Low + ", style=" + style + ", lotSize="
				+ lotSize + ", indexInd=" + indexInd + ", strongOrder=" + strongOrder + ", ltpOnDecision="
				+ ltpOnDecision + ", ltp=" + ltp + ", bestEntry=" + bestEntry + ", goForTrade=" + goForTrade
				+ ", playanleInd=" + playanleInd + ", changePercentage=" + changePercentage + ", candleNumber="
				+ candleNumber + ", parentRecordInd=" + parentRecordInd + ", styleNetChange1=" + styleNetChange1
				+ ", styleNetChange2=" + styleNetChange2 + ", styleNetChange3=" + styleNetChange3
				+ ", previousTop1StrikePriceCall=" + previousTop1StrikePriceCall + ", previousTop2StrikePriceCall="
				+ previousTop2StrikePriceCall + ", previousTop3StrikePriceCall=" + previousTop3StrikePriceCall
				+ ", previousTop1StrikePricePut=" + previousTop1StrikePricePut + ", previousTop2StrikePricePut="
				+ previousTop2StrikePricePut + ", previousTop3StrikePricePut=" + previousTop3StrikePricePut
				+ ", top1OpenInterestCallStyle=" + top1OpenInterestCallStyle + ", top2OpenInterestCallStyle="
				+ top2OpenInterestCallStyle + ", top3OpenInterestCallStyle=" + top3OpenInterestCallStyle
				+ ", top1OpenInterestPutStyle=" + top1OpenInterestPutStyle + ", top2OpenInterestPutStyle="
				+ top2OpenInterestPutStyle + ", top3OpenInterestPutStyle=" + top3OpenInterestPutStyle
				+ ", attentionStyleBuy=" + attentionStyleBuy + ", attentionStyleSell=" + attentionStyleSell
				+ ", totalOINetChangeCall=" + totalOINetChangeCall + ", totalOINetChangePut=" + totalOINetChangePut
				+ ", totalOINetChangeCallPrevious1=" + totalOINetChangeCallPrevious1 + ", totalOINetChangePutPrevious1="
				+ totalOINetChangePutPrevious1 + ", totalOINetChangeCallPrevious2=" + totalOINetChangeCallPrevious2
				+ ", totalOINetChangePutPrevious2=" + totalOINetChangePutPrevious2 + ", totalOINetChangeCallPrevious3="
				+ totalOINetChangeCallPrevious3 + ", totalOINetChangePutPrevious3=" + totalOINetChangePutPrevious3
				+ ", underlyingPricePrevious1=" + underlyingPricePrevious1 + ", underlyingPricePrevious2="
				+ underlyingPricePrevious2 + ", underlyingPricePrevious3=" + underlyingPricePrevious3
				+ ", totalOINetChangeCallStyle=" + totalOINetChangeCallStyle + ", totalOINetChangePutStyle="
				+ totalOINetChangePutStyle + ", sortOrder=" + sortOrder + ", topTenGainLooseSortOrder="
				+ topTenGainLooseSortOrder + ", sortOrderStyle=" + sortOrderStyle + ", yearHigh=" + yearHigh
				+ ", yearLow=" + yearLow + "]";
	}
	
	//Should be in StockDataBean
	private double week52High;
	private double week52Low;
	public double getWeek52High() {
		return week52High;
	}
	public void setWeek52High(double week52High) {
		this.week52High = week52High;
	}
	public double getWeek52Low() {
		return week52Low;
	}
	public void setWeek52Low(double week52Low) {
		this.week52Low = week52Low;
	}
	
	private static final String EMPTY_STR = "",  NA = "  NA", BUY = " BUY", SELL = " SELL", CAN_BUY = "CAN BUY", CAN_SELL = "CAN SELL", STRONG_BUY = "STRONG_BUY", STRONG_SELL = "STRONG_SELL", 
			STARTS_WITH_STRONG = "STRONG", BOLD_FONT_STYLE_CLASS = "boldFont", VERY_STRONG_BUY = "STRONG_BUY_VERY", VERY_STRONG_SELL = "STRONG_SELL_VERY";
	private String oITrend = null;
	public String getOITrend() {
		if (null == oITrend) {
		/*if (0 != top1OpenInterestCall && 0 != top1OpenInterestPut && 0 != underlyingPrice
				&& 0 != totalOpenInterestCall & 0 != totalOpenInterestPut) {
			if ((top1OpenInterestCall > top1OpenInterestPut) && (totalOpenInterestCall > totalOpenInterestPut)) {
				if (underlyingPrice < top3StrikePriceCall) {
					oITrend = STRONG_BUY;
				} else if (underlyingPrice < top1StrikePriceCall) {
					oITrend = BUY;
				}
			} else if ((top1OpenInterestPut > top1OpenInterestCall) && (totalOpenInterestPut > totalOpenInterestCall)) {
				if (underlyingPrice > top3StrikePricePut) {
					oITrend = STRONG_SELL;
				} else if (underlyingPrice > top1StrikePricePut) {
					oITrend = SELL;
				}
			}
		}*/
		oITrend = NA;
		/*if (0 != top1OpenInterestCall && 0 != top1OpenInterestPut && 0 != underlyingPrice
				&& 0 != totalOpenInterestCall & 0 != totalOpenInterestPut) {
			if ((top1OpenInterestCall > top1OpenInterestPut) && (totalOpenInterestCall > totalOpenInterestPut)) {
				if (underlyingPrice < top3StrikePriceCall) {
					oITrend = BUY;
				}
			} else if ((top1OpenInterestPut > top1OpenInterestCall) && (totalOpenInterestPut > totalOpenInterestCall)) {
				if (underlyingPrice > top1StrikePricePut) {
					oITrend = SELL;
				}
			}
		}*/
		
		//this.totalOINetChangeCall = this.top1OINetChangeCall + this.top2OINetChangeCall + this.top3OINetChangeCall;
		//this.totalOINetChangePut = this.top1OINetChangePut + this.top2OINetChangePut + this.top3OINetChangePut;
		
		double callOIAvg = 0;
		double putOIAvg = 0;
		double callOIAvgDiff = 0;
		double putOIAvgDiff = 0;
		double callOIStrike1 =0;
		double callOIStrike2 =0;
		double callOIStrike3 =0;
		double putOIStrike1 =0;
		double putOIStrike2 =0;
		double putOIStrike3 =0;
		
		if (0 != top1StrikePriceCall &&  0 != top2StrikePriceCall &&  0 != top3StrikePriceCall) {
		if (top1StrikePriceCall > top2StrikePriceCall) {
			if (top1StrikePriceCall > top3StrikePriceCall) {
				callOIStrike1 = top1StrikePriceCall;
				if (top2StrikePriceCall > top3StrikePriceCall) {
					callOIStrike2 = top2StrikePriceCall;
					callOIStrike3 = top3StrikePriceCall;
				} else {
					callOIStrike2 = top3StrikePriceCall;
					callOIStrike3 = top2StrikePriceCall;
				}
			} else {
				callOIStrike1 = top3StrikePriceCall;
				callOIStrike2 = top1StrikePriceCall;
				callOIStrike3 = top2StrikePriceCall;
			}
		} else if (top3StrikePriceCall > top2StrikePriceCall) {
			callOIStrike1 = top3StrikePriceCall;
			callOIStrike2 = top2StrikePriceCall;
			callOIStrike3 = top1StrikePriceCall;
		} else {
			callOIStrike1 = top2StrikePriceCall;
			if (top1StrikePriceCall > top3StrikePriceCall) {
				callOIStrike2 = top1StrikePriceCall;
				callOIStrike3 = top3StrikePriceCall;
			}else {
				callOIStrike2 =top3StrikePriceCall;
				callOIStrike3 =  top1StrikePriceCall ;
			}
		}
		
		if (top1StrikePricePut > top2StrikePricePut) {
			if (top1StrikePricePut > top3StrikePricePut) {
				putOIStrike1 = top1StrikePricePut;
				if (top2StrikePricePut > top3StrikePricePut) {
					putOIStrike2 = top2StrikePricePut;
					putOIStrike3 = top3StrikePricePut;
				} else {
					putOIStrike2 = top3StrikePricePut;
					putOIStrike3 = top2StrikePricePut;
				}
			} else {
				putOIStrike1 = top3StrikePricePut;
				putOIStrike2 = top1StrikePricePut;
				putOIStrike3 = top2StrikePricePut;
			}
		} else if (top3StrikePricePut > top2StrikePricePut) {
			putOIStrike1 = top3StrikePricePut;
			putOIStrike2 = top2StrikePricePut;
			putOIStrike3 = top1StrikePricePut;
		} else {
			putOIStrike1 = top2StrikePricePut;
			if (top1StrikePricePut > top3StrikePricePut) {
				putOIStrike2 = top1StrikePricePut;
				putOIStrike3 = top3StrikePricePut;
			}else {
				putOIStrike2 =top3StrikePricePut;
				putOIStrike3 =  top1StrikePricePut ;
			}
		}
		
		callOIAvg = ((top1StrikePriceCall + top2StrikePriceCall + top3StrikePriceCall)/3);
		putOIAvg =  ((top1StrikePricePut + top2StrikePricePut + top3StrikePricePut)/3);
		
		if (callOIAvg != 0) {
			callOIAvgDiff = callOIAvg - underlyingPrice;
		}
		if (putOIAvg != 0) {
			putOIAvgDiff = underlyingPrice - putOIAvg;
		}
		
		if (0 != top1OpenInterestCall && 0 != top1OpenInterestPut && 0 != underlyingPrice
				&& 0 != totalOpenInterestCall & 0 != totalOpenInterestPut) {
			if ((top1OpenInterestCall > top1OpenInterestPut) && (totalOpenInterestCall > totalOpenInterestPut)) {
				if (underlyingPrice < top1StrikePriceCall) {
					//oITrend = BUY;
					oITrend = SELL;
				}
			} else if ((top1OpenInterestPut > top1OpenInterestCall) && (totalOpenInterestPut > totalOpenInterestCall)) {
				if (underlyingPrice > top1StrikePricePut) {
					//oITrend = SELL;
					oITrend = BUY;
				}
			}
			
		if ( 0 != callOIAvgDiff &&  0 != putOIAvgDiff && (putOIAvgDiff < underlyingPrice) && (underlyingPrice< callOIAvg))  {
		if (oITrend == BUY && callOIStrike1 > underlyingPrice && callOIStrike2 > underlyingPrice ) {
			if (callOIAvgDiff > 0 && callOIAvgDiff< putOIAvgDiff) {
				//oITrend = STRONG_BUY;
				oITrend = STRONG_SELL;
			}
		} else if (oITrend == SELL && putOIStrike2 < underlyingPrice && putOIStrike3 < underlyingPrice ) {
			if (putOIAvgDiff > 0 && callOIAvgDiff > putOIAvgDiff) {
				//oITrend = STRONG_SELL;
				oITrend = STRONG_BUY;
			}
		}
		}
		/*System.out.println(symbol);
		System.out.println(callOIStrike1);
		System.out.println(callOIStrike2);
		System.out.println(callOIStrike3);
		System.out.println(putOIStrike1);
		System.out.println(putOIStrike2);
		System.out.println(putOIStrike3);*/
		
		//new with new site
		oITrend = NA;
		if (underlyingPrice > top1StrikePriceCall) {
			oITrend = STRONG_SELL;
			//oITrend = STRONG_BUY;
		} else if (underlyingPrice > top2StrikePriceCall) {
			oITrend = CAN_SELL;
			//oITrend = CAN_BUY;
		} else if (underlyingPrice > top3StrikePriceCall) {
			oITrend = SELL;
			//oITrend = BUY;
		} else if (underlyingPrice < top1StrikePricePut) {
			oITrend = STRONG_BUY;
			//oITrend = STRONG_SELL;
		}  else if (underlyingPrice < top2StrikePricePut) {
			oITrend = CAN_BUY;
			//oITrend = CAN_SELL;
		} else if (underlyingPrice < top3StrikePricePut) {
			oITrend = BUY;
			//oITrend = SELL;
		}
		
		
		
	}
		}
	}
		return oITrend;
	}

	public void setOITrend(String oITrend) {
		this.oITrend = oITrend;
	}

	private String style;

	public String getStyle() {
		if (null == style) {
			style = EMPTY_STR;
			//if (null != top1StrikePriceCall && underlyingPrice > top1StrikePriceCall) {
			if (0 != top1StrikePriceCall && underlyingPrice > top1StrikePriceCall) {
				//style = "color: red;";
				style = "color: green;";
			} else if (underlyingPrice < top1StrikePricePut) {
				//style = "color: green;";
				style = "color: red;";
			}

			if ((top1StrikePriceCall > top2StrikePriceCall && top2StrikePriceCall > top3StrikePriceCall)
					&& (null != oITrend && oITrend.contains(SELL))) {
				style = style + "font-weight: bold;";
			}
			if ((top1StrikePricePut < top2StrikePricePut && top2StrikePricePut < top3StrikePricePut)
					&& (null != oITrend && oITrend.contains(BUY))) {
				style = style + "font-weight: bold;";
			}

		}
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	private Integer lotSize;

	public Integer getLotSize() {
		return lotSize;
	}

	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}
	
	private Boolean indexInd = Boolean.FALSE;

	public Boolean getIndexInd() {
		return indexInd;
	}

	public void setIndexInd(Boolean indexInd) {
		this.indexInd = indexInd;
	}
	
	 
	//For playable concept
	private Double strongOrder =0d;
	private Double  ltpOnDecision;
	private Double  ltp;
	private Double  bestEntry;
	private String goForTrade;
	private Boolean  playanleInd = Boolean.FALSE;
	private Double changePercentage; 
	private Integer candleNumber;
	private Boolean parentRecordInd;
	
	public Double getStrongOrder() {
		return strongOrder;
	}

	public void setStrongOrder(Double strongOrder) {
		this.strongOrder = strongOrder;
	}

	public Double getLtpOnDecision() {
		return ltpOnDecision;
	}

	public void setLtpOnDecision(Double ltpOnDecision) {
		this.ltpOnDecision = ltpOnDecision;
	}

	public Double getLtp() {
		return ltp;
	}

	public void setLtp(Double ltp) {
		this.ltp = ltp;
	}

	public Double getBestEntry() {
		return bestEntry;
	}

	public void setBestEntry(Double bestEntry) {
		this.bestEntry = bestEntry;
	}

	public String getGoForTrade() {
		return goForTrade;
	}

	public void setGoForTrade(String goForTrade) {
		this.goForTrade = goForTrade;
	}

	public Boolean getPlayanleInd() {
		return playanleInd;
	}

	public void setPlayanleInd(Boolean playanleInd) {
		this.playanleInd = playanleInd;
	}

	public Double getChangePercentage() {
		return changePercentage;
	}

	public void setChangePercentage(Double changePercentage) {
		this.changePercentage = changePercentage;
	}

	public Integer getCandleNumber() {
		return candleNumber;
	}

	public void setCandleNumber(Integer candleNumber) {
		this.candleNumber = candleNumber;
	}

	public Boolean getParentRecordInd() {
		return parentRecordInd;
	}

	public void setParentRecordInd(Boolean parentRecordInd) {
		this.parentRecordInd = parentRecordInd;
	}
	
	
	
	private String styleNetChange1 = EMPTY_STR;
	private String styleNetChange2 = EMPTY_STR;
	private String styleNetChange3 = EMPTY_STR;

	public String getStyleNetChange1() {
		return styleNetChange1;
	}
	public void setStyleNetChange1(String styleNetChange1) {
		this.styleNetChange1 = styleNetChange1;
	}
	public String getStyleNetChange2() {
		return styleNetChange2;
	}
	public void setStyleNetChange2(String styleNetChange2) {
		this.styleNetChange2 = styleNetChange2;
	}
	public String getStyleNetChange3() {
		return styleNetChange3;
	}
	public void setStyleNetChange3(String styleNetChange3) {
		this.styleNetChange3 = styleNetChange3;
	}
	
	
	public void handleSortOrderAndStyle() {
		double top1OINetChangeDiff = Math.abs(top1OINetChangeCall - top1OINetChangePut);
		double top2OINetChangeDiff = Math.abs(top2OINetChangeCall - top2OINetChangePut);
		double top3OINetChangeDiff = Math.abs(top3OINetChangeCall - top3OINetChangePut);

		if (top1OINetChangeDiff > top2OINetChangeDiff) {
			if (top1OINetChangeDiff > top3OINetChangeDiff) {
				styleNetChange1 = BOLD_FONT_STYLE_CLASS;
				strongOrder = top1OINetChangeDiff;
			} else {
				styleNetChange3 = BOLD_FONT_STYLE_CLASS;
				strongOrder = top3OINetChangeDiff;
			}
		} else {
			if (top2OINetChangeDiff > top3OINetChangeDiff) {
				styleNetChange2 = BOLD_FONT_STYLE_CLASS;
				strongOrder = top3OINetChangeDiff;
			} else {
				styleNetChange3 = BOLD_FONT_STYLE_CLASS;
				strongOrder = top3OINetChangeDiff;
			}
		}

		//if (null != getOITrend() && getOITrend().startsWith(STARTS_WITH_STRONG)) {
		if (null != oITrend && oITrend.startsWith(STARTS_WITH_STRONG)) {
			//if(oITrend.startsWith(STRONG_BUY)) {
			if(oITrend.startsWith(STRONG_SELL)) {
				strongOrder = 10000+strongOrder;
				if (this.underlyingPrice > this.top1StrikePriceCall && this.underlyingPrice > this.top2StrikePriceCall
						&& this.underlyingPrice > this.top3StrikePriceCall) {
					//oITrend = VERY_STRONG_BUY;
					oITrend = VERY_STRONG_SELL;
					strongOrder = strongOrder + 1000;
				}
				
				/*if("TITAN".equals(this.symbol)) {
					if (this.underlyingPrice > this.top1StrikePriceCall && this.underlyingPrice > this.top2StrikePriceCall
							&& this.underlyingPrice > this.top3StrikePriceCall) {
						oITrend = VERY_STRONG_BUY;
						strongOrder = strongOrder + 1000;
					}
				}*/
			} //else if(oITrend.startsWith(STRONG_SELL)) {
				else if(oITrend.startsWith(STRONG_BUY)) {
				strongOrder = 10000-strongOrder;
				if (this.underlyingPrice !=0 && this.underlyingPrice < this.top1StrikePricePut && this.underlyingPrice < this.top2StrikePricePut
						&& this.underlyingPrice < this.top3StrikePricePut) {
					//oITrend = VERY_STRONG_SELL;
					oITrend = VERY_STRONG_BUY;
					strongOrder = strongOrder + 750;
				}
			}
		}
		
		/*if (symbol.startsWith(Constants.NIFTY)) {
			strongOrder = strongOrder + 500;
		} else if (symbol.startsWith(Constants.BANKNIFTY)) {
			strongOrder = strongOrder + 400;
		} else if (symbol.startsWith(Constants.FINNIFTY)) {
			strongOrder = strongOrder + 300;
		}*/
	}
	
	private double previousTop1StrikePriceCall;
	private double previousTop2StrikePriceCall;
	private double previousTop3StrikePriceCall;
	
	private double previousTop1StrikePricePut;
	private double previousTop2StrikePricePut;
	private double previousTop3StrikePricePut;
	
	private String top1OpenInterestCallStyle;
	private String top2OpenInterestCallStyle;
	private String top3OpenInterestCallStyle;
	private String top1OpenInterestPutStyle;
	private String top2OpenInterestPutStyle;
	private String top3OpenInterestPutStyle;
	
	private String attentionStyleBuy;
	private String attentionStyleSell;

	public double getPreviousTop1StrikePriceCall() {
		return previousTop1StrikePriceCall;
	}
	public void setPreviousTop1StrikePriceCall(double previousTop1StrikePriceCall) {
		this.previousTop1StrikePriceCall = previousTop1StrikePriceCall;
	}
	public double getPreviousTop2StrikePriceCall() {
		return previousTop2StrikePriceCall;
	}
	public void setPreviousTop2StrikePriceCall(double previousTop2StrikePriceCall) {
		this.previousTop2StrikePriceCall = previousTop2StrikePriceCall;
	}
	public double getPreviousTop3StrikePriceCall() {
		return previousTop3StrikePriceCall;
	}
	public void setPreviousTop3StrikePriceCall(double previousTop3StrikePriceCall) {
		this.previousTop3StrikePriceCall = previousTop3StrikePriceCall;
	}
	public double getPreviousTop1StrikePricePut() {
		return previousTop1StrikePricePut;
	}
	public void setPreviousTop1StrikePricePut(double previousTop1StrikePricePut) {
		this.previousTop1StrikePricePut = previousTop1StrikePricePut;
	}
	public double getPreviousTop2StrikePricePut() {
		return previousTop2StrikePricePut;
	}
	public void setPreviousTop2StrikePricePut(double previousTop2StrikePricePut) {
		this.previousTop2StrikePricePut = previousTop2StrikePricePut;
	}
	public double getPreviousTop3StrikePricePut() {
		return previousTop3StrikePricePut;
	}
	public void setPreviousTop3StrikePricePut(double previousTop3StrikePricePut) {
		this.previousTop3StrikePricePut = previousTop3StrikePricePut;
	}
	public String getTop1OpenInterestCallStyle() {
		return top1OpenInterestCallStyle;
	}

	public void setTop1OpenInterestCallStyle(String top1OpenInterestCallStyle) {
		this.top1OpenInterestCallStyle = top1OpenInterestCallStyle;
	}

	public String getTop2OpenInterestCallStyle() {
		return top2OpenInterestCallStyle;
	}

	public void setTop2OpenInterestCallStyle(String top2OpenInterestCallStyle) {
		this.top2OpenInterestCallStyle = top2OpenInterestCallStyle;
	}

	public String getTop3OpenInterestCallStyle() {
		return top3OpenInterestCallStyle;
	}

	public void setTop3OpenInterestCallStyle(String top3OpenInterestCallStyle) {
		this.top3OpenInterestCallStyle = top3OpenInterestCallStyle;
	}

	public String getTop1OpenInterestPutStyle() {
		return top1OpenInterestPutStyle;
	}

	public void setTop1OpenInterestPutStyle(String top1OpenInterestPutStyle) {
		this.top1OpenInterestPutStyle = top1OpenInterestPutStyle;
	}

	public String getTop2OpenInterestPutStyle() {
		return top2OpenInterestPutStyle;
	}

	public void setTop2OpenInterestPutStyle(String top2OpenInterestPutStyle) {
		this.top2OpenInterestPutStyle = top2OpenInterestPutStyle;
	}

	public String getTop3OpenInterestPutStyle() {
		return top3OpenInterestPutStyle;
	}

	public void setTop3OpenInterestPutStyle(String top3OpenInterestPutStyle) {
		this.top3OpenInterestPutStyle = top3OpenInterestPutStyle;
	}
	public String getAttentionStyleBuy() {
		return attentionStyleBuy;
	}
	public void setAttentionStyleBuy(String attentionStyleBuy) {
		this.attentionStyleBuy = attentionStyleBuy;
	}
	public String getAttentionStyleSell() {
		return attentionStyleSell;
	}
	public void setAttentionStyleSell(String attentionStyleSell) {
		this.attentionStyleSell = attentionStyleSell;
	}
	public Date getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
	
	private double totalOINetChangeCall;
	private double totalOINetChangePut;
	private double totalOpenInterestChangeCall;
	private double totalOpenInterestChangePut;
	private String priceMoveTrend; 
	private String priceOpenIntrestChangeTrend;
	public double getTotalOINetChangeCall() {
		return totalOINetChangeCall;
	}
	public void setTotalOINetChangeCall(double totalOINetChangeCall) {
		this.totalOINetChangeCall = totalOINetChangeCall;
	}
	public double getTotalOINetChangePut() {
		return totalOINetChangePut;
	}
	public void setTotalOINetChangePut(double totalOINetChangePut) {
		this.totalOINetChangePut = totalOINetChangePut;
	}
	public String getPriceMoveTrend() {
		return priceMoveTrend;
	}
	public void setPriceMoveTrend(String priceMoveTrend) {
		this.priceMoveTrend = priceMoveTrend;
	}
	public String getPriceOpenIntrestChangeTrend() {
		return priceOpenIntrestChangeTrend;
	}
	public void setPriceOpenIntrestChangeTrend(String priceOpenIntrestChangeTrend) {
		this.priceOpenIntrestChangeTrend = priceOpenIntrestChangeTrend;
	}
	public double getTotalOpenInterestChangeCall() {
		return totalOpenInterestChangeCall;
	}
	public void setTotalOpenInterestChangeCall(double totalOpenInterestChangeCall) {
		this.totalOpenInterestChangeCall = totalOpenInterestChangeCall;
	}
	public double getTotalOpenInterestChangePut() {
		return totalOpenInterestChangePut;
	}
	public void setTotalOpenInterestChangePut(double totalOpenInterestChangePut) {
		this.totalOpenInterestChangePut = totalOpenInterestChangePut;
	}

	private double totalOINetChangeCallPrevious1;
	private double totalOINetChangePutPrevious1;
	private double totalOINetChangeCallPrevious2;
	private double totalOINetChangePutPrevious2;
	private double totalOINetChangeCallPrevious3;
	private double totalOINetChangePutPrevious3;
	private double totalOINetChangeCallPrevious4;
	private double totalOINetChangePutPrevious4;
	private double totalOINetChangeCallPrevious5;
	private double totalOINetChangePutPrevious5;
	
	private double underlyingPricePrevious1;
	private double underlyingPricePrevious2;
	private double underlyingPricePrevious3;
	private double underlyingPricePrevious4;
	private double underlyingPricePrevious5;
	
	private double totalOpenInterestChangeCallPrevious1;
	private double totalOpenInterestChangePutPrevious1;
	private double totalOpenInterestChangeCallPrevious2;
	private double totalOpenInterestChangePutPrevious2;
	private double totalOpenInterestChangeCallPrevious3;
	private double totalOpenInterestChangePutPrevious3;
	private double totalOpenInterestChangeCallPrevious4;
	private double totalOpenInterestChangePutPrevious4;
	private double totalOpenInterestChangeCallPrevious5;
	private double totalOpenInterestChangePutPrevious5;
	
	private String totalOINetChangeCallStyle;
	private String totalOINetChangePutStyle;
	private String totalOpenInterestChangeCallStyle;
	private String totalOpenInterestChangePutStyle;
	
	public double getTotalOINetChangeCallPrevious1() {
		return totalOINetChangeCallPrevious1;
	}
	public void setTotalOINetChangeCallPrevious1(double totalOINetChangeCallPrevious1) {
		this.totalOINetChangeCallPrevious1 = totalOINetChangeCallPrevious1;
	}
	public double getTotalOINetChangePutPrevious1() {
		return totalOINetChangePutPrevious1;
	}
	public void setTotalOINetChangePutPrevious1(double totalOINetChangePutPrevious1) {
		this.totalOINetChangePutPrevious1 = totalOINetChangePutPrevious1;
	}
	public double getTotalOINetChangeCallPrevious2() {
		return totalOINetChangeCallPrevious2;
	}
	public void setTotalOINetChangeCallPrevious2(double totalOINetChangeCallPrevious2) {
		this.totalOINetChangeCallPrevious2 = totalOINetChangeCallPrevious2;
	}
	public double getTotalOINetChangePutPrevious2() {
		return totalOINetChangePutPrevious2;
	}
	public void setTotalOINetChangePutPrevious2(double totalOINetChangePutPrevious2) {
		this.totalOINetChangePutPrevious2 = totalOINetChangePutPrevious2;
	}
	public double getTotalOINetChangeCallPrevious3() {
		return totalOINetChangeCallPrevious3;
	}
	public void setTotalOINetChangeCallPrevious3(double totalOINetChangeCallPrevious3) {
		this.totalOINetChangeCallPrevious3 = totalOINetChangeCallPrevious3;
	}
	public double getTotalOINetChangePutPrevious3() {
		return totalOINetChangePutPrevious3;
	}
	public void setTotalOINetChangePutPrevious3(double totalOINetChangePutPrevious3) {
		this.totalOINetChangePutPrevious3 = totalOINetChangePutPrevious3;
	}
	public double getTotalOINetChangeCallPrevious4() {
		return totalOINetChangeCallPrevious4;
	}
	public void setTotalOINetChangeCallPrevious4(double totalOINetChangeCallPrevious4) {
		this.totalOINetChangeCallPrevious4 = totalOINetChangeCallPrevious4;
	}
	public double getTotalOINetChangePutPrevious4() {
		return totalOINetChangePutPrevious4;
	}
	public void setTotalOINetChangePutPrevious4(double totalOINetChangePutPrevious4) {
		this.totalOINetChangePutPrevious4 = totalOINetChangePutPrevious4;
	}
	public double getTotalOINetChangeCallPrevious5() {
		return totalOINetChangeCallPrevious5;
	}
	public void setTotalOINetChangeCallPrevious5(double totalOINetChangeCallPrevious5) {
		this.totalOINetChangeCallPrevious5 = totalOINetChangeCallPrevious5;
	}
	public double getTotalOINetChangePutPrevious5() {
		return totalOINetChangePutPrevious5;
	}
	public void setTotalOINetChangePutPrevious5(double totalOINetChangePutPrevious5) {
		this.totalOINetChangePutPrevious5 = totalOINetChangePutPrevious5;
	}
	public double getUnderlyingPricePrevious1() {
		return underlyingPricePrevious1;
	}
	public void setUnderlyingPricePrevious1(double underlyingPricePrevious1) {
		this.underlyingPricePrevious1 = underlyingPricePrevious1;
	}
	public double getUnderlyingPricePrevious2() {
		return underlyingPricePrevious2;
	}
	public void setUnderlyingPricePrevious2(double underlyingPricePrevious2) {
		this.underlyingPricePrevious2 = underlyingPricePrevious2;
	}
	public double getUnderlyingPricePrevious3() {
		return underlyingPricePrevious3;
	}
	public void setUnderlyingPricePrevious3(double underlyingPricePrevious3) {
		this.underlyingPricePrevious3 = underlyingPricePrevious3;
	}
	public double getUnderlyingPricePrevious4() {
		return underlyingPricePrevious4;
	}
	public void setUnderlyingPricePrevious4(double underlyingPricePrevious4) {
		this.underlyingPricePrevious4 = underlyingPricePrevious4;
	}
	public double getUnderlyingPricePrevious5() {
		return underlyingPricePrevious5;
	}
	public void setUnderlyingPricePrevious5(double underlyingPricePrevious5) {
		this.underlyingPricePrevious5 = underlyingPricePrevious5;
	}
	public double getTotalOpenInterestChangeCallPrevious1() {
		return totalOpenInterestChangeCallPrevious1;
	}
	public void setTotalOpenInterestChangeCallPrevious1(double totalOpenInterestChangeCallPrevious1) {
		this.totalOpenInterestChangeCallPrevious1 = totalOpenInterestChangeCallPrevious1;
	}
	public double getTotalOpenInterestChangePutPrevious1() {
		return totalOpenInterestChangePutPrevious1;
	}
	public void setTotalOpenInterestChangePutPrevious1(double totalOpenInterestChangePutPrevious1) {
		this.totalOpenInterestChangePutPrevious1 = totalOpenInterestChangePutPrevious1;
	}
	public double getTotalOpenInterestChangeCallPrevious2() {
		return totalOpenInterestChangeCallPrevious2;
	}
	public void setTotalOpenInterestChangeCallPrevious2(double totalOpenInterestChangeCallPrevious2) {
		this.totalOpenInterestChangeCallPrevious2 = totalOpenInterestChangeCallPrevious2;
	}
	public double getTotalOpenInterestChangePutPrevious2() {
		return totalOpenInterestChangePutPrevious2;
	}
	public void setTotalOpenInterestChangePutPrevious2(double totalOpenInterestChangePutPrevious2) {
		this.totalOpenInterestChangePutPrevious2 = totalOpenInterestChangePutPrevious2;
	}
	public double getTotalOpenInterestChangeCallPrevious3() {
		return totalOpenInterestChangeCallPrevious3;
	}
	public void setTotalOpenInterestChangeCallPrevious3(double totalOpenInterestChangeCallPrevious3) {
		this.totalOpenInterestChangeCallPrevious3 = totalOpenInterestChangeCallPrevious3;
	}
	public double getTotalOpenInterestChangePutPrevious3() {
		return totalOpenInterestChangePutPrevious3;
	}
	public void setTotalOpenInterestChangePutPrevious3(double totalOpenInterestChangePutPrevious3) {
		this.totalOpenInterestChangePutPrevious3 = totalOpenInterestChangePutPrevious3;
	}
	public double getTotalOpenInterestChangeCallPrevious4() {
		return totalOpenInterestChangeCallPrevious4;
	}
	public void setTotalOpenInterestChangeCallPrevious4(double totalOpenInterestChangeCallPrevious4) {
		this.totalOpenInterestChangeCallPrevious4 = totalOpenInterestChangeCallPrevious4;
	}
	public double getTotalOpenInterestChangePutPrevious4() {
		return totalOpenInterestChangePutPrevious4;
	}
	public void setTotalOpenInterestChangePutPrevious4(double totalOpenInterestChangePutPrevious4) {
		this.totalOpenInterestChangePutPrevious4 = totalOpenInterestChangePutPrevious4;
	}
	public double getTotalOpenInterestChangeCallPrevious5() {
		return totalOpenInterestChangeCallPrevious5;
	}
	public void setTotalOpenInterestChangeCallPrevious5(double totalOpenInterestChangeCallPrevious5) {
		this.totalOpenInterestChangeCallPrevious5 = totalOpenInterestChangeCallPrevious5;
	}
	public double getTotalOpenInterestChangePutPrevious5() {
		return totalOpenInterestChangePutPrevious5;
	}
	public void setTotalOpenInterestChangePutPrevious5(double totalOpenInterestChangePutPrevious5) {
		this.totalOpenInterestChangePutPrevious5 = totalOpenInterestChangePutPrevious5;
	}
	public String getTotalOINetChangeCallStyle() {
		return totalOINetChangeCallStyle;
	}
	public void setTotalOINetChangeCallStyle(String totalOINetChangeCallStyle) {
		this.totalOINetChangeCallStyle = totalOINetChangeCallStyle;
	}
	public String getTotalOINetChangePutStyle() {
		return totalOINetChangePutStyle;
	}
	public void setTotalOINetChangePutStyle(String totalOINetChangePutStyle) {
		this.totalOINetChangePutStyle = totalOINetChangePutStyle;
	}
	public String getTotalOpenInterestChangeCallStyle() {
		return totalOpenInterestChangeCallStyle;
	}
	public void setTotalOpenInterestChangeCallStyle(String totalOpenInterestChangeCallStyle) {
		this.totalOpenInterestChangeCallStyle = totalOpenInterestChangeCallStyle;
	}
	public String getTotalOpenInterestChangePutStyle() {
		return totalOpenInterestChangePutStyle;
	}
	public void setTotalOpenInterestChangePutStyle(String totalOpenInterestChangePutStyle) {
		this.totalOpenInterestChangePutStyle = totalOpenInterestChangePutStyle;
	}

	private Integer sortOrder;
	private Integer topTenGainLooseSortOrder;
	private String sortOrderStyle;
	private Double yearHigh;
	private Double yearLow;
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Integer getTopTenGainLooseSortOrder() {
		return topTenGainLooseSortOrder;
	}
	public void setTopTenGainLooseSortOrder(Integer topTenGainLooseSortOrder) {
		this.topTenGainLooseSortOrder = topTenGainLooseSortOrder;
	}
	public String getSortOrderStyle() {
		return sortOrderStyle;
	}
	public void setSortOrderStyle(String sortOrderStyle) {
		this.sortOrderStyle = sortOrderStyle;
	}
	public Double getYearHigh() {
		return yearHigh;
	}
	public void setYearHigh(Double yearHigh) {
		this.yearHigh = yearHigh;
	}
	public Double getYearLow() {
		return yearLow;
	}
	public void setYearLow(Double yearLow) {
		this.yearLow = yearLow;
	}
	public Double getPutCallRatio() {
		return putCallRatio;
	}
	public void setPutCallRatio(Double putCallRatio) {
		this.putCallRatio = putCallRatio;
	}
	public String getPutCallRatioStyleClass() {
		return putCallRatioStyleClass;
	}
	public void setPutCallRatioStyleClass(String putCallRatioStyleClass) {
		this.putCallRatioStyleClass = putCallRatioStyleClass;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getTotalOpenInterestCallStr() {
		return myformatter.format(totalOpenInterestCall);
	}
	public String getTotalOpenInterestPutStr() {
		return myformatter.format(totalOpenInterestPut) ;
	}
	public String getTotalOIVolumesCallStr() {
		return myformatter.format(totalOIVolumesCall) ;
	}
	public String getTotalOIVolumesPutStr() {
		return myformatter.format(totalOIVolumesPut) ;
	}
	
	
	// TODO123 start
	private Double top1OpenInterestCallBidVal1;
	private Double top1OpenInterestCallAskVal1;
	private Double top1OpenInterestCallBidAskDiffVal1;
	private Double top1OpenInterestCallBidAskAmtDiffVal1;
	private Double top1OpenInterestPutBidVal1;
	private Double top1OpenInterestPutAskVal1;
	private Double top1OpenInterestPutBidAskDiffVal1;
	private Double top1OpenInterestPutBidAskAmtDiffVal1;
	private Double top2OpenInterestCallBidVal1;
	private Double top2OpenInterestCallAskVal1;
	private Double top2OpenInterestCallBidAskDiffVal1;
	private Double top2OpenInterestCallBidAskAmtDiffVal1;
	private Double top2OpenInterestPutBidVal1;
	private Double top2OpenInterestPutAskVal1;
	private Double top2OpenInterestPutBidAskDiffVal1;
	private Double top2OpenInterestPutBidAskAmtDiffVal1;
	private Double top3OpenInterestCallBidVal1;
	private Double top3OpenInterestCallAskVal1;
	private Double top3OpenInterestCallBidAskDiffVal1;
	private Double top3OpenInterestCallBidAskAmtDiffVal1;
	private Double top3OpenInterestPutBidVal1;
	private Double top3OpenInterestPutAskVal1;
	private Double top3OpenInterestPutBidAskDiffVal1;
	private Double top3OpenInterestPutBidAskAmtDiffVal1;
	private Double top1ImpliedVolatilityCall;
	private Double top2ImpliedVolatilityCall;
	private Double top3ImpliedVolatilityCall;
	private Double top1ImpliedVolatilityPut;
	private Double top2ImpliedVolatilityPut;
	private Double top3ImpliedVolatilityPut;
	private Double totalImpliedVolatilityCall;
	private Double totalImpliedVolatilityPut;
	private String totalImpliedVolatilityCallStyle;
	private String totalImpliedVolatilityPutStyle;
	// TODO123 end
	public Double getTop1OpenInterestCallBidVal1() {
		return top1OpenInterestCallBidVal1;
	}
	public void setTop1OpenInterestCallBidVal1(Double top1OpenInterestCallBidVal1) {
		this.top1OpenInterestCallBidVal1 = top1OpenInterestCallBidVal1;
	}
	public Double getTop1OpenInterestCallAskVal1() {
		return top1OpenInterestCallAskVal1;
	}
	public void setTop1OpenInterestCallAskVal1(Double top1OpenInterestCallAskVal1) {
		this.top1OpenInterestCallAskVal1 = top1OpenInterestCallAskVal1;
	}
	public Double getTop1OpenInterestCallBidAskDiffVal1() {
		return top1OpenInterestCallBidAskDiffVal1;
	}
	public void setTop1OpenInterestCallBidAskDiffVal1(Double top1OpenInterestCallBidAskDiffVal1) {
		this.top1OpenInterestCallBidAskDiffVal1 = top1OpenInterestCallBidAskDiffVal1;
	}
	public Double getTop1OpenInterestCallBidAskAmtDiffVal1() {
		return top1OpenInterestCallBidAskAmtDiffVal1;
	}
	public void setTop1OpenInterestCallBidAskAmtDiffVal1(Double top1OpenInterestCallBidAskAmtDiffVal1) {
		this.top1OpenInterestCallBidAskAmtDiffVal1 = top1OpenInterestCallBidAskAmtDiffVal1;
	}
	public Double getTop1OpenInterestPutBidVal1() {
		return top1OpenInterestPutBidVal1;
	}
	public void setTop1OpenInterestPutBidVal1(Double top1OpenInterestPutBidVal1) {
		this.top1OpenInterestPutBidVal1 = top1OpenInterestPutBidVal1;
	}
	public Double getTop1OpenInterestPutAskVal1() {
		return top1OpenInterestPutAskVal1;
	}
	public void setTop1OpenInterestPutAskVal1(Double top1OpenInterestPutAskVal1) {
		this.top1OpenInterestPutAskVal1 = top1OpenInterestPutAskVal1;
	}
	public Double getTop1OpenInterestPutBidAskDiffVal1() {
		return top1OpenInterestPutBidAskDiffVal1;
	}
	public void setTop1OpenInterestPutBidAskDiffVal1(Double top1OpenInterestPutBidAskDiffVal1) {
		this.top1OpenInterestPutBidAskDiffVal1 = top1OpenInterestPutBidAskDiffVal1;
	}
	public Double getTop1OpenInterestPutBidAskAmtDiffVal1() {
		return top1OpenInterestPutBidAskAmtDiffVal1;
	}
	public void setTop1OpenInterestPutBidAskAmtDiffVal1(Double top1OpenInterestPutBidAskAmtDiffVal1) {
		this.top1OpenInterestPutBidAskAmtDiffVal1 = top1OpenInterestPutBidAskAmtDiffVal1;
	}
	public Double getTop2OpenInterestCallBidVal1() {
		return top2OpenInterestCallBidVal1;
	}
	public void setTop2OpenInterestCallBidVal1(Double top2OpenInterestCallBidVal1) {
		this.top2OpenInterestCallBidVal1 = top2OpenInterestCallBidVal1;
	}
	public Double getTop2OpenInterestCallAskVal1() {
		return top2OpenInterestCallAskVal1;
	}
	public void setTop2OpenInterestCallAskVal1(Double top2OpenInterestCallAskVal1) {
		this.top2OpenInterestCallAskVal1 = top2OpenInterestCallAskVal1;
	}
	public Double getTop2OpenInterestCallBidAskDiffVal1() {
		return top2OpenInterestCallBidAskDiffVal1;
	}
	public void setTop2OpenInterestCallBidAskDiffVal1(Double top2OpenInterestCallBidAskDiffVal1) {
		this.top2OpenInterestCallBidAskDiffVal1 = top2OpenInterestCallBidAskDiffVal1;
	}
	public Double getTop2OpenInterestCallBidAskAmtDiffVal1() {
		return top2OpenInterestCallBidAskAmtDiffVal1;
	}
	public void setTop2OpenInterestCallBidAskAmtDiffVal1(Double top2OpenInterestCallBidAskAmtDiffVal1) {
		this.top2OpenInterestCallBidAskAmtDiffVal1 = top2OpenInterestCallBidAskAmtDiffVal1;
	}
	public Double getTop2OpenInterestPutBidVal1() {
		return top2OpenInterestPutBidVal1;
	}
	public void setTop2OpenInterestPutBidVal1(Double top2OpenInterestPutBidVal1) {
		this.top2OpenInterestPutBidVal1 = top2OpenInterestPutBidVal1;
	}
	public Double getTop2OpenInterestPutAskVal1() {
		return top2OpenInterestPutAskVal1;
	}
	public void setTop2OpenInterestPutAskVal1(Double top2OpenInterestPutAskVal1) {
		this.top2OpenInterestPutAskVal1 = top2OpenInterestPutAskVal1;
	}
	public Double getTop2OpenInterestPutBidAskDiffVal1() {
		return top2OpenInterestPutBidAskDiffVal1;
	}
	public void setTop2OpenInterestPutBidAskDiffVal1(Double top2OpenInterestPutBidAskDiffVal1) {
		this.top2OpenInterestPutBidAskDiffVal1 = top2OpenInterestPutBidAskDiffVal1;
	}
	public Double getTop2OpenInterestPutBidAskAmtDiffVal1() {
		return top2OpenInterestPutBidAskAmtDiffVal1;
	}
	public void setTop2OpenInterestPutBidAskAmtDiffVal1(Double top2OpenInterestPutBidAskAmtDiffVal1) {
		this.top2OpenInterestPutBidAskAmtDiffVal1 = top2OpenInterestPutBidAskAmtDiffVal1;
	}
	public Double getTop3OpenInterestCallBidVal1() {
		return top3OpenInterestCallBidVal1;
	}
	public void setTop3OpenInterestCallBidVal1(Double top3OpenInterestCallBidVal1) {
		this.top3OpenInterestCallBidVal1 = top3OpenInterestCallBidVal1;
	}
	public Double getTop3OpenInterestCallAskVal1() {
		return top3OpenInterestCallAskVal1;
	}
	public void setTop3OpenInterestCallAskVal1(Double top3OpenInterestCallAskVal1) {
		this.top3OpenInterestCallAskVal1 = top3OpenInterestCallAskVal1;
	}
	public Double getTop3OpenInterestCallBidAskDiffVal1() {
		return top3OpenInterestCallBidAskDiffVal1;
	}
	public void setTop3OpenInterestCallBidAskDiffVal1(Double top3OpenInterestCallBidAskDiffVal1) {
		this.top3OpenInterestCallBidAskDiffVal1 = top3OpenInterestCallBidAskDiffVal1;
	}
	public Double getTop3OpenInterestCallBidAskAmtDiffVal1() {
		return top3OpenInterestCallBidAskAmtDiffVal1;
	}
	public void setTop3OpenInterestCallBidAskAmtDiffVal1(Double top3OpenInterestCallBidAskAmtDiffVal1) {
		this.top3OpenInterestCallBidAskAmtDiffVal1 = top3OpenInterestCallBidAskAmtDiffVal1;
	}
	public Double getTop3OpenInterestPutBidVal1() {
		return top3OpenInterestPutBidVal1;
	}
	public void setTop3OpenInterestPutBidVal1(Double top3OpenInterestPutBidVal1) {
		this.top3OpenInterestPutBidVal1 = top3OpenInterestPutBidVal1;
	}
	public Double getTop3OpenInterestPutAskVal1() {
		return top3OpenInterestPutAskVal1;
	}
	public void setTop3OpenInterestPutAskVal1(Double top3OpenInterestPutAskVal1) {
		this.top3OpenInterestPutAskVal1 = top3OpenInterestPutAskVal1;
	}
	public Double getTop3OpenInterestPutBidAskDiffVal1() {
		return top3OpenInterestPutBidAskDiffVal1;
	}
	public void setTop3OpenInterestPutBidAskDiffVal1(Double top3OpenInterestPutBidAskDiffVal1) {
		this.top3OpenInterestPutBidAskDiffVal1 = top3OpenInterestPutBidAskDiffVal1;
	}
	public Double getTop3OpenInterestPutBidAskAmtDiffVal1() {
		return top3OpenInterestPutBidAskAmtDiffVal1;
	}
	public void setTop3OpenInterestPutBidAskAmtDiffVal1(Double top3OpenInterestPutBidAskAmtDiffVal1) {
		this.top3OpenInterestPutBidAskAmtDiffVal1 = top3OpenInterestPutBidAskAmtDiffVal1;
	}
	public Double getTop1ImpliedVolatilityCall() {
		return top1ImpliedVolatilityCall;
	}
	public void setTop1ImpliedVolatilityCall(Double top1ImpliedVolatilityCall) {
		this.top1ImpliedVolatilityCall = top1ImpliedVolatilityCall;
	}
	public Double getTop2ImpliedVolatilityCall() {
		return top2ImpliedVolatilityCall;
	}
	public void setTop2ImpliedVolatilityCall(Double top2ImpliedVolatilityCall) {
		this.top2ImpliedVolatilityCall = top2ImpliedVolatilityCall;
	}
	public Double getTop3ImpliedVolatilityCall() {
		return top3ImpliedVolatilityCall;
	}
	public void setTop3ImpliedVolatilityCall(Double top3ImpliedVolatilityCall) {
		this.top3ImpliedVolatilityCall = top3ImpliedVolatilityCall;
	}
	public Double getTop1ImpliedVolatilityPut() {
		return top1ImpliedVolatilityPut;
	}
	public void setTop1ImpliedVolatilityPut(Double top1ImpliedVolatilityPut) {
		this.top1ImpliedVolatilityPut = top1ImpliedVolatilityPut;
	}
	public Double getTop2ImpliedVolatilityPut() {
		return top2ImpliedVolatilityPut;
	}
	public void setTop2ImpliedVolatilityPut(Double top2ImpliedVolatilityPut) {
		this.top2ImpliedVolatilityPut = top2ImpliedVolatilityPut;
	}
	public Double getTop3ImpliedVolatilityPut() {
		return top3ImpliedVolatilityPut;
	}
	public void setTop3ImpliedVolatilityPut(Double top3ImpliedVolatilityPut) {
		this.top3ImpliedVolatilityPut = top3ImpliedVolatilityPut;
	}
	public Double getTotalImpliedVolatilityCall() {
		return totalImpliedVolatilityCall;
	}
	public void setTotalImpliedVolatilityCall(Double totalImpliedVolatilityCall) {
		this.totalImpliedVolatilityCall = totalImpliedVolatilityCall;
	}
	public Double getTotalImpliedVolatilityPut() {
		return totalImpliedVolatilityPut;
	}
	public void setTotalImpliedVolatilityPut(Double totalImpliedVolatilityPut) {
		this.totalImpliedVolatilityPut = totalImpliedVolatilityPut;
	}
	public String getTotalImpliedVolatilityCallStyle() {
		return totalImpliedVolatilityCallStyle;
	}
	public void setTotalImpliedVolatilityCallStyle(String totalImpliedVolatilityCallStyle) {
		this.totalImpliedVolatilityCallStyle = totalImpliedVolatilityCallStyle;
	}
	public String getTotalImpliedVolatilityPutStyle() {
		return totalImpliedVolatilityPutStyle;
	}
	public void setTotalImpliedVolatilityPutStyle(String totalImpliedVolatilityPutStyle) {
		this.totalImpliedVolatilityPutStyle = totalImpliedVolatilityPutStyle;
	}
	
	public String getTop1OpenInterestCallBidAskAmtDiffVal1Style() {
		String styleClass = "";
		if (null != getTop1OpenInterestCallBidAskAmtDiffVal1()
				&& getTop1OpenInterestCallBidAskAmtDiffVal1() > 0
				&& getTop1OpenInterestCallBidAskAmtDiffVal1() < 300) {
			styleClass = "bidAskStrongBuy";
		} else if (null != getTop1OpenInterestCallBidAskAmtDiffVal1()
				&& getTop1OpenInterestCallBidAskAmtDiffVal1() > 0
				&& getTop1OpenInterestCallBidAskAmtDiffVal1() < 500) {
			styleClass = "bidAskBuy";
		}
		return styleClass;
	}
	
	public String getTop2OpenInterestCallBidAskAmtDiffVal1Style() {
		String styleClass = "";
		if (null != getTop2OpenInterestCallBidAskAmtDiffVal1()
				&& getTop2OpenInterestCallBidAskAmtDiffVal1() > 0
				&& getTop2OpenInterestCallBidAskAmtDiffVal1() < 300) {
			styleClass = "bidAskStrongBuy";
		} else if (null != getTop2OpenInterestCallBidAskAmtDiffVal1()
				&& getTop2OpenInterestCallBidAskAmtDiffVal1() > 0
				&& getTop2OpenInterestCallBidAskAmtDiffVal1() < 500) {
			styleClass = "bidAskBuy";
		}
		return styleClass;
	}
	
	public String getTop3OpenInterestCallBidAskAmtDiffVal1Style() {
		String styleClass = "";
		if (null != getTop3OpenInterestCallBidAskAmtDiffVal1()
				&& getTop3OpenInterestCallBidAskAmtDiffVal1() > 0
				&& getTop3OpenInterestCallBidAskAmtDiffVal1() < 300) {
			styleClass = "bidAskStrongBuy";
		} else if (null != getTop3OpenInterestCallBidAskAmtDiffVal1()
				&& getTop3OpenInterestCallBidAskAmtDiffVal1() > 0
				&& getTop3OpenInterestCallBidAskAmtDiffVal1() < 500) {
			styleClass = "bidAskBuy";
		}
		return styleClass;
	}
	
	public String getTop1OpenInterestPutBidAskAmtDiffVal1Style() {
		String styleClass = "";
		if (null != getTop1OpenInterestPutBidAskAmtDiffVal1()
				&& getTop1OpenInterestPutBidAskAmtDiffVal1() > 0
				&& getTop1OpenInterestPutBidAskAmtDiffVal1() < 300) {
			styleClass = "bidAskStrongSell";
		} else if (null != getTop1OpenInterestPutBidAskAmtDiffVal1()
				&& getTop1OpenInterestPutBidAskAmtDiffVal1() > 0
				&& getTop1OpenInterestPutBidAskAmtDiffVal1() < 500) {
			styleClass ="bidAskSell";
		}
		return styleClass;
	}
	
	public String getTop2OpenInterestPutBidAskAmtDiffVal1Style() {
		String styleClass = "";
		if (null != getTop2OpenInterestPutBidAskAmtDiffVal1()
				&& getTop2OpenInterestPutBidAskAmtDiffVal1() > 0
				&& getTop2OpenInterestPutBidAskAmtDiffVal1() < 300) {
			styleClass = "bidAskStrongSell";
		} else if (null != getTop2OpenInterestPutBidAskAmtDiffVal1()
				&& getTop2OpenInterestPutBidAskAmtDiffVal1() > 0
				&& getTop2OpenInterestPutBidAskAmtDiffVal1() < 500) {
			styleClass ="bidAskSell";
		}
		return styleClass;
	}
	
	public String getTop3OpenInterestPutBidAskAmtDiffVal1Style() {
		String styleClass = "";
		if (null != getTop3OpenInterestPutBidAskAmtDiffVal1()
				&& getTop3OpenInterestPutBidAskAmtDiffVal1() > 0
				&& getTop3OpenInterestPutBidAskAmtDiffVal1() < 300) {
			styleClass = "bidAskStrongSell";
		} else if (null != getTop3OpenInterestPutBidAskAmtDiffVal1()
				&& getTop3OpenInterestPutBidAskAmtDiffVal1() > 0
				&& getTop3OpenInterestPutBidAskAmtDiffVal1() < 500) {
			styleClass ="bidAskSell";
		}
		return styleClass;
	}
}