Ext.onReady(function(){
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
	
	function formatDate(value){
        return value ? value.dateFormat('Y-m-d H:i:s') : '';
    }
	var sr=new Ext.grid.RowSelectionModel({singleSelect:true});
	
	var cm = new Ext.grid.ColumnModel({
	 	width:480,
		 defaults: {
	            sortable: true	
		 },
	        columns: [
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
	                dataIndex: 'status'
	            },        {
	                header   : '创建时间', 
	                width    : 150, 
	                sortable : true, 
	                renderer:formatDate,
	                dataIndex: 'createTime'
	            }
	 		]
	 	 });
	 var grid = new Ext.grid.GridPanel({
//		region:"west",
		loadMask:true,
		store:store,
		frame:true,
		width:500,
		height:300,
		cm:cm,
		sm:sr,
		title:'员工报销单 ',
//		autoExpandColumn: 'dlId',
		bbar:new Ext.PagingToolbar({
			 pageSize: 2,
	         store: store,
	         displayInfo: true,
	         displayMsg: 'Displaying topics {0} - {1} of {2}',
	         emptyMsg: "No topics to display"
		})
	});
	
	store.load({params:{start:0, limit:3}});
	grid.render("emp");
})