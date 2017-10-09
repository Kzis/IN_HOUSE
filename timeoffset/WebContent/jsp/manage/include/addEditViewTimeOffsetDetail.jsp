<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>

<link href="<s:url value='/css/style-to.css' />" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
	var centralPath = "<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>";
	var _jData;
	var _projConFlag;
	
	$( document ).ready(function() {
				
		//Check หน้าจอเพื่อจัดการหน้าจอ
		checkPageDetail();
	});
	
	function checkPageDetail(){
		if('<s:property value="page.getPage()"/>' == "view"){
			
			//Hide class ในตาราง
			jQuery(".checkbox").hide();
			jQuery(".edit").hide();
			jQuery("#btnDel").hide();
			jQuery(".TOTAL_RECORD").hide();

			// ManageRow ที่เขียนขึ้นมาเองเพื่อจัดการ Table
			manageRowPageDetail();

		}else if('<s:property value="page.getPage()"/>' == "edit"){
			
			//Show class ที่ hide จากข้างบนในกรณีที่กดสลับไปมา
			jQuery(".checkbox").show();
			jQuery(".edit").show();
			jQuery("#btnDel").show();
			jQuery(".TOTAL_RECORD").show();
		     
		   // ManageRow ที่เขียนขึ้นมาเองเพื่อจัดการ Table
		    manageRowPageDetail();
		    
		   // Set class เพื่อความสวยงาม
		    jQuery("#conditionId-button").css("width","290px");
		    jQuery("#conditionId").selectmenu("refresh");	
		    
			jQuery("#projectId").prop('disabled', true);
			jQuery("#projectId-button").prop('disabled', true);
			
			jQuery("#conditionId").prop('disabled', true);
			jQuery("#conditionId-button").prop('disabled', true);
			
		    jQuery("#projectId").selectmenu("refresh");	

		}else{
			
			jQuery("#projectId").selectmenu({
				change: function( event, ui ) {
					loadCondition();
				}
	   		});
			
			//Show class ที่ hide จากข้างบนในกรณีที่กดสลับไปมา
			jQuery(".checkbox").show();
			jQuery(".edit").show();
			jQuery("#btnDel").show();
			jQuery(".TOTAL_RECORD").show();

		}
	}
	
	function manageRowPageDetail(){
		
		//Change Icon
		jQuery(".d_approve").each(function(i){
			
			//ถ้าเป็น Header ไม่ต้องจัดการ
			if(i > 0){
				
				//get ค่า approveStatus ในแต่ละ row
				var txtApproveStatus = jQuery(this).text();
				
				//set ค่า title จาก list ที่ hidden ไว้
				var title = jQuery("[name='manage.listNewProject["+(i-1)+"].approveStatusDesc']").val();
				
				//set title ที่ได้ ลงใน  html icon ที่จะไปแทน approveStatus
				var iconApprove = "<img class='cursor' title='"+ title + "' src='<s:url value='"+centralPath+"/images/menu/i_check.png'/>'>";
				var iconDisApprove = "<img class='cursor' title='"+ title + "' src='<s:url value='"+centralPath+"/images/menu/i_delete.png'/>'>";
				var iconWaitApprove = "<img class='cursor' title='"+ title + "' src='<s:url value='"+centralPath+"/images/menu/i_help.png'/>'>";
				
				//Check value approveStatus เพื่อ สลับ icon
				if(txtApproveStatus == "W"){
					jQuery(this).html(iconWaitApprove);
				}else if(txtApproveStatus == "D"){
					jQuery(this).html(iconDisApprove);
				}else if(txtApproveStatus == "A"){
					jQuery(this).html(iconApprove);
				}
			}
		});
		
		//Hide Edit
		jQuery(".edit").each(function(i){
			
			//ถ้าเป็น Header ไม่ต้องจัดการ
			if(i > 0){
				
				//get ค่า approveStatus ในแต่ละ row
				var txtApproveStatus = jQuery("[name='manage.listNewProject["+(i-1)+"].approveStatus']").val();
				
				//Check value approveStatus เพื่อ  hide edit
				if(txtApproveStatus == "A" || txtApproveStatus == "D"){
					jQuery(this).html("");
				}
			}
		});
		
		//Hide Checkbox
		jQuery(".checkbox").each(function(i){
			
			//ถ้าเป็น Header ไม่ต้องจัดการ
			if(i > 0){
				
				//get ค่า approveStatus ในแต่ละ row
				var txtApproveStatus = jQuery("[name='manage.listNewProject["+(i-1)+"].approveStatus']").val();
				
				//Check value approveStatus เพื่อ  hide checkbox
				if(txtApproveStatus == "A" || txtApproveStatus == "D"){
					jQuery(this).html("");
				}
			}
		});
				
	}
	
	function loadCondition(){
		
		var projectId = jQuery("#projectId option:selected").val();
		
		//Check เพื่อวาด
		if(projectId != ""){
			//โหลด combobox
			jQuery.ajax({
	            type : "POST",
	            url: "<s:url value='/selectitem/ProjectConditionSelectItemServlet'/>",
	            data : {projectId: projectId},
	            async : false,
	            success : function(jsonData) {
	            	
	            	_jData = jsonData;
	            	            	
	            	if('<s:property value="page.getPage()"/>' == "edit"){
		 				var combo = "<select id='conditionId' name='manage.projectConditionId' class='combox75' style='width:300px;'>";
		 				combo += "<option value=''></option>";
	
		 				for (var index = 0; index < jsonData.length; index++) {
							combo += "<option value='" + jsonData[index].key + "'>" + jsonData[index].value + "</option>";			
		 				}
		 				
		 				combo += "</select>";
		 				jQuery("#spanCondition").html(combo);
	            	}else{
	            		var combo = "<select id='conditionId' name='manage.projectConditionId' class='combox75 disabled='disabled' ' style='width:300px;'>";
		 				combo += "<option value=''></option>";
	
		 				for (var index = 0; index < jsonData.length; index++) {
							combo += "<option value='" + jsonData[index].key + "'>" + jsonData[index].value + "</option>";			
		 				}
		 				
		 				combo += "</select>";
		 				jQuery("#spanCondition").html(combo);
	            	}

	              }
	          });
		}else{
			if('<s:property value="page.getPage()"/>' == "edit"){
				var combo = "<select id='conditionId' name='manage.projectConditionId' class='combox75'>";
				combo += "<option value=''></option>";
				combo += "</select>";
				jQuery("#spanCondition").html(combo);
			}else{
				var combo = "<select id='conditionId' name='manage.projectConditionId' class='combox75' disabled='disabled'>";
				combo += "<option value=''></option>";
				combo += "</select>";
				jQuery("#spanCondition").html(combo);
			}
			
		}
		
		jQuery("#conditionId").attr('validName','<s:text name="to.conditionTime" />' );
		jQuery("#conditionId").selectmenu();
		jQuery("#conditionId").selectmenu("refresh");
		
		jQuery("#conditionId").selectmenu({
			change: function( event, ui ) {
				checkFlag(jQuery("#conditionId option:selected").val());
			}
   		});
		
		//Check เพื่อจัดการ Style หลังจากวาด Tag ใหมไปแล้ว
		if('<s:property value="page.getPage()"/>' == "edit"){
			jQuery("#projectId").prop('disabled', true);
			jQuery("#projectId-button").prop('disabled', true);
			
			jQuery("#conditionId").prop('disabled', true);
			jQuery("#conditionId-button").prop('disabled', true);
			jQuery("#conditionId-button").css('width', '287px');
		}else{
			jQuery("#conditionId-button").css('width', '287px');
		}
		
	}
	
	function checkFlag(key){
		
		// Method จัดการหน้าจอหลังจากการเลือก Combobox
		var hourTot;
			
		for(var index = 0; index < _jData.length; index++){
						
			if(key == _jData[index].key){
				
				_projConFlag = _jData[index].object.projConFlag;
				hourTot = _jData[index].object.hourTot;
			}
		}

		if(_projConFlag == "Y"){ // fix hour 
			
			// จัดการหน้าจอ
			jQuery("#trStartDate").hide();
			jQuery("#trEndDate").hide();
			jQuery("#trWorkDate").show();
			
			// add class
			jQuery("#workDate_dd_sl_mm_sl_yyyy").addClass('requireInput'); 
			
			// remove class
			jQuery("#startDate_dd_sl_mm_sl_yyyy").removeClass('requireInput');
			jQuery("#endDate_dd_sl_mm_sl_yyyy").removeClass('requireInput');
			jQuery("#startTime_hh_cl_mm").removeClass('requireInput');
			jQuery("#endTime_hh_cl_mm").removeClass('requireInput');
			
			// set value
			convertFixHour(hourTot);
			
		}else{
			
			// จัดการหน้าจอ
			jQuery("#trStartDate").show();
			jQuery("#trEndDate").show();
			jQuery("#trWorkDate").hide();
			
			// add class
			jQuery("#startDate_dd_sl_mm_sl_yyyy").addClass('requireInput');
			jQuery("#endDate_dd_sl_mm_sl_yyyy").addClass('requireInput');
			jQuery("#startTime_hh_cl_mm").addClass('requireInput');
			jQuery("#endTime_hh_cl_mm").addClass('requireInput');
			
			// remove class
			jQuery("#workDate_dd_sl_mm_sl_yyyy").removeClass('requireInput'); 
			
			// set value
			var txtStartDate = jQuery("#startDate_dd_sl_mm_sl_yyyy").val()
			var txtEndDate = jQuery("#endDate_dd_sl_mm_sl_yyyy").val();
			var txtStartTime = jQuery("#startTime_hh_cl_mm").val();
			var txtEndTime = jQuery("#endTime_hh_cl_mm").val();
			
			//check ว่ามีค่าครบทุกตัวหรือไม่ ดักตรงนี้สำหรับกรณี add ลง inform แล้วกด edit กลับมา เวลาจะได้ยังคงอยู่
			if(	(txtStartDate != "" && txtStartDate != 'undefined' && txtStartDate != "__/__/_____") 
					&& (txtEndDate != "" && txtEndDate != 'undefined' && txtEndDate != "__/__/_____") 
					&& (txtStartTime != "" && txtStartTime != 'undefined' && txtStartTime!= "__:__") 
					&& (txtEndTime != "" && txtEndTime != 'undefined' && txtEndTime !="__:__")){
				
				calculateTimeOffsetDayHourMinute();	
				
			}else{
				jQuery("#day").val("0");
				jQuery("#hour").val("0");
				jQuery("#minute").val("0");
			}
				
		}
		
		//set value to hidden data
		jQuery("#proj_con_flag").val(_projConFlag);
		jQuery("#conditionId-button").css("width","290px");
	}
	
	/*
		Function : มีไว้สำหรับการแปลงค่า FixHourTOT จาก ProjectCondition ที่กำหนดมาเป็นทศนิยม
	*/
	function convertFixHour(hourTot){
		
		var minute;
		var hour;
		var day = 0;
		
		//แยก Hour ที่ได้ ออกเป็น Hour และ Minute
		if(hourTot.indexOf(".") >= 0){
			minute = hourTot.split(".")[1];
			minute = parseInt(minute) * 6;
			hour = hourTot.split(".")[0];
		}
		
		//Ceck ว่า Minute ที่แปลงมาเกิน 60 หรือไม่
		if(parseInt(minute) >= 60){
			var numHour = Math.trunc(minute / 60); //hr
			var numMinutes = minute % 60; //min
			
			minute = numMinutes;
			hour = parseInt(hour) + parseInt(numHour);
		}
		
		//Check ว่า Hour ที่แปลงมาเกิน 8 หรือไม่
		if(parseInt(hour) >= 8){
			var numDay = Math.trunc(hour / 8); //day
			var numHour = hour % 8; // hour
			
			hour = numHour;
			day = numDay;
		}
		
		jQuery("#day").val(day);
		jQuery("#hour").val(hour);
		jQuery("#minute").val(minute);
	}
	
	function checkFlagPageEdit(){
		
		var projConFlag = jQuery("#hide_projectConditionFlag").val();
		var hourTot = jQuery("#hide_hourTot").val();;
		
		if(projConFlag == "Y"){ // fix hour 
			// จัดการหน้าจอ
			jQuery("#trStartDate").hide();
			jQuery("#trEndDate").hide();
			jQuery("#trWorkDate").show();
			
			// add class
			jQuery("#workDate_dd_sl_mm_sl_yyyy").addClass('requireInput'); 
			
			// remove class
			jQuery("#startDate_dd_sl_mm_sl_yyyy").removeClass('requireInput');
			jQuery("#endDate_dd_sl_mm_sl_yyyy").removeClass('requireInput');
			jQuery("#startTime_hh_cl_mm").removeClass('requireInput');
			jQuery("#endTime_hh_cl_mm").removeClass('requireInput');
			
			// set value
			jQuery("#day").val("0");
			jQuery("#hour").val(hourTot);
			jQuery("#minute").val("0");
		}else{
			// จัดการหน้าจอ
			jQuery("#trStartDate").show();
			jQuery("#trEndDate").show();
			jQuery("#trWorkDate").hide();
			
			// add class
			jQuery("#startDate_dd_sl_mm_sl_yyyy").addClass('requireInput');
			jQuery("#endDate_dd_sl_mm_sl_yyyy").addClass('requireInput');
			jQuery("#startTime_hh_cl_mm").addClass('requireInput');
			jQuery("#endTime_hh_cl_mm").addClass('requireInput');
			
			// remove class
			jQuery("#workDate_dd_sl_mm_sl_yyyy").removeClass('requireInput'); 

			// set value
			jQuery("#hour").val("");
		}
		
	}
	
	function addEditRowInForm(){
				
		//Check Flag เพื่อจะจับ Require 
		if(_projConFlag == "Y"){
			
			jQuery("#workDate").addClass("requireFill");		
			jQuery("#manage_remark").removeClass("requireFill");
			
			//Set date time to object for checkDateTimeOverlap
			jQuery("#startDate").input_dateformat('dateValue',"");
			jQuery("#endDate").input_dateformat('dateValue',"");
			jQuery("#startTime").input_timeformat("timeValue","");
			jQuery("#endTime").input_timeformat("timeValue","");
		
			jQuery("#startDate").input_dateformat('dateValue',jQuery("#workDate").val());
			jQuery("#endDate").input_dateformat('dateValue',jQuery("#workDate").val());
			jQuery("#startTime").input_timeformat("timeValue","00:00");
			jQuery("#endTime").input_timeformat("timeValue","23:59");	
			
		 }else{
			 
			// Add  class : requireInput ของรายละเอียดเวลาชดเชย เพิ่มในกรณีมีการกดปุุ่ม แก้ไข ก่อน ตกลง
			jQuery("#projectId").addClass("requireFill");
			jQuery("#conditionId").addClass("requireFill");
			jQuery("#startDate").addClass("requireFill");
			jQuery("#startTime").addClass("requireFill");
			jQuery("#endDate").addClass("requireFill");
			jQuery("#endTime").addClass("requireFill");
			jQuery("#day").addClass("requireFill");
			jQuery("#hour").addClass("requireFill");
			jQuery("#minute").addClass("requireFill");
			jQuery("#detailWork").addClass("requireFill");
					
			jQuery("#workDate").removeClass("requireFill");
			jQuery("#manage_remark").removeClass("requireFill");
		 }
		
		// VALIDATE ALL INSIDE DIV ID
		if(!validateDivInputAll("addForm")){
			return false;
		}
		
		var divId = "divTableAdd";
		var tableId = "tableId_" + divId;
		var listName = "manage.listNewProject";
		var index = 0;
		
		if(jQuery("#currentRowEdited_" + divId).val() == ""){
			//เป็นการเพิ่มใหม่
					
			// Check Overplap Data Before Add
			if(!checkDateTimeOverlap()){
				console.log("##### [ADD OVERLAP] #####");
				return false;
			}
			
			index = jQuery('table#'+tableId+' tbody tr').length;
			
			var clArr = ["checkbox", "edit", "col-width-150px","col-width-150px","col-width-150px center","col-width-150px", "col-width-100px", "col-width-100px", "col-width-auto"];
			var elmArr = new Array();
			
			 if(_projConFlag == "Y"){
             	jQuery("#startTime").val("00:00");
         		jQuery("#endTime").val("23:59");
             	
             	var htmlStartDate = "<input type='hidden' name='"+ listName + "["+index+"].startDate' value='" + jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + "'/>"
                var htmlStartTime = "<input type='hidden' name='"+ listName + "["+index+"].startTime' value='" + jQuery("#startTime_hh_cl_mm").val() + "'/>"
                var htmlStartDateTime = "<input type='hidden' name='"+ listName + "["+index+"].startDateTime' value='" + jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime_hh_cl_mm").val() + "'/>"
                var htmlEndDate = "<input type='hidden' name='"+ listName + "["+index+"].endDate' value='" + jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + "'/>"
                var htmlEndTime = "<input type='hidden' name='"+ listName + "["+index+"].endTime' value='" + jQuery("#endTime_hh_cl_mm").val() + "'/>"
                var htmlEndDateTime = "<input type='hidden' name='"+ listName + "["+index+"].endDateTime' value='" + jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime_hh_cl_mm").val() + "'/>"
                var htmlWorkDate = "<input type='hidden' name='"+ listName + "["+index+"].workDate' value='" + jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + "'/>"

			 }else{
             	var htmlStartDate = "<input type='hidden' name='"+ listName + "["+index+"].startDate' value='" + jQuery("#startDate_dd_sl_mm_sl_yyyy").val() + "'/>"
             	var htmlStartTime = "<input type='hidden' name='"+ listName + "["+index+"].startTime' value='" + jQuery("#startTime_hh_cl_mm").val() + "'/>"
             	var htmlStartDateTime = "<input type='hidden' name='"+ listName + "["+index+"].startDateTime' value='" + jQuery("#startDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime_hh_cl_mm").val() + "'/>"
             	var htmlEndDate = "<input type='hidden' name='"+ listName + "["+index+"].endDate' value='" + jQuery("#endDate_dd_sl_mm_sl_yyyy").val() + "'/>"
             	var htmlEndTime = "<input type='hidden' name='"+ listName + "["+index+"].endTime' value='" + jQuery("#endTime_hh_cl_mm").val() + "'/>"
             	var htmlEndDateTime = "<input type='hidden' name='"+ listName + "["+index+"].endDateTime' value='" + jQuery("#endDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime_hh_cl_mm").val() + "'/>"
                var htmlWorkDate = "<input type='hidden' name='"+ listName + "["+index+"].workDate' value='" + jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + "'/>"
			 }
			
			
			//checkbox & hidden value column
			elmArr[0] = "<input type='checkbox' id='cnkColumnId' name='cnkColumnId' value=''/>"
				+ "<input type='hidden' name='"+ listName + "["+index+"].id' value=''/>"
				+ "<input type='hidden' name='"+ listName + "["+index+"].deleteFlag' value=''/>"
                + "<input type='hidden' name='"+ listName + "["+index+"].edited' value='false'/>"
                + "<input type='hidden' name='"+ listName + "["+index+"].projectId' value='" + jQuery("#projectId option:selected").val() + "'/>"
                + "<input type='hidden' name='"+ listName + "["+index+"].project' value='" + jQuery("#projectId option:selected").text() + "'/>"
                + "<input type='hidden' name='"+ listName + "["+index+"].projectConditionId' value='" + jQuery("#conditionId option:selected").val() + "'/>"
                + "<input type='hidden' name='"+ listName + "["+index+"].projectCondition' value='" + jQuery("#conditionId option:selected").text() + "'/>"
                + "<input type='hidden' name='"+ listName + "["+index+"].projectConditionFlag' value='" + jQuery("#proj_con_flag").val() + "'/>"

				+ htmlStartDate
                + htmlStartTime
                + htmlStartDateTime
                + htmlEndDate
                + htmlEndTime
                + htmlEndDateTime
                + htmlWorkDate

                + "<input type='hidden' name='"+ listName + "["+index+"].day' value='" + jQuery("#day").val() + "'/>"
                + "<input type='hidden' name='"+ listName + "["+index+"].hour' value='" + jQuery("#hour").val() + "'/>"
                + "<input type='hidden' name='"+ listName + "["+index+"].minute' value='" + jQuery("#minute").val() + "'/>"
                + "<input type='hidden' name='"+ listName + "["+index+"].detailWork' value='" + jQuery("#detailWork").val() + "'/>";
                
            //edit column       
			elmArr[1] = "<a href='javascript:void(0);' onclick='prepareDataBeforeSetInForm(" + index + ",\"" + divId + "\",receiveDataEditedInForm,\"" + listName + "\", arrayHid_" + divId + ");'>"
					+ "<img src='<s:url value='"+centralPath+"/images/icon/i_edit.png'/>' />"
					+ "</a>"; 
					
			if('<s:property value="page.getPage()"/>' == "add"){
				
				elmArr[2] = jQuery("#projectId option:selected").text();
				elmArr[3] = jQuery("#conditionId option:selected").text();
				
				if(_projConFlag == "Y"){
					elmArr[4] = jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime_hh_cl_mm").val();
					elmArr[5] = jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime_hh_cl_mm").val();
				}else{
					elmArr[4] = jQuery("#startDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime_hh_cl_mm").val();
					elmArr[5] = jQuery("#endDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime_hh_cl_mm").val();
				}
				
				elmArr[6] = jQuery("#day").val();
				elmArr[7] = jQuery("#hour").val();
				elmArr[8] = jQuery("#minute").val();
				
			}else if('<s:property value="page.getPage()"/>' == "edit"){
				
				elmArr[2] = jQuery("#detailWork").val();
				
				if(_projConFlag == "Y"){
					elmArr[3] = jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime_hh_cl_mm").val();
					elmArr[4] = jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime_hh_cl_mm").val();
				}else{
					elmArr[3] = jQuery("#startDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime_hh_cl_mm").val();
					elmArr[4] = jQuery("#endDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime_hh_cl_mm").val();
				}
				
				elmArr[5] = jQuery("#day").val();
				elmArr[6] = jQuery("#hour").val();
				elmArr[7] = jQuery("#minute").val();
				
				elmArr[8] = jQuery("input[name='"+ listName + "["+index+"].approveStatus']").val();
				
			}			
			
			//2. create row 
            tableCreateTableRow(tableId, clArr, elmArr);
			
            clearValueInForm();
            
		}else{
						
			// Check Overplap Data Before Edit
			if(!checkDateTimeOverlap()){
				console.log("##### [EDIT OVERLAP] #####");
				return false;
			}
					
			//เป็นการแก้ไขรายการที่ Row
            index = jQuery("#currentRowEdited_" + divId).val();
     			
            //เปลี่ยน Flag เพื่อให้รู้ว่ารายการนี้ถูกแก้ไข
            jQuery("input[name='"+ listName + "["+index+"].edited']").val("true");
            
            //แก้ไขค่าที่แสดง และค่าที่ซ่อนไว้
            jQuery("input[name='"+ listName + "["+index+"].projectId']").val(jQuery("#projectId option:selected").val());
            jQuery("input[name='"+ listName + "["+index+"].project']").val(jQuery("#projectId option:selected").text());
            jQuery("input[name='"+ listName + "["+index+"].projectConditionId']").val(jQuery("#conditionId option:selected").val());
            jQuery("input[name='"+ listName + "["+index+"].projectCondition']").val(jQuery("#conditionId option:selected").text());
            jQuery("input[name='"+ listName + "["+index+"].projectConditionFlag']").val(jQuery("#proj_con_flag").val());
           
            if(_projConFlag == "Y"){
            	jQuery("input[name='"+ listName + "["+index+"].startDate']").val(jQuery("#workDate_dd_sl_mm_sl_yyyy").val());
                jQuery("input[name='"+ listName + "["+index+"].startTime']").val("00:00");
                jQuery("input[name='"+ listName + "["+index+"].startDateTime']").val(jQuery("#startDate_dd_sl_mm_sl_yyyy").val() + " " + "00:00:00");
                jQuery("input[name='"+ listName + "["+index+"].endDate']").val(jQuery("#workDate_dd_sl_mm_sl_yyyy").val());
                jQuery("input[name='"+ listName + "["+index+"].endTime']").val("23:59");
                jQuery("input[name='"+ listName + "["+index+"].endDateTime']").val(jQuery("#endDate_dd_sl_mm_sl_yyyy").val() + " " + "23:59:00");
                jQuery("input[name='"+ listName + "["+index+"].workDate']").val(jQuery("#workDate_dd_sl_mm_sl_yyyy").val());
            }else{
				jQuery("input[name='"+ listName + "["+index+"].startDate']").val(jQuery("#startDate_dd_sl_mm_sl_yyyy").val());
				jQuery("input[name='"+ listName + "["+index+"].startTime']").val(jQuery("#startTime_hh_cl_mm").val());
				jQuery("input[name='"+ listName + "["+index+"].startDateTime']").val(jQuery("#startDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime_hh_cl_mm").val()+":00");
				jQuery("input[name='"+ listName + "["+index+"].endDate']").val(jQuery("#endDate_dd_sl_mm_sl_yyyy").val());
				jQuery("input[name='"+ listName + "["+index+"].endTime']").val(jQuery("#endTime_hh_cl_mm").val());
				jQuery("input[name='"+ listName + "["+index+"].endDateTime']").val(jQuery("#endDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime_hh_cl_mm").val()+":00");
				jQuery("input[name='"+ listName + "["+index+"].workDate']").val(jQuery("#workDate_dd_sl_mm_sl_yyyy").val());
            }
            
            jQuery("input[name='"+ listName + "["+index+"].day']").val(jQuery("#day").val());
            jQuery("input[name='"+ listName + "["+index+"].hour']").val(jQuery("#hour").val());
            jQuery("input[name='"+ listName + "["+index+"].minute']").val(jQuery("#minute").val());
            jQuery("input[name='"+ listName + "["+index+"].detailWork']").val(jQuery("#detailWork").val());
			
            jQuery("#manage_to_id").val(jQuery("input[name='"+ listName + "["+index+"].timeOffsetId']").val());
            
            if('<s:property value="page.getPage()"/>' == "add"){
            
	            jQuery('table#'+tableId+' tbody tr:eq(' + index + ') td').each(function(index, ele){
	                switch (index) {
	                case 3:
	                    jQuery(ele).html(jQuery("#projectId option:selected").text());
	                    
	                    break;
	                case 4:
	                    jQuery(ele).html(jQuery("#conditionId option:selected").text());
	                    
	                    break;
	                case 5:
	                	 if(_projConFlag == "Y"){
	                		 jQuery(ele).html(jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime").val()+":00");
	                	 }else{
	                		 jQuery(ele).html(jQuery("#startDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime_hh_cl_mm").val()+":00");
	                	 }
	                    
	                    break;
	                case 6:
	                    if(_projConFlag == "Y"){
	                    	jQuery(ele).html(jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime").val()+":00");
	                    }else{
	                    	jQuery(ele).html(jQuery("#endDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime_hh_cl_mm").val()+":00");
	                    }
	                    
	                    break;
	                case 7:
	                    jQuery(ele).html(jQuery("#day").val());
	                    
	                    break;
					case 8:
						jQuery(ele).html(jQuery("#hour").val());
	                        
						break;
					case 9:
						jQuery(ele).html(jQuery("#minute").val());
	                            
						break;
	                default:
	                    break;
	            }       
	            });
	            
            }else if('<s:property value="page.getPage()"/>' == "edit"){
            	
            	 jQuery('table#'+tableId+' tbody tr:eq(' + index + ') td').each(function(index, ele){
 	                switch (index) {
 	                case 3:
 	                    jQuery(ele).html(jQuery("#detailWork").val());
 	                    
 	                    break;
 	                case 4:
 	                	 if(_projConFlag == "Y"){
 	                		 jQuery(ele).html(jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime").val()+":00");
 	                	 }else{
 	                		 jQuery(ele).html(jQuery("#startDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#startTime_hh_cl_mm").val()+":00");
 	                	 }
 	                    
 	                    break;
 	                case 5:
 	                    if(_projConFlag == "Y"){
 	                    	jQuery(ele).html(jQuery("#workDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime").val()+":00");
 	                    }else{
 	                    	jQuery(ele).html(jQuery("#endDate_dd_sl_mm_sl_yyyy").val() + " " + jQuery("#endTime_hh_cl_mm").val()+":00");
 	                    }
 	                    
 	                    break;
 	                case 6:
 	                    jQuery(ele).html(jQuery("#day").val());
 	                    
 	                    break;
 					case 7:
 						jQuery(ele).html(jQuery("#hour").val());
 	                        
 						break;
 					case 8:
 						jQuery(ele).html(jQuery("#minute").val());
 	                            
 						break;
 					case 9:
 						jQuery(ele).html(jQuery("input[name='"+ listName + "["+index+"].approveStatus']").val());
 	                            
 						break;
 	                default:
 	                    break;
 	            }       
 	            });
            }
        }
		
		//เคลียร์ค่า index ที่เก็บไว้ ว่า row ไหนกำลังถูกแก้ไข
        jQuery("#currentRowEdited_" + divId).val("");
     
        //เลิก Highlight Row ที่ถูกเลือก
        jQuery('table#'+tableId+' tbody tr:eq(' + index + ')').removeClass("ui-state-highlight");
        
      	//เคลียร์ค่าใน Form
        clearValueInForm();
	}
	
	function receiveDataEditedInForm(obj){

		_projConFlag = obj.projectConditionFlag;
		jQuery("#proj_con_flag").val(_projConFlag);
		
		//จัดการหน้าจอ
		if(obj.projectConditionFlag == "Y"){ //fix hour
			
			jQuery("#trStartDate").hide();
			jQuery("#trEndDate").hide();
			jQuery("#trWorkDate").show();
			
			// add class
			jQuery("#workDate_dd_sl_mm_sl_yyyy").addClass('requireFill'); 
			
			// remove class
			jQuery("#startDate_dd_sl_mm_sl_yyyy").removeClass('requireFill');
			jQuery("#endDate_dd_sl_mm_sl_yyyy").removeClass('requireFill');
			jQuery("#startTime_hh_cl_mm").removeClass('requireFill');
			jQuery("#endTime_hh_cl_mm").removeClass('requireFill');
		}else{
			jQuery("#trStartDate").show();
			jQuery("#trEndDate").show();
			jQuery("#trWorkDate").hide();
			
			// add class
			jQuery("#startDate_dd_sl_mm_sl_yyyy").addClass('requireFill');
			jQuery("#endDate_dd_sl_mm_sl_yyyy").addClass('requireFill');
			jQuery("#startTime_hh_cl_mm").addClass('requireFill');
			jQuery("#endTime_hh_cl_mm").addClass('requireFill');
			
			// remove class
			jQuery("#workDate_dd_sl_mm_sl_yyyy").removeClass('requireInput'); 
		}

		var divId = "divTableAdd"; //กำหนดให้ตรงกับตัวแปร divresult
	    var tableId = "tableId_" + divId;
		
	    //ชื่อตัวแปรจะตรงกับค่าที่กำหนดไว้ใน hiddenData
	    jQuery("#projectId").val(obj.projectId);
	    jQuery("#projectId").selectmenu("refresh");
	    
	    //โหลดตรงนี้เพื่อให้เวลากด Edit แล้วมีค่ามาแสดงผล combobox ไม่งั้นจะไม่สามารถเอา projectConditionId ไป map ได้
	    loadCondition();
	    
	    jQuery("#conditionId").val(obj.projectConditionId);
	    jQuery("#conditionId").selectmenu("refresh");
		
		if(obj.projectConditionFlag == "Y"){
			
			var arrDate = obj.workDate.split("/");
			arrDate[2] = (parseInt(arrDate[2])-543).toString();
			obj.workDate = arrDate[0] + "/" + arrDate[1] + "/" + arrDate[2];

			jQuery("#workDate").input_dateformat('dateValue',obj.workDate);
			jQuery("#startTime").input_timeformat("timeValue","00:00");
			jQuery("#endTime").input_timeformat("timeValue","23:59");
		}else{

			// ใส่เพื่อให้ค่าถูกต้อง เนื่องจากตัวกลางใส่ค่าให้ผิด จึงทำการเครียค่าเพื่อให้เกิด : ก่อนจึง add เข้าไปใหม่ 19/05/2017
			jQuery("#startDate").input_dateformat('dateValue',"");
			jQuery("#endDate").input_dateformat('dateValue',"");
			jQuery("#startTime").input_timeformat("timeValue","");
			jQuery("#endTime").input_timeformat("timeValue","");
			
			var arrStartDate = obj.startDate.split("/");
			arrStartDate[2] = (parseInt(arrStartDate[2])-543).toString();
			obj.startDate = arrStartDate[0] + "/" + arrStartDate[1] + "/" + arrStartDate[2];
			
			var arrEndDate = obj.endDate.split("/");
			arrEndDate[2] = (parseInt(arrEndDate[2])-543).toString();
			obj.endDate = arrEndDate[0] + "/" + arrEndDate[1] + "/" + arrEndDate[2];
			
			jQuery("#startDate").input_dateformat('dateValue',obj.startDate);
			jQuery("#endDate").input_dateformat('dateValue',obj.endDate);
			jQuery("#startTime").input_timeformat("timeValue",obj.startTime);
			jQuery("#endTime").input_timeformat("timeValue",obj.endTime);
			
		}

	    jQuery("#day").val(obj.day);
	    jQuery("#hour").val(obj.hour);
	    jQuery("#minute").val(obj.minute);
	    jQuery("#detailWork").val(obj.detailWork);
	}
	
	function cancelAddEditRowInForm(){
		
		var divId = "divTableAdd"; 
		 
        var index = jQuery("#currentRowEdited_" + divId).val();
        var tableId = "tableId_" + divId;
         
        //เคลียร์ค่า index ที่เก็บไว้ ว่า row ไหนกำลังถูกแก้ไข
        jQuery("#currentRowEdited_" + divId).val("");
     
        //เลิก Highlight Row ที่ถูกเลือก
        jQuery('table#'+tableId+' tbody tr:eq(' + index + ')').removeClass("ui-state-highlight");
		
		clearValueInForm();
			
		jQuery("#conditionId").prop('disabled', true);
		jQuery("#conditionId-button").prop('disabled', true);
		
		jQuery("#conditionId-button").css("width","290px");
		jQuery("#conditionId").selectmenu("refresh");
	}
	
	
</script>
		
<fieldset class="fieldsetStyle" id="fieldset_manage" style="margin: -1px 18px 18px">
	
	<s:if test="page.getPage() == 'add' ">
		<!-- div สำหรับแสดงผล  -->
		<div id="divTableAdd" class="RESULT" style="width: 95%; height:300px;" ></div>
		<!--ส่วนของการกำหนด parameters -->
		<s:set var="divresult" value='{"divTableAdd"}'/> 
		<s:set var="callbackFunction" value='{"receiveDataEditedInForm"}'/> 
		<s:set var="listTableData" value='%{"manage.listNewProject"}'/> 
		<s:set var="columnId" value='{"id"}' />
		<s:set var="columnName" value='{getText("to.project"), getText("to.conditionTime"), getText("to.startDateTime"), getText("to.endDateTime"), getText("to.totalDayOffset"), getText("to.totalHourOffset"), getText("to.totalMinuteOffset")  }'/>
		<s:set var="columnData" value='{"project","projectCondition","startDateTime","endDateTime","day","hour","minute"}'/>
		<s:set var="columnCSSClass" value='{"col-width-150px","col-width-150px","col-width-150px date","col-width-150px date", "col-width-100px number_right", "col-width-100px number_right", "col-width-auto number_right"}'/>
		<s:set var="hiddenData" value='{"projectId","project","projectConditionId","projectCondition","projectConditionFlag","hourTot","startDate","startTime","startDateTime","endDate","endTime","endDateTime","workDate","day","hour","minute","detailWork","remark"}'/>
		<s:set var="settingTable" value='{"null:false:true"}'/> 
		<!-- include table template -->
		<s:include value="/jsp/template/tableAddDeleteRowInForm.jsp"/>
	</s:if>
	
	<s:elseif test="page.getPage() == 'edit' ">
		<!-- div สำหรับแสดงผล  -->
		<div id="divTableAdd" class="RESULT" style="width: 95%; height:300px;" ></div>
		<!--ส่วนของการกำหนด parameters -->
		<s:set var="divresult" value='{"divTableAdd"}'/> 
		<s:set var="callbackFunction" value='{"receiveDataEditedInForm"}'/> 
		<s:set var="listTableData" value='%{"manage.listNewProject"}'/> 
		<s:set var="columnId" value='{"id"}' />
		<s:set var="columnName" value='{getText("to.workDetail"), getText("to.startDateTime"), getText("to.endDateTime"), getText("to.totalDayOffset"), getText("to.totalHourOffset"), getText("to.totalMinuteOffset"), getText("to.approveStatusTO")  }'/>
		<s:set var="columnData" value='{"detailWork","startDateTime","endDateTime","day","hour","minute","approveStatus"}'/>
		<s:set var="columnCSSClass" value='{"col-width-200px","col-width-160px date","col-width-160px date", "col-width-90px number_right", "col-width-90px number_right", "col-width-90px number_right", "col-width-auto d_approve date"}'/>
		<s:set var="hiddenData" value='{"projectId","project","projectConditionId","projectCondition","projectConditionFlag","hourTot","startDate","startTime","startDateTime","endDate","endTime","endDateTime","workDate","day","hour","minute","detailWork","remark","approveStatus","approveStatusDesc","rownum","id","timeOffsetId","deletedByEditPage"}'/>
		<s:set var="settingTable" value='{"null:false:true"}'/> 
		<!-- include table template -->
		<s:include value="/jsp/template/tableAddDeleteRowInForm.jsp"/>
	</s:elseif>

	<s:elseif test="page.getPage() == 'view' ">
		<!-- div สำหรับแสดงผล  -->
		<div id="divTableAdd" class="RESULT" style="width: 95%; height:300px;" ></div>
		<!--ส่วนของการกำหนด parameters -->
		<s:set var="divresult" value='{"divTableAdd"}'/> 
		<s:set var="callbackFunction" value='{""}'/> 
		<s:set var="listTableData" value='%{"manage.listNewProject"}'/> 
		<s:set var="columnId" value='{"id"}' />
		<s:set var="columnName" value='{getText("to.workDetail"), getText("to.startDateTime"), getText("to.endDateTime"), getText("to.totalDayOffset"), getText("to.totalHourOffset"), getText("to.totalMinuteOffset"), getText("to.approveStatusTO")  }'/>
		<s:set var="columnData" value='{"detailWork","startDateTime","endDateTime","day","hour","minute","approveStatus"}'/>
		<s:set var="columnCSSClass" value='{"col-width-225px","col-width-160px date","col-width-160px date", "col-width-90px number_right", "col-width-90px number_right", "col-width-90px number_right", "col-width-auto d_approve date"}'/>
		<s:set var="hiddenData" value='{"projectId","project","projectConditionId","projectCondition","projectConditionFlag","hourTot","startDate","startTime","startDateTime","endDate","endTime","endDateTime","workDate","day","hour","minute","detailWork","remark","approveStatus","approveStatusDesc","rownum"}'/>
		<s:set var="settingTable" value='{"null:false:true"}'/> 
		<!-- include table template -->
		<s:include value="/jsp/template/tableAddDeleteRowInForm.jsp"/>
	</s:elseif>

	<legend><s:text name='to.timeOffsetDetail' /></legend>
	
	<br/>
	
	<s:if test="page.getPage() == 'add' || page.getPage() == 'edit'">
		<table id="addEditDetail" class="FORM">
			<tr style="display: none;">
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%"></td>
				<td class="VALUE" style="width: 40%"></td>
				<td class="VALUE" style="width: 5%"></td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%"><s:text name='to.project' /><em>*</em></td>
				<td class="VALUE" style="width: 40%">
					<s:select 					
						id="projectId"					
						name="manage.projectId"
						list="listProject"
						headerKey=""
						headerValue=""
						listKey="key"
						listValue="value"
						cssClass="combox requireFill"
						cssStyle="width:300px;"
						validName="%{getText('to.project')}"
						/>
				</td>
				<s:hidden id="hide_projectId" name="manage.projectId" />
				<s:hidden id="hide_project" name="manage.project" />
				<td class="VALUE" style="width: 5%"></td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%"><s:text name='to.conditionTime' /><em>*</em></td>
				<td class="VALUE" style="width: 40%">
					<span id="spanCondition">
	           			<select id="conditionId" name="manage.projectConditionId" class="combox" disabled="disabled" style="width:300px" >
							<option value=""></option>
						</select>
					</span>
				</td>
				<s:hidden id="hide_projectConditionId" name="manage.projectConditionId" />
				<s:hidden id="hide_projectCondition" name="manage.projectCondition" />
				<s:hidden id="hide_projectConditionFlag" name="manage.projectConditionFlag" />
				<s:hidden id="hide_hourTot" name="manage.hourTot" />
				<td class="VALUE" style="width: 5%"></td>
				<td class="BORDER"></td>
			</tr>
			<tr id="trStartDate">
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%"><s:text name='to.startDate' /><em>*</em></td>
				<td class="VALUE" style="width: 40%">
					<s:textfield 
						id="startDate" 
						name ="manage.startDate" 
						cssClass="input_datepicker_from_to datepicker" 
						validName="%{getText('to.startDate')}" 
					/>
					<em>&nbsp;&nbsp;</em>
					<s:text name='to.time' /><em>*</em><em>&nbsp;&nbsp;</em>
					<s:textfield id="startTime" name="manage.startTime" cssClass="timepicker" validName="%{getText('to.startTime')}"  />
<%-- 					<s:text name='to.unitMin' /> --%>
				</td>
				<td class="VALUE" style="width: 5%"></td>
				<td class="BORDER"></td>
			</tr>
			<tr  id="trEndDate"> 
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%"><s:text name='to.endDate' /><em>*</em></td>
				<td class="VALUE" style="width: 40%">
					<s:textfield 
						id="endDate" 
						name ="manage.endDate" 
						cssClass="datepicker" 
						validName="%{getText('to.endDate')}" 
	
					/>
					<em>&nbsp;&nbsp;</em>
					<s:text name='to.time' /><em>*</em><em>&nbsp;&nbsp;</em>
					<s:textfield id="endTime" name="manage.endTime" cssClass="timepicker" validName="%{getText('to.endTime')}"  />
<%-- 					<s:text name='to.unitMin' /> --%>
				</td>
				<td class="VALUE" style="width: 5%"></td>
				<td class="BORDER"></td>
			</tr>
			<tr id="trWorkDate" style="display: none;">
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%"><s:text name='to.workDay' /><em>*</em></td>
				<td class="VALUE" style="width: 40%">
					<s:textfield 
						id="workDate" 
						name ="manage.workDate" 
						cssClass="datepicker" 
						validName="%{getText('to.workDay')}" 
	
					/>
				</td>
				<td class="VALUE" style="width: 5%"></td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%"><s:text name='to.totalHourOffset' /><em>&nbsp;&nbsp;</em></td>
				<td class="VALUE" style="width: 40%">
					<s:textfield id="day" name ="manage.day" cssClass="readonly txt10" cssStyle="text-align: right; width:60px;"/>
					<s:text name='to.day' />
					<em style="padding-left:25px;">&nbsp;&nbsp;</em><s:textfield id="hour" name="manage.hour" cssClass="readonly txt10" cssStyle="text-align: right; width:60px;"/>
					<s:text name='to.hour' />
					<em style="padding-left:25px;">&nbsp;&nbsp;</em><s:textfield id="minute" name="manage.minute" cssClass="readonly txt10" cssStyle="text-align: right; width:60px;"/>
					<s:text name='to.min' />
				</td>
				<td class="VALUE" style="width: 5%">
				</td>
				<td class="BORDER"></td>
			</tr>
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 30%; vertical-align: top"><s:text name='to.workDetail' /><em>*</em></td>
				<td class="VALUE" colspan="2">
					<s:textarea id="detailWork" 
						name="manage.detailWork" 
						cssStyle="resize:none;"  
						cssClass="requireFill" 
						cols="50" 
						rows="2" 
						validName="%{getText('to.workDetail')}" 
					/>
				</td>
				<td class="BORDER"></td>
			</tr>
		</table>
	</s:if>
	
	<s:if test="page.getPage() == 'view' ">
		<table id="viewDetail" class="FORM">
			<tr>
				<td class="BORDER"></td>
				<td class="LABEL" style="width: 25%; vertical-align: top;"><s:text name='to.remark' /><em>*</em></td>
				<td class="VALUE" style="width: 40%">
						<s:textarea id="manage_remark" 
							name="manage.remark" 
							cssStyle="resize:none;" 
							cssClass="requireFill readonly" 
							cols="37" 
							rows="2" 
							validName="%{getText('to.remark')}" 
						/>
				</td>
				<td class="VALUE" style="width: 5%">
				<td class="BORDER"></td>
			</tr>
		</table>
	</s:if>	
	
	<s:if test="page.getPage() == 'add' || page.getPage() == 'edit'">
		<table class="BUTTON">
			<tr>
				<td class="LEFT RIGHT_70"></td>
				<td class="RIGHT LEFT_30">
					<div id="divButtonPredefine" class="ui-sitbutton-predefine"
						data-buttonType = "ok|cancel"
						data-auth = "true|true"
						data-func = "addEditRowInForm()|cancelAddEditRowInForm()"
						data-style = "btn-small|btn-small"
						data-container = "false">
					</div>
					
				</td>
			</tr>
		</table>
	</s:if>	
		
</fieldset>