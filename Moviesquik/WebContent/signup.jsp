<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>Sign Up</title>
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/signup.css" rel="stylesheet">
	<link href="css/glyphicon.css" rel="stylesheet">
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	
	<!-- <link href="bootstrap-3.3.7-dist\css\bootstrap.min.css" rel="stylesheet">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js"></script> -->
	<script src="js/bootstrap-validate.js"></script>
	<script src="js/validation.js"></script>
	<script src="js/popup.js"></script>
	
</head>
<body>

	<!-- ======================
		  Header section
	======================= -->
	
	<header id="header" class="header-always-fixed foreground-box singup-header">
			
		<nav class="navbar navbar-light">
			<a class="navbar-brand" href="./"><img alt="" src="res/drawable/logo.png" height="50px"></a>
			<div class="navbar-content">
				<form class="form-inline">
					<a class="btn btn-outline-danger" type="button" href="registration?cancel=true">Cancel</a>
				</form>
			</div>
		</nav>
			
	</header>
	
	<section id="intro">
		<div class="signup-wrapper" id=<c:if test="${new_user != null and plan == null}">"plans-wrapper"</c:if> >
			
			<c:if test="${new_user == null}">
			
				<div id="form-header">
					<div class="note">STEP 1 OF 3</div>
					<h2>Sign up to start your free month.</h2>
					<h5 class="note">Just two more steps and you're finished!<br>We hate paperwork, too.</h5>
				</div>
				
				<form method="POST" id="signupform" action="registration" class="well form-horizontal" onsubmit="return onSubmitUserDataForm()">
				<fieldset>
					
					<div class="form-row">
						<div class="col">
							<label for="first_name" class="control-label">First name</label>
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
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
					
					<div class="form-row">
						<div class="col">
							<label for="email">Email address</label>
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
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
							<select class="custom-select" title="ignore" name="gender">
								<option value="female">Female</option>
								<option value="male">Male</option>
								<option value="ignore">Ignore</option>
							</select>
						</div>
					</div>
					
					<div class="form-row">
						<div class="col">
							<label for="password">Choose a password (8-40 characters)</label>
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
							</div>
						</div>
					</div>
					<div class="form-row">
						<div class="col">
							<label for="confirm_password">Confirm Password</label>
							<div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								<input type="password" id="confirm_password" name="confirm_password" class="form-control" placeholder="Confirm password" required>
							</div>
						</div>
					</div>
					
					<div class="form-row">
						<div class="col">
							<div class="form-check form-check-inline">
								<input id="agree_checkbox" type="checkbox">
								<label for="agree_checkbox">By creating an account, you agree to Moviesquik's <a href="">Condition of use</a> and <a href="">Privacy</a>.</label>
							</div>
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="form-row btn-form-row">
						<div class="col">
							<button class="btn btn-main btn-main-light" type="submit"><h4>Continue</h4></button>
						</div>
					</div>
					
				</fieldset>
				</form>
			
			</c:if>
			<c:if test="${new_user != null and plan == null}">
				
				<div id="form-header">
					<div class="note">STEP 2 OF 3</div>
					<h2>Choose a plan that's right for you.</h2>
					<h5 class="note">Downgrade or upgrade at any time.</h5>
				</div>
				
				<form method="GET" action="registration">
				<div class="card-deck mb-3 text-center">
			        <div class="card mb-4 box-shadow">
			          <div class="card-header">
			            <h4 class="my-0 font-weight-normal">Basic</h4>
			          </div>
			          <div class="card-body">
			            <h1 class="card-title pricing-card-title">$8.99 <small class="text-muted">/ mo</small></h1>
			            <ul class="list-unstyled mt-3 mb-4">
			              <li>10 users included</li>
			              <li>2 GB of storage</li>
			              <li>Email support</li>
			              <li>Help center access</li>
			            </ul>
			            <button class="btn btn-main btn-main-light" type="submit" name="basic"><h4>Choose basic</h4></button>
			          </div>
			        </div>
			        <div class="card mb-4 box-shadow">
			          <div class="card-header">
			            <h4 class="my-0 font-weight-normal">Standard</h4>
			          </div>
			          <div class="card-body">
			            <h1 class="card-title pricing-card-title">$12.99 <small class="text-muted">/ mo</small></h1>
			            <ul class="list-unstyled mt-3 mb-4">
			              <li>20 users included</li>
			              <li>10 GB of storage</li>
			              <li>Priority email support</li>
			              <li>Help center access</li>
			            </ul>
			            <button class="btn btn-main btn-main-light" type="submit" name="standard"><h4>Choose standard</h4></button>
			          </div>
			        </div>
			        <div class="card mb-4 box-shadow">
			          <div class="card-header">
			            <h4 class="my-0 font-weight-normal">Premium</h4>
			          </div>
			          <div class="card-body">
			            <h1 class="card-title pricing-card-title">$15.99 <small class="text-muted">/ mo</small></h1>
			            <ul class="list-unstyled mt-3 mb-4">
			              <li>30 users included</li>
			              <li>15 GB of storage</li>
			              <li>Phone and email support</li>
			              <li>Help center access</li>
			            </ul>
			            <button class="btn btn-main btn-main-light" type="submit" name="premium"><h4>Choose premium</h4></button>
			          </div>
			        </div>
				</div>
				</form>
				
			</c:if>
			
			<c:if test="${new_user != null and plan != null}">
				
				<c:if test="${invalid_credit_card != null}">
										
					<div id="invalid-card-popup" class="modal" tabindex="-1">
				        <div class="modal-dialog">
				            <div class="modal-content">
				                <div class="modal-header">
				                    <h5 class="modal-title">Invalid credit/debit card</h5>
				                    <button type="button" class="close" data-dismiss="modal">&times;</button>
				                </div>
				                <div class="modal-body">
				                    <p>The provided credit/debit card is invalid or is already used.</p>
				                    <p class="text-secondary"><small>Please setup a new credit/debit card.</small></p>
				                </div>
				                <div class="modal-footer">
				                    <button type="button" class="btn btn-main btn-main-light" data-dismiss="modal">Cancel</button>
				                    <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
				                </div>
				            </div>
				        </div>
				    </div>
										  										
				</c:if>
				
				<div id="form-header" class="form-header-payment">
					<div class="note">STEP 3 OF 3</div>
					<h2>Setup your credit or debit card.</h2>
				</div>
				
				<form method="POST" action="registration" onsubmit="return onSubmitCreditCardDataForm()">
					<div class="payment-methods-img">
						<img alt="" src="res/drawable/payment_methods.png" height="70px">
					</div>
					<div class="d-block my-3">
		              <div class="custom-control custom-radio">
		                <input name="paymentMethod" class="custom-control-input" id="credit" required type="radio" checked>
		                <label class="custom-control-label" for="credit">Credit card</label>
		              </div>
		              <div class="custom-control custom-radio">
		                <input name="paymentMethod" class="custom-control-input" id="debit" required type="radio">
		                <label class="custom-control-label" for="debit">Debit card</label>
		              </div>
		              <div class="custom-control custom-radio">
		                <input name="paymentMethod" class="custom-control-input" id="paypal" required type="radio">
		                <label class="custom-control-label" for="paypal">Paypal</label>
		              </div>
		            </div>
		            
		            <div class="form-row">
		              <div class="col">
		                <label for="cc-name">Name on card</label>
		                <div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
		                		<input class="form-control" id="cc-name" name="cc-name" type="text" required placeholder="Full name as displayed on card">
		                </div>
		              </div>
		            </div>
		            <div class="form-row">
		              <div class="col">
		                <label for="cc-number">Credit card number (16 digits)</label>
		                <div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-credit-card"></i></span>
		                		<input class="form-control" id="cc-number" name="cc-number" required type="number" placeholder="Credit card number">
		                </div>
		              </div>
		            </div>
		            
		            <div class="form-row">
		              <div class="col-md-5 mb-3">
		                <label for="cc-expiration">Expiration</label>
		                <div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
		                		<input class="form-control" id="cc-expiration-month" name="cc-expiration-month" required type="month" value="2019-12" placeholder="Month">
		                </div>
		              </div>
		              <div class="col-md-3 mb-3">
		                <label for="cc-expiration">CVV</label>
		                <div class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
		                		<input class="form-control" id="cc-cvv" name="cc-cvv" required type="number" placeholder="CVV">
		                </div>
		              </div>
		            </div>
		            
		            <hr class="mb-4">
		            By ticking the tickbox below, you agree to our <a href="">Terms of Use</a> and <a href="">Privacy Statement</a>. You may cancel at any time during your free trial and will
		            not be charged. Moviesquik will automatically continue your membership at the end of your free trial and change the membership fee to your payment method on a monthly basis
		            until you cancel.<br>
		            <div class="form-row">
						<div class="col">
							<div class="form-check form-check-inline">
								<input id="agree_checkbox" type="checkbox">
								<label for="agree_checkbox">I agree.</label>
							</div>
						</div>
					</div>
		            <hr class="mb-4">
		            
		            <button class="btn btn-main btn-main-light" type="submit"><h4>START MEMBERSHIP</h4></button>
				</form>
				
			</c:if>
			
		</div>
	</section>
	
</body>
</html>