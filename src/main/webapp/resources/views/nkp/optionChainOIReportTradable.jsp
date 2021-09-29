<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<t:simpleTemplate>
	<jsp:attribute name="body_area">
        <div class="dataTableTopic">
			<div class="dataTableTopicInnerDiv">
        		<a class="space-nbsp2 float-l" href='<c:url value = "/"/>'>
        			<img align="top"
					src="<c:url value="/resources/static/images/home.png"/>" /> Home
        		</a>
        		<span class="redBold pad-r-25">${candleNumber}</span> 
        		<span class="dataTableTopicContent space-nbsp4"><b>Option Chain (Open Interest) Analysis Report </b></span>
        		<a href='<c:url value = "/getOptionChainReport2"/>'>
        			<img align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" />
        		</a>
        		<span class="float-r redBold">${lastUpdateTime}</span>
        		<a id="checkBoxClick" class="float-r" href='<c:url value = "/applyBestTrades"/>'></a>
        		<span class="float-r redBold pad-r-25">${failedOptionChainSymbolCount}</span> 
        	</div>
        </div>
 		<div class="container dataTableContentPanel">
        	<table class="dataTaleHeaderStye rowSpanOptionChainReport" id="mytableHeader">
        		<tbody>
	   				<tr class="dataTaleHeaderRowStye">
	   				<th rowspan="2">0</th>
						<th rowspan="2"><a
								href='<c:url value = "/getOptionChainReport2"/>'>Symbols</a></th>
						<th rowspan="2">Ticker</th>
						<th rowspan="2" class="tableRightBorderOverride"><a
								href='<c:url value = "/getOptionChainReport2SortByTrend"/>'>Trend</a></th>
								<th rowspan="2" class="tableRightBorderOverride">PriceTrend</th>
						<th class="tableRightBorderOverride" colspan="3">Tradable</th>
						<th class="tableRightBorderOverride" colspan="3">Strike</th>
						<th class="tableRightBorderOverride" colspan="3">Net Change</th>
								<th class="tableRightBorderOverride">NetChgTot</th>
								<th>Total OI</th>
								<th class="tableRightBorderOverride">Total Vol</th>
						<th class="tableRightBorderOverride" colspan="3">OI</th>
						<th class="tableRightBorderOverride" colspan="3">OI Change</th>
						<th class="tableRightBorderOverride" colspan="3">OI Volumes</th>
						<th>MktVal</th>
						<th>Total OI</th>
						<th>Total Vol</th>
						<th rowspan="2">CNDL_TIME</th>
					</tr>
				</tbody>
			</table>
			<table class="dataTaleContentStye" id="mytable">
        		<tbody>
					<c:forEach items="${optionChainDataList}" var="optionChainBean" varStatus="status">
						<tr class="${optionChainBean.attentionStyleBuy}">
						<td rowspan="2" align="center"  class="defaultWhiteBackground"><input id="bestTradeIndId_${status.index}" type="checkbox" name="${optionChainBean.symbol}" value="${optionChainBean.symbol}" onclick="handleCheckBoxEvent(this)" ${optionChainBean.bestTradeIndStr}  /></td>
							<td rowspan="2" align="center" class="defaultWhiteBackground"><a
								href='<c:url value = "/getBestTradeReportDetails?symbol=${optionChainBean.symbol}"/>'>${optionChainBean.symbol}</a></td>
							<td rowspan="2" align="center" class="defaultWhiteBackground">${optionChainBean.tickerSize}</td>
							<td class="tableRightBorderOverride defaultWhiteBackground" rowspan="2" align="center" style="${optionChainBean.style}">${optionChainBean.OITrend}</td>
						<td rowspan="2" align="center" class="defaultWhiteBackground tableRightBorderOverride">${optionChainBean.priceMoveTrend}</td>
						
							<td align="center">${optionChainBean.ltpOnDecision}</td>
							<td align="center">${optionChainBean.bestEntry}</td>
							<td align="center" class="tableRightBorderOverride">${optionChainBean.openInterestChangePercentage}</td>
							
							<td align="center" class="${optionChainBean.top1OpenInterestCallStyle}">${optionChainBean.top1StrikePriceCall}</td>
							<td align="center" class="${optionChainBean.top2OpenInterestCallStyle}">${optionChainBean.top2StrikePriceCall}</td>
							<td align="center" class="tableRightBorderOverride ${optionChainBean.top2OpenInterestCallStyle}">${optionChainBean.top3StrikePriceCall}</td>
							<td align="center" class="${optionChainBean.styleNetChange1}">${optionChainBean.top1OINetChangeCall}</td>
							<td align="center" class="${optionChainBean.styleNetChange2}">${optionChainBean.top2OINetChangeCall}</td>
							<td align="center" class="tableRightBorderOverride ${optionChainBean.styleNetChange3}">${optionChainBean.top3OINetChangeCall}</td>
									<td align="center" class="tableRightBorderOverride">${optionChainBean.totalOINetChangeCall}</td>
									<td align="center">${optionChainBean.totalOpenInterestCallStr}</td>
									<td align="center" class="tableRightBorderOverride">${optionChainBean.totalOIVolumesCallStr}</td>
							<td align="center">${optionChainBean.top1OpenInterestCall}</td>
							<td align="center">${optionChainBean.top2OpenInterestCall}</td>
							<td align="center" class="tableRightBorderOverride">${optionChainBean.top3OpenInterestCall}</td>
							<td align="center">${optionChainBean.top1OpenInterestChangeCall}</td>
							<td align="center">${optionChainBean.top2OpenInterestChangeCall}</td>
							<td align="center" class="tableRightBorderOverride">${optionChainBean.top3OpenInterestChangeCall}</td>
							<td align="center">${optionChainBean.top1OIVolumesCall}</td>
							<td align="center">${optionChainBean.top2OIVolumesCall}</td>
							<td align="center" class="tableRightBorderOverride">${optionChainBean.top3OIVolumesCall}</td>
							<td rowspan="2" align="center" class="defaultWhiteBackground">${optionChainBean.underlyingPrice}</td>
							<td align="center">${optionChainBean.totalOpenInterestCallStr}</td>
							<td align="center">${optionChainBean.totalOIVolumesCallStr}</td>
							<td rowspan="2" align="center" class="defaultWhiteBackground">${optionChainBean.timeStampStr}</td>
						</tr>
						<tr class="tableRowBackGround ${optionChainBean.attentionStyleSell}">
						
							<td align="center">${optionChainBean.goForTrade}</td>
							<!--  td align="center">${optionChainBean.ltp}</td-->
							<td align="center">${optionChainBean.underlyingPrice}</td>
							<td align="center" class="tableRightBorderOverride">${optionChainBean.changePercentage}</td>
							
							<td align="center" class="${optionChainBean.top1OpenInterestPutStyle}">${optionChainBean.top1StrikePricePut}</td>
							<td align="center" class="${optionChainBean.top2OpenInterestPutStyle}">${optionChainBean.top2StrikePricePut}</td>
							<td align="center" class="tableRightBorderOverride ${optionChainBean.top3OpenInterestPutStyle}">${optionChainBean.top3StrikePricePut}</td>
							<td align="center" class="${optionChainBean.styleNetChange1}">${optionChainBean.top1OINetChangePut}</td>
							<td align="center" class="${optionChainBean.styleNetChange2}">${optionChainBean.top2OINetChangePut}</td>
							<td align="center" class="tableRightBorderOverride ${optionChainBean.styleNetChange3}">${optionChainBean.top3OINetChangePut}</td>
							<td align="center">${optionChainBean.totalOpenInterestPutStr}</td>
									<td align="center" class="tableRightBorderOverride">${optionChainBean.totalOINetChangePut}</td>
									<td align="center">${optionChainBean.totalOIVolumesPutStr}</td>
									<td align="center" class="tableRightBorderOverride">${optionChainBean.top1OpenInterestPut}</td>
							<td align="center">${optionChainBean.top2OpenInterestPut}</td>
							<td align="center" class="tableRightBorderOverride">${optionChainBean.top3OpenInterestPut}</td>
							<td align="center">${optionChainBean.top1OpenInterestChangePut}</td>
							<td align="center">${optionChainBean.top2OpenInterestChangePut}</td>
							<td align="center" class="tableRightBorderOverride">${optionChainBean.top3OpenInterestChangePut}</td>
							<td align="center">${optionChainBean.top1OIVolumesPut}</td>
							<td align="center">${optionChainBean.top2OIVolumesPut}</td>
							<td align="center" class="tableRightBorderOverride">${optionChainBean.top3OIVolumesPut}</td>
							<td align="center">${optionChainBean.totalOpenInterestPutStr}</td>
							<td align="center">${optionChainBean.totalOIVolumesPutStr}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>	
		</div>
	</jsp:attribute>
</t:simpleTemplate>