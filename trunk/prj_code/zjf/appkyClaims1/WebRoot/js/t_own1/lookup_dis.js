FNS.empLook = function(){
	var store1 = new Ext.data.JsonStore({
    	autoDestroy: true,
    	url: 'appkyClaims1/select.action',
        storeId: 'myStore1',
        root:"result", 
        totalProperty:"totalCount",//配置分页
        fields: [
        	{name: 'DL_ID', type: 'float'},
        	{name: 'E_SN', type: 'string'}, 
        	{name: 'MONEY', type: 'string'},
        	{name: 'EVENT_EXPLAIN',type:'string'},
        	{name: 'CREATE_TIME', mapping: 'CREATE_TIME', type: 'date', dateFormat:'Y-m-d'},
        	{name: 'STATUS', type: 'string'}
        	]
     });
     
	

     function formatDate(value){
     	return value ? value.dateFormat('Y-m-d') : ''; 
	 };
	 
	 var grid = new Ext.grid.GridPanel({
	    	//renderTo:"x",
	    	store: store1,
	    	height:400,
	    	width:610,
	    	loadMask: true,//加载画面
    	 	columns :
    	 			[{
						name : 'DL_ID',
						mapping : 'DL_ID',
						header:"DL_ID",
						type : 'float'
					},{
						name : 'E_SN',
						mapping : 'E_SN',
						header:"E_SN",
						type : 'string'
					},{
						name : 'CREATE_TIME',
						type : 'date',
						mapping : 'CREATE_TIME',
						header:"CREATE_TIME",
						dateFormat : 'Y-m-d'
					}, {
						name : 'EVENT_EXPLAIN',
						mapping : 'EVENT_EXPLAIN',
						header:"EVENT_EXPLAIN",
						type : 'string'
					},{
						name : 'MONEY',
						mapping : 'MONEY',
						header:"MONEY",
						type : 'string'
					},{
						name : 'STATUS',
						mapping : 'STATUS',
						header:"STATUS",
						type : 'string'
				}], 
				bbar: new Ext.PagingToolbar({
	           		pageSize: 3,
	            	store: store1,
	            	width:610,
	            	height:30,
	            	displayInfo: true,
	            	plugins: new Ext.ux.ProgressBarPager()
	        	})
	    });
	    
	    
	 var rightMenu = new Ext.menu.Menu( {  
			id : 'rightClickCont',  
			items : [{  
		    	id:'rMenu1',  
		    	text:'修 改', 
		    	//icon:'../img/icon/modify.png',  
		   		handler:modify  
		    }, {  
		    	id:'rMenu2',  
		    	text:'删 除',
		    	handler:del  
		    }]  
	 });    
	    
	//增加右击事件
	grid.addListener('rowcontextmenu', rightClickFn); 

	function rightClickFn(gridPanel, rowIndex, e) {  
    	e.preventDefault();  
    	rightMenu.showAt(e.getXY());  
    	//gridpanel默认右击是不会选择当前行的，所以必须添加这句代码  
    	grid.getSelectionModel().selectRow(rowIndex);  
	};
	

	
	//修改报销单
	function modify() { 
		var record = grid.getSelectionModel().getSelected();
		var DL_ID = record.data['DL_ID'];
		var fp=new Ext.FormPanel({
			labelWidth: 100, // label settings here cascade unless overridden
			//url:'save-form.php',
			frame:true,
			bodyStyle:'padding:5px 5px 0',
			width: 500,
			defaults: {width: 230,readOnly:true,disabled:true},
			defaultType: 'textfield',
	        items: [{
	                fieldLabel: 'DL_ID',
	                id:'DL_ID',
	                name: 'DL_ID'
	                
	            },{
	            	fieldLabel: 'E_SN',
	                id:'E_SN',
	                name: 'E_SN'
	            },{
	                fieldLabel: 'CREATE_TIME',
	                id:'CREATE_TIME',
	                format: 'Y-m-d',
	                name: 'CREATE_TIME'
	            },{
	            	fieldLabel: 'MONEY',
	                id:'MONEY',
	                name: 'MONEY'
	            },{
	            	fieldLabel: 'STATUS',
	                id:'STATUS',
	                name: 'STATUS'
	            },{
	            	fieldLabel: 'EVENT_EXPLAIN',
	                id:'EVENT_EXPLAIN',
	                name: 'EVENT_EXPLAIN',
	                xtype: 'textarea',
	                disabled:false,
	                readOnly:false
	            }
        	],
        	buttons: [{
		            text: 'Save',
		            handler:function(){
		            	var EVENT_EXPLAIN=fp.getForm().findField("EVENT_EXPLAIN").getValue();
		            	Ext.Ajax.request({
							url: path+'/upClaim.action',
							method: 'GET',
							params: {'DL_ID':DL_ID,'EVENT_EXPLAIN':EVENT_EXPLAIN },
							callback: function(op,success,resp){
								if(success=true){
									var js=Ext.util.JSON.decode(resp.responseText);
									if(js['success']==true){
										alert('更新成功..');
									}
									else{
										alert('更新失败,报销单已在审批...');
									}
								}
							}
						});
		            }
		        },{
		            text: 'Cancel' 
		    }]
        });
        
        
        
     	var store2 = new Ext.data.JsonStore({
    		autoDestroy: true,
    		url: 'appkyClaims1/seldetail.action',
        	storeId: 'myStore2',
        	root:"result", 
        	baseParams:{DL_ID:DL_ID},
        	totalProperty:"totalCount",//配置分页
        	fields: [
        		{name: 'DS_ID', type: 'float'},
        		{name: 'SHEET_ID', type: 'string'}, 
        		{name: 'MONEY', type: 'string'},
        		{name: 'COST_EXPLAIN',type:'string'},
        		{name: 'ITEM_ID',type:'float'}
        		]
     	});
     	store2.load({params:{start:0, limit:3}});
		
        var grid1 = new Ext.grid.GridPanel({
	    	//renderTo:"x",
        	title:'报销单明细',
	    	store: store2,
	    	height:180,
	    	width:500,
	    	loadMask: true,//加载画面
    	 	columns :
    	 			[{
						name : 'DS_ID',
						mapping : 'DS_ID',
						header:"DS_ID",
						type : 'float'
					},{
						name : 'SHEET_ID',
						mapping : 'SHEET_ID',
						header:"SHEET_ID",
						type : 'string'
					},{
						name : 'MONEY',
						mapping : 'MONEY',
						header:"MONEY"
					}, {
						name : 'COST_EXPLAIN',
						mapping : 'COST_EXPLAIN',
						header:"COST_EXPLAIN",
						type : 'string'
					},{
						name : 'ITEM_ID',
						mapping : 'ITEM_ID',
						header:"ITEM_ID",
						type : 'float'
					}], 
				bbar: new Ext.PagingToolbar({
	           		pageSize: 3,
	            	store: store2,
	            	width:610,
	            	height:30,
	            	displayInfo: true,
	            	plugins: new Ext.ux.ProgressBarPager()
	        	})
	    });
	    
        
   	
    var e= FNS.createWin(Ext.apply({
    		title:'修改报销单',
			width: 518,
			height:600,
			modal:true,
			renderTo: Ext.getCmp('rpId').el,
			items :[fp,grid1]
		}),arguments[0]);
		
		fp.getForm().loadRecord(record);//通过record 给formpanel
		e.show();
	};
		
		
	function del(){
		var record = grid.getSelectionModel().getSelected();
		
		var DL_ID = record.data['DL_ID'];
		Ext.Ajax.request({
    		url: path+'/del.action',
    		method: 'GET',
    		params: {'DL_ID':DL_ID },
    		callback: function(op,success,resp){
				if(success=true){
					var js=Ext.util.JSON.decode(resp.responseText);
					if(js['success']==true){
						alert('删除成功');
						store1.remove(record);//删除行
					}
					else{
						alert('删除失败');
					}
				}
			}
    	});
	 }
	

	    
	emp = FNS.createWin(Ext.apply({
			width: 618,
			height:435,
			resizable:false,
			maximizable: false,//固定大小
			items :grid
	},arguments[0]));
	
	emp.show();	
	
	store1.load({params:{start:0, limit:3}});
	
}


	//申请报销单
	FNS.sendClaims=function(){
		var send=FNS.createWin(Ext.apply({
			width: 400,
			maximizable: false
		},arguments[0]));
		
		send.show();	
	}

	
	
	
	