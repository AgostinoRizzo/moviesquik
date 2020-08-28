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
	<link href="css/header.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/common.css" rel="stylesheet">
	<link href="css/glyphicon.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="lib/typeahead/bootstrap3-typeahead.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	<script src="js/popup.js"></script>
	<script src="js/business/login-intro.js"></script>
	
</head>
<body>

	<!-- ======================
		  Header section
	======================= -->
	
	<jsp:include page="header.jsp"></jsp:include>
	
	<img id="business-background-img" src="res/drawable/business_intro_0.jpg">
	<div class="page-container-wrapper">
		<div class="page-container container">
			
			<div id="business-login-intro">
			
				<img src="res/drawable/business_logo.png" id="business-logo-img">
				<h1>Management and Analytics</h1>
				
				<h5>Sign in into your analyst account to manage media contents, money collections, media servers, pages and contents analytics, external APIs and more.</h5>
				
				<form method="POST" action="business/login" id="signup-form">
					
					<div class="form-row">
						<div class="col col-md-6 col-12">
							<label for="email">Analyst email</label>
							<div class="input-group">
								
								<span class="input-group-addon"><span class="fa fa-user"></span></span>
								<input type="text" id="email" name="email" class="form-control" placeholder="email@example.com" value="admin@moviesquik.com" required>
								
							</div>
						</div>
						<div class="col col-md-5 col-12">
							<label for="password">Password</label>
							<div class="input-group">
								<span class="input-group-addon"><span class="fa fa-lock"></span></span>
								<input type="password" id="password" name="password" class="form-control" placeholder="Password" value="adminpassword1234" required>
								
							</div>
						</div>
						
						<div class="col col-md-1 col-12" id="sign-in-btn-col">
							<button type="submit" class="btn btn-primary" id="signin-submit-btn">Sign in</button>
						</div>
					</div>
					
				</form>
				
				<br><br>
				<div><a href="./" id="back-home-link">Back to Moviesquik</a></div>
			
			</div>
			
		</div>
	</div>
	
	<c:if test="${error != null && error.type == 'invalid_login'}">
				
		<div id="invalid-login-popup" class="modal" tabindex="-1">
	        <div class="modal-dialog">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <h5 class="modal-title">Invalid login</h5>
	                    <button type="button" class="close" data-dismiss="modal">&times;</button>
	                </div>
	                <div class="modal-body">
	                    <p>Incorrect email or password.</p>
	                    <p class="text-secondary"><small>Please enter a correct email address and password.</small></p>
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-main btn-main-light" data-dismiss="modal">Cancel</button>
	                </div>
	            </div>
	        </div>
	    </div>
			  										
	</c:if>

</body>
</html>