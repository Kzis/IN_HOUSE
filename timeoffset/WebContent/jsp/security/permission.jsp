<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<script type="text/javascript">
		$(document).keypress(function(e) {
		    if(e.which == 13) {
		    	login();
		    }
		});
	
		function login() {
	
			if(validateFormInputAll() == false){
				return false;
			}
		
			submitPage("<s:url value='/jsp/security/loginUser.action' />");
		}
			
	</script>
	
	<style type="text/css">
		TABLE.FORM TD {
			/** border: 1px solid black; **/
		} 
		
		INPUT[type=text] {
			width: 55%;
		}
	</style>
	
</head>
<body>
	<s:form id="permissionForm" name="permissionForm" method="post"
		namespace="/" action="initPermission" cssClass="margin-zero" onsubmit="return false;">
		<table class="FORM">
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL"> &nbsp; </td>
				<td class="VALUE"> &nbsp; </td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL"><s:text name="to.loginemail" /><em>*</em></td>
				<td class="VALUE">
					<s:textfield 
						id="permission_username" 
						name="username"
						cssClass="requireInput  ui-corner-all"
						validName="%{getText('to.loginemail')}"
						placeholder="e.g. john.s"
						value=""
					/>
				</td>
				<td class="BORDER"></td>
			</tr>

			<tr>
				<td class="BORDER"></td>
				<td class="LABEL"><s:text name="to.loginhrm" /><em>*</em></td>
				<td class="VALUE">
					<s:password 
						id="permission_password" 
						name="password"
						cssClass="requireInput  ui-corner-all"
						validName="%{getText('to.loginhrm')}"
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
						<input type="button"
							class="ui-button ui-corner-all ui-widget" 
							id="btnLogin" 
							value='<s:text name="Sign in"/>' onclick="login();"
						/>
					</div>
				</td>
				<td class="BORDER"></td>
			</tr>
		</table>
		
		<s:token />
	</s:form>
</body>
</html>