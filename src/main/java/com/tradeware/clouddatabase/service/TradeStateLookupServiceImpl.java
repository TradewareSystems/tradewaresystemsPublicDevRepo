package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.TradeStateLookupBean;
import com.tradeware.clouddatabase.entity.TradeStateLookupEntity;
import com.tradeware.clouddatabase.repository.TradeStateLookupRepository;
@Service
public class TradeStateLookupServiceImpl implements TradeStateLookupService {

	@Autowired
	private TradeStateLookupRepository tradeStateLookupRepository;
	
	private List<TradeStateLookupBean> convertEntityListToBeanList(List< TradeStateLookupEntity> listEntity) {
		List<TradeStateLookupBean> listBean = new ArrayList<TradeStateLookupBean>(listEntity.size());
		for (TradeStateLookupEntity entity : listEntity) {
			listBean.add(entity.populateBean());
		}
		return listBean;
	}
	
	public List<TradeStateLookupBean> findAllOrderByTradeStateDescription() {
		//return convertEntityListToBeanList(tradeStateLookupRepository.findAllOrderByTradeStateDescription() );
		return convertEntityListToBeanList(tradeStateLookupRepository.findAll());
	}

	public TradeStateLookupBean findByTradeStateId(String tradeStateId) {
		TradeStateLookupEntity entity = tradeStateLookupRepository.findByTradeStateId(tradeStateId);
		return entity.populateBean();
	}

	public TradeStateLookupBean save(TradeStateLookupBean tradeStateLookupBean) {
		TradeStateLookupEntity tradeStateLookupEntity = new TradeStateLookupEntity();
		tradeStateLookupEntity.populateEntity(tradeStateLookupBean);
		tradeStateLookupEntity = tradeStateLookupRepository.save(tradeStateLookupEntity);
		return tradeStateLookupEntity.populateBean();
	}
}
