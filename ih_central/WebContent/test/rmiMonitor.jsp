<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title>RMI Check Status</title>
	<style>
		/* bashboard background */
		div.dashboard {
			background-color: #ecf0f5;
			padding-left: 15px;
			padding-right: 15px;
		}
		
		iframe.dashboard {
			width: 100%;
			padding-left: 15px;
			padding-right: 15px;
		}
		
		.col-dashboard {
			padding-left: 0px !important;  
			padding-right: 0px !important;
		}
		
		/* box */
		.box {
		    position: relative;
		    border-radius: 3px;
		    background: #ffffff;
		    border-top: 3px solid #d2d6de;
/* 			margin: 20px; */
			margin-top: 20px;
			margin-left: 5%;
			margin-right: 5%;
		    width: auto;
		    box-shadow: 0 1px 1px rgba(0,0,0,0.1);
		}
		
		.box.box-success {
		    border-top-color: #5cb85c;
		}
		
		.box.box-info {
		    border-top-color: #5bc0de;
		}
		
		.box.box-warning {
		    border-top-color: #f0ad4e;
		}
		
		.box.box-danger {
		    border-top-color: #dd4b39;
		}
		
		.box-header {
		    color: #444;
		    display: block;
		    padding: 10px;
		    position: relative;
		}
		
		.box-header.with-border {
		    border-bottom: 1px solid #f4f4f4;
		}
		
		.box-header .box-title {
		    display: inline-block;
		    font-size: 18px;
		    margin: 0;
		    line-height: 1;
	 	    font-weight: bold;
		}
		
		.box-body {
		    border-top-left-radius: 0;
		    border-top-right-radius: 0;
		    border-bottom-right-radius: 3px;
		    border-bottom-left-radius: 3px;
		    padding: 10px;
		}
		
		.box-body .box-scroll {
		    border-bottom-right-radius: 0;
		    border-bottom-left-radius: 0;
		    position: relative;
		    overflow-x: hidden;
		    padding: 0;
		}
	
		.box-footer {
		    border-top-left-radius: 0;
		    border-top-right-radius: 0;
		    border-bottom-right-radius: 3px;
		    border-bottom-left-radius: 3px;
		    border-top: 1px solid #f4f4f4;
		    padding: 10px;
		    background-color: #fff;
		}
		
		/* label */
		.label {
		    display: inline;
		    padding: .2em .6em .3em;
		    font-size: 75%;
		    font-weight: 700;
		    line-height: 1;
		    color: #fff;
		    text-align: center;
		    white-space: nowrap;
		    vertical-align: baseline;
		    border-radius: .25em;
		}
		
		.label-success {
	    	background-color: #5cb85c !important;
	    }
	    
		.label-info {
	    	background-color: #5bc0de !important;
	    }
	    
		.label-warning {
	    	background-color: #f0ad4e !important;
	    }
	    
		.label-danger {
	    	background-color: #dd4b39 !important;
	    }
	    
	    .label-secondary {
	    	color: #333;
			background-color: #DDDDDD !important;
		}
		
		/* Text */
		.text-main {
		    font-weight: 600;
		    color: #444;
		    overflow: hidden;
		    white-space: nowrap;
		    text-overflow: ellipsis;
		    display: block;
		    text-align: center;
		    margin-left: -10px;
			margin-right: -10px;
		}
		
		.text-details {
		    color: #999;
		    font-size: 12px;
		    margin-bottom: 10px;
		    text-align: center
		}
		
		.text-center {
			text-align: center !important;
		}
		
		/* list */
		.list-in-box {
		    list-style: none;
		    margin: 0;
		    padding: 0;
		}
	
		.list-in-box > .item {
		    -webkit-box-shadow: none;
		    box-shadow: none;
		    border-radius: 0;
		    border-bottom: 1px solid #f4f4f4;
 		    padding: 20px 50px;
 		    display: -webkit-flex;
    		display: flex;
		}
		
		body {
			background-color: #ecf0f5;
		}
		
		.left {
			width: 80%;
			text-align: left;
		}
		
		.middle {
			width: 10%;
			text-align: right;
		}
		
		.right {
			width: 10%;
			text-align: right;
		}
		
		.img-loader {
			display: none;
			width: 100%;
			height: 100px;
			position: absolute;
			text-align: center;
 			top: 20%;
 			left: auto;
		}
		
	</style>
	
	<script type="text/javascript" src="${CONTEXT_CENTRAL}/js/jquery/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="${CONTEXT_CENTRAL}/js/jquery/jquery-ui-1.12.1.custom.min.js"></script>
	
	<script type="text/javascript">
		
		function sf() {
			jQuery(document).ajaxStart(function(){
				jQuery("#loading").show();
			});

			jQuery(document).ajaxComplete(function(){
				jQuery("#loading").fadeOut(1000);
			});
			
			jQuery(document).ajaxError(function(){
				jQuery("#loading").fadeOut(1000);
			});
			
			checkRmi();
		}
		
		function checkRmi() {
			var urlAction = "${CONTEXT_CENTRAL}/RMIMonitorServlet";
			
			jQuery.ajax({
				url: urlAction,
				type: "POST",
				dataType: "json",
				async: false,
			    success: function(data) {
// 			    	console.log(data)
// 			    	console.log(data.lstRegistereds)
// 			    	console.log(data.portStatus)
			    	
			    	if (data != null) {
			    		// Clear dld data
			    		jQuery("#idStauts").html("");
			    		
			    		var htmlTmp = "";
						var txtRmiNameId = "";
						var txtRmiStatusId = "";
						var txtRmiButtonId = "";
						
			    		
						// Open port
						txtRmiNameId = "idPortName";
						txtRmiStatusId = "idPortStatus";
						txtRmiButtonId = "idPortButton";
						
						htmlTmp = getItemHtml(txtRmiNameId, txtRmiStatusId, txtRmiButtonId);
						jQuery("#idStauts").append(htmlTmp);
						
						jQuery("#" + txtRmiNameId).html("Central Port");
						jQuery("#" + txtRmiButtonId).hide();
						
						if (data.portStatus == "Y") {
							jQuery("#" + txtRmiStatusId).removeClass("label-warning").addClass("label-success").html("Ready");
						} else {
							jQuery("#" + txtRmiStatusId).removeClass("label-warning").addClass("label-danger").html("Error");
						}
						
						
						for (var i = 0; i < data.lstRegistereds.length; i++) {
							var regist = data.lstRegistereds[i];
// 							console.log(regist.registKey)
// 							console.log(regist.registName)
// 							console.log(regist.registUrl)
// 							console.log(regist.registStatus)
// 							console.log(regist.active)
							
							txtRmiNameId = "idRmiName" + i;
							txtRmiStatusId = "idRmiStatus" + i;
							txtRmiButtonId = "idRmiButton" + i;
							
							htmlTmp = getItemHtml(txtRmiNameId, txtRmiStatusId, txtRmiButtonId);
							jQuery("#idStauts").append(htmlTmp);
							
							jQuery("#" + txtRmiNameId).html(regist.registName);
							jQuery("#" + txtRmiButtonId).attr("onClick", "registerRMI('" + regist.registUrl + "')");
							
							if (regist.registStatus == "Y") {
								jQuery("#" + txtRmiStatusId).removeClass("label-warning").addClass("label-success").html("Ready");
							} else {
								jQuery("#" + txtRmiStatusId).removeClass("label-warning").addClass("label-danger").html("Error");
							}
						}
			    	}
			    },
			    error: function(event, jqXHR, ajaxSettings, thrownError) {
			    	alert("Please check URL Servlet!")
			    	jQuery("span.label").attr("class", "").addClass("label label-warning").html("Unknown");
			    }
			})
			
		}
		
		function getItemHtml(txtRmiNameId, txtRmiStatusId, txtRmiButtonId) {
			return htmlTemp = '<div class="item"><div class="left"><span id="' + txtRmiNameId + '"></span></div><div class="middle"><input type="button" id="' + txtRmiButtonId + '" value="Register"></div><div class="right"><span id="' + txtRmiStatusId + '" class="label label-warning">Unknown</span></div></div>';
		}
		
		function registerRMI(url) {
			console.log(url)
			
			jQuery.ajax({
				url: url,
				type: "POST",
				async: false,
			    complete: function(xhr, textStatus) {
			        if (xhr.status == 200) {
			        	checkRmi();
			        }
			    },
			    error: function(event, jqXHR, ajaxSettings, thrownError) {
			    	alert("Can not Regist RMI!")
			    }
			})
		}
			
	</script>
</head>
<body onload="sf()">
	<div class="col-dashboard">
		<div class="box box-danger">
			<div class="box-header with-border">
				<div class="box-title">RMI Check Status</div>
			</div>
	
			<div class="box-body">
				<div class="list-in-box" id="idStauts">
					
				</div>
			</div>
			
			<div class="box-footer text-center">
				<a href="javascript:checkRmi();" class="uppercase" style="color: #72afd2;">
					<span>Refresh</span>
				</a>
			</div>
		</div>
	</div>
	
	<div id="loading" class="img-loader">
		<img src="${CONTEXT_CENTRAL}/images/loading.gif" width="32" height="32" />
		<br><br>
		<span>Loading...</span>
	</div>
</body>
</html>