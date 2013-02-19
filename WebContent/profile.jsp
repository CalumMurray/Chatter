<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chatter Profile</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- Standard css stylesheet -->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,400italic' rel='stylesheet' type='text/css'>	<!-- Google Web font -->
<link href='http://fonts.googleapis.com/css?family=Iceland' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/relativeTime.js" > </script>	<!-- Custom javascript to turn timestamps into e.g. "x seconds ago" etc.  -->


<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script><!-- JQuery CDN -->

<!-- JQuery script to make messages clickable for a "delete" interface -->
<script>
$(document).ready(function(){
  	$("div.messageContainer").click(function() {
	  alert("Show this message individually for deletion");	  
	});
});
</script>





</head>

<body onLoad="relativeTime()">

	<jsp:include page="/top-right.jsp" />

	<h1 id="bannerText">Chatter</h1>

	<div class="wrapper">
		<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />
		
		<div class="profileContainer">

			<!--  content -->
			<h1> ${user.firstName} </h1>
			
			<div id="profilePicture">
				<img src="images/blank_profile.png" alt="Blank Profile Picture"/>
			</div>
			
			<!-- Account information -->
			<p> <a href="friends"> Following: ${following} </a> </p>
			<p> Followers: </p>
		
		
<!-- Create message form direct from profile page -->
			<form name="messageForm" method="post" action="${pageContext.request.contextPath}/profile">
				<input class="inputcontent" type="text" name="message" value="Type your chat here..." />
				<input class="orangebutton" type="submit" value="Post Chat" />
			</form>
			
		</div>
		
		
		<div id="messageFeed">
		
			<div id="messageTitle">
				<h2 id="messageTitle">Chat Feed</h2>
			</div>
			
			<c:if test="${userMessages == null}">
				<p id="returnMessage"><c:out value="No messages in feed" /></p>
			</c:if>
	
			<!-- TODO: Style message box -->
			<c:forEach items="${userMessages}" var="message">  
			    <div class="messageContainer">
			    	<div class="messageAuthor">
			    		<c:out value="${message.author.firstName} ${message.author.lastName} (${message.author.email}) says.."/><hr />
			    	</div>
			    	<div class="messageContent">
			        	<c:out value="${message.content}"/>
			        </div>
			        <!--  TODO: Timestamp into user-readable "time ago" -->
			        <div class="timeAgo">
			        	<p class="timestamp">${message.timeStamp}</p>
					</div>
			    </div>
			</c:forEach>  
	

		</div>
		
		
		<jsp:include page="/footer.jsp" />
		
		
	</div>



</body>

</html>