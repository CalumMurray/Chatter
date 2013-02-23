<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- Core JSTL Tag Library -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chatter: Followers</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- Standard css stylesheet -->
<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,400italic' rel='stylesheet' type='text/css'> <!-- Web fonts -->
<link href='http://fonts.googleapis.com/css?family=Iceland' rel='stylesheet' type='text/css'>


</head>
<body>
<jsp:include page="/top-right.jsp" />

	<h1 id="bannerText">Chatter</h1>

	<div class="wrapper">
	
		<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />

		<div id="friendsFeed">
		
			<h1 id="friendsTitle">Followers</h1>
			<hr />
			
			<!-- If you nobody's following you -->
			<c:if test="${failureMessage != null}">
				<p id="returnMessage"><c:out value="${failureMessage}" /></p>
			</c:if>
			
			<!--<c:if test="${deleteMessage != null}">
				<p id="returnMessage"><c:out value="${deleteMessage }" /></p>
			</c:if>-->
		    
			<!--  List friends -->
			<c:forEach items="${followerList}" var="follower">  
			
			
			    <div class="friendContainer">  
			    	<div title="${follower.email}" class="friendEmail">
			    		<img id="friendProfilePicture" src="images/blank_profile.png" alt="Blank Profile Picture"/>
			    		<a id="friendDetails" href="${pageContext.request.contextPath}/profile/${follower.email}"><c:out value="${follower.firstName} ${follower.lastName}"/></a>
			    	</div>
			    	
			    </div>
			</c:forEach>  
			
		</div>
		

	</div>
	
	<!-- Footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>