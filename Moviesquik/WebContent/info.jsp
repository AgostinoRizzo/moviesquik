<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>
		<c:if test="${registration_done != null}">Info</c:if>
		<c:if test="${error != null}">Error</c:if>
	</title>
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/signup.css" rel="stylesheet">
	
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
			
		  		<c:if test="${registration_done != null}"><h1 class="display">Thank You!</h1></c:if>
				<c:if test="${error != null}">
					<h1 class="display">
						<c:if test="${error.type == 'registration_error'}">Registration Error!</c:if>
						<c:if test="${error.type == 'session_error'}">Session Error!</c:if>
						<c:if test="${error.type == 'parameter_error'}">Parameter Error!</c:if>
					</h1>
				</c:if>
				  
				  <div class="lead">
					  	<h6>
					  		<c:if test="${registration_done != null}">
					  			<strong>Registration done.</strong> Welcome <strong>${registration_done.email}</strong>. Before you can start enjoy watching Movies, TV Shows and more, please create your first profile.
					  		</c:if>
					  		
					  		<c:if test="${error != null}">
					  			<c:if test="${error.type == 'registration_error' || error.type == 'session_error'}">
					  				<strong>An error occurred while registering your account.</strong> All provided data are not committed to the system.
					  			</c:if>
					  		</c:if>
					  	</h6>
				 
				  	</div>
				  <hr>
				  
				  <div>
				    <c:if test="${error != null}">Having trouble? <a href="">Contact us</a></c:if>
				  </div>
				  
				  <br>
				  
				  <div class="lead">
				    <a class="btn btn-main" href="." role="button">
				    		<c:if test="${registration_done != null}"><h4>Continue</h4></c:if>
				    		<c:if test="${error != null}"><h4>Continue to Homepage</h4></c:if>
				    </a>
				  </div>
			</div>
		</div>
	</section>
</body>
</html>