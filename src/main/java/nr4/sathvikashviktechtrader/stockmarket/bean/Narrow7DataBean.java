package nr4.sathvikashviktechtrader.stockmarket.bean;

public class Narrow7DataBean extends StockDataBean {

	private static final long serialVersionUID = 4649182492747317410L;
	private Double day1High;
	private Double day1Low;
	private Double day2High;
	private Double day2Low;
	private Double day3High;
	private Double day3Low;
	private Double day4High;
	private Double day4Low;
	private Double day5High;
	private Double day5Low;
	private Double day6High;
	private Double day6Low;
	private Double day7High;
	private Double day7Low;

	private Double day1Open;
	private Double day1Close;
	private Double day2Open;
	private Double day2Close;
	private Double day3Open;
	private Double day3Close;
	private Double day4Open;
	private Double day4Close;
	private Double day5Open;
	private Double day5Close;
	private Double day6Open;
	private Double day6Close;
	private Double day7Open;
	private Double day7Close;

	private Boolean narrow7RuleInd = Boolean.FALSE;
	private String utTradableState = "NA";

	public Double getDay1High() {
		return day1High;
	}

	public void setDay1High(Double day1High) {
		this.day1High = day1High;
	}

	public Double getDay1Low() {
		return day1Low;
	}

	public void setDay1Low(Double day1Low) {
		this.day1Low = day1Low;
	}

	public Double getDay2High() {
		return day2High;
	}

	public void setDay2High(Double day2High) {
		this.day2High = day2High;
	}

	public Double getDay2Low() {
		return day2Low;
	}

	public void setDay2Low(Double day2Low) {
		this.day2Low = day2Low;
	}

	public Double getDay3High() {
		return day3High;
	}

	public void setDay3High(Double day3High) {
		this.day3High = day3High;
	}

	public Double getDay3Low() {
		return day3Low;
	}

	public void setDay3Low(Double day3Low) {
		this.day3Low = day3Low;
	}

	public Double getDay4High() {
		return day4High;
	}

	public void setDay4High(Double day4High) {
		this.day4High = day4High;
	}

	public Double getDay4Low() {
		return day4Low;
	}

	public void setDay4Low(Double day4Low) {
		this.day4Low = day4Low;
	}

	public Double getDay5High() {
		return day5High;
	}

	public void setDay5High(Double day5High) {
		this.day5High = day5High;
	}

	public Double getDay5Low() {
		return day5Low;
	}

	public void setDay5Low(Double day5Low) {
		this.day5Low = day5Low;
	}

	public Double getDay6High() {
		return day6High;
	}

	public void setDay6High(Double day6High) {
		this.day6High = day6High;
	}

	public Double getDay6Low() {
		return day6Low;
	}

	public void setDay6Low(Double day6Low) {
		this.day6Low = day6Low;
	}

	public Double getDay7High() {
		return day7High;
	}

	public void setDay7High(Double day7High) {
		this.day7High = day7High;
	}

	public Double getDay7Low() {
		return day7Low;
	}

	public void setDay7Low(Double day7Low) {
		this.day7Low = day7Low;
	}

	public Double getDay1Open() {
		return day1Open;
	}

	public void setDay1Open(Double day1Open) {
		this.day1Open = day1Open;
	}

	public Double getDay1Close() {
		return day1Close;
	}

	public void setDay1Close(Double day1Close) {
		this.day1Close = day1Close;
	}

	public Double getDay2Open() {
		return day2Open;
	}

	public void setDay2Open(Double day2Open) {
		this.day2Open = day2Open;
	}

	public Double getDay2Close() {
		return day2Close;
	}

	public void setDay2Close(Double day2Close) {
		this.day2Close = day2Close;
	}

	public Double getDay3Open() {
		return day3Open;
	}

	public void setDay3Open(Double day3Open) {
		this.day3Open = day3Open;
	}

	public Double getDay3Close() {
		return day3Close;
	}

