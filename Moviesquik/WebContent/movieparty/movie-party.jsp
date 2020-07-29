<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>Movie Party</title>
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/common.css" rel="stylesheet">
	<link href="css/movieparty.css" rel="stylesheet">
	<link href="css/contents.css" rel="stylesheet">
	<link href="css/media.css" rel="stylesheet">
	<link href="css/home.css" rel="stylesheet">
	<link href="css/posting.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	<script src="js/bootstrap-validate.js"></script>
	<script src="js/media/show_media_content.js"></script>
	<script src="js/movieparty/create-party.js" type="module"></script>
	<script src="js/movieparty/party-chat.js" type="module"></script>
	
</head>
<body>

	<!-- ======================
		  Header section
	======================= -->
	
	<jsp:include page="../header.jsp"></jsp:include>
	
	
	<!-- ======================
		  Create Party contents
	======================= -->
	
	<div id="create-party-page-content" class="">
		
		<div id="movie-party-header">
		
			<div class="note">MOVIE PARTY</div>
			<h2>${party.name}</h2>
			<h5 class="note">${party.description}</h5>
			
			<br>
			
			<c:if test="${party.isExpired() && !party.isPlaying()}"><h5><span class="badge badge-danger">The event is expired</span> - ${party.humanReadableStartDateTime}</h5></c:if>
			<c:if test="${!party.isExpired() && !party.isPlaying()}">
				<h5><span class="badge badge-info"><i class="fa fa-calendar"></i> Upcoming</span> - ${party.humanReadableStartDateTime}</h5><br>
				<button type="button" class="btn btn-link"><i class="fa fa-check-square"></i> Participate</button>
				<button type="button" class="btn btn-link"><i class="fa fa-question-circle"></i> Maybe</button>
				<button type="button" class="btn btn-link"><i class="fa fa-times-circle"></i> I can't</button>
			</c:if>
			<c:if test="${party.isPlaying()}">
				<h5><span class="badge badge-success"><i class="fa fa-circle"></i> Being played now...</span> - started ${party.humanReadableStartDateTime}</h5><br>
				<div class="btn btn-outline-success btn-sm"><i class="fa fa-film"></i> Watch now</div>
			</c:if>
			
			
			
		</div>
		
		<br>
		
		<div><img class="movieparty-media-card-img" src="${party.media.poster}"></div>		
		
		<br><br>
		
		<div class="container">
			<div class="row">
			
				<div class="media p-3 media-post-box main-media-post-box movieparty-media-box col-6">
				
					  <input type="hidden" id="party-id" value="${party.id}">
					  
					  <div class="media-body">
					  
					  		<div class="row">
					  			<div class="col-auto col-light-left"><img src="res/drawable/movie_party_icon.png" width="50px" class=""></div>
							    <div class="col col-light-right header-text">
							    	<h5>
							    		${party.name}
							    		<c:if test="${party.description != null && party.description.length() > 0}"> - ${party.description}</c:if>
							    	
							    	</h5>
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
										
										<div class="row">
											<div class="col-auto">Start time</div>
											<div class="col-auto note text-overflow">
												<c:if test="${party.isPlaying()}"><span class="badge badge-success">playing now</span></c:if>
												<c:if test="${party.isExpired() && !party.isPlaying()}"><span class="badge badge-danger">expired</span></c:if>
												${party.humanReadableStartDateTime}
											</div>
										</div>
										
										<div class="row"><div class="col-auto">Movie&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="col-auto note text-overflow">${party.media.title}</div></div>
										<div class="row"><div class="col-auto">Duration&nbsp;&nbsp;</div><div class="col-auto note text-overflow">120 min</div></div>
										
										<hr class="mb-4">
										
										<div class="row"><div class="col text-overflow">Group organizer and coordinator</div></div>
										<br>
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
											
									    
									    <hr class="mb-4">
										
										<div class="">
										<div class="row"><div class="col text-overflow">${party.invitations.size()} total invitations</div></div>
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
								  		
								  		<br><br>
								  		
										<div class="">
										<div class="row"><div class="col text-overflow">${party.participations.size() + 1} participants</div></div>
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
								  		
									</div>
						    </div>
						    
						    
					  </div>
				</div>
				
				<!-- movie party chat section -->
				<div class="col-6">
				
					<form class="media p-3 new-post-media" method="POST" action="createpost">

						  <div class="media-body">
						  		<div class="row">
							  		<div class="col-auto col-light-left"><img src="${user_profile_img_src}" width="30px" class="avatar-img rounded-circle"></div>
								    <div class="col col-light-right col-light-left">
								    	<textarea class="form-control text-area-post" rows="2" id="new-msg-text" placeholder="What do you think about ${party.media.title}, ${user.firstName}?" name="post-text"></textarea>
								    </div>
								    
								    <div class="col col-light-right" id="chat-send-btn-col">
								    	<button class="col post-action btn btn-success btn-sm" id="chat-send-btn" type="submit"><i class="fa fa-paper-plane"></i></button>
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
									    
									    <div class="participants-row text-overflow" id="chat-participants">
						    	
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
							    
							    <br>
						  </div>
					</form>
					
					
			  		
			  		<br>
			  		
			  		<div>
			  			There are no messages for this party.
			  		</div>
				
				</div>
			
			</div>
		</div>
					
	</div>
	
	<jsp:include page="../footer.html"></jsp:include>
	
</body>
</html>