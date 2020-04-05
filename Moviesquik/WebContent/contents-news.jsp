<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<c:if test="${user != null}">

	<!-- Suggested Media Contents -->
	<c:if test="${search_result.suggestedContents.size() > 0}">
		<div id="contents-news-group">
		
			<c:set var="media_content_list_title" scope="request" value="Suggested for you"/>
			<c:set var="media_content_list" scope="request" value="${search_result.suggestedContents}"/>
			<c:set var="home_news_view" scope="request" value="1"/>
			<jsp:include page="searching/media-group.jsp"></jsp:include>
			
		</div>
	</c:if>

</c:if>