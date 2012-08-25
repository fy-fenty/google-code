Ext.onReady(function(){
	var fm = new Ext.FormPanel({
		title: '1st fm',
		width: 600,
		frame: true,
		layout: 'column',
		collapsible: true,
		labelWidth: 50,
		labelAlign: 'right',
		defaults: {
			bodyStyle: 'margin: 5px 15px'
		},
		items: [{
			columnWidth: 0.30,
			layout: 'form',
			defaults: {
				xtype: 'textfield'
			},
			items:[
				{
					name: 'uname',
					fieldLabel: 'xm'
				}
			]
		},{
			columnWidth: 0.30,
			layout: 'form',
			defaults: {
				xtype: 'textfield'
			},
			items:[
				{
					name: 'uage',
					fieldLabel: 'age'
				}
			]
		},{
			columnWidth: 0.40,
			layout: 'fit',
			items:[
				{
					xtype: 'textarea',
					name: 'upwd',
					fieldLabel: 'pwd'
				}
			]
		}],
//		items: [
//			{
//				name: 'uname',
//				fieldLabel: 'xm'
//			},
//			{
//				name: 'upwd',
//				fieldLabel: 'pw'
//			},
//			{
//				name: 'uage',
//				fieldLabel: 'age'
//			}
//		],
		buttons: [{
			text: '提交'
		}]
	});
	fm.render("d");
});