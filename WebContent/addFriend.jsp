<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chatter: Add Friend</title>
<link href='http://fonts.googleapis.com/css?family=Roboto:400,700,400italic' rel='stylesheet' type='text/css'>
	<link href='http://fonts.googleapis.com/css?family=Iceland' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script>
//Gets the browser specific XmlHttpRequest Object
function getXmlHttpRequestObject() {
	if (window.XMLHttpRequest) {
		return new XMLHttpRequest();
	} else if(window.ActiveXObject) {
		return new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		alert("Browser doesn't support AJAX!");
	}
	
	//Our XmlHttpRequest object to get the auto suggest
	var searchReq = getXmlHttpRequestObject();
	
}

//Called from keyup on the search textbox.
//Starts the AJAX request.
function searchSuggest() {
	if (searchReq.readyState == 4 || searchReq.readyState == 0) {
		var str = escape(document.getElementByClassName('inputcontent').value);
		var parameters="name="+str;
		searchReq.open("POST", 'searchSuggest', true);
		mypostrequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		searchReq.send(parameters);
		searchReq.onreadystatechange = handleSearchSuggest; 
	}		
}

//Called when the AJAX response is returned.
function handleSearchSuggest() {
	if (searchReq.readyState == 4) {
		var ss = document.getElementById('searchSuggestion')
		ss.innerHTML = '';
		var str = searchReq.responseText.split("\n");
		for(var i=0; i < str.length - 1; i++) {
			//Build our element string.  This is cleaner using the DOM, but
			//IE doesn't support dynamically added attributes.
			var suggest = '<div onmouseover="javascript:suggestOver(this);" ';
			suggest += 'onmouseout="javascript:suggestOut(this);" ';
			suggest += 'onclick="javascript:setSearch(this.innerHTML);" ';
			suggest += 'class="suggest_link">' + str[i] + '</div>';
			ss.innerHTML += suggest;
		}
	}



}
</script>
</head>
<body>

	<jsp:include page="/top-right.jsp" />

	<h1 id="bannerText">Chatter</h1>

	<div class="wrapper">
	
<!-- Navigation Bar -->
		<jsp:include page="/nav-bar.jsp" />

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

		<jsp:include page="/footer.jsp" />

	</div>
	
</body>
</html>