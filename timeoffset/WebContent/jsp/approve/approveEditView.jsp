<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>
<%@page import="java.util.UUID"%>

<%
String tokenName = UUID.randomUUID().toString();
%>

<html>
<head>
<script type="text/javascript">
	
	var centralPath = "<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>";
	var footerTag1 = "<a href='javascript:void(0);' onclick='updateApproveStatus(\"A\",\"<s:text name='50001'/>\");' class='action-link'><img src='<s:url value='"+centralPath+"/images/menu/i_check.png'/>'><s:text name="approve" /></a>";
	var footerTag2 = "&nbsp;<a href='javascript:void(0);' onclick='updateApproveStatus(\"D\",\"<s:text name='50002'/>\");' class='action-link'><img src='<s:url value='"+centralPath+"/images/menu/i_delete.png'/>'><s:text name="disapprove" /></a>";
	
	function sf() {
		
		//วาด Table custom ในหน้าที่ include ไว้ 
		genCustomTableStyle("divResult",1098,null,200);
				
		//Manage หน้าต่าง ๆ 
		if('<s:property value="page.getPage()"/>' == "approve"){

			//วาด Footer ใน  Table Custom
			jQuery("#footerTable").html(footerTag1 + footerTag2);
		
			//ทำการ Check value ใน Table เพื่อจัดการ table
			manageRowApprove("approve");
			
		}else if('<s:property value="page.getPage()"/>' == "view"){
			
			//ไม่มีการวาด Footer
			
			//ทำการ Check value ใน Table เพื่อจัดการ table
			manageRowApprove("view");
		}
		
		
	}
	
	//##### [FUNCTION] #####
	
	//Table Footer
	function updateApproveStatus(flag,msg) {
		
		var chk = validateSelect('criteria.selectedIds');		//name ของ checkbox table กลางเป็นคน gen ให้โดยชื่อเดียวกัน
		if (chk == false) {
			return false;
		}
		
		//หา input type checkbox ที่ไม่ใช่ name=chkall และทำการ check
		jQuery("input[type='checkbox']:not([name='chkall']):checked").each(function(i){
			
			var rows;
			
			//set ค่า title จาก list ที่ hidden ไว้
			var title = jQuery("[name='approve.listDetail["+(i)+"].approveStatusDesc']").val();
			
			//set html tag icon
			var iconApprove = "<img class='cursor' title='"+ title + "' src='<s:url value='"+centralPath+"/images/menu/i_check.png'/>'>";
			var iconDisApprove = "<img class='cursor' title='"+ title + "' src='<s:url value='"+centralPath+"/images/menu/i_delete.png'/>'>";
			
			//กดปุ่ม อนุมัติ
			if(flag == "A"){
				
				//หา tr ตัวที่ใกล้ที่สุด และ หา class d_approve แล้ว เปลี่ยน icon
				jQuery(this).closest("tr").find(".d_approve").html(iconApprove);
				
				//หาว่า row ไหน ถูกแก้ไข
				rows = parseInt(jQuery(this).closest("tr").find(".order").text());
				
				//เปลี่ยนค่าที่ Hidden ไว้
				jQuery("[name='approve.listDetail["+(rows-1)+"].approveStatus']").val(flag);
				jQuery("[name='approve.listDetail["+(rows-1)+"].approveStatusDesc']").val("<s:text name='to.approve'/>");
				
			}else if(flag == "D"){
				
				//หา tr ตัวที่ใกล้ที่สุด และ หา class d_approve แล้ว เปลี่ยน icon
				jQuery(this).closest("tr").find(".d_approve").html(iconDisApprove);
				
				//หาว่า row ไหน ถูกแก้ไข
				rows = parseInt(jQuery(this).closest("tr").find(".order").text());
				
				//เปลี่ยนค่าที่ Hidden ไว้
				jQuery("[name='approve.listDetail["+(rows-1)+"].approveStatus']").val(flag);
				jQuery("[name='approve.listDetail["+(rows-1)+"].approveStatusDesc']").val("<s:text name='to.disApprove'/>");
				
			}
			
			
			//เอา checkbox ที่ติ๊กออก เมื่อทำ process เสร้จแล้ว
			jQuery("input[type='checkbox']").attr('checked',false);
		});
		
	}
	
	//##### [BUTTON FUNCTION] #####
	//Page : Approve
	function approve(){
		
		// คุณต้องการบันทึกการแก้ไขข้อมูล
		if (confirm('<s:text name="50004" />') == false) {
			return false;
		}
		
		// Go to method edit : TimeOffsetApproveAction
		submitPage("<s:url value='/jsp/approve/editTimeOffsetApprove.action'/>")
	}
	
	function cancelApprove(){
	
		// คุณต้องการออกจากหน้าจออนุมัติ ?
		if (confirm('<s:text name="50019" />') == false) {
			return false;
		}
		
		var todoId = "<s:property value='approve.todoId' />";

		if(!(todoId == null)){
			jQuery("#approve_todoId").val(todoId);
		}
		
		submitForm();
	}
	
	//Page : View
	function cancelView() {
		
		submitForm();
	}
	
	function submitForm() {
		if(jQuery("[name='criteria.criteriaKey']").val() != ""){
			submitPage("<s:url value='/jsp/approve/cancelTimeOffsetApprove.action'/>")
        }else{
        	submitPage("<s:url value='/jsp/approve/initTimeOffsetApprove.action'/>")
        }
	}
	
	//Check เพื่อ เปิด-ปิด remark ตอนโหลดหน้า
	function checkApproveStatus(){
		
		var approveStatus = "<s:property value='approve.approveStatus' />";
		
		if(approveStatus == 'D'){
			jQuery("#approve_remark").removeAttr('disabled');
			jQuery("#approve_remark").removeClass('readonly');
		}
		
	}
	
	//Check เพื่อ เปิด-ปิด remark ตอนกด radio
	function changeApproveStatus(value){
		
		jQuery("#approve_approveStatus").val(value);
		
		if(value == 'D'){
			jQuery("#approve_remark").removeAttr('disabled');
			jQuery("#approve_remark").removeClass('readonly');
		}else{
			jQuery("#approve_remark").attr('disabled', true);
		}
	}
	
