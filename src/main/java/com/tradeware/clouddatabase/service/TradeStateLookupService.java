package com.tradeware.clouddatabase.service;

import java.util.List;

import com.tradeware.clouddatabase.bean.TradeStateLookupBean;

public interface TradeStateLookupService {

	public List<TradeStateLookupBean> findAllOrderByTradeStateDescription();

	public TradeStateLookupBean findByTradeStateId(String tradeStateId);

	public TradeStateLookupBean save(TradeStateLookupBean tradeStateLookupBean);
}
