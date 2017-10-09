<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.string.StringDelimeter"%>
<%@ page import="com.cct.inhouse.common.InhouseAction"%>
<%@ page import="util.web.SessionUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Meeting Room Booking</title>
</head>
<body>
	<s:form action="addRoomBooking">
		<%
			String[] messages = (String[]) SessionUtil.get(InhouseAction.MESSAGE);
			SessionUtil.remove(InhouseAction.MESSAGE);
			
			String subject = StringDelimeter.EMPTY.getValue();
			if (messages != null && messages.length > 1 && messages[1] != null) {
				subject = messages[1];
			}
			
			String body = StringDelimeter.EMPTY.getValue();
			if (messages != null && messages.length > 2 && messages[2] != null) {
				body = messages[2];
			}
		%>
		<h1>Oops! An Error Occurred</h1>
		<h2>The server returned a "<%=subject%>"</h2>
		<h3><%=body%></h3>
		<br>
		<h3>Field error</h3>
		<s:fielderror/>
		<br>
		<h3>Action error</h3>
		<s:actionerror/>
		<br>
		<h3>Action message</h3>
		<s:actionmessage/>
	</s:form>
</body>
</html>
