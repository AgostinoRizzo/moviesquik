<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div id="media-content-page-container" class="container">
	<div class="row">
		
		<div class="col-5 media-content-hader">
		
				<h2 class="media-content-title">${media_content.title}</h2>
			    <p class="media-content-production note"><strong>${media_content.production}</strong></p>
				
		    	<jsp:include page="media_content_ratings.jsp"></jsp:include>
		   
		    	<p class="">${media_content.genre}</p>
		    	<c:if test="${user != null}">
				    <div class="btn-group">
					    <a href="#" class="btn btn-outline-success btn-sm"><i class="fa fa-play"></i> Watch</a>
					    <a href="#" class="btn btn-outline-warning btn-sm"><i class="fa fa-plus"></i> Add</a>
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
		    	<div>Director: &nbsp;<strong class="note">${media_content.director}</strong></div>
		    	<div>Actors: &nbsp;<strong class="note">${media_content.actors}</strong></div>
		    	
		    	<br>
		    	
		    	&#128065;&nbsp;${media_content.views}&nbsp;&nbsp;&nbsp;&#129505;&nbsp;${media_content.likes}
		</div>
		
		<div class="col-4">
		</div>
		
		<div class="col-3">
			<img class="card-img-top can-point" src="${media_content.poster}">
		</div>
		
		<%-- <div class="col-3">
			<c:set var="small_view" scope="request" value="true"/>
			<jsp:include page="../contents.jsp"></jsp:include>
			<c:remove var="small_view"/>
		</div> --%>
		
	</div>
</div>