<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<c:set var="foreign_user" scope="request" value="${party.isPrivate() && (invitation == null || (invitation.answer == null || invitation.answer != 'PARTICIPATE')) && !party.administrator.getId().equals(user.getId())}"/>

<c:set var = "user_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
<c:if test="${user.profileImagePath != null && user.profileImagePath.length() > 0}">
	<c:set var = "user_profile_img_src" scope = "request" value = "${user.profileImagePath}"/>
</c:if>

<div id="movie-party-chat-container">

	<div class="media p-3 new-post-media">

		  <div class="media-body">
		  		<div class="row">
			  		<div class="col-auto col-light-left"><img src="${user_profile_img_src}" width="30px" class="avatar-img rounded-circle" id="user-icon"></div>
				    <div class="col col-light-right col-light-left">
				    	<textarea class="form-control text-area-post" rows="2" id="new-msg-text" placeholder="What do you think about ${party.media.title}, ${user.firstName}?" name="post-text"></textarea>
				    </div>
				    
				    <div class="col col-light-right" id="chat-send-btn-col">
				    	<button class="col post-action btn btn-success btn-sm" id="chat-send-btn"><i class="fa fa-paper-plane"></i></button>
				    </div>
			    </div>
			    
			    
			    <div class="dropdown-divider"></div>
			    
			    <div class="row post-action-row">
			    		<div class="row new-post-action-row">
			    			
			    			<!-- Emoji post action -->
					    	<div class="post-action dropdown row-item">
					    		
					    		<div class="btn btn-secondary dropdown-toggle" role="button" id="dropdown-emoji-panel-link" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					    			<h5>&#128522;</h5>&nbsp;Emoji
					    		</div>
					    		<jsp:include page="../posting/emoji_panel.jsp"></jsp:include>
					    	</div>
					    	
					    </div>
					    
					    <div class="row participants-row text-overflow" id="chat-participants">
		    	
				  			<c:set var = "party_admin_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
							<c:if test="${party.administrator.profileImagePath != null && party.administrator.profileImagePath.length() > 0}">
								<c:set var = "party_admin_profile_img_src" scope = "request" value = "${party.administrator.profileImagePath}"/>
							</c:if>
					  		<div class="col-auto col-light-left"><a href="user?id=${party.administrator.id}"><img src="${party_admin_profile_img_src}" width="30px" class="avatar-img rounded-circle"></a></div>
				  			
				  			<c:forEach items="${party.participations}" var="participation">
				  				<c:set var = "party_participant_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
								<c:if test="${participation.participant.profileImagePath != null && participation.participant.profileImagePath.length() > 0}">
									<c:set var = "party_participant_profile_img_src" scope = "request" value = "${participation.participant.profileImagePath}"/>
								</c:if>
						  		<div class="col-auto col-light-right col-light-left"><a href="user?id=${participation.participant.id}"><img src="${party_participant_profile_img_src}" width="30px" class="avatar-img rounded-circle"></a></div>
				  			</c:forEach>
				  			
				  		</div>
 		
					    
			    </div>
			    
			    <br>
		  </div>
	</div>
	
	<br>
	
	<!-- movie party chat messages section -->
	<c:if test="${foreign_user}">
	
		<div>
			<input type="hidden" id="foreign-user-flag" value="${true}">
			<strong><i class="fa fa-lock"></i> The party conversation is private.</strong><br>Please take part to this event to access it.
		</div>
		
		<div class="modal" id="cannot-send-msg-modal" tabindex="-1">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <h5 class="modal-title">
		                	<i class="fa fa-warning colored"></i> Private party conversation
		                </h5>
		                <button type="button" class="close" data-dismiss="modal">&times;</button>
		            </div>
		            <div class="modal-body">
		            	<strong>You cannot send messages to this conversation.</strong> 
		            	Please take part to this event to access and communicate with all the participants of this group. 
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-primary" data-dismiss="modal">Okay</button>
		            </div>
		        </div>
		    </div>
		</div>
		
	</c:if>
	
	<c:if test="${!foreign_user}">
		<jsp:include page="../chat/chat-content.jsp"></jsp:include>
	</c:if>

</div>