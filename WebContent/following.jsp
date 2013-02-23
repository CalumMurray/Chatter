<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- Core JSTL Tag Library -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chatter Friends</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- Standard css stylesheet -->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,400italic' rel='stylesheet' type='text/css'> <!-- Web fonts -->
<link href='http://fonts.googleapis.com/css?family=Iceland' rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" /><!-- JQueryUI css for dialog -->
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> <!--  JQuery CDN-->
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script><!--  JQueryUI CDN-->

<script src="../jquery.tooltip.js" type="text/javascript"></script> <!-- Tooltip plugin for JQuery -->
<script src="../lib/jquery.dimensions.js" type="text/javascript"></script>

<!-- Displays tooltip on user's name showing the email address. -->
<script>

$('.friendEmail').tooltip( { 
    track: true, 
    delay: 0, 
    showURL: false, 
    showBody: " - ", 
    fade: 250 
});

</script>

<!-- AJAX call with JQuery to use HTTP DELETE method to delete stop following a user -->
<script>
$(document).ready(function(){
	$("button[name='delete']").click(function() {
		alert("in JQuery's click for delete ajax");
		var buttonText = $(this).text();
		var email = buttonText.substring(buttonText.lastIndexOf(' '), buttonText.length);
		$.ajax({
		  url: "${pageContext.request.contextPath}/following/" + email,
		  type: "DELETE",
		}).done(function() {
			$("body").append("<p id='returnMessage'>You've stopped following email</p>");
			$("#dialog").dialog();
		});
	});
});

</script>

<!-- Dynamically make hidden form to POST user to start following, to servlet -->
<script>
function addFollowing(userToFollow) {
	//MNake hidden form
	alert("in addFollowing(" + userToFollow + ")");
    var form = document.createElement('form');
    form.setAttribute('method', 'post');
    form.style.display = 'hidden';
    document.body.appendChild(form);
    
  	//Make hidden input element with user to follow
    var input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("name", "userToFollow");
    input.setAttribute("value", userToFollow);
    
    form.submit();
}

</script>
</head>
<body>

	
	<jsp:include page="/top-right.jsp" />

	<h1 id="bannerText">Chatter</h1>

	<div class="wrapper">
	
		<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />

		<div id="friendsFeed">
		
			<h1 id="friendsTitle">Following</h1>
			<hr />
			
			<!-- If you aren't following anybody -->
			<c:if test="${failureMessage != null}">
				<p id="returnMessage"><c:out value="${failureMessage}" /></p>
			</c:if>
					    
			<!--  List friends -->
			<c:forEach items="${users}" var="currentUser">  
			
						<div id="dialog">
						<p> You've stopped following ${currentUser.email}</p>
						</div>
			
			    <div class="friendContainer">  
			    	<div title="${currentUser.email}" class="friendEmail">
			    		<img id="friendProfilePicture" src="images/blank_profile.png" alt="Blank Profile Picture"/>
			    		<a id="friendDetails" href="${pageContext.request.contextPath}/profile/${currentUser.email}"><c:out value="${currentUser.firstName} ${currentUser.lastName}"/></a> <c:if test="${currentUser.friend == true}"><p id="tick">(&#10004;)</p></c:if>
			    		<div id="friendOptions">
				    		<c:choose>
					    		<c:when test="${currentUser.friend == false}">
					    			<form method="POST" >
					    				<input type="hidden" name="userToFollow" value="${currentUser.email}" />
					    				<input type="submit" class="orangebutton" id="friendButton" value="Follow" />	<!--  POST via javascript "addFollowing" function-->
					    			</form>
				    			</c:when>
				    			<c:otherwise>
				    				<button class="greybutton" id="friendButton" type="button" name="delete">Stop Following ${currentUser.email}</button>
				    			</c:otherwise>
			    			</c:choose>
		    			</div>
			    	</div>
			    	
			    </div>
			</c:forEach>  
			
		</div>
		
		
		<!-- <h1>Add more friends</h1>
		<hr />
		<form name="friendForm" method="post" action="${pageContext.request.contextPath}/add/friend">
			<input class="inputcontent" type="text" name="friend" value="Type friend's name or email..." 
					onkeyup="searchSuggest();" />
			<div id="searchSuggestion">
			</div>
			<div class="buttons">
				<input class="orangebutton" type="submit" value="Add" />
			</div>
		</form> -->

		<!-- TODO: AJAX Suggest -->

		<!-- List possibilities 
		<c:if test="${possibleFriends != null}">
			<c:forEach items="${possibleFriends}" var="friend">  
				<c:out value="${friend.firstname} ${friend.lastname} (${friend.email})" /><a href="${pageContext.request.contextPath}/add/friend/${friend.email}">Add</a>
			</c:forEach>
		</c:if>-->



	</div>
	
	<!-- Footer -->
	<jsp:include page="footer.jsp" />
	
</body>
</html>