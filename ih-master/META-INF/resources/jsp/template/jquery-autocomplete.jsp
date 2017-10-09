<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/autocomplete/autocomplete.css"/>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/autocomplete/autocomplete.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/autocomplete/autocomplete-ajax_2.1.0.js"></script>
<script type="text/javascript">
	// กำหนด config ต่างๆ ของ autocomplate
	var autocompleteConfig = {
		contextPath: CONTEXT_CENTRAL,
		msgShowAll: validateMessage.CODE_CLICKINFO,			// กดเพื่อแสดงข้อมูล
		msgNotMatch: validateMessage.CODE_30011,			// ไม่พบข้อมูลที่ต้องการ
		msgMinChar: validateMessage.CODE_10026				// ระบุเงื่อนไขในการค้นหาอย่างน้อย xxx ตัวอักษร
	};
</script>