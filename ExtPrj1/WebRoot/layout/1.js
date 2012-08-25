Ext.onReady(function() {
			var item1 = new Ext.Panel({
						title : 'Accordion Item 1',
						html : '&lt;empty panel&gt;',
						cls : 'empty'
					});

			var item2 = new Ext.Panel({
						title : 'Accordion Item 2',
						html : '&lt;empty panel&gt;',
						cls : 'empty'
					});

			var item3 = new Ext.Panel({
						title : 'Accordion Item 3',
						html : '&lt;empty panel&gt;',
						cls : 'empty'
					});

			var item4 = new Ext.Panel({
						title : 'Accordion Item 4',
						html : '&lt;empty panel&gt;',
						cls : 'empty'
					});

			var item5 = new Ext.Panel({
						title : 'Accordion Item 5',
						html : '&lt;empty panel&gt;',
						cls : 'empty'
					});

			var accordion = new Ext.Panel({
						region : 'west',
						split : true,
						width : 210,
						layout : 'accordion',
						items : [item1, item2, item3, item4, item5]
					});

			var mp = new Ext.Panel({
						title : 'border布局',
						height : 400,
						width : 700,
						layout : 'border',
						items : [{
									title : 'North',
									collapsible : true,
									region : 'north',
									height : 50,
									html : '这个是北方',
									split : true
								}, {
									region : 'center',
									html : '这个是中心'
								}, accordion]
					});
			mp.render("d");
		});