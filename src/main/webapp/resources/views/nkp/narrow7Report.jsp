<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<t:simpleTemplate>
	<jsp:attribute name="body_area">
		<div
			style="height: 74%; width: 98%; position: absolute; 
				border: 1px solid rgba(18, 27, 27, 0.51); border-radius: 5px; margin: 5px 5px 5px 5px;">
            	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 10px;">
        			<a style="float:left;" href = '<c:url value = "/"/>'><img align="top" src="<c:url value="/images/home.png"/>" />&nbsp; Home</a>
        			<span style="font: normal;font-family: sans-serif; color: light-blue;">Narrow 7 Calls Level Report </span>
        			&nbsp;&nbsp;<a href = '<c:url value = "/getNarrow7CallDayReport"/>'><img align="top" src="<c:url value="/images/refresh.png"/>" /></a>
        		</div>
        		<div style="width: 99%;max-height:92%; overflow-x :scroll;padding-left:10px; ">
        			<table style="min-height:90%;width:100%;">
						<tr>
							<th rowspan="2">Symbols</th>
							<th rowspan="2">Tradable</th>
							<th rowspan="2">Open</th>
							<th rowspan="2">LTP</th>
							<th colspan="3">Day 1</th>
							<th colspan="3">Day 2</th>
							<th colspan="3">Day 3</th>
							<th colspan="3">Day 4</th>
							<th colspan="3">Day 5</th>
							<th colspan="3">Day 6</th>
							<th colspan="3">Day 7</th>
						</tr>
						<tr>
						    <th>High</th>
						    <th>Low</th>
						    <th>Diff</th>
						    <th>High</th>
						    <th>Low</th>
						    <th>Diff</th>
						    <th>High</th>
						    <th>Low</th>
						    <th>Diff</th>
						    <th>High</th>
						    <th>Low</th>
						    <th>Diff</th>
						    <th>High</th>
						    <th>Low</th>
						    <th>Diff</th>
						    <th>High</th>
						    <th>Low</th>
						    <th>Diff</th>
						    <th>High</th>
						    <th>Low</th>
						    <th>Diff</th>
						</tr>
        				<c:forEach items="${narrow7Beans}" var="narrow7Bean" varStatus="status">
							<tr>
								<td align="center">${narrow7Bean.stockName}</td>
								<td align="center">${narrow7Bean.tradableState}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.open)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.ltp)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day1High)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day1Low)}</td>
								<td align="center">${narrow7Bean.day1HighLowDiff}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day2High)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day2Low)}</td>
								<td align="center">${narrow7Bean.day2HighLowDiff}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day3High)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day3Low)}</td>
								<td align="center">${narrow7Bean.day3HighLowDiff}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day4High)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day4Low)}</td>
								<td align="center">${narrow7Bean.day4HighLowDiff}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day5High)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day5Low)}</td>
								<td align="center">${narrow7Bean.day5HighLowDiff}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day6High)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day6Low)}</td>
								<td align="center">${narrow7Bean.day6HighLowDiff}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day7High)}</td>
								<td align="center">${narrow7Bean.getScaledValue(narrow7Bean.day7Low)}</td>
								<td align="center">${narrow7Bean.day7HighLowDiff}</td>
							</tr>
						</c:forEach>
					</table>	
			</div>
        </div>
	</jsp:attribute>
</t:simpleTemplate>