<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<c:if test="${user.notifications != null && !user.notifications.isEmpty()}">
	
	<c:set var = "new_notifications_counter" scope = "request" value = "0"/>
	
	<c:forEach items="${user.notifications}" var="notification">
		
		<div class="notification-box">
	           <div class="row notification-box-row">
	           
	           		 <!-- notification icon -->
	           		 
	           		 <c:if test="${notification.subjectUser != null}">
			             <div class="col-lg-1 col-sm-1 col-1 text-center">
			      			
			             			<c:set var = "user_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
									<c:if test="${notification.subjectUser.profileImagePath != null && notification.subjectUser.profileImagePath.length() > 0}">
										<c:set var = "user_profile_img_src" scope = "request" value = "${notification.subjectUser.profileImagePath}"/>
									</c:if>
			               			<img src="${user_profile_img_src}" class="notification-avatar-img rounded-circle">
			               		
			             </div>    
		             </c:if>
		             
		             <c:if test="${notification.subjectUser == null && notification.movieParty != null}">
			             <div class="col-lg-1 col-sm-1 col-1 text-center">
			               			<img src="res/drawable/movie_party_icon.png" class="notification-avatar-img">
			             </div>    
		             </c:if>
		             
		             
		             <!-- notification content -->
		             
		             <div class="col-lg-10 col-sm-10 col-10 notification-body">
		             	   
		             	   <div class="row">
			               		<div class="col">
			               			<strong class="">${notification.title}</strong>
			               			<c:if test="${user.unreadNotifications != null && new_notifications_counter < user.unreadNotifications.size()}">
			               				&nbsp;&nbsp;<small class="new-notification-marker">New</small>
			               				<c:set var = "new_notifications_counter" value = "${new_notifications_counter + 1}"/>
			               			</c:if>
			               		</div>
			               		<!-- <div class="col-2"><a href="#">more</a></div> -->
			               </div>
			               
			               <p>
			               		${notification.description}
			               </p>
			               
			               <small class="note" title="${notification.dateTime}">${notification.humanReadableDateTime}</small>
			               
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
	<button class="btn btn-link">show all</button>
</div>
