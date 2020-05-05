<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

		

<!-- New post box -->
<jsp:include page="./new_post_box.jsp"></jsp:include>

<!-- Media contents -->
<div class="col-auto news-media-contents-col home-container-col">
	<c:set var="small_view" scope="request" value="true"/>
	<%-- <c:set var="no_view_suggested" scope="request" value="true"/> ### uncomment to hide suggested contents ### --%>
	<jsp:include page="../contents.jsp"></jsp:include>
	<c:remove var="small_view"/>
	<%-- <c:remove var="no_view_suggested"/> ### uncomment to hide suggested contents ### --%>
</div>
				
<!-- List all personal posts -->
<div id="news-list">
	<jsp:include page="./news_content.jsp"></jsp:include>
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