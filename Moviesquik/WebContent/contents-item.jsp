<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="small_view" scope="request" value="1"/>

<div class="card media-content-card view-overlay">
	  
	  <input type="hidden" id="media-id" value="${media_content.id}">
	
	  <div class="card-image-container">
	  		<div class="remove-media-item-btn"><i class="fa fa-times clickable"></i></div>
		  <img class="card-img-top can-point" src="${media_content.poster}">
	  </div>
	 <%--  <c:if test="${empty small_view && empty home_news_view && empty not_view_media_body}">
		  <div class="card-body">
		  
		    <jsp:include page="content-ratings.html"></jsp:include>
		    
		    <h5 class="card-title">${media_content.title}</h5>
		    <p class="card-text note">${media_content.production}</p>
		    
		    <c:if test="${user != null && empty home_news_view}">
			    <div class="btn-group">
				    <a href="#" class="btn btn-outline-success btn-sm"><i class="fa fa-play"></i> Watch</a>
				    <jsp:include page="watchlist/add-item.jsp"></jsp:include>
			    </div>
		    </c:if>
		  </div>
	  </c:if> --%>
	  
	  <%-- <c:if test="${not empty small_view || not empty not_view_media_body}"> --%>
		  	<div class="card-body">
		  	
			    
			    
			    <p class="card-title">${media_content.title}</p>
			    <jsp:include page="content-ratings.jsp"></jsp:include>
			    <%-- <p class="card-text note">${media_content.production}</p> --%>
			    
			  </div>
	  <%-- </c:if> --%>
	  
</div>