package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tradeware.clouddatabase.bean.StrategyOrbBean;
import com.tradeware.stockmarket.util.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "T_OPEN_RANGE_BREAK_OUT", uniqueConstraints = {
		@UniqueConstraint(name = "OPEN_RANGE_BREAK_OUT_CONSTRAINT", columnNames = {
				"OPEN_RANGE_BREAK_OUT_ID"/* ,"col2" */ }) })
public class StrategyOrbEntity extends AbstractEntity {

	private static final long serialVersionUID = 1949578932574851313L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPEN_RANGE_BREAK_OUT_ID", unique = true, nullable = false, columnDefinition="serial")
	private Integer itemId;
	
	@Column(name = "SYMBOL_ID", length=32)
	private String symbolId;
	
	@Column(name = "SYMBOL_NAME", length=32)
	private String symbolName;
	
	@Column(name = "LOT_SIZE", precision=6)
	private Integer lotSize;
	
	@Column(name = "BUY_AT_VAL", precision=7, scale=2)
	private Double buyAtVal;
	
	@Column(name = "SELL_AT_VAL", precision=7, scale=2)
	private Double sellAtVal;
	
	@Column(name = "TRADED_AT_VAL", precision=7, scale=2)
	private Double tradedAtVal;
	
	@Column(name = "EXITED_AT_VAL", precision=7, scale=2)
	private Double exitedAtVal;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRADED_AT_DT_TM")
	private Date tradedAtDtTm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXITED_AT_DT_TM")
	private Date exitedAtDtTm;
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "TRADABLE_STATE_ID", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "TRADE_STATE_ID")
	private TradeStateLookupEntity tradableStateId;

	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "TRADED_STATE_ID", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "TRADE_STATE_ID")
	private TradeStateLookupEntity tradedStateId;
	
	@Column(name = "BUY_SELL_DIFF_VAL", precision=27, scale=2)
	private Double buyerSellerDiffVal; 
	
	@Column(name = "VWAP_VAL", precision=7, scale=2)
	private Double vwapValue;//In other entity also
	
	@Column(name = "TRADE_TYPE", length=12)
	private String tradeType;
	
	@Column(name = "TRADED_LOT_COUNT")
	private Integer tradedLotCount;//In other entity also
	
	
	
	@Column(name = "BUYER_QUANTITY_VAL", precision=17, scale=2)
	private Double buyerQuantityVal; 
	
	@Column(name = "SELLER_QUANTITY_VAL", precision=17, scale=2)
	private Double sellerQuantityVal;
	
	@Column(name = "PROF_LOS_AMT_VAL", precision=7, scale=2)
	private Double profitLossAmtVal; 
	
	@Column(name = "POS_MOVE_VAL", precision=7, scale=2)
	private Double positiveMoveVal;
	
	@Column(name = "NEG_MOVE_VAL", precision=7, scale=2)
	private Double negativeMoveVal;
	
	@Column(name = "POS_MOVE_LTP", precision=7, scale=2)
	private Double positiveMoveLtp;
	
	@Column(name = "NEG_MOVE_LTP", precision=7, scale=2)
	private Double negativeMoveLtp;
	
	@Column(name = "VOLUMES", precision=17, scale=2)
	private Long volumes;
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "OHLC_STATE_ID", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "TRADE_STATE_ID")
	private TradeStateLookupEntity ohlcStateId;

	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "STRENGTHABLE_TRADE_STATE_ID", nullable = true, 
		updatable = true, insertable = true, unique = false, referencedColumnName = "TRADE_STATE_ID")
	private TradeStateLookupEntity strengthableTradeStateId;
	
	@Column(name = "STRNTH_TRADABLE_STY_CLS", length=16)
	private String strengthStyleClass;
	
	@Column(name = "CNDL_NUM", precision=4)
	private Integer candleNumber;
	
	@Column(name = "DAY_LVL_TRADE_IND")
	private Boolean dayLevelTradeInd;
	
	@Column(name = "GAP_UP_DOWN_MOVE_VAL", precision=7, scale=2)
	private Double gapUpDownMoveVal;
	
	@Column(name = "CUST_TRADE_RULE_IND")
	private Boolean tempCustomTradingRuleInd;
	
	@Column(name = "REVERSE_MOTHER_CNDL_IND")
	private Boolean reverseMotherCandleInd;
	
	@Column(name = "DT_STAMP")
	@Temporal(TemporalType.DATE)
	private Date tradedDateStamp;
	
	//@Column(name = "HEIKIN_ASHI_TREND")
	//private String heikinAshiTrend;
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "HEIKIN_ASHI_TREND", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "VOLUME_TREND_ID")
	private VolumeTrendLookupEntity heikinAshiTrendId;
	
	@Column(name = "OI_TREND")
	private String optionChainTrend;
	
	@Column(name = "OI_PRICE_TREND")
	private String optionChainPriceTrend;
	
	@Column(name = "OPTION_CHAIN_ID")
	private Integer optionChainId;
	
	@Column(name = "OI_INFO", length=128)
	private String oiInfo;
	
	
	@Column(name = "BUY_SELL_RATIO")
	private Double buySellQuantityRatio;
	
	@Column(name = "PERCEN_CHANGE", precision=5, scale=2)
	private Double percentageChange;
	
	@Column(name = "STRATEGY_RULE", length=12)
	private String strategyRule;
	
	@Column(name = "CNDL_TIME_FRAME", length=16)
	private String candleTimeFrame;
	
	@Column(name = "TARGET_PRICE", precision=7, scale=2)
	private Double targetPrice;
	
	@Column(name = "STOP_LOSS", precision = 7, scale = 2, nullable = true)
	private Double stopLoss;
	
	@Column(name = "CURRENT_CNDL_OPEN", precision=7, scale=2)
	private Double currentCandleOpen;//In other entity also
	
	@Column(name = "BUY_SELL_DIFF_VAL_2", precision=7, scale=2)
	private Double buyerSellerDiffVal2;//In other entity also
	
	
	@Column(name = "BUY_SELL_DIFF_VAL_3", precision=7, scale=2)
	private Double buyerSellerDiffVal3;//In other entity also

	@Column(name = "TRADE_COUNT")
	private Integer tradeCountPerDay;
	
	@Column(name = "PROF_LOS_AMT_VAL_FINAL", precision=9, scale=2)
	private Double profitLossAmtValFinal;
	
	@Column(name = "STRENGTHABLE_TRADE_INFO", length=128)
	private String strengthableTradeInfo;
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "VWAP_TRADE_STATE_ID", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "VOLUME_TREND_ID")
	private VolumeTrendLookupEntity vwapTradeStateId;
	
	//TODO : may not require
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "VOLUME_TRADE_STATE_ID", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "VOLUME_TREND_ID")
	private VolumeTrendLookupEntity volumeTradeStateId;
	
	
	// 04-21-2021 start - afterSomeSuccess  [04-27-2021]
	@Column(name = "MNAL_TRADE_EXIT_IND")
	private Boolean manualTradeExitInd;
	
	@Column(name = "MNAL_EXITED_AT_VAL", precision=7, scale=2)
	private Double manualExitedAtVal;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MNAL_EXITED_AT_DT_TM")
	private Date manualExitedAtDtTm;
	
	@Column(name = "MNAL_PROF_LOS_AMT_VAL", precision=7, scale=2)
	private Double manualBookProfitLossAmtVal;
	
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "MNAL_TRADE_EXIT_STATE_ID", nullable = true, updatable = true, 
		insertable = true, unique = false, referencedColumnName = "TRADE_STATE_ID")
	private TradeStateLookupEntity manualTradeExitStateId;
	
	@Column(name = "WAIT_ENGULFING_COUNT")
	private Integer waitForEngulfingCount;
	// 04-21-2021 end
	
	
	@Column(name = "TREND_TRADABLE_STATE_ID", length=24)
	private String trendTradableStateId;
	
	//relations start
		//@OneToOne(fetch = FetchType.LAZY)
	   // @JoinColumn(name = "ORB_PREV_DAY_HIST_ID", referencedColumnName = "ORB_PREV_DAY_HIST_ID")
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "strategyOrbEntity")
	private  StrategyOrbPreviousDayHistEntity previousDayHistEntity; 
		
		/*@OneToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "ORB_MULTI_LVL_TRADE_ID", referencedColumnName = "ORB_MULTI_LVL_TRADE_ID")*/
		//@OneToOne(mappedBy = "strategyOrbEntity", cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
	   // @PrimaryKeyJoinColumn
		//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	    //@JoinColumn(name = "ORB_MULTI_LVL_TRADE_ID")
		/* @OneToOne(mappedBy = "strategyOrbEntity", fetch = FetchType.LAZY,
		            cascade = CascadeType.ALL)*/
		//@OneToOne(fetch = FetchType.LAZY)
		   // @JoinColumn(name = "ORB_MULTI_LVL_TRADE_ID", nullable = false)
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "strategyOrbEntity")
	private StrategyOrbMultilevelTradingEntity multilevelTradingEntity;

		//@OneToOne(fetch = FetchType.LAZY)
	   // @JoinColumn(name = "ORB_PREV_CNDL_DETAIL_ID", referencedColumnName = "ORB_PREV_CNDL_DETAIL_ID")
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "strategyOrbEntity")
	private  StrategyOrbPreviousCandleDeatilEntity previousCandleDeatilEntity;
		
		//@OneToOne(fetch = FetchType.LAZY)
	    //@JoinColumn(name = "ORB_PREV_CNDL_AVG_HIST_ID", referencedColumnName = "ORB_PREV_CNDL_AVG_HIST_ID")
		@OneToOne(cascade = CascadeType.ALL, mappedBy = "strategyOrbEntity")
		private  StrategyOrbPreviousCandleAverageHistEntity previousCandleAverageHistEntity;
		
		//@OneToOne(fetch = FetchType.LAZY)
	    //@JoinColumn(name = "ORB_VWAP_VOLUME_DETAIL_ID", referencedColumnName = "ORB_VWAP_VOLUME_DETAIL_ID")
		@OneToOne(cascade = CascadeType.ALL, mappedBy = "strategyOrbEntity")
		private  StrategyOrbVwapAndVolumeInfoEntity vwapAndVolumeInfoEntity;
		
		//@OneToOne(cascade = CascadeType.ALL)
	   //@JoinColumn(name = "ORB_PREV_CNDL_OHLC_ID", referencedColumnName = "ORB_PREV_CNDL_OHLC_ID")
		@OneToOne(cascade = CascadeType.ALL, mappedBy = "strategyOrbEntity")
		private  StrategyOrbPreviousCandleOHLCEntity previousCandleOHLCEntity;
		
		@OneToOne(cascade = CascadeType.ALL, mappedBy = "strategyOrbEntity")
		private  StrategyOrbTradingRuleEntity tradingRuleEntity;
		
		@OneToOne(cascade = CascadeType.ALL, mappedBy = "strategyOrbEntity")
		private  StrategyOrbTechnicalIndicatorEntity technicalIndicatorEntity;
		
		@OneToOne(cascade = CascadeType.ALL, mappedBy = "strategyOrbEntity")
		private  StrategyOrbStochasticEntity strategyOrbStochasticEntity;
		
		@OneToOne(cascade = CascadeType.ALL, mappedBy = "strategyOrbEntity")
		private  StrategyOrbHeikenAshiEntity strategyOrbHeikenAshiEntity;
		//relations end

	public void populateEntity(StrategyOrbBean bean) {
		this.symbolId = bean.getSymbolId();
		this.symbolName = bean.getSymbolName();
		this.lotSize = bean.getLotSize();
		this.buyAtVal = bean.getBuyAtVal();
		this.sellAtVal = bean.getSellAtVal();
		this.tradedAtVal = bean.getTradedAtVal();
		this.exitedAtVal = bean.getExitedAtVal();
		this.tradedAtDtTm = bean.getTradedAtDtTm();
		this.exitedAtDtTm = bean.getExitedAtDtTm();
		this.tradableStateId = new TradeStateLookupEntity(bean.getTradableStateId());
		this.tradedStateId = new TradeStateLookupEntity(bean.getTradedStateId());
		this.buyerSellerDiffVal = bean.getBuyerSellerDiffVal();
		this.vwapValue = bean.getVwapValue();
		this.tradeType = bean.getTradeType();
		this.tradedLotCount = bean.getTradedLotCount();
		this.buyerQuantityVal = bean.getBuyerQuantityVal();
		this.sellerQuantityVal = bean.getSellerQuantityVal();
		this.profitLossAmtVal = bean.getProfitLossAmtVal();
		this.positiveMoveVal = bean.getPositiveMoveVal();
		this.negativeMoveVal = bean.getNegativeMoveVal();
		this.positiveMoveLtp = bean.getPositiveMoveLtp();
		this.negativeMoveLtp = bean.getNegativeMoveLtp();
		this.volumes = bean.getVolumes();
		
		this.ohlcStateId = new TradeStateLookupEntity(bean.getOhlcStateId());
		this.strengthableTradeStateId = new TradeStateLookupEntity(bean.getStrengthableTradeStateId());
		this.strengthStyleClass = bean.getStrengthStyleClass();
		this.candleNumber = bean.getCandleNumber();
		this.dayLevelTradeInd = bean.getDayLevelTradeInd();
		this.gapUpDownMoveVal = bean.getGapUpDownMoveVal();
		this.tempCustomTradingRuleInd = bean.getTempCustomTradingRuleInd();
		this.tradedDateStamp = bean.getTradedDateStamp();
		//this.heikinAshiTrend = bean.getHeikinAshiTrend();
		//TODO
				this.heikinAshiTrendId = new VolumeTrendLookupEntity(
						null != bean.getHeikinAshiTrendId() ? bean.getHeikinAshiTrendId() : Constants.NA);
		
		this.buySellQuantityRatio = bean.getBuySellQuantityRatio();
		this.percentageChange = bean.getPercentageChange();
		this.strategyRule = bean.getStrategyRule();
		this.candleTimeFrame = bean.getCandleTimeFrame();
		
		
		this.currentCandleOpen = bean.getCurrentCandleOpen();
		this.buyerSellerDiffVal2 = bean.getBuyerSellerDiffVal2();
		this.buyerSellerDiffVal3 = bean.getBuyerSellerDiffVal3();
		
		this.vwapTradeStateId = new VolumeTrendLookupEntity(bean.getVwapTradeStateId());
		/*
		this.volStrengthStyleClass = bean.getVolStrengthStyleClass();
		this.vwapTradeVolInfo = bean.getVwapTradeVolInfo();
		this.vwapTradeVolRatioInfo = bean.getVwapTradeVolRatioInfo();*/
		this.strengthableTradeInfo = bean.getStrengthableTradeInfo();
		
		
		this.tradeCountPerDay = bean.getTradeCountPerDay();
		this.profitLossAmtValFinal = bean.getProfitLossAmtValFinal();
		
		this.oiInfo = bean.getOiInfo();
		this.optionChainTrend = bean.getOptionChainTrend();
		this.optionChainPriceTrend = bean.getOptionChainPriceTrend();
		this.optionChainId = bean.getOptionChainId();
		this.targetPrice = bean.getTargetPrice();
		this.stopLoss = bean.getStopLoss();
		this.reverseMotherCandleInd = bean.getReverseMotherCandleInd();
		
		this.manualTradeExitInd = bean.getManualTradeExitInd();
		this.manualExitedAtVal = bean.getManualExitedAtVal();
		this.manualExitedAtDtTm = bean.getManualExitedAtDtTm(); 
		this.manualBookProfitLossAmtVal = bean.getManualBookProfitLossAmtVal();
		//TODO
		this.manualTradeExitStateId = new TradeStateLookupEntity(
				null != bean.getManualTradeExitStateId() ? bean.getManualTradeExitStateId() : Constants.NA);
		this.waitForEngulfingCount = bean.getWaitForEngulfingCount();
		this.trendTradableStateId = bean.getTrendTradableStateId();

		if (Constants.BUY.equals(bean.getTradableStateId()) || Constants.SELL.equals(bean.getTradableStateId())) {
			previousDayHistEntity = new StrategyOrbPreviousDayHistEntity();
			previousDayHistEntity.populateEntity(bean);
			previousDayHistEntity.setStrategyOrbEntity(this);

			multilevelTradingEntity = new StrategyOrbMultilevelTradingEntity();
			multilevelTradingEntity.populateEntity(bean);
			multilevelTradingEntity.setStrategyOrbEntity(this);

			previousCandleDeatilEntity = new StrategyOrbPreviousCandleDeatilEntity();
			previousCandleDeatilEntity.populateEntity(bean);
			previousCandleDeatilEntity.setStrategyOrbEntity(this);

			previousCandleAverageHistEntity = new StrategyOrbPreviousCandleAverageHistEntity();
			previousCandleAverageHistEntity.populateEntity(bean);
			previousCandleAverageHistEntity.setStrategyOrbEntity(this);

			vwapAndVolumeInfoEntity = new StrategyOrbVwapAndVolumeInfoEntity();
			vwapAndVolumeInfoEntity.populateEntity(bean);
			vwapAndVolumeInfoEntity.setStrategyOrbEntity(this);

			previousCandleOHLCEntity = new StrategyOrbPreviousCandleOHLCEntity();
			previousCandleOHLCEntity.populateEntity(bean);
			previousCandleOHLCEntity.setStrategyOrbEntity(this);

			tradingRuleEntity = new StrategyOrbTradingRuleEntity();
			tradingRuleEntity.populateEntity(bean);
			tradingRuleEntity.setStrategyOrbEntity(this);
			
			technicalIndicatorEntity = new StrategyOrbTechnicalIndicatorEntity();
			technicalIndicatorEntity.populateEntity(bean);
			technicalIndicatorEntity.setStrategyOrbEntity(this);
			
			strategyOrbStochasticEntity = new StrategyOrbStochasticEntity();
			strategyOrbStochasticEntity.populateEntity(bean);
			strategyOrbStochasticEntity.setStrategyOrbEntity(this);
			
			strategyOrbHeikenAshiEntity = new StrategyOrbHeikenAshiEntity();
			strategyOrbHeikenAshiEntity.populateEntity(bean);
			strategyOrbHeikenAshiEntity.setStrategyOrbEntity(this);
			
		}
	}
	
	public void populateBean(StrategyOrbBean bean) {
		bean.setItemId(this.itemId);
		bean.setSymbolId(this.symbolId);
		bean.setSymbolName(this.symbolName);
		bean.setLotSize(this.lotSize);
		bean.setBuyAtVal(this.buyAtVal);
		bean.setSellAtVal(this.sellAtVal);
		bean.setTradedAtVal(this.tradedAtVal);
		bean.setExitedAtVal(this.exitedAtVal);
		bean.setTradedAtDtTm(this.tradedAtDtTm);
		bean.setExitedAtDtTm(this.exitedAtDtTm);
		bean.setTradableStateId(this.tradableStateId.getTradeStateId());
		bean.setTradableStateDescription(this.tradableStateId.getTradeStateDescription());
		bean.setTradedStateId(this.tradedStateId.getTradeStateId());
		bean.setTradedStateDescription(this.tradedStateId.getTradeStateDescription());
		bean.setBuyerSellerDiffVal(this.buyerSellerDiffVal);
		bean.setVwapValue(this.vwapValue);
		bean.setTradeType(this.tradeType);
		bean.setTradedLotCount(this.tradedLotCount);
		bean.setBuyerQuantityVal(this.buyerQuantityVal);
		bean.setSellerQuantityVal(this.sellerQuantityVal);
		bean.setProfitLossAmtVal(this.profitLossAmtVal);
		bean.setPositiveMoveVal(this.positiveMoveVal);
		bean.setNegativeMoveVal(this.negativeMoveVal);
		bean.setPositiveMoveLtp(this.positiveMoveLtp);
		bean.setNegativeMoveLtp(this.negativeMoveLtp);
		bean.setVolumes(this.volumes);
		
		bean.setOhlcStateId(this.ohlcStateId.getTradeStateId());
		bean.setOhlcStateDescription(this.ohlcStateId.getTradeStateDescription());
		bean.setStrengthableTradeStateId(this.strengthableTradeStateId.getTradeStateId());
		bean.setStrengthableTradeStateDescription(this.strengthableTradeStateId.getTradeStateDescription());
		bean.setStrengthStyleClass(this.strengthStyleClass);
		bean.setCandleNumber(this.candleNumber);
		bean.setDayLevelTradeInd(this.dayLevelTradeInd);
		bean.setGapUpDownMoveVal(this.gapUpDownMoveVal);
		bean.setTempCustomTradingRuleInd(this.tempCustomTradingRuleInd);
		bean.setOiInfo(this.oiInfo);
		bean.setTradedDateStamp(this.tradedDateStamp);
		//bean.setHeikinAshiTrend(this.heikinAshiTrend);
		//TODO:
				if (null != this.heikinAshiTrendId) {
				bean.setHeikinAshiTrendId(this.heikinAshiTrendId.getVolumeTrendId());
				bean.setHeikinAshiTrendDescription(this.heikinAshiTrendId.getVolumeTrendDescription());
				}
		bean.setWaitForEngulfingCount(this.waitForEngulfingCount);
				
		bean.setOptionChainTrend(this.optionChainTrend);
		bean.setOptionChainPriceTrend(this.optionChainPriceTrend);
		bean.setOptionChainId(this.optionChainId);
		bean.setBuySellQuantityRatio(this.buySellQuantityRatio);
		bean.setPercentageChange(this.percentageChange);
		bean.setStrategyRule(this.strategyRule);
		bean.setCandleTimeFrame(this.candleTimeFrame);
		
		bean.setCurrentCandleOpen(this.currentCandleOpen);
		
		bean.setBuyerSellerDiffVal2(this.buyerSellerDiffVal2);
		bean.setBuyerSellerDiffVal3(this.buyerSellerDiffVal3);
		bean.setTradeCountPerDay(this.tradeCountPerDay);
		bean.setProfitLossAmtValFinal(this.profitLossAmtValFinal);
		
		//bean.setVolStrengthStyleClass(this.volStrengthStyleClass);
		bean.setStrengthableTradeInfo(this.strengthableTradeInfo);
		bean.setVwapTradeStateId(this.vwapTradeStateId.getVolumeTrendId());
		bean.setVwapTradeStateDescription(this.vwapTradeStateId.getVolumeTrendDescription());
		//bean.setVwapTradeVolInfo(this.vwapTradeVolInfo);
		//bean.setVwapTradeVolRatioInfo(this.vwapTradeVolRatioInfo);
		//bean.setLastCndleOhlcVal(this.lastCndleOhlcVal);
		bean.setVolumeTradeStateId(this.vwapTradeStateId.getVolumeTrendId());
		bean.setVolumeTradeStateDescription(this.vwapTradeStateId.getVolumeTrendDescription());
		bean.setTargetPrice(this.targetPrice);
		bean.setStopLoss(this.stopLoss);
		this.reverseMotherCandleInd = bean.getReverseMotherCandleInd();

		bean.setManualTradeExitInd(this.manualTradeExitInd);
		bean.setManualExitedAtVal(this.manualExitedAtVal);
		bean.setManualExitedAtDtTm(this.manualExitedAtDtTm);
		bean.setManualBookProfitLossAmtVal(this.manualBookProfitLossAmtVal);
		bean.setTrendTradableStateId(this.trendTradableStateId);
		//TODO:
		if (null != this.manualTradeExitStateId) {
		bean.setManualTradeExitStateId(this.manualTradeExitStateId.getTradeStateId());
		bean.setManualTradeExitStateDescription(this.manualTradeExitStateId.getTradeStateDescription());
		}
		
		if (null != previousDayHistEntity) {
			previousDayHistEntity.populateBean(bean);
		}
		if (null != multilevelTradingEntity) {
			multilevelTradingEntity.populateBean(bean);
		}
		if (null != previousCandleDeatilEntity) {
			previousCandleDeatilEntity.populateBean(bean);
		}
		if (null != previousCandleAverageHistEntity) {
			previousCandleAverageHistEntity.populateBean(bean);
		}
		if (null != vwapAndVolumeInfoEntity) {
			vwapAndVolumeInfoEntity.populateBean(bean);
		}
		if (null != previousCandleOHLCEntity) {
			previousCandleOHLCEntity.populateBean(bean);
		}
		if (null != multilevelTradingEntity) {
			multilevelTradingEntity.populateBean(bean);
		}
		if (null != tradingRuleEntity) {
			tradingRuleEntity.populateBean(bean);
		}
		if (null != technicalIndicatorEntity) {
			technicalIndicatorEntity.populateBean(bean);
		}
		if (null != strategyOrbStochasticEntity) {
			strategyOrbStochasticEntity.populateBean(bean);
		}
		if (null != strategyOrbHeikenAshiEntity) {
			strategyOrbHeikenAshiEntity.populateBean(bean);
		}
	}
}
