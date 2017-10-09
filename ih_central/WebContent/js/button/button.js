/**
 * Standard button
 */

function initButton(){
	//Default button
	jQuery(":button").button();

	//ปุ่มและ icon
	jQuery(".btnSearch").button({
		icons: {
			primary: "ui-icon-search"
	    }
	});

	jQuery(".btnRefresh").button({
	    icons: {
	        primary: "ui-icon-refresh"
	    }
	});

	jQuery(".btnEdit").button({
	    icons: {
	        primary: "ui-icon-disk"
	    }
	});

	jQuery(".btnCancel").button({
	    icons: {
	        primary: "ui-icon-close"
	    }
	});

	jQuery(".btnAdd").button({
	    icons: {
	        primary: "ui-icon-disk"
	    }
	});

	jQuery(".btnPrint").button({
	    icons: {
	        primary: "ui-icon-print"
	    }
	});

	jQuery(".btnClear").button({
	    icons: {
	        primary: "ui-icon-close"
	    }
	});
	
	jQuery(".btnClose").button({
	    icons: {
	        primary: "ui-icon-close"
	    }
	});
	
	jQuery(".btnSave").button({
	    icons: {
	        primary: "ui-icon-disk"
	    }
	});
	
	jQuery(".btnRegister").button({
		icons: {
			primary: "ui-icon-disk"
		}
	});
	
	jQuery(".btnBack").button({
		icons: {
			primary: "ui-icon-triangle-1-w"
		}
	});
	
	jQuery(".btnOK").button({
		icons: {
			primary: "ui-icon-disk"
		}
	});
	
	jQuery(".btnDeleteCustom").button({
		icons: {
			primary: "ui-icon-trash"
		}
	});
	
	jQuery(".btnBilling").button({
		icons: {
			primary: "ui-icon-circle-triangle-e"
		}
	});
	
	jQuery(".btnSale").button({
		icons: {
			primary: "ui-icon-circle-triangle-w"
		}
	});
}

/**
* สำหรับ print หน้าจอในรูปแบบ browser
*/
function printBrowser() {
	/*
	if (typeof document.getElementsByName("printId")[0] != "undefined") {
		var operationId = document.getElementsByName("printId")[0].value;
	    $.ajax({
	        type: 'post',
	        url: contextPath +'/jsp/log/printLog.action',
	        data: {
	          'operationId': operationId,
	        },
	        success: function (response) {

	        },
	        error: function () {

	        }
	    });
	}
	*/
	window.print();
    return false;
}