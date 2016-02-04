<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>        
<% String pathContext = request.getContextPath();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
repository compare

<form>
	base:
	<select name="base">
		<c:forEach items="${upAndDown}" var="stream">
			<option>${stream}</option>
		</c:forEach>
	</select>
</form>

<form>
	head:
	<select name="head">
		<c:forEach items="${upAndDown}" var="stream">
			<option>${stream}</option>
		</c:forEach>
	</select>
</form>


	
	


	

</body>
</html>