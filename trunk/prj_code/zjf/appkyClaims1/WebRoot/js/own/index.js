Ext.onReady(function() {

	 var store1 = new Ext.data.JsonStore({
    	autoDestroy: true,
    	url: 'appkyClaims1/select.action',
        storeId: 'myStore',
        root:"result",
        fields: [
        	{name: 'DL_ID', type: 'float'},
        	{name: 'E_SN', type: 'string'}, 
        	{name: 'MONEY', type: 'string'}, 
        	{name: 'STATUS', type: 'string'}, 
        	{name: 'CREATE_TIME', mapping: 'CREATE_TIME', type: 'date', dateFormat:'Y-m-d'}
        	]
    });
	
		
		function formatDate(value){
	         return value ? value.dateFormat('Y-m-d') : ''; 
	    }
	    
	    var grid = new Ext.grid.GridPanel({
	    	//renderTo:"x",
	    	store: store1,
	    	height:800,
	    	
    	 	columns :
    	 			[{
						name : 'DL_ID',
						mapping : 'DL_ID',
						header:"DL_ID",
						type : 'float'
					},{
						name : 'E_SN',
						mapping : 'E_SN',
						header:"E_SN",
						type : 'string'
					},{
						name : 'CREATE_TIME',
						type : 'date',
						mapping : 'CREATE_TIME',
						header:"CREATE_TIME",
						dateFormat : 'Y-m-d'
					}, {
						name : 'MONEY',
						mapping : 'MONEY',
						header:"MONEY",
						type : 'string'
					},{
						name : 'STATUS',
						mapping : 'STATUS',
						header:"STATUS",
						type : 'string'
				}]
	    });
	

		var tree = new Ext.tree.TreePanel({
					//renderTo:'tree-div',
					//title: 'My Task List',
					height : 600,
					width : 400,
					useArrows : true,
					autoScroll : true,
					animate : true,
					denableDD : true,
					containerScroll : true,
					rootVisible : false,
					frame : true,
					root : {
						nodeType : 'async'
					},
					dataUrl : 'tree.action',
					listeners : {
						"click" : show
					}
				});

		function show(node, e) {
			eval(node.attributes.url)();
		}

		var center1 = new Ext.TabPanel({
					region : 'center', // a center region is ALWAYS required for border layout
					bodyStyle : 'background:#f1f1f1'

				})

		var item1 = new Ext.Panel({
					title : '报销单',
					items : tree
				});

		var item2 = new Ext.Panel({
					title : 'Accordion Item 2',
					html : '&lt;empty panel&gt;',
					cls : 'empty'
				});

		var accordion = new Ext.Panel({
					region : 'west',
					margins : '5 0 5 5',
					split : true,
					width : 210,
					layout : 'accordion',
					items : [item1, item2]
				});

		var viewport = new Ext.Viewport({
					layout : 'border',
					items : [accordion, center1]
				});

		function empLook() {
			center1.add({
						title : 'look emp',
						iconCls : 'tabs',
						closable : true,
						itemId:'gridId',
						items :grid
					}).show();
		}

		function sendClaims() {
			alert('sendClaims');
		}
		
	 store1.load({params:{uname:'yyyy'}});
	});