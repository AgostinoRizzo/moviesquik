<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>


<%-- <div class="row user-list-row">
		            			
	  <c:if test="${user.profileImagePath != null && user.profileImagePath.length() > 0}">
	  		<a href="user?id=${user.id}" class="col-auto sidenav-item-col"><img src="res/user/${user.profileImagePath}" class="avatar-img card-list-avatar-img rounded-circle">
	  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
	  		</a>
	  </c:if>
	  <c:if test="${user.profileImagePath == null || user.profileImagePath.length() == 0}">
	  		<a href="user?id=${user.id}" class="col-auto sidenav-item-col"><img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle">
	  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
	  		</a>
	  </c:if>		  
	  
	  <div class="col-auto users-list-name">
	    <p class="users-list-name-text">${user.fullName}</p>
	    <p class="note">${user.email}</p>
	  </div>
</div> --%>

<div class="users-col-title">
	<strong class="note">Friends</strong><br>
</div>
	
<c:forEach begin="0" end="10" step="1">
<c:forEach items="${user.friends}" var="friend">
	<div class="row user-list-row">
		            			
		  <c:if test="${friend.profileImagePath != null && friend.profileImagePath.length() > 0}">
		  		<a href="user?id=${friend.id}" class="col-auto col-light-left"><img src="res/user/${friend.profileImagePath}" class="avatar-img card-list-avatar-img rounded-circle">
		  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
		  		</a>
		  </c:if>
		  <c:if test="${friend.profileImagePath == null || friend.profileImagePath.length() == 0}">
		  		<a href="user?id=${friend.id}" class="col-auto col-light-left"><img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle">
		  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
		  		</a>
		  </c:if>		  
		  
		  <div class="col users-list-name col-light-right">
		    <p class="users-list-name-text">${friend.fullName}<%--  ${friend.lastName} --%></p>
		    <p class="note">${friend.email}</p>
		    <%-- <p><a href="#">${friend.email}</a></p> --%>
		  </div>
	</div>
</c:forEach>
</c:forEach>