	public void setDay3Close(Double day3Close) {
		this.day3Close = day3Close;
	}

	public Double getDay4Open() {
		return day4Open;
	}

	public void setDay4Open(Double day4Open) {
		this.day4Open = day4Open;
	}

	public Double getDay4Close() {
		return day4Close;
	}

	public void setDay4Close(Double day4Close) {
		this.day4Close = day4Close;
	}

	public Double getDay5Open() {
		return day5Open;
	}

	public void setDay5Open(Double day5Open) {
		this.day5Open = day5Open;
	}

	public Double getDay5Close() {
		return day5Close;
	}

	public void setDay5Close(Double day5Close) {
		this.day5Close = day5Close;
	}

	public Double getDay6Open() {
		return day6Open;
	}

	public void setDay6Open(Double day6Open) {
		this.day6Open = day6Open;
	}

	public Double getDay6Close() {
		return day6Close;
	}

	public void setDay6Close(Double day6Close) {
		this.day6Close = day6Close;
	}

	public Double getDay7Open() {
		return day7Open;
	}

	public void setDay7Open(Double day7Open) {
		this.day7Open = day7Open;
	}

	public Double getDay7Close() {
		return day7Close;
	}

	public void setDay7Close(Double day7Close) {
		this.day7Close = day7Close;
	}

	public Boolean getNarrow7RuleInd() {
		return narrow7RuleInd;
	}

	public void setNarrow7RuleInd(Boolean narrow7RuleInd) {
		this.narrow7RuleInd = narrow7RuleInd;
	}

	public String getUtTradableState() {
		return utTradableState;
	}

	public void setUtTradableState(String utTradableState) {
		this.utTradableState = utTradableState;
	}

	public Narrow7DataBean(Integer lotSize, String symbolName) {
		super(lotSize, symbolName);
	}

