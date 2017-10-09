<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<s:include value="/jsp/setting/roomsetting/include/addEditDeleteSetting-js-css.jsp"/>
</head>
<body>
	<s:form id="addEditDeleteForm" name="addEditDeleteForm" method="post"
		namespace="/jsp/setting" action="initRoomSetting" cssClass="margin-zero" onsubmit="return false;">
		<div class="DIV_ALL_ROOM">
			<table id="tableAllRoom" class="display tableAllRoom">
				<thead>
					<tr>
						<th class="order"><s:text name="no" /></th>
						<th class="col-width-auto"><s:text name="name" /></th>
						<th class="status"><s:text name="status" /></th>
					</tr>
				</thead>
				<tbody id="tableAllRoom_listAllRoom">
					<s:iterator value="listAllRoom" var="result" status="roomStatus">
						<tr>
							<td class="order"><s:property value="%{#roomStatus.index + 1}"/></td>
							<td class="col-width-auto name"><a class="LINK" href='javascript:searchRoomDetail(<s:property value="#result.id"/>)'><s:property value="#result.name"/></a></td>
							<td class="status"><s:property value="#result.active.desc"/></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		<div class="DIV_IN_FORM">
			<fieldset class="fieldsetStyle hide-border hide-background">
				<table class="FORM">
					<tr>
						<td class="LABEL LABEL_SETTING_FIRST"><s:text name="name" /><em>*</em></td>
						<td class="VALUE VALUE_SETTING"><s:textfield id="roomSetting_name" name="roomSetting.name" cssClass="txt180 requireInput" validName="%{getText('name')}"/></td>
						<td class="LABEL LABEL_SETTING"><s:text name="color" /><em>*</em></td>
						<td class="VALUE VALUE_SETTING">
							<s:select 
								id="roomSetting_color" 
								name="roomSetting.color"
								list="listColor" 
								listKey="key" 
								listValue="value"
								headerKey=""
								headerValue=""
								cssClass="combox180 requireInput"
								validName="%{getText('color')}"
							/>
						</td>
					</tr>
					<tr>
						<td class="LABEL LABEL_SETTING_FIRST"><s:text name="bk.autoApprove" /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE VALUE_SETTING">
							<s:select
								id="roomSetting_autotime" 
								name="roomSetting.autotime" 
								list="listAutotime" 
								listKey="key"
								listValue="value"
								cssClass="combox180" />
						</td>
						<td class="LABEL LABEL_SETTING"><s:text name="phone" /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE VALUE_SETTING"><s:textfield id="roomSetting_phone" name="roomSetting.phone" cssClass="txt180"/></td>
					</tr>
					<tr>
						<td class="LABEL LABEL_SETTING_FIRST"><s:text name="bk.attendees" /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE VALUE_SETTING"><s:textfield id="roomSetting_attendeesMax" name="roomSetting.attendeesMax" cssClass="txt180"/></td>
						<td class="LABEL LABEL_SETTING"><s:text name="priority" /><em>*</em></td>
						<td class="VALUE VALUE_SETTING">
							<s:select 
								id="roomSetting_priority" 
								name="roomSetting.priority" 
								list="listRoomPriority"
								listKey="key"
								listValue="value" 
								cssClass="combox180"/>
						</td>
					</tr>
					<tr style="display: none;">
						<td class="LABEL LABEL_SETTING_FIRST"><s:text name="equipment" /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE VALUE_SETTING" colspan="3">
							<s:iterator value="listEquipment" status="status" var="result">
								<div class="EQUIPMENT_BLOCK">
									<input type="checkbox" name="equipmentList" value='<s:property value="key" />' text='<s:property value="value"/>'/>
									<span class="margin-radio"><s:property value="value"/></span>
								</div>
							</s:iterator>
							<s:textfield id="roomSetting_equipmentListId" name="roomSetting.equipmentListId"/>
						</td>
					</tr>
					<tr>
						<td class="LABEL LABEL_SETTING_FIRST" style="vertical-align: top"><s:text name="detail" /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE VALUE_SETTING" colspan="3">
							<s:textarea 
								id="roomSetting_detail"
								name="roomSetting.detail" 
								cssStyle="width: 325px; height: 75px;"/>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldsetStyle">
				<legend><s:text name="bk.roomClosed" /></legend>
				<table class="FORM">
					<tr>
						<td class="LABEL LABEL_SETTING_FIRST"><s:text name="startDate" /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE VALUE_SETTING">
							<input
								type="text"
								id="roomSetting_startDate"
								name="roomSetting.startDate"
								class="input_datetime_from_to datepicker"
								time-id-from="roomSetting_startTime"
								time-id-to="roomSetting_endTime"
								datepicker-id-from="roomSetting_startDate"
								datepicker-id-to="roomSetting_endDate"
							/>
							<input
								type="text"
								id="roomSetting_startTime" 
								name="roomSetting.startTime"
								class="timepicker"
							/>
						</td>
						<td class="LABEL LABEL_SETTING"><s:text name="endDate" /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE VALUE_SETTING">
							<input
								type="text" 
								id="roomSetting_endDate" 
								class="datepicker"
							/>
							<input
								type="text"
								id="roomSetting_endTime" 
								class="timepicker"
							/>
						</td>
					</tr>
					<tr>
						<td class="LABEL LABEL_SETTING_FIRST"></td>
						<td class="VALUE VALUE_SETTING">
							<button id="btnAddTime" class="btnAdd" type="button" onclick="addTableTimeClose();">
								<s:text name="add" />
							</button>
						</td>
						<td class="LABEL LABEL_SETTING"></td>
						<td class="VALUE VALUE_SETTING"></td>
					</tr>
				</table>
				<!-- ================================= Start Table Template Section ========================================== -->
				<!-- div สำหรับแสดงผล  -->
				<div id="divTableTimeClose" class="RESULT RESULT_TIME_CLOSE" style="width: 670px; height:280px;" ></div>
				<!--ส่วนของการกำหนด parameters -->
				<s:set var="divresult" value='{"divTableTimeClose"}'/> 
				<s:set var="callbackFunction" value='{"receiveTableTimeCloseEditedInForm"}'/> 
				<s:set var="listTableData" value='%{"roomSetting.listTimeClosed"}'/> <!-- domain list เช่น userData.listPermission -->
				<s:set var="columnName" value='{getText("closedDate"), getText("startTime"), getText("endTime")}'/>
				<s:set var="columnData" value='{"startDate","startTime", "endTime"}'/>
				<s:set var="columnCSSClass" value='{"col-width-150px date","col-width-125px date","col-width-125px date"}'/>
				<s:set var="hiddenData" value='{"startDate","startTime","endTime"}'/> <!-- domain properties สำหรับดึงข้อมูลมาเก็บเป็น hidden -->
				<s:set var="settingTable" value='{"670:false:true"}'/>
				<!-- include table template -->
				<s:include value="/jsp/template/tableAddDeleteRowInForm.jsp"/>
				<!-- ================================= End Table Template Section ========================================== -->
			</fieldset>
			<fieldset class="fieldsetStyle hide-border hide-background">
				<table class="FORM">
					<tr>
						<td id="tdButtonSet" style="text-align: center;">
							<button id="btnSaveAdd" class="btnSaveAdd" type="button" onclick="saveAdd();"><s:text name="create" /></button>
							<button id="btnCancelAdd" class="btnCancelAdd" type="button" onclick="cancelAdd();"><s:text name="cancel" /></button>
							<button id="btnSaveEdit" class="btnSaveEdit" type="button" onclick="saveEdit();"><s:text name="save" /></button>
							<button id="btnCancelEdit" class="btnCancelEdit" type="button" onclick="cancelEdit();"><s:text name="cancel" /></button>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
		<s:token />
		<s:hidden name="roomSetting.id"/>
	</s:form>
</body>
</html>