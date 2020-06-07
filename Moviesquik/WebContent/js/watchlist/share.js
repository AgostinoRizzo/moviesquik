/**
 * 
 */

//const SHARE_WATCHLIST_URL = "sharewatchlist";
//
//function onShareWatchlist(watchlistId, text)
//{
//	text.trim();
//	
//	$.ajax(
//		{
//			type: "POST",
//			url: SHARE_WATCHLIST_URL,
//			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
//			data: { "watchlist_id":watchlistId, "text":text },
//			success: function(data)
//				{
//					var title = "";
//					var description = "";
//					
//					if ( data.shared )
//					{
//						title = "<i class=\"fa fa-check colored\"></i> Watchlist shared";
//						description = "Watchlist successfully shared with your friends";
//					}
//					else
//					{
//						title = "<i class=\"fa fa-warning colored\"></i> Watchlist not shared";
//						description = "Your watchlist is empty";
//					}
//					
//					$("#share-watchlist-modal").find(".modal-title").html(title);
//					$("#share-watchlist-modal").find(".modal-body").text(description);
//					$("#share-watchlist-modal").modal('show');
//				}
//		}
//	);
//}

$(document).ready( function() 
{
	$(document).on("click", ".btn-share-watchlist", function() 
		{
			$(this).closest(".view-all-contents").find(".share-watchlist-input-modal").modal("show");
		});

//	$(document).on("click", ".share-watchlist-submit-btn", function() 
//		{
//			$(this).closest(".modal").modal('hide');
//			var text = $(this).closest(".modal").find(".share-watchlist-text").val();
//			alert(text);
//			onShareWatchlist($(this).closest(".media-contents-list").find("#watchlist-id").val(), text);
//		});
	
	$(document).on("click", ".emoji-panel .emoji", function() 
		{
			var emoji_code = $(this).text();
			var textArea = $(this).closest(".modal").find(".share-watchlist-text");
			textArea.val(textArea.val() + emoji_code);
		});
	
	if ( $("#share-watchlist-modal").length )
		$("#share-watchlist-modal").modal('show');
});