<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Login</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<h1>User Login</h1>
	<form action="login" method="post">
		<table>
			<tr>
				<td>Email:</td>
				<td><input name="email" value="${ email }"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" value="${ password }"></td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="submit">Login</button>
				</td>
			</tr>
		</table>
		<div style="color:red;">${ errorMessage }</div>
	</form>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>