<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chatter Friends</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- Standard css stylesheet -->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,400italic' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Iceland' rel='stylesheet' type='text/css'>

<script src="../jquery.tooltip.js" type="text/javascript"></script> <!-- Tooltip plugin for JQuery -->
<script src="../lib/jquery.dimensions.js" type="text/javascript"></script>

<script>
$('.friendName').tooltip( { 
    track: true, 
    delay: 0, 
    showURL: false, 
    showBody: " - ", 
    fade: 250 
});

</script>


</head>
<body>

	
	<jsp:include page="/top-right.jsp" />

	<h1 id="bannerText">Chatter</h1>

	<div class="wrapper">
	
		<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />

		<c:if test="${friends == null}">
			<c:out value="Make some friends!" />
		</c:if>
		
		<h2 id="friendsTitle">Friends</h2>
		
		<!--  List friends -->
		<c:forEach items="${friends}" var="friend">  
		    <div class="friendContainer">  
		    	<div title="${friend.email}" class="friendName">
		    		<p><c:out  value="${friend.firstName} ${friend.lastName}"/></p>
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
			<input class="orangebutton" type="submit" value="Add" />
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
			<p> <c:out value="${message}" />
		</c:if>

		<!-- Footer -->
		<jsp:include page="footer.jsp" />


	</div>

</body>
</html>