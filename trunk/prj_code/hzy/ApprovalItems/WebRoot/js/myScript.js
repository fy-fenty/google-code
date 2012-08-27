var FNS = {
	projName : '/ApprovalItems',
	createWin : function(cf) {
		var win1 = new Ext.Window(Ext.applyIf(cf || {}, {
			manager : group,
			title : '系统窗口',
			renderTo : Ext.getBody(),
			minimizable : false,
			maximizable : true,
			constrain : true,
			constrainHeader : true
		}));
		var group = new Ext.WindowGroup();
		return win1;
	},
	createDlWin : function(cf) {
		cf = cf || {};
		var combo = new Ext.form.ComboBox({
			valueField : 'DE_ID',
			editable : false,
			triggerAction : 'all',
			displayField : 'ITEM_NAME',
			store : new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : FNS.projName + '/employee-findAllDetailItem.action'
				}),
				reader : new Ext.data.JsonReader({
					idProperty : 'DL_ID',
					root : 'result',
					fields : [ 'DE_ID', 'ITEM_NAME' ]
				})
			})
		});
		var textField = new Ext.form.TextField();
		var userStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				api : {
					read : FNS.projName + '/employee-findDd.action'
				}
			}),
			reader : new Ext.data.JsonReader({
				root : 'dds',
				fields : [ {
					name : 'money',
					allowBlank : false
				}, {
					name : 'costExplain'
				}, {
					name : 'itemId',
					allowBlank : false
				}, {
					name : 'accessory'
				}, {
					name : 'costExplain'
				}, {
					name : 'dsId'
				}, {
					name : 'flag'
				}, {
					name : 'itemId'
				}, {
					name : 'money'
				}, {
					name : 'sheetId'
				} ]
			}),
			writer : new Ext.data.JsonWriter({
				encode : true,
				writeAllFields : true
			}),
			autoSave : false,
			listeners : {
				save : function(store, batch, data) {
					if (batch > 0) {
						Ext.Msg.alert('提示', '提交报销单成功！');
					} else {
						Ext.Msg.alert('提示', '提交报销单失败！');
					}
				}
			}
		});
		var userColumns = [ {
				header : "金额",
				width : 120,
				sortable : true,
				dataIndex : 'money',
				editor : textField
			}, {
				header : "说明",
				width : 120,
				dataIndex : 'costExplain',
				editor : new Ext.form.TextArea()
			}, {
				header : "类别",
				width : 120,
				sortable : true,
				dataIndex : 'itemId',
				editor : combo
			}, {
                xtype: 'actioncolumn',
                width: 50,
                items: [ {
                    icon   : '../shared/icons/fam/accept.png',  // Use a URL in the icon config
                    handler: function(grid, rowIndex, colIndex) {
                        var rc = grid.store.getAt(rowIndex);
						Ext.Ajax.request({
						   url: FNS.projName+'/employee-updateDd.action',
						   success: function(response, opts) {
						      var obj = Ext.decode(response.responseText);
						      console.dir(obj);
						   },
						   failure: function(response, opts) {
						      console.log('server-side failure with status code ' + response.status);
						   }
						});
					}
                }, {
                    icon   : '../shared/icons/fam/delete.gif',  // Use a URL in the icon config
                    handler: function(grid, rowIndex, colIndex) {
                    	 var rc = grid.store.getAt(rowIndex);
						 Ext.Ajax.request({
						    url: FNS.projName+'/employee-deleteDd.action',
						    success: function(response, opts) {
						       var obj = Ext.decode(response.responseText);
						       console.dir(obj);
						    }, 
						    failure: function(response, opts) {
						       console.log('server-side failure with status code ' + response.status);
						    }
						});
                    }
                } ]
			}
		];
		var userGrid = new App.user.Grid({
			store : userStore,
			columns : userColumns
		});
		var onSave = function() {
			this.store.setBaseParam('hdVo.comment', mform.getForm().getFieldValues()['hdVo.comment']);
			this.store.save();
		};
		var onUpdate = function() {
			mform.getForm().submit({
				url : FNS.projName + '/employee-updateDl.action',
				success : function(form, action) {
					Ext.Msg.alert('Success', action.result.msg);
				},
				failure : function(form, action) {
					Ext.Msg.alert('Success', action.result.msg+":"+action.result.exception);
				}
			});
		};
		var mform = new Ext.FormPanel({
			frame : true,
			width : 700,
			labelAlign : 'right',
			labelWidth : 70,
			items : [ {
				fieldLabel : '事件说明',
				height : 80,
				width : 600,
				xtype : 'textarea',
				name : 'hdVo.comment'
			}, userGrid ],
			buttons : [ {
				text : '提交',
				handler : onSave,
				scope : userGrid,
				xtype : ''
			}, {
				text : '保存',
				handler : onUpdate,
				scope : userGrid
			} ]
		});
		if (cf.load && cf.dlId) {
			userGrid.store.load({
				params : {
					'hdVo.dlId' : cf.dlId
				}
			});
			mform.getForm().load({
				url : FNS.projName + '/employee-findDl.action',
				params : {
					'hdVo.dlId' : cf.dlId
				}
			});
		}
		return FNS.createWin(Ext.apply({
			items : [ mform ]
		}, arguments[0]));
	}
};