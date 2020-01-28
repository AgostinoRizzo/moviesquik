<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<div id="" class="media-contents-group-list-header row">
		<div id="media-contents-search-result-title" class="media-contents-search-result-title col col-12 col-lg-8">
			
			<div class="btn-group">
			
				<h5 class="media-contents-list-header-title">${media_content_list_title}</h5>
			
			</div>
									
		</div>
		
</div>

<div class="media-contents-list">
			
	<!-- Search result content -->
	<div id="search-result" class="media-contents-list-items row" role="listbox">
		<c:forEach items="${media_content_list}" var="current_media_content">
			<c:set var="media_content" scope="request" value="${current_media_content}"/>
			<div class="col-12 col-sm-6 col-md-4 col-xl-2 media-col-item">
			<jsp:include page="../contents-item.jsp"></jsp:include>
			</div>
		</c:forEach>
	</div>
    			
	<div class="more-collapse-box d-none">
		<button id="suggested-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
		<button id="suggested-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
	</div>
</div>