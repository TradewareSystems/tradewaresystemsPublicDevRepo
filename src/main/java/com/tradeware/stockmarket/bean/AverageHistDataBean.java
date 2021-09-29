package com.tradeware.stockmarket.bean;

public class AverageHistDataBean extends AbstractTradeDataBean {

	private static final long serialVersionUID = -5856817312630449360L;

	private Double avgLowMin1;
	private Double avgHighMin1;
	private Double avgCloseMin1;
	private Double avgLowMin5;
	private Double avgHighMin5;
	private Double avgCloseMin5;
	private String min5HeikinAshiTrendId;
	
	private Double min60StochasticValK1;
	private Double min60StochasticValD3;
	private Double min60StochasticValK2;
	private Double min60StochasticValD4;
	private Double min60StochasticValK3;
	private Double min60StochasticValD5;
	private String min60StochasticTrend;

	private Double min15StochasticValK1;
	private Double min15StochasticValD3;
	private Double min15StochasticValK2;
	private Double min15StochasticValD4;
	private Double min15StochasticValK3;
	private Double min15StochasticValD5;
	private String min15StochasticTrend;

	private Double min5StochasticValK1;
	private Double min5StochasticValD3;
	private Double min5StochasticValK2;
	private Double min5StochasticValD4;
	private Double min5StochasticValK3;
	private Double min5StochasticValD5;
	private String min5StochasticTrend;
	
	private Double rsi1;
	private Double rsi2;
	private Double rsi3;
	
	public AverageHistDataBean(Integer lotSize, String symbolId) {
		super(lotSize, symbolId);
	}

	public AverageHistDataBean(Integer lotSize, String symbolId, String kiteFutureKey) {
		super(lotSize, symbolId);
		super.setKiteFutureKey(kiteFutureKey);
	}
	public Double getAvgLowMin1() {
		return avgLowMin1;
	}
	public void setAvgLowMin1(Double avgLowMin1) {
		this.avgLowMin1 = avgLowMin1;
	}
	public Double getAvgHighMin1() {
		return avgHighMin1;
	}
	public void setAvgHighMin1(Double avgHighMin1) {
		this.avgHighMin1 = avgHighMin1;
	}
	public Double getAvgCloseMin1() {
		return avgCloseMin1;
	}
	public void setAvgCloseMin1(Double avgCloseMin1) {
		this.avgCloseMin1 = avgCloseMin1;
	}
	public Double getAvgLowMin5() {
		return avgLowMin5;
	}
	public void setAvgLowMin5(Double avgLowMin5) {
		this.avgLowMin5 = avgLowMin5;
	}
	public Double getAvgHighMin5() {
		return avgHighMin5;
	}
	public void setAvgHighMin5(Double avgHighMin5) {
		this.avgHighMin5 = avgHighMin5;
	}
	public Double getAvgCloseMin5() {
		return avgCloseMin5;
	}
	public void setAvgCloseMin5(Double avgCloseMin5) {
		this.avgCloseMin5 = avgCloseMin5;
	}
	public String getMin5HeikinAshiTrendId() {
		return min5HeikinAshiTrendId;
	}

	public void setMin5HeikinAshiTrendId(String min5HeikinAshiTrendId) {
		this.min5HeikinAshiTrendId = min5HeikinAshiTrendId;
	}

	public Double getMin60StochasticValK1() {
		return min60StochasticValK1;
	}

	public void setMin60StochasticValK1(Double min60StochasticValK1) {
		this.min60StochasticValK1 = min60StochasticValK1;
	}

	public Double getMin60StochasticValD3() {
		return min60StochasticValD3;
	}

	public void setMin60StochasticValD3(Double min60StochasticValD3) {
		this.min60StochasticValD3 = min60StochasticValD3;
	}

	public Double getMin60StochasticValK2() {
		return min60StochasticValK2;
	}

	public void setMin60StochasticValK2(Double min60StochasticValK2) {
		this.min60StochasticValK2 = min60StochasticValK2;
	}

	public Double getMin60StochasticValD4() {
		return min60StochasticValD4;
	}

	public void setMin60StochasticValD4(Double min60StochasticValD4) {
		this.min60StochasticValD4 = min60StochasticValD4;
	}

	public Double getMin60StochasticValK3() {
		return min60StochasticValK3;
	}

