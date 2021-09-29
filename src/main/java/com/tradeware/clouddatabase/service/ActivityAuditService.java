package com.tradeware.clouddatabase.service;

import java.util.Date;
import java.util.List;

import com.tradeware.clouddatabase.bean.ActivityAuditBean;

public interface ActivityAuditService {
	public List<ActivityAuditBean> findAll();

	public List<ActivityAuditBean> findAllByDateStampOrderByDateTimeStampDesc(Date dateStamp);

	public ActivityAuditBean save(ActivityAuditBean activityAuditBean);
}
