<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<script src="js/media/rate-media-content.js"></script>

<div id="media-content-page-container" class="container">
	
	<input type="hidden" id="media-id" value="${media_content.id}">
	
	<div class="row">
		
		<div class="col-1">
			<h3><span id="mc-page-back" class="fa fa-arrow-left clickable"></span></h3>
		</div>
		
		<div class="col-6 media-content-hader">
		
				<h2 class="media-content-title">${media_content.title}</h2>
			    <p class="media-content-production note"><strong>${media_content.production}</strong></p>
				
		    	<jsp:include page="media_content_ratings.jsp"></jsp:include>
		    	&nbsp;<strong>${media_content.statistics.actualRate}</strong> / 5
		   		
		    	<p class="">${media_content.genre}</p>
		    	<c:if test="${user != null}">
				    <div class="btn-group">
					    <a class="btn btn-outline-success btn-sm btn-watch" href="watch?key=${media_content.id}"><i class="fa fa-play"></i> Watch</a>
					    <jsp:include page="../watchlist/add-item.jsp"></jsp:include>
				    </div>
			    </c:if>
			    
			    <br>
			    <br>
			    
			    <h5>Plot: &nbsp;<strong class="note">${media_content.plot}</strong></h5>
			    <br>
		    	<div>Type: &nbsp;<strong class="note">${media_content.type}</strong></div>
		    	<div>Year: &nbsp;<strong class="note">${media_content.year}</strong></div>
		    	<div>Released: &nbsp;<strong class="note">${media_content.humanReadableReleasedDateTime}</strong></div>
		    	<div>Type: &nbsp;<strong class="note">${media_content.type}</strong></div>
		    	<div>Runtime: &nbsp;<strong class="note">${media_content.runtime}</strong></div>
		    	<div>Stream time: &nbsp;<strong class="note">${media_content.getMinStreamTime()} min</strong></div>
		    	<div>Director: &nbsp;<strong class="note">${media_content.director}</strong></div>
		    	<div>Actors: &nbsp;<strong class="note">${media_content.actors}</strong></div>
		    	
		    	<br>
		    	
		    	<h5>
		    		<i class="fa fa-eye"></i> ${media_content.statistics.views} views&nbsp;&nbsp;&nbsp;
		    		<i class="fa fa-thumbs-up"></i> <span id="main-likes-count">${media_content.statistics.likes}</span>&nbsp;&nbsp;&nbsp;
		    		<i class="fa fa-thumbs-down"></i> <span id="main-nolikes-count">${media_content.statistics.nolikes}</span>
		    	</h5>
		</div>
		
		
		<div class="col-4">
			<img class="card-img-top can-point" src="${media_content.poster}">
		</div>
		
		<div class="col-1"></div>
		
	</div>
	
	<br><br><br>
	
	<div class="row">
		
		<div class="col-6" id="reviews-col">
			
			<h4 class="media-content-title">Reviews</h4><br>
			
			<div class="row">
				
				<div class="col-auto tile" id="avg-user-rating-col">
					<strong>Average user rating</strong><br><br>
					<h4><strong>${media_content.statistics.actualRate}</strong> / 5</h4>
					<jsp:include page="media_content_ratings.jsp"></jsp:include>
				</div>
				
				<div class="col-8 tile rating-breackdown-col">
					<strong>Rating breakdown</strong><br><br>
					
					<div class="row">
						<div class="col-auto">5 <i class="fa fa-star checked-star"></i></div>
						<div class="col"><div class="progress"><div class="progress-bar bg-success" style="width:${media_content.statistics.ratingBreakdown[0]}%"></div></div></div>
					</div>
					
					<div class="row">
						<div class="col-auto">4 <i class="fa fa-star checked-star"></i></div>
						<div class="col"><div class="progress"><div class="progress-bar bg-primary" style="width:${media_content.statistics.ratingBreakdown[1]}%"></div></div></div>
					</div>
					
					<div class="row">
						<div class="col-auto">3 <i class="fa fa-star checked-star"></i></div>
						<div class="col"><div class="progress"><div class="progress-bar bg-info" style="width:${media_content.statistics.ratingBreakdown[2]}%"></div></div></div>
					</div>
					
					<div class="row">
						<div class="col-auto">2 <i class="fa fa-star checked-star"></i></div>
						<div class="col"><div class="progress"><div class="progress-bar bg-warning" style="width:${media_content.statistics.ratingBreakdown[3]}%"></div></div></div>
					</div>
					
					<div class="row">
						<div class="col-auto">1 <i class="fa fa-star checked-star"></i></div>
						<div class="col"><div class="progress"><div class="progress-bar bg-danger" style="width:${media_content.statistics.ratingBreakdown[4]}%"></div></div></div>
					</div>
					
				</div>
				
			</div>
			
			<div class="row">
				<div class="col-auto tile" id="your-review-col">
				
					<strong>Your review</strong><br><br>
					
					<div class="row">
					
						<div class="col-auto" id="my-rate-col">
							
							<c:set var = "has_rate" scope = "request" value = "${user_review != null && user_review.rate != null}"/>
							
							<c:if test="${has_rate}">
								<h5 id="rate-string"><strong>${user_review.rate}</strong> / 5</h5>
								<c:forEach var="i" begin="1" end="${user_review.rate}">
									<span class="fa fa-star checked-star clickable"></span>
								</c:forEach>
								<c:forEach var="i" begin="${user_review.rate + 1}" end="5">
									<span class="fa fa-star clickable"></span>
								</c:forEach>
							</c:if>
							
							<c:if test="${!has_rate}">
								<h5 id="rate-string">Not rated</h5>
								<c:forEach var="i" begin="1" end="5">
									<span class="fa fa-star clickable"></span>
								</c:forEach>
							</c:if>
							
						</div>
						
						<div class="col-auto like-notlike-col">
							
							<c:set var = "has_like" scope = "request" value = "${user_review != null && user_review.like != null && user_review.like}"/>
							<h5 class="like-notlike-count">
								<c:if test="${has_like}"><i class="fa fa-thumbs-up text-primary clickable"></i></c:if>
								<c:if test="${!has_like}"><i class="fa fa-thumbs-up clickable"></i></c:if>
								<span id="likes-count">${media_content.statistics.likes}</span>
							</h5>I like
						</div>
						
						<div class="col-auto like-notlike-col">
							
							<c:set var = "has_notlike" scope = "request" value = "${user_review != null && user_review.like != null && !user_review.like}"/>
							<h5 class="like-notlike-count">
								<c:if test="${has_notlike}"><i class="fa fa-thumbs-down text-primary clickable"></i></c:if>
								<c:if test="${!has_notlike}"><i class="fa fa-thumbs-down clickable"></i></c:if>
								<span id="notlikes-count">${media_content.statistics.nolikes}</span>
							</h5>I don't like
						</div>
						
					</div>
					
				</div>
			</div>
			
		</div>
		
		<div class="col-6">
			<h4 class="media-content-title">Trailer</h4><br>
			<iframe class="media-content-trailer-frame" src="https://www.youtube.com/embed/vcLU0DhDhi0"></iframe>
		</div>
		
	</div>
	
	<br><br><br>
	
	<!-- <div class="row">
		
		<div class="col-1"></div>
		
		<div class="col-10">
			<h4 class="media-content-title">Reviews</h4><br>
		</div>
		
		<div class="col-1"></div>
		
	</div> -->
	
</div>