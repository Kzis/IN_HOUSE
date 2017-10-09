<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="util.string.StringDelimeter"%>
<%@ page import="com.cct.inhouse.common.InhouseAction"%>
<%@ page import="util.web.SessionUtil"%>
<html>
<head>
	<style type="text/css">
		BODY {
			width: 1000px;
		}
		
		FROM {
			width: 1000px;
		}
	</style>
	<script type="text/javascript">
		function sf(){
			
		}
		
		function maxExceed() {
			submitPage("<s:url value='/jsp/component/maxExceedException.action' />");
		}
		
		function maxExceedAlert() {
			submitPage("<s:url value='/jsp/component/maxExceedAlertException.action' />");
		}
		
		function maxExceedReport() {
			submitPage("<s:url value='/jsp/component/maxExceedReportException.action' />");
		}
		
		function duplicate() {
			submitPage("<s:url value='/jsp/component/duplicateException.action' />");
		}
		
		function serverValidate() {
			submitPage("<s:url value='/jsp/component/serverValidateException.action' />");
		}
		
		function authenticate() {
			submitPage("<s:url value='/jsp/component/authenticateException.action' />");
		}
		
		function authorization() {
			submitPage("<s:url value='/jsp/component/authorizationException.action' />");
		}
		
		function exception() {
			submitPage("<s:url value='/jsp/component/exceptionException.action' />");
		}
		
		function exceptionMessage() {
			submitPage("<s:url value='/jsp/component/exceptionMessageException.action' />");
		}
		
		function ajaxException() {
			jQuery.ajax({
				type: "POST"
				, url: "<s:url value='/jsp/component/exceptionAjaxMessageException.action' />"
				, async: true // ให้ทำงานพร้อมกันทำ ajax ให้เสร็จก่อน ค่อยทำ java script 
				, success: function(data){
					if (data.actionMessage.type == 'ERROR') {
						showNotifyActionMessage(data.actionMessage);
					} else {
						alert('Success');
					}
				}
			});
		}
		
		function customBasic() {
			submitPage("<s:url value='/jsp/component/customBasicException.action' />");
		}
		
		function customListMsg() {
			submitPage("<s:url value='/jsp/component/customListMsgException.action' />");
		}
		
		function add() {
			submitPage("<s:url value='/jsp/component/addException.action' />");
		}
		
		function edit() {
			submitPage("<s:url value='/jsp/component/editException.action' />");
		}
		
		function updateActive() {
			jQuery.ajax({
				type: "POST"
				, url: "<s:url value='/jsp/component/xupdateActiveException.action' />"
				, async: true // ให้ทำงานพร้อมกันทำ ajax ให้เสร็จก่อน ค่อยทำ java script 
				, success: function(data){
					if (data.actionMessage.type == 'ERROR') {
						showNotifyActionMessage(data.actionMessage);
					} else {
						showNotifyActionMessage(data.actionMessage);
					}
				}
			});
		}
		
		function showConfrimMessage(msgConfirm) {
			if (confirm(msgConfirm)) {
				
			}
		}
	</script>
</head>
<body>
	<s:form cssClass="margin-zero" name="template" cssStyle="width: 1000px;">
		<h4>Test Exception</h4>
		<table>
			<tr>
				<td>
					<br><br>
					<input type="button" value="maxExceed" onclick="maxExceed();"/> OK
					<br><br>
					<input type="button" value="maxExceedAlert" onclick="maxExceedAlert();"/>  OK
					<br><br>
					<input type="button" value="maxExceedReport" onclick="maxExceedReport();"/>  OK
					<br><br>
					<input type="button" value="add" onclick="add();"/> OK
					<br><br>
					<input type="button" value="edit" onclick="edit();"/>  OK
					<br><br>
					<input type="button" value="updateActive" onclick="updateActive();"/>  OK
				</td>
				<td>
					<br><br>
					<input type="button" value="duplicate" onclick="duplicate();"/>	OK
					<br><br>
					<input type="button" value="serverValidate" onclick="serverValidate();"/> Notifiy Alert
					<br><br>
					<input type="button" value="authenticate" onclick="authenticate();"/>
					<br><br>
					<input type="button" value="authorization" onclick="authorization();"/>
					<br><br>
					<input type="button" value="exception" onclick="exception();"/> OK
					<br><br>
					<input type="button" value="exceptionMessage" onclick="exceptionMessage();"/> OK
					<br><br>
					<input type="button" value="ajaxException" onclick="ajaxException();"/> OK
					<br><br>
					<input type="button" value="customBasic" onclick="customBasic();"/> OK
					<br><br>
					<input type="button" value="customListMsg" onclick="customListMsg();"/> OK
				</td>
			</tr>
		</table>
		
		
		<s:token/>	
	</s:form>
</body>
</html>