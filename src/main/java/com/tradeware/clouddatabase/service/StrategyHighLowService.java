package com.tradeware.clouddatabase.service;

import java.util.Date;
import java.util.List;

import com.tradeware.clouddatabase.bean.StrategyHighLowBean;
import com.tradeware.clouddatabase.entity.StrategyHighLowEntity;

public interface StrategyHighLowService {
	
	StrategyHighLowEntity save(StrategyHighLowEntity symbol);
	
	List<StrategyHighLowEntity> saveAll(List<StrategyHighLowEntity> symbols);

	// TODO : Refactor
	List<StrategyHighLowEntity> findAll();//Not in use
	List<StrategyHighLowBean> findAllByTradedDateAfter(Date tradedDate);//In use
	List<StrategyHighLowBean> findAllByTradedDateOrderBySymbolTradedAtDt(Date tradedDate); //In use
	List<StrategyHighLowBean> findAllTrades(); //in use
	List<StrategyHighLowBean> findAllTradesByTradedDate(Date tradedDate);
	List<StrategyHighLowBean> findAllTradesByTradedDateAndTrendMactchWithNifty50(Date tradedDate);
	List<StrategyHighLowBean> findAllTradesByTradedDateAndTrendMactchWithNifty50AndCloseRuleOrderBySymbolAscTradedAtDtAsc(Date tradedDate);
	List<StrategyHighLowBean> getRunningTrades();
	List<StrategyHighLowBean> findAllByTradedStateOrderByTradedAtDt(String tradedState);//may be not require
	List<StrategyHighLowBean> findAllByTradedStateOrderBySymbolTradedAtDt(String tradedState); //In use
	List<StrategyHighLowBean> findAllByTradedStateStartingWithAndTradedDateAfterOrderByTradedAtDt(String tradedState);
	List<StrategyHighLowBean> findAllByTradedStateStartingWithAndTradedDateOrderBySymbolTradedAtDt(String tradedState);
	//List<StrategyHighLowBean> findAllByTradedStateLikeOrderByTradedAtDt(String tradedState);
	List<StrategyHighLowBean> findAllByTradedStateContainsAndTradedDateAfterOrderByTradedAtDt(String tradedState);
	List<StrategyHighLowBean> findAllByTradedStateContainsAndTradedDateOrderBySymbolTradedAtDt(String tradedState);

	//List<StrategyHighLowBean> findAllByTradeStrategyRule(String tradeStrategy);

	//StrategyHighLowEntity saveTrade(StrategyHighLowBean symbol);
	StrategyHighLowBean saveTrade(StrategyHighLowBean symbol);
	
	List<StrategyHighLowEntity> saveAllTrades(List<StrategyHighLowBean> symbols);
	
	void updateTrade(String tradedState, Double exitedAt, Date exitedAtDt, Double profitLossAmt,
			Double positiveMoveValue, Double negativeMoveValue, Integer itemId);
	
	
	//reports
	List<StrategyHighLowBean> findAllByTradedStateAndCloseRulesAndTradedDateAfterOrderByTradedAtDt(String tradedState);
	
	List<StrategyHighLowBean> findAllByCustomRuleSell();
	List<StrategyHighLowBean> findAllByCustomRuleBuy();
	
	//Reports 
	List<StrategyHighLowBean> findAllByNr4RuleAndTradedDateOrderBySymbolAscTradedAtDtAsc(boolean nr4RuleInd, Date tradedDate);
	List<StrategyHighLowBean> findAllByNr4RuleAndTradedDateGreaterThanEqualOrderBySymbolAscTradedAtDtAsc(boolean nr4RuleInd, Date tradedDate);
	List<StrategyHighLowBean> findAllByTradedStateAndCPROrderBySymbolAscTradedAtDtAsc(String tradedState, Date tradedDate);
	List<StrategyHighLowBean> findAllByTradedStateAndNotCPROrderBySymbolAscTradedAtDtAsc(String tradedState, Date tradedDate);
	
	List<StrategyHighLowBean> findAllByTradedStateAndCPROAndCloserderBySymbolAscTradedAtDtAsc(String tradedState, Date tradedDate);
	List<StrategyHighLowBean> findAllByTradedStateAndNotCPRAndCloseOrderBySymbolAscTradedAtDtAsc(String tradedState, Date tradedDate);
}
