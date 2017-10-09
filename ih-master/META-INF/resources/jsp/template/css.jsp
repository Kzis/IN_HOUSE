<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/theme/cupertino/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/theme/jquery-ui-theme-custom.css" />
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/ui/pagination.css"/>
<!--[if lte IE 8]>
	<link href="<s:url value='/css/default-ie.css' />" rel="stylesheet" type="text/css"/>
	<style type="text/css">
		.THEAD_SCROLL_Y {
			position: absolute !important; 
			margin-left: -0.5px !important;
		}
		.TABLE_SCROLL_Y {
		
		}
	</style>
<![endif]-->

<!--[if gte IE 9]>
	<link href="<s:url value='/css/default-ie.css' />" rel="stylesheet" type="text/css"/>
	<style type="text/css">
		.THEAD_SCROLL_Y {
			position: absolute !important; 
			margin-left: -0.5px !important;
		}
		.TABLE_SCROLL_Y {
			margin-left: -0.5px !important;
		}
	</style>	
<![endif]-->		

<!--[if !IE]><!-->
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/default-not-ie.css" />
	<style type="text/css">
		.THEAD_SCROLL_Y {
			position: absolute !important; 
			margin-left: -1px !important;
		}
		.TABLE_SCROLL_Y {
		
		}
	</style>
<!--<![endif]-->
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/css1600.css"/>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/ui/datatables/jqueryui/css/jquery.dataTables.css"/>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/ui/datatables/jqueryui/css/jquery.dataTables_themeroller.css"/>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/pushmenu/normalize.css"/>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/pushmenu/icons.css"/>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/pushmenu/component.css"/>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/notify/jquery.notify.css"/>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/ui/resize.css"/>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/ui/picklist/css/picklist.css"/>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/ui/loader/css/loader.css"/>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/ui/button/jqueryui/css/button.css"/>


