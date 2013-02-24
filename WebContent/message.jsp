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


<!-- JQuery AJAX to send HTTP DELETE method to /messages/<id> to delete the message from db -->
<script>
$(document).ready(function(){    
	$("button[name='delete']").click(function() {
		$.ajax({
		  url: "${pageContext.request.contextPath}/messages/${message.id}",
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
			location.href = "${pageContext.request.contextPath}/profile";
		});
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
			
		 <div class="friendContainer" onclick="viewIndividualMessage(${message.id})">
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

	    
	    <c:if test="${ message.author.email == user.email }">
	    	<!-- Only deletable if it's your own message -->
	    	<button class="greybutton" id="messageButton" name="delete"> <img id="deleteImage" src="${pageContext.request.contextPath}/images/delete.png">Delete </button>
	    </c:if>
	
		
		
		
		
		<div id="dialog" title="Deleted">
			<p> Message deleted! </p>
		</div>

	</div>

<jsp:include page="/footer.jsp" />

</body>

</html>