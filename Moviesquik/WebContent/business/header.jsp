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
		
		<a class="navbar-brand" href="./"><img alt="" src="res/drawable/business_logo.png"></a>
		
		<div class="collapse navbar-collapse justify-content-end" id="navbarToggler">
			
		</div>
		
		<c:if test="${admin != null}">
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
								<a class="dropdown-item" href="movieparty?action=new"><span class="fa fa-plus"></span> Create Party</a>
							</div>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Family</a>
						</li>
						
				</ul>
				
				<ul id="navbar-nav-user" class="navbar-nav">
						
						<li class="nav-item dropdown last-nav-item">
					        <a id="nav-user-avatar-box" class="nav-link" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					          
					          	<c:set var = "user_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
								<c:if test="${admin.profileImagePath != null && admin.profileImagePath.length() > 0}">
									<c:set var = "user_profile_img_src" scope = "request" value = "${admin.profileImagePath}"/>
								</c:if>
					          	<img src="${user_profile_img_src}" class="avatar-img rounded-circle">
					          	
					        </a>
					        <div id="nav-user-avatar-dropdown-menu" class="dropdown-menu dropdown-menu-right header-dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
					        	<a class="dropdown-item" href="user"><small>Signed in as</small><br><strong>${admin.firstName} ${admin.lastName}</strong></a>
					        	<div class="dropdown-divider"></div>
					        	<a class="dropdown-item" href="#">Dashboard</a>
					        	<a class="dropdown-item" href="#">Edit Profile</a>
					        	<div class="dropdown-divider"></div>
					        	<a class="dropdown-item" href="business/login?logout=true">Sign Out</a>
					        </div>
					    </li>
					      
				</ul>
				
			
			</div>
		</c:if>
		
	</nav>
		
</header>