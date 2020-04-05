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
	<link href="css/plans.css" rel="stylesheet">
	<link href="css/posting.css" rel="stylesheet">
	<link href="css/searching.css" rel="stylesheet">
	<link href="css/media.css" rel="stylesheet">
	<link href="css/home.css" rel="stylesheet">
	<link href="css/contents.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="lib/typeahead/bootstrap3-typeahead.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	<script src="js/posting.js"></script>
	<script src="js/commenting.js"></script>
	<script src="js/send-feedback.js"></script>
	<script src="js/more-news.js"></script>
	<script src="js/notifications.js"></script>
	<script src="js/media/show_media_content.js"></script>
	<script src="js/contents.js"></script>
	<script src="js/media-contents-filler.js"></script>
	
</head>
<body>

	<!-- ======================
		  Header section
	======================= -->
	
	<jsp:include page="header.jsp"></jsp:include>

	<div id="page-content">
	
		<!--==========================
	    	  Intro Section
	    ============================-->
    
	    <!-- News Contents -->
	    
	    <%-- <jsp:include page="contents-news.jsp"></jsp:include> --%>
	    
	    <!-- Home Section -->
	    <c:if test="${user != null}">
		    <jsp:include page="home/home.jsp"></jsp:include>
	    </c:if>
	    
	    <c:if test="${user == null}">
	    <section id="intro">
	    
			<div class="intro-text">
			
				<c:if test="${user == null}">
				
					<h1 class="main-text"><strong>Movies, TV shows and more.</strong></h1>
					<p>Watch what you want. Wherever you want. With anyone you want.</p>
					
					<div>
						<form class="form-inline" id="join_form">
							<a id="join_btn" href="registration" class="btn btn-outline-primary scrollto">JOIN FREE FOR A MONTH</a>
			      		</form>
			      	</div>
				
				</c:if>
				
				<!-- MediaContent of the day -->
				<%-- <c:if test="${media_content_of_the_day != null}">
				
					<c:if test="${user != null}">
						
						<div class="intro-watch">
							<h1 class="main-text"><strong>${media_content_of_the_day.title}</strong></h1>
							<p>${media_content_of_the_day.production}.</p>
							<p>${media_content_of_the_day.plot}.</p>
							
							<div>
								<form class="form-inline">
									<div class="btn-group">
										<a href="registration" class="btn btn-outline-success"><i class="fa fa-play"></i> WATCH</a>
										<a href="#" class="btn btn-outline-warning"><i class="fa fa-plus"></i> Add</a>
									</div>
									<div id="btn-group-info" class="btn-group">
										<a href="registration" class="btn btn-outline-secondary"><i class="fa fa-info"></i> More Info</a>
									</div>
					      		</form>
					      	</div>
				      	</div>
				    
					</c:if>
				
					<div class="intro-movie-description">
							<div>
								<c:if test="${user == null}"><p>${media_content_of_the_day.title}</p></c:if>
								<jsp:include page="content-ratings.html"></jsp:include>
								&nbsp; &nbsp; ${media_content_of_the_day.genre}
							</div>
					</div>
				
				</c:if> --%>
				<!-- /MediaContent of the day -->
				
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
		
		
		<jsp:include page="contents.jsp"></jsp:include>
		
		<div class="pricing-section">
			<div class="pricing-header">
				<div class="note">TRY IT FREE FOR A MONTH</div>
				<h2>Choose a plan that's right for you.</h2>
				<h5 class="note">Downgrade or upgrade at any time.</h5>
			</div>
			<div class="pricing-body">
				<jsp:include page="pricing.jsp"></jsp:include>
			</div>
		</div>
	
		<jsp:include page="footer.html"></jsp:include>
		
		</c:if>
		
	</div>
	
</body>
</html>