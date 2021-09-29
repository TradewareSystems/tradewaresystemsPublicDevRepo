package com.tradeware.clouddatabase.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tradeware.clouddatabase.entity.IntradayInfoEntity;

@Repository
public interface IntradayInfoRepository extends CrudRepository<IntradayInfoEntity, Integer> {

	public List<IntradayInfoEntity> findAllByDateStampOrderBySymbolId(Date dateStamp);

	@SuppressWarnings("unchecked")
	public IntradayInfoEntity save(IntradayInfoEntity symbol);

	//public List<IntradayInfoEntity> saveAll(List<IntradayInfoEntity> symbol);

	@Query(value = "UPDATE T_INTRADAY_TRADE set INTRA_TRADE_SIGNAL=:intradaySignal, POS_TRADE_SIGNAL=:postionalSignal, "
			+ "INTRA_BUY_SIGNAL_ACTIVE_AT =:intraBuySignalActiveAt, INTRA_SELL_SIGNAL_ACTIVE_AT =:intraSellSignalActiveAt, "
			+ "POS_BUY_SIGNAL_ACTIVE_AT=:positionalBuySignalActiveAt, POS_SELL_SIGNAL_ACTIVE_AT=:positionalSellSignalActiveAt "
			+ "where INTRADAY_TRADE_ID =:intradayTradeId", nativeQuery = true)
	void updateIntradayTradeTrade(@Param("intradayTradeId") Integer intradayTradeId,
			@Param("intradaySignal") String intradaySignal, @Param("postionalSignal") String postionalSignal,
			@Param("intraBuySignalActiveAt") Date intraBuySignalActiveAt,
			@Param("intraSellSignalActiveAt") Date intraSellSignalActiveAt,
			@Param("positionalBuySignalActiveAt") Date positionalBuySignalActiveAt,
			@Param("positionalSellSignalActiveAt") Date positionalSellSignalActiveAt);
}
