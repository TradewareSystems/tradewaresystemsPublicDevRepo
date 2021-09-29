package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.ProfitLossSummaryBean;
import com.tradeware.clouddatabase.entity.ProfitLossSummaryEntity;
import com.tradeware.clouddatabase.repository.ProfitLossSummaryRepository;

@Service
public class ProfitLossSummaryServiceImpl implements ProfitLossSummaryService {

	@Autowired
	private ProfitLossSummaryRepository repository;

	private List<ProfitLossSummaryBean> convertEntityListToBeanList(List<ProfitLossSummaryEntity> entityList) {
		List<ProfitLossSummaryBean> beanList = new ArrayList<ProfitLossSummaryBean>(entityList.size());
		for (ProfitLossSummaryEntity entity : entityList) {
			ProfitLossSummaryBean bean = entity.populateBean();
			beanList.add(bean);
		}
		return beanList;
	}

	private Iterable<ProfitLossSummaryEntity> convertBeanListToEntityList(List<ProfitLossSummaryBean> listBean) {
		List<ProfitLossSummaryEntity> listEntity = new ArrayList<ProfitLossSummaryEntity>(listBean.size());
		for (ProfitLossSummaryBean bean : listBean) {
			ProfitLossSummaryEntity entity = new ProfitLossSummaryEntity();
			entity.populateEntity(bean);
			listEntity.add(entity);
		}
		return listEntity;
	}

	@Override
	public List<ProfitLossSummaryBean> findAll() {
		return convertEntityListToBeanList(repository.findAll());
	}

	@Override
	public List<ProfitLossSummaryBean> findAllByDateStampOrderByDateStampDesc(Date dateStamp) {
		return convertEntityListToBeanList(repository.findAllByDateStampOrderByDateStampDesc(dateStamp));
	}

	@Override
	public List<ProfitLossSummaryBean> findAllByStrategyRuleAndDateStampOrderByDateStampDesc(String strategyRule,
			Date dateStamp) {
		return convertEntityListToBeanList(
				repository.findAllByStrategyRuleAndDateStampOrderByDateStampDesc(strategyRule, dateStamp));
	}

	@Override
	public ProfitLossSummaryBean save(ProfitLossSummaryBean bean) {
		ProfitLossSummaryEntity entity = new ProfitLossSummaryEntity();
		entity.populateEntity(bean);
		repository.save(entity);
		return entity.populateBean();
	}

	@Override
	public List<ProfitLossSummaryBean> saveAll(List<ProfitLossSummaryBean> beans) {
		Iterable<ProfitLossSummaryEntity> result = repository.saveAll(convertBeanListToEntityList(beans));
		List<ProfitLossSummaryBean> resultList = new ArrayList<ProfitLossSummaryBean>();
		for (ProfitLossSummaryEntity entity : result) {
			resultList.add(entity.populateBean());
		}
		return resultList;
	}
}
