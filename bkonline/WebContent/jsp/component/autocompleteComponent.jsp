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
		
		jQuery(document).ready(function() {
			initListAllQAUserSelectItem();
			initListDepartmentSelectItem();
			initListEmployeeFullnameByDepartmentIdSelectItem();
			initListProjectsSelectItem();
		});
		
		function initListAllQAUserSelectItem() {
            jQuery("#listAllQAUserSelectItemCode").autocompletezAjax([{
                url: "<s:url value='%{@com.cct.domain.SelectItemServletURL@AllQAUserSelectItemServlet}'/>",   
                defaultKey: "",
                defaultValue: "",
                cssStyle: "width: 200px;"
            }]);			
		}
		
		function initListDepartmentSelectItem() {
            jQuery("#listDepartmentSelectItemCode").autocompletezAjax([{
                url: "<s:url value='%{@com.cct.domain.SelectItemServletURL@DepartmentSelectItemServlet}'/>",   
                defaultKey: "",
                defaultValue: "",
                cssStyle: "width: 200px;"
            }]);			
		}
		
		function initListEmployeeFullnameByDepartmentIdSelectItem() {
            jQuery("#listEmployeeFullnameByDepartmentIdSelectItemCode").autocompletezAjax([{
                url: "<s:url value='%{@com.cct.domain.SelectItemServletURL@EmployeeFullnameByDepartmentIdServlet}'/>",   
                postParamsId: {departmentId: "departmentId"},
                defaultKey: "",
                defaultValue: "",
                cssStyle: "width: 200px;"
            }]);			
		}
		
		function initListProjectsSelectItem() {
            jQuery("#listProjectsSelectItemCode").autocompletezAjax([{
                url: "<s:url value='%{@com.cct.domain.SelectItemServletURL@ProjectsSelectItemServlet}'/>",   
                defaultKey: "",
                defaultValue: "",
                cssStyle: "width: 200px;"
            }]);			
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
								<td class="LEFT">การดึงข้อมูล Autocomplete จาก HTTP Servlet ด้วย Widget Autocomplete</td>
								<td class="RIGHT"></td>
							</tr>
							<tr>
								<td class="LEFT"><img src="<s:url value='/jsp/component/include/DropdownlistAndAutocomplete.png' />"/></td>
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
		        	<table>
		        		<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType">ผู้ใช้งานระบบ</td>
		               		<td class="GlobalData">
		               			<s:hidden id="listAllQAUserSelectItemCode" name="listAllQAUserSelectItemCode" code-of="listAllQAUserSelectItem_autocomplete" />
                    			<s:hidden id="listAllQAUserSelectItemText" name="listAllQAUserSelectItemText" text-of="listAllQAUserSelectItem_autocomplete" />
		               		</td>
		               		<td class="ActionClass">
		               			<p class="COMMENT_BLOCK">
		               				เป็นการใช้งานแบบปกติโดยการ Request และรับ Response CommonSelectItem ในรูปแบบ JSON กลับมา
		               			</p>
		               			<fieldset>
		               				<legend>1. Servlet Class</legend>
		               				@Override<br>
									public List&lt;CommonSelectItem&gt; searchSelectItem(HttpServletRequest request<br>
									<div class="SPACE_BLOCK">&nbsp;</div>, CCTConnection conn, CommonUser commonUser<br>
									<div class="SPACE_BLOCK">&nbsp;</div>, Locale locale, String team, String limit) throws Exception {<br>
									<div class="SPACE_BLOCK">&nbsp;</div><br>
									<div class="SPACE_BLOCK">&nbsp;</div>List&lt;CommonSelectItem&gt; collectionSelectItem = SelectItemRMIProviders.requestSelectItem(<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>commonUser<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, locale<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, SelectItemMethod.SEARCH_ALL_QA_USER_SELECT_ITEM<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, team<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, limit);<br>
									<div class="SPACE_BLOCK">&nbsp;</div><br>
									<div class="SPACE_BLOCK">&nbsp;</div>return collectionSelectItem;<br>
									}
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>2. Javascript</legend>
		               				jQuery("#listAllQAUserSelectItemCode").autocompletezAjax([{<br>
						            <div class="SPACE_BLOCK">&nbsp;</div>url: "&lt;s:url value='%{@com.cct.domain.SelectItemServletURL@AllQAUserSelectItemServlet}'/&gt;",<br>   
						            <div class="SPACE_BLOCK">&nbsp;</div>defaultKey: "",<br>
						            <div class="SPACE_BLOCK">&nbsp;</div>defaultValue: "",<br>
						            <div class="SPACE_BLOCK">&nbsp;</div>cssStyle: "width: 200px;"<br>
						            }]);
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>3. HTML</legend>
		               				&lt;s:hidden id="listAllQAUserSelectItemCode" name="listAllQAUserSelectItemCode"<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div>code-of="listAllQAUserSelectItem_autocomplete" /&gt;<br>
                    				&lt;s:hidden id="listAllQAUserSelectItemText" name="listAllQAUserSelectItemText"<br>
                    				<div class="SPACE_BLOCK">&nbsp;</div>text-of="listAllQAUserSelectItem_autocomplete" /&gt;
		               			</fieldset>	
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType">แผนก</td>
		               		<td class="GlobalData">
		               			<s:hidden id="listDepartmentSelectItemCode" name="listDepartmentSelectItemCode" code-of="listDepartmentSelectItem_autocomplete" />
                    			<s:hidden id="listDepartmentSelectItemText" name="listDepartmentSelectItemText" text-of="listDepartmentSelectItem_autocomplete" />
		               		</td>
		               		<td class="ActionClass">
		               			<p class="COMMENT_BLOCK">
		               				เป็นการใช้งานแบบปกติโดยการ Request และรับ Response CommonSelectItem ในรูปแบบ JSON กลับมา<br>
		               				เป็นการใช้งานแบบรับส่ง Object โดยส่ง  Object สำหรับเป็นเงือนไขการค้นหา<br>และรับ Response CommonSelectItemObject&lt;Department&gt; ในรูปแบบ JSON กลับมา
		               			</p>
		               			<fieldset>
		               				<legend>1. Servlet Class</legend>
		               				@Override<br>
									public List&lt;CommonSelectItemObject&lt;?&gt;&gt; searchSelectItem(HttpServletRequest request<br>
									<div class="SPACE_BLOCK">&nbsp;</div>, CCTConnection conn, CommonUser commonUser<br>
									<div class="SPACE_BLOCK">&nbsp;</div>, Locale locale, String team, String limit) throws Exception {<br>
									<div class="SPACE_BLOCK">&nbsp;</div><br>
									<div class="SPACE_BLOCK">&nbsp;</div>Department criteria = new Department();<br>
									<div class="SPACE_BLOCK">&nbsp;</div><br>
									<div class="SPACE_BLOCK">&nbsp;</div>List&lt;CommonSelectItemObject&lt;?&gt;&gt; collectionSelectItem = SelectItemRMIProviders.requestSelectItemObject(<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>commonUser<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, locale<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, SelectItemMethod.SEARCH_DEPARTMENT_SELECT_ITEM_OBJ<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, team<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, limit<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, criteria);<br>
									<div class="SPACE_BLOCK">&nbsp;</div><br>
									<div class="SPACE_BLOCK">&nbsp;</div>return collectionSelectItem;<br>
									}
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>2. Javascript</legend>
		               				jQuery("#listDepartmentSelectItemCode").autocompletezAjax([{<br>
						            <div class="SPACE_BLOCK">&nbsp;</div>url: "&lt;s:url value='%{@com.cct.domain.SelectItemServletURL@DepartmentSelectItemServlet}'/&gt;",<br>   
						            <div class="SPACE_BLOCK">&nbsp;</div>defaultKey: "",<br>
						            <div class="SPACE_BLOCK">&nbsp;</div>defaultValue: "",<br>
						            <div class="SPACE_BLOCK">&nbsp;</div>cssStyle: "width: 200px;"<br>
						            }]);
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>3. HTML</legend>
		               				&lt;s:hidden id="listDepartmentSelectItemCode" name="listDepartmentSelectItemCode"<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div>code-of="listDepartmentSelectItem_autocomplete" /&gt;<br>
                    				&lt;s:hidden id="listDepartmentSelectItemText" name="listDepartmentSelectItemText"<br>
                    				<div class="SPACE_BLOCK">&nbsp;</div>text-of="listDepartmentSelectItem_autocomplete" /&gt;
		               			</fieldset>	
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType">พนักงานตามแผนก</td>
		               		<td class="GlobalData">
								<s:hidden id="listEmployeeFullnameByDepartmentIdSelectItemCode" name="listEmployeeFullnameByDepartmentIdSelectItemCode" code-of="listEmployeeFullnameByDepartmentIdSelectItem_autocomplete" />
                    			<s:hidden id="listEmployeeFullnameByDepartmentIdSelectItemText" name="listEmployeeFullnameByDepartmentIdSelectItemText" text-of="listEmployeeFullnameByDepartmentIdSelectItem_autocomplete" />
								<div style="margin: auto; width: 99%;">Filter by DepartmentId:</div>
								<div style="margin: auto; width: 99%;"><input type="text" id="departmentId" name="departmentId" value="5"/></div>
		               		</td>
		               		<td class="ActionClass">
		               			<p class="COMMENT_BLOCK">
		               				เป็นการใช้งานแบบรับส่ง Object โดยส่ง Object สำหรับเป็นเงือนไขการค้นหา<br>
									และรับ Response CommonSelectItem ในรูปแบบ JSON กลับมา
		               			</p>
		               			<fieldset>
		               				<legend>1. Servlet Class</legend>
		               				@Override<br>
									public List&lt;CommonSelectItem&gt; searchSelectItem(HttpServletRequest request<br>
									<div class="SPACE_BLOCK">&nbsp;</div>, CCTConnection conn, CommonUser commonUser<br>
									<div class="SPACE_BLOCK">&nbsp;</div>, Locale locale, String team, String limit) throws Exception {<br>
									<div class="SPACE_BLOCK">&nbsp;</div><br>
									<div class="SPACE_BLOCK">&nbsp;</div>Department criteria = new Department();<br>
									<div class="SPACE_BLOCK">&nbsp;</div>// Filter with Programmer<br>
									<div class="SPACE_BLOCK">&nbsp;</div>criteria.setDepartmentId(SessionUtil.requestParameter("departmentId"));<br>
									<div class="SPACE_BLOCK">&nbsp;</div><br>
									<div class="SPACE_BLOCK">&nbsp;</div>List&lt;CommonSelectItem&gt; collectionSelectItem = SelectItemRMIProviders.requestSelectItem(<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>commonUser<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, locale<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, SelectItemMethod.SEARCH_DEPARTMENT_SELECT_ITEM_OBJ<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, team<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, limit<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, criteria);<br>
									<div class="SPACE_BLOCK">&nbsp;</div><br>
									<div class="SPACE_BLOCK">&nbsp;</div>return collectionSelectItem;<br>
									}
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>2. Javascript</legend>
		               				jQuery("#listEmployeeFullnameByDepartmentIdSelectItemCode").autocompletezAjax([{<br>
                					<div class="SPACE_BLOCK">&nbsp;</div>url: "&lt;s:url value='%{@com.cct.domain.SelectItemServletURL@EmployeeFullnameByDepartmentIdServlet}'/&gt;",<br>   
                					<div class="SPACE_BLOCK">&nbsp;</div>postParamsId: {departmentId: "departmentId"},<br>
                					<div class="SPACE_BLOCK">&nbsp;</div>defaultKey: "",<br>
                					<div class="SPACE_BLOCK">&nbsp;</div>defaultValue: "",<br>
                					<div class="SPACE_BLOCK">&nbsp;</div>cssStyle: "width: 200px;"<br>
            						}]);
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>3. HTML</legend>
		               				&lt;s:hidden id="listEmployeeFullnameByDepartmentIdSelectItemCode"<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div>name="listEmployeeFullnameByDepartmentIdSelectItemCode"<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div>code-of="listEmployeeFullnameByDepartmentIdSelectItem_autocomplete" /&gt;<br>
                    				&lt;s:hidden id="listEmployeeFullnameByDepartmentIdSelectItemText"<br>
                    				<div class="SPACE_BLOCK">&nbsp;</div>name="listEmployeeFullnameByDepartmentIdSelectItemText"<br>
                    				<div class="SPACE_BLOCK">&nbsp;</div>text-of="listEmployeeFullnameByDepartmentIdSelectItem_autocomplete" /&gt;<br>
									&lt;div style="margin: auto; width: 99%;"&gt;Filter by DepartmentId:&lt;/div&gt;<br>
									&lt;div style="margin: auto; width: 99%;"&gt;<br>
									<div class="SPACE_BLOCK">&nbsp;</div>&lt;input type="text" id="departmentId" name="departmentId" value="5"/&gt;<br>
									&lt;/div&gt;
		               			</fieldset>		
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="GlobalType">โครงการ</td>
		               		<td class="GlobalData">
								<s:hidden id="listProjectsSelectItemCode" name="listProjectsSelectItemCode" code-of="listProjectsSelectItem_autocomplete" />
                    			<s:hidden id="listProjectsSelectItemText" name="listProjectsSelectItemText" text-of="listProjectsSelectItem_autocomplete" />
		               		</td>
		               		<td class="ActionClass">
		               			<p class="COMMENT_BLOCK">
		               				เป็นการใช้งานแบบปกติโดยการ Request และรับ Response CommonSelectItem ในรูปแบบ JSON กลับมา
		               			</p>
		               			<fieldset>
		               				<legend>1. Servlet Class</legend>
		               				@Override<br>
									public List&lt;CommonSelectItem&gt; searchSelectItem(HttpServletRequest request<br>
									<div class="SPACE_BLOCK">&nbsp;</div>, CCTConnection conn, CommonUser commUser<br>
									<div class="SPACE_BLOCK">&nbsp;</div>, Locale locale, String team, String limit) throws Exception {<br>
									<div class="SPACE_BLOCK">&nbsp;</div><br>
									<div class="SPACE_BLOCK">&nbsp;</div>List&lt;CommonSelectItem&gt; collectionSelectItem = SelectItemRMIProviders.requestSelectItem(<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>commonUser<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, locale<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, SelectItemMethod.SEARCH_ALL_QA_USER_SELECT_ITEM<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, team<br>
									<div class="SPACE_BLOCK">&nbsp;</div><div class="SPACE_BLOCK">&nbsp;</div>, limit);<br>
									<div class="SPACE_BLOCK">&nbsp;</div><br>
									<div class="SPACE_BLOCK">&nbsp;</div>return collectionSelectItem;<br>
									}
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>2. Javascript</legend>
		               				jQuery("#listProjectsSelectItemCode").autocompletezAjax([{<br>
                					<div class="SPACE_BLOCK">&nbsp;</div>url: "&lt;s:url value='%{@com.cct.domain.SelectItemServletURL@ProjectsSelectItemServlet}'/&gt;",  <br> 
                					<div class="SPACE_BLOCK">&nbsp;</div>defaultKey: "",<br>
                					<div class="SPACE_BLOCK">&nbsp;</div>defaultValue: "",<br>
                					<div class="SPACE_BLOCK">&nbsp;</div>cssStyle: "width: 200px;"<br>
            						}]);	
		               			</fieldset>
		               			<br>
		               			<fieldset>
		               				<legend>3. HTML</legend>
		               				&lt;s:hidden id="listProjectsSelectItemCode" name="listProjectsSelectItemCode"<br>
		               				<div class="SPACE_BLOCK">&nbsp;</div>code-of="listProjectsSelectItem_autocomplete" /&gt;<br>
                    				&lt;s:hidden id="listProjectsSelectItemText" name="listProjectsSelectItemText"<br>
                    				<div class="SPACE_BLOCK">&nbsp;</div>text-of="listProjectsSelectItem_autocomplete" /&gt;
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