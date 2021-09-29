package com.tradeware.clouddatabase.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tradeware.clouddatabase.entity.StrategyOrbEntity;

@Repository
public interface StrategyOrbRepository extends CrudRepository<StrategyOrbEntity, Integer> {

	List<StrategyOrbEntity> findAll();

	List<StrategyOrbEntity> findAllByTradedDateStampOrderByTradedAtDtTmDesc(Date tradedDateStamp);

	List<StrategyOrbEntity> findAllByTradedDateStampOrderBySymbolNameAscTradedAtDtTmAsc(Date tradedDateStamp);

	List<StrategyOrbEntity> findAllByTempCustomTradingRuleIndOrderByTradedAtDtTm(Boolean tempCustomTradingRuleInd);

	List<StrategyOrbEntity> findAllByTempCustomTradingRuleIndAndTradedDateStampOrderByTradedAtDtTm(
			Boolean tempCustomTradingRuleInd, Date tradedDateStamp);

	/*@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE (entity.tradedState = 'BUY' OR entity.tradedState = 'SELL')"
			+ " AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")*/
	/*@Query(value = "SELECT OPEN_RANGE_BREAK_OUT.* FROM T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT WHERE (OPEN_RANGE_BREAK_OUT.TRADED_STATE_ID = 'BUY' OR "
			+ "OPEN_RANGE_BREAK_OUT.TRADED_STATE_ID = 'SELL' AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp "
			+ "ORDER BY OPEN_RANGE_BREAK_OUT.DT_STAMP DESC", nativeQuery = true)*/
	//New Query
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(entity.tradedStateId.tradeStateId = 'BUY' OR entity.tradedStateId.tradeStateId = 'SELL') "
			+ "AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> getRunningTrades(@Param("tradedDateStamp") Date tradedDateStamp);

	@Transactional
	@Modifying
	/*@Query(value = "UPDATE T_OPEN_RANGE_BREAK_OUT set TRADED_STATE=:tradedState, EXITED_AT_VAL =:exitedAtVal, "
			+ "EXITED_AT_DT_TM =:exitedAtDtTm, PROF_LOS_AMT_VAL =:profitLossAmtVal, POS_MOVE_VAL=:positiveMoveVal, "
			+ "NEG_MOVE_VAL=:negativeMoveVal where OPEN_RANGE_BREAK_OUT_ID =:itemId", nativeQuery = true)*/
	@Query(value = "UPDATE StrategyOrbEntity entity set entity.tradedStateId.tradeStateId=:tradedState, "
			+ "entity.exitedAtVal =:exitedAtVal, entity.exitedAtDtTm =:exitedAtDtTm, "
			+ "entity.profitLossAmtVal =:profitLossAmtVal, entity.positiveMoveVal=:positiveMoveVal, "
			+ "entity.negativeMoveVal=:negativeMoveVal, entity.positiveMoveLtp =:positiveMoveLtp, "
			+ "entity.negativeMoveLtp=:negativeMoveLtp, entity.stopLoss=:stopLossVal "
			+ "where entity.itemId =:itemId")
	void updateTrade(@Param("tradedState") String tradedState, @Param("exitedAtVal") Double exitedAtVal,
			@Param("exitedAtDtTm") Date exitedAtDtTm, @Param("profitLossAmtVal") Double profitLossAmtVal,
			@Param("positiveMoveVal") Double positiveMoveVal, @Param("negativeMoveVal") Double negativeMoveVal,
			@Param("positiveMoveLtp") Double positiveMoveLtp, @Param("negativeMoveLtp") Double negativeMoveLtp,
			@Param("stopLossVal") Double stopLossVal,
			@Param("itemId") Integer itemId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE T_OPEN_RANGE_BREAK_OUT set PROF_LOS_AMT_VAL =:profitLossAmtVal, POS_MOVE_VAL=:positiveMoveVal, "
			+ "NEG_MOVE_VAL=:negativeMoveVal, POS_MOVE_LTP =:positiveMoveLtp, NEG_MOVE_LTP =:negativeMoveLtp, "
			+ "STOP_LOSS =:stopLossValue where OPEN_RANGE_BREAK_OUT_ID =:itemId", nativeQuery = true)
	void updatePositiveNegativeMoves(@Param("profitLossAmtVal") Double profitLossAmtVal,
			@Param("positiveMoveVal") Double positiveMoveVal, @Param("negativeMoveVal") Double negativeMoveVal,
			@Param("positiveMoveLtp") Double positiveMoveLtp, @Param("negativeMoveLtp") Double negativeMoveLtp,
			@Param("stopLossValue") Double stopLossValue, @Param("itemId") Integer itemId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE T_OPEN_RANGE_BREAK_OUT set TRADED_LOT_COUNT=:tradedLotCount, TRADED_AT_AVG_VAL =:tradedAtAvgVal, "
			+ "TRADED_AT_VAL_2 =:tradedAtVal2, TRADED_AT_DT_TM_2 =:tradedAtDtTm2,  TRADED_AT_VAL_3 =:tradedAtVal3, TRADED_AT_DT_TM_3 =:tradedAtDtTm3 "
			+", TARGET_AMT_VAL_2 =:targetAmtVal2, TARGET_AMT_VAL_3 =:targetAmtVal3 "
			+ " where OPEN_RANGE_BREAK_OUT_ID =:itemId", nativeQuery = true)
	void updateAdujustTrade(@Param("tradedLotCount") Integer tradedLotCount,
			@Param("tradedAtAvgVal") Double tradedAtAvgVal, @Param("tradedAtVal2") Double tradedAtVal2,
			@Param("tradedAtDtTm2") Date tradedAtDtTm2, @Param("tradedAtVal3") Double tradedAtVal3,
			@Param("tradedAtDtTm3") Date tradedAtDtTm3, @Param("targetAmtVal2") Double targetAmtVal2, @Param("targetAmtVal3") Double targetAmtVal3, 
			@Param("itemId") Integer itemId);
	
