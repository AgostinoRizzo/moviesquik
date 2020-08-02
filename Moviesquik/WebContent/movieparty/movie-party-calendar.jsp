<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>


<div class="loader loader-sm d-none"></div>

<c:set var="i" scope="request" value="${1}"/>
<c:set var="date" scope="request" value=""/>

<c:forEach items="${parties}" var="party">
	
	<div class="clickable-light">
		<a href="movieparty?key=${party.id}">
			
			<c:set var="date" value="${party.humanReadableStartDateTime}"/>
			
			<c:if test="${i == 1}"><span class="badge badge-success">${date}</span></c:if>
			<c:if test="${i == 2}"><span class="badge badge-warning">${date}</span></c:if>
			<c:if test="${i == 3}"><span class="badge badge-info">${date}</span></c:if>
			<c:if test="${i == 4}">
				<span class="badge badge-danger">${date}</span>
				<c:set var="i" scope="request" value="${0}"/>
			</c:if>
			
			&nbsp;${party.name} - ${party.media.title}
			
		</a>
	</div>
	<br>
	
	<c:set var="i" value="${i+1}"/>
		
</c:forEach>

<c:if test="${parties.size() == 0}">
	No events in your calendar.
</c:if>