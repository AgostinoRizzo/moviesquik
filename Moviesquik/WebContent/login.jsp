<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>Login</title>
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/signup.css" rel="stylesheet">
	<link href="css/intro.css" rel="stylesheet">
	<link href="css/glyphicon.css" rel="stylesheet">
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	
	<script src="js/bootstrap-validate.js"></script>
	<script src="js/validation.js"></script>
	<script src="js/popup.js"></script>
	
</head>
<body>
	
	<!-- ======================
		  Header section
	======================= -->
	
	<header id="header">
			
		<nav class="navbar navbar-light">
			<a class="navbar-brand" href="./"><img alt="" src="res/drawable/logo.png" height="50px"></a>
		</nav>
			
	</header>

	<section id="intro">
		<div class="login-wrapper">
			
			<div id="form-header">
				<h1>Sign In</h1>
			</div>
			
			
			<form method="POST" id="signinform" action="login" class="well form-horizontal">
			<fieldset>
				
				<div class="form-row">
					<div class="col">
						<label for="email">Email address</label>
						<div class="input-group">
							
							<span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
							<input type="text" id="email" name="email" class="form-control" placeholder="email@example.com" required>
							
						</div>
					</div>
				</div>
					
				<div class="form-row">
					<div class="col">
						<label for="password">Password</label>
						<div class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
							<input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
							
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
							
						</div>
					</div>
				</div>
				
				<a href="">Forgotten your email or password?</a>
				
				<hr class="mb-3">
				
				<div class="form-row btn-form-row">
					<div class="col">
						<button class="btn btn-main btn-main-light" type="submit"><h5>Sign In</h5></button>
					</div>
				</div>
				<div class="form-check form-check-inline">
					<input id="remember_me_checkbox" type="checkbox">
					<label for="remember_me_checkbox">Remember me</label>
				</div>
				
			</fieldset>
			</form>
			
		</div>
	</section>
	
</body>
</html>