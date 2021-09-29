package org.tradeware.stockmarket.engine.nseindiatool;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tradeware.stockmarket.bean.OptionChainDataBean;
import org.tradeware.stockmarket.util.StockUtil;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NSEIndiaToolOptionChainToolNewSite {
	private static NSEIndiaToolOptionChainToolNewSite singletonTool;
	private static WebClient webClient = null;

	public static NSEIndiaToolOptionChainToolNewSite getInstance() {
		if (null == singletonTool) {
			webClient = new WebClient();
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setCssEnabled(false);
			singletonTool = new NSEIndiaToolOptionChainToolNewSite();
		}
		return singletonTool;
	}

	/*private NSEIndiaToolOptionChainToolNewSite() {
		//retriveOptionChainDataForEachStock("ACC");
	}*/
	
	public NSEIndiaToolOptionChainToolNewSite() {
		//retriveOptionChainDataForEachStock("ACC");
	}
	String INVALID_OPEN_INTREST_VAL = "-";
	int top1CallOIRowIndex = 0, top2CallOIRowIndex = 0, top3CallOIRowIndex = 0;
	int top1PutOIRowIndex = 0, top2PutOIRowIndex = 0, top3PutOIRowIndex = 0;
	double top1CallOI = 0, top2CallOI = 0, top3CallOI = 0;
	double top1PutOI = 0, top2PutOI = 0, top3PutOI = 0;
	
	private void clearIndexes() {
		top1CallOIRowIndex = 0;
		top2CallOIRowIndex = 0;
		top3CallOIRowIndex = 0;
		top1PutOIRowIndex = 0;
		top2PutOIRowIndex = 0;
		top3PutOIRowIndex = 0;
		top1CallOI = 0;
		top2CallOI = 0;
		top3CallOI = 0;
		top1PutOI = 0;
		top2PutOI = 0;
		top3PutOI = 0;
	}
	JSONParser jsonParser = new JSONParser();
	//private static final String URL = "https://nseindia.com/marketinfo/sym_map/symbolMapping.jsp?instrument=OPTSTK&date=-&segmentLink=17&symbol=";
	private static final String URL = "https://www.nseindia.com/api/option-chain-equities?symbol=";
	private static final String URLINDEX = "https://www.nseindia.com/api/option-chain-indices?symbol=";
	public OptionChainDataBean retriveOptionChainDataForEachStock(OptionChainDataBean bean) {//(String symbol) {
	//	OptionChainDataBean bean  = new OptionChainDataBean(symbol);
		try {
			clearIndexes();
			/*HtmlPage page = webClient
					.getPage(URL+StockUtil.getSymbolForURL(bean.getSymbol()));//+symbol);

			HtmlTable optionChainTable = (HtmlTable) ((HtmlPage)page).getElementById("octable");
			List<HtmlTableRow> tableRows = optionChainTable.getBodies().get(0).getRows();
			
			
			// for (HtmlTableRow tableRow : tableRows) {
			for (int i = 1; i < tableRows.size()-1; i++) {
				List<HtmlTableCell> rowElementDataCells = tableRows.get(i).getCells();
				 
				HtmlTableRow tableRow = tableRows.get(i);
				if (i == 1) {
					bean.setStrikePrice1(Double.valueOf(rowElementDataCells.get(11).asText().replaceAll(",", "")));
				} else if (i == 2) {
					bean.setStrikePrice2(Double.valueOf(rowElementDataCells.get(11).asText().replaceAll(",", "")));
					bean.setTickerSize(bean.getStrikePrice2() - bean.getStrikePrice1());
				}

				String callOI = rowElementDataCells.get(1).asText().replaceAll(",", "");
				String putOI = rowElementDataCells.get(21).asText().replaceAll(",", "");
				
				String callOI = rowElementDataCells.get(1).asText().replaceAll(",", "");
				String putOI = rowElementDataCells.get(21).asText().replaceAll(",", "");
				*/
			
			webClient.getPage("https://www.nseindia.com");
			
			String dataURL = URL + StockUtil.getSymbolForURL(bean.getSymbol());
			if (null != bean.getIndexInd() && bean.getIndexInd()) {
				dataURL = URLINDEX + StockUtil.getSymbolForURL(bean.getSymbol().replaceAll(SPACE, EMPTY_STRING));
			}
			String pageResponse = webClient.getPage(dataURL).getWebResponse().getContentAsString();
			
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(pageResponse);
			JSONObject rawJsonDataArrayFiltered = (JSONObject) rawJsonData.get(JSON_DATA_KEY_FILTERED);
			//JSONObject rawJsonDataArrayBaseRecords = (JSONObject) rawJsonData.get(JSON_DATA_KEY_RECORDS);
			if (null != rawJsonDataArrayFiltered) {
			JSONArray rawJsonDataArray = (JSONArray) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_DATA);
			for (int i = 0; i < rawJsonDataArray.size(); i++) {
				JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
				if (/*null == bean.getStrikePrice1()*/0 == bean.getStrikePrice1()) {
					bean.setStrikePrice1(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_STRIKE_PRICE))));
				} else if (/*null == bean.getStrikePrice2()*/0 == bean.getStrikePrice2()) {
					bean.setStrikePrice2(Double.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_STRIKE_PRICE))));
					bean.setTickerSize(bean.getStrikePrice2() - bean.getStrikePrice1());
				}
				
				Object callData =  jsonSymbolObject.get(JSON_DATA_KEY_CE);
				Object putData = jsonSymbolObject.get(JSON_DATA_KEY_PE);
