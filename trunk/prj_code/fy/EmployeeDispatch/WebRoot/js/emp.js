var sheetId=null;
function renderStatus(value){
	        if(value==null){
	        	return "草稿"
	        }else if(value==1){
	        	return "待审批";
	        }else if(value==2){return "已审批";}else if(value==3){return '已终止';}
	        else if(value==4){return "已打回";}else if(value==5){return "待付款";}else if(value==6)
	        {return "已付款";}
	    }
		function formatDate(value){
	        return value ? value.dateFormat('Y-m-d H:i:s') : '';
	    }

function findAllDispatchListByESn(){
		var proxy=new Ext.data.HttpProxy({
			url: '/EmpCheck/showEmpDList.action',
			method:'POST'
		});
		
		var myreader=new Ext.data.JsonReader({
			idProperty:'DL_ID',
			root:'result',
			totalProperty:'totalCount',
			fields:[
				{name: 'dlId', mapping: 'DL_ID'},
				{name: 'esn', mapping: 'E_SN'},
	        	{name: 'dmoney', mapping: 'DMONEY'},
	          	{name: 'status', mapping: 'CHECK_STATUS'},
	          	{name: 'createTime', mapping: 'CREATE_TIME',type:'date',dateFormat:'Y-m-d H:i:s'}
			]
		});
		var store = new Ext.data.Store({
			proxy : proxy,  
		    storeId: 'myStore',
		    reader:myreader
		});
	
//		var chbox=new Ext.grid.CheckboxSelectionModel({
			var sr=new Ext.grid.RowSelectionModel({
					singleSelect:true,
					listeners: {
                        rowselect: function(sm, row, rec) {
//                        	alert(rec.get('dlId'));
                        	sheetId=rec.get('dlId');
                        }
                    }
			}
		);
		
		var cm = new Ext.grid.ColumnModel({
		 	width:480,
			 defaults: {
		            sortable: true	
			 },
		        columns:[
		            {
		                id :'dlId',
		                header   : '报销单号', 
		                width    : 70, 
		                sortable : true,
		                dataIndex: 'dlId'
		            },
		            {
		                header   : '创建人', 
		                width    : 100, 
		                sortable : true, 
		                dataIndex: 'esn'
		            },
		            {
		                header   : '金额', 
		                width    : 100, 
		                sortable : true, 
		                dataIndex: 'dmoney'
		            },        {
		                header   : '状态', 
		                width    : 60, 
		                sortable : true, 
		                renderer:renderStatus,
		                dataIndex: 'status'
		            },        {
		                header   : '创建时间', 
		                width    : 150, 
		                sortable : true, 
		                renderer:formatDate,
		                dataIndex: 'createTime'
		            },{
	            	 header   : '操作', 
	                xtype: 'actioncolumn',
	                width: 50,
	                items: [{
	                	icon   : 'images/delete.gif', 
	                    tooltip: 'Sell stock',
	                    handler: function(grid, rowIndex, colIndex) {
	                    	var rec = store.getAt(rowIndex);
	                    	 var rsm = grid.getSelectionModel(); 
	                         Ext.Msg.confirm("警告", "确定要删除数据？", function (btn) {  
	                        	 if(btn == "yes"){
	                        	grid.rsLoadMask = new Ext.LoadMask(grid.bwrap,{msg: "请等待..."});
	                    	 	grid.rsLoadMask.show();
	                        		 Ext.Ajax.request({
									    url: '/EmpCheck/deleteDispatchList.action',
									    success: function(rs){
									    	var l=Ext.util.JSON.decode(rs.responseText);
									    	if(l['success']==true){	
									    		grid.rsLoadMask.hide();
									    		Ext.Msg.alert("提示",l['msg']);	
									    		store.remove(rec);
									    		store.reload();
									    	}else{
									    		Ext.Msg.alert("提示",l['msg']);	
									    		grid.rsLoadMask.hide();
									    	}
									   },
									   method :'POST',
									   params: {'dispatchvo.dlId':rec.get('dlId')}
									 });            		 
	                        	 }
	                         });
	                    }
	                }]}
		 		]
		 	 });
		 var grid = new Ext.grid.GridPanel({
		 	layout:'fit',
			loadMask:true,
			store:store,
			frame:true,
			width:550,
			height:300,
			cm:cm,
			sm:sr,
	//		autoExpandColumn: 'dlId',
			bbar:new Ext.PagingToolbar({
				 pageSize: 2,
		         store: store,
		         displayInfo: true,
		         displayMsg: 'Displaying topics {0} - {1} of {2}',
		         emptyMsg: "No topics to display"
			}),
			tbar:new Ext.Toolbar({
				items:[' ',{
	        			text:'更新',
	        			handler:function(btn){
	        				var gs=grid.getSelectionModel();
	        				if(gs.hasSelection()){
	        					var rs=gs.getSelections();
	        					for (var index = 0; index < rs.length; index++) {
	        						alert(rs[index].get('dlId'));
	        						sheetId=rs[index].get('dlId');
//	        						msg(rs[index]);
	        					}
	        				}else{
	        					Ext.Msg.alert('提示','请选择一条记录！');
    							 return;
	        				}
	        			}
	        		}]
			})
		});
		store.load({params:{start:0, limit:2}});
		return grid; 
/*		var win =new Ext.Window({
		    title:'员工报销单',
		    modal: true,
		    frame:true,
		    closable:true,
		    layout:'fit',
		    width:510,
		    height:300,
	        items:grid
	      });
	      win.show(this);
	});*/
}

