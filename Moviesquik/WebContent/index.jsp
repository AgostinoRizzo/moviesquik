<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>Moviesquik</title>
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/intro.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	
</head>
<body>

	<!-- ======================
		  Header section
	======================= -->
	
	<header id="header">
			
		<nav class="navbar navbar-expand-lg navbar-light">
			
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler" aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
				<span class="icon-bar top-bar"></span>
				<span class="icon-bar middle-bar"></span>
				<span class="icon-bar bottom-bar"></span>
			</button>
			
			<a class="navbar-brand" href="./"><img alt="" src="res/drawable/logo3.png"></a>
			
			<div class="collapse navbar-collapse justify-content-end" id="navbarToggler">
			
				<c:if test="${user == null}">
				
					<ul class="navbar-nav">
						<li class="nav-item active">
							<a class="nav-link" href="#">About <span class="sr-only">(current)</span></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Prices</a>
						</li>
						<li class="nav-item">
							<a class="nav-link disabled" href="registration">Sign Up</a>
						</li>
					</ul>
					<form class="form-inline my-2 my-lg-0">
						<button class="btn btn-main" type="button" onclick="window.location.href='login'">Sign In</button>
					</form>
				
				</c:if>
				
				<c:if test="${user != null}">
					
					<ul class="navbar-nav">
				
							<li class="nav-item active">
								<a class="nav-link" href="#">About <span class="sr-only">(current)</span></a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="#">Prices</a>
							</li>
							<li class="nav-item">
								<a class="nav-link disabled" href="registration">Sign Up</a>
							</li>
							
					</ul>
					
					<ul id="navbar-nav-user" class="navbar-nav">
							
							<li class="nav-item dropdown">
						        <a id="nav-user-avatar-box" class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						          <img src="res/drawable/user_avatar.jpg" width="40" height="40" class="rounded-circle">
						        </a>
						        <div id="nav-user-avatar-dropdown-menu" class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
						        	<a class="dropdown-item" href="#"><small>Signed in as</small><br><strong>${user.firstName} ${user.lastName}</strong></a>
						        	<div class="dropdown-divider"></div>
						        	<a class="dropdown-item" href="#">Dashboard</a>
						        	<a class="dropdown-item" href="#">Edit Profile</a>
						        	<div class="dropdown-divider"></div>
						        	<a class="dropdown-item" href="login?logout=true">Sign Out</a>
						        </div>
						    </li>
						      
					</ul>
					
				</c:if>
				
			</div>
		</nav>
			
	</header>
	
	
	<!--==========================
    	  Intro Section
    ============================-->
    
    <section id="intro">

		<div class="intro-text">
			<h1 class="main-text"><strong>Movies, TV shows and more.</strong></h1>
			<p>Watch what you want. Wherever you want. With anyone you want.</p>
			
			<div>
				<form class="form-inline" id="join_form">
					<a id="join_btn" href="registration" class="btn btn-outline-primary scrollto">JOIN FREE FOR A MONTH</a>
	      		</form>
	      	</div>
	      	
	      	<footer class="intro-movie-description fixed-bottom">
				<div>
					<img id="rate-star-img" alt="" src="res/drawable/rate_star.png">
					<img id="rate-star-img" alt="" src="res/drawable/rate_star.png">
					<img id="rate-star-img" alt="" src="res/drawable/rate_star.png">
					<img id="rate-star-img" alt="" src="res/drawable/rate_star.png">
					<img id="rate-star-img" alt="" src="res/drawable/rate_star_empty.png">
					&nbsp; 4.3 &nbsp; &nbsp; Animation &nbsp; | &nbsp; Adventure
				</div>
			</footer>
		</div>
		
		<!-- <div class="content-screens" id="slide">

			<div class="content-screen-1 content-screen wow fadeInUp" data-wow-delay="0.4s" data-wow-duration="0.6s" id="cont1">
        		<img src="res/drawable/product_wallpaper.png" alt="" height="200px">
      		</div>
			
			<div class="content-screen-2 content-screen wow fadeInUp" data-wow-delay="0.4s" data-wow-duration="0.6s" id="cont2">
        		<img src="res/drawable/product_wallpaper.png" alt="" height="200px">
      		</div>
      		
      		<div class="content-screen-3 content-screen wow fadeInUp" data-wow-delay="0.4s" data-wow-duration="0.6s" id="cont3">
        		<img src="res/drawable/product_wallpaper.png" alt="" height="200px">
      		</div>

    	</div> -->

	</section>

</body>
</html>