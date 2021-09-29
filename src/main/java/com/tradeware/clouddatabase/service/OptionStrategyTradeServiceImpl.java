package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.OptionLiveTradeMainBean;
import com.tradeware.clouddatabase.entity.OptionLiveTradeMainEntity;
import com.tradeware.clouddatabase.repository.OptionStrategyTradeRepository;

@Service
public class OptionStrategyTradeServiceImpl implements OptionStrategyTradeService {

	@Autowired
	private OptionStrategyTradeRepository optionStrategyTradeRepository;

	private List<OptionLiveTradeMainBean> convertEntityListToBeanList(List<OptionLiveTradeMainEntity> listEntity) {
		List<OptionLiveTradeMainBean> listBean = new ArrayList<OptionLiveTradeMainBean>(listEntity.size());
		for (OptionLiveTradeMainEntity entity : listEntity) {
			listBean.add(entity.populateBean());
		}
		return listBean;
	}

	@Override
	public List<OptionLiveTradeMainBean> findAll() {
		//return convertEntityListToBeanList(optionStrategyTradeRepository.findAll());
		return convertEntityListToBeanList(optionStrategyTradeRepository.findAllByOrderByTradedDateStampDesc());
	}

	@Override
	public OptionLiveTradeMainBean findByTradeId(Integer tradeId) {
		OptionLiveTradeMainBean userBean = null;
		OptionLiveTradeMainEntity userEntity = optionStrategyTradeRepository.findByTradeId(tradeId);
		if (null != userEntity) {
			userBean = userEntity.populateBean();
		}
		return userBean;
	}

	@Override
	public List<OptionLiveTradeMainBean> findByTradePosition(String tradePosition) {
		return convertEntityListToBeanList(optionStrategyTradeRepository.findByTradePosition(tradePosition));
	}
	
	@Override
	public List<OptionLiveTradeMainBean> findAllByTradedDateStamp(Date tradedDateStamp) {
		return convertEntityListToBeanList(optionStrategyTradeRepository.findAllByTradedDateStamp(tradedDateStamp));
	}

	@Override
	public List<OptionLiveTradeMainBean> findByTradePositionAndTradeStrategy(String tradePosition,
			String radeStrategy) {
		return convertEntityListToBeanList(
				optionStrategyTradeRepository.findByTradePositionAndTradeStrategy(tradePosition, radeStrategy));
	}
	
	@Override
	public OptionLiveTradeMainBean save(OptionLiveTradeMainBean tradeMainBean) {
		OptionLiveTradeMainEntity tradeMainEntity = new OptionLiveTradeMainEntity();
		tradeMainEntity.populateEntity(tradeMainBean);
		tradeMainEntity = optionStrategyTradeRepository.save(tradeMainEntity);
		tradeMainBean = tradeMainEntity.populateBean();
		return tradeMainBean;
	}

	@Override
	public List<OptionLiveTradeMainBean> findAllByRunningChildTrade(List<String> tradeStrategyList) {
		return convertEntityListToBeanList(optionStrategyTradeRepository.findAllByRunningChildTrade(tradeStrategyList));
	}
	
	@Override
	public List<OptionLiveTradeMainBean> findAllByRunningTrade() {
		return convertEntityListToBeanList(optionStrategyTradeRepository.findAllByRunningTrade());
	}
	
	@Override
	public OptionLiveTradeMainBean findRunningTradeByTradeId(Integer tradeId) {
		OptionLiveTradeMainBean tradeMainBean = null;
		OptionLiveTradeMainEntity userEntity = optionStrategyTradeRepository.findRunningTradeByTradeId(tradeId);
		if (null != userEntity) {
			tradeMainBean = userEntity.populateBean();
		}
		return tradeMainBean;
	}
}
