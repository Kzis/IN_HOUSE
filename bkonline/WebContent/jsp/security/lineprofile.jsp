<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<s:include value="/jsp/security/include/permission-js-css.jsp"/>
</head>
<body>
	<s:form id="permissionForm" name="permissionForm" method="post"
		namespace="/" action="initUser" cssClass="margin-zero" onsubmit="return false;">
		<table class="FORM">
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL"><s:text name="bk.lineDisplayName" /><em>*</em></td>
				<td class="VALUE">
					<s:textfield 
						id="lineDisName" 
						name="lineDisplayName"
						cssClass="requireInput"
						validName="%{getText('bk.lineDisplayName')}"
					/>
					<div 
						id="divButtonSearchPredefine" 
						class="ui-sitbutton-predefine"  
						style="text-align: center; display: inline;"
						data-buttonType="save" 
						data-auth="true" 
						data-container ="false"
						data-func = "saveAdd()"	>
					</div>
				</td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL"></td>
				<td class="VALUE">ต้องกรอกเป็นตัวอักษรภาษาอังกฤษเท่านั้น ห้ามมี emoji และต้องมีตัวเล็ก ตัวใหญ่ตรงกัน</td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td colspan="2" style="text-align: center;">
					<div id="divButtonSearchPredefine" style="text-align: center;" >
						<input type="button" class="ui-button ui-corner-all ui-widget" id="btnLogin" value='<s:text name="Ready to Meeting Room Booking"/>' onclick="gotoMeeting();"/>
					</div>
				</td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td colspan="2" style="text-align: center;">
					<h4 style="margin-bottom: 0px;">วิธีการตรวจสอบ Line Display Name สำหรับ PC</h4>
					<img src="${application.CONTEXT_OWNER}/jsp/security/include/0-line-profile-pc-01.png" style="width: 300px;">
					<img src="${application.CONTEXT_OWNER}/jsp/security/include/1-line-profile-pc-01.png" style="width: 300px;">
				</td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td colspan="2" style="text-align: center;">
					<h4 style="margin-bottom: 0px;">วิธีการตรวจสอบ Line Display Name สำหรับ Android</h4>
					<img src="${application.CONTEXT_OWNER}/jsp/security/include/0-line-profile-android-01.png" style="width: 300px;">
					<img src="${application.CONTEXT_OWNER}/jsp/security/include/1-line-profile-android-01.png" style="width: 300px;">
				</td>
				<td class="BORDER"></td>
			</tr>
		</table>
		<s:token />
	</s:form>
</body>
</html>