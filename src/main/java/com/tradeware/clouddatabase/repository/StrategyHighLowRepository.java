package com.tradeware.clouddatabase.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tradeware.clouddatabase.entity.StrategyHighLowEntity;
@Repository
public interface StrategyHighLowRepository extends CrudRepository<StrategyHighLowEntity, String> {
	List<StrategyHighLowEntity> findAll();

	//List<StrategyHighLowEntity> findAllByTradedDateAfter(Date tradedDate);
	List<StrategyHighLowEntity> findAllByTradedDate(Date tradedDate);
	List<StrategyHighLowEntity> findAllByTradedDateOrderBySymbolAscTradedAtDtAsc(Date tradedDate);
	
	@Query(value = "SELECT entity FROM StrategyHighLowEntity entity WHERE entity.strenthTradableState IS NOT NULL "
			+ "AND entity.strenthTradableStateNifty IS NOT NULL AND entity.strenthTradableState = entity.strenthTradableStateNifty "
			//+ "AND entity.tradedDate = :tradedDate ORDER BY entity.symbol, entity.tradedAtDt")
			+ "AND entity.tradedDate = :tradedDate ORDER BY entity.symbol, entity.tradedAtDt")
	List<StrategyHighLowEntity> findAllTradesByTradedDateAndTrendMactchWithNifty50OrderBySymbolAscTradedAtDtAsc(@Param("tradedDate")Date tradedDate);
	
	@Query(value = "SELECT entity FROM StrategyHighLowEntity entity WHERE entity.strenthTradableState IS NOT NULL "
			+ "AND entity.strenthTradableStateNifty IS NOT NULL AND entity.strenthTradableState = entity.strenthTradableStateNifty "
			+ "AND entity.closeRule = true AND entity.closeHighRule1 = true "
			//+ "AND entity.tradedDate = :tradedDate ORDER BY entity.symbol, entity.tradedAtDt")
			+ "AND entity.tradedDate = :tradedDate ORDER BY entity.symbol, entity.tradedAtDt")
	List<StrategyHighLowEntity> findAllTradesByTradedDateAndTrendMactchWithNifty50AndCloseRuleOrderBySymbolAscTradedAtDtAsc(@Param("tradedDate")Date tradedDate);

	/* It works for in memory H2 database
	   *
	  @Transactional
	  @Modifying 
	   @Query(value =
	  "UPDATE T_HIGH_LOW entity set entity.TRADED_STATE=:tradedState, entity.EXITED_AT =:exitedAt, "
	  + "entity.EXITED_AT_DT_TM =:exitedAtDt, entity.PROF_LOS_AMT =:profitLossAmt, entity.POS_MOVE_VAL=:positiveMoveValue, "
	  + "entity.NEG_MOVE_VAL=:negativeMoveValue where entity.HIGH_LOW_ID =:itemId",
	  nativeQuery = true)
	  void updateTrade(@Param("tradedState") String tradedState, @Param("exitedAt") Double exitedAt,
				@Param("exitedAtDt") Date exitedAtDt, @Param("profitLossAmt") Double profitLossAmt,
				@Param("positiveMoveValue") Double positiveMoveValue, @Param("negativeMoveValue") Double negativeMoveValue,
				@Param("itemId") Integer itemId);*/ 
	 
	@Transactional
	@Modifying
	@Query(value = "UPDATE T_HIGH_LOW set TRADED_STATE=:tradedState, EXITED_AT =:exitedAt, "
			+ "EXITED_AT_DT_TM =:exitedAtDt, PROF_LOS_AMT =:profitLossAmt, POS_MOVE_VAL=:positiveMoveValue, "
			+ "NEG_MOVE_VAL=:negativeMoveValue where HIGH_LOW_ID =:itemId", nativeQuery = true)
	void updateTrade(@Param("tradedState") String tradedState, @Param("exitedAt") Double exitedAt,
			@Param("exitedAtDt") Date exitedAtDt, @Param("profitLossAmt") Double profitLossAmt,
			@Param("positiveMoveValue") Double positiveMoveValue, @Param("negativeMoveValue") Double negativeMoveValue,
			@Param("itemId") Integer itemId);

