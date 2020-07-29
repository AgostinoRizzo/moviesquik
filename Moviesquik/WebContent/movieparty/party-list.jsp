<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<c:if test="${parties == null}">
	<c:set var = "parties" scope = "request" value = "${user.allMovieParties}"/>
</c:if>

<c:forEach items="${parties}" var="party">

	<div class="media p-3 media-post-box main-media-post-box tile movieparty-media-box">
	
		  <input type="hidden" id="party-id" value="${party.id}">
		  
		  <img class="movieparty-media-card-img" src="${party.media.poster}">
		  
		  <div class="media-body">
		  
		  		<div class="row">
		  			<div class="col-auto col-light-left"><img src="res/drawable/movie_party_icon.png" width="50px" class=""></div>
				    <div class="col col-light-right header-text">
				    	<h6>
				    		<a href="movieparty?key=${party.id}">${party.name}</a>
				    		<c:if test="${party.description != null && party.description.length() > 0}"> - ${party.description}</c:if>
				    	
				    	</h6>
				    	<small class="note" data-toggle="tooltip" data-placement="top" title="${party.creationDateTime}">
				    		<c:if test="${party.isPrivate()}"><span class="badge badge-warning">Private event</span></c:if>
				    		<c:if test="${!party.isPrivate()}"><span class="badge badge-success">Public event</span></c:if>
				    		 Posted ${party.humanReadableCreationDateTime}
				    	</small>
				    </div>
		  		</div>
			    
			    <br>
			    
			    <!-- media content information -->
		    	<div class="row">
				
					<div class="col-12">
							
							<div class="note row">
								<div class="col-2"><span class="badge badge-dark">Start time</span></div>
								<div class="col-10 text-overflow">
									<c:if test="${party.isPlaying()}"><span class="badge badge-success">playing now</span></c:if>
									<c:if test="${party.isExpired() && !party.isPlaying()}"><span class="badge badge-danger">expired</span></c:if>
									${party.humanReadableStartDateTime}
								</div>
							</div>
							
							<div class="note row"><div class="col-2"><span class="badge badge-dark">Movie</span></div><div class="col-10 text-overflow">${party.media.title}</div></div>
							<div class="note row"><div class="col-2"><span class="badge badge-dark">Duration</span></div><div class="col-10 text-overflow">120 min</div></div>
							
							<!-- <div class="dropdown-divider"></div> --><br>
							
							<div class="note row"><div class="col text-overflow"><span class="badge badge-dark">Group organizer and coordinator</span></div></div>
							<div class="row administrator-row">
								<div class="col-9 text-overflow">
									<div class="row">
							  			<c:set var = "party_admin_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
										<c:if test="${party.administrator.profileImagePath != null && party.administrator.profileImagePath.length() > 0}">
											<c:set var = "party_admin_profile_img_src" scope = "request" value = "res/user/${party.administrator.profileImagePath}"/>
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
								
						    
						    <!-- <div class="dropdown-divider"></div> -->
							<br>
							<!-- <div class="row"> -->
							
							<div class="">
							<div class="note row"><div class="col text-overflow"><span class="badge badge-dark">${party.invitations.size()} total invitations</span></div></div>
							<div class="row invitations-row text-overflow">
						    	
					  			<c:forEach items="${party.invitations}" var="invitation">
					  				<c:set var = "party_guest_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
									<c:if test="${invitation.guest.profileImagePath != null && invitation.guest.profileImagePath.length() > 0}">
										<c:set var = "party_guest_profile_img_src" scope = "request" value = "res/user/${invitation.guest.profileImagePath}"/>
									</c:if>
							  		<div class="col-auto col-light-left"><a href="user?id=${invitation.guest.id}"><img src="${party_guest_profile_img_src}" width="30px" class="avatar-img rounded-circle"></a></div>
					  			</c:forEach>
					  			
					  		</div>
					  		</div>
					  		
					  		<br>
					  		
							<div class="">
							<div class="note row"><div class="col text-overflow"><span class="badge badge-dark">${party.participations.size() + 1} participants</span></div></div>
							<div class="row participants-row text-overflow">
			    	
					  			<c:set var = "party_admin_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
								<c:if test="${party.administrator.profileImagePath != null && party.administrator.profileImagePath.length() > 0}">
									<c:set var = "party_admin_profile_img_src" scope = "request" value = "res/user/${party.administrator.profileImagePath}"/>
								</c:if>
						  		<div class="col-auto col-light-left"><a href="user?id=${party.administrator.id}"><img src="${party_admin_profile_img_src}" width="30px" class="avatar-img rounded-circle"></a></div>
					  			
					  			<c:forEach items="${party.participations}" var="participation">
					  				<c:set var = "party_participant_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
									<c:if test="${participation.participant.profileImagePath != null && participation.participant.profileImagePath.length() > 0}">
										<c:set var = "party_participant_profile_img_src" scope = "request" value = "res/user/${participation.participant.profileImagePath}"/>
									</c:if>
							  		<div class="col-auto col-light-left"><a href="user?id=${participation.participant.id}"><img src="${party_participant_profile_img_src}" width="30px" class="avatar-img rounded-circle"></a></div>
					  			</c:forEach>
					  			
					  		</div>
					  		</div>
					  		
					  		
					  		<!-- </div> -->
					  		
						</div>
						
						<!-- <div class="col-5">
							
						</div> -->
			    </div>
			    <!-- <br> -->
		  		
			    <%-- <div class="row" id="count-row">
			    	<div class="col">
			    		<small class="fa-container"><a class="add-like"><i class="fa fa-thumbs-up rounded-circle clickable"></i></a></small>
			    		<small class="fa-container"><a class="add-love"><i class="fa fa-heart rounded-circle clickable"></i></a></small>
			    		<small class="note" id="likes-loves-count">&nbsp;&nbsp; ${post_to_display.numLikes} likes, ${post_to_display.numLoves} loves</small>
			    	</div>
			    	<div class="col" id="n-comments-col">
			    		<small class="note show-comments-list-btn">${post_to_display.numAllComments} comments</small>
			    	</div>
			    </div> --%>
			    
			    <!-- <div class="dropdown-divider"></div> -->
			    
			    
			    <%-- Like, Comment and Share buttons 
			    <div class="row post-action-row">
			    	<div class="col post-action"><p class="btn add-like"><i class="fa fa-thumbs-up"></i>&nbsp;&nbsp;Like<p></div>
			    	<div class="col post-action"><p class="btn comment-btn"><i class="fa fa-comment"></i>&nbsp;&nbsp;Comment<p></div>
			    	<form class="col post-action" method="POST" action="sharepost">
			    		<input type="hidden" name="share-post-id" value="${post_to_display.id}">
			    		<p class="btn share-post-submit-btn"><i class="fa fa-share"></i>&nbsp;&nbsp;Share<p>
			    	</form>
			    </div> 
			    --%>
			    
			    
			    <%-- <div class="comments-list">
			    	<jsp:include page="./comments_list.jsp"></jsp:include>
			    </div>
			    
			    <c:if test="${post_to_display.comments.size() != post_to_display.numAllComments}">
				    <div class="text-center" id="view-more-comments-tag">
						<div class="loader loader-sm more-comments-loader d-none"></div>
						<button class="btn btn-link view-all-btn" id="comments-view-more-btn">view all</button>
					</div>
				</c:if> --%>
			    
		  </div>
	</div>

</c:forEach>