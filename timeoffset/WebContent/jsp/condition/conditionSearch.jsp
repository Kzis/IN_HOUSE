<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>
<html>
<head>
<script type="text/javascript">
	var centralPath = "<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>";
	var active = "<a href='javascript:void(0);' onclick='updateActive(\"Y\",\"<s:text name='50001'/>\");' class='action-link'><img src='<s:url value='"+centralPath+"/images/icon/i_open.png' />'> <s:text name='to.active' /></a>";
	var inactive = "&nbsp;<a href='javascript:void(0);' onclick='updateActive(\"N\",\"<s:text name='50002'/>\");' class='action-link'><img src='<s:url value='"+centralPath+"/images/icon/i_close.png' />'> <s:text name='to.inactive' /></a>";
	
	var colData = [
             { data: null, class: "order", orderable: false, defaultContent: ""},
             { data: null, class: "checkbox d_checkbox center", orderable: false, defaultContent: ""},
             { data: null, class: "checkbox d_edit", orderable: false, defaultContent: ""},
             { data: "projectAbbr", class: "",width:"225px"},
             { data: "projConDetail", class: "thaiName col-width-auto"  ,orderable: false},
             { data: "hourTot",class: "right" ,width:"110px",orderable: false},
             { data: "active.code", class: "center d_status", width:"125px", orderable: false}
    ];
	
	function sf(){		
		if(jQuery("[name='criteria.criteriaKey']").val() != ""){
            searchAjax();
        }
	}
	
	function searchPage(){
		//document.getElementsByName('criteria.criteriaKey')[0].value = '';
		searchAjax();
	}

	function searchAjax(){
		
		var aOption = {
				divResultId: "div_datatable",
				tableId: "tableResult",
				urlSearch: "<s:url value='/jsp/condition/searchProjectCondition.action' />",
				urlUpdateActive: "",
				urlEdit: {
					url: "<s:url value='/jsp/condition/gotoEditProjectCondition.action' />",
					authen: "<s:property value='ATH.edit' />"
				},
				pk: "projectCondition.id",
				footerHtml: active + inactive
		};
		
		dataTable("<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>", colData, aOption);
	}
	
	function clearFormCallBack(){
	
		var lpp = "<s:property value='criteria.linePerPage' />";
		jQuery("#criteria_linePerPage").val(lpp);
		jQuery("#criteria_linePerPage").selectmenu("refresh");
		
	}
	
	function clearPage(){
		submitPage("<s:url value='/jsp/condition/initProjectCondition.action' />")
	}
	
	function gotoAddPage(){
		submitPage("<s:url value='/jsp/condition/gotoAddProjectCondition.action' />")
	}
	
	//เมื่อกดแก้ไขสถานะ Active & Inactive
	function updateActive(active, msg){
		
		chk = submitAjaxStatus(active, msg);
		if(chk == false){
			return false;
		}
		
		updateAjax();
	}
	
	function updateAjax(){
		
		var aOption = {
				divResultId: "div_datatable",
				tableId: "tableResult",
				urlSearch: "<s:url value='/jsp/condition/changeActiveProjectCondition.action' />",
				urlUpdateActive: "",
				urlEdit: {
					url: "<s:url value='/jsp/condition/gotoEditProjectCondition.action' />",
					authen: "<s:property value='ATH.edit' />"
				},
				urlUpdateActive:"",
				pk: "projectCondition.id",
				footerHtml: active + inactive
		};
		
		dataTable("<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>", colData, aOption);
		
	}
	

</script>
</head>

<body>
	<s:form id="searchForm" name="searchForm" method="post" cssClass="margin-zero" onsubmit="return false;">
	
		<div id="divCriteria" class="CRITERIA CRITERIA_SYSTEM">
			<div class="RESULT_BOX BORDER_COLOR STYLE_CRITERIA_1600">
				<div class="RESULT_BOX_TITAL">
					<table class="TOTAL_RECORD">
						<tr>
							<td class="LEFT" style="width: 10%;"><s:text name="criteria"/></td>
							<td class="RIGHT">
								
								<div id="dibButtonPredefine" class="ui-sitbutton-predefine"
									data-buttonType = "gotoadd"
									data-auth = "<s:property value='ATH.add'/>"
									data-func = "gotoAddPage()"
									data-style = "btn-small"
									data-container = "false"
								>
								</div>
								
							</td>
						</tr>
					</table>
				</div>
				
				<br/>
				<table class="FORM">
					<tr style="display: none;">
						<td class="BORDER"></td>
						<td class="LABEL"></td>
						<td class="VALUE"></td>
						<td class="BORDER"></td>
					</tr>
					
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL" style="width: 20%"><s:text name='to.project' /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE" style="width: 30%">
							<s:select 					
								id="criteria_projectId"					
								name="criteria.projectId"
								list="listProject"
								headerKey=""
								headerValue="%{getText('all')}"
								listKey="key"
								listValue="value"
								cssClass="combox clearform"
								/>
						</td>
						<td class="BORDER"></td>
					</tr>
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL"><s:text name='activeStatus' /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE">
							<s:select 					
								id="criteria_activeStatus"					
								name="criteria.activeStatus"
								list="listActiveStatus"
								headerKey=""
								headerValue="%{getText('all')}"
								listKey="key"
								listValue="value"
								cssClass="combox clearform"
								/>
						</td>
						<td class="BORDER"></td>
					</tr>
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL"><s:text name='recordsPerPage' /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE">
							<div>
								<s:select 
									id="criteria_linePerPage" 
									cssClass="lpp-style clearform"
									name="criteria.linePerPage" 
									list="LPP" 
								/>
							</div>
						</td>
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
			<div id="div_datatable" class="RESULT RESULT_SYSTEM ex_highlight_row" style="display: none;">
				<table class="display" id="tableResult">
					<thead>
						<tr>
							<th class="order"><s:text name="no"></s:text></th>
							<th><input type="checkbox" name="chkall" onClick="checkboxToggleDataTable('criteria.selectedIds',this)" /></th>
							<th class="checkbox"><s:text name="edit"/></th>
							<th><s:text name="to.project" /></th>
							<th><s:text name="to.projectDetail" /></th>
							<th><s:text name="to.totalHourOffset" /></th>
							<th><s:text name="activeStatus" /></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="7" class="dataTables_empty">Loading data from server</td>
						</tr>
					</tbody>
				</table>
			</div>
		
		<s:hidden id="projectCondition_id" name="projectCondition.id" />
		
	</s:form>
</body>
</html>