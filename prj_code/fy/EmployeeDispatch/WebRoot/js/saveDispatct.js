FNS.saveDispatct=function(){
	var addDetailStore=new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url: path+'/findDetial.action',
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

	var editor = new Ext.ux.grid.RowEditor({
			    saveText : '保存',
			    cancelText : '取消',
			    commitChangesText : '请先保存或取消',
			    errorText : '提示',
			    listeners: {
                       afteredit : function(roweditor, changes, record, rowIndex){
    						record.commit();
    					},
    					canceledit:function(roweditor, changes, record, rowIndex){
    						if(record!=null)
    							record.reject();
    					}
			    }
		});
	var com=FNS.cbo({listWidth:150});
	var cm = new Ext.grid.ColumnModel({
		 	width:500,
			 defaults: {
		            sortable: true	
			 },
		        columns:[{
		        		id:'itemId',
		                header   : '明细选项', 
		                width    : 270, 
		                sortable : true, 
		                dataIndex: 'itemId',
		                renderer:FNS.renderItem,
		                editor:com
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
		                width    : 200, 
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
		width:380,
		labelWidth:60,
		labelAlign: 'top',
		frame:true,
		bodyStyle:'padding:5px 0px 5px 5px',
		items:[{
		         xtype:'textarea',   
		         fieldLabel:'事件说明', 
		         name:'even',
		         id:'evend',  
		         labelSeparator:':',   
		         labelWidth:60,
		         width:330
        	}]	
	});
	
/*	var panel1=new Ext.Panel({
		width:400,
		height:500,
		frame:true,
		layout: {
			type:'vbox',
			padding:'5',
			align:'stretch'
		}, 
		defaults:{margins:'0 0 5 0'},
		items:[dListForm,grid]
		
	});*/
	
	function commitOrSave(url){		
		var ev=dListForm.getForm().findField('even').getValue()
//		alert(ev);
            var data="";
            	if(addDetailStore.getCount()!=0){
	            	data="[";
	            	for (var i = 0; i < addDetailStore.getCount(); i++) {
	            		var record=addDetailStore.getAt(i);
	            		var itemId=record.get('itemId');
	            		var money=record.get('money');
	            		var costExplain=record.get('costExplain');
	            		if(i!=0){data += ",";}
						 data += "{itemId:'"+itemId+"'";
						 data += ",money:'"+money+"'";
						 data += ",costExplain:'"+costExplain+"'}";
	            		}
	            	  data += "]";
            	}
//            	 alert('data:'+data+"  "+url);
            	Ext.Ajax.request({
						url: url,
						success: function(rs){
								var l=Ext.util.JSON.decode(rs.responseText);
								if(l['success']==true){	
									 Ext.Msg.alert("提示",l['msg']);	
								}else{
									Ext.Msg.alert("提示",l['msg']);
									   }
							},
						method :'POST',
						params: {'dispatchvo.eventExplain':ev,'data':data}
				});
			}
	
	var m = FNS.createWin(Ext.apply({
			width: 380,
			height:420,
			layout: {
				type:'vbox',
				padding:'5',
				align:'stretch'
			}, 
			defaults:{margins:'0 0 5 0'},
			items:[dListForm,grid],
			buttons:[{
	            text: '保存',
	            handler:function(){
	            	commitOrSave(path+'/addAll.action')
	            }
        	},{
        		text:'提交',
				handler:function(){
					if(addDetailStore.getCount()==0){
						Ext.Msg.alert("提示","请选择增加一条明细");
						return;
					}
					commitOrSave(path+'/commitAddAllDispatch.action')
				}
			},{
            text: '取消',
            handler : function() {   
            	m.close();
            }   
        }]
		},arguments[0]));
	
		m.show();
	
		m.on('beforeclose',function(){
		});
}