var path = '/ExtPrj1';
Ext.onReady(function(){
	var FNS = {
		testWin: function(n){
			var win1 = new Ext.Window({
		        title: n['text']||'系统窗口',
		        width: 300,
		        height: 200,
		        manager: windows,
		        minimizable: true,
		        maximizable: true,
		        constrain: true,
		        renderTo: rp.el,
		        constrainHeader:true
		    });
		    win1.show();
		}
	};
	
    var windows = new Ext.WindowGroup();
	
	var item4 = new Ext.tree.TreePanel({
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: true,
	    containerScroll: true,
	    border: false,
	    loader: new Ext.tree.TreeLoader({
	    	dataUrl: path+'/servlet/MenuTree'
	    }),	
	    root: new Ext.tree.AsyncTreeNode({
	  		text: '根部',
	  		draggable: false,
	      	expanded:false
	  	}),
	  	listeners: {
            click: function(n) {
//              Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
            	FNS[n.attributes['myfn_name']].createDelegate(viewport,[n])();
            }
        }
	});

    var item5 = new Ext.Panel({
        title: 'Accordion Item 5',
        html: '&lt;empty panel&gt;',
        cls:'empty'
    });
	var accordion = new Ext.Panel({
        region:'west',
        margins:'5 0 5 5',
        split:true,
        width: 210,
        layout:'accordion',
        items: [item4,item5]
    });
    var rp = new Ext.Panel({
			region:'center',
			html: 'empty1111111111111'
		});
	var viewport = new Ext.Viewport({
        layout:'border',
        items:[accordion, rp]
    });

	viewport.render(Ext.getBody());
});
