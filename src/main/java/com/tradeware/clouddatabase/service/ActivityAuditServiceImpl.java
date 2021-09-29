package com.tradeware.clouddatabase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeware.clouddatabase.bean.ActivityAuditBean;
import com.tradeware.clouddatabase.entity.ActivityAuditEntity;
import com.tradeware.clouddatabase.repository.ActivityAuditRepository;

@Service
public class ActivityAuditServiceImpl implements ActivityAuditService {

	@Autowired
	private ActivityAuditRepository activityAuditRepository;

	private List<ActivityAuditBean> convertEntityListToBeanList(List<ActivityAuditEntity> listEntity) {
		List<ActivityAuditBean> listBean = new ArrayList<ActivityAuditBean>(listEntity.size());
		for (ActivityAuditEntity entity : listEntity) {
			listBean.add(entity.populateBean());
		}
		return listBean;
	}

	@Override
	public List<ActivityAuditBean> findAll() {
		return convertEntityListToBeanList(activityAuditRepository.findAll());
	}

	@Override
	public List<ActivityAuditBean> findAllByDateStampOrderByDateTimeStampDesc(Date dateStamp) {
		return convertEntityListToBeanList(
				activityAuditRepository.findAllByDateStampOrderByDateTimeStampDesc(dateStamp));
	}

	@Override
	public ActivityAuditBean save(ActivityAuditBean activityAuditBean) {
		ActivityAuditEntity activityAuditEntity = new ActivityAuditEntity();
		activityAuditEntity.populateEntity(activityAuditBean);
		activityAuditEntity = activityAuditRepository.save(activityAuditEntity);
		return activityAuditEntity.populateBean();
	}
}
