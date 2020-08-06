<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="chat-popup d-none">
	
	<div class="chat-popup-header">
		<div class="row user-list-row">
	
			<a href="user?id=3540" class="col-auto col-light-left"><img src="res/drawable/user_avatar.jpg" class="avatar-img card-list-avatar-img rounded-circle">
	  			<i class="fa fa-check-circle fa-xs line-status online-status"></i>
	  		</a>
	  		
	  		<div class="col users-list-name col-light-right">
	  			<p class="users-list-name-text"></p>
	  			<p class="note"></p>
			</div>
			
			<div class="col col-right">
				<h5><i class="fa fa-times clickable" id="close-chat-popup-btn"></i></h5>
			</div>
			
		</div>
	</div>
	
	<div class="chat-popup-body">
		
		<jsp:include page="chat-content.jsp"></jsp:include>
		
		<div id="chat-popup-text-form">
			
			<div class="media p-3 new-post-media">

				  <div class="media-body">
				  		<div class="row">
						    <div class="col col-light-left">
						    	<textarea class="form-control text-area-post" rows="2" id="new-msg-text" placeholder="Type a message..." name="post-text"></textarea>
						    </div>
						    
						    <div class="col-auto col-light-right" id="chat-send-btn-col">
						    	<button class="col post-action btn btn-primary btn-sm" id="chat-send-btn"><i class="fa fa-paper-plane"></i></button>
						    </div>
					    </div>
					    
					    
					    <div class="row post-action-row">
				    		<div class="row new-post-action-row">
				    			
				    			<!-- Emoji post action -->
						    	<div class="post-action dropdown row-item">
						    		
						    		<div class="btn btn-secondary dropdown-toggle" role="button" id="dropdown-emoji-panel-link" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						    			<h6>&#128522;</h6>&nbsp;Emoji
						    		</div>
						    		<jsp:include page="../posting/emoji_panel.jsp"></jsp:include>
						    	</div>
						    	
						    </div>
		 		
					    </div>
					    
					    <br>
				  </div>
			</div>
			
		</div>
		
	</div>
	
</div>