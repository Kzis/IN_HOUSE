<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cct.inhouse.common.InhouseAction"%>
<%@ page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/jquery/jquery-ui-1.12.1.custom.min.js"></script>
<!-- meeting -->
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/javascript.js"></script>
<!-- ------ -->
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/ui/pagination.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/ui/headersorts.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/ui/inputmethod_0.0.1.js"></script>

<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/validate/inputvalidate-0.0.1.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/control/control-0.0.1.js"></script>

<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/jquery.loadJSON.js"></script>

<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/dialog/dialog.js"></script>
<%-- <script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/dialog/dialogDataTable_1.0.1.js"></script>--%>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/table/table_0.0.1.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/table/tableForce.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/submit/submit.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/jquery/jquery-button.js"></script>

<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/charactor/checkCharactor.js"></script>

<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/datatables/jqueryui/js/jquery.dataTables-sit-0.0.1.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/datatables/jqueryui/js/dataTables-sit-0.0.2.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/datatables/jqueryui/js/dataTables.fixedColumns.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/datatables/jqueryui/js/dataTables.dialog-sit-0.0.1.js"></script>

<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/pushmenu/modernizr.custom.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/pushmenu/classie.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/pushmenu/mlpushmenu.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/notify/jquery.notify.min.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/javascript-validate_0.0.3.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/picklist/js/jquery-picklist.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/picklist/js/jquery-picklist-sit-0.0.1.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/button/jqueryui/js/button-sit-0.0.1.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/button/jqueryui/js/button-predefine-sit.js"></script>

<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/loader/js/loader-sit-0.0.1.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/ui/loader/js/ajax-loader.js"></script>
<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/ajax-event-sit-0.0.1.js"></script>


