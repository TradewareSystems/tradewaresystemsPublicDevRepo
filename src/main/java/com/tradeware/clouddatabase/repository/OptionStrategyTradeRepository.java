package com.tradeware.clouddatabase.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tradeware.clouddatabase.entity.OptionLiveTradeMainEntity;

public interface OptionStrategyTradeRepository extends CrudRepository<OptionLiveTradeMainEntity, Integer> {

	List<OptionLiveTradeMainEntity> findAllByOrderByTradedDateStampDesc();
	
	OptionLiveTradeMainEntity findByTradeId(Integer tradeId);

	List<OptionLiveTradeMainEntity> findByTradePosition(String tradePosition);
	
	List<OptionLiveTradeMainEntity> findAllByTradedDateStamp(Date tradedDateStamp);

	List<OptionLiveTradeMainEntity> findByTradePositionAndTradeStrategy(String tradePosition, String radeStrategy);

	@SuppressWarnings("unchecked")
	OptionLiveTradeMainEntity save(OptionLiveTradeMainEntity entity);
	
	
	
	@Query(value = "SELECT entity FROM OptionLiveTradeMainEntity entity "
			+ "JOIN entity.tradeChildEntity childEntity WHERE "
			+ "(childEntity.tradePosition = 'OPEN' OR childEntity.tradePosition = 'NEW')"
			+ "AND entity.tradeStrategy IN (:tradeStrategyList)")
	List<OptionLiveTradeMainEntity> findAllByRunningChildTrade(@Param("tradeStrategyList") List<String> tradeStrategyList);
	
	@Query(value = "SELECT entity FROM OptionLiveTradeMainEntity entity "
			+ "JOIN entity.tradeChildEntity childEntity WHERE "
			+ "(childEntity.tradePosition = 'OPEN' OR childEntity.tradePosition = 'NEW')")
	List<OptionLiveTradeMainEntity> findAllByRunningTrade();
	
	@Query(value = "SELECT entity FROM OptionLiveTradeMainEntity entity "
			+ "JOIN entity.tradeChildEntity childEntity WHERE "
			+ "(childEntity.tradePosition = 'OPEN' OR childEntity.tradePosition = 'NEW')"
			+ "AND entity.tradedDateStamp = :tradedDateStamp")
	List<OptionLiveTradeMainEntity> findAllByRunningTrade(@Param("tradedDateStamp") Date tradedDateStamp);
	
	@Query(value = "SELECT entity FROM OptionLiveTradeMainEntity entity "
			+ "JOIN entity.tradeChildEntity childEntity WHERE "
			+ "entity.tradeId = :tradeId "
			+ "AND (childEntity.tradePosition = 'OPEN' OR childEntity.tradePosition = 'NEW')")
	OptionLiveTradeMainEntity findRunningTradeByTradeId(@Param("tradeId") Integer tradeId);
}
