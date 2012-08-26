var path = '/ApproveProject';
Ext.namespace("Emp.view");
var manager = new Ext.WindowGroup();
Emp.factory = {
	createWin : function(options) {
		if (Ext.getCmp(options.id)) {
			return;
		}
		var win = new Ext.Window(Ext.applyIf(options || {}, {
					title : '系统窗口',
					width : 200,
					height : 200,
					manager : manager,
					renderTo : Ext.getBody(),
					minimizable : false,
					maximizable : true,
					constrain : true,
					constrainHeader : true
				}));
		win.show();
		return win;
	},
	creComboBox : function(options) {
		return new Ext.form.ComboBox(Ext.applyIf(options || {}, {
					editable : false,
					width : 60,
					typeAhead : true,
					triggerAction : 'all',
					displayField : 'itemName',
					lazyRender : true,
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
				}));
	}

};