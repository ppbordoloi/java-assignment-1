<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home page</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<h3>Welcome to User Management Application</h3>
	<a href="login">Login</a>|<a href="user-register">Register</a>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>