<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<fieldset class="fieldsetStyle hide-border hide-background" id="fieldset_manage">
	<table class="FORM">
		<tr style="display: none;">
			<td class="BORDER"></td>
			<td class="LABEL" style="width: 20%"></td>
			<td class="VALUE" style="width: 30%"></td>
			<td class="BORDER"></td>
		</tr>
		<tr>
			<td class="BORDER"></td>
			<td class="LABEL" style="width: 20%"><s:text name='to.fullNameEmp' /><em>&nbsp;&nbsp;</em></td>
			<td class="VALUE" style="width: 30%">			
				<s:textfield id="manage_fullName" value="%{#session.USER_SESSION.fullName}" cssClass="readonly txt79" />
			</td>
			<td class="BORDER"></td>
		</tr>
	</table>
</fieldset>