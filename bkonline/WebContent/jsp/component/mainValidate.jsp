<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="util.string.StringDelimeter"%>
<%@ page import="com.cct.inhouse.common.InhouseAction"%>
<%@ page import="util.web.SessionUtil"%>
<%
	int rowno = 0;

	String[] messages = (String[]) SessionUtil.get(InhouseAction.MESSAGE);
	SessionUtil.remove(InhouseAction.MESSAGE);
			
	String subject = StringDelimeter.EMPTY.getValue();
	if (messages != null && messages.length > 1 && messages[1] != null) {
		subject = messages[1];
	}
			
	String body = StringDelimeter.EMPTY.getValue();
	if (messages != null && messages.length > 2 && messages[2] != null) {
		body = messages[2];
	}
%>
<html>
<head>
	<script type="text/javascript">
		function sf(){
			initFieldErrorMessage();
		}
		
		function saveAdd() {
			if (confirm('<s:text name="50003" />') == false) {
		    	return false;
		    }
			
			submitPage("<s:url value='/jsp/component/addValidate.action' />");
		}
		
		function initFieldErrorMessage() {
			var myTitle = "<%=subject%>"
			var myMessage = "<%=body%>";
			if (myMessage != "") {
				showNotifyMessageServerValidate(myTitle, myMessage);
			}
		}
	</script>
	<link type="text/css" rel="stylesheet" href='<s:url value="/jsp/component/include/component.css"/>'/>
</head>
<body>
	<s:form cssClass="margin-zero" name="template">
		<s:include value="/jsp/component/include/component.jsp"/>
		<s:fielderror cssStyle="display: ;"/>
		<div id="divResult">
	    	<div id="divResultBorderCustom">
	        	<div id="divTitleCustom">
	        		<table class="TOTAL_RECORD">
						<tbody>
							<tr>
								<td class="LEFT">&nbsp;</td>
								<td class="RIGHT">&nbsp;</td>
							</tr>
						</tbody>
					</table>
	        	</div>
		        <div id="divHeaderCustom">
		            <table id="subHeaderCustom">
		                <tr>
							<th class="order">No.</th>
							<th class="ActionClass">Component</th>
							<th class="Remark">Remark</th>
		                </tr>
		            </table>
		        </div>
		        <div id="divDetailCustom">
		            <table id="tableId_divDetailCustom">
		            	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			requireInput:<s:textfield name="requireInput" value="requireInput"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			intValue:<s:textfield name="intValue"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			longValue:<s:textfield name="longValue"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			dateValue:<s:textfield name="dateValue"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			timeValue:<s:textfield name="timeValue"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			stringLength:<s:textfield name="stringLength"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			dayRangeLimit:<s:textfield name="startDate" value="01/12/2017"/> - <s:textfield name="endDate" value="31/12/2017"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			emailValue:<s:textfield name="emailValue"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			dateBeforeLimit Day:<s:textfield name="dateBeforeLimitD"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			dateBeforeLimit Month:<s:textfield name="dateBeforeLimitM"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			dateBeforeLimit Year:<s:textfield name="dateBeforeLimitY"/>
		               		</td>
		               		<td class="Remark">
		               			<input type="button" onclick="saveAdd();" value="Validate"/>
		               		</td>
		               	</tr>
		            </table>
		        </div>
		        <div id="divFooterCustom">
					<table class="TOTAL_RECORD">
						<tbody>
							<tr>
								<td class="LEFT">&nbsp;</td>
								<td class="RIGHT">&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</div>
	   		</div>
		</div>
		<br>
		<s:token/>	
	</s:form>
</body>
</html>