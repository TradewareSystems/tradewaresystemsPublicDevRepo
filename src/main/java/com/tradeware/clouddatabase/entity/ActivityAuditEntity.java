package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.ActivityAuditBean;

@Entity
@Table(name = "T_ACTIVITY_AUDIT")
public class ActivityAuditEntity extends AbstractEntity {

	private static final long serialVersionUID = 820174455114043705L;

	@Id
	@Column(name = "ACTIVITY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer activityId;
	
	@Column(name = "ACTIVITY_CATEGORY")
	private String activityCategory;
	
	@Column(name = "ACTIVITY_DESC")
	private String activityDesc;
	
	@Column(name = "DT_TM_STAMP", columnDefinition= "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp;
	
	@Column(name = "DT_STAMP")
	@Temporal(TemporalType.DATE)
	private Date dateStamp;

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityCategory() {
		return activityCategory;
	}

	public void setActivityCategory(String activityCategory) {
		this.activityCategory = activityCategory;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}

	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}
	
	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public void populateEntity(ActivityAuditBean bean) {
		this.activityId = bean.getActivityId();
		this.activityCategory = bean.getActivityCategory();
		this.activityDesc = bean.getActivityDesc();
		this.dateTimeStamp = bean.getDateTimeStamp();
		this.dateStamp = bean.getDateStamp();
	}

	public ActivityAuditBean populateBean() {
		ActivityAuditBean bean = new ActivityAuditBean();
		bean.setActivityId(this.activityId);
		bean.setActivityCategory(this.activityCategory);
		bean.setActivityDesc(this.activityDesc);
		bean.setDateTimeStamp(this.dateTimeStamp);
		bean.setDateStamp(this.dateStamp);
		return bean;
	}
}
