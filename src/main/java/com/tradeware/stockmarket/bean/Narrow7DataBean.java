package com.tradeware.stockmarket.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Narrow7DataBean extends AbstractTradeDataBean {

	private static final long serialVersionUID = 635760552623740130L;
	private Double day1High;
	private Double day1Low;
	private Double day2High;
	private Double day2Low;
	private Double day3High;
	private Double day3Low;
	private Double day4High;
	private Double day4Low;
	private Double day5High;
	private Double day5Low;
	private Double day6High;
	private Double day6Low;
	private Double day7High;
	private Double day7Low;

	private Double day1Open;
	private Double day1Close;
	private Double day2Open;
	private Double day2Close;
	private Double day3Open;
	private Double day3Close;
	private Double day4Open;
	private Double day4Close;
	private Double day5Open;
	private Double day5Close;
	private Double day6Open;
	private Double day6Close;
	private Double day7Open;
	private Double day7Close;
	
	private String tradingDate;
	private List<String> nr7TradeDates = new ArrayList<String>();
	private String nr7TradeDatesStr = "";
	
	public String getNr7TradeDatesStr() {
		for (String date : nr7TradeDates) {
			nr7TradeDatesStr = nr7TradeDatesStr + date +"; ";
		}
		
		return nr7TradeDatesStr;
	}


	private Integer itemId;
	private String tradableState;
	private String tradableBuyState;
	private String tradableSellState;
	private String tradedState;
	private Double positiveMoveVal;
	private Double negativeMoveVal;
	private Double candle1SizeAmt;
	private Double candle2SizeAmt;
	private Double candle3SizeAmt;
	private Double candle4SizeAmt;
	private Double candle5SizeAmt;
	private Double candle6SizeAmt;
	private Double candle7SizeAmt;
	private Double candleHighsDiff;
	private Double candleLowsDiff;
	private Double candle1HighMinusClose;
	private Double candle1CloseMinusLow;
	private Double candle2HighMinusClose;
	private Double candle2CloseMinusLow;
	private Double candle3HighMinusClose;
	private Double candle3CloseMinusLow;
	private Double candle4HighMinusClose;
	private Double candle4CloseMinusLow;
	private Double candle5HighMinusClose;
	private Double candle5CloseMinusLow;
	private Double candle6HighMinusClose;
	private Double candle6CloseMinusLow;
	private Double candle7HighMinusClose;
	private Double candle7CloseMinusLow;
	private String candle1Type;
	private String candle2Type;
	private String candle3Type;
	private String candle4Type;
	private String candle5Type;
	private String candle6Type;
	private String candle7Type;
	private String ohlcState;
	private String tradableStrength;
	private String strengthTradableState;
	private Boolean tempCustomTradingRuleInd;
	private Integer candleNumber;
	private Double gapUpDownMoveVal;
	private String oiInfo;
	private Date tradedDateStamp;
	private String heikinAshiTrend;
	private String optionChainTrend;
	private String optionChainPriceTrend;
	private String candleTimeFrame;
	private String strengthStyleClass;
	private String strategyRule;
	private String volStrengthTrend;
	private String volStrengthStyleClass;
	
	private Double vwap;
	private Double openIntrest;
	private Double openIntrestTopHigh;
	private Double openIntrestTopLow;
	private Double openIntrestAvg;
	
	private Double volumes;
	private Double volumesTopHigh;
	private Double volumesTopLow;
	private Double volumesAvg;
	
	
	public Double getSettlePrice() {
		return settlePrice;
	}


	public void setSettlePrice(Double settlePrice) {
		this.settlePrice = settlePrice;
	}


	public Double getNumberOfContracts() {
		return numberOfContracts;
	}


	public void setNumberOfContracts(Double numberOfContracts) {
		this.numberOfContracts = numberOfContracts;
	}


	private Double numberOfContracts;
	private Double settlePrice;
	private Double turnoverActual;//In rupes
	private Double turnover;// Turnover (in lacs);
	private Double averageContracts;
	private Double averageTurnover;

	public Double getTurnoverActual() {
		return turnoverActual;
	}


	public void setTurnoverActual(Double turnoverActual) {
		this.turnoverActual = turnoverActual;
	}


	public Double getTurnover() {
		return turnover;
	}


	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}


	public Double getAverageContracts() {
		return averageContracts;
	}


	public void setAverageContracts(Double averageContracts) {
		this.averageContracts = averageContracts;
	}


	public Double getAverageTurnover() {
		return averageTurnover;
	}


	public void setAverageTurnover(Double averageTurnover) {
		this.averageTurnover = averageTurnover;
	}


	private Boolean narrow7RuleInd = Boolean.FALSE;
	private String utTradableState = "NA";
	
	public Narrow7DataBean(Integer lotSize, String symbolId) {
		super(lotSize, symbolId);
	}


	public Narrow7DataBean clone() {
		Narrow7DataBean bean = new Narrow7DataBean(super.getLotSize(), super.getSymbolId());
		super.clone(bean);
		bean.setSymbolName(super.getSymbolName());
		bean.setDay1High(this.day1High );
		bean.setDay1Low(this.day1Low );
		bean.setDay2High(this.day2High );
		bean.setDay2Low(this.day2Low );
		bean.setDay3High(this.day3High );
		bean.setDay3Low(this.day3Low );
		bean.setDay4High(this.day4High );
		bean.setDay4Low(this.day4Low );
		bean.setDay5High(this.day5High );
		bean.setDay5Low(this.day5Low );
		bean.setDay6High(this.day6High );
		bean.setDay6Low(this.day6Low );
		bean.setDay7High(this.day7High );
		bean.setDay7Low(this.day7Low );

		bean.setDay1Open(this.day1Open );
		bean.setDay1Close(this.day1Close );
		bean.setDay2Open(this.day2Open );
		bean.setDay2Close(this.day2Close );
		bean.setDay3Open(this.day3Open );
		bean.setDay3Close(this.day3Close );
		bean.setDay4Open(this.day4Open );
		bean.setDay4Close(this.day4Close );
		bean.setDay5Open(this.day5Open );
		bean.setDay5Close(this.day5Close );
		bean.setDay6Open(this.day6Open );
		bean.setDay6Close(this.day6Close );
		bean.setDay7Open(this.day7Open );
		bean.setDay7Close(this.day7Close );

		bean.setNarrow7RuleInd(this.narrow7RuleInd );
		bean.setUtTradableState(this.utTradableState );
		
		bean.setItemId(this.itemId);
		bean.setTradableState(this.tradableState);
		bean.setTradableBuyState(this.tradableBuyState);
		bean.setTradableSellState(this.tradableSellState);
		bean.setTradedState(this.tradedState);
		bean.setPositiveMoveVal(this.positiveMoveVal);
		bean.setNegativeMoveVal(this.negativeMoveVal);
		bean.setCandle1SizeAmt(this.candle1SizeAmt);
		bean.setCandle2SizeAmt(this.candle2SizeAmt);
		bean.setCandle3SizeAmt(this.candle3SizeAmt);
		bean.setCandle4SizeAmt(this.candle4SizeAmt);
		bean.setCandle5SizeAmt(this.candle5SizeAmt);
		bean.setCandle6SizeAmt(this.candle6SizeAmt);
		bean.setCandle7SizeAmt(this.candle7SizeAmt);
		bean.setCandleHighsDiff(this.candleHighsDiff);
		bean.setCandleLowsDiff(this.candleLowsDiff);
		bean.setCandle1HighMinusClose(this.candle1HighMinusClose);
		bean.setCandle1CloseMinusLow(this.candle1CloseMinusLow);
		bean.setCandle2HighMinusClose(this.candle2HighMinusClose);
		bean.setCandle2CloseMinusLow(this.candle2CloseMinusLow);
		bean.setCandle3HighMinusClose(this.candle3HighMinusClose);
		bean.setCandle3CloseMinusLow(this.candle3CloseMinusLow);
		bean.setCandle4HighMinusClose(this.candle4HighMinusClose);
		bean.setCandle4CloseMinusLow(this.candle4CloseMinusLow);
		bean.setCandle5HighMinusClose(this.candle5HighMinusClose);
		bean.setCandle5CloseMinusLow(this.candle5CloseMinusLow);
		bean.setCandle6HighMinusClose(this.candle6HighMinusClose);
		bean.setCandle6CloseMinusLow(this.candle6CloseMinusLow);
		bean.setCandle7HighMinusClose(this.candle7HighMinusClose);
		bean.setCandle7CloseMinusLow(this.candle7CloseMinusLow);
		bean.setCandle1Type(this.candle1Type);
		bean.setCandle2Type(this.candle2Type);
		bean.setCandle3Type(this.candle3Type);
		bean.setCandle4Type(this.candle4Type);
		bean.setCandle5Type(this.candle5Type);
		bean.setCandle6Type(this.candle6Type);
		bean.setCandle7Type(this.candle7Type);
		bean.setOhlcState(this.ohlcState);
		bean.setTradableStrength(this.tradableStrength);
		bean.setStrengthTradableState(this.strengthTradableState);
		bean.setTempCustomTradingRuleInd(this.tempCustomTradingRuleInd);
		bean.setCandleNumber(this.candleNumber);
		bean.setGapUpDownMoveVal(this.gapUpDownMoveVal);
		bean.setOiInfo(this.oiInfo);
		bean.setTradedDateStamp(this.tradedDateStamp);
		bean.setHeikinAshiTrend(this.heikinAshiTrend);
		bean.setOptionChainTrend(this.optionChainTrend);
		bean.setOptionChainPriceTrend(this.optionChainPriceTrend);
		bean.setCandleTimeFrame(this.candleTimeFrame);
		bean.setStrengthStyleClass(this.strengthStyleClass);
		bean.setStrategyRule(this.strategyRule);
		bean.setVolStrengthTrend(volStrengthTrend);
		bean.setVolStrengthStyleClass(volStrengthStyleClass);
		
		bean.setNumberOfContracts(numberOfContracts);
		bean.setSettlePrice(settlePrice);
		bean.setTurnoverActual(turnoverActual);
		bean.setTurnover(turnover);
		bean.setAverageContracts(averageContracts);
		bean.setAverageTurnover(averageTurnover);
		
		return bean;
	}


	public Double getDay1High() {
		return day1High;
	}


	public void setDay1High(Double day1High) {
		this.day1High = day1High;
	}


	public Double getDay1Low() {
		return day1Low;
	}


	public void setDay1Low(Double day1Low) {
		this.day1Low = day1Low;
	}


	public Double getDay2High() {
		return day2High;
	}


	public void setDay2High(Double day2High) {
		this.day2High = day2High;
	}


	public Double getDay2Low() {
		return day2Low;
	}


	public void setDay2Low(Double day2Low) {
		this.day2Low = day2Low;
	}


	public Double getDay3High() {
		return day3High;
	}


	public void setDay3High(Double day3High) {
		this.day3High = day3High;
	}


	public Double getDay3Low() {
		return day3Low;
	}


	public void setDay3Low(Double day3Low) {
		this.day3Low = day3Low;
	}


	public Double getDay4High() {
		return day4High;
	}


	public void setDay4High(Double day4High) {
		this.day4High = day4High;
	}


	public Double getDay4Low() {
		return day4Low;
	}


	public void setDay4Low(Double day4Low) {
		this.day4Low = day4Low;
	}


	public Double getDay5High() {
		return day5High;
	}


	public void setDay5High(Double day5High) {
		this.day5High = day5High;
	}


	public Double getDay5Low() {
		return day5Low;
	}


	public void setDay5Low(Double day5Low) {
		this.day5Low = day5Low;
	}


	public Double getDay6High() {
		return day6High;
	}


	public void setDay6High(Double day6High) {
		this.day6High = day6High;
	}


	public Double getDay6Low() {
		return day6Low;
	}


	public void setDay6Low(Double day6Low) {
		this.day6Low = day6Low;
	}


	public Double getDay7High() {
		return day7High;
	}


	public void setDay7High(Double day7High) {
		this.day7High = day7High;
	}


	public Double getDay7Low() {
		return day7Low;
	}


	public void setDay7Low(Double day7Low) {
		this.day7Low = day7Low;
	}


	public Double getDay1Open() {
		return day1Open;
	}


	public void setDay1Open(Double day1Open) {
		this.day1Open = day1Open;
	}


	public Double getDay1Close() {
		return day1Close;
	}


	public void setDay1Close(Double day1Close) {
		this.day1Close = day1Close;
	}


	public Double getDay2Open() {
		return day2Open;
	}


	public void setDay2Open(Double day2Open) {
		this.day2Open = day2Open;
	}


	public Double getDay2Close() {
		return day2Close;
	}


	public void setDay2Close(Double day2Close) {
		this.day2Close = day2Close;
	}


	public Double getDay3Open() {
		return day3Open;
	}


	public void setDay3Open(Double day3Open) {
		this.day3Open = day3Open;
	}


	public Double getDay3Close() {
		return day3Close;
	}


	public void setDay3Close(Double day3Close) {
		this.day3Close = day3Close;
	}


	public Double getDay4Open() {
		return day4Open;
	}


	public void setDay4Open(Double day4Open) {
		this.day4Open = day4Open;
	}


	public Double getDay4Close() {
		return day4Close;
	}


	public void setDay4Close(Double day4Close) {
		this.day4Close = day4Close;
	}


	public Double getDay5Open() {
		return day5Open;
	}


	public void setDay5Open(Double day5Open) {
		this.day5Open = day5Open;
	}


	public Double getDay5Close() {
		return day5Close;
	}


	public void setDay5Close(Double day5Close) {
		this.day5Close = day5Close;
	}


	public Double getDay6Open() {
		return day6Open;
	}


	public void setDay6Open(Double day6Open) {
		this.day6Open = day6Open;
	}


	public Double getDay6Close() {
		return day6Close;
	}


	public void setDay6Close(Double day6Close) {
		this.day6Close = day6Close;
	}


	public Double getDay7Open() {
		return day7Open;
	}


	public void setDay7Open(Double day7Open) {
		this.day7Open = day7Open;
	}


	public Double getDay7Close() {
		return day7Close;
	}


	public void setDay7Close(Double day7Close) {
		this.day7Close = day7Close;
	}


	public Integer getItemId() {
		return itemId;
	}


	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}


	public String getTradableState() {
		return tradableState;
	}


	public void setTradableState(String tradableState) {
		this.tradableState = tradableState;
	}


	public String getTradableBuyState() {
		return tradableBuyState;
	}


	public void setTradableBuyState(String tradableBuyState) {
		this.tradableBuyState = tradableBuyState;
	}


	public String getTradableSellState() {
		return tradableSellState;
	}


	public void setTradableSellState(String tradableSellState) {
		this.tradableSellState = tradableSellState;
	}


	public String getTradedState() {
		return tradedState;
	}


	public void setTradedState(String tradedState) {
		this.tradedState = tradedState;
	}


	public Double getPositiveMoveVal() {
		return positiveMoveVal;
	}


	public void setPositiveMoveVal(Double positiveMoveVal) {
		this.positiveMoveVal = positiveMoveVal;
	}


	public Double getNegativeMoveVal() {
		return negativeMoveVal;
	}


	public void setNegativeMoveVal(Double negativeMoveVal) {
		this.negativeMoveVal = negativeMoveVal;
	}


	public Double getCandle1SizeAmt() {
		return candle1SizeAmt;
	}


	public void setCandle1SizeAmt(Double candle1SizeAmt) {
		this.candle1SizeAmt = candle1SizeAmt;
	}


	public Double getCandle2SizeAmt() {
		return candle2SizeAmt;
	}


	public void setCandle2SizeAmt(Double candle2SizeAmt) {
		this.candle2SizeAmt = candle2SizeAmt;
	}


	public Double getCandle3SizeAmt() {
		return candle3SizeAmt;
	}


	public void setCandle3SizeAmt(Double candle3SizeAmt) {
		this.candle3SizeAmt = candle3SizeAmt;
	}


	public Double getCandle4SizeAmt() {
		return candle4SizeAmt;
	}


	public void setCandle4SizeAmt(Double candle4SizeAmt) {
		this.candle4SizeAmt = candle4SizeAmt;
	}


	public Double getCandle5SizeAmt() {
		return candle5SizeAmt;
	}


	public void setCandle5SizeAmt(Double candle5SizeAmt) {
		this.candle5SizeAmt = candle5SizeAmt;
	}


	public Double getCandle6SizeAmt() {
		return candle6SizeAmt;
	}


	public void setCandle6SizeAmt(Double candle6SizeAmt) {
		this.candle6SizeAmt = candle6SizeAmt;
	}


	public Double getCandle7SizeAmt() {
		return candle7SizeAmt;
	}


	public void setCandle7SizeAmt(Double candle7SizeAmt) {
		this.candle7SizeAmt = candle7SizeAmt;
	}


	public Double getCandleHighsDiff() {
		return candleHighsDiff;
	}


	public void setCandleHighsDiff(Double candleHighsDiff) {
		this.candleHighsDiff = candleHighsDiff;
	}


	public Double getCandleLowsDiff() {
		return candleLowsDiff;
	}


	public void setCandleLowsDiff(Double candleLowsDiff) {
		this.candleLowsDiff = candleLowsDiff;
	}


	public Double getCandle1HighMinusClose() {
		return candle1HighMinusClose;
	}


	public void setCandle1HighMinusClose(Double candle1HighMinusClose) {
		this.candle1HighMinusClose = candle1HighMinusClose;
	}


	public Double getCandle1CloseMinusLow() {
		return candle1CloseMinusLow;
	}


	public void setCandle1CloseMinusLow(Double candle1CloseMinusLow) {
		this.candle1CloseMinusLow = candle1CloseMinusLow;
	}


	public Double getCandle2HighMinusClose() {
		return candle2HighMinusClose;
	}


	public void setCandle2HighMinusClose(Double candle2HighMinusClose) {
		this.candle2HighMinusClose = candle2HighMinusClose;
	}


	public Double getCandle2CloseMinusLow() {
		return candle2CloseMinusLow;
	}


	public void setCandle2CloseMinusLow(Double candle2CloseMinusLow) {
		this.candle2CloseMinusLow = candle2CloseMinusLow;
	}


	public Double getCandle3HighMinusClose() {
		return candle3HighMinusClose;
	}


	public void setCandle3HighMinusClose(Double candle3HighMinusClose) {
		this.candle3HighMinusClose = candle3HighMinusClose;
	}


	public Double getCandle3CloseMinusLow() {
		return candle3CloseMinusLow;
	}


	public void setCandle3CloseMinusLow(Double candle3CloseMinusLow) {
		this.candle3CloseMinusLow = candle3CloseMinusLow;
	}


	public Double getCandle4HighMinusClose() {
		return candle4HighMinusClose;
	}


	public void setCandle4HighMinusClose(Double candle4HighMinusClose) {
		this.candle4HighMinusClose = candle4HighMinusClose;
	}


	public Double getCandle4CloseMinusLow() {
		return candle4CloseMinusLow;
	}


	public void setCandle4CloseMinusLow(Double candle4CloseMinusLow) {
		this.candle4CloseMinusLow = candle4CloseMinusLow;
	}


	public Double getCandle5HighMinusClose() {
		return candle5HighMinusClose;
	}


	public void setCandle5HighMinusClose(Double candle5HighMinusClose) {
		this.candle5HighMinusClose = candle5HighMinusClose;
	}


	public Double getCandle5CloseMinusLow() {
		return candle5CloseMinusLow;
	}


	public void setCandle5CloseMinusLow(Double candle5CloseMinusLow) {
		this.candle5CloseMinusLow = candle5CloseMinusLow;
	}


	public Double getCandle6HighMinusClose() {
		return candle6HighMinusClose;
	}


	public void setCandle6HighMinusClose(Double candle6HighMinusClose) {
		this.candle6HighMinusClose = candle6HighMinusClose;
	}


	public Double getCandle6CloseMinusLow() {
		return candle6CloseMinusLow;
	}


	public void setCandle6CloseMinusLow(Double candle6CloseMinusLow) {
		this.candle6CloseMinusLow = candle6CloseMinusLow;
	}


	public Double getCandle7HighMinusClose() {
		return candle7HighMinusClose;
	}


	public void setCandle7HighMinusClose(Double candle7HighMinusClose) {
		this.candle7HighMinusClose = candle7HighMinusClose;
	}


	public Double getCandle7CloseMinusLow() {
		return candle7CloseMinusLow;
	}


	public void setCandle7CloseMinusLow(Double candle7CloseMinusLow) {
		this.candle7CloseMinusLow = candle7CloseMinusLow;
	}


	public String getCandle1Type() {
		return candle1Type;
	}


	public void setCandle1Type(String candle1Type) {
		this.candle1Type = candle1Type;
	}


	public String getCandle2Type() {
		return candle2Type;
	}


	public void setCandle2Type(String candle2Type) {
		this.candle2Type = candle2Type;
	}


	public String getCandle3Type() {
		return candle3Type;
	}


	public void setCandle3Type(String candle3Type) {
		this.candle3Type = candle3Type;
	}


	public String getCandle4Type() {
		return candle4Type;
	}


	public void setCandle4Type(String candle4Type) {
		this.candle4Type = candle4Type;
	}


	public String getCandle5Type() {
		return candle5Type;
	}


	public void setCandle5Type(String candle5Type) {
		this.candle5Type = candle5Type;
	}


	public String getCandle6Type() {
		return candle6Type;
	}


	public void setCandle6Type(String candle6Type) {
		this.candle6Type = candle6Type;
	}


	public String getCandle7Type() {
		return candle7Type;
	}


	public void setCandle7Type(String candle7Type) {
		this.candle7Type = candle7Type;
	}


	public String getOhlcState() {
		return ohlcState;
	}


	public void setOhlcState(String ohlcState) {
		this.ohlcState = ohlcState;
	}


	public String getTradableStrength() {
		return tradableStrength;
	}


	public void setTradableStrength(String tradableStrength) {
		this.tradableStrength = tradableStrength;
	}


	public String getStrengthTradableState() {
		return strengthTradableState;
	}


	public void setStrengthTradableState(String strengthTradableState) {
		this.strengthTradableState = strengthTradableState;
	}


	public Boolean getTempCustomTradingRuleInd() {
		return tempCustomTradingRuleInd;
	}


	public void setTempCustomTradingRuleInd(Boolean tempCustomTradingRuleInd) {
		this.tempCustomTradingRuleInd = tempCustomTradingRuleInd;
	}


	public Integer getCandleNumber() {
		return candleNumber;
	}


	public void setCandleNumber(Integer candleNumber) {
		this.candleNumber = candleNumber;
	}


	public Double getGapUpDownMoveVal() {
		return gapUpDownMoveVal;
	}


	public void setGapUpDownMoveVal(Double gapUpDownMoveVal) {
		this.gapUpDownMoveVal = gapUpDownMoveVal;
	}


	public String getOiInfo() {
		return oiInfo;
	}


	public void setOiInfo(String oiInfo) {
		this.oiInfo = oiInfo;
	}


	public Date getTradedDateStamp() {
		return tradedDateStamp;
	}


	public void setTradedDateStamp(Date tradedDateStamp) {
		this.tradedDateStamp = tradedDateStamp;
	}


	public String getHeikinAshiTrend() {
		return heikinAshiTrend;
	}


	public void setHeikinAshiTrend(String heikinAshiTrend) {
		this.heikinAshiTrend = heikinAshiTrend;
	}


	public String getOptionChainTrend() {
		return optionChainTrend;
	}


	public void setOptionChainTrend(String optionChainTrend) {
		this.optionChainTrend = optionChainTrend;
	}


	public String getOptionChainPriceTrend() {
		return optionChainPriceTrend;
	}


	public void setOptionChainPriceTrend(String optionChainPriceTrend) {
		this.optionChainPriceTrend = optionChainPriceTrend;
	}


	public String getCandleTimeFrame() {
		return candleTimeFrame;
	}


	public void setCandleTimeFrame(String candleTimeFrame) {
		this.candleTimeFrame = candleTimeFrame;
	}


	public String getStrengthStyleClass() {
		return strengthStyleClass;
	}


	public void setStrengthStyleClass(String strengthStyleClass) {
		this.strengthStyleClass = strengthStyleClass;
	}


	public String getStrategyRule() {
		return strategyRule;
	}


	public void setStrategyRule(String strategyRule) {
		this.strategyRule = strategyRule;
	}


	public Boolean getNarrow7RuleInd() {
		return narrow7RuleInd;
	}


	public void setNarrow7RuleInd(Boolean narrow7RuleInd) {
		this.narrow7RuleInd = narrow7RuleInd;
	}


	public String getUtTradableState() {
		return utTradableState;
	}


	public void setUtTradableState(String utTradableState) {
		this.utTradableState = utTradableState;
	}


	public String getVolStrengthTrend() {
		return volStrengthTrend;
	}


	public void setVolStrengthTrend(String volStrengthTrend) {
		this.volStrengthTrend = volStrengthTrend;
	}


	public String getVolStrengthStyleClass() {
		return volStrengthStyleClass;
	}


	public void setVolStrengthStyleClass(String volStrengthStyleClass) {
		this.volStrengthStyleClass = volStrengthStyleClass;
	}


	public String getTradingDate() {
		return tradingDate;
	}


	public void setTradingDate(String tradingDate) {
		this.tradingDate = tradingDate;
	}


	public List<String> getNr7TradeDates() {
		return nr7TradeDates;
	}


	public void setNr7TradeDates(List<String> nr7TradeDates) {
		this.nr7TradeDates = nr7TradeDates;
	}
	
	
}
