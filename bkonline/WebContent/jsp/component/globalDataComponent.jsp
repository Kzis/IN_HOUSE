<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	int rowno = 0;
%>
<html>
<head>
	<script type="text/javascript">
		function sf(){
		    genCustomTableStyle("divResult", 1280, null, null);			
		}
	</script>
	<link type="text/css" rel="stylesheet" href='<s:url value="/jsp/component/include/component.css"/>'/>
</head>
<body>
	<s:form cssClass="margin-zero" name="template">
		<s:include value="/jsp/component/include/component.jsp"/>
		<div id="divResult">
	    	<div id="divResultBorderCustom">
	        	<div id="divTitleCustom">
	        		<table class="TOTAL_RECORD">
						<tbody>
							<tr>
								<td class="LEFT">การดึงข้อมูล Select Item จาก CON_GLOBAL_DATA</td>
								<td class="RIGHT"></td>
							</tr>
							<tr>
								<td class="LEFT"><img src="<s:url value='/jsp/component/include/GlobalData.png' />"/></td>
								<td class="RIGHT"></td>
							</tr>
						</tbody>
					</table>
	        	</div>
		        <div id="divHeaderCustom">
		            <table id="subHeaderCustom">
		                <tr>
							<th class="order">No.</th>
							<th class="GlobalType">Global Type</th>
							<th class="GlobalData">Global Data<br>ในรูปแบบ &lt;s:select&gt;</th>
							<th class="ActionClass">การเรียกใช้งานผ่าน Java Action Class</th>
		                </tr>
		            </table>
		        </div>
		        <div id="divDetailCustom">
		            <table id="tableId_divDetailCustom">
		            	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType"><s:property value="@com.cct.inhouse.enums.GlobalType@ACTIVE_STATUS.getValue()"/></td>
		               		<td class="GlobalData">
								<s:select 
									list="@com.cct.inhouse.central.rmi.service.SelectItemRMIProviders@getSelectItems(@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale(), @com.cct.inhouse.enums.GlobalType@ACTIVE_STATUS)"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value" 
									cssClass="GlobalData"
								>
								</s:select>
		               		</td>
		               		<td class="ActionClass">
		               			getModel().setListActiveStatus(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.getSelectItems(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getLocale()<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, GlobalType.ACTIVE_STATUS<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>)<br>
		               			);
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType"><s:property value="@com.cct.inhouse.enums.GlobalType@APPROVE_STATUS.getValue()"/></td>
		               		<td class="GlobalData">
								<s:select 
									list="@com.cct.inhouse.central.rmi.service.SelectItemRMIProviders@getSelectItems(@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale(), @com.cct.inhouse.enums.GlobalType@APPROVE_STATUS)"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value" 
									cssClass="GlobalData" 		
								>
								</s:select>
		               		</td>
		               		<td class="ActionClass">
		               			getModel().setListApproveStatus(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.getSelectItems(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getLocale()<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, GlobalType.APPROVE_STATUS<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>)<br>
		               			);
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType"><s:property value="@com.cct.inhouse.enums.GlobalType@REPORT_TYPE.getValue()"/></td>
		               		<td class="GlobalData">
								<s:select 
									list="@com.cct.inhouse.central.rmi.service.SelectItemRMIProviders@getSelectItems(@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale(), @com.cct.inhouse.enums.GlobalType@REPORT_TYPE)"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value" 
									cssClass="GlobalData" 		
								>
								</s:select>
		               		</td>
		               		<td class="ActionClass">
		               			getModel().setListReportType(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.getSelectItems(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getLocale()<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, GlobalType.REPORT_TYPE<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>)<br>
		               			);
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType"><s:property value="@com.cct.inhouse.enums.GlobalType@TASK_STATUS.getValue()"/></td>
		               		<td class="GlobalData">
								<s:select 
									list="@com.cct.inhouse.central.rmi.service.SelectItemRMIProviders@getSelectItems(@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale(), @com.cct.inhouse.enums.GlobalType@TASK_STATUS)"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value" 
									cssClass="GlobalData" 		
								>
								</s:select>
		               		</td>
		               		<td class="ActionClass">
		               			getModel().setListTaskStatus(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.getSelectItems(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getLocale()<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, GlobalType.TASK_STATUS<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>)<br>
		               			);
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType"><s:property value="@com.cct.inhouse.enums.GlobalType@ROOM_STATUS.getValue()"/></td>
		               		<td class="GlobalData">
								<s:select 
									list="@com.cct.inhouse.central.rmi.service.SelectItemRMIProviders@getSelectItems(@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale(), @com.cct.inhouse.enums.GlobalType@ROOM_STATUS)"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value" 
									cssClass="GlobalData" 		
								>
								</s:select>
		               		</td>
		               		<td class="ActionClass">
		               			getModel().setListRoomStatus(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.getSelectItems(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getLocale()<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, GlobalType.ROOM_STATUS<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>)<br>
		               			);
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType"><s:property value="@com.cct.inhouse.enums.GlobalType@EVENT_STATUS.getValue()"/></td>
		               		<td class="GlobalData">
								<s:select 
									list="@com.cct.inhouse.central.rmi.service.SelectItemRMIProviders@getSelectItems(@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale(), @com.cct.inhouse.enums.GlobalType@EVENT_STATUS)"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value" 
									cssClass="GlobalData" 		
								>
								</s:select>
		               		</td>
		               		<td class="ActionClass">
		               			getModel().setListEventStatus(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.getSelectItems(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getLocale()<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, GlobalType.EVENT_STATUS<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>)<br>
		               			);
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType"><s:property value="@com.cct.inhouse.enums.GlobalType@AUTO_TIME.getValue()"/></td>
		               		<td class="GlobalData">
								<s:select 
									list="@com.cct.inhouse.central.rmi.service.SelectItemRMIProviders@getSelectItems(@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale(), @com.cct.inhouse.enums.GlobalType@AUTO_TIME)"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value" 
									cssClass="GlobalData" 		
								>
								</s:select>
		               		</td>
		               		<td class="ActionClass">
		               			getModel().setListAutoTime(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.getSelectItems(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getLocale()<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, GlobalType.AUTO_TIME<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>)<br>
		               			);
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType"><s:property value="@com.cct.inhouse.enums.GlobalType@EQUIPMENT_ROOM.getValue()"/></td>
		               		<td class="GlobalData">
								<s:select 
									list="@com.cct.inhouse.central.rmi.service.SelectItemRMIProviders@getSelectItems(@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale(), @com.cct.inhouse.enums.GlobalType@EQUIPMENT_ROOM)"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value" 
									cssClass="GlobalData" 		
								>
								</s:select>
		               		</td>
		               		<td class="ActionClass">
		               			getModel().setListEquipmentRoom(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.getSelectItems(<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getLocale()<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, GlobalType.EQUIPMENT_ROOM<br>
		               			<div class="SPACE_BLOCK">&nbsp;</div>)<br>
		               			);
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
	
	<%--
		2. <s:property value="@com.cct.inhouse.enums.GlobalType@AUTO_TIME"/><br>
		3. <s:property value="@java.util.Locale@ENGLISH"/><br>
		3. <s:property value="@getLocale()"/><br>
		4. <s:property value="@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale()"/><br>
		5. <s:property value="@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getMapGlobalData()"/><br>
		6. <s:property value="@com.cct.inhouse.central.rmi.service.SelectItemRMIProviders@getSelectItems(@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale(), @com.cct.inhouse.enums.GlobalType@AUTO_TIME)"/><br>
	--%>
		
		<br>	
	</s:form>
</body>
</html>