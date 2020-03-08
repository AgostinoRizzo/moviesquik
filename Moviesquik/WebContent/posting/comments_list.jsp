<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<c:if test="${post_to_display.comments.size() > 0}">
	<div class="dropdown-divider"></div>
</c:if>

<div class="loader loader-sm d-none"></div>

<c:forEach items="${post_to_display.comments}" var="comment">

	<div class="media p-3 row media-post-box media-comment-box">
	
		  <div class="media-body">
		  		<div class="row">
		  			<c:set var = "post_owner_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
					<c:if test="${comment.owner.profileImagePath != null && comment.owner.profileImagePath.length() > 0}">
						<c:set var = "post_owner_profile_img_src" scope = "request" value = "res/user/${comment.owner.profileImagePath}"/>
					</c:if>
			  		<div class="col-1"><img src="${post_owner_profile_img_src}" width="30px" class="avatar-img rounded-circle"></div>
				    
				    <div class="col header-text">
				    	<a>${comment.owner.fullName}</a>
				    	&nbsp;
				    	<small class="note" data-toggle="tooltip" data-placement="top" title="${comment.dateTime}">${comment.humanReadableDateTime}</small>
				    	<p class="post-text">${comment.text}</p>
				    </div>
			    </div>
			    
			    
			    
		  </div>
	</div>

</c:forEach>


