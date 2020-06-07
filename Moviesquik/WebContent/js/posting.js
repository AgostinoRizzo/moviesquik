/**
 * 
 */


$(document).ready( function()
	{
		$(document).on("click", ".emoji-panel .emoji", function() 
		{
			var emoji_code = $(this).text();
			var textArea = $(this).closest(".media-body").find(".text-area-post");
			textArea.val(textArea.val() + emoji_code);
		});
		
		
		$(document).on("keyup", "#new-post-text", function() 
		{
			var content = $.trim($(this).val());
			if ( content.length > 0 )
				$("#publish-row-item").show();
			else
				$("#publish-row-item").hide();
		});
		
		$(document).on("keyup", ".new-comment-text", function() 
		{
			var content = $.trim($(this).val());
			if ( content.length > 0 )
				$(this).closest(".media").find(".publish-comment-row-item").show();
			else
				$(this).closest(".media").find(".publish-comment-row-item").hide();
		});
		
		$(document).on("click", ".comment-btn", function() 
		{
			var text = $(this).text().trim();
			
			if ( text == "Comment" )
				{
					$(this).closest(".media").next(".new-comment-media").show();
					$(this).html("<i class=\"fa fa-remove\"></i>&nbsp;&nbsp;Remove");
				}
			else
				{
					$(this).closest(".media").next(".new-comment-media").hide();
					$(this).html("<i class=\"fa fa-comment\"></i>&nbsp;&nbsp;Comment");
				}
		});
		
		$(document).on("click", ".share-post-submit-btn", function() 
		{
			$(this).closest("form").submit();
		});
		
		$(document).on("click", ".shared-watchlist-box", function() 
		{
			location.href = "watchlist?action=page&key=" + $(this).closest(".media-post-box").find("#shared-watchlist-id").val();
		});
	}
);