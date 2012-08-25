function sendClaims() {
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
	var form = new Ext.FormPanel({
				width : 700,
				height : 200,
				frame:true,
				defaultType : 'textfield',
				defaults : {
					width : 150
				},
				items : [{
							fieldLabel : '用户编号',
							name : 'E_SN',
							allowBlank : false,
							disabled : true
						}, new Ext.form.TimeField({
									fieldLabel : '创建时间',
									name : 'time',
									minValue : '8:00am',
									maxValue : '6:00pm',
									disabled : true
								}), {
							fieldLabel : '总金额',
							name : 'money',
							allowBlank : false,
							disabled : true
						}, {
							fieldLabel : '报销单状态',
							name : 'money',
							allowBlank : false,
							disabled : true
						}],
				buttons : [{
							text : '保存'
						}, {
							text : '重置'
						}]

			});

	var detailGrid = new Ext.grid.EditorGridPanel({
				store : store,
				width : 700,
				height : 400,
				selModel:new Ext.grid.RowSelectionModel(),
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
				width : 700,
				height : 800,
				items : [form, detailGrid]
			});
	return panel;
}