<script type="text/javascript">
	const CONTEXT_CENTRAL = "${application.CONTEXT_CENTRAL}"; 
	const CONTEXT_OWNER = "${application.CONTEXT_OWNER}";
	const PREFIX_URL_ICON = "${application.PREFIX_URL_ICON}"; //สำหรับไฟล์ js ดึงไปใช้
	var fillAtLeast = "<s:property value='@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getAutocomplete().getFillAtLeast()'/>";
	var theme = "<s:property value='@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getTheme()' />";
	var defaultLinePerpage = "<s:property value='@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getDefaultLpp()'/>";
	var validateMessage = {
			CODE_10001: '<s:text name="10001"/>', // คุณต้องทำการเลือกอย่างน้อย 1 รายการ
			CODE_10002: '<s:text name="10002"/>', // xxx ไม่สามารถว่างได้
			CODE_10003: '<s:text name="10003"/>', // ข้อมูลซ้ำ กรุณาตรวจสอบข้อมูลอีกครั้ง
			CODE_10004: '<s:text name="10004"/>', // ข้อมูลไม่ถูกต้อง กรุณาตรวจสอบข้อมูล
			CODE_10005: '<s:text name="10005"/>', // ขอบเขตวันที่ผิดพลาด
			CODE_10006: '<s:text name="10006"/>', // กรุณาตรวจสอบข้อมูลวันที่
			CODE_10007: '<s:text name="10007"/>', // รหัสผ่านที่ยืนยันไม่ถูกต้อง
			CODE_10008: '<s:text name="10008"/>', // รูปแบบอีเมลไม่ถูกต้อง
			CODE_10009: '<s:text name="10009"/>', // รหัสผ่านเก่าไม่ถูกต้อง
			CODE_10010: '<s:text name="10010"/>', // กรุณาระบุข้อมูลใหม่ เนื่องจากข้อมูล CAPTCHA ไม่ถูกต้อง
			CODE_10011: '<s:text name="10011"/>', // กรุณาตรวจสอบข้อมูลเวลา
			CODE_10012: '<s:text name="10012"/>', // ขอบเขตเวลาผิดพลาด
			CODE_10013: '<s:text name="10013"/>', // ไม่พบข้อมูล xx
			CODE_10015: '<s:text name="10015"/>', // xxx ต้องเลือกอย่างน้อย 1 รายการ
			CODE_10016: '<s:text name="10016"/>', // xxx ไม่ถูกต้อง
			CODE_10017: '<s:text name="10017"/>', // ไม่มีสิทธิ์เข้าใช้งาน
			CODE_10018: '<s:text name="10018"/>', // คุณต้องมีข้อมูล xxx อย่างน้อย 1 รายการ
			CODE_10019: '<s:text name="10019"/>', // ข้อมูลไม่เพียงพอ
			CODE_10020: '<s:text name="10020"/>', // กรุณาตรวจสอบรูปแบบวันที่
			CODE_10021: '<s:text name="10021"/>', // กรุณาตรวจสอบรูปแบบปี
			CODE_10022: '<s:text name="10022"/>', // กรุณาตรวจสอบรูปแบบเดือน
			CODE_10023: '<s:text name="10023"/>', // กรุณาตรวจสอบรูปแบบวัน
			CODE_10024: '<s:text name="10024"/>', // รูปแบบข้อมูลไม่ถูกต้อง
			CODE_10025: '<s:text name="10025"/>', // เลือกช่วงวันที่ได้ไม่เกิน 31 วัน
			CODE_10026: '<s:text name="10026"/>', // ระบุเงื่อนไขในการค้นหาอย่างน้อย xxx ตัวอักษร
			CODE_10027: '<s:text name="10027"/>', // กรุณาตรวจสอบไฟล์นำเข้า เนื่องจากนามสกุลไม่รองรับ
			CODE_10028: '<s:text name="10028"/>', // รองรับขนาดไฟล์ไม่เกิน xxx
			CODE_10029: '<s:text name="10029"/>', // ไม่สามารถ upload ไฟล์มากกว่า 1 ไฟล์ / 1 ครั้ง
			
			CODE_10045: '<s:text name="10045"/>', // File type is invalid, allowed extensions are: xxx
			CODE_10046: '<s:text name="10046"/>', // File size exceeded allowed. Maximum size is xxx
			
			CODE_20001: '<s:text name="20001"/>', // ไม่สามารถติดต่อฐานข้อมูลได้

			CODE_30001: '<s:text name="30001"/>', // เปลี่ยนสถานะเป็นใช้งานเรียบร้อยแล้ว		
			CODE_30002: '<s:text name="30002"/>', // เปลี่ยนสถานะเป็นยกเลิกเรียบร้อยแล้ว
			CODE_30003: '<s:text name="30003"/>', // จัดเก็บข้อมูลเรียบร้อยแล้ว
			CODE_30004: '<s:text name="30004"/>', // แก้ไขข้อมูลเรียบร้อยแล้ว
			CODE_30005: '<s:text name="30005"/>', // ลบข้อมูลเรียบร้อยแล้ว
			CODE_30006: '<s:text name="30006"/>', // ประมวลผลข้อมูลสำเร็จ
			CODE_30007: '<s:text name="30007"/>', // บันทึกข้อมูลไม่สำเร็จ
			CODE_30008: '<s:text name="30008"/>', // บันทึกการแก้ไขข้อมูลไม่สำเร็จ
			CODE_30009: '<s:text name="30009"/>', // ลบข้อมูลไม่สำเร็จ
			CODE_30010: '<s:text name="30010"/>', // ประมวลผลข้อมูลไม่สำเร็จ
			CODE_30011: '<s:text name="30011"/>', // ไม่พบข้อมูลที่ต้องการ
			CODE_30012: '<s:text name="30012"/>', // ไม่สามารถบันทึกเปลี่ยนสถานะเป็นใช้งาน
			CODE_30013: '<s:text name="30013"/>', // ไม่สามารถบันทึกเปลี่ยนสถานะเป็นยกเลิก
			CODE_30014: '<s:text name="30014"/>', // จำนวนข้อมูลที่ค้นพบ = xxx รายการ ต้องการแสดงข้อมูลหรือไม่ ?
			CODE_30015: '<s:text name="30015"/>', // จำนวนข้อมูลที่ค้นพบ = xxx รายการ ต้องการออกรายงานหรือไม่ ?
			CODE_30016: '<s:text name="30016"/>', // ไม่สามารถบันทึกเปลี่ยนสถานะเป็นพร้อมใช้งาน
			CODE_30017: '<s:text name="30017"/>', // ไม่สามารถบันทึกเปลี่ยนสถานะเป็นระงับใช้งาน
			CODE_30018: '<s:text name="30018"/>', // จำนวนข้อมูลที่ค้นพบมีจำนวนมาก กรุณาระบุเงื่อนไขในการค้นหา
			CODE_30019: '<s:text name="30019"/>', // ข้อมูลไม่สอดคล้องกับข้อมูลปัจจุบัน กรุณาระบุข้อมูลใหม่อีกครั้ง
			CODE_30020: '<s:text name="30020"/>', // ไม่มีข้อมูลแสดง

			CODE_40001: '<s:text name="40001"/>', // กรุณาตรวจสอบสิทธิ์การใช้งาน
			CODE_40002: '<s:text name="40002"/>', // มีการใช้งานรหัสผู้ใช้ นี้อยู่ ต้องการ Override สิทธิ์ หรือไม่?
			CODE_40003: '<s:text name="40003"/>', // ไม่สามารถใช้งานระบบได้ เนื่องจากมีผู้ใช้รหัสนี้เข้าใช้งานซ้อนกัน

			CODE_50001: '<s:text name="50001"/>', // คุณต้องการเปลี่ยนสถานะเป็นใช้งานหรือไม่ ?
			CODE_50002: '<s:text name="50002"/>', // คุณต้องการเปลี่ยนสถานะเป็นยกเลิกหรือไม่ ?
			CODE_50003: '<s:text name="50003"/>', // คุณต้องการบันทึกการเพิ่มข้อมูล ?
			CODE_50004: '<s:text name="50004"/>', // คุณต้องการบันทึกการแก้ไขข้อมูล ?
			CODE_50005: '<s:text name="50005"/>', // คุณต้องการลบข้อมูล ?
			CODE_50006: '<s:text name="50006"/>', // คุณต้องการยกเลิกการบันทึกข้อมูล ?		
			CODE_50007: '<s:text name="50007"/>', // คุณต้องการยกเลิกการแก้ไขข้อมูล ?
			CODE_50008: '<s:text name="50008"/>', // คุณต้องการออกจากหน้าจอค้นหาข้อมูล ?
			CODE_50009: '<s:text name="50009"/>', // คุณต้องการออกจากหน้าจอเพิ่มข้อมูล ?
			CODE_50010: '<s:text name="50010"/>', // คุณต้องการออกจากหน้าจอแก้ไขข้อมูล ?
			CODE_50011: '<s:text name="50011"/>', // คุณต้องการออกจากโปรแกรม ?
			CODE_50012: '<s:text name="50012"/>', // คุณต้องการเลิกงาน ?
			CODE_50013: '<s:text name="50013"/>', // คุณต้องการเปลี่ยนสถานะเป็นพร้อมใช้งานหรือไม่ ?
			CODE_50014: '<s:text name="50014"/>', // คุณต้องการเปลี่ยนสถานะเป็นระงับใช้งานหรือไม่ ?
			CODE_50015: '<s:text name="50015"/>', // คุณต้องการ Reset Password หรือไม่ ?
			CODE_50016: '<s:text name="50016"/>', // คุณต้องการลบข้อมูลทำรายการหรือไม่ ?
			CODE_50017: '<s:text name="50017"/>', // คุณต้องการออกจากหน้าจอแสดงข้อมูล ?
			CODE_50018: '<s:text name="50018"/>', // คุณต้องการกลับไปยังหน้าจอหลัก ?
			CODE_50019: '<s:text name="50019"/>', // คุณต้องการออกจากหน้าจออนุมัติ ?
			CODE_50020: '<s:text name="50020"/>', // คุณต้องการแก้ไขข้อมูล ?
			CODE_50021: '<s:text name="50021"/>', // คุณต้องการปิดหน้าจอ ?
				
			CODE_numberOfSearch: '<s:text name="numberOfSearch"/>',
			CODE_order: '<s:text name="record(s)"/>',
			CODE_EDIT: '<s:text name="edit"/>',
			CODE_VIEW: '<s:text name="view"/>',
			CODE_ACTIVE: '<s:text name="active"/>',
			CODE_INACTIVE: '<s:text name="inactive"/>',
			
			CODE_SEARCH: '<s:text name="search"/>',
			CODE_CLEAR: '<s:text name="clear"/>',
			CODE_SAVE: '<s:text name="save"/>',
			CODE_CANCEL: '<s:text name="cancel"/>' ,
			CODE_EDIT: '<s:text name="edit"/>',
			CODE_CLOSE: '<s:text name="closePage"/>',
			CODE_PRINT: '<s:text name="print"/>',
			CODE_OK: '<s:text name="ok"/>',
			
			/* Autocomplete */
			CODE_CLICKINFO: '<s:text name="clickInformation"/>'			// กดเพื่อแสดงข้อมูล
			
	};
</script>