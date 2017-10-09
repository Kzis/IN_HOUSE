<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<s:include value="/jsp/booking/roombooking/include/addRoomBooking-js-css.jsp"/>
	</head>
	<body>
		<s:form id="addRoomBookingForm" name="addRoomBookingForm" method="post"
			namespace="/jsp/booking" action="addRoomBooking" cssClass="margin-zero"
			onsubmit="return false;">
			<table class="FORM">
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL"><s:text name="startDate" /><em>*</em></td>
					<td class="VALUE">
						<s:textfield 
							id="roomBooking_startDate"
							name="roomBooking.startDate"
							cssClass="input_datetime_from_to requireInput"
							validName="%{getText('startDate')}"
							time-id-from="roomBooking_startTime"
							time-id-to="roomBooking_endTime"
							datepicker-id-from="roomBooking_startDate"
							datepicker-id-to="roomBooking_endDate"
						/>
						<s:textfield 
							id="roomBooking_startTime" 
							name="roomBooking.startTime"
							cssClass="requireInput"
							validName="%{getText('startTime')}"
						/>
					</td>
					<td class="LABEL"><s:text name="endDate" /><em>*</em></td>
					<td class="VALUE">
						<s:textfield 
							id="roomBooking_endDate" 
							name="roomBooking.endDate" 
							cssClass="requireInput"
							validName="%{getText('endDate')}"
						/>
						<s:textfield 
							id="roomBooking_endTime" 
							name="roomBooking.endTime"
							cssClass="requireInput"
							validName="%{getText('endTime')}"
						/>
					</td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL"><s:text name="bk.subjectMeeting" /><em>*</em></td>
					<td class="VALUE">
						<s:textfield 
							id="roomBooking_subject" 
							name="roomBooking.subject"
							cssClass="requireInput txt"
							validName="%{getText('bk.subjectMeeting')}"
						/>
					</td>
					<td class="LABEL"></td>
					<td class="VALUE"></td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL">
						<s:text name="bk.bookingBy" /><em>*</em>
						<s:hidden id="roomBooking_userBookId" name="roomBooking.userBookId" />
						<s:hidden id="roomBooking_userBookName" name="roomBooking.userBookName" />
						<s:hidden id="roomBooking_userBookDepartmentId" name="roomBooking.userBookDepartmentId" />
						<s:hidden id="roomBooking_userBookPositionId" name="roomBooking.userBookPositionId" />
					</td>
					<td class="VALUE">
						<input type="text" id="allUserCode" name="allUserCode" code-of="allUser_autocomplete" class="requireInput autocomplete" style="width: 140px;" validName='<s:text name="bk.bookingBy" />' />
                    	<input type="text" id="allUserText" name="allUserText" text-of="allUser_autocomplete" />
					</td>
					<td class="LABEL"><s:text name="phoneno" /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
						<s:textfield 
							id="roomBooking_userBookPhone" 
							name="roomBooking.userBookPhone" 
							cssClass="txt160" 
						/></td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="vertical-align: top;"><s:text name="bk.notificationUse" /><em>*</em></td>
					<td class="VALUE" colspan="3">
						<div id="notificationUseGroupId" class="requireGroup" style="width: 550px; float: left;">
							<s:iterator value="listNotificationUse" status="status" var="result">
								<div style="width: 530px; float: left;">
									<div style="width: 110px; height: 45px;  float: left;">
										<input type="checkbox" name="notificationUse" class="requireInput" validName='<s:text name="bk.notificationUse" />' value='<s:property value="key" />'/>
										<span class="margin-radio"><s:property value="value"/></span>
									</div>
									<div style="width: 400px; height: 35px; float: left;">
										<s:if test='%{key == "E"}'>
											<em>&nbsp;&nbsp;</em><s:textfield id="roomBooking_userBookEmail" name="roomBooking.userBookEmail" readonly="true" cssClass="txt" cssStyle="width: 350px;"/>
										</s:if>
										<s:elseif test='%{key == "L"}'>
											<em>&nbsp;&nbsp;</em><s:textfield id="roomBooking_userBookLineId" name="roomBooking.userBookLineId" readonly="true" cssClass="txt" cssStyle="width: 350px;"/>
										</s:elseif>	
									</div>
								</div>
							</s:iterator>
						</div>
						<s:hidden 
							id="roomBooking_notificationEmail" 
							name="roomBooking.notificationEmail"
						/>
						<s:hidden 
							id="roomBooking_notificationLine" 
							name="roomBooking.notificationLine"
						/>
					</td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="vertical-align: top;"><s:text name="bk.selectRoom" /><em>*</em></td>
					<td class="VALUE" colspan="3">
						<div id="emptyRoomGroupId" class="requireGroup" style="width: 550px; float: left;"></div>
						<div style="width: 100%; float: left;">
							<button id="btnCheckEmptyRoom" class="btnCheckEmptyRoom" type="button" onclick="searchListEmptyRoomButton();"><s:text name="check" /></button>
						</div>
						<s:hidden id="roomBooking_roomId" name="roomBooking.roomId"/>
						<s:hidden id="roomBooking_roomName" name="roomBooking.roomName"/>
					</td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL"><s:text name="bk.totalAttendees" /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE">
						<s:textfield 
							id="roomBooking_attendees" 
							name="roomBooking.attendees"
							cssClass="txt"
						/>
					</td>
					<td class="LABEL"></td>
					<td class="VALUE"></td>
					<td class="BORDER"></td>
				</tr>
				<tr style="display: none;">
					<td class="BORDER"></td>
					<td class="LABEL"><s:text name="moreEquipment" /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE" colspan="3">
						<s:iterator value="listEquipment" status="status" var="result">
							<div style="width: 150px; float: left; padding-top: 3px;">
								<input type="checkbox" name="equipmentList" value='<s:property value="key" />' text='<s:property value="value"/>'/>
								<span class="margin-radio"><s:property value="value"/></span>
							</div>
						</s:iterator>
						<s:hidden id="roomBooking_equipmentListId" name="roomBooking.equipmentListId"/>
						<s:hidden id="roomBooking_equipmentListName" name="roomBooking.equipmentListName"/>
					</td>
					<td class="BORDER"></td>
				</tr>
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="vertical-align: top;"><s:text name="moreDetail" /><em>&nbsp;&nbsp;</em></td>
					<td class="VALUE" colspan="3">
						<s:textarea 
							id="roomBooking_detail" 
							name="roomBooking.detail"
							cssClass="textarea"
							cssStyle="width: 446px; height: 73px;"
						/>
					</td>
					<td class="BORDER"></td>
				</tr>
			</table>
			<div id="divButton<s:property value='page.Page'/>" 
					class="ui-sitbutton" 
					data-buttonType="<s:property value='page.Page'/>" 
					<s:if test="page.getPage() == 'add'">
				   		data-auth="<s:property value='ATH.add'/>" 
				   	</s:if>
				   	<s:elseif test="page.getPage() == 'edit'">
				   		data-auth="<s:property value='ATH.edit'/>"
				   	</s:elseif>
			>
			</div>
		   	<s:token/>
		   	</s:form>
	</body>
</html>