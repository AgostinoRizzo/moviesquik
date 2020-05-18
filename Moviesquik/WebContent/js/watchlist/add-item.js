/**
 * 
 */

const ADD_WATCHLIST_ITEM_URL = "addtowatchlist";

function onAddToWatchlist(media_content_id, watchlistName) 
{
	$.ajax(
		{
			type: "POST",
			url: ADD_WATCHLIST_ITEM_URL,
			data: { "media_content_id":media_content_id, "watchlist_name": watchlistName },
			success: function(data)
				{
					var title = "";
					var description = "";
					
					if ( data.added )
					{
						title = "<i class=\"fa fa-check\"></i> Media content added";
						description = "Media content successfully added to the " + watchlistName + " watchlist";
					}
					else
					{
						title = "<i class=\"fa fa-warning\"></i> Media content already added";
						description = "Media content already added to the " + watchlistName + " watchlist";
					}
					
					$("#add-watchlist-item-modal").find(".modal-title").html(title);
					$("#add-watchlist-item-modal").find(".modal-body").text(description);
					$("#add-watchlist-item-modal").modal('show');
				}
		}
	);
}

$(document).ready( function() 
{
	$(document).on("click", ".watchlist-dropdown-item", function() 
	{
		var mediaContentId = $(this).closest("#media-content-page-container").find("#media-id").val();
		if ( mediaContentId === undefined ) mediaContentId = $(this).closest(".media-content-card").find("#media-id").val();
		if ( mediaContentId === undefined ) return;
		onAddToWatchlist(mediaContentId ,$(this).find(".watchlist-name").text().trim()); 
	});
});