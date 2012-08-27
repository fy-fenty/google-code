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
	    	dataUrl: path+'/tree.action'
	    }),	
	    root: new Ext.tree.AsyncTreeNode({
	  		text: '根部',
	  		draggable: false,
	      	expanded:false
	  	}),
	  	listeners: {
            click: function(n) {
            	if(n.hasChildNodes()){
            		return;
            	}
            	
            	FNS[n.attributes.url].createDelegate(FNS,[{
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
        items: [item,{
        	title: 'x2',
        	html: '222222'
        }]
    });
    var rp = new Ext.Panel({
        region:'center',
        margins:'5 5 5 0',
        cls:'empty',
        id:'rpId',
        bodyStyle:'background:#f1f1f1'
        //html:'<br/><br/>&lt;empty center panel&gt;'
    });
    
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[accordion,rp]
    });
    
    viewport.render(Ext.getBody());
});
