<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" />


<!-- JQuery CDN -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js">
</script>

<script> 
$(document).ready(function(){
  $("button").click(function(){
    $("input").hide();
  });
});
</script> 

<script>
function validateLogin()
{
	//TODO: Move to external reusable .js script file	
	var email=document.forms["loginForm"]["email"].value;
	
	if ( email == "")
	{
		alert("Certain fields are missing.");
		return false;
	}
	/*else if ()
		//TODO: Check email matches regular expression*/
	return true;

}
</script>

</head>

<body>

	<div class="wrapper">
	
<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />
	

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

				<input class="greybutton" type="submit" value="Cancel" />

			</div>

		</form>
		<!-- End Login Form -->
		
		<p> Not a member? <a href="${pageContext.request.contextPath}/register">Register here</a>. </p>
	
		<p>
			<!-- Possibly display failure message -->
			<jsp:scriptlet> String message = (String)request.getAttribute("message");
				if (message != null)
					out.println(message);
			</jsp:scriptlet>
		</p>  
		
	</div>




</body>

</html>