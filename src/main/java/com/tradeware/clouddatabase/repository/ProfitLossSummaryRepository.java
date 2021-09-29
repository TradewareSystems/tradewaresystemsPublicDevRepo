package com.tradeware.clouddatabase.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tradeware.clouddatabase.entity.ProfitLossSummaryEntity;

public interface ProfitLossSummaryRepository extends CrudRepository<ProfitLossSummaryEntity, Integer> {

	List<ProfitLossSummaryEntity> findAll();

	List<ProfitLossSummaryEntity> findAllByDateStampOrderByDateStampDesc(Date dateStamp);
	
	List<ProfitLossSummaryEntity> findAllByStrategyRuleAndDateStampOrderByDateStampDesc(String strategyRule,
			Date dateStamp);

}
