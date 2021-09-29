package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.Narrow7Bean;
import com.tradeware.clouddatabase.entity.Narrow7Entity;
import com.tradeware.clouddatabase.repository.Narrow7Repository;

@Service
public class Narrow7ServiceImpl implements Narrow7Service {

	@Autowired
	private Narrow7Repository repository;

	private List<Narrow7Bean> convertEntityListToBeanList(List<Narrow7Entity> entityList) {
		List<Narrow7Bean> beanList = new ArrayList<Narrow7Bean>(entityList.size());
		for (Narrow7Entity entity : entityList) {
			Narrow7Bean bean = new Narrow7Bean();
			entity.populateBean(bean);
			beanList.add(bean);
		}
		return beanList;
	}

	@Override
	public List<Narrow7Bean> findAll() {
		return convertEntityListToBeanList(repository.findAll());
	}

	@Override
	public Narrow7Bean save(Narrow7Bean bean) {
		Narrow7Entity entity = new Narrow7Entity();
		entity.populateEntity(bean);
		entity = repository.save(entity);
		entity.populateBean(bean);
		return bean;
	}

	@Override
	public List<Narrow7Bean> getRunningTrades(Date tradedDateStamp) {
		return convertEntityListToBeanList(repository.getRunningTrades(tradedDateStamp));
	}

	@Override
	public List<Narrow7Bean> findAllByTradedDateStampOrderByTradedAtDtDesc(Date tradedDateStamp) {
		return convertEntityListToBeanList(repository.findAllByTradedDateStampOrderByTradedAtDtDesc(tradedDateStamp));
	}

	@Override
	public List<Narrow7Bean> findAllByTradedDateStampOrderBySymbolAscTradedAtDtAsc(Date tradedDateStamp) {
		return convertEntityListToBeanList(repository.findAllByTradedDateStampOrderBySymbolAscTradedAtDtAsc(tradedDateStamp));
	}

	@Override
	public void updateTrade(String tradedState, Double exitedAt, Date exitedAtDt, Double profitLossAmt,
			Double positiveMoveValue, Double negativeMoveValue, Integer itemId) {
		repository.updateTrade(tradedState, exitedAt, exitedAtDt, profitLossAmt, positiveMoveValue, negativeMoveValue,
				itemId);
	}

	@Override
	public List<Narrow7Bean> findAllByTempCustomTradingRuleIndOrderByTradedAtDt(Boolean tempCustomTradingRuleInd) {
		return convertEntityListToBeanList(
				repository.findAllByTempCustomTradingRuleIndOrderByTradedAtDt(tempCustomTradingRuleInd));
	}

	@Override
	public List<Narrow7Bean> findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDt(
			Boolean tempCustomTradingRuleInd, Date tradedDateStamp) {
		return convertEntityListToBeanList(repository
				.findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDt(tempCustomTradingRuleInd, tradedDateStamp));
	}

	@Override
	public List<Narrow7Bean> findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtAsc(Date tradedDateStamp) {
		return convertEntityListToBeanList(
				repository.findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtAsc(tradedDateStamp));
	}

	@Override
	public List<Narrow7Bean> findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtAsc(
			Date tradedDateStamp) {
		return convertEntityListToBeanList(repository
				.findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtAsc(
						tradedDateStamp));
	}

	@Override
	public List<Narrow7Bean> findAllByTradedDateStampFilterItemsOrderByTradedAtDtDesc(Date tradedDateStamp) {
		return convertEntityListToBeanList(repository.findAllByTradedDateStampFilterItemsOrderByTradedAtDtDesc(tradedDateStamp));
	}
}
