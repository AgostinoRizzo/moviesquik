/**
 * 
 */

const REMOVE_WATCHLIST_ITEM_MOUSE_DOWN_TIME = 2000;

function onRemoveWatchlistItem(mediaContentId, watchlistId) 
{
	$("#remove-watchlist-item-modal #media_content_id").val(mediaContentId)
	$("#remove-watchlist-item-modal #watchlist_id").val(watchlistId);
	$("#remove-watchlist-item-modal").modal("show");
}

function onRemoveWatchlist(watchlistId) 
{
	$("#remove-watchlist-modal #watchlist_id").val(watchlistId);
	$("#remove-watchlist-modal").modal("show");
}

$(document).ready( function() 
{
	$(document).on("click", ".remove-media-item-btn", function() 
	{
		onRemoveWatchlistItem($(this).closest(".media-content-card").find("#media-id").val(),
							  $(this).closest(".media-contents-list").find("#watchlist-id").val());
	});
	
	$(document).on("click", ".remove-watchlist-btn", function() 
	{
		onRemoveWatchlist($(this).closest(".media-contents-list").find("#watchlist-id").val());
	});
	
	if ( $("#item-removed-modal").length )
		$("#item-removed-modal").modal("show");
	
	if ( $("#watchlist-removed-modal").length )
		$("#watchlist-removed-modal").modal("show");
});