	// 04-21-2021 start - afterSomeSuccess [04-27-2021]
	@Transactional
	@Modifying
	@Query(value = "UPDATE StrategyOrbEntity entity "
			+ "set entity.manualTradeExitInd = true, entity.manualTradeExitStateId.tradeStateId=:manualTradeExitStateId, "
			+ "entity.manualExitedAtVal =:manualExitedAtVal, entity.manualExitedAtDtTm =:manualExitedAtDtTm, "
			+ "entity.manualBookProfitLossAmtVal =:manualBookProfitLossAmtVal where entity.itemId =:itemId")
	void manualTradeClose(@Param("manualTradeExitStateId") String manualTradeExitStateId,
			@Param("manualExitedAtVal") Double manualExitedAtVal, @Param("manualExitedAtDtTm") Date manualExitedAtDtTm,
			@Param("manualBookProfitLossAmtVal") Double manualBookProfitLossAmtVal, @Param("itemId") Integer itemId);
	// 04-21-2021 end

	// Additional Reports
	/*@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE entity.ohlcState IS NOT NULL AND"
			+ " (entity.tradedState LIKE 'BUY%' OR entity.tradedState LIKE 'SELL%') AND entity.tradableState != 'NA'"
			+ " AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")*/
	@Query(value = "SELECT OPEN_RANGE_BREAK_OUT.* FROM T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT WHERE OPEN_RANGE_BREAK_OUT.OHLC_STATE_ID  IS NOT NULL AND "
			+ "(OPEN_RANGE_BREAK_OUT.TRADED_STATE_ID LIKE 'BUY%' OR OPEN_RANGE_BREAK_OUT.TRADED_STATE_ID LIKE 'SELL%') AND OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID != 'NA' "
			+ "AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp ORDER BY OPEN_RANGE_BREAK_OUT.DT_STAMP DESC", nativeQuery = true)
	List<StrategyOrbEntity> findAllByTradedDateStampAndOhlcStateNotNullOrderBySymbolAscTradedAtDtTmAsc(
			@Param("tradedDateStamp") Date tradedDateStamp);

