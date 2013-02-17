<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" />


<script src="http://yui.yahooapis.com/2.7.0/build/yuiloader/yuiloader-min.js"></script>  <!-- Yahoo's YUI CDN -->
<script>   
var loader = new YAHOO.util.YUILoader({  
    require: ["get","dom","event","animation", "slider"],  
    loadOptional: true,  
    onSuccess: function() {  
    YAHOO.util.Event.onDOMReady(init);  
    },  
    timeout: 10000,  
    combine: true  
});  
loader.insert();  
  
function init()
{  
console.warn('YUI Loaded');  
//TODO: Do my own function here
}  
</script>  

<script>
function validateRegister()
{
	//TODO: Move to external reusable .js script file	
	var fname=document.forms["registerForm"]["firstname"].value;
	var lname=document.forms["registerForm"]["lastname"].value;
	var email=document.forms["registerForm"]["email"].value;
	var pass1=document.forms["registerForm"]["password"].value;
	var pass2=document.forms["registerForm"]["password2"].value;
	
	if (fname == "" || lname == "" || email == "")
	{
		alert("Certain fields are missing.");
		return false;
	}
	if (pass1 != pass2)
	{
		alert("Passwords don't match.");
		return false;
	}
	return true;

}
</script>

</head>

<body>

	<div class="wrapper">
	
<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />

<!--  Register form -->


		<form class="myForm" action="${pageContext.request.contextPath}/register" method="post" onsubmit="return validateRegister()" name="registerForm">

			<div class="formtitle">Register with <i>Chatter</i> </div>

			<div class="input">
				<div class="inputtext">First Name: </div>
				<div class="inputcontent">

					<input type="text" name="firstname" value="${user.firstName}" />

				</div>
			</div>
			
			<div class="input">
				<div class="inputtext">Last Name: </div>
				<div class="inputcontent">

					<input type="text" name="lastname" value="${user.lastName}"/>

				</div>
			</div>
			
			<div class="input">
				<div class="inputtext">Email: </div>
				<div class="inputcontent">

					<input type="text" name="email" value="${user.email}"/>

				</div>
			</div>

			<div class="input nobottomborder">
				<div class="inputtext">Password: </div>
				<div class="inputcontent">

					<input type="password" name="password" />

				</div>
			</div>
			
			<div class="input nobottomborder">
				<div class="inputtext">Confirm Password: </div>
				<div class="inputcontent">

					<input type="password" name="password2" />

				</div>
			</div>

			<div class="buttons">

				<input class="orangebutton" type="submit" value="Register" />

				<input class="greybutton" type="submit" value="Cancel" />

			</div>

		</form>
		<!-- End Register Form -->
		
		<p> Already Registered? <a href="${pageContext.request.contextPath}/login"> Login here. </a></p>
		
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