package org.tradeware.stockmarket.tradewaredatabase.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.tradeware.stockmarket.tradewaredatabase.bean.SettingLkpBean;

@Entity
@Table(name = "T_SETTING_LKP")
public class SettingLkpEntity implements Serializable {

	private static final long serialVersionUID = -4652933258575344121L;
	
	@Id 
	@Column(name = "SETTING_ID")
	private Integer settingId;
	
	@Column(name = "SETTING_CD")
	private String settingCode;
	
	@Column(name = "SETTING_VAL")
	private String settingValue;
	
	@Column(name = "COMMENT")
	private String comment;

	public Integer getSettingId() {
		return settingId;
	}
	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}
	public String getSettingCode() {
		return settingCode;
	}
	public void setSettingCode(String settingCode) {
		this.settingCode = settingCode;
	}
	public String getSettingValue() {
		return settingValue;
	}
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void populateEntity(SettingLkpBean bean) {
		this.settingId = bean.getSettingId();
		this.settingCode = bean.getSettingCode();
		this.settingValue = bean.getSettingValue();
		this.comment = bean.getComment();
	}
	
	public void populateBean(SettingLkpBean bean) {
		 bean.setSettingId(this.settingId);
		 bean.setSettingCode(this.settingCode);
		 bean.setSettingValue(this.settingValue);
		 bean.setComment(this.comment);
	}
}
