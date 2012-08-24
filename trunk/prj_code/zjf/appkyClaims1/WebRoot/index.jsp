<%@page import="org.zjf.vo.CurrentUser"%>
<%@page import="org.zjf.util.SessionListener"%>
<%@page import="org.zjf.constant.MyConstant"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	 <link rel="stylesheet" href="css/base-extjs-css/ext-all.css" type="text/css"></link>
	 
	<script type="text/javascript" src="js/base-extjs/ext-base.js"></script>
	<script type="text/javascript" src="js/base-extjs/ext-all-debug.js"></script>
	<script type="text/javascript" src="js/own/index.js"></script>

  </head>

   <% 
  		if(session.getAttribute(MyConstant.CURRENT_USER)==null){
  			/* alert("已经被登录,请重新登陆"); */
  			/* window.location.href="/appkyClaims1/login.html"; */
  			
  			response.sendRedirect("/appkyClaims1/login.html");
  		}
	%>
  
  <body>
   	<div id="x"></div>
  </body>
</html>
