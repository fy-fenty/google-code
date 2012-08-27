FNS.modifyDispatct = function(record){
	var sheetId=record.get('dlId');
//	alert(sheetId+"  "+record.get('status'));
	var specidDlistStore=new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url: path+'/findSpecidDispatch.action',
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
						readOnly:true,
						disabled:true
	            	},{
		            	xtype:'datefield',
		            	format:'Y-m-d',
		            	fieldLabel: '创建时间',
						name: 'createTime',
						disabled:true,
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
						disabled:true,
						readOnly:true
	            	}]
				}]
			},{
		          xtype:'textarea',   
		          fieldLabel:'费用说明', 
		          name:'eventExplain',
//		          id:'memo',
		          labelSeparator:':',   
		          labelWidth:60,
		          width:393,
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
	var detailStore=new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url: path+'/findDetial.action',
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
	var gcom=FNS.cbo();
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
		                width    : 116, 
		                sortable : true,
		                dataIndex: 'itemId',
		                renderer:FNS.renderItem,
		                editor:gcom
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
									    url: path+'/deleteDetail.action',
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
	                       	//alert(rec.modified);
	                        if(rec.modified==null){
	                        	Ext.Msg.alert("提示","请选择一项修改");	
	                        	return;
	                        }
	                        var myMask = new Ext.LoadMask(view.getRow(rowIndex), {msg:"正在更新数据...",
                			 removeMask : true});
	                        myMask.show();
	                        Ext.Ajax.request({
	                        	url: path+'/updateDetail.action',
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
		 
	var com=FNS.cbo({name:'mycombo',listWidth:170});//combobox
	
	var mo=new Ext.form.NumberField({width:70,name:'money'});
	var cos=new Ext.form.TextField({width:100,xtype:'textfield'});
					
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
				items:[com,'-','金额:',mo,'-','费用说明:',cos,{
					text:'增加明细',
					handler:function(){
						var cboitem=com.getValue();
						var money=mo.getValue();
						var cost=cos.getValue();
//						alert(money+"  "+cost);
						if(cboitem==""||money==""){
							Ext.Msg.alert("提示",'请选择或填写');	
							return;
						}
						Ext.Ajax.request({
	                        	url: path+'/addDetail.action',
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
	if(record.get('status')!=4&&record.get('status')!=null){
			grid.getTopToolbar().disabled=true;
			grid.disabled=true;
	}
	detailStore.load({params:{'sheetId':sheetId}});
	
	var resStore=new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url: path+'/findDisResult.action',
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
		                renderer:FNS.formatDate,
		                dataIndex: 'checkTime'
		            },{
		            	header   : '审批状态', 
		                width    : 60,
		                sortable : true, 
		                renderer:FNS.renderStatus,
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
		
	if(Ext.get(sheetId)!=null){
		return;
	}
	var	s = FNS.createWin(Ext.apply({
			id:sheetId,
			width: 530,
			height:570,
			title:'修改报销单',
			renderTo:Ext.getCmp('rpId').el,
			items:[myform,grid,rsgrid],
			layout: {
				type:'vbox',
				padding:'5',
				align:'stretch'
			}, 
			defaults:{margins:'0 0 5 0'},
			buttons:[{
					text:'提交',
					id:'commit',
					handler:function(){
	            	 Ext.Ajax.request({
							url: path+'/commitDispatch.action',
							success: function(rs){
									var l=Ext.util.JSON.decode(rs.responseText);
									if(l['success']==true){	
										 Ext.Msg.alert("提示",l['msg']);
										 resStore.reload();
									}else{
										Ext.Msg.alert("提示",l['msg']);
										   }
								},
							method :'POST',
							params: {'dispatchvo.eventExplain':myform.getForm().findField("eventExplain").getValue(),'dispatchvo.dlId':sheetId}
						});	
					}
				},{
	            text: '更新',
	            id:'update',
	            handler:function(){
	            	var eventCost=myform.getForm().findField("eventExplain").getValue();
	            	if(specidDlistStore.getAt(0).get('eventExplain')==eventCost){
	            		Ext.Msg.alert("提示","报销单无更新");
	            		return;
	            	}
	            	 Ext.Ajax.request({
							url:path+'/updateDlist.action',
							success: function(rs){
									var l=Ext.util.JSON.decode(rs.responseText);
									if(l['success']==true){	
										 Ext.Msg.alert("提示",l['msg']);	
										 s.close();
									}else{
										Ext.Msg.alert("提示",l['msg']);
										   }
								},
							method :'POST',
							params: {'dispatchvo.eventExplain':eventCost,'dispatchvo.dlId':sheetId}
					});
	//            	alert(Ext.get('memo').getValue()+"  "+sheetId);
	            }
	        },{
	            text: '取消',
	            handler : function() {
	            	s.close();
	            }   
	        }]
		},arguments[0]));
		
		s.show();
		if(record.get('status')!=4&&record.get('status')!=null){
			Ext.getCmp('commit').disable();
			Ext.getCmp('update').disable();
			myform.getForm().findField("eventExplain").disable();
		}
		s.on('beforeclose',function(){
	});
	
}
