Ext.onReady(function(){
	var x = new Ext.Button({
		text: 'xxx',
		handler: function(){
			
		}
	});
	
	var fm = new Ext.FormPanel({
		width: 400,
		title: '1ST',
		frame: true,
		labelAlign: 'right',
		labelWidth: 50,
		bodyStyle: 'padding: 5px 5px',
		defaults: {
			xtype: 'textfield',			
			width: 300
		},
		items: [{
			id: 'uname',
			fieldLabel: '姓名',
			name: 'uname',
			value: 'xxxx'
		},{
			id: 'upwd',
			fieldLabel: '密码',
			name: 'upwd',
			value: 'yyyy'
		},{
			id: 'birthday',
			xtype: 'datefield',			
			fieldLabel: '生日',
			name: 'birthday',
			format: 'Y-m-d'
		}],
		buttonAlign: 'right',
		buttons: [{
			text: '修改',
			handler: function(btn){
				//这里ajax
				var data = {
					uname: 'xm',upwd:'123456',birthday:new Date()
				};
//				fm.getForm().setValues(data);
				fm.findById("birthday").setValue(data.birthday);
			}
		},{
			text: '重置',
			handler: function(btn){
				//这里ajax
				
				fm.getForm().reset();
			}
		}]
	});
	fm.render("d");
});