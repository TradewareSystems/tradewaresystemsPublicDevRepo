package hoghlow.tradeware.stockmarket.bean;

import java.io.Serializable;
import java.util.Date;

import com.zerodhatech.models.HistoricalData;
import com.zerodhatech.models.Order;

public class StockDataBean implements Serializable,  Cloneable {

	private static final long serialVersionUID = 819997159604571584L;
	private static String NA = "NA";
	//private static String EMPTY_STRING = "";
	private String stockId; //Symbol Id
	private String stockName; //Symbol Name
	private Integer lotSize;
	private Double ltp;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private Date tradedInAt;
	private Date tradedOutAt;
	private String kiteFutureKey;
	private Double buyerSellerDiff;
	private String signalTriggeredInAt;
	private String signalTriggeredOutAt;
	private long instrumentToken;
	
	private String tradableState = NA;
	private String tradedState = NA;
	private Double tradedVal;
	private Double buyerAt;
	private Double sellerAt;
	private Double profitLossAmt = 0d;
	private Double profitChaseVal;
	
	private Double negativeDirectionLtp;
	private Double negativeDirectionLoss = 0d;
	private Double positiveDirectionLtp;
	private Double positiveDirectionProfit = 0d;
	
	private String volumeInfoOnTradeEntry;
	private Long volumes;
	private String additinalInfo; 
	private boolean green3Candle;
	private boolean red3Candle;
	private String volumeTrend = "NA";
	
	private String volumeInfoOnTradeEntryNifty50;
	//private double volumes;
	private String additionalInfoNifty50; 
	private boolean green3CandleNifty50;
	private boolean red3CandleNifty50;
	//private boolean closeHigherHighRule;
	private String volumeTrendNifty50 = "NA";
	
	private double buyOrSellAt;
	private double stopLoss;
	private String reportListKey;
	
	private HistoricalData histData;
	private double rsi1;
	private double rsi2;
	private double rsi3;
	private Double dayRsi;
	
	private boolean closeRule;
	private boolean closeHighRule1;
	private boolean closeHighRule2;
	private boolean openHighLowRule;
	private boolean openHighLowHARule;
	private boolean smallCandle;
	private boolean oppositeHighLowRule;
	private boolean nr4Rule;
	private boolean dayHighLowRule;
	private boolean cprRuleInd;
	private String lastCandle;
	private String ohlcState;
	private Double strengthFactor;
	private Double vwapVal;
	private Double candleMovement;
	private Double day1Movement;
	private Double day2Movement;
	private Double minute5CPR;
	private Double minute15CPR;
	//KiteConnect
	private Order kiteOrder;
	private String kiteOrderId;
	//private Date tradingDayDt;
	
