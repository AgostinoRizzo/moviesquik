<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>


<div class="row user-list-row clickable-light" id="current-user-link-item">
		            			
	  <c:if test="${user.profileImagePath != null && user.profileImagePath.length() > 0}">
	  		<a href="user?id=${user.id}" class="col-auto col-light-left"><img src="${user.profileImagePath}" class="avatar-img card-list-avatar-img rounded-circle" id="user-icon">
	  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
	  		</a>
	  </c:if>
	  <c:if test="${user.profileImagePath == null || user.profileImagePath.length() == 0}">
	  		<a href="user?id=${user.id}" class="col-auto col-light-left"><img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle" id="user-icon">
	  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
	  		</a>
	  </c:if>		  
	  
	  <div class="col users-list-name col-light-right">
	    <p class="users-list-name-text">${user.fullName}</p>
	    <p class="note">${user.email}</p>
	  </div>
</div>

<br>

<div class="row sidenav-list-row" id="foryou-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-heart sidenav-item-icon foryou-icon"></i></div>
	<div class="col-auto clickable">For You</div>
</div>

<div class="row sidenav-list-row" id="browse-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-film sidenav-item-icon browse-icon"></i></div>
	<div class="col-auto clickable">Browse</div>
</div>

<div class="row sidenav-list-row" id="news-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-comment sidenav-item-icon news-icon"></i></div>
	<div class="col-auto clickable">News</div>
</div>


<br>
<div class="row user-list-row"><div class="col-auto note sidenav-item-title">LIBRARY</div></div>

<div class="row sidenav-list-row" id="watchlist-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-list-ul sidenav-item-icon watchlist-icon"></i></div>
	<div class="col-auto clickable">Watch List</div>
</div>

<div class="row sidenav-list-row" id="recentlywatched-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-history sidenav-item-icon recentlywatched-icon"></i></div>
	<div class="col-auto clickable">Recently Watched</div>
</div>

<br>
<div class="row user-list-row"><div class="col-auto note sidenav-item-title">MOVIE PARTY</div></div>

<div class="row sidenav-list-row" id="create-party-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-plus sidenav-item-icon create-party-icon"></i></div>
	<div class="col-auto clickable">Create Party</div>
</div>

<c:forEach items="${user.allMovieParties}" var="party">
	<div class="row sidenav-list-row open-party-link-item">
		<input type="hidden" id="party-id" value="${party.id}">
		<div class="col-auto sidenav-item-col clickable"><i class="fa fa-calendar sidenav-item-icon movie-party-icon"></i></div>
		<div class="col clickable text-overflow">
			${party.name}
			<c:if test="${party.description.length() > 0}"> - ${party.description}</c:if>
		</div>
	</div>
</c:forEach>

