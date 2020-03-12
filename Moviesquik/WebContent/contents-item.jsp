<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<div class="card media-content-card view-overlay">
	  
	  <img class="card-img-top" src="${media_content.poster}">
	  
	  <c:if test="${empty small_view && empty home_news_view}">
		  <div class="card-body">
		  
		    <jsp:include page="content-ratings.html"></jsp:include>
		    
		    <h5 class="card-title">${media_content.title}</h5>
		    <p class="card-text note">${media_content.production}</p>
		    
		    <c:if test="${user != null && empty home_news_view}">
			    <div class="btn-group">
				    <a href="#" class="btn btn-outline-success btn-sm"><i class="fa fa-play"></i> Watch</a>
				    <a href="#" class="btn btn-outline-warning btn-sm"><i class="fa fa-plus"></i> Add</a>
			    </div>
		    </c:if>
		  </div>
	  </c:if>
	  
	  <c:if test="${not empty small_view}">
		  	<div class="card-body">
			    
			    <p class="card-title">${media_content.title}</p>
			    <%-- <p class="card-text note">${media_content.production}</p> --%>
			    
			  </div>
	  </c:if>
	  
</div>