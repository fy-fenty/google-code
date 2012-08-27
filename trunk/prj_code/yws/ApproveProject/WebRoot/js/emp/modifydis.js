Emp.factory.modifyDispatct = function modifyDispatct() {

	var winUpdate;
	var view = {
		storeDisGrid : new Ext.data.Store({
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
				}),
		disStore : new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : "/ApproveProject/emp/disById.action",
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
								idProperty : "dlId",
								fields : [{
											name : "dlId",
											type : "string"
										}, {
											name : "ESn",
											type : "string"
										}, {
											name : "createTime",
											type : "string"
										}, {
											name : "STATUS",
											type : "string"
										}, {
											name : "eventExplain",
											type : "string"
										}]
							})
				}),
		detailStore : new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : "/ApproveProject/emp/detail.action",
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
								idProperty : "DS_ID",
								fields : [{
											name : "dsId",
											type : "string",
											mapping : "DS_ID"
										}, {
											name : "itemId",
											type : "string",
											mapping : "ITEM_ID"
										}, {
											name : "itemName",
											type : "string",
											mapping : "ITEM_NAME"

										}, {
											name : "money",
											type : "float",
											mapping : "MONEY"
										}, {
											name : "costExplain",
											type : "string",
											mapping : "COST_EXPLAIN"
										}]
							})
				}),
		createForm : function() {

			var App = new Ext.App({});

			var proxy = new Ext.data.HttpProxy({
						url : '/ApproveProject/emp/a.action'
					});

			// var reader = new Ext.data.JsonReader({
			// totalProperty : 'total',
			// successProperty : 'success',
			// idProperty : 'id',
			// root : 'data',
			// messageProperty : 'message'
			// }, [{
			// name : 'id'
			// }, {
			// name : 'money',
			// allowBlank : false
			// }, {
			// name : 'costExplain'
			// }, {
			// name : 'itemId',
			// allowBlank : false
			// }]);

			var writer = new Ext.data.JsonWriter({
						encode : true,
						writeAllFields : false
					});

			var store = new Ext.data.Store({
						id : 'user',
						restful : true,
						proxy : proxy,
						// reader : reader,
						writer : writer,
						autoSave : true
					});

			var comboBox = Emp.factory.creComboBox({
						listeners : {
							select : function() {
								var store = userGrid.getStore();
								for (var i = 0; i < store.getCount(); i++)
									if (store.getAt(i) == comboBox.rowRecord) {
										store.getAt(i).data.itemId = comboBox
												.getValue();
										break;
									}
							}
						}
					});
			var userColumns = [{
						header : '金额',
						width : 70,
						renderer : 'usMoney',
						dataIndex : "money",
						editor : new Ext.form.NumberField({
									allowBlank : false
								})
					}, {
						header : '明細选项',
						width : 130,
						dataIndex : "itemName",
						allowBlank : false,
						editor : comboBox,
						renderer : function(value, metadata, record) {
							comboBox.rowRecord = record;
							var index = comboBox.getStore().find('deId', value);
							if (index != -1) {
								return comboBox.getStore().getAt(index).data.itemName;
							}
							return value;
						}
					}, {
						header : '明細ID',
						width : 130,
						dataIndex : "itemId",
						allowBlank : false
					}, {
						header : '明細說明',
						width : 70,
						dataIndex : "costExplain",
						editor : new Ext.form.TextField({})
					}, {
						xtype : 'actioncolumn',
						header : "操作",
						width : 50,
						items : [{
									icon : '/ApproveProject/images/delete.gif',
									tooltip : 'Sell stock',
									handler : function(grid, rowIndex, colIndex) {
										grid.getStore().removeAt(rowIndex);
									}
								}, {
									icon : "/ApproveProject/images/accept.png",
									handler : function(grid, rowIndex, colIndex) {
										grid.getStore().getAt(rowIndex)
												.commit();
									}
								}]
					}];

			var isUpdate = true;
			// use RowEditor for editing
			var editor = new Ext.ux.grid.RowEditor({
				errorText : null,
				saveText : "Update",
				listeners : {
					afteredit : function(r, o, record) {
						var data = "";
						if (isUpdate) {
							var record = userGrid.getSelectionModel()
									.getSelected();
							data = Ext.encode(record.data);
						} else {
							data = Ext.encode(record.data);
						}
						alert(data);
						Ext.Ajax.request({
							url : '/ApproveProject/emp/updateOrAddDetail.action',
							success : function() {
								userGrid.getStore().commitChanges();
							},
							failure : function() {
								alert(2);
							},
							params : {
								data : data,
								disId : Ext.get("dlId").getValue()
							}
						});
					},
					bindHandler : function() {
					}
				}

			});

			var rightMenu = new Ext.ux.grid.RightMenu({
						items : [{
									text : '增加明细',
									recHandler : function(btn, ev) {
										var u = new userGrid.store.recordType({
													money : '',
													costExplain : '',
													itemId : ''
												});
										editor.stopEditing();
										userGrid.store.insert(0, u);
										editor.startEditing(0, 1);
										isUpdate = false;
									}
								}, {
									text : '删除明细',
									recHandler : function(record, rowIndex,
											grid) {
									}
								}]
					});

			var userGrid = new Ext.grid.GridPanel({
						selModel : new Ext.grid.RowSelectionModel({
									singleSelect : true
								}),
						frame : true,
						iconCls : 'icon-grid',
						height : 160,
						store : view.detailStore,
						plugins : [rightMenu, editor],
						columns : userColumns,
						viewConfig : {
							forceFit : true
						},
						listeners : {
							afterrender : function() {
							}
						}
					});
			var form = new Ext.form.FormPanel({
				layout : "fit",
				labelAlign : 'right',
				labelWidth : 80,
				frame : true,
				defaults : {
					buttonAlign : "center"
				},
				items : [{
					xtype : 'fieldset',
					height : 200,
					title : '报销单',
					autoHeight : true,
					defaultType : "textfield",
					items : [{
								fieldLabel : '报销单编号',
								name : 'dlId',
								id : "dlId",
								disabled : true
							}, {
								fieldLabel : '雇员编号',
								name : 'ESn',
								disabled : true
							}, {
								fieldLabel : '创建时间',
								name : 'createTime',
								disabled : true
							}, {
								width : 300,
								height : 60,
								xtype : 'textarea',
								fieldLabel : '事件说明',
								id : "eventExplain",
								name : "eventExplain"
							}],
					buttons : [{
						text : "保存报销单",
						handler : function() {
							Ext.Ajax.request({
								url : '/ApproveProject/emp/updateDispatch.action',
								success : function() {
									Ext.Msg.alert("操作提示", "更新成功!");
								},
								failure : function() {
									alert(2);
								},
								params : {
									eventExplain : Ext.get("eventExplain")
											.getValue(),
									disId : Ext.get("dlId").getValue()
								}
							});
						}
					}, {
						text : "提交报销单",
						handler : function() {
							Ext.Ajax.request({
								url : '/ApproveProject/emp/commitDispatch.action',
								success : function() {
									Ext.Msg.alert("操作提示", "已进入审批流程!");
									winUpdate.close();
								},
								failure : function() {
									alert(2);
								},
								params : {
									disId : Ext.get("dlId").getValue(),
									eventExplain : Ext.get("eventExplain")
											.getValue()
								}
							});
						}
					}]
				}, {
					xtype : 'fieldset',
					title : '报销单明细',
					autoHeight : true,
					items : [userGrid]
				}]
			});
			return form;
		},
		createWin : function() {
			var win = new Ext.Window({
				modal : true,
				closable : false,
				items : [wingrid = new Ext.grid.GridPanel({
					selModel : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					plugins : [new Ext.ux.grid.RightMenu({
						items : [{
							text : '修改',
							recHandler : function(record, rowIndex, grid) {
								var record = wingrid.getSelectionModel()
										.getSelected();
								if (!(record.json.STATUS == "草稿" || record.json.STATUS == "已打回")) {
									Ext.Msg.alert("提示", "该报销单不是草稿或正在审批！");
									return;
								}
								view.disStore.load({
									params : {
										disId : record.json.DL_ID
									},
									callback : function(r, options, success) {
										if (success) {
											var form = view.createForm();
											form.getForm().reset();
											form.getForm().loadRecord(r[0]);
											win.destroy();
											winUpdate = Emp.factory
													.createWin(Ext.apply({
														width : 500,
														height : 500,
														id : "win_dis"
																+ r[0].data.dlId,
														items : [form]
													}, arguments[0]));
										}
									}
								});

								view.detailStore.load({
											params : {
												disId : record.json.DL_ID
											},
											callback : function(r, options,
													success) {
											}
										});
							}
						}, {
							text : '删除',
							recHandler : function(record, rowIndex, grid) {
								alert(1);
							}
						}, {
							text : '取消',
							recHandler : function(record, rowIndex, grid) {
								win.destroy();
							}
						}]
					})],
					layout : "fit",
					store : view.storeDisGrid,
					height : 160,
					width : 370,
					columns : [{
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
								width : 70,
								sortable : true,
								dataIndex : 'STATUS'
							}, {
								header : '金額',
								width : 110,
								sortable : true,
								renderer : 'usMoney',
								dataIndex : 'MONEY'
							}],
					bbar : new Ext.PagingToolbar({
								store : view.storeDisGrid,
								displayInfo : true,
								pageSize : 5,
								displayMsg : "Displaying topics {0} - {1} of {2}",
								emptyMsg : "No topics to display"
							})
				})]
			});

			win.on('beforedestroy', function(w) {
						w.hide();
						return false;
					});
			return win;
		}
	};
	var winView = view.createWin();
	winView.show();
	view.storeDisGrid.load({});

}