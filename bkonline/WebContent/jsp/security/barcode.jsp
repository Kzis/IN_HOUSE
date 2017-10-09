<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<s:include value="/jsp/security/include/permission-js-css.jsp"/>
</head>
<body>
	<s:form id="permissionForm" name="permissionForm" method="post"
		namespace="/" action="initPermission" cssClass="margin-zero" onsubmit="return false;">
		
			<div style="text-align: center;">
				<img src="http://qr-official.line.me/L/0qd_haqlRX.png">
				
			</div>
		
		
		
		<div id="divButtonSearchPredefine" style="text-align: center;" >
			<!-- <a href="https://line.me/R/ti/p/%40hxw5226o"><img height="36" border="0" alt="เพิ่มเพื่อน" src="https://scdn.line-apps.com/n/line_add_friends/btn/en.png"></a> -->
			<input type="button" class="ui-button ui-corner-all ui-widget" id="btnLogin" value='<s:text name="Ready to Meeting Room Booking"/>' onclick="gotoMeeting();"/>
		</div>
		<s:token />
	</s:form>
</body>
</html>