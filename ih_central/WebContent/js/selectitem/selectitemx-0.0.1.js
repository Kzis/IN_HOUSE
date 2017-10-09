//default value of combobox use in javascript.jsp
//Start create combobox widgets

/**
 * feature list --------------
 * 1. โหลดข้อมูลผ่าน ajax
 * 2. set css style ได้
 * 3. set css class ได้
 * 4. ทำ trigger change ได้ โดยรับ parameter แบบ array
 * 4.1. เมื่อ post parameter เป็นว่างจะไม่ยิงขึ้นไปที่ server และ clear dropdownlist
 * 4.2. เมื่อ set value เป็น ว่าง dropdownlist จะ clear dropdownlist ตัวถัดไปให้เลย
 * 4.3. เมื่อ set value ไปที่ dropdownlist จะ load dropdownlist ตัวถัดไปให้เลย
 * 4.4. สามารถ disable และ enable ได้แบบตัวเดียวและ หลายตัว
 * 4.5. สามารถเขียนฟังก์ชั่นควบคุมการทำงานก่อน event change ได้ 
 * 4.6. สามารถเขียนฟังก์ชั่นควบคุมการทำงานหลัง  event change ได้
 */
(function( $ ) {
	$.widget( "custom.selectitemx", {
		version: "0.0.1",
		options: [{
			seq: 0,
			displayModelInput: "none",
			inputModelId: undefined,
		    url: undefined,
		    postParamsId: undefined,
		    postParameGenerate: undefined,
		    cssClass: undefined,
		    cssStyle: undefined,
		    defaultKey: undefined,
		    defaultValue: undefined,
		    submitType: "POST",
		    async: false,
		    disabled: false,
			requireInput: false,
			requireFill: false,
		    beforeChangeFunction: undefined,
		    afterChangeFunction: undefined,
		    selectitemType: undefined,			// A : Autocomplete , D : Dropdown
		    selectitemAjax: true,				// true : Autocomplete Ajax , false : Autocomplete Model
		    selectitemId: undefined,
		    selectitemCode: undefined,
		    selectitemText: undefined,
		    selectitemElement: undefined,
		    selectitemInput: undefined,
		    selectitemWrapper: undefined,
		    selectitemButton: undefined,
		    selectitemTimeoutToolTipId: undefined,
		    minLength: 0,
		    delay: 0,
		    limit: "",
		    searchAllIndex: false,				// หากเป็น true จะค้นหาจากตำแหน่งใดก็ได้
		    arrowDis : false,
		    clearForm: false,
	    	validName: "",
	    	postParamsRealtimeId: undefined		// id ของ element ที่ต้องการนำไปเป็นเงื่อนไขการ filter อื่นที่ไม่ได้มาจาก selectitem ที่ filter
		}],

		_create: function() {
			if (this._validateConfig() == false) {
				return false;
			}
			
			this._manageOptions();

			for (var key in this.options) {
				if (this.options[key].selectitemType == "A") {
					// create autocomplete
					this._createAutocomplete(this.options[key]);
					this._createShowAllButton(this.options[key]);
					
				} else if (this.options[key].selectitemType == "D") {
					// create dropdown
					this._createDropdownlist(this.options[key]);
					this._manageChangeDropdownFunction(key);
					
				}
			}
		},
		
		_validateConfig: function() {
			var input = undefined;
			for (var key in this.options) {
				// ประเภทของ selectitem dropdown หรือ autocomplete
				var selectitemType = this.options[key].selectitemType;
				if (selectitemType == undefined) {
					return false;
				}
				
				if (key == 0) {
					input = this.element;
				} else {
					input = jQuery("#" + this.options[key].inputModelId);
				}
				
				var selectitemId = jQuery(input).attr('code-of');
				if (selectitemId == undefined){
					selectitemId = jQuery(input).attr('text-of');
				}
				
				if (selectitemId == undefined){
					return false;
				}

				if ((jQuery("input[code-of='" + selectitemId + "']").length != 1)
						|| (jQuery("input[text-of='" + selectitemId + "']").length != 1)){
					return false;
				}
				
				this.options[key].selectitemId = selectitemId;
				this.options[key].selectitemCode = jQuery("#" + jQuery("input[code-of='" + selectitemId + "']").attr('id'));
				this.options[key].selectitemText = jQuery("#" + jQuery("input[text-of='" + selectitemId + "']").attr('id'));
				this.options[key].selectitemElement = jQuery("#" + selectitemId);
				
				// ใช้งานเฉพาะ autocomplete
				if (this.options[key].selectitemType == "A") {
					this.options[key].selectitemWrapper = jQuery( "<span>" ).addClass( "custom-combobox" ).insertAfter( this.options[key].selectitemElement );
				}
				
			}
			return true;
		},
		
		_manageOptions: function() {
			var input = undefined;
			for (var key in this.options) {
				if (key == 0) {
					input = this.element;	
				} else {
					input = jQuery("#" + this.options[key].inputModelId);
				}
				
				// กำหนดค่า sequence ให้ dropdownlist
				this.options[key].seq = key;

				// กำหนดค่า modelId โดยใช้ id จาก input
				if (this.options[key].inputModelId == undefined) {
					this.options[key].inputModelId = jQuery(input).attr('id');
				}
				
				// กำหนดค่า selectitemAjax โดยยึดที่ค่า true เสมอ
				if (this.options[key].selectitemAjax == undefined) {
					this.options[key].selectitemAjax = true;
				}
				
				// กำหนดค่า css และ style โดยยึดที่ config เป็นหลัก
				if (this.options[key].cssClass == undefined) {
					this.options[key].cssClass = jQuery(input).attr('class');
				}
				
				// กำหนดค่า css และ style โดยยึดที่ config เป็นหลัก
				if (this.options[key].cssStyle == undefined) {
					this.options[key].cssStyle = jQuery(input).attr('style');
				}

				// กำหนดค่า readonly โดยยึดที่ค่า true เสมอ
				if (this.options[key].disabled == undefined) {
					this.options[key].disabled = false;
				}
				
				if (this.options[key].disabled == false) {
					if (jQuery(input).attr('disabled')) {
						this.options[key].disabled = true;	
					}
				}

				// กำหนดค่า requireInput โดยยึดที่ค่า true เสมอ
				if (this.options[key].requireInput == undefined) {
					this.options[key].requireInput = false;
				}
				
				if (this.options[key].requireInput == false) {
					if (jQuery(input).hasClass('requireInput')) {
						this.options[key].requireInput = true;	
					}
				}
				
				// กำหนดค่า requireFill โดยยึดที่ค่า false เสมอ
				if (this.options[key].requireFill == undefined) {
					this.options[key].requireFill = false;
				}
				
				if (this.options[key].requireFill == false) {
					if (jQuery(input).hasClass('requireFill')) {
						this.options[key].requireFill = true;	
					}
				}
				
				// กำหนดค่า post parameter
				if (this.options[key].postParamsId != undefined) {
					//this.options[key].postParameGenerate = generatePostParameters(this.options[key].postParamsId);
				}
				
				if (this.options[key].submitType == undefined) {
					this.options[key].submitType = "POST";
				}

				if (this.options[key].async == undefined) {
					this.options[key].async = false;
				}

				if (this.options[key].displayModelInput == undefined) {
					this.options[key].displayModelInput = "none";
				}
				
				if (this.options[key].minLength == undefined) {
					this.options[key].minLength = 0;
				}
				
				if (this.options[key].delay == undefined) {
					this.options[key].delay = 750;
				}
				
				if (this.options[key].limit == undefined) {
					this.options[key].limit = "";
				}
				
				if (this.options[key].arrowDis == undefined) {
					this.options[key].arrowDis = false;
				}
				
				if (this.options[key].arrowDis == true) {
					this.options[key].arrowDis = true;
				}
				
				if (this.options[key].validName == undefined) {
					this.options[key].validName = "";
				}
				
				if (this.options[key].validName == "") {
					if (jQuery(input).attr('validName') != undefined) {
						this.options[key].validName = jQuery(input).attr('validName');
					}
				}
				
				if (this.options[key].clearForm == undefined) {
					this.options[key].clearForm = false;
				}
			}
		},
		
		_createAutocomplete: function(currentOptions) {
			// สร้าง post parameter ก่อน เพื่อไว้ตรวจสอบว่าจะ call ไปยัง servlet หรือไม่
			currentOptions.postParameGenerate = generatePostParameters(currentOptions.postParamsId);

			if (currentOptions.postParamsId == undefined) {
				// เป็น autocomplete ตัวแรก (ไม่มี post paramsId)
				if (currentOptions.url != undefined) {
					// กรณีไม่มี post paramsId แต่มี url
					if (currentOptions.selectitemAjax) {
						// กรณีเป็น autocomplete ajax จะวาด autocomplete ว่างๆ รอการกรอกข้อมูลถึงจะค้นหา และวาดใหม่
						drawingSelectItemFromJson(undefined, currentOptions);
						currentOptions.selectitemElement.removeAttr('disabled').removeClass('readonly');
						
					} else {
						// กรณีเป็น autocomplete ปรกติ จะวาด autocomplete จากข้อมูลที่ ค้นหาได้ทันที
						this._loadData(currentOptions);
					}
					
				} else {
					// กรณี autocomplete ตัวแรก สร้างจาก selectitem (ไม่มี url)
					jQuery("span[id ^='" + currentOptions.selectitemId + "']").hide();
				}
				this._createDataAutocomplete(currentOptions);
				
			} else {
				if (haveEmptyPostParameters(currentOptions.postParameGenerate)) {
					// กรณีมี post parameter แต่เป็นค่าว่างตัวใดตัวหนึ่งให้ clear autocomplete
					this._createEmptyAutocomplete(currentOptions);
					
				} else {
					// กรณี post parameter มีค่าครบทุกตัว ให้โหลดข้อมูลได้
					if (currentOptions.url != undefined) {
						if (currentOptions.selectitemAjax) {
							drawingSelectItemFromJson(undefined, currentOptions);
							currentOptions.selectitemElement.removeAttr('disabled').removeClass('readonly');
						
						} else {
							// กรณีเป็น autocomplete ปรกติ จะวาด autocomplete จากข้อมูลที่ ค้นหาได้ทันที
							this._loadData(currentOptions);
						}
					}
					this._createDataAutocomplete(currentOptions);
				}
			}
		},
		
		_getLastSeq: function() {
			var lastSeq = 0;
			for (var key in this.options) {
				lastSeq = key;
			}
			return lastSeq;
		},
		
		_createDataAutocomplete: function(options) {
			options.selectitemElement.css('display', options.displayModelInput);
			options.selectitemCode.css('display', options.displayModelInput);
			options.selectitemText.css('display', options.displayModelInput);
			
			var that = this;
			var selected = options.selectitemElement.children( ":selected" );
			var value = selected.text() ? selected.text() : "";
			var msgMinChar = "";
			
			if (selectitemxConfig.msgMinChar != undefined) {
				msgMinChar = selectitemxConfig.msgMinChar.replace("xxx", options.minLength);
			}
			
			options.selectitemInput = jQuery( "<input type='text'>" )
			.appendTo( options.selectitemWrapper )
			.val( value )
			.attr( "id", options.selectitemId + "_input_id" )
			.attr( "name", options.selectitemId + "_input_name" )
			.attr( "title", "" )
			.attr("class", options.cssClass)
			.attr("style", options.cssStyle)
			.attr( "seq", options.seq )
			.addClass( "custom-combobox-input" )
			.tooltip({tooltipClass: "ui-state-highlight"})
			.attr( "validName", options.validName )
			.click(function(){
				this.select();
			})
			.keyup(function(){
				if (options.minLength == 0) {
					return true;
				}
				if (options.selectitemInput.val().length < options.minLength) {
					options.selectitemTimeoutToolTipId = that._delay(function() {
						/*jQuery(this).attr( "title", msgMinChar).tooltip( "open" );*/
					}, 100);
				} else {
					options.selectitemInput.tooltip( "close" ).attr( "title", "" );
					clearTimeout(options.selectitemTimeoutToolTipId);
				}
			})
			.blur(function() {
				if (options.selectitemInput.val() == '') {
					//clear ค่า เพื่อเข้าเงื่อนไข ตอน autocomplete change
					options.selectitemCode.val('');
					options.selectitemText.val('');
					options.selectitemElement.val('');
				}
			})
			.change(function(){
				// console.info('text change');
				if (options.selectitemInput.val() == '') {
					options.selectitemCode.val('');
					options.selectitemText.val('');
					that._manageChangeAutoComFunction(options.seq);
					
					if (options.afterChangeFunction != undefined) {
						options.afterChangeFunction();
					}
				}
				options.selectitemInput.tooltip( "close" ).attr( "title", "" );
				clearTimeout(options.selectitemTimeoutToolTipId);
			})
			.autocomplete({
				delay: options.delay,
				minLength: options.minLength,
				source: jQuery.proxy( this, "_source", options.seq ),
				select: function( event, ui ) {
					// console.info('select:---');
					if ((options.selectitemCode.val() == ui.item.option.value)
							&& (options.selectitemText.val() == ui.item.label)) {
						return;
					}
					options.selectitemCode.val(ui.item.option.value);
					options.selectitemText.val(ui.item.label);
					
					// ----------------------------------------------
					that._manageChangeAutoComFunction(parseInt(options.seq, 10));
					
					if (options.afterChangeFunction != undefined) {
						options.afterChangeFunction();
					}
				},
				close : function() {
//					console.info('close:---');
					 
					if (this.value != '') {
						if(this.value.length < options.minLength){
							options.selectitemCode.val('');
							options.selectitemText.val('');
						}
						return;
					}
					
					if(this.value == ''){
						if(options.minLength == 0){
							options.selectitemCode.val('');
							options.selectitemText.val('');
						}
					}
					
					if ((options.selectitemCode.val() == options.selectitemElement.val())
							&& (options.selectitemText.val() == options.selectitemInput.val())) {
						return;
					}
					
					that._manageChangeAutoComFunction(parseInt(options.seq, 10));
					
					if (options.afterChangeFunction != undefined) {
						options.afterChangeFunction();
					}
				}
			});
			
			if (options.selectitemElement.attr("disabled") == "disabled") {
				options.selectitemInput.removeAttr('disabled').attr('disabled', 'disabled').removeClass('readonly').addClass('readonly');
			}
			
			if (options.disabled) {
				options.selectitemInput.removeAttr('disabled').attr('disabled', 'disabled').removeClass('readonly').addClass('readonly');
			}
			
			if (options.requireInput) {
				options.selectitemInput.removeClass('requireInput').addClass('requireInput');
			}
			
			if (options.clearForm) {
				options.selectitemInput.removeClass('clearform').addClass('clearform');
			}
			
			this._on( options.selectitemInput, {
				autocompleteselect: function( event, ui ) {
					ui.item.option.selected = true;
					this._trigger( "select", event, {
						item: ui.item.option
					});
					options.selectitemElement.trigger("change");
				},
				autocompletechange: function( event, ui ) {
					// console.info('autocompletechange');
					if ((options.selectitemCode.val() == options.selectitemElement.val())
							&& (options.selectitemText.val() == options.selectitemInput.val())) {
						return;
					} else {
						// ถ้าเป็น autocomplete ajax จะทำการ load ค่าใน autocomplete ใหม่ผ่าน function _loadOption
						if ((options.selectitemType == "A") && (options.selectitemAjax == true)) {
							options.postParameGenerate = generatePostParameters(options.postParamsId);
							options.postParameGenerate.term = options.selectitemInput.val();
							this._loadOption(options);
						}
						
						options.selectitemInput.data( "ui-autocomplete" ).term = options.selectitemInput.val();
						return this._removeIfInvalid(event, ui, options.seq);
						
					}
				}
			});
			
			// FIXME เปิดใช้งานได้ในอนาคตหากเปลี่ยนวิธีการวาดโดยให้ dropdown และ autocomplete ใช้ class เดียวกันในการวาด
			// กำหนดความกว้างของ autocomplete ให้เท่ากับ selectitem เนื่องจากต้องการให้มีขนาดเท่ากับ dropdownlist ที่จำเป็นต้องปรับขนาดให้เท่ากับ selectitem ที่ซ่อนไว้
//			var attrWidth = jQuery("#" + options.selectitemId).width();
//			options.selectitemInput.css("width", attrWidth);
			
			// กรณีมีค่าอยู่ model ให้ set ลง autocomplete ด้วย
			options.selectitemElement.val(options.selectitemCode.val());
			options.selectitemInput.val(options.selectitemText.val());
			options.selectitemInput.data( "ui-autocomplete" ).term = options.selectitemText.val();
		},
		
		_loadOption: function(options) {
			jQuery.ajax({
				type: options.submitType,
				url: options.url,
				data: options.postParameGenerate,
				async: options.async,
				success: function(jsonData) {
					jQuery("#" + options.selectitemId).find('option').remove();	
					if ((options.defaultKey != undefined) && (options.defaultValue != undefined)) {
						jQuery("#" + options.selectitemId).append('<option value="' + options.defaultKey + '">' + options.defaultValue + '</option>');
					}
					
					if (jsonData != undefined) {
						for (var index = 0; index < jsonData.length; index++) {
							jQuery("#" + options.selectitemId).append('<option value="' + jsonData[index].key + '">' + jsonData[index].value + '</option>');
						}
					}
				}
			});
		},
		
		_loadData: function(options) {
			jQuery.ajax({
				type: options.submitType,
				url: options.url,
				data: options.postParameGenerate,
				async: options.async,
				success: function(jsonData) {
					drawingSelectItemFromJson(jsonData, options);
				}
			});
		}, 
		
		_createShowAllButton: function(currentOptions) {
			var that = this;
			var input = currentOptions.selectitemInput, wasOpen = false;
			var defaultMinLength = currentOptions.minLength;
			
			if (currentOptions.arrowDis) {
				currentOptions.selectitemButton = jQuery( "<a>" )
				.attr( "tabIndex", -1 )
				.attr( "seq", currentOptions.seq )
				.tooltip()
				.appendTo( currentOptions.selectitemWrapper )
				.button({
					icons: {primary: "ui-icon-triangle-1-s"},
					text: false
				})
				.removeClass( "ui-corner-all" )
				.addClass( "custom-combobox-toggle ui-corner-right" )
				.mousedown(function() {
					wasOpen = input.autocomplete( "widget" ).is( ":visible" );
				});
			} else {
				currentOptions.selectitemButton = jQuery( "<a>" )
				.attr( "tabIndex", -1 )
				.attr( "title", selectitemxConfig.msgShowAll ) //"Show All Items"
				.attr( "seq", currentOptions.seq )
				.tooltip()
				.appendTo( currentOptions.selectitemWrapper )
				.button({
					icons: {primary: "ui-icon-triangle-1-s"},
					text: false
				})
				.removeClass( "ui-corner-all" )
				.addClass( "custom-combobox-toggle ui-corner-right" )
				.mousedown(function() {
					wasOpen = input.autocomplete( "widget" ).is( ":visible" );
				});
			}

			if (currentOptions.selectitemElement.attr("disabled") == "disabled") {
				currentOptions.selectitemButton.addClass('ui-state-disabled').attr( "disabled", "disabled");
			}
			
			if (currentOptions.disabled) {
				currentOptions.selectitemButton.removeAttr('disabled').attr('disabled', 'disabled').removeClass('ui-state-disabled').addClass('ui-state-disabled');
			}
			
			//ARROW DIS ให้ปิดปุ่มลูกศรขวาด้วย
			if (currentOptions.arrowDis) {
				currentOptions.selectitemButton.removeAttr('disabled').attr('disabled', 'disabled').removeClass('ui-state-disabled').addClass('ui-state-disabled');
			}
			
			if(!currentOptions.arrowDis){
				currentOptions.selectitemButton.click(function() {
					if (currentOptions.selectitemButton.attr("disabled") != "disabled") {
						input.focus();
	
						// Close if already visible
						if ( wasOpen ) {
							return;
						}
	
						if (input.val() == '') {
							input.data( "ui-autocomplete" ).term = '';
						}
	
						var term = "";
						if (input.data( "ui-autocomplete" ).term != undefined) {
							term = input.data( "ui-autocomplete" ).term;
						}
	
						input.autocomplete( "option","minLength", 0 ); //set minLength: 0 to search all items.
						input.autocomplete( "search", term ); // Pass empty string as value to search for, displaying all results
						input.autocomplete( "option","minLength", defaultMinLength ); //set minLength to default
					}
				});
			}
		},
		
		_createEmptyAutocomplete: function(options) {
			// ใช้กรณีไม่มีข้อมูล หรือ post parameter มีค่าเป็นว่าง
			drawingSelectItemFromJson(undefined, options);
			this._createDataAutocomplete(options);
		},
		
		_source: function( seq, request, response ) {
			if (this.options[seq].selectitemAjax == true) {
				this._sourceForAutocompleteAjax(seq, request, response);
			} else {
				this._sourceForAutocomplete(seq,request,response);
			}
		},
		
		_sourceForAutocompleteAjax: function( seq, request, response ) {
			var config = this.options[seq];
			var select = config.selectitemElement;
			var input = config.selectitemInput;

			if (config.delay > 0) {
				if (input.hasClass( "working" )) {
					return;
				}
				input.autocomplete( "widget" ).hide();
				// alert('url("' + selectitemxConfig.contextPath + '/images/loading.gif") no-repeat right center;');
				// input.addClass('working');
				input.css('background', 'url("' + selectitemxConfig.contextPath + '/images/loading.gif") no-repeat right center');
				// alert(1);
				input.attr('readonly', 'readonly');
			}

			var dataObject = config.postParameGenerate;
			dataObject.term = request.term;
			dataObject.limit = config.limit;
			if (config.postParamsRealtimeId != undefined) {
				var dataObjectRealtime = generatePostParameters(config.postParamsRealtimeId);
				for (var key in dataObjectRealtime) {
					dataObject[key] = dataObjectRealtime[key];
				}
			} 
			
			jQuery.ajax({
				error: function( jqXHR, textStatus, errorThrown) {
					//console.info( jqXHR, textStatus, errorThrown);
					//console.info( textStatus);
					//console.info( errorThrown);
					//submitPage(selectitemxConfig.contextPath + "/jsp/security/initMainpage.action");
					submitPage(window.location.pathname);
				},
		        url: config.url,
		        type: config.submitType,
		        data: dataObject,
		        dataType: "json",
		        async : config.async,
		        global: false,
		        success: function(data) {
		        	select.find('option')
		            .remove()
		            .end()
		            .append(jQuery('<option></option>').val(config.headerKey).html(config.headerText));

		        	jQuery.each(data, function(val, item) {
		        		select.append(jQuery('<option></option>').val(item.key).html(item.value));	
		        	});

		        	response( select.children( "option" ).map(function() {
		        		var text = jQuery( this ).text();
		        			return {
		        				label: text,
		        				value: text,
		        				option: this
		        			};
		        		})
		        	);

		        	if (config.delay > 0) {
		        		input.removeClass('working');
		        		input.css('background', '');
		        		input.removeAttr('readonly', '');
		        	}
		        }
		   });
		},
		
		_sourceForAutocomplete: function( seq, request, response ) {
			var matcher;
			if (this.options[seq].searchAllIndex && request.term != "") {
				//ค้นหาจากตำแหน่งใดก็ได้
                matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i" );
				
			} else {
				//ค้นหาเฉพาะคำที่ขึ้นต้นด้วย
				matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex(request.term), "i" );
			}
			
			response( this.options[seq].selectitemElement.children( "option" ).map(function() {
				var text = $( this ).text();
				if ( !request.term || matcher.test(text)  ) //commenting for show a empty value
					return {
						label: text,
						value: text,
						option: this
					};
				})
			);
		},
		
		_manageChangeAutoComFunction: function(newSeq) {
			var that = this;
			var options = this.options;
			var lastSeq = this._getLastSeq();
			var thisSeq = 0;
			if (newSeq != undefined) {
				thisSeq = parseInt(newSeq, 10);
			}
			
			var currentOptions = options[thisSeq];
			for (; thisSeq <= lastSeq; thisSeq++) {
				var nextOptions = that.options[thisSeq + 1];
				if (nextOptions != undefined) {
					if (nextOptions.selectitemType == "A") {
						nextOptions.selectitemCode.val("");
						nextOptions.selectitemText.val("");
						that._createAutocomplete(nextOptions);
						that._createShowAllButton(nextOptions);
						that._manageChangeAutoComFunction(nextOptions.seq);
						
					} else if (nextOptions.selectitemType == "D") {
						jQuery("#" + nextOptions.inputModelId).val("");
						that._createDropdownlist(nextOptions);
						that._manageChangeDropdownFunction(nextOptions.seq);
						
					}
				}
			}
		},
		
		_removeIfInvalid: function( event, ui, seq ) {
			var value = this.options[seq].selectitemInput.val(), valueLowerCase = value.toLowerCase(), valid = false;
			var key = "";
			var text = "";
			this.options[seq].selectitemElement.children( "option" ).each(function() {
				if ( jQuery( this ).text().toLowerCase() === valueLowerCase ) {
					this.selected = valid = true;
					text = jQuery( this ).text();
					key = jQuery( this ).val();
					return false;
				}
			});
			
			// Found a match, nothing to do
			if ( valid ) {
				this.options[seq].selectitemCode.val(key);
				this.options[seq].selectitemText.val(text);
				this._manageChangeAutoComFunction(seq);
				return;
			}

			// Remove invalid value
			this.options[seq].selectitemInput
				.val( "" )
				.attr( "title", value + " " + selectitemxConfig.msgNotMatch)
				.tooltip( "open" );

			this.options[seq].selectitemElement.val( "" );
			this._delay(function() {this.options[seq].selectitemInput.tooltip( "close" ).attr( "title", "" );}, 2000);
			this.options[seq].selectitemInput.data( "ui-autocomplete" ).term = "";

			text = "";
			key = "";

			this.options[seq].selectitemCode.val(key);
			this.options[seq].selectitemText.val(text);
			this._manageChangeAutoComFunction(seq);
		},
		
		autocompleteValue: function(selectSeq, newValue) {
			selectSeq = parseInt(selectSeq, 10);
			if (newValue == undefined) {
				return {codeValue: this.options[selectSeq].autocompleteCode.val(), textValue: this.options[selectSeq].autocompleteText.val()};
//				return {codeValue: this.options[selectSeq].selectitemElement.val(), textValue: this.options[selectSeq].selectitemInput.val()};
			
			} else {
				if ((newValue.codeValue == "") || (newValue.textValue == "")) {
					var lastSeq = this._getLastSeq();
					
					this.options[selectSeq].selectitemInput.val( "" );
					this.options[selectSeq].selectitemInput.data( "ui-autocomplete" ).term = "";
					this.options[selectSeq].selectitemElement.val("");
					this.options[selectSeq].selectitemCode.val("");
					this.options[selectSeq].selectitemText.val("");
					
					this.options[selectSeq].postParameGenerate = generatePostParameters(this.options[selectSeq].postParamsId);
					if (this.options[selectSeq].postParamsId == undefined) {
						this.options[selectSeq].selectitemElement.removeAttr('disabled').removeClass('readonly'); 
						this.options[selectSeq].selectitemInput.removeAttr('disabled').removeClass('readonly');
						this.options[selectSeq].selectitemButton.removeAttr('disabled').removeClass('ui-state-disabled');
					} else {
						if (haveEmptyPostParameters(this.options[selectSeq].postParameGenerate)) {
							this.options[selectSeq].selectitemElement.attr('disabled', 'disabled').addClass('readonly'); 
							this.options[selectSeq].selectitemInput.attr('disabled', 'disabled').addClass('readonly');
							this.options[selectSeq].selectitemButton.attr('disabled', 'disabled').addClass('ui-state-disabled');
						} else {
							this.options[selectSeq].selectitemElement.removeAttr('disabled').removeClass('readonly'); 
							this.options[selectSeq].selectitemInput.removeAttr('disabled').removeClass('readonly');
							this.options[selectSeq].selectitemButton.removeAttr('disabled').removeClass('ui-state-disabled');
						}
					}

					selectSeq++;
					for (; selectSeq <= lastSeq; selectSeq++) {
						this.options[selectSeq].selectitemInput.val( "" );
						this.options[selectSeq].selectitemInput.data( "ui-autocomplete" ).term = "";
						this.options[selectSeq].selectitemElement.val("");
						this.options[selectSeq].selectitemCode.val("");
						this.options[selectSeq].selectitemText.val("");
						this._createEmptyAutocomplete(this.options[selectSeq]);
						this._createShowAllButton(this.options[selectSeq]);
					}
				} else {
					if (this.options[selectSeq].selectitemAjax == true) {
						this.options[selectSeq].postParameGenerate = generatePostParameters(this.options[selectSeq].postParamsId);
						this.options[selectSeq].postParameGenerate.term = newValue.textValue
						this._loadData(this.options[selectSeq]);
					}
					
					this.options[selectSeq].selectitemElement.removeAttr('disabled').removeClass('readonly'); 
					this.options[selectSeq].selectitemInput.removeAttr('disabled').removeClass('readonly');
					this.options[selectSeq].selectitemButton.removeAttr('disabled').removeClass('ui-state-disabled');
					this._createDataAutocomplete(this.options[selectSeq]);
					this._createShowAllButton(this.options[selectSeq]);
					
					if (jQuery("#" + this.options[selectSeq].selectitemId + " option[value='" + newValue.codeValue + "']").text() != newValue.textValue) {
						this.selectitemValue(selectSeq, {codeValue: "", textValue: ""});
						
					} else {
						this.options[selectSeq].selectitemInput.val( newValue.textValue );
						this.options[selectSeq].selectitemInput.data( "ui-autocomplete" ).term = newValue.textValue;
						this.options[selectSeq].selectitemElement.val( newValue.codeValue);
						this.options[selectSeq].selectitemCode.val(newValue.codeValue);
						this.options[selectSeq].selectitemText.val(newValue.textValue);
						this._manageChangeAutoComFunction(selectSeq);
						
					}
				}
			}
		},
		
		toDisabled: function(selectSeq) {
			var firstSeq = 0;
			var thisSeq = this._getLastSeq();
			if (selectSeq != undefined) {
				thisSeq = parseInt(selectSeq, 10);
			}
			
			for (; thisSeq >= firstSeq; thisSeq--) {
				this.options[thisSeq].selectitemButton.attr('disabled', true).addClass("ui-state-disabled").attr('tabindex', -1);
				this.options[thisSeq].selectitemElement.attr('disabled', true).addClass("readonly").attr('tabindex', -1);
				this.options[thisSeq].selectitemInput.attr('disabled', true).addClass("readonly").attr('tabindex', -1);
			}
		},

		toEnabled: function(selectSeq) {
			var lastSeq = this._getLastSeq();
			var thisSeq = 0;
			if (selectSeq != undefined) {
				thisSeq = parseInt(selectSeq, 10);
			}
			
			for (; thisSeq <= lastSeq; thisSeq++) {
				// กรณีเป็น autocomplete
				if (this.options[thisSeq].selectitemType == "A") {
					if (this.options[thisSeq].selectitemAjax == true) {
						// กรณีเป็น autocomplete ajax
						this.options[thisSeq].postParameGenerate = generatePostParameters(this.options[thisSeq].postParamsId);
						if (this.options[thisSeq].postParamsId == undefined) {
							this.options[thisSeq].selectitemButton.removeAttr('disabled').removeClass("ui-state-disabled").removeAttr('tabindex');
							this.options[thisSeq].selectitemElement.removeAttr('disabled').removeClass("readonly").removeAttr('tabindex');
							this.options[thisSeq].selectitemInput.removeAttr('disabled').removeClass("readonly").removeAttr('tabindex');
							
						} else {
							if (haveEmptyPostParameters(this.options[thisSeq].postParameGenerate)) {

							} else {
								this.options[thisSeq].selectitemButton.removeAttr('disabled').removeClass("ui-state-disabled").removeAttr('tabindex');
								this.options[thisSeq].selectitemElement.removeAttr('disabled').removeClass("readonly").removeAttr('tabindex');
								this.options[thisSeq].selectitemInput.removeAttr('disabled').removeClass("readonly").removeAttr('tabindex');
							}
						}
						
					} else {
						// กรณีเป็น autocomplete ธรรมดา
						if ((jQuery("#" + this.options[thisSeq].selectitemId + " option").length == 1) && jQuery("#" + this.options[thisSeq].selectitemId + " option")[0].value == "") {
							
						} else {
							this.options[thisSeq].selectitemButton.removeAttr('disabled').removeClass("ui-state-disabled").removeAttr('tabindex');
							this.options[thisSeq].selectitemElement.removeAttr('disabled').removeClass("readonly").removeAttr('tabindex');
							this.options[thisSeq].selectitemInput.removeAttr('disabled').removeClass("readonly").removeAttr('tabindex');
						}
					}
				} else if (this.options[thisSeq].selectitemType == "D") {
					
				}
			}
		},
		
		_createDropdownlist: function(currentOptions) {
			// สร้าง post parameter ก่อน เพื่อไว้ตรวจสอบว่าจะ call ไปยัง servlet หรือไม่
			currentOptions.postParameGenerate = generatePostParameters(currentOptions.postParamsId);
			
			jQuery("#" + currentOptions.inputModelId).css('display', currentOptions.displayModelInput);
			
			// hide text field
			jQuery("input[code-of='" + currentOptions.selectitemId + "']").hide();
			jQuery("input[text-of='" + currentOptions.selectitemId + "']").hide();
			
			if (currentOptions.postParamsId == undefined) {
				// กรณีไม่มี post parameter ให้ load ปกติ			
				this._createDataDropdownlist(currentOptions);
				
			} else {
				if (haveEmptyPostParameters(currentOptions.postParameGenerate)) {
					// กรณีมี post parameter แต่เป็นค่าว่างตัวใดตัวหนึ่งให้ clear dropdownlist	
					this._createEmptyDropdownlist(currentOptions);
					
				} else {
					// กรณี post parameter มีค่าครบทุกตัว ให้โหลดข้อมูลได้
					this._createDataDropdownlist(currentOptions);
				}
			}
		},
		
		_createDataDropdownlist: function(options) {
			// ใช้กรณีมีข้อมูล โดยรับข้อมูลจาก servlet
			if (options.url == undefined) {
				jQuery("#" + options.selectitemId).val(jQuery("#" + options.inputModelId).val());
				jQuery("#" + options.selectitemId).attr("seq", options.seq).attr("name", options.inputModelName);
				
			} else {
				jQuery.ajax({
					url: options.url,
				    data: options.postParameGenerate,
				    type: options.submitType,
				    async: options.async,
				    success: function(jsonData) {
				    	drawingSelectItemFromJson(jsonData, options);
				    }
				});
			}
		},
		
		_createEmptyDropdownlist: function(options) {
			// ใช้กรณีไม่มีข้อมูล หรือ post parameter มีค่าเป็นว่าง (ใช้ในกรณี dropdown ตัวแรกมาจาก dropdownlist ไม่ได้มาจาก url)
			drawingSelectItemFromJson(undefined, options);
		},
		
		_manageChangeDropdownFunction: function(newSeq) {
			var that = this;
			var options = this.options;
			var lastSeq = this._getLastSeq();
			var seq = 0;
			if (newSeq != undefined) {
				seq = parseInt(newSeq, 10);
			}
			
			var currentOptions = options[seq];
			jQuery("#" + currentOptions.selectitemId).selectmenu({
				change: function(event, ui) {
					var thisSeq = parseInt(jQuery(this).attr('seq'), 10);
					var thisOption = options[thisSeq];
					
					var statusBeforeChange = undefined;
					if (thisOption.beforeChangeFunction != undefined) {
						statusBeforeChange = thisOption.beforeChangeFunction();
						if ((statusBeforeChange != undefined) && (statusBeforeChange == false)) {
							jQuery("#" + thisOption.selectitemId).val(jQuery("#" + thisOption.inputModelId).val());
							return false;
						}
					}
					
					// กำหนดค่าจาก model ให้ selectitem
					if (jQuery("#" + thisOption.inputModelId).val() != jQuery(this).val()) {
						jQuery("input[code-of='" + thisOption.selectitemId + "']").val(jQuery(this).val());
						jQuery("input[text-of='" + thisOption.selectitemId + "']").val(jQuery(this).find(":selected").text());
					}
					
					for (; thisSeq <= lastSeq; thisSeq++) {
						var nextOptions = options[thisSeq + 1];
						if (nextOptions != undefined) {
							if (nextOptions.selectitemType == "A") {
								nextOptions.selectitemCode.val("");
								nextOptions.selectitemText.val("");
								that._createAutocomplete(nextOptions);		
								that._createShowAllButton(nextOptions);
								that._manageChangeAutoComFunction(nextOptions.seq);
								
							} else if (nextOptions.selectitemType == "D") {
								jQuery("input[code-of='" + nextOptions.selectitemId + "']").val("");
								jQuery("input[text-of='" + nextOptions.selectitemId + "']").val("");
								that._createDropdownlist(nextOptions);
								that._manageChangeDropdownFunction(nextOptions.seq);
								
							}
						}
					}
					
					if (thisOption.afterChangeFunction != undefined) {
						thisOption.afterChangeFunction();
					}
				}
			});
			
			// Set css & style to selectmenu
			jQuery("#" + currentOptions.selectitemId + "-button").each(function(){
				// ใส่ class
				if (currentOptions.cssClass != undefined) {
					jQuery(this).addClass(currentOptions.cssClass);
				}
				
				// ใส่ style
				if (currentOptions.cssStyle != undefined) {
					jQuery(this).attr('style', currentOptions.cssStyle);
				}
				
				// กำหนดขนาด selectmenu โดยไม่จำเป็นต้องเรียก initialWidget()
				attrWidth = jQuery("#" + currentOptions.selectitemId).width();
				jQuery("#" + currentOptions.selectitemId + "-button").css("width", attrWidth);
			});
			
			jQuery("#" + currentOptions.selectitemId).selectmenu("refresh");
		}
		
	});

}) ( jQuery );