	/*@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE entity.ohlcState IS NOT NULL AND"
			+ " entity.ohlcState = entity.tradableState"
			+ " AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")*/
	//Old query
	/*@Query(value = "SELECT OPEN_RANGE_BREAK_OUT.* FROM T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT WHERE (OPEN_RANGE_BREAK_OUT.OHLC_STATE_ID  IS NOT NULL AND "
			+ "OPEN_RANGE_BREAK_OUT.OHLC_STATE_ID = OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp ORDER BY "
			+ "OPEN_RANGE_BREAK_OUT.DT_STAMP DESC", nativeQuery = true)*/
	//New query
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "entity.tradingRuleEntity.tradingRuleOHLCAnd3TimesStrengthInd = true "
			+ "AND entity.tradingRuleEntity.tradingRuleTradableStateEqCandleTypeTrendEqOhlcStateAndPrevDayHrCrossInd = true "
			+ "AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByTradedDateStampAndOhlcStateNotNullAndOhlcStateEqualToTradableStateOrderBySymbolAscTradedAtDtTmAsc(
			@Param("tradedDateStamp") Date tradedDateStamp);

	// VERSION 2
	/*@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE (entity.tradedState LIKE 'BUY%' OR entity.tradedState LIKE 'SELL%')"
			+ " AND entity.tradableState != 'NA' AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")*/
	@Query(value = "SELECT OPEN_RANGE_BREAK_OUT.* FROM T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT WHERE (OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID = 'BUY' OR "
			+ "OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID = 'SELL')  AND OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID != 'NA' AND "
			+ "OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber "
			+ "ORDER BY OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC", nativeQuery = true)
	List<StrategyOrbEntity> findAllByTradedDateStampFilterItemsOrderByTradedAtDtTmDesc(
			@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	
	
	
	/*@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE (entity.tradedState = 'BUY' OR entity.tradedState = 'SELL')"
			+ " AND entity.dayLevelTradeInd = true AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")*/
	/*@Query(value = "SELECT OPEN_RANGE_BREAK_OUT.* FROM T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT WHERE (OPEN_RANGE_BREAK_OUT.TRADED_STATE_ID = 'BUY' OR "
			+ "OPEN_RANGE_BREAK_OUT.TRADED_STATE_ID = 'SELL') AND  OPEN_RANGE_BREAK_OUT.DAY_LVL_TRADE_IND  = true AND "
			+ "OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp ORDER BY OPEN_RANGE_BREAK_OUT.DT_STAMP DESC", nativeQuery = true)*/
	//New Query
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(entity.tradedStateId.tradeStateId = 'BUY' OR entity.tradedStateId.tradeStateId = 'SELL') "
			+ " AND entity.dayLevelTradeInd = true AND entity.tradedDateStamp = :tradedDateStamp "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> getRunningTradesDayLevelTrade(@Param("tradedDateStamp") Date tradedDateStamp);
	
	List<StrategyOrbEntity> findAllByTradedDateStampAndDayLevelTradeIndOrderBySymbolNameAscTradedAtDtTmDesc(Date tradedDateStamp, Boolean dayLevelTradeInd);
	
	/*@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE (entity.tradedState LIKE 'BUY%' OR entity.tradedState LIKE 'SELL%')"
			+ " AND entity.tradableState != 'NA' AND entity.dayLevelTradeInd = true AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")*/
/*	@Query(value = "SELECT OPEN_RANGE_BREAK_OUT.* FROM T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT WHERE (OPEN_RANGE_BREAK_OUT.TRADED_STATE_ID  LIKE 'BUY%' OR "
			+ "OPEN_RANGE_BREAK_OUT.TRADED_STATE_ID  LIKE 'SELL%') AND OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID != 'NA' "
			+ "AND OPEN_RANGE_BREAK_OUT.DAY_LVL_TRADE_IND  = true AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp ORDER BY "
			+ "OPEN_RANGE_BREAK_OUT.DT_STAMP DESC", nativeQuery = true)*/
	//New Query
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(entity.tradableStateId.tradeStateId = 'BUY' OR entity.tradableStateId.tradeStateId = 'SELL') "
			+ " AND entity.dayLevelTradeInd = true AND entity.tradedDateStamp = :tradedDateStamp "
			+ " AND entity.candleNumber <= :candleNumber ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByTradedDateFilterAndDayLevelTradeindItemsOrderBytradedAtDtTmDesc(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	
	// Additional Reports
		/*@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE entity.ohlcState IS NOT NULL AND"
				+ " entity.tradableState != 'NA' AND (entity.tradedState LIKE 'BUY%' OR entity.tradedState LIKE 'SELL%') AND entity.dayLevelTradeInd = true "
				+ " AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")*/
	@Query(value = "SELECT OPEN_RANGE_BREAK_OUT.* FROM T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT WHERE OPEN_RANGE_BREAK_OUT.OHLC_STATE_ID  IS NOT NULL AND "
			+ "OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID != 'NA' AND (OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID  LIKE 'BUY%' OR OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID LIKE 'SELL%') "
			+ "AND OPEN_RANGE_BREAK_OUT.DAY_LVL_TRADE_IND  = true AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp ORDER BY "
			+ "OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC", nativeQuery = true)
		List<StrategyOrbEntity> findAllByTradedDateAndOhlcStateNotNullAndDayLevelIndOrderBySymbolAsctradedAtDtTmAsc(
				@Param("tradedDateStamp") Date tradedDateStamp);

		/*@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE entity.ohlcState IS NOT NULL AND"
				+ " entity.ohlcState = entity.tradableState AND entity.dayLevelTradeInd = true AND entity.tradableState != 'NA'"
				+ " AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")*/
	@Query(value = "SELECT OPEN_RANGE_BREAK_OUT.* FROM T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT WHERE OPEN_RANGE_BREAK_OUT.OHLC_STATE_ID  IS NOT NULL AND "
			+ "OPEN_RANGE_BREAK_OUT.OHLC_STATE_ID = OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID AND OPEN_RANGE_BREAK_OUT.DAY_LVL_TRADE_IND = true "
			+ "AND OPEN_RANGE_BREAK_OUT.TRADABLE_STATE_ID != 'NA' AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp ORDER BY "
			+ "OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC", nativeQuery = true)
		List<StrategyOrbEntity> findAllByTradedDateAndOhlcStateNotNullAndOhlcStateEqualToTradableStateAndDayLevelIndOrderBySymbolAsctradedAtDtTmAsc(
				@Param("tradedDateStamp") Date tradedDateStamp);
		
		
		//custom propfit rule analysed and found on 11/13/2020
	/**
	 * select DISTINCT(DT_STAMP) from T_OPEN_RANGE_BREAK_OUT where ((
	 * (CNDL_2_HIG_MINUS_CLS >1 and ((CNDL_2_HIG_MINUS_CLS * 7) <
	 * CNDL_2_CLS_MINUS_LOW) and CNDL_LOWS_DIFF < 7000) AND TRADABLE_STATE = 'BUY')
	 * or ( (CNDL_2_CLS_MINUS_LOW >1 and((CNDL_2_CLS_MINUS_LOW * 7) <
	 * CNDL_2_HIG_MINUS_CLS)) AND TRADABLE_STATE = 'SELL') and CNDL_HIGHS_DIFF <
	 * 7000) ORDER BY TRADED_AT_DT_TM DESC;
	 */
	/*@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE ("
			+ "( (entity.candle2HighMinusClose > 1 AND ((entity.candle2HighMinusClose * 7) <= entity.candle2CloseMinusLow) ) "
			+ "AND entity.candleLowsDiff <= 7000 AND entity.tradableState = 'BUY') "
			+ "OR ( (entity.candle2CloseMinusLow > 1  AND ((entity.candle2CloseMinusLow * 7) <= entity.candle2HighMinusClose)) "
			+ "AND entity.candleHighsDiff <= 7000 AND entity.tradableState = 'SELL') ) "
			+ " AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")*/
	/*@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT where (( (OPEN_RANGE_BREAK_OUT.CNDL_2_HIG_MINUS_CLS > 1 "
			+ "and ((OPEN_RANGE_BREAK_OUT.CNDL_2_HIG_MINUS_CLS * 7) <= OPEN_RANGE_BREAK_OUT.CNDL_2_CLS_MINUS_LOW) ) and OPEN_RANGE_BREAK_OUT.CNDL_LOWS_DIFF <= 7000 "
			+ "AND OPEN_RANGE_BREAK_OUT.TRADABLE_STATE = 'BUY') or ( (OPEN_RANGE_BREAK_OUT.CNDL_2_CLS_MINUS_LOW > 1 "
			+ "and ((OPEN_RANGE_BREAK_OUT.CNDL_2_CLS_MINUS_LOW * 7) <= OPEN_RANGE_BREAK_OUT.CNDL_2_HIG_MINUS_CLS)) AND OPEN_RANGE_BREAK_OUT.CNDL_HIGHS_DIFF <= 7000 "
			+ "AND OPEN_RANGE_BREAK_OUT.TRADABLE_STATE = 'SELL') ) AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp ORDER BY OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC;", nativeQuery = true)*/
	//New query
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "((entity.tradableStateId.tradeStateId = 'BUY' "
			+ "AND entity.previousCandleDeatilEntity.candleLowsDiff <= 7000 "
			+ "AND (entity.previousCandleDeatilEntity.candle2HighMinusClose > 1 "
				+ "AND ( (entity.previousCandleDeatilEntity.candle2HighMinusClose * 7) <= "
				+ "entity.previousCandleDeatilEntity.candle2CloseMinusLow  )   )    ) "
			+ "OR "
			+ "(entity.tradableStateId.tradeStateId = 'SELL' "
			+ "AND entity.previousCandleDeatilEntity.candleHighsDiff <= 7000 "
			+ "AND (entity.previousCandleDeatilEntity.candle2CloseMinusLow > 1 "
				+ "AND ( (entity.previousCandleDeatilEntity.candle2CloseMinusLow * 7) <= "
				+ "entity.previousCandleDeatilEntity.candle2HighMinusClose  )   )    ) ) "
			
			+"AND (entity.previousDayHistEntity.prevDayHrCrossInd = true) "
			+"AND (entity.tradableStateId.tradeStateId = entity.previousDayHistEntity.candleTypeTrendId.tradeStateId ) "
			
			+ "AND entity.tradedDateStamp = :tradedDateStamp ORDER BY entity.tradedAtDtTm desc")
	//List<StrategyOrbEntity> findAllByCustomRule1(@Param("tradedDateStamp") Date tradedDateStamp);
	List<StrategyOrbEntity> findAllByTradingRule7TimesStrenth(@Param("tradedDateStamp") Date tradedDateStamp);
	
	/*@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT where  (CURRENT_CNDL_OPEN > CURRENT_CNDL_AVG_LOW AND "
			+ "CURRENT_CNDL_OPEN < CURRENT_CNDL_AVG_HIH) AND ((TRADABLE_STATE = 'BUY' AND CURRENT_CNDL_OPEN > CURRENT_CNDL_AVG_CLOSE AND CURRENT_CNDL_OPEN > VWAP_VAL) "
			+ "OR (TRADABLE_STATE = 'SELL' AND CURRENT_CNDL_OPEN <CURRENT_CNDL_AVG_CLOSE AND CURRENT_CNDL_OPEN < VWAP_VAL)) AND GAP_UP_DOWN_MOVE_VAL < 500 AND "
			+ "BUY_SELL_DIFF_VAL < 1000 AND (TRADE_TYPE= 'FORWARD' OR TRADE_TYPE IS NULL) AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp "
			+ " AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber ORDER BY OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC;", nativeQuery = true)*/
	//New query
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
		+ " ( (entity.previousCandleAverageHistEntity.currentCandleOpen > entity.previousCandleAverageHistEntity.avgLow1min "
		+ "AND entity.previousCandleAverageHistEntity.currentCandleOpen < entity.previousCandleAverageHistEntity.avgHigh1min) "
		+ "AND (  (entity.tradableStateId.tradeStateId = 'BUY' "
		+ "AND (entity.previousCandleAverageHistEntity.currentCandleOpen > entity.previousCandleAverageHistEntity.avgClose1min "
		+ "AND entity.previousCandleAverageHistEntity.currentCandleOpen > entity.vwapAndVolumeInfoEntity.vwapValue)    ) "
		+ "OR "
		+ "(entity.tradableStateId.tradeStateId = 'SELL' "
		+ "AND (entity.previousCandleAverageHistEntity.currentCandleOpen < entity.previousCandleAverageHistEntity.avgClose1min "
		+ "AND entity.previousCandleAverageHistEntity.currentCandleOpen < entity.vwapAndVolumeInfoEntity.vwapValue)) ) ) "
				
		+"AND (entity.previousDayHistEntity.prevDayHrCrossInd = true)"
		+"AND (entity.tradableStateId.tradeStateId = entity.previousDayHistEntity.candleTypeTrendId.tradeStateId ) "
				
		+"AND (entity.gapUpDownMoveVal < 500 AND entity.buyerSellerDiffVal < 1000) "

		+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
		+ "ORDER BY entity.tradedAtDtTm desc")
	//List<StrategyOrbEntity> findAllByCustomRule2(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	List<StrategyOrbEntity> findAllByTradingRuleOpenBtwnAvgHiLoClsAndVwap(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	
	@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT where  (CURRENT_CNDL_OPEN > CURRENT_CNDL_AVG_LOW AND "
			+ "CURRENT_CNDL_OPEN < CURRENT_CNDL_AVG_HIH) AND ((TRADABLE_STATE = 'BUY' AND CURRENT_CNDL_OPEN > CURRENT_CNDL_AVG_CLOSE AND CURRENT_CNDL_OPEN > VWAP_VAL) "
			+ "OR (TRADABLE_STATE = 'SELL' AND CURRENT_CNDL_OPEN <CURRENT_CNDL_AVG_CLOSE AND CURRENT_CNDL_OPEN < VWAP_VAL)) AND GAP_UP_DOWN_MOVE_VAL < 500 AND "
			+ "BUY_SELL_DIFF_VAL < 1000 AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp "
			+ " AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber ORDER BY OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC;", nativeQuery = true)
	List<StrategyOrbEntity> findAllByCustomRule2IncludeReverse(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE T_OPEN_RANGE_BREAK_OUT set CURRENT_CNDL_AVG_HIH=:avgHigh, CURRENT_CNDL_AVG_LOW =:avgLow, "
			+ "CURRENT_CNDL_AVG_CLOSE =:avgClose, CURRENT_CNDL_OPEN =:currentCandleOpen, CURRENT_CNDL_AVG_HIGH_MINUS_CLS=:avgHighMinusClose, "
			+ "CURRENT_CNDL_AVG_CLS_MINUS_LOW=:avgCloseMinusLow, VWAP_VAL=:vwapVal where OPEN_RANGE_BREAK_OUT_ID =:itemId", nativeQuery = true)
	void updateTradeAVGHLC(@Param("avgHigh") Double avgHigh, @Param("avgLow") Double avgLow,
			@Param("avgClose") Double avgClose, @Param("currentCandleOpen") Double currentCandleOpen,
			@Param("avgHighMinusClose") Double avgHighMinusClose, @Param("avgCloseMinusLow") Double avgCloseMinusLow, @Param("vwapVal") Double vwapVal,
			@Param("itemId") Integer itemId);
	
	
	@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT where OPEN_RANGE_BREAK_OUT.TRADABLE_STATE IN('BUY', 'SELL') "
			+ "AND OPEN_RANGE_BREAK_OUT.TRADE_TYPE = 'REVERSE' AND ( (OPEN_RANGE_BREAK_OUT.GAP_UP_DOWN_MOVE_VAL > 1000 AND OPEN_RANGE_BREAK_OUT.TRADABLE_STATE = 'SELL') "
			+ "Or  (OPEN_RANGE_BREAK_OUT.GAP_UP_DOWN_MOVE_VAL >= 1500 AND OPEN_RANGE_BREAK_OUT.TRADABLE_STATE = 'BUY')) "
			+ "AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber "
			+ "ORDER BY OPEN_RANGE_BREAK_OUT.TRADABLE_STATE DESC, OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC", nativeQuery = true)
	List<StrategyOrbEntity> findAllByCustomReverseTradeRule(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	
	@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT where  "
			+ " OPEN_RANGE_BREAK_OUT.TRADE_TYPE = 'REVERSE' AND OPEN_RANGE_BREAK_OUT.GAP_UP_DOWN_MOVE_VAL > 1000 AND OPEN_RANGE_BREAK_OUT.TRADABLE_STATE = 'SELL' "
			+ "AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber "
			+ "ORDER BY OPEN_RANGE_BREAK_OUT.TRADABLE_STATE DESC, OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC", nativeQuery = true)
	List<StrategyOrbEntity> findAllByCustomReverseSellOnlyTradeRule(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	
	@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT where "
			+ " OPEN_RANGE_BREAK_OUT.TRADE_TYPE = 'REVERSE' AND OPEN_RANGE_BREAK_OUT.GAP_UP_DOWN_MOVE_VAL >= 1500 AND OPEN_RANGE_BREAK_OUT.TRADABLE_STATE = 'BUY' "
			+ "AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber "
			+ "ORDER BY OPEN_RANGE_BREAK_OUT.TRADABLE_STATE DESC, OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC", nativeQuery = true)
	List<StrategyOrbEntity> findAllByCustomReverseBuyOnlyTradeRule(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);

	// AND (CNDL_2_CLS_MINUS_LOW > (CNDL_2_HIG_MINUS_CLS * 4) AND TRADABLE_STATE = 'SELL' ) AND CNDL_NUM <=25

	@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT where OPEN_RANGE_BREAK_OUT.TRADABLE_STATE = 'SELL' "
			+ "AND OPEN_RANGE_BREAK_OUT.TRADE_TYPE = 'REVERSE' AND OPEN_RANGE_BREAK_OUT.GAP_UP_DOWN_MOVE_VAL > 1700 "
			+ "AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber "
			+ "ORDER BY OPEN_RANGE_BREAK_OUT.TRADABLE_STATE DESC, OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC", nativeQuery = true)
	List<StrategyOrbEntity> findAllByCustomReverseSellGt1700TradeRule(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);


	@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT where OPEN_RANGE_BREAK_OUT.TRADABLE_STATE = 'SELL' "
			+ "AND OPEN_RANGE_BREAK_OUT.TRADE_TYPE = 'REVERSE' AND (OPEN_RANGE_BREAK_OUT.CNDL_2_CLS_MINUS_LOW > (OPEN_RANGE_BREAK_OUT.CNDL_2_HIG_MINUS_CLS * 4)) "
			+ "AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber "
			+ "ORDER BY OPEN_RANGE_BREAK_OUT.TRADABLE_STATE DESC, OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC", nativeQuery = true)
	List<StrategyOrbEntity> findAllByCustomReverseSellWithStrengthTradeRule(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);

	
	
	//Phase 2 start - 03-11-2021 - March
	//
	/*@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT where ((TRADABLE_STATE = 'BUY' AND VOL_STRNTH_TREND_INFO LIKE '%VWAP STRONG %') "
			+ "OR (TRADABLE_STATE = 'SELL' AND VOL_STRNTH_TREND_INFO LIKE '%VWAP STRONG %')) AND GAP_UP_DOWN_MOVE_VAL < 1500 AND "
			+ "BUY_SELL_DIFF_VAL < 1000 AND (TRADE_TYPE= 'FORWARD' OR TRADE_TYPE IS NULL) AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp "
			+ " AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber ORDER BY OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC;", nativeQuery = true)*/
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "("
			+ "( (entity.tradableStateId.tradeStateId = 'BUY' "
				+ "AND ( (entity.vwapAndVolumeInfoEntity.vwapTradeStateId.volumeTrendId IN ('VBUY', 'SBUY')) "
				+ "OR (entity.vwapAndVolumeInfoEntity.volumeTradeStateId.volumeTrendId "
				+ "IN ('BUY3', 'BUY4', 'BUY5', 'BUY6', 'BUY7'))   )   )) "
			+ "OR ( (entity.tradableStateId.tradeStateId = 'SELL' "
				+ "AND  ( (entity.vwapAndVolumeInfoEntity.vwapTradeStateId.volumeTrendId IN ('VSEL', 'SSEL')) "
				+ "OR (entity.vwapAndVolumeInfoEntity.volumeTradeStateId.volumeTrendId "
				+ "IN ('SEL3', 'SEL4', 'SEL5', 'SEL6', 'SEL7'))   ) ) ) "
			+ ") "
			
			+"AND (entity.previousDayHistEntity.prevDayHrCrossInd = true) "
			+"AND (entity.tradableStateId.tradeStateId = entity.previousDayHistEntity.candleTypeTrendId.tradeStateId ) "
			
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByStrongVwapAndVolumeRule(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	//List<StrategyOrbEntity> findAllByCustomRule3AllVwapStrong(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	
	
	
	
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "("
			+ "(entity.tradableStateId.tradeStateId = 'BUY' "
				+ "AND (entity.vwapAndVolumeInfoEntity.volumeTradeStateId.volumeTrendId "
				+ "IN ('BUY', 'BUY3', 'BUY4', 'BUY5', 'BUY6', 'BUY7'))      ) "
			+ "OR (entity.tradableStateId.tradeStateId = 'SELL' "
				+ "AND (entity.vwapAndVolumeInfoEntity.volumeTradeStateId.volumeTrendId "
				+ "IN ('SELL', 'SEL3', 'SEL4', 'SEL5', 'SEL6', 'SEL7'))    ) "
			+ ") "
			
			+"AND (entity.previousDayHistEntity.prevDayHrCrossInd = true) "
			+"AND (entity.tradableStateId.tradeStateId = entity.previousDayHistEntity.candleTypeTrendId.tradeStateId ) "
			
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByStrongVolumePressureRuleMatch(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "("
			+ "(entity.vwapAndVolumeInfoEntity.volumeTradeStateId.volumeTrendId "
				//+ "IN ('BUY', 'BUY3', 'BUY4', 'BUY5', 'BUY6', 'BUY7')) "
				+ "IN ('BUY3', 'BUY4', 'BUY5', 'BUY6', 'BUY7')) "
			+ "OR (entity.vwapAndVolumeInfoEntity.volumeTradeStateId.volumeTrendId "
				//+ "IN ('SELL', 'SEL3', 'SEL4', 'SEL5', 'SEL6', 'SEL7')) "
				+ "IN ('SEL3', 'SEL4', 'SEL5', 'SEL6', 'SEL7')) "
			+ ") "
			
			+"AND (entity.previousDayHistEntity.prevDayHrCrossInd = true) "
			+"AND (entity.tradableStateId.tradeStateId = entity.previousDayHistEntity.candleTypeTrendId.tradeStateId ) "
			
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByStrongVolumePressureRule(@Param("tradedDateStamp") Date tradedDateStamp, @Param("candleNumber") Integer candleNumber);
	
	//Phase 2 end
	
	
	
	//Phase 3
	 /*@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT "
		 		+ "where (OPEN_RANGE_BREAK_OUT.SYMBOL_ID LIKE '%NIFTY 50%' "
		 		+ "OR OPEN_RANGE_BREAK_OUT.SYMBOL_ID LIKE '%BANKNIFTY%' "
		 		+ "OR OPEN_RANGE_BREAK_OUT.SYMBOL_ID LIKE '%FINNIFTY%') "
		 		+ "AND OPEN_RANGE_BREAK_OUT.DT_TM_STAMP = :tradedDateStamp "
		 		+ "AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber "
		 		+ "ORDER BY OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM DESC;", nativeQuery = true)*/
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(entity.symbolId LIKE '%NIFTY 50%' OR entity.symbolId LIKE '%BANKNIFTY%' "
			+ "OR entity.symbolId LIKE '%FINNIFTY%') "
			+ " AND entity.tradedDateStamp = :tradedDateStamp "
			+ " AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllNIFTYorBANKNIFTY(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);

	
	
	/*@Query(value = "select OPEN_RANGE_BREAK_OUT.* from T_OPEN_RANGE_BREAK_OUT OPEN_RANGE_BREAK_OUT "
			+ "where OPEN_RANGE_BREAK_OUT.OPEN_RANGE_BREAK_OUT_ID IN "
			+ "(select OPEN_RANGE_BREAK_OUT_ID from  T_ORB_TRADE_RULE WHERE "
			+ "TRADE_ON_SMA_VWAP_OPEN_BETWEEN_AVG_HI_LO_LVL2_IND = true) "
			+ "AND OPEN_RANGE_BREAK_OUT.DT_STAMP = :tradedDateStamp "
			+ "AND OPEN_RANGE_BREAK_OUT.CNDL_NUM <= :candleNumber "
			+ "ORDER BY OPEN_RANGE_BREAK_OUT.TRADED_AT_DT_TM desc", nativeQuery = true)*/
	
	
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity INNER JOIN "
			+ "StrategyOrbTradingRuleEntity tradingRuleEntity ON "
			+ "entity.itemId = tradingRuleEntity.strategyOrbEntity.itemId WHERE "
			+" (tradingRuleEntity.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleInd = true) "
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllBySmaVwapLevel2Rule(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
	
	
	/**
	 *   select count(*), SUM(BR.PROF_LOS_AMT_VAL) from T_OPEN_RANGE_BREAK_OUT BR 
	 *   INNER JOIN T_ORB_PREV_CNDL_AVG_HIST HIST ON BR.OPEN_RANGE_BREAK_OUT_ID = HIST.OPEN_RANGE_BREAK_OUT_ID 
	 *   AND (HIST.CURRENT_CNDL_OPEN >  HIST.CNDL_AVG_LOW_1_MIN AND HIST.CURRENT_CNDL_OPEN < HIST.CNDL_AVG_HIH_1_MIN) 
	 *   AND ((BR.TRADABLE_STATE_ID = 'BUY' AND HIST.CURRENT_CNDL_OPEN > HIST.CNDL_AVG_CLOSE_1_MIN 
	 *   AND HIST.CURRENT_CNDL_OPEN > BR.VWAP_VAL AND ((( (BR.BUY_AT_VAL-BR.VWAP_VAL) * BR.LOT_SIZE) > 0) 
	 *   AND (( (BR.BUY_AT_VAL-BR.VWAP_VAL) * BR.LOT_SIZE) < 7500)) ) OR (BR.TRADABLE_STATE_ID = 'SELL' 
	 *   AND HIST.CURRENT_CNDL_OPEN < HIST.CNDL_AVG_CLOSE_1_MIN AND HIST.CURRENT_CNDL_OPEN < BR.VWAP_VAL 
	 *   AND ((( (BR.VWAP_VAL - BR.SELL_AT_VAL) * BR.LOT_SIZE) > 0)  
	 *   AND (( (BR.VWAP_VAL - BR.SELL_AT_VAL) * BR.LOT_SIZE) < 7500)) )) 
	 *   AND BR.DT_STAMP > CURRENT_DATE - 30 AND BR.BUY_SELL_DIFF_VAL <= 300 
	 *   AND BR.BUY_SELL_DIFF_VAL_2 <= 500 AND  BR.GAP_UP_DOWN_MOVE_VAL <=2500;
	 */
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(entity.previousCandleAverageHistEntity.currentCandleOpen > "
			+ "entity.previousCandleAverageHistEntity.avgLow1min "
			+ "AND entity.previousCandleAverageHistEntity.currentCandleOpen < "
			+ "entity.previousCandleAverageHistEntity.avgHigh1min) "
			+ "AND ( (entity.tradableStateId = 'BUY' "
						+ "AND entity.previousCandleAverageHistEntity.currentCandleOpen > " 
						+ "entity.previousCandleAverageHistEntity.avgClose1min "
						+ "AND entity.previousCandleAverageHistEntity.currentCandleOpen > "
						+ "entity.vwapAndVolumeInfoEntity.vwapValue "
						+ "AND (((entity.buyAtVal - entity.vwapAndVolumeInfoEntity.vwapValue) * (entity.lotSize)) > 0) "
						+ "AND (((entity.buyAtVal - entity.vwapAndVolumeInfoEntity.vwapValue) * (entity.lotSize)) < 7500) ) "
						
				+ "OR (entity.tradableStateId = 'SELL' "
						+ "AND entity.previousCandleAverageHistEntity.currentCandleOpen < " 
						+ "entity.previousCandleAverageHistEntity.avgClose1min "
						+ "AND entity.previousCandleAverageHistEntity.currentCandleOpen < "
						+ "entity.vwapAndVolumeInfoEntity.vwapValue "
						+ "AND (((entity.vwapAndVolumeInfoEntity.vwapValue - entity.sellAtVal) * (entity.lotSize)) > 0) "
						+ "AND (((entity.vwapAndVolumeInfoEntity.vwapValue - entity.sellAtVal) * (entity.lotSize)) < 7500) ) ) "
			+ "AND entity.buyerSellerDiffVal <= 300 AND entity.buyerSellerDiffVal2 <= 500 AND entity.gapUpDownMoveVal <= 2500 "
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllBySmaVwapLevel2PlusRule(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
	
	//Phase 3 end
	
	
	//phase 4 start 04-23-2021 start
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "entity.buyerSellerDiffVal < 200  and entity.buyerSellerDiffVal2 < 250 and "
			+ "entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByInitProfitableProdRule(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
	
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "entity.buyerSellerDiffVal < 200  AND entity.buyerSellerDiffVal2 < 250 AND "
			+ "entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber AND "
			
			+ "entity.tradedAtDtTm <= :tradedCloseTimeBeffore AND " 
			+ "entity.tradableStateId in ('BUY', 'SELL') "
			
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByInitProfitableFilterProdRule(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("tradedCloseTimeBeffore") Date tradedCloseTimeBeffore, @Param("candleNumber") Integer candleNumber);
	
	
	@Query("SELECT ((SUM(entity.profitLossAmtVal)) + (count(*) * 150)) from StrategyOrbEntity entity "
			+ "where entity.tradedAtDtTm >= (CURRENT_DATE -7/24) "
			+ "AND entity.tradingRuleEntity.tradePlacedRuleInd = true")
	Double checkTodayTradingNotForceClosedByCrossMaxLossLimit();
	//phase 4 end 04-23-2021
	
	
	// Phase 4 :: 05-09-2021 start - afterSomeSuccess
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(entity.tradingRuleEntity.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd = true "
			+ "OR entity.tradingRuleEntity.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd = true ) "
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllBySmaVwapRuleTrades(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
	// Phase 4 :: 05-09-2021 end - afterSomeSuccess
	
	// Phase 5 :: 05-15-2021 start - afterSomeSuccess
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(entity.tradingRuleEntity.tradingRuleForwardEngulfingInd = true ) "
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByForwardEngulfingRuleTrades(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
	// Phase 5 :: 05-15-2021 end - afterSomeSuccess
	
	
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "entity.vwapAndVolumeInfoEntity.volumeTradeStateId "
			+ "IN ('BUY5','BUY6','BUY7','SEL5','SEL6','SEL7') "
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByVolumeStrengthTrades(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
	
	
	/*@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(	(entity.strategyOrbStochasticEntity.min60StochasticValK1 <=25 "
			+ "AND entity.strategyOrbStochasticEntity.min15StochasticValK1 <=25 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValK1 <=25 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValD3 <=25 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValK1 >= "
			+ "entity.strategyOrbStochasticEntity.min5StochasticValD3 AND entity.tradableStateId = 'BUY') "
			+"OR 	(entity.strategyOrbStochasticEntity.min60StochasticValK1 >=75 "
			+ "AND entity.strategyOrbStochasticEntity.min15StochasticValK1 >=75 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValK1 >=75 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValD3 >=75 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValD3 <= "
			+ "entity.strategyOrbStochasticEntity.min5StochasticValK1 AND entity.tradableStateId = 'SELL') "
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")*/
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(	(entity.strategyOrbStochasticEntity.min60StochasticValK1 <=25 "
			+ "AND entity.strategyOrbStochasticEntity.min15StochasticValK1 <=25 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValK1 <=25 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValD3 <=25 AND entity.tradableStateId = 'BUY') "
			+"OR 	(entity.strategyOrbStochasticEntity.min60StochasticValK1 >=75 "
			+ "AND entity.strategyOrbStochasticEntity.min15StochasticValK1 >=75 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValK1 >=75 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValD3 >=75 AND entity.tradableStateId = 'SELL')  )"
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByStochasticVolumeStrengthTrades(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
	
	
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(	(entity.strategyOrbStochasticEntity.min60StochasticValK1 <=25 "
			+ "AND entity.strategyOrbStochasticEntity.min15StochasticValK1 <=25 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValK1 <=25 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValD3 <=25 AND entity.tradableStateId = 'BUY' "
			+ "AND (entity.strategyOrbStochasticEntity.min60StochasticTrend = 'VS_BUY' OR "
			+ "		entity.strategyOrbStochasticEntity.min15StochasticTrend = 'VS_BUY' OR"
			+ "		entity.strategyOrbStochasticEntity.min5StochasticTrend = 'VS_BUY') ) "
			+"OR 	(entity.strategyOrbStochasticEntity.min60StochasticValK1 >=75 "
			+ "AND entity.strategyOrbStochasticEntity.min15StochasticValK1 >=75 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValK1 >=75 "
			+ "AND entity.strategyOrbStochasticEntity.min5StochasticValD3 >=75 AND entity.tradableStateId = 'SELL' "
			+ "AND (entity.strategyOrbStochasticEntity.min60StochasticTrend = 'VS_SELL' OR "
			+ "		entity.strategyOrbStochasticEntity.min15StochasticTrend = 'VS_SELL' OR"
			+ "		entity.strategyOrbStochasticEntity.min5StochasticTrend = 'VS_SELL') )  )"
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByStrongStochasticVolumeStrengthTrades(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
	
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
	+ "(	(entity.strategyOrbStochasticEntity.min5StochasticValK1 < 40 "
	+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin60 IN ('BUY', 'HVSB', 'VVSB') "
	+ "AND (entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin15 NOT IN ('SELL', 'HVSS', 'VVSS') "
		+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin5 NOT IN ('SELL', 'HVSS', 'VVSS') ) "
	+ "AND (entity.tradableStateId = 'BUY' "
				+ "AND (((entity.buyAtVal - entity.vwapValue) * (entity.lotSize)) > 0) "
				+ "AND (((entity.buyAtVal - entity.vwapValue) * (entity.lotSize)) < 15000) ) ) "
	+"OR (entity.strategyOrbStochasticEntity.min5StochasticValK1 > 60 "
	+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin60 IN ('SELL', 'HVSS', 'VVSS') "
	+ "AND (entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin15 NOT IN ('BUY', 'HVSB', 'VVSB') "
		+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin5 NOT IN ('BUY', 'HVSB', 'VVSB') ) "
	+ "AND (entity.tradableStateId = 'SELL' "
				+ "AND (((entity.vwapValue - entity.sellAtVal) * (entity.lotSize)) > 0) "
				+ "AND (((entity.vwapValue - entity.sellAtVal) * (entity.lotSize)) < 15000) ) )  ) "
	+ "AND entity.buyerSellerDiffVal <= 500 AND entity.buyerSellerDiffVal2 <= 750 AND entity.gapUpDownMoveVal <= 500 "			
	+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
	+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByStochasticRule1Trades(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
	
	
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(	( (entity.strategyOrbStochasticEntity.min5StochasticValK1 < 40 OR "
			+ "			entity.strategyOrbStochasticEntity.min15StochasticValK1 < 40 OR "
			+ "			entity.strategyOrbStochasticEntity.min60StochasticValK1 < 40) "
			+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin60 IN ('BUY', 'HVSB', 'VVSB') "
			+ "AND (entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin15 NOT IN ('SELL', 'HVSS', 'VVSS') "
				+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin5 NOT IN ('SELL', 'HVSS', 'VVSS') ) "
			+ "AND (entity.tradableStateId = 'BUY' "
						+ "AND (((entity.buyAtVal - entity.vwapValue) * (entity.lotSize)) > 0) "
						+ "AND (((entity.buyAtVal - entity.vwapValue) * (entity.lotSize)) < 15000) ) ) "
			+"OR ( (entity.strategyOrbStochasticEntity.min5StochasticValK1 > 60 OR "
			+ "		entity.strategyOrbStochasticEntity.min15StochasticValK1 > 60 OR "
			+ "		entity.strategyOrbStochasticEntity.min60StochasticValK1 > 60 ) "
			+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin60 IN ('SELL', 'HVSS', 'VVSS') "
			+ "AND (entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin15 NOT IN ('BUY', 'HVSB', 'VVSB') "
				+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin5 NOT IN ('BUY', 'HVSB', 'VVSB') ) "
			+ "AND (entity.tradableStateId = 'SELL' "
						+ "AND (((entity.vwapValue - entity.sellAtVal) * (entity.lotSize)) > 0) "
						+ "AND (((entity.vwapValue - entity.sellAtVal) * (entity.lotSize)) < 15000) ) )  ) "
			+ "AND entity.buyerSellerDiffVal <= 500 AND entity.buyerSellerDiffVal2 <= 750 AND entity.gapUpDownMoveVal <= 500 "			
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
			List<StrategyOrbEntity> findAllByStochasticRule2Trades(@Param("tradedDateStamp") Date tradedDateStamp,
					@Param("candleNumber") Integer candleNumber);
	
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(	( (entity.strategyOrbStochasticEntity.min5StochasticValK1 < 20 OR "
			+ "			entity.strategyOrbStochasticEntity.min15StochasticValK1 < 20 OR "
			+ "			entity.strategyOrbStochasticEntity.min60StochasticValK1 < 20) "
			+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin60 IN ('BUY', 'HVSB', 'VVSB') "
			+ "AND (entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin15 NOT IN ('SELL', 'HVSS', 'VVSS') "
				+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin5 NOT IN ('SELL', 'HVSS', 'VVSS') ) "
			+ "AND (entity.tradableStateId = 'BUY' "
						+ "AND (((entity.buyAtVal - entity.vwapValue) * (entity.lotSize)) > 0) "
						+ "AND (((entity.buyAtVal - entity.vwapValue) * (entity.lotSize)) < 15000) ) ) "
			+"OR ( (entity.strategyOrbStochasticEntity.min5StochasticValK1 > 80 OR "
			+ "		entity.strategyOrbStochasticEntity.min15StochasticValK1 > 80 OR "
			+ "		entity.strategyOrbStochasticEntity.min60StochasticValK1 > 80 ) "
			+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin60 IN ('SELL', 'HVSS', 'VVSS') "
			+ "AND (entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin15 NOT IN ('BUY', 'HVSB', 'VVSB') "
				+ "AND entity.strategyOrbHeikenAshiEntity.heikinAshiTrendIdMin5 NOT IN ('BUY', 'HVSB', 'VVSB') ) "
			+ "AND (entity.tradableStateId = 'SELL' "
						+ "AND (((entity.vwapValue - entity.sellAtVal) * (entity.lotSize)) > 0) "
						+ "AND (((entity.vwapValue - entity.sellAtVal) * (entity.lotSize)) < 15000) ) )  ) "
			+ "AND entity.buyerSellerDiffVal <= 500 AND entity.buyerSellerDiffVal2 <= 750 AND entity.gapUpDownMoveVal <= 500 "			
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
			List<StrategyOrbEntity> findAllByStochasticStrongRule3Trades(@Param("tradedDateStamp") Date tradedDateStamp,
					@Param("candleNumber") Integer candleNumber);
	
	/**
	 *  select count(*), SUM(BR.PROF_LOS_AMT_VAL) from T_OPEN_RANGE_BREAK_OUT BR INNER JOIN T_ORB_STOCHASTIC_DTL STOC 
	 *  ON BR.OPEN_RANGE_BREAK_OUT_ID = STOC.OPEN_RANGE_BREAK_OUT_ID 
	 *  AND ((BR.TRADABLE_STATE_ID = 'BUY' AND (STOC.STOCH_MIN_5_VAL_K1 <40 OR STOC.STOCH_MIN_15_VAL_K1 <40 
	 *  OR STOC.STOCH_MIN_60_VAL_K1 <40) )  OR (BR.TRADABLE_STATE_ID = 'SELL' AND (STOC.STOCH_MIN_5_VAL_K1 >60 OR STOC.STOCH_MIN_15_VAL_K1 >60 
	 *  OR STOC.STOCH_MIN_60_VAL_K1 > 60)   )  ) AND BR.DT_STAMP = CURRENT_DATE - 2 
	 *  AND BR.BUY_SELL_DIFF_VAL <= 500 AND BR.BUY_SELL_DIFF_VAL_2 <= 700 AND  BR.GAP_UP_DOWN_MOVE_VAL <=2500;
	 */
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "( (entity.tradableStateId = 'BUY' AND (entity.strategyOrbStochasticEntity.min5StochasticValK1 < 40 "
					+ "OR entity.strategyOrbStochasticEntity.min15StochasticValK1 < 40 "
					+ "OR entity.strategyOrbStochasticEntity.min60StochasticValK1 < 40) ) "
			+ "OR (entity.tradableStateId = 'SELL' AND (entity.strategyOrbStochasticEntity.min5StochasticValK1 > 60 "
					+ "OR entity.strategyOrbStochasticEntity.min15StochasticValK1 > 60  "
					+ "OR entity.strategyOrbStochasticEntity.min60StochasticValK1 > 60) )  ) "
			+ "AND entity.buyerSellerDiffVal <= 500 AND entity.buyerSellerDiffVal2 <= 750 AND entity.gapUpDownMoveVal <= 2500 "			
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByStochasticBasicRuleTrades(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
	
	@Query(value = "SELECT entity FROM StrategyOrbEntity entity WHERE "
			+ "(entity.tradableStateId = 'BUY' OR entity.tradableStateId = 'SELL') "
			+ "AND entity.profitLossAmtVal > 0 "
			+ "AND entity.tradedDateStamp = :tradedDateStamp AND entity.candleNumber <= :candleNumber "
			+ "ORDER BY entity.tradedAtDtTm desc")
	List<StrategyOrbEntity> findAllByProfiitTrades(@Param("tradedDateStamp") Date tradedDateStamp,
			@Param("candleNumber") Integer candleNumber);
}
