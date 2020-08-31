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
		
		<a class="navbar-brand" href="business"><img alt="" src="res/drawable/business_logo.png"></a>
		
		<c:if test="${admin != null}">
			<div class="collapse navbar-collapse justify-content-end" id="navbarToggler">
				
				<ul id="navbar-nav-links" class="navbar-nav">
						
						<li class="nav-item">
							<a class="nav-link can-point" id="header-dashboard-link-item">Dashboard</a>
						</li>
						<li class="nav-item">
							<a class="nav-link can-point" id="header-media-contents-link-item">Media Contents</a>
						</li>
						<li class="nav-item">
							<a class="nav-link can-point" id="header-servers-link-item">CDN Servers</a>
						</li>
						<li class="nav-item">
							<a class="nav-link can-point" id="header-analytics-link-item">Analytics</a>
						</li>
						<li class="nav-item">
							<a class="nav-link can-point">Money Collection</a>
						</li>
						<li class="nav-item">
							<a class="nav-link can-point">Gift Cards</a>
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
					        	<a class="dropdown-item can-point" id="menu-dashboard-link-item">Dashboard</a>
					        	<a class="dropdown-item can-point" id="menu-media-contents-link-item">Media Contents</a>
					        	<a class="dropdown-item can-point" id="menu-servers-link-item">CDN Servers</a>
					        	<a class="dropdown-item can-point" id="menu-analytics-link-item">Analytics</a>
					        	<a class="dropdown-item can-point">Money Collection</a>
					        	<a class="dropdown-item can-point">Gift Cards</a>
					        	<div class="dropdown-divider"></div>
					        	<a class="dropdown-item" href="business/login?logout=true">Sign Out</a>
					        </div>
					    </li>
					      
				</ul>
				
			
			</div>
		</c:if>
		
	</nav>
		
</header>