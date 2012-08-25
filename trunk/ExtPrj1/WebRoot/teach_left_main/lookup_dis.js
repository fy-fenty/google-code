FNS.lookup_dis = function(){
	var o={},a=0,c=3;
	
	var m = FNS.createWin(Ext.apply({
		width: 400,
		maximizable: false
	},arguments[0]));
	
	m.show();
	
	m.on('beforeclose',function(){
		o=a=c=null;		
	});
}