	//@Transactional
	//@Modifying
	/**@Query(value = "UPDATE StrategyHighLowEntity entity set entity.tradedState=:tradedState, entity.exitedAt =:exitedAt, "
			+ "entity.exitedAtDt =:exitedAtDt, entity.profitLossAmt =:profitLossAmt, entity.positiveMoveValue=:positiveMoveValue, "
			+ "entity.negativeMoveValue=:negativeMoveValue where entity.itemId =:itemId", nativeQuery = true)
	void updateTrade(@Param("tradedState") String tradedState, @Param("exitedAt") Double exitedAt,
			@Param("exitedAtDt") Date exitedAtDt, @Param("profitLossAmt") Double profitLossAmt,
			@Param("positiveMoveValue") Double positiveMoveValue, @Param("negativeMoveValue") Double negativeMoveValue,
			@Param("itemId") Integer itemId);**/

	// for reports
	// just BUY, SELL report
	List<StrategyHighLowEntity> findAllByTradedStateOrderByTradedAtDt(String tradedState);
	List<StrategyHighLowEntity> findAllByTradedStateOrderBySymbolAscTradedAtDtAsc(String tradedState);

	// just (BUY,BUY_EXIT_PROFIT,BUY_EXIT_LOSS)
	// (SELL,SELL_EXIT_PROFIT,SELL_EXIT_LOSS) report
	//List<StrategyHighLowEntity> findAllByTradedStateStartingWithAndTradedDateAfterOrderByTradedAtDt(String tradedState, Date tradedDate);
	List<StrategyHighLowEntity> findAllByTradedStateStartingWithAndTradedDateOrderByTradedAtDt(String tradedState, Date tradedDate);
	List<StrategyHighLowEntity> findAllByTradedStateStartingWithAndTradedDateOrderBySymbolAscTradedAtDtAsc(String tradedState, Date tradedDate);

	// just BUY, SELL with only profit/loss report
	//List<StrategyHighLowEntity> findAllByTradedStateLikeOrderByTradedAtDt(String tradedState);
	//List<StrategyHighLowEntity> findAllByTradedStateContainsAndTradedDateAfterOrderByTradedAtDt(String tradedState, Date tradedDate);
	List<StrategyHighLowEntity> findAllByTradedStateContainsAndTradedDateOrderByTradedAtDt(String tradedState, Date tradedDate);
	List<StrategyHighLowEntity> findAllByTradedStateContainsAndTradedDateOrderBySymbolAscTradedAtDtAsc(String tradedState, Date tradedDate);

	@Query(value = "SELECT entity FROM StrategyHighLowEntity entity WHERE (entity.tradedState = 'BUY' OR entity.tradedState = 'SELL')"
			+ " AND entity.tradedDate = :tradedDate ORDER BY entity.tradedAtDt")
	List<StrategyHighLowEntity> getRunningTrades(@Param("tradedDate") Date tradedDate);
	
	@Query(value = "SELECT entity FROM StrategyHighLowEntity entity WHERE entity.tradedState LIKE %:tradedState% "
			+ "AND entity.smallCandle = false AND entity.closeRule = true AND (entity.closeHighRule1 = true OR entity.closeHighRule2 = true) "
			+ "AND entity.tradedDate = :tradedDate ORDER BY entity.symbol, entity.tradedAtDt")
	/*List<StrategyHighLowEntity> findAllByTradedStateAndCloseRulesAndTradedDateAfterOrderByTradedAtDt(
			@Param("tradedState") String tradedState, @Param("tradedDate") Date tradedDate);*/
	List<StrategyHighLowEntity> findAllByTradedStateAndCloseRulesAndTradedDateOrderByTradedAtDt(
			@Param("tradedState") String tradedState, @Param("tradedDate") Date tradedDate);
	
	
	
