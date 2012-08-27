Ext.onReady(function() {

	 var store1 = new Ext.data.JsonStore({
    	autoDestroy: true,
    	url: 'appkyClaims1/select.action',
        storeId: 'myStore',
        root:"result", 
        totalProperty:"totalCount",//配置分页
        fields: [
        	{name: 'DL_ID', type: 'float'},
        	{name: 'E_SN', type: 'string'}, 
        	{name: 'MONEY', type: 'string'}, 
        	{name: 'CREATE_TIME', mapping: 'CREATE_TIME', type: 'date', dateFormat:'Y-m-d'},
        	{name: 'STATUS', type: 'string'}
        	]
     });
	
		
		function formatDate(value){
	         return value ? value.dateFormat('Y-m-d') : ''; 
	    }
	    
	    var grid = new Ext.grid.GridPanel({
	    	//renderTo:"x",
	    	store: store1,
	    	height:400,
	    	width:505,
	    	loadMask: true,//加载画面
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
				}], 
				bbar: new Ext.PagingToolbar({
	           		pageSize: 3,
	            	store: store1,
	            	width:505,
	            	height:30,
	            	displayInfo: true,
	            	plugins: new Ext.ux.ProgressBarPager()
	        	})
	    });
	
	   
	    var top = new Ext.FormPanel({
        labelAlign: 'top',
        frame:true,
        title: 'Multi Column, Nested Layouts and Anchoring',
        bodyStyle:'padding:5px 5px 0',
        width: 600,
        items: [{
            layout:'column',
            items:[{
                columnWidth:.5,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'E_SN',
                    name: 'first',
                    anchor:'95%'
                }, {
                    xtype:'textfield',
                    fieldLabel: 'Company',
                    name: 'company',
                    anchor:'95%'
                }]
            },{
                columnWidth:.5,
                layout: 'form',
                items: [{
                    xtype:'textfield',
                    fieldLabel: 'Last Name',
                    name: 'last',
                    anchor:'95%'
                },{
                    xtype:'textfield',
                    fieldLabel: 'Email',
                    name: 'email',
                    vtype:'email',
                    anchor:'95%'
                }]
            }]
        },{
            xtype:'htmleditor',
            id:'bio',
            fieldLabel:'EVENT_EXPLAIN',
            height:200,
            anchor:'98%'
        }],

        buttons: [{
            text: 'Save'
        },{
            text: 'Cancel'
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
					dataUrl : 'appkyClaims1/tree.action',
					listeners : {
						"click" : show //click事件
					}
				});

		function show(node, e) {
			eval(node.attributes.url)();//转换成函数
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
						itemId:'lookId',
						items :grid
					}).show();
		}

		function sendClaims() {
			center1.add({title : 'sendClaims',
						iconCls : 'tabs',
						closable : true,
						itemId:'sendClaimsId',
						items :top}).show();
		}
		
	store1.load({params:{start:0, limit:3}});	
	
	});