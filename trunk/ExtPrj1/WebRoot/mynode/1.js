var path = '/ExtWeb1';
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var tree = new Ext.tree.TreePanel({
	    useArrows: true,
	    autoScroll: true,
	    animate: true,
	    enableDD: true,
	    containerScroll: true,
	    border: false,
	    loader: new Ext.tree.TreeLoader({
	    	dataUrl: path+'/servlet/TreeServlet'
	    }),	
	    root: new Ext.tree.AsyncTreeNode({
	  		text: '根部',
	  		draggable: false,
	      	expanded:false
	  	})
	});
	var p = new Ext.Panel({
		title: '测试',
		width: 200,
		height: 500,
		renderTo: 'd',		
		layout: 'fit',
		items:[tree]
	});
	tree.expandAll();
//	tree.getRootNode().on("beforeload",function(){
//		alert("before");
//	});
//	
	var sm = tree.getSelectionModel();
    sm.on('beforeselect', function(sm, node){
    	 alert(node.isLeaf());
         return node.isLeaf();
    });
    sm.on('selectionchange', function(sm, node){
    	var str = "";
    	function x(n){
    		var arys = n.childNodes;
    		for(var i =0;i<arys.length;i++){
    			if(arys[i].isLeaf()){
    				str += arys[i].text+"-";
    			}else{
    				x(arys[i]);
    			}
    		}
    	}
    	x(tree.getRootNode());
    });
});