package com.tradeware.clouddatabase.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tradeware.clouddatabase.entity.TradeStateLookupEntity;

@Repository
public interface TradeStateLookupRepository extends CrudRepository<TradeStateLookupEntity, String> {
	public List<TradeStateLookupEntity> findAll();
	//public List<TradeStateLookupEntity> findAllOrderByTradeStateDescription();

	public TradeStateLookupEntity findByTradeStateId(String tradeStateId);

	@SuppressWarnings("unchecked")
	public TradeStateLookupEntity save(TradeStateLookupEntity tradeStateLookupBean);
}
