var path = '/ExtPrj1';
var FNS = {
	createWin : function(cf){
		var win1 = new Ext.Window(Ext.applyIf(cf||{},{
			title: '系统窗口',
	        width: 200,
	        height: 200,
	        renderTo: Ext.getBody(),
	        minimizable: false,
	        maximizable: true,
	        constrain: true,
	        constrainHeader:true
		}))
		return win1;
	}
};
//Ext.namespace("Lux.fns");
//Lux.fns.createWin = function(cf){
//	var win1 = new Ext.Window(Ext.applyIf(cf||{},{
//		title: '系统窗口',
//        width: 200,
//        height: 200,
//        renderTo: Ext.getBody(),
//        minimizable: false,
//        maximizable: true,
//        constrain: true,
//        constrainHeader:true
//	}));
//	
//    return win1;
//}
/*
var FNS = (function(){
	var x = 0;
	return {
		age: x
	};
})();
*/