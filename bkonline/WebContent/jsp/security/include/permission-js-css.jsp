<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	$(document).keypress(function(e) {
	    if(e.which == 13) {
	    	login();
	    }
	});

	function login() {

		if(validateFormInputAll() == false){
			return false;
		}

		
		submitPage("<s:url value='/jsp/security/loginUser.action' />");
	}

	function saveAdd() {
		if(validateFormInputAll() == false){
			return false;
		}
		submitPage("<s:url value='/jsp/security/savelineProfileUser.action' />");
	}
	
	function gotoMeeting() {
		submitPage("<s:url value='/jsp/security/initPermission.action' />");
	}

	
	
</script>
<style type="text/css">
	TABLE.FORM TD {
		/** border: 1px solid black; **/
	} 
	
	INPUT[type=text] {
		width: 55%;
	}
</style>