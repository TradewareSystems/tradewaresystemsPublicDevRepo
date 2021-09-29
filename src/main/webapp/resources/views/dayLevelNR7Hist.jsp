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
        			
        			${totalTrades} <span
					style="font: normal; font-family: sans-serif; color: light-blue;">Running Report </span>
        			&nbsp;&nbsp;<a href='<c:url value = "/report"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" /></a>
        			${profitLoss}
        			
        			<a style="float: right;"
					href='<c:url value = "/reportFull"/>'>&nbsp; Full Day Report</a>
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
								<th>Key</th>
								<th>Tradable State</th>
								<th>Buy At</th> 
								<th>Sell At</th> 
								<th>Top High</th>
								<th>Top Low</th>
								<th>NR7</th>
							</tr>
						<!-- /thead -->
					</table>
					<table style="min-height: 90%; width: 100%;" class="" id="mytable">
        				<tbody>
						<c:forEach items="${reportList}" var="reportDataBean">
							<tr>
								<td align="center"><a
								href='<c:url value = "/stockDetails?stockId=${reportDataBean.kiteFutureKey}"/>'>${reportDataBean.symbolName}</a></td>
								<td align="center">${reportDataBean.lotSize}</td>
								<td align="center">${reportDataBean.kiteFutureKey}</td>
								<td align="center">${reportDataBean.tradableStateId}</td>
								<td align="center">${reportDataBean.buyAtVal}</td>
								<td align="center">${reportDataBean.sellAtVal}</td>
								<td align="center">${reportDataBean.topHigh}</td>
								<td align="center">${reportDataBean.topLow}</td>
								<td align="center">${reportDataBean.nr7TradeDatesStr}</td>
							</tr>
							<tr class="spanRowBackGround spanBottomRowBorder">
								<td align="center">${reportDataBean.min60StochasticTrend}</td>
								<td align="center">${reportDataBean.min60StochasticValK1}</td>
								<td align="center">${reportDataBean.min60StochasticValD3}</td>
								
								<td align="center">${reportDataBean.min60HeikinAshiTrendId}</td>
								<td align="center"> </td>
								<td align="center"> </td>
								
								<td align="center"> </td>
								<td align="center"> </td>
								<td align="center">RSI::: &nbsp; ${reportDataBean.min60RsiTrend} &nbsp; ${reportDataBean.rsi1} &nbsp; ${reportDataBean.rsi2} &nbsp; ${reportDataBean.rsi3}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>	
			</div>
	</jsp:attribute>
</t:masterTemplate2>