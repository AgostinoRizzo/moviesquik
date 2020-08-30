<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<form class="media p-3 new-post-media" method="POST" action="createpost">

	  <div class="media-body">
	  		<div class="row">
		  		<div class="col-auto col-light-left"><img src="${user_profile_img_src}" width="30px" class="avatar-img rounded-circle"></div>
			    <div class="col col-light-right">
			    	<textarea class="form-control text-area-post" rows="2" id="new-post-text" placeholder="What's in your mind, ${user.firstName}?" name="post-text"></textarea>
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
				    		<jsp:include page="./emoji_panel.jsp"></jsp:include>
				    	</div>
				    	
				    	<!-- Review post action -->
				    	<!-- 
				    	<div class="post-action dropdown row-item">
				    		
				    		<div class="btn btn-secondary dropdown-toggle" role="button" id="dropdown-emoji-panel-link" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    			<h5>&#127909;</h5>&nbsp;Review
				    		</div>
				    		<jsp:include page="./emoji_panel.jsp"></jsp:include>
				    	</div>
				    	 -->
				    	
				    	<!-- CinemaRoom post action -->
				    	<div class="post-action dropdown row-item">
				    		
				    		<div class="btn btn-secondary dropdown-toggle" role="button" id="dropdown-emoji-panel-link" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    			<h5>&#127871;</h5>&nbsp;Cinema Room
				    		</div>
				    		<jsp:include page="./emoji_panel.jsp"></jsp:include>
				    	</div>
				    	
				    	<!-- Social-Facebook posting -->
				    	<c:if test="${user != null && user.facebookId != null}">
					    	<div class="post-action dropdown row-item">
					    		
					    		<button class="btn btn-secondary dropdown-toggle" role="button" id="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					    			<h5><span class="fa fa-facebook text-primary"></span></h5> Social
					    		</button>
					    		<div class="dropdown-menu header-dropdown-menu">
					    			<a class="dropdown-item clickable-light" id="publish-on-facebook-btn">Publish on Facebook</a>
					    		</div>
					    	</div>
				    	</c:if>
				    	
				    	<div class="row-item" id="publish-row-item">
				    		<button class="col post-action btn btn-main btn-sm" type="submit">Publish</button>
				    	</div>
				    	
				    	<!-- <div class="col-2 post-action"><p class="btn"><i class="fa fa-comment"></i>&nbsp;&nbsp;Comment<p></div>
				    	<div class="col-2 post-action"><p class="btn"><i class="fa fa-share"></i>&nbsp;&nbsp;Share<p></div> -->
				    </div>
		    	
		    </div>
		    
		    <br>
	  </div>
</form>
