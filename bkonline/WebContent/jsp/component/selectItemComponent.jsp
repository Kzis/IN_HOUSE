<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	int rowno = 0;
%>
<html>
<head>
	<script type="text/javascript">
		function sf(){
		    genCustomTableStyle("divResultSelectItem", 1280, null, null);	
		}
	</script>
	<link type="text/css" rel="stylesheet" href='<s:url value="/jsp/component/include/component.css"/>'/>
</head>
<body>
	<s:form cssClass="margin-zero" name="template">	
		<s:include value="/jsp/component/include/component.jsp"/>
		<div id="divResultSelectItem">
	    	<div id="divResultBorderCustom">
	        	<div id="divTitleCustom">
	        		<table class="TOTAL_RECORD">
						<tbody>
							<tr>
								<td class="LEFT">การดึงข้อมูล Select Item จาก Action Class ด้วย SelectItemRMIProviders</td>
								<td class="RIGHT"></td>
							</tr>
							<tr>
								<td class="LEFT"><img src="<s:url value='/jsp/component/include/SelectItem.png' />"/></td>
								<td class="RIGHT"></td>
							</tr>
						</tbody>
					</table>
	        	</div>
		        <div id="divHeaderCustom">
		            <table id="subHeaderCustom">
		                <tr>
							<th class="order">No.</th>
							<th class="GlobalType">Caption</th>
							<th class="GlobalData">UI</th>
							<th class="ActionClass">Example</th>
		                </tr>
		            </table>
		        </div>
		        <div id="divDetailCustom">
		        	<table id="tableSelectItem_divDetailCustom">
		            	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType">ผู้ใช้งานระบบ</td>
		               		<td class="GlobalData">
								<s:select 
									list="listAllQAUserSelectItem"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value"
									cssClass="combox"
								></s:select>
		               		</td>
		               		<td class="ActionClass">
		               			<p class="COMMENT_BLOCK">
		               				เป็นการใช้งานแบบปกติโดยการ Request และรับ Response ในรูปแบบ CommonSelectItem
		               			</p>
		               			<fieldset>
		               				<legend>1. Action Class</legend>
		               				getModel().setListAllQAUserSelectItem(<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.requestSelectItem(<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getUser()<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, getLocale()<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, SelectItemMethod.SEARCH_ALL_QA_USER_SELECT_ITEM<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div>)<br>
		               				);
		               				<h4 class="COMMENT_BLOCK">SelectItemRMIProviders.requestSelectItem(CommonUser, Locale, SelectItemMethod) รับ Parameter 3 ตัวได้แก่</h4>
	               					<ol class="COMMENT_BLOCK">
	               						<li>CommonUser: ให้ใช้ Method getUser() จาก CommonAction</li>
	               						<li>Locale: ให้ใช้ Method getLocale() จาก CommonAction ที่  extend มาจาก  ActionSupport</li>
	               						<li>SelectItemMethod: ให้ใช้ String จาก <font class="ActionClass">SelectItemMethod.SEARCH_ALL_QA_USER_SELECT_ITEM</font> เพื่อเรียกใช้งาน<br>Method ที่กำหนดไว้ใน SelectItemManager</li>
	               					</ol>
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>2. JSP File</legend>
			               			&lt;s:select<br>
									<div class="SPACE_BLOCK">&nbsp;</div>list="listAllQAUserSelectItem"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>headerKey=""<br>
									<div class="SPACE_BLOCK">&nbsp;</div>headerValue=""<br>
									<div class="SPACE_BLOCK">&nbsp;</div>listKey="key"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>listValue="value"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>cssClass="combox"<br>
									&gt;&lt;/s:select&gt;
		               			</fieldset>	
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType">แผนก</td>
		               		<td class="GlobalData">
								<s:select 
									list="listDepartmentSelectItem"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="object.departmentCode + '-' + object.departmentDesc + ' (' + object.departmentName + ')'"
									cssClass="combox"
								></s:select>
		               		</td>
		               		<td class="ActionClass">
		               			<p class="COMMENT_BLOCK">
		               				เป็นการใช้งานแบบรับส่ง Object โดยส่ง  Object สำหรับเป็นเงือนไขการค้นหา<br>และรับ Response ในรูปแบบ CommonSelectItemObject&lt;Department&gt;
		               			</p>
		               			<fieldset>
		               				<legend>1. Action Class</legend>
		               				<font class="COMMENT_BLOCK">// สร้าง Object request</font><br>
									Department departmentCriteria = new Department();<br>
									<br>
									getModel().setListDepartmentSelectItem(<br>
									<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.requestSelectItemObject(<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getUser()<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, getLocale()<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, SelectItemMethod.SEARCH_DEPARTMENT_SELECT_ITEM_OBJ<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, departmentCriteria<br>
									<div class="SPACE_BLOCK">&nbsp;</div>)<br>
									);
									<h4 class="COMMENT_BLOCK">SelectItemRMIProviders.requestSelectItemObject(CommonUser, Locale, SelectItemMethod, Object) รับ Parameter 4  ตัวได้แก่</h4>
	               					<ol class="COMMENT_BLOCK">
	               						<li>CommonUser: ให้ใช้ Method getUser() จาก CommonAction</li>
	               						<li>Locale: ให้ใช้ Method getLocale() จาก CommonAction ที่  extend มาจาก  ActionSupport</li>
	               						<li>SelectItemMethod: ให้ใช้ String จาก <font class="ActionClass">SelectItemMethod.SEARCH_DEPARTMENT_SELECT_ITEM_OBJ</font> เพื่อเรียกใช้งาน<br>Method ที่กำหนดไว้ใน SelectItemManager</li>
	               						<li>Object: ให้ใช้  Department Object เพือส่งเงือนไขไปค้นหา</li>
	               					</ol>
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>2. JSP File</legend>
			               			&lt;s:select<br>
									<div class="SPACE_BLOCK">&nbsp;</div>list="listDepartmentSelectItem"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>headerKey=""<br>
									<div class="SPACE_BLOCK">&nbsp;</div>headerValue=""<br>
									<div class="SPACE_BLOCK">&nbsp;</div>listKey="key"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>listValue="object.departmentCode + '-' + object.departmentDesc + ' (' + object.departmentName + ')'"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>cssClass="combox"<br>
									&gt;&lt;/s:select&gt;
		               			</fieldset>	
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType">พนักงานตามแผนก</td>
		               		<td class="GlobalData">
								<s:select 
									list="listEmployeeFullnameByDepartmentIdSelectItem"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value"
									cssClass="combox"
								></s:select>
		               		</td>
		               		<td class="ActionClass">
		               			<p class="COMMENT_BLOCK">
		               				เป็นการใช้งานแบบรับส่ง Object โดยส่ง Object สำหรับเป็นเงือนไขการค้นหา<br>
									และรับ Response Object ในรูปแบบ CommonSelectItem
		               			</p>
		               			<fieldset>
		               				<legend>1. Action Class</legend>
		               				<font class="COMMENT_BLOCK">// สร้าง Object request</font><br>
									Department employeeFullnameCriteria = new Department();<br>
									employeeFullnameCriteria.setDepartmentId("5");<font class="COMMENT_BLOCK">// Filter with Programmer</font><br>
									<br>
									getModel().setListEmployeeFullnameByDepartmentIdSelectItem(<br>
									<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.requestSelectItem(<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getUser()<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, getLocale()<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, SelectItemMethod.SEARCH_EMPLOYEE_BY_DEPARTMENT_ID<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, employeeFullnameCriteria<br>
									<div class="SPACE_BLOCK">&nbsp;</div>)<br>
									);
									<h4 class="COMMENT_BLOCK">SelectItemRMIProviders.requestSelectItem(CommonUser, Locale, SelectItemMethod, Object)<br>รับ Parameter 4  ตัวได้แก่</h4>
	               					<ol class="COMMENT_BLOCK">
	               						<li>CommonUser: ให้ใช้ Method getUser() จาก CommonAction</li>
	               						<li>Locale: ให้ใช้ Method getLocale() จาก CommonAction ที่  extend มาจาก  ActionSupport</li>
	               						<li>SelectItemMethod: ให้ใช้ String จาก <font class="ActionClass">SelectItemMethod.SEARCH_EMPLOYEE_BY_DEPARTMENT_ID</font><br>
	               							เพื่อเรียกใช้งาน Method ที่กำหนดไว้ใน SelectItemManager</li>
	               						<li>Object: ให้ใช้  Department Object เพือส่งเงือนไขไปค้นหา</li>
	               					</ol>
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>2. JSP File</legend>
			               			&lt;s:select<br>
									<div class="SPACE_BLOCK">&nbsp;</div>list="listEmployeeFullnameByDepartmentIdSelectItem"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>headerKey=""<br>
									<div class="SPACE_BLOCK">&nbsp;</div>headerValue=""<br>
									<div class="SPACE_BLOCK">&nbsp;</div>listKey="key"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>listValue="value"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>cssClass="combox"<br>
									&gt;&lt;/s:select&gt;
		               			</fieldset>	
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType">โครงการ</td>
		               		<td class="GlobalData">
								<s:select 
									list="listProjectsSelectItem"
									headerKey=""
									headerValue=""
									listKey="key"
									listValue="value"
									cssClass="combox"
								></s:select>
		               		</td>
		               		<td class="ActionClass">
								<p class="COMMENT_BLOCK">
		               				เป็นการใช้งานแบบปกติโดยการ Request และรับ Response ในรูปแบบ CommonSelectItem
		               			</p>
		               			<fieldset>
		               				<legend>1. Action Class</legend>
		               				getModel().setListProjectsSelectItem(<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div>SelectItemRMIProviders.requestSelectItem(<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>getUser()<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, getLocale()<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, SelectItemMethod.SEARCH_PROJECTS_SELECT_ITEM<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div>)<br>
		               				);
		               				<h4 class="COMMENT_BLOCK">SelectItemRMIProviders.requestSelectItem(CommonUser, Locale, SelectItemMethod) รับ Parameter 3 ตัวได้แก่</h4>
	               					<ol class="COMMENT_BLOCK">
	               						<li>CommonUser: ให้ใช้ Method getUser() จาก CommonAction</li>
	               						<li>Locale: ให้ใช้ Method getLocale() จาก CommonAction ที่  extend มาจาก  ActionSupport</li>
	               						<li>SelectItemMethod: ให้ใช้ String จาก <font class="ActionClass">SelectItemMethod.SEARCH_PROJECTS_SELECT_ITEM</font> เพื่อเรียกใช้งาน<br>Method ที่กำหนดไว้ใน SelectItemManager</li>
	               					</ol>
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>2. JSP File</legend>
			               			&lt;s:select<br>
									<div class="SPACE_BLOCK">&nbsp;</div>list="listProjectsSelectItem"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>headerKey=""<br>
									<div class="SPACE_BLOCK">&nbsp;</div>headerValue=""<br>
									<div class="SPACE_BLOCK">&nbsp;</div>listKey="key"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>listValue="value"<br>
									<div class="SPACE_BLOCK">&nbsp;</div>cssClass="combox"<br>
									&gt;&lt;/s:select&gt;
		               			</fieldset>
		               		</td>
		               	</tr>
					</table>
		        </div>
		       	<div id="divFooterCustom">
					<table class="TOTAL_RECORD">
						<tbody>
							<tr>
								<td class="LEFT">&nbsp;</td>
								<td class="RIGHT">&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<br>
	</s:form>
</body>
</html>