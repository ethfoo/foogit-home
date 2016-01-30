<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% String contextPath = request.getContextPath(); 
   
%>
<head>
<script language="javascript">

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login page</title>
</head>
<body>

	<div>
	用户信息：
	<h4>${user.email}</h4>
	</div>
	
	<div>

		<c:choose>
			<c:when test="${isSessionUserSelf == 'true'}">
				为本用户：
				<a href="<%=contextPath%>/new"><button type="button">create new project</button></a>
				
			</c:when>
			
			<c:otherwise>
				此页面非本用户：
				
			</c:otherwise>
		</c:choose>
	
	</div>
	
	<div>
		repository list:
		<c:forEach items="${repoList}" var="it">
			<a href="<%=contextPath%>/user/${user.userName}/${it.repoName}">${it.repoName}</a>
		</c:forEach>
	</div>
</body>
</html>