package org.tradeware.stockmarket.bean;

import java.math.BigDecimal;

public class ReportBean {

	private StringBuilder signalTriggeredIn = new StringBuilder("");
	private StringBuilder signalTriggeredOut = new StringBuilder("");
	public ReportBean clone() throws CloneNotSupportedException {
		return (ReportBean) super.clone();
	}
	public String getSignalTriggeredInAt() {
		return signalTriggeredIn.toString();
	}

	public void setSignalTriggeredInAt(Object... signalTriggeredInAt) {
		if (null != signalTriggeredIn && !"".equals(signalTriggeredIn)) {
			signalTriggeredIn.append("<BR>");
		}
		for (Object value : signalTriggeredInAt) {
			signalTriggeredIn.append(String.valueOf(value));	
		}
	}

	public void setSignalTriggeredInAtClear(Object... signalTriggeredInAt) {
		signalTriggeredIn.delete(0, signalTriggeredIn.length());
		for (Object value : signalTriggeredInAt) {
			signalTriggeredIn.append(String.valueOf(value));	
		}
	}

	public String getSignalTriggeredOutAt() {
		return signalTriggeredOut.toString();
	}

	public void setSignalTriggeredOutAt(Object... signalTriggeredOutAt) {
		if (null != signalTriggeredOut && !"".equals(signalTriggeredOut)) {
			signalTriggeredOut.append("<BR>");
		}
		for (Object value : signalTriggeredOutAt) {
			signalTriggeredOut.append(String.valueOf(value));	
		}
	}

	public void setSignalTriggeredOutAtClear(Object... signalTriggeredOutAt) {
		signalTriggeredOut.delete(0, signalTriggeredOut.length());
		for (Object value : signalTriggeredOutAt) {
			signalTriggeredOut.append(String.valueOf(value));	
		}
	}

	// sclae amount value to
	public double getScaledVal(double value) {
		//return new BigDecimal(value).setScale(1, 0).setScale(2, 0).doubleValue();
		return new BigDecimal(value).setScale(2, 0).doubleValue();
	}
	public BigDecimal getScaledValueObj(double value) {
		return new BigDecimal(value).setScale(2, 0);
	}
	public double getScaledValue(BigDecimal value) {
		double scaledVal = 0;
		if (null != value) {
			scaledVal = value.setScale(2, 0).doubleValue();
		}
		return scaledVal;
	}

	private String tempString;
	public String getTempString() {
		return tempString;
	}
	public void setTempString(String tempString) {
		this.tempString = tempString;
	}
}
