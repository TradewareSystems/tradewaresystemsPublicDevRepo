<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:masterTemplate2>
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
					src="<c:url value="/resources/static/images/home.png"/>" />&nbsp; filter1000</a>
						
        			
        			${totalTrades} <span
					style="font: normal; font-family: sans-serif; color: light-blue;">Full Day Report </span>
        			&nbsp;&nbsp;<a href='<c:url value = "/report"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" /></a>
        			${profitLoss}
        			<a style="float: right;" href='<c:url value = "/report"/>'>&nbsp; Running Report</a>
        		</div>
        		</div>
        		<div class="container"
			style="width: 99%; max-height: 92%; overflow-x: scroll; padding-left: 10px;">
        			<table
				style="min-height: 90%; width: 100%; position: sticky; top: 0;"
				class=" " id="mytableHeader">
        				<!--  thead class="header"-->
        					<tr style="background: lightgrey;">
								<th>Symbols</th>
								<th>Lot Size</th>
								<th>Tradable State</th>
								<th>OI TREND</th>
								<th>Buy/Sell_SL</th>
								<th>Traded Status</th>
								<th>Profit/Loss</th>
								<th>B/S Diff</th>
								<th>GAP</th>
								<th>Strength</th>
								<th>CNDL/VOL STRNTH</th>
								<th>Loss</th>
								<th>Traded At</th>
								<th>Exited at</th>
								<th>RATIO</th>
								
								<th>CNDL</th>
								<th>OHLC</th>
								<th>C1_MOV</th>
								<th>C2_MOV</th>
								<th>HiDiff</th>
								<th>LoDiff</th>
								<th>C1_H-C</th>
								<th>C1_C-L</th>
								<th>C2_H-C</th>
								<th>C2_C-L</th>
								<th>C1</th>
								<th>C2</th>
								<th>StopLoss</th>
							</tr>
						<!-- /thead -->
					</table>
					<table style="min-height: 90%; width: 100%;" class="" id="mytable">
        				<tbody>
						<c:forEach items="${reportList}" var="reportDataBean">
							<tr>
								<td align="center"><a
								href='<c:url value = "/stockDetails?stockId=${reportDataBean.kiteFutureKey}"/>'>${reportDataBean.symbolName} &nbsp; ${reportDataBean.itemId}</a></td>
								<td align="center">${reportDataBean.lotSize} &nbsp; ${reportDataBean.tradedLotCount}</td>
								<td align="center">${reportDataBean.tradableStateDescription} &nbsp; ${reportDataBean.heikinAshiTrend}</td>
								<td align="center">${reportDataBean.optionChainTrend} &nbsp; / &nbsp; ${reportDataBean.optionChainPriceTrend} &nbsp; / &nbsp; ${reportDataBean.optionChainId}  
										 &nbsp; / &nbsp; ${reportDataBean.tradeType}</td>
								<td align="center">${reportDataBean.buyAtVal} - ${reportDataBean.sellAtVal} AH:${reportDataBean.avgHigh1min},  AL:${reportDataBean.avgLow1min},  
																					AC:${reportDataBean.avgClose1min}, O:${reportDataBean.currentCandleOpen}</td>
								<td align="center">${reportDataBean.tradedStateDescription}</td>
								<td align="center">${reportDataBean.profitLossAmtVal}</td>
								<td align="center">${reportDataBean.buyerSellerDiffVal} &nbsp; ${reportDataBean.buyerSellerDiffVal2} 
									&nbsp;BQ:${reportDataBean.buyerQuantityVal} &nbsp;SQ:${reportDataBean.sellerQuantityVal}</td>
								<td align="center">${reportDataBean.gapUpDownMoveVal} &nbsp;  H-C:${reportDataBean.avgHighMinusClose1min} &nbsp;  
									C-L:${reportDataBean.avgCloseMinusLow1min}   &nbsp;  VWAP:${reportDataBean.vwapValue}</td>
								<td align="center" class="${reportDataBean.strengthStyleClass}">${reportDataBean.strengthableTradeStateDescription} 
											&nbsp; ${reportDataBean.strategyRule}</td>
								<td align="center">${reportDataBean.optionChainTrend} 
									<span class="${reportDataBean.volStrengthStyleClass}"> ${reportDataBean.volumeTradeStateDescription}</span>
									</td>
								<td align="center"> ${reportDataBean.tradedAtAvgVal}    &nbsp;   ${reportDataBean.profitLossAmtVal}    
								 &nbsp;  <span style='color: green'>${reportDataBean.positiveDirectionProfit}</span> / 
								 <span style='color: red'>${reportDataBean.negativeDirectionLoss}</span>
								     &nbsp;  ${reportDataBean.targetPrice} &nbsp;  ${reportDataBean.prevDayHrCrossInd}    
								      &nbsp;  ${reportDataBean.prevDayHrBuyAtVal}     &nbsp;  ${reportDataBean.prevDayHrSellAtVal}</td>
								<td align="center">${reportDataBean.signalTriggeredInAt}</td>
								<td align="center">${reportDataBean.signalTriggeredOutAt}</td>
								<td align="center">${reportDataBean.buySellQuantityRatio} &nbsp; ${reportDataBean.percentageChange}</td>
								
								<td align="center">${reportDataBean.candleNumber}</td>
								<td align="center">${reportDataBean.ohlcStateDescription}</td>
								<td align="center">${reportDataBean.candle3SizeAmt}</td>
								<td align="center">${reportDataBean.candle4SizeAmt}</td>
								<td align="center">${reportDataBean.candleHighsDiff}</td>
								<td align="center">${reportDataBean.candleLowsDiff}</td>
								<td align="center">${reportDataBean.candle3HighMinusClose}</td>
								<td align="center">${reportDataBean.candle3CloseMinusLow}</td>
								<td align="center">${reportDataBean.candle4HighMinusClose}</td>
								<td align="center">${reportDataBean.candle4CloseMinusLow}</td>
								<td align="center">${reportDataBean.candle3Type}</td>
								<td align="center">${reportDataBean.candle4Type}</td>
								
								<td align="center">${reportDataBean.stopLoss};${reportDataBean.targetPrice};${reportDataBean.baseStopLoss} &nbsp; 
								${reportDataBean.trailingStopLoss1500}: ${reportDataBean.trailingStopLoss2000}&nbsp; 
								${reportDataBean.trailingStopLoss2500}: ${reportDataBean.trailingStopLoss3000}&nbsp; </td>
							</tr>
						</c:forEach>
						</tbody>
					</table>	
			</div>
	</jsp:attribute>
</t:masterTemplate2>