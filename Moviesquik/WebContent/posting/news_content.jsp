<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<c:if test="${user_to_display == null && posts == null}">
	<c:set var="user_to_display" scope="request" value="${user}"/>
	<c:set var="posts" scope="request" value="${user_to_display.allPosts}"/>
</c:if>
<c:if test="${posts == null}">
	<c:set var="posts" scope="request" value="${user_to_display.posts}"/>
</c:if>
<c:forEach items="${posts}" var="post">
	<c:set var="post_to_display" scope="request" value="${post}"/>
	<jsp:include page="./post_box.jsp"></jsp:include>
</c:forEach>