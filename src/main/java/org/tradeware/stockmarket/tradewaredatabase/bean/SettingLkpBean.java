package org.tradeware.stockmarket.tradewaredatabase.bean;

import java.io.Serializable;

public class SettingLkpBean implements Serializable {

	private static final long serialVersionUID = -8742528575903940948L;
	private Integer settingId;
	private String settingCode;
	private String settingValue;
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
}