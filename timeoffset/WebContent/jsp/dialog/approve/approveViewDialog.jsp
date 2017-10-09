<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<script type="text/javascript">


	function initApproveViewDialog(){
				
		var params = {id : ids};
		
		jQuery("#criteriaIds").val(ids);
		
		hideTable();
		
		jQuery.ajax({
	        type : "POST",
	        url : "<s:url value='/jsp/dialog/searchListByIdApproveDialog.action' />",
	        data : jQuery.param(params),
	        async : false,
	        success : function(data) {
				initialCriteria(data.criteriaPopup);
				console.log(data);
				renderData("popupResult", data.listReference, data.messagePopup, data.criteriaPopup, false);
	        }
	    });
		
	}
	
	function researchListReference(){
		
		jQuery.ajax({
			type : "POST",
			url : "<s:url value='/jsp/dialog/searchListApproveDialog.action' />",
			data : jQuery(divPopup + ' :input').serialize(),
			async : true,
			success : function(data) {
				renderData("popupResult", data.listReference, data.messagePopup, data.criteriaPopup, false);
			}
		});
		
	}
	
	function hideTable(){
		//Hide total records
		jQuery("#popupResult").find("table.TOTAL_RECORD").hide();
		
		//Hide border & Icon triangle 
		jQuery("#boxHeaderpopupResult table tbody tr .RESULT_HEADER_SORT").find("th:nth-child(1)").css("border","0px");
		jQuery("#boxHeaderpopupResult table tbody tr .RESULT_HEADER_SORT").find("th:nth-child(2)").hide();
	}
	
	function closeDialog(){
		jQuery('#divIdApproveViewDialog').dialog('close');
	}

</script>
		<br/>
		<!------------------------------------- TABLE ----------------------------------->
		<!-- div ผลลัพธ์จากการค้นหาที่หน้า Popup  -->
		<div id="popupResult" style="display:none; width: 100%; height:350px; " ></div>
		<s:set var="divresult" value='{"popupResult"}'/> 
		<s:set var="columnName" value='{getText("to.approve"),getText("to.view"), "วันที่ - เวลา เริ่มต้น","วันที่ - เวลา สิ้นสุด","จำนวนชั่วโมง"}'/>
		<s:set var="columnData" value='{"conditionProject","workDetail","startDateTime","endDateTime","time"}'/>
		<s:set var="columnCSSClass" value='{"col-width-225px","col-width-200px","col-width-175px date","col-width-175px date","number_right"}'/>
		<s:set var="ajaxFunction" value='%{""}'/>
		<s:set var="criteriaName" value='%{"criteriaPopup"}'/>
		<s:set var="settingTable" value='{"null:false:true"}'/>
		<!-- include table template -->
		<s:include value="/jsp/template/tableDialogWindows.jsp"/>
		<!------------------------------------- TABLE ----------------------------------->
		
		<table class="FORM">
				
		<tr>
			<td class="BORDER"></td>
			<td class="LABEL"></td>
			<td class="VALUE"></td>
			<td class="LABEL"></td>
			<td class="VALUE">
				<!------------------------------------- BUTTON ----------------------------------->
				<button id="btnClose" class="jbutton ui-icon-close" onclick="closeDialog();"><s:text name='to.close'/></button>
				<!------------------------------------- BUTTON ----------------------------------->
			</td>
			<td class="BORDER"></td>
			</tr>
		</table>
	
	<s:hidden id="criteriaIds" name="criteriaPopup.approve.ids"/>
	
	