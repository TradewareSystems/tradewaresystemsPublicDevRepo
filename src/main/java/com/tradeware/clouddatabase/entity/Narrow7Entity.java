package com.tradeware.clouddatabase.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.Narrow7Bean;


@Entity
@Table(name = "TABLE_NR7_BREAK_OUT")
public class Narrow7Entity implements Serializable {

	private static final long serialVersionUID = -3994037429022910047L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPEN_RANGE_BREAK_OUT_ID")
	private Integer itemId;

	@Column(name = "SYMBOL")
	private String symbol;

	@Column(name = "LOT_SIZE")
	private Integer lotSize;

	@Column(name = "BUY_AT_VAL")
	private Double buyAtVal;

	@Column(name = "SELL_AT_VAL")
	private Double sellAtVal;

	@Column(name = "TRADABLE_STATE")
	private String tradableState;

	@Column(name = "TRADED_STATE")
	private String tradedState;

	@Column(name = "TRADED_AT_VAL")
	private Double tradedAtVal;

	@Column(name = "EXITED_AT_VAL")
	private Double exitedAtVal;

	@Column(name = "TRADED_AT_DT_TM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date tradedAtDt;

	@Column(name = "EXITED_AT_DT_TM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date exitedAtDt;

	@Column(name = "PROF_LOS_AMT")
	private Double profitLossAmt;

	@Column(name = "BUY_SELL_DIFF")
	private Double buySellDiff;

	@Column(name = "POS_MOVE_VAL")
	private Double positiveMoveValue;

	@Column(name = "NEG_MOVE_VAL")
	private Double negativeMoveValue;

	@Column(name = "VOLUMES")
	private Long volumes;

	@Column(name = "TRADEABLE_STRENGTH")
	private String tradableStrength;

	@Column(name = "STRENGTH_TRADE_STATE")
	private String strengthTradableState;

	@Column(name = "CNDL_1_SIZE")
	private Double candle1SizeAmt;

	@Column(name = "CNDL_2_SIZE")
	private Double candle2SizeAmt;

	@Column(name = "CNDL_3_SIZE")
	private Double candle3SizeAmt;

	@Column(name = "CNDL_4_SIZE")
	private Double candle4SizeAmt;

	@Column(name = "CNDL_1_HIG_MINUS_CLS")
	private Double candle1HighMinusClose;

	@Column(name = "CNDL_1_CLS_MINUS_LOW")
	private Double candle1CloseMinusLow;

	@Column(name = "CNDL_2_HIG_MINUS_CLS")
	private Double candle2HighMinusClose;

	@Column(name = "CNDL_2_CLS_MINUS_LOW")
	private Double candle2CloseMinusLow;

	@Column(name = "CNDL_3_HIG_MINUS_CLS")
	private Double candle3HighMinusClose;

	@Column(name = "CNDL_3_CLS_MINUS_LOW")
	private Double candle3CloseMinusLow;

	@Column(name = "CNDL_4_HIG_MINUS_CLS")
	private Double candle4HighMinusClose;

	@Column(name = "CNDL_4_CLS_MINUS_LOW")
	private Double candle4CloseMinusLow;

	@Column(name = "CNDL_1_TYPE")
	private String candle1Type;

	@Column(name = "CNDL_2_TYPE")
	private String candle2Type;

	@Column(name = "CNDL_3_TYPE")
	private String candle3Type;

	@Column(name = "CNDL_4_TYPE")
	private String candle4Type;

	@Column(name = "OHLC_STATE")
	private String ohlcState;

	@Column(name = "CUST_TRADE_RULE_IND")
	private Boolean tempCustomTradingRuleInd;

	@Column(name = "DT_TM_STAMP")
	@Temporal(TemporalType.DATE)
	private Date tradedDateStamp;

	@Column(name = "OI_INFO")
	private String oiInfo;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getLotSize() {
		return lotSize;
	}

	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}

	public Double getBuyAtVal() {
		return buyAtVal;
	}

	public void setBuyAtVal(Double buyAtVal) {
		this.buyAtVal = buyAtVal;
	}

	public Double getSellAtVal() {
		return sellAtVal;
	}

	public void setSellAtVal(Double sellAtVal) {
		this.sellAtVal = sellAtVal;
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

	public Double getTradedAtVal() {
		return tradedAtVal;
	}

	public void setTradedAtVal(Double tradedAtVal) {
		this.tradedAtVal = tradedAtVal;
	}

	public Double getExitedAtVal() {
		return exitedAtVal;
	}

	public void setExitedAtVal(Double exitedAtVal) {
		this.exitedAtVal = exitedAtVal;
	}

	public Date getTradedAtDt() {
		return tradedAtDt;
	}

	public void setTradedAtDt(Date tradedAtDt) {
		this.tradedAtDt = tradedAtDt;
	}

	public Date getExitedAtDt() {
		return exitedAtDt;
	}

	public void setExitedAtDt(Date exitedAtDt) {
		this.exitedAtDt = exitedAtDt;
	}

	public Double getProfitLossAmt() {
		return profitLossAmt;
	}

	public void setProfitLossAmt(Double profitLossAmt) {
		this.profitLossAmt = profitLossAmt;
	}

	public Double getBuySellDiff() {
		return buySellDiff;
	}

	public void setBuySellDiff(Double buySellDiff) {
		this.buySellDiff = buySellDiff;
	}

	public Double getPositiveMoveValue() {
		return positiveMoveValue;
	}

	public void setPositiveMoveValue(Double positiveMoveValue) {
		this.positiveMoveValue = positiveMoveValue;
	}

	public Double getNegativeMoveValue() {
		return negativeMoveValue;
	}

	public void setNegativeMoveValue(Double negativeMoveValue) {
		this.negativeMoveValue = negativeMoveValue;
	}

	public Long getVolumes() {
		return volumes;
	}

	public void setVolumes(Long volumes) {
		this.volumes = volumes;
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

	public Boolean getTempCustomTradingRuleInd() {
		return tempCustomTradingRuleInd;
	}

	public void setTempCustomTradingRuleInd(Boolean tempCustomTradingRuleInd) {
		this.tempCustomTradingRuleInd = tempCustomTradingRuleInd;
	}

	public Date getTradedDateStamp() {
		return tradedDateStamp;
	}

	public void setTradedDateStamp(Date tradedDateStamp) {
		this.tradedDateStamp = tradedDateStamp;
	}

	public String getOiInfo() {
		return oiInfo;
	}

	public void setOiInfo(String oiInfo) {
		this.oiInfo = oiInfo;
	}

	public void populateEntity(Narrow7Bean bean) {
		this.itemId = bean.getItemId();
		this.symbol = bean.getSymbol();
		this.lotSize = bean.getLotSize();
		this.buyAtVal = bean.getBuyAtVal();
		this.sellAtVal = bean.getSellAtVal();
		this.tradableState = bean.getTradableState();
		this.tradedState = bean.getTradedState();
		this.tradedAtVal = bean.getTradedAtVal();
		this.exitedAtVal = bean.getExitedAtVal();
		this.tradedAtDt = bean.getTradedAtDt();
		this.exitedAtDt = bean.getExitedAtDt();
		this.profitLossAmt = bean.getProfitLossAmt();
		this.buySellDiff = bean.getBuySellDiff();
		this.positiveMoveValue = bean.getPositiveMoveValue();
		this.negativeMoveValue = bean.getNegativeMoveValue();
		this.volumes = bean.getVolumes();
		this.tradableStrength = bean.getTradableStrength();
		this.strengthTradableState = bean.getStrengthTradableState();
		this.candle1SizeAmt = bean.getCandle1SizeAmt();
		this.candle2SizeAmt = bean.getCandle2SizeAmt();
		this.candle3SizeAmt = bean.getCandle3SizeAmt();
		this.candle4SizeAmt = bean.getCandle4SizeAmt();
		this.candle1HighMinusClose = bean.getCandle1HighMinusClose();
		this.candle1CloseMinusLow = bean.getCandle1CloseMinusLow();
		this.candle2HighMinusClose = bean.getCandle2HighMinusClose();
		this.candle2CloseMinusLow = bean.getCandle2CloseMinusLow();
		this.candle3HighMinusClose = bean.getCandle3HighMinusClose();
		this.candle3CloseMinusLow = bean.getCandle3CloseMinusLow();
		this.candle4HighMinusClose = bean.getCandle4HighMinusClose();
		this.candle4CloseMinusLow = bean.getCandle4CloseMinusLow();
		this.candle1Type = bean.getCandle1Type();
		this.candle2Type = bean.getCandle2Type();
		this.candle3Type = bean.getCandle3Type();
		this.candle4Type = bean.getCandle4Type();
		this.ohlcState = bean.getOhlcState();
		this.tempCustomTradingRuleInd = bean.getTempCustomTradingRuleInd();
		this.tradedDateStamp = bean.getTradedDateStamp();
		this.oiInfo = bean.getOiInfo();
	}

	public void populateBean(Narrow7Bean bean) {
		bean.setItemId(this.itemId);
		bean.setSymbol(this.symbol);
		bean.setLotSize(this.lotSize);
		bean.setBuyAtVal(this.buyAtVal);
		bean.setSellAtVal(this.sellAtVal);
		bean.setTradableState(this.tradableState);
		bean.setTradedState(this.tradedState);
		bean.setTradedAtVal(this.tradedAtVal);
		bean.setExitedAtVal(this.exitedAtVal);
		bean.setTradedAtDt(this.tradedAtDt);
		bean.setExitedAtDt(this.exitedAtDt);
		bean.setProfitLossAmt(this.profitLossAmt);
		bean.setBuySellDiff(this.buySellDiff);
		bean.setPositiveMoveValue(this.positiveMoveValue);
		bean.setNegativeMoveValue(this.negativeMoveValue);
		bean.setVolumes(this.volumes);
		bean.setTradableStrength(tradableStrength);
		bean.setStrengthTradableState(strengthTradableState);
		bean.setCandle1SizeAmt(candle1SizeAmt);
		bean.setCandle2SizeAmt(candle2SizeAmt);
		bean.setCandle3SizeAmt(candle3SizeAmt);
		bean.setCandle4SizeAmt(candle4SizeAmt);
		bean.setCandle1HighMinusClose(candle1HighMinusClose);
		bean.setCandle1CloseMinusLow(candle1CloseMinusLow);
		bean.setCandle2HighMinusClose(candle2HighMinusClose);
		bean.setCandle2CloseMinusLow(candle2CloseMinusLow);
		bean.setCandle3HighMinusClose(candle3HighMinusClose);
		bean.setCandle3CloseMinusLow(candle3CloseMinusLow);
		bean.setCandle4HighMinusClose(candle4HighMinusClose);
		bean.setCandle4CloseMinusLow(candle4CloseMinusLow);
		bean.setCandle1Type(candle1Type);
		bean.setCandle2Type(candle2Type);
		bean.setCandle3Type(candle3Type);
		bean.setCandle4Type(candle4Type);
		bean.setOhlcState(ohlcState);
		bean.setTempCustomTradingRuleInd(tempCustomTradingRuleInd);
		bean.setTradedDateStamp(this.tradedDateStamp);
		bean.setOiInfo(this.oiInfo);
	}
}