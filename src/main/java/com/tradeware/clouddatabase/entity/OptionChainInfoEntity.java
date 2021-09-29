package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.OptionChainInfoBean;
@Entity
@Table(name = "T_OPTION_CHAIN")
public class OptionChainInfoEntity  extends AbstractEntity {
	private static final long serialVersionUID = -8490147799338185256L;
	
	@Id
	@Column(name = "OPTION_CHAIN_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer optionChainId;
	
	@Column(name = "SYMBOL_ID")
	private String symbolId;

	@Column(name = "LOT_SIZE")
	private Integer lotSize;
	
	@Column(name = "OPT_TICK_SIZE")
	private Double tickerSize;
	
	@Column(name = "INDEX_IND")
	private Boolean indexInd = Boolean.FALSE;
	
	@Column(name = "STYLE")
	private String style;
	
	@Column(name = "OI_TREND")
	private String oiTrend;//oITrend;

	// CALLS
	@Column(name = "OI_CALL_1")
	private Double top1OpenInterestCall;
	
	@Column(name = "OI_CALL_2")
	private Double top2OpenInterestCall;
	
	@Column(name = "OI_CALL_3")
	private Double top3OpenInterestCall;

	@Column(name = "OI_CALL_CHANGE_1")
	private Double top1OpenInterestChangeCall;
	
	@Column(name = "OI_CALL_CHANGE_2")
	private Double top2OpenInterestChangeCall;
	
	@Column(name = "OI_CALL_CHANGE_3")
	private Double top3OpenInterestChangeCall;

	@Column(name = "OI_CALL_VOLUME_1")
	private Double top1OIVolumesCall;
	
	@Column(name = "OI_CALL_VOLUME_2")
	private Double top2OIVolumesCall;
	
	@Column(name = "OI_CALL_VOLUME_3")
	private Double top3OIVolumesCall;

	@Column(name = "OI_CALL_NET_CHANGE_1")
	private Double top1OINetChangeCall;
	
	@Column(name = "OI_CALL_NET_CHANGE_2")
	private Double top2OINetChangeCall;
	
	@Column(name = "OI_CALL_NET_CHANGE_3")
	private Double top3OINetChangeCall;

	@Column(name = "OI_CALL_STRIKE_1")
	private Double top1StrikePriceCall;
	
	@Column(name = "OI_CALL_STRIKE_2")
	private Double top2StrikePriceCall;
	
	@Column(name = "OI_CALL_STRIKE_3")
	private Double top3StrikePriceCall;

	// PUTs
	@Column(name = "OI_PUT_1")
	private Double top1OpenInterestPut;
	
	@Column(name = "OI_PUT_2")
	private Double top2OpenInterestPut;
	
	@Column(name = "OI_PUT_3")
	private Double top3OpenInterestPut;

	@Column(name = "OI_PUT_CHANGE_1")
	private Double top1OpenInterestChangePut;
	
	@Column(name = "OI_PUT_CHANGE_2")
	private Double top2OpenInterestChangePut;
	
	@Column(name = "OI_PUT_CHANGE_3")
	private Double top3OpenInterestChangePut;

	@Column(name = "OI_PUT_VOLUME_1")
	private Double top1OIVolumesPut;
	
	@Column(name = "OI_PUT_VOLUME_2")
	private Double top2OIVolumesPut;
	
	@Column(name = "OI_PUT_VOLUME_3")
	private Double top3OIVolumesPut;

	@Column(name = "OI_PUT_NET_CHANGE_1")
	private Double top1OINetChangePut;
	
	@Column(name = "OI_PUT_NET_CHANGE_2")
	private Double top2OINetChangePut;
	
	@Column(name = "OI_PUT_NET_CHANGE_3")
	private Double top3OINetChangePut;

	@Column(name = "OI_PUT_STRIKE_1")
	private Double top1StrikePricePut;
	
	@Column(name = "OI_PUT_STRIKE_2")
	private Double top2StrikePricePut;
	
	@Column(name = "OI_PUT_STRIKE_3")
	private Double top3StrikePricePut;
	
	@Column(name = "OI_CALL_TOTAL")
	private Double totalOpenInterestCall;
	
	@Column(name = "OI_PUT_TOTAL")
	private Double totalOpenInterestPut;
	
	@Column(name = "OI_CALL_TOTAL_VOLUMES")
	private Double totalOIVolumesCall;
	
	@Column(name = "OI_PUT_TOTAL_VOLUMES")
	private Double totalOIVolumesPut;
	
	//OI Spruts
	@Column(name = "OI_DAY_1")
	private Double day1OpenInterest;
	
	@Column(name = "OI_DAY_2")
	private Double day2OpenInterest;
	
	@Column(name = "OI_CHANGE")
	private Double openInterestChange;
	
	@Column(name = "OI_CHANGE_PERCENT")
	private Double openInterestChangePercentage;
	
	@Column(name = "OI_VOLUMES")
	private Double openInterestVolumes;//contracts
	
	@Column(name = "OI_LTP")
	private Double underlyingPrice;//current market price  value
	
	@Column(name = "TIME_FRAME_NUM")
	private Integer timeFrameNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_TM_STAMP")
	private Date dateTimeStamp;
	
	@Column(name = "DT_STAMP")
	@Temporal(TemporalType.DATE)
	private Date dateStamp;
	
	@Column(name = "BEST_TRADE_IND")
	private Boolean bestTradeInd;
	
	@Column(name = "DISPLAY_SORT_ORDER")
	private Double strongOrder;

	@Column(name = "LTP_ON_DECISION")
	private Double ltpOnDecision;

	@Column(name = "MKT_PRICE")
	private Double lastPrice;

	@Column(name = "BEST_ENTRY")
	private Double bestEntry;

	@Column(name = "GO_TRADE_ENTRY")
	private String goForTrade;

	// private Boolean playanleInd = Boolean.FALSE;
	@Column(name = "CHANGE_PERCENTAGE")
	private Double changePercentage;
	
	@Column(name = "CANDLE_NUMBER")
	private Integer candleNumber;
	
	@Column(name = "PARENT_RECORD_IND")
	private Boolean parentRecordInd;
	
	@Column(name = "OI_CALL_1_STYLE")
	private String top1OpenInterestCallStyle;
	
	@Column(name = "OI_CALL_2_STYLE")
	private String top2OpenInterestCallStyle;
	
	@Column(name = "OI_CALL_3_STYLE")
	private String top3OpenInterestCallStyle;

	@Column(name = "OI_PUT_1_STYLE")
	private String top1OpenInterestPutStyle;
	
	@Column(name = "OI_PUT_2_STYLE")
	private String top2OpenInterestPutStyle;
	
	@Column(name = "OI_PUT_3_STYLE")
	private String top3OpenInterestPutStyle;
	
	@Column(name = "ATTENTION_STYLE_BUY")
	private String attentionStyleBuy;
	
	@Column(name = "ATTENTION_STYLE_SELL")
	private String attentionStyleSell;
	
	@Column(name = "OI_CALL_NET_CHANGE_TOTAL")
	private Double totalOINetChangeCall;
	
	@Column(name = "OI_PUT_NET_CHANGE_TOTAL")
	private Double totalOINetChangePut;
	
	@Column(name = "PRICE_MOVE_TREND")
	private String priceMoveTrend; 
	
	@Column(name = "OI_CALL_NET_CHANGE_TOTAL_STYLE")
	private String totalOINetChangeCallStyle;
	
	@Column(name = "OI_PUT_NET_CHANGE_TOTAL_STYLE")
	private String totalOINetChangePutStyle;
	
	@Column(name = "OI_CALL_CHANGE_TOTAL")
	private Double totalOpenInterestChangeCall;
	
	@Column(name = "OI_PUT_CHANGE_TOTAL")
	private Double totalOpenInterestChangePut;
	
	@Column(name = "PRICE_MOVE_OI_CHANGE_TREND")
	private String priceOpenIntrestChangeTrend; 
	
	@Column(name = "OI_CALL_CHANGE_TOTAL_STYLE")
	private String totalOpenInterestChangeCallStyle;
	
	@Column(name = "OI_PUT_CHANGE_TOTAL_STYLE")
	private String totalOpenInterestChangePutStyle;
	
	@Column(name = "GAIN_LOSE_ORDER")
	private Integer sortOrder;
	
	@Column(name = "TOP_GAIN_LOSE_ORDER")
	private Integer topTenGainLooseSortOrder;
	
	@Column(name = "YEAR_HIGH")
	private Double yearHigh;
	
	@Column(name = "YEAR_LOW")
	private Double yearLow;
	
	@Column(name = "PUT_CALL_RATIO")
	private Double putCallRatio;
	
	@Column(name = "PUT_CALL_RATIO_STYLE_CLS")
	private String putCallRatioStyleClass;
	
	// TODO123 start
		// CALLS
		@Column(name = "OI_BID_1_CALL_1")
		private Double top1OpenInterestCallBidVal1;

		@Column(name = "OI_ASK_1_CALL_1")
		private Double top1OpenInterestCallAskVal1;
		
		@Column(name = "OI_BID_ASK_DIFF_1_CALL_1")
		private Double top1OpenInterestCallBidAskDiffVal1;
		
		@Column(name = "OI_BID_ASK_DIFF_1_CALL_AMT_1")
		private Double top1OpenInterestCallBidAskAmtDiffVal1;
		
		@Column(name = "OI_BID_1_PUT_1")
		private Double top1OpenInterestPutBidVal1;

		@Column(name = "OI_ASK_1_PUT_1")
		private Double top1OpenInterestPutAskVal1;
		
		@Column(name = "OI_BID_ASK_DIFF_1_PUT_1")
		private Double top1OpenInterestPutBidAskDiffVal1;
		
		@Column(name = "OI_BID_ASK_DIFF_1_PUT_AMT_1")
		private Double top1OpenInterestPutBidAskDiffAmtVal1;
		
		
		@Column(name = "OI_BID_2_CALL_1")
		private Double top2OpenInterestCallBidVal1;

		@Column(name = "OI_ASK_2_CALL_1")
		private Double top2OpenInterestCallAskVal1;
		
		@Column(name = "OI_BID_ASK_DIFF_2_CALL_1")
		private Double top2OpenInterestCallBidAskDiffVal1;
		
		@Column(name = "OI_BID_ASK_DIFF_2_CALL_AMT_1")
		private Double top2OpenInterestCallBidAskAmtDiffVal1;
		
		@Column(name = "OI_BID_2_PUT_1")
		private Double top2OpenInterestPutBidVal1;

		@Column(name = "OI_ASK_2_PUT_1")
		private Double top2OpenInterestPutAskVal1;
		
		@Column(name = "OI_BID_ASK_DIFF_2_PUT_1")
		private Double top2OpenInterestPutBidAskDiffVal1;

		@Column(name = "OI_BID_ASK_DIFF_2_PUT_AMT_1")
		private Double top2OpenInterestPutBidAskDiffAmtVal1;
		
		@Column(name = "OI_BID_3_CALL_1")
		private Double top3OpenInterestCallBidVal1;

		@Column(name = "OI_ASK_3_CALL_1")
		private Double top3OpenInterestCallAskVal1;
		
		@Column(name = "OI_BID_ASK_DIFF_3_CALL_1")
		private Double top3OpenInterestCallBidAskDiffVal1;

		@Column(name = "OI_BID_ASK_DIFF_3_CALL_AMT_1")
		private Double top3OpenInterestCallBidAskAmtDiffVal1;
		
		@Column(name = "OI_BID_3_PUT_1")
		private Double top3OpenInterestPutBidVal1;

		@Column(name = "OI_ASK_3_PUT_1")
		private Double top3OpenInterestPutAskVal1;
		
		@Column(name = "OI_BID_ASK_DIFF_3_PUT_1")
		private Double top3OpenInterestPutBidAskDiffVal1;

		@Column(name = "OI_BID_ASK_DIFF_3_PUT_AMT_1")
		private Double top3OpenInterestPutBidAskDiffAmtVal1;
	
	@Column(name = "OI_CALL_IV_1")
	private Double top1ImpliedVolatilityCall;
	
	@Column(name = "OI_CALL_IV_2")
	private Double top2ImpliedVolatilityCall;
	
	@Column(name = "OI_CALL_IV_3")
	private Double top3ImpliedVolatilityCall;
	
	@Column(name = "OI_PUT_IV_1")
	private Double top1ImpliedVolatilityPut;
	
	@Column(name = "OI_PUT_IV_2")
	private Double top2ImpliedVolatilityPut;
	
	@Column(name = "OI_PUT_IV_3")
	private Double top3ImpliedVolatilityPut;
	
	@Column(name = "OI_CALL_IV_TOTAL")
	private Double totalImpliedVolatilityCall;
	
	@Column(name = "OI_PUT_IV_TOTAL")
	private Double totalImpliedVolatilityPut;
	
	@Column(name = "OI_CALL_IV_TOTAL_STYLE")
	private String totalImpliedVolatilityCallStyle;
	
	@Column(name = "OI_PUT_IV_TOTAL_STYLE")
	private String totalImpliedVolatilityPutStyle;

	// TODO123 end
	
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
/*
	public String getoITrend() {
		return oITrend;
	}

	public void setoITrend(String oITrend) {
		this.oITrend = oITrend;
	}*/

	public Double getTop1OpenInterestCall() {
		return top1OpenInterestCall;
	}

	public String getOiTrend() {
		return oiTrend;
	}

	public void setOiTrend(String oiTrend) {
		this.oiTrend = oiTrend;
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
	
	public Double getLtpOnDecision() {
		return ltpOnDecision;
	}

	public void setLtpOnDecision(Double ltpOnDecision) {
		this.ltpOnDecision = ltpOnDecision;
	}
	
	public Double getStrongOrder() {
		return strongOrder;
	}
	public void setStrongOrder(Double strongOrder) {
		this.strongOrder = strongOrder;
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

	public void populateEntity(OptionChainInfoBean bean) {
		this.optionChainId = bean.getOptionChainId();
		this.symbolId = bean.getSymbolId();
		this.lotSize = bean.getLotSize();
		this.tickerSize = bean.getTickerSize();
		this.setStyle(bean.getStyle());
		//this.setoITrend(bean.getOITrend());
		this.setOiTrend(bean.getOITrend());
		this.setIndexInd(bean.getIndexInd());
		this.top1OpenInterestCall = bean.getTop1OpenInterestCall();
		this.top2OpenInterestCall = bean.getTop2OpenInterestCall();
		this.top3OpenInterestCall = bean.getTop3OpenInterestCall();
		this.top1OpenInterestChangeCall = bean.getTop1OpenInterestChangeCall();
		this.top2OpenInterestChangeCall = bean.getTop2OpenInterestChangeCall();
		this.top3OpenInterestChangeCall = bean.getTop3OpenInterestChangeCall();
		this.top1OIVolumesCall = bean.getTop1OIVolumesCall();
		this.top2OIVolumesCall = bean.getTop2OIVolumesCall();
		this.top3OIVolumesCall = bean.getTop3OIVolumesCall();
		this.top1OINetChangeCall = bean.getTop1OINetChangeCall();
		this.top2OINetChangeCall = bean.getTop2OINetChangeCall();
		this.top3OINetChangeCall = bean.getTop3OINetChangeCall();
		this.top1StrikePriceCall = bean.getTop1StrikePriceCall();
		this.top2StrikePriceCall = bean.getTop2StrikePriceCall();
		this.top3StrikePriceCall = bean.getTop3StrikePriceCall();
		this.top1OpenInterestPut = bean.getTop1OpenInterestPut();
		this.top2OpenInterestPut = bean.getTop2OpenInterestPut();
		this.top3OpenInterestPut = bean.getTop3OpenInterestPut();
		this.top1OpenInterestChangePut = bean.getTop1OpenInterestChangePut();
		this.top2OpenInterestChangePut = bean.getTop2OpenInterestChangePut();
		this.top3OpenInterestChangePut = bean.getTop3OpenInterestChangePut();
		this.top1OIVolumesPut = bean.getTop1OIVolumesPut();
		this.top2OIVolumesPut = bean.getTop2OIVolumesPut();
		this.top3OIVolumesPut = bean.getTop3OIVolumesPut();
		this.top1OINetChangePut = bean.getTop1OINetChangePut();
		this.top2OINetChangePut = bean.getTop2OINetChangePut();
		this.top3OINetChangePut = bean.getTop3OINetChangePut();
		this.top1StrikePricePut = bean.getTop1StrikePricePut();
		this.top2StrikePricePut = bean.getTop2StrikePricePut();
		this.top3StrikePricePut = bean.getTop3StrikePricePut();
		this.totalOpenInterestCall = bean.getTotalOpenInterestCall();
		this.totalOpenInterestPut = bean.getTotalOpenInterestPut();
		this.totalOIVolumesCall = bean.getTotalOIVolumesCall();
		this.totalOIVolumesPut = bean.getTotalOIVolumesPut();
		this.day1OpenInterest = bean.getDay1OpenInterest();
		this.day2OpenInterest = bean.getDay2OpenInterest();
		this.openInterestChange = bean.getOpenInterestChange();
		this.openInterestChangePercentage = bean.getOpenInterestChangePercentage();
		this.openInterestVolumes = bean.getOpenInterestVolumes();// contracts
		this.underlyingPrice = bean.getUnderlyingPrice();// current market price value
		this.timeFrameNumber = bean.getTimeFrameNumber();
		this.dateTimeStamp = bean.getDateTimeStamp();
		this.dateStamp = bean.getDateStamp();
		this.bestTradeInd = bean.getBestTradeInd();
		this.strongOrder = bean.getStrongOrder();
		this.ltpOnDecision = bean.getLtpOnDecision();
		this.lastPrice = bean.getLastPrice();
		this.bestEntry = bean.getBestEntry();
		this.goForTrade = bean.getGoForTrade();
		this.changePercentage = bean.getChangePercentage();
		this.candleNumber = bean.getCandleNumber();
		this.parentRecordInd = bean.getParentRecordInd();
		this.top1OpenInterestCallStyle = bean.getTop1OpenInterestCallStyle();
		this.top2OpenInterestCallStyle = bean.getTop2OpenInterestCallStyle();
		this.top3OpenInterestCallStyle = bean.getTop3OpenInterestCallStyle();
		this.top1OpenInterestPutStyle = bean.getTop1OpenInterestPutStyle();
		this.top2OpenInterestPutStyle = bean.getTop2OpenInterestPutStyle();
		this.top3OpenInterestPutStyle = bean.getTop3OpenInterestPutStyle();
		this.attentionStyleBuy = bean.getAttentionStyleBuy();
		this.attentionStyleSell = bean.getAttentionStyleSell();
		
		this.totalOINetChangeCall = bean.getTotalOINetChangeCall();
		this.totalOINetChangePut = bean.getTotalOINetChangePut();
		this.priceMoveTrend = bean.getPriceMoveTrend();
		this.totalOINetChangeCallStyle = bean.getTotalOINetChangeCallStyle();
		this.totalOINetChangePutStyle = bean.getTotalOINetChangePutStyle();
		this.totalOpenInterestChangeCall = bean.getTotalOpenInterestChangeCall();
		this.totalOpenInterestChangePut = bean.getTotalOpenInterestChangePut();
		this.priceOpenIntrestChangeTrend = bean.getPriceOpenIntrestChangeTrend();
		this.totalOpenInterestChangeCallStyle = bean.getTotalOpenInterestChangeCallStyle();
		this.totalOpenInterestChangePutStyle = bean.getTotalOpenInterestChangePutStyle();
		
		this.sortOrder = bean.getSortOrder();
		this.topTenGainLooseSortOrder = bean.getTopTenGainLooseSortOrder();
		this.yearHigh = bean.getYearHigh();
		this.yearLow = bean.getYearLow();
		this.putCallRatio = bean.getPutCallRatio();
		this.putCallRatioStyleClass = bean.getPutCallRatioStyleClass();
		
		this.top1OpenInterestCallBidVal1 = bean.getTop1OpenInterestCallBidVal1();
		this.top1OpenInterestCallAskVal1 = bean.getTop1OpenInterestCallAskVal1();
		this.top1OpenInterestCallBidAskDiffVal1 = bean.getTop1OpenInterestCallBidAskDiffVal1();
		this.top1OpenInterestCallBidAskAmtDiffVal1 = bean.getTop1OpenInterestCallBidAskAmtDiffVal1();
		this.top1OpenInterestPutBidVal1 = bean.getTop1OpenInterestPutBidVal1();
		this.top1OpenInterestPutAskVal1 = bean.getTop1OpenInterestPutAskVal1();
		this.top1OpenInterestPutBidAskDiffVal1 = bean.getTop1OpenInterestPutBidAskDiffVal1();
		this.top1OpenInterestPutBidAskDiffAmtVal1 = bean.getTop1OpenInterestPutBidAskDiffAmtVal1();
		this.top2OpenInterestCallBidVal1 = bean.getTop2OpenInterestCallBidVal1();
		this.top2OpenInterestCallAskVal1 = bean.getTop2OpenInterestCallAskVal1();
		this.top2OpenInterestCallBidAskDiffVal1 = bean.getTop2OpenInterestCallBidAskDiffVal1();
		this.top2OpenInterestCallBidAskAmtDiffVal1 = bean.getTop2OpenInterestCallBidAskAmtDiffVal1();
		this.top2OpenInterestPutBidVal1 = bean.getTop2OpenInterestPutBidVal1();
		this.top2OpenInterestPutAskVal1 = bean.getTop2OpenInterestPutAskVal1();
		this.top2OpenInterestPutBidAskDiffVal1 = bean.getTop2OpenInterestPutBidAskDiffVal1();
		this.top2OpenInterestPutBidAskDiffAmtVal1 = bean.getTop2OpenInterestPutBidAskDiffAmtVal1();
		this.top3OpenInterestCallBidVal1 = bean.getTop3OpenInterestCallBidVal1();
		this.top3OpenInterestCallAskVal1 = bean.getTop3OpenInterestCallAskVal1();
		this.top3OpenInterestCallBidAskDiffVal1 = bean.getTop3OpenInterestCallBidAskDiffVal1();
		this.top3OpenInterestCallBidAskAmtDiffVal1 = bean.getTop3OpenInterestCallBidAskAmtDiffVal1();
		this.top3OpenInterestPutBidVal1 = bean.getTop3OpenInterestPutBidVal1();
		this.top3OpenInterestPutAskVal1 = bean.getTop3OpenInterestPutAskVal1();
		this.top3OpenInterestPutBidAskDiffVal1 = bean.getTop3OpenInterestPutBidAskDiffVal1();
		this.top3OpenInterestPutBidAskDiffAmtVal1 = bean.getTop3OpenInterestPutBidAskDiffAmtVal1();
		this.top1ImpliedVolatilityCall = bean.getTop1ImpliedVolatilityCall();
		this.top2ImpliedVolatilityCall = bean.getTop2ImpliedVolatilityCall();
		this.top3ImpliedVolatilityCall = bean.getTop3ImpliedVolatilityCall();
		this.top1ImpliedVolatilityPut = bean.getTop1ImpliedVolatilityPut();
		this.top2ImpliedVolatilityPut = bean.getTop2ImpliedVolatilityPut();
		this.top3ImpliedVolatilityPut = bean.getTop3ImpliedVolatilityPut();
		this.totalImpliedVolatilityCall = bean.getTotalImpliedVolatilityCall();
		this.totalImpliedVolatilityPut = bean.getTotalImpliedVolatilityPut();
		this.totalImpliedVolatilityCallStyle = bean.getTotalImpliedVolatilityCallStyle();
		this.totalImpliedVolatilityPutStyle = bean.getTotalImpliedVolatilityPutStyle();
	}

	public OptionChainInfoBean populateBean() {
		OptionChainInfoBean bean = new OptionChainInfoBean();

		bean.setOptionChainId(optionChainId);
		bean.setSymbolId(this.symbolId);
		bean.setLotSize(this.lotSize);
		bean.setTickerSize(this.tickerSize);
		bean.setStyle(this.getStyle());
		//bean.setOITrend(this.getoITrend());
		bean.setOITrend(this.getOiTrend());
		bean.setIndexInd(this.getIndexInd());
		bean.setTop1OpenInterestCall(this.top1OpenInterestCall);
		bean.setTop2OpenInterestCall(this.top2OpenInterestCall);
		bean.setTop3OpenInterestCall(this.top3OpenInterestCall);
		bean.setTop1OpenInterestChangeCall(this.top1OpenInterestChangeCall);
		bean.setTop2OpenInterestChangeCall(this.top2OpenInterestChangeCall);
		bean.setTop3OpenInterestChangeCall(this.top3OpenInterestChangeCall);
		bean.setTop1OIVolumesCall(this.top1OIVolumesCall);
		bean.setTop2OIVolumesCall(this.top2OIVolumesCall);
		bean.setTop3OIVolumesCall(this.top3OIVolumesCall);
		bean.setTop1OINetChangeCall(this.top1OINetChangeCall);
		bean.setTop2OINetChangeCall(this.top2OINetChangeCall);
		bean.setTop3OINetChangeCall(this.top3OINetChangeCall);
		bean.setTop1StrikePriceCall(this.top1StrikePriceCall);
		bean.setTop2StrikePriceCall(this.top2StrikePriceCall);
		bean.setTop3StrikePriceCall(this.top3StrikePriceCall);
		bean.setTop1OpenInterestPut(this.top1OpenInterestPut);
		bean.setTop2OpenInterestPut(this.top2OpenInterestPut);
		bean.setTop3OpenInterestPut(this.top3OpenInterestPut);
		bean.setTop1OpenInterestChangePut(this.top1OpenInterestChangePut);
		bean.setTop2OpenInterestChangePut(this.top2OpenInterestChangePut);
		bean.setTop3OpenInterestChangePut(this.top3OpenInterestChangePut);
		bean.setTop1OIVolumesPut(this.top1OIVolumesPut);
		bean.setTop2OIVolumesPut(this.top2OIVolumesPut);
		bean.setTop3OIVolumesPut(this.top3OIVolumesPut);
		bean.setTop1OINetChangePut(this.top1OINetChangePut);
		bean.setTop2OINetChangePut(this.top2OINetChangePut);
		bean.setTop3OINetChangePut(this.top3OINetChangePut);
		bean.setTop1StrikePricePut(this.top1StrikePricePut);
		bean.setTop2StrikePricePut(this.top2StrikePricePut);
		bean.setTop3StrikePricePut(this.top3StrikePricePut);
		bean.setTotalOpenInterestCall(this.totalOpenInterestCall);
		bean.setTotalOpenInterestPut(this.totalOpenInterestPut);
		bean.setTotalOIVolumesCall(this.totalOIVolumesCall);
		bean.setTotalOIVolumesPut(this.totalOIVolumesPut);
		bean.setDay1OpenInterest(this.day1OpenInterest);
		bean.setDay2OpenInterest(this.day2OpenInterest);
		bean.setOpenInterestChange(this.openInterestChange);
		bean.setOpenInterestChangePercentage(this.openInterestChangePercentage);
		bean.setOpenInterestVolumes(this.openInterestVolumes);// contracts
		bean.setUnderlyingPrice(this.underlyingPrice);// current market price value
		bean.setTimeFrameNumber(this.timeFrameNumber);
		bean.setDateTimeStamp(this.dateTimeStamp);
		bean.setDateStamp(this.dateStamp);
		bean.setBestTradeInd(this.bestTradeInd);
		
		bean.setStrongOrder(this.strongOrder);
		bean.setLtpOnDecision(this.ltpOnDecision);
		bean.setLastPrice(this.lastPrice);
		bean.setBestEntry(this.bestEntry);
		bean.setGoForTrade(this.goForTrade);
		bean.setChangePercentage(this.changePercentage);
		bean.setCandleNumber(this.candleNumber);
		bean.setParentRecordInd(this.parentRecordInd);
		
		 bean.setTop1OpenInterestCallStyle(this.top1OpenInterestCallStyle);
		 bean.setTop2OpenInterestCallStyle(this.top2OpenInterestCallStyle);
		 bean.setTop3OpenInterestCallStyle(this.top3OpenInterestCallStyle);
		 bean.setTop1OpenInterestPutStyle(this.top1OpenInterestPutStyle);
		 bean.setTop2OpenInterestPutStyle(this.top2OpenInterestPutStyle);
		 bean.setTop3OpenInterestPutStyle(this.top3OpenInterestPutStyle);
		 bean.setAttentionStyleBuy(this.attentionStyleBuy);
		 bean.setAttentionStyleSell(this.attentionStyleSell);
		 
		 bean.setTotalOINetChangeCall(totalOINetChangeCall);
		 bean.setTotalOINetChangePut(totalOINetChangePut);
		 bean.setPriceMoveTrend(priceMoveTrend);
		 bean.setTotalOINetChangeCallStyle(this.totalOINetChangeCallStyle);
		 bean.setTotalOINetChangePutStyle(this.totalOINetChangePutStyle);
		 bean.setTotalOpenInterestChangeCall(totalOpenInterestChangeCall);
		 bean.setTotalOpenInterestChangePut(totalOpenInterestChangePut);
		 bean.setPriceOpenIntrestChangeTrend(priceOpenIntrestChangeTrend);
		 bean.setTotalOpenInterestChangeCallStyle(this.totalOpenInterestChangeCallStyle);
		 bean.setTotalOpenInterestChangePutStyle(this.totalOpenInterestChangePutStyle);
		
		 bean.setSortOrder(this.sortOrder);
		 bean.setTopTenGainLooseSortOrder(this.topTenGainLooseSortOrder);
		 bean.setYearHigh(yearHigh);
		 bean.setYearLow(yearLow);
		 bean.setPutCallRatio(this.putCallRatio);
		 bean.setPutCallRatioStyleClass(this.putCallRatioStyleClass);
		 
		 bean.setTop1OpenInterestCallBidVal1(this.top1OpenInterestCallBidVal1);
		 bean.setTop1OpenInterestCallAskVal1(this.top1OpenInterestCallAskVal1);
		 bean.setTop1OpenInterestCallBidAskDiffVal1(this.top1OpenInterestCallBidAskDiffVal1);
		 bean.setTop1OpenInterestCallBidAskAmtDiffVal1(this.top1OpenInterestCallBidAskAmtDiffVal1);
		 bean.setTop1OpenInterestPutBidVal1(this.top1OpenInterestPutBidVal1);
		 bean.setTop1OpenInterestPutAskVal1(this.top1OpenInterestPutAskVal1);
		 bean.setTop1OpenInterestPutBidAskDiffVal1(this.top1OpenInterestPutBidAskDiffVal1);
		 bean.setTop1OpenInterestPutBidAskDiffAmtVal1(this.top1OpenInterestPutBidAskDiffAmtVal1);
		 bean.setTop2OpenInterestCallBidVal1(this.top2OpenInterestCallBidVal1);
		 bean.setTop2OpenInterestCallAskVal1(this.top2OpenInterestCallAskVal1);
		 bean.setTop2OpenInterestCallBidAskDiffVal1(this.top2OpenInterestCallBidAskDiffVal1);
		 bean.setTop2OpenInterestCallBidAskAmtDiffVal1(this.top2OpenInterestCallBidAskAmtDiffVal1);
		 bean.setTop2OpenInterestPutBidVal1(this.top2OpenInterestPutBidVal1);
		 bean.setTop2OpenInterestPutAskVal1(this.top2OpenInterestPutAskVal1);
		 bean.setTop2OpenInterestPutBidAskDiffVal1(this.top2OpenInterestPutBidAskDiffVal1);
		 bean.setTop2OpenInterestPutBidAskDiffAmtVal1(this.top2OpenInterestPutBidAskDiffAmtVal1);
		 bean.setTop3OpenInterestCallBidVal1(this.top3OpenInterestCallBidVal1);
		 bean.setTop3OpenInterestCallAskVal1(this.top3OpenInterestCallAskVal1);
		 bean.setTop3OpenInterestCallBidAskDiffVal1(this.top3OpenInterestCallBidAskDiffVal1);
		 bean.setTop3OpenInterestCallBidAskAmtDiffVal1(this.top3OpenInterestCallBidAskAmtDiffVal1);
		 bean.setTop3OpenInterestPutBidVal1(this.top3OpenInterestPutBidVal1);
		 bean.setTop3OpenInterestPutAskVal1(this.top3OpenInterestPutAskVal1);
		 bean.setTop3OpenInterestPutBidAskDiffVal1(this.top3OpenInterestPutBidAskDiffVal1);
		 bean.setTop3OpenInterestPutBidAskDiffAmtVal1(this.top3OpenInterestPutBidAskDiffAmtVal1);
		 bean.setTop1ImpliedVolatilityCall(this.top1ImpliedVolatilityCall);
		 bean.setTop2ImpliedVolatilityCall(this.top2ImpliedVolatilityCall);
		 bean.setTop3ImpliedVolatilityCall(this.top3ImpliedVolatilityCall);
		 bean.setTop1ImpliedVolatilityPut(this.top1ImpliedVolatilityPut);
		 bean.setTop2ImpliedVolatilityPut(this.top2ImpliedVolatilityPut);
		 bean.setTop3ImpliedVolatilityPut(this.top3ImpliedVolatilityPut);
		 bean.setTotalImpliedVolatilityCall(this.totalImpliedVolatilityCall);
		 bean.setTotalImpliedVolatilityPut(this.totalImpliedVolatilityPut);
		 bean.setTotalImpliedVolatilityCallStyle(this.totalImpliedVolatilityCallStyle);
		 bean.setTotalImpliedVolatilityPutStyle(this.totalImpliedVolatilityPutStyle);
		return bean;
	}
}
