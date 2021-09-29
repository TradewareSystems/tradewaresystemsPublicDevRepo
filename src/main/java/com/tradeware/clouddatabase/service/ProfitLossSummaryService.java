package com.tradeware.clouddatabase.service;

import java.util.Date;
import java.util.List;

import com.tradeware.clouddatabase.bean.ProfitLossSummaryBean;

public interface ProfitLossSummaryService {

	List<ProfitLossSummaryBean> findAll();

	List<ProfitLossSummaryBean> findAllByDateStampOrderByDateStampDesc(Date dateStamp);

	List<ProfitLossSummaryBean> findAllByStrategyRuleAndDateStampOrderByDateStampDesc(String strategyRule,
			Date dateStamp);

	ProfitLossSummaryBean save(ProfitLossSummaryBean bean);

	List<ProfitLossSummaryBean> saveAll(List<ProfitLossSummaryBean> beans);
}
