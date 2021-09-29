package org.tradeware.stockmarket.bean;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class OptionChainDataBean {
	public static NumberFormat myformatter = new DecimalFormat("########");  
	
	private String symbol;
	private double strikePrice1;
	private double strikePrice2;
	private double tickerSize;
	private Integer timeFrameNumber;
	// CALLS
	private double top1OpenInterestCall;
	private double top2OpenInterestCall;
	private double top3OpenInterestCall;

	private double top1OpenInterestChangeCall;
	private double top2OpenInterestChangeCall;
	private double top3OpenInterestChangeCall;

	private double top1OIVolumesCall;
	private double top2OIVolumesCall;
	private double top3OIVolumesCall;

	private double top1OINetChangeCall;
	private double top2OINetChangeCall;
	private double top3OINetChangeCall;

	private Double top1StrikePriceCall;
	private Double top2StrikePriceCall;
	private Double top3StrikePriceCall;

	// PUTs
	private double top1OpenInterestPut;
	private double top2OpenInterestPut;
	private double top3OpenInterestPut;

	private double top1OpenInterestChangePut;
	private double top2OpenInterestChangePut;
	private double top3OpenInterestChangePut;

	private double top1OIVolumesPut;
	private double top2OIVolumesPut;
	private double top3OIVolumesPut;

	private double top1OINetChangePut;
	private double top2OINetChangePut;
	private double top3OINetChangePut;

	private double top1StrikePricePut;
	private double top2StrikePricePut;
	private double top3StrikePricePut;
	
	private double totalOpenInterestCall;
	private double totalOpenInterestPut;
	private double totalOIVolumesCall;
	private double totalOIVolumesPut;
	
	//OI Spruts
	private double day1OpenInterest;
	private double day2OpenInterest;
	private double openInterestChange;
	private double openInterestChangePercentage;
	private double openInterestVolumes;//contracts
	private double underlyingPrice;//current market price  value
	
	private Boolean bestTradeInd;
	
	public OptionChainDataBean(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Integer getTimeFrameNumber() {
		return timeFrameNumber;
	}

	public void setTimeFrameNumber(Integer timeFrameNumber) {
		this.timeFrameNumber = timeFrameNumber;
	}

	public double getStrikePrice1() {
		return strikePrice1;
	}
	public void setStrikePrice1(double strikePrice1) {
		this.strikePrice1 = strikePrice1;
	}
	public double getStrikePrice2() {
		return strikePrice2;
	}
	public void setStrikePrice2(double strikePrice2) {
		this.strikePrice2 = strikePrice2;
	}
	public double getTickerSize() {
		return tickerSize;
	}
	public void setTickerSize(double tickerSize) {
		this.tickerSize = tickerSize;
	}
	public double getTop1OpenInterestCall() {
		return top1OpenInterestCall;
	}
	public void setTop1OpenInterestCall(double top1OpenInterestCall) {
		this.top1OpenInterestCall = top1OpenInterestCall;
	}
	public double getTop2OpenInterestCall() {
		return top2OpenInterestCall;
	}
	public void setTop2OpenInterestCall(double top2OpenInterestCall) {
		this.top2OpenInterestCall = top2OpenInterestCall;
	}
	public double getTop3OpenInterestCall() {
		return top3OpenInterestCall;
	}
	public void setTop3OpenInterestCall(double top3OpenInterestCall) {
		this.top3OpenInterestCall = top3OpenInterestCall;
	}
	public double getTop1OpenInterestChangeCall() {
		return top1OpenInterestChangeCall;
	}
	public void setTop1OpenInterestChangeCall(double top1OpenInterestChangeCall) {
		this.top1OpenInterestChangeCall = top1OpenInterestChangeCall;
	}
	public double getTop2OpenInterestChangeCall() {
		return top2OpenInterestChangeCall;
	}
	public void setTop2OpenInterestChangeCall(double top2OpenInterestChangeCall) {
		this.top2OpenInterestChangeCall = top2OpenInterestChangeCall;
	}
	public double getTop3OpenInterestChangeCall() {
		return top3OpenInterestChangeCall;
	}
	public void setTop3OpenInterestChangeCall(double top3OpenInterestChangeCall) {
		this.top3OpenInterestChangeCall = top3OpenInterestChangeCall;
	}
	public double getTop1OIVolumesCall() {
		return top1OIVolumesCall;
	}
	public void setTop1OIVolumesCall(double top1oiVolumesCall) {
		top1OIVolumesCall = top1oiVolumesCall;
	}
	public double getTop2OIVolumesCall() {
		return top2OIVolumesCall;
	}
	public void setTop2OIVolumesCall(double top2oiVolumesCall) {
		top2OIVolumesCall = top2oiVolumesCall;
	}
	public double getTop3OIVolumesCall() {
		return top3OIVolumesCall;
	}
	public void setTop3OIVolumesCall(double top3oiVolumesCall) {
		top3OIVolumesCall = top3oiVolumesCall;
	}
	public double getTop1OINetChangeCall() {
		return top1OINetChangeCall;
	}
	public void setTop1OINetChangeCall(double top1oiNetChangeCall) {
		top1OINetChangeCall = top1oiNetChangeCall;
	}
	public double getTop2OINetChangeCall() {
		return top2OINetChangeCall;
	}
	public void setTop2OINetChangeCall(double top2oiNetChangeCall) {
		top2OINetChangeCall = top2oiNetChangeCall;
	}
	public double getTop3OINetChangeCall() {
		return top3OINetChangeCall;
	}
	public void setTop3OINetChangeCall(double top3oiNetChangeCall) {
		top3OINetChangeCall = top3oiNetChangeCall;
	}
	public Double getTop1StrikePriceCall() {
		return top1StrikePriceCall;
	}
	public void setTop1StrikePriceCall(Double top1StrikePriceCall) {
		this.top1StrikePriceCall = top1StrikePriceCall;
	}
	public Double getTop2StrikePriceCall() {
		return top2StrikePriceCall;
	}
	public void setTop2StrikePriceCall(Double top2StrikePriceCall) {
		this.top2StrikePriceCall = top2StrikePriceCall;
	}
	public Double getTop3StrikePriceCall() {
		return top3StrikePriceCall;
	}
	public void setTop3StrikePriceCall(Double top3StrikePriceCall) {
		this.top3StrikePriceCall = top3StrikePriceCall;
	}
	public double getTop1OpenInterestPut() {
		return top1OpenInterestPut;
	}
	public void setTop1OpenInterestPut(double top1OpenInterestPut) {
		this.top1OpenInterestPut = top1OpenInterestPut;
	}
	public double getTop2OpenInterestPut() {
		return top2OpenInterestPut;
	}
	public void setTop2OpenInterestPut(double top2OpenInterestPut) {
		this.top2OpenInterestPut = top2OpenInterestPut;
	}
	public double getTop3OpenInterestPut() {
		return top3OpenInterestPut;
	}
	public void setTop3OpenInterestPut(double top3OpenInterestPut) {
		this.top3OpenInterestPut = top3OpenInterestPut;
	}
	public double getTop1OpenInterestChangePut() {
		return top1OpenInterestChangePut;
	}
	public void setTop1OpenInterestChangePut(double top1OpenInterestChangePut) {
		this.top1OpenInterestChangePut = top1OpenInterestChangePut;
	}
	public double getTop2OpenInterestChangePut() {
		return top2OpenInterestChangePut;
	}
	public void setTop2OpenInterestChangePut(double top2OpenInterestChangePut) {
		this.top2OpenInterestChangePut = top2OpenInterestChangePut;
	}
	public double getTop3OpenInterestChangePut() {
		return top3OpenInterestChangePut;
	}
	public void setTop3OpenInterestChangePut(double top3OpenInterestChangePut) {
		this.top3OpenInterestChangePut = top3OpenInterestChangePut;
	}
	public double getTop1OIVolumesPut() {
		return top1OIVolumesPut;
	}
	public void setTop1OIVolumesPut(double top1oiVolumesPut) {
		top1OIVolumesPut = top1oiVolumesPut;
	}
	public double getTop2OIVolumesPut() {
		return top2OIVolumesPut;
	}
	public void setTop2OIVolumesPut(double top2oiVolumesPut) {
		top2OIVolumesPut = top2oiVolumesPut;
	}
	public double getTop3OIVolumesPut() {
		return top3OIVolumesPut;
	}
	public void setTop3OIVolumesPut(double top3oiVolumesPut) {
		top3OIVolumesPut = top3oiVolumesPut;
	}
	public double getTop1OINetChangePut() {
		return top1OINetChangePut;
	}
	public void setTop1OINetChangePut(double top1oiNetChangePut) {
		top1OINetChangePut = top1oiNetChangePut;
	}
	public double getTop2OINetChangePut() {
		return top2OINetChangePut;
	}
	public void setTop2OINetChangePut(double top2oiNetChangePut) {
		top2OINetChangePut = top2oiNetChangePut;
	}
	public double getTop3OINetChangePut() {
		return top3OINetChangePut;
	}
	public void setTop3OINetChangePut(double top3oiNetChangePut) {
		top3OINetChangePut = top3oiNetChangePut;
	}
	public double getTop1StrikePricePut() {
		return top1StrikePricePut;
	}
	public void setTop1StrikePricePut(double top1StrikePricePut) {
		this.top1StrikePricePut = top1StrikePricePut;
	}
	public double getTop2StrikePricePut() {
		return top2StrikePricePut;
	}
	public void setTop2StrikePricePut(double top2StrikePricePut) {
		this.top2StrikePricePut = top2StrikePricePut;
	}
	public double getTop3StrikePricePut() {
		return top3StrikePricePut;
	}
	public void setTop3StrikePricePut(double top3StrikePricePut) {
		this.top3StrikePricePut = top3StrikePricePut;
	}
	public double getTotalOpenInterestCall() {
		return totalOpenInterestCall;
	}
	public void setTotalOpenInterestCall(double totalOpenInterestCall) {
		this.totalOpenInterestCall = totalOpenInterestCall;
	}
	public double getTotalOpenInterestPut() {
		return totalOpenInterestPut;
	}
	public void setTotalOpenInterestPut(double totalOpenInterestPut) {
		this.totalOpenInterestPut = totalOpenInterestPut;
	}
	public double getTotalOIVolumesCall() {
		return totalOIVolumesCall;
	}
	public void setTotalOIVolumesCall(double totalOIVolumesCall) {
		this.totalOIVolumesCall = totalOIVolumesCall;
	}
	public double getTotalOIVolumesPut() {
		return totalOIVolumesPut;
	}
	public void setTotalOIVolumesPut(double totalOIVolumesPut) {
		this.totalOIVolumesPut = totalOIVolumesPut;
	}
	public double getDay1OpenInterest() {
		return day1OpenInterest;
	}
	public void setDay1OpenInterest(double day1OpenInterest) {
		this.day1OpenInterest = day1OpenInterest;
	}
	public double getDay2OpenInterest() {
		return day2OpenInterest;
	}
	public void setDay2OpenInterest(double day2OpenInterest) {
		this.day2OpenInterest = day2OpenInterest;
	}
	public double getOpenInterestChange() {
		return openInterestChange;
	}
	public void setOpenInterestChange(double openInterestChange) {
		this.openInterestChange = openInterestChange;
	}
	public double getOpenInterestChangePercentage() {
		return openInterestChangePercentage;
	}
	public void setOpenInterestChangePercentage(double openInterestChangePercentage) {
		this.openInterestChangePercentage = openInterestChangePercentage;
	}
	public double getOpenInterestVolumes() {
		return openInterestVolumes;
	}
	public void setOpenInterestVolumes(double openInterestVolumes) {
		this.openInterestVolumes = openInterestVolumes;
	}
	public double getUnderlyingPrice() {
		return underlyingPrice;
	}
	public void setUnderlyingPrice(double underlyingPrice) {
		this.underlyingPrice = underlyingPrice;
	}
	public Boolean getBestTradeInd() {
		return bestTradeInd;
	}
	public void setBestTradeInd(Boolean bestTradeInd) {
		this.bestTradeInd = bestTradeInd;
	}

	@Override
	public String toString() {
		return "OptionChainDataBean [strikePrice1=" + strikePrice1 + ", strikePrice2=" + strikePrice2 + ", tickerSize="
				+ tickerSize + ", top1OpenInterestCall=" + top1OpenInterestCall + ", top2OpenInterestCall="
				+ top2OpenInterestCall + ", top3OpenInterestCall=" + top3OpenInterestCall + ", top1OpenInterestChangeCall="
				+ top1OpenInterestChangeCall + ", top2OpenInterestChangeCall=" + top2OpenInterestChangeCall
				+ ", top3OpenInterestChangeCall=" + top3OpenInterestChangeCall + ", top1OIVolumesCall="
				+ top1OIVolumesCall + ", top2OIVolumesCall=" + top2OIVolumesCall + ", top3OIVolumesCall="
				+ top3OIVolumesCall + ", top1OINetChangeCall=" + top1OINetChangeCall + ", top2OINetChangeCall="
				+ top2OINetChangeCall + ", top3OINetChangeCall=" + top3OINetChangeCall + ", top1StrikePriceCall="
				+ top1StrikePriceCall + ", top2StrikePriceCall=" + top2StrikePriceCall + ", top3StrikePriceCall="
				+ top3StrikePriceCall + ", top1OpenInterestPut=" + top1OpenInterestPut + ", top2OpenInterestPut="
				+ top2OpenInterestPut + ", top3OpenInterestPut=" + top3OpenInterestPut + ", top1OpenInterestChangePut="
				+ top1OpenInterestChangePut + ", top2OpenInterestChangePut=" + top2OpenInterestChangePut
				+ ", top3OpenInterestChangePut=" + top3OpenInterestChangePut + ", top1OIVolumesPut=" + top1OIVolumesPut
				+ ", top2OIVolumesPut=" + top2OIVolumesPut + ", top3OIVolumesPut=" + top3OIVolumesPut
				+ ", top1OINetChangePut=" + top1OINetChangePut + ", top2OINetChangePut=" + top2OINetChangePut
				+ ", top3OINetChangePut=" + top3OINetChangePut + ", top1StrikePricePut=" + top1StrikePricePut
				+ ", top2StrikePricePut=" + top2StrikePricePut + ", top3StrikePricePut=" + top3StrikePricePut
				+ ", totalOpenInterestCall=" + totalOpenInterestCall + ", totalOpenInterestPut=" + totalOpenInterestPut
				+ ", totalOIVolumesCall=" + totalOIVolumesCall + ", totalOIVolumesPut=" + totalOIVolumesPut + "]";
	}
	
	//Should be in StockDataBean
	private double week52High;
	private double week52Low;
	public double getWeek52High() {
		return week52High;
	}
	public void setWeek52High(double week52High) {
		this.week52High = week52High;
	}
	public double getWeek52Low() {
		return week52Low;
	}
	public void setWeek52Low(double week52Low) {
		this.week52Low = week52Low;
	}
	
	private static final String EMPTY_STR = "",  NA = "NA", BUY = "BUY", SELL = "SELL", STRONG_BUY = "STRONG_BUY", STRONG_SELL = "STRONG_SELL", 
			STARTS_WITH_STRONG = "STRONG", BOLD_FONT_STYLE_CLASS = "boldFont";
	private String oITrend = null;
	public String getOITrend() {
		if (null == oITrend) {
		/*if (0 != top1OpenInterestCall && 0 != top1OpenInterestPut && 0 != underlyingPrice
				&& 0 != totalOpenInterestCall & 0 != totalOpenInterestPut) {
			if ((top1OpenInterestCall > top1OpenInterestPut) && (totalOpenInterestCall > totalOpenInterestPut)) {
				if (underlyingPrice < top3StrikePriceCall) {
					oITrend = STRONG_BUY;
				} else if (underlyingPrice < top1StrikePriceCall) {
					oITrend = BUY;
				}
			} else if ((top1OpenInterestPut > top1OpenInterestCall) && (totalOpenInterestPut > totalOpenInterestCall)) {
				if (underlyingPrice > top3StrikePricePut) {
					oITrend = STRONG_SELL;
				} else if (underlyingPrice > top1StrikePricePut) {
					oITrend = SELL;
				}
			}
		}*/
		oITrend = NA;
		/*if (0 != top1OpenInterestCall && 0 != top1OpenInterestPut && 0 != underlyingPrice
				&& 0 != totalOpenInterestCall & 0 != totalOpenInterestPut) {
			if ((top1OpenInterestCall > top1OpenInterestPut) && (totalOpenInterestCall > totalOpenInterestPut)) {
				if (underlyingPrice < top3StrikePriceCall) {
					oITrend = BUY;
				}
			} else if ((top1OpenInterestPut > top1OpenInterestCall) && (totalOpenInterestPut > totalOpenInterestCall)) {
				if (underlyingPrice > top1StrikePricePut) {
					oITrend = SELL;
				}
			}
		}*/
		
		double callOIAvg = 0;
		double putOIAvg = 0;
		double callOIAvgDiff = 0;
		double putOIAvgDiff = 0;
		double callOIStrike1 =0;
		double callOIStrike2 =0;
		double callOIStrike3 =0;
		double putOIStrike1 =0;
		double putOIStrike2 =0;
		double putOIStrike3 =0;
		
		if (null != top1StrikePriceCall &&  null != top2StrikePriceCall &&  null != top3StrikePriceCall) {
		if (top1StrikePriceCall > top2StrikePriceCall) {
			if (top1StrikePriceCall > top3StrikePriceCall) {
				callOIStrike1 = top1StrikePriceCall;
				//callOIStrike2 = top2StrikePriceCall > top3StrikePriceCall ? top2StrikePriceCall : top3StrikePriceCall;
				if (top2StrikePriceCall > top3StrikePriceCall) {
					callOIStrike2 = top2StrikePriceCall;
					callOIStrike3 = top3StrikePriceCall;
				} else {
					callOIStrike2 = top3StrikePriceCall;
					callOIStrike3 = top2StrikePriceCall;
				}
			} else {
				callOIStrike1 = top3StrikePriceCall;
				callOIStrike2 = top1StrikePriceCall;
				callOIStrike3 = top2StrikePriceCall;
			}
		} else if (top3StrikePriceCall > top2StrikePriceCall) {
			callOIStrike1 = top3StrikePriceCall;
			callOIStrike2 = top2StrikePriceCall;
			callOIStrike3 = top1StrikePriceCall;
		} else {
			callOIStrike1 = top2StrikePriceCall;
			//callOIStrike2 = top1StrikePriceCall > top3StrikePriceCall ? top1StrikePriceCall : top3StrikePriceCall;
			if (top1StrikePriceCall > top3StrikePriceCall) {
				callOIStrike2 = top1StrikePriceCall;
				callOIStrike3 = top3StrikePriceCall;
			}else {
				callOIStrike2 =top3StrikePriceCall;
				callOIStrike3 =  top1StrikePriceCall ;
			}
		}
		
		if (top1StrikePricePut > top2StrikePricePut) {
			if (top1StrikePricePut > top3StrikePricePut) {
				putOIStrike1 = top1StrikePricePut;
				//putOIStrike2 = top2StrikePricePut > top3StrikePricePut ? top2StrikePricePut : top3StrikePricePut;
				if (top2StrikePricePut > top3StrikePricePut) {
					putOIStrike2 = top2StrikePricePut;
					putOIStrike3 = top3StrikePricePut;
				} else {
					putOIStrike2 = top3StrikePricePut;
					putOIStrike3 = top2StrikePricePut;
				}
			} else {
				putOIStrike1 = top3StrikePricePut;
				putOIStrike2 = top1StrikePricePut;
				putOIStrike3 = top2StrikePricePut;
			}
		} else if (top3StrikePricePut > top2StrikePricePut) {
			putOIStrike1 = top3StrikePricePut;
			putOIStrike2 = top2StrikePricePut;
			putOIStrike3 = top1StrikePricePut;
		} else {
			putOIStrike1 = top2StrikePricePut;
			//putOIStrike2 = top1StrikePricePut > top3StrikePricePut ? top1StrikePricePut : top3StrikePricePut;
			if (top1StrikePricePut > top3StrikePricePut) {
				putOIStrike2 = top1StrikePricePut;
				putOIStrike3 = top3StrikePricePut;
			}else {
				putOIStrike2 =top3StrikePricePut;
				putOIStrike3 =  top1StrikePricePut ;
			}
		}
		
		callOIAvg = ((top1StrikePriceCall + top2StrikePriceCall + top3StrikePriceCall)/3);
		putOIAvg =  ((top1StrikePricePut + top2StrikePricePut + top3StrikePricePut)/3);
		
		if (callOIAvg != 0) {
			callOIAvgDiff = callOIAvg - underlyingPrice;
		}
		if (putOIAvg != 0) {
			putOIAvgDiff = underlyingPrice - putOIAvg;
		}
		
		if (0 != top1OpenInterestCall && 0 != top1OpenInterestPut && 0 != underlyingPrice
				&& 0 != totalOpenInterestCall & 0 != totalOpenInterestPut) {
			if ((top1OpenInterestCall > top1OpenInterestPut) && (totalOpenInterestCall > totalOpenInterestPut)) {
				if (underlyingPrice < top1StrikePriceCall) {
					oITrend = BUY;
				}
			} else if ((top1OpenInterestPut > top1OpenInterestCall) && (totalOpenInterestPut > totalOpenInterestCall)) {
				if (underlyingPrice > top1StrikePricePut) {
					oITrend = SELL;
				}
			}
			
		if ( 0 != callOIAvgDiff &&  0 != putOIAvgDiff && (putOIAvgDiff < underlyingPrice) && (underlyingPrice< callOIAvg))  {
		if (oITrend == BUY && callOIStrike1 > underlyingPrice && callOIStrike2 > underlyingPrice ) {
			if (callOIAvgDiff > 0 && callOIAvgDiff< putOIAvgDiff) {
				oITrend = STRONG_BUY;
			}
		} else if (oITrend == SELL && putOIStrike2 < underlyingPrice && putOIStrike3 < underlyingPrice ) {
			if (putOIAvgDiff > 0 && callOIAvgDiff > putOIAvgDiff) {
				oITrend = STRONG_SELL;
			}
		}
		}
		/*System.out.println(symbol);
		System.out.println(callOIStrike1);
		System.out.println(callOIStrike2);
		System.out.println(callOIStrike3);
		System.out.println(putOIStrike1);
		System.out.println(putOIStrike2);
		System.out.println(putOIStrike3);*/
		
		//new with new site
		oITrend = NA;
		if (underlyingPrice > top1StrikePriceCall) {
			oITrend = STRONG_SELL;
		} else if (underlyingPrice > top2StrikePriceCall) {
			oITrend = "CAN SELL";
		} else if (underlyingPrice > top3StrikePriceCall) {
			oITrend = SELL;
		} else if (underlyingPrice < top1StrikePricePut) {
			oITrend = STRONG_BUY;
		}  else if (underlyingPrice < top2StrikePricePut) {
			oITrend = "CAN BUY";
		} else if (underlyingPrice < top3StrikePricePut) {
			oITrend = BUY;
		}
	}
		}
	}
		return oITrend;
	}

	public void setOITrend(String oITrend) {
		this.oITrend = oITrend;
	}

	private String style;

	public String getStyle() {
		if (null == style) {
			style = EMPTY_STR;
			if (null != top1StrikePriceCall && underlyingPrice > top1StrikePriceCall) {
				style = "color: red;";
			} else if (underlyingPrice < top1StrikePricePut) {
				style = "color: green;";
			}
		}
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	private Integer lotSize;

	public Integer getLotSize() {
		return lotSize;
	}

	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}
	
	private Boolean indexInd = Boolean.FALSE;

	public Boolean getIndexInd() {
		return indexInd;
	}

	public void setIndexInd(Boolean indexInd) {
		this.indexInd = indexInd;
	}
	
	 
	//For playable concept
	private double strongOrder;
	private Double  ltpOnDecision;
	private Double  ltp;
	private Double  bestEntry;
	private String goForTrade;
	private Boolean  playanleInd = Boolean.FALSE;
	private Double changePercentage; 
	private Integer candleNumber;
	private Boolean parentRecordInd;
	
	public double getStrongOrder() {
		return strongOrder;
	}

	public void setStrongOrder(double strongOrder) {
		this.strongOrder = strongOrder;
	}

	public Double getLtpOnDecision() {
		return ltpOnDecision;
	}

	public void setLtpOnDecision(Double ltpOnDecision) {
		this.ltpOnDecision = ltpOnDecision;
	}

	public Double getLtp() {
		return ltp;
	}

	public void setLtp(Double ltp) {
		this.ltp = ltp;
	}

	public Double getBestEntry() {
		return bestEntry;
	}

	public void setBestEntry(Double bestEntry) {
		this.bestEntry = bestEntry;
	}

	public String getGoForTrade() {
		return goForTrade;
	}

	public void setGoForTrade(String goForTrade) {
		this.goForTrade = goForTrade;
	}

	public Boolean getPlayanleInd() {
		return playanleInd;
	}

	public void setPlayanleInd(Boolean playanleInd) {
		this.playanleInd = playanleInd;
	}

	public Double getChangePercentage() {
		return changePercentage;
	}

	public void setChangePercentage(Double changePercentage) {
		this.changePercentage = changePercentage;
	}

	public Integer getCandleNumber() {
		return candleNumber;
	}

	public void setCandleNumber(Integer candleNumber) {
		this.candleNumber = candleNumber;
	}

	public Boolean getParentRecordInd() {
		return parentRecordInd;
	}

	public void setParentRecordInd(Boolean parentRecordInd) {
		this.parentRecordInd = parentRecordInd;
	}
	
	
	
	private String styleNetChange1 = EMPTY_STR;
	private String styleNetChange2 = EMPTY_STR;
	private String styleNetChange3 = EMPTY_STR;

	public String getStyleNetChange1() {
		return styleNetChange1;
	}
	public void setStyleNetChange1(String styleNetChange1) {
		this.styleNetChange1 = styleNetChange1;
	}
	public String getStyleNetChange2() {
		return styleNetChange2;
	}
	public void setStyleNetChange2(String styleNetChange2) {
		this.styleNetChange2 = styleNetChange2;
	}
	public String getStyleNetChange3() {
		return styleNetChange3;
	}
	public void setStyleNetChange3(String styleNetChange3) {
		this.styleNetChange3 = styleNetChange3;
	}
	
	
	public void handleSortOrderAndStyle() {
		double top1OINetChangeDiff = Math.abs(top1OINetChangeCall - top1OINetChangePut);
		double top2OINetChangeDiff = Math.abs(top2OINetChangeCall - top2OINetChangePut);
		double top3OINetChangeDiff = Math.abs(top3OINetChangeCall - top3OINetChangePut);

		if (top1OINetChangeDiff > top2OINetChangeDiff) {
			if (top1OINetChangeDiff > top3OINetChangeDiff) {
				styleNetChange1 = BOLD_FONT_STYLE_CLASS;
				strongOrder = top1OINetChangeDiff;
			} else {
				styleNetChange3 = BOLD_FONT_STYLE_CLASS;
				strongOrder = top3OINetChangeDiff;
			}
		} else {
			if (top2OINetChangeDiff > top3OINetChangeDiff) {
				styleNetChange2 = BOLD_FONT_STYLE_CLASS;
				strongOrder = top3OINetChangeDiff;
			} else {
				styleNetChange3 = BOLD_FONT_STYLE_CLASS;
				strongOrder = top3OINetChangeDiff;
			}
		}

		//if (null != getOITrend() && getOITrend().startsWith(STARTS_WITH_STRONG)) {
		if (null != oITrend && oITrend.startsWith(STARTS_WITH_STRONG)) {
			if(oITrend.equals(STRONG_BUY)) {
				strongOrder = 10000+strongOrder;
			} else if(oITrend.equals(STRONG_SELL)) {
				strongOrder = 10000-strongOrder;
			}
		}
	}
	
	
	public String getTotalOpenInterestCallStr() {
		return myformatter.format(totalOpenInterestCall);
	}
	public String getTotalOpenInterestPutStr() {
		return myformatter.format(totalOpenInterestPut) ;
	}
	public String getTotalOIVolumesCallStr() {
		return myformatter.format(totalOIVolumesCall) ;
	}
	public String getTotalOIVolumesPutStr() {
		return myformatter.format(totalOIVolumesPut) ;
	}
}