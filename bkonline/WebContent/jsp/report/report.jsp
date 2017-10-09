<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<script type="text/javascript">
	function sf() {

	}
	jQuery(document).ready(function() {

	});
	function onCheckedChangemeetingRoomReport(checked, index){
		// Checkbox All
		if(index == -1){
			jQuery(".meetingRoomReport").prop('checked',checked);
			//jQuery(".meettingRoomReportChecked").val(checked);
		}else{
			// ถ้ามีอย่างน้อย 1 อันไม่เลือก checkbox all ต้องไม่เลือกด้วย
			if(jQuery(".meetingRoomReport:checkbox:not(:checked)").length == 0){
				jQuery(".meetingRoomReportAll").prop('checked',true);
			}else{
				jQuery(".meetingRoomReportAll").prop('checked',false);
			}
			//jQuery(".meettingRoomChecked").eq(index).val(checked);
		}
	}
	function gotocalendarpage(){
		//alert("message");
		submitPage("<s:url value='/jsp/meeting/initDataEventMeeting.action' />");
	}
	function print(){
		alert("ปริ้นล่ะจร้า :p");
		/* if (!validateAll()) {
            return false;
        }
		submitPage("<s:url value='/jsp/report/exportReport.action' />"); */
	}
	function backPage(){
		//alert("message");
		submitPage("<s:url value='/jsp/scheduler/initScheduler.action' />");
	}
	function clearForm(){
		//alert("message");
		submitPage("<s:url value='/jsp/report/initReport.action' />");
	}
	function checkPeriodSelected(check){
		jQuery(".Month").prop('hidden',true);
		jQuery(".Week").prop('hidden',true);
		jQuery(".Day").prop('hidden',true);
		jQuery(".Custom").prop('hidden',true);
		switch (check) {
		case "M":
			jQuery(".Month").removeAttr("hidden");
			break;
		case "W":
			//jQuery(".Month").removeAttr("hidden");
			jQuery(".Week").removeAttr("hidden");
			break;
		case "D":
			jQuery(".Day").removeAttr("hidden");
			break;
		case "C":
			jQuery(".Custom").removeAttr("hidden");
			break;
		}
	}
	function searchPage() {
        document.getElementsByName('criteria.criteriaKey')[0].value = '';
        searchAjax();
	}
	function searchAjax(){
	        var aOption = {
	            divResultId: "div_datatable",
	            tableId: "tableResult",
	            urlSearch: "<s:url value='/jsp/report/searchReport.action' />",
	            pk: "data.id", //input hidden in footer
	            createdRowFunc: "manageRow"
	        };
	        var colData = [
	                    { data: null, class: "order", orderable: false, defaultContent: ""},
	                    { data: "date", class: "col-width-50px", orderable: false},
	                    { data: "room", class: "col-width-125px", orderable: false},
	                    { data: "subject", class: "col-width-150px", orderable: false},
	                    { data: "startTime", class: "col-width-150px", orderable: false},
	                    { data: "endTime",class: "col-width-150px", orderable: false},
	                    { data: "userBooking", class: "col-width-150px", orderable: false}];
	             
	        dataTable("${application.CONTEXT_CENTRAL}", colData, aOption);
	}
	function manageRow(row, data) {
			/* if(data.active.code == "Y"){
				jQuery('.active', row).html(
					 "<img src='<s:url value='/images/icon/i_active.png' />'>" 
				);
			}
			else{
				jQuery('.active', row).html(
					 "<img src='<s:url value='/images/icon/i_inactive.png' />'>" 
				);
			} */
	}
</script>

