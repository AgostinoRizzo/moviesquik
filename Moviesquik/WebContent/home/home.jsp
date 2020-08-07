<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>


<!-- <div class="chapternav">
	<div class="col-1"></div>
	<div class="col-1"></div>
	<div class="col-1"></div>
	<div class="col-1"></div>
	<div class="col-1"><h3><span class="fa fa-film"></span></h3>For You</div>
	<div class="col-1"><h3><span class="fa fa-film"></span></h3>Trending Now</div>
	<div class="col-1"><h3><span class="fa fa-film"></span></h3>Most Popular</div>
	<div class="col-1"><h3><span class="fa fa-film"></span></h3>Most Favorites</div>
	<div class="col-1"></div>
	<div class="col-1"></div>
	<div class="col-1"></div>
	<div class="col-1"></div>
	<div class="col-1"></div>
</div> -->

<div class="home-wrapper">
	
	<div class="home-container-wrapper row" id="home-wrapper">
		
		<input type="hidden" id="user-id" value="${user.id}">
		
		<!-- Friends sidenav -->
		<div class="col-2 scollable-col sidenav users-col home-container-col">
					<jsp:include page="sidenav.jsp"></jsp:include>
		</div>
		
		<!-- List all personal posts -->
		<div class="col-4 scollable-col" id="posts-column">
					<!-- New post box -->
					<jsp:include page="../posting/new_post_box.jsp"></jsp:include>
					<div id="news-list">
						<jsp:include page="../posting/news_content.jsp"></jsp:include>
					</div>
					<div class="text-center" id="view-more-posts-tag">
						<div class="loader loader-sm d-none"></div>
						<button class="btn btn-link view-all-btn" id="posts-view-more-btn">view more</button>
					</div>
					
					<div id="post-feedback-popup" class="modal" tabindex="-1">
					    <div class="modal-dialog">
					        <div class="modal-content">
					            <div class="modal-header">
					                <h5 class="modal-title">Post feedback</h5>
					                <button type="button" class="close" data-dismiss="modal">&times;</button>
					            </div>
					            <div class="modal-body">
					                <p>The provided email address is already used.</p>
					                <p class="text-secondary"><small>Please enter a new email address.</small></p>
					            </div>
					            <div class="modal-footer">
					                <button type="button" class="btn btn-main btn-main-light" data-dismiss="modal">Cancel</button>
					                <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
					            </div>
					        </div>
					    </div>
					</div>
		</div>
		
		<!-- MovieParty posts -->
		<div class="col-4 scollable-col" id="movie-parties-column">
					<jsp:include page="../movieparty/party-list-header.jsp"></jsp:include>
					<div id="movieparties-list">
						<jsp:include page="../movieparty/party-list.jsp"></jsp:include>
					</div>
					<div class="text-center" id="view-more-movieparties-tag">
						<div class="loader loader-sm d-none"></div>
						<button class="btn btn-link view-all-btn" id="movieparties-view-more-btn">view more</button>
					</div>
		</div>
		
		<!-- Media contents -->
		<div class="col-10 scollable-col d-none" id="media-contents-column">
					<!-- Media contents -->
					<div class="news-media-contents-col home-container-col">
						<c:set var="small_view" scope="request" value="true"/>
						<%-- <c:set var="no_view_suggested" scope="request" value="true"/> ### uncomment to hide suggested contents ### --%>
						<jsp:include page="../contents.jsp"></jsp:include>
						<c:remove var="small_view"/>
						<%-- <c:remove var="no_view_suggested"/> ### uncomment to hide suggested contents ### --%>
					</div>
		</div>
		
		<!-- Chats sidenav -->
		<div class="col-2 scollable-col chat-list" id="chats-sidenav-column">
					<jsp:include page="users.jsp"></jsp:include>
					<jsp:include page="../chat/chat-popup.jsp"></jsp:include>
		</div>
		
		
		
		<div class="home-container col-auto home-col">
			
			<%-- <div class="row">
				<c:if test="${user != null}">
					
					<c:set var="not_view_media_body" scope="request" value="true"/>
					<br>
					<div class="media-contents-list">
				
						<div class="media-contents-list-header">
								<div class="media-contents-icon-title">
									<c:if test="${empty small_view}">
										<h4 class="media-contents-list-header-title"><!-- <span class="fa fa-lightbulb-o checked"></span>  -->Suggested for you</h4>
									</c:if>
									<c:if test="${not empty small_view}">
										<p class="media-contents-list-header-title"><!-- <span class="fa fa-lightbulb-o checked"></span>  -->Suggested for you</p>
									</c:if>
								</div>
								<div class="view-all-contents">
									<button id="suggested-view-all-btn" class="btn btn-link view-all-btn invisible">View All</button>
								</div>
						</div>
						<br><br>
						<!-- <hr class="mb-4"> -->
						
						<!-- Top content -->
					    <div id="suggested-media-contents-items" class="media-contents-list-items row" role="listbox">
					    	<c:if test="${empty small_view}">
					        	<jsp:include page="../contents-items.jsp"></jsp:include>
					        </c:if>
					        <c:if test="${not empty small_view}">
					        	<jsp:include page="../contents-items-sm.jsp"></jsp:include>
					        </c:if>
					    </div>
						
						<div class="more-collapse-box d-none">
							<button id="suggested-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
							<button id="suggested-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
						</div>
					</div>
					
					<c:remove var="not_view_media_body"/>
					
					<div></div>
					
				</c:if>
			</div> --%>
			
			<div class="row">
			
				<%-- <div class="col-auto users-col home-container-col">
					<jsp:include page="users.jsp"></jsp:include>
				</div>  --%>
				
				<%-- <div class="col home-container-col"> ----------------------------
					<h4 class="media-contents-list-header-title"><!-- <span class="fa fa-lightbulb-o checked"></span>  -->&nbsp;&nbsp;News</h4>
					<br><br>
					<jsp:include page="../posting/news.jsp"></jsp:include>
				</div> --%>
				
				<%-- <div class="col-auto news-media-contents-col home-container-col">
					<c:set var="small_view" scope="request" value="true"/>
					<c:set var="no_view_suggested" scope="request" value="true"/> ### uncomment to hide suggested contents ###
					<jsp:include page="../contents.jsp"></jsp:include>
					<c:remove var="small_view"/>
					<c:remove var="no_view_suggested"/> ### uncomment to hide suggested contents ###
				</div> --%>
				
				<%-- 
				<div class="col-auto home-navbar-col home-container-col">
					<nav class="navbar home-navbar">
		
						  <!-- Links -->
						  <ul class="navbar-nav home-navbar-nav">
							    <li class="nav-item row home-nav-item">
							    	<h5>&#128240;</h5>&nbsp;<a class="nav-link" href="">Home</a>
							    </li>
							    <li class="nav-item row home-nav-item">
							    	<h5>&#127916;</h5>&nbsp;<a class="nav-link" href="">Movies</a>
							    </li>
							    <li class="nav-item row home-nav-item">
							    	<h5>&#128250;</h5>&nbsp;<a class="nav-link" href="">TV Shows</a>
							    </li>
							    <li class="nav-item row home-nav-item">
							    	<h5>&#127871;</h5>&nbsp;<a class="nav-link" href="">Cinema Rooms</a>
							    </li>
							    <li class="nav-item row home-nav-item">
							    	<strong class="note"><br>Explore</strong>
							    </li>
							    <li class="nav-item row home-nav-item">
							    	<h5>&#128203;</h5>&nbsp;<a class="nav-link" href="">Watch List</a>
							    </li>
							    <li class="nav-item row home-nav-item">
							    	<h5>&#128106;</h5>&nbsp;<a class="nav-link" href="">Family</a>
							    </li>
							    <li class="nav-item row home-nav-item">
							    	<h5>&#128108;</h5>&nbsp;<a class="nav-link" href="">Friend List</a>
							    </li>
							    <li class="nav-item row home-nav-item">
							    	<h5>&#128270;</h5>&nbsp;<a class="nav-link" href="">Search</a>
							    </li>
							    <li class="nav-item row home-nav-item">
							    	<strong class="note"><br>Watch</strong>
							    </li>
							    
							    <c:forEach items="${genres}" var="genre">
							    	<li class="nav-item row home-nav-item">
							    		<h5>&#127902;</h5>&nbsp;<a class="nav-link" href="">${genre}</a>
							    	</li>
									<option value="${genre}">${genre}</option>
								</c:forEach>
						  </ul>
					
					</nav>
				</div> --%>
				
			</div>
		</div>
		
	</div>

</div>