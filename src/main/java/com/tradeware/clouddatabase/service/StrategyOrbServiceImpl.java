package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.StrategyOrbBean;
import com.tradeware.clouddatabase.entity.StrategyOrbEntity;
import com.tradeware.clouddatabase.repository.StrategyOrbRepository;

@Service
public class StrategyOrbServiceImpl implements StrategyOrbService {

	@Autowired
	private StrategyOrbRepository repository;
	
	private List<StrategyOrbBean> convertEntityListToBeanList(List<StrategyOrbEntity> entityList) {
		List<StrategyOrbBean> beanList = new ArrayList<StrategyOrbBean>(entityList.size());
		for (StrategyOrbEntity entity : entityList) {
			StrategyOrbBean bean = new StrategyOrbBean();
			entity.populateBean(bean);
			beanList.add(bean);
		}
		return beanList;
	}

	@Override
	public List<StrategyOrbBean> findAll() {
		return convertEntityListToBeanList(repository.findAll());
	}

	@Override
	public StrategyOrbBean save(StrategyOrbBean bean) {
		StrategyOrbEntity entity = new StrategyOrbEntity();
		entity.populateEntity(bean);
		entity = repository.save(entity);
		//entity = repository.saveAndFlush(entity);
		entity.populateBean(bean);
		return bean;
	}

	@Override
	public List<StrategyOrbBean> getRunningTrades(Date tradedDateStamp) {
		return convertEntityListToBeanList(repository.getRunningTrades(tradedDateStamp));
	}

	@Override
	public List<StrategyOrbBean> findAllByTradedDateStampOrderByTradedAtDtTmDesc(Date tradedDateStamp) {
		return convertEntityListToBeanList(repository.findAllByTradedDateStampOrderByTradedAtDtTmDesc(tradedDateStamp));
	}

	@Override
	public List<StrategyOrbBean> findAllByTradedDateStampOrderBySymbolNameAscTradedAtDtTmAsc(Date tradedDateStamp) {
		return convertEntityListToBeanList(
				repository.findAllByTradedDateStampOrderBySymbolNameAscTradedAtDtTmAsc(tradedDateStamp));
	}

	@Override
	public void updateTrade(String tradedState, Double exitedAt, Date exitedAtDtTm, Double profitLossAmt,
			Double positiveMoveValue, Double negativeMoveValue, Double positiveMoveLtp, Double negativeMoveLtp,
			Double stopLossValue, Integer itemId) {
		repository.updateTrade(tradedState, exitedAt, exitedAtDtTm, profitLossAmt, positiveMoveValue, negativeMoveValue,
				positiveMoveLtp, negativeMoveLtp, stopLossValue, itemId);
	}

	@Override
	public void updatePositiveNegativeMoves(Double profitLossAmtVal, Double positiveMoveVal, Double negativeMoveVal,
			Double positiveMoveLtp, Double negativeMoveLtp, Double stopLossValue, Integer itemId) {
		repository.updatePositiveNegativeMoves(profitLossAmtVal, positiveMoveVal, negativeMoveVal, positiveMoveLtp,
				negativeMoveLtp, stopLossValue, itemId);
	}

	@Override
	public void updateAdujustTrade(Integer tradedLotCount, Double tradedAtAvgVal, Double tradedAtVal2,
			Date tradedAtDtTm2, Double tradedAtVal3, Date tradedAtDtTm3, Double targetAmtVal2, Double targetAmtVal3,
			Integer itemId) {
		repository.updateAdujustTrade(tradedLotCount, tradedAtAvgVal, tradedAtVal2, tradedAtDtTm2, tradedAtVal3,
				tradedAtDtTm3, targetAmtVal2, targetAmtVal3, itemId);
	}
	
	// 04-21-2021 start - afterSomeSuccess [04-27-2021]
	@Override
	public void manualTradeClose(String manualTradeExitStateId, Double manualExitedAtVal, Date manualExitedAtDtTm,
			Double manualBookProfitLossAmtVal, Integer itemId) {
		repository.manualTradeClose(manualTradeExitStateId, manualExitedAtVal, manualExitedAtDtTm,
				manualBookProfitLossAmtVal, itemId);
	}
	// 04-21-2021 end

	@Override
	public List<StrategyOrbBean> findAllByTempCustomTradingRuleIndOrderByTradedAtDtTm(
			Boolean tempCustomTradingRuleInd) {
		return convertEntityListToBeanList(
				repository.findAllByTempCustomTradingRuleIndOrderByTradedAtDtTm(tempCustomTradingRuleInd));
	}

