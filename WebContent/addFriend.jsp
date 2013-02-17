<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chatter: Add Friend</title>

<!--  TODO: AJAX here -->


</head>
<body>

	<div class="wrapper">
	
<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />

		<form name="friendForm" method="post" action="${pageContext.request.contextPath}/add/friend">
			<input class="inputcontent" type="text" name="friend" value="Type friend's name or email..." />
			<input class="orangebutton" type="submit" value="Add" />
		</form>


	</div>
	
</body>
</html>