<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="media p-3 media-post-box">

	  <input type="hidden" id="post-id" value="${post_to_display.id}">
	  
	  <div class="media-body">
	  		<div class="row">
	  			<c:set var = "post_owner_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
				<c:if test="${post_to_display.owner.profileImagePath != null && post_to_display.owner.profileImagePath.length() > 0}">
					<c:set var = "post_owner_profile_img_src" scope = "request" value = "res/user/${post_to_display.owner.profileImagePath}"/>
				</c:if>
		  		<div class="col-1"><img src="${post_owner_profile_img_src}" width="30px" class="avatar-img rounded-circle"></div>
			    <div class="col header-text">
			    	<h5>${post_to_display.owner.fullName}</h5>
			    	<small class="note" data-toggle="tooltip" data-placement="top" title="${post_to_display.dateTime}">Posted ${post_to_display.humanReadableDateTime}</small>
			    </div>
		    </div>
		    
		    <p class="post-text">${post_to_display.text}</p>
		    
		    <div class="row" id="count-row">
		    	<div class="col">
		    		<small class="fa-container"><a href="#"><i class="fa fa-thumbs-up rounded-circle"></i></a></small>
		    		<small class="fa-container"><a href="#"><i class="fa fa-heart rounded-circle"></i></a></small>
		    		<small class="note">&nbsp;&nbsp; 123 likes, 123 loves</small>
		    	</div>
		    	<div class="col" id="n-comments-col">
		    		<small class="note show-comments-list-btn">123 comments</small>
		    	</div>
		    </div>
		    
		    <div class="dropdown-divider"></div>
		    
		    <div class="row post-action-row">
		    	<div class="col post-action"><p class="btn"><i class="fa fa-thumbs-up"></i>&nbsp;&nbsp;Like<p></div>
		    	<div class="col post-action"><p class="btn comment-btn"><i class="fa fa-comment"></i>&nbsp;&nbsp;Comment<p></div>
		    	<div class="col post-action"><p class="btn"><i class="fa fa-share"></i>&nbsp;&nbsp;Share<p></div>
		    </div>
		    
		    <div class="comments-list">
		    	<jsp:include page="./comments_list.jsp"></jsp:include>
		    </div>
		    
	  </div>
</div>

<jsp:include page="./new_comment_box.jsp"></jsp:include>
