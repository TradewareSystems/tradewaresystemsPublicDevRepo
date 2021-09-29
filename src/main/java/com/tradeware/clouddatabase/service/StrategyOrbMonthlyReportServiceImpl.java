package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.StrategyOrbMonthlyReportBean;
import com.tradeware.clouddatabase.entity.StrategyOrbMonthlyReportEntity;
import com.tradeware.clouddatabase.repository.StrategyOrbMonthlyReportRepository;
@Service
public class StrategyOrbMonthlyReportServiceImpl implements StrategyOrbMonthlyReportService {

	@Autowired
	private StrategyOrbMonthlyReportRepository monthlyReportRepository;

	// Monthly Reports
	private List<StrategyOrbMonthlyReportBean> convertMonthlyEntityListToBeanList(
			List<StrategyOrbMonthlyReportEntity> entityList) {
		List<StrategyOrbMonthlyReportBean> beanList = new ArrayList<StrategyOrbMonthlyReportBean>(entityList.size());
		for (StrategyOrbMonthlyReportEntity entity : entityList) {
			StrategyOrbMonthlyReportBean bean = new StrategyOrbMonthlyReportBean();
			entity.populateBean(bean);
			beanList.add(bean);
		}
		return beanList;
	}

	@Override
	public List<StrategyOrbMonthlyReportBean> findAllMonthlyReportByForwardEngulfingRuleTrades(Date startDate,
			Date endDate, Integer candleNumber) {
		return convertMonthlyEntityListToBeanList(monthlyReportRepository
				.findAllMonthlyReportByForwardEngulfingRuleTrades(startDate, endDate, candleNumber));
	}
	
	@Override
	public List<StrategyOrbMonthlyReportBean> findAllMonthlyReportByForwardEngulfingLvl2RuleTrades(Date startDate,
			Date endDate, Integer candleNumber) {
		return convertMonthlyEntityListToBeanList(monthlyReportRepository
				.findAllMonthlyReportByForwardEngulfingLvl2RuleTrades(startDate, endDate, candleNumber));
	}
	
	@Override
	public List<StrategyOrbMonthlyReportBean> findAllMonthlyReportByForwardEngulfingLvl3RuleTrades(Date startDate,
			Date endDate, Integer candleNumber) {
		return convertMonthlyEntityListToBeanList(monthlyReportRepository
				.findAllMonthlyReportByForwardEngulfingLvl3RuleTrades(startDate, endDate, candleNumber));
	}

	@Override
	public List<StrategyOrbMonthlyReportBean> findAllMonthlyReportBySmaVwapRuleTrades(Date startDate, Date endDate,
			Integer candleNumber) {
		return convertMonthlyEntityListToBeanList(
				monthlyReportRepository.findAllMonthlyReportBySmaVwapRuleTrades(startDate, endDate, candleNumber));
	}

	@Override
	public List<StrategyOrbMonthlyReportBean> findAllMonthlyReportBySmaVwapRuleUnionTrades(Date startDate, Date endDate,
			Integer candleNumber) {
		return convertMonthlyEntityListToBeanList(
				monthlyReportRepository.findAllMonthlyReportBySmaVwapRuleUnionTrades(startDate, endDate, candleNumber));
	}
	
	@Override
	public List<StrategyOrbMonthlyReportBean> findAllMonthlyReportBySmaVwapRule2Trades(Date startDate, Date endDate,
			Integer candleNumber) {
		return convertMonthlyEntityListToBeanList(
				monthlyReportRepository.findAllMonthlyReportBySmaVwapRule2Trades(startDate, endDate, candleNumber));
	}
	
}
