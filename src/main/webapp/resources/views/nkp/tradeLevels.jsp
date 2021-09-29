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
        		<span class="dataTableTopicContent space-nbsp4"><b>Intraday & Positional Trading Levels </b></span>
        		<a href='<c:url value = "/getTradeLevels"/>'>
        			<img align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" />
        		</a>
        	</div>
        </div>
        <div class="container dataTableContentPanel">
        	<table class="dataTaleHeaderStye" id="mytableHeader">
        		<tbody>
	   				<tr class="dataTaleHeaderRowStye">
						<th>Symbols</th>
						<th>Size</th>
						<th>FirstDayClose</th>
						<th>ATP</th>
						<th>Difference</th>
						<th>AVG Min Value</th>
						<th>Previous Close</th>
						<th>Open</th>
						<th>P-uptrend</th>
						<th>P-downtrend</th>
						<th>I-UP Trend</th>
						<th>I-Down trend</th>
					</tr>
				</tbody>
			</table>
			<table class="dataTaleContentStye" id="mytable">
      			<tbody>
					<c:forEach items="${tradeLevels}" var="contact">
						<tr>
							<td align="center">${contact.stockName}</td>
							<td align="center">${contact.lotSize}</td>
							<td align="center">${contact.firstDayClose}</td>
							<td align="center">${contact.atpValue}</td>
							<td align="center">${contact.differenceValue}</td>
							<td align="center">${contact.last5DaysAvgMin}</td>
							<td align="center">${contact.previousClose}</td>
							<td align="center">${contact.open}</td>
							<td align="center">${contact.positionalUptrendValue}</td>
							<td align="center">${contact.positionalDowntrendValue}</td>
							<td align="center">${contact.intradayUptrendValue}</td>
							<td align="center">${contact.intradayDowntrendValue}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>	
		</div>
	</jsp:attribute>
</t:simpleTemplate>