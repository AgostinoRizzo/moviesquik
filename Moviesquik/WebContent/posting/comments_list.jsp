<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<c:if test="${post_to_display.comments.size() > 0}">
	<div class="dropdown-divider"></div>
</c:if>

<div class="loader loader-sm d-none"></div>

<input type="hidden" id="num-comments-hidden" value="${post_to_display.numAllComments}">

<c:forEach items="${post_to_display.comments}" var="comment">

	<div class="media p-3 row media-post-box media-comment-box">
	
		  <div class="media-body">
		  		<div class="row">
		  			<c:set var = "post_owner_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
					<c:if test="${comment.owner.profileImagePath != null && comment.owner.profileImagePath.length() > 0}">
						<c:set var = "post_owner_profile_img_src" scope = "request" value = "res/user/${comment.owner.profileImagePath}"/>
					</c:if>
			  		<div class="col-auto col-light-left"><a href="user?id=${comment.owner.id}"><img src="${post_owner_profile_img_src}" width="30px" class="avatar-img rounded-circle"></a></div>
				    
				    <div class="col header-text col-light-right">
				    	<a class="a-hidden" href="user?id=${comment.owner.id}">${comment.owner.fullName}</a>
				    	&nbsp;
				    	<small class="note" data-toggle="tooltip" data-placement="top" title="${comment.dateTime}">${comment.humanReadableDateTime}</small>
				    	<p class="post-text">${comment.text}</p>
				    </div>
			    </div>
			    
			    
			    
		  </div>
	</div>

</c:forEach>


