<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.cct.inhouse.core.config.parameter.domain.ParameterConfig"%>
<%
	String defaultEnable = "enable";
	String defaultEnable_ATH = "true";

	String defaultNextTab = "fristtag";
	String defaultBackTab = "lasttag";
	String[] buttonAttribute = null;
	String buttonType = "";
	String criteriaName = "criteria";

	String[] functionAttribute = null;
	String function = "";
	try {
		if (request.getParameter("buttonType") != null) {
			buttonType = request.getParameter("buttonType").trim();
			buttonAttribute = buttonType.split(",");
			if (buttonAttribute.length < 1) {
				buttonAttribute = null;
				return;
			} else {
				for (int i = 0; i < buttonAttribute.length; i++) {
					buttonAttribute[i] = buttonAttribute[i].trim();
				}
			}

		}

		if (request.getParameter("criteriaName") != null) {
			criteriaName = request.getParameter("criteriaName")
					.toString();
		}
		request.setAttribute("criteriaName", criteriaName);

		if (request.getParameter("function") != null) {
			function = request.getParameter("function").trim();
			functionAttribute = function.split(",");
		}
	} catch (Exception ex) {
		//ex.printStackTrace();
	}
%>

<script type="text/javascript" src="${application.CONTEXT_CENTRAL}/js/button/button.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function(){
		var contextPath='<%=request.getContextPath()%>';
		initButton(contextPath);
		
		jQuery(".btnGotoAdd").button({
		    icons: {
		        primary: "ui-icon-plus"
		    }
		});
		
		jQuery(".btnClearF2").button({
		    icons: {
		        primary: "ui-icon-refresh"
		    }
		});
		jQuery(".btnCheckBlacklist").button({
		    icons: {
		        primary: "ui-icon-check"
		    }
		});
	});
