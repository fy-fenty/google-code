Ext.QuickTips.init();
LoginWindow=Ext.extend(Ext.Window,{
   title : '登陆系统',
   width : 275,
   height : 155,
   collapsible : true,
   defaults : {
        border : false
   },

   buttonAlign : 'center',

   createFormPanel :function() {

        //表单重置函数
        function reset(){
            myform.form.reset();
        };

        //表单提交函数
        function surely() {
            if(myform.getForm().isValid())
            {
                myform.form.submit({
                    waitMsg : '正在登录......',
                    url : '../../../index/login',
                    timeout: 3000,
                    success : function(form, action) {

                        if(action.result.type == 0)//OP
                        window.location.href = '../op/index.html';
                        else//CP
                        window.location.href = 'index.html';

                    },
                    failure : function(form, action) {
                        form.reset();
                        if (action.failureType == Ext.form.Action.SERVER_INVALID)
                        Ext.MessageBox.alert('警告', action.result.errors.msg);
                    }
                });
            }
        };

        var myform = new Ext.form.FormPanel( {
            bodyStyle : 'padding-top:6px',
            defaultType : 'textfield',
            labelAlign : 'right',
            labelWidth : 55,
            labelPad : 2,
            //frame : true,
            method:'POST',
            //增加表单键盘事件
           keys:[
            {
                key: [10,13],
                fn:surely
            } ],
            defaults : {
                allowBlank : false,
                width : 158
            },
            items : [{
                cls : 'user',
                name : 'username',
                fieldLabel : '帐 号',
                blankText : '帐号不能为空'
            }, {
                cls : 'key',
                name : 'password',
                fieldLabel : '密 码',
                blankText : '密码不能为空',
                inputType : 'password'
            }, {
                cls : 'key',
                name:'randCode',
                id:'randCode',
                fieldLabel:'验证码',
                width:70,
                blankText : '验证码不能为空'
            }],
            buttons:[
            {
                text:'确定',
                id:'sure',
                handler:surely
            },
            {
                text:'重置',
                id:'clear',
                handler:reset
            }]
        });
        return myform;
   },

   initComponent : function(){

        LoginWindow.superclass.initComponent.call(this);
        this.fp=this.createFormPanel();
        this.add(this.fp);

   }
});


Ext.onReady(function()
{
   var win=new LoginWindow();

   win.show();
   var bd = Ext.getDom('randCode');
   var bd2 = Ext.get(bd.parentNode);
   bd2.createChild({tag: 'img', src: 'code.php',align:'absbottom'});

}
);