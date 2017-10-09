<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<s:include value="/jsp/security/include/permission-js-css.jsp"/>
</head>
<body>
	<s:form id="permissionForm" name="permissionForm" method="post"
		namespace="/" action="initPermission" cssClass="margin-zero" onsubmit="return false;">
		<table class="FORM">
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL"><s:text name="bk.loginemail" /><em>*</em></td>
				<td class="VALUE">
					<s:textfield 
						id="permission_username" 
						name="username"
						cssClass="requireInput"
						validName="%{getText('bk.loginemail')}"
						placeholder="e.g. john.s"
						value=""
					/>
				</td>
				<td class="BORDER"></td>
			</tr>
			
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL"><s:text name="bk.loginhrm" /><em>*</em></td>
				<td class="VALUE">
					<s:password 
						id="permission_password" 
						name="password"
						cssClass="requireInput"
						validName="%{getText('bk.loginhrm')}"
						placeholder="e.g. 556643"
						maxlength="6"
						value=""
					/>
				</td>
				<td class="BORDER"></td>
			</tr>
			
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL"></td>
				<td class="VALUE">
					<div id="divButtonSearchPredefine" style="text-align: center; float: left;" >
						<input type="button" class="ui-button ui-corner-all ui-widget" id="btnLogin" value='<s:text name="Sign in"/>' onclick="login();"/>
					</div>
				</td>
				<td class="BORDER"></td>
			</tr>
		</table>
		
		<s:token />
	</s:form>
</body>
</html>