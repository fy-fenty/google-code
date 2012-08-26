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
		});
storeDis.load({});

var updateStore = {
	disForm : null,
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
										name : "DS_ID",
										type : "string"
									}, {
										name : "ITEM_NAME",
										type : "string"
									}, {
										name : "MONEY",
										type : "float"
									}, {
										name : "COST_EXPLAIN",
										type : "string"
									}]
						})
			})

}

/**
 * 修改报销单
 */
function modifyDispatct() {
	win.setTitle("修改报销单");
	win.show();
	var cm = new Ext.grid.ColumnModel({
		defaults : {
			sortable : true
		},
		columns : [new Ext.grid.RowNumberer(), {
					header : '金额',
					width : 70,
					renderer : 'usMoney',
					dataIndex : "MONEY",
					editor : new Ext.form.NumberField({
								allowBlank : false
							})
				}, {
					header : '明細选项',
					width : 130,
					dataIndex : "ITEM_NAME",
					editor : new Ext.form.ComboBox({
						typeAhead : true,
						triggerAction : 'all',
						displayField : 'itemName',
						// valueField : "deId",
						lazyRender : true,
						listeners : {
							change : function(com, newValue, oldValue) {
								// alert(com.getValue());
							},
							select : function(combo, record, index) {
								// alert(combo.getValue());
							}
						},
						store : new Ext.data.Store({
							proxy : new Ext.data.HttpProxy({
										url : "/ApproveProject/emp/ditem.action",
										method : "POST"
									}),
							reader : new Ext.data.JsonReader({
										idProperty : "deId",
										fields : [{
													name : "deId",
													type : "string"
												}, {
													name : "itemName",
													type : "string"
												}]
									})
						})
					})
				}, {
					header : '明細說明',
					width : 70,
					dataIndex : "COST_EXPLAIN",
					editor : new Ext.form.TextField({})
				}, {
					header : '附件',
					width : 60,
					// dataIndex : "accessory",
					// editor : new Ext.form.TextField({
					// inputType : "file",
					// allowBlank : false
					// })
					editor : new Ext.ux.form.FileUploadField({
								buttonOnly : true,
								buttonText : "上传",
								height : 18
							})
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
									grid.getStore().getAt(rowIndex).commit();
								}
							}]
				}]
	});

	var grid = new Ext.grid.EditorGridPanel({
				selModel : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
				store : updateStore.detailStore,
				cm : cm,
				height : 200,
				frame : true,
				clicksToEdit : 1
			});

	updateStore.disForm = new Ext.form.FormPanel({
				layout : "fit",
				labelAlign : 'right',
				labelWidth : 80,
				frame : true,
				buttonAlign : "center",
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
										name : "eventExplain"
									}]
						}, {
							xtype : 'fieldset',
							title : '报销单明细',
							autoHeight : true,
							items : [grid]
						}, {
							xtype : 'fieldset',
							title : '审批流程',
							autoHeight : true,
							items : [
							// grid = new Ext.grid.EditorGridPanel({
							// store : new Ext.data.Store({
							// baseParams : {
							// start : 0,
							// limit : 5
							// },
							// proxy : new Ext.data.HttpProxy({
							// url : "/ApproveProject/emp/dis.action",
							// method : "POST"
							// }),
							// reader : new Ext.data.JsonReader({
							// root : "data",
							// totalProperty : "totalCount",
							// idProperty : "DL_ID",
							// fields : [{
							// name : "DL_ID",
							// type : "string"
							// }, {
							// name : "E_SN",
							// type : "string"
							// }, {
							// name : "CREATE_TIME",
							// type : "date"
							// }, {
							// name : "STATUS",
							// type : "string"
							// }, {
							// name : "MONEY",
							// type : "float"
							// }]
							// })
							// }),
							// cm : new Ext.grid.ColumnModel({
							// defaults : {
							// sortable : true
							// },
							// columns : [new Ext.grid.RowNumberer(), {
							// header : '当前审批人',
							// width : 70
							// // dataIndex : "",
							// }, {
							// header : '审批时间',
							// width : 130
							// // dataIndex : "",
							// }, {
							// header : '下一个审批人',
							// width : 70
							// // dataIndex : "",
							// }, {
							// header : '审批意见',
							// width : 95
							// // dataIndex : "",
							// }, {
							// header : '审批状态',
							// width : 95
							// }]
							// }),
							// height : 200,
							// frame : true,
							// clicksToEdit : 1
							// })

							]
						}],
				buttons : [{
							text : "保存"
						}, {
							text : "取消"
						}]
			})
	return {
		panel : updateStore.disForm
	}
}

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
					var record = wingrid.getSelectionModel().getSelected();
					if (!(record.json.STATUS == "草稿" || record.json.STATUS == "已打回")) {
						Ext.Msg.alert("提示", "该报销单不是草稿或正在审批！");
						return;
					}
					updateStore.disStore.load({
								params : {
									disId : record.json.DL_ID
								},
								callback : function(r, options, success) {
									if (success) {
										updateStore.disForm.getForm().reset();
										updateStore.disForm.getForm()
												.loadRecord(r[0]);
										win.destroy();
									}
								}
							});

					updateStore.detailStore.load({
								params : {
									disId : record.json.DL_ID
								},
								callback : function(r, options, success) {
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
					Ext.getCmp("disTab").getActiveTab().destroy();
					win.destroy();
				}
			}]
		})],
		layout : "fit",
		store : storeDis,
		height : 180,
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
					renderer : Ext.util.Format.dateRenderer('m/d/Y'),
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
					store : storeDis,
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

var empDisGird;
/**
 * 查詢雇员报销单
 * 
 * @return {}
 */
function findAllDispatchListByESn() {
	empDisGird = new Ext.grid.GridPanel({
				selModel : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
				layout : "fit",
				store : storeDis,
				height : 400,
				columns : [{
							header : '报销单ID',
							width : 160,
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
							renderer : Ext.util.Format.dateRenderer('m/d/Y'),
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
			});

	return {
		panel : empDisGird,
		store : storeDis
	}
}

/**
 * 保存报销单
 */
function saveDispatct() {
	return {
		panel : new Object()
	};
}