	//Custom strategy queries for order placement
	@Query(value = "SELECT entity FROM StrategyHighLowEntity entity WHERE entity.tradedState LIKE '%SELL%' "
			+ "AND entity.smallCandle = false AND entity.closeRule = true AND entity.closeHighRule1 = true "
			+ "AND entity.openHighLowHARule = true AND entity.lastCandle = 'R' "
			+ "AND (entity.volumeTrend = 'LEVEL2_SELL' OR entity.volumeTrend = 'LEVEL1_SELL' OR entity.volumeTrend = 'LEVEL_NA') "
			+ "AND entity.tradedDate = :tradedDate ORDER BY entity.symbol, entity.tradedAtDt")
	List<StrategyHighLowEntity> findAllByCustomRuleSell(@Param("tradedDate") Date tradedDate);
	
	//Custom strategy queries for order placement
		@Query(value = "SELECT entity FROM StrategyHighLowEntity entity WHERE entity.tradedState LIKE '%BUY%' "
				+ "AND entity.smallCandle = false AND entity.closeRule = true AND entity.closeHighRule1 = true "
				+ "AND entity.openHighLowRule = true AND entity.openHighLowHARule = true AND entity.oppositeHighLowRule = true "
				+ "AND entity.lastCandle = 'G' AND (entity.volumeTrend = 'LEVEL2_BUY' OR entity.volumeTrend = 'LEVEL1_BUY') "
				+ "AND entity.tradedDate = :tradedDate ORDER BY entity.symbol, entity.tradedAtDt")
	List<StrategyHighLowEntity> findAllByCustomRuleBuy(@Param("tradedDate") Date tradedDate);
		
		//Reeports
		List<StrategyHighLowEntity> findAllByNr4RuleAndTradedDateOrderBySymbolAscTradedAtDtAsc(boolean nr4RuleInd, Date tradedDate);
	List<StrategyHighLowEntity> findAllByNr4RuleAndTradedDateGreaterThanEqualOrderBySymbolAscTradedAtDtAsc(boolean nr4RuleInd, Date tradedDate);
		
		@Query(value = "SELECT entity FROM StrategyHighLowEntity entity WHERE entity.tradedState LIKE %:tradedState% "
				+ "AND entity.minute15CPR > 0 AND entity.minute5CPR > 0 AND entity.tradedDate = :tradedDate ORDER BY entity.symbol, entity.tradedAtDt")
		List<StrategyHighLowEntity> findAllByTradedStateAndCPROrderBySymbolAscTradedAtDtAsc(@Param("tradedState") String tradedState, @Param("tradedDate") Date tradedDate);
		
		@Query(value = "SELECT entity FROM StrategyHighLowEntity entity WHERE entity.tradedState LIKE %:tradedState% "
				+ "AND (entity.minute15CPR <= 0 OR entity.minute5CPR <= 0) AND entity.tradedDate = :tradedDate ORDER BY entity.symbol, entity.tradedAtDt")
		List<StrategyHighLowEntity> findAllByTradedStateAndNotCPROrderBySymbolAscTradedAtDtAsc(@Param("tradedState") String tradedState, @Param("tradedDate") Date tradedDate);

		
		@Query(value = "SELECT entity FROM StrategyHighLowEntity entity WHERE entity.tradedState LIKE %:tradedState% "
				+ "AND entity.minute15CPR > 0 AND entity.minute5CPR > 0 AND entity.tradedDate = :tradedDate "
				+ "AND entity.closeRule = true AND entity.closeHighRule1 = true AND entity.closeHighRule2 = true "
				+ "ORDER BY entity.symbol, entity.tradedAtDt")
		List<StrategyHighLowEntity> findAllByTradedStateAndCPROAndCloserderBySymbolAscTradedAtDtAsc(@Param("tradedState") String tradedState, @Param("tradedDate") Date tradedDate);
		
		@Query(value = "SELECT entity FROM StrategyHighLowEntity entity WHERE entity.tradedState LIKE %:tradedState% "
				+ "AND (entity.minute15CPR <= 0 OR entity.minute5CPR <= 0) AND entity.tradedDate = :tradedDate "
				+ "AND entity.closeRule = true AND entity.closeHighRule1 = true AND entity.closeHighRule2 = true "
				+ "ORDER BY entity.symbol, entity.tradedAtDt")
		List<StrategyHighLowEntity> findAllByTradedStateAndNotCPRAndCloseOrderBySymbolAscTradedAtDtAsc(@Param("tradedState") String tradedState, @Param("tradedDate") Date tradedDate);
}