function modifyDispatct(){
	var specidDlistStore=new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url: '/EmpCheck/findSpecidDispatch.action',
				method:'POST'
			}),
		reader:new Ext.data.JsonReader({
			fields:[
				{name: 'dlId', mapping: 'dlId'},
				{name: 'ESn', mapping: 'ESn'},
				{name: 'eventExplain', mapping: 'eventExplain'},
				{name: 'createTime', mapping: 'createTime',type:'date',dateFormat:'Y-m-d H:i:s'}
			]
		})
	});
	
	var myform=new Ext.FormPanel({
		width:450,
		height:140,
		labelWidth:60,
		labelAlign: 'right',
		frame:true,
		bodyStyle:'padding:5px 5px 5px 0',
		items:[{
			layout:'column',
			items:[{
				columnWidth: 0.50,
				layout: 'form',
				defaults: {
	            	width: 150,
	            	xtype: 'textfield'	
	            },items:[{
		            	fieldLabel: '报销单号',
						name: 'dlId',
						readOnly:true
	            	},{
		            	xtype:'datefield',
		            	format:'Y-m-d',
		            	fieldLabel: '创建时间',
						name: 'createTime',
						readOnly:true
		            }]
				},{
					columnWidth: 0.50,
					layout: 'form',
					defaults:{
	            		width: 150,
	            		xtype: 'textfield'
					},
	            	items:[{
		            	fieldLabel: '创建人',
						name: 'ESn',
						readOnly:true
	            	}]
				}]
			},{
		          xtype:'textarea',   
		          fieldLabel:'费用说明', 
		          name:'eventExplain',
		          id:'memo',  
		          labelSeparator:':',   
		          labelWidth:60,
		          width:350,
		          height:50
        	}]	
	});
	if(sheetId==null){Ext.Msg.alert('提示','请选择一条记录！');return;}else{
		specidDlistStore.load({
				params:{'sheetId':sheetId},
				callback : function(r,ops,success){
					myform.getForm().loadRecord(specidDlistStore.getAt(0));
				}
			});
	}
	var cpro=new Ext.data.HttpProxy({
			url: '/EmpCheck/showCombo.action',
			method:'POST'
	});
		
	var cread=new Ext.data.JsonReader({
			idProperty:'DL_ID',
			root:'result',
			totalProperty:'totalCount',
			fields:[
				{name: 'deId', mapping: 'DE_ID'},
				{name: 'itemName', mapping: 'ITEM_NAME'}
			]
		});
	var cstrore = new Ext.data.Store({
		proxy : cpro,  
		reader:cread
	});
	
	var detailStore=new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url: '/EmpCheck/findDetial.action',
				method:'POST'
			}),
		reader:new Ext.data.JsonReader({
			fields:[
				{name: 'dsId', mapping: 'dsId'},
				{name: 'sheetId', mapping: 'sheetId'},
				{name: 'itemId', mapping: 'itemId'},
				{name: 'costExplain', mapping: 'costExplain'},
				{name: 'money', mapping: 'money'}
			]
		})
	});
	var cbx=new Ext.form.ComboBox({
		id:'myCombo',
		minChars:1,
		blankText : '请选择',
		typeAhead: true,
		hiddenName:'deId',
		listWidth: 90, 
		pageSize:5,
		loadingText: 'Searching...',
		valueField:'deId',
		typeAhead:true,
		mode:'remote',	
		forceSelection:true,
		emptyText:'this is null',
		selectOnFocus:true,
		triggerAction: 'all',
		displayField:'itemName', 
		store:cstrore,
		listeners:{
         select:function(combo,record,index){
//              alert(combo.value);
         } 
     } 
	});
	
	var cm = new Ext.grid.ColumnModel({
		 	width:400,
			defaults: {sortable: true},
		    columns:[
		         	{
		                id :'dsId',
		                header   : '明细编号', 
		                width    : 70, 
		                sortable : true,
		                dataIndex: 'dsId'
		            },	{
		                header   : '报销单编号', 
		                width    : 70, 
		                sortable : true,
		                dataIndex: 'sheetId'
		            },
		            {
		                header   : '明细选项', 
		                width    : 100, 
		                sortable : true,
		                dataIndex: 'itemId',
		                editor:cbx
		            },
		             {
		                header   : '费用说明', 
		                width    : 100, 
		                sortable : true, 
		                dataIndex: 'costExplain',
		                editor: new Ext.form.TextField({
	                    	allowBlank: false
	                	})
		            },
		            {
		                header   : '明细金额', 
		                width    : 60, 
		                sortable : true, 
		                dataIndex: 'money',
		                editor: new Ext.form.TextField({
	                    	allowBlank: false
	                	})
		            },{
	            	 header   : '操作', 
	                xtype: 'actioncolumn',
	                width: 70,
	                items: [{
	                    icon   : 'images/delete.gif', 
	                    tooltip: 'Sell stock',
	                    handler: function(grid, rowIndex, colIndex) {
	                    	var rec = detailStore.getAt(rowIndex);
	                    	 var rsm = grid.getSelectionModel(); 
	                         Ext.Msg.confirm("警告", "确定要删除数据？", function (btn) {  
	                        	 if(btn == "yes"){
	                        	grid.rsLoadMask = new Ext.LoadMask(grid.bwrap,{msg: "请等待..."});
	                    	 	grid.rsLoadMask.show();
	                        		 Ext.Ajax.request({
									    url: '/EmpCheck/deleteDetail.action',
									    success: function(rs){
									    	var l=Ext.util.JSON.decode(rs.responseText);
									    	if(l['success']==true){	
									    		grid.rsLoadMask.hide();
									    		Ext.Msg.alert("提示",l['msg']);	
									    		detailStore.remove(rec);
									    		detailStore.reload();
									    	}else{
									    		Ext.Msg.alert("提示",l['msg']);	
									    		grid.rsLoadMask.hide();
									    	}
									   },
									   method :'POST',
									   params: {'dvo.dsId':rec.get('dsId'),'dvo.sheetId':rec.get('sheetId')}
									 });            		 
	                        	 }
	                         });
	                    }
	                }, {
	                	 icon   : 'images/accept.png', 
	                   	 handler: function(grid, rowIndex, colIndex) {
	                   	 	var rec = detailStore.getAt(rowIndex);
	                       	var view = grid.getView();  
	                        var myMask = new Ext.LoadMask(view.getRow(rowIndex), {msg:"正在更新数据...",
                			 removeMask : true});
	                        myMask.show();
	                        Ext.Ajax.request({
	                        	url: '/EmpCheck/updateDetail.action',
	                        	method:'GET',
	                        	params: {'dvo.dsId':rec.get('dsId'),'dvo.sheetId':rec.get('sheetId'),'dvo.money':rec.get('money'),
	                        	'dvo.itemId':rec.get('itemId'),'dvo.costExplain':rec.get('costExplain')},
	                        	callback: function(op,success,resp){
								myMask.hide();
								var l=Ext.util.JSON.decode(resp.responseText);
								if(l['success']==true){
									Ext.Msg.alert("提示",l['msg']);	
									rec.commit();
								}else{
									Ext.Msg.alert("提示",l['msg']);	
									rec.reject();}	
								}
	                        });
	                   	 }
	                }]
	            }]
		 });
	var grid = new Ext.grid.EditorGridPanel({
	        store: detailStore,
	        loadMask: true,
	        cm: cm,
	        sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
	        width:500,
	        height: 200,
	        frame: true,
	        tbar:new Ext.Toolbar({
				displayInfo: true,
				items:[cbx,'-',{xtype:'label',text:'金额:'},{
					xtype:'textfield',
					width:50,
					id: 'money'
				},'-',{xtype:'label',text:'费用说明:'},{
					width:100,
					xtype:'textfield',
					id: 'costExplain'
				},{
					text:'增加明细',
					handler:function(){
						var cboitem=grid.getTopToolbar().get('myCombo').getValue();
						var money=grid.getTopToolbar().get('money').getValue();
						var cost=grid.getTopToolbar().get('costExplain').getValue();
//						alert(grid.getTopToolbar().get('myCombo').getValue());
						 Ext.Ajax.request({
	                        	url: '/EmpCheck/addDetail.action',
	                        	method:'GET',
	                        	params:{'itemId':cboitem,'cost':cost,'money':money,'sheetId':sheetId},
	                        	callback: function(op,success,resp){
								var l=Ext.util.JSON.decode(resp.responseText);
								if(l['success']==true){
										Ext.Msg.alert("提示",l['msg']);	
										detailStore.reload();}		
								else{Ext.Msg.alert("提示",l['msg']);	}	
									}
	                        });
					}
				}]
			})
	});	 
	detailStore.load({params:{'sheetId':sheetId}});
	
	var resStore=new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url: '/EmpCheck/findDisResult.action',
				method:'POST'
			}),
		reader:new Ext.data.JsonReader({
			fields:[
				{name: 'sheetId', mapping: 'sheetId'},
				{name: 'checkSn', mapping: 'checkSn'},
				{name: 'checkNext', mapping: 'checkNext'},
				{name: 'checkComment', mapping: 'checkComment'},
				{name: 'checkTime', mapping: 'checkTime',type:'date',dateFormat:'Y-m-d H:i:s'},			
				{name: 'checkStatus', mapping: 'checkStatus'}
			]
		})
	});
	var cm = new Ext.grid.ColumnModel({
		 	width:400,
			defaults: {sortable: true},
		    columns:[{
		                header   : '报销单号', 
		                width    : 60, 
		                sortable : true,
		                dataIndex: 'sheetId'
		            },
		            {
		                header   : '当前审核人', 
		                width    : 70, 
		                sortable : true,
		                dataIndex: 'checkSn'
		            },
		             {
		                header   : '下个审核人', 
		                width    : 70, 
		                sortable : true, 
		                dataIndex: 'checkNext'
		            },
		            {
		                header   : '审批意见', 
		                width    : 100, 
		                sortable : true, 
		                dataIndex: 'checkComment'
		            },{
		            	header   : '审批时间', 
		                width    : 120, 
		                sortable : true,
		                renderer:formatDate,
		                dataIndex: 'checkTime'
		            },{
		            	header   : '审批状态', 
		                width    : 60,
		                sortable : true, 
		                renderer:renderStatus,
		                dataIndex: 'checkStatus'
		           }]
	});
		var rsgrid = new Ext.grid.GridPanel({
	        store: resStore,
	        loadMask: true,
	        cm: cm,
	        sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
	        width:496,
	        height: 150,
	        frame: true
	});	
	resStore.load({params:{'sheetId':sheetId}});
	
