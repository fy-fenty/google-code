Ext.onReady(function(){
	var myData = [
    ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
    ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
    ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
    ['Hewlett-Packard Co.',36.53,-0.03,-0.08,'9/1 12:00am'],
    ['Wal-Mart Stores, Inc.',45.45,0.73,1.63,'9/1 12:00am']
];
//	var Employee=Ext.data.Record.create([
//	{ name : 'company', mapping : 1 },
//	{ name : 'price', mapping : 2 },
//	{name: 'change', type: 'float',mapping : 3},
//	 {name: 'pctChange', type: 'float',mapping : 4},
//	 {name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia',mapping : 5}
//	]);
////	
//	var mys=Ext.data.ArrayReader({idIndex: 0},Employee);
//	
//	var store=new Ext.data.Store({
//		reader:mys
//	});
//	store.loadData(myData);
	
	
	var store = new Ext.data.ArrayStore({
	    // store configs
	    autoDestroy: true,
	    storeId: 'myStore',
	    // reader configs
	    idIndex: 0,  
	    fields: [
	    	{name:'company'},
	       {name: 'price', type: 'float'},
	       {name: 'change', type: 'float'},
	       {name: 'pctChange', type: 'float'},
	       {name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}
	    ],
	    data:myData
	});
	
//	store.loadData(myData);
    
	var grid=new Ext.grid.GridPanel({
		autoHeight:true,
		store:store,
		colModel:new Ext.grid.ColumnModel({
			defaults:{
				width:120,
				sortable:true
			},
			columns:[
				{id:'sos',header:'company', width: 200, sortable: true, dataIndex: 'company'},
				{header:'Price',renderer:Ext.util.Format.usMoney,dataIndex:'price'},
				{header: 'Change',dataIndex: 'change'},
				{header: '% Change', dataIndex: 'pctChange'},
			    {
               	 	header: 'Last Updated', width: 135, dataIndex: 'lastChange',
                	xtype: 'datecolumn', format: 'M d, Y'
            	}
				]
		}),
		   viewConfig:{
             forceFit:true,
            getRowClass: function(record, index) {
            var c = record.get('change');
            if (c < 0) {
                return 'price-fall';
            } else if (c > 0) {
                return 'price-rise';
            }
        }
           },   
    autoExpandColumn: 'sos',
    width: 600,
    height: 300,
    frame: true,
    title: 'Framed with Row Selection and Horizontal Scrolling',
    iconCls: 'icon-grid',
      renderTo:'grid-example'    
	});
	
//	grid.render('grid-example');
});