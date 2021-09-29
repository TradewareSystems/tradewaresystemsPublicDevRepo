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
        		<span class="dataTableTopicContent space-nbsp4"><b>Option Chain (Open Interest) Analysis Report </b></span>
        		<a href='<c:url value = "/getOptionChainReport"/>'>
        			<img align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" />
        		</a>
        	</div>
        </div>	
        <div class="container dataTableContentPanel">
        	<table class="dataTaleHeaderStye getOptionChainReport" id="mytableHeader">
        		<tbody>
	   				<tr class="dataTaleHeaderRowStye">
						<th rowspan="2"><a
								href='<c:url value = "/getOptionChainReport"/>'>Symbols</a></th>
						<th rowspan="2">Ticker</th>
						<th rowspan="2"><a
								href='<c:url value = "/getOptionChainReportSortByTrend"/>'>Trend</a></th>
						<th colspan="15">Call</th>
						<th rowspan="2">Total OI</th>
						<th rowspan="2">Total Vol</th>
						<th colspan="15">Put</th>
						<th rowspan="2">Total OI</th>
						<th rowspan="2">Total Vol</th>
					</tr>
					<tr class="dataTaleHeaderRowStye">
						<th colspan="3">OI</th>
						<th colspan="3">OI Change</th>
						<th colspan="3">OI Volumes</th>
						<th colspan="3">Net Change</th>
						<th colspan="3">Strike</th>
						<th colspan="3">OI</th>
						<th colspan="3">OI Change</th>
						<th colspan="3">OI Volumes</th>
						<th colspan="3">Net Change</th>
						<th colspan="3">Strike</th>
					</tr>
				</tbody>
			</table>
			<table class="dataTaleContentStye" id="mytable">
        		<tbody>
					<c:forEach items="${optionChainDataList}" var="optionChainBean" varStatus="status">
						<tr>
							<td align="center">${optionChainBean.symbol}</td>
							<td align="center">${optionChainBean.tickerSize}</td>
							<td align="center" style="${optionChainBean.style}">${optionChainBean.OITrend}</td>
							<td align="center">${optionChainBean.top1OpenInterestCall}</td>
							<td align="center">${optionChainBean.top2OpenInterestCall}</td>
							<td align="center">${optionChainBean.top3OpenInterestCall}</td>
							<td align="center">${optionChainBean.top1OpenInterestChangeCall}</td>
							<td align="center">${optionChainBean.top2OpenInterestChangeCall}</td>
							<td align="center">${optionChainBean.top3OpenInterestChangeCall}</td>
							<td align="center">${optionChainBean.top1OIVolumesCall}</td>
							<td align="center">${optionChainBean.top2OIVolumesCall}</td>
							<td align="center">${optionChainBean.top3OIVolumesCall}</td>
							<td align="center">${optionChainBean.top1OINetChangeCall}</td>
							<td align="center">${optionChainBean.top2OINetChangeCall}</td>
							<td align="center">${optionChainBean.top3OINetChangeCall}</td>
							<td align="center">${optionChainBean.top1StrikePriceCall}</td>
							<td align="center">${optionChainBean.top2StrikePriceCall}</td>
							<td align="center">${optionChainBean.top3StrikePriceCall}</td>
							<td align="center">${optionChainBean.totalOpenInterestCallStr}</td>
							<td align="center">${optionChainBean.totalOIVolumesCallStr}</td>
							
							<td align="center">${optionChainBean.top1OpenInterestPut}</td>
							<td align="center">${optionChainBean.top2OpenInterestPut}</td>
							<td align="center">${optionChainBean.top3OpenInterestPut}</td>
							<td align="center">${optionChainBean.top1OpenInterestChangePut}</td>
							<td align="center">${optionChainBean.top2OpenInterestChangePut}</td>
							<td align="center">${optionChainBean.top3OpenInterestChangePut}</td>
							<td align="center">${optionChainBean.top1OIVolumesPut}</td>
							<td align="center">${optionChainBean.top2OIVolumesPut}</td>
							<td align="center">${optionChainBean.top3OIVolumesPut}</td>
							<td align="center">${optionChainBean.top1OINetChangePut}</td>
							<td align="center">${optionChainBean.top2OINetChangePut}</td>
							<td align="center">${optionChainBean.top3OINetChangePut}</td>
							<td align="center">${optionChainBean.top1StrikePricePut}</td>
							<td align="center">${optionChainBean.top2StrikePricePut}</td>
							<td align="center">${optionChainBean.top3StrikePricePut}</td>
							<td align="center">${optionChainBean.totalOpenInterestPutStr}</td>
							<td align="center">${optionChainBean.totalOIVolumesPutStr}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>	
		</div>
	</jsp:attribute>
</t:simpleTemplate>