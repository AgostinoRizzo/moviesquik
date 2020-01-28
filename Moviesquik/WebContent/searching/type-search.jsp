<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>Search</title>
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/contents.css" rel="stylesheet">
	<link href="css/searching.css" rel="stylesheet">
	<link href="css/user-profile.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="lib/typeahead/bootstrap3-typeahead.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	<script src="js/searching.js"></script>
	<script src="js/filter.js"></script>
	
</head>
<body>
	
	<!-- ======================
		  Header section
	======================= -->
	
	<jsp:include page="../header.jsp"></jsp:include>
	
	<!-- ======================
		  Search results
	======================= -->
	
	<div id="search-results-content">
		
		<input type="hidden" id="type-search" value="${search_result.type}">
		
		<div id="mc-search-result-type-header" class="media-contents-list-header row">
				<div id="media-contents-search-result-title" class="media-contents-search-result-title col col-12 col-lg-8">
					
					<div class="btn-group">
					
						<h2 class="media-contents-list-header-title">
							<c:if test="${search_result.type == 'movie'}">Movies</c:if>
							<c:if test="${search_result.type == 'tv_show'}">TV Shows</c:if>
						</h2>
						<h2 class="media-contents-list-header-title">&nbsp;-&nbsp;</h2>
						<h2 id="selected-genre-label" class="media-contents-list-header-title">All Genres</h2>
					
					</div>
											
				</div>
				
		</div>
		
		<div id="filter-section" class="media-contents-list-header filter-section row">
			
			<div id="" class="col col-12 col-lg-10">
				<div class="btn-group">
					<h5 class="media-contents-list-header-title"><span class="fa fa-sliders"></span> &nbsp;&nbsp;Filter </h5>
					&nbsp;
					&nbsp;
					&nbsp;
					&nbsp;
					&nbsp;
					
					<div class="form-inline">
					
						<select id="genres-select" class="form-control filter-select-position" name="">
							
							<option id="all-genres-option" value="all">All Genres</option>
							
							<c:forEach items="${genres}" var="genre">
								<option value="${genre}">${genre}</option>
							</c:forEach>
							
						</select>
						
						<button type="button" id="add-genre-filter-btn" class="btn btn-main filter-select-position"><span class="fa fa-plus"></span></button>
					
					</div>
					
					&nbsp;
					&nbsp;
					&nbsp;
					&nbsp;
					&nbsp;
					
					<h6 class="filter-lable note"><span class="fa fa-hourglass"></span> &nbsp;Duration</h6>
					&nbsp;
					&nbsp;
					&nbsp;
					<div class="form-inline">
					
						
						<select id="duration-select" class="form-control filter-select-position" name="">
							
							<option value="any">Any</option>
							<option value="short">Short (&#60; 4 minutes)</option>
							<option value="long">Long (&#62; 20 minutes)</option>
							
						</select>
						
					</div>
					
					&nbsp;
					&nbsp;
					&nbsp;
					&nbsp;
					&nbsp;
					
					<h6 class="filter-lable note"><span class="fa fa-film"></span> &nbsp;Features</h6>
					&nbsp;
					&nbsp;
					&nbsp;
					<div class="form-inline">
					
						
						<select id="features-select" class="form-control filter-select-position" name="">
							
							<option value="any">Any</option>
							<option value="4k">4K</option>
							<option value="hd">HD</option>
							
						</select>
						
					</div>
					
				</div>
				
				&nbsp;
				&nbsp;
				&nbsp;
				&nbsp;
				&nbsp;
				
				<button type="button" id="clear-filter-btn" class="btn btn-warning filter-select-position d-none"><span class="fa fa-times"></span> Clear</button>
			</div>
			
			<div class="sorting-policy-section col col-12 col-lg-2">
					
				<div class="btn-group">
				
					<!-- <h5 class="media-contents-list-header-title note">View</h5>
					&nbsp;
					&nbsp;
					
					<div class="form-inline">
						<select id="" class="form-control inline-widget" name="">
							
							<option value="grouped">Grouped</option>
							<option value="long">Long</option>
							
						</select>
					</div> -->
					
					<button type="button" id="group-view-btn" class="btn btn-outline-light inline-widget active"><span class="fa fa-align-left"></span></button>
					<button type="button" id="full-view-btn" class="btn btn-outline-light inline-widget"><span class="fa fa-th"></span></button>
					
				</div>
				
			</div>
				
		</div>
		
		<%-- <div id="filter-section" class="media-contents-list-header filter-section row">
			
			<div class="col col-12 col-lg-2">
					<h5 class="media-contents-list-header-title"><span class="fa fa-sliders"></span> &nbsp;&nbsp;Filter </h5>
			</div>
			
			<div class="col col-12 col-lg-2">
				<h5 class="note"><span class="fa fa-sliders"></span> &nbsp;&nbsp;Genres </h5>
			</div>
			
			<div class="col col-12 col-lg-2">
				<h5 class="note"><span class="fa fa-sliders"></span> &nbsp;&nbsp;Duration </h5>
			</div>
			
			<div class="col col-12 col-lg-2">
				<h5 class="note"><span class="fa fa-sliders"></span> &nbsp;&nbsp;Features </h5>
			</div>
			
			<div class="sorting-policy-section col col-12 col-lg-4">
					
				<div class="btn-group">
					
					<button type="button" id="group-view-btn" class="btn btn-outline-light btn-sm active"><span class="fa fa-align-left"></span></button>
					<button type="button" id="full-view-btn" class="btn btn-outline-light btn-sm"><span class="fa fa-th"></span></button>
					
				</div>
				
			</div>
				
		</div>
		
		<div id="" class="row">
		
			<div class="col col-12 col-lg-2">
			</div>
			
			<div class="col col-12 col-lg-2">
				<c:forEach items="${genres}" var="genre">
					<a href="${genre}">&nbsp;&nbsp;${genre}</a><br>
				</c:forEach>
			</div>
			
			<div class="col col-12 col-lg-2">
				<a href="">&nbsp;&nbsp;</a>Any<br>
				<a href="">&nbsp;&nbsp;</a>Short (&#60; 4 minutes)<br>
				<a href="">&nbsp;&nbsp;</a>Long (&#62; 20 minutes)<br>
			</div>
			
			<div class="col col-12 col-lg-2">
				<a href="">&nbsp;&nbsp;</a>Any<br>
				<a href="">&nbsp;&nbsp;</a>4K<br>
				<a href="">&nbsp;&nbsp;</a>HD<br>
			</div>
			
		</div> --%>
		
		<div id="media-contents-type-search-list-header" class="media-contents-list-header row d-none">
				<div id="media-contents-type-search-result-title" class="media-contents-search-result-title col col-12 col-lg-8">
					<h5 class="media-contents-list-header-title note">Explore titles related to: </h5>
					&nbsp;
					<!-- <span class="input-group-addon fa fa-film"></span> -->
					
					<c:set var="media_content_count" scope="request" value="1"/>
					<c:forEach items="${search_result.fullContents}" var="media_content">
						
						<h5 class="media-contents-list-header-title">
							${media_content.title}
							<c:if test="${media_content_count < search_result.fullContents.size()}">
								&nbsp;|&nbsp;
							</c:if>
						</h5>
						
						<c:set var="media_content_count" scope="request" value="${media_content_count + 1}"/>
						
					</c:forEach>
											
				</div>
				<div class="sorting-policy-section col col-12 col-lg-4">
					
					<div class="btn-group">
					
						<h5 class="media-contents-list-header-title note">Order by</h5>
						&nbsp;
						&nbsp;
						
						<div class="form-inline">
							<select id="sorting-policy-type-search-select" class="form-control sorting-policy-select" name="">
								
								<option value="suggested">Suggestions for you</option>
								<option value="year-released">Year Released</option>
								<option value="title-asc">A-Z</option>
								<option value="title-desc">Z-A</option>
								
							</select>
						</div>
					</div>
					
				</div>
		</div>
	
		<div id="type-search-content">
			<jsp:include page="type-search-content.jsp"></jsp:include>
		</div>
		
	</div>
	
</body>
</html>