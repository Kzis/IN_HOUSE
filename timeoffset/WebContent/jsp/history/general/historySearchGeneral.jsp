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

	function sf(){
		
// 		if(jQuery("[name='criteria.criteriaKey']").val() != ""){
//             searchAjax();
//         }
		
	}
	
	function searchAjax(){
		
		var aOption = {
				divResultId: "div_datatable",
				tableId: "tableResult",
				urlSearch: "<s:url value='/jsp/history/searchTimeOffsetHistoryGeneral.action' />",
				pk: "history.id",
				createdRowFunc: "manageRow"
		};
		
		var colData = [
		               { data: null, class: "order", orderable: false, defaultContent: ""},
	                   { data: "onsiteDateTimeFrom1", class: "center col-width-150px", orderable: false},
	                   { data: "onsiteDateTimeTo2", class: "center col-width-150px", orderable: false},
	                   { data: "siteServiceRemark", class: "thaiName col-width-auto", orderable: false},
	                   { data: "totalOnsiteDay", class: "right col-width-50px", orderable: false},
	                   { data: "totalOnsiteHour", class: "right col-width-75px", orderable: false},
	                   { data: "totalOnsiteMinute", class: "right col-width-75px", orderable: false},
	                   { data: "onsiteStatus", class: "center col-width-75px d_approve", orderable: false}
	                  ];
		
		dataTable(centralPath, colData, aOption);
		
	}
	
	function manageRow(row, data) {
		setPicApprove(row,data)
    }
	
	function setPicApprove(row,data){
		
		var iconApprove = "<img class='cursor' title='"+ data.onsiteStatusDesc + "' src='<s:url value='"+centralPath+"/images/menu/i_check.png'/>'>";
		var iconDisApprove = "<img class='cursor' title='"+ data.onsiteStatusDesc + "' src='<s:url value='"+centralPath+"/images/menu/i_delete.png'/>'>";
		var iconWaitApprove = "<img class='cursor' title='"+ data.onsiteStatusDesc + "' src='<s:url value='"+centralPath+"/images/menu/i_help.png'/>'>";
		
		if(data.onsiteStatus == "W"){
			jQuery('.d_approve',row).html(iconWaitApprove);
		}else if(data.onsiteStatus == "D"){
			jQuery('.d_approve',row).html(iconDisApprove);
		}else if(data.onsiteStatus == "A"){
			jQuery('.d_approve',row).html(iconApprove);
		}
	}

	//##### [BUTTON FUNCTION] #####
	
	function printPage(){
		if(!validateFormInputAll()){ // ตรวจสอบ require field ของ form นั้นๆ
	        return false;
	    }
		
		document.getElementsByName('criteria.criteriaKey')[0].value = '';
		submitPageReport("<s:url value='/jsp/history/exportTimeOffsetHistoryGeneral.action'/>")
	}
	
	function search(){
		if(!validateFormInputAll()){ // ตรวจสอบ require field ของ form นั้นๆ
	        return false;
	    }
		
		document.getElementsByName('criteria.criteriaKey')[0].value = '';
		searchAjax();
	}
	
	function cancel(){
		submitPage("<s:url value='/jsp/history/initTimeOffsetHistoryGeneral.action'/>")
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
								<td class="LEFT" style="width: 10%; vertical-align: middle;">
									<s:text name="criteria" />
								</td>
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
							<td class="LABEL" style="width: 10%"><s:text name='to.fullNameEmp' /><em>&nbsp;&nbsp;</em></td>
							<td class="VALUE" colspan="3">
								<s:textfield id="criteria_employeeName" name="criteria.employeeName" value="%{#session.USER_SESSION.fullName}" cssClass="readonly combox91" />
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
								cssStyle="width: 150px;"
								validName=""
								datepicker-id-from="criteria_startDate" datepicker-id-to="criteria_endDate"/> 
							</td>
							<td class="BORDER"></td>
						</tr>
						
						<tr>
							<td class="BORDER"></td>
							<td class="LABEL" style="width: 10%"><s:text name='to.approveStatusHRM' /><em>&nbsp;&nbsp;</em></td>
							<td class="VALUE">
								<s:select 					
									id="criteria_approveStatus"					
									name="criteria.approveStatus"
									list="listApprove"
									headerKey=""
									headerValue="%{getText('all')}"
									listKey="key"
									listValue="value"
									cssStyle="width: 140px;"
									/>
								
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
					
					<br/>
					
				</div>
			</div>
			
			<!--------------------------------------- divResult --------------------------------------->
			<div class="RESULT RESULT_SYSTEM">
				<div id="div_datatable" class="ex_highlight_row"
					style="display: none;">
					<table class="display" id="tableResult">
						<thead>
							<tr>
								<th class="order"><s:text name="no"></s:text></th>
								<th><s:text name="to.startDateTime" /></th>
								<th><s:text name="to.endDateTime" /></th>
								<th><s:text name="to.workPlace" /></th>
								<th><s:text name="to.totalDay" /></th>
								<th><s:text name="to.totalHour" /></th>
								<th><s:text name="to.totalMinute" /></th>
								<th><s:text name="approveStatus" /></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="9" class="dataTables_empty">Loading data from server</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			
			<s:include value="/jsp/template/hiddenSearchForDatatable.jsp" />
			<s:hidden name="criteria.criteriaKey" />
			<s:hidden name="page" id="page" />
		<s:token name="%{@org.apache.struts2.util.TokenHelper@generateGUID()}" />
		</s:form>


</body>
</html>