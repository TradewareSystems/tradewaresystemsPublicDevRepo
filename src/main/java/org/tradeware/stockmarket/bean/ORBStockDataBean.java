package org.tradeware.stockmarket.bean;

import java.util.Date;

public class ORBStockDataBean extends StockDataBean {

	private static final long serialVersionUID = 8944460301633800527L;
	private double openCandle1; 
	private double highCandle1;
	private double lowCandle1;
	private double closeCandle1;
	private double openCandle2; 
	private double highCandle2;
	private double lowCandle2;
	private double closeCandle2;
	private String candle1Code;
	private String candle2Code;
	private String errorMessage;
	private Date tradableTriggerTime;
	private boolean jackpatCallInd = true;
	
	public ORBStockDataBean(Integer lotSize, String stockName) {
		super(lotSize, stockName);
	}
	public double getOpenCandle1() {
		return openCandle1;
	}
	public void setOpenCandle1(double openCandle1) {
		this.openCandle1 = openCandle1;
	}
	public double getHighCandle1() {
		return highCandle1;
	}
	public void setHighCandle1(double highCandle1) {
		this.highCandle1 = highCandle1;
	}
	public double getLowCandle1() {
		return lowCandle1;
	}
	public void setLowCandle1(double lowCandle1) {
		this.lowCandle1 = lowCandle1;
	}
	public double getCloseCandle1() {
		return closeCandle1;
	}
	public void setCloseCandle1(double closeCandle1) {
		this.closeCandle1 = closeCandle1;
	}
	public double getOpenCandle2() {
		return openCandle2;
	}
	public void setOpenCandle2(double openCandle2) {
		this.openCandle2 = openCandle2;
	}
	public double getHighCandle2() {
		return highCandle2;
	}
	public void setHighCandle2(double highCandle2) {
		this.highCandle2 = highCandle2;
	}
	public double getLowCandle2() {
		return lowCandle2;
	}
	public void setLowCandle2(double lowCandle2) {
		this.lowCandle2 = lowCandle2;
	}
	public double getCloseCandle2() {
		return closeCandle2;
	}
	public void setCloseCandle2(double closeCandle2) {
		this.closeCandle2 = closeCandle2;
	}
	public String getCandle1Code() {
		return candle1Code;
	}
	public void setCandle1Code(String candle1Code) {
		this.candle1Code = candle1Code;
	}
	public String getCandle2Code() {
		return candle2Code;
	}
	public void setCandle2Code(String candle2Code) {
		this.candle2Code = candle2Code;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Date getTradableTriggerTime() {
		return tradableTriggerTime;
	}
	public void setTradableTriggerTime(Date tradableTriggerTime) {
		this.tradableTriggerTime = tradableTriggerTime;
	}
	public boolean isJackpatCallInd() {
		return jackpatCallInd;
	}
	public void setJackpatCallInd(boolean jackpatCallInd) {
		this.jackpatCallInd = jackpatCallInd;
	}
}