	//ForNifty50
	private String strenthTradableState;
	private String strenthTradableStateNifty;
	private String strengthFactorString;
	
	
	public StockDataBean(Integer lotSize, String stockName) {
		super();
		this.lotSize = lotSize; 
		this.stockName = stockName;
	}
	public StockDataBean clone() {
		StockDataBean bean = new StockDataBean(this.lotSize, this.stockName);
		bean.setStockId(this.stockId);
		bean.setStockName(this.stockName);
		bean.setLotSize(this.lotSize);
		bean.setLtp(this.ltp);
		bean.setOpen(this.open);
		bean.setHigh(this.high);
		bean.setLow(this.low);
		bean.setClose(this.close);
		bean.setTradedInAt(this.tradedInAt);;
		bean.setTradedOutAt(this.tradedOutAt);;
		bean.setKiteFutureKey(this.kiteFutureKey);;
		bean.setBuyerSellerDiff(this.buyerSellerDiff);;
		bean.setSignalTriggeredInAt(this.signalTriggeredInAt);;
		bean.setSignalTriggeredOutAt(this.signalTriggeredOutAt);;
		bean.setInstrumentToken(this.instrumentToken);;
		
		bean.setTradableState(this.tradableState);;
		bean.setTradedState(this.tradedState);;
		bean.setTradedVal(this.tradedVal);;
		bean.setBuyerAt(this.buyerAt);;
		bean.setSellerAt(this.sellerAt);;
		bean.setProfitLossAmt(this.profitLossAmt);
		bean.setProfitChaseVal(this.profitChaseVal);
	
		bean.setNegativeDirectionLtp(this.negativeDirectionLtp);;
		bean.setNegativeDirectionLoss(this.negativeDirectionLoss);;
		bean.setPositiveDirectionLtp(this.positiveDirectionLtp);
		bean.setPositiveDirectionProfit(this.positiveDirectionProfit);
		bean.setVolumeInfoOnTradeEntry(this.volumeInfoOnTradeEntry);
		
		bean.setVolumes(this.volumes);
		bean.setAdditinalInfo(this.additinalInfo);
		bean.setGreen3Candle(this.green3Candle);
		bean.setRed3Candle(this.red3Candle);
		bean.setVolumeTrend(this.volumeTrend);
		
		bean.setVolumeInfoOnTradeEntryNifty50(this.volumeInfoOnTradeEntryNifty50);
		bean.setAdditionalInfoNifty50(this.additionalInfoNifty50); 
		bean.setGreen3CandleNifty50(this.green3CandleNifty50);
		bean.setRed3CandleNifty50(this.red3CandleNifty50);
		bean.setVolumeTrendNifty50(this.volumeTrendNifty50);
		
		bean.setBuyOrSellAt(buyOrSellAt);
		bean.setStopLoss(stopLoss);
		bean.setReportListKey(reportListKey);
		bean.setHistData(histData);
		bean.setRsi1(rsi1);
		bean.setRsi2(rsi2);
		bean.setRsi3(rsi3);
		bean.setDayRsi(dayRsi);
		//bean.setCloseHigherHighRule(closeHigherHighRule);
		
		bean.setCloseRule(closeRule);
		bean.setCloseHighRule1(closeHighRule1);
		bean.setCloseHighRule2(closeHighRule2);
		bean.setOpenHighLowRule(openHighLowRule);
		bean.setOpenHighLowHARule(openHighLowHARule);
		bean.setSmallCandle(smallCandle);
		bean.setOppositeHighLowRule(oppositeHighLowRule);
		bean.setNr4Rule(nr4Rule);
		bean.setDayHighLowRule(dayHighLowRule);
		bean.setCprRuleInd(cprRuleInd);
		bean.setLastCandle(lastCandle);
		bean.setOhlcState(ohlcState);
		bean.setStrengthFactor(strengthFactor);
		
		bean.setKiteOrder(kiteOrder);
		bean.setKiteOrderId(kiteOrderId);
		
		bean.setDay1VolStrengthFactor(day1VolStrengthFactor);
		bean.setDay2VolStrengthFactor(day2VolStrengthFactor);
		bean.setDay1CandleVolumes(day1CandleVolumes);
		bean.setCurrentCandleVolumesOnTradeEntry(currentCandleVolumesOnTradeEntry);
		bean.setCurrentCandleOHLRuleInd(currentCandleOHLRuleInd);
		
		bean.setVwapVal(vwapVal);
		bean.setCandleMovement(candleMovement);
		bean.setDay1Movement(day1Movement);
		bean.setDay2Movement(day2Movement);
		bean.setMinute5CPR(minute5CPR);
		bean.setMinute15CPR(minute15CPR);
		
		bean.setStrengthFactorString(strengthFactorString);
		bean.setStrenthTradableState(strenthTradableState);
		bean.setStrenthTradableStateNifty(strenthTradableStateNifty);
		return bean;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public Integer getLotSize() {
		return lotSize;
	}
	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}
	public Double getLtp() {
		return ltp;
	}
	public void setLtp(Double ltp) {
		this.ltp = ltp;
	}
	public Double getOpen() {
		return open;
	}
	public void setOpen(Double open) {
		this.open = open;
	}
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public Double getClose() {
		return close;
	}
	public void setClose(Double close) {
		this.close = close;
	}
	public Date getTradedInAt() {
		return tradedInAt;
	}
	public void setTradedInAt(Date tradedInAt) {
		this.tradedInAt = tradedInAt;
	}
	public Date getTradedOutAt() {
		return tradedOutAt;
	}
	public void setTradedOutAt(Date tradedOutAt) {
		this.tradedOutAt = tradedOutAt;
	}
	public String getKiteFutureKey() {
		return kiteFutureKey;
	}
	public void setKiteFutureKey(String kiteFutureKey) {
		this.kiteFutureKey = kiteFutureKey;
	}
	public Double getBuyerSellerDiff() {
		return buyerSellerDiff;
	}
	public void setBuyerSellerDiff(Double buyerSellerDiff) {
		this.buyerSellerDiff = buyerSellerDiff;
	}
	public String getSignalTriggeredInAt() {
		return signalTriggeredInAt;
	}
	public void setSignalTriggeredInAt(String signalTriggeredInAt) {
		this.signalTriggeredInAt = signalTriggeredInAt;
	}
	public String getSignalTriggeredOutAt() {
		return signalTriggeredOutAt;
	}
	public void setSignalTriggeredOutAt(String signalTriggeredOutAt) {
		this.signalTriggeredOutAt = signalTriggeredOutAt;
	}
	public long getInstrumentToken() {
		return instrumentToken;
	}
	public void setInstrumentToken(long instrumentToken) {
		this.instrumentToken = instrumentToken;
	}
	public String getTradableState() {
		return tradableState;
	}
	public void setTradableState(String tradableState) {
		this.tradableState = tradableState;
	}
	public String getTradedState() {
		return tradedState;
	}
	public void setTradedState(String tradedState) {
		this.tradedState = tradedState;
	}
	public Double getTradedVal() {
		return tradedVal;
	}
	public void setTradedVal(Double tradedVal) {
		this.tradedVal = tradedVal;
	}
	public Double getBuyerAt() {
		return buyerAt;
	}
	public void setBuyerAt(Double buyerAt) {
		this.buyerAt = buyerAt;
	}
	public Double getSellerAt() {
		return sellerAt;
	}
	public void setSellerAt(Double sellerAt) {
		this.sellerAt = sellerAt;
	}
	public Double getProfitLossAmt() {
		return profitLossAmt;
	}
	public void setProfitLossAmt(Double profitLossAmt) {
		this.profitLossAmt = profitLossAmt;
	}
	public Double getProfitChaseVal() {
		return profitChaseVal;
	}
	public void setProfitChaseVal(Double profitChaseVal) {
		this.profitChaseVal = profitChaseVal;
	}
	public Double getNegativeDirectionLtp() {
		return negativeDirectionLtp;
	}
	public void setNegativeDirectionLtp(Double negativeDirectionLtp) {
		this.negativeDirectionLtp = negativeDirectionLtp;
	}
	public Double getNegativeDirectionLoss() {
		return negativeDirectionLoss;
	}
	public void setNegativeDirectionLoss(Double negativeDirectionLoss) {
		this.negativeDirectionLoss = negativeDirectionLoss;
	}
	public Double getPositiveDirectionLtp() {
		return positiveDirectionLtp;
	}
	public void setPositiveDirectionLtp(Double positiveDirectionLtp) {
		this.positiveDirectionLtp = positiveDirectionLtp;
	}
	public Double getPositiveDirectionProfit() {
		return positiveDirectionProfit;
	}
	public void setPositiveDirectionProfit(Double positiveDirectionProfit) {
		this.positiveDirectionProfit = positiveDirectionProfit;
	}
	public String getVolumeInfoOnTradeEntry() {
		return volumeInfoOnTradeEntry;
	}
	public void setVolumeInfoOnTradeEntry(String volumeInfoOnTradeEntry) {
		this.volumeInfoOnTradeEntry = volumeInfoOnTradeEntry;
	}
	public Long getVolumes() {
		return volumes;
	}
	public void setVolumes(Long volumes) {
		this.volumes = volumes;
	}
	public String getAdditinalInfo() {
		return additinalInfo;
	}
	public void setAdditinalInfo(String additinalInfo) {
		this.additinalInfo = additinalInfo;
	}
	public boolean isGreen3Candle() {
		return green3Candle;
	}
	public void setGreen3Candle(boolean green3Candle) {
		this.green3Candle = green3Candle;
	}
	public boolean isRed3Candle() {
		return red3Candle;
	}
	public void setRed3Candle(boolean red3Candle) {
		this.red3Candle = red3Candle;
	}
	public String getVolumeTrend() {
		return volumeTrend;
	}
	public void setVolumeTrend(String volumeTrend) {
		this.volumeTrend = volumeTrend;
	}
	public String getVolumeInfoOnTradeEntryNifty50() {
		return volumeInfoOnTradeEntryNifty50;
	}
	public void setVolumeInfoOnTradeEntryNifty50(String volumeInfoOnTradeEntryNifty50) {
		this.volumeInfoOnTradeEntryNifty50 = volumeInfoOnTradeEntryNifty50;
	}
	public String getAdditionalInfoNifty50() {
		return additionalInfoNifty50;
	}
	public void setAdditionalInfoNifty50(String additionalInfoNifty50) {
		this.additionalInfoNifty50 = additionalInfoNifty50;
	}
	public boolean isGreen3CandleNifty50() {
		return green3CandleNifty50;
	}
	public void setGreen3CandleNifty50(boolean green3CandleNifty50) {
		this.green3CandleNifty50 = green3CandleNifty50;
	}
	public boolean isRed3CandleNifty50() {
		return red3CandleNifty50;
	}
	public void setRed3CandleNifty50(boolean red3CandleNifty50) {
		this.red3CandleNifty50 = red3CandleNifty50;
	}
	public String getVolumeTrendNifty50() {
		return volumeTrendNifty50;
	}
	public void setVolumeTrendNifty50(String volumeTrendNifty50) {
		this.volumeTrendNifty50 = volumeTrendNifty50;
	}
	public double getBuyOrSellAt() {
		return buyOrSellAt;
	}
	public void setBuyOrSellAt(double buyOrSellAt) {
		this.buyOrSellAt = buyOrSellAt;
	}
	public double getStopLoss() {
		return stopLoss;
	}
	public void setStopLoss(double stopLoss) {
		this.stopLoss = stopLoss;
	}
	public String getReportListKey() {
		return reportListKey;
	}
	public void setReportListKey(String reportListKey) {
		this.reportListKey = reportListKey;
	}
	public HistoricalData getHistData() {
		return histData;
	}
	public void setHistData(HistoricalData histData) {
		this.histData = histData;
	}
	public double getRsi1() {
		return rsi1;
	}
	public void setRsi1(double rsi1) {
		this.rsi1 = rsi1;
	}
	public double getRsi2() {
		return rsi2;
	}
	public void setRsi2(double rsi2) {
		this.rsi2 = rsi2;
	}
	public double getRsi3() {
		return rsi3;
	}
	public void setRsi3(double rsi3) {
		this.rsi3 = rsi3;
	}
	public Double getDayRsi() {
		return dayRsi;
	}
	public void setDayRsi(Double dayRsi) {
		this.dayRsi = dayRsi;
	}
	/*public boolean isCloseHigherHighRule() {
		return closeHigherHighRule;
	}
	public void setCloseHigherHighRule(boolean closeHigherHighRule) {
		this.closeHigherHighRule = closeHigherHighRule;
	}*/
	public boolean isCloseRule() {
		return closeRule;
	}
	public void setCloseRule(boolean closeRule) {
		this.closeRule = closeRule;
	}
	public boolean isCloseHighRule1() {
		return closeHighRule1;
	}
	public void setCloseHighRule1(boolean closeHighRule1) {
		this.closeHighRule1 = closeHighRule1;
	}
	public boolean isCloseHighRule2() {
		return closeHighRule2;
	}
	public void setCloseHighRule2(boolean closeHighRule2) {
		this.closeHighRule2 = closeHighRule2;
	}
	public boolean isOpenHighLowRule() {
		return openHighLowRule;
	}
	public void setOpenHighLowRule(boolean openHighLowRule) {
		this.openHighLowRule = openHighLowRule;
	}
	public boolean isOpenHighLowHARule() {
		return openHighLowHARule;
	}
	public void setOpenHighLowHARule(boolean openHighLowHARule) {
		this.openHighLowHARule = openHighLowHARule;
	}
	public boolean isSmallCandle() {
		return smallCandle;
	}
	public void setSmallCandle(boolean smallCandle) {
		this.smallCandle = smallCandle;
	}
	public boolean isOppositeHighLowRule() {
		return oppositeHighLowRule;
	}
	public void setOppositeHighLowRule(boolean oppositeHighLowRule) {
		this.oppositeHighLowRule = oppositeHighLowRule;
	}
	public Order getKiteOrder() {
		return kiteOrder;
	}
	public void setKiteOrder(Order kiteOrder) {
		this.kiteOrder = kiteOrder;
	}
	public String getKiteOrderId() {
		return kiteOrderId;
	}
	public void setKiteOrderId(String kiteOrderId) {
		this.kiteOrderId = kiteOrderId;
	}
	public boolean isNr4Rule() {
		return nr4Rule;
	}
	public void setNr4Rule(boolean nr4Rule) {
		this.nr4Rule = nr4Rule;
	}
	public boolean isCprRuleInd() {
		return cprRuleInd;
	}
	public void setCprRuleInd(boolean cprRuleInd) {
		this.cprRuleInd = cprRuleInd;
	}
	public boolean isDayHighLowRule() {
		return dayHighLowRule;
	}
	public void setDayHighLowRule(boolean dayHighLowRule) {
		this.dayHighLowRule = dayHighLowRule;
	}
	public String getLastCandle() {
		return lastCandle;
	}
	public void setLastCandle(String lastCandle) {
		this.lastCandle = lastCandle;
	}
	public String getOhlcState() {
		return ohlcState;
	}
	public void setOhlcState(String ohlcState) {
		this.ohlcState = ohlcState;
	}
	public Double getStrengthFactor() {
		return strengthFactor;
	}
	public void setStrengthFactor(Double strengthFactor) {
		this.strengthFactor = strengthFactor;
	}
	
