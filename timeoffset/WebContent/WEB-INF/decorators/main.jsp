<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<title><s:text name="applicationName"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta http-equiv="Cache-control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="0" />
		
		<s:include value="/jsp/template/javascript.jsp"/>
		<s:include value="/jsp/template/css.jsp"/>
		<s:include value="/jsp/template/jquery-inputdatetimeformat.jsp"/>
		<s:include value="/jsp/template/jquery-autocomplete.jsp"/>
		<s:include value="/jsp/template/jquery-dropdownlist.jsp"/>
		<s:include value="/jsp/template/jquery-progress.jsp"/>
		<s:include value="/jsp/template/jquery-selectitemx.jsp"/>
		<s:include value="/jsp/template/theme_style.jsp"/>
		<s:include value="/jsp/template/javascript-lasted.jsp"/>
		
		<script type="text/javascript">
			jQuery( window ).resize(function() {
				resizeDataTable();
			});
			
			function sf(){
				
			}
			
		</script>
		<decorator:head/>
	</head>
	<body onload="sf();" class="margin-zero">
		<div class="mp-pusher" id="mp-pusher">
			<table class="body body_main" style="margin-left: auto; margin-right: auto;">
				<tr id="BODY_TR_HEADER" style="vertical-align: middle;">
					<td id="BODY_TD_HEADER" class="content">
						<s:include value="/jsp/template/header.jsp">
							<s:param name="PP_CODE" value="%{P_CODE}"/>
							<s:param name="FF_CODE" value="%{F_CODE}"/>
						</s:include>
					</td>
				</tr>
				<tr id="BODY_TR_CONTENT">
					<td id="BODY_TD_CONTENT" class="contentForm">
						<!-- ซ่อนไว้ดูดสี -->
						<div class="ui-widget-header" style="display: none;"></div>
						<div class="ui-state-hover" style="display: none;"></div>
						<div class="ui-state-default" style="display: none;"></div>
						<div class="ui-state-active" id="dood_color_ui_state_active" style="display: none;"></div>
						<decorator:body/>
						<s:include value="/jsp/template/message.jsp"/>	
						<s:include value="/jsp/template/progressbar.jsp"/>
					</td>
				</tr>
			</table>
			<s:include value="/jsp/template/javascript-validate.jsp"/>
		</div>
	</body>
</html>