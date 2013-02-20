<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chatter Friends</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- Standard css stylesheet -->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,400italic' rel='stylesheet' type='text/css'> <!-- Web fonts -->
<link href='http://fonts.googleapis.com/css?family=Iceland' rel='stylesheet' type='text/css'>

<script src="../jquery.tooltip.js" type="text/javascript"></script> <!-- Tooltip plugin for JQuery -->
<script src="../lib/jquery.dimensions.js" type="text/javascript"></script>

<script>
$('.userName').tooltip( { 
    track: true, 
    delay: 0, 
    showURL: false, 
    showBody: " - ", 
    fade: 250 
});

</script>

<script>

</script>

</head>
<body>

	
	<jsp:include page="/top-right.jsp" />

	<h1 id="bannerText">Chatter</h1>

	<div class="wrapper">
	
		<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />

		
		<h1 id="friendsTitle">Friends</h1>
		
		<c:if test="${message != null}">
			<p id="returnMessage"><c:out value="${message }" /></p>
		</c:if>
	    <hr />
		<!--  List friends -->
		<c:forEach items="${users}" var="user">  
		    <div class="friendContainer">  
		    	<div title="${user.email}" class="userName">
		    		<p><c:out  value="${user.firstName} ${user.lastName}"/></p>
		    		<div id="friendOptions">
			    		<c:choose>
				    		<c:when test="${user.friend == false}">
				    			<button class="orangebutton" type="submit" onclick="location.href='${pageContext.request.contextPath}/add/friend/${user.email}'" > Follow </button>
			    			</c:when>
			    			<c:otherwise>
			    				<p id="tick">&#10004;</p>
			    				<button class="greybutton" type="submit" onclick="location.href='${pageContext.request.contextPath}/delete/friend/${user.email}'" > Stop Following </button>
			    			</c:otherwise>
		    			</c:choose>
	    			</div>
		    	</div>
		    	
		    </div>
		</c:forEach>  

		<h1>Add more friends</h1>
		<hr />
		<form name="friendForm" method="post" action="${pageContext.request.contextPath}/add/friend">
			<input class="inputcontent" type="text" name="friend" value="Type friend's name or email..." 
					onkeyup="searchSuggest();" />
			<div id="searchSuggestion">
			</div>
			<div class="buttons">
				<input class="orangebutton" type="submit" value="Add" />
			</div>
		</form>

		<!-- TODO: AJAX Suggest -->

		<!-- List possibilities -->
		<c:if test="${possibleFriends != null}">
			<c:forEach items="${possibleFriends}" var="friend">  
				<c:out value="${friend.firstname} ${friend.lastname} (${friend.email})" /><a href="${pageContext.request.contextPath}/add/friend/${friend.email}">Add</a>
			</c:forEach>
		</c:if>

		<!-- Display success message? -->
		<c:if test="${message != null}">
			<p id="returnMessage"> <c:out value="${message}" />
		</c:if>

		


	</div>
	
		<!-- Footer -->
		<jsp:include page="footer.jsp" />
</body>
</html>