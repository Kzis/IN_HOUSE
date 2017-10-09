<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<html>
<head>
	<s:include value="/jsp/calendar/dashboard/include/dashboard-js-css.jsp"/>
</head>
<body>
	<div id="scheduler_here" class="dhx_cal_container">
		<div id="statusTabBtn" class="dhx_title_status_custom">
			&nbsp;<s:text name="bk.status"/>&nbsp;
			&nbsp;:&nbsp;
			<span id="statusBookingSpan">
				<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/booking.png">
				&nbsp;<s:text name="bk.booking"/>&nbsp;
			</span>
			&nbsp;|&nbsp;
			<span id="statusApproveSpan">
				<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png"/>
				&nbsp;<s:text name="bk.approve"/>&nbsp;
			</span>
			&nbsp;|&nbsp;
			<span id="statusCancelSpan">
				<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png">
				&nbsp;<s:text name="bk.cancel"/>&nbsp;
			</span>
			&nbsp;|&nbsp;
			<span id="statusCheckInSpan">
				<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png">
				&nbsp;<s:text name="bk.checkIn"/>&nbsp;
			</span>
			&nbsp;|&nbsp;
			<span id="statusCheckOutSpan">
				<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png">
				&nbsp;<s:text name="bk.checkOut"/>&nbsp;
			</span>
			&nbsp;|&nbsp; 
			<span id="statusOverLimitCheckOutSpan">
				<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/over_limit_check_out.png">
				&nbsp;<s:text name="bk.overCheckOut"/>&nbsp;
			</span>
			&nbsp;|&nbsp; 
			<%--
			<span id="statusUnstableSpan">
				<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/unstable.png">
				&nbsp;<s:text name="bk.unstable"/>&nbsp;
			</span>
			&nbsp;|&nbsp;
			 --%> 
			<span id="statusNotCheckInSpan"> 
				<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/not_check_in.png">
				&nbsp;<s:text name="bk.notCheckIn"/>&nbsp;
			</span>
			&nbsp;|&nbsp; 
			<span id="statusNotCheckOutSpan"> 
				<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/not_check_out.png">
				&nbsp;<s:text name="bk.notCheckOut"/>&nbsp;
			</span>
		</div>
		<div class="dhx_cal_navline">
			<div id="prevBtn" class="dhx_cal_prev_button"></div>
			<div id="nextBtn" class="dhx_cal_next_button"></div>
			<div id="todayTabBtn" class="dhx_cal_today_button"></div>
			<div class="dhx_cal_date"></div>
			<div id="dayTabBtn" class="dhx_cal_tab" name="day_tab" style="right: 204px;"></div>
			<div id="weekTabBtn" class="dhx_cal_tab" name="week_tab" style="right: 140px;"></div>
			<div id="monthTabBtn" class="dhx_cal_tab" name="month_tab" style="right: 76px;"></div>
			<div id="bookingTabBtn" class="dhx_cal_tab" name="<s:text name='bk.booking'/>"></div>
			<div id="myBookingTabBtn" class="dhx_cal_tab" name="myBookingTabBtn" data-checked="false">
				<input id="myBookingCheckBox" type="checkbox" style="margin-top: 5px;" data-checked="false"/>&nbsp;&nbsp;&nbsp;การจองของฉัน
			</div>
			<div id="filterTabBtn" class="dhx_cal_tab" name="Filter"></div>
			<div id="myDateTabBtn" class="dhx_cal_tab" name="dateTabBtn">
				<input id="myDateText" type="text"/>
				&nbsp; 
				<img id="myDateTabImg" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/go.png" class="event-data-form-btn-icon" onclick="gotoCurrentDay();">
			</div>
		</div>
		<div class="dhx_cal_header" style="margin-top: 15px;"></div>
		<div class="dhx_cal_data" style="margin-top: 15px;"></div>
	</div>
	<div id="mySideNavRoom" class="side-nav-room">
		<a href="javascript:void(0)" class="side-nav-room-select-all"><input type="checkbox" name="selectallroom">&nbsp;<s:text name="bk.select_all_room"/></a>
		<a href="javascript:void(0)" class="side-nav-room-close-btn" onclick="toggleSideNavRoom()">&times;</a>
		<a href="javascript:void(0)" class="side-nav-room-search">&nbsp;<s:text name="search"/></a>
	</div>
	<div id="eventDataForm" class="dhx_cal_light dhx_cal_light_wide dhx_cal_light_rec event-data-form">
		<div class="dhx_cal_ltitle event-data-form-title" role="heading">
			<div class="event-header event-header-left" style="width: 200px;">
				<div style="float: left;"><b><s:text name="bk.room"/>:</b>&nbsp;</div><div id="event-data-form-room" style="float: left;">ห้องประชุมใหญ่</div>
			</div>
			<div class="event-header event-header-right">
				&nbsp;&nbsp;&nbsp;<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/close.png" class="event-data-form-btn-icon" id="event-data-form-btn-close">
			</div>
			<div class="event-header event-header-right">
				<div style="float: left;"><b><s:text name="bk.status"/>:</b>&nbsp;</div><div id="event-data-form-status" style="float: left;">Check In</div>
			</div>
		</div>
		<div class="dhx_cal_larea event-data-form-body">
			<div class="dhx_wrap_section dhx_wrap_section-noborder">
				<div class="dhx_cal_lsection event-data-form-label">
					<b><s:text name='bk.subject'/>:</b>
				</div>
				<div id="event-data-form-subject" class="dhx_cal_ltext event-data-form-value" style="height: auto;">
					จูล่ง ได้รับฉายาว่าเป็น "สุภาพบุรุษจากเสียงสาน" เกิดในช่วงกลางคริสต์ศตวรรษที่ 2 ประมาณปี ค.ศ. 161[1] ที่อำเภอเจินติ้ง เมืองเสียงสาน มีแซ่เตียว (จ้าว) ชื่อ หยุน (แปลว่าเมฆ) ชื่อรอง จูล่ง หรือ จื่อหลง (แปลว่าบุตรมังกร) สูงประมาณ 6 ศอก (1.89 เมตร) หน้าผากกว้างดั่งเสือ ตาโต คิ้วดก กรามใหญ่กว้างบ่งบอกถึงนิสัยซื่อสัตย์ สุภาพเรียบร้อย น้ำใจกล้าหาญ สวมเกราะสีขาว ใช้ทวนยาวเป็นอาวุธ พาหนะคู่ใจ คือ ม้าสีขาว
				</div>
			</div>
			<div class="dhx_wrap_section dhx_wrap_section-noborder">
				<span class="dhx_cal_lsection event-data-form-label">
					<b><s:text name="bk.date"/>:</b>
				</span>
				<span id="event-data-form-start-date" class="dhx_cal_ltext event-data-form-value" style="float: left;">
					16/08/2017
				</span>
				<span class="dhx_cal_lsection event-data-form-label">
					<b><s:text name="bk.time"/>:</b>
				</span>
				<span id="event-data-form-start-end-time" class="dhx_cal_ltext event-data-form-value" style="float: left;">
					16:00 - 16:30
				</span>
			</div>
			<div class="dhx_wrap_section dhx_wrap_section-noborder">
				<div class="dhx_cal_lsection event-data-form-label">
					<b><s:text name="bk.totalAttendees" />:</b>
				</div>
				<div id="event-data-form-total-attendees" class="dhx_cal_ltext event-data-form-value">
					23
				</div>
			</div>
			<div class="dhx_wrap_section dhx_wrap_section-noborder">
				<div class="dhx_cal_lsection event-data-form-label">
					<b><s:text name="moreDetail" />:</b>
				</div>
				<div id="event-data-form-more-detail" class="dhx_cal_ltext event-data-form-value" style="height: auto;">
					จูล่งเดิมเป็นชาวเมืองเสียงสาน ต่อมาได้มาเป็นทหารของอ้วนเสี้ยว แต่อ้วนเสี้ยวหยาบช้า ไร้น้ำใจ จูล่งจึงหนีไปอยู่กับกองซุนจ้านเจ้าเมืองปักเป๋ง โดยที่ขณะนั้นกองซุนจ้านได้ทำศึกกับอ้วนเสี้ยว จูล่งยังได้ช่วยชีวิตกองซุนจ้านไว้แล้วสู้กับบุนทิวถึง 60 เพลง จนบุนทิวหนีไป ต่อมาจูล่งได้มีโอกาสรู้จักกับเล่าปี่ ทั้งสองต่างเลื่อมใสซึ่งกันและกัน เมื่อกองซุนจ้านฆ่าตัวตายเพราะแพ้อ้วนเสี้ยว จูล่งจึงได้ร่อนเร่พเนจรจนมาถึงเขาโงจิวสัน ซึ่งมีโจรป่ากลุ่มหนึ่งมีหุยง่วนเสียวเป็นหัวหน้า หุยง่วนเสียวคิดชิงม้าจากจูล่ง จูล่งจึงฆ่าหุยง่วนเสียวตายแล้วได้เป็นหัวหน้าโจรป่าแทน ต่อมากวนอูได้ใช้ให้จิวฉองมาตามหุยง่วนเสียวและโจรป่าไปช่วยรบ จิวฉองเมื่อเห็นจูล่งคุมโจรป่าจึงคิดว่าจูล่งคิดร้ายฆ่าหุยง่วนเสียว จิวฉองจึงตะบันม้าเข้ารบกับจูล่ง ปรากฏว่าจิวฉองต้องกลับไปหากวนอูในสภาพเลือดโทรมกาย ถูกแทงถึง 3 แผล (สำนวนสามก๊กฉบับวณิพกของ ยาขอบ) จิวฉองเล่าว่าคนผู้นี้มีฝีมือระดับลิโป้ ดังนั้นกวนอูกับเล่าปี่จึงต้องรุดไปดูด้วยตนเอง แต่เมื่อได้พบกันจูล่งก็เล่าความจริงทั้งหมด ตั้งแต่นั้นมาจูล่งก็ได้เป็นทหารเอกของเล่าปี่เคยรบชนะม้าเฉียวในการประลองตัวๆและยังเคยทะเลาะกับเตียวหุยตอนอยู่กับกองซุนจ้านจนเกือบสังหารเตียวหุยแต่กวนอูมาขวางไว้
				</div>
			</div>
			<div class="dhx_wrap_section dhx_wrap_section-noborder">
				<div class="dhx_cal_lsection event-data-form-label">
					<b><s:text name="bk.userUse"/>:</b>
				</div>
				<div id="event-data-form-user-book-name" class="dhx_cal_ltext event-data-form-value">
					สิทธิพล (เต๋า)
				</div>
			</div>
			<div class="dhx_wrap_section">
				<div class="dhx_cal_lsection event-data-form-label">
					<b><s:text name="bk.userBooking"/>:</b>
				</div>
				<div id="event-data-form-create-user-name" class="dhx_cal_ltext event-data-form-value">
					สิทธิพล (เต๋า)
				</div>
			</div>
		</div>
		<div id="event-data-form-btn-section" class="event-data-form-btn-section event-data-form-btn-section-general">
			<span class="event-data-form-btn">
				<a id="event-data-form-btn-approve" href="javascript:void(0);">
					<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png" class="event-data-form-btn-icon" />
					&nbsp;<s:text name="bk.approve"/>&nbsp;
				</a>
			</span>
			<span class="event-data-form-btn">
				<a id="event-data-form-btn-check-in" href="javascript:void(0);">
					<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png" class="event-data-form-btn-icon" />
					&nbsp;<s:text name="bk.checkIn"/>&nbsp;
				</a>
			</span>
			<span class="event-data-form-btn">
				<a id="event-data-form-btn-check-out" href="javascript:void(0);">
					<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png" class="event-data-form-btn-icon" />
					&nbsp;<s:text name="bk.checkOut"/>&nbsp;
				</a>
			</span>
			<input id="event-data-form-input-over-limit-check-out" type="text" class="event-data-form-input-over-limit-check-out input_timeformat" value="08:00" />
			<span class="event-data-form-btn">
				<a id="event-data-form-btn-over-limit-check-out" href="javascript:void(0);">
					<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/over_limit_check_out.png" class="event-data-form-btn-icon" />
					&nbsp;<s:text name="bk.overCheckOut"/>&nbsp;
				</a>
			</span>
			<span class="event-data-form-btn">
				<a id="event-data-form-btn-cancel" href="javascript:void(0);">
					<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png" class="event-data-form-btn-icon" />
					&nbsp;<s:text name="bk.cancel"/>&nbsp;
				</a>
			</span>
			<span class="event-data-form-btn">
				<a id="event-data-form-btn-close-2" href="javascript:void(0);">
					<img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/close.png" class="event-data-form-btn-icon" />
					&nbsp;<s:text name="bk.close"/>&nbsp;
				</a>
			</span>
		</div>
	</div>
</body>
</html>