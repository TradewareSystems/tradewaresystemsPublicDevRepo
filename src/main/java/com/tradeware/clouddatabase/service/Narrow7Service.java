package com.tradeware.clouddatabase.service;

import java.util.Date;
import java.util.List;

import com.tradeware.clouddatabase.bean.Narrow7Bean;

public interface Narrow7Service {
	List<Narrow7Bean> findAll();

	List<Narrow7Bean> findAllByTradedDateStampOrderByTradedAtDtDesc(Date tradedDateStamp);

	List<Narrow7Bean> findAllByTradedDateStampOrderBySymbolAscTradedAtDtAsc(Date tradedDateStamp);

	List<Narrow7Bean> getRunningTrades(Date tradedDateStamp);

	void updateTrade(String tradedState, Double exitedAt, Date exitedAtDt, Double profitLossAmt,
			Double positiveMoveValue, Double negativeMoveValue, Integer itemId);

	Narrow7Bean save(Narrow7Bean bean);

	List<Narrow7Bean> findAllByTempCustomTradingRuleIndOrderByTradedAtDt(Boolean tempCustomTradingRuleInd);

	List<Narrow7Bean> findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDt(
			Boolean tempCustomTradingRuleInd, Date tradedDateStamp);

	// Additional Reports
	List<Narrow7Bean> findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtAsc(Date tradedDateStamp);

	List<Narrow7Bean> findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtAsc(
			Date tradedDateStamp);
	
	//version 2
	List<Narrow7Bean> findAllByTradedDateStampFilterItemsOrderByTradedAtDtDesc( Date tradedDateStamp);
	
}
