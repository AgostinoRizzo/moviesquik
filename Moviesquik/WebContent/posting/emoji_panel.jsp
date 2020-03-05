<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="dropdown-menu emoji-panel" aria-labelledby="dropdown-emoji-panel-link">
	  <div class="row">
      		<small class="note"><strong>People</strong></small>
      		<br>
      </div>
      <div class="row">
      		<c:forEach var="i" begin="12" end="80" step="1">
      			<div class="col emoji">&#1285${i};</div>
      		</c:forEach>
      		<c:forEach var="i" begin="296" end="301" step="1">
      			<div class="col emoji">&#129${i};</div>
      		</c:forEach>
      		<c:forEach var="i" begin="312" end="327" step="1">
      			<div class="col emoji">&#129${i};</div>
      		</c:forEach>
      		<div class="col emoji">&#129488;</div>
      		<div class="col emoji">&#129505;</div>
      		<c:forEach var="i" begin="147" end="155" step="1">
      			<div class="col emoji">&#128${i};</div>
      		</c:forEach>
      		<div class="col emoji">&#128077;</div>
      		<div class="col emoji">&#128075;</div>
      </div>
      <br>
      <div class="row">
      		<small class="note"><strong>All</strong></small>
      		<br>
      </div>
      <div class="row">
      		<c:forEach var="i" begin="127756" end="127994" step="1">
      			<div class="col emoji">&#0${i};</div>
      		</c:forEach>
      </div>
</div>