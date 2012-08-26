<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.han.constant.AppConstant"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	if (request.getSession().getAttribute(AppConstant.CURRENT_USER) == null) {
		out.print("<script>");
		out.print("alert('非法跳转，请先登录!');");
		out
				.print("window.location.href='/ApproveProject/html/login.html'");
		out.print("</script>");
		return;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'success.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css"
			href="../ext-3.4.0/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css"
			href="/ApproveProject/css/success.css" />
		<link rel="stylesheet" type="text/css"
			href="/ApproveProject/css/RowEditor.css" />
		<link rel="stylesheet" type="text/css"
			href="/ApproveProject/css/fileuploadfield.css" />
		<script type="text/javascript"
			src="../ext-3.4.0/adapter/ext/ext-base.js">
</script>
		<script type="text/javascript" src="../ext-3.4.0/ext-all.js">
</script>
		<script type="text/javascript"
			src="/ApproveProject/js/ux/FileUploadField.js">
</script>
		<script type="text/javascript"
			src="/ApproveProject/js/ux/RowEditor.js">
</script>
		<script type="text/javascript" src="/ApproveProject/js/ux/App.js">
</script>
		<script type="text/javascript" src="/ApproveProject/js/emp/base.js">
</script>
		<script type="text/javascript"
			src="/ApproveProject/js/ux/RightMenu.js">
</script>
		<script type="text/javascript" src="/ApproveProject/js/emp/lookdis.js">
</script>
		<script type="text/javascript"
			src="/ApproveProject/js/emp/createdis.js">
</script>
		<script type="text/javascript"
			src="/ApproveProject/js/emp/modifydis.js">
</script>
		<script type="text/javascript" src="/ApproveProject/js/ux/main.js">
</script>
	</head>

	<body>
	</body>
</html>
