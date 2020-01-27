<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<!-- Search result content -->

<div id="media-contents-search-update-result-title" class="media-contents-search-result-title col col-12 col-lg-8 d-none">
	<h5 class="media-contents-list-header-title note">Explore titles related to: </h5>
	&nbsp;
	<!-- <span class="input-group-addon fa fa-film"></span> -->
	
	<c:set var="media_content_count" scope="request" value="1"/>
	<c:forEach items="${search_result.contents}" var="media_content">
		
		<h5 class="media-contents-list-header-title">
			${media_content.title}
			<c:if test="${media_content_count < search_result.contents.size()}">
				&nbsp;|&nbsp;
			</c:if>
		</h5>
		
		<c:set var="media_content_count" scope="request" value="${media_content_count + 1}"/>
		
	</c:forEach>
							
</div>

<c:forEach items="${search_result.contents}" var="current_media_content">
	<c:set var="media_content" scope="request" value="${current_media_content}"/>
	<div class="col-12 col-sm-6 col-md-4 col-xl-2 media-col-item">
	<jsp:include page="../contents-item.jsp"></jsp:include>
	</div>
</c:forEach>