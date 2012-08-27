Emp.factory.saveDispatct = function() {

	var App = new Ext.App({});

	var proxy = new Ext.data.HttpProxy({
				url : '/ApproveProject/emp/a.action'
			});

	var reader = new Ext.data.JsonReader({
				totalProperty : 'total',
				successProperty : 'success',
				idProperty : 'id',
				root : 'data',
				messageProperty : 'message'
			}, [{
						name : 'id'
					}, {
						name : 'money',
						allowBlank : false
					}, {
						name : 'costExplain'
					}, {
						name : 'itemId',
						allowBlank : false
					}]);

	var writer = new Ext.data.JsonWriter({
				encode : true,
				writeAllFields : false
			});

	var store = new Ext.data.Store({
				id : 'user',
				restful : true,
				proxy : proxy,
				reader : reader,
				// writer : writer,
				autoSave : true
			});

	var comboBox = Emp.factory.creComboBox({});
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
				dataIndex : "itemId",
				allowBlank : false,
				editor : comboBox,
				allowBlank : false,
				renderer : function(value, metadata, record) {
					var index = comboBox.getStore().find('deId', value);
					if (index != -1) {
						return comboBox.getStore().getAt(index).data.itemName;
					}
					return value;
				}
			}, {
				header : '明細說明',
				width : 70,
				dataIndex : "costExplain",
				editor : new Ext.form.TextField({})
			}];
	// use RowEditor for editing
	var editor = new Ext.ux.grid.RowEditor({});

	var rightMenu = new Ext.ux.grid.RightMenu({
				items : [{
							text : '增加明细',
							recHandler : onAdd
						}, {
							text : '删除明细',
							recHandler : function(record, rowIndex, grid) {
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
				store : store,
				plugins : [rightMenu, editor],
				columns : userColumns,
				viewConfig : {
					forceFit : true
				}
			});

	var creFrom = new Ext.form.FormPanel({
				labelAlign : 'right',
				labelWidth : 80,
				frame : true,
				buttonAlign : "center",
				items : [{
							xtype : 'fieldset',
							height : 230,
							title : '报销单',
							autoHeight : true,
							items : [{
										width : 300,
										height : 60,
										id : "eventExplain",
										xtype : 'textarea',
										fieldLabel : '事件说明',
										name : "eventExplain"
									}]
						}, {
							xtype : 'fieldset',
							title : '报销单明细',
							items : [userGrid]
						}],
				buttons : [{
					text : "保存",
					xtype : 'button',
					handler : function() {
						alert(storeToJson(store));
						Ext.Ajax.request({
									url : '/ApproveProject/emp/savedis.action',
									success : function() {
										store.commitChanges();
									},
									failure : function() {
									},
									params : {
										data : storeToJson(store),
										eventExplain : Ext.get("eventExplain")
												.getValue()
									}
								});

					}
				}, {
					xtype : 'button',
					text : "取消",
					handler : function() {
						win.close();
					}
				}]
			});

	/**
	 * store转json字符串
	 */
	function storeToJson(jsondata) {
		var listRecord;
		if (jsondata instanceof Ext.data.Store) {
			listRecord = new Array();
			jsondata.each(function(record) {
						listRecord.push(record.data);
					});
		} else if (jsondata instanceof Array) {
			listRecord = new Array();
			Ext.each(jsondata, function(record) {
						listRecord.push(record.data);
					});
		}
		return Ext.encode(listRecord);
	}
	var win = Emp.factory.createWin(Ext.apply({
				resizable : false,
				width : 500,
				height : 400,
				layout : "fit",
				items : [creFrom],
				listeners : {
					beforeshow : function() {
						onAdd();
					}
				}
			}, arguments[0]));
	/**
	 * onAdd
	 */
	function onAdd(btn, ev) {
		var u = new userGrid.store.recordType({
					money : '',
					costExplain : '',
					itemId : ''
				});
		editor.stopEditing();
		userGrid.store.insert(0, u);
		editor.startEditing(0, 1);
	}
	/**
	 * onDelete
	 */
	function onDelete() {
		var rec = userGrid.getSelectionModel().getSelected();
		if (!rec) {
			return false;
		}
		userGrid.store.remove(rec);
	}
}