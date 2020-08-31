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
	
	<title>Payment - Billing</title>
	<link rel="icon" type="image/x-icon" href="res/drawable/m_logo.png">
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/common.css" rel="stylesheet">
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
					
					<h4>Payment - Billing details</h4>
					<div class="note">BROWSE PAYMENT INFO AND BILLINGS DETAILS</div>
				</div>
				
				<form method="POST" id="addprofileform" action="addprofile" class="well form-horizontal" onsubmit="return onSubmitUserDataForm()">
				<fieldset>
					
					
					<div class="row">
						<div class="col-6 form-section-text">
							<h6>Credit/Debit Card</h6><br>
						</div>
						<div class="col-6 col-right">
							<!-- <a href="">Change</a> -->
						</div>
					</div>
					
					<div class="row note">
						<div class="col-6 col-left">
								<strong>Full name</strong>:<br>
								<strong>Card number</strong>: <br>
								<strong>Expiration</strong>:<br>
								<strong>CVV</strong>:<br>
						</div>
						<div class="col-6 col-right">
								${account.creditCard.name}<br>
								${account.creditCard.number.substring(0, 8)}<c:forEach var="i" begin="1" end="8">*</c:forEach><br>
								${account.creditCard.expirationString}<br>
								${account.creditCard.cvv.charAt(0)}**<br>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="row">
						<div class="col-10 form-section-text">
							<h6>Current Billing</h6><br>
						</div>
					</div>
					
					<div class="row note">
						<div class="col-6 col-left">
								<strong>Plan</strong>:<br>
								<strong>Start Date</strong>:<br>
								<strong>Validity Period</strong>:<br>
								<strong>Type</strong>:<br>
								<strong>Balance</strong>:<br>
						</div>
						<div class="col-6 col-right">
								<span class="badge badge-info">${account.billingReport.current.plan}</span><br>
							 	${account.billingReport.current.startDateString}<br>
							 	${account.billingReport.current.startDateString} - ${account.billingReport.current.endDateString}<br>
							 	MONTHLY SUBSCRIPTION<br>
							 	
							 	<c:if test="${account.billingReport.current.trial}">Free</c:if>
							 	<c:if test="${!account.billingReport.current.trial}">$${account.billingReport.current.balance}/month</c:if><br>
							 	
							 	<c:if test="${account.billingReport.current.trial}"><span class="badge badge-success">TRIAL</span></c:if>
							 	<c:if test="${!account.billingReport.current.trial}"><span class="badge badge-primary">PAYED</span></c:if><br>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="row">
						<div class="col-6 form-section-text">
							<h6>Next Billing Update</h6><br>
						</div>
						<div class="col-6 col-right">
							<a href="billing?action=changeplan">Change</a>
						</div>
					</div>
					
					<div class="row note">
						<div class="col-6 col-left">
								<strong>Plan</strong>:<br>
								<strong>Virtual Start Date</strong>:<br>
								<!-- <strong>Period</strong>:<br> -->
								<strong>Type</strong>:<br>
								<strong>Balance</strong>:<br>
						</div>
						<div class="col-6 col-right">
								<span class="badge badge-info">${account.billingReport.nextUpdate.plan}</span><br>
							 	${account.billingReport.current.endDateString}<br>
							 	<%-- ${account.billingReport.nextUpdate.startDateString}<br> --%>
							 	MONTHLY SUBSCRIPTION<br>
							 	
							 	<c:if test="${account.billingReport.nextUpdate.trial}">Free</c:if>
							 	<c:if test="${!account.billingReport.nextUpdate.trial}">$${account.billingReport.nextUpdate.balance}/month</c:if><br>
							 	
							 	<c:if test="${account.billingReport.nextUpdate.trial}"><span class="badge badge-success">TRIAL</span></c:if>
							 	<c:if test="${!account.billingReport.nextUpdate.trial}"><span class="badge badge-warning">TO PAY</span></c:if><br>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="row tile info-tile">
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
					
					<hr class="mb-4">
					
					<div class="row">
						<div class="col-10 form-section-text">
							<h6>Billing History</h6><br>
						</div>
					</div>
					
					<c:if test="${account.billingReport.history.isEmpty()}">
						<div class="row note">
							<div class="col-6 col-left">No previous billings.</div>
						</div>
					</c:if>
					
					<c:if test="${!account.billingReport.history.isEmpty()}">
						<c:forEach items="${account.billingReport.history}" var="previous_billing">
								<div class="row note tile info-tile">
									<div class="col-6 col-left">
										<strong>Plan</strong>:<br>
										<strong>Start Date</strong>:<br>
										<strong>Validity Period</strong>:<br>
										<strong>Type</strong>:<br>
										<strong>Balance</strong>:<br>
									</div>
									<div class="col-6 col-right">
											<span class="badge badge-info">${previous_billing.plan}</span><br>
										 	${previous_billing.startDateString}<br>
										 	${previous_billing.startDateString} - ${previous_billing.endDateString}<br>
										 	MONTHLY SUBSCRIPTION<br>
										 	
										 	<c:if test="${previous_billing.trial}">Free</c:if>
										 	<c:if test="${!previous_billing.trial}">$${previous_billing.balance}/month</c:if><br>
										 	
										 	<c:if test="${previous_billing.trial}"><span class="badge badge-success">TRIAL</span></c:if>
										 	<c:if test="${!previous_billing.trial}"><span class="badge badge-primary">PAYED</span></c:if><br>
									</div>
								</div><br>
						</c:forEach>
					</c:if>
					
				</fieldset>
				</form>
			
			</c:if>
			
		</div>
	</div>
	
	<jsp:include page="../footer.html"></jsp:include>
	
</body>
</html>