	@Override
	public List<StrategyOrbBean> findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDtTm(
			Boolean tempCustomTradingRuleInd, Date tradedDateStamp) {
		return convertEntityListToBeanList(
				repository.findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDtTm(
						tempCustomTradingRuleInd, tradedDateStamp));
	}

	@Override
	public List<StrategyOrbBean> findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtTmAsc(
			Date tradedDateStamp) {
		return convertEntityListToBeanList(
				repository.findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtTmAsc(tradedDateStamp));
	}

	@Override
	public List<StrategyOrbBean> findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtTmAsc(
			Date tradedDateStamp) {
		return convertEntityListToBeanList(repository
				.findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtTmAsc(
						tradedDateStamp));
	}

	@Override
	public List<StrategyOrbBean> findAllByTradedDateStampFilterItemsOrderByTradedAtDtTmDesc(Date tradedDateStamp, Integer candleNumber ) {
		return convertEntityListToBeanList(
				repository.findAllByTradedDateStampFilterItemsOrderByTradedAtDtTmDesc(tradedDateStamp, candleNumber));
	}
	
	
	@Override
	public List<StrategyOrbBean> getRunningTradesDayLevelTrade(Date tradedDateStamp) {
		return convertEntityListToBeanList(repository.getRunningTradesDayLevelTrade(tradedDateStamp));
	}
	
	@Override
	public List<StrategyOrbBean> findAllByTradedDateStampAndDayLevelTradeIndOrderBySymbolNameAscTradedAtDtTmDesc(Date tradedDateStamp,
			Boolean dayLevelTradeInd) {
		return convertEntityListToBeanList(repository
				.findAllByTradedDateStampAndDayLevelTradeIndOrderBySymbolNameAscTradedAtDtTmDesc(tradedDateStamp, dayLevelTradeInd));
	}
	
