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
	
	<title>Who's Watching?</title>
	<link rel="icon" type="image/x-icon" href="res/drawable/m_logo.png">
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/common.css" rel="stylesheet">
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
	<script src="js/accounting/whoiswatching.js"></script>
	
</head>
<body>
	
	<!-- ======================
		  Header section
	======================= -->
	
	<header id="header">
			
		<nav class="navbar navbar-expand-md lgnavbar-light">
			
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler" aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
				<span class="icon-bar top-bar"></span>
				<span class="icon-bar middle-bar"></span>
				<span class="icon-bar bottom-bar"></span>
			</button>
		
			<a class="navbar-brand" href="./"><img alt="" src="res/drawable/logo3.png"></a>
			
			<c:if test="${login_user != null}">
				<div class="collapse navbar-collapse justify-content-end" id="navbarToggler">
					<div class="navbar-content">
						<form class="form-inline">
							<a class="btn btn-outline-danger" type="button" href=".">Cancel</a>
						</form>
					</div>
				</div>
			</c:if>
			
			<c:if test="${login_user == null}">
			
				<div class="collapse navbar-collapse justify-content-end" id="navbarToggler">
				
					<ul class="navbar-nav">
						<li class="nav-item">
							<a class="nav-link" href="#">Watch Lists</a>
						</li>
						<li class="nav-item active">
							<a class="nav-link" href="account">Account <span class="sr-only">(current)</span></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="billing">Billing</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="billing">Payment</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Edit Profiles</a>
						</li>
					</ul>
					<form class="form-inline my-2 my-lg-0">
						<a class="btn btn-outline-danger" type="button" href="login?logout=true&subject=account">Sign Out</a>
					</form>
					
				</div>
				
			</c:if>
			
		</nav>
			
	</header>

	<section>
		<div class="whoiswatching-wrapper foreground-box <c:if test="${login_user == null}">profile-select</c:if>">
			
			<c:if test="${login_user == null}">
			
				<div id="form-header">
					<h3>Who's Watching?</h3>
					<c:if test="${!account.members.isEmpty() and login_user == null}">
						<div class="note">
						Select a profile to login.
						</div>
					</c:if>
				</div>
				
				<div class="row justify-content-center">
					
					<c:if test="${login_user == null}">
					
						<c:forEach items="${account.members}" var="profile">
							<div class="col-auto clickable">
								
								<c:set var = "profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
								<c:if test="${profile.profileImagePath != null && profile.profileImagePath.length() > 0}">
									<c:set var = "profile_img_src" scope = "request" value = "${profile.profileImagePath}"/>
								</c:if>
								<a href="whoiswatching?action=login&userid=${profile.id}" class="profile-img-button">
									<img alt="" src="${profile_img_src}" class="profile-img rounded-circle">
								</a>
								
								<c:if test="${profile.facebookId != null}">
									<div class="external-profile-icon">
										<i class="fa fa-facebook"></i>
									</div>
								</c:if>
								
								<br>
								<p class="profile-name">${profile.firstName} <c:if test="${profile.isKid}"><span class="badge badge-success">KID</span></c:if> </p>
								
							</div>
						</c:forEach>
						
						<div class="col-auto clickable">
							<a href="addprofile"><img alt="" src="res/drawable/new_profile.png" class="profile-img rounded-circle"></a>
							<br>
							<p class="profile-name">Add Profile</p>
						</div>
						
					</c:if>
					<c:if test="${login_user != null}">
						
						<div class="col-auto">
								
							<c:set var = "profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
							<c:if test="${login_user.profileImagePath != null && login_user.profileImagePath.length() > 0}">
								<c:set var = "profile_img_src" scope = "request" value = "${login_user.profileImagePath}"/>
							</c:if>
							<img alt="" src="${profile_img_src}" class="profile-img rounded-circle clickable">
							
							<br><br>
							<p>${login_user.firstName}</p>
							
						</div>
					
					</c:if>
					
				</div>
				
				<br><br><br>
			
			</c:if>
			
			<c:if test="${account.members.isEmpty() && account.billingReport.active}">
				<div class="note">
				<h5>Welcome <strong>${account.email}</strong>.</h5>
				Before you can start enjoy watching Movies, TV Shows and more, please create your first profile by clicking <i>Add Profile</i>.
				</div>
			</c:if>
			
			<c:if test="${!account.billingReport.active}">
			
				<div class="note-danger">
				<h5><strong><i class="fa fa-exclamation-triangle"></i> Your subscription is out of date!</strong></h5>
				</div>
				<div class="note">
				To continue enjoy watching Movies, TV Shows and more, please check your <a href="billing">billing update information</a> and <a href="billing">payment methods</a>.
				</div>
				
				<div id="existing-user-popup" class="modal" tabindex="-1">
			        <div class="modal-dialog">
			            <div class="modal-content">
			                <div class="modal-header">
			                    <h5 class="modal-title note-danger"><i class="fa fa-exclamation-triangle"></i> Your subscription is out of date!</h5>
			                    <button type="button" class="close" data-dismiss="modal">&times;</button>
			                </div>
			                <div class="modal-body">
			                    <p>To continue enjoy watching Movies, TV Shows and more, please check your <a href="billing">billing update information</a> and <a href="billing">payment methods</a>.</p>
			                    <p class="text-secondary"><small>Need help? <a href="">Contact us</a>.</small></p>
			                </div>
			                <div class="modal-footer">
			                    <button type="button" class="btn btn-main btn-main-light" data-dismiss="modal">Cancel</button>
			                    <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
			                </div>
			            </div>
			        </div>
			    </div>
			    
			</c:if>
			
			<div class="loader d-none"></div>
			
			<c:if test="${login_user != null}">
				<div class="note">
				<h5><strong><i class="fa fa-lock"></i> Protected profile</strong></h5>
				You are trying to login into a protected account. Enter your PIN to access this profile.
				</div>
				
				<c:if test="${login_user != null}">
					<br>
					<div class="row justify-content-center">
						<div class="col-auto">
									
							<c:set var = "profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
							<c:if test="${login_user.profileImagePath != null && login_user.profileImagePath.length() > 0}">
								<c:set var = "profile_img_src" scope = "request" value = "${login_user.profileImagePath}"/>
							</c:if>
							<a href="whoiswatching?action=login" class="profile-img-button"><img alt="" src="${profile_img_src}" class="login-profile-img rounded-circle clickable"></a>
							
							
							
						</div>
						<div class="col-auto" id="login-profile-name"><h6>${login_user.fullName}</h6><p class="note">${login_user.email}</p></div>
					</div>
					
					<br>
					
				</c:if>
					
				<form method="POST" action="whoiswatching?action=login&userid=${login_user.id}" id="profile-insert-password-form">
				
					<div class="form-row form-row-pin">
						<div class="col">
							<!-- <label for="password">Password</label> -->
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-lock"></i></span>
								<input type="password" id="pin" name="pin" class="form-control" placeholder="PIN (4 digits)" required>
								
								<c:if test="${error != null && error.type == 'invalid_login'}">
										
									<div id="invalid-login-popup" class="modal" tabindex="-1">
								        <div class="modal-dialog">
								            <div class="modal-content">
								                <div class="modal-header">
								                    <h5 class="modal-title">Invalid profile access</h5>
								                    <button type="button" class="close" data-dismiss="modal">&times;</button>
								                </div>
								                <div class="modal-body">
								                    <p>Incorrect profile PIN.</p>
								                    <p class="text-secondary"><small>Please enter a correct profile PIN.</small></p>
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
					
					<div>
						<a href="">Forgot password?</a>
					</div>
					
					<div class="profile-insert-password-submit-div">
						<hr class="mb-4">
						
						<div class="form-row btn-form-row">
							<div class="col">
								<button class="btn btn-main" type="submit"><h5>Continue</h5></button>
							</div>
						</div>
					</div>
				
				</form>
				
			</c:if>
			
			<%-- <c:if test="${login_user == null and !account.members.isEmpty()}">
				<div id="account-menu">
					
					
					<a href="" class="light-a">Watchlist</a>
					<hr class="mb-4">
					<a href="" class="light-a">Settings</a>
					<hr class="mb-4">
					<a href="" class="light-a">Account</a>
					<hr class="mb-4">
					<a href="" class="light-a">Legal</a>
					<hr class="mb-4">
					<a href="" class="light-a">Help</a>
					<hr class="mb-4">
					<a href="login?logout=true" class="light-a">Log Out</a>
					<hr class="mb-4">
					<p class="note">Version 1.0</p>
					
				</div>
			</c:if> --%>
			<br><br><br><br>
		
		</div>
	</section>
	
	<c:if test="${login_user != null}">
		<br><br>
		<jsp:include page="footer.html"></jsp:include>
	</c:if>
	
</body>
</html>