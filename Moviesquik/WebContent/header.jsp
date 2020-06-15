<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

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
		
		<div class="collapse navbar-collapse justify-content-end" id="navbarToggler">
		
			<c:if test="${user == null}">
			
				<ul class="navbar-nav">
					<li class="nav-item active">
						<a class="nav-link" href="#">About <span class="sr-only">(current)</span></a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#pricing-section">Prices</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="registration">Sign Up</a>
					</li>
				</ul>
				<form class="form-inline my-2 my-lg-0">
					<button class="btn btn-main" type="button" onclick="window.location.href='login'">Sign In</button>
				</form>
			
			</c:if>
			
		</div>
		
		<c:if test="${user != null}">
			<div class="collapse navbar-collapse justify-content-end" id="navbarToggler">
				
				<ul id="navbar-nav-links" class="navbar-nav">
						
						<li class="nav-item">
							<a class="nav-link" href="./">Home</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="mcsearch?type=movie&view=group">Movies</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="mcsearch?type=tv_show&view=group">TV Shows</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="watchlist">Watch List</a>
						</li>
						<li class="nav-item dropdown">
							<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Movie Party</a>
							<div class="dropdown-menu header-dropdown-menu">
								<a class="dropdown-item" href="movieparty"><span class="fa fa-plus"></span> Create Party</a>
							</div>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Family</a>
						</li>
						
				</ul>
				
				<form method="GET" action="search" class="form-inline my-2 my-lg-0 header-search-form">
					<!-- <span class="input-group-addon fa fa-search"></span> -->
					<span id="search-icon" class="input-group-addon fa fa-search clickable"></span>
					<span id="searching-icon" class="input-group-addon fa fa-search d-none"></span>
					<input id="search-textbox" class="form-control mr-sm-2 d-none" type="text" placeholder="Search on moviesquik" aria-label="Search" name="query" autocomplete="off">
				</form>
				    
				<ul class="navbar-nav notifications-navbar-nav">
						
						<li class="nav-item dropdown">
					        <a id="nav-user-avatar-box" class="nav-link" href="#" id="notifications-menu" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					          	<span class="fa fa-envelope checked" id="notifications-menu-btn"></span>
					          	
					          	
					        </a>
					        <div id="nav-user-notifications-dropdown-menu" class="dropdown-menu dropdown-menu-right notifications-dropdown-menu header-dropdown-menu" aria-labelledby="notifications-menu">
					        	<jsp:include page="notifications/notifications-list.jsp"></jsp:include>
					        </div>
					    </li>
					    
					    <c:if test="${user.unreadNotifications != null && user.unreadNotifications.size() > 0}">
					    	<small><span class="badge badge-pill badge-danger">${user.unreadNotifications.size()}</span></small>
					    </c:if>
					    <c:if test="${user.unreadNotifications == null || user.unreadNotifications.size() == 0}">
					    	<small><span class="badge badge-pill badge-danger invisible"></span></small>
					    </c:if>
					      
				</ul>
				
				<ul id="navbar-nav-user" class="navbar-nav">
						
						<li class="nav-item dropdown last-nav-item">
					        <a id="nav-user-avatar-box" class="nav-link" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					          
					          	<c:set var = "user_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
								<c:if test="${user.profileImagePath != null && user.profileImagePath.length() > 0}">
									<c:set var = "user_profile_img_src" scope = "request" value = "res/user/${user.profileImagePath}"/>
								</c:if>
					          	<img src="${user_profile_img_src}" class="avatar-img rounded-circle">
					          	
					        </a>
					        <div id="nav-user-avatar-dropdown-menu" class="dropdown-menu dropdown-menu-right header-dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					        	<a class="dropdown-item" href="user"><small>Signed in as</small><br><strong>${user.firstName} ${user.lastName}</strong></a>
					        	<div class="dropdown-divider"></div>
					        	<a class="dropdown-item" href="#">Dashboard</a>
					        	<a class="dropdown-item" href="#">Edit Profile</a>
					        	<div class="dropdown-divider"></div>
					        	<a class="dropdown-item" href="login?logout=true&subject=user">Sign Out</a>
					        </div>
					    </li>
					      
				</ul>
				
			
			</div>
		</c:if>
		
	</nav>
		
</header>

<!-- <div class="chapternav">
	<div class="container row">
		
		<div class="col-auto">
		</div>
		
	</div>
</div> -->