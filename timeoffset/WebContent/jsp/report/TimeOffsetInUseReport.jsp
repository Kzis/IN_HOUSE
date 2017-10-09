<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>
<%@page import="java.util.UUID"%>

<%
String tokenName = UUID.randomUUID().toString();
%>

<html>
<head>
<link href="<s:url value='/css/style-to.css' />" rel="stylesheet" type="text/css"/>
<script type="text/javascript">

	$( document ).ready(function() {
		
		jQuery("#criteria_projectId").selectmenu({
			change: function( event, ui ) {
				loadCondition();
			}
			});
	});	
	
	function sf(){
		
		loadDepartmentAndEmployeeName();
			
	}
		
	function loadCondition(){
		var projectId = jQuery("#criteria_projectId option:selected").val();
		
		if(projectId != ""){
			//โหลด combobox
			jQuery.ajax({
	            type : "POST",
	            url: "<s:url value='/selectitem/ProjectConditionSelectItemServlet'/>",
	            data : {projectId: projectId},
	            async : false,
	            success : function(jsonData) {
	 				var combo = "<select id='criteria_projConId' name='criteria.projConId' class='requireInput combox-to'>";
	 				combo += "<option value=''><s:text name='all' /></option>";

	 				for (var index = 0; index < jsonData.length; index++) {
						combo += "<option value='" + jsonData[index].key + "'>" + jsonData[index].value + "</option>";			
	 				}
	 				
	 				combo += "</select>";
	 				jQuery("#spanCondition").html(combo);
	              }
	          });
		}else{
			var combo = "<select id='criteria_projConId' name='criteria.projConId' class='requireInput combox-to' disabled='disabled'>";
			combo += "<option value=''><s:text name='all' /></option>";
			combo += "</select>";
			jQuery("#spanCondition").html(combo);
		}
		
		jQuery("#criteria_projConId").addClass('combox-to');
		jQuery("#criteria_projConId").selectmenu();
		
		var widtDepartment = jQuery("#criteria_projectId-button").width();
		
		//Set conditionproject ไม่ fresh width ในครั้งแรกที่วาด
		jQuery("#criteria_projConId-button").css({
			'width': widtDepartment
		});

		jQuery("#criteria_projConId").selectmenu("refresh");
		
	}
	
	function loadDepartmentAndEmployeeName(){
			
			jQuery("#criteria_departmentId").selectitemx(
					[{
						url:"<s:url value='/selectitem/DepartmentSelectItemServlet'/>",
						defaultKey: "",
						defaultValue: '<s:text name="all"/>',
						selectitemType: "D",
						cssClass:"combox-to clearform"
					},{
						inputModelId: 'criteria_employeeId',
						url: "<s:url value='/selectitem/EmployeeFullnameByDepartmentIdServlet'/>",
						defaultKey: "",
						defaultValue: '<s:text name="all"/>',
						postParamsId: {departmentId: "criteria_departmentId"},
						selectitemType: "A",
						cssClass:"autocomplete-to"
					}]		
				);
			
		}
	
	// ##### [BUTTON FUNCTION] #####
	
	function printPage(){	
		
		if(!validateFormInputAll()){ // ตรวจสอบ require field ของ form นั้นๆ
	        return false;
	    }
		
		//Set value
		jQuery("#timeOffsetInUseReport_projectName").val(jQuery("#criteria_projectId option:selected").text());
		jQuery("#timeOffsetInUseReport_projConName").val(jQuery("#criteria_projConId option:selected").text());
		
		console.log("projectName : " + jQuery("#timeOffsetInUseReport_projectName").val());
		console.log("projectConditionName : " + jQuery("#timeOffsetInUseReport_projConName").val());

		submitPageReport("<s:url value='/jsp/report/exportTOReport.action'/>")
	}
	
	function cancel(){
		submitPage("<s:url value='/jsp/report/initTOReport.action'/>")
	}


