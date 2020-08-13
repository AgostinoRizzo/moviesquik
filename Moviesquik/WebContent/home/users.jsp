<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>


<div class="users-col-title">
	<strong class="note">Messages</strong><br>
	<input type="text" id="chat-search-input" placeholder="Search">
</div>
	
<c:forEach items="${user.friends}" var="friend">
	<div class="row user-list-row clickable-light user-chat-row">
		  
		<input type="hidden" id="friend-id" value="${friend.id}">
					
		<c:if test="${friend.profileImagePath != null && friend.profileImagePath.length() > 0}">
			<div class="col-auto col-light-left user-icon-col"><img src="${friend.profileImagePath}" class="avatar-img card-list-avatar-img rounded-circle">
				<i class="fa fa-check-circle fa-xs line-status offline-status"></i>
			</div>
		</c:if>
		<c:if test="${friend.profileImagePath == null || friend.profileImagePath.length() == 0}">
			<div class="col-auto col-light-left user-icon-col"><img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle">
				<i class="fa fa-check-circle fa-xs line-status offline-status"></i>
			</div>
		</c:if>		  
		 
		<div class="col users-list-name col-light-right">
			<p class="users-list-name-text">${friend.fullName}</p>
			<p class="note">${friend.email}</p>
		</div>
		
		<div class="col-auto col-light-right col-light-left col-right">
			<small class="chat-note user-chat-last-time">00:00</small><br>
			<div class="new-message-badge d-none">1</div>
		</div>
		  
	</div>
</c:forEach>