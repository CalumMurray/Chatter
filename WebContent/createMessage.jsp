<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" type="text/css" />
<title>Chatter: Create Message</title>
</head>
<body>

	<div class="wrapper">

		<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />
		
		<!-- content -->
	
		<form name="messageForm" method="post" action="${pageContext.request.contextPath}/create/message">
			<input class="inputcontent" type="text" name="message" value="Type your chat here..." />
			<input class="orangebutton" type="submit" value="Post Chat" />
		</form>
		
		<p>
			<!-- Possibly display success message -->
			<jsp:scriptlet> String message = (String)request.getAttribute("successMessage");
				if (message != null)
					out.println(message);
			</jsp:scriptlet>
		</p>  
		
	</div>
	
</body>
</html>