	private Double day1VolStrengthFactor;
	private Double day2VolStrengthFactor;
	private Long day1CandleVolumes;
	private Long currentCandleVolumesOnTradeEntry;
	private boolean currentCandleOHLRuleInd;
	public Double getDay1VolStrengthFactor() {
		return day1VolStrengthFactor;
	}
	public void setDay1VolStrengthFactor(Double day1VolStrengthFactor) {
		this.day1VolStrengthFactor = day1VolStrengthFactor;
	}
	public Double getDay2VolStrengthFactor() {
		return day2VolStrengthFactor;
	}
	public void setDay2VolStrengthFactor(Double day2VolStrengthFactor) {
		this.day2VolStrengthFactor = day2VolStrengthFactor;
	}
	public Long getDay1CandleVolumes() {
		return day1CandleVolumes;
	}
	public void setDay1CandleVolumes(Long day1CandleVolumes) {
		this.day1CandleVolumes = day1CandleVolumes;
	}
	public Long getCurrentCandleVolumesOnTradeEntry() {
		return currentCandleVolumesOnTradeEntry;
	}
	public void setCurrentCandleVolumesOnTradeEntry(Long currentCandleVolumesOnTradeEntry) {
		this.currentCandleVolumesOnTradeEntry = currentCandleVolumesOnTradeEntry;
	}
	public boolean isCurrentCandleOHLRuleInd() {
		return currentCandleOHLRuleInd;
	}
	public void setCurrentCandleOHLRuleInd(boolean currentCandleOHLRuleInd) {
		this.currentCandleOHLRuleInd = currentCandleOHLRuleInd;
	}
	public Double getVwapVal() {
		return vwapVal;
	}
	public void setVwapVal(Double vwapVal) {
		this.vwapVal = vwapVal;
	}
	public Double getCandleMovement() {
		return candleMovement;
	}
	public void setCandleMovement(Double candleMovement) {
		this.candleMovement = candleMovement;
	}
	public Double getDay1Movement() {
		return day1Movement;
	}
	public void setDay1Movement(Double day1Movement) {
		this.day1Movement = day1Movement;
	}
	public Double getDay2Movement() {
		return day2Movement;
	}
	public void setDay2Movement(Double day2Movement) {
		this.day2Movement = day2Movement;
	}
	public Double getMinute5CPR() {
		return minute5CPR;
	}
	public void setMinute5CPR(Double minute5cpr) {
		minute5CPR = minute5cpr;
	}
	public Double getMinute15CPR() {
		return minute15CPR;
	}
	public void setMinute15CPR(Double minute15cpr) {
		minute15CPR = minute15cpr;
	}
	
