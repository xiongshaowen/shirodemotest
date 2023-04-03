<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    login.jsp---->登陆成功后的主页面main.jsp
    <br><br>
    <a href="${pageContext.request.contextPath}/logout.html">退出登陆</a>
    <br>
	<br>
	<a href="${pageContext.request.contextPath}/admin/userlist.html">用户列表</a>
	<br>
	<br>
	<a href="${pageContext.request.contextPath}/admin/adduser.html">添加用户</a>
	<br><br>
	<a href="${pageContext.request.contextPath}/test.html">测试页面不在/admin下</a>
	
	
	<shiro:guest>
	   游客访问<a href="login.jsp">登陆</a>
	</shiro:guest>
</body>
</html>