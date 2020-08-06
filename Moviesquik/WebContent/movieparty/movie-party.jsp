<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<c:set var="foreign_user" scope="request" value="${party.isPrivate() && (invitation == null || (invitation.answer == null || invitation.answer != 'PARTICIPATE')) && !party.administrator.getId().equals(user.getId())}"/>

<c:set var = "user_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
<c:if test="${user.profileImagePath != null && user.profileImagePath.length() > 0}">
	<c:set var = "user_profile_img_src" scope = "request" value = "${user.profileImagePath}"/>
</c:if>

<input type="hidden" id="user-id" value="${user.id}">

<div id="create-party-page-content" class="">
	
	<div id="movie-party-header">
	
		<div class="note">MOVIE PARTY</div>
		<h2>${party.name}</h2>
		<h5 class="note">${party.description}</h5>
		
		<br>
		
		<c:if test="${party.isExpired() && !party.isPlaying()}"><h5><span class="badge badge-danger">The event is expired</span> - started ${party.humanReadableStartDateTime}</h5></c:if>
		
		<c:if test="${!party.isExpired()}">
			
			<c:if test="${!party.isPlaying()}">
				<h5><span class="badge badge-info"><i class="fa fa-calendar"></i> Upcoming</span> - ${party.humanReadableStartDateTime}</h5><br>
			</c:if>
			
			<c:if test="${party.isPrivate() && invitation != null}">
				<c:if test="${invitation.answer == null || invitation.answer == 'MAYBE'}">
					<a href="movieparty?key=${party.id}&action=invitation&value=participate"><button type="button" class="btn btn-link"><i class="fa fa-check-square"></i> Participate</button></a>
				</c:if>
				<c:if test="${invitation.answer == null}">
					<a href="movieparty?key=${party.id}&action=invitation&value=maybe"><button type="button" class="btn btn-link"><i class="fa fa-question-circle"></i> Maybe</button></a>
				</c:if>
				<c:if test="${invitation.answer == null || invitation.answer == 'MAYBE'}">
					<a href="movieparty?key=${party.id}&action=invitation&value=not"><button type="button" class="btn btn-link"><i class="fa fa-times-circle"></i> I can't</button></a>
				</c:if>
			</c:if>
			<c:if test="${!party.isPrivate() && (invitation == null || (invitation != null && invitation.answer == null)) && !party.administrator.getId().equals(user.getId())}">
				<a href="movieparty?key=${party.id}&action=invitation&value=participate"><button type="button" class="btn btn-link">Add to calendar <i class="fa fa-calendar-check-o"></i></button></a>
			</c:if>
			
		</c:if>
		
	</div>
	
	<c:if test="${party.isExpired() && party.isPlaying()}">
		<h5><span class="badge badge-success"><i class="fa fa-circle"></i> Being played now...</span> - started ${party.humanReadableStartDateTime}</h5><br>
		<c:if test="${!foreign_user}">
			<div class="btn btn-link btn-sm" id="join-btn"><i class="fa fa-play"></i> Join now</div>
		</c:if>
	</c:if>
	
	<c:if test="${party.isExpired() && foreign_user}">
		<c:if test="${!party.isPlaying()}"><br></c:if>
		<div class="text-danger">You cannot join this event anymore!</div>
	</c:if>
	
	<br>
	
	<div><img class="movieparty-media-card-img" src="${party.media.poster}"></div>		
	
	<br><br>
	
	<div class="container">
		<div class="row">
		
			<div class="media p-3 media-post-box main-media-post-box movieparty-media-box col-lg-6">
			
				  <input type="hidden" id="party-id" value="${party.id}">
				  <input type="hidden" id="media-id" value="${party.media.id}">
				  
				  <div class="media-body">
				  
				  		<div class="row">
				  			<div class="col-auto col-light-left"><img src="res/drawable/movie_party_icon.png" width="50px" class=""></div>
						    <div class="col col-light-right header-text">
						    	<h5>
						    		${party.name}
						    		<c:if test="${party.description != null && party.description.length() > 0}"> - ${party.description}</c:if>
						    	
						    	</h5>
						    	
						    	<c:if test="${party.isPrivate()}"><span class="badge badge-warning"><i class="fa fa-lock"></i> Private event</span></c:if>
			    				<c:if test="${!party.isPrivate()}"><span class="badge badge-success"><i class="fa fa-globe"></i> Public event</span></c:if>
						    	<small class="note" data-toggle="tooltip" data-placement="top" title="${party.creationDateTime}">
						    		 Posted ${party.humanReadableCreationDateTime}
						    	</small>
						    </div>
				  		</div>
					    
					    <br>
					    
					    <!-- media content information -->
				    	<div class="row">
						
							<div class="col-12">
									
									<div class="row">
										<div class="col">Start time</div>
										<div class="col col-right note text-overflow">
											<c:if test="${party.isPlaying()}"><span class="badge badge-success"><i class="fa fa-play"></i> playing now</span></c:if>
											<c:if test="${party.isExpired() && !party.isPlaying()}"><span class="badge badge-danger">expired</span></c:if>
											<c:if test="${!party.isExpired() && !party.isPlaying()}"><span class="badge badge-info"><i class="fa fa-calendar"></i> Upcoming</span></c:if>
											<c:if test="${party.isExpired()}">started </c:if>${party.humanReadableStartDateTime}
										</div>
									</div>
									
									<div class="row"><div class="col">Movie</div><div class="col col-right note text-overflow clickable-light media-content-link text-info">${party.media.title}</div></div>
									<div class="row"><div class="col">Duration</div><div class="col col-right note text-overflow">${party.media.getMinStreamTime()} min</div></div>
									
									<hr class="mb-4">
									
									<div class="row"><div class="col text-overflow">Group organizer and coordinator</div></div>
									<br>
									<div class="row administrator-row">
										<div class="col-9 text-overflow">
											<div class="row">
									  			<c:set var = "party_admin_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
												<c:if test="${party.administrator.profileImagePath != null && party.administrator.profileImagePath.length() > 0}">
													<c:set var = "party_admin_profile_img_src" scope = "request" value = "${party.administrator.profileImagePath}"/>
												</c:if>
										  		<div class="col-auto col-light-left"><a href="user?id=${party.administrator.id}"><img src="${party_admin_profile_img_src}" width="30px" class="avatar-img rounded-circle"></a></div>
											    <div class="col col-light-right header-text text-overflow">
											    	<div><a class="a-hidden" href="user?id=${party.administrator.id}">${party.administrator.fullName}</a></div>
											    	<small class="note" data-toggle="tooltip" data-placement="top" title="${party.creationDateTime}">
											    		<span class="badge badge-success">Administrator</span>
											    	</small>
											    </div>
										    </div>
										</div>
									</div>
									
									<hr class="mb-4">
									
								    <c:if test="${party.isPrivate()}">
									    
										<div class="">
										<div class="row"><div class="col text-overflow">${party.invitations.size()} total invitations</div></div>
										<div class="row invitations-row text-overflow">
									    	
								  			<c:forEach items="${party.invitations}" var="invitation">
								  				<c:set var = "party_guest_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
												<c:if test="${invitation.guest.profileImagePath != null && invitation.guest.profileImagePath.length() > 0}">
													<c:set var = "party_guest_profile_img_src" scope = "request" value = "${invitation.guest.profileImagePath}"/>
												</c:if>
										  		<div class="col-auto col-light-left"><a href="user?id=${invitation.guest.id}"><img src="${party_guest_profile_img_src}" width="30px" class="avatar-img rounded-circle"></a></div>
								  			</c:forEach>
								  			
								  		</div>
								  		</div>
								  		<br><br>
								  		
							  		</c:if>
							  		
									<div class="">
									<div class="row"><div class="col text-overflow">${party.participations.size() + 1} participants</div></div>
									<div class="row participants-row text-overflow">
					    	
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
							  		
								</div>
					    </div>
					    
					    
				  </div>
			</div>
			
			<!-- movie party chat section -->
			<div class="col-lg-6">
				<jsp:include page="movie-party-chat.jsp"></jsp:include>
			</div>
		
		</div>
	</div>
				