</script>
</head>
<body>
<s:form id="editViewForm" name="editViewForm" method="post" cssClass="margin-zero" onsubmit="return false;">
	<br/>
	
	<!-- ส่วนของการแสดง User Fullname จาก Session -->
	<s:include value="/jsp/include/information.jsp" />
	
	<!-- ส่วนของการแสดง Table ที่มีไว้แสดงค่า เวลาชดเชย(Detail) ของ Master ที่กดมาจากหน้า Search  -->
	<s:include value="/jsp/approve/include/projectDetail.jsp" />
	
	<s:include value="/jsp/approve/include/approveDetail.jsp" />
				
	<s:if test="page.getPage() == 'approve' ">
		<div id="divButtonPredefineApprove" class="ui-sitbutton-predefine"
			data-buttonType = "save|cancel"
			data-auth = "<s:property value='ATH.approve'/>|true"
			data-func = "approve()|cancelApprove()"
			data-style = "btn-small|btn-small"
			data-container = "true">
		</div>
	</s:if>
	
	<s:if test="page.getPage() == 'view' ">
		<div id="divButtonPredefineView" class="ui-sitbutton-predefine"
			data-buttonType = "cancel"
			data-auth = "true"
			data-func = "cancelApprove()"
			data-style = "btn-small"
			data-container = "true">
		</div>
	</s:if>
	
	<!-- Hidden value -->
	<s:hidden id="page" name="page" />
	<s:hidden name="criteria.criteriaKey" />
	<s:hidden name="P_CODE" />
	<s:hidden name="F_CODE" />
	<s:hidden id="approve_id" name="approve.id" />
	<s:hidden id="approve_approveStatus" name="approve.approveStatus" />	
	
	<!-- 	Hidden เพิ่อเวลากด Save แล้วกลับมาจะยังอยู่ -->
	<s:hidden id="approve_projectABBR" name="approve.projectABBR" />	
	<s:hidden id="approve_projectConDetail" name="approve.projectConDetail" />	
	
	<!-- 	Hidden เพื่อเวลามาจากหน้า TODO จะต้องกลับไปหน้า TODO Search -->
	<s:hidden id="approve_todoId" name="approve.todoId" />
	
	<s:token name="%{@org.apache.struts2.util.TokenHelper@generateGUID()}" />
</s:form>
</body>
</html>