<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" />
</head>
<body>

	<!-- Navigation Bar -->
	<ul id="navigation-bar">
	  <li><a href="${pageContext.request.contextPath}/profile">Account</a></li>
	  <li><a href="${pageContext.request.contextPath}/create/message">Message</a></li>
	  <li><a href="${pageContext.request.contextPath}/read/message">Feed</a></li>
	  <li><a href="${pageContext.request.contextPath}/create/friend">Friends</a></li>
	  <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
	</ul>



</body>
</html>
