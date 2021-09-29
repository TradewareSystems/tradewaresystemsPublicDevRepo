package nr4.sathvikashviktechtrader.stockmarket.bean;

import java.io.Serializable;
import java.util.Date;

public class StockDataBean implements Serializable,  Cloneable {

	private static final long serialVersionUID = -4916897055242650330L;
	private String symbolId; //Symbol Id
	private String symbolName; //Symbol Name
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
	
	private double buyAtVal;
	private double sellAtVal;
	private double buyOrSellAt;
	private double stopLoss;
	private double targetPrice;
	private Long volumes;
	
	private String tradableState;
	private String tradedState;
	private Double tradedVal;
	private Double buyerAt;
	private Double sellerAt;
	private Double profitLossAmt;
	private Double profitChaseVal;
	
	private Double negativeDirectionLtp;
	private Double negativeDirectionLoss;
	private Double positiveDirectionLtp;
	private Double positiveDirectionProfit;
	
	private String kiteOrderId;
	private Integer numberOfLots;
	
	private String tradableStrength;
	private String strengthTradableState;
	
	//ORB CPR properties
	private Double candleHighsDiff;
	private Double candleLowsDiff;
	private Double candle1SizeAmt;
	private Double candle2SizeAmt;
	private Double candle3SizeAmt;
	private Double candle4SizeAmt;
	private Double candle1HighMinusClose;
	private Double candle1CloseMinusLow;
	private Double candle2HighMinusClose;
	private Double candle2CloseMinusLow;
	private Double candle3HighMinusClose;
	private Double candle3CloseMinusLow;
	private Double candle4HighMinusClose;
	private Double candle4CloseMinusLow;
	
	private String candle1Type;
	private String candle2Type;
	private String candle3Type;
	private String candle4Type;
	private String ohlcState;
	private boolean tempCustomTradingRuleInd;
	private String reportKey;
	
	public StockDataBean() {
		super();
	}

	public StockDataBean(Integer lotSize, String symbolName) {
		super();
		this.lotSize = lotSize;
		this.symbolName = symbolName;
		this.tradableState = "NA";
		this.tradedState = "NA";
		this.strengthTradableState = "NA";
		this.profitLossAmt = 0d;
		this.negativeDirectionLoss = 0d;
		this.positiveDirectionProfit = 0d;
		this.numberOfLots = 1;
	}
	
	public StockDataBean clone() {
		StockDataBean bean = new StockDataBean(this.lotSize, this.symbolName);
		bean.setSymbolId(this.symbolId);
		bean.setSymbolName(this.symbolName);
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
		
		bean.setBuyAtVal(buyAtVal);
		bean.setSellAtVal(sellAtVal);
		bean.setBuyOrSellAt(this.buyOrSellAt);
		bean.setStopLoss(this.stopLoss);
		bean.setTargetPrice(this.targetPrice);
		bean.setVolumes(this.volumes);
		bean.setKiteOrderId(this.kiteOrderId);
		bean.setNumberOfLots(this.numberOfLots);
		bean.setStrengthTradableState(strengthTradableState);
		bean.setTradableStrength(tradableStrength);
		bean.setCandle1SizeAmt(candle1SizeAmt);
		bean.setCandle2SizeAmt(candle2SizeAmt);
		bean.setCandleHighsDiff(candleHighsDiff);
		bean.setCandleLowsDiff(candleLowsDiff);
		bean.setCandle1HighMinusClose(candle1HighMinusClose);
		bean.setCandle1CloseMinusLow(candle1CloseMinusLow);
		bean.setCandle2HighMinusClose(candle2HighMinusClose);
		bean.setCandle2CloseMinusLow(candle2CloseMinusLow);
		bean.setCandle1Type(candle1Type);
		bean.setCandle2Type(candle2Type);
		bean.setOhlcState(ohlcState);
		bean.setTempCustomTradingRuleInd(tempCustomTradingRuleInd);
		bean.setReportKey(reportKey);
		
		bean.setOi(oi);
		bean.setOiDayHigh(oiDayHigh);
		bean.setOiDayLow(oiDayLow);
		bean.setOi1(oi1);
		bean.setOi2(oi2);
		bean.setOi3(oi3);
		bean.setOiDayHigh1(oiDayHigh1);
		bean.setOiDayHigh2(oiDayHigh2);
		bean.setOiDayHigh3(oiDayHigh3);
		bean.setOiDayLow1(oiDayLow1);
		bean.setOiDayLow2(oiDayLow2);
		bean.setOiDayLow3(oiDayLow3);
		bean.setOiInfo(oiInfo);
		return bean;
	}

