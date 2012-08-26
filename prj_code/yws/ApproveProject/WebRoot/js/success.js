Ext.onReady(function() {
	Ext.QuickTips.init();

	var tabs = new Ext.TabPanel({
				id : "disTab",
				autoTabs : true,
				autoShow : true,
				activeItem : 0,
				resizeTabs : true,
				minTabWidth : 118,
				tabWidth : 130,
				enableTabScroll : true,
				width : 641,
				height : 500,
				defaults : {
					autoScroll : true
				},
				plugins : new Ext.ux.TabCloseMenu()
			});

	tabs.on('beforedestroy', function(tab) {
				tab.hide();
				return false;
			});
	function addTab(id, tabName, obj) {
		tabs.add({
					title : "<span ext:qtip='" + tabName + "'>" + tabName
							+ "</span>",
					iconCls : 'tabs',
					id : id,
					closable : true,
					items : [obj]
				}).show();
		tabs.doLayout();
	}

	var panel = new Ext.Panel({
		style : "margin:50 auto",
		width : 850,
		height : 500,
		layout : 'border',
		defaults : {
			split : true
		},
		items : [{
			region : 'west',
			title : '菜单',
			width : 200,
			collapsible : true,
			xtype : "panel",
			minSize : 150,
			layout : "fit",
			border : false,
			maxSize : 300,
			items : [new Ext.Panel({
				width : 200,
				height : 300,
				border : false,
				layout : 'accordion',
				items : [{
					tools : [{
								type : 'gear',
								handler : function() {
									Ext.Msg.alert('提示', '点击。');
								}
							}, {
								type : 'refresh'
							}],
					items : [tree = new Ext.tree.TreePanel({
						layout : "fit",
						border : false,
						height : 500,
						id : 'menu-tree',
						title : 'Online Users',
						rootVisible : false,
						lines : false,
						autoScroll : true,
						tools : [{
							id : 'refresh',
							on : {
								click : function() {
									var tree = Ext.getCmp('menu-tree');
									tree.body.mask('Loading', 'x-mask-loading');
									tree.root.reload();
									tree.root.collapse(true, false);
									setTimeout(function() {
												tree.body.unmask();
												tree.root.expand(true, true);
											}, 1000);
								}
							}
						}],
						loader : new Ext.tree.TreeLoader({
									dataUrl : "/ApproveProject/emp/menu.action"
								}),
						root : new Ext.tree.AsyncTreeNode(),
						listeners : {
							click : function(n) {
								if (n.getDepth() == 2) {
									var grid = eval(n.attributes.funName + "()");
									if (!grid.panel) {
										return;
									}
									addTab(n.attributes.funName, n.text,
											grid.panel);
								}
							},
							beforeload : function(n) {
							}
						}
					})]
				}]
			})]
		}, {
			closable : true,
			region : 'center',
			xtype : "panel",
			layout : "fit",
			items : [tabs]
		}]
	});
	tree.expandAll();
	panel.render("menu");
});