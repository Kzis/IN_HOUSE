<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.UUID"%>

<%
String tokenName = UUID.randomUUID().toString();
%>

<html>
<head>
<script type="text/javascript">
	
	var hourTotGlobal;
	
	function sf(){

		var activeCode = "<s:property value='projectCondition.active.code'/>";
		var projConFlag = "<s:property value='projectCondition.projConFlag' />";
		
		if (jQuery("#page").val() == "ADD" || jQuery("#page").val() == undefined) {
	
			jQuery("#proj_con_id").val("");
			
			//Set Selected Con Proj Status , Active
			jQuery("#proj_con_status").prop('checked', true);
			jQuery("#proj_con_flag").prop('checked', true);
			
			setStyle();
			
		} else {

			//Set Selected Con Proj Status , By value
			if(projConFlag == "Y"){
				jQuery("#proj_con_flag").prop('checked', true);
				jQuery("#projectCondition_hourTot").val(hourTotGlobal);
			} else {
				jQuery("#proj_con_flag").prop('checked', false);
			}
			
			if(activeCode == "Y"){
				jQuery("#proj_con_status").prop('checked', true);
			}else{
				jQuery("#proj_con_status").prop('checked', false);
			}
			
			setStyle();
			
		}
		
		//Default UnCkeckedbox : hourTOT
		jQuery('#proj_con_flag').removeAttr('checked');
		jQuery('#projectCondition_hourTot').addClass('readonly');
		
	}
	
	/*
	 Function สำหรับจัดการตอน ติ๊ก Checkbox
	*/
	function setStyle() {

		//Default balue
		var defaultHourTot = "0.0";
		
		//รับค่าที่ติ๊กมาใส่ตัวแปร
		projConFlag = jQuery("#proj_con_flag").prop('checked');
		hourTotGlobal = jQuery("#projectCondition_hourTot").val();
		
		//ถ้า Checkbox มีการ check
		if(projConFlag == true || projConFlag == undefined){

			// TEXTBOX AMOUNT TIME OFFSET ENABLE 
			
			//ใส่ค่าชั่วโมงชดเชย
// 			jQuery("#projectCondition_hourTot").val(hourTot);
			
			jQuery("#projectCondition_hourTot").removeAttr('disabled');
			jQuery("#projectCondition_hourTot").removeClass('readonly');
			jQuery("#projectCondition_hourTot").addClass('requireInput');
			
		} else {

			// TEXTBOX AMOUNT TIME OFFSET DISABLE
			
			//ใส่ค่า Default
			jQuery("#projectCondition_hourTot").val(defaultHourTot);
			
			jQuery("#projectCondition_hourTot").attr('disabled', true);
			jQuery("#projectCondition_hourTot").removeClass('requireInput');
		}
		
	}
	
	/*
	 Function validate 
	   หลังจากที่ปุ่ม validateFromInputAll ของตัวกลางเสร็จแล้ว
	*/
	function validateProjectCondition(){
		
		var projConFlag = jQuery("#proj_con_flag").prop('checked');
		var hourTot = jQuery("#projectCondition_hourTot").val();
		
		//Check ว่าถ้ามีการติ๊ก Checkbox มาให้ทำการ Validate
		if(projConFlag == true || projConFlag == undefined){
			
			//ถ้ามีค่าแล้วต้องมีค่ามากกว่า 0
			if( (parseFloat(hourTot) > 0.0) || (parseFloat(hourTot) > 0) ){
				
				//ถ้ามีค่าแล้วต้องนอยกว่า 24
				if( parseFloat(hourTot) < 24 ){
					return true;
				}else{
					//ข้อมูลไม่ถูกต้อง กรุณาตรวจสอบข้อมูล
					alert('<s:text name="10004" />');
					jQuery("#projectCondition_hourTot").focus();
					return false;
				}
				
			}else{
				//ข้อมูลไม่ถูกต้อง กรุณาตรวจสอบข้อมูล
				alert('<s:text name="10004" />');
				jQuery("#projectCondition_hourTot").focus();
				return false;
			}
			
		}else{
			return true
		}
	
	}
	
	//##### [Button Function] #####

	// Page Add
	function saveAdd(){
		
		submitPage("<s:url value='/jsp/condition/addProjectCondition.action'/>")
	}
	
	function cancelAdd(){

		submitPageForm();
	}
	
	//Page Edit
	function saveEdit(){
		submitPage("<s:url value='/jsp/condition/editProjectCondition.action'/>")
	}
	
	function cancelEdit(){
		
		submitPageForm();
	}

	function submitPageForm() {
		if (document.getElementsByName('criteria.criteriaKey')[0].value == '') {
			action = "<s:url value='/jsp/condition/initProjectCondition.action' />";
		} else {
			action = "<s:url value='/jsp/condition/cancelProjectCondition.action' />";
		}
		submitPage(action);
	}