/*	var wins=new Ext.Window({
		title:'修改报销单',
		width:520,
		height:560,
		closable:true,
		modal:true,
		frame:true,
		layout: {
			type:'vbox',
			padding:'5',
			align:'stretch'
		}, 
		defaults:{margins:'0 0 5 0'},
		items:[myform,grid,rsgrid],
		buttons:[{
            text: '更新',
            formBind:true,
            handler:function(){}
        },{
            text: '重置',
            handler : function() {   
            	myform.getForm().reset();
            }   
        }]
	});
	wins.show(this);*/
	var pansd=new Ext.Panel({
//		renderTo:'d',
		title:'修改报销单',
		width:520,
		height:560,
		frame:true,
//		layout:'border',
		layout: {
			type:'vbox',
			padding:'5',
			align:'stretch'
		}, 
		defaults:{margins:'0 0 5 0'},
		items:[myform,grid,rsgrid],
		buttons:[{text:'提交',
				id:'commit',
//				disabled:true,
				handler:function(){
            	 Ext.Ajax.request({
						url: '/EmpCheck/commitDispatch.action',
						success: function(rs){
								var l=Ext.util.JSON.decode(rs.responseText);
								if(l['success']==true){	
									 Ext.Msg.alert("提示",l['msg']);	
								}else{
									Ext.Msg.alert("提示",l['msg']);
									   }
							},
						method :'POST',
						params: {'dispatchvo.dlId':sheetId}
					});	
				}
			},{
            text: '更新',
            formBind:true,
            handler:function(){
            	 Ext.Ajax.request({
						url: '/EmpCheck/updateDlist.action',
						success: function(rs){
								var l=Ext.util.JSON.decode(rs.responseText);
								if(l['success']==true){	
									 Ext.Msg.alert("提示",l['msg']);	
								}else{
									Ext.Msg.alert("提示",l['msg']);
									   }
							},
						method :'POST',
						params: {'dispatchvo.eventExplain':Ext.get('memo').getValue(),'dispatchvo.dlId':sheetId}
				});
//            	alert(Ext.get('memo').getValue()+"  "+sheetId);
            }
        },{
            text: '取消',
            handler : function() {}   
        }]
	});
