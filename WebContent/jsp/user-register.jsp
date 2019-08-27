<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<h1>User Registration</h1>
	<form action="user-register" method="post">
		<table>
			<tr>
				<td>Name:</td>
				<td><input name="name" value="${ name }"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input name="email" value="${ email }"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" value="${ password }"></td>
			</tr>
			<tr>
				<td>Confirm Password:</td>
				<td><input type="password" name="confirmPassword" value="${ confirmPassword }"></td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="submit">Register</button>
				</td>
			</tr>
		</table>
		<div style="color:red;">${ errorMessage }</div>
	</form>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>