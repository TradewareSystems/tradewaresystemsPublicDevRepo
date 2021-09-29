package com.tradeware.clouddatabase.service;

import java.util.Date;
import java.util.List;

import com.tradeware.clouddatabase.bean.IntradayInfoBean;
import com.tradeware.clouddatabase.entity.IntradayInfoEntity;

public interface IntradayInfoService {
	List<IntradayInfoBean> findAllByDateStampOrderBySymbolId(Date dateStamp);

	IntradayInfoEntity save(IntradayInfoBean bean);

	List<IntradayInfoBean> saveAll(List<IntradayInfoBean> symbol);

	void updateIntradayTradeTrade(Integer intradayTradeId, String intradaySignal, String postionalSignal,
			Date intraBuySignalActiveAt, Date intraSellSignalActiveAt, Date positionalBuySignalActiveAt,
			Date positionalSellSignalActiveAt);
}
