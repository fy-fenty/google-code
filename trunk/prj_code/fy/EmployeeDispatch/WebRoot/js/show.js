Ext.onReady(function(){
	Ext.QuickTips.init();
	
	function addTab(fun,itemId){
        tabs.add({
        	itemId:itemId,
            title:fun,
            iconCls: 'tabs',
            items:eval(fun)(),
            closable:true
        }).show();
    }
	var Tree=Ext.tree;
	var root=new Tree.AsyncTreeNode({
		expanded: true,
		draggable: false
	});
	var treepanel=new Tree.TreePanel({
		width:208,
		animate:true,
		border:true,
		autoScroll:true,
		enableDD:true,
		containerScroll:true,
		loader:new Tree.TreeLoader({
			dataUrl:'/EmpCheck/getMenu.action'
		}),
//		collapsible : true,
		root:root,
		rootVisible:false,
		listeners:{
			click:function(node,e){
				if(node.getDepth()==2){
//					alert(node.attributes.url);
					addTab(node.attributes.url,node.id);
				}
			}
		}
	});	
	treepanel.expandAll();
		var accordion = new Ext.Panel({
			id:'west-panel',
			region:'west',
			title: '雇员功能',
			margins:'5 0 5 5',
			height:500,
			collapsible: true,
			split:true,
			width: 210,
			layout:'accordion',
			layoutConfig:{
				animate:true
			},
			items:treepanel
		});
		
		var tabs=new Ext.TabPanel({	
			region:'center',
			margins:'5 5 5 0',
			deferredRender: false,
			activeTab: 0
		});
		var panel=new Ext.Panel({
			renderTo:'emp',
			title:'功能',
			width:900,
			height:650,
			layout:'border',
			items:[accordion,tabs]
		});
		
		
/*		var viewport = new Ext.Viewport({
			layout:'border',
			items:[accordion,tabs]
		});*/
});