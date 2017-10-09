<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>

<script type="text/javascript">

	var updateRow;

	function gotoEdit(id,order){

		divId = '#divIdApproveEditDialog';	
		ids = id;
		updateRow = order;
			
		jQuery(divId).dialog({
			autoOpen : false,
			height : 600,
			width : 1200,
			modal : true,
			open : function() {
				initApproveEditDialog();
			}, 
			close: function() {
		    	$(this).dialog("destroy");
			}
		});
		
		jQuery(divId).dialog('open');

	}
	
	function manageRowApprove(page){
		
		//Change Icon
		jQuery(".d_approve").each(function(i){
			
			//ถ้าเป็น Header ไม่ต้องจัดการ
			if(i > 0){
				
				//get ค่า approveStatus ในแต่ละ row
				var txtApproveStatus = jQuery(this).text();
				
				//set ค่า title จาก list ที่ hidden ไว้
				var title = jQuery("[name='approve.listDetail["+(i-1)+"].approveStatusDesc']").val();
				
				//set title ที่ได้ ลงใน  html icon ที่จะไปแทน approveStatus
				var iconApprove = "<img class='cursor' title='"+ title + "' src='<s:url value='"+centralPath+"/images/menu/i_check.png'/>'>";
				var iconDisApprove = "<img class='cursor' title='"+ title + "' src='<s:url value='"+centralPath+"/images/menu/i_delete.png'/>'>";
				var iconWaitApprove = "<img class='cursor' title='"+ title + "' src='<s:url value='"+centralPath+"/images/menu/i_help.png'/>'>";
				
				//Check value approveStatus เพื่อ สลับ icon
				if(txtApproveStatus == "W"){
					jQuery(this).html(iconWaitApprove);
				}else if(txtApproveStatus == "D"){
					jQuery(this).html(iconDisApprove);
				}else if(txtApproveStatus == "A"){
					jQuery(this).html(iconApprove);
				}
			}
		});
		
		if(page == "approve"){
			
			//Hide edit
			jQuery(".edit").each(function(i){
				
				//ถ้าเป็น Header ไม่ต้องจัดการ
				if(i > 0){
					
					//get ค่า approveStatus ในแต่ละ row
					var txtApproveStatus = jQuery("[name='approve.listDetail["+(i-1)+"].approveStatus']").val();
					
					//Check value approveStatus เพื่อ  hide checkbox
					if(txtApproveStatus == "A"){
						jQuery(this).html("");
					}else if(txtApproveStatus == "D"){
						jQuery(this).html("");
					}
				}
			});
			
			//Hide Checkbox
			jQuery(".d_checkbox").each(function(i){
				
				//ถ้าเป็น Header ไม่ต้องจัดการ
				if(i > 0){
					
					//get ค่า approveStatus ในแต่ละ row
					var txtApproveStatus = jQuery("[name='approve.listDetail["+(i-1)+"].approveStatus']").val();
					
					//Check value approveStatus เพื่อ  hide checkbox
					if(txtApproveStatus == "A"){
						jQuery(this).html("");
					}else if(txtApproveStatus == "D"){
						jQuery(this).html("");
					}
				}
			});
		}else if(page == "view"){
			
			//Hide All Checkbox
			jQuery(".checkbox").hide();
		}
		
		
	}
	
	function callBackGoToEdit(day,hour,min,txtStartDateTime,txtEndDateTime,remark){
		
// 		console.log("day : " + day);
// 		console.log("hour : " + hour);
// 		console.log("min : " + min);
// 		console.log("txtStartDateTime : " + txtStartDateTime);
// 		console.log("txtEndDateTime : " + txtEndDateTime);
		
		var numHour = (parseInt(day)*8) + parseInt(hour);
		var numMinute = parseInt(min);
		var listName = "approve.listDetail";
		
// 		console.log("updateRow : " + updateRow); 
// 		console.log("updateRow - 1 : " + (updateRow - 1));
		
		
		//Update ค่าที่แก้ไขใน rowUpdate
		jQuery("#tableId_divDetailCustom tbody tr").eq(updateRow-1).find("td.day").html(day);
		jQuery("#tableId_divDetailCustom tbody tr").eq(updateRow-1).find("td.hour").html(hour);
		jQuery("#tableId_divDetailCustom tbody tr").eq(updateRow-1).find("td.minute").html(min);
		jQuery("#tableId_divDetailCustom tbody tr").eq(updateRow-1).find("td.start").html(txtStartDateTime);
		jQuery("#tableId_divDetailCustom tbody tr").eq(updateRow-1).find("td.end").html(txtEndDateTime);
		
	
		
		//Edit ค่าที่ Hidden ไว้		
		jQuery("input[name='" + listName +"["+(updateRow - 1)+"].startDateTime']").val(txtStartDateTime);
		jQuery("input[name='" + listName +"["+(updateRow - 1)+"].endDateTime']").val(txtEndDateTime);
		jQuery("input[name='" + listName +"["+(updateRow - 1)+"].day']").val(day);
		jQuery("input[name='" + listName +"["+(updateRow - 1)+"].hour']").val(hour);
		jQuery("input[name='" + listName +"["+(updateRow - 1)+"].minute']").val(min);
		jQuery("input[name='" + listName +"["+(updateRow - 1)+"].remark']").val(remark);

	}