/**
 * วาด selectitem สำหรับสร้าง  autocomplete หรือ dropdown
 * @param jsonData
 * @param options
 */
function drawingSelectItemFromJson(jsonData, options) {
	var selectitemId = options.selectitemId;
	var html = "";
	if (options.selectitemType == "A") {
		// 1. เคลียร์ Autocomplete ออกก่อน
		jQuery("#" + selectitemId).remove();
		jQuery(options.selectitemWrapper).remove();
		jQuery(options.selectitemElement).remove();
		jQuery(options.selectitemInput).remove();
		
		// 2.1. สร้าง tag select
		html = "<select id='" + selectitemId + "' seq='" + options.seq + "'>";
		
	} else if (options.selectitemType == "D") {
		// 1. เคลียร์ dropdownlist ออกก่อน
		jQuery("#" + selectitemId).remove();
		
		// 2.1. สร้าง tag select
		html = "<select id='" + selectitemId + "' seq='" + options.seq + "' name='" + options.inputModelName + "'>";
		if ((options.defaultKey != undefined) && (options.defaultValue != undefined)) {
			html += "<option value='" + options.defaultKey + "'>" + options.defaultValue + "</option>";
		} else {
			html += "<option value=''></option>";
		}
	}
	
	// 2.2. วนสร้าง tag option
	if (jsonData != undefined) {
		for (var index = 0; index < jsonData.length; index++) {
			html += "<option value='" + jsonData[index].key + "'>" + jsonData[index].value + "</option>";			
		}
	}
	
	// 2.3. ปิด tag
	html += "</select>";

	// นำ selectitemId วาดลงใน tag แม่
	jQuery("#" + options.inputModelId).parent().append(html);
	
	var ele = jQuery("#" + selectitemId);	
	
	// ใส่ class
	if (options.cssClass != undefined) {
		jQuery(ele).addClass(options.cssClass);
	}
	
	// ใส่ style
	if (options.cssStyle != undefined) {
		jQuery(ele).attr('style', options.cssStyle);
	}
	
	// disabled เมื่อไม่มีข้อมูล
	if (options.postParamsRealtimeId != undefined) {
		
	} else if ((jsonData == undefined) || (jsonData.length == 0)) {
		jQuery(ele).removeAttr('disabled').removeClass('readonly').attr('disabled', 'disabled').addClass('readonly');
	}
	
	// disabled เมื่อถูกกำหนดให้เป็น disable
	if (options.disabled) {
		jQuery(ele).removeAttr('disabled').removeClass('readonly').attr('disabled', 'disabled').addClass('readonly');
	}
	
	// กรณีเป็น require input ให้ใส่ class requireInput ด้วย
	if (options.requireInput) {
		jQuery(ele).removeClass('requireInput').addClass('requireInput');
	}
	
	// กรณีเป็น require fill ให้ใส่ class requireFill ด้วย
	if (options.requireFill) {
		jQuery(ele).removeClass('requireFill').addClass('requireFill');
	}
	
	// กรณีมี validName ให้ใส่ attr validName ด้วย
	if (options.validName != undefined) {
		jQuery(ele).attr("validName", options.validName);
	}
	
	// กรณีมีค่าอยู่ใน model แล้วให้ set ลง selectitemId ด้วย
	jQuery(ele).val(jQuery("#" + options.inputModelId).val());
	
	if (options.selectitemType == "A") {
		options.selectitemElement = jQuery("#" + options.selectitemId);
		options.selectitemWrapper = jQuery( "<span>" ).addClass( "custom-combobox" ).insertAfter( options.selectitemElement );
	}
}

/**
 * ดึงข้อมูล json จาก Drop down list
 */
function getJsonDataFromDropdownlist(modelKeyId) {
	var dropdownlist = jQuery("#" + modelKeyId + "_DROPDOWNLIST");
	var optionElArray = dropdownlist.prop('options');
	var optionEl = optionElArray[dropdownlist.prop('selectedIndex')];
	if (jQuery(optionEl).attr('json') == undefined) {
		return "";
	} else {
		if (msieversion() >= 8) {
			return JSON.parse(jQuery(optionEl).attr('json'));
		} else {
			return "";			
		}
	}
}