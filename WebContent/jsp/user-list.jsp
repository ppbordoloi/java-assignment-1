<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User List</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>

	<h1>User List</h1>
	<form action="user-list" method="post">
		<div style="margin-bottom:20px">
			
			<label for="search">Search by UserName or User ID:</label>
			<input id="search" type="text" name="search" >
	
			<input type="submit" value="Search" />
			<span style="padding-left:50px"><a href="<%= request.getContextPath() %>/user-register">Register New User</a></span>
		</div>
		<table class="zdp-data-grid">
			<thead>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty userList}">
		            <tr><td colspan="4">No User available!</td></tr>
			    </c:if>
				<c:if test="${not empty userList}">
		        <c:forEach var="user" items="${userList}">
		            <tr>
		            	<td>${user.id}</td>
		            	<td>${user.username}</td>
		            	<td>${user.email}<c:if test="${ user.id == 1 }"> (Admin)</c:if></td>
		            	<td>
		            		<c:if test="${ user.id != 1 }">
		            			<a href="user-edit/${user.id}">Edit</a> | <a href="user-delete/${user.id}">Delete</a>
		            		</c:if>
		            	</td>
		            </tr>
		        </c:forEach>
			    </c:if>
			</tbody>
		</table>

		<div style="color:red;">${ errorMessage }</div>
		<div style="color:green;">${ successMessage }</div>

	</form>
	<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>