</script>
</head>

<body>
	<s:form id="addEditForm" name="addEditForm" method="post" cssClass="margin-zero" onsubmit="return false;">
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
				<td class="LABEL" style="width: 30%"><s:text name='to.project' /><em>*</em></td>
				<td class="VALUE" style="width: 60%">
					<s:select 					
						id="projectCondition_projectId"					
						name="projectCondition.projectId"
						list="listProject"
						headerKey=""
						headerValue=""
						listKey="key"
						listValue="value"
						cssClass="requireInput"
						cssStyle="width: 340px;" 
						validName="%{getText('to.project')}" 
						/>
				</td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%; vertical-align: top"><s:text name='to.projectDetail' /><em>*</em></td>
				<td class="VALUE" style="width: 60%">
					<s:textarea 
						id="projectCondition_projConDetail" 
						name="projectCondition.projConDetail" 
						cssClass="requireInput" 
						cssStyle="resize:none; width: 350px;" 
						rows="3" 
						validName="%{getText('to.projectDetail')}" 
					/>
				</td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%"><s:text name='to.timeConfig' /><em>&nbsp;&nbsp;</em></td>
				<td class="VALUE" style="width: 60%">
					<s:checkbox 
						id='proj_con_flag' 
						name='projectCondition.projConFlag' 
						onclick='setStyle(this.checked)'
					/>
					<s:text name='to.totalHourOffset' />
					<em>*</em><em>&nbsp;&nbsp;</em>
					<s:textfield id="projectCondition_hourTot" name="projectCondition.hourTot" cssClass="input_number_2_1 readonly requireInput txt5" validName="%{getText('to.totalTimeOffset')}" />
					<em>&nbsp;&nbsp;</em>
					<s:text name='to.hour' />
				</td>
					
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%"><s:text name='activeStatus' /><em>&nbsp;&nbsp;</em></td>
				<td class="VALUE" style="width: 60%">
					<s:checkbox id='proj_con_status' name='projectCondition.active.code' />
					<s:text name='active' />
				</td>
				<td class="BORDER"></td>
			</tr>
		</table>
		
		<!------------------------------------- BUTTON ----------------------------------->
		<s:if test="page.getPage() == 'add' ">

			<div id="divButtonAdd" class="ui-sitbutton"
				data-buttonType="add"
				data-auth="<s:property value='ATH.add'/>"
				data-messageConfirmAdd = "<s:text name="50003" />"
				data-messageCancelAdd = "<s:text name="50009" />"
				data-funcValidAdd = "validateProjectCondition"
				data-funcSaveAdd = "saveAdd"
				data-funcCancelAdd = "cancelAdd">
			</div>
						
		</s:if>
		
		<s:elseif test="page.getPage() == 'edit' ">
			
			<div id="divButtonEdit" class="ui-sitbutton"
				data-buttonType="edit"
				data-auth="<s:property value='ATH.edit'/>"
				data-messageConfirmEdit = "<s:text name="50004" />"
				data-messageCancelEdit = "<s:text name="50009" />"
				data-funcValidEdit = "validateProjectCondition"
				data-funcSaveAdd = "saveEdit"
				data-funcCancelAdd = "cancelEdit">
			</div>
			
		</s:elseif>
		
		<!-- Hidden value -->
		<s:hidden id="page" name="page" />
		<s:hidden name="criteria.criteriaKey" />
		<s:hidden name="P_CODE" />
		<s:hidden name="F_CODE" />
		<s:hidden id="proj_con_id" name="projectCondition.id" />
		<s:token name="%{@org.apache.struts2.util.TokenHelper@generateGUID()}" />
	</s:form>
	
	<script>
        // ใช้งานกับ input ที่มี class เป็น input_number_5
        new NumberControl(2,1);
    </script>
	
</body>
</html>