</head>
<body>
	<s:form id="uiForm" name="uiForm" method="post" cssClass="margin-zero"
		onsubmit="return false;">
		<div id="divSearchForm" class="CRITERIA">
			<div id="divCriteria" class="RESULT_BOX BORDER_COLOR"
				style="display:;">
				<div class="RESULT_BOX_TITAL ">
					<table class="TOTAL_RECORD">
						<tr>
							<td class="LEFT" style="width: 10%;"><s:text name="Report" /></td>
						</tr>
					</table>
				</div>
				<table class="FORM">
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL"><s:text name="เลือกห้องประชุม" /><em>*</em></td>
						<td class="VALUE" colspan="3">
							<table>
								<tr>
									<td><input type="checkbox" id="chkAllMeetingRoomReport"
										checked="checked" class="meetingRoomReportAll"
										onclick="onCheckedChangemeetingRoomReport(this.checked, -1);" /><em>&nbsp;</em>
										<s:text name="all" /></td>
								</tr>
							</table>
							<div id="meetingRoomReportGroupId" class="requireGroup">
								<table>
									<tr>
										<s:iterator value="listRoom" status="status" var="result">
											<s:if test="#status.index == 0 ">
											</s:if>
											<s:else>
												<td><input type="checkbox" checked="checked"
													name="meetingRoomReport"
													id="meetingRoomReport-<s:property value="#status.index" />"
													class="requireInput meetingRoomReport"
													onclick="onCheckedChangemeetingRoomReport(this.checked, <s:property value="#status.index" />);" /><em>&nbsp;&nbsp;</em>
													<s:property value="#result.value" /> <em>&nbsp;&nbsp;</em>
												</td>
												<input type="hidden"
													name="listRoom[<s:property value="#status.index" />].check"
													value="<s:property value="listRoom[#status.index].check" />" />
											</s:else>
										</s:iterator>
									</tr>
								</table>
							</div>
						</td>
						<td class="BORDER"></td>
					</tr>
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL" style="vertical-align: top; padding-top: 7px"><s:text
								name="ประเภทรายงาน" /><em>*</em></td>
						<td class="VALUE" colspan="2"><input type="radio"
							checked="checked" name="criteria.periodType" class="requireInput"
							value="M" onclick="checkPeriodSelected(this.value);" /> <s:text
								name="รายเดือน" /><em>&nbsp;&nbsp;</em> <input type="radio"
							name="criteria.periodType" class="requireInput" value="W"
							onclick="checkPeriodSelected(this.value);" /> <s:text
								name="รายสัปดาห์" /><em>&nbsp;&nbsp;</em> <input type="radio"
							name="criteria.periodType" class="requireInput" value="D"
							onclick="checkPeriodSelected(this.value);" /> <s:text
								name="รายวัน" /><em>&nbsp;&nbsp;</em> <input type="radio"
							name="criteria.periodType" class="requireInput" value="C"
							onclick="checkPeriodSelected(this.value);" /> <s:text
								name="กำหนดเอง" /><em>&nbsp;&nbsp;</em></td>
						<td class="VALUE"></td>
						<td class="BORDER"></td>
					</tr>
				</table>
				<div class="Month">
					<table class="FORM">
						<tr>
							<td class="BORDER"></td>
							<td class="LABEL" style="width: 116px;"><s:text
									name="เริ่มต้น" /><em>*</em></td>
							<td class="VALUE"><s:select list="listMonth"
									id="criteria_startDate" name="criteria.startDate" listKey="key"
									listValue="value" style="width: 125px;" /> <s:select
									list="listYear" id="criteria_startYear"
									name="criteria.startYear" listKey="key" listValue="value"
									style="width: 80px;" /></td>
							<td class="LABEL"><s:text name="สิ้นสุด" /><em>*</em></td>
							<td class="VALUE"><s:select list="listMonth"
									id="criteria_endDate" name="criteria.endDate" listKey="key"
									listValue="value" style="width: 125px;" /> <s:select
									list="listYear" id="criteria_startYear"
									name="criteria.startYear" listKey="key" listValue="value"
									style="width: 80px;" /></td>
							<td class="BORDER"></td>
						</tr>
					</table>
				</div>
				<div class="Week" hidden=true>
					<table class="FORM">
						<tr>
							<td class="BORDER"></td>
							<td class="LABEL" style="width: 116px;"><s:text
									name="เดือนที่ต้องการ" /><em>*</em></td>
							<td class="VALUE"><s:select list="listMonth"
									id="criteria_startDate" name="criteria.startDate" listKey="key"
									listValue="value" listValue="value" style="width: 125px;"
									cssClass="selectMonth" /> <s:select list="listYear"
									id="criteria_endDate" name="criteria.endDate" listKey="key"
									listValue="value" style="width: 80px;" cssClass="selectMonth" />
							</td>
							<td class="LABEL"></td>
							<td class="VALUE"></td>
							<td class="BORDER"></td>
						</tr>
						<tr>
							<td class="BORDER"></td>
							<td class="LABEL" style="width: 116px;"><s:text
									name="เลือกสัปดาห์" /><em>*</em></td>
							<td class="VALUE" style="padding-top: 10px">
								<table>
									<tr>
										<td><input type="radio" checked="checked"
											name="criteria.week" class="requireInput" value="1" /> <s:text
												name="สัปดาห์ที่ 1 เดือน ปื (วันที่เริ่มต้น-วันที่สิ้นสุด)" />
										</td>
									</tr>
									<tr>
										<td><input type="radio" name="criteria.week"
											class="requireInput" value="2" /> <s:text
												name="สัปดาห์ที่ 2 เดือน ปื (วันที่เริ่มต้น-วันที่สิ้นสุด)" />
										</td>
									</tr>
									<tr>
										<td><input type="radio" name="criteria.week"
											class="requireInput" value="3" /> <s:text
												name="สัปดาห์ที่ 3 เดือน ปื (วันที่เริ่มต้น-วันที่สิ้นสุด)" />
										</td>
									</tr>
									<tr>
										<td><input type="radio" name="criteria.week"
											class="requireInput" value="4" /> <s:text
												name="สัปดาห์ที่ 4 เดือน ปื (วันที่เริ่มต้น-วันที่สิ้นสุด)" />
										</td>
									</tr>
									<tr>
										<td><input type="radio" name="criteria.week"
											class="requireInput" value="5" /> <s:text
												name="สัปดาห์ที่ 5 เดือน ปื (วันที่เริ่มต้น-วันที่สิ้นสุด)" />
										</td>
									</tr>
								</table>
							</td>
							<td class="LABEL"></td>
							<td class="VALUE"></td>
							<td class="BORDER"></td>
						</tr>
					</table>
				</div>
				<div class="Day" hidden=true>
					<table class="FORM">
						<tr>
							<td class="BORDER"></td>
							<td class="LABEL" style="width: 116px;"><s:text
									name="เลือกวันที่ต้องการ" /><em>*</em></td>
							<td class="VALUE"><s:textfield name="criteria.startDate"
									id="criteria_startDate"
									cssClass="requireInput input_datepicker"
									placeholder="DD/MM/YYYY" cssStyle="width: 90px;" /></td>
							<td class="LABEL"></td>
							<td class="VALUE"></td>
							<td class="BORDER"></td>
						</tr>
					</table>
				</div>
				<div class="Custom" hidden=true>
					<table class="FORM">
						<tr>
							<td class="BORDER"></td>
							<td class="LABEL" style="width: 116px;"><s:text
									name="เริ่มต้น" /><em>*</em></td>
							<td class="VALUE"><s:textfield name="criteria.startDate"
									id="criteria_startDate"
									cssClass="requireInput input_datepicker_from_to checkInputSearch"
									placeholder="DD/MM/YYYY"
									datepicker-id-from="criteria_startDate"
									datepicker-id-to="criteria_endDate" cssStyle="width: 90px;" />
							</td>
							<td class="LABEL"><s:text name="สิ้นสุด" /><em>*</em></td>
							<td class="VALUE"><s:textfield id="criteria_endDate"
									name="criteria.endDate" cssClass="requireInput" /></td>
							<td class="BORDER"></td>
						</tr>
					</table>
				</div>
				<table class="FORM">
					<tr>
						<td class="BORDER"></td>
						<td class="LABEL"></td>
						<td class="VALUE"></td>
						<td class="LABEL"></td>
						<td class="VALUE"></td>
						<td class="BORDER"></td>
					</tr>
				</table>
			</div>
		</div>
		<!--------------------------------------- divResult --------------------------------------->
		<div class="RESULT RESULT_SYSTEM">
			<div id="div_datatable" class="ex_highlight_row"
				style="display: none;">
				<table class="display" id="tableResult">
					<thead>
						<tr>
							<th><s:text name="ลำดับ" /></th>
							<th><s:text name="วันที่" /></th>
							<th><s:text name="ชื่อห้อง" /></th>
							<th><s:text name="รายละเอียด" /></th>
							<th><s:text name="เวลาเริ่มต้น" /></th>
							<th><s:text name="เวลาสิ้นสุด" /></th>
							<th><s:text name="ผู้จอง" /></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="10" class="dataTables_empty">Loading data from
								server</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!------------------------------------- Search Result ------------------------------------->
		<s:hidden name="data.id" />
		<s:hidden name="criteria.criteriaKey" />
		<!------------------------------------- BUTTON ----------------------------------->
		<s:include value="/jsp/template/button.jsp">
			<s:param name="buttonType" value="%{'search,enable'}" />
		</s:include>
		<!------------------------------------- BUTTON ----------------------------------->
	</s:form>

</body>
</html>