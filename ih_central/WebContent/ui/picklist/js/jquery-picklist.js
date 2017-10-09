/**
 * jQuery PickList Widget
 *
 * Copyright (c) 2012 Jonathon Freeman <jonathon@awnry.com>
 * Distributed under the terms of the MIT License.
 *
 * http://code.google.com/p/jquery-ui-picklist/
 */
(function($)
{
    $.widget("awnry.pickList",
    {
        widgetEventPrefix: "pickList_",

        options:
        {
            // Container classes
            mainClass:                  "pickList",
            listContainerClass:         "pickList_listContainer",
            sourceListContainerClass:   "pickList_sourceListContainer",
            controlsContainerClass:     "pickList_controlsContainer",
            targetListContainerClass:   "pickList_targetListContainer",
            listClass:                  "pickList_list",
            sourceListClass:            "pickList_sourceList",
            targetListClass:            "pickList_targetList",
            clearClass:                 "pickList_clear",

            // List item classes
            listItemClass:              "pickList_listItem",
            richListItemClass:          "pickList_richListItem",
            selectedListItemClass:      "pickList_selectedListItem",

            // Control classes
            addAllClass:                "pickList_addAll",
            addClass:                   "pickList_add",
            removeAllClass:             "pickList_removeAll",
            removeClass:                "pickList_remove",

            // Control labels
            addAllLabel:                "&gt;&gt;",
            addLabel:                   "&gt;",
            removeAllLabel:             "&lt;&lt;",
            removeLabel:                "&lt;",

            // List labels
            listLabelClass:             "pickList_listLabel",
            sourceListLabel:            "",
            sourceListLabelClass:       "pickList_sourceListLabel",
            targetListLabel:            "",
            targetListLabelClass:       "pickList_targetListLabel",

            // Sorting
            sortItems:                  true,
            sortAttribute:              "label",

            // Name of custom value attribute for list items
            listItemValueAttribute:     "value",
            

            // Additional list items
            sortitem :     				"sortitem",  // attributeName ที่เก็บค่าไว้ ผูกไว้กับ Select 
            sortCustom :     			false,  //check ว่าจะใช้ sortitem ในการ sort ข้อมูลหรือไม่ Deafault คือ false
            name :     					"",
            id :     					"",
            items:                      [],
    		selectLimit:				"",
    		msg:						""
        },

        _create: function()
        {
            var self = this;

            self._buildPickList();
            self._refresh();
        },

        _buildPickList: function()
        {
            var self = this;

            self._trigger("beforeBuild");
            self.pickList = $("<div/>")
                    .hide()
                    .addClass(self.options.mainClass)
                    .insertAfter(self.element)
                    .append(self._buildSourceList())
                    .append(self._buildControls())
                    .append(self._buildTargetList())
                    .append( $("<div/>").addClass(self.options.clearClass) );

            self._populateLists();

            self.element.hide();
            self.pickList.show();

            self._trigger("afterBuild");
        },

        _buildSourceList: function()
        {
            var self = this;

            var container = $("<div/>")
                    .addClass(self.options.listContainerClass)
                    .addClass(self.options.sourceListContainerClass)
                    .css({
                        "-moz-user-select": "none",
                        "-webkit-user-select": "none",
                        "user-select": "none",
                        "-ms-user-select": "none"
                    })
                    .each(function()
                    {
                        this.onselectstart = function() { return false; };
                    });

            var label = $("<div/>")
                    .text(self.options.sourceListLabel)
                    .addClass(self.options.listLabelClass)
                    .addClass(self.options.sourceListLabelClass);

            self.sourceList = $("<ul/>")
                    .addClass(self.options.listClass)
                    .addClass(self.options.sourceListClass)
                    .delegate("li", "click", { pickList: self }, self._changeHandler);

            container
                    .append(label)
                    .append(self.sourceList);
            
          //double click
			self.sourceList.delegate(".pickList_listItem", "dblclick", {pickList: self}, function(e)
			{	
				if(!chkPickListDis(jQuery(this))){
					return false;
				}
				var self = e.data.pickList;
				self._addItems( self.sourceList.children(".ui-selected") );
			});

            return container;
        },

        _buildTargetList: function()
        {
            var self = this;

            var container = $("<div/>")
                    .addClass(self.options.listContainerClass)
                    .addClass(self.options.targetListContainerClass)
                    .css({
                        "-moz-user-select": "none",
                        "-webkit-user-select": "none",
                        "user-select": "none",
                        "-ms-user-select": "none"
                    })
                    .each(function()
                    {
                        this.onselectstart = function() { return false; };
                    });

            var label = $("<div/>")
                    .text(self.options.targetListLabel)
                    .addClass(self.options.listLabelClass)
                    .addClass(self.options.targetListLabelClass);

            self.targetList = $("<ul/>")
            		.attr("id", self.options.name)
                    .addClass(self.options.listClass)
                    .addClass(self.options.targetListClass)
                    .delegate("li", "click", { pickList: self }, self._changeHandler);

            container
                    .append(label)
                    .append(self.targetList);
            
          //double click
			self.targetList.delegate(".pickList_listItem", "dblclick", {pickList: self}, function(e)
			{
				if(!chkPickListDis(jQuery(this))){
					return false;
				}
				var self = e.data.pickList;
				self._removeItems( self.targetList.children(".ui-selected") );
			});
            
            return container;
        },

        _buildControls: function()
        {
            var self = this;

            self.controls = $("<div/>").addClass(self.options.controlsContainerClass);

            self.addAllButton = $("<button type='button'/>").click({pickList: self}, self._addAllHandler).html(self.options.addAllLabel).addClass(self.options.addAllClass).addClass("ui-state-default");
            self.addButton = $("<button type='button'/>").click({pickList: self}, self._addHandler).html(self.options.addLabel).addClass(self.options.addClass).addClass("ui-state-default");
            self.removeButton = $("<button type='button'/>").click({pickList: self}, self._removeHandler).html(self.options.removeLabel).addClass(self.options.removeClass).addClass("ui-state-default");
            self.removeAllButton = $("<button type='button'/>").click({pickList: self}, self._removeAllHandler).html(self.options.removeAllLabel).addClass(self.options.removeAllClass).addClass("ui-state-default");
           
            self.controls
                    .append(self.addAllButton)
                    .append(self.addButton)
                    .append(self.removeButton)
                    .append(self.removeAllButton);

            return self.controls;
        },
        
        _populateLists: function()
        {
            var self = this;

            self._trigger("beforePopulate");

            var sourceListItems = [];
            var targetListItems = [];
            var selectItems = self.element.children();

            selectItems.not(":selected").each(function()
            {
                sourceListItems.push( self._createDoppelganger(this) );
            });

            selectItems.filter(":selected").each(function()
            {
                targetListItems.push( self._createDoppelganger(this) );
            });

            self.sourceList.append(sourceListItems.join("\n"));
            self.targetList.append(targetListItems.join("\n"));
            self.insertItems(self.options.items);

            self._trigger("afterPopulate");
        },
        
        _addItems: function(items)
		{
			var self = this;
			self._trigger("beforeAdd");
			
			var items = self.sourceList.children(".ui-selected");

			self.targetList.append( self._removeSelections(items) );

			var itemIds = [];
			items.each(function()
			{
				itemIds.push( self._getItemValue(this) );
			});

			self.element.children().filter(function()
			{
				return $.inArray(this.value, itemIds) != -1;
			}).attr("selected", "selected");

			self._refresh();

			self._trigger("afterAdd", null, { items: items });
			self._trigger("onChange", null, { type: "add", items: items });
		},
		
		_removeItems: function(items)
		{
			var self = this;
			
			self._trigger("beforeRemove");

			var items = self.targetList.children(".ui-selected");
			self.sourceList.append( self._removeSelections(items) );

			var itemIds = [];
			items.each(function()
			{
				itemIds.push( self._getItemValue(this) );
			});

			self.element.children().filter(function()
			{
				return $.inArray(this.value, itemIds) != -1;
			}).removeAttr("selected");

			self._refresh();

			self._trigger("afterRemove", null, { items: items });
			self._trigger("onChange", null, { type: "remove", items: items });
		},

        _addAllHandler: function(e)
        {
        	var self = e.data.pickList;
        	var selectLimit = self.options.selectLimit;
        	
        	if(selectLimit == ""){
                self._trigger("beforeAddAll");

                var items = self.sourceList.children();
                self.targetList.append( self._removeSelections(items) );

                self.element.children().not(":selected").attr("selected", "selected");

                self._refresh();

                self._trigger("afterAddAll", null, { items: items });
                self._trigger("onChange", null, { type: "addAll", items: items });
        	}else{
        		alert(self.options.msg);
        	}
            
        },

        _addHandler: function(e)
        {
            var self = e.data.pickList;

            self._trigger("beforeAdd");

            var items = self.sourceList.children(".ui-selected");
            self.targetList.append( self._removeSelections(items) );

            var itemIds = [];
            items.each(function()
            {
                itemIds.push( self._getItemValue(this) );
            });

            self.element.children().filter(function()
            {
                return $.inArray(this.value, itemIds) != -1;
            }).attr("selected", "selected");

            self._refresh();

            self._trigger("afterAdd", null, { items: items });
            self._trigger("onChange", null, { type: "add", items: items });
        },

        _removeHandler: function(e)
        {
            var self = e.data.pickList;

            self._trigger("beforeRemove");

            var items = self.targetList.children(".ui-selected");
            self.sourceList.append( self._removeSelections(items) );

            var itemIds = [];
            items.each(function()
            {
                itemIds.push( self._getItemValue(this) );
            });

            self.element.children().filter(function()
            {
                return $.inArray(this.value, itemIds) != -1;
            }).removeAttr("selected");

            self._refresh();

            self._trigger("afterRemove", null, { items: items });
            self._trigger("onChange", null, { type: "remove", items: items });
        },

        _removeAllHandler: function(e)
        {
            var self = e.data.pickList;

            self._trigger("beforeRemoveAll");

            var items = self.targetList.children();
            self.sourceList.append( self._removeSelections(items) );

            self.element.children().filter(":selected").removeAttr("selected");

            self._refresh();

            self._trigger("afterRemoveAll", null, { items: items });
            self._trigger("onChange", null, { type: "removeAll", items: items });
        },

        _refresh: function()
        {
            var self = this;

            self._trigger("beforeRefresh");

            self._refreshControls();

            // Sort the selection lists.
            if(self.options.sortItems){
            	
//            	console.info(self.options.sortItems);
            	
            	if(self.options.sortCustom == true){
//            		console.info("true");
            		 self._sortItemsCus(self.sourceList, self.options);
                     self._sortItemsCus(self.targetList, self.options);
            		
            	}else{
//            		console.info("false");
            		  self._sortItems(self.sourceList, self.options);
                      self._sortItems(self.targetList, self.options);
            	}
            	
              
            }
            

            self._trigger("afterRefresh");
        },

        _refreshControls: function()
        {
            var self = this;
            
            var addBtnEnabled = (self.targetList.children().length < self.options.selectLimit);
           
            self._trigger("beforeRefreshControls");

            jQuery(self.sourceList.children()).each(function(){
            	return false;
            });
            // Enable/disable the Add All button state.
            if(self.sourceList.children().length)
            {
                self.addAllButton.removeAttr("disabled");
                self.addAllButton.removeClass("ui-state-disabled").removeClass("ui-button-disabled").removeClass("readonly");
                self.addAllButton.mouseover(function(){
					jQuery(this).addClass("ui-state-hover");
					})
					.mouseout(function() {
						jQuery(this).removeClass("ui-state-hover");
				});
                
                self.removeAllButton.removeClass("ui-state-hover");
                self.removeButton.removeClass("ui-state-hover");
                self.addButton.removeClass("ui-state-hover");
            }
            else
            {
                self.addAllButton.attr("disabled", "disabled");
                self.addAllButton.addClass("ui-state-disabled").addClass("ui-button-disabled").addClass("readonly");
            }

            // Enable/disable the Remove All button state.
            if(self.targetList.children().length)
            {
                self.removeAllButton.removeAttr("disabled");
                self.removeAllButton.removeClass("ui-state-disabled").removeClass("ui-button-disabled").removeClass("readonly");
                self.removeAllButton.mouseover(function(){
					jQuery(this).addClass("ui-state-hover");
					})
					.mouseout(function() {
						jQuery(this).removeClass("ui-state-hover");
				});
                
                self.addAllButton.removeClass("ui-state-hover");
                self.removeButton.removeClass("ui-state-hover");
                self.addButton.removeClass("ui-state-hover");
              
            }
            else
            {
                self.removeAllButton.attr("disabled", "disabled");
                self.removeAllButton.addClass("ui-state-disabled").addClass("ui-button-disabled").addClass("readonly");
            }

            // Enable/disable the Add button state.
			if(self.sourceList.children(".ui-selected").length)
			{
				self.addButton.removeAttr("disabled");
				self.addButton.removeClass("ui-state-disabled").removeClass("ui-button-disabled").removeClass("readonly");
				self.addButton.mouseover(function(){
						jQuery(this).addClass("ui-state-hover");
						})
						.mouseout(function() {
							jQuery(this).removeClass("ui-state-hover");
					});
				
				self.addAllButton.removeClass("ui-state-hover");
                self.removeButton.removeClass("ui-state-hover");
                self.removeAllButton.removeClass("ui-state-hover");
			}
			else
			{
				self.addButton.attr("disabled", "disabled");
				self.addButton.addClass("ui-state-disabled").addClass("ui-button-disabled").addClass("readonly");
			}
			
			if(addBtnEnabled){
				// Enable/disable the Add button state.
	            if(self.sourceList.children(".ui-selected").length && addBtnEnabled)
	            {
	                self.addButton.removeAttr("disabled");
	                self.addButton.removeClass("ui-state-disabled").removeClass("ui-button-disabled").removeClass("readonly");
	                self.addButton.mouseover(function(){
						jQuery(this).addClass("ui-state-hover");
						})
						.mouseout(function() {
							jQuery(this).removeClass("ui-state-hover");
					});
	                
	                self.addAllButton.removeClass("ui-state-hover");
	                self.removeButton.removeClass("ui-state-hover");
	                self.removeAllButton.removeClass("ui-state-hover");
	            }
	            else
	            {
	                self.addButton.attr("disabled", "disabled");
	                self.addButton.addClass("ui-state-disabled").addClass("ui-button-disabled").addClass("readonly");
	            }
			}

            // Enable/disable the Remove button state.
            if(self.targetList.children(".ui-selected").length)
            {
                self.removeButton.removeAttr("disabled");
                self.removeButton.removeClass("ui-state-disabled").removeClass("ui-button-disabled").removeClass("readonly");
                self.removeButton.mouseover(function(){
					jQuery(this).addClass("ui-state-hover");
					})
					.mouseout(function() {
						jQuery(this).removeClass("ui-state-hover");
				});
                
                self.addAllButton.removeClass("ui-state-hover");
                self.addButton.removeClass("ui-state-hover");
                self.removeAllButton.removeClass("ui-state-hover");
            }
            else
            {
                self.removeButton.attr("disabled", "disabled");
                self.removeButton.addClass("ui-state-disabled").addClass("ui-button-disabled").addClass("readonly");
            }

            self._trigger("afterRefreshControls");
        },

        _sortItems: function(list, options)
        {
            var items = new Array();

            list.children().each(function()
            {
                items.push( $(this) );
            });

            items.sort(function(a, b)
            {
                if(a.attr(options.sortAttribute) > b.attr(options.sortAttribute)){
                    return 1;
                }
                else if(a.attr(options.sortAttribute) == b.attr(options.sortAttribute)){
                    return 0;
                }
                else
                {
                    return -1;
                }
            	
            	
                
            });

            list.empty();
            
            for(var i = 0; i < items.length; i++)
            {
                list.append(items[i]);
            }
           
            
        },
        
        _sortItemsCus: function(list, options)
        {
            var items = new Array();

            list.children().each(function()
            {
                items.push( $(this) );
            });

            items.sort(function(a, b)
            {
            	 if(a.attr(options.sortitem) > b.attr(options.sortitem)){
                     return 1;
                 }
                 else if(a.attr(options.sortitem) == b.attr(options.sortitem)){
                     return 0;
                 }
                 else
                 {
                     return -1;
                 }
                
            });

            list.empty();

            for(var i = 0; i < items.length; i++)
            {
                list.append(items[i]);
            }
           
            
        },

        _changeHandler: function(e)
        {
            var self = e.data.pickList;
            
            var selectLimit = self.options.selectLimit;
            var isClickOnSourcePanel = this.parentNode.attributes["class"].value.indexOf("pickList_sourceList") >= 0;
            var selectedItems = self.sourceList.children(".ui-selected").length;
            var leftToSelect = self.options.selectLimit - self.targetList.children().length - selectedItems - 1;
            var canSelectMore = isClickOnSourcePanel && leftToSelect >= 0;
 
            if(e.ctrlKey)
            {
                if(self._isSelected( $(this) ))
                {
                    self._removeSelection( $(this) );
                }
                else
                {
                	if(selectLimit > 0){
                		if(canSelectMore){
                            self.lastSelectedItem = $(this);
                            self._addSelection( $(this) );
                        }
                        else if(!isClickOnSourcePanel){  //allow to select/deselect without restrictions on right panel.
                            self.lastSelectedItem = $(this);
                            self._addSelection( $(this) );
                        }
                        else if(selectLimit > self.targetList.children().length){
                        	alert(self.options.msg);
                        }
                	}
                	else{
                		self.lastSelectedItem = $(this);
    					self._addSelection( $(this) );
                	}
                }
            }
            //don't need to select with shift. For a small amount of selectale items
//          else if(e.shiftKey)
//          {
//              var current = self._getItemValue(this);
//              var last = self._getItemValue(self.lastSelectedItem);
//
//              if($(this).index() < $(self.lastSelectedItem).index())
//              {
//                  var temp = current;
//                  current = last;
//                  last = temp;
//              }
//
//              var pastStart = false;
//              var beforeEnd = true;
//
//              self._clearSelections( $(this).parent() );
//
//              $(this).parent().children().each(function()
//              {
//                  if(self._getItemValue(this) == last)
//                  {
//                      pastStart = true;
//                  }
//
//                  if(pastStart && beforeEnd)
//                  {
//                      self._addSelection( $(this) );
//                  }
//
//                  if(self._getItemValue(this) == current)
//                  {
//                      beforeEnd = false;
//                  }
//
//              });
//          }
            else
            {
            	if(selectLimit > 0){
					if(canSelectMore){
	                    self.lastSelectedItem = $(this);
	                    self._clearSelections( $(this).parent() );
	                    self._addSelection( $(this) );
                    }else if(!isClickOnSourcePanel){ //allow to select/deselect without restrictions on right panel.
	                    self.lastSelectedItem = $(this);
	                    self._clearSelections( $(this).parent() );
	                    self._addSelection( $(this) );
	                }else if(selectLimit == self.targetList.children().length){
                    	alert(self.options.msg);
                    }
				}else{
					self.lastSelectedItem = $(this);
					self._clearSelections( $(this).parent() );
					self._addSelection( $(this) );
				}
            	
			}
            self._refreshControls();
        },

        _isSelected: function(listItem)
        {
            return listItem.hasClass("ui-selected");
        },

        _addSelection: function(listItem)
        {
            var self = this;

            return listItem
                    .addClass("ui-selected")
                    .addClass("ui-state-highlight")
                    .addClass(self.options.selectedListItemClass);
        },

        _removeSelection: function(listItem)
        {
            var self = this;

            return listItem
                    .removeClass("ui-selected")
                    .removeClass("ui-state-highlight")
                    .removeClass(self.options.selectedListItemClass);
        },

        _removeSelections: function(listItems)
        {
            var self = this;

            listItems.each(function()
            {
                $(this)
                        .removeClass("ui-selected")
                        .removeClass("ui-state-highlight")
                        .removeClass(self.options.selectedListItemClass);
            });

            return listItems;
        },

        _clearSelections: function(list)
        {
            var self = this;

            list.children().each(function()
            {
                self._removeSelection( $(this) );
            });
        },

        _setOption: function(key, value)
        {
            switch(key)
            {
                case "clear":
                {
                    break;
                }
            }

            $.Widget.prototype._setOption.apply(this, arguments);
        },

        destroy: function()
        {
            var self = this;

            self._trigger("onDestroy");

            self.pickList.remove();
            self.element.show();

            $.Widget.prototype.destroy.call(self);
        },

        insert: function(item)
        {
            var self = this;

            var list = item.selected ? self.targetList : self.sourceList;
            var selectItem = self._createSelectItem(item);
            var listItem = self._createListItem(item);

            self.element.append(selectItem);
            list.append(listItem);

            self._trigger("onChange");

            self._refresh();
        },

        insertItems: function(items)
        {
            var self = this;

            var selectItems = [];
            var sourceItems = [];
            var targetItems = [];

            $(items).each(function()
            {
                var selectItem = self._createSelectItem(this);
                var listItem = self._createListItem(this);

                selectItems.push(selectItem);

                if(this.selected)
                {
                    targetItems.push(listItem);
                }
                else
                {
                    sourceItems.push(listItem);
                }
            });

            self.element.append(selectItems.join("\n"));
            self.sourceList.append(sourceItems.join("\n"));
            self.targetList.append(targetItems.join("\n"));

            self._trigger("onChange");

            self._refresh();
        },

        _createSelectItem: function(item)
        {
            var selected = item.selected ? " selected='selected'" : "";
            return "<option value='" + item.value + "'" + selected + ">" + item.label + "</option>";
        },

        _createListItem: function(item)
        {
            var self = this;
            console.info(item);
            if(item.element != undefined)
            {
                var richItemHtml = item.element.clone().wrap("<div>").parent().html();
                item.element.hide();   //div.getElementsByTagName("li")
                return "<li " + self.options.sortitem + "='" + item.sortitem +"' "  +  "' label='" + item.label  +  "' name='" + self.options.id + "'  class='" + self.options.listItemClass + " " + self.options.richListItemClass + "'>" + richItemHtml + "</li>";
            }

            return "<li " + self.options.sortitem + "='" + item.sortitem +"' "  +  self.options.listItemValueAttribute + "='" + item.value  + "' label='" + item.label +  "' name='" + self.options.id + "' class='" + self.options.listItemClass + "'>" + item.label + "</li>";
        },

        _createDoppelganger: function(item)
        {
            var self = this;
            
          //  console.info($(item).attr("sortitem"));
            
            return "<li "  + self.options.sortitem + "='" + $(item).attr("sortitem") +"' " + self.options.listItemValueAttribute + "='" + $(item).val() + "' label='" + $(item).text() +  "' sortitem='" + self.options.id + "' class='" + self.options.listItemClass + "'>" + $(item).text() + "</li>";
//            return "<li "  + self.options.listItemValueAttribute + "='" + $(item).val() + "' label='" + $(item).text() +  "' sortitem='" + self.options.id + "' class='" + self.options.listItemClass + "'>" + $(item).text() + "</li>";
        },

        _getItemValue: function(item)
        {
            var self = this;
            return $(item).attr(self.options.listItemValueAttribute);
        },
        
        _getItemSort: function(item)
        {
            var self = this;
            return $(item).attr(self.options.sortitem);
        },
        
        
        selectedAssets:function(){
            var self = this;
            var selectedItems = [];
            self.targetList.children().each(function(){
                var a = {};
                a.id = $(this).attr(self.options.listItemValueAttribute);
                a.name = $(this).attr("label");
                selectedItems.push(a);
            });
            return selectedItems;
        }
    });
}(jQuery));

//check for remove class doubleclick (picklist option)
function chkPickListDis(element){
	// if picklist options disable
	if(element.attr("disabled") == "disabled"){
		// return false : remove class doubleclick
		return false;
	}
	
	// return true : don't remove class
	return true;
}