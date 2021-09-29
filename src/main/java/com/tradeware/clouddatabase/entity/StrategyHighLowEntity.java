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

import com.tradeware.clouddatabase.bean.StrategyHighLowBean;

@Entity
//@Table(name = "TABLE_HIGH_LOW")
@Table(name = "T_HIGH_LOW")
public class StrategyHighLowEntity implements Serializable {

	private static final long serialVersionUID = 8775319443012306963L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HIGH_LOW_ID")
	private Integer itemId;
	
	@Column(name = "SYMBOL")
	private String symbol;
	
	@Column(name = "LOT_SIZE")
	private Integer lotSize;
	
	@Column(name = "BUY_VAL")
	private Double buyValue;
	
	@Column(name = "SELL_VAL")
	private Double sellValue;
	
	@Column(name = "TRADABLE_STATE")
	private String tradableState;
	
	@Column(name = "TRADED_STATE")
	private String tradedState;
	
	@Column(name = "TRADED_AT")
	private Double tradedAt;
	
	@Column(name = "EXITED_AT")
	private Double exitedAt;
	
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
	
	@Column(name = "ADD_INFO_1")
	private String additionalInfo1;
	
	@Column(name = "VOLUMES")
	private Long volumes;
 	
	@Column(name = "CLOSE_RULE")
	private Boolean closeRule;
	
	@Column(name = "CLOSE_HIGH_1")
	private Boolean closeHighRule1;
	
	@Column(name = "CLOSE_HIGH_2")
	private Boolean closeHighRule2;
	
	@Column(name = "OHL_RULE")
	private Boolean openHighLowRule;
	
	@Column(name = "OHL_HEIKEN_ASHI_RULE")
	private Boolean openHighLowHARule;

	@Column(name = "DT_TM_STAMP")
	@Temporal(TemporalType.DATE)
	private Date tradedDate;
	
	@Column(name = "SML_CNDL_IND")
	private Boolean smallCandle;
	
	@Column(name = "OPP_HIGH_LOW_IND")
	private Boolean oppositeHighLowRule;
	
	@Column(name = "NR4_IND")
	private Boolean nr4Rule;
	
	@Column(name = "DAY_HIGH_LOW_IND")
	private Boolean dayHighLowRule;
	
	@Column(name = "VOLUME_TREND")
	private String volumeTrend;
	
	@Column(name = "LAST_CANL")
	private String lastCandle;
	
	@Column(name = "OHLC_STATE")
	private String ohlcState;
	
	@Column(name = "STRENGTH_FACTOR")
	private Double strengthFactor;
	
	@Column(name = "DAY1_VOL_STRNT_FACTOR")
	private Double day1VolStrengthFactor;
	
	@Column(name = "DAY2_VOL_STRNT_FACTOR")
	private Double day2VolStrengthFactor;
	
	@Column(name = "DAY1_CNDL_VOL")
	private Long day1CandleVolumes;
	
	@Column(name = "CURRENT_CNDL_VOL")
	private Long currentCandleVolumesOnTradeEntry;
	
	@Column(name = "CURRENT_CNDL_OHLC_IND")
	private Boolean currentCandleOHLRuleInd;
	
	@Column(name = "VWAP_VAL")
	private Double vwapVal;
	
	@Column(name = "RSI_VAL")
	private Double rsiVal;
	
	@Column(name = "CNDL_MOVMNT")
	private Double candleMovement;
	
	@Column(name = "DAY1_CNDL_MOVMNT")
	private Double day1Movement;
	
	@Column(name = "DAY2_CNDL_MOVMNT")
	private Double day2Movement;
	
	@Column(name = "MINUTE5_CPR")
	private Double minute5CPR;
	
	@Column(name = "MINUTE15_CPR")
	private Double minute15CPR;
	
	@Column(name = "ADD_INFO_NIFTY50")
	private String additionalInfoNifty50;
	
	@Column(name = "STRENGTH_TRADABLE_STATE")
	private String strenthTradableState;
	
