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

	function sf(){

		
	}
	
	
	// ##### [BUTTON FUNCTION] #####
	
	function printPage(){	
		document.getElementsByName('criteria.criteriaKey')[0].value = '';
		submitPageReport("<s:url value='/jsp/report/exportInuseReport.action'/>")
	}
	
	function cancel(){
		document.getElementsByName('criteria.criteriaKey')[0].value = '';	
		submitPage("<s:url value='/jsp/report/initInuseReport.action'/>")
	}

	function checkReport(val){
// 		jQuery("label[for='radioGen1']").css('color','#110f0f');
// 		jQuery("label[for='radioGen2']").css('color','#110f0f');
	}
	
</script>
</head>
<body>

		<s:form id="reportForm" name="reportForm" method="post" cssClass="margin-zero" onsubmit="return false;">
			
			<div id="reportForm" class="CRITERIA CRITERIA_SYSTEM">
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
								cssClass="input_datepicker clearform"
								cssStyle="width: 150px;"
								validName="" />
								
							<td class="LABEL" style="width: 10%"><s:text name='to.dateTo' /><em>&nbsp;&nbsp;</em></td>
							<td class="VALUE">
								<s:textfield 
									id="criteria_endDate"
									name="criteria.endDate"
									cssClass="input_datepicker_from_to clearform"
									cssStyle="width: 150px;"
									validName=""
									datepicker-id-from="criteria_startDate" datepicker-id-to="criteria_endDate"/> 	
							</td>
	
							<td class="BORDER"></td>
						</tr>
						
						<tr>
							<td class="BORDER"></td>
							<td class="LABEL" style="vertical-align: top; padding-top:10px; width: 10%;"><s:text name='to.reportType' /><em>&nbsp;&nbsp;</em></td>
							<td class="VALUE" style="padding-top:10px;">
								<input type='radio' name="criteria.reportType" checked="checked" value="Excel" onclick="checkReport(this.checked)" /> 
								<span class='margin-radio'> <s:text name='Excel' /></span> 
								&nbsp; &nbsp; &nbsp;
								<input type='radio' name="criteria.reportType" value="PDF" onclick="checkReport(this.checked)" /> 
								<span class='margin-radio'> <s:text name='PDF' /></span> 
							</td>
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
			
			<s:hidden name="criteria.criteriaKey" />
		<s:token name="%{@org.apache.struts2.util.TokenHelper@generateGUID()}" />
		</s:form>

	
</body>
</html>