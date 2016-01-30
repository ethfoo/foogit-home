<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login page</title>
</head>
<body>

<h3>login page</h3>

<div>
	<h4>登陆框</h4>
	<form action="login" method="post">
		<div>
			<label>用户名：</label><input type="text" name="userName" value="${userName}" />
		</div>
		<div>
			<label>密码：</label><input type="password" name="password" value="${password}" />
		</div>
		<div>
			<input type="submit" name="loginBtn" value="登陆" />
		</div>
	</form>
	
	
</div>

<div>
	<h4>注册框</h4>
	<form action="register" method="post">
		<div>
			<label>用户名：</label><input type="text" name="userName" value="${userName}" />
		</div>
		<div>
			<label>密码：</label><input type="password" name="password" value="${password}" />
		</div>
		<div>
			<label>邮箱：</label><input type="text" name="email" value="${email}" />
		</div>
		<div>
			<input type="submit" name="registerBtn" value="注册" />
		</div>
	</form>
	
	</form>
</div>

</body>
</html>