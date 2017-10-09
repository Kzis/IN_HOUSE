<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.cct.inhouse.common.InhouseUser"%>
<%@ page import="com.cct.enums.UserSessionAttribute"%>
<%@ page import="util.web.SessionUtil"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<title><s:text name="bkonline"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta http-equiv="Cache-control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="0" />
		
		<!-- Add IntroJs styles -->
		<link href="${application.CONTEXT_CENTRAL}/ui/intro/css/introjs.css" rel="stylesheet"/>
		<link href="${application.CONTEXT_CENTRAL}/ui/intro/css/bootstrap-responsive.min.css" rel="stylesheet"/>
		
		<s:include value="/jsp/template/javascript.jsp"/>
		<s:include value="/jsp/template/css.jsp"/>
		<s:include value="/jsp/template/jquery-inputdatetimeformat.jsp"/>
		<s:include value="/jsp/template/theme_style.jsp"/>
		<decorator:head/>
		<link type="text/css" rel="stylesheet" href="${application.CONTEXT_OWNER}/css/bkonline-style.css" />
	</head>
	<body onload="sf()" class="margin-zero">
		<div class="mp-pusher" id="mp-pusher">
			<table class="body body_main" style="margin-left: auto; margin-right: auto; width: 100%;">
				<tr id="BODY_TR_HEADER">
					<td class="content" style="border-top: none;">
						<s:include value="/jsp/template/header.jsp">
							<s:param name="PP_CODE" value="%{P_CODE}"/>
							<s:param name="FF_CODE" value="%{F_CODE}"/>
						</s:include>
					</td>
				</tr>
				<tr id="BODY_TR_CONTENT">
					<td id="BODY_TD_CONTENT" class="contentForm" colspan="3">
						<!-- ซ่อนไว้ดูดสี -->
						<div class="ui-state-hover" style="display: none;"></div>
						<div class="ui-state-default" style="display: none;"></div>
						<div class="ui-state-active" id="dood_color_ui_state_active" style="display: none;"></div>
						
						<decorator:body/>
						<s:include value="/jsp/template/message.jsp"/>	
						<s:include value="/jsp/template/jquerytheme.jsp"/>
					</td>
				</tr>
			</table>
		</div>
		<s:include value="/jsp/template/javascript-lasted.jsp"/>
		<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/intro/js/intro.js"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				setTimeout(function() {
					var userName = '<%=((InhouseUser) SessionUtil.get(UserSessionAttribute.DEFAULT.getValue())).getDisplayName()%>';
					var userId = '<%=((InhouseUser) SessionUtil.get(UserSessionAttribute.DEFAULT.getValue())).getId()%>';
					var messageConfirm = "สวัสดีคุณ " + userName + ",\nคุณต้องการคำแนะนำเบื้องต้นในการใช้งานจากเราหรือไม่?";
					var checkHelp = localStorage.getItem(userId);
					// alert(checkHelp + ' > ' + localStorage.getItem('key'));
					if (checkHelp == null) {
						localStorage.setItem(userId, "Y");
						if (confirm(messageConfirm)) {
							startMenu01();	
						}	
					}
				}, 1000);
			});
		</script>
	</body>
</html>