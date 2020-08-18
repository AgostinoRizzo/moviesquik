<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>Change Billing Plan</title>
	<link rel="icon" type="image/x-icon" href="res/drawable/m_logo.png">
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/signup.css" rel="stylesheet">
	<link href="css/glyphicon.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	
	<script src="js/header.js"></script>
	
</head>
<body>

	<!-- ======================
		  Header section
	======================= -->
	
	<header id="header" class="foreground-box">
			
		<nav class="navbar navbar-light">
			<a class="navbar-brand" href="./"><img alt="" src="res/drawable/logo3.png"></a>
			<div class="navbar-content">
				<form class="form-inline">
					<a class="btn btn-outline-danger" type="button" href="billing">Cancel</a>
				</form>
			</div>
		</nav>
			
	</header>
	
	<div id="intro" class="intro-section">
		<div class="signup-wrapper foreground-box choose-plan-wrapper" >
			
			<c:if test="${account != null}">
			
				<div id="form-header">
					<div class="note">BILLING PLAN CHANGE</div>
					<h2>Choose a new plan that's right for you.</h2>
					<h5 class="note">Downgrade or upgrade at any time.</h5>
				</div>
				
				<form method="POST" action="billing?action=changeplan">
					<jsp:include page="../pricing.jsp"></jsp:include>
				</form>
			
			</c:if>
			
		</div>
	</div>
	
	<jsp:include page="../footer.html"></jsp:include>
	
</body>
</html>