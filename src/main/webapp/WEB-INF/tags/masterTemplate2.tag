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
        
      //listen for link click events at the document level
        if (document.addEventListener) {
            document.addEventListener('click', interceptClickEvent);
        } else if (document.attachEvent) {
            document.attachEvent('onclick', interceptClickEvent);
        }
      
        /* window.onload = function()
        {
          var lnks = document.links;
          if (lnks) {
            for (var i = 0; i < lnks.length; ++i) {
              lnks[i].onclick = linkOnClick;
            }
          }
        } */
        
      //intercepts clicks on link
        function interceptClickEvent(e) {
            var href;
            var target = e.target || e.srcElement;
                href = target.getAttribute('href');
                if (null != href) {
                	enablePageLoader();
                   //e.preventDefault();
                }
        }
        </script>
	<jsp:invoke fragment="header_area" />
</head>
<title>Tradeware</title>
<body onload="myFunction();">
	<div id="loader" class="center loader" role="dialog"></div>
	<div id="pageContentArea" class="center full-height">
		<div id="topHeaderArae" class="">
			<jsp:include page="/resources/views/common/includeTopHeaderNew.jsp"></jsp:include>
			<jsp:include page="/resources/views/common/settingsModel.jsp"></jsp:include>
		</div>
		<div id="middleBodyArae" class="bodyContent">
			<jsp:invoke fragment="body_area" />
		</div>
		<!-- div
			class="footerContent">
			Copy right@2019 All rights reserved to tradeware.in</div -->
	</div>
</body>
</html>