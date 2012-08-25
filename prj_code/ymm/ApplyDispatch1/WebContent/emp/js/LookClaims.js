function empLook() {
	// Ext.onReady(function(){
	var store = new Ext.data.JsonStore({
				// store configs
				autoDestroy : true,
				url : '/Apply/findAll.action',
				storeId : 'myStore',
				totalProperty : 'totalCount',
				remoteSort : true,
				// reader configs
				root : 'result',
				idProperty : 'name',
				fields : ['E_SN', 'SHEET_ID', 'TIME', 'ENAME', 'STATUS',
						'MONEY']
			});

	var grid = new Ext.grid.GridPanel({
				store : store,
				width : 700,
				height : 400,
				loadMask : true,
				columns : [{
							id : 'E_SN',
							header : "用户编号",
							dataIndex : 'E_SN',
							width : 100,
							sortable : true
						}, {
							id : 'SHEET_ID',
							header : "报销单id",
							dataIndex : 'SHEET_ID',
							width : 100,
							sortable : true
						}, {
							id : 'TIME',
							header : "申请时间",
							dataIndex : 'TIME',
							width : 100,
							sortable : true
						}, {
							id : 'ENAME',
							header : "用户姓名",
							dataIndex : 'ENAME',
							width : 100,
							sortable : true
						}, {
							id : 'STATUS',
							header : "报销单状态",
							dataIndex : 'STATUS',
							width : 100,
							sortable : true
						}, {
							id : 'MONEY',
							header : "报销单金额",
							dataIndex : 'MONEY',
							width : 100,
							sortable : true
						}, {
							xtype : 'actioncolumn',
							header:"删除",
							width : 50,
							items : [{
										icon : '../images/delete.gif', 
										tooltip : 'Sell stock',
										handler : function(grid, rowIndex,
												colIndex) {
											var rec = store.getAt(rowIndex);
											alert("Sell " + rec.get('company'));
										}
									}, {
										getClass : function(v, meta, rec) { 
											if (rec.get('change') < 0) {
												this.items[1].tooltip = 'Do not buy!';
												return 'alert-col';
											} else {
												this.items[1].tooltip = 'Buy stock';
												return 'buy-col';
											}
										},
										handler : function(grid, rowIndex,
												colIndex) {
											var rec = store.getAt(rowIndex);
											alert("Buy " + rec.get('company'));
										}
									}]
						}],
				bbar : new Ext.PagingToolbar({
							pageSize : 1,
							store : store,
							displayInfo : true,
							displayMsg : 'Displaying topics {0} - {1} of {2}',
							emptyMsg : "No topics to display",
							items : ['-', {
										pressed : true,
										enableToggle : true,
										text : 'Show Preview',
										cls : 'x-btn-text-icon details',
										toggleHandler : function(btn, pressed) {
											var view = grid.getView();
											view.showPreview = pressed;
											view.refresh();
										}
									}]
						})
			});

	var panel = new Ext.Panel({
				width : 800,
				frame : true,
				items : grid
			});
	panel.render("div");
	store.load({
				params : {
					"vo.start" : 0,
					"vo.limit" : 1
				}
			});
	return panel;
}
// });

// empLook();
