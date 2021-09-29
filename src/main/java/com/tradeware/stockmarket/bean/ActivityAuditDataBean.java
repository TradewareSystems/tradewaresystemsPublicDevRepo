package com.tradeware.stockmarket.bean;

import java.util.Date;

public class ActivityAuditDataBean extends AbstractDataBean {

	private static final long serialVersionUID = -1844134758804173112L;
	private Integer activityId;
	private String activityCategory;
	private String activityDesc;
	private Date dateTimeStamp;
	private Date dateStamp;
	private String dateTimeStampStr;
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
	public String getDateTimeStampStr() {
		return dateTimeStampStr;
	}
	public void setDateTimeStampStr(String dateTimeStampStr) {
		this.dateTimeStampStr = dateTimeStampStr;
	}
}
