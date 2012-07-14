Ext.onReady(function() {
	// create the Data Store
	var store = new Ext.data.JsonStore({
				root : 'topics',
				totalProperty : 'totalCount',
				idProperty : 'threadid',
				remoteSort : true,

				fields : ['title', 'forumtitle', 'forumid', 'author', {
							name : 'replycount',
							type : 'int'
						}, {
							name : 'lastpost',
							mapping : 'lastpost',
							type : 'date',
							dateFormat : 'timestamp'
						}, 'lastposter', 'excerpt'],

				// load using script tags for cross domain, if the data in on
				// the same domain as
				// this page, an HttpProxy would be better
				proxy : new Ext.data.ScriptTagProxy({
							url : 'http://extjs.com/forum/topics-browse-remote.php'
						})
			});
	store.setDefaultSort('lastpost', 'desc');
	// pluggable renders
	function renderTopic(value, p, record) {
		return String
				.format(
						'<b><a href="http://extjs.com/forum/showthread.php?t={2}" target="_blank">{0}</a></b><a href="http://extjs.com/forum/forumdisplay.php?f={3}" target="_blank">{1} Forum</a>',
						value, record.data.forumtitle, record.id,
						record.data.forumid);
	}
	function renderLast(value, p, r) {
		return String.format('{0}<br/>by {1}', value
						.dateFormat('M j, Y, g:i a'), r.data['lastposter']);
	}

	var grid = new Ext.grid.GridPanel({
				width : 700,
				height : 500,
				title : 'ExtJS.com - Browse Forums',
				store : store,
				trackMouseOver : false,
				disableSelection : true,
				loadMask : true,

				// grid columns
				columns : [{
							id : 'topic', // id assigned so we can apply
							// custom css (e.g.
							// .x-grid-col-topic b { color:#333
							// })
							header : "Topic",
							dataIndex : 'title',
							width : 420,
							renderer : renderTopic,
							sortable : true
						}, {
							header : "Author",
							dataIndex : 'author',
							width : 100,
							hidden : true,
							sortable : true
						}, {
							header : "Replies",
							dataIndex : 'replycount',
							width : 70,
							align : 'right',
							sortable : true
						}, {
							id : 'last',
							header : "Last Post",
							dataIndex : 'lastpost',
							width : 150,
							renderer : renderLast,
							sortable : true
						}],

				// customize view config
				viewConfig : {
					forceFit : true,
					enableRowBody : true,
					showPreview : true,
					getRowClass : function(record, rowIndex, p, store) {
						if (this.showPreview) {
							p.body = '<p>' + record.data.excerpt + '</p>';
							return 'x-grid3-row-expanded';
						}
						return 'x-grid3-row-collapsed';
					}
				},

				// paging bar on the bottom
				bbar : new Ext.PagingToolbar({
							pageSize : 25,
							store : store,
							displayInfo : true,
							displayMsg : 'Displaying topics {0} - {1} of {2}',
							emptyMsg : "No topics to display",
							items : ['-', {
										pressed : true,
										enableToggle : true,
										text : 'Show Preview',
										// cls : 'x-btn-text-icon details',
										toggleHandler : function(btn, pressed) {
											var view = grid.getView();
											view.showPreview = pressed;
											view.refresh();
										}
									}]
						})
			});

	// render it
	grid.render('topic-grid');

	// trigger the data store load
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});

	/*
	 * ! Ext JS Library 3.4.0 Copyright(c) 2006-2011 Sencha Inc.
	 * licensing@sencha.com http://www.sencha.com/license
	 */
	Ext.QuickTips.init();

	// NOTE: This is an example showing simple state management. During
	// development,
	// it is generally best to disable state management as dynamically-generated
	// ids
	// can change across page loads, leading to unpredictable results. The
	// developer
	// should ensure that stable state ids are set for stateful components in
	// real apps.
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

	// sample static data for the store
	var myData = [
			['3m Co', 71.72, 0.02, 0.03, '9/1 12:00am'],
			['Alcoa Inc', 29.01, 0.42, 1.47, '9/1 12:00am'],
			['Altria Group Inc', 83.81, 0.28, 0.34, '9/1 12:00am'],
			['American Express Company', 52.55, 0.01, 0.02, '9/1 12:00am'],
			['American International Group, Inc.', 64.13, 0.31, 0.49,
					'9/1 12:00am'],
			['AT&T Inc.', 31.61, -0.48, -1.54, '9/1 12:00am'],
			['Boeing Co.', 75.43, 0.53, 0.71, '9/1 12:00am'],
			['Caterpillar Inc.', 67.27, 0.92, 1.39, '9/1 12:00am'],
			['Citigroup, Inc.', 49.37, 0.02, 0.04, '9/1 12:00am'],
			['E.I. du Pont de Nemours and Company', 40.48, 0.51, 1.28,
					'9/1 12:00am'],
			['Exxon Mobil Corp', 68.1, -0.43, -0.64, '9/1 12:00am'],
			['General Electric Company', 34.14, -0.08, -0.23, '9/1 12:00am'],
			['General Motors Corporation', 30.27, 1.09, 3.74, '9/1 12:00am'],
			['Hewlett-Packard Co.', 36.53, -0.03, -0.08, '9/1 12:00am'],
			['Honeywell Intl Inc', 38.77, 0.05, 0.13, '9/1 12:00am'],
			['Intel Corporation', 19.88, 0.31, 1.58, '9/1 12:00am'],
			['International Business Machines', 81.41, 0.44, 0.54,
					'9/1 12:00am'],
			['Johnson & Johnson', 64.72, 0.06, 0.09, '9/1 12:00am'],
			['JP Morgan & Chase & Co', 45.73, 0.07, 0.15, '9/1 12:00am'],
			['McDonald\'s Corporation', 36.76, 0.86, 2.40, '9/1 12:00am'],
			['Merck & Co., Inc.', 40.96, 0.41, 1.01, '9/1 12:00am'],
			['Microsoft Corporation', 25.84, 0.14, 0.54, '9/1 12:00am'],
			['Pfizer Inc', 27.96, 0.4, 1.45, '9/1 12:00am'],
			['The Coca-Cola Company', 45.07, 0.26, 0.58, '9/1 12:00am'],
			['The Home Depot, Inc.', 34.64, 0.35, 1.02, '9/1 12:00am'],
			['The Procter & Gamble Company', 61.91, 0.01, 0.02, '9/1 12:00am'],
			['United Technologies Corporation', 63.26, 0.55, 0.88,
					'9/1 12:00am'],
			['Verizon Communications', 35.57, 0.39, 1.11, '9/1 12:00am'],
			['Wal-Mart Stores, Inc.', 45.45, 0.73, 1.63, '9/1 12:00am']];

	/**
	 * Custom function used for column renderer
	 * 
	 * @param {Object}
	 *            val
	 */
	function change(val) {
		if (val > 0) {
			return '<span style="color:green;">' + val + '</span>';
		} else if (val < 0) {
			return '<span style="color:red;">' + val + '</span>';
		}
		return val;
	}

	/**
	 * Custom function used for column renderer
	 * 
	 * @param {Object}
	 *            val
	 */
	function pctChange(val) {
		if (val > 0) {
			return '<span style="color:green;">' + val + '%</span>';
		} else if (val < 0) {
			return '<span style="color:red;">' + val + '%</span>';
		}
		return val;
	}

	// create the data store
	var store = new Ext.data.ArrayStore({
				fields : [{
							name : 'company'
						}, {
							name : 'price',
							type : 'float'
						}, {
							name : 'change',
							type : 'float'
						}, {
							name : 'pctChange',
							type : 'float'
						}, {
							name : 'lastChange',
							type : 'date',
							dateFormat : 'n/j h:ia'
						}]
			});

	// manually load local data
	store.loadData(myData);

	// create the Grid
	var grid = new Ext.grid.GridPanel({
		store : store,
		columns : [{
					id : 'company',
					header : 'Company',
					width : 160,
					sortable : true,
					dataIndex : 'company'
				}, {
					header : 'Price',
					width : 75,
					sortable : true,
					renderer : 'usMoney',
					dataIndex : 'price'
				}, {
					header : 'Change',
					width : 75,
					sortable : true,
					renderer : change,
					dataIndex : 'change'
				}, {
					header : '% Change',
					width : 75,
					sortable : true,
					renderer : pctChange,
					dataIndex : 'pctChange'
				}, {
					header : 'Last Updated',
					width : 85,
					sortable : true,
					renderer : Ext.util.Format.dateRenderer('m/d/Y'),
					dataIndex : 'lastChange'
				}, {
					xtype : 'actioncolumn',
					width : 50,
					items : [{
						icon : '../../ext-3.4.0/examples/shared/icons/fam/delete.gif', // Use
						// a
						// URL
						// in
						// the icon
						// config
						tooltip : 'Sell stock',
						handler : function(grid, rowIndex, colIndex) {
							var rec = store.getAt(rowIndex);
							alert("Sell " + rec.get('company'));
						}
					}, {
						getClass : function(v, meta, rec) { // Or
							// return
							// a
							// class from a
							// function
							if (rec.get('change') < 0) {
								this.items[1].tooltip = 'Do not buy!';
								return 'alert-col';
							} else {
								this.items[1].tooltip = 'Buy stock';
								return 'buy-col';
							}
						},
						handler : function(grid, rowIndex, colIndex) {
							var rec = store.getAt(rowIndex);
							alert(1);
							alert("Buy " + rec.get('company'));
						}
					}]
				}],
		stripeRows : true,
		autoExpandColumn : 'company',
		height : 350,
		width : 600,
		title : 'Array Grid',
		// config options for stateful behavior
		stateful : true,
		stateId : 'grid'
	});

	// render the grid to the specified div in the page
	grid.render('grid-example');

	
	
	
	
	
	
	

});
