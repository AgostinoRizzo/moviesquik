<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<!-- Style sheet files -->
<!-- <link href="css/contents.css" rel="stylesheet"> -->

<!-- JavaScript files -->
<!-- <script src="js/contents.js"></script>
<script src="js/media-contents-filler.js"></script> -->


<c:if test="${user != null && empty no_view_suggested}">
	
	<!-- Suggested media contents list -->
	<div class="media-contents-list">

		<div class="media-contents-list-header">
				<div class="media-contents-icon-title">
					<c:if test="${empty small_view}">
						<h4 class="media-contents-list-header-title"><!-- <span class="fa fa-lightbulb-o checked"></span>  -->Suggested for you</h4>
					</c:if>
					<c:if test="${not empty small_view}">
						<h5 class="media-contents-list-header-title"><!-- <span class="fa fa-lightbulb-o checked"></span>  -->Suggested for you</h5>
					</c:if>
				</div>
				<div class="view-all-contents">
					<button id="suggested-view-all-btn" class="btn btn-link view-all-btn invisible">View All</button>
				</div>
				
				<c:if test="${not empty small_view}"><br></c:if>
		</div>
		<br>
		<!-- <hr class="mb-4"> -->
		
		<!-- Top content -->
	    <div id="suggested-media-contents-items" class="media-contents-list-items row" role="listbox">
	    	<%-- <c:if test="${empty small_view}">
	        	<jsp:include page="contents-items.jsp"></jsp:include>
	        </c:if>
	        <c:if test="${not empty small_view}">
	        	<jsp:include page="contents-items-sm.jsp"></jsp:include>
	        </c:if> --%>
	        <jsp:include page="contents-items.jsp"></jsp:include>
	    </div>
		
		<div class="more-collapse-box d-none">
			<button id="suggested-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
			<button id="suggested-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
		</div>
	</div>
	
	<!-- May Like media contents list -->
	<div class="media-contents-list">

		<div class="media-contents-list-header">
				<div class="media-contents-icon-title">
					<c:if test="${empty small_view}">
						<h4 class="media-contents-list-header-title">You may like</h4>
					</c:if>
					<c:if test="${not empty small_view}">
						<h5 class="media-contents-list-header-title">You may like</h5>
					</c:if>
				</div>
				<div class="view-all-contents">
					<button id="maylike-view-all-btn" class="btn btn-link view-all-btn invisible">View All</button>
				</div>
				
				<c:if test="${not empty small_view}"><br></c:if>
		</div>
		<br>
		
		<!-- May Like content -->
	    <div id="maylike-media-contents-items" class="media-contents-list-items row" role="listbox">
	        <jsp:include page="contents-items.jsp"></jsp:include>
	    </div>
		
		<div class="more-collapse-box d-none">
			<button id="maylike-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
			<button id="maylike-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
		</div>
	</div>

</c:if>

<div class="media-contents-list">

	<div class="media-contents-list-header">
			
			<div class="media-contents-icon-title">
				<c:if test="${empty small_view}">
					<h4 class="media-contents-list-header-title"><span class="fa fa-line-chart checked"></span> Trending Now</h4>
				</c:if>
				<c:if test="${not empty small_view}">
					<h5 class="media-contents-list-header-title"><span class="fa fa-line-chart checked"></span> Trending Now</h5>
				</c:if>
			</div>
			<div class="view-all-contents">
				<button id="trending-now-view-all-btn" class="btn btn-link view-all-btn invisible">View All</button>
			</div>
			
			<c:if test="${not empty small_view}"><br></c:if>
	</div>
	<br>
	
	<!-- Trending Now content -->
    <div id="trending-now-media-contents-items" class="media-contents-list-items row" role="listbox">
        <jsp:include page="contents-items.jsp"></jsp:include>
    </div>
    
    <div class="more-collapse-box d-none">
		<button id="trending-now-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
		<button id="trending-now-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
	</div>

</div>

