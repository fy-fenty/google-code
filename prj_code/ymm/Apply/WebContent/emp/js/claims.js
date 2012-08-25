Ext.onReady(function() {

	var mygrid;
	var mysend;
	var mypanel = new Ext.Panel({
				width : 800
			});
	var tabs = new Ext.TabPanel({
				resizeTabs : true, // turn on tab resizing
				minTabWidth : 115,
				tabWidth : 135,
				enableTabScroll : true,
				width : 600,
				height : 250,
				defaults : {
					autoScroll : true
				}
			});
	var tree = new Ext.tree.TreePanel({
				width : 400,
				useArrows : true,
				autoScroll : true,
				animate : true,
				enableDD : true,
				containerScroll : true,
				rootVisible : false,
				root : {
					nodeType : 'async'
				},

				dataUrl : '/Apply/gernateMenu.action',
				// auto create TreeLoader
				listeners : {
					click : function(node, checked) {
						alert(node["attributes"]["funName"]);
						if (node.leaf == true) {
							if (node.attributes["text"] == "查看报销单") {
								if (mygrid != null)
									mygrid.show();
								if (mysend != null)
									mysend.hide();
								if (mygrid == null) {
									var myfunction = node["attributes"]["funName"];
									var window = eval(myfunction);
									mygrid = window();
									mypanel.add(mygrid);
									mypanel.doLayout();
								}
							} else {
								if (mygrid != null)
									mygrid.hide();
								if (mysend != null)
									mysend.show();
								else {
									var myfunction = node["attributes"]["funName"];
									var window = eval(myfunction);
									mysend = window();
									mypanel.add(mysend);
									mypanel.doLayout();
								}
							}
						}
					}
				}
			});
	tree.getRootNode();

	var item1 = new Ext.Panel({
				title : '员工操作列表',
				items : tree
			});

	var accordion = new Ext.Panel({
				region : 'west',
				margins : '5 0 5 5',
				split : true,
				width : 210,
				layout : 'accordion',
				items : [item1]
			});

	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [accordion, {
							region : 'center',
							margins : '5 5 5 0',
							items : mypanel
						}]
			});

});
