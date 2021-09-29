package com.tradeware.clouddatabase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tradeware.clouddatabase.bean.UserBean;

@Entity
@Table(name = "T_USER")
public class UserEntity extends AbstractEntity {

	private static final long serialVersionUID = 9057315006367064078L;

	@Id 
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME ")
	private String lastName;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "DT_OF_BIRTH")
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "EMAIL_ACTIVATE_IND")
	private Boolean emailActivationInd;
	
	@Column(name = "EMAIL_CONFIRM_TOKEN")
	private String confirmationToken;
	
	@Column(name = "API_KEY")
	private String apiKey;
	
	@Column(name = "SECRET_KEY")
	private String secretKey;
	
	@Column(name = "HIST_API_KEY")
	private String histApiKey;
	
	@Column(name = "HIST_SECRET_KEY")
	private String histSecretKey;
	
	@Column(name = "ACTIVE_IND")
	private Boolean activeInd;
	
	@Column(name = "ACCESS_TOKEN")
	private String accessToken;
	
	@Column(name = "PUBLIC_TOKEN")
	private String  publicToken;
	
	@Column(name = "REQUEST_TOKEN")
	private String requestToken; 
	
	@Column(name = "HIST_ACCESS_TOKEN")
	private String histAccessToken;
	
	@Column(name = "HIST_PUBLIC_TOKEN")
	private String histPublicToken;
	
	@Column(name = "HIST_REQUEST_TOKEN")
	private String histRequestToken;
	
	@Column(name = "USER_ROLE_ID")
	private Integer userRoleId;
	
	@Column(name = "DT_TM_STAMP", columnDefinition= "TIMESTAMP WITH TIME ZONE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeStamp;
	
	@Column(name = "ACCOUNT_EXPIRED_IND")
	private Boolean accountExpiredInd;
	
	@Column(name = "CREDENTIALS_EXPIRED_IND")
	private  Boolean credentialsExpiredInd;
	
	@Column(name = "ACCOUNT_LOCKED_IND")
	private Boolean accountLockedInd;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Boolean getEmailActivationInd() {
		return emailActivationInd;
	}
	public void setEmailActivationInd(Boolean emailActivationInd) {
		this.emailActivationInd = emailActivationInd;
	}
	public String getConfirmationToken() {
		return confirmationToken;
	}
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	public Boolean getAccountExpiredInd() {
		return accountExpiredInd;
	}
	public void setAccountExpiredInd(Boolean accountExpiredInd) {
		this.accountExpiredInd = accountExpiredInd;
	}
	public Boolean getCredentialsExpiredInd() {
		return credentialsExpiredInd;
	}
	public void setCredentialsExpiredInd(Boolean credentialsExpiredInd) {
		this.credentialsExpiredInd = credentialsExpiredInd;
	}
	public Boolean getAccountLockedInd() {
		return accountLockedInd;
	}
	public void setAccountLockedInd(Boolean accountLockedInd) {
		this.accountLockedInd = accountLockedInd;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getHistApiKey() {
		return histApiKey;
	}
	public void setHistApiKey(String histApiKey) {
		this.histApiKey = histApiKey;
	}
	public String getHistSecretKey() {
		return histSecretKey;
	}
	public void setHistSecretKey(String histSecretKey) {
		this.histSecretKey = histSecretKey;
	}
	public Boolean getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(Boolean activeInd) {
		this.activeInd = activeInd;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getPublicToken() {
		return publicToken;
	}
	public void setPublicToken(String publicToken) {
		this.publicToken = publicToken;
	}
	public String getRequestToken() {
		return requestToken;
	}
	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}
	public String getHistAccessToken() {
		return histAccessToken;
	}
	public void setHistAccessToken(String histAccessToken) {
		this.histAccessToken = histAccessToken;
	}
	public String getHistPublicToken() {
		return histPublicToken;
	}
	public void setHistPublicToken(String histPublicToken) {
		this.histPublicToken = histPublicToken;
	}
	public String getHistRequestToken() {
		return histRequestToken;
	}
	public void setHistRequestToken(String histRequestToken) {
		this.histRequestToken = histRequestToken;
	}
	public Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}
	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}
	public void populateEntity(UserBean bean) {
		this.userId = bean.getUserId();
		this.userName = bean.getUserName();
		this.firstName = bean.getFirstName();
		this.lastName = bean.getLastName();
		this.password = bean.getPassword();
		this.dateOfBirth = bean.getDateOfBirth();
		this.email = bean.getEmail();
		this.emailActivationInd = bean.getEmailActivationInd();
		this.confirmationToken = bean.getConfirmationToken();
		this.apiKey = bean.getApiKey();
		this.secretKey = bean.getSecretKey();
		this.histApiKey = bean.getHistApiKey();
		this.histSecretKey = bean.getHistSecretKey();
		this.activeInd = bean.getActiveInd();
		this.accessToken = bean.getAccessToken();
		this.publicToken = bean.getPublicToken();
		this.requestToken = bean.getRequestToken();
		this.histAccessToken = bean.getHistAccessToken();
		this.histPublicToken = bean.getHistPublicToken();
		this.histRequestToken = bean.getHistRequestToken();
		this.userRoleId = bean.getUserRoleId();
		this.dateTimeStamp = bean.getDateTimeStamp();
		this.accountExpiredInd = bean.getAccountExpiredInd();
		this.credentialsExpiredInd = bean.getCredentialsExpiredInd();
		this.accountLockedInd = bean.getAccountLockedInd();
	}

	public UserBean populateBean() {
		UserBean bean = new UserBean();
		bean.setUserId(this.userId);
		bean.setUserName(this.userName);
		bean.setFirstName(this.firstName);
		bean.setLastName(this.lastName);
		bean.setPassword(this.password);
		bean.setDateOfBirth(this.dateOfBirth);
		bean.setEmail(this.email);
		bean.setEmailActivationInd(this.emailActivationInd);
		bean.setConfirmationToken(this.confirmationToken);
		bean.setApiKey(this.apiKey);
		bean.setSecretKey(this.secretKey);
		bean.setHistApiKey(this.histApiKey);
		bean.setHistSecretKey(this.histSecretKey);
		bean.setActiveInd(this.activeInd);
		bean.setAccessToken(this.accessToken);
		bean.setPublicToken(this.publicToken);
		bean.setRequestToken(this.requestToken);
		bean.setHistAccessToken(this.histAccessToken);
		bean.setHistPublicToken(this.histPublicToken);
		bean.setHistRequestToken(this.histRequestToken);
		bean.setUserRoleId(this.userRoleId);
		bean.setDateTimeStamp(this.dateTimeStamp);
		bean.setAccountExpiredInd(this.accountExpiredInd);
		bean.setCredentialsExpiredInd(this.credentialsExpiredInd);
		bean.setAccountLockedInd(this.accountLockedInd);
		return bean;
	}
}