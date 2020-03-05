<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<c:if test="${user.unreadNotifications != null && !user.unreadNotifications.isEmpty()}">
	
	<c:forEach items="${user.unreadNotifications}" var="notification">
		
		<div class="notification-box dropdown-item">
	           <div class="row">
		             <div class="col-lg-2 col-sm-2 col-2 text-center">
		             		
		             		<c:if test="${notification.subjectUser != null}">
		             			<c:set var = "user_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
								<c:if test="${notification.subjectUser.profileImagePath != null && notification.subjectUser.profileImagePath.length() > 0}">
									<c:set var = "user_profile_img_src" scope = "request" value = "res/user/${notification.subjectUser.profileImagePath}"/>
								</c:if>
		               			<img src="${user_profile_img_src}" class="notification-avatar-img rounded-circle">
		               		</c:if>
		               		
		             </div>    
		             <div class="col-lg-10 col-sm-10 col-10">
		             	   
		             	   <div class="row">
			               		<div class="col-10"><strong class="">${notification.title}</strong></div>
			               		<div class="col-2"><a href="#">more</a></div>
			               </div>
			               
			               <div>
			               		${notification.description}
			               </div>
			               
			               <small class="">${notification.dateTime}</small>
			               
			       	 </div>
			       	 
		 		</div>
         </div>
         
         <div class="dropdown-divider"></div>
		
	</c:forEach>	
	
</c:if>

<c:if test="${user.unreadNotifications == null || user.unreadNotifications.isEmpty()}">
	<a class="notification-box dropdown-item" href="">
		No unread notifications.
	</a>
	<div class="dropdown-divider"></div>
</c:if>

<div class="container show-all-notification-container">
	<a>show all</a>
</div>
