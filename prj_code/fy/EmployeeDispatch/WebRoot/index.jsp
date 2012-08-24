<%@page import="org.fy.constant.AppConstant"%>
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
	
 	<link rel="stylesheet" type="text/css" href="css/ext-all.css">
 	 <link rel="stylesheet" type="text/css" href="css/RowEditor.css">
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/RowEditor.js"></script>
  	<script type="text/javascript" src="js/emp.js"></script>
	<script type="text/javascript" src="js/show.js"></script>
  </head>
  
  <body>
 <%--  	<%
  		if(request.getSession().getAttribute(AppConstant.CURRENT_USER)==null){
  			response.sendRedirect(request.getContextPath()+"/html/login.html");
  		}
  	 %> --%>
  	 	<div id="emp" style="margin: 0 auto; width:900px;"></div> 
  </body>
</html>
