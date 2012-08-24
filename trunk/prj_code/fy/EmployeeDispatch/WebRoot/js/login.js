function reloadcode(){//刷新验证码函数
 		var verify = document.getElementById('safecode');
 		verify.setAttribute('src', '../MyJsp.jsp?' + Math.random());
	}

Ext.onReady(function(){
	Ext.QuickTips.init();
	var myform=new Ext.FormPanel({
		width:350,
		labelWidth:100,
		frame:true,
		bodyStyle:'padding:5px 5px 5px',
		labelAlign: 'right',
		buttonAlign:'center',
		keys:[
            {
                key: [10,13],
                fn:surely
            }],
		defaults: {
            	width: 150,
            	xtype: 'textfield'	
         },
		items:[{
	            fieldLabel: '用户编号',
	            vtype: "alphanum",
				name: 'loginUser',
				allowBlank:false,
				blankText : '用户编号不能为空'
            },{
	            fieldLabel: '密码',
				name: 'loginPwd',
				inputType : 'password',
				allowBlank:false,
				blankText : '密码不能为空'
            },{
       	 		cls : 'key',
        		fieldLabel:'验证码',
     			name:'randCode',   
                id:'randCode',  
     			width:80,
     			allowBlank:false,
     			blankText:'验证码不能为空'
    		}]  
	});
	
	function surely() {
		var fo=myform.getForm();
          if(fo.isValid()){
          	var esn=fo.findField('loginUser').getValue();
//          	var esn=Ext.get()
          	var pwd=fo.findField('loginPwd').getValue();
          	var code=fo.findField('randCode').getValue();
          	
//          	alert(esn+' '+' '+pwd+' '+code);
            	Ext.Ajax.request({
            		url:'/EmpCheck/login.action',  
            		params:{'uv.esn':esn,'uv.pwd':pwd,'uv.code':code},
            		method:'POST',  
            		success:function(rs){
            			var l=Ext.util.JSON.decode(rs.responseText);
            		 	if(l['success']==true){
            		 		window.location.href='/EmpCheck/index.jsp';
            		 	}else{
//            		 		alert(l['msg']);
            		 		Ext.Msg.alert("提示",l['msg']);
            		 	}
            		}
            	});
          }
     };
        
	var win =new Ext.Window({
	    id:'regform',
	    title:'用户登录',
	    modal: true,
	    frame:true,
	    closable:true,
	    layout:'fit',
//	    resizable:false,
//	    draggable:true,//拖动
	    width:350,
	    height:180,
        items:myform,
        buttons: [{
            text: '提交',
            formBind:true,
            handler:surely
        },{
            text: '重置',
            handler : function() {   
            	myform.getForm().reset();
            }   
        }] 
    });
    win.show(this);
    var bd = Ext.getDom('randCode');
   	var bd2 = Ext.get(bd.parentNode);
//   	bd2.createChild({tag: 'img', src: '../MyJsp.jsp',align:'absbottom'});
   	 bd2.createChild([{
		 tag: 'span',
		 html: '<a onclick="reloadcode()" style="cursor: pointer;">'
		 },{
			 tag: 'img',
			 id: 'safecode',
			 src: '../MyJsp.jsp',
			 align: 'absbottom'
		 }
//		 ,{
//		 	tag: 'span',
//		 	html: '</a><font>点击图片刷新</font>'
//		}
		 ]);
		
   	
});