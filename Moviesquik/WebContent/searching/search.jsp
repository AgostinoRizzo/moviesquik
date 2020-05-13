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
	<link href="css/media.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="lib/typeahead/bootstrap3-typeahead.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	<script src="js/searching.js"></script>
	<script src="js/media/show_media_content.js"></script>
	
</head>
<body>
	
	<!-- ======================
		  Header section
	======================= -->
	
	<jsp:include page="../header.jsp"></jsp:include>
	
	
	<!-- ======================
		  Search results
	======================= -->
	
	<div id="page-content">
	
	<div id="search-results-content">
	
	<c:if test="${search_result.contents.isEmpty() && search_result.users.isEmpty()}">
		<div class="empty-result-wrapper">
			<h5 class="note">
				Your search for "${search_result.query}" did not have any matches.<br>
				Try different keywords<br>
			</h5>
		</div>
	</c:if>
	
	<c:if test="${!search_result.users.isEmpty()}">
		
		<div id="users-list-header" class="media-contents-list-header row">
				<div class="media-contents-search-result-title col col-12 col-lg-8">
					<h5 class="media-contents-list-header-title note">Explore people related to: </h5>
					&nbsp;
					<!-- <span class="input-group-addon fa fa-film"></span> -->
					
					<c:set var="user_count" scope="request" value="1"/>
					<c:forEach items="${search_result.users}" var="user">
						
						<h5 class="media-contents-list-header-title">
							${user.firstName}&nbsp;${user.lastName}
							<c:if test="${user_count < search_result.users.size()}">
								&nbsp;|&nbsp;
							</c:if>
						</h5>
						
						<c:set var="user_count" scope="request" value="${user_count + 1}"/>
						
					</c:forEach>
											
				</div>
		</div>
		
		<div class="media-contents-list">
				
			<!-- <hr class="mb-4"> -->
			
			<!-- Search result content -->
			<div id="users-search-result" class="media-contents-list-items row members-row" role="listbox">
			
				<!-- Users Search result content -->

				<c:forEach items="${search_result.users}" var="user">
					<div class="media col-3">
		            			
						  <c:if test="${user.profileImagePath != null && user.profileImagePath.length() > 0}">
						  		<a href="user?id=${user.id}"><img src="res/user/${user.profileImagePath}" class="avatar-img card-list-avatar-img rounded-circle"></a>
						  </c:if>
						  <c:if test="${user.profileImagePath == null || user.profileImagePath.length() == 0}">
						  		<a href="user?id=${user.id}"><img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle"></a>
						  </c:if>
						  
						  <div class="media-body">
						    <h4>${user.firstName} ${user.lastName}</h4>
						    <p><a href="#">${user.email}</a></p>
						  </div>
					</div>
				</c:forEach>
				
			</div>
		    			
			<div class="more-collapse-box d-none">
				<button id="suggested-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
				<button id="suggested-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
			</div>
		</div>
		
	</c:if>
	
	<div class="loader d-none"></div>
	
	<c:if test="${!search_result.contents.isEmpty()}">
		
		<div id="media-contents-list-header" class="media-contents-list-header row">
				<div id="media-contents-search-result-title" class="media-contents-search-result-title col col-12 col-lg-8">
					<h5 class="media-contents-list-header-title note">Explore titles related to: </h5>
					&nbsp;
					<!-- <span class="input-group-addon fa fa-film"></span> -->
					
					<c:set var="media_content_count" scope="request" value="1"/>
					<c:forEach items="${search_result.contents}" var="media_content">
						
						<h5 class="media-contents-list-header-title">
							${media_content.title}
							<c:if test="${media_content_count < search_result.contents.size()}">
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
							<input type="hidden" id="search-query" value="${search_result.query}">
							<select id="sorting-policy-select" class="form-control sorting-policy-select" name="">
								
								<option value="suggested">Suggestions for you</option>
								<option value="year-released">Year Released</option>
								<option value="title-asc">A-Z</option>
								<option value="title-desc">Z-A</option>
								
							</select>
						</div>
						
						<!-- <button type="button" class="btn btn-secondary btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Order by
						</button>
						<div class="dropdown-menu dropdown-menu-right">
							<a class="dropdown-item" href="#">Suggestions for you</a>
							<a class="dropdown-item" href="#">Year Released</a>
							<a class="dropdown-item" href="#">A-Z</a>
							<a class="dropdown-item" href="#">Z-A</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="#">Separated link</a>
						</div> -->
					</div>
					
				</div>
		</div>
			
		<div id="media-contents-list" class="media-contents-list">
			
			<!-- <hr class="mb-4"> -->
			
			<!-- Search result content -->
			<div id="search-result" class="media-contents-list-items row" role="listbox">
				<jsp:include page="search-result.jsp"></jsp:include>
			</div>
		    			
			<div class="more-collapse-box d-none">
				<button id="suggested-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
				<button id="suggested-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
			</div>
		</div>
		
	</c:if>
	</div>
	</div>
	
</body>
</html>