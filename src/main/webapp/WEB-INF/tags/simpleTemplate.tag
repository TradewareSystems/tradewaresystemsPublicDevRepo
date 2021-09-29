<!DOCTYPE html>
<%@tag description="Simple Template" pageEncoding="UTF-8"%>

<%@attribute name="header_area" fragment="true"%>
<%@attribute name="body_area" fragment="true"%>
<%@attribute name="switches_area" fragment="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<link href='<spring:url value="/resources/static/styles/style.css"/>'
	rel="stylesheet" />
<script type="text/javascript"
	src='<spring:url value="/resources/static/js/app.js"/>'></script>
<script type="text/javascript"
	src='<spring:url value="/resources/static/js/appAjax.js"/>'></script>
<script type="text/javascript">
	document.onreadystatechange = function() {
		handlePageLoader();
	};
</script>
</head>
<title>Tradeware Systems</title>
<body onload="myFunction();">
	<div id="loader" class="center loader" role="dialog"></div>
	<div id="pageContentArea" class="center full-height simplePageLayout">
		<div id="topHeaderArae" class="">
			<jsp:include
				page="/resources/views/common/includeTopHeaderSimple.jsp"></jsp:include>
		</div>
		<div id="middleBodyArae" class="middleAreaBody">
			<jsp:invoke fragment="body_area" />
		</div>
		<!-- div
			classa="footerAreaSimple">
			Copy right@2019 All rights reserved to tradeware.in</div -->
	</div>
</body>
</html>