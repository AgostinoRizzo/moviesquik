<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>${user_to_display.firstName} ${user_to_display.lastName}</title>
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/user-profile.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	
	<!-- Style sheet files -->
	<link href="css/contents.css" rel="stylesheet">
	
	<!-- JavaScript files -->
	<script src="js/contents.js"></script>
	<script src="js/media-contents-filler.js"></script>
	<script src="js/users-filler.js"></script>
	<script src="js/image-upload.js"></script>
	
</head>
<body>

	<!-- ======================
		  Constants
	======================= -->
	
	
	<!-- ======================
		  Header section
	======================= -->
	
	<jsp:include page="../header.jsp"></jsp:include>
	
	<div class="container user-profile-content">

    <div class="row">

	      <!-- User Profile Content Column -->
	      <div class="col-lg-8">
				
				<div class="row">
					<!-- Profile Image res/drawable/user_avatar.jpg -->
					<div class="col-4 col-sm-4 col-md-2">
							
							<c:set var = "user_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
							<c:if test="${user_to_display.profileImagePath != null && user_to_display.profileImagePath.length() > 0}">
								<c:set var = "user_profile_img_src" scope = "request" value = "res/user/${user_to_display.profileImagePath}"/>
							</c:if>
							<img src="${user_profile_img_src}" width="100" height="100" class="rounded-circle user-avatar-image">
					</div>
					
			        <!-- Name -->
			        <div class="col-8 col-sm-8 col-md-10">
				        <h2 class="user-name-box">${user_to_display.firstName} ${user_to_display.lastName}</h2>
				        <p class="lead">
				          <a href="#">${user_to_display.email}</a>
				        </p>
				        <div class="row">
					        <div class="col-2">
								<span class="fa fa-group"></span>
								${user_to_display.followersCount} followers
							</div>
							<div class="col-2">
								<span class="fa fa-comment"></span>
								12 posts
							</div>
							<div class="col-2">
								<span class="fa fa-video-camera"></span>
								12 views
							</div>
						</div>
				        <br>
				        <c:if test="${user_to_display.id != user.id}">
					        <form action="GET">
					        	<button type="submit" name="follow" class="btn btn-main col-4">Follow</button>
					        	<button class="btn btn-light col-4">Message</button>
					        </form>
				        </c:if>
			        
			        </div>
				</div>
	
	        <!-- Date/Time -->
	        <!-- <p>Posted on January 1, 2019 at 12:00 PM</p> -->
	        
	        <!-- Nav pills -->
			<ul class="nav nav-tabs">
				  <c:if test="${user_to_display.id == user.id || has_recently_watched != null}">
					  <li class="nav-item active">
					    <a class="nav-link" data-toggle="tab" href="#showcase"><span class="fa fa-video-camera"></span> Watch</a>
					  </li>
				  </c:if>
				  <li class="nav-item <c:if test="${user_to_display.id != user.id && has_recently_watched == null}">active</c:if>" >
				    <a class="nav-link" data-toggle="tab" href="#posts"><span class="fa fa-comment"></span> Posts</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" data-toggle="tab" href="#info"><span class="fa fa-info"></span> Info</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" data-toggle="tab" href="#family"><span class="fa fa-home"></span> Family (${user_to_display.family.members.size()})</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" data-toggle="tab" href="#followers"><span class="fa fa-group"></span> Followers (${user_to_display.followersCount})</a>
				  </li>
				  
				  <c:if test="${user_to_display.id == user.id}">
					  <li class="nav-item">
					    <a class="nav-link" data-toggle="tab" href="#edit"><span class="fa fa-edit"></span> Edit</a>
					  </li>
				  </c:if>
			</ul>
			
			<!-- Tab panes -->
			<div class="tab-content">
			  
			  <div class="tab-pane container active" id="showcase">
			  		<c:if test="${user_to_display.id == user.id}">

						<div class="media-contents-list">
					
							<div class="media-contents-list-header">
									<div class="media-contents-icon-title"><h3 class="media-contents-list-header-title"><!-- <span class="fa fa-lightbulb-o checked"></span>  -->Suggested for you</h3></div>
									<div class="view-all-contents">
										<button id="suggested-view-all-btn" class="btn btn-link view-all-btn invisible">View All</button>
									</div>
							</div>
							<br>
							<!-- <hr class="mb-4"> -->
							
							<!-- Top content -->
						    <div id="suggested-media-contents-items" class="media-contents-list-items row" role="listbox">
						        <jsp:include page="../contents-items-sm.jsp"></jsp:include>
						    </div>
							
							<div class="more-collapse-box d-none">
								<button id="suggested-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
								<button id="suggested-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
							</div>
						</div>
					
					</c:if>
					
					<c:if test="${has_recently_watched != null}">
						<div class="media-contents-list">
					
							<div class="media-contents-list-header">
									<div class="media-contents-icon-title"><h3 class="media-contents-list-header-title"><!-- <span class="fa fa-lightbulb-o checked"></span>  -->Recently watched</h3></div>
									<div class="view-all-contents">
										<button id="recently-view-all-btn" class="btn btn-link view-all-btn invisible">View All</button>
									</div>
							</div>
							<br>
							<!-- <hr class="mb-4"> -->
							
							<!-- Top content -->
						    <div id="recently-media-contents-items" class="media-contents-list-items row" role="listbox">
						        <jsp:include page="../contents-items-sm.jsp"></jsp:include>
						    </div>
							
							<div class="more-collapse-box d-none">
								<button id="recently-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
								<button id="recently-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
							</div>
						</div>
					
					</c:if>
			  </div>
			  
			  <div class="tab-pane container fade" id="posts">
			  		<div class="showcase-header-title"><h3>Showcase</h3></div>
	        
			        <c:if test="${user_to_display.id == user.id}">
				        <!-- Comments Form -->
				        <div class="card bg-dark text-white my-4">
				          <h5 class="card-header">Leave a Comment:</h5>
				          <div class="card-body">
				            <form>
				              <div class="form-group">
				                <textarea class="form-control" rows="3"></textarea>
				              </div>
				              <button class="btn btn-primary" type="submit">Submit</button>
				            </form>
				          </div>
				        </div>
					</c:if>
					
			        <!-- Single Comment -->
			        <div class="media mb-4">
			          <img class="d-flex mr-3 rounded-circle" alt="" src="http://placehold.it/50x50">
			          <div class="media-body">
			            <h5 class="mt-0">Commenter Name</h5>
			            Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
			          </div>
			        </div>
			
			        <!-- Comment with nested comments -->
			        <div class="media mb-4">
			          <img class="d-flex mr-3 rounded-circle" alt="" src="http://placehold.it/50x50">
			          <div class="media-body">
			            <h5 class="mt-0">Commenter Name</h5>
			            Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
			
			            <div class="media mt-4">
			              <img class="d-flex mr-3 rounded-circle" alt="" src="http://placehold.it/50x50">
			              <div class="media-body">
			                <h5 class="mt-0">Commenter Name</h5>
			                Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
			              </div>
			            </div>
			
			            <div class="media mt-4">
			              <img class="d-flex mr-3 rounded-circle" alt="" src="http://placehold.it/50x50">
			              <div class="media-body">
			                <h5 class="mt-0">Commenter Name</h5>
			                Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
			              </div>
			            </div>
			
			          </div>
			        </div>
			  </div>
			  
			  <div class="tab-pane container fade" id="info">
			  		<jsp:include page="user-info.jsp"></jsp:include>
			  </div>
			  
			  
			  <div class="tab-pane container fade" id="family">
			  		
			        <h5 class="">${user_to_display.family.name}</h5>
			        
	          		<div class="row members-row">
		            	<c:forEach items="${user_to_display.family.members}" var="member">
		            	
		            		<div class="media">
								  <img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle">
								  
								  <div class="media-body">
								    <h4>${member.firstName} ${member.lastName} <small class="is-master-text"><i>master</i></small></h4>
								    <p><a href="#">${member.email}</a></p>
								  </div>
							</div>
							
						</c:forEach>
					</div>
					
			  </div>
			  
			  <div class="tab-pane container fade" id="followers">
			        
	          		<div class="row members-row">
		            	<c:forEach items="${user_to_display.friends}" var="friend">
		            	
		            		<div class="media">
								  <img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle">
								  
								  <div class="media-body">
								    <h4>${friend.firstName} ${friend.lastName}</h4>
								    <p><a href="#">${friend.email}</a></p>
								  </div>
							</div>
							
						</c:forEach>
					</div>
					
			  </div>
			  
			  <c:if test="${user_to_display.id == user.id}">
				  <div class="tab-pane container fade" id="edit">
				  		<jsp:include page="user-profile-edit.jsp"></jsp:include>
				  </div>
			  </c:if>
		
			</div>
	
	      </div>
	
	      <!-- Sidebar Widgets Column -->
	      <div class="col-md-4">
	
	        <!-- Search Widget -->
	        <div class="card bg-dark text-white my-4">
	          <h5 class="card-header">Search</h5>
	          <div class="card-body">
	            <div class="input-group">
	              <input class="form-control" type="text" placeholder="Search for...">
	              <span class="input-group-btn">
	                <button class="btn btn-secondary" type="button">Go!</button>
	              </span>
	            </div>
	          </div>
	        </div>
	
	        <!-- Genres Widget -->
	        <div class="card bg-dark text-white my-4">
	          <h5 class="card-header"><span class="fa fa-film"></span> Favorites Genres (${user_to_display.favoritesGenres.size()})</h5>
	          <div class="card-body">
	            <div class="row">
	            
	            	<c:forEach items="${user_to_display.favoritesGenres}" var="genre">
			            	<div class="col-lg-4">
									<a href="#">${genre}</a>
							</div>
					</c:forEach>
	              
	            </div>
	          </div>
	        </div>
			
			<!-- Family card -->
	        <div class="card bg-dark text-white my-4">
	          <h5 class="card-header"></h5>
	          <div class="card-body">
	          		<div id="family-box" class="row">
		            	
					</div>
	          </div>
	        </div>
	        
	        <!-- Friends card -->
	        <div class="card bg-dark text-white my-4">
	          <h5 class="card-header"><span class="fa fa-group"></span> Followers (${user_to_display.followersCount})</h5>
	          <div class="card-body">
	          		<div id="friends-box" class="row">
	          			
	          			<c:forEach items="${user_to_display.friends}" var="friend">
			            	<div class="col-3 user-col-item">
									<a href="#" data-toggle="tooltip" data-placement="top" title="${friend.firstName} ${friend.lastName}">
										<img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle">
									</a>
									<p>${friend.firstName} ${friend.lastName}</p>
							</div>
						</c:forEach>
						
					</div>
	          </div>
	        </div>
	
	      </div>
	
	    </div>
	    <!-- /.row -->

  </div>
	
</body>
</html>