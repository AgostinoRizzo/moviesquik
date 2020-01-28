<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<c:if test="${search_result.viewTemplate == 'full'}">
	
	<div id="media-contents-search-update-result-title" class="media-contents-search-result-title col col-12 col-lg-8 d-none">
		<h5 class="media-contents-list-header-title note">Explore titles related to: </h5>
		&nbsp;
		<!-- <span class="input-group-addon fa fa-film"></span> -->
		
		<c:set var="media_content_count" scope="request" value="1"/>
		<c:forEach items="${search_result.fullContents}" var="media_content">
			
			<h5 class="media-contents-list-header-title">
				${media_content.title}
				<c:if test="${media_content_count < search_result.fullContents.size()}">
					&nbsp;|&nbsp;
				</c:if>
			</h5>
			
			<c:set var="media_content_count" scope="request" value="${media_content_count + 1}"/>
			
		</c:forEach>
								
	</div>

	<div class="media-contents-list">
	
		<!-- Search result content -->
		<div id="search-result" class="media-contents-list-items row" role="listbox">
			<c:forEach items="${search_result.fullContents}" var="current_media_content">
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


</c:if>

<c:if test="${search_result.viewTemplate == 'group'}">
	
	<!-- Suggested Media Contents -->
	<c:if test="${search_result.suggestedContents.size() > 0}">
		<c:set var="media_content_list_title" scope="request" value="Suggested for you"/>
		<c:set var="media_content_list" scope="request" value="${search_result.suggestedContents}"/>
		<jsp:include page="media-group.jsp"></jsp:include>
	</c:if>
	
	<!-- Recently Watched Media Contents -->
	<c:if test="${search_result.recentlyWatchedContents.size() > 0}">
		<c:set var="media_content_list_title" scope="request" value="Recently watched"/>
		<c:set var="media_content_list" scope="request" value="${search_result.recentlyWatchedContents}"/>
		<jsp:include page="media-group.jsp"></jsp:include>
	</c:if>
	
	<!-- Top Rated Media Contents -->
	<c:if test="${search_result.topRatedContents.size() > 0}">
		<c:set var="media_content_list_title" scope="request" value="Top rated"/>
		<c:set var="media_content_list" scope="request" value="${search_result.topRatedContents}"/>
		<jsp:include page="media-group.jsp"></jsp:include>
	</c:if>
	
	<!-- Most Popular Media Contents -->
	<c:if test="${search_result.mostPopularContents.size() > 0}">
		<c:set var="media_content_list_title" scope="request" value="Popular"/>
		<c:set var="media_content_list" scope="request" value="${search_result.mostPopularContents}"/>
		<jsp:include page="media-group.jsp"></jsp:include>
	</c:if>
	
	<!-- Most Favorites Media Contents -->
	<c:if test="${search_result.mostFavoritesContents.size() > 0}">
		<c:set var="media_content_list_title" scope="request" value="Most favorites"/>
		<c:set var="media_content_list" scope="request" value="${search_result.mostFavoritesContents}"/>
		<jsp:include page="media-group.jsp"></jsp:include>
	</c:if>
	
</c:if>