	public String getSymbolId() {
		return symbolId;
	}

	public void setSymbolId(String symbolId) {
		this.symbolId = symbolId;
	}

	public String getSymbolName() {
		return symbolName;
	}

	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
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

	public double getBuyOrSellAt() {
		return buyOrSellAt;
	}

	public void setBuyOrSellAt(double buyOrSellAt) {
		this.buyOrSellAt = buyOrSellAt;
	}

	public double getBuyAtVal() {
		return buyAtVal;
	}

	public void setBuyAtVal(double buyAtVal) {
		this.buyAtVal = buyAtVal;
	}

	public double getSellAtVal() {
		return sellAtVal;
	}

	public void setSellAtVal(double sellAtVal) {
		this.sellAtVal = sellAtVal;
	}

	public double getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(double stopLoss) {
		this.stopLoss = stopLoss;
	}

	public double getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(double targetPrice) {
		this.targetPrice = targetPrice;
	}

	public Long getVolumes() {
		return volumes;
	}

	public void setVolumes(Long volumes) {
		this.volumes = volumes;
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

	public String getKiteOrderId() {
		return kiteOrderId;
	}

	public void setKiteOrderId(String kiteOrderId) {
		this.kiteOrderId = kiteOrderId;
	}

	public Integer getNumberOfLots() {
		return numberOfLots;
	}

	public void setNumberOfLots(Integer numberOfLots) {
		this.numberOfLots = numberOfLots;
	}

	public String getTradableStrength() {
		return tradableStrength;
	}

	public void setTradableStrength(String tradableStrength) {
		this.tradableStrength = tradableStrength;
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

	public String getOhlcState() {
		return ohlcState;
	}

	public void setOhlcState(String ohlcState) {
		this.ohlcState = ohlcState;
	}

	public String getReportKey() {
		return reportKey;
	}

	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}

	public String getStrengthTradableState() {
		return strengthTradableState;
	}

	public void setStrengthTradableState(String strengthTradableState) {
		this.strengthTradableState = strengthTradableState;
	}

	public boolean isTempCustomTradingRuleInd() {
		return tempCustomTradingRuleInd;
	}

	public void setTempCustomTradingRuleInd(boolean tempCustomTradingRuleInd) {
		this.tempCustomTradingRuleInd = tempCustomTradingRuleInd;
	}

	@Override
	public String toString() {
		return "StockDataBean [symbolId=" + symbolId + ", symbolName=" + symbolName + ", lotSize=" + lotSize + ", ltp="
				+ ltp + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", tradedInAt="
				+ tradedInAt + ", tradedOutAt=" + tradedOutAt + ", kiteFutureKey=" + kiteFutureKey
				+ ", buyerSellerDiff=" + buyerSellerDiff + ", signalTriggeredInAt=" + signalTriggeredInAt
				+ ", signalTriggeredOutAt=" + signalTriggeredOutAt + ", instrumentToken=" + instrumentToken
				+ ", buyAtVal=" + buyAtVal + ", sellAtVal=" + sellAtVal + ", buyOrSellAt=" + buyOrSellAt + ", stopLoss="
				+ stopLoss + ", targetPrice=" + targetPrice + ", volumes=" + volumes + ", tradableState="
				+ tradableState + ", tradedState=" + tradedState + ", tradedVal=" + tradedVal + ", buyerAt=" + buyerAt
				+ ", sellerAt=" + sellerAt + ", profitLossAmt=" + profitLossAmt + ", profitChaseVal=" + profitChaseVal
				+ ", negativeDirectionLtp=" + negativeDirectionLtp + ", negativeDirectionLoss=" + negativeDirectionLoss
				+ ", positiveDirectionLtp=" + positiveDirectionLtp + ", positiveDirectionProfit="
				+ positiveDirectionProfit + ", kiteOrderId=" + kiteOrderId + ", numberOfLots=" + numberOfLots
				+ ", tradableStrength=" + tradableStrength + ", strengthTradableState=" + strengthTradableState
				+ ", candle1SizeAmt=" + candle1SizeAmt + ", candle2SizeAmt=" + candle2SizeAmt + ", candleHighsDiff="
				+ candleHighsDiff + ", candleLowsDiff=" + candleLowsDiff + ", candle1HighMinusClose="
				+ candle1HighMinusClose + ", candle1CloseMinusLow=" + candle1CloseMinusLow + ", candle2HighMinusClose="
				+ candle2HighMinusClose + ", candle2CloseMinusLow=" + candle2CloseMinusLow + ", candle1Type="
				+ candle1Type + ", candle2Type=" + candle2Type + ", ohlcState=" + ohlcState + ", reportKey=" + reportKey
				+ "]";
	}
	
	private double oi;
	private double oiDayHigh;
	private double oiDayLow;
	private double oi1;
	private double oi2;
	private double oi3;
	private double oiDayHigh1;
	private double oiDayHigh2;
	private double oiDayHigh3;
	private double oiDayLow1;
	private double oiDayLow2;
	private double oiDayLow3;
	private String oiInfo;

	public double getOi() {
		return oi;
	}

	public void setOi(double oi) {
		this.oi = oi;
	}

	public double getOiDayHigh() {
		return oiDayHigh;
	}

	public void setOiDayHigh(double oiDayHigh) {
		this.oiDayHigh = oiDayHigh;
	}

	public double getOiDayLow() {
		return oiDayLow;
	}

	public void setOiDayLow(double oiDayLow) {
		this.oiDayLow = oiDayLow;
	}

	public double getOi1() {
		return oi1;
	}

	public void setOi1(double oi1) {
		this.oi1 = oi1;
	}

	public double getOi2() {
		return oi2;
	}

	public void setOi2(double oi2) {
		this.oi2 = oi2;
	}

	public double getOi3() {
		return oi3;
	}

	public void setOi3(double oi3) {
		this.oi3 = oi3;
	}

	public double getOiDayHigh1() {
		return oiDayHigh1;
	}

	public void setOiDayHigh1(double oiDayHigh1) {
		this.oiDayHigh1 = oiDayHigh1;
	}

	public double getOiDayHigh2() {
		return oiDayHigh2;
	}

	public void setOiDayHigh2(double oiDayHigh2) {
		this.oiDayHigh2 = oiDayHigh2;
	}

	public double getOiDayHigh3() {
		return oiDayHigh3;
	}

	public void setOiDayHigh3(double oiDayHigh3) {
		this.oiDayHigh3 = oiDayHigh3;
	}

	public double getOiDayLow1() {
		return oiDayLow1;
	}

	public void setOiDayLow1(double oiDayLow1) {
		this.oiDayLow1 = oiDayLow1;
	}

	public double getOiDayLow2() {
		return oiDayLow2;
	}

	public void setOiDayLow2(double oiDayLow2) {
		this.oiDayLow2 = oiDayLow2;
	}

	public double getOiDayLow3() {
		return oiDayLow3;
	}

	public void setOiDayLow3(double oiDayLow3) {
		this.oiDayLow3 = oiDayLow3;
	}

	public String getOiInfo() {
		return oiInfo;
	}

	public void setOiInfo(String oiInfo) {
		this.oiInfo = oiInfo;
	}

}