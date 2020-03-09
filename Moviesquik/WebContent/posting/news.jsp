<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div class="container news-container">
	<div class="row">
		
		<div class="col-md-12 col-lg-2">
			<nav class="navbar">

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
							<%-- <option value="${genre}">${genre}</option> --%>
						</c:forEach>
				  </ul>
			
			</nav>
		</div>
		
		<div class="col-md-12 col-lg-6 posts-col">
		
			<!-- New post box -->
			<jsp:include page="./new_post_box.jsp"></jsp:include>
			
			<!-- List all personal posts -->
			<div id="news-list">
				<jsp:include page="./news_content.jsp"></jsp:include>
			</div>
		</div>
		
		<div class="col-md-12 col-lg-4 news-media-contents-col">
			<c:set var="small_view" scope="request" value="true"/>
			<jsp:include page="../contents.jsp"></jsp:include>
			<c:remove var="small_view"/>
		</div>
		
	</div>
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