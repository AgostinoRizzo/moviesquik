<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>Add Profile</title>
	
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
	<script src="js/bootstrap-validate.js"></script>
	<script src="js/validation.js"></script>
	<script src="js/popup.js"></script>
	<script src="js/registration/addprofile.js"></script>
	
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
					<a class="btn btn-outline-danger" type="button" href=".">Cancel</a>
				</form>
			</div>
		</nav>
			
	</header>
	
	<div id="intro" class="intro-section">
		<div class="signup-wrapper foreground-box" id=<c:if test="${new_user != null and plan == null}">"plans-wrapper"</c:if> >
			
			<c:if test="${account != null}">
			
				<div id="form-header">
					<div class="note">ADD NEW PROFILE</div>
					<h4>Sign up to start enjoy watching Movies, TV Shows and more from your personal profile.</h4>
				</div>
				
				<form method="POST" id="addprofileform" action="addprofile" class="well form-horizontal" onsubmit="return onSubmitUserDataForm()">
				<fieldset>
					
					<div class="form-row">
						<div class="col">
							<label for="first_name" class="control-label">First name</label>
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user"></i></span>
								<input type="text" id="first_name" name="first_name" class="form-control" placeholder="First name" required>
							</div>
						</div>
						<div class="col">
							<div class="form-group form-group-inline">
								<label for="last_name" class="control-label">Last name</label>
								<div>
									<input type="text" id="last_name" name="last_name" class="form-control" placeholder="Last name" required>
								</div>
							</div>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="row">
						<div class="col-10 form-section-text">
							<h6>Kids profile</h6><br>
							<p class="note">
							Kid friendly interface with only content suitable for kids.<br>
							No personal email address and profile protection required.
							</p>
						</div>
						<div class="col-2">
							<label class="switch" id="kids-profile-switch">
								<input type="checkbox">
								<span class="slider round"></span>
							</label>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="row">
						<div class="col-10 form-section-text">
							<h6>Personal Email address</h6><br>
							<p class="note">
							By adding a personal email address, you will personally receive all information and communications about you.<br>
							Otherwise, the account email will be used.
							</p>
						</div>
						<div class="col-2">
							<label class="switch" id="personal-email-switch">
								<input type="checkbox">
								<span class="slider round"></span>
							</label>
						</div>
					</div>
					
					<div class="form-row" id="personal-email-form">
						<div class="col">
							<label for="email">Email address</label>
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
								<input type="text" id="email" name="email" class="form-control" placeholder="email@example.com" required>
								
								<c:if test="${existing_user != null}">
										
										<div id="existing-user-popup" class="modal" tabindex="-1">
									        <div class="modal-dialog">
									            <div class="modal-content">
									                <div class="modal-header">
									                    <h5 class="modal-title">Existing user</h5>
									                    <button type="button" class="close" data-dismiss="modal">&times;</button>
									                </div>
									                <div class="modal-body">
									                    <p>The provided email address is already used.</p>
									                    <p class="text-secondary"><small>Please enter a new email address.</small></p>
									                </div>
									                <div class="modal-footer">
									                    <button type="button" class="btn btn-main btn-main-light" data-dismiss="modal">Cancel</button>
									                    <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
									                </div>
									            </div>
									        </div>
									    </div>
										  										
								</c:if>
								
							</div>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="form-row">
						<div class="col">
							<label for="birthday">Birthday</label>
							<div>
								<input type="date" id="birthday" name="birthday" class="form-control" required>
							</div>
						</div>
						<div class="col">
							<!-- <div class="form-check form-check-inline">
								<div class="form-radio-box">
									<input class="form-check-input" type="checkbox" value="female" checked>
									<label class="form-check-label">Female</label>
								</div>
								<div class="form-radio-box">
									<input class="form-check-input" type="radio" value="male">
									<label class="form-check-label">Male</label>
								</div>
							</div> -->
							<label for="email">Gender</label>
							<select class="custom-select" name="gender">
								<option value="female">Female</option>
								<option value="male">Male</option>
								<option value="ignore" selected>Ignore</option>
							</select>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="row">
						<div class="col-10 form-section-text">
							<h6>Protect your Profile</h6><br>
							<p class="note">
							By protecting your Profile, only you can access to it.<br>
							No other family component will have access.
							</p>
						</div>
						<div class="col-2">
							<label class="switch" id="profile-protection-switch">
								<input type="checkbox">
								<span class="slider round"></span>
							</label>
						</div>
					</div>
					
					<div id="profile-password-form">
					<div class="form-row">
						<div class="col">
							<label for="pin">Choose a PIN (4 digits)</label>
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-lock"></i></span>
								<input type="password" id="pin" name="pin" class="form-control" placeholder="PIN" required>
							</div>
						</div>
					</div>
					<div class="form-row">
						<div class="col">
							<label for="confirm_pin">Confirm PIN</label>
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-lock"></i></span>
								<input type="password" id="confirm_pin" name="confirm_pin" class="form-control" placeholder="Confirm PIN" required>
							</div>
						</div>
					</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="form-row">
						<div class="col">
							<div class="form-check form-check-inline">
								<input id="agree_checkbox" type="checkbox">
								<label for="agree_checkbox">By creating a new profile, you agree to Moviesquik's <a href="">Condition of use</a> and <a href="">Privacy</a>.</label>
							</div>
						</div>
					</div>
					
					<!-- <hr class="mb-4"> -->
					
					<div class="form-row btn-form-row">
						<div class="col">
							<button class="btn btn-main" type="submit"><h4>Create Profile</h4></button>
						</div>
					</div>
					
				</fieldset>
				</form>
			
			</c:if>
			
		</div>
	</div>
	
	<jsp:include page="../footer.html"></jsp:include>
	
</body>
</html>