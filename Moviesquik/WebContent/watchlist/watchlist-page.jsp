<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script src="https://www.googletagmanager.com/gtag/js?id=UA-176431235-1"></script>
	<script>
		window.dataLayer = window.dataLayer || [];
		function gtag(){dataLayer.push(arguments);}
		gtag('js', new Date());
	
		gtag('config', 'UA-176431235-1');
	</script>
	
	<title>Watch List</title>
	<link rel="icon" type="image/x-icon" href="res/drawable/m_logo.png">
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/header.css" rel="stylesheet">
	<link href="css/widget.css" rel="stylesheet">
	<link href="css/common.css" rel="stylesheet">
	<link href="css/contents.css" rel="stylesheet">
	<link href="css/media.css" rel="stylesheet">
	<link href="css/watchlist.css" rel="stylesheet">
	<link href="css/posting.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="js/anim.js"></script>
	<script src="js/header.js"></script>
	<script src="js/watchlist/create-watchlist.js"></script>
	<script src="js/watchlist/edit-watchlist.js"></script>
	<script src="js/watchlist/share.js"></script>
	<script src="js/bootstrap-validate.js"></script>
	<script src="js/media/show_media_content.js" type="module"></script>
	<script src="js/notification/notifications.js" type="module"></script>
	
</head>
<body>

	<!-- ======================
		  Header section
	======================= -->
	
	<jsp:include page="../header.jsp"></jsp:include>
	
	
	<!-- ======================
		  Watch Lists contents
	======================= -->
	
	<div id="single-watchlist-page-content" class="container">
	<div id="page-content">
			
		<div class="media-contents-list">
			
			<input type="hidden" id="watchlist-id" value="${watchlist.id}">
			
			<div class="media-contents-list-header">
			
					<div class="media-contents-icon-title">
						<h5 class="media-contents-list-header-title">
								<i class="fa fa-list-ul"></i>&nbsp;${watchlist.name}
						</h5>
					</div>
					
					<br><br>
					<p class="note watchlist-description">${watchlist.description}</p>
					
								
					<c:if test="${watchlist.items.isEmpty()}">
						<c:if test="${watchlist.description.length() > 0}">
							<br>
						</c:if>
						<p>No media contents in this Watch List.</p>
					</c:if>
					
					<div class="view-all-contents">
						
						<button class="btn btn-link view-all-btn btn-share-watchlist"><i class="fa fa-share"></i> Share</button>
						
						<form method="POST" action="sharewatchlist?action=other&key=${watchlist.id}" class="modal share-watchlist-input-modal" tabindex="-1">
								
							<input type="hidden" name="watchlist_id" value="${watchlist.id}">
							
						    <div class="modal-dialog">
						        <div class="modal-content">
						            <div class="modal-header">
						                <p class="modal-title">Share <strong>${watchlist.name}</strong></p>
						                <button type="button" class="close" data-dismiss="modal">&times;</button>
						            </div>
						            <div class="modal-body">
						            	<fieldset>
											<label for="name">Enter a text to share</label>&nbsp;&nbsp;<span class="badge badge-warning">Optional</span>
											<div class="input-group">
												<textarea class="form-control share-watchlist-text" rows="2" placeholder="Type a text here" name="text"></textarea>
											</div>
											
											<!-- Emoji post action -->
									    		<div class="btn btn-secondary dropdown-toggle dropdown-emoji-panel-link" role="button" id="dropdown-emoji-panel-link" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									    			&#128522;&nbsp;Emoji
									    		</div>
									    		<jsp:include page="../posting/emoji_panel.jsp"></jsp:include>
										    	
										</fieldset>
						            </div>
						            <div class="modal-footer">
						                <button type="submit" class="btn btn-primary share-watchlist-submit-btn"><i class="fa fa-share"></i> Share</button>
						                <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
						            </div>
						        </div>
						    </div>
						</form>
						
						<%-- <c:if test="${!watchlist.items.isEmpty()}">
							<button id="suggested-view-all-btn" class="btn btn-link view-all-btn">View All</button>
						</c:if> --%>
						
					</div>
					
			</div>
			
			<c:if test="${!watchlist.items.isEmpty()}">
			
				<c:set var="small_view" scope="request" value="1"/>
				
				<!-- media contents -->
				<div class="media-contents-list-items row" role="listbox">
				    <c:forEach items="${watchlist.items}" var="watchlist_item">					    	
						<c:set var="media_content" scope="request" value="${watchlist_item.mediaContent}"/>
						<div class="col-6 col-sm-5 col-md-4 col-lg-3 col-xl-2 media-col-item">
							<jsp:include page="../contents-item.jsp"></jsp:include>
						</div>
					</c:forEach>
				</div>
				
				<div class="more-collapse-box d-none">
					<button id="suggested-view-more-btn" class="btn btn-link view-more-btn"><span class="fa fa-plus checked"></span> More</button>
					<button id="suggested-view-collapse-btn" class="btn btn-link view-collapse-btn"><span class="fa fa-minus checked"></span> Collapse</button>
				</div>
			
			</c:if>
			
		</div>

		<c:if test="${on_shared != null}">
			<div id="share-watchlist-modal" class="modal" tabindex="-1">
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <div class="modal-header">
			                <h5 class="modal-title">
			                
			                	<c:if test="${on_shared.equals(\"success\")}">
			                		<i class="fa fa-check colored"></i> Watchlist shared
			                	</c:if>
			                	<c:if test="${!on_shared.equals(\"success\")}">
			                		<i class="fa fa-warning colored"></i> Watchlist not shared
			                	</c:if>
			                
			                </h5>
			                <button type="button" class="close" data-dismiss="modal">&times;</button>
			            </div>
			            <div class="modal-body">
			            
			            	<c:if test="${on_shared.equals(\"success\")}">
		                		Watchlist successfully shared with your friends
		                	</c:if>
		                	<c:if test="${!on_shared.equals(\"success\")}">
		                		Your watchlist is empty
		                	</c:if>
			            
			            </div>
			            <div class="modal-footer">
			                <button type="button" class="btn btn-primary" data-dismiss="modal">Okay</button>
			            </div>
			        </div>
			    </div>
			</div>
		</c:if>

		
	</div>
	</div>
	
</body>
</html>