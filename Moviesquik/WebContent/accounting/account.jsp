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
	
	<title>Account</title>
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
					<a class="btn btn-outline-danger" type="button" href=".">Back</a>
				</form>
			</div>
		</nav>
			
	</header>
	
	<div id="intro" class="intro-section">
		<div class="signup-wrapper foreground-box" >
			
			<c:if test="${account != null}">
			
				<div id="form-header">
					
					<h4>Account</h4>
					<div class="note">BROWSE YOUR ACCOUNT DETAILS AND BILLING</div>
				</div>
				
				<form method="POST" id="addprofileform" action="addprofile" class="well form-horizontal" onsubmit="return onSubmitUserDataForm()">
				<fieldset>
					
					
					<div class="row">
						<div class="col-10 form-section-text">
							<h6>Account details</h6><br>
						</div>
						<div class="col-2 col-right">
							<a href="">Change</a>
						</div>						
					</div>
					
					<div class="row note">
						<div class="col-6 col-left">
								<p class="p-light"><strong>Email</strong>:</p>
								<p class="p-light"><strong>Password</strong>:</p>
								<p class="p-light"><strong>Family name</strong>:</p>
						</div>
						<div class="col-6 col-right">
								<p class="p-light">${account.email}</p>
								<p class="p-light"><c:forEach var="i" begin="1" end="${account.password.length()}">*</c:forEach></p>
								<p class="p-light">${account.name}</p>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="row">
						<div class="col-10 form-section-text">
							<h6>Profiles</h6><br>
							<p class="note">
							
							<c:if test="${account.members.isEmpty()}">
								No profiles added.
							</c:if>
							
							<c:forEach items="${account.members}" var="profile">
								<div class="row">
									<div class="col-auto col-light-left">
										
										<c:set var = "profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
										<c:if test="${profile.profileImagePath != null && profile.profileImagePath.length() > 0}">
											<c:set var = "profile_img_src" scope = "request" value = "${profile.profileImagePath}"/>
										</c:if>
										<a href="user?id=${profile.id}"><img alt="" src="${profile_img_src}" class="avatar-img card-list-avatar-img rounded-circle"></a>
										
									</div>
									<div class="col-auto col-light-right note">
										<strong>${profile.fullName}</strong> <c:if test="${profile.isKid}"><span class="badge badge-success">KID</span></c:if> <br>
										${profile.email}
									</div>
								</div>
								<br>
							</c:forEach>
							
							</p>
						</div>
						<div class="col-2 col-right">
							<a href="">Change</a>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="row">
						<div class="col-10 form-section-text">
							<h6>Subscription</h6><br>
						</div>
						<div class="col-2 col-right">
							<a href="billing">More</a>
						</div>
					</div>
					
					<div class="row note">
						<div class="col-6 col-left">
								<p class="p-light"><strong>Current Plan</strong>:</p>
								<p class="p-light"><strong>Start Date</strong>:</p>
								<p class="p-light"><strong>Validity Period</strong>:</p>
								<p class="p-light"><strong>Type</strong>:</p>
								<p class="p-light"><strong>Balance</strong>:</p>
						</div>
						<div class="col-6 col-right">
								<p class="p-light"><span class="badge badge-info">${account.billingReport.current.plan}</span></p>
							 	<p class="p-light">${account.billingReport.current.startDateString}</p>
							 	<p class="p-light">${account.billingReport.current.startDateString} - ${account.billingReport.current.endDateString}</p>
							 	<p class="p-light">MONTHLY SUBSCRIPTION</p>
							 	
							 	<c:if test="${account.billingReport.current.trial}"><p class="p-light">Free</p></c:if>
							 	<c:if test="${!account.billingReport.current.trial}"><p class="p-light">$${account.billingReport.current.balance}/month</p></c:if>
							 	
							 	<c:if test="${account.billingReport.current.trial}"><p class="p-light"><span class="badge badge-success">TRIAL</span></p></c:if>
							 	<c:if test="${!account.billingReport.current.trial}"><p class="p-light"><span class="badge badge-primary">PAYED</span></p></c:if>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="row">
						<div class="col-8 col-left form-section-text">
						
							<h6>Auto Update </h6><br>
							<p class="note">
								Your auto update is currently <strong><c:if test="${account.autoUpdate}">active</c:if><c:if test="${!account.autoUpdate}">disabled</c:if></strong>.<br>
								Enable or disable the auto update for the next month at any time.
								<c:if test="${account.autoUpdate}">Your next billing update will be on <strong>${account.billingReport.current.endDateString}</strong></c:if>
							</p>
							
							<c:if test="${account.autoUpdate}"><a class="btn btn-sm btn-outline-danger" type="button" href="billing?action=autoupdate&value=false">Disable</a></c:if>
							<c:if test="${!account.autoUpdate}"><a class="btn btn-sm btn-outline-success" type="button" href="billing?action=autoupdate&value=true">Activate</a></c:if>
							
						</div>
						<div class="col-4 col-right">
							
							<c:if test="${account.autoUpdate}"><span class="badge badge-success">ACTIVE</span></c:if>
							<c:if test="${!account.autoUpdate}"><span class="badge badge-danger">DISABLED</span></c:if>
							
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