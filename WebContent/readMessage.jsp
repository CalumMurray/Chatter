<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chatter: User's Chats</title>

<script src="//ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.3.min.js">	<!--  JQuery -->
<script src="jquery.timeago.js" type="text/javascript"></script>	<!-- JQuery plugin for converting Timestamp format to "relative time ago" text -->


<script>
$(document).ready(function() {
	  $("abbr.timeago").timeago();
	});
</script>

</head>
<body>

	<div class="wrapper">
	
<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />
	
<!-- Messages -->
		<c:if test="${userMessages == null}">
			<c:out value="You've not posted any messages" />
		</c:if>

		<!-- TODO: Style message box -->
		<c:forEach items="${userMessages}" var="message">  
		    <div class="messageContainer">  
		    	<c:out value="${message.author.firstName} ${message.author.lastName}"/><hr />
		        <c:out value="${message.content}"/><hr />
		        <!--  TODO: Timestamp into user-readable "time ago" -->
		        <div class="timeAgo">
		        	<c:out value="${message.timeStamp}"/>
				</div>
		    </div>
		</c:forEach>  
	</div>
	

</body>
</html>