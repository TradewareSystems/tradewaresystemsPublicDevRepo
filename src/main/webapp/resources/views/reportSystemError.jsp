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
        			
        			<a href='<c:url value = "/reportSystemError"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" /> System Info/Errors</a>
						&nbsp;&nbsp;Total Error &nbsp; <b>${systemErrorList.size()}</b>
        		</div>
        		</div>
        		<div class="container"
			style=" max-height: 92%; overflow-x: scroll; padding-left: 10px; max-width: 99%;">
        			<table
				style="min-height: 90%; width: 100%; position: sticky; top: 0;"
				class=" " id="mytableHeader">
        				<!--  thead class="header"-->
        					<tr style="background: lightgrey;">
								<th>Id</th>
								<th>Class Name</th>
								<th>Method Name</th>
								<th>Error/Exception</th>
								<th>Severity</th>
								<th>CNT</th>
								<th>Error/Exception Info</th>
								<th>Date Time</th>
							</tr>
						<!-- /thead -->
					</table>
					<table style="min-height: 90%; width: 100%;" class="" id="mytable">
        				<tbody>
						<c:forEach items="${systemErrorList}" var="systemErrorBean"
						varStatus="status">
							<tr>
								<td align="center">${systemErrorBean.errorId}</td>
								<td align="center">${systemErrorBean.className}</td>
								<td align="center">${systemErrorBean.methodName}</td>
								<td align="center">${systemErrorBean.errorType}</td>
								<td align="center">${systemErrorBean.errorSevirity}</td>
								<td align="center">${systemErrorBean.errorCount}</td>
								<td align="center" width="300">${systemErrorBean.errorMessage}</td>
								<td align="center" class="noWrapText">${systemErrorBean.dateTimeStampStr}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>	
			</div>
	</jsp:attribute>
</t:masterTemplate2>