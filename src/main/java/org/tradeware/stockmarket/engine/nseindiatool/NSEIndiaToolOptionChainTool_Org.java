package org.tradeware.stockmarket.engine.nseindiatool;

import java.io.IOException;
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
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NSEIndiaToolOptionChainTool_Org {
	private static NSEIndiaToolOptionChainTool_Org singletonTool;
	private static WebClient webClient = null;

	public static NSEIndiaToolOptionChainTool_Org getInstance() {
		if (null == singletonTool) {
			webClient = new WebClient();
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setCssEnabled(false);
			singletonTool = new NSEIndiaToolOptionChainTool_Org();
		}
		return singletonTool;
	}

	/*private NSEIndiaToolOptionChainTool() {
		//retriveOptionChainDataForEachStock("ACC");
	}*/
	
	public NSEIndiaToolOptionChainTool_Org() {
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
	
	private static final String URL = "https://nseindia.com/marketinfo/sym_map/symbolMapping.jsp?instrument=OPTSTK&date=-&segmentLink=17&symbol="; 
	public OptionChainDataBean retriveOptionChainDataForEachStock(OptionChainDataBean bean) {//(String symbol) {
	//	OptionChainDataBean bean  = new OptionChainDataBean(symbol);
		try {
			clearIndexes();
			HtmlPage page = webClient
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
			updateOptionChainDataBean (bean, tableRows);
			
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

		} catch (Exception e) {
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
	
	private String getCellValue(String cellValue) {
		if (null == cellValue || cellValue.trim().length() == 0 || INVALID_OPEN_INTREST_VAL.equals(cellValue)) {
			return "0";
		} else {
			return cellValue.replaceAll(",", "");
		}
	}
	
	//private static final String OI_SPRUTS_URL = "https://nseindia.com/live_market/dynaContent/live_analysis/oi_spurts/topPositiveOIChangeData.json";
	private static final String OI_SPRUTS_URL = "https://www1.nseindia.com/live_market/dynaContent/live_analysis/oi_spurts/topPositiveOIChangeData.json";
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
					bean.handleSortOrderAndStyle();
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
