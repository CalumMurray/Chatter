<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chatter Profile</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" />
</head>

<body>

	<div class="wrapper">

		<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />
		
		<!--  content -->
		<h1>Login Successful!</h1>
		<h3> 
		Welcome, ${user.firstName} 
		</h3>
		<h2> Insert profile here... </h2>


	</div>



</body>

</html>