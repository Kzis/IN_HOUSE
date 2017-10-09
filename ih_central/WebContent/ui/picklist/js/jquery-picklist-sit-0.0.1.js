//default value of combobox use in javascript.jsp
//Start create combobox widgets
(function( $ ) {
	$.widget( "custom.sitPicklist", {
		version: "1.0.0",
		options: [{
			// Sorting
			sortItems:                  true,
            sortAttribute:              "label",
            	
        	// Name of custom value attribute for list items
            listItemValueAttribute:     "value",
            
        	sortitem :     				"sortitem",  // attributeName ที่เก็บค่าไว้ ผูกไว้กับ Select 
            sortCustom :     			false,  //check ว่าจะใช้ sortitem ในการ sort ข้อมูลหรือไม่ Deafault คือ false
    		selectLimit:				"",
    		msg:						"",
    			
			// Control labels
            addAllLabel:                "&gt;&gt;",
            addLabel:                   "&gt;",
            removeAllLabel:             "&lt;&lt;",
            removeLabel:                "&lt;"
		}],

		_create: function() {
			
			var divPicklist = this.element;
			
			divPicklist.attr("type", "ui-picklist");
			
			var selectPicklist = jQuery("select", divPicklist);
			
			// กรณีเป็นหน้าแก้ไข และ แสดง ให้เอาค่าที่เคยเลือกไว้ มาอยู่ด้านขวา
			var selectedValue = jQuery(".picklist-value",divPicklist).val().split(","); 
			for(var i=0;i<selectedValue.length;i++){
				jQuery("option", selectPicklist).each(function() {
				    if (selectedValue[i] == jQuery(this).val()) {
				    	jQuery(this).attr('selected','selected');
				    } 
				});
			}
			
			// จะให้ sort หรือไม๋? 
			if(jQuery(divPicklist).is('[data-sort-items]')){ 
				if(jQuery(divPicklist).attr("data-sort-items") == "false"){
					this.options.sortItems = false;
				}
			}
			
			// default จะ sort ตาม label
			if(jQuery(divPicklist).is('[data-sort-attribute]')){ 
				this.options.sortAttribute = jQuery(divPicklist).attr("data-sort-attribute");
			}
			
			// custom sort หรือไม่?
			if(jQuery(divPicklist).is('[data-custom-sort]')){ 
				if(jQuery(divPicklist).attr("data-custom-sort") == "true"){
					this.options.sortCustom = true;
				}
			}
			
			if(jQuery(divPicklist).is('[data-select-limit]')){ 
				this.options.selectLimit = jQuery(divPicklist).attr("data-select-limit");
			}
			
			if(jQuery(divPicklist).is('[data-limit-msg]')){ 
				this.options.msg = jQuery(divPicklist).attr("data-limit-msg");
			}
			
			
			// Control labels
			if(jQuery(divPicklist).is('[data-btn-add-all]')){ 
				this.options.addAllLabel = jQuery(divPicklist).attr("data-btn-add-all");
			}
			
			if(jQuery(divPicklist).is('[data-btn-add]')){ 
				this.options.addLabel = jQuery(divPicklist).attr("data-btn-add");
			}
			
			if(jQuery(divPicklist).is('[data-btn-remove-all]')){ 
				this.options.removeAllLabel = jQuery(divPicklist).attr("data-btn-remove-all");
			}
			
			if(jQuery(divPicklist).is('[data-btn-remove]')){ 
				this.options.removeLabel = jQuery(divPicklist).attr("data-btn-remove");
			}
			
			selectPicklist.pickList({
				name: selectPicklist.attr("name"),
				id: selectPicklist.attr("id"),
		        "onChange": function(event, obj){
		        	
		        	// Highlight รายการที่เพิ่งทำการเลือกล่าสุด
		        	if(obj.items != undefined && obj.items.length > 0){
		        		
		        		jQuery(obj.items[0]).closest("div.ui-picklist").find("li.ui-state-highlight").removeClass("ui-state-highlight");
		        		
			        	for(var index=0;index<obj.items.length;index++){
			        		jQuery(obj.items[index]).addClass("ui-state-highlight");
			        	}
		        	}
		        	
		        	var targetList = jQuery(".pickList_targetList > li", divPicklist);
		        	
		    		var strValue = "";
		    		var strLabel = "";

		    		for(var i = 0; i < targetList.length; i++){
		    			
		    			strValue += "," + targetList[i].getAttribute("value");
		    			strLabel += "," + targetList[i].getAttribute("label");
		    		}
		    		
		    		jQuery(".picklist-value",divPicklist).val(strValue.substring(1, strValue.length));
		    		jQuery(".picklist-label",divPicklist).val(strLabel.substring(1, strLabel.length));      
		    		
		        }
				,sortItems: this.options.sortItems
				,sortAttribute: this.options.sortAttribute
				,sortitem: this.options.sortitem
				,selectLimit: this.options.selectLimit
				,msg: this.options.msg
				,addAllLabel:  this.options.addAllLabel
	            ,addLabel: this.options.addLabel
	            ,removeAllLabel:  this.options.removeAllLabel
	            ,removeLabel: this.options.removeLabel
			});
			
			jQuery("li.pickList_listItem[value='']").remove();
		},
		
	});

}) ( jQuery );
