<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<script type="text/javascript">

	var hasProjConFlag;

	function initApproveEditDialog(){
		
		addClassDialog();
		
		var id = ids;
		var params = {"idx" : id};
		
	    jQuery.ajax({
	        type : "POST",
	        url : "<s:url value='/jsp/dialog/searchDetailByIdApproveDialog.action' />",
	        data : jQuery.param(params),
	        async : false,
	        success : function(data) {
	            if (data != null) {
	            	
	            	console.log(data);
            		
	            	//Remove dummy ที่เอามาดัก focus ที่ตัวกลางจับ ele แรก
	            	jQuery("#dummyFocus").hide();
	            	
	                dialogShowDetail(data); // เรียกใช้งาน function ใน dialog.js
	                setDataDialog(data);
          
	            } else {
	                // 'ไม่พบข้อมูลที่ต้องการ'
	            }
	        }
	    });
	    
	    
	}
	
	function addClassDialog(){
		
		jQuery("#approveDialog_startDate").input_dateformat({
	        dateformat :  "dd_sl_mm_sl_yyyy" ,// กำหนดรูปแบบ
			selectDateRange: { 
				dateTo: "approveDialog_endDate" // กำหนดวันที่สิ้นสุด
			} ,
			selectTimeRange: { 
				timeTo: "approveDialog_endTime",      // กำหนดเวลาสิ้นสุด
				timeFrom: "approveDialog_startTime" // กำหนดเวลาเริ่มต้น
			}
	    });
		
		jQuery("#approveDialog_endDate").input_dateformat({
	        dateformat :  "dd_sl_mm_sl_yyyy" , // กำหนดรูปแบบ
			selectDateRange: { 
				dateFrom: "approveDialog_startDate" // กำหนดวันที่เริ่มต้น
			} ,
		    selectTimeRange: { 
	            timeTo: "approveDialog_endTime",      // กำหนดเวลาสิ้นสุด
	            timeFrom: "approveDialog_startTime" // กำหนดเวลาเริ่มต้น
	        }
	    });
		
		jQuery("#approveDialog_startTime").input_timeformat({
            timeformat :  "hh_cl_mm" , // กำหนดรูปแบบ
			selectTimeRange: { 
				timeTo: "approveDialog_endTime"
			} ,
		    selectDateRange: {
	            dateFrom: "approveDialog_startDate",  // กำหนดวันที่เริ่มต้น
	            dateTo: "approveDialog_endDate"  // กำหนดวันที่สิ้นสุด
	        }
        });
		
		jQuery("#approveDialog_endTime").input_timeformat({
            timeformat :  "hh_cl_mm" , // กำหนดรูปแบบ
			selectTimeRange: { 
				 timeFrom: "approveDialog_startTime"
			} ,
	        selectDateRange: {
	            dateFrom: "approveDialog_startDate",  // กำหนดวันที่เริ่มต้น
	            dateTo: "approveDialog_endDate"  // กำหนดวันที่สิ้นสุด
	        }
        });
		
		jQuery("#approveDialog_workDate").input_dateformat({
	        dateformat :  "dd_sl_mm_sl_yyyy" , // กำหนดรูปแบบ
	    });
		
	}
	
	function setDataDialog(data){
		
		hasProjConFlag = data.objectPopup.projectConditionFlag;
		
		console.log(hasProjConFlag);
		
		//Check Flag เพื่อจัดการหน้าจอ และ Set ค่าให้ถูกต้อง
		if(checkFlag(data.objectPopup.projectConditionFlag)){
			jQuery("#approveDialog_projectName").val(data.objectPopup.projectName);
			jQuery("#approveDialog_projectCode").val(data.objectPopup.projectCode);
			
			jQuery("#approveDialog_projectConditionID").val(data.objectPopup.projectConditionID);
			jQuery("#approveDialog_projectConditionDETAIL").val(data.objectPopup.projectConditionDETAIL);

			jQuery("#approveDialog_workDetail").val(data.objectPopup.workDetail);
			
			jQuery("#approveDialog_startDate_dd_sl_mm_sl_yyyy").val(data.objectPopup.startDate);
			jQuery("#approveDialog_startTime_hh_cl_mm").val(data.objectPopup.startTime);
			
			jQuery("#approveDialog_endDate_dd_sl_mm_sl_yyyy").val(data.objectPopup.endDate);
			jQuery("#approveDialog_endTime_hh_cl_mm").val(data.objectPopup.endTime);
			
			jQuery("#approveDialog_workDate_dd_sl_mm_sl_yyyy").val(data.objectPopup.workDate);
			
			jQuery("#approveDialog_day").val(data.objectPopup.day);
			jQuery("#approveDialog_hour").val(data.objectPopup.hour);
			jQuery("#approveDialog_min").val(data.objectPopup.min);
			
			jQuery("#approveDialog_remark").val(data.objectPopup.remark);
			
			// Add class
			jQuery("#approveDialog_workDate_dd_sl_mm_sl_yyyy").addClass('requireInput');
			
			// remove class
			jQuery("#approveDialog_startDate_dd_sl_mm_sl_yyyy").removeClass('requireInput');
			jQuery("#approveDialog_startTime_hh_cl_mm").removeClass('requireInput');
			jQuery("#approveDialog_endDate_dd_sl_mm_sl_yyyy").removeClass('requireInput');
			jQuery("#approveDialog_endTime_hh_cl_mm").removeClass('requireInput');
			
		}else{
			jQuery("#approveDialog_projectName").val(data.objectPopup.projectName);
			jQuery("#approveDialog_projectCode").val(data.objectPopup.projectCode);
			
			jQuery("#approveDialog_projectConditionID").val(data.objectPopup.projectConditionID);
			jQuery("#approveDialog_projectConditionDETAIL").val(data.objectPopup.projectConditionDETAIL);

			jQuery("#approveDialog_workDetail").val(data.objectPopup.workDetail);
			
			jQuery("#approveDialog_startDate_dd_sl_mm_sl_yyyy").val(data.objectPopup.startDate);
			jQuery("#approveDialog_startTime_hh_cl_mm").val(data.objectPopup.startTime);
			
			jQuery("#approveDialog_endDate_dd_sl_mm_sl_yyyy").val(data.objectPopup.endDate);
			jQuery("#approveDialog_endTime_hh_cl_mm").val(data.objectPopup.endTime);
			
			jQuery("#approveDialog_day").val(data.objectPopup.day);
			jQuery("#approveDialog_hour").val(data.objectPopup.hour);
			jQuery("#approveDialog_min").val(data.objectPopup.min);
			
			jQuery("#approveDialog_remark").val(data.objectPopup.remark);
			
			// Add attr onchange
			jQuery("#approveDialog_startDate_dd_sl_mm_sl_yyyy").attr('onchange', 'calculateTimeOffset();');
			jQuery("#approveDialog_startTime_hh_cl_mm").attr('onchange', 'calculateTimeOffset();');
			jQuery("#approveDialog_endDate_dd_sl_mm_sl_yyyy").attr('onchange', 'calculateTimeOffset();');
			jQuery("#approveDialog_endTime_hh_cl_mm").attr('onchange', 'calculateTimeOffset();');
			
			// Add class
			jQuery("#approveDialog_startDate_dd_sl_mm_sl_yyyy").addClass('requireInput');
			jQuery("#approveDialog_startTime_hh_cl_mm").addClass('requireInput');
			jQuery("#approveDialog_endDate_dd_sl_mm_sl_yyyy").addClass('requireInput');
			jQuery("#approveDialog_endTime_hh_cl_mm").addClass('requireInput');
			
			// remove class
			jQuery("#approveDialog_workDate_dd_sl_mm_sl_yyyy").removeClass('requireInput'); 
		}
		
	}
	
	function checkFlag(projConFlag){
		
		if(projConFlag == "Y"){
			jQuery("#trStartDate").hide();
			jQuery("#trEndDate").hide();
			jQuery("#trWorkDate").show();
		
			return true;
		}else{
			jQuery("#trStartDate").show();
			jQuery("#trEndDate").show();
			jQuery("#trWorkDate").hide();
			return false;
		}
	}
	
	function calculateTimeOffset(){
		
		var txtStartDate = jQuery("#approveDialog_startDate_dd_sl_mm_sl_yyyy").val()
		var txtEndDate = jQuery("#approveDialog_endDate_dd_sl_mm_sl_yyyy").val();
		var txtStartTime = jQuery("#approveDialog_startTime_hh_cl_mm").val();
		var txtEndTime = jQuery("#approveDialog_endTime_hh_cl_mm").val();
		
		if(	(txtStartDate != "" && txtStartDate != 'undefined' && txtStartDate != "__/__/_____") 
				&& (txtEndDate != "" && txtEndDate != 'undefined' && txtEndDate != "__/__/_____") 
				&& (txtStartTime != "" && txtStartTime != 'undefined' && txtStartTime!= "__:__") 
				&& (txtEndTime != "" && txtEndTime != 'undefined' && txtEndTime !="__:__")){
			
			calculateTimeOffsetDayHourMinute();	
			
		}
	}
	
	function calculateTimeOffsetDayHourMinute(){
		
		var range = conpareTimeFormatWithDateFormatById('approveDialog_startTime', 'approveDialog_endTime', 'approveDialog_startDate', 'approveDialog_endDate');
		
		var day = (range.day)*(-1);
		var minute = day*24*60;
		
		var totMinute = minute + (range.minutes*-1);
		
		var fltHour = convertMinutesToHours(totMinute);

		convertHour24ToHour8(fltHour);	
	}
	
	/*
	Ex : Minutes = 733 min
		 mod = 13
		 divide = 12.21667
		 hour = 12 hr 13 min
	*/
	function convertMinutesToHours(minutes){
		
		var numHour = Math.trunc(minutes / 60); //hr
		var numMinutes = minutes % 60; //min
		
		if(numMinutes.toString().length == 1){
			var numMinutes = "0".concat(numMinutes.toString());
		}
		
		return numHour + "." + numMinutes;
	}
	
	/*
	Ex : Hour = 12.13 ==> 12 hr 13 min
		 ---
		 Day = 1
		 Hour = 4
		 Minutes = 13
		 So : 12 hr 13 min = 1 day 4 hour 13 min 
	*/
	function convertHour24ToHour8(hour){
		
		var numDay = Math.trunc(hour / 8); //day
		var numHour = hour % 8; // hour
		var numMinute;
		
		var numHourIndex = numHour.toString().indexOf(".");
		
		//numHour มีทศนิยมหรือไม่ ถ้ามี ก็ทำอันนี้
		if(!(numHourIndex == -1)){
			numMinute = numHour.toFixed(2).toString().substring(numHourIndex+1,numHour.length);
			
			if(numMinute < 10){
				numMinute = numMinute.substring(1,2);
			}
			
			numHour = numHour.toFixed(2).toString().substring(0,numHourIndex);
		}
		
		if(numMinute === undefined){
			numMinute = 0;
		}

		jQuery("#approveDialog_day").val(numDay);
		jQuery("#approveDialog_hour").val(numHour);
		jQuery("#approveDialog_min").val(numMinute);
		
// 		console.log("hour : " + hour);
// 		console.log("numDay : " + numDay);
// 		console.log("numHour : " + numHour);
// 		console.log("numMinute : " + numMinute);
// 		console.log("total : " + numDay + " day " + numHour + " hour " + numMinute + " minute ");
		
	}

	
	function edit(){

		// VALIDATE ALL INSIDE DIV_NAME
		if(!validateDivInputAll("divIdApproveEditDialog")){
			   return false;
		}
		
		// คุณต้องการบันทึกการแก้ไขข้อมูล
		if (confirm('<s:text name="50004" />') == false) {
			return false;
		}
		
		callBack();
	}
	
	function closeDialogEdit(){
		
		//Show dummy ที่เอามาดัก focus ที่ตัวกลางจับ ele แรก
    	jQuery("#dummyFocus").show();
		
    	// คุณต้องการออกจากหน้าจอแก้ไขข้อมูล ?
		if (confirm('<s:text name="50010" />') == false) {
			return false;
		}
		
		jQuery('#divIdApproveEditDialog').dialog('close');

	}
	
	function callBack(){
		
		var txtStartDateTime;
		var txtEndDateTime;
		
		if(hasProjConFlag == "Y"){
			jQuery("#proj_con_flag").val("Y");
			txtStartDateTime = jQuery("#approveDialog_workDate_dd_sl_mm_sl_yyyy").val() + " " + "00:00:00"
			txtEndDateTime = jQuery("#approveDialog_workDate_dd_sl_mm_sl_yyyy").val() + " " + "23:59:00"
		}else{
			jQuery("#proj_con_flag").val("N");
			txtStartDateTime = jQuery("#approveDialog_startDate_dd_sl_mm_sl_yyyy").val() +" "+ jQuery("#approveDialog_startTime_hh_cl_mm").val().concat(":00");
			txtEndDateTime = jQuery("#approveDialog_endDate_dd_sl_mm_sl_yyyy").val() +" "+ jQuery("#approveDialog_endTime_hh_cl_mm").val().concat(":00");
		}
		
// 		console.log("hasProjConFlag : " + hasProjConFlag);
// 		console.log("txtStartDateTime : " + txtStartDateTime);
// 		console.log("txtEndDateTime : " + txtEndDateTime);
		
		//Show dummy ที่เอามาดัก focus ที่ตัวกลางจับ ele แรก
    	jQuery("#dummyFocus").show();
		
		var day = jQuery("#approveDialog_day").val();
		var hour = jQuery("#approveDialog_hour").val();
		var minute = jQuery("#approveDialog_min").val();
		var remark = jQuery("#approveDialog_remark").val();

		callBackGoToEdit(day,hour,minute,txtStartDateTime,txtEndDateTime,remark);
		jQuery('#divIdApproveEditDialog').dialog('close');		
	}
	
