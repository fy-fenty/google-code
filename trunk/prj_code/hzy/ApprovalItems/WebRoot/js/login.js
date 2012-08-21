var projName = '/ApprovalItems';
function reCode(e){
	var rand = Math.random();
	e.src=projName+'/resource/checkCode.jsp?pp='+rand;
}
Ext.onReady(function() {
	Ext.QuickTips.init();
	LoginWindow = Ext.extend(Ext.Window, {
		title : '登陆系统',
		width : 265,
		height : 170,
		collapsible : true,
		defaults : {
			border : false
		},
		buttonAlign : 'center',
		createFormPanel : function() {
			return new Ext.form.FormPanel({
						bodyStyle : 'padding-top:6px',
						defaultType : 'textfield',
						labelAlign : 'right',
						labelWidth : 55,
						labelPad : 0,
						frame : true,
						defaults : {
							allowBlank : false,
							width : 158
						},
						items : [{
									cls : 'user',
									name : 'lgVo.esn',
									fieldLabel : '帐号',
									blankText : '帐号不能为空'
								}, {
									cls : 'key',
									name : 'lgVo.pwd',
									fieldLabel : '密码',
									blankText : '密码不能为空',
									inputType : 'password'
								}, {
									cls : 'key',
									name : 'lgVo.checkCode',
									id : 'randCode',
									fieldLabel : '验证码',
									width : 80,
									blankText : '验证码不能为空'
								}]
					});
		},
		login : function() {
			this.fp.form.submit({
						waitMsg : '正在登录......',
						url : projName+'/login.action',
						success : function(form, action) {
							alert('login success');
						},
						failure : function(form, action) {
							form.reset();
							alert('login fail');
						}
					});
		},
		initComponent : function() {

			LoginWindow.superclass.initComponent.call(this);
			this.fp = this.createFormPanel();
			this.add(this.fp);
			this.addButton('登陆', this.login, this);
			this.addButton('重置', function() {
						this.fp.form.reset();
					}, this);

		}
	});

	Ext.onReady(function() {
				var win = new LoginWindow();

				win.show();
				var bd = Ext.getDom('randCode');
				var bd2 = Ext.get(bd.parentNode);
				bd2.createChild({
							tag : 'img onclick=reCode(this) title=\'单击更换图片\'',
							src : projName+'/resource/checkCode.jsp',
							align : 'absbottom'
						});
			});
		/*
		 * var simple = new Ext.FormPanel({ labelWidth : 75, url :
		 * 'save-form.php', frame : true, title : 'Simple Form', style :
		 * 'margin:20px auto', bodyStyle : 'padding:5px 5px 0', width : 350,
		 * defaults : { width : 230 }, defaultType : 'textfield', items : [{
		 * fieldLabel : 'No', name : 'esn', allowBlank : false }, { fieldLabel :
		 * 'Pwd', name : 'password', allowBlank : false }], buttons : [{ text :
		 * 'Login', handler : login }] }); function login(){ Ext.Ajax.request({
		 *  }) }; simple.render(Ext.getBody());
		 */
})