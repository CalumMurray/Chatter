<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /><!-- Standard css style -->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,400italic' rel='stylesheet' type='text/css'>	<!-- Google Web Fonts -->
<link href='http://fonts.googleapis.com/css?family=Iceland' rel='stylesheet' type='text/css'>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script><!-- JQuery CDN -->
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script><!-- JQueryUI CDN -->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" /><!-- JQueryUI CSS -->




<script>
function validateLogin()
{
	//TODO: Move to external reusable .js script file	
	var email=document.forms["loginForm"]["email"].value;
	var filter=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;	//Email address Regular Expression
	if ( email == "")
	{
		alert("Certain fields are missing.");
		return false;
	}
	if (!filter.test(email))
	{
		alert("Malformed Email Address.");
		return false;
	}
	return true;

}
</script>

</head>

<body>


	<jsp:include page="/top-right.jsp" />

	<div id="bannerText">
		<h1>Chatter</h1>
	</div>
	
	
<!--  Login form -->

	<form class="myForm" action="${pageContext.request.contextPath}/login" method="post" onsubmit="return validateLogin()" name="loginForm">

		<div class="formtitle">Login to your account</div>
		
		<div class="input">
			<div class="inputtext">Email: </div>
			<div class="inputcontent">

				<input type="text" name="email" value="${user.email}" />

			</div>
		</div>

		<div class="input nobottomborder">
			<div class="inputtext">Password: </div>
			<div class="inputcontent">

				<input type="password" name="password" />
				<br/>
				<a href="#">Forgot password?</a>

			</div>
		</div>

		<div class="buttons">

			<input class="orangebutton" type="submit" value="Login" />

			<input class="greybutton" type="button" value="Cancel" />

		</div>

	</form>
	<!-- End Login Form -->
	
	
	<!-- Possibly display failure message -->
	<c:if test="${failureMessage != null}" >
 		<p id="returnMessage"><c:out value="${failureMessage}" /></p>
	</c:if>
	
	<p id="prompt"> Not a member? <a href="${pageContext.request.contextPath}/register">Register here</a>. </p>


	
	<jsp:include page="/footer.jsp" />
		




</body>

</html>