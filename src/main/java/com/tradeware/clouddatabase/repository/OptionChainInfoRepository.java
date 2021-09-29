package com.tradeware.clouddatabase.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tradeware.clouddatabase.entity.OptionChainInfoEntity;
import com.tradeware.clouddatabase.entity.StrategyOrbMonthlyReportEntity;

@Repository
public interface OptionChainInfoRepository extends CrudRepository<OptionChainInfoEntity, Integer> {

	//List<OptionChainInfoEntity> findAllBySymbolIdAndDateStampOrderByDateTimeStampAsc(String symbolId, Date dateStamp);
	List<OptionChainInfoEntity> findAllBySymbolIdAndDateStampGreaterThanEqualOrderByDateTimeStampDesc(String symbolId, Date dateStamp);
	
	List<OptionChainInfoEntity> findAllBySymbolIdAndDateStampGreaterThanEqualAndDateStampLessThanOrderByDateTimeStampDesc(String symbolId, Date dateStamp, Date dateStampMax);

	@SuppressWarnings("unchecked")
	OptionChainInfoEntity save(OptionChainInfoEntity symbol);

	/// @Override
	// List<OptionChainInfoEntity> saveAll(Iterable<OptionChainInfoEntity> symbol);

	List<OptionChainInfoEntity> findTopByDateStampOrderByDateTimeStampDescStrongOrderDesc(Date dateStamp);
	
	// AndTimeFrameNumberIn//, List<Integer> timeFrameNumberList
	List<OptionChainInfoEntity> findAllByCandleNumberAndDateStampOrderByIndexIndDescOiTrendDescStrongOrderDesc(Integer candleNumber,
			Date dateStamp);
	
