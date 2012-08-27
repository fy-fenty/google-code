var path = '/EmpCheck';
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
	},
	renderStatus:function(value){
	        if(value==null){return "草稿"
	        }else if(value==1){return "待审批";
	        }else if(value==2){return "已审批";}else if(value==3){return '已终止';}
	        else if(value==4){return "已打回";}else if(value==5){return "待付款";}else if(value==6)
	        {return "已付款";}
	 },
	formatDate:function(value){
	        return value ? value.dateFormat('Y-m-d H:i:s') : '';
	  },
	 renderItem:function(value){
	 		if(value==1){return "住宿费";
	        }else if(value==2){return "餐饮费";}else if(value==3){return '通讯费';}
	 },
	cbo:function(cm){
		var cstrore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url: path+'/showCombo.action',
							method:'POST'
						}),  
				reader:new Ext.data.JsonReader({
							idProperty:'DL_ID',
							root:'result',
							totalProperty:'totalCount',
							fields:[
								{name: 'deId', mapping: 'DE_ID'},
								{name: 'itemName', mapping: 'ITEM_NAME'}
				]})
			});
		 var cbx=new Ext.form.ComboBox(Ext.applyIf(cm||{},{
				minChars:1,
				blankText : '请选择',
				typeAhead: true,
				hiddenName:'deId',
				listWidth:113, 
				pageSize:5,
				loadingText: 'Searching...',
				valueField:'deId',
				typeAhead:true,
				mode:'remote',	
				forceSelection:true,
				emptyText:'this is null',
				selectOnFocus:true,
				triggerAction: 'all',
				displayField:'itemName', 
				store:cstrore,
				listeners:{
	         	select:function(combo,record,index){
//	              alert(combo.value);
	         	} 
	     	} 
		}));
		return cbx;
	 }
};