//	resStore.getCount()
//	Ext.getCmp('commit').disable();
	return pansd;
}


function saveDispatct(){
	var cstrore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url: '/EmpCheck/showCombo.action',
				method:'POST'
			}),  
		reader:new Ext.data.JsonReader({
				idProperty:'DL_ID',
				root:'result',
				totalProperty:'totalCount',
				fields:[
					{name: 'deId', mapping: 'DE_ID'},
					{name: 'itemName', mapping: 'ITEM_NAME'}
				]
			})
	});
	
	var addDetailStore=new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url: '/EmpCheck/findDetial.action',
				method:'POST'
			}),
		reader:new Ext.data.JsonReader({
			fields:[
				{name: 'itemId', mapping: 'itemId'},
				{name: 'costExplain', mapping: 'costExplain'},
				{name: 'money', mapping: 'money'}
			]
		})
	});
	var cbx=new Ext.form.ComboBox({
		id:'myCombo',
		minChars:1,
		blankText : '请选择',
		typeAhead: true,
		hiddenName:'deId',
		listWidth: 90, 
		pageSize:5,
		loadingText: 'Searching...',
		valueField:'deId',
		typeAhead:true,
		mode:'remote',	
		forceSelection:true,
		emptyText:'this is null',
		selectOnFocus:true,
		triggerAction: 'all',
		displayField:'itemName', 
		store:cstrore
	});
	var editor = new Ext.ux.grid.RowEditor({
			    saveText : '保存',
			    cancelText : '取消',
			    commitChangesText : '请先保存或取消',
			    errorText : '提示',
			    listeners: {
                       afteredit : function(roweditor, changes, record, rowIndex){
    						alert(11);
    					},
    					canceledit:function(roweditor, changes, record, rowIndex){
    						alert(1);
    					}
			    }
		});
	var cm = new Ext.grid.ColumnModel({
		 	width:300,
			 defaults: {
		            sortable: true	
			 },
		        columns:[{
		        		id:'itemId',
		                header   : '明细选项', 
		                width    : 150, 
		                sortable : true, 
		                dataIndex: 'itemId',
		                editor:cbx
		            },
		            {
		                header   : '金额', 
		                width    : 100, 
		                sortable : true, 
		                dataIndex: 'money',
		                editor: new Ext.form.TextField({
	                    	allowBlank: false
	                	})
		            },        {
		                header   : '费用说明', 
		                width    : 150, 
		                sortable : true, 
		                dataIndex: 'costExplain',
		                editor: new Ext.form.TextField({
	                    	allowBlank: false
	                	})
		          }]
		});
    
	var grid= new Ext.grid.EditorGridPanel({
        store: addDetailStore,
        frame:true,
        width: 300,
        height:230,
        viewConfig : {forceFit : true},
        cm:cm,
        sm:new Ext.grid.RowSelectionModel({singleSelect:false}),
        plugins : [editor],
        tbar:[{
            text: '增加明细',
            handler: function(){
            	var Plant = grid.getStore().recordType;
            	var e= new Plant({itemId:1,costExplain:'SOS',money:500});
              	editor.stopEditing();
                addDetailStore.insert(0, e);
                grid.getView().refresh();
                grid.getSelectionModel().selectRow(0);
                editor.startEditing(0);
            }
        },{
            text: '删除明细',
            handler: function(){
                 editor.stopEditing();
                var s = grid.getSelectionModel().getSelections();
                for(var i = 0, r; r = s[i]; i++){
                    addDetailStore.remove(r);
                }
            }
        }]
	});
	
	var dListForm=new Ext.FormPanel({
		width:300,
		labelWidth:60,
		labelAlign: 'top',
		frame:true,
		bodyStyle:'padding:5px 5px 5px 0',
		items:[{
		         xtype:'textarea',   
		         fieldLabel:'事件说明', 
		         name:'even',
		         id:'evend',  
		         labelSeparator:':',   
		         labelWidth:60,
		         width:350   
        	}]	
	});
	
	var panel1=new Ext.Panel({
		title:'增加报销单',
		width:300,
		height:420,
		frame:true,
		layout: {
			type:'vbox',
			padding:'5',
			align:'stretch'
		}, 
		defaults:{margins:'0 0 5 0'},
		items:[dListForm,grid],
		buttons:[{
            text: '保存',
            formBind:true,
            handler:function(){ 
            	  
            }
        },{
            text: '取消',
            handler : function() {   
            	myform.getForm().reset();
            }   
        }]
	});
	return panel1;
}


