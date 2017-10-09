<%@page import="java.util.UUID"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%
String tokenName = UUID.randomUUID().toString();
%>

<html>
<head>
<script src="/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript">
		
	function sf(){
		
		jQuery("#startDate").input_dateformat({			
	        dateformat :  "dd_sl_mm_sl_yyyy" ,// กำหนดรูปแบบ
			selectDateRange: { 
				dateTo: "endDate" // กำหนดวันที่สิ้นสุด
			} ,
			selectTimeRange: { 
				timeTo: "endTime",      // กำหนดเวลาสิ้นสุด
				timeFrom: "startTime" // กำหนดเวลาเริ่มต้น
			}
	    });
	 
	    jQuery("#endDate").input_dateformat({	    	
	        dateformat :  "dd_sl_mm_sl_yyyy" , // กำหนดรูปแบบ
			selectDateRange: { 
				dateFrom: "startDate" // กำหนดวันที่เริ่มต้น
			} ,
		    selectTimeRange: { 
	            timeTo: "endTime",      // กำหนดเวลาสิ้นสุด
	            timeFrom: "startTime" // กำหนดเวลาเริ่มต้น
	        }
	    });
	    
	    jQuery("#startTime").input_timeformat({
            timeformat :  "hh_cl_mm" , // กำหนดรูปแบบ
			selectTimeRange: { 
				timeTo: "endTime"
			} ,
		    selectDateRange: {
	            dateFrom: "startDate",  // กำหนดวันที่เริ่มต้น
	            dateTo: "endDate"  // กำหนดวันที่สิ้นสุด
	        }
        });
     
        jQuery("#endTime").input_timeformat({
            timeformat :  "hh_cl_mm" , // กำหนดรูปแบบ
			selectTimeRange: { 
				 timeFrom: "startTime"
			} ,
	        selectDateRange: {
	            dateFrom: "startDate",  // กำหนดวันที่เริ่มต้น
	            dateTo: "endDate"  // กำหนดวันที่สิ้นสุด
	        }
        });
        
        jQuery("#workDate").input_dateformat({
	        dateformat :  "dd_sl_mm_sl_yyyy" , // กำหนดรูปแบบ
	    });
        

        // Add class
		jQuery("#startDate_dd_sl_mm_sl_yyyy").addClass('requireFill');
		jQuery("#endDate_dd_sl_mm_sl_yyyy").addClass('requireFill');
		jQuery("#startTime_hh_cl_mm").addClass('requireFill');
		jQuery("#endTime_hh_cl_mm").addClass('requireFill');
        
        checkPage();
           
	}
	
	//##### [BUTTON FUNCTION] #####
	//Page : Add
	function saveAdd(){
		
		//Validate List
		//Check ว่าใน TableInForm มี List ตัวแรกหรือยัง ถ้ามีคือมีการ Add เวลามาแล้ว
		if(jQuery("input[name='manage.listNewProject[0].id']").val() == undefined){
			alert("ข้อมูลไม่เพียงพอ");
			return false;
		}
		
		// คุณต้องการบันทึกการเพิ่มข้อมูล ?
		if (confirm('<s:text name="50003" />') == false) {
			return false;
		}
		
		submitPage("<s:url value='/jsp/manage/addManageTimeOffset.action'/>")
		
		clearValueInForm();
	}
	
	function cancelAdd(){
		
		//คุณต้องการออกจากหน้าจอเพิ่มข้อมูล ?
		if (confirm('<s:text name="50009" />') == false) {
			return false;
		}
		
		if(jQuery("[name='criteria.criteriaKey']").val() != ""){
			submitPage("<s:url value='/jsp/manage/cancelManageTimeOffset.action'/>")
        }else{
        	submitPage("<s:url value='/jsp/manage/initManageTimeOffset.action'/>")
        }
	}
	
	//Page : Edit
	function saveEdit(){
		
		//Set Remark Object to each list
		jQuery(".order").each(function(i){
			
			// ไม่ยุ่งกับ Header
			if(i>0){
				//Set remark to list
				jQuery("[name='manage.listNewProject["+(i-1)+"].remark']").val(jQuery("#manage_remark").val());
			}
			
		});
		
		// Remove class : requireInput ของรายละเอียดเวลาชดเชย
		jQuery("#projectId").removeClass("requireFill");
		jQuery("#conditionId").removeClass("requireFill");
		
		jQuery("#startDate").removeClass("requireFill");
		jQuery("#startDate_dd_sl_mm_sl_yyyy").removeClass("requireFill");
		
		jQuery("#startTime").removeClass("requireFill");
		jQuery("#startTime_hh_cl_mm").removeClass("requireFill");
		
		jQuery("#endDate").removeClass("requireFill");
		jQuery("#endDate_dd_sl_mm_sl_yyyy").removeClass("requireFill");
		
		jQuery("#endTime").removeClass("requireFill");
		jQuery("#endTime_hh_cl_mm").removeClass("requireFill");
		
		
		jQuery("#day").removeClass("requireFill");
		jQuery("#hour").removeClass("requireFill");
		jQuery("#minute").removeClass("requireFill");
		jQuery("#detailWork").removeClass("requireFill");
		jQuery("#workDate").removeClass("requireFill");
		
		jQuery("#manage_remark").addClass("requireFill");
		
		// VALIDATE ALL
		if (!validateFormInputAll()) {
             return false;
        }
		
		//คุณต้องการบันทึกการแก้ไขข้อมูล ?
		if (confirm('<s:text name="50004" />') == false) {
			return false;
		}
		
		submitPage("<s:url value='/jsp/manage/editManageTimeOffset.action'/>")
		
	}
	
	function cancelEdit(){
		
		//คุณต้องการออกจากหน้าจอแก้ไขข้อมูล ?
		if (confirm('<s:text name="50010" />') == false) {
			return false;
		}
		
		submitForm();
	}
	
	function deleteEdit(){

		var nameCheckBox = jQuery('div#' + 'divTableAdd' + ' input[name=\'cnkColumnId\']:checked')
	
		//ตรวจสอบการเลือกและต้องการ row ที่ถูกเลือก
		var status = validateSelectOne(nameCheckBox);
		
		//ถ้าเป็น False คือไม่มีการเลือก checkbox
		if (status == false) {
			return false;
		}
		
		//คุณต้องการลบข้อมูลหรือไม่ ?
		if (confirm(validateMessage.CODE_50005) == false) {
			return false;
		}

		//Loop check value ถ้ามีการ check จะทำการเปลี่ยน Flag deletedByEditPage เป็น Y
		jQuery(".checkbox").each(function(i){

			if(jQuery("#" + "checkboxGen" + (i+1)).is(':checked')){
				jQuery("[name='manage.listNewProject["+((i+1)-2)+"].deletedByEditPage']").val("Y");
			}
			
		});
		
		//กรณีติ๊ก Checkbox แรกมา
		if(jQuery("#cnkColumnId").is(':checked')){
			jQuery("[name='manage.listNewProject[0].deletedByEditPage']").val("Y");
		}
		
		//[1] hidden selected row
		status.hide();
				
	}
	
	//Page : View	
	function closePage(){
		if (confirm('<s:text name="50017" />') == false) {
			return false;
		}	
		submitForm();
	}
	
	function submitForm() {
		if(jQuery("[name='criteria.criteriaKey']").val() != ""){
			submitPage("<s:url value='/jsp/manage/cancelManageTimeOffset.action'/>")
        }else{
        	submitPage("<s:url value='/jsp/manage/initManageTimeOffset.action'/>")
        }
	}
	
	//Validate JS
	function checkPage() {

		if('<s:property value="page.getPage()"/>' == "add"){
			
			// แก้เรื่อง footer โดนบัง
			jQuery("#divTableAdd").css("height", "290");
			jQuery("#DIV_RESULT_BOX").css("height","289");
			jQuery(".RESULT_BOX_TITAL").hide();
			jQuery(".RESULT_BOX_TITAL.BACKGROUND_COLOR").show();
			jQuery(".TOTAL_RECORD").show();
			
			// Add attr onchange	
			jQuery("#startDate_dd_sl_mm_sl_yyyy").attr('onchange', 'manageDefaultValueStartDate();');
			jQuery("#endDate_dd_sl_mm_sl_yyyy").attr('onchange', 'manageDefaultValueEndDate();');
			jQuery("#startTime_hh_cl_mm").attr('onchange', 'calculateTimeOffset();');
			jQuery("#endTime_hh_cl_mm").attr('onchange', 'calculateTimeOffset();');
			
		}else if('<s:property value="page.getPage()"/>' == "edit"){
			// แก้เรื่อง footer โดนบัง
			jQuery("#divTableAdd").css("height", "310");
			jQuery("#DIV_RESULT_BOX").css("height","310");
			jQuery(".RESULT_BOX_TITAL").hide();
			jQuery(".RESULT_BOX_TITAL.BACKGROUND_COLOR").show();
			jQuery(".TOTAL_RECORD").show();
			
			// Add attr onchange	
			jQuery("#startDate_dd_sl_mm_sl_yyyy").attr('onchange', 'manageDefaultValueStartDate();');
			jQuery("#endDate_dd_sl_mm_sl_yyyy").attr('onchange', 'manageDefaultValueEndDate();');
			jQuery("#startTime_hh_cl_mm").attr('onchange', 'calculateTimeOffset();');
			jQuery("#endTime_hh_cl_mm").attr('onchange', 'calculateTimeOffset();');
		}else{
			
			 jQuery(".TOTAL_RECORD").hide();
			 jQuery("#DIV_RESULT_BOX").css("height","299");
			 
		}
	}
	
	function validateCurrentDate(startDate , endDate){
		
		var today = new Date();
		var dateNow = today.getDate();
		var monthNow = today.getMonth()+1;
		var yearNow = today.getFullYear()+543;
		
		if(dateNow.toString().length == 1){
			dateNow = ("0").concat(dateNow);
		}
		
		if(monthNow.toString().length == 1){
			monthNow = ("0").concat(monthNow);
		}
		
		var todayValidate = yearNow.toString().concat(monthNow.toString()).concat(dateNow.toString());
		
// 		console.log("TODAY : " + today);
// 		console.log("DATE NOW : " + dateNow);
// 		console.log("MONTH NOW: " + monthNow);
// 		console.log("YEAR NOW: " + yearNow);
// 		console.log("TODAY VALIDATE: " + todayValidate);
// 		console.log("######################");
		
		if(startDate != "" && startDate != 'undefined' && startDate != "__/__/_____"){
			
			var startDateArr = startDate.split("/");
			var startDateValidate = startDateArr[2].concat(startDateArr[1]).concat(startDateArr[0]);

			if(parseInt(startDateValidate) > parseInt(todayValidate)){
				alert('<s:text name="to.startdateValidate" />');
				jQuery("#startDate").input_dateformat('dateValue',"");
				jQuery("#endDate").input_dateformat('dateValue',"");
				return true;
			}
			
		}
		
		if(endDate != "" && endDate != 'undefined' && endDate != "__/__/_____"){
			
			var endDateArr = endDate.split("/");
			var endDateValidate = endDateArr[2].concat(endDateArr[1]).concat(endDateArr[0]);
			
			if(parseInt(endDateValidate) > parseInt(todayValidate)){
				alert('<s:text name="to.enddateValidate" />');
				jQuery("#startDate").input_dateformat('dateValue',"");
				jQuery("#endDate").input_dateformat('dateValue',"");
				return true;
			}
			
		}
		
		return false;
		
	}
	
	function manageDefaultValueStartDate(){
		
// 		jQuery("#endDate").val(jQuery("#startDate_dd_sl_mm_sl_yyyy").val());
// 		jQuery("#endDate_dd_sl_mm_sl_yyyy").val(jQuery("#startDate_dd_sl_mm_sl_yyyy").val());
		
		var startDate = jQuery("#startDate").input_dateformat('dateValue');

		jQuery("#endDate").input_dateformat('dateValue', startDate.dateForDB);
		
		calculateTimeOffset()
	}
	
	function manageDefaultValueEndDate(){		
		calculateTimeOffset()
	}
	
	function calculateTimeOffset(){
		
		var txtStartDate = jQuery("#startDate_dd_sl_mm_sl_yyyy").val()
		var txtEndDate = jQuery("#endDate_dd_sl_mm_sl_yyyy").val();
		var txtStartTime = jQuery("#startTime_hh_cl_mm").val();
		var txtEndTime = jQuery("#endTime_hh_cl_mm").val();
		
// 		console.log("txtStartDate : " + txtStartDate);
// 		console.log("txtEndDate : " + txtEndDate);
// 		console.log("txtStartTime : " + txtStartTime);
// 		console.log("txtEndTime : " + txtEndTime);
// 		console.log("################");
				
		//Check ไม่ให้เกินวันที่ปัจจุบัน
		if( (validateCurrentDate(txtStartDate,txtEndDate)) ){
			return false;
		}
				
		//check ว่ามีค่าครบทุกตัวหรือไม่
		if(	(txtStartDate != "" && txtStartDate != 'undefined' && txtStartDate != "__/__/_____") 
				&& (txtEndDate != "" && txtEndDate != 'undefined' && txtEndDate != "__/__/_____") 
				&& (txtStartTime != "" && txtStartTime != 'undefined' && txtStartTime!= "__:__") 
				&& (txtEndTime != "" && txtEndTime != 'undefined' && txtEndTime !="__:__")){
			
			calculateTimeOffsetDayHourMinute();	
			
		}
	}
	
	function calculateTimeOffsetDayHourMinute(){
		
		var range = conpareTimeFormatWithDateFormatById('startTime', 'endTime', 'startDate', 'endDate');
		
		var day = (range.day)*(-1);
		var minute = day*24*60;
		
		var totMinute = minute + (range.minutes*-1);
		
		var fltHour = convertMinutesToHours(totMinute);
		
// 		console.log("calculateTimeOffsetDayHourMinute");
// 		console.log(range);
// 		console.log("day : " + day);
// 		console.log("minute : " + minute);
// 		console.log("totMinute : " + totMinute);
// 		console.log("fltHour : " + fltHour);
// 		console.log("######################");
		
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
	
// 		console.log("convertMinutesToHours");
// 		console.log("numHour : " + numHour);
// 		console.log("numMinutes : " + numMinutes);
// 		console.log("numMinutes length : " + numMinutes.toString().length);
// 		console.log("######################");
		
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
		
// 		console.log("convertHour24ToHour8");
			 
		var numDay = Math.trunc(hour / 8); //day
		var numHour = hour % 8; // hour
		var numMinute;
		
// 		console.log("numHour : " + numHour);
		
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

		jQuery("#day").val(numDay);
		jQuery("#hour").val(numHour);
		jQuery("#minute").val(numMinute);
		
// 		console.log("hour : " + hour);
// 		console.log("numDay : " + numDay);
// 		console.log("numHour : " + numHour);
// 		console.log("numHourIndex : " + numHourIndex);
// 		console.log("numMinute : " + numMinute);
// 		console.log("total : " + numDay + " day " + numHour + " hour " + numMinute + " minute ");
// 		console.log("######################");
	}

	
	function checkDateTimeOverlap(){
		
		// ส่วนของ Data ที่จะ Add เข้ามาใหม่
		
		var startDate = jQuery("#startDate").input_dateformat('intValue');
		var startTime = jQuery("#startTime").input_timeformat('timeValue').toString().replace(":","");
		
		var endDate = jQuery("#endDate").input_dateformat('intValue');
		var endTime = jQuery("#endTime").input_timeformat('timeValue').toString().replace(":","");
		
		if(startDate == 0 || endDate == 0 || startTime == "" || endTime == ""){
			return false;
		}
		
		var startDateTime = startDate + startTime;
		var endDateTime = endDate + endTime;
		
		var dateTime = (parseInt(endDateTime) - parseInt(startDateTime));
			
		// ส่วนของ Data Add ไปแล้วอยู่ใน Table
		var listLength = jQuery("#tableId_divTableAdd tbody tr:visible").length;
		
		if(listLength > 0){
			
			var startDateInList;
			var startTimeInList;
			var endDateInList;
			var endTimeInList;
			var startDateTimeInList;
			var endDateTimeInList;
			var dateTimeInList;
			var listName = "listNewProject";
			
			var compareB1A1;
			var compaerB1A2;
			var compareB2A1;
			var compareB2A2;
			var compareStart;
			var compareEnd;
			
			for(var index = 0 ; index<listLength ; index++){
				
				//Check ในกรณีที่ list approve or disapprove ไปแล้ว
				if(jQuery("input[name='"+ listName + "["+index+"].startDate']").val() != undefined){
					startDateInList = convertDate(jQuery("input[name='"+ listName + "["+index+"].startDate']").val());
					endDateInList = convertDate(jQuery("input[name='"+ listName + "["+index+"].endDate']").val());
					
					startTimeInList = jQuery("input[name='"+ listName + "["+index+"].startTime']").val().toString().replace(":","");				
					endTimeInList = jQuery("input[name='"+ listName + "["+index+"].endTime']").val().toString().replace(":","");
					
					startDateTimeInList = startDateInList + startTimeInList;
					endDateTimeInList = endDateInList + endTimeInList;
					
					dateTimeInList = (parseInt(endDateTimeInList) - parseInt(startDateTimeInList));
					
					compareB1A1 = (startDateTime <= startDateTimeInList) ; //(B1 <= A1)
					compaerB1A2 = (startDateTime >= endDateTimeInList); //(B1 >= A2)
					compareB2A1 = (endDateTime <= startDateTimeInList); //(B2 <= A1)
					compareB2A2 = (endDateTime >= endDateTimeInList); //(B2 >= A2)
					
					compareStart = compareB1A1 || compaerB1A2; // (B1 <= A1) || (B1 >= A2)
					compareEnd = compareB2A1 || compareB2A2; // (B2 <= A1) || (B2 >= A2)
							
					var DateRangesOverlap = max(startDateTime, startDateTimeInList) < min(endDateTime, endDateTimeInList)
									
					// ( (B1 <= A1) || (B1 >= A2) ) && ( (B2 <= A1) || (B2 >= A2) )
					if( (compareB1A1 || compaerB1A2) && (compareB2A1 || compareB2A2) ){
							
						if(DateRangesOverlap){
							alert("Data Overlap");
							return false;
						}
						
					}
					else{
						alert("Data Overlap");
						return false;
					}
				}
				
			}
			
			return true;
			
		}else{
			//Case add ครั้งแรก
			return true;
		}
	
	}
	
	function convertDate(data){
		
		var day = data.toString().substring(0,2);
		var month = data.toString().substring(3,5);
		var year = data.toString().substring(6,10);
		
		var newDate = year+month+day;
		
		return newDate;
		
	}
	
	function max(StartA,StartB){
		return (StartA >= StartB? StartA: StartB);
	}
	
	function min(EndA,EndB){
		return (EndA <= EndB? EndA: EndB);
	}
	
	function clearValueInForm(){
		
		jQuery("#projectId").val("");
		jQuery("#projectId").selectmenu("refresh");
		
		jQuery("#conditionId").val("");
		jQuery("#conditionId").selectmenu("refresh");

		jQuery("#startDate").input_dateformat('dateValue',"");
		jQuery("#endDate").input_dateformat('dateValue',"");
		jQuery("#workDate").input_dateformat('dateValue',"");
		
		jQuery("#startTime").input_timeformat("timeValue","");
		jQuery("#endTime").input_timeformat("timeValue","");
		
		jQuery("#day").val("");
		jQuery("#hour").val("");
		jQuery("#minute").val("");
		jQuery("#detailWork").val("");
	}

	
