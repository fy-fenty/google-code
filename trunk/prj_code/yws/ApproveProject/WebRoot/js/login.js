Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var validate = new Ext.Panel({
		style : "margin: 3px 0 10px 50px",
		layout : "column",
		items : [{
					columnWidth : .66,
					layout : "form",
					labelWidth : 50,
					defaultType : "textfield",
					items : [{
								fieldLabel : "验证码",
								id : "randCode",
								name : "loginVo.randCode",
								width : 80,
								allowBlank : false,
								blankText : "验证码不能为空！",
								regex : /^[0-9]{4}$/,
								regexText : "验证码由4位数字组成！"
							}]
				}, {
					columnWidth : .3,
					items : [{
						html : "<a href='javascript:document.getElementById(\"imgcode\").src=\"../html/checkcode.jsp?id=new Date().getTime()\"'><img src='../html/checkcode.jsp' id='imgcode' /></a>",
						width : 60,
						height : 20
					}]
				}]
	});
	var win = new Ext.Window({
				title : '用户登录',
				modal : true,
				width : 400,
				collapsible : true,
				defaults : {
					border : false
				},
				keys : [{ // 键盘回车提交功能
					key : [10, 13],
					fn : function() {
						submit(form);
					}
				}],
				buttonAlign : 'center',
				items : form = new Ext.FormPanel({
							bodyStyle : 'padding-top:10px',
							defaultType : 'textfield',
							labelAlign : 'right',
							frame : true,
							defaults : {
								allowBlank : false,
								autowidth : true,
								width : 210
							},
							items : [{
										xtype : 'textfield',
										fieldLabel : '用户名',
										cls : 'username',
										id : "uname",
										name : 'loginVo.uname',
										blankText : '用户名不能为空！'
									}, {
										xtype : 'textfield',
										fieldLabel : '密　码',
										cls : 'password',
										id : "pwd",
										name : 'loginVo.pwd',
										inputType : 'password',
										blankText : '密码不能为空！'
									}, validate]
						}),
				buttons : [{
							text : "登录",
							type : "submit",
							formBind : true,// 防止恶意提交
							handler : function() {
								submit(form);
							}
						}, {
							text : "重置",
							type : 'button',
							handler : function() {
								form.getForm().reset();
							}
						}]
			});
	function submit(form) {
		if (form.form.isValid()) {
			// form.form.submit({
			// waitTitle : '登陆',
			// waitMsg : '正在登陆',
			// url : '/ApproveProject/emp/login.action',
			// method : 'post',
			// success : function() {
			// window.location.href = '/ApproveProject/html/index.html';
			// },
			// failure : function() {
			// alert(2);
			// }
			// });
			Ext.Ajax.request({
						url : '/ApproveProject/login/login.action',
						success : function(form) {
							window.location.href = '/ApproveProject/html/success.jsp';
						},
						failure : function() {
							alert(2);
						},
						method : "post",
						params : {
							"loginVo.uname" : Ext.get("uname").getValue(),
							"loginVo.pwd" : Ext.get("pwd").getValue(),
							"loginVo.randCode" : Ext.get("randCode").getValue()
						}
					})
		} else {
			Ext.MessageBox.alert("提示", "登录信息不正确!");
		}
	}
	win.show();
});