<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>


<div class="row user-list-row clickable-light" id="current-user-link-item">
	
	<a class="col-auto col-light-left">
		<img src="${admin.profileImagePath}" class="avatar-img card-list-avatar-img rounded-circle" id="user-icon">
	</a>
	  
	<div class="col users-list-name col-light-right text-overflow">
		<p class="users-list-name-text">${admin.firstName} ${admin.lastName}</p>
		<p class="note">${admin.email}</p>
	</div>
</div>

<br>

<div class="row sidenav-list-row" id="dashboard-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-dashboard sidenav-item-icon foryou-icon"></i></div>
	<div class="col-auto clickable">Dashboard</div>
</div>

<div class="row sidenav-list-row" id="media-contents-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-film sidenav-item-icon browse-icon"></i></div>
	<div class="col-auto clickable">Media Contents</div>
</div>

<div class="row sidenav-list-row" id="servers-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-server sidenav-item-icon news-icon"></i></div>
	<div class="col-auto clickable">CDN Servers</div>
</div>

<br>

<div class="row sidenav-list-row" id="analytics-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-line-chart sidenav-item-icon watchlist-icon"></i></div>
	<div class="col-auto clickable">Analytics</div>
</div>

<div class="row sidenav-list-row" id="money-collection-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-money sidenav-item-icon recentlywatched-icon"></i></div>
	<div class="col-auto clickable">Money Collection</div>
</div>

<br>

<div class="row sidenav-list-row" id="gift-card-link-item">
	<div class="col-auto sidenav-item-col clickable"><i class="fa fa-gift sidenav-item-icon create-party-icon"></i></div>
	<div class="col-auto clickable">Gift Cards</div>
</div>