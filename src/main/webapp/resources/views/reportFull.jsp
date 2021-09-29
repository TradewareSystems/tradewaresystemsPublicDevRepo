<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:masterTemplate2>
	<jsp:attribute name="header_area">
		<script type="text/javascript">
			 window.setTimeout(function () {
				 reportScreenAutoRefreshEnableDisable( window.location); 
				 //alert('refreshing......'); window.location.reload();
				}, 30000);
		</script>
	</jsp:attribute>
	<jsp:attribute name="body_area">
            	<div
			style="padding-top: 5px; padding-bottom: 5px; padding-left: 10px; padding-right: 20px;">
				<div
				style="border-radius: 25px; border: 1px solid #d6d6c2; padding: 5px; font-family: Verdana; font-size: 12px;">
        			<a style="float: left;" href='<c:url value = "/"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/home.png"/>" />&nbsp; Home</a>
						&nbsp;&nbsp;
						<a style="float: left;" href='<c:url value = "/filter1000"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/home.png"/>" />&nbsp; Home_2</a>
					<a style="float: left;" href='<c:url value = "/filter1000Temp"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/home.png"/>" />&nbsp; Home_3</a>
						
        			
        			${totalTrades} <span
					style="font: normal; font-family: sans-serif; color: light-blue;">Full Day Report </span>
        			&nbsp;&nbsp;<a href='<c:url value = "/report"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" /></a>
        			${profitLoss}
        			<a style="float: right;" href='<c:url value = "/report"/>'>&nbsp; Running Report</a>
        		</div>
        		</div>
        	<div class="container dataTableContentPanel">
        	<table class="dataTaleHeaderStye rowSpanReportFull"
				id="mytableHeader">
        				<tr class="dataTaleHeaderRowStye">
								<th class="tableRightBorderOverride">Symbols</th>
								<th class="tableRightBorderOverride">Lot Size</th>
								<th colspan="2" class="tableRightBorderOverride">Buy/Sell_SL</th>
								<th colspan="2" class="tableRightBorderOverride">B/S Diff</th>
								<th colspan="2" class="tableRightBorderOverride">Profit/Loss</th>
								<th colspan="2" class="tableRightBorderOverride">Manual Exit</th>
								<th colspan="2" class="tableRightBorderOverride">CNDL/VOL</th>
								<th class="tableRightBorderOverride">Traded At</th>
								<th class="tableRightBorderOverride">Exited At</th>
								<th colspan="2" class="tableRightBorderOverride">Diff/Mov</th>
								<th colspan="2" class="tableRightBorderOverride">CNDL</th>
							</tr>
						<!-- /thead -->
					</table>
					<table class="dataTaleContentStye" id="mytable">
        				<tbody>
						<c:forEach items="${reportList}" var="reportDataBean">
							<tr>
								<td align="center" rowspan="2">
									<a href='<c:url value = "/stockDetails?stockId=${reportDataBean.kiteFutureKey}"/>'>
										${reportDataBean.symbolName} 
									</a>
								</td>
								<td align="center">${reportDataBean.lotSize}</td>
								<td align="right" class="colSpanText">Buy@</td>
								<td align="center">${reportDataBean.buyAtVal}</td>
								<td align="right" class="colSpanText">B/S</td>
								<td align="center">${reportDataBean.buyerSellerDiffVal}</td>
								<td align="right" class="colSpanText">Hrly </td>
								<td align="center"> ${reportDataBean.min60StochasticTrend}</td>
								<td align="right" class="colSpanText">CPRVL</td>
								<td align="center">${reportDataBean.valueCPR}</td>
								<td align="right" class="colSpanText">Rule</td>
								<td align="center"> ${reportDataBean.strategyRule}</td>
								<td rowspan="2" align="center">${reportDataBean.signalTriggeredInAt}</td>
								<td rowspan="4" align="center">${reportDataBean.signalTriggeredOutAt}</td>
								<td align="right" class="colSpanText">HiDiff</td>
								<td align="center">${reportDataBean.candleHighsDiff}</td>
								<td align="right" class="colSpanText">C1</td>
								<td align="center">${reportDataBean.candle3Type}</td>
							</tr>
							
							<tr class="spanRowBackGround">
								<td align="center">${reportDataBean.candleNumber}</td>
								<td align="right" class="colSpanText">Sell@</td>
								<td align="center">${reportDataBean.sellAtVal}</td>
								<td align="right" class="colSpanText">B/S2</td>
								<td align="center">${reportDataBean.buyerSellerDiffVal2}</td>
								<td align="right" class="colSpanText">15Min </td>
								<td align="center"> ${reportDataBean.min15StochasticTrend}</td>
									<td align="right" class="colSpanText">MP/L</td>
									<td align="center">${reportDataBean.manualBookProfitLossAmtVal}</td>
								<td align="right" class="colSpanText">STRN</td>
								<td align="center" class="${reportDataBean.strengthStyleClass}">
									${reportDataBean.strengthableTradeStateDescription}
								</td>
								<td align="right" class="colSpanText">LoDiff</td>
								<td align="center">${reportDataBean.candleLowsDiff}</td>
								<td align="right" class="colSpanText">C2</td>
								<td align="center">${reportDataBean.candle4Type}</td>
							</tr>
							<tr>
								<td align="center">${reportDataBean.itemId} &nbsp;
								<c:choose>
 										<c:when test="${reportDataBean.stochasticBasicRule1Ind}">
								    		<input class="stylishCheckbox" type="checkbox" 
												value="${reportDataBean.stochasticBasicRule1Ind}" 
												checked="checked" disabled="disabled"/> 
								    	</c:when>
									</c:choose>
								</td>
								<td align="center">${reportDataBean.tradedLotCount}</td>
								<td align="right" class="colSpanText">AvgHi</td>
								<td align="center">${reportDataBean.avgHigh1min}</td>
								<td align="right" class="colSpanText">Gap</td>
								<td align="center">${reportDataBean.gapUpDownMoveVal}</td>
								<td align="right" class="colSpanText">5Min </td>
								<td align="center"> ${reportDataBean.min5StochasticTrend}</td>
									<td align="right" class="colSpanText">MEx@</td>
									<td align="center">${reportDataBean.manualExitedAtVal}</td>
								<td align="right" class="colSpanText">VwapSTRN</td>
								<td align="center" class="${reportDataBean.volStrengthStyleClass}">
									${reportDataBean.vwapTradeStateDescription}
								</td>
								<td align="center" class="blueBold">${reportDataBean.profitLossAmtVal}</td>
								<td align="right" class="colSpanText">C1_MOV</td>
								<td align="center">${reportDataBean.candle3SizeAmt}</td>
								<td align="right" class="colSpanText">C1_H-C</td>
								<td align="center">${reportDataBean.candle3HighMinusClose}</td>
							</tr>
							<tr class="spanRowBackGround">
								<td align="center">${reportDataBean.candleTimeFrame}</td>
								<td align="center">
									<c:choose>
 										<c:when test="${reportDataBean.tradingRuleForwardEngulfingLvl2Ind}">
								    		<input class="stylishCheckbox" type="checkbox" 
												value="${reportDataBean.tradingRuleForwardEngulfingLvl2Ind}" 
												checked="checked" disabled="disabled"/> 
								    	</c:when>
									</c:choose>
									<c:choose>
 										<c:when test="${reportDataBean.tradingRuleForwardEngulfingLvl3Ind}">
								    		<input class="stylishCheckbox" type="checkbox" 
												value="${reportDataBean.tradingRuleForwardEngulfingLvl3Ind}" 
												checked="checked" disabled="disabled"/> 
								    	</c:when>
									</c:choose>
								</td>
								<td align="right" class="colSpanText">AvgLo</td>
								<td align="center">${reportDataBean.avgLow1min}</td>
								<td align="right" class="colSpanText">BQnt</td>
								<td align="center">${reportDataBean.buyerQuantityVal}</td>
								<td align="center">${reportDataBean.min60StochasticValK1}</td>
								<td align="center">${reportDataBean.min60StochasticValD3}</td>
									<td align="right" class="colSpanText">Vol</td>
									<td align="center">${reportDataBean.volumes}</td>
								<td align="right" class="colSpanText">VolSTRN</td>
								<td align="center" class="${reportDataBean.volStrengthStyleClass}">
									${reportDataBean.volumeTradeStateDescription}
								</td>
								<td align="center">
									<span class="greenBold"> ${reportDataBean.positiveDirectionProfit} </span> 
									/ <span class="redBold">${reportDataBean.negativeDirectionLoss} </span>
								</td>
								
								<td align="right" class="colSpanText">C2_MOV</td>
								<td align="center">${reportDataBean.candle4SizeAmt}</td>
								<td align="right" class="colSpanText">C1_C-L</td>
								<td align="center">${reportDataBean.candle3CloseMinusLow}</td>
							</tr>
							<tr>
								<td align="center">${reportDataBean.tradeType}</td>
								<td align="center">
									<c:choose>
 										<c:when test="${reportDataBean.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd}">
								    		<input class="stylishCheckbox" type="checkbox" 
												value="${reportDataBean.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2RuleProdInd}" 
												checked="checked" disabled="disabled"/> 
								    	</c:when>
									</c:choose> |
									<c:choose>
 										<c:when test="${reportDataBean.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd}">
								    		<input class="stylishCheckbox" type="checkbox" 
												value="${reportDataBean.tradeSmaAndVwapAndOpenBtwnAvgHiLoLvl2Min5RuleProdInd}" 
												checked="checked" disabled="disabled"/> 
								    	</c:when>
									</c:choose>
								</td>
								<td align="right" class="colSpanText">AvgClso</td>
								<td align="center">${reportDataBean.avgClose1min}</td>
								<td align="right" class="colSpanText">SQnt</td>
								<td align="center">${reportDataBean.sellerQuantityVal}</td>
								<td align="center">${reportDataBean.min15StochasticValK1}</td>
								<td align="center">${reportDataBean.min15StochasticValD3}</td>
									<td align="right" class="colSpanText">B/S_%</td>
									<td align="center">${reportDataBean.buySellQuantityRatio}</td>
								<td align="right" class="colSpanText">VWAP/CPR</td>
								<td align="center">${reportDataBean.vwapValue}/${reportDataBean.cprPivotalPoint}</td>
								<td align="center">
									<span class="greenFont"> ${reportDataBean.positiveDirectionLtp} </span> 
									/ <span class="redFont">${reportDataBean.negativeDirectionLtp} </span>
								</td>
								<td align="right" class="colSpanText">TRGT::${reportDataBean.targetPrice}</td>
								<td align="right" class="colSpanText">HrCrs</td>
								<td align="center">
									<c:choose>
  										<c:when test="${reportDataBean.prevDayHrCrossInd}">
									    	<input class="stylishCheckbox" type="checkbox" 
											value="${reportDataBean.prevDayHrCrossInd}" checked="checked" disabled="disabled"/> 
									    </c:when>
									    <c:otherwise>
    										 <input class="stylishCheckbox" type="checkbox" 
											 value="${reportDataBean.prevDayHrCrossInd}" disabled="disabled"/>
  										</c:otherwise>
									</c:choose>
								</td>
								<td align="right" class="colSpanText">C2_H-C</td>
								<td align="center">${reportDataBean.candle4HighMinusClose}</td>
							</tr>
							<tr class="spanRowBackGround">
								<td align="center"> TBD </td>
								<td align="center"> TBD </td>
								<td align="right" class="colSpanText">AvgOpn</td>
								<td align="center">${reportDataBean.currentCandleOpen}</td>
								<td align="right" class="colSpanText">OPT</td>
								<td align="center"> </td>
								<td align="center">${reportDataBean.min5StochasticValK1}</td>
								<td align="center">${reportDataBean.min5StochasticValD3}</td>
								<td align="center"> Pivt </td>
								<td align="center"> ${reportDataBean.cprPivotalPoint}</td>
								<td align="right" class="colSpanText">CPR LB/UB</td>
								<td align="center">${reportDataBean.cprLowerBound}/${reportDataBean.cprUpperBound}</td>
								<td align="center">Trd@:${reportDataBean.tradedAtAvgVal}/SL:${reportDataBean.stopLoss}</td>
								<td align="center">
									<div class="linkAsButtonDiv linkAsButtonPadding">
										<c:choose>
	  										<c:when test="${reportDataBean.manualTradeExitInd}">
										    	<input class="stylishCheckbox" type="checkbox" 
												value="${reportDataBean.manualTradeExitInd}" checked="checked" disabled="disabled"/> 
										    </c:when>
										    <c:otherwise>
	    										 <input class="stylishCheckbox" type="checkbox" 
												 value="${reportDataBean.manualTradeExitInd}" disabled="disabled"/>
	  										</c:otherwise>
										</c:choose>
										<c:choose>
	  										<c:when test="${reportDataBean.manualTradeExitInd}">
										    	<span class="linkAsButtonPadding"> CLOSED </span> 
										    </c:when>
										    <c:when test="${reportDataBean.tradedStateId eq 'BUY' or reportDataBean.tradedStateId eq 'SELL'}">
	    										 <a class="linkAsButton" onclick="ajaxManualClose(this, event); return false;" 
	    										 href='<c:url value = "/manualTradeClose?tradeId=${reportDataBean.itemId}"/>'>
	    										 Close</a>
	  										</c:when>
										</c:choose>
									</div>
								</td>
								
								<td align="right" class="colSpanText">TBD</td>
								<td align="center"> TBD</td>
								<td align="right" class="colSpanText">C2_C-L</td>
								<td align="center">${reportDataBean.candle4CloseMinusLow}</td>
							</tr>
							
							<tr>
								<td colspan="4" align="center"> ${reportDataBean.tradableStateDescription} 
									&nbsp; / ${reportDataBean.tradedStateDescription} </td>
									
								<td colspan="4" align="center">OHLC: ${reportDataBean.ohlcStateDescription} </td>
								<td colspan="2" align="center">CNDL: ${reportDataBean.candleTypeTrendDescription} </td>
								<td colspan="2" align="center">HKNASH: ${reportDataBean.heikinAshiTrendDescription}</td>
								<td colspan="2" align="center">${reportDataBean.tradePlaceRule}</td>
								<td colspan="4" align="center">${reportDataBean.min5HeikinAshiTrendIdDescription}</td>
							</tr>
							<tr class="spanRowBackGround spanBottomRowBorder">
								<td colspan="3" align="center">${reportDataBean.heikinAshiTrendIdMin60} </td>
								<td colspan="3" align="center">${reportDataBean.heikinAshiTrendIdMin15} </td>
								<td colspan="3" align="center">${reportDataBean.heikinAshiTrendIdMin5} </td>
								<td colspan="3" align="center"> ${reportDataBean.trendTradableStateId} </td>
								<td colspan="3" align="center"> </td>
								<td colspan="3" align="center"> </td>
							</tr>
						</c:forEach>
						</tbody>
					</table>	
			</div>
	</jsp:attribute>
</t:masterTemplate2>