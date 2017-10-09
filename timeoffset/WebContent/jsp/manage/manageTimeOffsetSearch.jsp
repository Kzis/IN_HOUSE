<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>
<html>
<head>

<link href="<s:url value='/css/style-to.css' />" rel="stylesheet" type="text/css"/>

<script type="text/javascript">

	var centralPath = "<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>";
	
	var deleteIcon = "<div cid='btnFooterCustom'>"
		+ "<a href='javascript:void(0);' onclick='deleted(\"criteria.selectedIds\");' class='action-link'><img src='<s:url value='"+centralPath+"/images/icon/i_delete.png'/>'><s:text name="delete" /></a>";
		
	var toShow = "<span id='toShow' style='text-align:right; float:right; font-weight: bold;  margin-right:-5%; '><s:text name="to.totalCanUse" />"
		+ "</span></div>";
				
	var aOption = {
			divResultId: "div_datatable",
			tableId: "tableResult",
			urlSearch: "<s:url value='/jsp/manage/searchManageTimeOffset.action' />",
			urlEdit: {
				url: "<s:url value='/jsp/manage/gotoEditManageTimeOffset.action' />",
				authen: "<s:property value='ATH.edit' />"
			},
			urlView: {
				url: "<s:url value='/jsp/manage/gotoViewManageTimeOffset.action' />",
				authen: "<s:property value='ATH.view' />"
			},
			pk: "manage.id",
			createdRowFunc: "manageRow",
			createdTableFunc: "changeTO",
			footerHtml: deleteIcon + toShow
	};
	
	var colData = [
            { data: null, class: "order", orderable: false, defaultContent: ""},
            { data: null, class: "checkbox d_checkbox", orderable: false, defaultContent: ""},
            { data: null, class: "checkbox d_view", orderable: false, defaultContent: ""},
            { data: null, class: "checkbox d_edit", orderable: false, defaultContent: ""},
            { data: "projectAbbr", class: "col-width-175px"},
            { data: "projConDetail", class: "thaiName col-width-auto" ,orderable: false},
            { data: "day",class: "right col-width-90px",orderable: false},
            { data: "hour", class: "right col-width-90px", orderable: false},
            { data: "minute", class: "right col-width-90px", orderable: false},
            { data: "processStatus", class: "center d_approve  col-width-90px", orderable: false}
    ];	
		
	function sf() {
		if(jQuery("[name='criteria.criteriaKey']").val() != ""){
            searchAjax();
        }	
	}

	function searchAjax(){
		dataTable(centralPath, colData, aOption);
	}
	
	function changeTO(settings){
		
		console.log(settings);
		
		if(settings.aoData[0] != undefined){
			//เอาค่า เวลาชดเชยคงเหลือมา set
			var to = settings.aoData[0]._aData.timeOffset;
			jQuery("#toShow").html(jQuery("#toShow").text().replace('xxx',to));
			
			//เอาค่า style มายัด
			var color = settings.aoData[0]._aData.styleColor;
			if(color == "R"){
				jQuery("#toShow").css('color', 'red');
			}else if(color == "G"){
				jQuery("#toShow").css('color', 'green');
			}else if(color == "B"){
				jQuery("#toShow").css('color', 'blue');
			}
		}
	}
	
	function manageRow(row,data){
		var iconApprove = "<img class='cursor' title='"+ data.processStatusDesc + "' src='<s:url value='"+centralPath+"/images/menu/i_check.png'/>'>";
		var iconDisApprove = "<img class='cursor' title='"+ data.processStatusDesc + "' src='<s:url value='"+centralPath+"/images/menu/i_delete.png'/>'>";
		var iconWaitApprove = "<img class='cursor' title='"+ data.processStatusDesc + "' src='<s:url value='"+centralPath+"/images/menu/i_help.png'/>'>";
		
		if(data.processStatus == "W"){
			jQuery('.d_approve',row).html(iconWaitApprove);
		}else if(data.processStatus == "C"){
			jQuery('.d_approve',row).html(iconApprove);
			jQuery('.d_checkbox',row).html(""); // hidden checkbox
			jQuery('.d_edit',row).html(""); // hidden icon edit
		}
	}
	
	function searchPage(){
		//document.getElementsByName('criteria.criteriaKey')[0].value = '';
		searchAjax()
	}
	
	function clearPage(){
		submitPage("<s:url value='/jsp/manage/initManageTimeOffset.action'/>")
	}
	
	function clearFormCallBack(){	
		var lpp = "<s:property value='criteria.linePerPage' />";
		jQuery("#criteria_linePerPage").val(lpp);
		jQuery("#criteria_linePerPage").selectmenu("refresh");
	}
	
	function gotoAddPage(){
		submitPage("<s:url value='/jsp/manage/gotoAddManageTimeOffset.action'/>")
	}

	function deleted(id, msg) {
		
		//Check ว่ามีการติ๊ก checkbox หรือไม่ ?
		var chk = validateSelect('criteria.selectedIds');
		if (chk == false) {
			return false;
		}
		
		// ต้องการลบข้อมูลหรือไม่ ?
		if (confirm('<s:text name="50005" />') == false) {
			return false;
		}
		
		updateAjax();
	}
	
	function updateAjax(){

		aOption.urlSearch= "<s:url value='/jsp/manage/delTOManageTimeOffset.action' />";
		
		dataTable("<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>", colData, aOption);
		
		// set url กลับเป็น search กรณีกด page navigate แล้วให้เข้า action search
		aOption.urlSearch= "<s:url value='/jsp/manage/searchManageTimeOffset.action' />";
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
				
				<s:include value="/jsp/include/information.jsp" />
				
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
								cssClass="combox28 clearform"
								/>
							
						</td>
						<td class="BORDER"></td>
					</tr>
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL" style="width: 20%"><s:text name='to.processStatus' /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE" style="width: 30%">
							<s:select 					
								id="criteria_processStatus"					
								name="criteria.processStatus"
								list="listProcessStatus"
								headerKey=""
								headerValue="%{getText('all')}"
								listKey="key"
								listValue="value"
								cssClass="combox28 clearform"
								/>
							
						</td>
						<td class="BORDER"></td>
					</tr>
					
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL" style="width: 20%"><s:text name='recordsPerPage' /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE" style="width: 30%">
							<div>
								<s:select 
									id="criteria_linePerPage" 
									cssClass="lpp-style clearform"
									name="criteria.linePerPage" 
									list="LPP" 
									cssStyle="width:100px;"
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
					data-funcValidSearch=""
					data-funcSearch="searchPage"
					data-funcClearFormCallBack="clearFormCallBack">
				</div>
				
				<s:include value="/jsp/template/hiddenSearchForDatatable.jsp" />
				
				<br>
			
			</div>
		</div>
		
		<!--------------------------------------- divResult --------------------------------------->
			<div id="div_datatable" class="RESULT RESULT_SYSTEM ex_highlight_row" style="display: none;">
				<table class="display" id="tableResult">
					<thead>
						<tr>
							<th class="order"><s:text name="no"></s:text></th>
							<th><input type="checkbox" name="chkall" onClick="checkboxToggleDataTable('criteria.selectedIds',this)" /></th>
							<th class="checkbox"><s:text name="view"/></th>
							<th class="checkbox"><s:text name="edit"/></th>
							<th><s:text name="to.project" /></th>
							<th><s:text name="to.conditionTime" /></th>
							<th><s:text name="to.totalDayOffset" /></th>
							<th><s:text name="to.totalHourOffset" /></th>
							<th><s:text name="to.totalMinuteOffset" /></th>
							<th><s:text name="to.processStatus" /></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="9" class="dataTables_empty">Loading data from server</td>
						</tr>
					</tbody>
				</table>
			</div>
		
		<s:hidden id="manage_id" name="manage.id" />
		<s:hidden id="manage_timeOffsetId" name="manage.timeOffsetId" />
	</s:form>


</body>
</html>