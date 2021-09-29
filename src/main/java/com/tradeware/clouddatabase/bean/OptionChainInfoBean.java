package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class OptionChainInfoBean  extends AbstractBean {
	
	private static final long serialVersionUID = -6319018652373043966L;

	private Integer optionChainId;
	private String symbolId;
	private Integer lotSize;
	private Double tickerSize;
	private Boolean indexInd = Boolean.FALSE;
	private String style;
	private String oITrend;
	private Double top1OpenInterestCall;
	private Double top2OpenInterestCall;
	private Double top3OpenInterestCall;
	private Double top1OpenInterestChangeCall;
	private Double top2OpenInterestChangeCall;
	private Double top3OpenInterestChangeCall;
	private Double top1OIVolumesCall;
	private Double top2OIVolumesCall;
	private Double top3OIVolumesCall;
	private Double top1OINetChangeCall;
	private Double top2OINetChangeCall;
	private Double top3OINetChangeCall;
	private Double top1StrikePriceCall;
	private Double top2StrikePriceCall;
	private Double top3StrikePriceCall;

	// PUTs
	private Double top1OpenInterestPut;
	private Double top2OpenInterestPut;
	private Double top3OpenInterestPut;
	private Double top1OpenInterestChangePut;
	private Double top2OpenInterestChangePut;
	private Double top3OpenInterestChangePut;
	private Double top1OIVolumesPut;
	private Double top2OIVolumesPut;
	private Double top3OIVolumesPut;
	private Double top1OINetChangePut;
	private Double top2OINetChangePut;
	private Double top3OINetChangePut;
	private Double top1StrikePricePut;
	private Double top2StrikePricePut;
	private Double top3StrikePricePut;
	private Double totalOpenInterestCall;
	private Double totalOpenInterestPut;
	private Double totalOIVolumesCall;
	private Double totalOIVolumesPut;
	
	//OI Spruts
	private Double day1OpenInterest;
	private Double day2OpenInterest;
	private Double openInterestChange;
	private Double openInterestChangePercentage;
	private Double openInterestVolumes;//contracts
	private Double underlyingPrice;//current market price  value
	private Integer timeFrameNumber;
	private Date dateTimeStamp;
	private Date dateStamp;
	private Boolean bestTradeInd;
	
	private Double strongOrder;
	private Double ltpOnDecision;
	private Double lastPrice;
	private Double bestEntry;
	private String goForTrade;
	// private Boolean playanleInd = Boolean.FALSE;
	private Double changePercentage;
	private Integer candleNumber;
	private Boolean parentRecordInd;
	
	
	private String top1OpenInterestCallStyle;
	private String top2OpenInterestCallStyle;
	private String top3OpenInterestCallStyle;
	private String top1OpenInterestPutStyle;
	private String top2OpenInterestPutStyle;
	private String top3OpenInterestPutStyle;
	private String attentionStyleBuy;
	private String attentionStyleSell;
	
	private Double totalOINetChangeCall;
	private Double totalOINetChangePut;
	private String priceMoveTrend; 
	private String totalOINetChangeCallStyle;
	private String totalOINetChangePutStyle;
	
	private Double totalOpenInterestChangeCall;
	private Double totalOpenInterestChangePut;
	private String priceOpenIntrestChangeTrend; 
	private String totalOpenInterestChangeCallStyle;
	private String totalOpenInterestChangePutStyle;
	
	private Integer sortOrder;
	private Integer topTenGainLooseSortOrder;
	private Double yearHigh;
	private Double yearLow;
	
	private Double putCallRatio;
	private String putCallRatioStyleClass;
	
	
	
	//TODO123 start
	private Double top1OpenInterestCallBidVal1;
	private Double top1OpenInterestCallAskVal1;
	private Double top1OpenInterestCallBidAskDiffVal1;
	private Double top1OpenInterestCallBidAskAmtDiffVal1;
	private Double top1OpenInterestPutBidVal1;
	private Double top1OpenInterestPutAskVal1;
	private Double top1OpenInterestPutBidAskDiffVal1;
	private Double top1OpenInterestPutBidAskDiffAmtVal1;
	private Double top2OpenInterestCallBidVal1;
	private Double top2OpenInterestCallAskVal1;
	private Double top2OpenInterestCallBidAskDiffVal1;
	private Double top2OpenInterestCallBidAskAmtDiffVal1;
	private Double top2OpenInterestPutBidVal1;
	private Double top2OpenInterestPutAskVal1;
	private Double top2OpenInterestPutBidAskDiffVal1;
	private Double top2OpenInterestPutBidAskDiffAmtVal1;
	private Double top3OpenInterestCallBidVal1;
	private Double top3OpenInterestCallAskVal1;
	private Double top3OpenInterestCallBidAskDiffVal1;
	private Double top3OpenInterestCallBidAskAmtDiffVal1;
	private Double top3OpenInterestPutBidVal1;
	private Double top3OpenInterestPutAskVal1;
	private Double top3OpenInterestPutBidAskDiffVal1;
	private Double top3OpenInterestPutBidAskDiffAmtVal1;
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
	//TODO123 end
	
	public Integer getOptionChainId() {
		return optionChainId;
	}
	public void setOptionChainId(Integer optionChainId) {
		this.optionChainId = optionChainId;
	}
	public String getSymbolId() {
		return symbolId;
	}
	public void setSymbolId(String symbolId) {
		this.symbolId = symbolId;
	}
	public Integer getLotSize() {
		return lotSize;
	}
	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}
	public Double getTickerSize() {
		return tickerSize;
	}
	public void setTickerSize(Double tickerSize) {
		this.tickerSize = tickerSize;
	}
	public Boolean getIndexInd() {
		return indexInd;
	}
	public void setIndexInd(Boolean indexInd) {
		this.indexInd = indexInd;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getOITrend() {
		return oITrend;
	}
	public void setOITrend(String oITrend) {
		this.oITrend = oITrend;
	}
	public Double getTop1OpenInterestCall() {
		return top1OpenInterestCall;
	}
	public void setTop1OpenInterestCall(Double top1OpenInterestCall) {
		this.top1OpenInterestCall = top1OpenInterestCall;
	}
	public Double getTop2OpenInterestCall() {
		return top2OpenInterestCall;
	}
	public void setTop2OpenInterestCall(Double top2OpenInterestCall) {
		this.top2OpenInterestCall = top2OpenInterestCall;
	}
	public Double getTop3OpenInterestCall() {
		return top3OpenInterestCall;
	}
	public void setTop3OpenInterestCall(Double top3OpenInterestCall) {
		this.top3OpenInterestCall = top3OpenInterestCall;
	}
	public Double getTop1OpenInterestChangeCall() {
		return top1OpenInterestChangeCall;
	}
	public void setTop1OpenInterestChangeCall(Double top1OpenInterestChangeCall) {
		this.top1OpenInterestChangeCall = top1OpenInterestChangeCall;
	}
	public Double getTop2OpenInterestChangeCall() {
		return top2OpenInterestChangeCall;
	}
	public void setTop2OpenInterestChangeCall(Double top2OpenInterestChangeCall) {
		this.top2OpenInterestChangeCall = top2OpenInterestChangeCall;
	}
	public Double getTop3OpenInterestChangeCall() {
		return top3OpenInterestChangeCall;
	}
	public void setTop3OpenInterestChangeCall(Double top3OpenInterestChangeCall) {
		this.top3OpenInterestChangeCall = top3OpenInterestChangeCall;
	}
	public Double getTop1OIVolumesCall() {
		return top1OIVolumesCall;
	}
	public void setTop1OIVolumesCall(Double top1oiVolumesCall) {
		top1OIVolumesCall = top1oiVolumesCall;
	}
	public Double getTop2OIVolumesCall() {
		return top2OIVolumesCall;
	}
	public void setTop2OIVolumesCall(Double top2oiVolumesCall) {
		top2OIVolumesCall = top2oiVolumesCall;
	}
	public Double getTop3OIVolumesCall() {
		return top3OIVolumesCall;
	}
	public void setTop3OIVolumesCall(Double top3oiVolumesCall) {
		top3OIVolumesCall = top3oiVolumesCall;
	}
	public Double getTop1OINetChangeCall() {
		return top1OINetChangeCall;
	}
	public void setTop1OINetChangeCall(Double top1oiNetChangeCall) {
		top1OINetChangeCall = top1oiNetChangeCall;
	}
	public Double getTop2OINetChangeCall() {
		return top2OINetChangeCall;
	}
	public void setTop2OINetChangeCall(Double top2oiNetChangeCall) {
		top2OINetChangeCall = top2oiNetChangeCall;
	}
	public Double getTop3OINetChangeCall() {
		return top3OINetChangeCall;
	}
	public void setTop3OINetChangeCall(Double top3oiNetChangeCall) {
		top3OINetChangeCall = top3oiNetChangeCall;
	}
	public Double getTop1StrikePriceCall() {
		return top1StrikePriceCall;
	}
	public void setTop1StrikePriceCall(Double top1StrikePriceCall) {
		this.top1StrikePriceCall = top1StrikePriceCall;
	}
	public Double getTop2StrikePriceCall() {
		return top2StrikePriceCall;
	}
	public void setTop2StrikePriceCall(Double top2StrikePriceCall) {
		this.top2StrikePriceCall = top2StrikePriceCall;
	}
	public Double getTop3StrikePriceCall() {
		return top3StrikePriceCall;
	}
	public void setTop3StrikePriceCall(Double top3StrikePriceCall) {
		this.top3StrikePriceCall = top3StrikePriceCall;
	}
	public Double getTop1OpenInterestPut() {
		return top1OpenInterestPut;
	}
	public void setTop1OpenInterestPut(Double top1OpenInterestPut) {
		this.top1OpenInterestPut = top1OpenInterestPut;
	}
	public Double getTop2OpenInterestPut() {
		return top2OpenInterestPut;
	}
	public void setTop2OpenInterestPut(Double top2OpenInterestPut) {
		this.top2OpenInterestPut = top2OpenInterestPut;
	}
	public Double getTop3OpenInterestPut() {
		return top3OpenInterestPut;
	}
	public void setTop3OpenInterestPut(Double top3OpenInterestPut) {
		this.top3OpenInterestPut = top3OpenInterestPut;
	}
	public Double getTop1OpenInterestChangePut() {
		return top1OpenInterestChangePut;
	}
	public void setTop1OpenInterestChangePut(Double top1OpenInterestChangePut) {
		this.top1OpenInterestChangePut = top1OpenInterestChangePut;
	}
	public Double getTop2OpenInterestChangePut() {
		return top2OpenInterestChangePut;
	}
	public void setTop2OpenInterestChangePut(Double top2OpenInterestChangePut) {
		this.top2OpenInterestChangePut = top2OpenInterestChangePut;
	}
	public Double getTop3OpenInterestChangePut() {
		return top3OpenInterestChangePut;
	}
	public void setTop3OpenInterestChangePut(Double top3OpenInterestChangePut) {
		this.top3OpenInterestChangePut = top3OpenInterestChangePut;
	}
	public Double getTop1OIVolumesPut() {
		return top1OIVolumesPut;
	}
	public void setTop1OIVolumesPut(Double top1oiVolumesPut) {
		top1OIVolumesPut = top1oiVolumesPut;
	}
	public Double getTop2OIVolumesPut() {
		return top2OIVolumesPut;
	}
	public void setTop2OIVolumesPut(Double top2oiVolumesPut) {
		top2OIVolumesPut = top2oiVolumesPut;
	}
	public Double getTop3OIVolumesPut() {
		return top3OIVolumesPut;
	}
	public void setTop3OIVolumesPut(Double top3oiVolumesPut) {
		top3OIVolumesPut = top3oiVolumesPut;
	}
	public Double getTop1OINetChangePut() {
		return top1OINetChangePut;
	}
	public void setTop1OINetChangePut(Double top1oiNetChangePut) {
		top1OINetChangePut = top1oiNetChangePut;
	}
	public Double getTop2OINetChangePut() {
		return top2OINetChangePut;
	}
	public void setTop2OINetChangePut(Double top2oiNetChangePut) {
		top2OINetChangePut = top2oiNetChangePut;
	}
	public Double getTop3OINetChangePut() {
		return top3OINetChangePut;
	}
	public void setTop3OINetChangePut(Double top3oiNetChangePut) {
		top3OINetChangePut = top3oiNetChangePut;
	}
	public Double getTop1StrikePricePut() {
		return top1StrikePricePut;
	}
	public void setTop1StrikePricePut(Double top1StrikePricePut) {
		this.top1StrikePricePut = top1StrikePricePut;
	}
	public Double getTop2StrikePricePut() {
		return top2StrikePricePut;
	}
	public void setTop2StrikePricePut(Double top2StrikePricePut) {
		this.top2StrikePricePut = top2StrikePricePut;
	}
	public Double getTop3StrikePricePut() {
		return top3StrikePricePut;
	}
	public void setTop3StrikePricePut(Double top3StrikePricePut) {
		this.top3StrikePricePut = top3StrikePricePut;
	}
	public Double getTotalOpenInterestCall() {
		return totalOpenInterestCall;
	}
	public void setTotalOpenInterestCall(Double totalOpenInterestCall) {
		this.totalOpenInterestCall = totalOpenInterestCall;
	}
	public Double getTotalOpenInterestPut() {
		return totalOpenInterestPut;
	}
	public void setTotalOpenInterestPut(Double totalOpenInterestPut) {
		this.totalOpenInterestPut = totalOpenInterestPut;
	}
	public Double getTotalOIVolumesCall() {
		return totalOIVolumesCall;
	}
	public void setTotalOIVolumesCall(Double totalOIVolumesCall) {
		this.totalOIVolumesCall = totalOIVolumesCall;
	}
	public Double getTotalOIVolumesPut() {
		return totalOIVolumesPut;
	}
	public void setTotalOIVolumesPut(Double totalOIVolumesPut) {
		this.totalOIVolumesPut = totalOIVolumesPut;
	}
	public Double getDay1OpenInterest() {
		return day1OpenInterest;
	}
	public void setDay1OpenInterest(Double day1OpenInterest) {
		this.day1OpenInterest = day1OpenInterest;
	}
	public Double getDay2OpenInterest() {
		return day2OpenInterest;
	}
	public void setDay2OpenInterest(Double day2OpenInterest) {
		this.day2OpenInterest = day2OpenInterest;
	}
	public Double getOpenInterestChange() {
		return openInterestChange;
	}
	public void setOpenInterestChange(Double openInterestChange) {
		this.openInterestChange = openInterestChange;
	}
	public Double getOpenInterestChangePercentage() {
		return openInterestChangePercentage;
	}
	public void setOpenInterestChangePercentage(Double openInterestChangePercentage) {
		this.openInterestChangePercentage = openInterestChangePercentage;
	}
	public Double getOpenInterestVolumes() {
		return openInterestVolumes;
	}
	public void setOpenInterestVolumes(Double openInterestVolumes) {
		this.openInterestVolumes = openInterestVolumes;
	}
	public Double getUnderlyingPrice() {
		return underlyingPrice;
	}
	public void setUnderlyingPrice(Double underlyingPrice) {
		this.underlyingPrice = underlyingPrice;
	}
	public Integer getTimeFrameNumber() {
		return timeFrameNumber;
	}
	public void setTimeFrameNumber(Integer timeFrameNumber) {
		this.timeFrameNumber = timeFrameNumber;
	}
	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}
	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}
	public Date getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
	
	public Boolean getBestTradeInd() {
		return bestTradeInd;
	}

	public void setBestTradeInd(Boolean bestTradeInd) {
		this.bestTradeInd = bestTradeInd;
	}
	public String getoITrend() {
		return oITrend;
	}
	public void setoITrend(String oITrend) {
		this.oITrend = oITrend;
	}
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
	public Double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
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
	public Double getTotalOINetChangeCall() {
		return totalOINetChangeCall;
	}
	public void setTotalOINetChangeCall(Double totalOINetChangeCall) {
		this.totalOINetChangeCall = totalOINetChangeCall;
	}
	public Double getTotalOINetChangePut() {
		return totalOINetChangePut;
	}
	public void setTotalOINetChangePut(Double totalOINetChangePut) {
		this.totalOINetChangePut = totalOINetChangePut;
	}
	public String getPriceMoveTrend() {
		return priceMoveTrend;
	}
	public void setPriceMoveTrend(String priceMoveTrend) {
		this.priceMoveTrend = priceMoveTrend;
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
	public Double getTotalOpenInterestChangeCall() {
		return totalOpenInterestChangeCall;
	}
	public void setTotalOpenInterestChangeCall(Double totalOpenInterestChangeCall) {
		this.totalOpenInterestChangeCall = totalOpenInterestChangeCall;
	}
	public Double getTotalOpenInterestChangePut() {
		return totalOpenInterestChangePut;
	}
	public void setTotalOpenInterestChangePut(Double totalOpenInterestChangePut) {
		this.totalOpenInterestChangePut = totalOpenInterestChangePut;
	}
	public String getPriceOpenIntrestChangeTrend() {
		return priceOpenIntrestChangeTrend;
	}
	public void setPriceOpenIntrestChangeTrend(String priceOpenIntrestChangeTrend) {
		this.priceOpenIntrestChangeTrend = priceOpenIntrestChangeTrend;
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
	public Double getTop1OpenInterestPutBidAskDiffAmtVal1() {
		return top1OpenInterestPutBidAskDiffAmtVal1;
	}
	public void setTop1OpenInterestPutBidAskDiffAmtVal1(Double top1OpenInterestPutBidAskDiffAmtVal1) {
		this.top1OpenInterestPutBidAskDiffAmtVal1 = top1OpenInterestPutBidAskDiffAmtVal1;
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
	public Double getTop2OpenInterestPutBidAskDiffAmtVal1() {
		return top2OpenInterestPutBidAskDiffAmtVal1;
	}
	public void setTop2OpenInterestPutBidAskDiffAmtVal1(Double top2OpenInterestPutBidAskDiffAmtVal1) {
		this.top2OpenInterestPutBidAskDiffAmtVal1 = top2OpenInterestPutBidAskDiffAmtVal1;
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
	public Double getTop3OpenInterestPutBidAskDiffAmtVal1() {
		return top3OpenInterestPutBidAskDiffAmtVal1;
	}
	public void setTop3OpenInterestPutBidAskDiffAmtVal1(Double top3OpenInterestPutBidAskDiffAmtVal1) {
		this.top3OpenInterestPutBidAskDiffAmtVal1 = top3OpenInterestPutBidAskDiffAmtVal1;
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
}
