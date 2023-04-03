<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/lib/md5/md5a.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/lib/aes/aes.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/lib/aes/pad-zeropadding-min.js"></script>
<script type="text/javascript">
	function checkForm() {
		var pwdipt = document.getElementById("pwd")
		var pwdvalue = pwdipt.value;
		//第一次加密密码
		pwdvalue = md5(pwdvalue);

		//加密后的密码进行第二次加密（可以java解密的加密）
		var uuidsalt = document.getElementById("uuidsalt").value;
		pwdvalue = encrypt(pwdvalue, uuidsalt, uuidsalt);
		pwdipt.value = pwdvalue;
		alert(pwdvalue);
		if (pwdipt.value.length == 44) { //md5加密后的密文为32位,第二加密后变为44位了
			return true;
		} else {
			return false; //表单提交不起作用
		}
	}

	//aes加密的一个加密方法
	function encrypt(data, key, iv) { //key,iv偏移量-一般与key相同:16位的字符串
		var key1 = CryptoJS.enc.Latin1.parse(key);
		var iv1 = CryptoJS.enc.Latin1.parse(iv);
		return CryptoJS.AES.encrypt(data, key1, {
			iv : iv1,
			mode : CryptoJS.mode.CBC,
			padding : CryptoJS.pad.ZeroPadding
		}).toString();
	}
</script>
</head>
<body>
	<input id="uuidsalt" type="hidden" value="${uuidsalt}"> 登陆页面
	login.jsp
	<br />
	<br />
	<form action="${pageContext.request.contextPath}/login.html"
		method="post" onsubmit="return checkForm()">
		用户名称:<input type="text" name="username"><br />
		<br /> 密码:<input type="password" name="password" id="pwd"><br />
		<input type="checkbox" name="rememberme" value="1">记住我1天
		<br /> <input type="submit" value="登录">
	</form>
	
	
	
	
	
	
	
	<%-- <shiro:guest>
	   游客访问<a href="login.jsp">登陆</a>
	</shiro:guest> --%>
</body>
</html>