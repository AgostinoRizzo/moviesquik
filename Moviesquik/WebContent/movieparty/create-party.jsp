<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>Create Party</title>
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/common.css" rel="stylesheet">
	<link href="css/movieparty.css" rel="stylesheet">
	<link href="css/contents.css" rel="stylesheet">
	<link href="css/media.css" rel="stylesheet">
	<link href="css/home.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	<script src="js/bootstrap-validate.js"></script>
	<script src="js/media/show_media_content.js"></script>
	<script src="js/movieparty/create-party.js" type="module"></script>
	
</head>
<body>

	<!-- ======================
		  Header section
	======================= -->
	
	<jsp:include page="../header.jsp"></jsp:include>
	
	
	<!-- ======================
		  Create Party contents
	======================= -->
	
	<div id="create-party-page-content" class="container">
		
		<div id="create-party-form-header">
			<div class="note">MOVIE PARTY</div>
			<h2>Create a new movie party</h2>
			<h5 class="note">Invite your friends to enjoy watching movies, TV shows and more all together!<br>You will be the administrator of this party.</h5>
		</div>
		
		<form class="well form-horizontal" id="create-party-form" onsubmit="return onSubmitPartyDataForm()" action="movieparty" method="POST">
			<fieldset>
				
				<div class="form-row">
					<div class="col">
						<div class="row user-list-row clickable-light" id="admin-user-item">
								            			
							  <c:if test="${user.profileImagePath != null && user.profileImagePath.length() > 0}">
							  		<a href="user?id=${user.id}" class="col-auto col-light-left"><img src="${user.profileImagePath}" class="avatar-img card-list-avatar-img rounded-circle">
							  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
							  		</a>
							  </c:if>
							  <c:if test="${user.profileImagePath == null || user.profileImagePath.length() == 0}">
							  		<a href="user?id=${user.id}" class="col-auto col-light-left"><img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle">
							  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
							  		</a>
							  </c:if>		  
							  
							  <div class="col users-list-name col-light-right">
							    <p class="users-list-name-text">${user.fullName}</p>
							    <p id="party-administrator-label">Party Administrator</p>
							  </div>
						</div>
					</div>
				</div>
				
				<br><br>
				
				<div class="form-row row">
					<div class="col-4">
						<label for="name">Party Name</label>
						<div class="input-group">
							<input name="name" class="form-control" id="name" required type="text" placeholder="Type a party name here" maxlength="30">
						</div>
					</div>
					<div class="col-8">
						<label for="description">Description&nbsp;&nbsp;<span class="badge badge-warning">Optional</span></label>
						<div class="input-group">
							<input name="description" class="form-control" id="description" type="text" placeholder="Type a party description here">
						</div>
					</div>
				</div>
				
				<br><hr class="mb-4"><br>
				
				<div class="form-row">
					<div class="col">
						<label for="name"><span class="input-group-addon"><span class="fa fa-calendar"></span></span> &nbsp;Event Date</label>
						<p class="note">Starting date of the Movie or TV Show</p><br>
						<div class="input-group">
								
		                		<input name="date" class="form-control" id="date" required type="date">
		                </div>
					</div>
					<div class="col">
						<label for="name"><span class="input-group-addon"><span class="fa fa-clock-o"></span></span> &nbsp;Event Time</label>
						<p class="note">Starting time of the Movie or TV Show</p><br>
						<div class="input-group">
								
		                		<input name="time" class="form-control" id="time" required type="time">
		                </div>
					</div>
				</div>
				
				<br><hr class="mb-4"><br>
				
				<div class="form-row">
					<div class="col">
						<label for="media-content">Movie, TV Show</label>
						<p class="note">Select a Movie or TV Show to watch during the event from your <strong>Watch Later</strong> watch list</p>
						
						<div class="media-contents-list">
							
							<input type="hidden" id="watchlist-id" value="${watchlist.id}">
							
							<c:if test="${!watchlist.items.isEmpty()}">
				
								<c:set var="small_view" scope="request" value="1"/>
								
								<!-- media contents -->
								<div class="media-contents-list-items row" role="listbox">
								    <c:forEach items="${watchlist.items}" var="watchlist_item">					    	
										<c:set var="media_content" scope="request" value="${watchlist_item.mediaContent}"/>
										<div class="col-6 col-sm-5 col-md-4 col-lg-3 col-xl-2 media-col-item">
											<jsp:include page="../contents-item.jsp"></jsp:include>
										</div>
									</c:forEach>
								</div>
							
							</c:if>
							
						</div>
						
					</div>
				</div>
				
				
				
				<br><hr class="mb-4"><br>
				
				<div class="form-row row">
					<div class="col-10 form-section-text">
						<label>Public event</label><br>
						<p class="note" id="public-event-switch-note">
						This event will be <span class="badge badge-info">private</span><br>
						Only invited friends can participate.
						</p>
					</div>
					<div class="col-2 switch-col">
						<label class="switch" id="public-party-switch">
							<input type="checkbox">
							<span class="slider round"></span>
						</label>
					</div>
				</div>
				
			</fieldset>
		</form>
		
		<br><hr class="mb-4"><br>
		
		<div id="guests-selection-form">
		
			<div class="row">
				<div class="col-10 form-section-text">
					<label>Private event guests</label><br>
					<p class="note" id="">
					Select and invite your friends to this private event.
					</p>
				</div>
			</div>
			
			<br>
			
		
			<div class="row">
				<div class="col query-input-col">
						<input name="user-name-query" class="form-control" id="user-name-query" type="text" placeholder="Type a friend name here">
				</div>
				<div class="col query-btn-col">
					<button class="btn btn-success btn-sm" id="user-guests-search-btn"><span class="fa fa-search"></span> Search</button>
				</div>
			</div>
			
			<br><div class="loader loader-sm d-none"></div><br>
			
			<div class="row" id="user-guests-search-result">
			</div>
			
			<hr class="mb-4">
			
			<div class="row">
				<div class="col">
					<label>Invited friends</label><br>
					<p class="note" id="invited-firends-note">
					No invited friends for this event. Please select at least one guest.
					</p>
					<p class="note">
					<strong>All invited friends will receive an invitation for this event.</strong>
					</p>
				</div>
			</div>
			
			<br>
			
			<div class="row" id="invited-users-list">
			</div>
			
			<hr class="mb-4">
			
		</div>
		
		<div class="form-row btn-form-row">
			<div class="col" id="create-party-submit-col">
				<button class="btn btn-success btn-sm" form="create-party-form" type="submit"><span class="fa fa-plus" id="create-party-submit-btn"></span> Create Party</button>
			</div>
		</div>
		
	</div>
	
	<jsp:include page="../footer.html"></jsp:include>
	
</body>
</html>