package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tradeware.clouddatabase.bean.IntradayInfoBean;
import com.tradeware.clouddatabase.entity.IntradayInfoEntity;
import com.tradeware.clouddatabase.repository.IntradayInfoRepository;

public class IntradayInfoServiceImpl  implements IntradayInfoService {

	@Autowired
	private IntradayInfoRepository intradayInfoRepository;

	@SuppressWarnings("unused")
	private List<IntradayInfoBean> convertEntityListToBeanList(List<IntradayInfoEntity> listEntity) {
		List<IntradayInfoBean> listBean = new ArrayList<IntradayInfoBean>(listEntity.size());
		for (IntradayInfoEntity entity : listEntity) {
			listBean.add(entity.populateBean());
		}
		return listBean;
	}
	
	private List<IntradayInfoEntity> convertBeanListToEntityList(List<IntradayInfoBean> listBean) {
		List<IntradayInfoEntity> listEntity = new ArrayList<IntradayInfoEntity>(listBean.size());
		for (IntradayInfoBean bean : listBean) {
			IntradayInfoEntity entity = new IntradayInfoEntity();
			entity.populateEntity(bean);
			listEntity.add(entity);
		}
		return listEntity;
	}

	@Override
	public List<IntradayInfoBean> findAllByDateStampOrderBySymbolId(Date dateStamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntradayInfoEntity save(IntradayInfoBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntradayInfoBean> saveAll(List<IntradayInfoBean> intradayInfoList) {
		Iterable<IntradayInfoEntity> listEntity =  intradayInfoRepository.saveAll(convertBeanListToEntityList(intradayInfoList));
		 
		 return null;//return convertEntityListToBeanList(listEntity.iterator().);
	}

	@Override
	public void updateIntradayTradeTrade(Integer intradayTradeId, String intradaySignal, String postionalSignal,
			Date intraBuySignalActiveAt, Date intraSellSignalActiveAt, Date positionalBuySignalActiveAt,
			Date positionalSellSignalActiveAt) {
		// TODO Auto-generated method stub
		
	}
}
