<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<t:simpleTemplate>
	<jsp:attribute name="body_area">
        <div class="dataTableTopic">
			<div class="dataTableTopicInnerDiv">
        		<a class="space-nbsp2 float-l" href='<c:url value = "/"/>'>
        			<img align="top"
					src="<c:url value="/resources/static/images/home.png"/>" /> Home
        		</a>
        		<span class="redBold pad-r-25">"----"</span>
        		<span class="dataTableTopicContent space-nbsp4"><b> Create Option Trade Order</b></span>
        		<a href='<c:url value = "/getOptionChainReport2"/>'>
        			<img align="top"
					src="<c:url value="/resources/static/images/refresh.png"/>" />
        		</a>
        		<span class="float-r redBold">"----"</span>
        		<a id="checkBoxClick" class="float-r"
					href='<c:url value = "/applyBestTrades"/>'></a>
        		<span class="float-r redBold pad-r-25">"----"</span> 
        	</div>
        </div>
        <div class="optionTradeFormLeftPanel">
			<div class="simpleHeader">
				<b>Option Strategy Trade Builder</b>
			</div>
			<form:form action="/submitOptionTradeOrder" method="POST" modelAttribute="optionTradeOrder" >
				<table class="divPanelTable">
					<tr>
						<td class="first"><form:label path="symbolId">  Index/Stock : </form:label></td>
						<td class="valueRightColumn">
							<form:select id="symbolId" name="symbolId" path="symbolId" cssClass="textFieldWidth_180" 
            					onchange="onOptionTradeOrderSymbolChange(this, event);">
            					<form:option value=" " label="--- Select ---" />
    								<c:forEach items="${indexMap}" var="indexMap">
    						 			<form:option value="${indexMap.key}" label="${indexMap.value}"/>
        							</c:forEach>
							</form:select>
						 </td>
					</tr>
					<tr>
						<td class="first"><form:label path="symbolId">  Current Prices : </form:label></td>
						<td class="secondLeftAlignFloatleft">
						 	<form:input id="stockLastPrice" name="stockLastPrice" readonly="true" 
						 		path="stockLastPrice" cssClass="textFieldWidth_100 inputDisabled"/>
						 	&nbsp; <form:input id="futureLastPrice" name="futureLastPrice" readonly="true" 
						 		path="futureLastPrice" cssClass="textFieldWidth_100 inputDisabled" />
						 </td>
					</tr>
					<tr>
						<td class="second"><form:label path="expiryDate">Expiry  Date :  </form:label> </td>
						<td class="valueRightColumn">
					 		<form:select id="expiryDate" name="expiryDate" path="expiryDate" cssClass="textFieldWidth_180" >
            					<form:option value=" " label="--- Select ---" />
    								<c:forEach items="${expiryDates}" var="closeDate">
           								<form:options items="${closeDate}"  />
        							</c:forEach>
							</form:select>
					 	</td>
					</tr>
				    <tr>
						<td class="first"> <form:label path="tradeStrategy"> Option Strategy  : </form:label>   </td>
							<td class="valueRightColumn  secondLeftAlign">
								<form:radiobuttons id="tradeStrategy" path="tradeStrategy"
									name="tradeStrategy" items="${tradeStrategyOptions}"
									onclick="onOptionTradeStrategySelection(this, event);" />
							</td>
					</tr>
					
					<tr>
						<td class="first"><form:label path="tradeSubStrategy"> Sub Strategy : </form:label></td>
						<td class="valueRightColumn">
							<form:select id="tradeSubStrategy" name="tradeSubStrategy" 
								path="tradeSubStrategy" cssClass="textFieldWidth_180"
								onchange="onOptionTradeSubStrategySelection(this, event);">
            					<form:option value=" " label="--- Select ---" />
    								<c:forEach items="${optionTradeSubStrategy}" var="tradeSubStrategy">
           								<form:options items="${optionTradeSubStrategy}"  />
        							</c:forEach>
							</form:select>
						 </td>
					</tr>
					
					<tr>
						<td class="first"><form:label path="numberOfLots"> Lots : </form:label></td>
						<td class="valueRightColumn">
							<form:input type="number" id="numberOfLots" name="numberOfLots" 
						 		path="numberOfLots" cssClass="textFieldWidth_180"/>
						 </td>
					</tr>
				</table>
				<table class="divPanelTable" id="strangleDataForm" style="display:none;">
					<%@ include file="optionTradeOrderStrangle.jsp" %>
				</table>
				<table class="divPanelTable" id="butterFlyDataForm" style="display:none;">
					<%@ include file="optionTradeOrderButterFly.jsp" %> 
				</table>
				
					
           			 <br>
            		<input type="submit" value="Submit" />
            		<input type="reset" value="Reset	" />
        		</form:form>
        		</div>
        		<br>
		<br>
	</jsp:attribute>
</t:simpleTemplate>