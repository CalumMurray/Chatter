<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Goodbye</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,400italic' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Iceland' rel='stylesheet' type='text/css'>

</head>
<body>

	<jsp:include page="/top-right.jsp" />

	<h1 id="bannerText">Chatter</h1>
	
	
	<div class="wrapper">
	
<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />
	
		<h1> Successfully Logged Out. </h1>
		<h2>Come back soon </h2>

		<jsp:include page="/footer.jsp" />

		
	</div>


</body>
</html>