package com.tradeware.stockmarket.bean;

public class StrategyOrbVwapAndVolumeInfoDataBean extends StrategyOrbMultilevelTradingDataBean {

	private static final long serialVersionUID = -5952254517027794854L;
	protected Integer vwapVolumeDetailId;
	protected Double vwapValue;//In other entity also
	protected String volumeTradeStateId;////In other entity also//@oneToOne
	private String volumeTradeStateDescription; // refer with LKP
	private String vwapTradeStateId;
	private String vwapTradeStateDescription;
	protected String volStrengthStyleClass;
	
	protected String vwapTradeVolInfo;    //, separated volume string not required in entity
	protected String vwapTradeVolRatioInfo;//, separated volume ration string not required in entity
	
	
	protected Long volume4;
	protected Long volume3;
	protected Long volume2;
	protected Long volume1;
	protected Long smallVolume;
	protected Double vol4Ratio;
	protected Double vol3Ratio;
	protected Double vol2Ratio;
	protected Double vol1Ratio;
	
	public StrategyOrbVwapAndVolumeInfoDataBean clone(StrategyOrbVwapAndVolumeInfoDataBean bean) {
		super.clone(bean);
		bean.setVwapVolumeDetailId(this.vwapVolumeDetailId);
		bean.setVwapValue(this.vwapValue); //In other entity also
		bean.setVolumeTradeStateId(this.volumeTradeStateId);  //In other entity also//@oneToOne
		bean.setVolumeTradeStateDescription(this.volumeTradeStateDescription); // refer with LKP
		bean.setVwapTradeStateId(this.vwapTradeStateId);
		bean.setVwapTradeStateDescription(this.vwapTradeStateDescription);
		bean.setVolStrengthStyleClass(this.volStrengthStyleClass);	
		bean.setVwapTradeVolInfo(this.vwapTradeVolInfo);    //, separated volume string not required in entity
		bean.setVwapTradeVolRatioInfo(this.vwapTradeVolRatioInfo); //, separated volume ration string not required in entity
		bean.setVolume4(this.volume4);
		bean.setVolume3(this.volume3);
		bean.setVolume2(this.volume2);
		bean.setVolume1(this.volume1);
		bean.setSmallVolume(this.smallVolume);
		bean.setVol4Ratio(this.vol4Ratio);
		bean.setVol3Ratio(this.vol3Ratio);
		bean.setVol2Ratio(this.vol2Ratio);
		bean.setVol1Ratio(this.vol1Ratio);
		return bean; 
	}
	
	public Integer getVwapVolumeDetailId() {
		return vwapVolumeDetailId;
	}
	public void setVwapVolumeDetailId(Integer vwapVolumeDetailId) {
		this.vwapVolumeDetailId = vwapVolumeDetailId;
	}
	public Double getVwapValue() {
		return vwapValue;
	}
	public void setVwapValue(Double vwapValue) {
		this.vwapValue = vwapValue;
	}
	public String getVolumeTradeStateId() {
		return volumeTradeStateId;
	}
	public void setVolumeTradeStateId(String volumeTradeStateId) {
		this.volumeTradeStateId = volumeTradeStateId;
	}
	public String getVolumeTradeStateDescription() {
		return volumeTradeStateDescription;
	}
	public void setVolumeTradeStateDescription(String volumeTradeStateDescription) {
		this.volumeTradeStateDescription = volumeTradeStateDescription;
	}
	public String getVwapTradeStateId() {
		return vwapTradeStateId;
	}
	public void setVwapTradeStateId(String vwapTradeStateId) {
		this.vwapTradeStateId = vwapTradeStateId;
	}
	public String getVwapTradeStateDescription() {
		return vwapTradeStateDescription;
	}
	public void setVwapTradeStateDescription(String vwapTradeStateDescription) {
		this.vwapTradeStateDescription = vwapTradeStateDescription;
	}
	public String getVolStrengthStyleClass() {
		return volStrengthStyleClass;
	}
	public void setVolStrengthStyleClass(String volStrengthStyleClass) {
		this.volStrengthStyleClass = volStrengthStyleClass;
	}
	public String getVwapTradeVolInfo() {
		return vwapTradeVolInfo;
	}
	public void setVwapTradeVolInfo(String vwapTradeVolInfo) {
		this.vwapTradeVolInfo = vwapTradeVolInfo;
	}
	public String getVwapTradeVolRatioInfo() {
		return vwapTradeVolRatioInfo;
	}
	public void setVwapTradeVolRatioInfo(String vwapTradeVolRatioInfo) {
		this.vwapTradeVolRatioInfo = vwapTradeVolRatioInfo;
	}
	public Long getVolume4() {
		return volume4;
	}
	public void setVolume4(Long volume4) {
		this.volume4 = volume4;
	}
	public Long getVolume3() {
		return volume3;
	}
	public void setVolume3(Long volume3) {
		this.volume3 = volume3;
	}
	public Long getVolume2() {
		return volume2;
	}
	public void setVolume2(Long volume2) {
		this.volume2 = volume2;
	}
	public Long getVolume1() {
		return volume1;
	}
	public void setVolume1(Long volume1) {
		this.volume1 = volume1;
	}
	public Long getSmallVolume() {
		return smallVolume;
	}
	public void setSmallVolume(Long smallVolume) {
		this.smallVolume = smallVolume;
	}
	public Double getVol4Ratio() {
		return vol4Ratio;
	}
	public void setVol4Ratio(Double vol4Ratio) {
		this.vol4Ratio = vol4Ratio;
	}
	public Double getVol3Ratio() {
		return vol3Ratio;
	}
	public void setVol3Ratio(Double vol3Ratio) {
		this.vol3Ratio = vol3Ratio;
	}
	public Double getVol2Ratio() {
		return vol2Ratio;
	}
	public void setVol2Ratio(Double vol2Ratio) {
		this.vol2Ratio = vol2Ratio;
	}
	public Double getVol1Ratio() {
		return vol1Ratio;
	}
	public void setVol1Ratio(Double vol1Ratio) {
		this.vol1Ratio = vol1Ratio;
	}
	@Override
	public String toString() {
		return "StrategyOrbVwapAndVolumeInfoDataBean [vwapVolumeDetailId=" + vwapVolumeDetailId + ", vwapValue="
				+ vwapValue + ", volumeTradeStateId=" + volumeTradeStateId + ", volumeTradeStateDescription="
				+ volumeTradeStateDescription + ", vwapTradeStateId=" + vwapTradeStateId
				+ ", vwapTradeStateDescription=" + vwapTradeStateDescription + ", volStrengthStyleClass="
				+ volStrengthStyleClass + ", vwapTradeVolInfo=" + vwapTradeVolInfo + ", vwapTradeVolRatioInfo="
				+ vwapTradeVolRatioInfo + ", volume4=" + volume4 + ", volume3=" + volume3 + ", volume2=" + volume2
				+ ", volume1=" + volume1 + ", smallVolume=" + smallVolume + ", vol4Ratio=" + vol4Ratio + ", vol3Ratio="
				+ vol3Ratio + ", vol2Ratio=" + vol2Ratio + ", vol1Ratio=" + vol1Ratio + "]" +"\r\n"+super.toString();
	}
	
	
}
