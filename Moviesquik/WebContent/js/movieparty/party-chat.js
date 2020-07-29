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
});