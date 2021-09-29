package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.VolumeTrendLookupBean;

@Entity
@Table(name = "T_VOLUME_TREND_LOOKUP")
public class VolumeTrendLookupEntity extends AbstractEntity {

	private static final long serialVersionUID = -5176484562890468498L;

	@Id
	@Column(name = "VOLUME_TREND_ID")
	private String volumeTrendId;

	@Column(name = "VOLUME_TREND_DESC")
	private String volumeTrendDescription;

	@Column(name = "VOLUME_TREND_CATEGORY")
	private String volumeTrendCategory;

	@Column(name = "DT_TM_STAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp;
	
	public VolumeTrendLookupEntity() {
		super();
	}

	public VolumeTrendLookupEntity(String volumeTrendId) {
		super();
		this.volumeTrendId = volumeTrendId;
	}

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

	public void populateEntity(VolumeTrendLookupBean bean) {
		this.volumeTrendId = bean.getVolumeTrendId();
		this.volumeTrendDescription = bean.getVolumeTrendDescription();
		this.volumeTrendCategory = bean.getVolumeTrendCategory();
		this.dateTimeStamp = bean.getDateTimeStamp();
	}

	public VolumeTrendLookupBean populateBean() {
		VolumeTrendLookupBean bean = new VolumeTrendLookupBean();
		bean.setVolumeTrendId(this.volumeTrendId);
		bean.setVolumeTrendDescription(this.volumeTrendDescription);
		bean.setVolumeTrendCategory(this.volumeTrendCategory);
		bean.setDateTimeStamp(this.dateTimeStamp);
		return bean;
	}
}