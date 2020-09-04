/**
 * 
 */

import '../chat/chat-controller.js';

function onChact() {
	
}

$(document).ready( function() 
{
	const foreignUser = $("#foreign-user-flag").length;
	
	$(document).on("click", ".emoji-panel .emoji", function() 
			{
				var emoji_code = $(this).text();
				var textArea = $(this).closest(".media-body").find(".text-area-post");
				textArea.val(textArea.val() + emoji_code);
			});
	
	$(document).on("click", "#chat-send-btn", function() 
	{
		if ( foreignUser )
			$("#cannot-send-msg-modal").modal('show');
		else
			onChatSend();
	});
	
	if ( !foreignUser )
	{
		const userId = $("#user-id").val();
		const userIconSrc = $("#user-icon").attr('src');
		const partyId = $("#party-id").val();
		var textArea = $(this).find("#new-msg-text");
		
		onChatInit( textArea, userId, userIconSrc, partyId, true );
	}
});