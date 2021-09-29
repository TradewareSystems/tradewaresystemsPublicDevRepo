<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<t:simpleTemplate>
	<jsp:attribute name="body_area">
        <div class="middleAreaBodyContentSimple">
         <c:if test="${not empty errorMessge}"><div class="redBold textMargin ">${errorMessge}</div></c:if>
          <c:if test="${not empty error}"><div class="redBold textMargin ">${error}</div></c:if>
 
			<form name='loginForm' action="/login" method='POST'>
				<table class="lableMargin" cellpadding="5px;">
					<tr class="noTableRowBorder">
						<td class="noTableRowBorder float-r">User Name :</td>
						<td class="noTableRowBorder"><input type="text"
							name="username" class="textFieldWidth" /></td>
					</tr>
					<tr class="noTableRowBorder">
						<td class="noTableRowBorder float-r">Password :</td>
						<td class="noTableRowBorder"><input type="password"
							name="password" class="textFieldWidth" /></td>
					</tr>
					<tr class="noTableRowBorder">
						<td class="noTableRowBorder"></td>
						<td class="noTableRowBorder float-r"><input type="submit"
							value="Submit" /></td>
					</tr>
				</table>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			 </form>
		</div>
	</jsp:attribute>
</t:simpleTemplate>