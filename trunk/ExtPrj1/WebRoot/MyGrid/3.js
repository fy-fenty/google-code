var path = '/ExtPrj1';
Ext.onReady(function() {
	Ext.QuickTips.init();
	var myReader = new Ext.data.JsonReader({
		idProperty : 'id',
		root : 'rows',
		totalProperty : 'total_num',
		fields : [ {
			name : 'mid',
			mapping : 'id'
		}, {
			name : 'name',
			mapping : 'firstname'
		}, {
			name : 'job',
			mapping : 'occupation'
		} ]
	});

	Ext.data.Api.actions['read'] = 'load';
	var myStore = new Ext.data.Store( {
		reader : myReader,
		baseParams : {
			dno: 77
		},
		proxy : new Ext.data.HttpProxy( {
			api : {
				'load' : {
					'method' : 'POST',
					url : path + '/servlet/MyServlet1'
				}
			}
		})
	});
	
	var x = new Ext.grid.CheckboxSelectionModel({});
	
	var grid = new Ext.grid.EditorGridPanel( {
		title: '标题123',
		store : myStore,
		loadMask: true,// 必须grid先于load
		height : 300,
		width : 700,
		/*
		selModel: new Ext.grid.RowSelectionModel({
			singleSelect: false
		}),
		*/
		selModel:  x,
		colModel : new Ext.grid.ColumnModel( {
			defaults : {
				width : 120,
				header : '选项'
			},
			title : 'Json',
			columns : [
			new Ext.grid.RowNumberer(), 
			{
				id : 'mid',
				sortable : true,
				dataIndex : 'mid'
			}, {
				header : '姓名',
				dataIndex : 'name',
				align : 'right',
				renderer : function(val){
					return '$'+val;
				}
			}, {
				header : '改变',
				dataIndex : 'job',
				align : 'right',
				editor: new Ext.form.TextField({
	                allowBlank: false
	            })
			},{
				header : '操作',
				xtype: 'actioncolumn',
				width: 100,
				align : 'center',
				items: [{
					icon: '../resources/accept.png',
					getClass: function(){
						return 'hx';
					},
					handler: function(g,rowIndex,colIndex,item,evt){
						var rs = g.getStore().getAt(rowIndex);
						g.rsLoadMask = new Ext.LoadMask(g.getView().getRow(rowIndex),{msg: "正在更新数据..."});
						g.rsLoadMask.show();
						Ext.Ajax.request({
							url: g.getStore().proxy.api['load'].url,
							method: 'GET',
							params: {'job': rs.get('job')},
							callback: function(op,success,resp){
								g.rsLoadMask.hide();
								Ext.MessageBox.alert("提示",Ext.util.JSON.decode(resp.responseText)['msg'],function(){
									rs.commit();
								});
							}
						});
					}
				},{
					icon: '../resources/delete.gif',
					handler: function(g,rowIndex,colIndex,item,evt){
						var rs = g.getStore().getAt(rowIndex);
						g.rsLoadMask = new Ext.LoadMask(g.bwrap,{msg: "请等待..."});
						g.rsLoadMask.show();
						Ext.Ajax.request({
							url: g.getStore().proxy.api['load'].url,
							method: 'GET',
							params: {'job': rs.get('job')},
							callback: function(op,success,resp){
								g.rsLoadMask.hide();
								Ext.MessageBox.alert("提示",Ext.util.JSON.decode(resp.responseText)['msg'],function(){
									g.getStore().remove(rs);	
								});
							}
						});
					}
				}]
			},x]
		}),
		bbar: new Ext.Toolbar({
			height: 30,
			items:[{
				text: '增加',
				handler: function(){
					var ck = grid.getSelectionModel();
					var cks = ck.getSelections();
					var str = "";
					Ext.each(cks,function(record,index,items){
						str +=record.get('name')+",";
					});
					alert(str)
				}
			}]
		}),
		tbar : new Ext.PagingToolbar( {
			pageSize : 5,
			store : myStore,
			displayInfo : true,
			displayMsg : '显示 {0} - {1} 总记录数 {2}',
			emptyMsg : "无数据"
		})
	});	
	grid.render("d");
	grid.getBottomToolbar().add({
		xtype: 'textfield',
		value: '下跌股'
	},'-',{
		xtype: 'tbtext', 
		text: 'Item 1'
	},'-',{
		text: '修改',
		handler: function(){
			var ck = grid.getSelectionModel();
			var record = ck.getSelected();
			record.set('name','xiaoY');
		}
	},'-',{
		text: 'pp',
		handler: function(){
			var ck = grid.getSelectionModel();
			var record = ck.getSelected();
			//record.commit();
			record.reject();
		}
	});
	myStore.baseParams = {dno: 79};
	myStore.load({
		params: {
			start: 0,
			limit: 5
		}
	});
//	myStore.loadData(d);
});