	public void setMin60StochasticValK3(Double min60StochasticValK3) {
		this.min60StochasticValK3 = min60StochasticValK3;
	}

	public Double getMin60StochasticValD5() {
		return min60StochasticValD5;
	}

	public void setMin60StochasticValD5(Double min60StochasticValD5) {
		this.min60StochasticValD5 = min60StochasticValD5;
	}

	public String getMin60StochasticTrend() {
		return min60StochasticTrend;
	}

	public void setMin60StochasticTrend(String min60StochasticTrend) {
		this.min60StochasticTrend = min60StochasticTrend;
	}

	public Double getMin15StochasticValK1() {
		return min15StochasticValK1;
	}

	public void setMin15StochasticValK1(Double min15StochasticValK1) {
		this.min15StochasticValK1 = min15StochasticValK1;
	}

	public Double getMin15StochasticValD3() {
		return min15StochasticValD3;
	}

	public void setMin15StochasticValD3(Double min15StochasticValD3) {
		this.min15StochasticValD3 = min15StochasticValD3;
	}

	public Double getMin15StochasticValK2() {
		return min15StochasticValK2;
	}

	public void setMin15StochasticValK2(Double min15StochasticValK2) {
		this.min15StochasticValK2 = min15StochasticValK2;
	}

	public Double getMin15StochasticValD4() {
		return min15StochasticValD4;
	}

	public void setMin15StochasticValD4(Double min15StochasticValD4) {
		this.min15StochasticValD4 = min15StochasticValD4;
	}

	public Double getMin15StochasticValK3() {
		return min15StochasticValK3;
	}

	public void setMin15StochasticValK3(Double min15StochasticValK3) {
		this.min15StochasticValK3 = min15StochasticValK3;
	}

	public Double getMin15StochasticValD5() {
		return min15StochasticValD5;
	}

	public void setMin15StochasticValD5(Double min15StochasticValD5) {
		this.min15StochasticValD5 = min15StochasticValD5;
	}

	public String getMin15StochasticTrend() {
		return min15StochasticTrend;
	}

	public void setMin15StochasticTrend(String min15StochasticTrend) {
		this.min15StochasticTrend = min15StochasticTrend;
	}

	public Double getMin5StochasticValK1() {
		return min5StochasticValK1;
	}

	public void setMin5StochasticValK1(Double min5StochasticValK1) {
		this.min5StochasticValK1 = min5StochasticValK1;
	}

	public Double getMin5StochasticValD3() {
		return min5StochasticValD3;
	}

	public void setMin5StochasticValD3(Double min5StochasticValD3) {
		this.min5StochasticValD3 = min5StochasticValD3;
	}

	public Double getMin5StochasticValK2() {
		return min5StochasticValK2;
	}

	public void setMin5StochasticValK2(Double min5StochasticValK2) {
		this.min5StochasticValK2 = min5StochasticValK2;
	}

	public Double getMin5StochasticValD4() {
		return min5StochasticValD4;
	}

	public void setMin5StochasticValD4(Double min5StochasticValD4) {
		this.min5StochasticValD4 = min5StochasticValD4;
	}

	public Double getMin5StochasticValK3() {
		return min5StochasticValK3;
	}

	public void setMin5StochasticValK3(Double min5StochasticValK3) {
		this.min5StochasticValK3 = min5StochasticValK3;
	}

	public Double getMin5StochasticValD5() {
		return min5StochasticValD5;
	}

	public void setMin5StochasticValD5(Double min5StochasticValD5) {
		this.min5StochasticValD5 = min5StochasticValD5;
	}

	public String getMin5StochasticTrend() {
		return min5StochasticTrend;
	}

	public void setMin5StochasticTrend(String min5StochasticTrend) {
		this.min5StochasticTrend = min5StochasticTrend;
	}

	public Double getRsi1() {
		return rsi1;
	}

	public void setRsi1(Double rsi1) {
		this.rsi1 = rsi1;
	}

	public Double getRsi2() {
		return rsi2;
	}

	public void setRsi2(Double rsi2) {
		this.rsi2 = rsi2;
	}

	public Double getRsi3() {
		return rsi3;
	}

	public void setRsi3(Double rsi3) {
		this.rsi3 = rsi3;
	}
	
	
	private String heikinAshiTrendIdMin5;
	private String heikinAshiTrendIdMin15;
	private String heikinAshiTrendIdMin60;
	
