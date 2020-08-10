<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<c:forEach var="i" begin="1" end="${media_content.statistics.rate}">
	<span class="fa fa-star checked-star"></span>
</c:forEach>
<c:forEach var="i" begin="${media_content.statistics.rate + 1}" end="5">
	<span class="fa fa-star"></span>
</c:forEach>
