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

import com.tradeware.clouddatabase.bean.SystemErrorBean;

@Entity
@Table(name = "T_SYSTEM_ERROR")
public class SystemErrorEntity extends AbstractEntity{

	private static final long serialVersionUID = -3151274608797948628L;

	@Id 
	@Column(name = "ERROR_ID", columnDefinition="serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long errorId;
	
	@Column(name = "CLASS_NAME", length=256)
	private String className;
	
	@Column(name = "METHOD_NAME", length=256)
	private String methodName;
	
	@Column(name = "ERROR_MESSAGE", length=2048)
	private String errorMessage;
	
	@Column(name = "ERROR_TYPE", length=256)
	private String errorType;
	
	@Column(name = "ERROR_SEVIRITY", length=256)
	private String errorSevirity;
	
	@Column(name = "ERROR_COUNT")
	private Integer errorCount;
	
	@Column(name = "ADDITIONAL_INFO", length=4096)
	private String additionalInfo;
	
	@Column(name = "ERROR_IND")
	private Boolean errorInd;
	
	@Column(name = "DT_TM_STAMP", columnDefinition= "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp;
	
	@Column(name = "DT_STAMP", columnDefinition= "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.DATE)
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
	public void populateEntity(SystemErrorBean bean) {
		this.errorId = bean.getErrorId();
		this.className = bean.getClassName();
		this.methodName = bean.getMethodName();
		this.errorMessage = bean.getErrorMessage();
		this.errorType = bean.getErrorType();
		this.errorSevirity = bean.getErrorSevirity();
		this.errorCount = bean.getErrorCount();
		this.errorInd = bean.getErrorInd();
		this.additionalInfo = bean.getAdditionalInfo();
		this.dateTimeStamp = bean.getDateTimeStamp();
		this.dateStamp = bean.getDateStamp();
	}
	public SystemErrorBean populateBean() {
		SystemErrorBean bean = new SystemErrorBean();
		bean.setErrorId(this.errorId);
		bean.setClassName(this.className);
		bean.setMethodName(this.methodName);
		bean.setErrorMessage(this.errorMessage);
		bean.setErrorType(this.errorType);
		bean.setErrorSevirity(this.errorSevirity);
		bean.setErrorCount(this.errorCount);
		bean.setErrorInd(this.errorInd);
		bean.setAdditionalInfo(this.additionalInfo);
		bean.setDateTimeStamp(this.dateTimeStamp);
		bean.setDateStamp(this.dateStamp);
		return bean;
	}
}
