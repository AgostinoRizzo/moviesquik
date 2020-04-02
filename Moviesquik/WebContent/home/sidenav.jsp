<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="sidenav users-col home-container-col">

	<div class="row user-list-row">
			            			
		  <c:if test="${user.profileImagePath != null && user.profileImagePath.length() > 0}">
		  		<a href="user?id=${user.id}" class="col-auto sidenav-item-col"><img src="res/user/${user.profileImagePath}" class="avatar-img card-list-avatar-img rounded-circle">
		  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
		  		</a>
		  </c:if>
		  <c:if test="${user.profileImagePath == null || user.profileImagePath.length() == 0}">
		  		<a href="user?id=${user.id}" class="col-auto sidenav-item-col"><img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle">
		  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
		  		</a>
		  </c:if>		  
		  
		  <div class="col-auto users-list-name">
		    <p class="users-list-name-text">${user.fullName}</p>
		    <p class="note">${user.email}</p>
		  </div>
	</div>
	
	<br>
	
	<div class="row user-list-row" id="foryou-link-item">
		<div class="col-auto sidenav-item-col"><i class="fa fa-heart sidenav-item-icon foryou-icon"></i></div>
		<div class="col-auto">For You</div>
	</div>
	
	<div class="row user-list-row" id="browse-link-item">
		<div class="col-auto sidenav-item-col"><i class="fa fa-film sidenav-item-icon browse-icon"></i></div>
		<div class="col-auto">Browse</div>
	</div>
	
	<div class="row user-list-row" id="news-link-item">
		<div class="col-auto sidenav-item-col"><i class="fa fa-comment sidenav-item-icon news-icon"></i></div>
		<div class="col-auto">News</div>
	</div>
	
	
	<br>
	<div class="row user-list-row"><div class="col-auto note sidenav-item-title">LIBRARY</div></div>
	
	<div class="row user-list-row" id="watchlist-link-item">
		<div class="col-auto sidenav-item-col"><i class="fa fa-list-ul sidenav-item-icon watchlist-icon"></i></div>
		<div class="col-auto">Watch List</div>
	</div>
	
	<div class="row user-list-row" id="recentlywatched-link-item">
		<div class="col-auto sidenav-item-col"><i class="fa fa-history sidenav-item-icon recentlywatched-icon"></i></div>
		<div class="col-auto">Recently Watched</div>
	</div>
	
	<br>
	<div class="row user-list-row"><div class="col-auto note sidenav-item-title">FRIENDS</div></div>
	<jsp:include page="users.jsp"></jsp:include>
	
</div>