	@Query(value = "SELECT OPTION_CHAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN INNER JOIN "
			+ "(SELECT DISTINCT SYMBOL_ID, MAX(DT_TM_STAMP) AS MAX_DT_TM_STAMP "
			+ "FROM T_OPTION_CHAIN WHERE DT_TM_STAMP > CURRENT_DATE - 1 GROUP BY SYMBOL_ID) "
			+ "GROUPED_OPTION_CHAIN ON OPTION_CHAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID "
			+ "AND OPTION_CHAIN.DT_TM_STAMP = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP "
			+ "AND OPTION_CHAIN.DT_TM_STAMP > CURRENT_DATE - 1 "
			+ "ORDER BY OPTION_CHAIN.INDEX_IND DESC, OPTION_CHAIN.OI_TREND DESC, "
			+ "OPTION_CHAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)
	/*@Query(value = "SELECT OPTION_CHAIN FROM OptionChainInfoEntity OPTION_CHAIN INNER JOIN (SELECT DISTINCT symbolId, MAX(dateTimeStamp) AS MAX_DT_TM_STAMP "
			+ "FROM OptionChainInfoEntity GROUP BY symbolId) GROUPED_OPTION_CHAIN ON OPTION_CHAIN.symbolId = GROUPED_OPTION_CHAIN.symbolId "
			+ "AND OPTION_CHAIN.dateTimeStamp = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP  ORDER BY OPTION_CHAIN.indexInd DESC, OPTION_CHAIN.oiTrend DESC, "
			+ "OPTION_CHAIN.strongOrder DESC")*/
	List<OptionChainInfoEntity> findAllByLatestOptinChainData(Date dateStamp);
	
	
	@Query(value = "SELECT OPTION_CHAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN INNER JOIN "
			+ "(SELECT DISTINCT SYMBOL_ID, MAX(DT_TM_STAMP) AS MAX_DT_TM_STAMP "
			+ "FROM T_OPTION_CHAIN GROUP BY SYMBOL_ID) GROUPED_OPTION_CHAIN ON "
			+ "OPTION_CHAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID "
			+ "AND OPTION_CHAIN.SYMBOL_ID IN ('BANKNIFTY', 'NIFTY', 'FINNIFTY', 'HDFCBANK', 'ICICIBANK', 'SBIN', 'KOTAKBANK', "
			+ "'AXISBANK', 'INDUSINDBK', 'AUBANK', 'BANDHANBNK') "
			+ "AND OPTION_CHAIN.DT_TM_STAMP = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP "
			+ "AND OPTION_CHAIN.DT_TM_STAMP > CURRENT_DATE - 1 "
			+ "ORDER BY OPTION_CHAIN.INDEX_IND DESC, OPTION_CHAIN.OI_TREND DESC, "
			+ "OPTION_CHAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)
	/*@Query(value = "SELECT OPTION_CHAIN FROM OptionChainInfoEntity OPTION_CHAIN INNER JOIN (SELECT DISTINCT symbolId, MAX(dateTimeStamp) AS MAX_DT_TM_STAMP "
			+ "FROM OptionChainInfoEntity GROUP BY symbolId) GROUPED_OPTION_CHAIN ON OPTION_CHAIN.symbolId = GROUPED_OPTION_CHAIN.symbolId "
			+ "AND OPTION_CHAIN.dateTimeStamp = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP  ORDER BY OPTION_CHAIN.indexInd DESC, OPTION_CHAIN.oiTrend DESC, "
			+ "OPTION_CHAIN.strongOrder DESC")*/
	List<OptionChainInfoEntity> findAllByLatestOptinChainDataForBankNifty(Date dateStamp);
	
	@Query(value = "SELECT OPTION_CHAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN INNER JOIN (SELECT DISTINCT SYMBOL_ID, MAX(DT_TM_STAMP) AS MAX_DT_TM_STAMP "
			+ "FROM T_OPTION_CHAIN WHERE DT_TM_STAMP BETWEEN :startDate AND :endDate GROUP BY SYMBOL_ID) GROUPED_OPTION_CHAIN ON OPTION_CHAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID "
			+ "AND OPTION_CHAIN.DT_TM_STAMP = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP AND OPTION_CHAIN.DT_TM_STAMP BETWEEN :startDate AND :endDate "
			+ "ORDER BY OPTION_CHAIN.INDEX_IND DESC, OPTION_CHAIN.OI_TREND DESC, "
			+ "OPTION_CHAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)
	/*@Query(value = "SELECT OPTION_CHAIN FROM OptionChainInfoEntity OPTION_CHAIN INNER JOIN (SELECT DISTINCT symbolId, MAX(dateTimeStamp) AS MAX_DT_TM_STAMP "
			+ "FROM OptionChainInfoEntity GROUP BY symbolId) GROUPED_OPTION_CHAIN ON OPTION_CHAIN.symbolId = GROUPED_OPTION_CHAIN.symbolId "
			+ "AND OPTION_CHAIN.dateTimeStamp = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP  ORDER BY OPTION_CHAIN.indexInd DESC, OPTION_CHAIN.oiTrend DESC, "
			+ "OPTION_CHAIN.strongOrder DESC")*/
	List<OptionChainInfoEntity> findAllOptinChainDataByGivenDate(
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	
	@Query(value = "SELECT OPTION_CHAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN INNER JOIN (SELECT DISTINCT SYMBOL_ID, MAX(DT_TM_STAMP) AS MAX_DT_TM_STAMP "
			+ "FROM T_OPTION_CHAIN GROUP BY SYMBOL_ID) GROUPED_OPTION_CHAIN ON OPTION_CHAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID "
			+ "AND OPTION_CHAIN.DT_TM_STAMP = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP AND OPTION_CHAIN.DT_TM_STAMP > CURRENT_DATE - 1 "
			+ "AND OPTION_CHAIN.SYMBOL_ID = :symbolId ORDER BY OPTION_CHAIN.INDEX_IND DESC, OPTION_CHAIN.OI_TREND DESC, "
			+ "OPTION_CHAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)
	/*@Query(value = "SELECT OPTION_CHAIN.* FROM OptionChainInfoEntity OPTION_CHAIN INNER JOIN (SELECT DISTINCT symbolId, MAX(dateTimeStamp) AS MAX_DT_TM_STAMP "
			+ "FROM OptionChainInfoEntity GROUP BY symbolId) GROUPED_OPTION_CHAIN ON OPTION_CHAIN.symbolId = GROUPED_OPTION_CHAIN.symbolId "
			+ "AND OPTION_CHAIN.dateTimeStamp = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP AND OPTION_CHAIN.dateTimeStamp > CURRENT_DATE - 1 "
			//+ "AND OPTION_CHAIN.dateTimeStamp = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP AND OPTION_CHAIN.dateTimeStamp > :dateStamp - 1 "
			+ "AND OPTION_CHAIN.symbolId = :symbolId ORDER BY OPTION_CHAIN.indexInd DESC, OPTION_CHAIN.oiTrend DESC, "
			+ "OPTION_CHAIN.strongOrder DESC", nativeQuery = true)*/
	/*@Query(value = "SELECT OPTION_CHAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN INNER JOIN (SELECT DISTINCT SYMBOL_ID, MAX(DT_TM_STAMP) AS MAX_DT_TM_STAMP "
			+ "FROM T_OPTION_CHAIN GROUP BY SYMBOL_ID) GROUPED_OPTION_CHAIN ON OPTION_CHAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID "
			+ "AND OPTION_CHAIN.DT_TM_STAMP = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP AND OPTION_CHAIN.DT_TM_STAMP > CURRENT_DATE - 1 "
			+ "AND OPTION_CHAIN.SYMBOL_ID = :symbolId ORDER BY OPTION_CHAIN.INDEX_IND DESC, OPTION_CHAIN.OI_TREND DESC, "
			+ "OPTION_CHAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)*/
	/*@Query(value = "SELECT OPTION_CHAIN FROM OptionChainInfoEntity OPTION_CHAIN INNER JOIN (SELECT DISTINCT symbolId, MAX(dateTimeStamp) AS MAX_DT_TM_STAMP "
			+ "FROM OptionChainInfoEntity GROUP BY symbolId) GROUPED_OPTION_CHAIN ON OPTION_CHAIN.symbolId = GROUPED_OPTION_CHAIN.symbolId "
			+ "AND OPTION_CHAIN.dateTimeStamp = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP  ORDER BY OPTION_CHAIN.indexInd DESC, OPTION_CHAIN.oiTrend DESC, "
			+ "OPTION_CHAIN.strongOrder DESC")*/
	//OptionChainInfoEntity findSymbolByLatestOptinChainData( @Param("dateStamp") Date dateStamp, @Param("symbolId") String symbolId);
	OptionChainInfoEntity findSymbolByLatestOptinChainData(@Param("symbolId") String symbolId);
	
	/*@Query(value = "SELECT OPTION_CHAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN INNER JOIN (SELECT DISTINCT SYMBOL_ID, MAX(DT_TM_STAMP) AS MAX_DT_TM_STAMP "
			+ "FROM T_OPTION_CHAIN WHERE BEST_TRADE_IND = true GROUP BY SYMBOL_ID) GROUPED_OPTION_CHAIN ON OPTION_CHAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID "
			+ "AND OPTION_CHAIN.DT_TM_STAMP = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP ORDER BY OPTION_CHAIN.INDEX_IND DESC, "
			+ "OPTION_CHAIN.OI_TREND DESC, OPTION_CHAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)*/
	/*@Query(value = "SELECT OPTION_CHAIN FROM OptionChainInfoEntity OPTION_CHAIN INNER JOIN (SELECT DISTINCT symbolId, MAX(dateTimeStamp) AS MAX_DT_TM_STAMP "
			+ "FROM OptionChainInfoEntity GROUP BY symbolId) GROUPED_OPTION_CHAIN ON OPTION_CHAIN.symbolId = GROUPED_OPTION_CHAIN.symbolId "
			+ "AND OPTION_CHAIN.dateTimeStamp = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP  ORDER BY OPTION_CHAIN.indexInd DESC, OPTION_CHAIN.oiTrend DESC, "
			+ "OPTION_CHAIN.strongOrder DESC")*/
	
	@Query(value = "SELECT OPTION_CHAIN_MAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN_MAIN INNER JOIN (SELECT DISTINCT OPTION_CHAIN.SYMBOL_ID AS SYMBOL_ID_2, "
			+ "MAX(OPTION_CHAIN.DT_TM_STAMP) AS MAX_DT_TM_STAMP FROM T_OPTION_CHAIN OPTION_CHAIN INNER JOIN (SELECT DISTINCT SYMBOL_ID AS SYMBOL_ID_3 "
			+ "FROM T_OPTION_CHAIN  WHERE PARENT_RECORD_IND = true AND BEST_TRADE_IND = true AND DT_STAMP = CURRENT_DATE GROUP "
			+ "BY SYMBOL_ID) DOUBLE_GROUPED_OPTION_CHAIN ON OPTION_CHAIN.SYMBOL_ID = DOUBLE_GROUPED_OPTION_CHAIN.SYMBOL_ID_3 GROUP BY SYMBOL_ID_2) "
			+ "GROUPED_OPTION_CHAIN ON OPTION_CHAIN_MAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID_2 AND OPTION_CHAIN_MAIN.DT_TM_STAMP = "
			+ "GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP ORDER BY OPTION_CHAIN_MAIN.INDEX_IND DESC, OPTION_CHAIN_MAIN.OI_TREND DESC, "
			+ "OPTION_CHAIN_MAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)
	/*@Query(value = "SELECT OPTION_CHAIN_MAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN_MAIN INNER JOIN (SELECT DISTINCT OPTION_CHAIN.SYMBOL_ID AS SYMBOL_ID_2, "
			+ "MAX(OPTION_CHAIN.DT_TM_STAMP) AS MAX_DT_TM_STAMP FROM T_OPTION_CHAIN OPTION_CHAIN WHERE OPTION_CHAIN.DT_STAMP = CURRENT_DATE  GROUP BY SYMBOL_ID) "
			+ "GROUPED_OPTION_CHAIN ON OPTION_CHAIN_MAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID_2 "
			+ "AND OPTION_CHAIN_MAIN.DT_TM_STAMP = GROUPED_OPTION_CHAIN.MAX_DT_TM_STAMP AND OPTION_CHAIN_MAIN.PARENT_RECORD_IND = true "
			+ "AND OPTION_CHAIN_MAIN.BEST_TRADE_IND = true AND OPTION_CHAIN_MAIN.DT_STAMP = CURRENT_DATE ORDER BY OPTION_CHAIN_MAIN.INDEX_IND DESC, "
			+ "OPTION_CHAIN_MAIN.OI_TREND DESC, OPTION_CHAIN_MAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)*/
	List<OptionChainInfoEntity> findAllBestTradeOptinChainData(Date dateStamp);
	
	//
	OptionChainInfoEntity findBySymbolIdAndDateStampAndParentRecordIndTrue(String symbolId, Date dateStamp);
	
	@Query(value = "SELECT OPTION_CHAIN_MAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN_MAIN INNER JOIN (SELECT DISTINCT OPTION_CHAIN.SYMBOL_ID AS SYMBOL_ID_2, "
			+ "MIN(OPTION_CHAIN.DT_TM_STAMP) AS MIN_DT_TM_STAMP FROM T_OPTION_CHAIN OPTION_CHAIN WHERE OPTION_CHAIN.DT_STAMP = CURRENT_DATE  GROUP BY SYMBOL_ID) "
			+ "GROUPED_OPTION_CHAIN ON OPTION_CHAIN_MAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID_2 AND OPTION_CHAIN_MAIN.DT_TM_STAMP = "
			+ "GROUPED_OPTION_CHAIN.MIN_DT_TM_STAMP AND OPTION_CHAIN_MAIN.SYMBOL_ID =:symbolId ORDER BY OPTION_CHAIN_MAIN.INDEX_IND DESC, "
			+ "OPTION_CHAIN_MAIN.OI_TREND DESC, OPTION_CHAIN_MAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)
	OptionChainInfoEntity findBySymbolIdAndDateStampAndParentRecordIndTrue(@Param("symbolId") String symbolId);
	@Transactional
	@Modifying
	@Query(value = "UPDATE T_OPTION_CHAIN set PARENT_RECORD_IND = true, BEST_TRADE_IND=:bestTradeInd, LTP_ON_DECISION =:ltpOnDecision, "
			+ "BEST_ENTRY =:bestEntry, CHANGE_PERCENTAGE=:changePercentage, GO_TRADE_ENTRY =:goForTrade "
			+ "where OPTION_CHAIN_ID =:optionChainId", nativeQuery = true)
			//+ "where SYMBOL_ID =:symbolId AND PARENT_RECORD_IND = true", nativeQuery = true)
	void updateBestTrade(@Param("bestTradeInd") Boolean bestTradeInd, @Param("ltpOnDecision") Double ltpOnDecision,
			@Param("bestEntry") Double bestEntry, @Param("changePercentage") Double changePercentage,
			@Param("goForTrade") String goForTrade, @Param("optionChainId") Integer optionChainId);
			//@Param("goForTrade") String goForTrade, @Param("symbolId") String symbolId);
	
	
	//Attention Calls
	@Query(value = "SELECT OPTION_CHAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN INNER JOIN (SELECT DISTINCT SYMBOL_ID, MIN(DT_TM_STAMP) "
			+ "AS MIN_DT_TM_STAMP FROM T_OPTION_CHAIN WHERE (DT_STAMP >= :dateStamp AND DT_STAMP < :dateStampMax) AND "
			+ "(ATTENTION_STYLE_BUY IS NOT NULL AND ATTENTION_STYLE_SELL IS NOT NULL) GROUP BY SYMBOL_ID) "
			+ "GROUPED_OPTION_CHAIN ON OPTION_CHAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID AND OPTION_CHAIN.DT_TM_STAMP = "
			+ "GROUPED_OPTION_CHAIN.MIN_DT_TM_STAMP  ORDER BY OPTION_CHAIN.INDEX_IND DESC, "
			+ "OPTION_CHAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)
	List<OptionChainInfoEntity> findAllByAttentionIndAndDateStampStrict(/*@Param("orperator") String orperator,*/
			@Param("dateStamp") Date dateStamp, @Param("dateStampMax") Date dateStampMax);
	
	
	@Query(value = "SELECT OPTION_CHAIN.* FROM T_OPTION_CHAIN OPTION_CHAIN INNER JOIN (SELECT DISTINCT SYMBOL_ID, MIN(DT_TM_STAMP) "
			+ "AS MIN_DT_TM_STAMP FROM T_OPTION_CHAIN WHERE (DT_STAMP >= :dateStamp AND DT_STAMP < :dateStampMax) AND "
			+ "(ATTENTION_STYLE_BUY IS NOT NULL OR ATTENTION_STYLE_SELL IS NOT NULL) GROUP BY SYMBOL_ID) "
			+ "GROUPED_OPTION_CHAIN ON OPTION_CHAIN.SYMBOL_ID = GROUPED_OPTION_CHAIN.SYMBOL_ID AND OPTION_CHAIN.DT_TM_STAMP = "
			+ "GROUPED_OPTION_CHAIN.MIN_DT_TM_STAMP  ORDER BY OPTION_CHAIN.INDEX_IND DESC, "
			+ "OPTION_CHAIN.DISPLAY_SORT_ORDER DESC", nativeQuery = true)
	List<OptionChainInfoEntity> findAllByAttentionIndAndDateStampPartial(/*@Param("orperator") String orperator,*/
			@Param("dateStamp") Date dateStamp, @Param("dateStampMax") Date dateStampMax);
}
