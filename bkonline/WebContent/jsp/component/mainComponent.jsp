<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	int rowno = 0;
%>
<html>
<head>	
	<script type="text/javascript">
		function sf(){
		    genCustomTableStyle("divResult", 1280, null, null);			
		}
		
		function gotoo(component) {
			jQuery("input[name='page']").val(component);
			submitPage("<s:url value='/jsp/component/gotooComponent.action' />");
		}
	</script>
	<link type="text/css" rel="stylesheet" href='<s:url value="/jsp/component/include/component.css"/>'/>
</head>
<body>
	<s:form cssClass="margin-zero" name="template">
		<s:include value="/jsp/component/include/component.jsp"/>
		<div id="divResult">
	    	<div id="divResultBorderCustom">
	        	<div id="divTitleCustom">
	        		<table class="TOTAL_RECORD">
						<tbody>
							<tr>
								<td class="LEFT">&nbsp;</td>
								<td class="RIGHT">&nbsp;</td>
							</tr>
						</tbody>
					</table>
	        	</div>
		        <div id="divHeaderCustom">
		            <table id="subHeaderCustom">
		                <tr>
							<th class="order">No.</th>
							<th class="ActionClass">Component</th>
							<th class="Remark">Remark</th>
		                </tr>
		            </table>
		        </div>
		        <div id="divDetailCustom">
		            <table id="tableId_divDetailCustom">
		            	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			<a href="<s:url value='/jsp/component/globalDataComponent.jsp'/>">Global Data</a>
		               		</td>
		               		<td class="Remark">
		               			Global Data เป็นโหลดข้อมูล Select Item ผ่าน Action และ Set ผ่าน Model มาวาดที่หน้า JSP ผ่าน Tag &lt;s:select&gt;<br>
		               			ซึ่ง Select Item แบบนี้ไม่สามารถทำ Filter ได้<br>
		               			<br>
		               			โดยมีหลักการทำงานดังนี้<br>
		               			Action จะเรียกไปที่ SelectItemRMIProviders<br>
		               			เพื่อร้องขอข้อมูล Global Data ถูกเก็บอยู่ที่ Central Server<br>
		               			ผ่านทาง Java Remote Method Invocation (RMI) ซึ่งถูกโหลดค่าเก็บไว้เมื่อ Start Server<br> 
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			<a href="javascript:gotoo('selectItemComponent');">Select Item</a>
		               		</td>
		               		<td class="Remark">
		               			Select Item เป็นโหลดข้อมูล Select Item ผ่าน Action และ Set ผ่าน Model มาวาดที่หน้า JSP ผ่าน Tag &lt;s:select&gt;<br>
		               			ซึ่ง Select Item แบบนี้ไม่สามารถทำ Filter ได้<br>
		               			<br>
		               			โดยมีหลักการทำงานดังนี้<br>
		               			Action จะเรียกไปที่ SelectItemRMIProviders<br>
		               			เพื่อร้องขอข้อมูล Select Item ที่ Central Server<br>
		               			ผ่านทาง Java Remote Method Invocation (RMI) ซึ่งข้อมูลจะถูก excute จากฐานข้อมูล เป็นครั้งๆ ไป<br>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			<a href="javascript:gotoo('dropdownlistComponent');">Dropdownlist</a>
		               		</td>
		               		<td class="Remark">
		               			Dropdownlist เป็นโหลดข้อมูล Select Item ผ่าน SelectItemServlet โดยใช้ Ajax และรับ-ส่งอยู่ในรูปแบบ JSON<br>
		               			ซึ่งสามารถเพิ่มเงื่อนไขสำหรับ Filter ได้เช่น จังหวัด อำเภอเป็นต้น<br>
		               			<br>
		               			โดยมีหลักการทำงานดังนี้<br>
		               			Ajax จะเรียกไปที่ SelectItemServlet และเรียกไปที่ SelectItemRMIProviders<br>
		               			เพื่อร้องขอข้อมูล Select Item ที่ Central Server<br>
		               			ผ่านทาง Java Remote Method Invocation (RMI) ซึ่งข้อมูลจะถูก excute จากฐานข้อมูล เป็นครั้งๆ ไป<br>
		               		</td>
		               	</tr>
		               	<tr>
		               		<td class="order"><%=++rowno%>.</td>
		               		<td class="ActionClass">
		               			<a href="javascript:gotoo('autocompleteComponent');">Autocomplete</a>
		               		</td>
		               		<td class="Remark">
		               			Autocomplete มีการทำงานคล้ายกับ Dropdownlist โดยเพิ่มความสามารถกรอกข้อมูลสำหรับ Filter ได้
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
	
	<%--
		2. <s:property value="@com.cct.inhouse.enums.GlobalType@AUTO_TIME"/><br>
		3. <s:property value="@java.util.Locale@ENGLISH"/><br>
		3. <s:property value="@getLocale()"/><br>
		4. <s:property value="@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale()"/><br>
		5. <s:property value="@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getMapGlobalData()"/><br>
		6. <s:property value="@com.cct.inhouse.service.SelectItemProviders@getSelectItems(@com.cct.inhouse.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getApplicationLocale(), @com.cct.inhouse.enums.GlobalType@AUTO_TIME)"/><br>
	--%>
		
		<br>	
	</s:form>
</body>
</html>