<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div id="party-list-header-container" class="tile">

	<a href="movieparty?action=new" type="button" class="btn btn-info btn-sm party-list-header-item party-list-header-main-item"><i class="fa fa-plus"></i> New party</a>
	
	<div class="btn btn-dark btn-sm party-list-header-item" data-toggle="collapse" data-target="#calendar-container"><i class="fa fa-calendar"></i> Calendar</div>
	
	<div class="btn btn-dark btn-sm dropdown-toggle party-list-header-item" data-toggle="dropdown"><i class="fa fa-filter"></i> Filter parties</div>
	<div class="dropdown-menu header-dropdown-menu">
		<a class="dropdown-item clickable-light" id="movieparty-search-public"><i class="fa fa-globe"></i> Public only</a>
		<a class="dropdown-item clickable-light" id="movieparty-search-private"><i class="fa fa-lock"></i> Private only</a>
		<a class="dropdown-item clickable-light" id="movieparty-search-playing"><i class="fa fa-play"></i> Playing now</a>
		<a class="dropdown-item clickable-light" id="movieparty-search-upcoming"><i class="fa fa-calendar"></i> Upcoming</a>
		<a class="dropdown-item clickable-light" id="movieparty-search-expired"><i class="fa fa-window-close"></i> Expired</a>
	</div>
	
	<!-- <div class="btn btn-dark btn-sm dropdown-toggle party-list-header-item" data-toggle=""><i class="fa fa-sort"></i></div> -->
	
	<div class="btn btn-light btn-sm party-list-header-item" id="movieparty-search-all"><i class="fa fa-refresh"></i></div>
	
	<br>
	
	<div id="movieparty-filter-display">
		
	</div>
	
</div>

<div class="collapse tile" id="calendar-container">
	<jsp:include page="../movieparty/movie-party-calendar.jsp"></jsp:include>
</div>

<div class="loader loader-sm d-none" id="movieparty-filter-search-loader"></div>