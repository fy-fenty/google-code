var path = '/ExtPrj1';
Ext.onReady(function(){
	var myReader = new Ext.data.JsonReader( {
		totalProperty: 'total_num',
	    root: 'rows',
	    idProperty: 'id',
	    fields : [{
	    	name: 'mid',
	    	type: 'string',
	    	mapping: 'id'
	    },{
	    	name: 'mname',
	    	type: 'string',
	    	mapping: 'firstname'
	    },{
	    	name: 'mx',
	    	type: 'string',
	    	mapping: 'occupation'
	    }]
	});
	
//	Ext.data.Api.actions.read = "abc";
	var myStore = new Ext.data.Store({
//		url : path + '/servlet/MyServlet1',
		reader: myReader,
		proxy : new Ext.data.HttpProxy({
			method: "POST",
			url : path + '/servlet/MyServlet1'
		}) 
	});
	
	myStore.load({
		params: {"x":99},
		callback: function(rs,ops,success){
			alert(myStore.getCount());	
//			alert(myStore.getTotalCount());
		}
	});
	
	
});