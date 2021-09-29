package com.tradeware.clouddatabase.service;

import java.util.Date;
import java.util.List;

import com.tradeware.clouddatabase.bean.StrategyOrbBean;

public interface StrategyOrbService {

	List<StrategyOrbBean> findAll();

	List<StrategyOrbBean> findAllByTradedDateStampOrderByTradedAtDtTmDesc(Date tradedDateStamp);

	List<StrategyOrbBean> findAllByTradedDateStampOrderBySymbolNameAscTradedAtDtTmAsc(Date tradedDateStamp);

	List<StrategyOrbBean> findAllByTempCustomTradingRuleIndOrderByTradedAtDtTm(Boolean tempCustomTradingRuleInd);

	List<StrategyOrbBean> findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDtTm(
			Boolean tempCustomTradingRuleInd, Date tradedDateStamp);

	List<StrategyOrbBean> getRunningTrades(Date tradedDateStamp);

	StrategyOrbBean save(StrategyOrbBean bean);
	
	void updateTrade(String tradedState, Double exitedAtVal, Date exitedAtDtTm, Double profitLossAmtVal,
			Double positiveMoveVal, Double negativeMoveVal, Double positiveMoveLtp, Double negativeMoveLtp,
			Double stopLossValue, Integer itemId);

	void updatePositiveNegativeMoves(Double profitLossAmtVal, Double positiveMoveVal, Double negativeMoveVal,
			Double positiveMoveLtp, Double negativeMoveLtp, Double stopLossValue, Integer itemId);

	void updateAdujustTrade(Integer tradedLotCount, Double tradedAtAvgVal, Double tradedAtVal2, Date tradedAtDtTm2,
			Double tradedAtVal3, Date tradedAtDtTm3, Double targetAmtVal2, Double targetAmtVal3, Integer itemId);

	// 04-21-2021 start - afterSomeSuccess [04-27-2021]
	void manualTradeClose(String manualTradeExitStateId, Double manualExitedAtVal, Date manualExitedAtDtTm,
			Double manualBookProfitLossAmtVal, Integer itemId);
	// 04-21-2021 end
	
	List<StrategyOrbBean> findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtTmAsc(
			Date tradedDateStamp);

	List<StrategyOrbBean> findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtTmAsc(
			Date tradedDateStamp);

	List<StrategyOrbBean> findAllByTradedDateStampFilterItemsOrderByTradedAtDtTmDesc(Date tradedDateStamp, Integer candleNumber);
	
	
	List<StrategyOrbBean> getRunningTradesDayLevelTrade(Date tradedDateStamp);
	
	List<StrategyOrbBean> findAllByTradedDateStampAndDayLevelTradeIndOrderBySymbolNameAscTradedAtDtTmDesc(Date tradedDateStamp, Boolean dayLevelTradeInd);
	
	List<StrategyOrbBean> findAllByTradedDateFilterAndDayLevelTradeindItemsOrderByTradedAtDtTmDesc( Date tradedDate, Integer candleNumber);
	
	List<StrategyOrbBean> findAllByTradedDateAndOhlcStateNotNullAndDayLevelIndOrderBySymbolAscTradedAtDtTmAsc(
			Date tradedDateStamp);

	List<StrategyOrbBean> findAllByTradedDateAndOhlcStateNotNullAndOhlcStateEqualToTradableStateAndDayLevelIndOrderBySymbolAscTradedAtDtTmAsc(
			Date tradedDateStamp);
	
	// custome reports
	//List<StrategyOrbBean> findAllByCustomRule1(Date tradedDateStamp);
	List<StrategyOrbBean> findAllByTradingRule7TimesStrenth(Date tradedDateStamp);

	//List<StrategyOrbBean> findAllByCustomRule2(Date tradedDateStamp, Integer candleNumber);
	List<StrategyOrbBean> findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwap(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllByCustomRule2IncludeReverse(Date tradedDateStamp, Integer candleNumber);

	List<StrategyOrbBean> findAllByCustomReverseTradeRule(Date tradedDateStamp, Integer candleNumber);
	List<StrategyOrbBean> findAllByCustomReverseSellOnlyTradeRule(Date tradedDateStamp, Integer candleNumber);
	List<StrategyOrbBean> findAllByCustomReverseBuyOnlyTradeRule(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllByCustomReverseSellGt1700TradeRule(Date tradedDateStamp, Integer candleNumber);

	List<StrategyOrbBean> findAllByCustomReverseSellWithStrengthTradeRule(Date tradedDateStamp, Integer candleNumber);
	
	void updateTradeAVGHLC(Double avgHigh, Double avgLow, Double avgClose, Double currentCandleOpen,
			Double avgHighMinusClose, Double avgCloseMinusLow, Double vwapVal, Integer itemId);
	
	//Phase 2 March-13-2021 start
	//List<StrategyOrbBean> findAllByCustomRule3AllVwapStrong(Date tradedDateStamp, Integer candleNumber);
	List<StrategyOrbBean> findAllByStrongVwapAndVolumeRule(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllNIFTYorBANKNIFTY(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllByStrongVolumePressureRule(Date tradedDateStamp, Integer candleNumber);
	List<StrategyOrbBean> findAllByStrongVolumePressureRuleMatch(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllBySmaVwapLevel2Rule(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllBySmaVwapLevel2PlusRule(Date tradedDateStamp, Integer candleNumber);
	//Phase 2 March-13-2021 END

	// phase 4 start 04-23-2021 start
	List<StrategyOrbBean> findAllByInitProfitableProdRule(Date tradedDateStamp, Integer candleNumber);

	List<StrategyOrbBean> findAllByInitProfitableFilterProdRule(Date tradedDateStamp, Date tradedCloseTimeBeffore,
			Integer candleNumber);
	
	Double checkTodayTradingNotForceClosedByCrossMaxLossLimit();
	// phase 4 end 04-23-2021
	
	// Phase 4 :: 05-09-2021 start - afterSomeSuccess
	List<StrategyOrbBean> findAllBySmaVwapRuleTrades(Date tradedDateStamp, Integer candleNumber);
	List<StrategyOrbBean> findAllByForwardEngulfingRuleTrades(Date tradedDateStamp, Integer candleNumber);
	// Phase 4 :: 05-09-2021 end - afterSomeSuccess
	
	List<StrategyOrbBean> findAllByVolumeStrengthTrades(Date tradedDateStamp, Integer candleNumber);
	List<StrategyOrbBean> findAllByStochasticVolumeStrengthTrades(Date tradedDateStamp, Integer candleNumber);
	List<StrategyOrbBean> findAllByStrongStochasticVolumeStrengthTrades(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllByStochasticRule1Trades(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllByStochasticRule2Trades(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllByStochasticStrongRule3Trades(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllByStochasticBasicRuleTrades(Date tradedDateStamp, Integer candleNumber);
	
	List<StrategyOrbBean> findAllByProfiitTrades(Date tradedDateStamp, Integer candleNumber);
}