</script>
<table class="FORM">
	<tr style="display: none;">
		<td class="BORDER"></td>
		<td class="LABEL" style="width: 10%"></td>
		<td class="VALUE" style="width: 40%"></td>
		<td class="VALUE" style="width: 25%"></td>
		<td class="BORDER"></td>
	</tr>
	<tr>
		<td class="BORDER"><s:textfield id="dummyFocus" cssStyle="width:1px; height:1px" /></td>
		<td class="LABEL" style="width: 10%"><s:text name='to.project' /><em>&nbsp;&nbsp;</em></td>
		<td class="VALUE" colspan="2">
			<s:textfield id="approveDialog_projectName" name="approveDialog.projectName" cssClass="readonly" cssStyle="width:415px;" />
			<s:hidden id="approveDialog_projectId" name="approveDialog.projectId"/>
		</td>
		<td class="BORDER"></td>
	</tr>
	<tr>
		<td class="BORDER"></td>
		<td class="LABEL" style="width: 10%"><s:text name='to.conditionTime' /><em>&nbsp;&nbsp;</em></td>
		<td class="VALUE" colspan="2">
			<s:textfield id="approveDialog_projectConditionDETAIL" name="approveDialog.projectConditionDETAIL" cssClass="readonly" cssStyle="width:415px;" />
			<s:hidden id="approveDialog_projectConditionID" name="approveDialog.projectConditionID" />
		</td>
		<td class="BORDER"></td>
	</tr>
	<tr>
		<td class="BORDER"></td>
		<td class="LABEL" style="vertical-align: top; width: 25%;"><s:text name='to.workDetail' /><em>&nbsp;&nbsp;</em></td>
		<td class="VALUE" colspan="2">
			<s:textarea id="approveDialog_workDetail" name="approveDialog.workDetail" cssStyle="resize:none;"  cssClass="readonly" cols="45" rows="2"/>
		</td>
		<td class="BORDER"></td>
	</tr>
	
	<tr id="trStartDate">
		<td class="BORDER"></td>
		<td class="LABEL" style="width: 25%"><s:text name='to.startDate' /><em>*</em></td>
		<td class="VALUE" style="width: 37%">
			<s:textfield 
				id="approveDialog_startDate" 
				name ="approveDialog.startDate" 
				cssClass="input_datepicker_from_to  datepicker" 
				validName="%{getText('to.startDate')}" 
			/>
			<em>&nbsp;&nbsp;</em>
			<s:text name='to.time' /><em>*</em><em>&nbsp;&nbsp;</em>
			<s:textfield id="approveDialog_startTime" name="approveDialog.startTime" 
					cssClass="timepicker" 
					validName="%{getText('to.startTime')}"  />
		</td>
		<td class="VALUE" style="width: 25%"><s:text name='to.unitMin' /></td>
		<td class="BORDER"></td>
	</tr>
	
	<tr id="trEndDate">
		<td class="BORDER"></td>
		<td class="LABEL" style="width: 25%"><s:text name='to.endDate' /><em>*</em></td>
		<td class="VALUE" style="width: 37%">
			<s:textfield 
				id="approveDialog_endDate" 
				name ="approveDialog.endDate" 
				cssClass="datepicker" 
				validName="%{getText('to.endDate')}" 

			/>
			<em>&nbsp;&nbsp;</em>
			<s:text name='to.time' /><em>*</em><em>&nbsp;&nbsp;</em>
			<s:textfield id="approveDialog_endTime" name="approveDialog.endTime" 
					cssClass="timepicker" 
					validName="%{getText('to.endTime')}"  />
		</td>
		<td class="VALUE"><s:text name='to.unitMin' /></td>
		<td class="BORDER"></td>
	</tr>
	
	<tr id="trWorkDate" style="display: none;">
			<td class="BORDER"></td>
			<td class="LABEL" style="width: 25%"><s:text name='to.workDay' /><em>*</em></td>
			<td class="VALUE" style="width: 35%">
				<s:textfield 
					id="approveDialog_workDate" 
					name ="approveDialog.workDate" 
					cssClass="datepicker %{#cssClass}" 
					validName="%{getText('to.workDay')}" 

				/>
			</td>
			<td class="VALUE" style="width: 45%"></td>
			<td class="BORDER"></td>
		</tr>
	
	<tr>
		<td class="BORDER"></td>
		<td class="LABEL" style="width: 25%"><s:text name='to.totalHourOffset' /><em>*</em></td>
		<td class="VALUE" colspan="2">
			<s:textfield id="approveDialog_day" name ="approveDialog.day" cssClass="readonly" cssStyle="text-align: right; width:50px;"/>
			<s:text name='to.day' />
			<em>&nbsp;&nbsp;</em><s:textfield id="approveDialog_hour" name ="approveDialog.hour" cssClass="readonly" cssStyle="text-align: right; width:50px;"/>
			<s:text name='to.hour' />
			<em>&nbsp;&nbsp;</em><s:textfield id="approveDialog_min" name="approveDialog.min" cssClass="readonly" cssStyle="text-align: right; width:50px;"/>
			<s:text name='to.min' />
		</td>
		<td class="BORDER"></td>
	</tr>
			
	<tr>
		<td class="BORDER"></td>
		<td class="LABEL" style="vertical-align: top; width: 25%;"><s:text name='to.remark' /><em>*</em></td>
		<td class="VALUE" colspan="2"><s:textarea id="approveDialog_remark" name="approveDialog.remark" cssStyle="resize:none;"  cssClass="requireFill" cols="45" rows="2" validName="%{getText('to.remark')}" /></td>
		<td class="BORDER"></td>
	</tr>
</table>

<table class="BUTTON">
	<tr>
		<td class="LEFT RIGHT_70"></td>
		<td class="RIGHT LEFT_30">
			<button id="btnOk"  class="btnOK"  type="button" onclick="edit();">
				<s:text name="ok"/>
				<s:hidden id="proj_con_flag" name="approve.projectConFlag" />
			</button>
			<button id="btnCancel"  class="btnCancel" type="button" onclick="closeDialogEdit();" >
				<s:text name="closePage"/>
			</button>
		</td>
	</tr>
</table>