	private Double heikenAshiOpen3Min5;  
	private Double heikenAshiClose3Min5; 
	private Double heikenAshiHigh3Min5;  
	private Double heikenAshiLow3Min5;  
	private Double heikenAshiOpen2Min5;  
	private Double heikenAshiClose2Min5; 
	private Double heikenAshiHigh2Min5;  
	private Double heikenAshiLow2Min5;  
	private Double heikenAshiOpen1Min5;  
	private Double heikenAshiClose1Min5; 
	private Double heikenAshiHigh1Min5;  
	private Double heikenAshiLow1Min5; 
	private Double heikenAshiOpen3Min15;  
	private Double heikenAshiClose3Min15; 
	private Double heikenAshiHigh3Min15;  
	private Double heikenAshiLow3Min15;  
	private Double heikenAshiOpen2Min15;  
	private Double heikenAshiClose2Min15; 
	private Double heikenAshiHigh2Min15;  
	private Double heikenAshiLow2Min15;  
	private Double heikenAshiOpen1Min15;  
	private Double heikenAshiClose1Min15; 
	private Double heikenAshiHigh1Min15;  
	private Double heikenAshiLow1Min15;
	private Double heikenAshiOpen3Min60;  
	private Double heikenAshiClose3Min60; 
	private Double heikenAshiHigh3Min60;  
	private Double heikenAshiLow3Min60;  
	private Double heikenAshiOpen2Min60;  
	private Double heikenAshiClose2Min60; 
	private Double heikenAshiHigh2Min60;  
	private Double heikenAshiLow2Min60;  
	private Double heikenAshiOpen1Min60;  
	private Double heikenAshiClose1Min60; 
	private Double heikenAshiHigh1Min60;  
	private Double heikenAshiLow1Min60;
	public String getHeikinAshiTrendIdMin5() {
		return heikinAshiTrendIdMin5;
	}
	public void setHeikinAshiTrendIdMin5(String heikinAshiTrendIdMin5) {
		this.heikinAshiTrendIdMin5 = heikinAshiTrendIdMin5;
	}
	public String getHeikinAshiTrendIdMin15() {
		return heikinAshiTrendIdMin15;
	}
	public void setHeikinAshiTrendIdMin15(String heikinAshiTrendIdMin15) {
		this.heikinAshiTrendIdMin15 = heikinAshiTrendIdMin15;
	}
	public String getHeikinAshiTrendIdMin60() {
		return heikinAshiTrendIdMin60;
	}
	public void setHeikinAshiTrendIdMin60(String heikinAshiTrendIdMin60) {
		this.heikinAshiTrendIdMin60 = heikinAshiTrendIdMin60;
	}
	public Double getHeikenAshiOpen3Min5() {
		return heikenAshiOpen3Min5;
	}
	public void setHeikenAshiOpen3Min5(Double heikenAshiOpen3Min5) {
		this.heikenAshiOpen3Min5 = heikenAshiOpen3Min5;
	}
	public Double getHeikenAshiClose3Min5() {
		return heikenAshiClose3Min5;
	}
	public void setHeikenAshiClose3Min5(Double heikenAshiClose3Min5) {
		this.heikenAshiClose3Min5 = heikenAshiClose3Min5;
	}
	public Double getHeikenAshiHigh3Min5() {
		return heikenAshiHigh3Min5;
	}
	public void setHeikenAshiHigh3Min5(Double heikenAshiHigh3Min5) {
		this.heikenAshiHigh3Min5 = heikenAshiHigh3Min5;
	}
	public Double getHeikenAshiLow3Min5() {
		return heikenAshiLow3Min5;
	}
	public void setHeikenAshiLow3Min5(Double heikenAshiLow3Min5) {
		this.heikenAshiLow3Min5 = heikenAshiLow3Min5;
	}
	public Double getHeikenAshiOpen2Min5() {
		return heikenAshiOpen2Min5;
	}
	public void setHeikenAshiOpen2Min5(Double heikenAshiOpen2Min5) {
		this.heikenAshiOpen2Min5 = heikenAshiOpen2Min5;
	}
	public Double getHeikenAshiClose2Min5() {
		return heikenAshiClose2Min5;
	}
	public void setHeikenAshiClose2Min5(Double heikenAshiClose2Min5) {
		this.heikenAshiClose2Min5 = heikenAshiClose2Min5;
	}
	public Double getHeikenAshiHigh2Min5() {
		return heikenAshiHigh2Min5;
	}
	public void setHeikenAshiHigh2Min5(Double heikenAshiHigh2Min5) {
		this.heikenAshiHigh2Min5 = heikenAshiHigh2Min5;
	}
	public Double getHeikenAshiLow2Min5() {
		return heikenAshiLow2Min5;
	}
	public void setHeikenAshiLow2Min5(Double heikenAshiLow2Min5) {
		this.heikenAshiLow2Min5 = heikenAshiLow2Min5;
	}
	public Double getHeikenAshiOpen1Min5() {
		return heikenAshiOpen1Min5;
	}
	public void setHeikenAshiOpen1Min5(Double heikenAshiOpen1Min5) {
		this.heikenAshiOpen1Min5 = heikenAshiOpen1Min5;
	}
	public Double getHeikenAshiClose1Min5() {
		return heikenAshiClose1Min5;
	}
	public void setHeikenAshiClose1Min5(Double heikenAshiClose1Min5) {
		this.heikenAshiClose1Min5 = heikenAshiClose1Min5;
	}
	public Double getHeikenAshiHigh1Min5() {
		return heikenAshiHigh1Min5;
	}
	public void setHeikenAshiHigh1Min5(Double heikenAshiHigh1Min5) {
		this.heikenAshiHigh1Min5 = heikenAshiHigh1Min5;
	}
	public Double getHeikenAshiLow1Min5() {
		return heikenAshiLow1Min5;
	}
	public void setHeikenAshiLow1Min5(Double heikenAshiLow1Min5) {
		this.heikenAshiLow1Min5 = heikenAshiLow1Min5;
	}
	public Double getHeikenAshiOpen3Min15() {
		return heikenAshiOpen3Min15;
	}
	public void setHeikenAshiOpen3Min15(Double heikenAshiOpen3Min15) {
		this.heikenAshiOpen3Min15 = heikenAshiOpen3Min15;
	}
	public Double getHeikenAshiClose3Min15() {
		return heikenAshiClose3Min15;
	}
	public void setHeikenAshiClose3Min15(Double heikenAshiClose3Min15) {
		this.heikenAshiClose3Min15 = heikenAshiClose3Min15;
	}
	public Double getHeikenAshiHigh3Min15() {
		return heikenAshiHigh3Min15;
	}
	public void setHeikenAshiHigh3Min15(Double heikenAshiHigh3Min15) {
		this.heikenAshiHigh3Min15 = heikenAshiHigh3Min15;
	}
	public Double getHeikenAshiLow3Min15() {
		return heikenAshiLow3Min15;
	}
	public void setHeikenAshiLow3Min15(Double heikenAshiLow3Min15) {
		this.heikenAshiLow3Min15 = heikenAshiLow3Min15;
	}
	public Double getHeikenAshiOpen2Min15() {
		return heikenAshiOpen2Min15;
	}
	public void setHeikenAshiOpen2Min15(Double heikenAshiOpen2Min15) {
		this.heikenAshiOpen2Min15 = heikenAshiOpen2Min15;
	}
	public Double getHeikenAshiClose2Min15() {
		return heikenAshiClose2Min15;
	}
	public void setHeikenAshiClose2Min15(Double heikenAshiClose2Min15) {
		this.heikenAshiClose2Min15 = heikenAshiClose2Min15;
	}
	public Double getHeikenAshiHigh2Min15() {
		return heikenAshiHigh2Min15;
	}
	public void setHeikenAshiHigh2Min15(Double heikenAshiHigh2Min15) {
		this.heikenAshiHigh2Min15 = heikenAshiHigh2Min15;
	}
	public Double getHeikenAshiLow2Min15() {
		return heikenAshiLow2Min15;
	}
	public void setHeikenAshiLow2Min15(Double heikenAshiLow2Min15) {
		this.heikenAshiLow2Min15 = heikenAshiLow2Min15;
	}
	public Double getHeikenAshiOpen1Min15() {
		return heikenAshiOpen1Min15;
	}
	public void setHeikenAshiOpen1Min15(Double heikenAshiOpen1Min15) {
		this.heikenAshiOpen1Min15 = heikenAshiOpen1Min15;
	}
	public Double getHeikenAshiClose1Min15() {
		return heikenAshiClose1Min15;
	}
	public void setHeikenAshiClose1Min15(Double heikenAshiClose1Min15) {
		this.heikenAshiClose1Min15 = heikenAshiClose1Min15;
	}
	public Double getHeikenAshiHigh1Min15() {
		return heikenAshiHigh1Min15;
	}
	public void setHeikenAshiHigh1Min15(Double heikenAshiHigh1Min15) {
		this.heikenAshiHigh1Min15 = heikenAshiHigh1Min15;
	}
	public Double getHeikenAshiLow1Min15() {
		return heikenAshiLow1Min15;
	}
	public void setHeikenAshiLow1Min15(Double heikenAshiLow1Min15) {
		this.heikenAshiLow1Min15 = heikenAshiLow1Min15;
	}
	public Double getHeikenAshiOpen3Min60() {
		return heikenAshiOpen3Min60;
	}
	public void setHeikenAshiOpen3Min60(Double heikenAshiOpen3Min60) {
		this.heikenAshiOpen3Min60 = heikenAshiOpen3Min60;
	}
	public Double getHeikenAshiClose3Min60() {
		return heikenAshiClose3Min60;
	}
	public void setHeikenAshiClose3Min60(Double heikenAshiClose3Min60) {
		this.heikenAshiClose3Min60 = heikenAshiClose3Min60;
	}
	public Double getHeikenAshiHigh3Min60() {
		return heikenAshiHigh3Min60;
	}
	public void setHeikenAshiHigh3Min60(Double heikenAshiHigh3Min60) {
		this.heikenAshiHigh3Min60 = heikenAshiHigh3Min60;
	}
	public Double getHeikenAshiLow3Min60() {
		return heikenAshiLow3Min60;
	}
	public void setHeikenAshiLow3Min60(Double heikenAshiLow3Min60) {
		this.heikenAshiLow3Min60 = heikenAshiLow3Min60;
	}
	public Double getHeikenAshiOpen2Min60() {
		return heikenAshiOpen2Min60;
	}
	public void setHeikenAshiOpen2Min60(Double heikenAshiOpen2Min60) {
		this.heikenAshiOpen2Min60 = heikenAshiOpen2Min60;
	}
	public Double getHeikenAshiClose2Min60() {
		return heikenAshiClose2Min60;
	}
	public void setHeikenAshiClose2Min60(Double heikenAshiClose2Min60) {
		this.heikenAshiClose2Min60 = heikenAshiClose2Min60;
	}
	public Double getHeikenAshiHigh2Min60() {
		return heikenAshiHigh2Min60;
	}
	public void setHeikenAshiHigh2Min60(Double heikenAshiHigh2Min60) {
		this.heikenAshiHigh2Min60 = heikenAshiHigh2Min60;
	}
	public Double getHeikenAshiLow2Min60() {
		return heikenAshiLow2Min60;
	}
	public void setHeikenAshiLow2Min60(Double heikenAshiLow2Min60) {
		this.heikenAshiLow2Min60 = heikenAshiLow2Min60;
	}
	public Double getHeikenAshiOpen1Min60() {
		return heikenAshiOpen1Min60;
	}
	public void setHeikenAshiOpen1Min60(Double heikenAshiOpen1Min60) {
		this.heikenAshiOpen1Min60 = heikenAshiOpen1Min60;
	}
	public Double getHeikenAshiClose1Min60() {
		return heikenAshiClose1Min60;
	}
	public void setHeikenAshiClose1Min60(Double heikenAshiClose1Min60) {
		this.heikenAshiClose1Min60 = heikenAshiClose1Min60;
	}
	public Double getHeikenAshiHigh1Min60() {
		return heikenAshiHigh1Min60;
	}
	public void setHeikenAshiHigh1Min60(Double heikenAshiHigh1Min60) {
		this.heikenAshiHigh1Min60 = heikenAshiHigh1Min60;
	}
	public Double getHeikenAshiLow1Min60() {
		return heikenAshiLow1Min60;
	}
	public void setHeikenAshiLow1Min60(Double heikenAshiLow1Min60) {
		this.heikenAshiLow1Min60 = heikenAshiLow1Min60;
	}
}
