
Ext.onReady(function(){  
    Ext.QuickTips.init();  
    Ext.form.Field.prototype.msgTarget="qtip";  
    var loginform = new Ext.form.FormPanel({  
        renderTo:'container',  
        title:'用户登录',  
        labelSeparator:":",  
        collapsible :true,  
        width:300,  
        height:160,  
        id:'login', 
        name:'login',
        labelWidth: 80,  
        bodyStyle: 'padding:5px 5px 0 0',  
        border: false,  
        frame:true,  
        defaults:{width:178,xtype:'textfield'},  
        items:[  
            new Ext.form.TextField({  
            fram:true,  
            fieldLabel:"用户名",  
            //name:'userName',  
            id:'userName',
            allowBlank:false,
            value:'xxxx1001',
            blankText:'用户名不能为空'  
        }),{  
            fieldLabel:"密码",  
            name:'password',  
            allowBlank:false,  
            inputType:'password',
            value:'123456',
            blankText:'密码不能为空'  
        },{  
            name: 'vcode',  
            id: 'vcode',  
            fieldLabel: '验证码',  
            maxLength: 4,  
            width: 80,  
            allowBlank: false,  
            blankText: '验证码不能为空!'  
                    }],  
        keys:{  
            key: 13,  
            fn:login  
        },  
        buttons:[{  
            text:'登录',  
            handler:login  
        },{  
            text:'重置',handler:function(){  
                loginform.form.reset();  
            }  
        }]  
    });
    

    Ext.onReady(function() {
		var bd = Ext.getDom('vcode');
		var bd2 = Ext.get(bd.parentNode);
		bd2.createChild({
			tag : 'img',		
			src : 'image.jsp',
			align : 'absbottom'
		});
	});
    
    function login(){  
  		var uname = loginform.getForm().findField("userName").getValue();
  		var password=loginform.getForm().findField("password").getValue();
  		var vcode=loginform.getForm().findField("vcode").getValue();
  		Ext.Ajax.request({
    		url:'appkyClaims1/login.action',
    		method: 'GET',
    		params: {'uname':uname ,'passwprd':password,'vcode':vcode},
    		callback: function(op,success,resp){
    				if(success){
    					var s= eval(resp.responseText);
    					if(s[0].success==true){
    						document.location.href = "/appkyClaims1/index.jsp";
    					}
    				}else{
    						alert("登录失败...");
    						document.location.href = "/appkyClaims1/login.html";
    					}
					
				}
				
    	});
    }  
    
});