	public Narrow7DataBean clone() {
		Narrow7DataBean bean = new Narrow7DataBean(super.getLotSize(), super.getSymbolName());
		bean.setSymbolId(super.getSymbolId());
		bean.setSymbolName(super.getSymbolName());
		bean.setLotSize(super.getLotSize());
		bean.setLtp(super.getLtp());
		bean.setOpen(super.getOpen());
		bean.setHigh(super.getHigh());
		bean.setLow(super.getLow());
		bean.setClose(super.getClose());
		bean.setTradedInAt(super.getTradedInAt());
		bean.setTradedOutAt(super.getTradedOutAt());
		bean.setKiteFutureKey(super.getKiteFutureKey());
		bean.setBuyerSellerDiff(super.getBuyerSellerDiff());
		bean.setSignalTriggeredInAt(super.getSignalTriggeredInAt());
		bean.setSignalTriggeredOutAt(super.getSignalTriggeredOutAt());
		bean.setInstrumentToken(super.getInstrumentToken());

		bean.setTradableState(super.getTradableState());
		bean.setTradedState(super.getTradedState());
		bean.setTradedVal(super.getTradedVal());
		bean.setBuyerAt(super.getBuyerAt());
		bean.setSellerAt(super.getSellerAt());
		bean.setProfitLossAmt(super.getProfitLossAmt());
		bean.setProfitChaseVal(super.getProfitChaseVal());

		bean.setNegativeDirectionLtp(super.getNegativeDirectionLtp());
		bean.setNegativeDirectionLoss(super.getNegativeDirectionLoss());
		bean.setPositiveDirectionLtp(super.getPositiveDirectionLtp());
		bean.setPositiveDirectionProfit(super.getPositiveDirectionProfit());

		bean.setBuyAtVal(super.getBuyAtVal());
		bean.setSellAtVal(super.getSellAtVal());
		bean.setBuyOrSellAt(super.getBuyOrSellAt());
		bean.setStopLoss(super.getStopLoss());
		bean.setTargetPrice(super.getTargetPrice());
		bean.setVolumes(super.getVolumes());
		bean.setKiteOrderId(super.getKiteOrderId());
		bean.setNumberOfLots(super.getNumberOfLots());
		bean.setStrengthTradableState(super.getStrengthTradableState());
		bean.setTradableStrength(super.getTradableStrength());
		bean.setCandle1SizeAmt(super.getCandle1SizeAmt());
		bean.setCandle2SizeAmt(super.getCandle2SizeAmt());
		bean.setCandleHighsDiff(super.getCandleHighsDiff());
		bean.setCandleLowsDiff(super.getCandleLowsDiff());
		bean.setCandle1HighMinusClose(super.getCandle1HighMinusClose());
		bean.setCandle1CloseMinusLow(super.getCandle1CloseMinusLow());
		bean.setCandle2HighMinusClose(super.getCandle2HighMinusClose());
		bean.setCandle2CloseMinusLow(super.getCandle2CloseMinusLow());
		bean.setCandle1Type(super.getCandle1Type());
		bean.setCandle2Type(super.getCandle2Type());
		bean.setOhlcState(super.getOhlcState());
		bean.setTempCustomTradingRuleInd(super.isTempCustomTradingRuleInd());
		bean.setReportKey(super.getReportKey());

		bean.setOi(super.getOi());
		bean.setOiDayHigh(super.getOiDayHigh());
		bean.setOiDayLow(super.getOiDayLow());
		bean.setOi1(super.getOi1());
		bean.setOi2(super.getOi2());
		bean.setOi3(super.getOi3());
		bean.setOiDayHigh1(super.getOiDayHigh1());
		bean.setOiDayHigh2(super.getOiDayHigh2());
		bean.setOiDayHigh3(super.getOiDayHigh3());
		bean.setOiDayLow1(super.getOiDayLow1());
		bean.setOiDayLow2(super.getOiDayLow2());
		bean.setOiDayLow3(super.getOiDayLow3());
		bean.setOiInfo(super.getOiInfo());
		
		bean.setDay1High(this.day1High );
		bean.setDay1Low(this.day1Low );
		bean.setDay2High(this.day2High );
		bean.setDay2Low(this.day2Low );
		bean.setDay3High(this.day3High );
		bean.setDay3Low(this.day3Low );
		bean.setDay4High(this.day4High );
		bean.setDay4Low(this.day4Low );
		bean.setDay5High(this.day5High );
		bean.setDay5Low(this.day5Low );
		bean.setDay6High(this.day6High );
		bean.setDay6Low(this.day6Low );
		bean.setDay7High(this.day7High );
		bean.setDay7Low(this.day7Low );

		bean.setDay1Open(this.day1Open );
		bean.setDay1Close(this.day1Close );
		bean.setDay2Open(this.day2Open );
		bean.setDay2Close(this.day2Close );
		bean.setDay3Open(this.day3Open );
		bean.setDay3Close(this.day3Close );
		bean.setDay4Open(this.day4Open );
		bean.setDay4Close(this.day4Close );
		bean.setDay5Open(this.day5Open );
		bean.setDay5Close(this.day5Close );
		bean.setDay6Open(this.day6Open );
		bean.setDay6Close(this.day6Close );
		bean.setDay7Open(this.day7Open );
		bean.setDay7Close(this.day7Close );

		bean.setNarrow7RuleInd(this.narrow7RuleInd );
		bean.setUtTradableState(this.utTradableState );
		
		bean.setBuyStatus(this.buyStatus);
		bean.setSellStatus(this.sellStatus);
		return bean;
	}
	
	private String buyStatus = "NA";
	private String sellStatus = "NA";

	public String getBuyStatus() {
		return buyStatus;
	}
	public void setBuyStatus(String buyStatus) {
		this.buyStatus = buyStatus;
	}
	public String getSellStatus() {
		return sellStatus;
	}
	public void setSellStatus(String sellStatus) {
		this.sellStatus = sellStatus;
	}
}
