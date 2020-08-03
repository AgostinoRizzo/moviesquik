<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

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
		   		
		    	<p class="">${media_content.genre}</p>
		    	<c:if test="${user != null}">
				    <div class="btn-group">
					    <a class="btn btn-outline-success btn-sm btn-watch" href="watch?key=${media_content.id}"><i class="fa fa-play"></i> Watch</a>
					    <jsp:include page="../watchlist/add-item.jsp"></jsp:include>
				    </div>
			    </c:if>
			    
			    <br>
			    <br>
			    
			    <div>Plot: &nbsp;<strong class="note">${media_content.plot}</strong></div>
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
		    	
		    	&#128065;&nbsp;${media_content.views}&nbsp;&nbsp;&nbsp;&#129505;&nbsp;${media_content.likes}
		</div>
		
		
		<div class="col-4">
			<img class="card-img-top can-point" src="${media_content.poster}">
		</div>
		
		<div class="col-1"></div>
		
		<%-- <div class="col-3">
			<c:set var="small_view" scope="request" value="true"/>
			<jsp:include page="../contents.jsp"></jsp:include>
			<c:remove var="small_view"/>
		</div> --%>
		
	</div>
	
	<br>
	
	<div class="row">
		
		<div class="col-1"></div>
		
		<div class="col-10">
			<h4 class="media-content-title">Trailer</h4><br>
			<iframe class="media-content-trailer-frame" src="https://www.youtube.com/embed/vcLU0DhDhi0"></iframe>
		</div>
		
		<div class="col-1"></div>
		
	</div>
	
	<br><br><br>
	
	<div class="row">
		
		<div class="col-1"></div>
		
		<div class="col-10">
			<h4 class="media-content-title">Reviews</h4><br>
		</div>
		
		<div class="col-1"></div>
		
	</div>
	
</div>