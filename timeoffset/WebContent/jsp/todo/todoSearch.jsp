<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>
<html>
<head>
<link href="<s:url value='/css/style-to.css' />" rel="stylesheet" type="text/css"/>
<script type="text/javascript">

	var centralPath = "<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>";

	var footerTag1 = "<a href='javascript:void(0);' onclick='updateApproveStatus(\"A\",\"<s:text name='50001'/>\");' class='action-link'><img src='<s:url value='"+centralPath+"/images/icon/i_active_status.png'/>'><s:text name="approve" /></a>";
	var footerTag2 = "&nbsp;<a href='javascript:void(0);' onclick='updateApproveStatus(\"D\",\"<s:text name='50002'/>\");' class='action-link'><img src='<s:url value='"+centralPath+"/images/icon/i_cancel_status.png'/>'><s:text name="disapprove" /></a>";
	
	var colData = [
	               { data: null, class: "order col-width-40px", orderable: false, defaultContent: ""},
                   { data: null, class: "checkbox d_edit col-width-40px", orderable: false, defaultContent: ""},
                   { data: "projectABBR", class: "col-width-150px"},
                   { data: "projectConditionDETAIL", class: "thaiName col-width-225px"},
                   { data: "fullName", class: "thaiName col-width-auto"},
                   { data: "day", class: "right col-width-100px", orderable: false},
                   { data: "hour", class: "right col-width-100px", orderable: false},
                   { data: "minute", class: "right col-width-100px", orderable: false},
                  ];
	
	function sf(){
		searchAjax();
	}
	
	function searchAjax(){
		
		var aOption = {
				divResultId: "div_datatable",
				tableId: "tableResult",
				urlSearch: "<s:url value='/jsp/todo/searchTimeOffsetTodo.action' />",
				urlEdit: {
					url: "<s:url value='/jsp/todo/gotoApproveTimeOffsetTodo.action' />",
					authen: "<s:property value='ATH.approve' />"
				},
				urlView: {
					url: "",
					authen: ""
				},
				pk: "todo.id",
// 				createdTableFunc: "manageTable",
				height:"450px"
// 				footerHtml: footerTag1+footerTag2
				
		};
		
		dataTable(centralPath, colData, aOption);
		
	}
	
	function manageRow(row, data) {
		
		var url = "/timeoffset/jsp/todo/gotoApproveTimeOffsetTodo.action";
		var id = data.timeOffsetID;
		var pk = "todo.id";
		
		var submit = 'submitAction("url","pk","id")';
		submit = submit.replace("id",id);
		submit = submit.replace("url",url);
		submit = submit.replace("pk",pk);
		
		var iconApprove = "<img class='cursor' title='"+ '<s:text name="edit" />' + "' src='<s:url value='"+centralPath+"/images/icon/i_edit.png'/>'"+"' onclick='" + submit + "'>";;
		
		jQuery(".d_edit",row).html(iconApprove);		
	
	}
	
	function manageTable(obj){
		jQuery(".d_checkbox").hide();
	}

	function updateApproveStatus(flag,msg) {
		
		chk = submitAjaxStatus(flag, msg);
		if(chk == false){
			return false;
		}
		
		updateAjax();
	}
	
	function updateAjax(){
		
		var aOption = {
				divResultId: "div_datatable",
				tableId: "tableResult",
				urlSearch: "<s:url value='/jsp/todo/changeActiveTimeOffsetTodo.action' />",
				urlEdit: {
					url: "<s:url value='/jsp/todo/gotoApproveTimeOffsetTodo.action' />",
					authen: "<s:property value='ATH.edit' />"
				},
				urlView: {
					url: "",
					authen: ""
				},
				pk: "todo.id",
				footerHtml: footerTag1+footerTag2
				
		};
		
		dataTable(centralPath, colData, aOption);	
	}		
	
</script>

</head>
<body>

	<s:form id="searchForm" name="searchForm" method="post" cssClass="margin-zero" onsubmit="return false;">
		
		<div id="divCriteria" class="CRITERIA CRITERIA_SYSTEM">
			<div class="RESULT_BOX BORDER_COLOR STYLE_CRITERIA_1600" style="display:none">
				
			
				<s:hidden name="criteria.linePerPage" value="50"/>
				<s:include value="/jsp/template/hiddenSearchForDatatable.jsp" />
			
			</div>
		</div>

		<div id="div_datatable" class="RESULT RESULT_SYSTEM ex_highlight_row" style="display: none;">
				<table class="display" id="tableResult">
					<thead>
						<tr>
							<th class="order"><s:text name="no"></s:text></th>
							<th class="checkbox"><s:text name="approve"/></th>
							<th><s:text name="to.project" /></th>
							<th><s:text name="to.projectDetail" /></th>
							<th><s:text name="to.fullNameEmp" /></th>
							<th><s:text name="to.totalDayOffsetWait"/></th>
							<th><s:text name="to.totalHourOffsetWait" /></th>
							<th><s:text name="to.totalMinuteOffsetWait" /></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="8" class="dataTables_empty">Loading data from server</td>
						</tr>
					</tbody>
				</table>
		</div>
		
		<s:hidden id="todo_id" name="todo.id" />
		<s:hidden id="todo_id2" name="todo.id2" />
		<s:token />
	</s:form>

</body>
</html>