<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Edit</title>
	<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
  <h1>User Edit:</h1>
  <form action="user-edit" method="post">
    <table>
      <tr>
        <td>Id:</td>
        <td><input type="hidden" name="id" value="${ user.id }">${ user.id }</td>
      </tr>
      <tr>
        <td>Name:</td>
        <td><input name="username" value="${ user.username }"></td>
      </tr>
      <tr>
        <td>Email:</td>
        <td><input name="email" value="${ user.email }"></td>
      </tr>
      <tr>
        <td>
          <button type="submit">Update</button>
        </td>
        <td>
          <a href="<%= request.getContextPath() %>/user-list">Cancel</a>
        </td>
      </tr>
    </table>
    <div style="color:red;">${ errorMessage }</div>
  </form>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>