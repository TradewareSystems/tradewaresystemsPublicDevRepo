package com.tradeware.clouddatabase.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tradeware.clouddatabase.entity.ActivityAuditEntity;

@Repository
public interface ActivityAuditRepository extends CrudRepository<ActivityAuditEntity, Integer> {

	public List<ActivityAuditEntity> findAll();

	public List<ActivityAuditEntity> findAllByDateStampOrderByDateTimeStampDesc(Date dateStamp);

	@SuppressWarnings("unchecked")
	public ActivityAuditEntity save(ActivityAuditEntity activityAuditBean);
}
