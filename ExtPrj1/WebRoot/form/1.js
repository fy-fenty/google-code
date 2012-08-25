Ext.onReady(function(){
//	var fm = new Ext.FormPanel({
//		title: '1st FORM',
//		width: 300,
//		bodyStyle:'padding:5px 5px 0',
//		labelWidth: 40,
//		labelAlign: 'right',
//		frame: true,
//		defaults: {
//			xtype: 'textfield',
//			width: 200
//		},
//		buttonAlign: 'right',
//		buttons: [{
//			text: '获得值',
//			handler: function(btn){
//				var val = {
//					uname: '小红',
//					upwd: '123456',
//					birthday: new Date()
//				};
//				
//				fm.findById('uname').setValue(val.uname);
//				fm.findById('upwd').setValue(val.upwd);
//				fm.findById('bir').setValue(val.birthday);
//			}
//		}],
//		items:[{
//			fieldLabel: '姓名',
//			id: 'uname',
//			name: 'uname'
//		},{
//			fieldLabel: '密码',
//			id: 'upwd',
//			name: 'upwd'
//		},{
//			fieldLabel: '生日',
//			xtype: 'datefield',
//			id: 'bir',
//			name: 'bir',
//			format: 'Y-m-d'
//		},{
//			id: 'sex',
//			name: 'sex',
//			xtype: 'combo',
//			fieldLabel: '性别',
//			value: 1,
//			store: [[0,'男'],[1,'女']]
//		}]
//	});
//	fm.render("d");
	
	var fm = new Ext.FormPanel({
		title: '1st FORM',
		width: 500,
		bodyStyle:'padding:5px 5px 0',
		labelWidth: 40,
		labelAlign: 'right',
		frame: true,
		layout: 'column',
		buttonAlign: 'right',
		buttons: [{
			text: '获得值',
			handler: function(btn){
				var val = {
					uname: '小红',
					upwd: '123456',
					birthday: new Date()
				};
				
				fm.findById('uname').setValue(val.uname);
				fm.findById('upwd').setValue(val.upwd);
				fm.findById('bir').setValue(val.birthday);
			}
		}],
		items:[{
			columnWidth: 0.50,
            layout: 'form',
            defaults: {
            	width: 150,
            	xtype: 'textfield'	
            },
            items: [{
				fieldLabel: '姓名',
				id: 'uname',
				name: 'uname'
			},{
				fieldLabel: '生日',
				xtype: 'datefield',
				id: 'bir',
				name: 'bir',
				format: 'Y-m-d'
			},{
				fieldLabel: '密码',
				id: 'upwd',
				name: 'upwd'
			}]
		},{
			columnWidth: 0.50,
			layout: 'form',
			defaults: {
				width: 150,
            	xtype: 'textfield'	
            },
			items: [{
				id: 'sex',
				name: 'sex',
				xtype: 'combo',
				fieldLabel: '性别',
				value: 1,
				store: [[0,'男'],[1,'女']]
			}]
		}]
	});
	fm.render("d");
	
});