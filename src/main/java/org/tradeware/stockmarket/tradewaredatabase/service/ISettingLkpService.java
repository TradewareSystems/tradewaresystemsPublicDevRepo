package org.tradeware.stockmarket.tradewaredatabase.service;

import java.util.List;

import org.tradeware.stockmarket.tradewaredatabase.bean.SettingLkpBean;
import org.tradeware.stockmarket.tradewaredatabase.entity.SettingLkpEntity;

public interface ISettingLkpService {

	List<SettingLkpEntity> findAll();
	
	List<SettingLkpBean> findAllSettngs();
}
