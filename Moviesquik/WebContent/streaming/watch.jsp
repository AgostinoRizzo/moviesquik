<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

	<title>${media_content.title}</title>
	
	<!-- Bootstrap CSS File -->
	<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Style sheet files -->
	<link href="css/main.css" rel="stylesheet">
	<link href="css/common.css" rel="stylesheet">
	<link href="css/streaming.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<!-- JavaScript files -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/jquery/jquery-migrate.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="lib/typeahead/bootstrap3-typeahead.js"></script>
	<script src="js/streaming/stream-player.js" type="module"></script>
	
</head>
<body>
	
	<input type="hidden" id="media-id" value="${media_content.id}">
	
	<div id="loading-wrapper">
		<div id="brand-icon"><img alt="" src="res/drawable/logo3.png"></div>
		<div class="loader loader-sm"></div>
	</div>
	
	<jsp:include page="player.jsp"></jsp:include>
	
	<div id="no-connection-modal" class="modal" tabindex="-1">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title">Unable to connect</h5>
	                <button type="button" class="close" data-dismiss="modal">&times;</button>
	            </div>
	            <div class="modal-body">
	                <p>There seems to be an issue connecting to the streaming servers.</p>
	                <p class="text-secondary"><small>Please try again later if the issue persists.</small></p>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-primary" data-dismiss="modal">Retry</button>
	            </div>
	        </div>
	    </div>
	</div>
	
	<div id="no-servers-modal" class="modal" tabindex="-1">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title">Unable to connect</h5>
	                <button type="button" class="close" data-dismiss="modal">&times;</button>
	            </div>
	            <div class="modal-body">
	                <p>There seems to be an issue connecting to the streaming servers.</p>
	                <p class="text-secondary"><strong>The content does not exist or is not available in your country.</strong></p>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-primary" data-dismiss="modal">Okay</button>
	            </div>
	        </div>
	    </div>
	</div>
	
</body>
</html>