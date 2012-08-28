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
					read : FNS.projName + '/employee-findDd.action',
					create  : FNS.projName + '/employee-saveDispatch.action',
					update  : undefined,
					destroy : undefined
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
				save : function(store,batch,data){
					alert(batch);
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
			}, cf.load?{
                xtype: 'actioncolumn',
                width: 50,
                items: [ {
                    icon   : '../shared/icons/fam/accept.png',  // Use a URL in the icon config
                    handler: function(grid, rowIndex, colIndex) {
                        var rc = grid.store.getAt(rowIndex);
						Ext.Ajax.request({
						    url: FNS.projName+'/employee-updateDd.action',
						    params : {
								'hdVo.dd.dsId' : rc.data.dsId,
								'hdVo.dd.money':rc.data.money,
								'hdVo.dd.costExplain':rc.data.costExplain,
								'hdVo.dd.itemId':rc.data.itemId
						    },
						    success: function(response, opts) {
						       var obj = Ext.decode(response.responseText);
						       Ext.Msg.alert('Success: ', obj.msg);
						    }, 
						    failure: function(response, opts) {
						       var obj = Ext.decode(response.responseText);
								Ext.Msg.alert('Failure: ', obj.msg+":"+obj.exception);
						    }
						});
					}
                }, {
                    icon   : '../shared/icons/fam/delete.gif',  // Use a URL in the icon config
                    handler: function(grid, rowIndex, colIndex) {
                    	 var rc = grid.store.getAt(rowIndex);
						 Ext.Ajax.request({
						    url: FNS.projName+'/employee-deleteDd.action',
						    params : {
								'hdVo.dd.dsId' : rc.data.dsId
						    },
						    callback : function(options,success,response){
						        var obj = Ext.decode(response.responseText);
						        if(obj.success){
									Ext.Msg.alert('Success: ', obj.msg);
									grid.store.remove(rc);
						        } else {
									Ext.Msg.alert('Failure: ', obj.msg+','+obj.exception);
						        }
						    }
						});
                    }
                } ]
			}:{hidden:true}
		];
		var userGrid = new App.user.Grid({
			store : userStore,
			columns : userColumns
		});
		var onCommit = function() {
			if(cf.load){
				mform.getForm().submit({
					url : FNS.projName + '/employee-commitDispatch.action',
					success : function(form, action) {
						Ext.Msg.alert('Success', action.result.msg);
					},
					failure : function(form, action) {
						Ext.Msg.alert('Success', action.result.msg+":"+action.result.exception);
					}
				});
			} else {
				this.store.setBaseParam('hdVo', {'comment':mform.getForm().getFieldValues()['hdVo.comment'],'isCommit':true});
				this.store.save();
			}
		};
		var onSave = function(){
			if(cf.load){
				mform.getForm().submit({
					url : FNS.projName + '/employee-updateDl.action',
					success : function(form, action) {
						Ext.Msg.alert('Success', action.result.msg);
					},
					failure : function(form, action) {
						Ext.Msg.alert('Success', action.result.msg+":"+action.result.exception);
					}
				});
			} else {
				this.store.setBaseParam('hdVo', {'comment':mform.getForm().getFieldValues()['hdVo.comment']});
				this.store.save();				
			}
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
				handler : onCommit,
				scope : userGrid,
				xtype : ''
			}, {
				text : '保存',
				handler : onSave,
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