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
repository home


${isSessionUserSelf}

<div>
	<h6>branch list:</h6>
	<ul>
		<c:forEach items="${branchList}" var="li">
			<li><a href="<%=pathContext %>${urlMiddle}/tree/${li}">${li}</a></li>
		</c:forEach>
	</ul>
</div>


<div>
	repository:
	<c:choose>
		<c:when test="${isRepoEmpty == 'true' }">
			<div>
				<h5>repository is empty</h5>
			</div>
		</c:when>
		
		<c:otherwise>
			<div>
				<c:forEach items="${pathItemList}" var="it">
					<c:choose>
						<c:when test="${it.file == 'true'}">
							<div>--is file--><a href="<%=pathContext %>${urlMiddle}/blob/master/${it.path}">${it.name }</a></div>
						</c:when>
						<c:otherwise>
							<div>--is path--><a href="<%=pathContext %>${urlMiddle}/tree/master/${it.path}">${it.name }</a></div>
						</c:otherwise>
					</c:choose>
	
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
	
	
	


	
</div>
</body>
</html>