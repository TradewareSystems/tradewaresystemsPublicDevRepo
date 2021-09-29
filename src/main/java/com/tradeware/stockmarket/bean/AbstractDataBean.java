package com.tradeware.stockmarket.bean;

import java.io.Serializable;

public class AbstractDataBean implements Serializable {

	private static final long serialVersionUID = 1048479043659865736L;

	public static String STATUS_NA = "NA";
	public static Integer DEFAULT_TRADE_LOT = 1;
	public static String TRADE_TYPE_FORWARD = "FORWARD";
	public static String TRADE_TYPE_REVERSE = "REVERSE";
}
