<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<s:if test="page.getPage() == 'approve'"> 
	 <s:set var="cssClass" value="" />
</s:if>
<s:else>
	<s:set var="cssClass" value="%{'readonly'}" />
</s:else>
	
	<br/>
	<table class="FORM">
		<tr style="display: none;">
			<td class="BORDER"></td>
			<td class="LABEL" style="width: 30%"></td>
			<td class="VALUE" style="width: 60%"></td>
			<td class="BORDER"></td>
		</tr>
		<tr>
			<td class="BORDER"></td>
			<td class="LABEL" style="width: 30%; vertical-align: top"><s:text name='to.remark' /><em>&nbsp;&nbsp;</em></td>
			<td class="VALUE" style="width: 60%">
				<s:textarea id="approve_remark" name="approve.remark" cssClass="%{#cssClass}" cssStyle="resize: none;" cols="55" rows="2"  validName="%{getText('to.remark')}" />
			</td>
			<td class="BORDER"></td>
		</tr>
		
		<s:if test="page.getPage() == 'view'">
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%"><s:text name='to.approver' /><em>&nbsp;&nbsp;</em></td>
				<td class="VALUE" style="width: 60%"><s:textfield id="approve_approveUser" name="approve.approveUser" cssClass="readonly txt50" /></td>
				<td class="BORDER"></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td class="BORDER"></td> -->
<%-- 				<td class="LABEL" style="width: 30%"><s:text name='to.dateApprove' /><em>&nbsp;&nbsp;</em></td> --%>
<!-- 				<td class="VALUE" style="width: 60%"> -->
<%-- 					<s:textfield id="approve_approveDate" name="approve.approveDate" cssClass="readonly txt35" /> --%>
<%-- 					<em>&nbsp;&nbsp;</em><s:text name='to.time' /> --%>
<%-- 					<em>&nbsp;&nbsp;</em><s:textfield id="approve_approveTime" name="approve.approveTime" cssClass="readonly txt100" /> --%>
<%-- 					<em style="padding-left:5px;">&nbsp;&nbsp;</em><s:text name='to.min' /> --%>
<!-- 				</td> -->
<!-- 				<td class="BORDER"></td> -->
<!-- 			</tr> -->
		</s:if>
	</table>
	
	
<!-- </fieldset> -->

