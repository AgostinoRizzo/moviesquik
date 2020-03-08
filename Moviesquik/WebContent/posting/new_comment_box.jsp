<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="media p-3 new-post-media new-comment-media">

	  <div class="media-body">
	  		<div class="row">
		  		<div class="col-1"><img src="${user_profile_img_src}" width="30px" class="avatar-img rounded-circle"></div>
			    <div class="col-11">
			    	<textarea class="form-control text-area-post new-comment-text" rows="1" id="new-comment-text" placeholder="Write a comment..." name="comment-text"></textarea>
			    
			    	<div class="dropdown-divider"></div>
				    
				    <div class="row post-action-row">
				    		<div class="row new-post-action-row">
				    			
				    			<!-- Emoji post action -->
						    	<div class="post-action dropdown row-item">
						    		
						    		<div class="btn btn-secondary dropdown-toggle" role="button" id="dropdown-emoji-panel-link" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						    			<h5>&#128522;</h5>&nbsp;Emoji
						    		</div>
						    		<jsp:include page="./emoji_panel.jsp"></jsp:include>
						    	</div>
						    	
						    	<div class="row-item publish-comment-row-item">
						    		<button class="col post-action btn btn-main btn-sm new-comment-submit-btn">Comment</button>
						    	</div>
						    	
						    </div>
				    	
				    </div>
		    
			    </div>
		    </div>
		    
		    <br>
	  </div>
</div>