if(null != callData && null != putData) {
				String callOI = getCellValue(((JSONObject) callData).get(JSON_DATA_KEY_OPEN_INTREST));
				String putOI = getCellValue(((JSONObject)putData).get(JSON_DATA_KEY_OPEN_INTREST));
				bean.setUnderlyingPrice(Double.valueOf(String.valueOf(((JSONObject) callData).get(JSON_DATA_KEY_UNDERLYING_VALUE))));
				
				// handle CALLS Data
				if (callOI != null && !"".equals(callOI) && !INVALID_OPEN_INTREST_VAL.equals(callOI)) {
					if (0 == top1CallOIRowIndex) {
						top1CallOIRowIndex = i;
						top1CallOI = Double.valueOf(callOI);
					} else if (0 == top2CallOIRowIndex) {
						double temp2CallOI = Double.valueOf(callOI);
						if (top1CallOI > temp2CallOI) {
							top2CallOI = temp2CallOI;
							top2CallOIRowIndex = i;
						} else {
							top2CallOI = new Double(top1CallOI);
							top1CallOI = new Double(temp2CallOI);
							top2CallOIRowIndex = new Integer(top1CallOIRowIndex);
							top1CallOIRowIndex = new Integer(i);
						}
					} else if (0 == top3CallOIRowIndex) {
						double temp3CallOI = Double.valueOf(callOI);
						if (top1CallOI > temp3CallOI) {
							if (top2CallOI > temp3CallOI) {
								top3CallOI = temp3CallOI;
								top3CallOIRowIndex = i;
							} else {
								top3CallOI = new Double(top2CallOI);
								top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
								top2CallOI = new Double(temp3CallOI);
								top2CallOIRowIndex = i;
							}
						} else {
							top3CallOI = new Double(top2CallOI);
							top2CallOI = new Double(top1CallOI);
							top1CallOI = new Double(temp3CallOI);
							top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
							top2CallOIRowIndex = new Integer(top1CallOIRowIndex);
							top1CallOIRowIndex = new Integer(i);
						}
					} else {
						double tempCallOI = Double.valueOf(callOI);
						if (top1CallOI > tempCallOI) {
							if (top2CallOI > tempCallOI) {
								if (top3CallOI > tempCallOI) {
									// Do nothing
								} else {
									top3CallOI = new Double(tempCallOI);
									top3CallOIRowIndex = new Integer(i);
								}
							} else {
								top3CallOI = new Double(top2CallOI);
								top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
								top2CallOI = new Double(tempCallOI);
								top2CallOIRowIndex = new Integer(i);
							}
						} else {
							top3CallOI = new Double(top2CallOI);
							top3CallOIRowIndex = new Integer(top2CallOIRowIndex);
							top2CallOI = new Double(top1CallOI);
							top2CallOIRowIndex = new Integer(top1CallOIRowIndex);
							top1CallOI = new Double(tempCallOI);
							top1CallOIRowIndex = new Integer(i);
						}
					}

				}

				// handle PUTS Data
				if (putOI != null && !"".equals(putOI) && !INVALID_OPEN_INTREST_VAL.equals(putOI)) {
					if (0 == top1PutOIRowIndex) {
						top1PutOIRowIndex = i;
						top1PutOI = Double.valueOf(putOI);
					} else if (0 == top2PutOIRowIndex) {
						double temp2PutOI = Double.valueOf(putOI);
						if (top1PutOI > temp2PutOI) {
							top2PutOI = temp2PutOI;
							top2PutOIRowIndex = i;
						} else {
							top2PutOI = new Double(top1PutOI);
							top1PutOI = new Double(temp2PutOI);
							top2PutOIRowIndex = new Integer(top1PutOIRowIndex);
							top1PutOIRowIndex = new Integer(i);
						}
					} else if (0 == top3PutOIRowIndex) {
						double temp3PutOI = Double.valueOf(putOI);
						if (top1PutOI > temp3PutOI) {
							if (top2PutOI > temp3PutOI) {
								top3PutOI = temp3PutOI;
								top3PutOIRowIndex = i;
							} else {
								top3PutOI = new Double(top2PutOI);
								top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
								top2PutOI = new Double(temp3PutOI);
								top2PutOIRowIndex = i;
							}
						} else {
							top3PutOI = new Double(top2PutOI);
							top2PutOI = new Double(top1PutOI);
							top1PutOI = new Double(temp3PutOI);
							top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
							top2PutOIRowIndex = new Integer(top1PutOIRowIndex);
							top1PutOIRowIndex = new Integer(i);
						}
					} else {
						double tempPutOI = Double.valueOf(putOI);
						if (top1PutOI > tempPutOI) {
							if (top2PutOI > tempPutOI) {
								if (top3PutOI > tempPutOI) {
									// Do nothing
								} else {
									top3PutOI = new Double(tempPutOI);
									top3PutOIRowIndex = new Integer(i);
								}
							} else {
								top3PutOI = new Double(top2PutOI);
								top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
								top2PutOI = new Double(tempPutOI);
								top2PutOIRowIndex = new Integer(i);
							}
						} else {
							top3PutOI = new Double(top2PutOI);
							top3PutOIRowIndex = new Integer(top2PutOIRowIndex);
							top2PutOI = new Double(top1PutOI);
							top2PutOIRowIndex = new Integer(top1PutOIRowIndex);
							top1PutOI = new Double(tempPutOI);
							top1PutOIRowIndex = new Integer(i);
						}
					}
				}
				}
			}
			// updateOptionChainDataBean (bean, tableRows);
			bean.setTotalOpenInterestCall((Double.valueOf(getCellValue(String.valueOf(
					((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_CE)).get(JSON_DATA_KEY_TOTAL_OI)))))
					* bean.getLotSize());
			bean.setTotalOIVolumesCall(Double.valueOf(getCellValue(String.valueOf(
					((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_CE)).get(JSON_DATA_KEY_TOTAL_VOLUMES)))));

			bean.setTotalOpenInterestPut((Double.valueOf(getCellValue(String.valueOf(
					((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_PE)).get(JSON_DATA_KEY_TOTAL_OI)))))
					* bean.getLotSize());
			bean.setTotalOIVolumesPut(Double.valueOf(getCellValue(String.valueOf(
					((JSONObject) rawJsonDataArrayFiltered.get(JSON_DATA_KEY_PE)).get(JSON_DATA_KEY_TOTAL_VOLUMES)))));
			updateOptionChainDataBeanNewSite(bean, rawJsonDataArray);
			
			/*String result = "Top1OpenInterestCall:"+bean.getTop1OpenInterestCall()+" ,Top1OpenInterestChangeCall:"+bean.getTop1OpenInterestChangeCall()
			+" ,Top1OIVolumesCall:"+bean.getTop1OIVolumesCall()+" ,Top1OINetChangeCall:"+bean.getTop1OINetChangeCall()+" ,Top1StrikePriceCall:"+bean.getTop1StrikePriceCall();
			String result2 = "Top2OpenInterestCall:"+bean.getTop2OpenInterestCall()+" ,Top2OpenInterestChangeCall:"+bean.getTop2OpenInterestChangeCall()
			+" ,Top2OIVolumesCall:"+bean.getTop2OIVolumesCall()+" ,Top2OINetChangeCall:"+bean.getTop2OINetChangeCall()+" ,Top2StrikePriceCall:"+bean.getTop2StrikePriceCall();
			String result3 = "Top3OpenInterestCall:"+bean.getTop3OpenInterestCall()+" ,Top3OpenInterestChangeCall:"+bean.getTop3OpenInterestChangeCall()
			+" ,Top3OIVolumesCall:"+bean.getTop3OIVolumesCall()+" ,Top3OINetChangeCall:"+bean.getTop3OINetChangeCall()+" ,Top3StrikePriceCall:"+bean.getTop3StrikePriceCall();
			String result4 = "Top1OpenInterestPut:"+bean.getTop1OpenInterestPut()+" ,Top1OpenInterestChangePut:"+bean.getTop1OpenInterestChangePut()
			+" ,Top1OIVolumesPut:"+bean.getTop1OIVolumesPut()+" ,Top1OINetChangePut:"+bean.getTop1OINetChangePut()+" ,Top1StrikePricePut:"+bean.getTop1StrikePricePut();
			String result5 = "Top2OpenInterestPut:"+bean.getTop2OpenInterestPut()+" ,Top2OpenInterestChangePut:"+bean.getTop2OpenInterestChangePut()
			+" ,Top2OIVolumesPut:"+bean.getTop2OIVolumesPut()+" ,Top2OINetChangePut:"+bean.getTop2OINetChangePut()+" ,Top2StrikePricePut:"+bean.getTop2StrikePricePut();
			String result6 = "Top3OpenInterestPut:"+bean.getTop3OpenInterestPut()+" ,Top3OpenInterestChangePut:"+bean.getTop3OpenInterestChangePut()
			+" ,Top3OIVolumesPut:"+bean.getTop3OIVolumesPut()+" ,Top3OINetChangePut:"+bean.getTop3OINetChangePut()+" ,Top3StrikePricePut:"+bean.getTop3StrikePricePut();
			String result7 = "TotalOpenInterestCall:"+bean.getTotalOpenInterestCall()+" ,TotalOIVolumesCall:"+bean.getTotalOIVolumesCall()
			+" ,TotalOpenInterestPut:"+bean.getTotalOpenInterestPut()+" ,TotalOIVolumesCall:"+bean.getTotalOIVolumesPut();
			System.out.println(result);
			System.out.println(result2);
			System.out.println(result3);
			System.out.println(result4);
			System.out.println(result5);
			System.out.println(result6);
			System.out.println(result7);*/
		} else {
			System.out.println(bean.getSymbol()+"  --  Data not retrived properly....");
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR At retriveOptionChainDataForEachStock() : "+bean.getSymbol() + "   -    " + e.getMessage());
		}
		return bean;
	}
	
	private void updateOptionChainDataBean(OptionChainDataBean bean, List<HtmlTableRow> tableRows) {
		List<HtmlTableCell> rowElementDataCells = tableRows.get(top1CallOIRowIndex).getCells();
		bean.setTop1OpenInterestCall(new Double(getCellValue(rowElementDataCells.get(1).asText())));
		bean.setTop1OpenInterestChangeCall(new Double(getCellValue(rowElementDataCells.get(2).asText())));
		bean.setTop1OIVolumesCall(new Double(getCellValue(rowElementDataCells.get(3).asText())));
		bean.setTop1OINetChangeCall(new Double(getCellValue(rowElementDataCells.get(6).asText())));
		bean.setTop1StrikePriceCall(new Double(getCellValue(rowElementDataCells.get(11).asText())));
		
		List<HtmlTableCell> row2ElementDataCells = tableRows.get(top2CallOIRowIndex).getCells();
		bean.setTop2OpenInterestCall(new Double(getCellValue(row2ElementDataCells.get(1).asText())));
		bean.setTop2OpenInterestChangeCall(new Double(getCellValue(row2ElementDataCells.get(2).asText())));
		bean.setTop2OIVolumesCall(new Double(getCellValue(row2ElementDataCells.get(3).asText())));
		bean.setTop2OINetChangeCall(new Double(getCellValue(row2ElementDataCells.get(6).asText())));
		bean.setTop2StrikePriceCall(new Double(getCellValue(row2ElementDataCells.get(11).asText())));
		
		List<HtmlTableCell> row3ElementDataCells = tableRows.get(top3CallOIRowIndex).getCells();
		bean.setTop3OpenInterestCall(new Double(getCellValue(row3ElementDataCells.get(1).asText())));
		bean.setTop3OpenInterestChangeCall(new Double(getCellValue(row3ElementDataCells.get(2).asText())));
		bean.setTop3OIVolumesCall(new Double(getCellValue(row3ElementDataCells.get(3).asText())));
		bean.setTop3OINetChangeCall(new Double(getCellValue(row3ElementDataCells.get(6).asText())));
		bean.setTop3StrikePriceCall(new Double(getCellValue(row3ElementDataCells.get(11).asText())));
		
		List<HtmlTableCell> rowTotalElementDataCells = tableRows.get(tableRows.size()-1).getCells();
		bean.setTotalOpenInterestCall(new Double(getCellValue(rowTotalElementDataCells.get(1).asText())));
		bean.setTotalOIVolumesCall(new Double(getCellValue(rowTotalElementDataCells.get(3).asText())));
		
		//Put Data
		List<HtmlTableCell> row1PutElementDataCells = tableRows.get(top1PutOIRowIndex).getCells();
		bean.setTop1OpenInterestPut(new Double(getCellValue(row1PutElementDataCells.get(21).asText())));
		bean.setTop1OpenInterestChangePut(new Double(getCellValue(row1PutElementDataCells.get(20).asText())));
		bean.setTop1OIVolumesPut(new Double(getCellValue(row1PutElementDataCells.get(19).asText())));
		bean.setTop1OINetChangePut(new Double(getCellValue(row1PutElementDataCells.get(16).asText())));
		bean.setTop1StrikePricePut(new Double(getCellValue(row1PutElementDataCells.get(11).asText())));
		
		List<HtmlTableCell> row2PutElementDataCells = tableRows.get(top2PutOIRowIndex).getCells();
		bean.setTop2OpenInterestPut(new Double(getCellValue(row2PutElementDataCells.get(21).asText())));
		bean.setTop2OpenInterestChangePut(new Double(getCellValue(row2PutElementDataCells.get(20).asText())));
		bean.setTop2OIVolumesPut(new Double(getCellValue(row2PutElementDataCells.get(19).asText())));
		bean.setTop2OINetChangePut(new Double(getCellValue(row2PutElementDataCells.get(16).asText())));
		bean.setTop2StrikePricePut(new Double(getCellValue(row2PutElementDataCells.get(11).asText())));
		
		List<HtmlTableCell> row3PutElementDataCells = tableRows.get(top3PutOIRowIndex).getCells();
		bean.setTop3OpenInterestPut(new Double(getCellValue(row3PutElementDataCells.get(21).asText())));
		bean.setTop3OpenInterestChangePut(new Double(getCellValue(row3PutElementDataCells.get(20).asText())));
		bean.setTop3OIVolumesPut(new Double(getCellValue(row3PutElementDataCells.get(19).asText())));
		bean.setTop3OINetChangePut(new Double(getCellValue(row3PutElementDataCells.get(16).asText())));
		bean.setTop3StrikePricePut(new Double(getCellValue(row3PutElementDataCells.get(11).asText())));
		
		List<HtmlTableCell> rowPutTotalElementDataCells = tableRows.get(tableRows.size()-1).getCells();
		bean.setTotalOpenInterestPut(new Double(getCellValue(rowPutTotalElementDataCells.get(7).asText())));
		bean.setTotalOIVolumesPut(new Double(getCellValue(rowPutTotalElementDataCells.get(5).asText())));
	}
	
	private void updateOptionChainDataBeanNewSite(OptionChainDataBean bean, JSONArray rawJsonDataArray) {
		JSONObject callData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top1CallOIRowIndex))
				.get(JSON_DATA_KEY_CE);
		bean.setTop1OpenInterestCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_OPEN_INTREST)))))
						* bean.getLotSize());
		bean.setTop1OpenInterestChangeCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());
		bean.setTop1OIVolumesCall(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop1OINetChangeCall(
				getRoundupToTwoValue(Double.valueOf(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop1StrikePriceCall(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_STRIKE_PRICE)))));

		callData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top2CallOIRowIndex)).get(JSON_DATA_KEY_CE);
		bean.setTop2OpenInterestCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_OPEN_INTREST)))))
						* bean.getLotSize());
		bean.setTop2OpenInterestChangeCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());
		bean.setTop2OIVolumesCall(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop2OINetChangeCall(
				getRoundupToTwoValue(Double.valueOf(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop2StrikePriceCall(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_STRIKE_PRICE)))));

		callData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top3CallOIRowIndex)).get(JSON_DATA_KEY_CE);
		bean.setTop3OpenInterestCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_OPEN_INTREST)))))
						* bean.getLotSize());
		bean.setTop3OpenInterestChangeCall(
				(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());
		bean.setTop3OIVolumesCall(
				new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop3OINetChangeCall(
				getRoundupToTwoValue(Double.valueOf(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop3StrikePriceCall(new Double(getCellValue(String.valueOf(callData.get(JSON_DATA_KEY_STRIKE_PRICE)))));
		
		/*List<HtmlTableCell> rowTotalElementDataCells = tableRows.get(tableRows.size()-1).getCells();
		bean.setTotalOpenInterestCall(new Double(getCellValue(rowTotalElementDataCells.get(1).asText())));
		bean.setTotalOIVolumesCall(new Double(getCellValue(rowTotalElementDataCells.get(3).asText())));*/
		
		//Put Data
		JSONObject putData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top1PutOIRowIndex)).get(JSON_DATA_KEY_PE);
		bean.setTop1OpenInterestPut((new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_OPEN_INTREST)))))
				* bean.getLotSize());
		bean.setTop1OpenInterestChangePut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());
		bean.setTop1OIVolumesPut(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop1OINetChangePut(
				getRoundupToTwoValue(Double.valueOf(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop1StrikePricePut(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_STRIKE_PRICE)))));

		putData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top2PutOIRowIndex)).get(JSON_DATA_KEY_PE);
		bean.setTop2OpenInterestPut((new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_OPEN_INTREST)))))
				* bean.getLotSize());
		bean.setTop2OpenInterestChangePut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());
		bean.setTop2OIVolumesPut(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop2OINetChangePut(
				getRoundupToTwoValue(Double.valueOf(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop2StrikePricePut(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_STRIKE_PRICE)))));

		putData = (JSONObject) ((JSONObject) rawJsonDataArray.get(top3PutOIRowIndex)).get(JSON_DATA_KEY_PE);
		bean.setTop3OpenInterestPut((new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_OPEN_INTREST)))))
				* bean.getLotSize());
		bean.setTop3OpenInterestChangePut(
				(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST)))))
						* bean.getLotSize());
		bean.setTop3OIVolumesPut(
				new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_TOTAL_TRADED_VOLUME)))));
		bean.setTop3OINetChangePut(
				getRoundupToTwoValue(Double.valueOf(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_CHANGE_NET_CHANGE))))));
		bean.setTop3StrikePricePut(new Double(getCellValue(String.valueOf(putData.get(JSON_DATA_KEY_STRIKE_PRICE)))));
		
		/*List<HtmlTableCell> rowPutTotalElementDataCells = tableRows.get(tableRows.size()-1).getCells();
		bean.setTotalOpenInterestPut(new Double(getCellValue(rowPutTotalElementDataCells.get(7).asText())));
		bean.setTotalOIVolumesPut(new Double(getCellValue(rowPutTotalElementDataCells.get(5).asText())));*/
		
		bean.getOITrend();//this additional call as new nseindia site has underlying price in json data
	}
	
	private String getCellValue(String cellValue) {
		if (null == cellValue || cellValue.trim().length() == 0 || INVALID_OPEN_INTREST_VAL.equals(cellValue)) {
			return "0";
		} else {
			return cellValue.replaceAll(",", "");
		}
	}
	
	private String getCellValue(Object cellValueObj) {
		if (null == cellValueObj) {
			return "0";
		} else {
			String cellValue = String.valueOf(cellValueObj);
			if (cellValue.trim().length() == 0 || INVALID_OPEN_INTREST_VAL.equals(cellValue)) {
				return "0";
			} else {
				return cellValue.replaceAll(",", "");
			}
		}
	}
	
	public double getRoundupToTwoValue(double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.DOWN).doubleValue();
	}
	
	//private static final String OI_SPRUTS_URL = "https://nseindia.com/live_market/dynaContent/live_analysis/oi_spurts/topPositiveOIChangeData.json";
	private static final String OI_SPRUTS_URL = "https://www1.nseindia.com/live_market/dynaContent/live_analysis/oi_spurts/topPositiveOIChangeData.json";
	private static final String JSON_DATA_KEY_RECORDS = "records";
	private static final String JSON_DATA_KEY_FILTERED = "filtered";
	private static final String JSON_DATA_KEY_DATA = "data";
	private static final String JSON_DATA_KEY_SYMBOL = "symbol";
	private static final String JSON_DATA_KEY_OI_DAY1 = "latestOI";
	private static final String JSON_DATA_KEY_OI_DAY2 = "prevOI";
	private static final String JSON_DATA_KEY_OI_CHANGE = "oiChange";
	private static final String JSON_DATA_KEY_OI_CHANGE_PER = "percOIchange";
	private static final String JSON_DATA_KEY_OI_VOLUMES = "volume";
	private static final String JSON_DATA_KEY_UNDERLYING_PRICE = "underlying";
	private static final String COMMA = ",";
	private static final String EMPTY_STRING = "";
	private static final String SPACE = "";
	
	private static final String JSON_DATA_KEY_CE = "CE";
	private static final String JSON_DATA_KEY_PE = "PE";
	private static final String JSON_DATA_KEY_TOTAL_OI = "totOI";
	private static final String JSON_DATA_KEY_TOTAL_VOLUMES = "totVol";
	private static final String JSON_DATA_KEY_OPEN_INTREST = "openInterest";
	private static final String JSON_DATA_KEY_CHANGE_IN_OPEN_INTEREST = "changeinOpenInterest";
	private static final String JSON_DATA_KEY_TOTAL_TRADED_VOLUME  = "totalTradedVolume";
	private static final String JSON_DATA_KEY_IMPLIED_VOLATILITY = "impliedVolatility";
	private static final String JSON_DATA_KEY_CHANGE_NET_CHANGE = "change";
	private static final String JSON_DATA_KEY_UNDERLYING_VALUE = "underlyingValue";
	private static final String JSON_DATA_KEY_TOTAL_BUY_QUANTITY = "totalBuyQuantity";
	private static final String JSON_DATA_KEY_TOTAL_SELL_QUANTITY = "totalSellQuantity";
	private static final String JSON_DATA_KEY_PCHANGE = "pChange";
	private static final String JSON_DATA_KEY_PCHANGE_IN_OPEN_INTEREST  ="pchangeinOpenInterest";
	
	private static final String JSON_DATA_KEY_STRIKE_PRICE = "strikePrice";
	
	public Hashtable<String, OptionChainDataBean> retriveOptionChainSpurtsDataForEachStock(
			Hashtable<String, OptionChainDataBean> mapOptionChain) {
		try {
			String response = webClient.getPage(OI_SPRUTS_URL).getWebResponse().getContentAsString();
			JSONParser jsonParser = new JSONParser();
			JSONObject rawJsonData = (JSONObject) jsonParser.parse(response);
			JSONArray rawJsonDataArray = (JSONArray) rawJsonData.get(JSON_DATA_KEY_DATA);
			for (int i = 0; i < rawJsonDataArray.size(); i++) {
				JSONObject jsonSymbolObject = (JSONObject) rawJsonDataArray.get(i);
				String symbol = String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_SYMBOL));
//System.out.println(symbol);
				if (mapOptionChain.containsKey(symbol)) {
					OptionChainDataBean bean = mapOptionChain.get(symbol);
					bean.setDay1OpenInterest(Double
							.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_OI_DAY1)).replaceAll(COMMA, EMPTY_STRING)));
					bean.setDay2OpenInterest(Double
							.valueOf(String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_OI_DAY2)).replaceAll(COMMA, EMPTY_STRING)));
					bean.setOpenInterestChange(Double.valueOf(
							String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_OI_CHANGE)).replaceAll(COMMA, EMPTY_STRING)));
					bean.setOpenInterestChangePercentage(Double.valueOf(
							String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_OI_CHANGE_PER)).replaceAll(COMMA, EMPTY_STRING)));
					bean.setOpenInterestVolumes(Double.valueOf(
							String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_OI_VOLUMES)).replaceAll(COMMA, EMPTY_STRING)));
					bean.setUnderlyingPrice(Double.valueOf(
							String.valueOf(jsonSymbolObject.get(JSON_DATA_KEY_UNDERLYING_PRICE)).replaceAll(COMMA, EMPTY_STRING)));
					bean.getOITrend();
					mapOptionChain.replace(symbol, bean);
				}
			}
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapOptionChain;
	}
}
