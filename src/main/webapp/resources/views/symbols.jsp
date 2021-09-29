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
        			
        			<a href='<c:url value = "/getSymbols"/>'><img
					align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" /> Tradeware Symbols</a>
						&nbsp;&nbsp;Total Symbols &nbsp; <b>${symbolList.size()}</b>
					<span style="float: right;">LastUpdated : ${lastUpated}</span>
        		</div>
        		</div>
        		<div class="container"
			style=" max-height: 92%; overflow-x: scroll; padding-left: 10px; max-width: 99%;">
        			<table
				style="min-height: 90%; width: 100%; position: sticky; top: 0;"
				class=" " id="mytableHeader">
        				<!--  thead class="header"-->
        					<tr style="background: lightgrey;">
        						<th>S.NO</th>
								<th>Symbol Id</th>
								<th>Lot Size</th>
								<th>Ticker Size</th>
								<th>SMA</th>
								<th>FnOInd</th>
								<th>EqtInd</th>
								<th>IndxInd</th>
								<th>VldInd</th>
								
								<th>Category</th>
								<th>Sector</th>
								<th>InstrumentToken</th>
								<th>YearHigh</th>
								<th>YearLow</th>
								<th>HiLo Per</th>
								<th>HiLo Trend</th>
								
							</tr>
						<!-- /thead -->
					</table>
					<table style="min-height: 90%; width: 100%;" class="" id="mytable">
        				<tbody>
						<c:forEach items="${symbolList}" var="symbolBean"
						varStatus="status">
							<tr>
								<td align="center">${status.index}</td>	
								<td align="center">${symbolBean.symbolId}</td>
								<td align="center">${symbolBean.lotSize}</td>
								<td align="center">${symbolBean.optionTickerSize}</td>
								<td align="center">${symbolBean.close200SmaTradableRatio}</td>
								<td align="center">
									<c:choose>
  										<c:when test="${symbolBean.fnoInd}">
									    	<input class="stylishCheckbox" type="checkbox"
											value="${symbolBean.fnoInd}" checked="checked"
											disabled="disabled" /> 
									    </c:when>
									    <c:otherwise>
    										 <input class="stylishCheckbox" type="checkbox"
											value="${symbolBean.fnoInd}" disabled="disabled" />
  										</c:otherwise>
									</c:choose>
								</td>
								<td align="center">
									<c:choose>
  										<c:when test="${symbolBean.equityInd}">
									    	<input class="stylishCheckbox" type="checkbox"
											value="${symbolBean.equityInd}" checked="checked"
											disabled="disabled" /> 
									    </c:when>
									    <c:otherwise>
    										 <input class="stylishCheckbox" type="checkbox"
											value="${symbolBean.equityInd}" disabled="disabled" />
  										</c:otherwise>
									</c:choose>
								</td>
								<td align="center">
									<c:choose>
  										<c:when test="${symbolBean.indexInd}">
									    	<input class="stylishCheckbox" type="checkbox"
											value="${symbolBean.indexInd}" checked="checked"
											disabled="disabled" /> 
									    </c:when>
									    <c:otherwise>
    										 <input class="stylishCheckbox" type="checkbox"
											value="${symbolBean.indexInd}" disabled="disabled" />
  										</c:otherwise>
									</c:choose>
								</td>
								<td align="center">
									<c:choose>
  										<c:when test="${symbolBean.validInd}">
									    	<input class="stylishCheckbox" type="checkbox"
											value="${symbolBean.validInd}" checked="checked"
											disabled="disabled" /> 
									    </c:when>
									    <c:otherwise>
    										 <input class="stylishCheckbox" type="checkbox"
											value="${symbolBean.validInd}" disabled="disabled" />
  										</c:otherwise>
									</c:choose>
								</td>
								<td align="center">${symbolBean.categoryFilter}</td>
								<td align="center">${symbolBean.sector}</td>
								<td align="center">${symbolBean.instrumentToken}</td>
								<td align="center">${symbolBean.week52High}</td>
								<td align="center">${symbolBean.week52Low}</td>
								
								<td align="center">${symbolBean.yrHiLoNearPer}</td>
								<td align="center">${symbolBean.yrHiLoNearTrend}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>	
			</div>
	</jsp:attribute>
</t:masterTemplate2>