</script>
</head>
<body>
	
	<s:form id="searchForm" name="searchForm" method="post" cssClass="margin-zero" onsubmit="return false;">
	
	<div id="divSearchForm" class="CRITERIA CRITERIA_SYSTEM">
		<div id="divCriteria" class="RESULT_BOX BORDER_COLOR STYLE_CRITERIA_1600" style="">
		
			<div class="RESULT_BOX_TITAL">
				<table class="TOTAL_RECORD">
					<tr>
						<td class="LEFT" style="width: 10%; vertical-align: middle;"><s:text name="criteria" /></td>
					</tr>
				</table>
			</div>
			
			<br/>
			
			<table class="FORM">
				<tr style="display: none;">
					<td class="BORDER"></td>
					<td class="LABEL"></td>
					<td class="VALUE"></td>
					<td class="LABEL"></td>
					<td class="VALUE"></td>
					<td class="BORDER"></td>
				</tr>
				
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 10%"><s:text name='to.project' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE" colspan="3">
						<s:select 					
							id="criteria_projectId"					
							name="criteria.projectId"
							list="listProject"
							headerKey=""
							headerValue="%{getText('all')}"
							listKey="key"
							listValue="value"
							cssClass="requireInput combox-to"
							/>
					</td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 10%"><s:text name='to.conditionTime' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE" colspan="3">
						<span id="spanCondition">
		           			<select id="criteria_projConId" name="criteria.projConId" class="requireInput combox-to" disabled="disabled">
								<option value=""><s:text name='all' /></option>
							</select>
						</span>
					</td>
					<td class="BORDER"></td>
				</tr>
				
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 10%"><s:text name='to.department' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
						<s:textfield id="criteria_departmentId" name="criteria.departmentId" code-of="department_selectitem" cssClass="combox-to" validName="%{getText('to.department')}" />
						<s:textfield id="criteria_departmentName" name="criteria.departmentName" text-of="department_selectitem" cssClass="combox-to" validName="%{getText('to.department')}" />
					</td>
		
					<td class="LABEL"></td>
					<td class="VALUE"></td>
					<td class="BORDER"></td>
				</tr>
					
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL"><s:text name='to.fullNameEmp' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
						<span id="spanFullNameEmp">
							<s:textfield id="criteria_employeeId" name="criteria.employeeId" code-of="employee_autocomplete" cssClass="autocomplete-to"  validName="%{getText('to.fullNameEmp')}"/>
							<s:textfield id="criteria_employeeName" name="criteria.employeeName" text-of="employee_autocomplete" cssClass="autocomplete-to"  validName="%{getText('to.fullNameEmp')}"/>
						</span>
					</td>
		
					<td class="LABEL"></td>
					<td class="VALUE"></td>
					<td class="BORDER"></td>
				</tr>
				
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 10%"><s:text name='to.dateFrom' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
					<s:textfield 
						id="criteria_startDate"
						name="criteria.startDate"
						cssClass="input_datepicker"
						cssStyle="width: 160px;"
						validName="" />
					</td>
			
					<td class="LABEL" style="width: 10%"><s:text name='to.dateTo' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
					<s:textfield 
						id="criteria_endDate"
						name="criteria.endDate"
						cssClass="input_datepicker_from_to "
						cssStyle="width: 150px;"
						validName=""
						datepicker-id-from="criteria_startDate" datepicker-id-to="criteria_endDate"/> 
					</td>
					<td class="BORDER"></td>
				</tr>
				
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 10%"><s:text name='to.approveStatusTO' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
						<s:select 					
							id="criteria_approveStatus"	
							name="criteria.approveStatus"
							list="listApprove"
							headerKey=""
							headerValue="%{getText('all')}"
							listKey="key"
							listValue="value" 
							cssClass="combox-to"
							cssStyle="width: 150px;"
							/>
					</td>
					<td class="LABEL"></td>
					<td class="VALUE"></td>
					<td class="BORDER"></td>
				</tr>
				
			</table>
			
			<!------------------------------------- BUTTON ----------------------------------->			
			<div id="divButtonPredefinePrint" class="ui-sitbutton-predefine"
				data-buttonType = "print|clear"
				data-auth = "<s:property value='ATH.print'/>|true"
				data-func = "printPage()|cancel()"
				data-style = "btn-small|btn-small"
				data-container = "true">
			</div>
							
			<br/>
				
		</div>
	</div>
	
	<s:hidden id="timeOffsetInUseReport_projectName" name="report.projectName" />
	<s:hidden id="timeOffsetInUseReport_projConName" name="report.projConName" />
	<s:token name="%{@org.apache.struts2.util.TokenHelper@generateGUID()}" />
	</s:form>
	
</body>
</html>