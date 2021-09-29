package com.tradeware.clouddatabase.service;

import java.util.Date;
import java.util.List;

import com.tradeware.clouddatabase.bean.SystemErrorBean;

public interface SystemErrorService {
	public List<SystemErrorBean> findAll();

	public List<SystemErrorBean> findAllByDateStampOrderByDateTimeStampDesc(Date dateStamp);

	public List<SystemErrorBean> findAllByErrorIndAndDateStampOrderByDateTimeStampDesc(Boolean errorInd, Date dateStamp);

	public SystemErrorBean save(SystemErrorBean systemErrorBean);
}
