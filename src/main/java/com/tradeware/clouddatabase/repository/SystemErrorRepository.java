package com.tradeware.clouddatabase.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tradeware.clouddatabase.entity.SystemErrorEntity;

@Repository
public interface SystemErrorRepository extends CrudRepository<SystemErrorEntity, Long> {

	public List<SystemErrorEntity> findAll();

	public List<SystemErrorEntity> findAllByDateStampOrderByDateTimeStampDesc(Date dateStamp);

	public List<SystemErrorEntity> findAllByErrorIndAndDateStampOrderByDateTimeStampDesc(Boolean errorInd,
			Date dateStamp);

	@SuppressWarnings("unchecked")
	public SystemErrorEntity save(SystemErrorEntity systemErrorBean);
}
