Emp.factory.findAllDispatchListByESn = function() {
	var storeDis = new Ext.data.Store({
				baseParams : {
					start : 0,
					limit : 5
				},
				proxy : new Ext.data.HttpProxy({
							url : "/ApproveProject/emp/dis.action",
							method : "POST"
						}),
				reader : new Ext.data.JsonReader({
							root : "data",
							totalProperty : "totalCount",
							idProperty : "DL_ID",
							fields : [{
										name : "DL_ID",
										type : "string"
									}, {
										name : "E_SN",
										type : "string"
									}, {
										name : "CREATE_TIME",
										type : "date"
									}, {
										name : "STATUS",
										type : "string"
									}, {
										name : "MONEY",
										type : "float"
									}]
						})
			})
	var win = Emp.factory.createWin(Ext.apply({
		width : 500,
		height : 260,
		layout : "fit",
		items : [new Ext.grid.GridPanel({
					selModel : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					store : storeDis,
					columns : [{
								header : '报销单ID',
								width : 100,
								sortable : true,
								dataIndex : 'DL_ID'
							}, {
								header : '编号',
								width : 75,
								sortable : true,
								dataIndex : 'E_SN'
							}, {
								header : '创建时间',
								width : 100,
								sortable : true,
								renderer : Ext.util.Format
										.dateRenderer('m/d/Y'),
								dataIndex : 'CREATE_TIME'
							}, {
								header : '当前状态',
								width : 75,
								sortable : true,
								dataIndex : 'STATUS'
							}, {
								header : '金額',
								width : 120,
								sortable : true,
								renderer : 'usMoney',
								dataIndex : 'MONEY'
							}],
					bbar : new Ext.PagingToolbar({
								store : storeDis,
								displayInfo : true,
								pageSize : 5,
								displayMsg : "Displaying topics {0} - {1} of {2}",
								emptyMsg : "No topics to display"
							})
				})]
	}, arguments[0]));
	storeDis.load();
}