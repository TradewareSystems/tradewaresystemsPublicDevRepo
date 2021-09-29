<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<t:simpleTemplate>
	<jsp:attribute name="body_area">
        <div
			style="height: 75%; min-width: 40%; position: absolute; left: 28%; border: 1px solid rgba(18, 27, 27, 0.51); border-radius: 5px; margin: 5px 5px 5px 5px;">
			<div style="min-height: 5px;margin-left:25px;">
			<c:if test="${not empty errorMessge}">
				<div style="color: red; font-weight: bold; margin: 5px 0px;">${errorMessge}</div>
			</c:if>
			</div>
			<form:errors path="*" cssClass="errorblock" element="div"/>
		<form:form action="/registration" method='POST' modelAttribute="userForm">
        <table class="tableNoBorder">
      	  <tr>
                <td class="noTableRowLeftCellBorder">UserId:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='text' name='userId' path='userId' value='' /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="userId" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">UserName:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='text'  path='userName'  /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="userName" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">Password:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='password' name='password' path='password' /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="password" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">Confirm Password:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='password' name='passwordConfirm' path='passwordConfirm' id="passwordConfirm"/></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="passwordConfirm" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">FirstName:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='text' name='firstName' path='firstName' value='' /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="firstName" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">LastName:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='text' name='lastName' path='lastName' value='' /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="lastName" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">Email:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='text' name='email' path='email' value='' /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="email" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">DateOfBirth:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='date' name='dateOfBirth' path='dateOfBirth' value='' /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="dateOfBirth" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">API Key:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='text' name='apiKey' path='apiKey' /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="apiKey" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">Secret Key:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='text' name='secretKey' path='secretKey' /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="secretKey" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">History API Key:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='text' name='histApiKey' path='histApiKey' /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="histApiKey" cssClass="error" /></td>
            </tr>
            <tr>
                <td class="noTableRowLeftCellBorder">History Secret Key:</td>
                <td class="noTableRowRightCellBorder"><form:input class="textFieldWidth_225" type='text' name='histSecretKey' path='histSecretKey' /></td>
                <td class="noTableRowBorderErrorCell"><form:errors path="histSecretKey" cssClass="error" /></td>
            </tr>
            <tr>
            <td class="noTableRowLeftCellBorder"></td>
                <td class="noTableRowButtonCellBorder"><input name="submit" type="submit"
							value="submit" class="buttonStyle"/></td>
							<td class="noTableRowBorderErrorCell"></td>
            </tr>
        </table>
					
   </form:form>
		</div>
	
	
	</jsp:attribute>
</t:simpleTemplate>