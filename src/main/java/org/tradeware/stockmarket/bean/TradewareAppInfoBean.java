package org.tradeware.stockmarket.bean;

import java.io.Serializable;

public class TradewareAppInfoBean implements Serializable{

	private static final long serialVersionUID = -4016858063774495785L;
	
	//Constants and updates should be database driven
	public static final int STATUS_APP_DOWN = -1;
	public static final int STATUS_APP_START_IN_PROGRESS = 0;
	public static final int STATUS_APP_UP_AND_RUNNING = 1;
	
	private int appStatus;

	public TradewareAppInfoBean() {
		super();
		this.appStatus = STATUS_APP_DOWN;
	}

	public int getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(int appStatus) {
		this.appStatus = appStatus;
	}
}
