package com.tradeware.clouddatabase.service;

import java.util.Date;
import java.util.List;

import com.tradeware.clouddatabase.bean.StrategyOrbMonthlyReportBean;

public interface StrategyOrbMonthlyReportService {

	List<StrategyOrbMonthlyReportBean> findAllMonthlyReportByForwardEngulfingRuleTrades(Date startDate,
			 Date endDate,  Integer candleNumber);
	
	List<StrategyOrbMonthlyReportBean> findAllMonthlyReportBySmaVwapRuleTrades(Date startDate,
			 Date endDate,  Integer candleNumber);
	
	List<StrategyOrbMonthlyReportBean> findAllMonthlyReportByForwardEngulfingLvl2RuleTrades(Date startDate,
			 Date endDate,  Integer candleNumber);
	
	List<StrategyOrbMonthlyReportBean> findAllMonthlyReportByForwardEngulfingLvl3RuleTrades(Date startDate,
			 Date endDate,  Integer candleNumber);
	
	List<StrategyOrbMonthlyReportBean> findAllMonthlyReportBySmaVwapRuleUnionTrades(Date startDate,
			 Date endDate,  Integer candleNumber);
	
	List<StrategyOrbMonthlyReportBean> findAllMonthlyReportBySmaVwapRule2Trades(Date startDate, Date endDate,
			Integer candleNumber);
}
