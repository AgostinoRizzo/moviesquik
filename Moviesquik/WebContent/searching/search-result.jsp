<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<!-- Search result content -->

<c:forEach items="${search_result.contents}" var="current_media_content">
	<c:set var="media_content" scope="request" value="${current_media_content}"/>
	<div class="col-12 col-sm-6 col-md-4 col-xl-2 media-col-item">
	<jsp:include page="../contents-item.jsp"></jsp:include>
	</div>
</c:forEach>