</script>
</head>
<body>
	<s:form id="addForm" name="addForm" method="post" cssClass="margin-zero" onsubmit="return false;">
		<br/>
		<s:include value="/jsp/include/information.jsp" />
		

		<s:include value="/jsp//manage/include/addEditViewTimeOffsetDetail.jsp" />
		
		<!------------------------------------- BUTTON ----------------------------------->
		
		<s:if test="page.getPage() == 'add' ">

			<div id="divButtonPredefineAdd" class="ui-sitbutton-predefine"
				data-buttonType = "save|cancel"
				data-auth = "<s:property value='ATH.add'/>|true"
				data-func = "saveAdd()|cancelAdd()"
				data-style = "btn-small|btn-small"
				data-container = "true">
			</div>
											
		</s:if>
		
		<s:if test="page.getPage() == 'edit' ">
		
			<table class="FORM">
				<tr>
					<td class="BORDER"></td>
					<td class="LABEL" style="width: 30%; vertical-align: top"><s:text name='to.remark' /><em>*</em></td>
					<td class="VALUE" style="width: 40%">
						<s:textarea id="manage_remark" 
							name="manage.remark"
							cssStyle="resize:none;" 
							cssClass="requireInput" 
							cols="50" 
							rows="2" 
							validName="%{getText('to.remark')}" 
						/>
					</td>
					<td class="VALUE" style="width: 5%"></td>
					<td class="BORDER"></td>
				</tr>
			</table>
			
			<div id="divButtonPredefineEdit" class="ui-sitbutton-predefine"
				data-buttonType = "edit|cancel"
				data-auth = "<s:property value='ATH.edit'/>|true"
				data-func = "saveEdit()|cancelEdit()"
				data-style = "btn-small|btn-small"
				data-container = "true">
			</div>
						
		</s:if>
		
		<s:if test="page.getPage() == 'view' ">
		
			<s:include value="/jsp/manage/include/approveDetail.jsp" />
			
			<table class="BUTTON">
				<tr>
					<td class="LEFT RIGHT_70"></td>
					<td class="RIGHT LEFT_30">
					
						<div id="divButtonPredefineCancel" class="ui-sitbutton-predefine"
							data-buttonType = "cancel"
							data-auth = "true"
							data-func = "closePage()"
							data-style = "btn-small"
							data-container = "false">
						</div>
					
					</td>
				</tr>
			</table>
		</s:if>
		
		
		
		<!-- Hidden value -->
		<s:hidden id="page" name="page" />
		<s:hidden name="criteria.criteriaKey" />
		<s:hidden name="P_CODE" />
		<s:hidden name="F_CODE" />
		<s:hidden id="manage_id" name="manage.id" />
		<s:hidden id="manage_to_id" name="manage.timeOffsetId" />
		<s:hidden id="proj_con_flag" name="manage.projectConditionFlag" />
		<s:hidden id = "config_workStart" />
				
		<s:token name="%{@org.apache.struts2.util.TokenHelper@generateGUID()}" />
		
	</s:form>
</body>
</html>