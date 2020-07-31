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
	
	$("#chat-send-btn").click( function() {
		alert("TODO: Check foreign user. If not then send message.");
	});
});