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
<%@ page import="com.cct.inhouse.timeoffset.util.log.LogUtil"%>
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
</html>
<script type="text/javascript">
	var loadNotificationMessageInterval = '';
	jQuery(document).ready(function() {
		jQuery("#mp-menu > div > ul").append("<li><a id='a-logout' href='${application.CONTEXT_OWNER}/jsp/security/permission.jsp' class=class='icon mp-level-open'>Logout</a></li>");
	});
</script> 