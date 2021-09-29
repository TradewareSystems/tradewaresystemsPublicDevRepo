package com.tradeware.clouddatabase.service;

import java.util.Date;
import java.util.List;

import com.tradeware.clouddatabase.bean.OptionLiveTradeMainBean;

public interface OptionStrategyTradeService {

	List<OptionLiveTradeMainBean> findAll();

	OptionLiveTradeMainBean findByTradeId(Integer tradeId);

	List<OptionLiveTradeMainBean> findByTradePosition(String tradePosition);
	
	List<OptionLiveTradeMainBean> findAllByTradedDateStamp(Date tradedDateStamp);

	List<OptionLiveTradeMainBean> findByTradePositionAndTradeStrategy(String tradePosition, String radeStrategy);

	OptionLiveTradeMainBean save(OptionLiveTradeMainBean bean);

	List<OptionLiveTradeMainBean> findAllByRunningTrade();

	List<OptionLiveTradeMainBean> findAllByRunningChildTrade(List<String> tradeStrategyList);

	OptionLiveTradeMainBean findRunningTradeByTradeId(Integer tradeId);

}
