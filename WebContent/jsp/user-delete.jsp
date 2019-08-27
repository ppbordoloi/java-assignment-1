<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Delete</title>
	<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
  <h1>User Delete:</h1>
  <form action="user-delete" method="post">
    <input type="hidden" name="id" value="${ user.id }">
	<h3>Are you sure you want to delete the User "${ user.email }"?</h3>
	<input type="submit" value="Delete" />
    <div style="color:red;">${ errorMessage }</div>
  </form>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>