<div class="media-contents-list">

	<div class="media-contents-list-header">
			<!-- <img class="media-contents-list-header_icon" alt="" src="res/drawable/rate_star.png"> -->
			<div class="media-contents-icon-title">
				<c:if test="${empty small_view}">
					<h4 class="media-contents-list-header-title"><!-- <span class="fa fa-eye checked"></span>  -->Most Popular</h4>
				</c:if>
				<c:if test="${not empty small_view}">
					<h5 class="media-contents-list-header-title"><!-- <span class="fa fa-eye checked"></span>  -->Most Popular</h5>
				</c:if>
			</div>
			<div class="view-all-contents">
				<button id="most-popular-view-all-btn" class="btn btn-link view-all-btn invisible">View All</button>
			</div>
			
			<c:if test="${not empty small_view}"><br></c:if>
	</div>
	<br>
	<!-- <hr class="mb-4"> -->
	
	<!-- Top content -->
    <div id="most-popular-media-contents-items" class="media-contents-list-items row" role="listbox">
        <%-- <c:if test="${empty small_view}">
        	<jsp:include page="contents-items.jsp"></jsp:include>
        </c:if>
        <c:if test="${not empty small_view}">
        	<jsp:include page="contents-items-sm.jsp"></jsp:include>
        </c:if> --%>
        <jsp:include page="contents-items.jsp"></jsp:include>
    </div>
    
    <div class="more-collapse-box d-none">
		<button id="most-popular-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
		<button id="most-popular-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
	</div>

</div>

<div class="media-contents-list">

	<div class="media-contents-list-header">
			
			<div class="media-contents-icon-title">
				<c:if test="${empty small_view}">
					<h4 class="media-contents-list-header-title"><span class="fa fa-star checked"></span> Top Rated</h4>
				</c:if>
				<c:if test="${not empty small_view}">
					<h5 class="media-contents-list-header-title"><span class="fa fa-star checked"></span> Top Rated</h5>
				</c:if>
			</div>
			<div class="view-all-contents">
				<button id="top-rated-view-all-btn" class="btn btn-link view-all-btn invisible">View All</button>
			</div>
			
			<c:if test="${not empty small_view}"><br></c:if>
	</div>
	<br>
	
	<!-- Top Rated content -->
    <div id="top-rated-media-contents-items" class="media-contents-list-items row" role="listbox">
        <jsp:include page="contents-items.jsp"></jsp:include>
    </div>
    
    <div class="more-collapse-box d-none">
		<button id="top-rated-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
		<button id="top-rated-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
	</div>

</div>

<div class="media-contents-list">

	<div class="media-contents-list-header">
			<!-- <img class="media-contents-list-header_icon" alt="" src="res/drawable/rate_star.png"> -->
			<div class="media-contents-icon-title">
				<c:if test="${empty small_view}">
					<h4 class="media-contents-list-header-title"><span class="fa fa-heart checked"></span> Most Favorites</h4>
				</c:if>
				<c:if test="${not empty small_view}">
					<h5 class="media-contents-list-header-title"><span class="fa fa-heart checked"></span> Most Favorites</h5>
				</c:if>
			</div>
			<div class="view-all-contents">
				<button id="most-favorites-view-all-btn" class="btn btn-link view-all-btn invisible">View All</button>
			</div>
			
			<c:if test="${not empty small_view}"><br></c:if>
	</div>
	<br>
	<!-- <hr class="mb-4"> -->
	
	<!-- Top content -->
    <div id="most-favorites-media-contents-items" class="media-contents-list-items row" role="listbox">
        <%-- <c:if test="${empty small_view}">
        	<jsp:include page="contents-items.jsp"></jsp:include>
        </c:if>
        <c:if test="${not empty small_view}">
        	<jsp:include page="contents-items-sm.jsp"></jsp:include>
        </c:if> --%>
        <jsp:include page="contents-items.jsp"></jsp:include>
    </div>
    
    <div class="more-collapse-box d-none">
		<button id="most-favorites-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
		<button id="most-favorites-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
	</div>

</div>

<c:if test="${user != null && empty no_view_suggested}">
	<div class="media-contents-list">
	
		<div class="media-contents-list-header">
				
				<div class="media-contents-icon-title">
					<c:if test="${empty small_view}">
						<h4 class="media-contents-list-header-title"><span class="fa fa-history checked"></span> Recently Watched</h4>
					</c:if>
					<c:if test="${not empty small_view}">
						<h5 class="media-contents-list-header-title"><span class="fa fa-history checked"></span> Recently Watched</h5>
					</c:if>
				</div>
				<div class="view-all-contents">
					<button id="recently-watched-view-all-btn" class="btn btn-link view-all-btn invisible">View All</button>
				</div>
				
				<c:if test="${not empty small_view}"><br></c:if>
		</div>
		<br>
		
		<!-- Top Rated content -->
	    <div id="recently-watched-media-contents-items" class="media-contents-list-items row" role="listbox">
	        <jsp:include page="contents-items.jsp"></jsp:include>
	    </div>
	    
	    <div class="more-collapse-box d-none">
			<button id="recently-watched-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
			<button id="recently-watched-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
		</div>
	
	</div>
</c:if>