	public String getStrenthTradableState() {
		return strenthTradableState;
	}
	public void setStrenthTradableState(String strenthTradableState) {
		this.strenthTradableState = strenthTradableState;
	}
	public String getStrengthFactorString() {
		return strengthFactorString;
	}
	public void setStrengthFactorString(String strengthFactorString) {
		this.strengthFactorString = strengthFactorString;
	}
	public String getStrenthTradableStateNifty() {
		return strenthTradableStateNifty;
	}
	public void setStrenthTradableStateNifty(String strenthTradableStateNifty) {
		this.strenthTradableStateNifty = strenthTradableStateNifty;
	}
	@Override
	public String toString() {
		return "StockDataBean [stockId=" + stockId + ", stockName=" + stockName + ", lotSize=" + lotSize + ", ltp="
				+ ltp + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", tradedInAt="
				+ tradedInAt + ", tradedOutAt=" + tradedOutAt + ", kiteFutureKey=" + kiteFutureKey
				+ ", buyerSellerDiff=" + buyerSellerDiff + ", signalTriggeredInAt=" + signalTriggeredInAt
				+ ", signalTriggeredOutAt=" + signalTriggeredOutAt + ", instrumentToken=" + instrumentToken
				+ ", tradableState=" + tradableState + ", tradedState=" + tradedState + ", tradedVal=" + tradedVal
				+ ", buyerAt=" + buyerAt + ", sellerAt=" + sellerAt + ", profitLossAmt=" + profitLossAmt
				+ ", profitChaseVal=" + profitChaseVal + ", negativeDirectionLtp=" + negativeDirectionLtp
				+ ", negativeDirectionLoss=" + negativeDirectionLoss + ", positiveDirectionLtp=" + positiveDirectionLtp
				+ ", positiveDirectionProfit=" + positiveDirectionProfit + ", volumeInfoOnTradeEntry="
				+ volumeInfoOnTradeEntry + ", volumes=" + volumes + ", additinalInfo=" + additinalInfo
				+ ", green3Candle=" + green3Candle + ", red3Candle=" + red3Candle + ", volumeTrend=" + volumeTrend
				+ ", volumeInfoOnTradeEntryNifty50=" + volumeInfoOnTradeEntryNifty50 + ", additionalInfoNifty50="
				+ additionalInfoNifty50 + ", green3CandleNifty50=" + green3CandleNifty50 + ", red3CandleNifty50="
				+ red3CandleNifty50 + ", volumeTrendNifty50=" + volumeTrendNifty50 + ", buyOrSellAt=" + buyOrSellAt
				+ ", stopLoss=" + stopLoss + ", reportListKey=" + reportListKey + ", histData=" + histData + ", rsi1="
				+ rsi1 + ", rsi2=" + rsi2 + ", rsi3=" + rsi3 + ", closeRule=" + closeRule + ", closeHighRule1="
				+ closeHighRule1 + ", closeHighRule2=" + closeHighRule2 + ", openHighLowRule=" + openHighLowRule
				+ ", openHighLowHARule=" + openHighLowHARule + ", smallCandle=" + smallCandle + ", oppositeHighLowRule="
				+ oppositeHighLowRule + ", nr4Rule=" + nr4Rule + ", dayHighLowRule=" + dayHighLowRule + ", cprRuleInd="
				+ cprRuleInd + ", lastCandle=" + lastCandle + ", ohlcState=" + ohlcState + ", strengthFactor="
				+ strengthFactor + ", vwapVal=" + vwapVal + ", candleMovement=" + candleMovement + ", day1Movement="
				+ day1Movement + ", day2Movement=" + day2Movement + ", minute5CPR=" + minute5CPR + ", minute15CPR="
				+ minute15CPR + ", kiteOrder=" + kiteOrder + ", kiteOrderId=" + kiteOrderId + ", day1VolStrengthFactor="
				+ day1VolStrengthFactor + ", day2VolStrengthFactor=" + day2VolStrengthFactor + ", day1CandleVolumes="
				+ day1CandleVolumes + ", currentCandleVolumesOnTradeEntry=" + currentCandleVolumesOnTradeEntry
				+ ", currentCandleOHLRuleInd=" + currentCandleOHLRuleInd + "]";
	}
	
}