	var flagCheckMenu = false;
	var maxDay  = "31";
	var minDay  = "0";
	var mapMenu = new Object();
	var widthMenu = 0;
	var windowScreenWidth = 0
	var sizeScreen1200 = 1183;
	var sizeScreen1600 = 1583;
	var activateTab = 0;
	var previousActiveTab = 0;
	var useGlobalTab = true;	
	var useGlobalFocus = true;
	$( document ).ready(function() {
		if (typeof(document.getElementsByName("urlEdit")[0]) == "undefined"){
			$("#reload").hide();
		}
	});

	
	document.onkeydown = function(e) { 
		e = e || window.event;
		var keyCode = e.keyCode || e.which;
		if(keyCode == 112) {
			if(e.ctrlKey) {
				if (typeof F1 == 'function') { 
					F1(); 
				}
			}
		}		
		
		if(keyCode == 113) {
			if(e.ctrlKey) {
				if (typeof F2 == 'function') { 
					F2(); 
				}
			}
		}
		
		if(keyCode == 114) {
			if(e.ctrlKey) {
				if (typeof F3 == 'function') { 
					F3(); 
				}
			}
		}
		
		if(keyCode == 115) {
			if(e.ctrlKey) {
				if (typeof F4 == 'function') { 
					F4(); 
				}
			}
		}
		
		if(keyCode == 117) {
			if(e.ctrlKey) {
				if (typeof F6 == 'function') { 
					F6(); 
				}
			}
		}
		
		if(keyCode == 118) {
			if(e.ctrlKey) {
				if (typeof F7 == 'function') { 
					F7(); 
				}
			}
		}
		
		if(keyCode == 119) {
			if(e.ctrlKey) {
				if (typeof F8 == 'function') { 
					F8(); 
				}
			}
		}
		
		if(keyCode == 120) {
			if(e.ctrlKey) {
				if (typeof F9 == 'function') { 
					F9(); 
				}
			}
		}
		
		if(keyCode == 121) {
			if(e.ctrlKey) {
				if (typeof F10 == 'function') { 
					F10(); 
				}
			}
		}
		
		if(keyCode == 122) {
			if(e.ctrlKey) {
				if (typeof F11 == 'function') { 
					F11(); 
				}
			}
		}
		
		if(keyCode == 123) {
			if(e.ctrlKey) {
				if (typeof F12 == 'function') { 
					F12(); 
				}
			}
		}
		
		if(keyCode == 27) {
			if(e.ctrlKey) {
				if (typeof ESC == 'function') { 
					ESC(); 
				}
			}
		}
	}

	/** กำหนดขนาดของ LoaderStatus 
		- ความยาว/ความกว้าง
		- ตำแหน่งของรูปภาพ ที่ต้องอยู่ตรงกลางของหน้าจอ  
	**/
	function showLoaderStatus(){
		// Create the overlay
		var overlay = jQuery('<div></div>').css({
				'background-color': '#fff',
				'opacity':0.7,
				'width':document.body.scrollWidth,
				'height':(jQuery("#BODY_TR_CONTENT").height() + jQuery("#BODY_TR_HEADER").height()),
				'position':'absolute',
				'top':'0px',
				'left':'0px',
				'z-index':99999
		}).addClass('ajax_overlay');

		jQuery("body").append(overlay.append(
				jQuery('<div></div>').addClass('ajax_loader')
			).fadeIn(200)
		);
	}
	
	function hideLoaderStatus(){
		jQuery("div.ajax_overlay").remove();
    }

    function reloadEditPage(){
		if (typeof(document.getElementsByName("urlEdit")[0]) != "undefined"){
			var url =document.getElementsByName("urlEdit")[0].value ;
			submitPage(url);
		}

	}  

    