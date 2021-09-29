package com.tradeware.stockmarket.bean;

import java.util.Calendar;
import java.util.Date;

public class SystemErrorDataBean extends AbstractDataBean {
	private static final long serialVersionUID = -2696320310321379562L;
	private Long errorId;
	private String className;
	private String methodName;
	private String errorMessage;
	private String errorType;
	private String errorSevirity;
	private Integer errorCount;
	private Boolean errorInd;
	private StringBuffer additionalInfo;
	private Date dateTimeStamp;
	private Date dateStamp;
	private String dateTimeStampStr;

	public SystemErrorDataBean() {
		super();
	}
	public SystemErrorDataBean(String className, String methodName, String errorMessage, String errorType,
			String errorSevirity, Boolean errorInd) {
		super();
		this.className = className;
		this.methodName = methodName;
		this.errorMessage = errorMessage;
		this.errorType = errorType;
		this.errorSevirity = errorSevirity;
		//this.errorCount = 
		//this.additionalInfo = additionalInfo;
		this.errorInd = errorInd;
		this.dateStamp = Calendar.getInstance().getTime();
		this.dateTimeStamp = Calendar.getInstance().getTime();
	}
	public Long getErrorId() {
		return errorId;
	}
	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getErrorSevirity() {
		return errorSevirity;
	}
	public void setErrorSevirity(String errorSevirity) {
		this.errorSevirity = errorSevirity;
	}
	public Boolean getErrorInd() {
		return errorInd;
	}
	public void setErrorInd(Boolean errorInd) {
		this.errorInd = errorInd;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public StringBuffer getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(StringBuffer additionalInfo) {
		this.additionalInfo = additionalInfo;
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
	@Override
	public String toString() {
		return "SystemErrorDataBean [errorId=" + errorId + ", className=" + className + ", methodName=" + methodName
				+ ", errorMessage=" + errorMessage + ", errorType=" + errorType + ", errorSevirity=" + errorSevirity
				+ ", errorCount=" + errorCount + ", errorInd=" + errorInd + ", dateTimeStamp=" + dateTimeStamp
				+ ", dateStamp=" + dateStamp + ", dateTimeStampStr=" + dateTimeStampStr + "]";
	}
}