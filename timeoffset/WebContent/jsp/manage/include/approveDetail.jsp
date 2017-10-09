<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<fieldset class="fieldsetStyle" id="fieldset_manage">
	<legend><s:text name='to.approveDetail' /></legend>
	
	<br/>
	<table class="FORM">
		<tr style="display: none;">
			<td class="BORDER"></td>
			<td class="LABEL" style="width: 25%"></td>
			<td class="VALUE" style="width: 40%"></td>
			<td class="VALUE" style="width: 5%">
			<td class="BORDER"></td>
		</tr>
		<tr>
			<td class="BORDER"></td>
			<td class="LABEL" style="width: 25%; vertical-align: top"><s:text name='to.remark' /><em>*</em> :</td>
			<td class="VALUE" style="width: 40%"><s:textarea id="manage_approveRemark" name="manage.approveRemark" cssClass="readonly" cols="37" rows="2"/></td>
			<td class="VALUE" style="width: 5%">
			<td class="BORDER"></td>
		</tr>
		<tr>
			<td class="BORDER"></td>
			<td class="LABEL" style="width: 25%"><s:text name='to.approver' /></td>
			<td class="VALUE" style="width: 40%"><s:textfield id="manage_approveUser" name="manage.approveUser" cssClass="readonly txt50" /></td>
			<td class="VALUE" style="width: 5%">
			<td class="BORDER"></td>
		</tr>
	</table>
</fieldset>