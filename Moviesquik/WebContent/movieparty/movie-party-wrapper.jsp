<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script src="https://www.googletagmanager.com/gtag/js?id=UA-176431235-1"></script>
	<script>
		window.dataLayer = window.dataLayer || [];
		function gtag(){dataLayer.push(arguments);}
		gtag('js', new Date());
	
		gtag('config', 'UA-176431235-1');
	</script>
	
	<title>Movie Party</title>
	<link rel="icon" type="image/x-icon" href="res/drawable/m_logo.png">
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/common.css" rel="stylesheet">
	<link href="css/movieparty.css" rel="stylesheet">
	<link href="css/contents.css" rel="stylesheet">
	<link href="css/media.css" rel="stylesheet">
	<link href="css/home.css" rel="stylesheet">
	<link href="css/posting.css" rel="stylesheet">
	<link href="css/chat.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	<script src="js/notification/notifications.js" type="module"></script>
	<script src="js/media/show_media_content.js" type="module"></script>
	<script src="js/bootstrap-validate.js"></script>
	<script src="js/movieparty/movie-party.js" type="module"></script>
	<script src="js/movieparty/party-chat.js" type="module"></script>
	
</head>
<body>

	<!-- ======================
		  Header section
	======================= -->
	
	<jsp:include page="../header.jsp"></jsp:include>
	
	
	<!-- ======================
		  Create Party contents
	======================= -->
	<div id="movie-party-page-container">
		<jsp:include page="movie-party.jsp"></jsp:include>
	</div>
	
	<jsp:include page="../footer.html"></jsp:include>
	
</body>
</html>