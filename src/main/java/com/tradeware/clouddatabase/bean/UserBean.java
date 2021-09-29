package com.tradeware.clouddatabase.bean;

import java.util.Date;

public class UserBean extends AbstractBean {
	private static final long serialVersionUID = 8450095355701036346L;
	private String userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private Date dateOfBirth;
	private String email;
	private Boolean emailActivationInd;
	private String confirmationToken;
	private String apiKey;
	private String secretKey;
	private String histApiKey;
	private String histSecretKey;
	private Boolean activeInd;
	private String accessToken;
	private String publicToken;
	private String requestToken;
	private String histAccessToken;
	private String histPublicToken;
	private String histRequestToken;
	private Integer userRoleId;
	private Date dateTimeStamp;
	
	private Boolean accountExpiredInd;
	private  Boolean credentialsExpiredInd;
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
}