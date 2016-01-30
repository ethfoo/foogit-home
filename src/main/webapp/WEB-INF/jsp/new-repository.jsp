<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <script language="javascript">
 
 //动态改变form的action url值，添加repositoryName
  function changeAction(){
  var repositoryName = document.getElementById("repoName").value;
  var oldUrl = document.getElementById("createNewRepoForm").action;
  document.getElementById("createNewRepoForm").action=oldUrl+repositoryName;
  }
  </script>
</head>
<body>
<h2>create new project</h2>

<form id="createNewRepoForm" action="<%=request.getContextPath()%>/user/${user.userName}/" method="post" onSubmit="changeAction();">
		<h5>owner:</h5>
	<h5>${user.userName}/</h5>
	<h5>repository name:</h5>
	<h5><input type="text" id="repoName" name="repositoryName" value="${repositoryName}"></h5>

	<input type="radio" name="isPrivate" value="false" checked="checked">public<br>
	<input type="radio" name="isPrivate" value="true">private
	<div>
		<input type="submit" value="确定">
	</div>
	
</form>


</body>
</html>