	@Override
	public List<StrategyOrbBean> findAllByTradedDateFilterAndDayLevelTradeindItemsOrderByTradedAtDtTmDesc(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByTradedDateFilterAndDayLevelTradeindItemsOrderBytradedAtDtTmDesc(tradedDateStamp, candleNumber));
	}
	
	@Override
	public List<StrategyOrbBean> findAllByTradedDateAndOhlcStateNotNullAndDayLevelIndOrderBySymbolAscTradedAtDtTmAsc(
			Date tradedDateStamp) {
		return convertEntityListToBeanList(
				repository.findAllByTradedDateAndOhlcStateNotNullAndDayLevelIndOrderBySymbolAsctradedAtDtTmAsc(tradedDateStamp));
	}

	@Override
	public List<StrategyOrbBean> findAllByTradedDateAndOhlcStateNotNullAndOhlcStateEqualToTradableStateAndDayLevelIndOrderBySymbolAscTradedAtDtTmAsc(
			Date tradedDateStamp) {
		return convertEntityListToBeanList(
				repository.findAllByTradedDateAndOhlcStateNotNullAndOhlcStateEqualToTradableStateAndDayLevelIndOrderBySymbolAsctradedAtDtTmAsc(tradedDateStamp));
	}
	
	@Override
	//public List<StrategyOrbBean> findAllByCustomRule1(Date tradedDateStamp) {
	public List<StrategyOrbBean> findAllByTradingRule7TimesStrenth(Date tradedDateStamp) {
		return convertEntityListToBeanList(repository.findAllByTradingRule7TimesStrenth(tradedDateStamp));
	}
	
	@Override
	//public List<StrategyOrbBean> findAllByCustomRule2(Date tradedDateStamp, Integer candleNumber) {
	public List<StrategyOrbBean> findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwap(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwap(tradedDateStamp, candleNumber));
	}
	
	@Override
	public List<StrategyOrbBean> findAllByCustomRule2IncludeReverse(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByCustomRule2IncludeReverse(tradedDateStamp, candleNumber));
	}
	
	@Override
	public List<StrategyOrbBean> findAllByCustomReverseTradeRule(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByCustomReverseTradeRule(tradedDateStamp, candleNumber));
	}
	
	@Override
	public List<StrategyOrbBean> findAllByCustomReverseSellOnlyTradeRule(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByCustomReverseSellOnlyTradeRule(tradedDateStamp, candleNumber));
	}
	@Override
	public List<StrategyOrbBean> findAllByCustomReverseBuyOnlyTradeRule(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByCustomReverseBuyOnlyTradeRule(tradedDateStamp, candleNumber));
	}
	
	@Override
	public List<StrategyOrbBean> findAllByCustomReverseSellGt1700TradeRule(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByCustomReverseSellGt1700TradeRule(tradedDateStamp, candleNumber));
	}

	@Override
	public List<StrategyOrbBean> findAllByCustomReverseSellWithStrengthTradeRule(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByCustomReverseSellWithStrengthTradeRule(tradedDateStamp, candleNumber));
	}
	
	@Override
	public void updateTradeAVGHLC(Double avgHigh, Double avgLow, Double avgClose, Double currentCandleOpen,
			Double avgHighMinusClose, Double avgCloseMinusLow, Double vwapVal, Integer itemId) {
		repository.updateTradeAVGHLC(avgHigh, avgLow, avgClose, currentCandleOpen, avgHighMinusClose, avgCloseMinusLow,
				vwapVal, itemId);
	}
	
	
	//Phase 2 March-13-2021 start
	@Override
	//public List<StrategyOrbBean> findAllByCustomRule3AllVwapStrong(Date tradedDateStamp, Integer candleNumber) {
	public List<StrategyOrbBean> findAllByStrongVwapAndVolumeRule(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByStrongVwapAndVolumeRule(tradedDateStamp, candleNumber));
	}
	
	@Override
	public List<StrategyOrbBean> findAllNIFTYorBANKNIFTY(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllNIFTYorBANKNIFTY(tradedDateStamp, candleNumber));
	}
	
	
	@Override
	public List<StrategyOrbBean> findAllByStrongVolumePressureRule(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByStrongVolumePressureRule(tradedDateStamp, candleNumber));
	}
	@Override
	public List<StrategyOrbBean> findAllByStrongVolumePressureRuleMatch(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByStrongVolumePressureRuleMatch(tradedDateStamp, candleNumber));
	}
	
	
	@Override
	public List<StrategyOrbBean> findAllBySmaVwapLevel2Rule(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllBySmaVwapLevel2Rule(tradedDateStamp, candleNumber));
	}
	
	@Override
	public List<StrategyOrbBean> findAllBySmaVwapLevel2PlusRule(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllBySmaVwapLevel2PlusRule(tradedDateStamp, candleNumber));
	}
	
	//Phase 2 March-13-2021 start
	
	// phase 4 start 04-23-2021 start
	@Override
	public List<StrategyOrbBean> findAllByInitProfitableProdRule(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByInitProfitableProdRule(tradedDateStamp, candleNumber));
	}
	
	@Override
	public List<StrategyOrbBean> findAllByInitProfitableFilterProdRule(Date tradedDateStamp,
			Date tradedCloseTimeBeffore, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByInitProfitableFilterProdRule(tradedDateStamp,
				tradedCloseTimeBeffore, candleNumber));
	}
	
	@Override
	public Double checkTodayTradingNotForceClosedByCrossMaxLossLimit() {
		return repository.checkTodayTradingNotForceClosedByCrossMaxLossLimit();
	}
	// phase 4 end 04-23-2021
	
	// Phase 4 :: 05-09-2021 start - afterSomeSuccess
	@Override
	public List<StrategyOrbBean> findAllBySmaVwapRuleTrades(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllBySmaVwapRuleTrades(tradedDateStamp, candleNumber));
	}
	public List<StrategyOrbBean> findAllByForwardEngulfingRuleTrades(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByForwardEngulfingRuleTrades(tradedDateStamp, candleNumber));
	}
	// Phase 4 :: 05-09-2021 end - afterSomeSuccess
	
	public List<StrategyOrbBean> findAllByVolumeStrengthTrades(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByVolumeStrengthTrades(tradedDateStamp, candleNumber));
	}
	
	public List<StrategyOrbBean> findAllByStochasticVolumeStrengthTrades(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByStochasticVolumeStrengthTrades(tradedDateStamp, candleNumber));
	}
	
	public List<StrategyOrbBean> findAllByStrongStochasticVolumeStrengthTrades(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByStrongStochasticVolumeStrengthTrades(tradedDateStamp, candleNumber));
	}
	
	public List<StrategyOrbBean> findAllByStochasticRule1Trades(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByStochasticRule1Trades(tradedDateStamp, candleNumber));
	}
	
	public List<StrategyOrbBean> findAllByStochasticRule2Trades(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByStochasticRule2Trades(tradedDateStamp, candleNumber));
	}
	
	public List<StrategyOrbBean> findAllByStochasticStrongRule3Trades(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByStochasticStrongRule3Trades(tradedDateStamp, candleNumber));
	}
	
	public List<StrategyOrbBean> findAllByStochasticBasicRuleTrades(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByStochasticBasicRuleTrades(tradedDateStamp, candleNumber));
	}
	
	public List<StrategyOrbBean> findAllByProfiitTrades(Date tradedDateStamp, Integer candleNumber) {
		return convertEntityListToBeanList(repository.findAllByProfiitTrades(tradedDateStamp, candleNumber));
	}
}
