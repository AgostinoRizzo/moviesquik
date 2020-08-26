<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<br>

<div id="media-search-form">
	
	<h5>Media Contents Search</h5><br>
	<p class="note">
		The search can be done just by title or by rating.
	</p>
	
	<div class="form-row">
		<div class="col col-md-5 col-12">
			<label for="title">Title</label>
			<div class="input-group">
				
				<span class="input-group-addon"><span class="fa fa-file-video-o"></span></span>
				<input type="text" id="title" name="title" class="form-control" placeholder="Insert a media title" value="" required>
				
			</div>
		</div>
		<div class="col col-md-5 col-12">
			<label for="rating">Rating</label>
			<div class="input-group">
				<span class="input-group-addon"><span class="fa fa-line-chart"></span></span>
				<select class="form-control" name="rating" id="rating">
					<option value="trendingnow">Trending Now</option>
					<option value="popular">Most Popular</option>
					<option value="toprated">Top Rated</option>
					<option value="favorites">Most Favorites</option>
				</select>
				
			</div>
		</div>
		
		<div class="col col-md-2 col-12" id="search-media-btn-col">
			<button type="button" class="btn btn-primary" id="search-analytics-media-btn"><span class="fa fa-search"></span> Search</button>
		</div>
	</div>
	
	<br><br>
	
	<div id="media-search-results-container">
		
	</div>
	
</div>

<div id="analytics-media-search-results">

	<div id="contents-section">
	
		<div class="media-contents-list">
		
			<div class="media-contents-list-header">
					
					<div class="media-contents-icon-title">
						<h4 class="media-contents-list-header-title"><span class="fa fa-line-chart checked"></span> Trending Now</h4>
					</div>
					
					<br>
			</div>
			<br>
			
			<!-- Trending Now content -->
		    <div id="trending-now-media-contents-items" class="media-contents-list-items row" role="listbox">
		        
		        <div class="col-6"><div class="row">
		        	<c:forEach items="${trendingnow}" var="mc" varStatus="status">
		        		<c:if test="${status.count <= 4}">
		        			<c:set var="media_content" scope="request" value="${mc}"/>
							<div class="col-12 col-sm-6 col-md-6 col-xl-3 media-col-item analytics-media-col-item">
								<jsp:include page="../contents-item.jsp"></jsp:include>
							</div>
						</c:if>
					</c:forEach>
				</div></div>
				
				<div class="col-6"><div class="row">
		        	<c:forEach items="${trendingnow}" var="mc" varStatus="status">
		        		<c:if test="${status.count > 4}">
		        			<c:set var="media_content" scope="request" value="${mc}"/>
							<div class="col-12 col-sm-6 col-md-6 col-xl-3 media-col-item analytics-media-col-item">
								<jsp:include page="../contents-item.jsp"></jsp:include>
							</div>
						</c:if>
					</c:forEach>
				</div></div>
				
		    </div>
		
		</div>
		
	</div>
	
</div>

<br><br>

<div id="analytics-media-dashboard-container" class="d-none">

	<div class="row">
		<div class="col-6">
			<h5><span class="fa fa-dashboard"></span> Media Content Dashboard</h5>
			<div class="loader loader-sm d-none" id="media-analytics-charts-loader"></div>
		</div>
		<div class="col-6 col-right">
			<div class="input-group">
				<span class="input-group-addon"><span class="fa fa-line-chart"></span></span>
				<select class="form-control" name="charts-window" id="charts-window-select">
					<option value="last-3days">Last Three Days</option>
					<option value="last-week" selected>Last Week</option>
					<option value="last-2weeks">Last Two Weeks</option>
					<option value="last-month">Last Month</option>
				</select>
				
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-2 col-sm-2 col-md-2 col-xl-2 media-col-item" id="dashboard-media-card-col">
		
		</div>
		
		<div class="col-10 tile" id="media-dashboard-content-col">
			
			<div class="row" id="charts-data-row">
			
				<div class="col-6">
					<h5><span class="fa fa-line-chart text-info"></span> Trending/Popularity chart</h5><br>
					<div class="note">
						<span class="fa fa-info text-warning"></span> 
						The trending chart displays the recent media content popularity (sharing rate) referring to last day.
					</div>
					<div class="note">
						<span class="fa fa-info text-warning"></span> 
						The popularity chart displays the long period media content popularity (sharing rate) referring to last week.
					</div>
					
					<br>
					
					<div class="" id="trending-chart">
					
					</div>
					
				</div>
				
				<div class="col-6">
					<h5><span class="fa fa-line-chart text-info"></span> Ratings chart</h5><br>
					<div class="note">
						<span class="fa fa-info text-warning"></span> 
						The ratings chart displays the average user rating starts given to the media content in addition to the external online database OMDb API ratings.
					</div>
					
					<br>
					
					<div class="" id="ratings-chart">
					
					</div>
					
				</div>
			
			</div>
			
			<br>
			
			<div class="row" id="charts-data-row">
			
				<div class="col-6">
					<h5><span class="fa fa-line-chart text-info"></span> Likes/Dislikes chart</h5><br>
					<div class="note">
						<span class="fa fa-info text-warning"></span> 
						The likes/dislikes chart displays the average user likes/dislikes given to the media content referring to all time.
					</div>
					
					<br>
					
					<div class="" id="likes-chart">
					
					</div>
					
				</div>
				
				<div class="col-6">
					<h5><span class="fa fa-line-chart text-info"></span> Views chart</h5><br>
					<div class="note">
						<span class="fa fa-info text-warning"></span> 
						The views chart displays the growth of the number of views referring to all time.
					</div>
					
					<br>
					
					<div class="" id="views-chart">
					
					</div>
					
				</div>
			
			</div>
			
		</div>
	</div>
	
</div>