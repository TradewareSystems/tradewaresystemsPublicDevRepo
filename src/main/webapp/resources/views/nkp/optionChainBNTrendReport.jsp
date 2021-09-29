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
 		<div class="dataTableContentPanel">
 			<c:forEach items="${optionChainDataList}" var="optionChainBean" varStatus="status">
 				<div class="borderedContentPanel">
 					<p>${optionChainBean.symbol}</p>
 					
 					<p>${optionChainBean.OITrend} </p> <p> ${optionChainBean.priceMoveTrend}</p>
 					
 					<p>${optionChainBean.top1StrikePriceCall}  ${optionChainBean.top1StrikePricePut} </p>
 					
 					 <p class="${optionChainBean.sortOrderStyle}">${optionChainBean.changePercentage}</p>
 					
 					<p>${optionChainBean.topTenGainLooseSortOrder}/${optionChainBean.sortOrder}</p>
 					
 					<p>${optionChainBean.putCallRatio}</p>  <p>${optionChainBean.underlyingPrice}</p>
 				</div> 
 			</c:forEach>
		</div>
	</jsp:attribute>
</t:simpleTemplate>