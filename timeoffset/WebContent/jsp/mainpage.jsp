<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cct.common.CommonUser"%>
<%@ page import="util.web.SessionUtil"%>

<%
String haveMenu = "true";
if (SessionUtil.getAttribute("useMenu") != null) {
	haveMenu = (String) SessionUtil.getAttribute("useMenu");
}
%>

<html>
<head>

</head>
<body>
<s:form cssClass="margin-zero" name="template">
	
</s:form>
</body>
</html>