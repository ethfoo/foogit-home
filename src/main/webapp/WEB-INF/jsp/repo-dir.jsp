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

	<c:forEach items="${pathItemList}" var="it">
		<c:choose>
			<c:when test="${it.file == 'true'}">
				<div>--is file--><a href="<%=pathContext %>${urlMiddle}/blob/${branch }/${it.path}">${it.name }</a></div>
			</c:when>
			<c:otherwise>
				<div>--is path--><a href="<%=pathContext %>${urlMiddle}/tree/${branch }/${it.path}">${it.name }</a></div>
			</c:otherwise>
		</c:choose>
	
	</c:forEach>

</body>
</html>