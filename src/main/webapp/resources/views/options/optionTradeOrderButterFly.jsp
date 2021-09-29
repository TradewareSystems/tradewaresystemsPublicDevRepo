<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<!-- spread attributes start -->
	<tr>
		<td class="first">
			<form:label path="strikePriceAtm"> ATM Strike : </form:label>
		</td>
		<td class="valueRightColumnNoLeftPadding">
			<form:select id="strikePriceAtm"
				name="strikePriceAtm" path="strikePriceAtm"
				cssClass="textFieldWidth_180"
				onchange="onAtmStrikePriceSelectionOfButterFly(this, event);">
				<form:option value=" " label="--- Select ---" />
					<c:forEach items="${optionTradeSubStrategy}" var="tradeSubStrategy">
						<form:options items="${optionTradeSubStrategy}" />
				</c:forEach>
			</form:select>
		</td>
	</tr>
	<tr>
		<td class="first"></td>
		<td class="secondLeftAlign">
			<form:input id="atmTradeType" name="atmTradeType"
				readonly="true" path="atmTradeType"
				cssClass="textFieldWidth_60 inputDisabled" /> &nbsp; 
			<form:input id="askPriceOfAtm" name="askPriceOfAtm" readonly="true"
				path="askPriceOfAtm" cssClass="textFieldWidth_60 inputDisabled" />&nbsp; 
			<form:input id="bidPriceOfAtm" name="bidPriceOfAtm"
				readonly="true" path="bidPriceOfAtm"
				cssClass="textFieldWidth_60 inputDisabled" />
		</td>
	</tr>
	
	<tr>
		<td class="first">
			<form:label path="strikePriceLowerOfAtm"> ITM(Lower) Strike : </form:label>
		</td>
		<td class="valueRightColumnNoLeftPadding">
			<form:select id="strikePriceLowerOfAtm"
				name="strikePriceLowerOfAtm" path="strikePriceLowerOfAtm"
				cssClass="textFieldWidth_180"
				onchange="onMainStrikePriceSelection(this, event);">
				<form:option value=" " label="--- Select ---" />
			</form:select>
		</td>
	</tr>
	<tr>
		<td class="first"></td>
		<td class="secondLeftAlign">
			<form:input id="lowerOfAtmTradeType" name="lowerOfAtmTradeType"
				readonly="true" path="lowerOfAtmTradeType"
				cssClass="textFieldWidth_60 inputDisabled" /> &nbsp; 
			<form:input id="askPriceLowerOfAtm" name="askPriceLowerOfAtm" readonly="true"
				path="askPriceLowerOfAtm" cssClass="textFieldWidth_60 inputDisabled" /> &nbsp; 
			<form:input id="bidPriceLowerOfAtm" name="bidPriceLowerOfAtm"
				readonly="true" path="bidPriceLowerOfAtm"
				cssClass="textFieldWidth_60 inputDisabled" />
		</td>
	</tr>
	<tr>
		<td class="first"><form:label path="strikePriceHigherOfAtm"> OTM(Higher) Strike : </form:label></td>
		<td class="valueRightColumnNoLeftPadding">
			<form:select id="strikePriceHigherOfAtm"
				name="strikePriceHigherOfAtm" path="strikePriceHigherOfAtm"
				cssClass="textFieldWidth_180"
				onchange="onProtectStrikePriceSelection(this, event);">
				<form:option value=" " label="--- Select ---" />
			</form:select>
		</td>
	</tr>
	<tr>
		<td class="first"></td>
		<td class="secondLeftAlign">
			<form:input id="higherOfAtmTradeType" name="higherOfAtmTradeType"
				readonly="true" path="higherOfAtmTradeType"
				cssClass="textFieldWidth_60 inputDisabled" /> &nbsp; 
			<form:input id="askPriceHigherOfAtm" name="askPriceHigherOfAtm" readonly="true"
				path="askPriceHigherOfAtm" cssClass="textFieldWidth_60 inputDisabled" />&nbsp; 
			<form:input id="bidPriceHigherOfAtm" name="bidPriceHigherOfAtm"
				readonly="true" path="bidPriceHigherOfAtm"
				cssClass="textFieldWidth_60 inputDisabled" />
		</td>
	</tr>
	<!-- spread attributes end -->
