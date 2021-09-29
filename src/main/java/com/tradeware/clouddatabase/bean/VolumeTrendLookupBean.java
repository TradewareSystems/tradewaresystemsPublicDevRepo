package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class VolumeTrendLookupBean extends AbstractBean {
	private static final long serialVersionUID = 278270727331188232L;
	private String volumeTrendId;
	private String volumeTrendDescription;
	private String volumeTrendCategory;
	private Date dateTimeStamp;
	
	public String getVolumeTrendId() {
		return volumeTrendId;
	}
	public void setVolumeTrendId(String volumeTrendId) {
		this.volumeTrendId = volumeTrendId;
	}
	public String getVolumeTrendDescription() {
		return volumeTrendDescription;
	}
	public void setVolumeTrendDescription(String volumeTrendDescription) {
		this.volumeTrendDescription = volumeTrendDescription;
	}
	public String getVolumeTrendCategory() {
		return volumeTrendCategory;
	}
	public void setVolumeTrendCategory(String volumeTrendCategory) {
		this.volumeTrendCategory = volumeTrendCategory;
	}
	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}
	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}
}