Ext.onReady(function(){
	//以下创建主界面
	var item = new Ext.tree.TreePanel({
		title: '员工',
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: true,
	    containerScroll: true,
	    border: false,
	    loader: new Ext.tree.TreeLoader({
	    	dataUrl: path+'/getMenu.action'
	    }),	
	    root: new Ext.tree.AsyncTreeNode({
	  		draggable: false,
	      	expanded:false
	  	}),
	  	rootVisible:false,
	  	listeners: {
            click: function(n) {
            	if(n.hasChildNodes()){
            		return;
            	}
            	if(Ext.get(n.attributes['url'])!=null){
            		return;
            	}
            	if(n.attributes['url']=='modifyDispatct'){
            		Ext.Msg.alert('提示','此功能在查询功能当中！');
            		return;
            	}
//            	alert(n.attributes['url']);
            	FNS[n.attributes['url']].createDelegate(FNS,[{
            		id:n.attributes['url'],
            		title: n.attributes['text'],
            		renderTo: rp.el
            	}])();
            }
        }
	});
	item.expandAll();
	
     var accordion = new Ext.Panel({
        region:'west',
        margins:'5 0 5 5',
        split:true,
        width: 210,
        layout:'accordion',
        items: [item,{
        	title: '部门经理',
        	html: '222222'
        },{
        	title: '总经理',
        	html: '222222'
        }]
    });
    var rp = new Ext.Panel({
        region:'center',
        id:'rpId',
        margins:'5 5 5 0',
        cls:'empty',
        bodyStyle:'background:#f1f1f1'
    });
    
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[accordion,rp]
    });
    
    viewport.render(Ext.getBody());
});
