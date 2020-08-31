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
	
	<title>Login</title>
	<link rel="icon" type="image/x-icon" href="res/drawable/m_logo.png">
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/signup.css" rel="stylesheet">
	<link href="css/intro.css" rel="stylesheet">
	<link href="css/glyphicon.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	
	<script src="js/bootstrap-validate.js"></script>
	<script src="js/header.js"></script>
	<script src="js/validation.js"></script>
	<script src="js/popup.js"></script>
	<script src="js/login/login.js"></script>
	
</head>
<body>
	
	<!-- ======================
		  Header section
	======================= -->
	
	<header id="header">
			
		<nav class="navbar navbar-light">
			<a class="navbar-brand" href="./"><img alt="" src="res/drawable/logo3.png"></a>
		</nav>
			
	</header>

	<section>
		<div class="login-wrapper foreground-box">
			
			<div id="form-header">
				<h1>Sign In</h1>
				<p class="note">Login into your Moviesquik account</p>
			</div>
			
			
			<form method="POST" id="signinform" action="login" class="well form-horizontal">
			<fieldset>
				
				<div class="form-row">
					<div class="col">
						<label for="email">Email address</label>
						<div class="input-group">
							
							<span class="input-group-addon"><span class="fa fa-envelope"></span></span>
							<input type="text" id="email" name="email" class="form-control" placeholder="email@example.com" value="myfamily@email.com" required>
							
						</div>
					</div>
				</div>
					
				<div class="form-row">
					<div class="col">
						<label for="password">Password</label>
						<div class="input-group">
							<span class="input-group-addon"><span class="fa fa-lock"></span></span>
							<input type="password" id="password" name="password" class="form-control" placeholder="Password" value="password1234" required>
							
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
						<button class="btn btn-main" type="submit" id="signin-submit-btn"><h5>Sign In</h5></button>
					</div>
				</div>
				<!-- <div class="form-check form-check-inline">
					<input id="remember_me_checkbox" type="checkbox">
					<label for="remember_me_checkbox">Remember me</label>
				</div> -->
				
			</fieldset>
			</form>
			
		</div>
	</section>
	
	<br><br><br>
	<jsp:include page="footer.html"></jsp:include>
	
</body>
</html>