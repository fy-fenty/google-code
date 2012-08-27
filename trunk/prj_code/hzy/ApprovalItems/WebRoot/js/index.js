Ext.onReady(function(){
	var tree = new Ext.tree.TreePanel({
		title : '员工操作',
		rootVisible : false,
		useArrows : true,
		autoScroll : true,
		animate : true,
		enableDD : true,
		containerScroll : true,
		border : false,
		width : 200,
		dataUrl : FNS.projName + '/menu.action',
		root : {
			expanded : true,
			nodeType : 'async',
			text : 'Ext JS',
			draggable : false,
			id : 'source'
		},
		listeners : {
			click : function(n) {
				if(n.hasChildNodes()){
					return;
				}
				var url = n.attributes['url'];
				var curWind = Ext.get(url);
				if(curWind != null){
					curWind.show();
					return;
				}
				FNS.proportype[n.attributes['url']].createDelegate(FNS,[{
					id : url,
            		title: n.attributes['text'],
            		renderTo: rp.el
            	}])();
			}
		}
	});
	var accordion = new Ext.Panel({
		region:'west',
		margins:'5 0 5 5',
		split:true,
		width: 210,
		layout:'accordion',
		items: [tree]
	});
	var rp = new Ext.Panel({
		region:'center',
		margins:'5 5 5 0',
		cls:'empty',
		bodyStyle:'background:#f1f1f1'
	})
	new Ext.Viewport({
		layout:'border',
		items:[ accordion, rp ]
	}); 
});
/*Ext.onReady(function(){
	var tabs = new Ext.TabPanel({
		region : 'center',
        enableTabScroll:true,
		id : 'tab',
		width : 600,
		height : 300,
		frame : true
	});
	var tree = new Ext.tree.TreePanel({
		region : 'west',
		rootVisible : false,
		useArrows : true,
		autoScroll : true,
		animate : true,
		enableDD : true,
		containerScroll : true,
		border : false,
		width : 200,
		dataUrl : myScript.projName + '/menu.action',
		root : {
			expanded : true,
			nodeType : 'async',
			text : 'Ext JS',
			draggable : false,
			id : 'source'
		},
		listeners : {
			click : function(n) {
				var fn = 'myScript.EmployeeJs.' + n.attributes.url+'()';
				eval(fn);
			}
		}
	});
	var con = new Ext.Panel({
		title : 'XXX 企业后台管理系统',
		style : 'margin: 50px auto',
		width : 800,
		height : 400,
		frame : true,
		layout : 'border',
		items : [tree, tabs]
	});
	con.render(Ext.getBody());
});*/