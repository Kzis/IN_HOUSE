<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>
<%@page import="java.util.UUID"%>

<%
String tokenName = UUID.randomUUID().toString();
%>

<html>
<head>

<link href="<s:url value='/css/style-to.css' />" rel="stylesheet" type="text/css"/>

<script type="text/javascript">

	var centralPath = "<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>";
	
	var footerHtml =  "<s:text name="to.remark" />"  + " : "
					+ "<s:text name="to.descriptionUnitOfDay" />" + " , " 
					+ "<s:text name="to.descriptionUnitOfHour" />" + " , "
					+ "<s:text name="to.descriptionUnitOfMinute" />"
	
	var aOption = {
			divResultId: "div_datatable",
			tableId: "tableResult",
			urlSearch: "<s:url value='/jsp/report/searchInuseManagerReport.action' />",
			urlEdit: {
				url: "",
				authen: ""
			},
			urlView: {
				url: "",
				authen: ""
			},
			pk: "report.id",
			createdRowFunc: "manageRow",
// 			createdTableFunc: "changeTable",
			childRow: "Y",
			checkbox: "N",
			footerHtml: footerHtml
	};
	
	var colData = [
	               { data: null, class: "order col-width-30fix", orderable: false, defaultContent: ""},
                   { data: null, class: "syndata col-width-30fix", orderable: false, defaultContent: ""},
                   { data: null, class: "details-control show_datail col-width-30fix", orderable: false, defaultContent: ""},
                   { data: "department",class: "col-width-105px", orderable: false},
                   { data: "fullName", class: "thaiName col-width-135px", orderable: false},
                   { data: "timeOffset", class: "right col-width-100px", orderable: false},
                   { data: "timePendingHRM", class: "right col-width-100px", orderable: false},
                   { data: "dataTimeSyndataLasted", class: "center col-width-120px", orderable: false}
                  ];

	function sf(){
		
		loadDepartmentAndEmployeeName();
				
		if(jQuery("[name='criteria.criteriaKey']").val() != ""){
            searchAjax();
        }
		
	}
		
	function loadDepartmentAndEmployeeName(){
		
		jQuery("#criteria_departmentId").selectitemx(
				[{
					url:"<s:url value='/selectitem/DepartmentSelectItemServlet'/>",
					defaultKey: "",
					defaultValue: '<s:text name="all"/>',
					selectitemType: "D",
					cssClass:"combox-to"
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
	
	function searchAjax(){
		
		rowChild("<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>", colData, aOption);
	
	}
	
	function manageRow(row, data) {
		
		if(data.listDetail == null || data.listDetail.length == 0){
			jQuery('.show_datail',row).attr('class','');
		}
		
		var colour = data.highlightRow;
				
		if(data.highlightRow == 2){ //red		
			jQuery(row).addClass("highlight-red-style");		
		}else if(data.highlightRow == 3){ //yellow
			jQuery(row).addClass("highlight-yellow-style");
		}

		var idRow = data.id;

		var imgSyn = "<img src='<s:url value='"+centralPath+"/images/menu/i_refresh.png' />' onClick='gotoSyncData("+idRow+")' style='cursor: pointer;' />";

		jQuery(row).find("td.syndata").html("<center>"+imgSyn+ "</center>");
		
    }
	
	//ไม่ได้ใช้
	function changeTable(){

		jQuery(".d_checkbox").hide();
		
		jQuery("#tableResult tbody tr").each(function(){
			
			var idRow = jQuery(this).find("td.checkbox").find("input[name='criteria.selectedIds']").val();
		
			var imgSyn = "<img src='<s:url value='"+centralPath+"/images/menu/i_refresh.png' />' onClick='gotoSyncData("+idRow+")' style='cursor: pointer;' />";
				
			jQuery(this).find("td.syndata").html("<center>"+imgSyn+ "</center>");
				
		})
		
	}
	
	function gotoSyncData(idRow){

		jQuery("#syn_id").val(idRow);

		jQuery.ajax({
			url : "<s:url value='/jsp/report/syncDataInuseManagerReport.action' />",
			dataType : 'json',
			async : true,
			data : jQuery('form').serialize(),
			type : "post",
			success : function(data) {
				
				if(data.messageAjax.messageType == "E"){
					
				} else{
					rowChild("<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>", colData, aOption);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//
			}
		});
	}
	

	
	/* Formatting function for row details - modify as you need */
	function createDataTableRowChild ( d ) {

		// `d` is the original data object for the row
		var data = d.data();
		var rowIndex = d.index();
		
		var divTbl = jQuery("#subTable").clone().removeClass("slider").attr("style", "");
		var table = divTbl.find("table").attr("id", "tableSubResult_"+rowIndex).css("width","80%");

		var tbody = table.find("tbody");
		var row = "";
		var no = 1;
		var wi = jQuery("#tableResult thead tr th:last").css("width");
		
		for (var i = 0; i < data.listDetail.length; i++) {
			
			if (i%2 == 0) {
				row += "<tr class='odd'>";
			} else {
				row += "<tr class='even'>";	
			}
			
			row += "<td class='order'>"+ no+"</td>";
			row += "<td class='center col-width-150px'>"+data.listDetail[i].startDateTime+"</td>";
			row += "<td class='center col-width-150px'>"+data.listDetail[i].endDateTime+"</td>";
			row += "<td class='right'>"+data.listDetail[i].totalDay+"</td>";
			row += "<td class='right'>"+data.listDetail[i].totalHour+"</td>";
			row += "<td class='right'>"+data.listDetail[i].totalMinute+"</td>";
			row += "<td class='' width='"+wi+"'>"+data.listDetail[i].workPlace+"</td>";
			row += "</tr>";
			
			no++;
		}		
		
		tbody.html(row);
		
		
		divTbl.attr("class", "slider").css("display", "block");
	    return divTbl;
		
	}
	
	
	// ##### [BUTTON FUNCTION] #####
	
	function printPage(){
		
		if(!validateFormInputAll()){ // ตรวจสอบ require field ของ form นั้นๆ
	        return false;
	    }
		document.getElementsByName('criteria.criteriaKey')[0].value = '';
		submitPageReport("<s:url value='/jsp/report/exportInuseManagerReport.action'/>")
	}
	
	function search(){
		if(!validateFormInputAll()){ // ตรวจสอบ require field ของ form นั้นๆ
	        return false;
	    }
		
		document.getElementsByName('criteria.criteriaKey')[0].value = '';
		searchAjax();
	}
	
	function cancel(){
		
		submitPage("<s:url value='/jsp/report/initInuseManagerReport.action'/>")
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
								<td class="LEFT" style="width: 10%; vertical-align: middle;"><s:text
										name="criteria" /></td>
							</tr>
						</table>
				</div>
				
				<br>
			
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
						<td class="LABEL" style="width: 10%"><s:text name='to.department' /><em>*</em></td>
						<td class="VALUE" colspan="3">
							<s:textfield id="criteria_departmentId" name="criteria.departmentId" code-of="department_selectitem" cssClass="requireInput" validName="%{getText('to.department')}" />
							<s:textfield id="criteria_departmentName" name="criteria.departmentName" text-of="department_selectitem" cssClass="requireInput" validName="%{getText('to.department')}" />
						</td>

						<td class="BORDER"></td>
					</tr>
					
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL" style="width: 10%"><s:text name='to.fullNameEmp' /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE" colspan="3">
							<span id="spanFullNameEmp">
								<s:textfield id="criteria_employeeId" name="criteria.employeeId" code-of="employee_autocomplete" cssClass="" validName="%{getText('to.fullNameEmp')}"/>
								<s:textfield id="criteria_employeeName" name="criteria.employeeName" text-of="employee_autocomplete" cssClass=""  validName="%{getText('to.fullNameEmp')}"/>
							</span>
						</td>

						<td class="BORDER"></td>
					</tr>
					
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL" style="width: 10%"><s:text name='to.dateFrom' /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE" >
						<s:textfield 
							id="criteria_startDate"
							name="criteria.startDate"
							cssClass="input_datepicker"
							cssStyle="width: 150px;"
							validName="" />
						</td>
			
						<td class="LABEL" style="width: 10%"><s:text name='to.dateTo' /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE">
						<s:textfield 
							id="criteria_endDate"
							name="criteria.endDate"
							cssClass="input_datepicker_from_to "
							cssStyle="width: 130px;"
							validName=""
							datepicker-id-from="criteria_startDate" datepicker-id-to="criteria_endDate"/> 
						</td>
						<td class="BORDER"></td>
					</tr>
					
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL" style="width: 10%"><s:text name='recordsPerPage' /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE">
							<div>
								<s:select 
									id="criteria_linePerPage" 
									cssClass="lpp-style"
									name="criteria.linePerPage" 
									list="LPP" 
								/>
							</div>
							
						</td>

						<td class="BORDER"></td>
					</tr>
				
				</table>
				
				
				<!------------------------------------- BUTTON ----------------------------------->			
				<table class="BUTTON">
					<tr>
						<td class="LEFT RIGHT_65"></td>
						<td class="RIGHT LEFT_35">

							<div id="divButtonPredefineApprove" class="ui-sitbutton-predefine"
								data-buttonType = "print|search|clear"
								data-auth = "<s:property value='ATH.search'/>|<s:property value='ATH.print'/>|true"
								data-func = "printPage()|search()|cancel()"
								data-style = "btn-small|btn-small|btn-small"
								data-container = "false">
							</div>
							
						</td>
					</tr>
				</table>
			
				<br>
				
				<s:include value="/jsp/template/hiddenSearchForDatatable.jsp" />
				
			</div>
		</div>
		
		<!--------------------------------------- divResult --------------------------------------->

		<div id="div_datatable" class="RESULT_SYSTEM ex_highlight_row" style="display: none;">
			<table class="display" id="tableResult">
				<thead>
					<tr>
						<th class="order col-width-30fix"><s:text name="no"></s:text></th>
						<th class="checkbox col-width-30fix"><s:text name="syn"/></th>
						<th class="checkbox col-width-30fix"><s:text name=""/></th>
						<th ><s:text name="to.department" /></th>
						<th><s:text name="to.fullNameEmp" /></th>
						<th><s:text name="to.timeoffset" /></th>
						<th><s:text name="to.timePendingHRM" /></th>
						<th><s:text name="to.dateTimeSyndataLasted" /></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="9" class="dataTables_empty">Loading data from server</td>
					</tr>
				</tbody>
			</table>
				
			<div id="subTable" style="display: none;">
				<table class="display dataTable no-footer" id="tableSubResult" aria-describedby="tableResult_info" role="grid" style="width: 65%;" align="right">
					<thead>
						<tr role="row">
							<th class="center sorting_disabled ui-state-default"><s:text name="no"/></th>
							<th class="center sorting_disabled ui-state-default"><s:text name="to.startDateTime"/></th>
							<th class="center sorting_disabled ui-state-default"><s:text name="to.endDateTime"/></th>
							<th class="center sorting_disabled ui-state-default"><s:text name="to.totalDay" /></th>
							<th class="center sorting_disabled ui-state-default"><s:text name="to.totalHour" /></th>
							<th class="center sorting_disabled ui-state-default"><s:text name="to.totalMinute" /></th>
							<th class="center sorting_disabled ui-state-default"><s:text name="to.workPlace" /></th>
						</tr>
					</thead>
					<tbody>
							
					</tbody>
				</table>
			</div>
				
		</div>
		
		<s:hidden id="syn_id" name="report.id" />
		<s:hidden name="page" id="page" />
		<s:token name="%{@org.apache.struts2.util.TokenHelper@generateGUID()}" />
		
	</s:form>
	
</body>
</html>