</script>
<s:if test="%{#buttonType.size() > 0}">
	<s:if test="%{#buttonType[0].toUpperCase() == 'GOTOADD'}">
		<s:if test="#buttonFunction.size() > 0">
			<button id="btnGotoAdd" class="btnGotoAdd" type="button"
				onclick='<s:property value="#buttonFunction[0]"/>();'></button>
		</s:if>
		<s:else>
			<button id="btnGotoAdd" class="btnGotoAdd" type="button"
				onclick="return gotoAddPage();"></button>
		</s:else>
	</s:if>
	<s:else>
		<table class="BUTTON">
			<tr>
				<td class="LEFT RIGHT_70"></td>
				<td class="RIGHT LEFT_30">
					<s:if test="%{#buttonType.size() > 0}">
						<s:iterator value="#buttonType" status="status" var="buttonAttr">
							<!----------------------------------------
						###[1] CHECK ENABLE STATUS BUTTON 
						----------------------------------------->
							<s:set var="statusEnable" value="#buttonEnable[#status.index]" />

							<!----------------------------------------
						###[2] CHECK FUNCTION BUTTON 
						----------------------------------------->
							<s:set var="function" value="" />
							<s:if
								test="#buttonFunction[#status.index] != null && #buttonFunction[#status.index] != ''">
								<s:set var="btnFunction"
									value='#buttonFunction[#status.index].split(":")' />

								<s:iterator value="btnFunction" status="num" var="item">
									<s:if test="#function != null && #function != ''">
										<s:if test="#num.index == 1">
											<s:set var="function" value="#item +' '+ #function" />
										</s:if>
										<s:else>
											<s:set var="function"
												value='#function + "\'" + #item + "\'" +","' />
										</s:else>
									</s:if>
									<s:else>
										<s:set var="function" value="#item + '('" />
									</s:else>
								</s:iterator>

								<s:if test="#btnFunction.length == 1">
									<s:set var="function"
										value="#function.substring(0,#function.length()-1)+ '();'" />
								</s:if>
								<s:elseif test="#btnFunction.length == 2">
									<s:set var="function"
										value="#function.substring(0,#function.length())+ ');'" />
								</s:elseif>
								<s:else>
									<s:set var="function"
										value="#function.substring(0,#function.length()-1)+ ');'" />
								</s:else>
							</s:if>

							<!----------------------------------------
						###[3] CREATE BUTTON
						----------------------------------------->
							<s:if test="#buttonAttr.toUpperCase() == 'SEARCH'">
								<s:if test="#statusEnable">
									<button id="btnSearch" class="btnSearch" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="search" />
									</button>
								</s:if>
								<s:else>
									<button id="btnSearch" class="btnSearch" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="search" />
									</button>
								</s:else>
							</s:if>

							<s:if test="#buttonAttr.toUpperCase() == 'CLEAR'">
								<s:if test="#statusEnable">
									<button id="btnClear" class="btnClear" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="clear" />
									</button>
								</s:if>
								<s:else>
									<button id="btnClear" class="btnClear" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="clear" />
									</button>
								</s:else>
							</s:if>

							<!-- Send email -->
							<s:if test="#buttonAttr.toUpperCase() == 'SENDEMAIL'">
								<s:if test="#statusEnable">
									<button id="btnSendEmail" class="btnSendEmail" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="Send email" />
									</button>
								</s:if>
								<s:else>
									<button id="btnSendEmail" class="btnSendEmail" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="Send email" />
									</button>
								</s:else>
							</s:if>

							<!-- Edit -->
							<s:if test="#buttonAttr.toUpperCase() == 'EDIT'">
								<s:if test="#statusEnable">
									<button id="btnEdit" class="btnEdit" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="Edit" />
									</button>
								</s:if>
								<s:else>
									<button id="btnEdit" class="btnEdit" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="Edit" />
									</button>
								</s:else>
							</s:if>

							<!-- Back -->
							<s:if test="#buttonAttr.toUpperCase() == 'BACK'">
								<s:if test="#statusEnable">
									<button id="btnBack" class="btnBack" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="back" />
									</button>
								</s:if>
								<s:else>
									<button id="btnBack" class="btnBack" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="back" />
									</button>
								</s:else>
							</s:if>

							<!-- Add another record -->
							<s:if test="#buttonAttr.toUpperCase() == 'ADD_ANOTHER_RECORD'">
								<s:if test="#statusEnable">
									<button id="btnAddAnotherRecord" class="btnAddAnotherRecord"
										type="button" onclick="<s:property value='#function.trim()'/>">
										<s:text name="Add another record" />
									</button>
								</s:if>
								<s:else>
									<button id="btnAddAnotherRecord" class="btnAddAnotherRecord"
										type="button" onclick="return false;" tabindex="-1"
										disabled="disabled">
										<s:text name="Add another record" />
									</button>
								</s:else>
							</s:if>

							<!-- Register -->
							<s:if test="#buttonAttr.toUpperCase() == 'REGISTER'">
								<s:if test="#statusEnable">
									<button id="btnRegister" class="btnRegister" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="register" />
									</button>
								</s:if>
								<s:else>
									<button id="btnRegister" class="btnRegister" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="register" />
									</button>
								</s:else>
							</s:if>

							<!-- Cancel -->
							<s:if test="#buttonAttr.toUpperCase() == 'CANCEL'">
								<s:if test="#statusEnable">
									<button id="btnCancel" class="btnCancel" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="cancel" />
									</button>
								</s:if>
								<s:else>
									<button id="btnCancel" class="btnCancel" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="cancel" />
									</button>
								</s:else>
							</s:if>

							<!-- Save -->
							<s:if test="#buttonAttr.toUpperCase() == 'SAVE'">
								<s:if test="#statusEnable">
									<button id="btnSave" class="btnSave" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="save" />
									</button>
								</s:if>
								<s:else>
									<button id="btnSave" class="btnSave" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="save" />
									</button>
								</s:else>
							</s:if>

							<!-- CLEAR(F2) -->
							<s:if test="#buttonAttr.toUpperCase() == 'CLEARF2'">
								<s:if test="#statusEnable">
									<button id="btnClearF2" class="btnClearF2" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="Clear(F2)" />
									</button>
								</s:if>
								<s:else>
									<button id="btnClearF2" class="btnClearF2" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="Clear(F2)" />
									</button>
								</s:else>
							</s:if>

							<!-- CHECKBLACKLIST -->
							<s:if test="#buttonAttr.toUpperCase() == 'CHECKBLACKLIST'">
								<s:if test="#statusEnable">
									<button id="btnCheckBlacklist" class="btnCheckBlacklist"
										type="button" onclick="<s:property value='#function.trim()'/>">
										<s:text name="Check Black List & Save(F12)" />
									</button>
								</s:if>
								<s:else>
									<button id="btnCheckBlacklist" class="btnCheckBlacklist"
										type="button" onclick="return false;" tabindex="-1"
										disabled="disabled">
										<s:text name="Check Black List & Save(F12)" />
									</button>
								</s:else>
							</s:if>
							<!-- Add another record -->
							<s:if test="#buttonAttr.toUpperCase() == 'FILLTER'">
								<s:if test="#statusEnable">
									<button id="btnFillter" class="btnFillter" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="Fillter" />
									</button>
								</s:if>
								<s:else>
									<button id="btnFillter" class="btnFillter" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="Fillter" />
									</button>
								</s:else>
							</s:if>
							<!-- Add SUBMIT -->
							<s:if test="#buttonAttr.toUpperCase() == 'SUBMIT'">
								<s:if test="#statusEnable">
									<button id="btnAddCheckInOut" class="btnSubmit" type="button" onclick="<s:property value='#function.trim()'/>">
										<s:text name="Submit"/>
									</button>
								</s:if>
								<s:else>
									<button id="btnAddCheckInOut"  class="btnSubmit" type="button" onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="Submit"/>
									</button>
								</s:else>
							</s:if>
							
							<!-- OK -->
							<s:if test="#buttonAttr.toUpperCase() == 'OK'">
								<s:if test="#statusEnable">
									<button id="btnOK" class="btnOK" type="button" onclick="<s:property value='#function.trim()'/>">
										<s:text name="ok"/>
									</button>
								</s:if>
								<s:else>
									<button id="btnOK"  class="btnOK" type="button" onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="ok"/>
									</button>
								</s:else>
							</s:if>
							
							<!-- Request -->
							<s:if test="#buttonAttr.toUpperCase() == 'REQUEST'">
								<s:if test="#statusEnable">
									<button id="btnRequest" class="btnSave" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="request" />
									</button>
								</s:if>
								<s:else>
									<button id="btnRequest" class="btnSave" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="request" />
									</button>
								</s:else>
							</s:if>
							
							<!-- Check Status -->
							<s:if test="#buttonAttr.toUpperCase() == 'CHECK_STATUS'">
								<s:if test="#statusEnable">
									<button id="btnCheckStatus" class="btnCheckStatus" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="checkStatus" />
									</button>
								</s:if>
								<s:else>
									<button id="btnCheckStatus" class="btnCheckStatus" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="checkStatus" />
									</button>
								</s:else>
							</s:if>
							
							<!-- Add -->
							<s:if test="#buttonAttr.toUpperCase() == 'ADD'">
								<s:if test="#statusEnable">
									<button id="btnAdd" class="btnAdd" type="button"
										onclick="<s:property value='#function.trim()'/>">
										<s:text name="add" />
									</button>
								</s:if>
								<s:else>
									<button id="btnAdd" class="btnAdd" type="button"
										onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="add" />
									</button>
								</s:else>
							</s:if>
							
							<!-- NORMAL GOTO ADD -->
							<s:if test="#buttonAttr.toUpperCase() == 'GOTOADDNORMAL'">
								<s:if test="#statusEnable">
									<button id="btnGotoAddNormal" class="btnGotoAddNormal btnGotoAdd" type="button" onclick="<s:property value='#function.trim()'/>">
										<s:text name="Add"/>
									</button>
								</s:if>
								<s:else>
									<button id="btnGotoAddNormal"  class="btnGotoAddNormal btnGotoAdd" type="button" onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="Add"/>
									</button>
								</s:else>
							</s:if>
							
							<!-- DELETE -->
							<s:if test="#buttonAttr.toUpperCase() == 'DELETE'">
								<s:if test="#statusEnable">
									<button id="btnDelete" class="btnDelete" type="button" onclick="<s:property value='#function.trim()'/>">
										<s:text name="Delete"/>
									</button>
								</s:if>
								<s:else>
									<button id="btnDelete"  class="btnDelete" type="button" onclick="return false;" tabindex="-1" disabled="disabled">
										<s:text name="Delete"/>
									</button>
								</s:else>
							</s:if>
							
						</s:iterator>
					</s:if></td>
			</tr>
			<tr>
				<td style="height: 14px;" class="CENTER">&nbsp;</td>
			</tr>
		</table>
	</s:else>
</s:if>
<script type="text/javascript">
	// สำหรับลบ input ส่วนเกิน
	jQuery(document).ready(function() {
		jQuery("input[name='" + '<s:property value="%{#request.criteriaName}"/>' + ".criteriaKey']").each(function(index) {
			//console.info(index);
			if (index > 0) {
				jQuery(this).remove();
			}
		});

		jQuery("input[name='P_CODE']").each(function(index) {
			if (index > 0) {
				jQuery(this).remove();
			}
		});

		jQuery("input[name='F_CODE']").each(function(index) {
			if (index > 0) {
				jQuery(this).remove();
			}
		});

		jQuery("input[name='page']").each(function(index) {
			if (index > 0) {
				jQuery(this).remove();
			}
		});
	});
</script>
