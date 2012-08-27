Ext.onReady(function(){
	var store = new Ext.data.Store({
	    proxy: new Ext.data.ScriptTagProxy({
	 	   url: '/ApprovalItems/employee-getDetailItem.action'
	    }),
	    reader: new Ext.data.JsonReader({
	        root: 'data',
	        totalProperty: 'totalCount',
	        id: 'post_id'
	    }, ['dlId','itemName'])
	});
	var combo = new Ext.form.ComboBox({
	    store: store,
	    displayField:'state',
	    typeAhead: true,
	    forceSelection: true,
	    triggerAction: 'all',
	    emptyText:'Select a state...',
	    selectOnFocus:true
	});
	new Ext.FormPanel({
		renderTo : Ext.getBody(),
		title : 'aaaa',
		items : [combo]
	});
})
/*
Ext.onReady(function(){
    var ds = new Ext.data.Store({
        proxy: new Ext.data.ScriptTagProxy({
            url: '/ApprovalItems/employee-getDetailItem.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount',
            id: 'post_id'
        }, ['dlId','itemName'])
    });

    // Custom rendering Template
    var resultTpl = new Ext.XTemplate(
        '<tpl for="."><div class="search-item">',
            '<h3><span>{lastPost:date("M j, Y")}<br />by {author}</span>{title}</h3>',
            '{excerpt}',
        '</div></tpl>'
    );
    
    var search = new Ext.form.ComboBox({
    	renderTo : Ext.getBody(),
        store: ds,
        displayField:'itemName',
        typeAhead: false,
        width: 570,
        pageSize:10,
        tpl: resultTpl
    });
});*/