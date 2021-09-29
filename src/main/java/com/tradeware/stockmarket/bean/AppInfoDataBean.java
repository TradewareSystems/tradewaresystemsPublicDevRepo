package com.tradeware.stockmarket.bean;

import java.io.Serializable;

public class AppInfoDataBean implements Serializable{
	private static final long serialVersionUID = -5154872027844195338L;
	//Constants and updates should be database driven
	public static final int STATUS_APP_DOWN = -1;
	public static final int STATUS_APP_START_IN_PROGRESS = 0;
	public static final int STATUS_APP_UP_AND_RUNNING = 1;
	
	private int appStatus;

	public AppInfoDataBean() {
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
