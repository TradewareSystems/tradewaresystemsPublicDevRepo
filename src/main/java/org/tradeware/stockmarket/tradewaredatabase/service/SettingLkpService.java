package org.tradeware.stockmarket.tradewaredatabase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tradeware.stockmarket.tradewaredatabase.bean.SettingLkpBean;
import org.tradeware.stockmarket.tradewaredatabase.entity.SettingLkpEntity;
import org.tradeware.stockmarket.tradewaredatabase.repository.SettingLkpDAO;
@Service
public class SettingLkpService implements ISettingLkpService {
	@Autowired
	private SettingLkpDAO dao;

	@Override
	public List<SettingLkpEntity> findAll() {
		return dao.findAll();
	}

	// TODO : Refactor
	@Override
	public List<SettingLkpBean> findAllSettngs() {
		List<SettingLkpEntity> list = dao.findAll();
		List<SettingLkpBean> listBean = new ArrayList<SettingLkpBean>(list.size());
		for (SettingLkpEntity entity : list) {
			SettingLkpBean bean = new SettingLkpBean();
			entity.populateBean(bean);
			listBean.add(bean);
		}
		return listBean;
	}
}
