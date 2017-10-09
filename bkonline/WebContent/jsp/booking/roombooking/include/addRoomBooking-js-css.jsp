<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	function sf() {
		jQuery("#notifications-message-list-help").hide();
		initAllUserAutocomplete();
		initUserBooking();
		initNotificationUse();
		// initEmptyRoom();
	}

	function initAllUserAutocomplete() {
		jQuery("#allUserCode").autocompletezAjax([{
               url: "<s:url value='%{@com.cct.inhouse.bkonline.web.selectitem.servlet.SelectItemServletURL@AllUserDisplayNameAndNicknameServlet}'/>"
               , defaultKey: ""
               , defaultValue: ""
               , afterChangeFunction: function() {
					searchUserById();       
               }
           }]);
	}
	
	var objUser = undefined;
	function searchUserById() {
		var txtUserId = jQuery("#allUserCode").val();
		if (txtUserId == '') {
			clearSerchUser();
			return;
		}
		
		var elOption = jQuery("#allUser_autocomplete > option[value='"+ txtUserId + "']");
		if (elOption == undefined) {
			clearSerchUser();
			return;
		}
		
		var objUserTemp = JSON.parse(jQuery(elOption).attr('json'));
		objUser = objUserTemp;
		jQuery("#roomBooking_userBookId").val(objUser.userId);
		jQuery("#roomBooking_userBookName").val(objUser.nameWithNickName);
		jQuery("#roomBooking_userBookEmail").val(objUser.email);
		jQuery("#roomBooking_userBookLineId").val(objUser.lineUserId);
		jQuery("#roomBooking_userBookDepartmentId").val(objUser.departmentId);
		jQuery("#roomBooking_userBookPositionId").val(objUser.positionId);
	}
	
	function clearSerchUser() {
		objUser = undefined;
		jQuery("#roomBooking_userBookId").val("");
		jQuery("#roomBooking_userBookName").val("");
		jQuery("#roomBooking_userBookEmail").val("");
		jQuery("#roomBooking_userBookLineId").val("");
		jQuery("#roomBooking_userBookDepartmentId").val("");
		jQuery("#roomBooking_userBookPositionId").val("");
	}
	
	function searchListEmptyRoomButton() {
		searchListEmptyRoom('', true);
	}
	
	function searchListEmptyRoom(txtDefaultId, isClick) {

		jQuery('#btnCheckEmptyRoom').removeClass('border_select');
		jQuery("#divEmptyRoom").html("");
		
		var txtStartDate = jQuery("#roomBooking_startDate").input_dateformat('dateValue');
		var txtStartTime = jQuery("#roomBooking_startTime").val();
		var txtEndDate = jQuery("#roomBooking_endDate").input_dateformat('dateValue');
		var txtEndTime = jQuery("#roomBooking_endTime").val();
		
		if (isClick) {
			// clearNotifyMessage();
			if(validateFormInputAll() == false){
				return false;
			}
		} else {
			if (txtStartDate == '' || txtStartTime == '' || txtEndDate == '' || txtEndTime == '') {
				return false;
			}
		}
		
		
		jQuery.ajax({
			type: "POST"
			, url: "<s:url value='%{@com.cct.inhouse.bkonline.web.selectitem.servlet.SelectItemServletURL@ListEmptyRoomServlet}'/>"
			, data: { 'startDate':txtStartDate.dateForDB
					, 'startTime':txtStartTime
					, 'endDate':txtEndDate.dateForDB
					, 'endTime':txtEndTime 
			} 
			, async: true // ให้ทำงานพร้อมกันทำ ajax ให้เสร็จก่อน ค่อยทำ java script 
			, success: function(data){
				if (data.error) {
					showNotifyMessageValidate(data.errorMessage + "<br/>");
					return;
				}
				
				var htmlEmptyRoom = "";
				jQuery.each(data.listEmptyRoom, function(i) {
					htmlEmptyRoom += '<div style="width: 180px; height: 35px; float: left;">';
					htmlEmptyRoom += '<input type="radio" name="emptyRoom" class="requireInput" validName="' + '<s:text name="bk.selectRoom" />' + '" value="' + this.id + '" roomName="' + this.roomName + '" ';
					
					if (txtDefaultId == undefined || txtDefaultId == '') {
						if (i == 0) {
							htmlEmptyRoom += ' checked="checked" ';	
						} 
					} else if (this.id == txtDefaultId){
						htmlEmptyRoom += ' checked="checked" ';	
					} 
					htmlEmptyRoom += ' />' + this.roomName;
					htmlEmptyRoom += '</div>';
				});
				// jQuery("#emptyRoomGroupId").html('<div style="width: 90%;">' + htmlEmptyRoom + '</div>');
				jQuery("#emptyRoomGroupId").html(htmlEmptyRoom);
			}
		});
	}
	
	// Validate เองที่ตัวกลางทำให้ไม่ได้
	function checkValidAdd() {
		bindInputToModel();
		jQuery('#btnCheckEmptyRoom').removeClass('border_select');
		if (jQuery("input[name='emptyRoom']").length == 0) {
			jQuery('#btnCheckEmptyRoom').addClass('border_select');
			var type = jQuery('#btnCheckEmptyRoom').attr("type");
			var msg = validateMessage.CODE_10015.replace("xxx", '<s:text name="bk.selectRoom" />') + "<br/>";
			var el = jQuery('#btnCheckEmptyRoom');
			showNotifyMessageValidate(msg);
			return false;
		}
	}
	
	function saveAdd() {
		submitPage("<s:url value='/jsp/booking/addRoomBooking.action' />");
	}
	    
	function cancelAdd() {
		submitPage("<s:url value='/jsp/calendar/dashboard/initDashboard.action' />");
	}
	
	
	function initNotificationUse() {
		var txtNotificationUse = jQuery("#roomBooking_notificationEmail").val();
		if (txtNotificationUse == "") {
			txtNotificationUse = "N";
		}
		jQuery("input[name='notificationUse'][value='" + txtNotificationUse + "']").prop("checked", true);
		jQuery("#roomBooking_notificationEmail").val(txtNotificationUse);
		
		
		txtNotificationUse = jQuery("#roomBooking_notificationLine").val();
		if (txtNotificationUse == "") {
			txtNotificationUse = "N";
		}
		jQuery("input[name='notificationUse'][value='" + txtNotificationUse + "']").prop("checked", true);
		jQuery("#roomBooking_notificationLine").val(txtNotificationUse);
		
	}
	
	function initUserBooking() {
		var jsonUserBooking = {codeValue: jQuery("#roomBooking_userBookId").val(), textValue: jQuery("#roomBooking_userBookName").val()}
        jQuery('#allUserCode').autocompletezAjax('autocompleteValue', 0, jsonUserBooking);
		searchUserById();
	}
	
	function initEmptyRoom() {
		searchListEmptyRoom(jQuery("#roomBooking_roomId").val(), false);
	}
	
	function bindInputToModel() {
		// Set ค่า Value ให้  Input ที่ Map กับ Model
		
		// ส่วนการแจ้งเตือนกลับ
		jQuery('#roomBooking_notificationEmail').val('N');
		jQuery('#roomBooking_notificationLine').val('N');
		jQuery("input[name='notificationUse']").each(function() {
			if (this.checked) {
				if (this.value == 'E') {
					jQuery('#roomBooking_notificationEmail').val(this.value);
				} else if (this.value == 'L') {
					jQuery('#roomBooking_notificationLine').val(this.value);
				}
			}
		});
		
		// ส่วนห้องว่าง
		jQuery("#roomBooking_roomId").val("");
		jQuery("#roomBooking_roomName").val("");
		jQuery("input[name='emptyRoom']").each(function() {
			if (this.checked) {
				jQuery("#roomBooking_roomId").val(this.value);
				jQuery("#roomBooking_roomName").val(jQuery(this).attr("roomName"));
				return;
			}
		});
		
		// ส่วนของอุปกรณ์เสริม
		jQuery("#roomBooking_equipmentListId").val("");
		jQuery("#roomBooking_equipmentListName").val("");
		var txtEquId = "";
		var txtEquName = "";
		jQuery("input[name='equipmentList']").each(function() {
			if (this.checked) {
				txtEquId += "," + this.value;
				txtEquName += "," + jQuery(this).attr('text');
			}
		});
		
		if (txtEquId.length > 0) {
			txtEquId = txtEquId.substring(1);
			txtEquName = txtEquName.substring(1);
			
			jQuery("#roomBooking_equipmentListId").val(txtEquId);
			jQuery("#roomBooking_equipmentListName").val(txtEquName);
		}
	}
</script>
<style type="text/css">
	TABLE.FORM TD {
		/** border: 1px solid black; **/
	} 
	
	INPUT {
		width: 95%;
	}
</style>