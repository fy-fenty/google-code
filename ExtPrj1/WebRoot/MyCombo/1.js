var path = '/ExtPrj1';
Ext.onReady(function() {
	Ext.QuickTips.init();
	var myReader = new Ext.data.JsonReader( {
		idProperty : 'id',
		root : 'rows',
		totalProperty : 'total_num',
		fields : [ {
			name : 'mid',
			mapping : 'id'
		}, {
			name : 'mname',
			mapping : 'firstname'
		}, {
			name : 'job',
			mapping : 'occupation'
		} ]
	});

	Ext.data.Api.actions['read'] = 'load';
	var myStore = new Ext.data.Store( {
		reader : myReader,
		proxy : new Ext.data.HttpProxy( {
			api : {
				'load' : {
					'method' : 'POST',
					url : path + '/servlet/MyServlet1'
				}
			}
		})
	});
	/*
	myStore.load({
		callback: function(){
			
		}
	});
	*/
	var combo = new Ext.form.ComboBox({
		renderTo: 'd',
		minChars: 3,
		listWidth: 220, 
        typeAhead: true, //是否把选中的内容替代到text框中
        forceSelection: true, 
        triggerAction: 'all', //点击 triggerBtn 的时候,再查询还是列出所有(local一般是all)
        mode: 'remote',
        valueField: 'mid',
        displayField: 'job',
        editable : false,
        resizable: true,
        pageSize: 2,
        editable: true,
        store: myStore
	});
	
	combo.on("select",function(self,record,index){
		
		Ext.MessageBox.alert('提示', record.data["mid"]+"-"+record.data["mname"]);
		
	});
	
//	myStore.loadData(d);
});