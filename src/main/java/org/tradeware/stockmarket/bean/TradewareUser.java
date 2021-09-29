package org.tradeware.stockmarket.bean;

import java.io.Serializable;

public class TradewareUser implements Serializable {

	private static final long serialVersionUID = 7488235742523980967L;
	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
