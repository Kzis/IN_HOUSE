<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>
<html>
<head>
<link href="<s:url value='/css/style-to.css' />" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
	var centralPath = "<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>";
	
	//For data table
	var aOption = {
			divResultId: "div_datatable",
			tableId: "tableResult",
			urlSearch: "<s:url value='/jsp/approve/searchTimeOffsetApprove.action' />",
			urlEdit: {
				url: "<s:url value='/jsp/approve/gotoEditTimeOffsetApprove.action' />",
				authen: "<s:property value='ATH.edit' />"
			},
			urlView: {
				url: "<s:url value='/jsp/approve/gotoViewTimeOffsetApprove.action' />",
				authen: "<s:property value='ATH.view' />"
			},
			pk: "approve.id",
			createdRowFunc: "manageRow"
	};
	
	var colData = [
	               { data: null, class: "order", orderable: false, defaultContent: ""},
                   { data: null, class: "checkbox d_view", orderable: false, defaultContent: ""},
                   { data: null, class: "checkbox d_approve", orderable: false, defaultContent: ""},
                   { data: "projectAbbr", class: "col-width-150px"},
                   { data: "projConDetail", class: "col-width-300px"},
                   { data: "fullName",class: "col-width-200px"},
                   { data: "day", class: "right col-width-100px", orderable: false},
                   { data: "hour", class: "right col-width-100px", orderable: false},
                   { data: "minute", class: "right col-width-100px", orderable: false},
                   { data: "approveUser", class: "col-width-150px", orderable: false},
                   { data: "processStatus", class: "center col-width-auto d_approveStatus", orderable: false}
                  ];

	$( document ).ready(function() {

		loadEmployee();
		
		jQuery("#criteria_projectId").selectmenu({
			change: function( event, ui ) {
				loadCondition();
			}
   		});
		
	});	
	
	function loadEmployee(){
	
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
	 				var combo = "<select id='criteria_projConId' name='criteria.projConId' class='combox-to clearform' >";
	 				combo += "<option value=''><s:text name='all' /></option>";

	 				for (var index = 0; index < jsonData.length; index++) {
						combo += "<option vlocaalue='" + jsonData[index].key + "'>" + jsonData[index].value + "</option>";			
	 				}
	 				
	 				combo += "</select>";
	 				jQuery("#spanCondition").html(combo);
	              }
	          });
		}else{
			var combo = "<select id='criteria_projConId' name='criteria.projConId' class='combox-to clearform' disabled='disabled'>";
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
	
	function sf() {

		setStyleManual();
		
		if(jQuery("[name='criteria.criteriaKey']").val() != ""){
            searchAjax();
        }
		
	}
	
	function setStyleManual(){
	
		var widtDepartment = jQuery("#criteria_projectId-button").width();
		
		//Set department ไม่ fresh width ในครั้งแรกที่วาด (เพราะใช้ selectitemx) 
		jQuery("#department_selectitem-button").css({
			'width':widtDepartment
		});

		jQuery("#employee_autocomplete_input_id").css({
			'width':widtDepartment
		});
		
	}
	
	function clearFormCallBack(){
		
		//Clear ชื่อสกุลพนักงาน และทำให้เป็น Disable (Autocomplete)
		jQuery("#employee_autocomplete_input_id").val("");
		jQuery("#employee_autocomplete_input_id").attr('disabled', 'disabled');
		
		//Clear เงื่อนไขเวลาชดเชย และทำให้เป็น Disable (Selectitem)
		jQuery("#criteria_projConId").val("");
		jQuery("#criteria_projConId").attr('disabled', 'disabled');
		jQuery("#criteria_projConId").selectmenu("refresh");
		
	}
	
	function searchPage(){
		document.getElementsByName('criteria.criteriaKey')[0].value = '';
		searchAjax()
	}
	
	function clearPage(){
		submitPage("<s:url value='/jsp/approve/initTimeOffsetApprove.action'/>")
	}
	
	function searchAjax(){
       dataTable(centralPath, colData, aOption);
	}
	
	function manageRow(row, data) {

		var iconApprove = "<img class='cursor' title='"+ data.approveStatusDesc + "' src='<s:url value='"+centralPath+"/images/menu/i_check.png'/>'>";
		var iconDisApprove = "<img class='cursor' title='"+ data.approveStatusDesc + "' src='<s:url value='"+centralPath+"/images/menu/i_delete.png'/>'>";
		var iconWaitApprove = "<img class='cursor' title='"+ data.approveStatusDesc + "' src='<s:url value='"+centralPath+"/images/menu/i_help.png'/>'>";
		
		if(data.processStatus == "W"){
			jQuery('.d_approveStatus',row).html(iconWaitApprove);
		}else if(data.approveStatus == "D"){
			jQuery('.d_approveStatus',row).html(iconDisApprove);
		}else if(data.processStatus == "C"){
			jQuery('.d_approveStatus',row).html(iconApprove);
		}
		
		var url = "/timeoffset/jsp/approve/gotoEditTimeOffsetApprove.action";
		var id = data.id;
		var pk = "approve.id";
		
		var submit = 'submitAction("url","pk","id")';
		submit = submit.replace("id",id);
		submit = submit.replace("url",url);
		submit = submit.replace("pk",pk);
		
		var iconApprove = "<img class='cursor' title='"+ '<s:text name="to.approve" />' + "' src='<s:url value='"+centralPath+"/images/icon/i_edit.png'/>'"+"' onclick='" + submit + "'>";
		
		if('<s:property value="ATH.approve"/>'){
			jQuery('.d_approve',row).html(iconApprove);
		}
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
					<td class="LABEL" style="width: 10%"><s:text name='to.department' /><em>&nbsp;&nbsp;</em></td>

					<td class="VALUE" colspan="3">
						<s:textfield id="criteria_departmentId" name="criteria.departmentId" code-of="department_selectitem" cssClass="combox-to clearform" />
						<s:textfield id="criteria_departmentName" name="criteria.departmentName" text-of="department_selectitem" cssClass="combox-to clearform" />
					</td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 10%"><s:text name='to.fullNameEmp' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE" colspan="3">
						<span id="spanEmp">
							<s:textfield id="criteria_employeeId" name="criteria.employeeId" code-of="employee_autocomplete" cssClass="autocomplete-to"/>
                        	<s:textfield id="criteria_employeeName" name="criteria.employeeName" text-of="employee_autocomplete" cssClass="autocomplete-to"/>
						</span>
					</td>
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
							cssClass="combox-to clearform"
							/>
					</td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 10%"><s:text name='to.conditionTime' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE" colspan="3">
						<span id="spanCondition">
		           			<select id="criteria_projConId" name="criteria.projConId" class="combox-to" disabled="disabled">
								<option value=""><s:text name='all' /></option>
							</select>
						</span>
					</td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 10%"><s:text name='to.dateFrom' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
						<s:textfield 
							id="criteria_startDate"
							name="criteria.startDate"
							cssClass="input_dateformat input_datepicker_from_to datepicker clearform" 
							cssStyle="width:160px;"
							datepicker-id-from="criteria_startDate"
							datepicker-id-to="criteria_endDate" />
					</td>
					<td class="LABEL"><s:text name='to.dateTo' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
					<s:textfield 
						id="criteria_endDate"
						name="criteria.endDate"
						cssClass="input_dateformat datepicker clearform"
						cssStyle="width:130px;" /> 
					</td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 10%"><s:text name='approveStatus' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
						<s:select 					
							id="criteria_processStatus"	
							name="criteria.processStatus"
							list="listProcessStatus"
							headerKey=""
							headerValue="%{getText('all')}"
							listKey="key"
							listValue="value" 
							cssClass=" clearform"
							cssStyle="width:150px;"
							/>
					</td>
					<td class="LABEL"></td>
					<td class="VALUE"></td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 10%"> <s:text name='recordsPerPage' /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
						<div>
							<s:select 
								id="criteria_linePerPage" 
								cssClass="lpp-style clearform"
								name="criteria.linePerPage" 
								list="LPP"
								cssStyle="width:150px;"
							/>
						</div>
					</td>
					<td class="LABEL"></td>
					<td class="VALUE"></td>
					<td class="BORDER"></td>
				</tr>
			</table>
			
			<!------------------------------------- BUTTON ----------------------------------->
			<div id="divButtonSearch" class="ui-sitbutton"
				data-buttonType="search"
				data-auth="<s:property value='ATH.search'/>"
				data-divCriteriaId="divCriteria"
				data-divTableResultId="div_datatable"
				data-funcSearch="searchPage"
				data-funcClearFormCallBack="clearFormCallBack">
			</div>
			
			<s:include value="/jsp/template/hiddenSearchForDatatable.jsp" />
			
		</div>
	</div>
	
	<!--------------------------------------- divResult --------------------------------------->
	<div class="RESULT RESULT_SYSTEM">
		<div id="div_datatable" class="ex_highlight_row" style="display: none;">
			<table class="display" id="tableResult">
				<thead>
					<tr>
						<th class="order"><s:text name="no"></s:text></th>
						<th class="checkbox"><s:text name="view"/></th>
						<th class="checkbox"><s:text name="approve"/></th>
						<th><s:text name="to.project" /></th>
						<th><s:text name="to.conditionTime" /></th>
						<th><s:text name="to.fullNameEmp" /></th>
						<th><s:text name="to.totalDayOffset" /></th>
						<th><s:text name="to.totalHourOffset" /></th>
						<th><s:text name="to.totalMinuteOffset" /></th>
						<th><s:text name="to.approver" /></th>
						<th><s:text name="approveStatus" /></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="10" class="dataTables_empty">Loading data from server</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
		
	<s:hidden id="approve_id" name="approve.id" />
	<s:token />
</s:form>
	
</body>
</html>