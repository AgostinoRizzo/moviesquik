<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Moviesquik for Business</title>
	<link rel="icon" type="image/x-icon" href="res/drawable/m_logo.png">
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/business.css" rel="stylesheet">
	<link href="css/business-home.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/common.css" rel="stylesheet">
	<link href="css/glyphicon.css" rel="stylesheet">
	<link href="css/home.css" rel="stylesheet">
	<link href="css/media.css" rel="stylesheet">
	<link href="css/contents.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"/>
	
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="lib/typeahead/bootstrap3-typeahead.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	
	<script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"></script>
	
	<script src="js/business/sidenav.js" type="module"></script>
	<script src="js/business/media-search-utils.js"></script>
	<script src="js/business/media-search.js" type="module"></script>
	<script src="js/business/media-search-analytics.js" type="module"></script>
	<script src="js/business/media-analytics-dashboard.js" type="module"></script>
	<script src="js/business/cdn-charts.js" type="module"></script>
	<script src="js/business/cdn-servers-list.js"></script>
	
</head>
<body>

	<!-- ======================
		  Header section
	======================= -->
	
	<jsp:include page="header.jsp"></jsp:include>
	
	<div class="page-container home-container-wrapper row">
		
		<!-- home sidenav -->
		<div class="col-2 scollable-col sidenav users-col home-container-col" id="home-sidenav-col">
			<jsp:include page="sidenav.jsp"></jsp:include>
		</div>
		
		<!-- home page content -->
		<div class="col-10 scollable-col home-container-col" id="home-page-content">
			
		</div>
		
	</div>

</body>
</html>