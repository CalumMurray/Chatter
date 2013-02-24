<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- Core JSTL Tag Library -->
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
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" /><!-- JQuery UI CSS -->
  <script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script><!-- JQuery UI CDN -->


<!-- View a message on it's own with the possibility of deleting it -->
<script>
function viewIndividualMessage(messageID)
{
	location.href="${pageContext.request.contextPath}/messages/" + messageID;
}
</script>

<!-- Use JQuery AJAX to HTTP DELETE to /profile in order to delete your profile -->
<script>
$(document).ready(function(){    
	$("p#delete").click(function() {
		var result = window.confirm("You are about to delete your profile.\nAre you sure?\nYou'll miss out...");
		if (result)
		{
			//Call AJAX Delete
			alert("Deleting profile");
			$.ajax({
			  url: "${pageContext.request.contextPath}/profile",
			  type: "DELETE",
			}).done(function(data) {
				
				$( "#dialog" ).dialog({
			        modal: true,
			        buttons: {
				        Ok: function() {
				          $( this ).dialog( "close" );
				        }
			        }
				});
				location.href = "${pageContext.request.contextPath}/login";
			});
		}
		else
			return;		
	});
});
</script>

<!-- <script>
$(document).ready(function(){
  	$("p#seeMore").click(function() {
  		//AJAX GET
  		$.get("messages?more=true",  
  	          function(responseText) {  
  	                $("#result").html(responseText);
  	                fadeMessagesIn();
  	           },  
  	           "json"  
  	    );  
	});
});

function fadeMessagesIn()
{
}
</script>
 -->

</head>

<body onLoad="relativeTime()">

	<jsp:include page="/top-right.jsp" />

	<h1 id="bannerText">Chatter</h1>

	<div class="wrapper">
		<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />
		
		<div id="leftSide">
			<div class="profileContainer">
	
				<!--  content -->
				<h1> ${user.firstName} </h1> <p id="delete"><a href=""></a>(Delete?)</p>
				
				<div id="profilePicture">
					<img src="${pageContext.request.contextPath}/images/blank_profile.png" alt="Blank Profile Picture"/>
				</div>
				
				<!-- Only allow link when it's the logged-in Session user -->
				<c:choose>
					 <c:when test="${otherUser != true}">
						<!-- Account information -->
						<p><a href="${pageContext.request.contextPath}/following"> Following: ${following} </a> </p>
						<p> <a href="${pageContext.request.contextPath}/followers"> Followers: ${followers} </a> </p>
					</c:when>
					<c:otherwise>
						<!-- Account information -->
						<p>Following: ${following} </p>
						<p> Followers: ${followers} </p>
					</c:otherwise>
				</c:choose>
			
			
	<!-- Create message form direct from profile page -->
				<form name="messageForm" method="post" action="${pageContext.request.contextPath}/messages">	<!-- Post to "/messages" creates a new message -->
					<textarea class="inputcontent" name="message" placeholder="Type your chat here" cols="40" rows="4" ></textarea> 
					<input class="orangebutton" type="submit" value="Post Chat" />
				</form>
				
				<c:if test="${successMessage != null}">
				<p id="returnMessage"><c:out value="${successMessage }" /></p>
			</c:if>
			</div>
		</div>
		
		
		<div id="messageFeed">
		
			<div id="messageTitle">
				<h2 id="messageTitle">Chat Feed</h2><hr />
			</div>
			
			<c:if test="${userMessages.size() == 0}">
				<p id="returnMessage"><c:out value="No messages in feed" /></p>
			</c:if>
	

			<c:forEach items="${userMessages}" var="message">  
			    <div class="messageContainer" onclick="viewIndividualMessage(${message.id})">
			    	<div class="messageAuthor">
			    		<c:out value="${message.author.firstName} ${message.author.lastName} (${message.author.email}) says.."/><hr />
			    	</div>
			    	<div class="messageContent">
			        	<c:out value="${message.content}"/>
			        </div>
			        <div class="timeAgo">
			        	<p class="timestamp">${message.timeStamp}</p>
					</div>
			    </div>
			</c:forEach>  
	
	
			<!-- TODO: Load more with AJAX -->
			<c:if test="${userMessages.size() != 0}">
				<p id="seeMore"> See More... </p>
			</c:if>
			
			
		
		</div>
		
		
		
	<div id="#dialog" hidden="true">
		<p>Profile Deleted</p>
	</div>	
		
	</div>

<jsp:include page="/footer.jsp" />

</body>

</html>