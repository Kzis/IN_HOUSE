<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>

<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/jquery-progress.css"/>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/jquery/jquery-progress.js"></script>
<script type="text/javascript">
	var progressValue = {
		msgSuccess: validateMessage.CODE_30006			// ประมวลผลข้อมูลสำเร็จ
	};
	
	jQuery( document ).ready(function() {
		// สร้าง progress bar ให้ auto จาก div ที่มี class .progressbar
		jQuery("div .progressbar_cct").progressbartext({});
	});
</script>
