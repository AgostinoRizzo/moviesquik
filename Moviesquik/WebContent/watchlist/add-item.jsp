<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="btn-group">
	<button class="btn btn-outline-warning btn-sm dropdown-toggle dropdown-toggle-split add-to-watchlist-btn" data-toggle="dropdown">
		<i class="fa fa-plus"></i> Add 
	</button>
	
	<div class="dropdown-menu dropdown-watchlists">
		
		<div class="dropdown-item can-point watchlist-dropdown-item">
			<i class="fa fa-history"></i> &nbsp;<div class="watchlist-name">Watch Later</div> <span class="badge badge-info">default</span>
		</div>
		<div class="dropdown-item can-point watchlist-dropdown-item">
			<i class="fa fa-heart"></i> &nbsp;<div class="watchlist-name">Favorites</div> <span class="badge badge-info">default</span>
		</div>
		
		<c:forEach items="${user.watchlists}" var="watchlist">
			<c:if test="${!watchlist.isDefault}">
				<div class="dropdown-item can-point watchlist-dropdown-item">
					<i class="fa fa-list-ul"></i> &nbsp;<div class="watchlist-name">${watchlist.name}</div> <span class="badge badge-warning">personal</span>
				</div>
			</c:if>
		</c:forEach>
		
		<a id="new-watchlist-dropdown-item" class="dropdown-item new-watchlist-dropdown-item" href="watchlist"><i class="fa fa-plus"></i> &nbsp;New Watch List</a>
		
	</div>
</div>

<div id="add-watchlist-item-modal" class="modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">Okay</button>
            </div>
        </div>
    </div>
</div>
				    