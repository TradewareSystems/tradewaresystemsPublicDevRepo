package com.tradeware.clouddatabase.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tradeware.clouddatabase.entity.Narrow7Entity;

public interface Narrow7Repository extends CrudRepository<Narrow7Entity, Integer> {

	List<Narrow7Entity> findAll();

	List<Narrow7Entity> findAllByTradedDateStampOrderByTradedAtDtDesc(Date tradedDateStamp);

	List<Narrow7Entity> findAllByTradedDateStampOrderBySymbolAscTradedAtDtAsc(Date tradedDateStamp);
	
	List<Narrow7Entity> findAllByTempCustomTradingRuleIndOrderByTradedAtDt(Boolean tempCustomTradingRuleInd);
	
	List<Narrow7Entity> findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDt(Boolean tempCustomTradingRuleInd, Date tradedDateStamp);

	@Query(value = "SELECT entity FROM Narrow7Entity entity WHERE (entity.tradedState = 'BUY' OR entity.tradedState = 'SELL')"
			+ " AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDt desc")
	List<Narrow7Entity> getRunningTrades(@Param("tradedDateStamp") Date tradedDateStamp);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE TABLE_NR7_BREAK_OUT set TRADED_STATE=:tradedState, EXITED_AT_VAL =:exitedAtVal, "
			+ "EXITED_AT_DT_TM =:exitedAtDt, PROF_LOS_AMT =:profitLossAmt, POS_MOVE_VAL=:positiveMoveValue, "
			+ "NEG_MOVE_VAL=:negativeMoveValue where OPEN_RANGE_BREAK_OUT_ID =:itemId", nativeQuery = true)
	void updateTrade(@Param("tradedState") String tradedState, @Param("exitedAtVal") Double exitedAtVal,
			@Param("exitedAtDt") Date exitedAtDt, @Param("profitLossAmt") Double profitLossAmt,
			@Param("positiveMoveValue") Double positiveMoveValue, @Param("negativeMoveValue") Double negativeMoveValue,
			@Param("itemId") Integer itemId);
	
	
	//Additional Reports
	@Query(value = "SELECT entity FROM Narrow7Entity entity WHERE entity.ohlcState IS NOT NULL AND"
			+ " (entity.tradedState LIKE 'BUY%' OR entity.tradedState LIKE 'SELL%') AND entity.tradableState != 'NA'"
			+ " AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDt desc")
	List<Narrow7Entity> findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtAsc(@Param("tradedDateStamp") Date tradedDateStamp);
	@Query(value = "SELECT entity FROM Narrow7Entity entity WHERE entity.ohlcState IS NOT NULL AND"
			+ " entity.ohlcState = entity.tradableState"
			+ " AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDt desc")
	List<Narrow7Entity> findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtAsc(@Param("tradedDateStamp") Date tradedDateStamp);
	
	
	//VERSION 2
	@Query(value = "SELECT entity FROM Narrow7Entity entity WHERE (entity.tradedState LIKE 'BUY%' OR entity.tradedState LIKE 'SELL%')"
			+ " AND entity.tradableState != 'NA' AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDt desc")
	List<Narrow7Entity> findAllByTradedDateStampFilterItemsOrderByTradedAtDtDesc(@Param("tradedDateStamp") Date tradedDateStamp);
}
