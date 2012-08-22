Ext.onReady(function() {
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
											id : 'username',
											name : 'uservo.ESn',
											fieldLabel : '帐号',
											blankText : '帐号不能为空',
											allowBlank : false
										}, {
											cls : 'key',
											id : 'userpwd',
											name : 'uservo.UPwd',
											fieldLabel : '密码',
											blankText : '密码不能为空',
											inputType : 'password',
											allowBlank : false
										}, {
											cls : 'key',
											name : 'uservo.core',
											id : 'randCode',
											fieldLabel : '验证码',
											width : 80,
											blankText : '验证码不能为空',
											allowBlank : false
										}]
							});
				},
				login : function() {
					Ext.Ajax.request({
								url : '/ApplyDispatch1/login.action',
								success : function(response){
									alert(response.responseText);
								},
								failure : function(){
								
								},
								headers : {
									'my-header' : 'foo'
								},
								params : {
									"uservo.ESn": Ext.get('username').getValue(),
									"uservo.UPwd":Ext.get('userpwd').getValue(),
									"uservo.core":Ext.get('randCode').getValue()
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
		bd2.createChild([{
					tag : 'span',
					html : '<a onclick="reloadcode()" style="cursor: pointer;">'
				}, {
					tag : 'img',
					id : 'safecode',
					src : 'images/core.jsp',
					align : 'absbottom'
				}, {
					tag : 'span',
					html : '</a>'
				}]);

	});
});
function reloadcode() {// 刷新验证码函数
	var verify = document.getElementById('safecode');
	verify.setAttribute('src', 'images/core.jsp?' + Math.random());
}
