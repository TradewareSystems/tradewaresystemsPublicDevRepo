package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.SystemErrorBean;
import com.tradeware.clouddatabase.entity.SystemErrorEntity;
import com.tradeware.clouddatabase.repository.SystemErrorRepository;
@Service
public class SystemErrorServiceImpl implements SystemErrorService {

	@Autowired
	private SystemErrorRepository systemErrorRepository;

	private List<SystemErrorBean> convertEntityListToBeanList(List<SystemErrorEntity> listEntity) {
		List<SystemErrorBean> listBean = new ArrayList<SystemErrorBean>(listEntity.size());
		for (SystemErrorEntity entity : listEntity) {
			listBean.add(entity.populateBean());
		}
		return listBean;
	}

	public List<SystemErrorBean> findAll() {
		return convertEntityListToBeanList(systemErrorRepository.findAll());
	}

	public List<SystemErrorBean> findAllByDateStampOrderByDateTimeStampDesc(Date dateStamp) {
		return convertEntityListToBeanList(systemErrorRepository.findAllByDateStampOrderByDateTimeStampDesc(dateStamp));
	}

	public List<SystemErrorBean> findAllByErrorIndAndDateStampOrderByDateTimeStampDesc(Boolean errorInd,
			Date dateStamp) {
		return convertEntityListToBeanList(
				systemErrorRepository.findAllByErrorIndAndDateStampOrderByDateTimeStampDesc(errorInd, dateStamp));
	}

	public SystemErrorBean save(SystemErrorBean systemErrorBean) {
	//System.out.println(systemErrorBean);
		SystemErrorEntity systemErrorEntity = new SystemErrorEntity();
		systemErrorEntity.populateEntity(systemErrorBean);
		systemErrorEntity = systemErrorRepository.save(systemErrorEntity);
		return systemErrorEntity.populateBean();
	}
}
