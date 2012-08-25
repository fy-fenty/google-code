var path = '/ExtPrj1';
Ext.onReady(function() {
	Ext.QuickTips.init();
	var myReader = new Ext.data.JsonReader( {
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
		proxy : new Ext.data.HttpProxy( {
			api : {
				'load' : {
					'method' : 'POST',
					url : path + '/servlet/MyServlet1'
				}
			}
		})
	});
	
	var grid = new Ext.grid.GridPanel( {
		store : myStore,
		loadMask: true,// 必须grid先于load
		height : 300,
		width : 700,
		selModel: new Ext.grid.RowSelectionModel({
			singleSelect: true
		}),
		colModel : new Ext.grid.ColumnModel( {
			defaults : {
				width : 120,
				header : '选项'
			},
			title : 'Json',
			columns : [ {
				id : 'mid',
				sortable : true,
				dataIndex : 'mid'
			}, {
				header : '姓名',
				dataIndex : 'name',
				align : 'right'
			}, {
				header : '改变',
				dataIndex : 'job',
				align : 'right'
			}]
		}),
		tbar : new Ext.PagingToolbar( {
			pageSize : 10,
			store : myStore,
			displayInfo : true,
			displayMsg : '显示 {0} - {1} 总记录数 {2}',
			emptyMsg : "无数据"
		})
	});	
	grid.render("d");
	myStore.load();
//	myStore.loadData(d);
});