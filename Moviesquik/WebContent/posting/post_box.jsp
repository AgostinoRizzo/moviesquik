<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="media p-3 media-post-box main-media-post-box tile">

	  <input type="hidden" id="post-id" value="${post_to_display.id}">
	  
	  <div class="media-body">
	  		<div class="row">
	  			<c:set var = "post_owner_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
				<c:if test="${post_to_display.owner.profileImagePath != null && post_to_display.owner.profileImagePath.length() > 0}">
					<c:set var = "post_owner_profile_img_src" scope = "request" value = "${post_to_display.owner.profileImagePath}"/>
				</c:if>
		  		<div class="col-auto col-light-left"><a href="user?id=${post_to_display.owner.id}"><img src="${post_owner_profile_img_src}" width="30px" class="avatar-img rounded-circle"></a></div>
			    <div class="col col-light-right header-text">
			    	<h6><a class="a-hidden" href="user?id=${post_to_display.owner.id}">${post_to_display.owner.fullName}</a><%-- &nbsp;&nbsp;<small class="note">${post_to_display.owner.email}</small> --%></h6>
			    	<small class="note" data-toggle="tooltip" data-placement="top" title="${post_to_display.dateTime}">Posted ${post_to_display.humanReadableDateTime}</small>
			    </div>
		    </div>
		    
		    <p class="post-text">${post_to_display.text}</p>
		    
		    <!-- additional post information -->
		    <c:if test="${post_to_display.watchlist != null}">
		    	<div class="shared-watchlist-box can-point">
			    	<input type="hidden" id="shared-watchlist-id" value="${post_to_display.watchlist.id}">
			    	<p class="post-text">
			    		<i class="fa fa-list-ul"></i>&nbsp;${watchlist.name}${post_to_display.watchlist.name}<br>
			    		<small class="note">${post_to_display.watchlist.description}</small></p>
				    <div class="card-image-container watchlist-item-card-img-container row">
				    	<c:forEach items="${post_to_display.watchlist.previewItems}" var="watchlist_item">
							<img class="watchlist-item-card-img col-4 clickable" src="${watchlist_item.mediaContent.poster}">
						</c:forEach>
				  </div>
			  </div>
			  <br>
		    </c:if>
		    
		    <div class="row" id="count-row">
		    	<div class="col">
		    		<small class="fa-container"><a class="add-like"><i class="fa fa-thumbs-up rounded-circle clickable"></i></a></small>
		    		<small class="fa-container"><a class="add-love"><i class="fa fa-heart rounded-circle clickable"></i></a></small>
		    		<small class="note" id="likes-loves-count">&nbsp;&nbsp; ${post_to_display.numLikes} likes, ${post_to_display.numLoves} loves</small>
		    	</div>
		    	<div class="col" id="n-comments-col">
		    		<small class="note show-comments-list-btn">${post_to_display.numAllComments} comments</small>
		    	</div>
		    </div>
		    
		    <div class="dropdown-divider"></div>
		    
		    <div class="row post-action-row">
		    	<div class="col post-action"><p class="btn add-like"><i class="fa fa-thumbs-up"></i>&nbsp;&nbsp;Like<p></div>
		    	<div class="col post-action"><p class="btn comment-btn"><i class="fa fa-comment"></i>&nbsp;&nbsp;Comment<p></div>
		    	<form class="col post-action" method="POST" action="sharepost">
		    		<input type="hidden" name="share-post-id" value="${post_to_display.id}">
		    		<p class="btn share-post-submit-btn"><i class="fa fa-share"></i>&nbsp;&nbsp;Share<p>
		    	</form>
		    </div>
		    
		    <div class="comments-list">
		    	<jsp:include page="./comments_list.jsp"></jsp:include>
		    </div>
		    
	  </div>
</div>

<jsp:include page="./new_comment_box.jsp"></jsp:include>

