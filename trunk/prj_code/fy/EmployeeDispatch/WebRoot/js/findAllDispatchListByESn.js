FNS.findAllDispatchListByESn = function(){
		var proxy=new Ext.data.HttpProxy({
			url: path+'/showEmpDList.action',
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
	
		var rightMenu = new Ext.ux.grid.RightMenu({  
            items : [{  
                        text : '修改',
                        recHandler:function(record,rowIndex,grid){
//                        	alert(1);
                        	FNS.modifyDispatct(record);
//                        	m.close();
                        }  
                    }, {  
                        text : '删除',  
                        recHandler : function(record, rowIndex, grid) {  
//                            alert(record.get('dlId')+"  "+rowIndex);   
	                      Ext.Msg.confirm("警告", "确定要删除数据？", function (btn) {  
	                        if(btn == "yes"){
	                        	grid.rsLoadMask = new Ext.LoadMask(grid.bwrap,{msg: "请等待..."});
	                    	 	grid.rsLoadMask.show();
	                        		 Ext.Ajax.request({
									    url: path+'/deleteDispatchList.action',
									    success: function(rs){
									    	var l=Ext.util.JSON.decode(rs.responseText);
									    	if(l['success']==true){	
									    		grid.rsLoadMask.hide();
									    		Ext.Msg.alert("提示",l['msg']);	
									    		store.remove(record);
									    		store.reload();
									    	}else{
									    		Ext.Msg.alert("提示",l['msg']);	
									    		grid.rsLoadMask.hide();
									    	}
									   },
									   method :'POST',
									   params: {'dispatchvo.dlId':record.get('dlId')}
									 });            		 
	                        	 }
	                         });
                        }  
                    }]  
        });  
		
		var sr=new Ext.grid.RowSelectionModel({
					singleSelect:true,
					listeners: {
                        rowselect: function(sm, row, rec) {
//                        	alert(rec.get('dlId'));
//                        	sheetId=rec.get('dlId');
                        }
                    }
			});
		
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
		                renderer:FNS.renderStatus,
		                dataIndex: 'status'
		            },        {
		                header   : '创建时间', 
		                width    : 150, 
		                sortable : true, 
		                renderer:FNS.formatDate,
		                dataIndex: 'createTime'
		            }]
		 	 });
		 var grid = new Ext.grid.GridPanel({
			loadMask:true,
			store:store,
			frame:true,
			width:480,
			height:300,
			cm:cm,
			sm:sr,
			plugins : [rightMenu], 
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
		
		var	m = FNS.createWin(Ext.apply({
				layout:'fit',
				width: 512,
				height:330,
				items:grid
			},arguments[0]));
		m.show();
	
		m.on('beforeclose',function(){
		});
	
}