	@Column(name = "STRENGTH_TRADABLE_STATE_NIFTY50")
	private String strenthTradableStateNifty;
	
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
	public Double getBuyValue() {
		return buyValue;
	}
	public void setBuyValue(Double buyValue) {
		this.buyValue = buyValue;
	}
	public Double getSellValue() {
		return sellValue;
	}
	public void setSellValue(Double sellValue) {
		this.sellValue = sellValue;
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
	public Double getTradedAt() {
		return tradedAt;
	}
	public void setTradedAt(Double tradedAt) {
		this.tradedAt = tradedAt;
	}
	public Double getExitedAt() {
		return exitedAt;
	}
	public void setExitedAt(Double exitedAt) {
		this.exitedAt = exitedAt;
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
	public String getAdditionalInfo1() {
		return additionalInfo1;
	}
	public void setAdditionalInfo1(String additionalInfo1) {
		this.additionalInfo1 = additionalInfo1;
	}
	public Long getVolumes() {
		return volumes;
	}
	public void setVolumes(Long volumes) {
		this.volumes = volumes;
	}
	public Boolean getCloseRule() {
		return closeRule;
	}
	public void setCloseRule(Boolean closeRule) {
		this.closeRule = closeRule;
	}
	public Boolean getCloseHighRule1() {
		return closeHighRule1;
	}
	public void setCloseHighRule1(Boolean closeHighRule1) {
		this.closeHighRule1 = closeHighRule1;
	}
	public Boolean getCloseHighRule2() {
		return closeHighRule2;
	}
	public void setCloseHighRule2(Boolean closeHighRule2) {
		this.closeHighRule2 = closeHighRule2;
	}
	public Boolean getOpenHighLowRule() {
		return openHighLowRule;
	}
	public void setOpenHighLowRule(Boolean openHighLowRule) {
		this.openHighLowRule = openHighLowRule;
	}
	public Boolean getOpenHighLowHARule() {
		return openHighLowHARule;
	}
	public void setOpenHighLowHARule(Boolean openHighLowHARule) {
		this.openHighLowHARule = openHighLowHARule;
	}
	public Date getTradedDate() {
		return tradedDate;
	}
	public void setTradedDate(Date tradedDate) {
		this.tradedDate = tradedDate;
	}
	
	public Boolean getSmallCandle() {
		return smallCandle;
	}
	public void setSmallCandle(Boolean smallCandle) {
		this.smallCandle = smallCandle;
	}
	public Boolean getOppositeHighLowRule() {
		return oppositeHighLowRule;
	}
	public void setOppositeHighLowRule(Boolean oppositeHighLowRule) {
		this.oppositeHighLowRule = oppositeHighLowRule;
	}
	public Boolean getNr4Rule() {
		return nr4Rule;
	}
	public void setNr4Rule(Boolean nr4Rule) {
		this.nr4Rule = nr4Rule;
	}
	public Boolean getDayHighLowRule() {
		return dayHighLowRule;
	}
	public void setDayHighLowRule(Boolean dayHighLowRule) {
		this.dayHighLowRule = dayHighLowRule;
	}
	public String getVolumeTrend() {
		return volumeTrend;
	}
	public void setVolumeTrend(String volumeTrend) {
		this.volumeTrend = volumeTrend;
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
	public Boolean getCurrentCandleOHLRuleInd() {
		return currentCandleOHLRuleInd;
	}
	public void setCurrentCandleOHLRuleInd(Boolean currentCandleOHLRuleInd) {
		this.currentCandleOHLRuleInd = currentCandleOHLRuleInd;
	}
	public Double getVwapVal() {
		return vwapVal;
	}
	public void setVwapVal(Double vwapVal) {
		this.vwapVal = vwapVal;
	}
	public Double getRsiVal() {
		return rsiVal;
	}
	public void setRsiVal(Double rsiVal) {
		this.rsiVal = rsiVal;
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
	public String getAdditionalInfoNifty50() {
		return additionalInfoNifty50;
	}
	public void setAdditionalInfoNifty50(String additionalInfoNifty50) {
		this.additionalInfoNifty50 = additionalInfoNifty50;
	}
	public String getStrenthTradableState() {
		return strenthTradableState;
	}
	public void setStrenthTradableState(String strenthTradableState) {
		this.strenthTradableState = strenthTradableState;
	}
	public String getStrenthTradableStateNifty() {
		return strenthTradableStateNifty;
	}
	public void setStrenthTradableStateNifty(String strenthTradableStateNifty) {
		this.strenthTradableStateNifty = strenthTradableStateNifty;
	}
	public void populateEntity(StrategyHighLowBean bean) {
		this.itemId = bean.getItemId();
		this.symbol = bean.getSymbol();
		this.lotSize = bean.getLotSize();
		this.buyValue = bean.getBuyValue();
		this.sellValue = bean.getSellValue();
		this.tradableState = bean.getTradableState();
		this.tradedState = bean.getTradedState();
		this.tradedAt = bean.getTradedAt();
		this.exitedAt = bean.getExitedAt();
		this.tradedAtDt = bean.getTradedAtDt();
		this.exitedAtDt = bean.getExitedAtDt();
		this.profitLossAmt = bean.getProfitLossAmt();
		this.buySellDiff = bean.getBuySellDiff();
		this.positiveMoveValue = bean.getPositiveMoveValue();
		this.negativeMoveValue = bean.getNegativeMoveValue();
		this.additionalInfo1 = bean.getAdditionalInfo1();
		this.volumes = bean.getVolumes();
		this.closeRule = bean.getCloseRule();
		this.closeHighRule1 = bean.getCloseHighRule1();
		this.closeHighRule2 = bean.getCloseHighRule2();
		this.openHighLowRule = bean.getOpenHighLowRule();
		this.openHighLowHARule = bean.getOpenHighLowHARule();
		this.tradedDate = bean.getTradedDate();
		this.smallCandle = bean.getSmallCandle();
		this.oppositeHighLowRule = bean.getOppositeHighLowRule();
		this.nr4Rule = bean.getNr4Rule();
		this.dayHighLowRule = bean.getDayHighLowRule();
		this.volumeTrend = bean.getVolumeTrend();
		this.lastCandle = bean.getLastCandle();
		this.ohlcState = bean.getOhlcState();
		this.strengthFactor = bean.getStrengthFactor();
		
		this.day1VolStrengthFactor = bean.getDay1VolStrengthFactor();
		this.day2VolStrengthFactor = bean.getDay2VolStrengthFactor();
		this.day1CandleVolumes = bean.getDay1CandleVolumes();
		this.currentCandleVolumesOnTradeEntry = bean.getCurrentCandleVolumesOnTradeEntry();
		this.currentCandleOHLRuleInd = bean.getCurrentCandleOHLRuleInd();
		
		this.vwapVal = bean.getVwapVal();
		this.rsiVal = bean.getRsiVal();
		this.candleMovement = bean.getCandleMovement();
		this.day1Movement = bean.getDay1Movement();
		this.day2Movement = bean.getDay2Movement();
		this.minute5CPR = bean.getMinute5CPR();
		this.minute15CPR = bean.getMinute15CPR();
		this.additionalInfoNifty50 = bean.getAdditionalInfoNifty50();
		this.strenthTradableState = bean.getStrenthTradableState();
		this.strenthTradableStateNifty = bean.getStrenthTradableStateNifty();
	}
	
	public void populateBean(StrategyHighLowBean bean) {
		bean.setItemId(this.itemId);
		bean.setSymbol(this.symbol);
		bean.setLotSize(this.lotSize);
		bean.setBuyValue(this.buyValue);
		bean.setSellValue(this.sellValue);
		bean.setTradableState(this.tradableState);
		bean.setTradedState(this.tradedState);
		bean.setTradedAt(this.tradedAt);
		bean.setExitedAt(this.exitedAt);
		bean.setTradedAtDt(this.tradedAtDt);
		bean.setExitedAtDt(this.exitedAtDt);
		bean.setProfitLossAmt(this.profitLossAmt);
		bean.setBuySellDiff(this.buySellDiff);
		bean.setPositiveMoveValue(this.positiveMoveValue);
		bean.setNegativeMoveValue(this.negativeMoveValue);
		bean.setAdditionalInfo1(this.additionalInfo1);
		bean.setVolumes(this.volumes);
		bean.setCloseRule(this.closeRule);
		bean.setCloseHighRule1(this.closeHighRule1);
		bean.setCloseHighRule2(this.closeHighRule2);
		bean.setOpenHighLowRule(this.openHighLowRule);
		bean.setOpenHighLowHARule(this.openHighLowHARule);
		bean.setTradedDate(this.tradedDate);
		bean.setSmallCandle(this.smallCandle);
		bean.setOppositeHighLowRule(this.oppositeHighLowRule);
		bean.setNr4Rule(this.nr4Rule);
		bean.setDayHighLowRule(this.dayHighLowRule);
		bean.setVolumeTrend(this.volumeTrend);
		bean.setLastCandle(this.lastCandle);
		bean.setOhlcState(this.ohlcState);
		bean.setStrengthFactor(this.strengthFactor);
		
		 bean.setDay1VolStrengthFactor(this.day1VolStrengthFactor);
		 bean.setDay2VolStrengthFactor(this.day2VolStrengthFactor);
		 bean.setDay1CandleVolumes(this.day1CandleVolumes);
		 bean.setCurrentCandleVolumesOnTradeEntry(this.currentCandleVolumesOnTradeEntry);
		 bean.setCurrentCandleOHLRuleInd(this.currentCandleOHLRuleInd);

		bean.setVwapVal(this.vwapVal);
		bean.setRsiVal(this.rsiVal);
		bean.setCandleMovement(this.candleMovement);
		bean.setDay1Movement(this.day1Movement);
		bean.setDay2Movement(this.day2Movement);
		bean.setMinute5CPR(this.minute5CPR);
		bean.setMinute15CPR(this.minute15CPR);
		bean.setAdditionalInfoNifty50(this.additionalInfoNifty50);
		bean.setStrenthTradableState(this.strenthTradableState);
		bean.setStrenthTradableStateNifty(this.strenthTradableStateNifty);
	}
}
