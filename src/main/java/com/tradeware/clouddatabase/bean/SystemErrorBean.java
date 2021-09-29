package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class SystemErrorBean extends AbstractBean {
	private static final long serialVersionUID = -7970002011570838236L;
	private Long errorId;
	private String className;
	private String methodName;
	private String errorMessage;
	private String errorType;
	private String errorSevirity;
	private Integer errorCount;
	private Boolean errorInd;
	private String additionalInfo;
	private Date dateTimeStamp;
	private Date dateStamp;

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
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public void setErrorInd(Boolean errorInd) {
		this.errorInd = errorInd;
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
	@Override
	public String toString() {
		return "SystemErrorBean [errorId=" + errorId + ", className=" + className + ", methodName=" + methodName
				+ ", errorMessage=" + errorMessage + ", errorType=" + errorType + ", errorSevirity=" + errorSevirity
				+ ", errorCount=" + errorCount + ", errorInd=" + errorInd + ", additionalInfo=" + additionalInfo
				+ ", dateTimeStamp=" + dateTimeStamp + ", dateStamp=" + dateStamp + "]";
	}
}