</script>

	<!-- div สำหรับแสดงผล  -->
	<fieldset class="fieldsetStyle">
		<legend><s:text name="to.workDetail"></s:text> <s:property value='approve.projectABBR' /> (<s:property value='approve.projectConDetail' />) </legend>
		
		<div id="divResult">
		    <div id="divResultBorderCustom">
		         
		        <!-- *********************** 2.div header **************************** -->
		        <div id="divHeaderCustom">
		            <table id="subHeaderCustom">
		                <tr>
		                    <th class="order"> <s:text name="no" /> </th>
		                    <th class="checkbox"><input type="checkbox" class="d_checkbox" name="chkall" onClick="checkboxToggleDataTable('criteria.selectedIds',this)" /></th>
		                    <th class="col-width-auto"> <s:text name="to.workDetail" /> </th>
		                    <th class="col-width-175px"> <s:text name="to.startDateTime" /> </th>
		                    <th class="col-width-175px"> <s:text name="to.endDateTime" /> </th>
		                    <th class="col-width-75px"> <s:text name="to.totalDayOffset" /> </th>
		                    <th class="col-width-75px"> <s:text name="to.totalHourOffset" /> </th>
		                    <th class="col-width-75px"> <s:text name="to.totalMinuteOffset" /> </th>
		                    <th class="col-width-75px d_approve"> <s:text name="to.approveStatusTO" /></th>
		                    <s:if test="page.getPage() == 'approve'">
		                    	<th class="col-width-100px edit"> <s:text name="edit" /> </th>
		                    </s:if>
		                </tr>
		            </table>
		        </div>
		        <!-- *********************** 2.div header **************************** -->
		         
		        <!-- *********************** 3.div detail **************************** -->
		        <div id="divDetailCustom">
		            <table id="tableId_divDetailCustom">
		          
		                <s:iterator value="approve.listDetail" status="status" var="result">
                                <s:if test="#status.even == true">
                                    <s:set var="tabclass" value="%{'LINE_ODD'}" />
                                </s:if>
                                <s:else>
                                    <s:set var="tabclass" value="%{'LINE_EVEN'}" />
                                </s:else>
                                <tr class="<s:property value='tabclass' /> ">
                                    <td class="order"><s:property value="#result.rownum" /></td>
                                 	<td class="checkbox d_checkbox center"><input type="checkbox" id="" name="criteria.selectedIds" class="flatStyle" value='<s:property value="#result.id" />'>
                                 		<s:hidden id="approve_listDetail[%{#status.index}]_id" name="approve.listDetail[%{#status.index}].id" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_rownum" name="approve.listDetail[%{#status.index}].rownum" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_workDetail" name="approve.listDetail[%{#status.index}].workDetail" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_timeOffsetId" name="approve.listDetail[%{#status.index}].timeOffsetId" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_timeOffsetDetId" name="approve.listDetail[%{#status.index}].timeOffsetDetId" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_startDateTime" name="approve.listDetail[%{#status.index}].startDateTime" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_endDateTime" name="approve.listDetail[%{#status.index}].endDateTime" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_day" name="approve.listDetail[%{#status.index}].day" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_hour" name="approve.listDetail[%{#status.index}].hour" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_minute" name="approve.listDetail[%{#status.index}].minute" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_remark" name="approve.listDetail[%{#status.index}].remark" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_approveStatus" name="approve.listDetail[%{#status.index}].approveStatus" />
                                    	<s:hidden id="approve_listDetail[%{#status.index}]_approveStatusDesc" name="approve.listDetail[%{#status.index}].approveStatusDesc" />
                                 	</td>
                                    <td class="col-width-auto"><s:property value="#result.workDetail" /></td>
                                    <td class="col-width-175px date start"><s:property value="#result.startDateTime" /></td>
                                    <td class="col-width-175px date end"><s:property value="#result.endDateTime" /></td>
                                    <td class="col-width-75px number_right day"><s:property value="#result.day" /></td>
                                    <td class="col-width-75px number_right hour"><s:property value="#result.hour" /></td>
                                    <td class="col-width-75px number_right minute"><s:property value="#result.minute" /></td>
                                 	<td class="col-width-75px date d_approve"><s:property value="#result.approveStatus" /></td>
                                 	
                                 	<s:if test="page.getPage() == 'approve'">
										<td class="checkbox col-width-100px edit">
	                                        <img src='<%=ParameterConfig.getParameter().getContextConfig().getURLContextCentral()%>/images/icon/i_edit.png'
	                                            title="<s:text name='edit'/>" 
	                                            class="cursor"
	                                            onclick="gotoEdit('<s:property value='#result.id' />','<s:property value='#result.rownum' />')"
	                                        />
	                                    </td>
									</s:if>
                                     
                                    
                                </tr>
                            </s:iterator>
                            
		            </table>
		        </div>
		        <!-- *********************** 3.div detail **************************** -->
		        <!-- *********************** 4.div footer **************************** -->
		        <div id="divFooterCustom">
		            <table id="tableId_divFooterCustom">
		            	<tr>
		            		<td class="col-width-75px">
								<a id="footerTable"></a>
							</td>
		            	</tr>
		            </table>
		        </div>        
		        <!-- *********************** 4.div footer **************************** -->
		       
		     
		    </div>
		</div>
		
	</fieldset>	
	
	<!-- 	1. ส่วนของการ include jsp file -->
	<div id="divIdApproveEditDialog" title="<s:text name='to.dialogEditHour'/>" style="display: none;">
		<s:include value="/jsp/dialog/approve/approveEditDialog.jsp"/>
	</div>
	
	