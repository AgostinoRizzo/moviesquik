<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

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
						
						<li class="nav-item">
							<a class="nav-link" href="#">Home</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Movies</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">TV Shows</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Watch List</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Family</a>
						</li>
						
				</ul>
				
				<form class="form-inline my-2 my-lg-0 header-search-form">
					<input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
				</form>
				    
				<ul class="navbar-nav">
						
						<li class="nav-item dropdown">
					        <a id="nav-user-avatar-box" class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					          <span class="fa fa-bell checked"></span>
					        </a>
					        <div id="nav-user-avatar-dropdown-menu" class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
					        	
					        </div>
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