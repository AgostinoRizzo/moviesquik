<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
						<div class="col-12 col-sm-6 col-md-6 col-xl-3 media-col-item">
							<jsp:include page="../contents-item.jsp"></jsp:include>
						</div>
					</c:if>
				</c:forEach>
			</div></div>
			
			<div class="col-6"><div class="row">
	        	<c:forEach items="${trendingnow}" var="mc" varStatus="status">
	        		<c:if test="${status.count > 4}">
	        			<c:set var="media_content" scope="request" value="${mc}"/>
						<div class="col-12 col-sm-6 col-md-6 col-xl-3 media-col-item">
							<jsp:include page="../contents-item.jsp"></jsp:include>
						</div>
					</c:if>
				</c:forEach>
			</div></div>
			
	    </div>
	
	</div>
	
</div>

<br><br>

<div id="media-search-form">
	
	<h5>Online Database Media Search</h5><br>
	<p class="note">
		The media contents search is carried out from the external online database OMDb API. 
		The search can be done just by title or including the year of release.
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
			<label for="year">Year of release <span class="badge badge-warning">Optional</span></label>
			<div class="input-group">
				<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
				<input type="number" id="year" name="year" class="form-control" min="2000" max="2020" value="2020">
				
			</div>
		</div>
		
		<div class="col col-md-2 col-12" id="search-media-btn-col">
			<button type="button" class="btn btn-primary" id="search-media-btn"><span class="fa fa-search"></span> Search</button>
		</div>
	</div>
	
	<br><br>
	
	<div id="media-search-results-container">
		
	</div>
	
</div>