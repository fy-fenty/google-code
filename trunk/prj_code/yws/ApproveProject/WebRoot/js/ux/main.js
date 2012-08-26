Ext.onReady(function() {
			Ext.QuickTips.init();
			var item = new Ext.tree.TreePanel({
						title : '员工',
						useArrows : true,
						autoScroll : true,
						animate : true,
						enableDD : true,
						containerScroll : true,
						border : false,
						rootVisible : false,
						loader : new Ext.tree.TreeLoader({
									dataUrl : path + '/emp/menu.action'
								}),
						root : new Ext.tree.AsyncTreeNode({
									draggable : false,
									expanded : false
								}),
						listeners : {
							click : function(n) {
								if (n.hasChildNodes()) {
									return;
								}
								Emp.factory[n.attributes['funName']]
										.createDelegate(Emp.factory, [{
													title : n.text,
													renderTo : rp.el,
													id : n.attributes['funName']
												}])();
							}
						}
					});
			var accordion = new Ext.Panel({
						region : 'west',
						margins : '5 0 5 5',
						split : true,
						width : 210,
						layout : 'accordion',
						items : [item, {
									title : '部门经理'
								}, {
									title : "总经理"
								}]
					});
			var rp = new Ext.Panel({
						region : 'center',
						margins : '5 5 5 0',
						cls : 'empty'
					});

			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [accordion, rp]
					});

			viewport.render(Ext.getBody());
		});