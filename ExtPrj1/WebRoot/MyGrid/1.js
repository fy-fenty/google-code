Ext.onReady(function(){
	Ext.QuickTips.init();
	
	var mydata = [
	              ['3m Co',                               71.72, 0.02,  0.03,  '2012-01-01 22:20:00'],
	              ['Alcoa Inc',                           29.01, 0.42,  1.47,  '2012-01-01 22:20:00'],
	              ['Altria Group Inc',                    83.81, 0.28,  0.34,  '2012-01-01 22:20:00'],
	              ['American Express Company',            52.55, 0.01,  0.02,  '2012-01-01 22:20:00'],
	              ['American International Group, Inc.',  64.13, 0.31,  0.49,  '2012-01-01 22:20:00'],
	              ['AT&T Inc.',                           31.61, -0.48, -1.54, '2012-01-01 22:20:00'],
	              ['Boeing Co.',                          75.43, 0.53,  0.71,  '2012-01-01 22:20:00'],
	              ['Caterpillar Inc.',                    67.27, 0.92,  1.39,  '2012-01-01 22:20:00'],
	              ['Citigroup, Inc.',                     49.37, 0.02,  0.04,  '2012-01-01 22:20:00'],
	              ['E.I. du Pont de Nemours and Company', 40.48, 0.51,  1.28,  '2012-01-01 22:20:00'],
	              ['Exxon Mobil Corp',                    68.1,  -0.43, -0.64, '2012-01-01 22:20:00'],
	              ['General Electric Company',            34.14, -0.08, -0.23, '2012-01-01 22:20:00'],
	              ['General Motors Corporation',          30.27, 1.09,  3.74,  '2012-01-01 22:20:00'],
	              ['Hewlett-Packard Co.',                 36.53, -0.03, -0.08, '2012-01-01 22:20:00'],
	              ['Honeywell Intl Inc',                  38.77, 0.05,  0.13,  '2012-01-01 22:20:00'],
	              ['Intel Corporation',                   19.88, 0.31,  1.58,  '2012-01-01 22:20:00'],
	              ['International Business Machines',     81.41, 0.44,  0.54,  '2012-01-01 22:20:00'],
	              ['Johnson & Johnson',                   64.72, 0.06,  0.09,  '2012-01-01 22:20:00'],
	              ['JP Morgan & Chase & Co',              45.73, 0.07,  0.15,  '2012-01-01 22:20:00'],
	              ['McDonald\'s Corporation',             36.76, 0.86,  2.40,  '2012-01-01 22:20:00'],
	              ['Merck & Co., Inc.',                   40.96, 0.41,  1.01,  '2012-01-01 22:20:00'],
	              ['Microsoft Corporation',               25.84, 0.14,  0.54,  '2012-01-01 22:20:00'],
	              ['Pfizer Inc',                          27.96, 0.4,   1.45,  '2012-01-01 22:20:00'],
	              ['The Coca-Cola Company',               45.07, 0.26,  0.58,  '2012-01-01 22:20:00'],
	              ['The Home Depot, Inc.',                34.64, 0.35,  1.02,  '2012-01-01 22:20:00'],
	              ['The Procter & Gamble Company',        61.91, 0.01,  0.02,  '2012-01-01 22:20:00'],
	              ['United Technologies Corporation',     63.26, 0.55,  0.88,  '2012-01-01 22:20:00'],
	              ['Verizon Communications',              35.57, 0.39,  1.11,  '2012-01-01 22:20:00'],            
	              ['Wal-Mart Stores, Inc.',               45.45, 0.73,  1.63,  '2012-01-01 22:20:00']
	          ];
	var myrender = function(val){
		return "时间:  "+val.format('Y-m-d');
		
	};
	var store = new Ext.data.ArrayStore({
        fields: [
           {name: 'company'},
           {name: 'price',      type: 'float'},
           {name: 'change',     type: 'float'},	
           {name: 'pctChange',  type: 'float'},
           					 {
        	   name: 'lastChange',
        	   type: 'date', dateFormat: 'Y-m-d H:i:s'
           					 }
        ]
    });
	store.loadData(mydata);

	var grid = new Ext.grid.GridPanel({
		store: store,
		height: 350,						
		width: 780,
		colModel: new Ext.grid.ColumnModel({
			defaults: {
				width: 120,
				header: '选项'
			},
			title: 'MyFirstArrayGrid',
			columns: [
				{id: 'ah', header: '公司', width: 200, sortable: true, dataIndex: 'company'},
				{header: '价格', dataIndex: 'price',align: 'right'},
				{header: '改变', dataIndex: 'change',align: 'right'},
				{header: '改表的百分比', dataIndex: 'pctChange'},
				{header: '最后修改时间', dataIndex: 'lastChange',width: 150,renderer : myrender}
			 ]
		})
	});
	
	grid.render("d");
});