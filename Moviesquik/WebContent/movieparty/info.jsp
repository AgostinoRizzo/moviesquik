<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>
		<c:if test="${error != null}">Error</c:if>
		<c:if test="${success != null}">Info</c:if>
	</title>
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/common.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/signup.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	
</head>
<body>
	
	<!-- ======================
		  Header section
	======================= -->
	
	<header id="header" class="header-always-fixed foreground-box">
			
		<nav class="navbar navbar-light">
			<a class="navbar-brand" href="./"><img alt="" src="res/drawable/logo.png"></a>
			<div class="navbar-content">
				<!-- <form class="form-inline">
					<a class="btn btn-outline-danger" type="button" href="signup?cancel=true">Cancel</a>
				</form> -->
			</div>
		</nav>
			
	</header>
	
	<section>
		<div class="info-wrapper">
			<div class="text-center">
			
				<c:if test="${error != null}">
					<h4 class="display">
						<c:if test="${error.type == 'datetime_error'}">Date and time Error!</c:if>
						<c:if test="${error.type == 'creation_error'}">Creation Error!</c:if>
					</h4>
				</c:if>
				<c:if test="${success != null}">
					<h4 class="display"><i class="fa fa-check colored"></i>  Movie party successfully created</h4>
				</c:if>
				  
				  <div class="lead">
					  	<h6>
					  		<c:if test="${error != null}">
					  			<c:if test="${error.type == 'datetime_error'}">
					  				<strong>Please provide a future date and time for the movie party.</strong> All provided data are not committed to the system.
					  			</c:if>
					  			<c:if test="${error.type == 'creation_error'}">
					  				<strong>An error occurred while creating your movie party.</strong> All provided data are not committed to the system.
					  			</c:if>
					  		</c:if>
					  		
					  		<c:if test="${success != null}">
					  			<strong>The media party will start ${startTime}. All invited friends will receive an invitation for this event.</strong>
					  		</c:if>
					  	</h6>
				 
				  	</div>
				  <hr>
				  
				  <br>
				  
				  <div class="lead">
				  	<c:if test="${error != null}">
					    <a class="btn btn-main" href="movieparty" role="button">Continue</a>
				    </c:if>
				    
				    <c:if test="${success != null}">
					    <a class="btn btn-main" href="." role="button">Continue</a>
				    </c:if>
				  </div>
			</div>
		</div>
	</section>
</body>
</html>