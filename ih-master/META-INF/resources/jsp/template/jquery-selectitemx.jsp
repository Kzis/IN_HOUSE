<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>

<link type="text/css" rel="stylesheet" href="${application.CONTEXT_CENTRAL}/css/autocomplete/autocomplete.css"/>
<script type="text/javascript">
	// กำหนด config ต่างๆ ของ autocomplate
	var selectitemxConfig = {
		contextPath: CONTEXT_CENTRAL,
		msgShowAll: validateMessage.CODE_CLICKINFO,			// กดเพื่อแสดงข้อมูล
		msgNotMatch: validateMessage.CODE_30011,			// ไม่พบข้อมูลที่ต้องการ
		msgMinChar: validateMessage.CODE_10026				// ระบุเงื่อนไขในการค้นหาอย่างน้อย xxx ตัวอักษร
	};
</script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/selectitem/selectitemx-0.0.1.js"></script>
