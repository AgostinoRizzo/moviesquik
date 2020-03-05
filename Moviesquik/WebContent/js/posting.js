/**
 * 
 */

$(document).ready( function()
	{
		$(".emoji-panel .emoji").click(function() 
		{
			var emoji_code = $(this).text();
			$(".text-area-post").append(emoji_code);
		});
		
		$("#new-post-text").keyup(function() 
		{
			var content = $.trim($(this).val());
			if ( content.length > 0 )
				$("#publish-row-item").show();
			else
				$("#publish-row-item").hide();
		});	
	}
);