FNS.proportype = {
	findAllDispatchListByESn : function() {
		var store = new Ext.data.JsonStore({
			root : 'result',
			totalProperty : 'totalCount',
			url : FNS.projName
					+ '/employee-findAllDispatchListByESn.action',
			fields : [ {
				name : 'dlId',
				mapping : 'DLID'
			}, {
				name : 'createTime',
				mapping : 'CREATETIME'
			}, {
				name : 'esn',
				mapping : 'ESN'
			}, {
				name : 'currentStatus',
				mapping : 'CURRENTSTATUS'
			}, {
				name : 'totalMoney',
				mapping : 'TOTALMONEY'
			} ],
			paramNames : {
		        start : 'hdVo.start',
		        limit : 'hdVo.limit'
		    }
		});
		var grid = new Ext.grid.GridPanel({
			id : 'seeDL',
			height : 300,
			store : store,
			trackMouseOver : false,
			loadMask : true,
			columns : [ {
				header : "报销单编号",
				dataIndex : 'dlId',
				width : 100,
				sortable : true
			}, {
				header : "创建时间",
				dataIndex : 'createTime',
				width : 150,
				sortable : true
			}, {
				header : "创建人",
				dataIndex : 'esn',
				width : 100,
				align : 'right',
				sortable : true
			}, {
				header : "当前状态",
				dataIndex : 'currentStatus',
				width : 100,
				sortable : true
			}, {
				header : "明细总金额",
				dataIndex : 'totalMoney',
				width : 80,
				sortable : true
			} ],
			bbar : new Ext.PagingToolbar({
				pageSize : 5,
				store : store,
				displayInfo : true,
				displayMsg : '当前显示 {0} - {1} of {2}',
				emptyMsg : "没有任何数据"
			})
		});
		store.load({params : {'hdVo.start' : 0,'hdVo.limit' : 5}});
		FNS.createWin(Ext.apply({
			items : [ grid ]
		},arguments[0])).show();
	},
	saveDispatct : function(cf) {
		FNS.createDlWin(arguments[0]).show();
	},
	modifyDispatct : function(){
		var findAll = Ext.getCmp('seeDL');
		if(!findAll){
			Ext.Msg.alert('提示','查看所有报销单窗口未打开');
			return;
		}
		var selected = findAll.getSelectionModel().getSelected();
		if(!selected){
			Ext.Msg.alert('提示','请选中要修改的报销单');
			return;
		}
		FNS.createDlWin(Ext.apply({
			load : true,
			dlId : selected.data['dlId']
		},arguments[0])).show();
	}
};