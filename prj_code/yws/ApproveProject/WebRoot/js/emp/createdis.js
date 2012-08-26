Emp.factory.saveDispatct = function() {

	var App = new Ext.App({});

	var proxy = new Ext.data.HttpProxy({
				api : {
					read : '/ApproveProject/emp/savedis.action',
					create : '/ApproveProject/emp/savedis.action',
					update : '/ApproveProject/emp/savedis.action',
					destroy : '/ApproveProject/emp/savedis.action'
				}
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
						name : 'email',
						allowBlank : false
					}, {
						name : 'first',
						allowBlank : false
					}, {
						name : 'last',
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
				writer : writer,
				autoSave : true
			});

	var userColumns = [{
				header : '金额',
				width : 70,
				renderer : 'usMoney',
				dataIndex : "MONEY",
				editor : new Ext.form.NumberField({
						// allowBlank : false
						})
			}, {
				header : '明細选项',
				width : 130,
				dataIndex : "ITEM_NAME",
				allowBlank : false,
				editor : Emp.factory.creComboBox()
			}, {
				header : '明細說明',
				width : 70,
				dataIndex : "COST_EXPLAIN",
				editor : new Ext.form.TextField({})
			}];
	// use RowEditor for editing
	var editor = new Ext.ux.grid.RowEditor({
				saveText : 'Update'
			});

	var rightMenu = new Ext.ux.grid.RightMenu({
				items : [{
							text : '增加',
							recHandler : onAdd
						}, {
							text : '删除',
							recHandler : function(record, rowIndex, grid) {
							}
						}]
			});

	var userGrid = new Ext.grid.GridPanel({
				frame : true,
				iconCls : 'icon-grid',
				height : 160,
				store : store,
				plugins : [editor, rightMenu],
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
							height : 200,
							title : '报销单',
							autoHeight : true,
							defaultType : "textfield",
							items : [{
										width : 300,
										height : 60,
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
							handler : function() {
								store.save();
							}
						}, {
							text : "取消"
						}]

			});
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
					first : '',
					last : '',
					email : ''
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