</div>


<!-- info modal -->
<c:if test="${party.isPrivate()}">

	<c:if test="${on_participate != null}">
		<div class="modal party-info-modal" tabindex="-1">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <h5 class="modal-title">
		                	<i class="fa fa-check colored"></i> You are now a participant of this event
		                </h5>
		                <button type="button" class="close" data-dismiss="modal">&times;</button>
		            </div>
		            <div class="modal-body">
		            	You can now watch the event movie and communicate with all the participants of this group. 
		            	<strong>You will receive all notifications for this event.</strong>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-primary" data-dismiss="modal">Okay</button>
		            </div>
		        </div>
		    </div>
		</div>
	</c:if>
	
	<c:if test="${on_maybe != null}">
		<div class="modal party-info-modal" tabindex="-1">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <h5 class="modal-title">
		                	<i class="fa fa-check colored"></i> This event will be ignored
		                </h5>
		                <button type="button" class="close" data-dismiss="modal">&times;</button>
		            </div>
		            <div class="modal-body">
		            	To watch the event movie and communicate with all the participants of this group, please became a member. 
		            	<strong>You will not receive notifications for this event.</strong>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-primary" data-dismiss="modal">Okay</button>
		            </div>
		        </div>
		    </div>
		</div>
	</c:if>
	
	<c:if test="${on_not != null}">
		<div class="modal party-info-modal" tabindex="-1">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <h5 class="modal-title">
		                	<i class="fa fa-check colored"></i> This event will be discarded
		                </h5>
		                <button type="button" class="close" data-dismiss="modal">&times;</button>
		            </div>
		            <div class="modal-body">
		            	You cannot watch the event movie and communicate with all the participants of this group. 
		            	<strong>You will not receive any notification for this event.</strong>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-primary" data-dismiss="modal">Okay</button>
		            </div>
		        </div>
		    </div>
		</div>
	</c:if>
	
</c:if>

<c:if test="${on_participate != null && !party.isPrivate()}">
	<div class="modal party-info-modal" tabindex="-1">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title">
	                	<i class="fa fa-check colored"></i> Event added to your calendar
	                </h5>
	                <button type="button" class="close" data-dismiss="modal">&times;</button>
	            </div>
	            <div class="modal-body">
	            	You are now a participant of this event and you can now watch the event movie. 
	            	<strong>You will receive all notifications for this event.</strong>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-primary" data-dismiss="modal">Okay</button>
	            </div>
	        </div>
	    </div>
	</div>
</c:if>
