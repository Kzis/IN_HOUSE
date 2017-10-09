<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/scheduler/dhtmlxscheduler.css"/>
<style type="text/css" >
	.dhx_cal_container {
		width: 100%; 
		height: 100%; 
		z-index: 9; 
		position: absolute;
	}
</style>
<style type="text/css">
	 /* The side navigation menu */
	.side-nav-room {
	    height: 100%; /* 100% Full-height */
	    width: 0; /* 0 width - change this with JavaScript */
	    position: fixed; /* Stay in place */
	    z-index: 1; /* Stay on top */
	    top: 0;
	    right: 0;
	    background-color: #111; /* Black*/
	    overflow-x: hidden; /* Disable horizontal scroll */
	    padding-top: 40px; /* Place content 60px from the top */
	    transition: 0.5s; /* 0.5 second transition effect to slide in the sidenav */
	    z-index: 10;
		opacity: 0.8;
	}

	/* The navigation menu links */
	.side-nav-room a {
	    padding: 8px 8px 8px 32px;
	    text-decoration: none;
	    font-size: 16px;
	    color: #818181;
	    display: block;
	    transition: 0.3s
	}
	
	/* When you mouse over the navigation links, change their color */
	.side-nav-room a:hover, .offcanvas a:focus{
	    color: #f1f1f1;
	}
	
	/* Position and style the close button (top right corner) */
	.side-nav-room .side-nav-room-select-all {
	    position: fixed;
	    top: 0;
	    padding: 8px 8px 8px 16px;
	    font-size: 18px;
	}
	
	.side-nav-room .side-nav-room-close-btn {
		float: right;
		margin-top: -40px;
	    padding: 8px 8px 8px 8px;
	    font-size: 18px;
	}
	
	a.side-nav-room-close-btn {
		cursor: pointer;
		color: white;
		font-weight: bold;
	}
	
	a.side-nav-room-search {
		color: white;
		text-align: center;
		padding: 8px 8px 8px 8px;
	}
	
	a.side-nav-room-close-btn:hover, .offcanvas a.side-nav-room-close-btn:focus{
	    color: white;
	}
	
	/* On smaller screens, where height is less than 450px, change the style of the sidenav (less padding and a smaller font size) */
	@media screen and (max-height: 450px) {
	    .side-nav-room {padding-top: 15px;}
	    .side-nav-room a {font-size: 18px;}
	} 
	
	#mySideNavRoomExpand {
	    transition: right 0.5s;
	    padding: 0px;
	    position: fixed; 
	    right: 0px; 
	    top: 350px;
	    z-index: 10;
	}
	
	IMG.ROOM-EXPAND-ICON {
		width: 41px; 
		height: 41px;
	}
	
	.event-header {
		margin: 0px 0px;
		font-size: 14px;
		font-weight: bold;
		padding: 0px 2px 0px 2px;
	}
	
	.event-subject {
		margin: 0px 0px;
		font-size: 12px;
		font-weight: bold;
		padding: 0px 2px 0px 2px;
	}
	
	.event-detail {
		margin: 0px 0px;
		font-size: 10px;
		padding: 0px 2px 0px 2px;
	}
	
	.event-bookby {
		font-size: 12px;
		font-style: italic;
		text-align: right;
		padding: 4px 2px 0px 2px;
	}
	
	.icon_check_in_out {
		transform: rotate(90deg);
	}
	
	.dhx_menu_icon.icon_approve {
  		background-image: url('${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png');  
  		background-repeat: no-repeat;
  		background-position: center center;
  		background-size: 16px;
	}
	
	.dhx_menu_icon.icon_check_in {
  		background-image: url('${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png');  
  		background-repeat: no-repeat;
  		background-position: center center;
  		background-size: 16px;
	}
	
	.dhx_menu_icon.icon_check_out {
  		background-image: url('${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png');
  		background-repeat: no-repeat;
  		background-position: center center;
  		background-size: 16px;
	}
	
	.dhx_menu_icon.icon_over_limit_check_out {
  		background-image: url('${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/over_limit_check_out.png');
  		background-repeat: no-repeat;
  		background-position: center center;
  		background-size: 16px;
	}
	
	.dhx_menu_icon.icon_cancel {
  		background-image: url('${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png');
  		background-repeat: no-repeat;
  		background-position: center center;
  		background-size: 16px;
	}
	
	div.dhx_menu_head {
		background-image: url('${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/tool.png');
		background-repeat: no-repeat;
  		background-position: center top;
  		background-size: 16px;
  		
		width: 16px;
		height: 16px;
		margin-left: 3px;
		
		padding: 0px 0px 3px 0px;
		border: none;
		cursor: default
	}
	
	.dhx_cal_event.selected {
		box-shadow: 10px 10px 5px #888888;
	}
	
	.dhx_cal_select_menu.selected {
		box-shadow: none;
	}
	
	.dhx_title_status_custom {
		font-size: 14px;
		margin-top: 50px;
		margin-left: 50px;
	}
	
	.dhx_title_status_custom img {
		width: 16px; 
		height: 16px;
	}
	
	#bookingTabBtn {
		color: #0000ff !important;
		border-color: #698490 !important;
		background-color: transparent !important;
	}
	
	#myBookingTabBtn {
		color: #0000ff !important;
		border-color: #698490 !important;
		background-color: transparent !important;
		width: 125px;
	}
	
	#myDateTabBtn {
		margin-top: 0px; 
		right: 310px !important; 
		left: auto !important;
		width: 115px;
	}
	
	#myDateText_dd_sl_mm_sl_yyyy {
		border: 0px; 
		padding: 0px;
		width: 80px !important;
		font-weight: bold;
		font-size: 12px;
		color: #0000ff !important;
		text-align: center;
	}
	
	#myDateText_dd_sl_mm_sl_yyyy::-moz-focus, #myDateText_dd_sl_mm_sl_yyyy::-moz-selection { 
		border: 0px; 
	}
	
	#myDateText_dd_sl_mm_sl_yyyy::-moz-placeholder { /* Firefox 19+ */
		font-size: 12px;
		font-family: arial;
	}
	
	#myDateText_dd_sl_mm_sl_yyyy:-ms-input-placeholder { /* IE 10+ */
		font-size: 12px;
		font-family: arial;
	}
	
	#filterTabBtn {
		margin-top: 0px; 
		right: 220px !important; 
		left: auto !important;
	}
	
	.dhx_wrap_section-noborder {
		border: none !important;
	}
	
	.event-data-form {
		height: auto;
		display: none; 
		position: fixed;
		margin-top: 50px;
		margin-left: auto;
		margin-right: auto;
	}
	
	.event-data-form-title {
		cursor: pointer; 
		padding-left: 10px; 
		padding-right: 10px;
	}
	
	.event-header-left, .event-header-right {
		font-weight: normal;
	}
	
	.event-header-left {
		float: left;
	}
	
	.event-header-right {
		float: right;
	}
	
	.event-data-form-body {
		height: auto;
	}
	
	.event-data-form-label {
		width: 200px !important;
	}
	
	.event-data-form-value {
		height: 20px; 
		padding: 5px 0px 0px 10px; 
		font-size: 13px;
	}
	
	.event-data-form-btn {
		border: 1px solid silver; 
		padding: 6px 5px 5px 5px; 
		margin:0px 5px 0px 5px; 
		border-radius: 6px;
	}
	
	.event-data-form-btn:hover {
    	background-color: skyblue;
	}
	
	.event-data-form-btn-icon {
		width: 16px; 
		height: 16px;
	}
	
	.event-data-form-btn-section {
		height: 25px;
		width: 80%;
		margin: 5px auto 5px auto; 
		padding-top: 10px; 
		text-align: center;
	}
	
	.event-data-form-input-over-limit-check-out {
		width: 50px; 
		padding: 0px !important; 
		margin: -10px 0px 0px 4px; 
		text-align: center; 
		border-radius: 6px;
		color: black;
	}
		
	#event-data-form-input-over-limit-check-out_hh_cl_mm {
		color: black;
	}
	
	#event-data-form-input-over-limit-check-out_hh_cl_mm::-moz-placeholder { /* Firefox 19+ */
		font-size: 12px;
	}
	
	#event-data-form-input-over-limit-check-out_hh_cl_mm:-ms-input-placeholder { /* IE 10+ */
		font-size: 12px;
	}

