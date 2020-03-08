/**
 * 
 */

$(document).ready( function()
	{
		$(".emoji-panel .emoji").click(function() 
		{
			var emoji_code = $(this).text();
			$(this).closest(".media-body").find(".text-area-post").append(emoji_code);
		});
		
		
		$("#new-post-text").keyup(function() 
		{
			var content = $.trim($(this).val());
			if ( content.length > 0 )
				$("#publish-row-item").show();
			else
				$("#publish-row-item").hide();
		});
		
		$(".new-comment-text").keyup(function() 
		{
			var content = $.trim($(this).val());
			if ( content.length > 0 )
				$(this).closest(".media").find(".publish-comment-row-item").show();
			else
				$(this).closest(".media").find(".publish-comment-row-item").hide();
		});
		
		$(".comment-btn").click(function() 
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
	}
);