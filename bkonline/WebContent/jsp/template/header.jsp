<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="util.string.StringDelimeter"%>
<%@ page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>
<%@ page import="com.cct.inhouse.common.InhouseUser"%>
<%@ page import="com.cct.inhouse.common.InhouseAction"%>
<%@ page import="com.cct.common.CommonUser"%>
<%@ page import="com.cct.domain.Operator"%>
<%@ page import="util.web.SessionUtil"%>
<%@ page import="com.cct.inhouse.util.web.InHousePushMenuUtil"%>
<%@ page import="com.cct.inhouse.bkonline.util.log.LogUtil"%>
<%
	boolean haveUser = false;
	boolean haveMenu = false;
	String htmlMenu = StringDelimeter.EMPTY.getValue();
	String htmlHeader = StringDelimeter.EMPTY.getValue();
	String PP_CODE = StringDelimeter.EMPTY.getValue();
	String context = request.getContextPath();
	if (request.getParameter("PP_CODE") != null) {
		PP_CODE = request.getParameter("PP_CODE");
	}
	
	String FF_CODE = "";
	if (request.getParameter("FF_CODE") != null) {
		FF_CODE = request.getParameter("FF_CODE");
	}
	
	LogUtil.SEC.debug("PP_CODE: " + PP_CODE);
	LogUtil.SEC.debug("FF_CODE: " + FF_CODE);
	try {
		if (SessionUtil.getAttribute("useMenu") != null) {
			haveMenu = (boolean) SessionUtil.getAttribute("useMenu");
		}
		
		haveMenu = true;
		if (haveMenu) {
			InhouseUser user = InhouseAction.getUser();
			if (user != null) {
				haveUser = true;
				htmlMenu = new InHousePushMenuUtil(context, user.getMapOperator()).genarateMenuBar();
				htmlHeader = InHousePushMenuUtil.searchLabel(user.getMapOperator(), FF_CODE);
			}
		}
	} catch (Exception ex) {
		LogUtil.SEC.error(ex);
		haveMenu = false;
	}
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		function pushMenuRestoreState() {
			var DELIMITER_FUNCTION = "_";
			
			var PCODE = '<%=PP_CODE%>';
			if (PCODE == '') {
				return;
			}
			var groupOperatorId = jQuery("[id$=" + PCODE+ "]").attr('id');
			var processGroupOperatorId = groupOperatorId;
			var currentOperatorId = "";
			var indexOfGroupOperatorId = processGroupOperatorId.indexOf(DELIMITER_FUNCTION);
			if (indexOfGroupOperatorId > 0) {
				while (indexOfGroupOperatorId > 0) {
					if (currentOperatorId.length > 0) {
						currentOperatorId += DELIMITER_FUNCTION
					}
					currentOperatorId += processGroupOperatorId.substring(0, indexOfGroupOperatorId);
					processGroupOperatorId = processGroupOperatorId.substring(indexOfGroupOperatorId + 1, processGroupOperatorId.length);
					//console.info('currentOperatorId ' + currentOperatorId);
					//console.info('groupOperatorId ' + processGroupOperatorId);
					indexOfGroupOperatorId = processGroupOperatorId.indexOf(DELIMITER_FUNCTION);
					jQuery("#" + currentOperatorId).parent().parent().parent().addClass('mp-level-open mp-level-overlay').css('transform', '');;
				}
			}
			
			jQuery("#" + groupOperatorId).css({"background":"rgba(0,0,0,0.2)","box-shadow":"inset 0 -1px rgba(0,0,0,0)"}).parent().parent().parent().addClass('mp-level-open').css('transform', '');
			jQuery("#mp-pusher").addClass("mp-pushed");
			jQuery("#mp-pusher").attr('currentLevel', groupOperatorId.split(DELIMITER_FUNCTION).length - 1).attr('currentMenuId', groupOperatorId);
		}
	</script>
	<style type="text/css">
		em.heightlight {
			color: blue;
			font-weight: bold;
			padding-left: 5px;
			padding-right: 5px;
		}
		
		em.heightlight-black {
			color: black;
			font-weight: bold;
			padding-left: 5px;
			padding-right: 5px;
		}
		
		#pushMenuDivId, #systemNavigateDivId, #profileMenuDivId {
			float: left;
			padding: 5px; 
			border: none;
		}
		
		#profileMenuDivId {
			float: right;
		}
		
		#pushMenuBoxDivId {
			border-top-left-radius: 6px;
			border-top-right-radius: 6px;
			border-bottom-right-radius: 6px;
			border-bottom-left-radius: 6px;
			width: 32px; 
			height: 32px; 
			background-color: #1F6882;
			overflow: hidden;
			padding: 4px;
			cursor: pointer;
		}
		
		#pushMenuBoxDivId::before  {
			background: #fff none repeat scroll 0 0;
			box-shadow: 0 4px #1F6882, 0 10px #fff, 0 14px #1F6882, 0 20px #fff;
			left: 6px;
			right: 2px;
		    top: 7px;
		    position: absolute;
		    width: 28px;
		}

		#systemNavigateTitleDivId {
			font-weight: bold;
			font-size: 18px;
			font-family: Tahoma,sans-serif;
		}
		
		#systemNavigateDetailDivId {
			font-weight: bold;
			font-size: 16px;
			font-family: Tahoma,sans-serif;
		}
		
		img.topbar-noti-badge-image {
			height: 45px;		
		}

		span.topbar-noti-badge-count {
			margin-top: -45px;
			margin-right: -2px;
			display: inline-block;
		  	vertical-align: text-top;
		  	padding: 0 5px;
		  	line-height: 16px;
		  	font-size: 11px;
		  	color: white;
		  	text-shadow: 0 1px #902a27;
		  	background: #e93631;
		  	border-radius: 100%;
		  	background-image: -webkit-linear-gradient(top, #f65d5f, #e93631);
		  	background-image: -moz-linear-gradient(top, #f65d5f, #e93631);
		  	background-image: -o-linear-gradient(top, #f65d5f, #e93631);
		  	background-image: linear-gradient(to bottom, #f65d5f, #e93631);
		  	-webkit-box-shadow: inset 0 1px rgba(255, 255, 255, 0.2), 0 1px rgba(0, 0, 0, 0.2);
		  	box-shadow: inset 0 1px rgba(255, 255, 255, 0.2), 0 1px rgba(0, 0, 0, 0.2);
		}
		
		img.topbar-noti-badge-image-action {
			height: 45px;		
		}

		span.topbar-noti-badge-count-action {
			margin-top: -45px;
			margin-right: -2px;
			display: inline-block;
		  	vertical-align: text-top;
		  	padding: 0 5px;
		  	line-height: 16px;
		  	font-size: 11px;
		  	color: white;
		  	text-shadow: 0 1px #902a27;
		  	background: #e93631;
		  	border-radius: 100%;
		  	background-image: -webkit-linear-gradient(top, #f65d5f, #e93631);
		  	background-image: -moz-linear-gradient(top, #f65d5f, #e93631);
		  	background-image: -o-linear-gradient(top, #f65d5f, #e93631);
		  	background-image: linear-gradient(to bottom, #f65d5f, #e93631);
		  	-webkit-box-shadow: inset 0 1px rgba(255, 255, 255, 0.2), 0 1px rgba(0, 0, 0, 0.2);
		  	box-shadow: inset 0 1px rgba(255, 255, 255, 0.2), 0 1px rgba(0, 0, 0, 0.2);
		}
		
		/* THE NOTIFICAIONS WINDOW. THIS REMAINS HIDDEN WHEN THE PAGE LOADS. */
	    #notifications-action {
	        display: none;
	        width: 430px;
	        position: absolute;
	        top: 55px;
	        right: 0;
	        background: #FFF;
	        border: solid 1px rgba(100, 100, 100, .20);
	        -webkit-box-shadow: 0 3px 8px rgba(0, 0, 0, .20);
	        z-index: 10;
	        text-align: left;
	        
	    }
	    
	    /* AN ARROW LIKE STRUCTURE JUST OVER THE NOTIFICATIONS WINDOW */
	    #notifications-action:before {         
	        content: '';
	        display:block;
	        width:0;
	        height:0;
	        color:transparent;
	        border:10px solid #CCC;
	        border-color:transparent transparent #FFF;
	        margin-top:-20px;
	        margin-left:337px;
	    }
        
	    #notifications-action h3 {
	        display:block;
	        color:#333; 
	        background:#FFF;
	        font-weight:bold;
	        font-size:13px;    
	        padding:8px;
	        margin:0;
	        border-bottom:solid 1px rgba(100, 100, 100, .30);
	    }
	    
	    div.notifications-message-action {
	    	height: 363px; 
	    	overflow-x: visible; 
	    	overflow-y: scroll;
	    }
	    
	    div.notifications-message-box-action {
	    	cursor: pointer;
	    	padding: 1px 15px 0px 15px;
	    }
	    
	    p.notifications-message-text-action {
	    	font-size:13px;
	    	margin: 10px;
	    }
	    
	    b.notifications-message-subject-action {
	    	color: blue;
	    }
	    
	    div.notifications-message-line-action {
	    	height: 1px;
	    	border: none;
	    	border-bottom: 1px solid silver;
	    }
	    
	    img.notifications-message-icon-action {
	    	width: 16px;
	    	height: 16px;
	    }
	    
		/* THE NOTIFICAIONS WINDOW. THIS REMAINS HIDDEN WHEN THE PAGE LOADS. */
	    #notifications {
	        display: none;
	        width: 430px;
	        position: absolute;
	        top: 55px;
	        right: 0;
	        background: #FFF;
	        border: solid 1px rgba(100, 100, 100, .20);
	        -webkit-box-shadow: 0 3px 8px rgba(0, 0, 0, .20);
	        z-index: 10;
	        text-align: left;
	        
	    }
	    
	    /* AN ARROW LIKE STRUCTURE JUST OVER THE NOTIFICATIONS WINDOW */
	    #notifications:before {         
	        content: '';
	        display:block;
	        width:0;
	        height:0;
	        color:transparent;
	        border:10px solid #CCC;
	        border-color:transparent transparent #FFF;
	        margin-top:-20px;
	        margin-left:275px;
	    }
        
	    #notifications h3 {
	        display:block;
	        color:#333; 
	        background:#FFF;
	        font-weight:bold;
	        font-size:13px;    
	        padding:8px;
	        margin:0;
	        border-bottom:solid 1px rgba(100, 100, 100, .30);
	    }
	    
	    img.topbar-noti-badge-image-help {
			height: 45px;		
		}

		span.topbar-noti-badge-count-help {
			margin-top: -45px;
			margin-right: -2px;
			display: inline-block;
		  	vertical-align: text-top;
		  	padding: 0 5px;
		  	line-height: 16px;
		  	font-size: 11px;
		  	color: white;
		  	text-shadow: 0 1px #902a27;
		  	background: #e93631;
		  	border-radius: 100%;
		  	background-image: -webkit-linear-gradient(top, #f65d5f, #e93631);
		  	background-image: -moz-linear-gradient(top, #f65d5f, #e93631);
		  	background-image: -o-linear-gradient(top, #f65d5f, #e93631);
		  	background-image: linear-gradient(to bottom, #f65d5f, #e93631);
		  	-webkit-box-shadow: inset 0 1px rgba(255, 255, 255, 0.2), 0 1px rgba(0, 0, 0, 0.2);
		  	box-shadow: inset 0 1px rgba(255, 255, 255, 0.2), 0 1px rgba(0, 0, 0, 0.2);
		}
		
		/* THE NOTIFICAIONS WINDOW. THIS REMAINS HIDDEN WHEN THE PAGE LOADS. */
	    #notifications-help {
	        display: none;
	        width: 430px;
	        position: absolute;
	        top: 55px;
	        right: 0;
	        background: #FFF;
	        border: solid 1px rgba(100, 100, 100, .20);
	        -webkit-box-shadow: 0 3px 8px rgba(0, 0, 0, .20);
	        z-index: 10;
	        text-align: left;
	        
	    }
	    
	    /* AN ARROW LIKE STRUCTURE JUST OVER THE NOTIFICATIONS WINDOW */
	    #notifications-help:before {         
	        content: '';
	        display:block;
	        width:0;
	        height:0;
	        color:transparent;
	        border:10px solid #CCC;
	        border-color:transparent transparent #FFF;
	        margin-top:-20px;
	        margin-left:213px;
	    }
        
	    #notifications-help h3 {
	        display:block;
	        color:#333; 
	        background:#FFF;
	        font-weight:bold;
	        font-size:13px;    
	        padding:8px;
	        margin:0;
	        border-bottom:solid 1px rgba(100, 100, 100, .30);
	    }
	    
	    div.notifications-message-help {
	    	height: 363px; 
	    	overflow-x: visible; 
	    	overflow-y: scroll;
	    }
	    
	    div.notifications-message-box-help {
	    	cursor: pointer;
	    	padding: 1px 15px 0px 15px;
	    }
	    
	    p.notifications-message-text-help {
	    	font-size:13px;
	    	margin: 10px;
	    }
	    
	    b.notifications-message-subject-help {
	    	color: blue;
	    }
	    
	    div.notifications-message-line-help {
	    	height: 1px;
	    	border: none;
	    	border-bottom: 1px solid silver;
	    }
	    
	    img.notifications-message-icon-help {
	    	width: 16px;
	    	height: 16px;
	    }
	        
	    .seeAll {
	        background:#F6F7F8;
	        padding:8px;
	        font-size:12px;
	        font-weight:bold;
	        border-top:solid 1px rgba(100, 100, 100, .30);
	        text-align:center;
	    }
	    .seeAll a {
	        color:#3b5998;
	    }
	    .seeAll a:hover {
	        background:#F6F7F8;
	        color:#3b5998;
	        text-decoration:underline;
	    }
	    
	    div.notifications-message {
	    	height: 363px; 
	    	overflow-x: visible; 
	    	overflow-y: scroll;
	    }
	    
	    div.notifications-message-box {
	    	cursor: pointer;
	    	padding: 1px 15px 0px 15px;
	    }
	    
	    p.notifications-message-text {
	    	font-size:13px;
	    	margin: 10px;
	    }
	    
	    b.notifications-message-subject {
	    	color: blue;
	    }
	    
	    div.line-odd {
	    	background:#F6F7F8;
	    }
	    
	    div.line-event {
	    
	    }
	    
	    div.notifications-message-line {
	    	height: 1px;
	    	border: none;
	    	border-bottom: 1px solid silver;
	    }
	    
	    img.notifications-message-icon {
	    	width: 16px;
	    	height: 16px;
	    }
	    
	    .introjs-tooltiptext {
	    	line-height: 25px;
	    }
	</style>
</head>
<%if (haveUser)  {%>
	<%if (haveMenu)  {%>
		<div id="pushMenuDivId">
			<div id="pushMenuBoxDivId" class="menu-trigger" onclick="pushMenuRestoreState();"></div>
		</div>
		<div id="systemNavigateDivId">
			<div id="systemNavigateTitleDivId"><%=ParameterConfig.getApplication().getTitle() %></div>
			<div id="systemNavigateDetailDivId"><%=htmlHeader%></div>
		</div>
		<!-- mp-menu -->
		<nav id="mp-menu" class="mp-menu">
			<%=htmlMenu%>
		</nav>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				new mlPushMenu( document.getElementById( 'mp-menu' ), document.getElementById( 'pushMenuBoxDivId' ) );	
			});
		</script>
	<%} else { %>
		<div id="pushMenuDivId">
			<div id="pushMenuBoxDivId">
				&nbsp;
			</div>
		</div>
		<div id="systemNavigateDivId">
			<div id="systemNavigateTitleDivId">&nbsp;</div>
			<div id="systemNavigateDetailDivId">&nbsp;</div>
		</div>
	<%} %>
<%} else { %>
	<div id="pushMenuDivId">
		<div id="pushMenuBoxDivId">
			&nbsp;
		</div>
	</div>
	<div id="systemNavigateDivId">
		<div id="systemNavigateTitleDivId">&nbsp;</div>
		<div id="systemNavigateDetailDivId">&nbsp;</div>
	</div>
<%} %>
<div id="profileMenuDivId">
	<div style="float: right;">
		<a tabindex='-1' href="${application.CONTEXT_OWNER}/jsp/calendar/dashboard/initDashboard.action" title="Goto Calendar">
			<img id="image-goto-home" style="width: 41px; height: 41px;" src="${application.CONTEXT_CENTRAL}/images/menu/i_home_48.png"></img>
		</a>
	</div>
	<div style="height: 40px; width: 7px; border: none; border-left: 1px solid silver; float: right;"></div>
	<div style="height: 40px; width: 7px; border: none; float: right;"></div>
	<div style="float: right; text-align: right; width: 46px; height: 42px;">
		<a id="topbar-noti-btn-action" tabindex='-1' href="javascript:void(0);" title="Notification">
			<span class="topbar-noti-badge-image-action"><img id="image-noti-action" class="topbar-noti-badge-image-action" src="${application.CONTEXT_CENTRAL}/images/menu/i_actions_48.png"></img></span>
			<span id="topbar-noti-badge-count-action" class="topbar-noti-badge-count-action"></span>
		</a>
		<div id="notifications-action">
        	<h3>Notification</h3>
            <div id="notifications-message-list-action" class="notifications-message-action"></div>
            <div class="seeAll"><a href="javascript:void(0);">&nbsp;</a></div>
        </div>
	</div>
	<div style="height: 40px; width: 7px; border: none; border-left: 1px solid silver; float: right;"></div>
	<div style="height: 40px; width: 7px; border: none; float: right;"></div>
	<div style="float: right; text-align: right; width: 46px; height: 42px;">
		<a id="topbar-noti-btn" tabindex='-1' href="javascript:void(0);" title="To do list">
			<span class="topbar-noti-badge-image"><img id="image-noti-todo" class="topbar-noti-badge-image" src="${application.CONTEXT_CENTRAL}/images/menu/i_todo_48.png"></img></span>
			<span id="topbar-noti-badge-count" class="topbar-noti-badge-count"></span>
		</a>
		<div id="notifications">
        	<h3>To do list</h3>
            <div id="notifications-message-list" class="notifications-message"></div>
            <div class="seeAll"><a href="javascript:void(0);">&nbsp;</a></div>
        </div>
	</div>
	<div style="height: 40px; width: 7px; border: none; border-left: 1px solid silver; float: right;"></div>
	<div style="height: 40px; width: 7px; border: none; float: right;"></div>
	<div style="float: right; text-align: right; width: 46px; height: 42px;">
		<a id="topbar-noti-btn-help" tabindex='-1' href="javascript:void(0);" title="Help">
			<span class="topbar-noti-badge-image"><img id="image-noti-help" class="topbar-noti-badge-image" src="${application.CONTEXT_CENTRAL}/images/menu/i_help_48.png"></img></span>
			<span id="topbar-noti-badge-count-help" class="topbar-noti-badge-count"></span>
		</a>
		<div id="notifications-help">
        	<h3>Help</h3>
            <div id="notifications-message-list-help" class="notifications-message" style="height: 440px;">
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startMenu01(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนงานเมนู</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startHeader01(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนงาน Navigator Bar</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startCalendar01(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนงานควบคุมการทำงานของปฏิทิน</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startCalendar02(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนสถานะต่างๆ ของห้อง</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startTodoList01(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนแสดงข้อความแจ้งเตือนสิ่งที่ต้องทำ</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startAction01(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนแสดงข้อความแจ้งเตือนทั่วไป</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startEventBooking(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนแสดงข้อมูลบนหน้าปฏิทิน</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startEventStatusBooking(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนแสดงข้อมูลการจอง</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startEventStatusApprove(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนแสดงข้อมูลการอนุมัติ</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startEventStatusCheckIn(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนแสดงข้อมูลการเข้าใช้งาน</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            	<div class="notifications-message-box" onclick="$('#notifications-help').hide();startEventStatusCheckOut(true);">
            		<p class="notifications-message-text">
            			<b class="notifications-message-subject">คำอธิบายส่วนแสดงข้อมูลการคืนห้อง</b>
            		</p>
            		<div class="notifications-message-line"></div>
            	</div>
            </div>
            <div class="seeAll"><a href="javascript:void(0);">&nbsp;</a></div>
        </div>
	</div>
	<div style="height: 40px; width: 7px; border: none; border-left: 1px solid silver; float: right;"></div>
	<div style="height: 40px; width: 7px; border: none; float: right;"></div>
	<%--
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startAll();">0-All</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startMenu01(true);">1-Menu</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startHeader01(true);">2-Header</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startCalendar01(true);">3-Calendar</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startCalendar02(true);">4-Room Status</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startTodoList01(true);">5-To do list</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startAction01(true);">6-Action</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startEventBooking(true);">7-Booking</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startEventStatusBooking(true);">8-StatusBooking</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startEventStatusApprove(true);">9-StatusApprove</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startEventStatusCheckIn(true);">10-StatusCheckIn</a>
	<a class="btn btn-large btn-success" href="javascript:void(0);" onclick="javascript:startEventStatusCheckOut(true);">11-StatusCheckOut</a>
	 --%>
</div>
</html>
<script type="text/javascript">
	var loadNotificationMessageInterval = '';
	jQuery(document).ready(function() {
		jQuery("#mp-menu > div > ul").append("<li><a id='a-logout' href='${application.CONTEXT_OWNER}/jsp/security/permission.jsp' class=class='icon mp-level-open'>Logout</a></li>");
		var space = jQuery( window ).width() - jQuery("TABLE.body").width();
		if (space > 0) {
			jQuery("#notifications").css("right", space / 2);
			jQuery("#notifications-action").css("right", space / 2);
			jQuery("#notifications-help").css("right", space / 2);
		}
		
		// 1287px
		$('#topbar-noti-btn').click(function () {
			$('#notifications-action').hide();
			$('#notifications-help').hide();
			
			stopLoadNotificationMessageInterval();
			loadNotificationMessage();
		    startLoadNotificationMessageInterval();
			
	        // TOGGLE (SHOW OR HIDE) NOTIFICATION WINDOW.
	        $('#notifications').fadeToggle('fast', 'linear', function () {
	            if ($('#notifications').is(':hidden')) {
	                $('#noti_Button').css('background-color', '#2E467C');
	            } else {
	            	$('#noti_Button').css('background-color', '#FFF');        // CHANGE BACKGROUND COLOR OF THE BUTTON.
	            }
			});

		 	$('#noti_Counter').fadeOut('slow');                 // HIDE THE COUNTER.
			return false;
		});
		
		$('#topbar-noti-btn-action').click(function () {
			$('#notifications').hide();
			$('#notifications-help').hide();
			
			stopLoadNotificationMessageInterval();
			loadNotificationMessage();
		    startLoadNotificationMessageInterval();
			
	        // TOGGLE (SHOW OR HIDE) NOTIFICATION WINDOW.
	        $('#notifications-action').fadeToggle('fast', 'linear', function () {
	            if ($('#notifications-action').is(':hidden')) {
	                $('#noti_Button-action').css('background-color', '#2E467C');
	            } else {
	            	$('#noti_Button-action').css('background-color', '#FFF');        // CHANGE BACKGROUND COLOR OF THE BUTTON.
	            }
			});

		 	$('#noti_Counter-action').fadeOut('slow');                 // HIDE THE COUNTER.
			return false;
		});
		
		$('#topbar-noti-btn-help').click(function () {
			$('#notifications-action').hide();
			$('#notifications').hide();
			
	        // TOGGLE (SHOW OR HIDE) NOTIFICATION WINDOW.
	        $('#notifications-help').fadeToggle('fast', 'linear', function () {
	            if ($('#notifications-help').is(':hidden')) {
	                $('#noti_Button-help').css('background-color', '#2E467C');
	            } else {
	            	$('#noti_Button-help').css('background-color', '#FFF');        // CHANGE BACKGROUND COLOR OF THE BUTTON.
	            }
			});

		 	$('#noti_Counter-help').fadeOut('slow');                 // HIDE THE COUNTER.
			return false;
		});
		
		// HIDE NOTIFICATIONS WHEN CLICKED ANYWHERE ON THE PAGE.
        $(document).click(function () {
            // $('#notifications').hide();

            // CHECK IF NOTIFICATION COUNTER IS HIDDEN.
            // if ($('#noti_Counter').is(':hidden')) {
                // CHANGE BACKGROUND COLOR OF THE BUTTON.
            //     $('#noti_Button').css('background-color', '#2E467C');
            // }
        });

        $('#notifications').click(function () {
            return false;       // DO NOTHING WHEN CONTAINER IS CLICKED.
        });
        
        $('#notifications-action').click(function () {
            return false;       // DO NOTHING WHEN CONTAINER IS CLICKED.
        });
        
        loadNotificationMessage();
        startLoadNotificationMessageInterval();
	});
	
	function startLoadNotificationMessageInterval() {
		loadNotificationMessageInterval = setInterval(function(){ 
			loadNotificationMessage();
		}, 30000);		
	}
	
	function stopLoadNotificationMessageInterval() {
		clearInterval(loadNotificationMessageInterval);
	}
	
	function loadNotificationMessage() {
		jQuery.ajax({
			type: "POST"
			, url: "<s:url value='/jsp/notification/ajaxSearchMessageNotification.action'/>"
			, async: true // ไม่รอให้ทำงานจบ
			, global: false
			, success: function(data){
				// console.info(data);
				drawTodoMessage(data);
				drawActionMessage(data);
			}
		});
	}
	
	function drawTodoMessage(data) {
		jQuery("#notifications-message-list").empty();
		
		if (data.totalTodoMessage == 0) {
			jQuery("#topbar-noti-badge-count").fadeOut();
		} else {
			if (jQuery("#topbar-noti-badge-count").html() == (data.totalTodoMessage + "")) {
				// Nothing
			} else {
				jQuery("#topbar-noti-badge-count").fadeOut("slow", function() {
					jQuery(this).html(data.totalTodoMessage);
					jQuery(this).fadeIn("slow");
				});
			}
			
			// console.info(data.listTodoMessage.length);
			
			for(var index = 0; index < data.listTodoMessage.length; index++) {
				var messageNoti = data.listTodoMessage[index];
				var foundNoti = jQuery("div[data-notificationId='" + messageNoti.notificationId + "']").length;
				// console.info(foundNoti);
				if (foundNoti <= 0) {
					var html =""; 
					if ((messageNoti.statusTo == 'CO')
							|| (messageNoti.statusTo == 'C')) {
						html += '<div data-notificationId="' + messageNoti.notificationId + '" class="notifications-message-box" onclick="clearBadgeNotificationMessage(' + messageNoti.notificationId + ');">';
					} else {
						html += '<div data-notificationId="' + messageNoti.notificationId + '" class="notifications-message-box" onclick="gotoEventData(' + messageNoti.eventId + ', \'' + messageNoti.eventStartDate + '\');">';	
					}
					
					html += "<p class=\"notifications-message-text\">";
					if (messageNoti.statusTo == 'W') {
						html += "<img class='notifications-message-icon' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/booking.png'>";	
					} else if (messageNoti.statusTo == 'A') {
						html += "<img class='notifications-message-icon' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png'>";	
					} else if (messageNoti.statusTo == 'CI') {
						html += "<img class='notifications-message-icon' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png'>";	
					} else if (messageNoti.statusTo == 'CO') {
						html += "<img class='notifications-message-icon' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png'>";	
					} else if (messageNoti.statusTo == 'C') {
						html += "<img class='notifications-message-icon' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png'>";	
					} else if (messageNoti.statusTo == 'NI') {
						html += "<img class='notifications-message-icon' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/not_check_in.png'>";	
					} else if (messageNoti.statusTo == 'NO') {
						html += "<img class='notifications-message-icon' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/not_check_out.png'>";	
					}
					
					html += "&nbsp;"
					html += "<b class='notifications-message-subject'>" + messageNoti.subject + "</b> " + messageNoti.roomName + "&nbsp;&nbsp;&nbsp;<b>ผู้แจ้ง:</b> " + messageNoti.userName + "<br/>";
					html += "<b>หัวข้อ:</b> " + messageNoti.eventSubject + "<br/>";
					html += "<b>วันที่:</b> " +  messageNoti.eventStartDate + "&nbsp;&nbsp;&nbsp;<b>เวลา:</b> " + messageNoti.eventStartTime + " - " + messageNoti.eventEndTime + "<br/>";
					html += "<b>วันที่แจ้ง:</b> " +  messageNoti.createDateTime + "<br/>";
					html += "</p>";
					html += "<div class='notifications-message-line'></div>";
					html += "</div>";
					
					
					var $newMessage = $(html);
					jQuery("#notifications-message-list").prepend($newMessage);
				}
			}
			
			jQuery("div.notifications-message-box").each(function(index) {
				if ((index % 2) == 1) {
					jQuery(this).addClass("line-even");							
				} else {
					jQuery(this).addClass("line-odd");	
				}
			});
		}
	}

	function drawActionMessage(data) {
		jQuery("#notifications-message-list-action").empty();
		
		if (data.totalActionMessage == 0) {
			jQuery("#topbar-noti-badge-count-action").fadeOut();
		} else {
			if (jQuery("#topbar-noti-badge-count-action").html() == (data.totalActionMessage + "")) {
				// Nothing
			} else {
				jQuery("#topbar-noti-badge-count-action").fadeOut("slow", function() {
					jQuery(this).html(data.totalActionMessage);
					jQuery(this).fadeIn("slow");
				});
			}
			
			// console.info(data.listTodoMessage.length);
			
			for(var index = 0; index < data.listActionMessage.length; index++) {
				var messageNoti = data.listActionMessage[index];
				var foundNoti = jQuery("div[data-notificationId='" + messageNoti.notificationId + "']").length;
				// console.info(foundNoti);
				if (foundNoti <= 0) {
					var html =""; 
					if ((messageNoti.statusTo == 'CO')
							|| (messageNoti.statusTo == 'C')) {
						html += '<div data-notificationId="' + messageNoti.notificationId + '" class="notifications-message-box-action" onclick="clearBadgeNotificationMessage(' + messageNoti.notificationId + ');">';
					} else {
						html += '<div data-notificationId="' + messageNoti.notificationId + '" class="notifications-message-box-action" onclick="gotoEventData(' + messageNoti.eventId + ', \'' + messageNoti.eventStartDate + '\');">';	
					}
					
					html += "<p class='notifications-message-text-action'>";
					if (messageNoti.statusTo == 'W') {
						html += "<img class='notifications-message-icon-action' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/booking.png'>";	
					} else if (messageNoti.statusTo == 'A') {
						html += "<img class='notifications-message-icon-action' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png'>";	
					} else if (messageNoti.statusTo == 'CI') {
						html += "<img class='notifications-message-icon-action' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png'>";	
					} else if (messageNoti.statusTo == 'CO') {
						html += "<img class='notifications-message-icon-action' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png'>";	
					} else if (messageNoti.statusTo == 'C') {
						html += "<img class='notifications-message-icon-action' src='${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png'>";	
					}
					html += "&nbsp;"
					html += "<b class='notifications-message-subject-action'>" + messageNoti.subject + "</b> " + messageNoti.roomName + "&nbsp;&nbsp;&nbsp;<b>ผู้แจ้ง:</b> " + messageNoti.userName + "<br/>";
					html += "<b>หัวข้อ:</b> " + messageNoti.eventSubject + "<br/>";
					html += "<b>วันที่:</b> " +  messageNoti.eventStartDate + "&nbsp;&nbsp;&nbsp;<b>เวลา:</b> " + messageNoti.eventStartTime + " - " + messageNoti.eventEndTime + "<br/>";
					html += "<b>วันที่แจ้ง:</b> " +  messageNoti.createDateTime + "<br/>";
					html += "</p>";
					html += "<div class='notifications-message-line-action'></div>";
					html += "</div>";
					
					
					var $newMessage = $(html);
					jQuery("#notifications-message-list-action").prepend($newMessage);
				}
			}
			
			jQuery("div.notifications-message-box-action").each(function(index) {
				if ((index % 2) == 1) {
					jQuery(this).addClass("line-even");							
				} else {
					jQuery(this).addClass("line-odd");	
				}
			});
		}
	}
	
	function gotoEventData(eventId, bookingDay) {
		if (jQuery("#scheduler_here").length == 0) {
			// อยู่บนหน้าอื่นๆ ที่ไม่ใช่หน้าปฏิทิน
			jQuery("form").append("<input type='hidden' name='tempEventId' id='tempEventId'>");
			jQuery("form").append("<input type='hidden' name='tempCurrentDay' id='tempCurrentDay'>");

			jQuery("#tempEventId").val(eventId);
			jQuery("#tempCurrentDay").val(bookingDay);
			submitPage("<s:url value='/jsp/booking/gotoEventDataRoomBooking.action' />");
			return;
		}
		
		var timeout = 10;
		var haveEvent = jQuery("div[event_id='" + eventId + "']").length;
		if (haveEvent == 0) {
			// ไม่เจอ event ให้โหลดใหม่
			currentDay = bookingDay

			stopLoadEventSchedulerInterval();		
			loadEventScheduler();
			startLoadEventSchedulerInterval();	
			timeout = 1000;
		}
		
		setTimeout( function() {
				focusEventData(eventId);
		}, timeout)
	}
	
	function focusEventData(eventId) {
		if (jQuery("div[event_id='" + eventId + "']").length == 1) {
			jQuery("div[event_id='" + eventId + "']").click();
			var eventOffsetTop = $("div[event_id='" + eventId + "']").css('top').replace('px', '');
			$('div.dhx_cal_data').animate({
				scrollTop: eventOffsetTop
			}, 1000);
		
			jQuery("div[event_id='" + eventId + "']").fadeOut("slow", function() {
				jQuery(this).fadeIn("slow");
			});
		}
	}
	
	function clearBadgeNotificationMessage(notificationId) {
		stopLoadNotificationMessageInterval();
		jQuery.ajax({
			type: "POST"
			, url: "<s:url value='/jsp/notification/ajaxClearMessageNotification.action'/>"
			, async: true // ไม่รอให้ทำงานจบ
			, data: {'notificationId': notificationId }
			, global: false
			, success: function(data){
				loadNotificationMessage();
				startLoadNotificationMessageInterval();
			}
		});
	}
	
	function startMenu01(isContiune){
        var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
            , steps: [
	              {
	                  element: document.querySelector('#pushMenuBoxDivId'),
	                  intro: "เมนูระบบ"
	              }
              ]
          });
          
		intro.onafterchange(function(targetElement) {
			jQuery("#mp-pusher").removeClass('introjs-fixParent').addClass('mp-pushed').css('transform', 'translate3d(300px, 0px, 0px)').css('zIndex', 9999999);
        	jQuery("div[data-level='1']").addClass('mp-level-open');
        	jQuery(".introjs-helperLayer ").css('transform', 'translate3d(300px, 0px, 0px)');
        	jQuery(".introjs-tooltipReferenceLayer").css('transform', 'translate3d(300px, 0px, 0px)');
        	setTimeout(function() {
        		jQuery("#scheduler_here").css("zIndex", 0);	
        	}, 150);
		});
		
		intro.setOptions({
			doneLabel: 'ถัดไป'
		});
        intro.start().onexit(function() {
        	startMenu02(isContiune);	
		});
          
          
      }
	
	function startMenu02(isContiune) {
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
                {
                	element: document.getElementById('10220000'),
        		    intro: "ระบบจองห้องประชุม"
                }
                , {
                	element: document.getElementById('10230000'),
        		    intro: "ระบบแสดงข้อมูลการจองในรูปปฏิทิน"
                }
                , {
                    element: document.querySelector('#a-logout'),
                    intro: "ออกจากระบบ"
                }
            ]
		});
		
		intro.onafterchange(function(targetElement) {
			jQuery("#mp-pusher").removeClass('introjs-fixParent').addClass('mp-pushed').css('transform', 'translate3d(300px, 0px, 0px)').css('zIndex', 9999999);
			jQuery("#mp-menu").removeClass('introjs-fixParent');
			jQuery("div[data-level='1']").removeClass('introjs-fixParent').addClass('mp-level-open');
    		jQuery(".introjs-helperLayer ").css('zIndex', 9999999).css('background', 'none');
    		setTimeout(function() {
        		jQuery("#scheduler_here").css("zIndex", 0);	
        	}, 150);
		});
		
		intro.setOptions({
			doneLabel: 'เสร็จสิ้น'
   			, nextLabel: 'ถัดไป'
   			, prevLabel: 'ย้อนกลับ'
   			, skipLabel: 'ข้าม'
		});
		
        intro.start().onexit(function() {
        	jQuery("#mp-pusher").removeClass('mp-pushed').css('transform', '').css('zIndex', '');
			jQuery("div[data-level='1']").removeClass('mp-level-open');
			jQuery("#scheduler_here").css("zIndex", '');	
			if (isContiune == undefined) {
				startHeader01();
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
	}
	
	function startHeader01(isContiune) {
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
                {
                	element: document.getElementById('systemNavigateDetailDivId'),
        		    intro: "แสดงชื่อระบบที่กำลังใช้งานอยู่"
                }
                ,{
                	element: document.getElementById('image-noti-todo'),
        		    intro: "แสดงจำนวนข้อความแจ้งเตือนสิ่งที่ต้องทำ"
                }
                ,{
                	element: document.getElementById('notifications'),
        		    intro: "แสดงข้อความ<em class='heightlight'>แจ้งเตือนสิ่งที่ต้องทำ</em><br>โดยเรียงจากลำดับจากเหตุการณ์ที่ขึ้นเกิดก่อน"
                }
                ,{
                	element: document.getElementById('image-noti-action'),
        		    intro: "แสดงจำนวนข้อความแจ้งเตือนทั่วไป"
                } 
                ,{
                	element: document.getElementById('notifications-action'),
        		    intro: "แสดงข้อความแจ้งเตือนทั่วไป<br>โดยเรียงจากลำดับจากเหตุการณ์ที่ขึ้นเกิดก่อน"
                }
                ,{
                	element: document.getElementById('image-goto-home'),
        		    intro: "กลับหน้าหลัก"
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
			// alert('onbeforechange: ' + targetElement.id);
			if (targetElement.id == 'notifications') {
				jQuery('#notifications').show();
				jQuery('#notifications-action').hide();
			} else if (targetElement.id == 'notifications-action') {
				jQuery('#notifications').hide();
				jQuery('#notifications-action').show();
			} else {
				jQuery('#notifications').hide();
				jQuery('#notifications-action').hide();
			}
		});
		
		
		intro.start().onexit(function() {
			if (isContiune == undefined) {
				startCalendar01();
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
	}
	
	function startTodoList01(isContiune) {
		stopLoadNotificationMessageInterval();
		createDemoTodoList();
		jQuery('#notifications').show();
		
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
                {
                   	element: document.getElementById('notifications'),
        		    intro: "แสดงข้อความ<em class='heightlight'>แจ้งเตือนสิ่งที่ต้องทำ</em> โดยเรียงจากลำดับจากเหตุการณ์ที่ขึ้นเกิดก่อน",
        		    position: 'left'
                }, {
                   	element: document.getElementById('notifications-message-box-1'),
        		    intro: '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/booking.png">&nbsp;<b class="notifications-message-subject">แจ้งขอใช้งาน</b><br>แจ้งเตือนเพื่อรับทราบและให้ผู้ดูแลระบบ <img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png" class="event-data-form-btn-icon" />&nbsp;<s:text name="bk.approve"/>&nbsp;ให้ใช้งานห้อง โดยชลัช (ลัช) ผู้ทำรายการ',
        		    position: 'left'
        		    
                }, {
                   	element: document.getElementById('notifications-message-box-2'),
        		    intro: '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/not_check_in.png">&nbsp;<b class="notifications-message-subject">แจ้งไม่มีการเข้าใช้งาน</b><br>แจ้งเตือนเพื่อรับทราบและทำการบันทึก <img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png" class="event-data-form-btn-icon" />&nbsp;<s:text name="bk.checkIn"/><br>โดยระบบอัตโนมัติ',
        		    position: 'left'
                }, {
                   	element: document.getElementById('notifications-message-box-3'),
                   	intro: '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/not_check_out.png">&nbsp;<b class="notifications-message-subject">แจ้งไม่มีการคืนห้อง</b><br>แจ้งเตือนเพื่อรับทราบและทำการบันทึก <img src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png" class="event-data-form-btn-icon" />&nbsp;<s:text name="bk.checkOut"/><br>โดยระบบอัตโนมัติ',
        		    position: 'left'
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
			jQuery("#notifications").addClass("introjs-showElement");
		});
		
		intro.start().onexit(function() {
			jQuery('#notifications').removeClass("introjs-showElement");
			jQuery('#notifications').hide();
			jQuery("#notifications-message-list").empty();
			if (isContiune == undefined) {
				startAction01();
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
		
	}
	
	function createDemoTodoList() {
		var html = ''; 
		html += '<div class="notifications-message-box line-odd">'; 
		html += '<p id="notifications-message-box-1" class="notifications-message-text">'; 
		html += '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/booking.png">&nbsp;<b class="notifications-message-subject">แจ้งขอใช้งาน</b> ห้องประชุม 1&nbsp;&nbsp;&nbsp;<b>ผู้แจ้ง:</b> ชลัช (ลัช)<br>'; 
		html += '<b>หัวข้อ:</b> ประชุมจ่ายงาน APPS<br>'; 
		html += '<b>วันที่:</b> 21/08/2017&nbsp;&nbsp;&nbsp;<b>เวลา:</b> 08:30 - 16:00<br>'; 
		html += '<b>วันที่แจ้ง:</b> 20/08/2017 09:30<br>'; 
		html += '</p>'; 
		html += '<div class="notifications-message-line"></div>'; 
		html += '</div>'; 
	
		html += '<div class="notifications-message-box line-even">'; 
		html += '<p id="notifications-message-box-2" class="notifications-message-text">'; 
		html += '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/not_check_in.png">&nbsp;<b class="notifications-message-subject">แจ้งไม่มีการเข้าใช้งาน</b> ห้องประชุม 1&nbsp;&nbsp;&nbsp;<b>ผู้แจ้ง:</b> ระบบอัตโนมัติ<br>'; 
		html += '<b>หัวข้อ:</b> ประชุมจ่ายงาน APPS<br>'; 
		html += '<b>วันที่:</b> 21/08/2017&nbsp;&nbsp;&nbsp;<b>เวลา:</b> 08:30 - 16:00<br>'; 
		html += '<b>วันที่แจ้ง:</b> 21/08/2017 08:40<br>'; 
		html += '</p>'; 
		html += '<div class="notifications-message-line"></div>'; 
		html += '</div>'; 
	
		html += '<div class="notifications-message-box line-odd">'; 
		html += '<p id="notifications-message-box-3" class="notifications-message-text">'; 
		html += '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/not_check_out.png">&nbsp;<b class="notifications-message-subject">แจ้งไม่มีการคืนห้อง</b> ห้องประชุม 1&nbsp;&nbsp;&nbsp;<b>ผู้แจ้ง:</b> ระบบอัตโนมัติ<br>'; 
		html += '<b>หัวข้อ:</b> ประชุมจ่ายงาน APPS<br>'; 
		html += '<b>วันที่:</b> 21/08/2017&nbsp;&nbsp;&nbsp;<b>เวลา:</b> 08:30 - 16:00<br>'; 
		html += '<b>วันที่แจ้ง:</b> 21/08/2017 16:10<br>'; 
		html += '</p>'; 
		html += '<div class="notifications-message-line"></div>'; 
		html += '</div>'; 
			
		jQuery("#notifications-message-list").empty();
		jQuery("#notifications-message-list").prepend(html);
	}
	
	function startAction01(isContiune) {
		stopLoadNotificationMessageInterval();
		createDemoAction();
		jQuery('#notifications-action').show();
		
		var intro = introJs();
		intro.setOptions({
   			showBullets: false
   			, showProgress: true
   			, steps: [
                {
                	element: document.getElementById('notifications-action'),
        		    intro: "แสดงข้อความ<em class='heightlight'>แจ้งเตือนทั่วไป</em>โดยเรียงจากลำดับจากเหตุการณ์ที่ขึ้นเกิดก่อน",
        		    position: 'left'
                }, {
                   	element: document.getElementById('notifications-message-box-1'),
                   	intro: '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png">&nbsp;<b class="notifications-message-subject">แจ้งอนุมัติใช้งาน</b>'
                   			+ '<br>ข้อความแจ้งไปยัง<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em>รับทราบว่า<em class="heightlight-black">ผู้ดูแลระบบ</em>ได้อนุญาตให้ใช้งานห้องได้ โดยพีรพล (โอ๋) ผู้ทำรายการ',
        		    position: 'left'
        		    
                }, {
                   	element: document.getElementById('notifications-message-box-2'),
                   	intro: '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png">&nbsp;<b class="notifications-message-subject">แจ้งเข้าใช้งาน</b>'
               				+ '<br>ข้อความแจ้งไปยัง<em class="heightlight-black">ผู้ดูแลระบบ</em>รับทราบว่ามีการเข้าใช้งานห้องแล้ว โดยชลัช (ลัช) ผู้ทำรายการ',
        		    position: 'left'
                }, {
                   	element: document.getElementById('notifications-message-box-3'),
                   	intro: '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png">&nbsp;<b class="notifications-message-subject">แจ้งคืนห้อง</b>'
               				+ '<br>ข้อความแจ้งไปยัง<em class="heightlight-black">ผู้ดูแลระบบ</em>รับทราบว่ามีการคืนห้องแล้ว โดยชลัช (ลัช) ผู้ทำรายการ',
        		    position: 'left'
                }, {
                   	element: document.getElementById('notifications-message-box-4'),
                   	intro: '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png">&nbsp;<b class="notifications-message-subject">แจ้งคืนห้อง</b>'
           					+ '<br>ข้อความแจ้งไปยัง<em class="heightlight-black">ผู้ขอใช้</em>หรือ<em class="heightlight-black">ผู้แจ้ง</em>และ<em class="heightlight-black">ผู้ดูแลระบบ</em>รับทราบว่ามีการยกเลิกการใช้งานห้องแล้ว โดยณัฐฐินี (บลู) ผู้ทำรายการ',
        		    position: 'left'
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
			jQuery("#notifications-action").addClass("introjs-showElement");
		});
		
		intro.start().onexit(function() {
			jQuery('#notifications-action').removeClass("introjs-showElement");
			jQuery('#notifications-action').hide();
			jQuery("#notifications-message-list-action").empty();
			if (isContiune == undefined) {
				startEventBooking();
			} else {
				alert('จบการแนะนำแล้วนะครับ\nหากต้องการดูซ้ำให้คลิกที่ Help ในส่วนของ Navigator Bar ด้านบน');
				window.location = "<s:url value='/jsp/calendar/dashboard/initDashboard.action' />";
			}
		});
		
	}
	
	function createDemoAction() {
		var html = ''; 
		html += '<div class="notifications-message-box line-odd">'; 
		html += '<p id="notifications-message-box-1" class="notifications-message-text">'; 
		html += '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/approve.png">&nbsp;<b class="notifications-message-subject">แจ้งอนุมัติใช้งาน</b> ห้องประชุม 1&nbsp;&nbsp;&nbsp;<b>ผู้แจ้ง:</b> พีรพล (โอ๋)<br>'; 
		html += '<b>หัวข้อ:</b> ประชุมจ่ายงาน APPS<br>'; 
		html += '<b>วันที่:</b> 21/08/2017&nbsp;&nbsp;&nbsp;<b>เวลา:</b> 08:30 - 16:00<br>'; 
		html += '<b>วันที่แจ้ง:</b> 20/08/2017 10:30<br>'; 
		html += '</p>'; 
		html += '<div class="notifications-message-line"></div>'; 
		html += '</div>'; 
	
		html += '<div class="notifications-message-box line-even">'; 
		html += '<p id="notifications-message-box-2" class="notifications-message-text">'; 
		html += '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_in.png">&nbsp;<b class="notifications-message-subject">แจ้งเข้าใช้งาน</b> ห้องประชุม 1&nbsp;&nbsp;&nbsp;<b>ผู้แจ้ง:</b> ชลัช (ลัช)<br>'; 
		html += '<b>หัวข้อ:</b> ประชุมจ่ายงาน APPS<br>'; 
		html += '<b>วันที่:</b> 21/08/2017&nbsp;&nbsp;&nbsp;<b>เวลา:</b> 08:30 - 16:00<br>'; 
		html += '<b>วันที่แจ้ง:</b> 21/08/2017 08:25<br>'; 
		html += '</p>'; 
		html += '<div class="notifications-message-line"></div>'; 
		html += '</div>'; 
	
		html += '<div class="notifications-message-box line-odd">'; 
		html += '<p id="notifications-message-box-3" class="notifications-message-text">'; 
		html += '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/check_out.png">&nbsp;<b class="notifications-message-subject">แจ้งคืนห้อง</b> ห้องประชุม 1&nbsp;&nbsp;&nbsp;<b>ผู้แจ้ง:</b> ชลัช (ลัช)<br>'; 
		html += '<b>หัวข้อ:</b> ประชุมจ่ายงาน APPS<br>'; 
		html += '<b>วันที่:</b> 21/08/2017&nbsp;&nbsp;&nbsp;<b>เวลา:</b> 08:30 - 16:00<br>'; 
		html += '<b>วันที่แจ้ง:</b> 21/08/2017 16:05<br>'; 
		html += '</p>'; 
		html += '<div class="notifications-message-line"></div>'; 
		html += '</div>'; 
	
		html += '<div class="notifications-message-box line-even">'; 
		html += '<p id="notifications-message-box-4" class="notifications-message-text">'; 
		html += '<img class="notifications-message-icon" src="${application.CONTEXT_CENTRAL}/images/scheduler/icon_pin/cancel.png">&nbsp;<b class="notifications-message-subject">แจ้งยกเลิก</b> ห้องประชุมใหญ่&nbsp;&nbsp;&nbsp;<b>ผู้แจ้ง:</b> ณัฐฐินี (บลู)<br>'; 
		html += '<b>หัวข้อ:</b> ประชุมภายในฝ่าย PG<br>'; 
		html += '<b>วันที่:</b> 23/08/2017&nbsp;&nbsp;&nbsp;<b>เวลา:</b> 09:00 - 10:00<br>'; 
		html += '<b>วันที่แจ้ง:</b> 23/08/2017 09:30<br>'; 
		html += '</p>'; 
		html += '<div class="notifications-message-line"></div>'; 
		html += '</div>'; 
			
		jQuery("#notifications-message-list-action").empty();
		jQuery("#notifications-message-list-action").prepend(html);
	}
</script> 