</style>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/scheduler/dhtmlxscheduler.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/scheduler/dhtmlxscheduler_minical.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/scheduler/dhtmlxscheduler_multiselect.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/scheduler/dhtmlxscheduler_recurring.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/scheduler/dhtmlxscheduler_tooltip.js"></script>
<script type="text/javascript">
	var roomIdSelected = '';
	var eventIdFocus = '<s:property value="dashboardData.eventIdFocus"/>';
	var currentDay = '<s:property value="dashboardData.currentDay"/>'; // DD/MM/YYYY
	var currentMode = '<s:property value="dashboardData.currentMode"/>'; // Day, Week, Month
	var currentStep = ''; // Previous, Next
	var loadEventSchedulerInterval = '';
	const LOAD_EVENT_WAIT = 60000;
	function sf() {
		initSelectRoom();	
		configScheduler();
		configTooltip();
		configEventBox();
		initInputMyDateText();
		loadEventScheduler();
		startLoadEventSchedulerInterval();
		
		if (eventIdFocus != '') {
			var tempEventIdFocus = eventIdFocus;
			eventIdFocus = '';
			setTimeout( function() {
				focusEventData(tempEventIdFocus);
			}, 1000);
			
		}
	}
	
	function initInputMyDateText() {
		jQuery("#myDateText").each(function() {
			jQuery(this).addClass('dateformat').input_dateformat({
				
			});
		});
	}
	
	function startLoadEventSchedulerInterval() {
		loadEventSchedulerInterval = setInterval(function(){ 
			loadEventScheduler();
		}, LOAD_EVENT_WAIT);		
	}
	
	function stopLoadEventSchedulerInterval() {
		clearInterval(loadEventSchedulerInterval);
	}
	
	function initSelectRoom() {
		
		// ส่วนแสดงสถานะห้องทั้งหมด
		jQuery.ajax({
			type: "POST"
			, url: "<s:url value='/jsp/calendar/dashboard/ajaxSearchSelectRoomDashboard.action'/>"
			, async: true // ไม่รอให้ทำงานจบ
			, success: function(data){
				var htmlEmptyRoom = '<a href="javascript:void(0)" class="side-nav-room-select-all" onclick="jQuery(\'#selectallroom\').click();"><input type="checkbox" id="selectallroom" name="selectallroom" checked="checked" onclick="checkboxToggle(\'selectroom\', this.checked);generateSelectedRoomId(\'mySideNavRoom\');">&nbsp;<s:text name="bk.select_all_room"/></a>';
				htmlEmptyRoom += '<a href="javascript:void(0)" class="side-nav-room-close-btn" onclick="toggleSideNavRoom()">&times;</a>'
				if (data.messageAjax.messageType == 'E') {
					showNotifyMessageError(data.messageAjax.message, data.messageAjax.messageDetail);
				} else {
					jQuery.each(data.listSelectRoom, function(i) {
						htmlEmptyRoom += '<a href="javascript:void(0)" onclick="selectedRoomId(this);"><input type="checkbox" checked="checked" name="selectroom" roomId=' + this.id + '>&nbsp;' + this.name;
						if (this.active.desc != null) {
							htmlEmptyRoom += ' (' + this.active.desc + ')';	
						}
						htmlEmptyRoom += '</a>';
						
						if (roomIdSelected != '') {
							roomIdSelected += ","	
						}
						roomIdSelected += this.id;	
					});
				} 
				
				htmlEmptyRoom += '<a href="javascript:void(0)" class="side-nav-room-search" onclick="loadEventScheduler();">&nbsp;<s:text name="search"/></a>';
				
				jQuery("#mySideNavRoom").html(htmlEmptyRoom);
			}
		});
	}
	
	function selectedRoomId(element) {
		
		// ส่วน Select ตามห้อง
		var $elementCheckbox = jQuery(element).children("input[type='checkbox']");
		$elementCheckbox.prop('checked', !$elementCheckbox.prop('checked'));
		
		// ส่วน Select all เมื่อเลือกครบให้ Checked ด้วย ถ้าเลือกไม่ครบให้ Unchecked all ด้วย
		var parentId = jQuery(element).parent().attr('id');
		var selectAll = jQuery("#" + parentId + " input[type='checkbox']:first");
		var allCheckbox = jQuery("#" + parentId + " input[type='checkbox']").not(selectAll).length;
		var allChecked = jQuery("#" + parentId + " input[type='checkbox']:checked").not(selectAll).length;
		if (allCheckbox == allChecked) {
			jQuery("#" + parentId + " input[type='checkbox']:first").prop('checked', 'checked');
		} else {
			jQuery("#" + parentId + " input[type='checkbox']:first").prop('checked', '');
		}
		
		generateSelectedRoomId(parentId);
	}
	
	function generateSelectedRoomId(parentId) {
		// สร้าง id room ที่เลือก
		roomIdSelected = '';
		var selectAll = jQuery("#" + parentId + " input[type='checkbox']:first");
		jQuery("#" + parentId + " input[type='checkbox']:checked").not(selectAll).each(function(index) {
			if (index > 0) {
				roomIdSelected += ","	
			}
			roomIdSelected += jQuery(this).attr('roomId');	
		});
		// console.info(roomIdSelected);
	}
	
	/* Open and Close/Hide the sidenav */
	function toggleSideNavRoom() {
		if ((document.getElementById("mySideNavRoom").style.width == '') 
				|| (document.getElementById("mySideNavRoom").style.width == '0px')) {
			document.getElementById("mySideNavRoom").style.width = "250px";
			setTimeout(function() {
				jQuery("#filterTabBtn").addClass('active');	
			}, 75);
		} else {
			document.getElementById("mySideNavRoom").style.width = "0";
			jQuery("#filterTabBtn").removeClass('active');
		}
	}
	
	function selectMode(mode) {
		currentMode = mode;
		currentStep = '';
		loadEventScheduler();
	}
	
	function loadMoreEventScheduler(step) {
		currentStep = step;
		loadEventScheduler();
	}
	
	function loadEventScheduler(){
		// alert(currentDay + ' , ' + currentMode + ' , ' + currentStep);
		// ส่วนแสดง event บนปฏิทิน
		jQuery.ajax({
			type: "POST"
			, url: "<s:url value='/jsp/calendar/dashboard/ajaxSearchEventDataDashboard.action'/>"
			, async: true // ไม่รอให้ทำงานจบ
			, data: {'dashboardData.roomIdSelected': roomIdSelected, 'dashboardData.currentMode': currentMode, 'dashboardData.currentDay': currentDay, 'dashboardData.currentStep': currentStep}
			, success: function(data){
				 drawEventAfterResponse(data);
			}
		});
	}
	
	function configScheduler(){
		scheduler.config.xml_date="%d/%m/%Y %H:%i";
		scheduler.config.time_step = 30;
		scheduler.config.hour_size_px = (44 * 2); // (44*3) (40):(50)
		scheduler.config.multi_day = true;
		scheduler.config.first_hour = 6;
		scheduler.config.limit_time_select = false;
		scheduler.config.details_on_dblclick = false;
		scheduler.config.details_on_create = false;
		
		//ตั้งค่า style ตาม css 
		scheduler.templates.event_class = function(start, end, event) {
			var css = "";

			if (event.skin) // if event has subject property then special class should be assigned
				css += "event_" + event.skin;

			if (event.id == scheduler.getState().select_id) {
				css += " selected";
			}
			return css; // default return

		};
		
		scheduler.renderEvent = function(container, event, width, height, header_content, body_content) {
			var container_width = container.style.width; // e.g. "105px"
			var htmlHeader = '<div class="dhx_event_move dhx_title" data-owner="' + event.dataOwner + '">';
			htmlHeader += header_content;
	        htmlHeader += "</div>";
	        
	        var htmlBody = '';
	        var bodyHeight = container.style.height.replace("px", "");
	        bodyHeight = bodyHeight - 44 - 7;
	        if (bodyHeight < 0) {
	        	bodyHeight = 0;
			}
	        htmlBody += '<div class="dhx_body" style="width:' + (width - 14) + 'px; height:' + bodyHeight + 'px;">';
			htmlBody += body_content;
			htmlBody += "</div>";
			
			container.innerHTML = htmlHeader + htmlBody;
			return true; // required, true - we've created custom form; false - display default one instead
		};
		
		scheduler.showLightbox = function(id) {
			actionShowLightbox(id);
		};
		

		// adds a handler for the 'onclick' event
		jQuery("#prevBtn").click(function(e){
			loadMoreEventScheduler('previous');
		});
		
		jQuery("#nextBtn").click(function(e){
			loadMoreEventScheduler('next');
		});
		
		jQuery("#dayTabBtn").click(function(e){
			selectMode('day');
		});
		
		jQuery("#weekTabBtn").click(function(e){
			selectMode('week');
		});
		
		jQuery("#monthTabBtn").click(function(e){
			selectMode('month');
		});
		
		jQuery("#todayTabBtn").click(function(e){
			currentDay = '';
			loadMoreEventScheduler('');
		});
		
		jQuery("#bookingTabBtn").click(function(e){
			window.location = "<s:url value='/jsp/booking/initRoomBooking.action' />";
		});
		
		jQuery("#myBookingTabBtn").click(function(e){
			filterForMyBookingTabBtn();
		});
		
		jQuery("#myBookingCheckBox").click(function(e){
			// filterForMyBookingCheckBox();
			return false;
		});
		
		jQuery("#filterTabBtn").click(function(e){
			toggleSideNavRoom();
		});
		
		focusDateInCalendar();
	}
	
	function actionShowLightbox(id) {
		drawActionContent(id)
		drowActionButton(id);
		scheduler.startLightbox(id, document.getElementById("eventDataForm"));
		// var eventDataFormWidth = jQuery("#eventDataForm").width();
		// var windowWidth = jQuery( window ).width();
		// alert(eventDataFormWidth + ' > ' + windowWidth);
		// jQuery("#eventDataForm").css('marginLeft', 'auto');
		// jQuery("#eventDataForm").css('marginRight', 'auto');
		// var space = jQuery( window ).width()
		jQuery("#eventDataForm").css('top', 50);
	    jQuery("#eventDataForm").draggable();
	    jQuery("#event-data-form-btn-close").click(function(e){
	    	 scheduler.endLightbox(false, document.getElementById("eventDataForm"));
		});
	}
	
	function focusDateInCalendar() {
		scheduler.init('scheduler_here', new Date(currentDay.substring(6, 10), parseInt(currentDay.substring(3, 5), 10) - 1, currentDay.substring(0, 2)), currentMode);
		scheduler.config.scroll_hour = (new Date).getHours();
	}
	
	function configTooltip(){
		//config tooltip
		dhtmlXTooltip.config.className = 'dhtmlXTooltip tooltip'; 
		dhtmlXTooltip.config.timeout_to_display = 50; 
		dhtmlXTooltip.config.delta_x = 15; 
		dhtmlXTooltip.config.delta_y = -20;
		scheduler.templates.tooltip_text = function(start, end, event) {
			var htmltooltip = "";
			htmltooltip += "<b><s:text name='bk.room'/>:</b> " + event.roomSettingData.name + "&nbsp;&nbsp;&nbsp;";
			htmltooltip += "<b><s:text name='bk.status'/>:</b> " + event.roomBooking.bookingStatusName + "<br/>";
			htmltooltip += "<b><s:text name='bk.subject'/>:</b> " + event.roomBooking.subject + "<br/>";
			htmltooltip += "<b><s:text name='bk.date'/>:</b> " + event.roomBooking.startDate + "&nbsp;&nbsp;&nbsp;";
			htmltooltip += "<b><s:text name='bk.time'/>:</b> " + event.roomBooking.startTime + " - " + event.roomBooking.endTime + "<br/>";
			if (event.roomBooking.attendees == '') {
				htmltooltip += "<b><s:text name='bk.totalAttendees' />:</b> -<br/>";
			} else {
				htmltooltip += "<b><s:text name='bk.totalAttendees' />:</b> " + event.roomBooking.attendees + "<br/>";	
			}
			
			if (event.roomBooking.detail == '') {
				htmltooltip += "<b>รายละเอียดเพิ่มเติม:</b> -<br/>";
			} else {
				htmltooltip += "<b>รายละเอียดเพิ่มเติม:</b> " + event.roomBooking.detail + "<br/>";
			}
			htmltooltip += "<b><s:text name='bk.userUse'/>:</b> " + event.roomBooking.userBookName + "<br/>";
			htmltooltip += "<b><s:text name='bk.userBooking'/>:</b> " + event.roomBooking.createUserName + "<br/>";
			
			return htmltooltip;
		};
	}
	
	function configEventBox(){
		
		//sets HeaderEvent <img src='/workshopui/images/icon/i_apply.png'/> pic <b style='color:#F08080'>
		scheduler.attachEvent("onTemplatesReady", drowTitleEvent);
		scheduler.attachEvent("onBeforeDrag", function(){
		    return false;
		});
		
		scheduler.attachEvent("onDblClick", function (id, e){
			scheduler.showLightbox(id);
			return false;
		});
		
		scheduler.locale.labels.icon_approve = "Approve";
		scheduler.locale.labels.icon_check_in = "Check In";
		scheduler.locale.labels.icon_check_out = "Check Out";
		scheduler.locale.labels.icon_over_limit_check_out = "Over Limit Check Out";
		scheduler.locale.labels.icon_cancel = "Cancel";
		
		scheduler.attachEvent("onClick", function(id){
		    drowActionIcon(scheduler.getEvent(id));
		    return true;
		});

		scheduler._click.buttons.approve = function(id){
			confirmDialog(id, 'A');
		};
		
		scheduler._click.buttons.check_in = function(id){
			confirmDialog(id, 'CI');
		};
		
		scheduler._click.buttons.check_out = function(id){
			confirmDialog(id, 'CO');
		};
		
		scheduler._click.buttons.over_limit_check_out = function(id){
			actionShowLightbox(id);
		};
		
		scheduler._click.buttons.cancel = function(id){
			confirmDialog(id, 'C');
		};
	}
	
	function drawActionContent(id) {
		var event = scheduler.getEvent(id);
		
		var dataRoomName = "";
		if (event.dataOwner) {
			dataRoomName += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/favourites.png' class='dhx_title_status'/>";
		}
		dataRoomName += event.roomSettingData.name;
		
		jQuery("#event-data-form-room").html(dataRoomName);
		jQuery("#event-data-form-status").html(event.roomBooking.bookingStatusName);
		jQuery("#event-data-form-subject").html(event.roomBooking.subject);
		jQuery("#event-data-form-start-date").html(event.roomBooking.startDate);
		jQuery("#event-data-form-start-end-time").html(event.roomBooking.startTime + " - " + event.roomBooking.endTime);
		if (event.roomBooking.attendees == '') {
			jQuery("#event-data-form-total-attendees").html("-");
		} else {
			jQuery("#event-data-form-total-attendees").html(event.roomBooking.attendees);	
		}
		if (event.roomBooking.detail == '') {
			jQuery("#event-data-form-more-detail").html("-");
		} else {
			jQuery("#event-data-form-more-detail").html(event.roomBooking.detail);
		}
		jQuery("#event-data-form-user-book-name").html(event.roomBooking.userBookName);
		jQuery("#event-data-form-create-user-name").html(event.roomBooking.createUserName);
	}
	
	function drowActionButton(id) {
		var event = scheduler.getEvent(id);
		var isAdmin = "<s:property value='ATH.edit'/>";
		var isDataOwner = event.dataOwner;
		var isDataOverLimitCheckOut = event.dataOverLimitCheckOut;
		
		// alert(event.roomBooking.bookingStatusCode + ', ' + isDataOwner);
		jQuery("#event-data-form-btn-section").empty();
	    if (event.roomBooking.bookingStatusCode == 'W') {
	    	// Wait > Approve / Cancel
	    	if (isAdmin == "true") {
	    		jQuery("#event-data-form-btn-section").append(createActionButtonApprove());
	    		jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
	    		jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    	} else {
	    		// for owner
	    		if (isDataOwner == true) {
		    		jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
		    		jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    		} else {
	    			// Nothing
	    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    		}
	    	}

	    } else if (event.roomBooking.bookingStatusCode == 'U') {
        	// Unstable > Approve / Cancel
        	if (isAdmin == "true") {
        		jQuery("#event-data-form-btn-section").append(createActionButtonApprove());
	    		jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
	    		jQuery("#event-data-form-btn-section").append(createActionButtonClose());
   	    	} else {
   	    		// for owner
   	    		if (isDataOwner == true) {
   	    			jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
   	    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    		} else {
	    			// Nothing    	
	    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    		}
   	    	}	
        	 
		} else if (event.roomBooking.bookingStatusCode == 'CI') {
        	// Check In > Check Out / Cancel (admin)
	    	if (isAdmin == "true") {
	    		if (isDataOverLimitCheckOut) {
	    			jQuery("#event-data-form-btn-section").append(createActionInputOverLimitCheckOut());
	    			initInputOverLimitCheckOut();
	    			
	    			jQuery("#event-data-form-btn-section").append(createActionButtonOverLimitCheckOut());
	    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    		} else {
	    			jQuery("#event-data-form-btn-section").append(createActionButtonCheckOut());
	    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    		}
	    	} else {
	    		// for owner
	    		if (isDataOwner == true) {
	    			if (isDataOverLimitCheckOut) {
	    				jQuery("#event-data-form-btn-section").append(createActionInputOverLimitCheckOut());
	    				initInputOverLimitCheckOut();
	    				
	    				jQuery("#event-data-form-btn-section").append(createActionButtonOverLimitCheckOut());	
	    				jQuery("#event-data-form-btn-section").append(createActionButtonClose());
		    		} else {
		    			jQuery("#event-data-form-btn-section").append(createActionButtonCheckOut());
		    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
		    		}
	    		} else {
	    			// Nothing  		
	    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    		}
	    	}
        	
		} else if (event.roomBooking.bookingStatusCode == 'CO') {
        	// Check Out > Nothing
			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
        	
		} else if (event.roomBooking.bookingStatusCode == 'A') {
			// Approve > Check In / Check Out / Cancel (admin)
			if (isAdmin == "true") {
				if (isDataOverLimitCheckOut) {
					jQuery("#event-data-form-btn-section").append(createActionButtonCheckIn());
					jQuery("#event-data-form-btn-section").append(createActionInputOverLimitCheckOut());
					initInputOverLimitCheckOut();
					
	    			jQuery("#event-data-form-btn-section").append(createActionButtonOverLimitCheckOut());
	    			jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
	    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    		} else {
	    			jQuery("#event-data-form-btn-section").append(createActionButtonCheckIn());
	    			jQuery("#event-data-form-btn-section").append(createActionButtonCheckOut());
	    			jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
	    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    		}
	    	} else {
	    		// for owner
	    		if (isDataOwner == true) {
	    			if (isDataOverLimitCheckOut) {
	    				jQuery("#event-data-form-btn-section").append(createActionButtonCheckIn());
	    				jQuery("#event-data-form-btn-section").append(createActionInputOverLimitCheckOut());
	    				initInputOverLimitCheckOut();
	    				
		    			jQuery("#event-data-form-btn-section").append(createActionButtonOverLimitCheckOut());
		    			jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
		    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
		    		} else {
		    			jQuery("#event-data-form-btn-section").append(createActionButtonCheckIn());
		    			jQuery("#event-data-form-btn-section").append(createActionButtonCheckOut());
		    			jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
		    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
		    		}
	    		} else {
	    			// Nothing
	    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
	    		}
	    	}
		}
	    
	    
	    // ผุก event กับปุ่ม
	    jQuery("#event-data-form-btn-approve").click(function(e){
	    	confirmDialog(id, 'A');
		});

	    jQuery("#event-data-form-btn-check-in").click(function(e){
	    	confirmDialog(id, 'CI');
		});
	    
	    jQuery("#event-data-form-btn-check-out").click(function(e){
	    	confirmDialog(id, 'CO');
		});

	    jQuery("#event-data-form-btn-over-limit-check-out").click(function(e){
	    	
	    	// event-data-form-input-over-limit-check-out
	    	// event-data-form-input-over-limit-check-out_hh_cl_mm
	    	// input_timeformat_hh_cl_mm
	    	var element = document.getElementById("event-data-form-input-over-limit-check-out_hh_cl_mm");
	    	var status = inputValidateTimeFormatValueName(element);
			if (status == 'ok') {
				var event = scheduler.getEvent(id);
				// console.info(event);
				// console.info(event.roomBooking);
				// console.info(event.roomBooking.endTime);
				if (element.value <= event.roomBooking.startTime) {
					jQuery("#event-data-form-input-over-limit-check-out_hh_cl_mm")
					.removeClass("input_timeformat_hh_cl_mm")
					.removeClass("requireTimeformat_hh_cl_mm_select")
					.addClass("requireTimeformat_hh_cl_mm_select");
				
					var msg = "เวลาคืนน้อยกว่าเวลาเริ่มต้นใช้งาน<br/>";
					showNotifyMessageValidate(msg);
					element.focus();
				
					return false;
				}
			} else {
				jQuery("#event-data-form-input-over-limit-check-out_hh_cl_mm")
					.removeClass("input_timeformat_hh_cl_mm")
					.removeClass("requireTimeformat_hh_cl_mm_select")
					.addClass("requireTimeformat_hh_cl_mm_select");
				
				var msg = validateMessage.CODE_10003.replace("xxx", '<s:text name="bk.inputOverLimitCheckOut"/>') + "<br/>";
				showNotifyMessageValidate(msg);
				element.focus();
				return false;
			}
	    	confirmDialog(id, 'OLCO');
		});
	    
	    jQuery("#event-data-form-btn-cancel").click(function(e){
	    	confirmDialog(id, 'C');
		});
	    
	    jQuery("#event-data-form-btn-close-2").click(function(e){
	    	jQuery("#event-data-form-btn-close").click();
		});
		
	}
	
	function createActionButtonApprove() {
		var html = '';
		html += '<span class="event-data-form-btn">';
		html += '<a id="event-data-form-btn-approve" href="javascript:void(0);">';
		html += '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png" class="event-data-form-btn-icon" />';
		html += '&nbsp;<s:text name="bk.approve"/>&nbsp;';
		html += '</a>';
		html += '</span>';
		return html;
	}
	
	function createActionButtonCheckIn() {
		var html = '';
		html += '<span class="event-data-form-btn">';
		html += '<a id="event-data-form-btn-check-in" href="javascript:void(0);">';
		html += '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png" class="event-data-form-btn-icon" />';
		html += '&nbsp;<s:text name="bk.checkIn"/>&nbsp;';
		html += '</a>';
		html += '</span>';
		return html;
	}
	
	function createActionButtonCheckOut() {
		var html = '';
		html += '<span class="event-data-form-btn">';
		html += '<a id="event-data-form-btn-check-out" href="javascript:void(0);">';
		html += '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png" class="event-data-form-btn-icon" />';
		html += '&nbsp;<s:text name="bk.checkOut"/>&nbsp;';
		html += '</a>';
		html += '</span>';
		return html;
	}
	
	function createActionInputOverLimitCheckOut() {
		var html = '';
		html += '&nbsp;&nbsp;&nbsp;<b style="color: #747473;"><s:text name="bk.inputOverLimitCheckOut"/>:</b>';
		html += '<input id="event-data-form-input-over-limit-check-out" type="text" class="event-data-form-input-over-limit-check-out" validName="<s:text name="bk.inputOverLimitCheckOut"/>"/>';
		return html;
	}
	
	function initInputOverLimitCheckOut() {
		jQuery("#event-data-form-input-over-limit-check-out").each(function() {
			jQuery(this).addClass('timepicker').input_timeformat({
				timeformat :  "hh_cl_mm"
			});
		});
	}
	
	function createActionButtonOverLimitCheckOut() {
		var html = '';
		html += '<span class="event-data-form-btn">';
		html += '<a id="event-data-form-btn-over-limit-check-out" href="javascript:void(0);">';
		html += '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/over_limit_check_out.png" class="event-data-form-btn-icon" />';
		html += '&nbsp;<s:text name="bk.overCheckOut"/>&nbsp;';
		html += '</a>';
		html += '</span>';
		return html;
	}
	
	function createActionButtonCancel() {
		var html = '';
		html += '<span class="event-data-form-btn">';
		html += '<a id="event-data-form-btn-cancel" href="javascript:void(0);">';
		html += '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png" class="event-data-form-btn-icon" />';
		html += '&nbsp;<s:text name="bk.cancel"/>&nbsp;';
		html += '</a>';
		html += '</span>';
		return html;
	}
	
	function createActionButtonClose() {
		var html = '';
		html += '<span class="event-data-form-btn">';
		html += '<a id="event-data-form-btn-close-2" href="javascript:void(0);">';
		html += '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/close.png" class="event-data-form-btn-icon" />';
		html += '&nbsp;<s:text name="bk.close"/>&nbsp;';
		html += '</a>';
		html += '</span>';
		return html;
	}
	
	function drowActionIcon(event) {
		var isAdmin = "<s:property value='ATH.edit'/>";
		var isDataOwner = event.dataOwner;
		var isDataOverLimitCheckOut = event.dataOverLimitCheckOut; 
	    if (event.roomBooking.bookingStatusCode == 'W') {
	    	// Wait > Approve / Cancel
	    	if (isAdmin == "true") {
	    		scheduler.config.icons_select = ["icon_approve", "icon_cancel"];
	    	} else {
	    		// for owner
	    		if (isDataOwner == true) {
	    			scheduler.config.icons_select = ["icon_cancel"];
	    		} else {
	    			scheduler.config.icons_select = [];	    			
	    		}
	    	}

	    } else if (event.roomBooking.bookingStatusCode == 'U') {
        	// Unstable > Approve / Cancel
        	if (isAdmin == "true") {
        		scheduler.config.icons_select = ["icon_approve", "icon_cancel"];
   	    	} else {
   	    		// for owner
   	    		if (isDataOwner == true) {
	    			scheduler.config.icons_select = ["icon_cancel"];
	    		} else {
	    			scheduler.config.icons_select = [];	    			
	    		}
   	    	}	
        	 
		} else if (event.roomBooking.bookingStatusCode == 'CI') {
        	// Check In > Check Out / Cancel (admin)
	    	if (isAdmin == "true") {
	    		if (isDataOverLimitCheckOut) {
	    			scheduler.config.icons_select = ["icon_over_limit_check_out"];	    			
	    		} else {
	    			scheduler.config.icons_select = ["icon_check_out"];	
	    		}
	    	} else {
	    		// for owner
	    		if (isDataOwner == true) {
	    			if (isDataOverLimitCheckOut) {
		    			scheduler.config.icons_select = ["icon_over_limit_check_out"];	    			
		    		} else {
		    			scheduler.config.icons_select = ["icon_check_out"];	
		    		}
	    		} else {
	    			scheduler.config.icons_select = [];	    			
	    		}
	    	}
        	
		} else if (event.roomBooking.bookingStatusCode == 'CO') {
        	// Check Out > Nothing
        	scheduler.config.icons_select = [];
        	
		} else if (event.roomBooking.bookingStatusCode == 'A') {
			// Approve > Check In / Check Out / Cancel (admin)
			if (isAdmin == "true") {
				if (isDataOverLimitCheckOut) {
	    			scheduler.config.icons_select = ["icon_check_in", "icon_over_limit_check_out", "icon_cancel"];
	    		} else {
	    			scheduler.config.icons_select = ["icon_check_in", "icon_check_out", "icon_cancel"];	
	    		}
	    	} else {
	    		// for owner
	    		if (isDataOwner == true) {
	    			if (isDataOverLimitCheckOut) {
		    			scheduler.config.icons_select = ["icon_check_in", "icon_over_limit_check_out", "icon_cancel"];
		    		} else {
		    			scheduler.config.icons_select = ["icon_check_in", "icon_check_out", "icon_cancel"];	
		    		}
	    		} else {
	    			scheduler.config.icons_select = [];	    			
	    		}
	    	}
		}
	}
	
	function drowTitleEvent(){
	    scheduler.templates.event_header = function(start, end, event){
	    	
	    	var htmlHeader = "<p class='event-header'>";
	    	if (event.dataOwner) {
	    		htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/favourites.png' class='dhx_title_status'/>";
	    	}
	    	
	    	htmlHeader += event.roomSettingData.name;
	    	htmlHeader += "<br>";
	    	
	    	if (event.roomBooking.bookingStatusCode == 'W') {
	    		htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve_dis.png' class='dhx_title_status dhx_title_status_approve'/>";
	    		htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in_dis.png' class='dhx_title_status dhx_title_status_check_in'/>";
	    		htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out_dis.png' class='dhx_title_status dhx_title_status_check_out'/>";
	        	
	        } else if (event.roomBooking.bookingStatusCode == 'U') {
	        	htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/unstable.png' class='dhx_title_status dhx_title_status_unstable'/>";
	        	 
			} else if (event.roomBooking.bookingStatusCode == 'CI') {
				htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png' class='dhx_title_status dhx_title_status_approve'/>";
				htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png' class='dhx_title_status dhx_title_status_check_in'/>";
				htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out_dis.png' class='dhx_title_status dhx_title_status_check_out'/>";
	        	
			} else if (event.roomBooking.bookingStatusCode == 'CO') {
				htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png' class='dhx_title_status dhx_title_status_approve'/>";
				htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png' class='dhx_title_status dhx_title_status_check_in'/>";
				htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png' class='dhx_title_status dhx_title_status_check_out'/>";
	        	
			} else if (event.roomBooking.bookingStatusCode == 'A') {
				htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png' class='dhx_title_status dhx_title_status_approve'/>";
				htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in_dis.png' class='dhx_title_status dhx_title_status_check_in'/>";
				htmlHeader += "<img src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out_dis.png' class='dhx_title_status dhx_title_status_check_out'/>";
	        }
	    	
	        htmlHeader += "</p>";
	        return htmlHeader;
		};
		
		scheduler.templates.event_text = function(start, end, event){
			var htmlBody = "<p class='event-subject'>";
			htmlBody += event.roomBooking.subject;
			htmlBody += "</p>";
			htmlBody += "<p class='event-detail'>";
			htmlBody += event.roomBooking.detail;
			htmlBody += "</p>";
			htmlBody += "<h4 class='event-bookby'>";
			htmlBody += "<b><s:text name='bk.userUse'/>:</b> " + event.roomBooking.userBookName + "<br/>";
			htmlBody += "<b><s:text name='bk.userBooking'/>:</b> " + event.roomBooking.createUserName + "<br/>";
			htmlBody += "</h4>";
			return htmlBody;
			
	    }
	}
	
	function confirmDialog(id, check) {
		stopLoadEventSchedulerInterval();
		stopLoadNotificationMessageInterval();
		clearNotifyMessage();
		
		var timeOverLimitCheckOut = jQuery("#event-data-form-input-over-limit-check-out_hh_cl_mm").val();
		var event = scheduler.getEvent(id);
		var eventIdSelected = id;
		var message;
		var actionURL;
		if (check == 'A') {
			message = "Event will be approved, Are you sure?";
			actionURL = "<s:url value='/jsp/calendar/dashboard/ajaxApproveEventDataDashboard.action' />";
		} else if (check == 'CI'){
			message = "Check In, Are you sure?";
			actionURL = "<s:url value='/jsp/calendar/dashboard/ajaxCheckInDataDashboard.action' />";
		} else if (check == 'CO'){
			message = "Check Out, Are you sure?";
			actionURL = "<s:url value='/jsp/calendar/dashboard/ajaxCheckOutDataDashboard.action' />";
		} else if (check == 'OLCO'){
			message = "Over Limit Check Out, Are you sure?";
			actionURL = "<s:url value='/jsp/calendar/dashboard/ajaxOverLimitCheckOutDataDashboard.action' />";
		} else if (check == 'C'){
			message = "Cancel, Are you sure?";
			actionURL = "<s:url value='/jsp/calendar/dashboard/ajaxCancelDataDashboard.action' />";
		}
		
		dhtmlx.modalbox({
			text: message,
			width: "300px",
			position: "middle",
			buttons:["Yes", "Cancel"],
			callback: function(index) {
				if (index == 0) {
					jQuery.ajax({
					   type: "POST"
					   , url: actionURL
					   , data: {'dashboardData.eventIdSelected': eventIdSelected, 'dashboardData.roomIdSelected': roomIdSelected, 'dashboardData.currentMode': currentMode, 'dashboardData.currentDay': currentDay, 'dashboardData.currentStep': currentStep, 'dashboardData.timeOverLimitCheckOut': timeOverLimitCheckOut}
					   , async: true // รอจนกว่าทำงานจบ
					   , success: function(data){
						    jQuery("#event-data-form-btn-close").click();
							drawEventAfterResponse(data);
							startLoadEventSchedulerInterval();
							loadNotificationMessage();
							startLoadNotificationMessageInterval();
					   }	   
					});
				}
			}
		});
	}
	
	
	function drawEventAfterResponse(data) {
		if (data.messageAjax.messageType == 'O') {
			showNotifyMessageWarning(data.messageAjax.message);
		} else if (data.messageAjax.messageType == 'E') {
			showNotifyMessageError(data.messageAjax.message, data.messageAjax.messageDetail);
		}
		
		var jsonDataArray = [] ;
		scheduler.clearAll();
		
		currentStep = '';
		currentDay = data.dashboardData.currentDay;
		setInputMyDateText();

		jQuery.each(data.listDashboardData, function(i) {
			var showMe = true;
			var showMyBooking = jQuery("#myBookingCheckBox").prop('checked');
			var tmpSkin = this.roomSettingData.color;
			if (this.dataOwner) {
				// tmpSkin = "Owner"; 
			}
			
			if (showMyBooking) {
				if (this.dataOwner == false) {
					showMe = false;
				}
			}
			
			if (showMe) {
				jsonDataArray.push({
					id : this.id,
			    	start_date : this.roomBooking.startDate + ' ' + this.roomBooking.startTime,
					end_date : this.roomBooking.endDate + ' ' + this.roomBooking.endTime,
					text : this.roomBooking.subject,
					skin : tmpSkin,
					roomBooking: this.roomBooking,
					roomSettingData: this.roomSettingData,
					dataOwner: this.dataOwner,
					dataOverLimitCheckOut: this.dataOverLimitCheckOut
	  			});
			} 
		});

		focusDateInCalendar();
		scheduler.parse(jsonDataArray, "json");
	}
		
	function filterForMyBookingTabBtn() {
		var myBooking = jQuery("#myBookingTabBtn").prop('data-checked');
		if ((myBooking == undefined) 
				|| (myBooking == false)) {
			jQuery("#myBookingTabBtn").prop('data-checked', true);
			jQuery("#myBookingCheckBox").prop('data-checked', true);
		} else {
			jQuery("#myBookingTabBtn").prop('data-checked', false);
			jQuery("#myBookingCheckBox").prop('data-checked', false);
		}

		filterForMyBookingCheckBox();
		loadEventScheduler();
	}
	
	function filterForMyBookingCheckBox() {
		var myBooking = jQuery("#myBookingCheckBox").prop('data-checked');
		jQuery("#myBookingCheckBox").prop('checked', myBooking);
		
	}
	
	function gotoCurrentDay() {
		var dateValue = jQuery("#myDateText").input_dateformat('dateValue');
		var dateForDB = dateValue.dateForDB
		currentDay = dateForDB;
		loadEventScheduler();	
	}
	
	function setInputMyDateText() {
		jQuery("#myDateText").input_dateformat('dateValue', currentDay);
	}
	
	function startCalendar01(isContiune) {
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
                {
        		    intro: "ยินดีต้อนรับสู่ระบบแสดงข้อมูลการจองในรูปปฏิทิน ถ้าพร้อมแล้วกดปุ่ม<em class='heightlight'>ถัดไป</em>ได้เลย"
                }
                , {
                	element: jQuery('.dhx_cal_navline')[0],
        		    intro: "นี้คือส่วนควบคุมการทำงานของหน้าปฏิทิน"
                }
				, {
                	element: document.getElementById('dayTabBtn'),
        		    intro: "ปุ่มเปลื่ยนการแสดงผลปฏิทินเป็น<em class='heightlight'>แบบรายวัน</em>"
                }
				, {
					element: jQuery('.dhx_cal_data')[0],
        		    intro: "นี้คือการแสดงผล<em class='heightlight'>แบบรายวัน</em>",
        		    scrollTo: "tooltip"
                }
				, {
                	element: document.getElementById('weekTabBtn'),
        		    intro: "ปุ่มเปลื่ยนการแสดงผลปฏิทินเป็น<em class='heightlight'>แบบรายสัปดาห์</em>"
                }
				, {
					element: jQuery('.dhx_cal_data')[0],
        		    intro: "นี้คือการแสดงผล<em class='heightlight'>แบบรายสัปดาห์</em>",
        		    scrollTo: "tooltip"
                }
				, {
                	element: document.getElementById('monthTabBtn'),
        		    intro: "ปุ่มเปลื่ยนการแสดงผลปฏิทินเป็น<em class='heightlight'>แบบรายเดือน</em>"
                }
				, {
					element: jQuery('.dhx_cal_data')[0],
        		    intro: "นี้คือการแสดงผล<em class='heightlight'>แบบรายเดือน</em>",
        		    scrollTo: "tooltip"
                }
				, {
					element: document.getElementById('bookingTabBtn'),
        		    intro: "ปุ่มสำหรับไป<em class='heightlight'>ระบบจองห้องประชุม</em>",
                }
				, {
					element: document.getElementById('myBookingTabBtn'),
    		    	intro: "เลือก&nbsp;<input id='checkboxGen-myBookingTabBtn' class='flatStyle' checked='checked' type='checkbox'><label for='checkboxGen-myBookingTabBtn' style='border: 1px solid rgb(38, 148, 232);'></label>&nbsp;เพื่อแสดงผลเฉพาะข้อมูลที่ตนเองเป็น<em class='heightlight'>ผู้ขอใช้</em>หรือ<em class='heightlight'>ผู้แจ้ง</em>บนปฏิทิน",
    		    	
      			}
				, {
					element: document.getElementById('myDateTabBtn'),
        		    intro: "เลือกวันที่ที่ต้องแสดงข้อมูล แล้วกดปุ่ม " + '<img id="myDateTabImg" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/go.png" class="event-data-form-btn-icon">',
                }
				, {
					element: document.getElementById('filterTabBtn'),
					intro: "ปุ่ม<em class='heightlight'>กรองข้อมูลห้อง</em>",
                }
				, {
					element: document.getElementById('mySideNavRoom'),
					intro: "นี้คือส่วนกรองข้อมูลห้อง<br>เลือก&nbsp;<input id='checkboxGen-mySideNavRoom' class='flatStyle' checked='checked' type='checkbox'><label for='checkboxGen-mySideNavRoom' style='border: 1px solid rgb(38, 148, 232);'></label>&nbsp;หน้าห้องที่ต้องแสดงผล และกด&nbsp;" + '<a href="javascript:void(0)" class="side-nav-room-search" style="background-color: #111; font-size: 14px; padding: 2px 5px 3px 2px; border-radius: 6px;">&nbsp;ค้นหา</a>',
                }
				, {
					element: document.getElementById('todayTabBtn'),
        		    intro: "ไปวันที่ปัจจุบัน"
				}
				, {
					element: document.getElementById('prevBtn'),
        		    intro: "ย้อนกลับ 1 วัน/สัปดาห์/เดือน"
				}
				, {
					element: document.getElementById('nextBtn'),
					intro: "ถัดไป 1 วัน/สัปดาห์/เดือน"
				}
      		]
		});
		
		intro.setOptions({
			doneLabel: 'เสร็จสิ้น'
   			, nextLabel: 'ถัดไป'
   			, prevLabel: 'ย้อนกลับ'
   			, skipLabel: 'ข้าม'
		});
		
		intro.onafterchange(function(targetElement) {
			if (targetElement.id == 'dayTabBtn') {
				jQuery("#dayTabBtn").click();
			} else if (targetElement.id == 'weekTabBtn') {
				jQuery("#weekTabBtn").click();
			} else if (targetElement.id == 'monthTabBtn') {
				jQuery("#monthTabBtn").click();
			} else if (targetElement.id == 'filterTabBtn') {
				document.getElementById("mySideNavRoom").style.width = "0";
				jQuery("#filterTabBtn").removeClass('active');
			} else if (targetElement.id == 'mySideNavRoom') {
				jQuery("#filterTabBtn").click();
			} else if (targetElement.id == 'todayTabBtn') {
				document.getElementById("mySideNavRoom").style.width = "0";
				jQuery("#filterTabBtn").removeClass('active');
			}			
			
		});
		
		intro.start().onexit(function() {
			if (isContiune == undefined) {
				startCalendar02();
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
	}
	
	function startCalendar02(isContiune) {
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
                {
        		    intro: "เรามาทำความรู้จักกับสถานะต่างๆ ของห้องกันดีกว่า ถ้าพร้อมแล้วกดปุ่ม<em class='heightlight'>ถัดไป</em>ได้เลย"
                }
                , {
                	element: document.getElementById('statusTabBtn'),
        		    intro: "นี้คือคำอธิบายสถานะต่างๆ ขอบระบบ"
                }, {
                	element: document.getElementById('statusBookingSpan'),
        		    intro: '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/booking.png"/>&nbsp;<s:text name="bk.booking"/>&nbsp;คือสถานะที่<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em>ต้องการจองห้องประชุมเพื่อใช้งาน โดย<em class="heightlight">สามารถจองล่วงหน้าได้ไม่เกิน 90 วัน</em>'
                }, {
                	element: document.getElementById('statusApproveSpan'),
        		    intro: '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png"/>&nbsp;<s:text name="bk.approve"/>&nbsp;คือสถานะที่ผู้ดูแลระบบอนุญาตให้สามารถใช้งานห้องได้แล้ว'
                }, {
                	element: document.getElementById('statusCancelSpan'),
        		    intro: '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png"/>&nbsp;<s:text name="bk.cancel"/>&nbsp;คือสถานะที่<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em>ยกเลิกการใช้งานห้อง โดย<em class="heightlight">ถ้ามีการเข้าใช้งานแล้วจะไม่สามารถยกเลิกได้</em>'
                }, {
                	element: document.getElementById('statusCheckInSpan'),
        		    intro: '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png"/>&nbsp;<s:text name="bk.checkIn"/>&nbsp;คือสถานะที่<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em>บันทึกการเข้าใช้งานห้อง โดย<em class="heightlight">สามารถบันทึกได้ก่อนและหลังเวลาเริ่มต้น 15 นาทีเท่านั้น</em>'
                }, {
                	element: document.getElementById('statusCheckOutSpan'),
        		    intro: '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png"/>&nbsp;<s:text name="bk.checkOut"/>&nbsp;คือสถานะที่<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em>บันทึกการคืนห้อง โดย<em class="heightlight">สามารถบันทึกได้หลังจากเวลาเริ่มต้น และหลังเวลาสิ้นสุดไม่เกิน 15 นาทีเท่านั้น</em>'
                }, {
                	element: document.getElementById('statusOverLimitCheckOutSpan'),
        		    intro: '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/over_limit_check_out.png"/>&nbsp;<s:text name="bk.overCheckOut"/>&nbsp;คือสถานะที่<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em><em class="heightlight">ไม่มีการบันทึกข้อมูลการคืนห้องหลังจากเวลาสิ้นสุดไปแล้ว 15 นาที</em>'
                }, {
                   	element: document.getElementById('statusNotCheckInSpan'),
        		    intro: '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/not_check_in.png"/>&nbsp;<s:text name="bk.notCheckIn"/>&nbsp;คือสถานะแจ้งเตือนกรณี<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em><em class="heightlight">ไม่บันทึกการเข้าใช้งานห้อง หลังจากเวลาเริ่มต้นไปแล้ว 10 นาที</em>'
                }, {
                	element: document.getElementById('statusNotCheckOutSpan'),
        		    intro: '<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/not_check_out.png"/>&nbsp;<s:text name="bk.notCheckOut"/>&nbsp;คือสถานะแจ้งเตือนกรณี<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em><em class="heightlight">ไม่บันทึกการคืนห้อง หลังจากเวลาสิ้นสุดไปแล้ว 10 นาที</em>'
                }
      		]
		});
		
		intro.setOptions({
			doneLabel: 'เสร็จสิ้น'
   			, nextLabel: 'ถัดไป'
   			, prevLabel: 'ย้อนกลับ'
   			, skipLabel: 'ข้าม'
		});
		
		intro.start().onexit(function() {
			if (isContiune == undefined) {
				startTodoList01();
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
	}
	
	function startEventBooking(isContiune) {
		
		stopLoadEventSchedulerInterval();
		stopLoadNotificationMessageInterval();
		clearNotifyMessage();
		createDemoEvent('W');
		
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
                {
                	element: jQuery('.dhx_cal_event')[0],
                	intro: 'นี้คือส่วนแสดงข้อมูลการจอง'
                }, {
                	element: jQuery('.event-header')[0],
                	intro: 'ส่วนแสดงห้องประชุม และสถานะต่างๆ'
                }, {
                	element: jQuery('.dhx_title_status')[0],
                	intro: "ไอคอนแสดงสถานะ<em class='heightlight-black'>ผู้ขอใช้</em>หรือ<em class='heightlight-black'>ผู้แจ้ง</em><em class='heightlight'>ถ้าเป็นบุคคลอื่นจะไม่แสดงสถานะนี้</em>"
                }, {
                	element: jQuery('.dhx_title_status_approve')[0],
                	intro: "ไอคอนแสดงสถานะ<em class='heightlight'>อนุมัติ</em>โดยจะถูกเปิดขึ้นเมื่อ<em class='heightlight-black'>ผู้ดูแลระบบ</em><em class='heightlight'>อนุญาตให้สามารถใช้งานห้องได้แล้ว</em>"
                }, {
                	element: jQuery('.dhx_title_status_check_in')[0],
                	intro: "ไอคอนแสดงสถานะ<em class='heightlight'>เข้าใช้งาน</em>โดยจะถูกเปิดขึ้นเมื่อ<em class='heightlight-black'>ผู้ขอใช้</em>หรือ<em class='heightlight-black'>ผู้แจ้ง</em><em class='heightlight'>บันทึกการเข้าใช้งานห้องแล้ว</em>"
                }, {
                	element: jQuery('.dhx_title_status_check_out')[0],
                	intro: "ไอคอนแสดงสถานะ<em class='heightlight'>คืนห้อง</em>โดยจะถูกเปิดขึ้นเมื่อ<em class='heightlight-black'>ผู้ขอใช้</em>หรือ<em class='heightlight-black'>ผู้แจ้ง</em><em class='heightlight'>บันทึกการคืนห้องแล้ว</em>"
                }, {
	               	element: jQuery('.dhx_cal_event')[0],
                   	intro: "คุณสามารถ<em class='heightlight'>ดับเบิ้ลคลิก</em>ที่ข้อมูลการจอง เพื่อแสดงรายละเอียดเพิ่มเติม และเปลื่ยนแปลงสถานะได้ ถ้าพร้อมแล้วกดปุ่ม<em class='heightlight'>ถัดไป</em>ได้เลย"
                }, {
                	element: document.getElementById('eventDataForm'),
                	intro: 'นี้คือส่วนแสดงรายละเอียดเพิ่ม เช่นห้องประชุม, สถานะ, หัวข้อ, วันที่และเวลา, อื่นๆ'
                }, {
                	element: document.getElementById('event-data-form-btn-section'),
                	intro: 'ส่วนปุ่มควบคุมการทำงาน โดยปุ่มที่แสดงจะเปลื่ยนแปลงไปตามสถานะ',
                	position: 'bottom'
                }
      		]
		});
		
		intro.setOptions({
			doneLabel: 'เสร็จสิ้น'
   			, nextLabel: 'ถัดไป'
   			, prevLabel: 'ย้อนกลับ'
   			, skipLabel: 'ข้าม'
		});
		
		intro.onbeforechange(function(targetElement) {
			if (jQuery(targetElement).hasClass('dhx_title_status_approve')) {
				jQuery(targetElement).attr('src', '${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png');
			
			} else if (jQuery(targetElement).hasClass('dhx_title_status_check_in')) {
				jQuery(targetElement).attr('src', '${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png');
				
			} else if (jQuery(targetElement).hasClass('dhx_title_status_check_out')) {
				jQuery(targetElement).attr('src', '${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png');
				
			} else if ((targetElement.id == 'eventDataForm')
					|| (targetElement.id == 'event-data-form-btn-section')) {
				jQuery(jQuery('.dhx_cal_event')[0]).dblclick();
			}
		});
		
		intro.onafterchange(function(targetElement) {
			if (targetElement.id == 'event-data-form-btn-section') {
				jQuery(jQuery('.dhx_cal_event')[0]).dblclick();
				jQuery("#eventDataForm").removeClass('introjs-fixParent');
				jQuery(".introjs-helperLayer").css('background', 'none');
			} else if (targetElement.id == 'event-data-form-status') {
				jQuery(jQuery('.dhx_cal_event')[0]).dblclick();
				jQuery("#eventDataForm").removeClass('introjs-fixParent');
				jQuery(".introjs-helperLayer").css('background', 'none');
			}
		});
		
		intro.start().onexit(function() {
			jQuery("#event-data-form-btn-close").click();
			if (isContiune == undefined) {
				startEventStatusBooking();
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
		
	}
	
	function startEventStatusBooking(isContiune) { 
		stopLoadEventSchedulerInterval();
		stopLoadNotificationMessageInterval();
		clearNotifyMessage();
		createDemoEvent('W');
		
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
   				{
                	element: document.getElementById('event-data-form-status'),
                	intro: 'สถานะ<em class="heightlight">ขอใช้งาน</em>',
                	position: 'right'
                }, {
                	element: document.getElementById('event-data-form-btn-section'),
                	intro: 'สถานะ<em class="heightlight">ขอใช้งาน</em><br>กรณี<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em><br>สามารถ<span class="event-data-form-btn" style="padding: 0px 0px 1px 2px"><a id="event-data-form-btn-cancel" href="javascript:void(0);"><img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png" class="event-data-form-btn-icon">&nbsp;ยกเลิก&nbsp;</a></span>ได้',
                	position: 'bottom'
                }, {
                	element: document.getElementById('event-data-form-btn-section'),
                	intro: 'สถานะ<em class="heightlight">ขอใช้งาน</em><br>กรณี<em class="heightlight-black">ผู้ดูแลระบบ</em><br>สามารถ<span class="event-data-form-btn" style="padding: 0px 0px 1px 2px"><a id="event-data-form-btn-approve" href="javascript:void(0);"><img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png" class="event-data-form-btn-icon">&nbsp;อนุมัติ&nbsp;</a></span>และ<span class="event-data-form-btn" style="padding: 0px 0px 1px 2px"><a id="event-data-form-btn-cancel" href="javascript:void(0);"><img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png" class="event-data-form-btn-icon">&nbsp;ยกเลิก&nbsp;</a></span>ได้',
                	position: 'bottom'
                }
   			]
		});
		
		intro.setOptions({
			doneLabel: 'เสร็จสิ้น'
   			, nextLabel: 'ถัดไป'
   			, prevLabel: 'ย้อนกลับ'
   			, skipLabel: 'ข้าม'
		});
		
		intro.onbeforechange(function(targetElement) {
			jQuery(jQuery('.dhx_cal_event')[0]).dblclick();
			if (targetElement.id == 'event-data-form-status') {
				jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-general').addClass('event-data-form-btn-section-admin');
			} else if (targetElement.id == 'event-data-form-btn-section') {
				if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-admin')) {
					jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-admin').addClass('event-data-form-btn-section-general');	
				} else if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-general')) {
					jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-general').addClass('event-data-form-btn-section-admin');
				}
			}
		});
		
		intro.onafterchange(function(targetElement) {
			jQuery("#eventDataForm").removeClass('introjs-fixParent');
			jQuery(".introjs-helperLayer").css('background', 'none');
			jQuery("#event-data-form-btn-section").empty();
			if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-admin')) {
				jQuery("#event-data-form-btn-section").append(createActionButtonApprove());
				jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
				jQuery("#event-data-form-btn-section").append(createActionButtonClose());				
			} else if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-general')) {
				jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
				jQuery("#event-data-form-btn-section").append(createActionButtonClose());
			}
		});
		
		intro.start().onexit(function() {
			jQuery("#event-data-form-btn-close").click();
			if (isContiune == undefined) {
				startEventStatusApprove();
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
                
	}
	
	function startEventStatusApprove(isContiune) { 
		stopLoadEventSchedulerInterval();
		stopLoadNotificationMessageInterval();
		clearNotifyMessage();
		createDemoEvent('A');
		
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
   				{
                	element: document.getElementById('event-data-form-status'),
                	intro: 'สถานะ<em class="heightlight">อนุมัติ</em>',
                	position: 'right'
                }, {
                	element: document.getElementById('event-data-form-btn-section'),
                	intro: 'สถานะ<em class="heightlight">อนุมัติ</em>'
                			+ '<br>กรณี<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em>และ<em class="heightlight-black">ผู้ดูแลระบบ</em>'
                			+ '<br>สามารถ'
                			+ '<span class="event-data-form-btn" style="padding: 0px 0px 1px 2px"><a id="event-data-form-btn-check-in" href="javascript:void(0);"><img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png" class="event-data-form-btn-icon">&nbsp;เข้าใช้งาน&nbsp;</a></span>'
                			+ '<span class="event-data-form-btn" style="padding: 0px 0px 1px 2px"><a id="event-data-form-btn-check-out" href="javascript:void(0);"><img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png" class="event-data-form-btn-icon">&nbsp;คืนห้อง&nbsp;</a></span>'
                			+ '<br>และ'
                			+ '<span class="event-data-form-btn" style="padding: 0px 0px 1px 2px"><a id="event-data-form-btn-cancel" href="javascript:void(0);"><img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png" class="event-data-form-btn-icon">&nbsp;ยกเลิก&nbsp;</a></span>ได้',
                	position: 'bottom'
                }, {
                	element: document.getElementById('event-data-form-btn-section'),
                	intro: 'สถานะ<em class="heightlight">อนุมัติ</em>'
                			+ '<br>กรณี<em class="heightlight-black">ไม่คืนห้องภายในเวลาที่กำหนด</em>'
                			+ '<br>ระบบจะบังคับให้<em class="heightlight">ระบุเวลาคืนห้อง</em><br>และบันทึก<span class="event-data-form-btn" style="padding: 0px 0px 1px 2px"><a id="event-data-form-btn-over-limit-check-out" href="javascript:void(0);"><img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/over_limit_check_out.png" class="event-data-form-btn-icon">&nbsp;คืนห้อง (เกินกำหนดเวลา)&nbsp;</a></span>',
                	position: 'bottom'
                }
   			]
		});
		
		intro.setOptions({
			doneLabel: 'เสร็จสิ้น'
   			, nextLabel: 'ถัดไป'
   			, prevLabel: 'ย้อนกลับ'
   			, skipLabel: 'ข้าม'
		});
		
		intro.onbeforechange(function(targetElement) {
			jQuery(jQuery('.dhx_cal_event')[0]).dblclick();
			if (targetElement.id == 'event-data-form-status') {
				jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-general').addClass('event-data-form-btn-section-admin');
			} else if (targetElement.id == 'event-data-form-btn-section') {
				if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-admin')) {
					jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-admin').addClass('event-data-form-btn-section-general');	
				} else if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-general')) {
					jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-general').addClass('event-data-form-btn-section-admin');
				}
			}
		});
		
		intro.onafterchange(function(targetElement) {
			jQuery("#eventDataForm").removeClass('introjs-fixParent');
			jQuery(".introjs-helperLayer").css('background', 'none');
			jQuery("#event-data-form-btn-section").empty();
			if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-admin')) {
				jQuery("#event-data-form-btn-section").append(createActionButtonCheckIn());
				jQuery("#event-data-form-btn-section").append(createActionInputOverLimitCheckOut());
				initInputOverLimitCheckOut();
				jQuery("#event-data-form-btn-section").append(createActionButtonOverLimitCheckOut());
    			jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
    			
			} else if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-general')) {
				jQuery("#event-data-form-btn-section").append(createActionButtonCheckIn());
    			jQuery("#event-data-form-btn-section").append(createActionButtonCheckOut());
    			jQuery("#event-data-form-btn-section").append(createActionButtonCancel());
    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());

			}
		});
		
		intro.start().onexit(function() {
			jQuery("#event-data-form-btn-close").click();
			if (isContiune == undefined) {
				startEventStatusCheckIn();
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
                
	}
	
	function startEventStatusCheckIn(isContiune) { 
		stopLoadEventSchedulerInterval();
		stopLoadNotificationMessageInterval();
		clearNotifyMessage();
		createDemoEvent('CI');
		
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
   				{
                	element: document.getElementById('event-data-form-status'),
                	intro: 'สถานะ<em class="heightlight">เข้าใช้งาน</em>',
                	position: 'right'
                }, {
                	element: document.getElementById('event-data-form-btn-section'),
                	intro: 'สถานะ<em class="heightlight">เข้าใช้งาน</em>'
                			+ '<br>กรณี<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em>และ<em class="heightlight-black">ผู้ดูแลระบบ</em>'
                			+ '<br>สามารถ'
                			+ '<span class="event-data-form-btn" style="padding: 0px 0px 1px 2px"><a id="event-data-form-btn-check-out" href="javascript:void(0);"><img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png" class="event-data-form-btn-icon">&nbsp;คืนห้อง&nbsp;</a></span>ได้',
                	position: 'bottom'
                }, {
                	element: document.getElementById('event-data-form-btn-section'),
                	intro: 'สถานะ<em class="heightlight">เข้าใช้งาน</em>'
                			+ '<br>กรณี<em class="heightlight-black">ไม่คืนห้องภายในเวลาที่กำหนด</em>'
                			+ '<br>ระบบจะบังคับให้<em class="heightlight">ระบุเวลาคืนห้อง</em><br>และบันทึก<span class="event-data-form-btn" style="padding: 0px 0px 1px 2px"><a id="event-data-form-btn-over-limit-check-out" href="javascript:void(0);"><img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/over_limit_check_out.png" class="event-data-form-btn-icon">&nbsp;คืนห้อง (เกินกำหนดเวลา)&nbsp;</a></span>',
                	position: 'bottom'
                }
   			]
		});
		
		intro.setOptions({
			doneLabel: 'เสร็จสิ้น'
   			, nextLabel: 'ถัดไป'
   			, prevLabel: 'ย้อนกลับ'
   			, skipLabel: 'ข้าม'
		});
		
		intro.onbeforechange(function(targetElement) {
			jQuery(jQuery('.dhx_cal_event')[0]).dblclick();
			if (targetElement.id == 'event-data-form-status') {
				jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-general').addClass('event-data-form-btn-section-admin');
			} else if (targetElement.id == 'event-data-form-btn-section') {
				if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-admin')) {
					jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-admin').addClass('event-data-form-btn-section-general');	
				} else if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-general')) {
					jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-general').addClass('event-data-form-btn-section-admin');
				}
			}
		});
		
		intro.onafterchange(function(targetElement) {
			jQuery("#eventDataForm").removeClass('introjs-fixParent');
			jQuery(".introjs-helperLayer").css('background', 'none');
			jQuery("#event-data-form-btn-section").empty();
			if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-admin')) {
				jQuery("#event-data-form-btn-section").append(createActionInputOverLimitCheckOut());
				initInputOverLimitCheckOut();
				jQuery("#event-data-form-btn-section").append(createActionButtonOverLimitCheckOut());
    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
    			
			} else if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-general')) {
    			jQuery("#event-data-form-btn-section").append(createActionButtonCheckOut());
    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());

			}
		});
		
		intro.start().onexit(function() {
			jQuery("#event-data-form-btn-close").click();
			if (isContiune == undefined) {
				startEventStatusCheckOut();
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
                
	}
	
	function startEventStatusCheckOut(isContiune) { 
		stopLoadEventSchedulerInterval();
		stopLoadNotificationMessageInterval();
		clearNotifyMessage();
		createDemoEvent('CO');
		
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
   				{
                	element: document.getElementById('event-data-form-status'),
                	intro: 'สถานะ<em class="heightlight">คืนห้อง</em>',
                	position: 'right'
                }, {
                	element: document.getElementById('event-data-form-btn-section'),
                	intro: 'สถานะ<em class="heightlight">คืนห้อง</em>'
                			+ '<br>กรณี<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em>และ<em class="heightlight-black">ผู้ดูแลระบบ</em>'
                			+ '<br>สามารถดูข้อมูล และรายละเอียดได้เท่านั้น'
                			+ '',
                	position: 'bottom'
                }
   			]
		});
		
		intro.setOptions({
			doneLabel: 'เสร็จสิ้น'
   			, nextLabel: 'ถัดไป'
   			, prevLabel: 'ย้อนกลับ'
   			, skipLabel: 'ข้าม'
		});
		
		intro.onchange(function(targetElement) {
			
		});
		
		intro.onbeforechange(function(targetElement) {
			jQuery(jQuery('.dhx_cal_event')[0]).dblclick();
			if (targetElement.id == 'event-data-form-status') {
				jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-general').addClass('event-data-form-btn-section-admin');
			} else if (targetElement.id == 'event-data-form-btn-section') {
				if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-admin')) {
					jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-admin').addClass('event-data-form-btn-section-general');	
				} else if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-general')) {
					jQuery("#event-data-form-btn-section").removeClass('event-data-form-btn-section-general').addClass('event-data-form-btn-section-admin');
				}
			}
		});
		
		intro.onafterchange(function(targetElement) {
			jQuery("#eventDataForm").removeClass('introjs-fixParent');
			jQuery(".introjs-helperLayer").css('background', 'none');
			jQuery("#event-data-form-btn-section").empty();
			if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-admin')) {
    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());
    			
			} else if (jQuery("#event-data-form-btn-section").hasClass('event-data-form-btn-section-general')) {
    			jQuery("#event-data-form-btn-section").append(createActionButtonClose());

			}
		});
		
		intro.start().onexit(function() {
			jQuery("#event-data-form-btn-close").click();
			if (isContiune == undefined) {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
                
	}
	
	function createDemoEvent(caseId) {
		scheduler.clearAll();
		
		// กำหนดวันแสดง
		currentMode = 'week';
		currentDay = '23/08/2017';
		
		var jsonDataArray = [];
		var eventObj = new Object();
		var roomSettingData = new Object();
		var roomBooking = new Object();
		
		if (caseId == 'W') { 
			roomBooking.bookingStatusName = 'ขอใช้งาน';
		} else if (caseId == 'A') {
			roomBooking.bookingStatusName = 'อนุมัติ';
		} else if (caseId == 'CI') {
			roomBooking.bookingStatusName = 'เข้าใช้งาน';
		} else if (caseId == 'CO') {
			roomBooking.bookingStatusName = 'คืนห้อง';
		}

		roomSettingData.name = 'ห้องประชุมใหญ่';
		roomBooking.bookingStatusCode = caseId;
		roomBooking.startDate = '23/08/2017';
		roomBooking.startTime = '08:00';
		roomBooking.endDate = '23/08/2017';
		roomBooking.endTime = '10:00';
		roomBooking.subject = 'ประชุมภายในฝ่าย PG';
		roomBooking.userBookName = 'อรัญญา (ใจ)';
		roomBooking.createUserName = 'ชลัช (ลัช)';
		roomBooking.attendees = '15';
		roomBooking.detail = 'ประชุมติดตามงาน และอธิบายกฏระเบียบต่างๆ ภายในฝ่าย';

		eventObj.roomSettingData = roomSettingData;
		eventObj.roomBooking = roomBooking;
		eventObj.id = '1';
		eventObj.dataOwner = true;
		eventObj.dataOverLimitCheckOut = false;
		
		jsonDataArray.push({
			id : eventObj.id,
	    	start_date : eventObj.roomBooking.startDate + ' ' + eventObj.roomBooking.startTime,
			end_date : eventObj.roomBooking.endDate + ' ' + eventObj.roomBooking.endTime,
			text : eventObj.roomBooking.subject,
			skin : '',
			roomBooking: eventObj.roomBooking,
			roomSettingData: eventObj.roomSettingData,
			dataOwner: eventObj.dataOwner,
			dataOverLimitCheckOut: eventObj.dataOverLimitCheckOut
		});

		scheduler.parse(jsonDataArray, "json");
		focusDateInCalendar();
			$('div.dhx_cal_data').animate({
				scrollTop: 0
			}, 0);
	}
</script>
